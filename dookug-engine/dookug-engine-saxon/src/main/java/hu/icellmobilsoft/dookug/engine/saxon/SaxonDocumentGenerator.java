/*-
 * #%L
 * DookuG
 * %%
 * Copyright (C) 2023 i-Cell Mobilsoft Zrt.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package hu.icellmobilsoft.dookug.engine.saxon;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Optional;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.xmlgraphics.util.MimeConstants;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import hu.icellmobilsoft.coffee.cdi.trace.annotation.Traced;
import hu.icellmobilsoft.coffee.cdi.trace.constants.SpanAttribute;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.coffee.rest.validation.xml.JaxbTool;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.se.api.exception.TechnicalException;
import hu.icellmobilsoft.coffee.tool.utils.compress.GZIPUtil;
import hu.icellmobilsoft.dookug.api.dto.constants.ConfigKeys;
import hu.icellmobilsoft.dookug.common.cdi.DocumentGeneratorQualifier;
import hu.icellmobilsoft.dookug.common.cdi.constants.QualifierConstants;
import hu.icellmobilsoft.dookug.common.cdi.template.IDocumentGenerator;
import hu.icellmobilsoft.dookug.common.cdi.template.TemplateContainer;
import hu.icellmobilsoft.dookug.common.dto.constant.XsdConstants;
import hu.icellmobilsoft.dookug.common.rest.cdi.RequestContainer;
import hu.icellmobilsoft.dookug.engine.pdfbox.signing.SignatureGenerator;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.BaseGeneratorSetupType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.InlineGeneratorSetupType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.ParametersDataType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.StoredTemplateGeneratorSetupType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.generator.saxon.SaxonGeneratorParametersData;

/**
 * Saxon / XSLT implementation for document generation
 *
 * @author mate.biro
 * @since 0.0.1
 */
@DocumentGeneratorQualifier(QualifierConstants.DocumentGeneratorType.SAXON)
@ApplicationScoped
public class SaxonDocumentGenerator implements IDocumentGenerator {

    private static final String EMPTY_DATASET = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<empty/>";

    @Inject
    private TemplateContainer templateContainer;

    @Inject
    private RequestContainer requestContainer;

    @Inject
    private JaxbTool jaxbTool;

    @Inject
    @ConfigProperty(name = ConfigKeys.Saxon.DOOKUG_SERVICE_ENGINE_SAXON_FOPCONFIG)
    private Optional<String> fopConfigPathOpt;

    @Inject
    @ConfigProperty(name = ConfigKeys.Saxon.DOOKUG_SERVICE_ENGINE_SAXON_XSLT_LANGUAGE_VARIABLE)
    private Optional<String> xsltLangVarOpt;

    @Inject
    @ConfigProperty(name = ConfigKeys.Saxon.DOOKUG_SERVICE_ENGINE_SAXON_XSLT_LANGUAGE_DEFAULT)
    private Optional<String> xsltLangDefaultOpt;

    @Inject
    private SignatureGenerator signatureGenerator;

    @Inject
    private XSLTTemplateCache xsltTemplateCache;

    @Override
    public void generateToOutputStream(OutputStream outputStream, Map<String, String> parameterData, String digitalSignatureProfile)
            throws BaseException {
        generateToOutputStream(outputStream, new ParametersDataType(), digitalSignatureProfile);
    }

    @Override
    @Traced(component = "XSLT-PDF", kind = SpanAttribute.INTERNAL)
    public void generateToOutputStream(OutputStream outputStream, ParametersDataType parameterData, String digitalSignatureProfile)
            throws BaseException {
        BaseGeneratorSetupType generatorSetup = requestContainer.getGeneratorSetup();

        SaxonGeneratorParametersData saxonParameters;
        if (parameterData != null && parameterData.getGeneratorParameters() != null && parameterData.getGeneratorParameters().length > 0) {
            saxonParameters = jaxbTool
                    .unmarshalXML(SaxonGeneratorParametersData.class, parameterData.getGeneratorParameters(), XsdConstants.SUPER_XSD_PATH);
        } else {
            saxonParameters = new SaxonGeneratorParametersData().withXmlDataset(EMPTY_DATASET.getBytes(StandardCharsets.UTF_8));
        }

        if (!saxonParameters.isSetFopConfig() && fopConfigPathOpt.isEmpty()) {
            throw new TechnicalException(CoffeeFaultType.OPERATION_FAILED, "FOP config file not configured!");
        }

        if (xsltLangVarOpt.isEmpty()) {
            throw new TechnicalException(CoffeeFaultType.OPERATION_FAILED, "XSLT language variable not configured!");
        }

        if (xsltLangDefaultOpt.isEmpty()) {
            throw new TechnicalException(CoffeeFaultType.OPERATION_FAILED, "XSLT default language not configured!");
        }

        Result result = getFOStream(outputStream, saxonParameters, digitalSignatureProfile);
        try (InputStream templateStream = templateContainer.getCompiledResultAsStream();) {
            // setting up XMLDataset
            Source params = null;
            if (BooleanUtils.isTrue(saxonParameters.isXmlDatasetCompressed())) {
                params = new StreamSource(new ByteArrayInputStream(GZIPUtil.decompress(saxonParameters.getXmlDataset())));
            } else {
                params = new StreamSource(new ByteArrayInputStream(saxonParameters.getXmlDataset()));
            }

            // Saxon transformer initialization
            String key = DigestUtils.sha1Hex(templateContainer.getCompiledResultAsBytes());
            Templates template = xsltTemplateCache.get(key);
            Transformer transformer = template.newTransformer();

            // Start XSLT transformation and FOP processing
            transformer.setParameter(xsltLangVarOpt.get(), getLanguage(generatorSetup).toUpperCase());
            transformer.transform(params, result);

            // add digital signing if needed by configuration
            signatureGenerator.addDigitalSignatureIfNeeded(outputStream, digitalSignatureProfile);

        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            throw new TechnicalException(
                    CoffeeFaultType.OPERATION_FAILED,
                    MessageFormat.format("XSLT PDF transformation failed with error: [{0}]", e.getLocalizedMessage()),
                    e);
        } finally {
            // Always call this in the finally block to ensure that resources are closed even if any error occurs between the two signatureGenerator
            // calls
            signatureGenerator.closeStreams();
        }
    }

    /**
     * Get the FO stream
     * 
     * @param outputStream
     *            generate output
     * @param saxonParameters
     * @param digitalSignatureProfile
     *            nullable, the digital signature profile name
     * @throws BaseException
     *             if any error occurs
     */
    @Traced
    private Result getFOStream(OutputStream outputStream, SaxonGeneratorParametersData saxonParameters, String digitalSignatureProfile)
            throws BaseException {
        try {
            // create an instance of fop factory
            FopFactory fopFactory = newFopFactory(saxonParameters);

            // a user agent is needed for transformation
            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

            // Construct fop with desired output format
            Fop fop;
            if (StringUtils.isNotBlank(digitalSignatureProfile)) {
                fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, signatureGenerator.getOutputStreamForUnsignedPdf());
            } else {
                fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, outputStream);
            }

            // Resulting SAX events (the generated FO) must be piped through to FOP
            return new SAXResult(fop.getDefaultHandler());
        } catch (Exception e) {
            throw new TechnicalException(
                    CoffeeFaultType.OPERATION_FAILED,
                    MessageFormat.format("XSLT FO transformation failed with error: [{0}]", e.getLocalizedMessage()),
                    e);
        }
    }

    private FopFactory newFopFactory(SaxonGeneratorParametersData saxonParameters) throws BaseException {
        try {
            // create an instance of fop factory
            FopFactory fopFactory = null;
            if (saxonParameters.getFopConfig() != null) {
                try (InputStream fopConfig = new ByteArrayInputStream(saxonParameters.getFopConfig())) {
                    fopFactory = FopFactory.newInstance(Path.of(fopConfigPathOpt.get()).toUri(), fopConfig);
                }
            }
            if (fopFactory == null) {
                fopFactory = FopFactory.newInstance(new File(fopConfigPathOpt.get()));
            }

            return fopFactory;
        } catch (Exception e) {
            throw new TechnicalException(
                    CoffeeFaultType.OPERATION_FAILED,
                    MessageFormat.format("XSLT FO transformation failed with error: [{0}]", e.getLocalizedMessage()),
                    e);
        }

    }

    private String getLanguage(BaseGeneratorSetupType baseGeneratorSetup) {
        String requestLanguage = null;
        if (baseGeneratorSetup instanceof StoredTemplateGeneratorSetupType generatorSetup) {
            requestLanguage = generatorSetup.getTemplate().getTemplateLanguage() == null ? null : generatorSetup.getTemplate().getTemplateLanguage();
        } else if (baseGeneratorSetup instanceof InlineGeneratorSetupType generatorSetup) {
            requestLanguage = generatorSetup.getTemplateLanguage() == null ? null : generatorSetup.getTemplateLanguage();
        }

        if (StringUtils.isBlank(requestLanguage)) {
            return xsltLangDefaultOpt.get();
        }
        return requestLanguage;
    }
}
