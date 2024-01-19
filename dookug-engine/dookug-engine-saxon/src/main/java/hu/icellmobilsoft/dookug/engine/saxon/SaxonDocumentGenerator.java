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
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Optional;

import javax.xml.XMLConstants;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.xmlgraphics.util.MimeConstants;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.coffee.dto.exception.TechnicalException;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.coffee.rest.validation.xml.JaxbTool;
import hu.icellmobilsoft.coffee.tool.utils.compress.GZIPUtil;
import hu.icellmobilsoft.dookug.api.dto.constants.ConfigKeys;
import hu.icellmobilsoft.dookug.common.cdi.DocumentGeneratorQualifier;
import hu.icellmobilsoft.dookug.common.cdi.constants.QualifierConstants;
import hu.icellmobilsoft.dookug.common.cdi.sign.DigitalSigningDto;
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
import net.sf.saxon.TransformerFactoryImpl;

/**
 * Saxon / XSLT implementation for document generation
 *
 * @author mate.biro
 * @since 0.0.1
 */
@DocumentGeneratorQualifier(QualifierConstants.DocumentGeneratorType.SAXON)
@ApplicationScoped
public class SaxonDocumentGenerator implements IDocumentGenerator {

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

    @Override
    public void generateToOutputStream(OutputStream outputStream, Map<String, String> parameterData, DigitalSigningDto digitalSigningDto)
            throws BaseException {
        generateToOutputStream(outputStream, new ParametersDataType(), digitalSigningDto);
    }

    @Override
    public void generateToOutputStream(OutputStream outputStream, ParametersDataType parameterData, DigitalSigningDto digitalSigningDto)
            throws BaseException {
        BaseGeneratorSetupType generatorSetup = requestContainer.getGeneratorSetup();
        SaxonGeneratorParametersData saxonParameters = jaxbTool
                .unmarshalXML(SaxonGeneratorParametersData.class, parameterData.getGeneratorParameters(), XsdConstants.SUPER_XSD_PATH);

        if (!saxonParameters.isSetXmlDataset()) {
            throw new TechnicalException(CoffeeFaultType.OPERATION_FAILED, "xmlDataSet is missing from setup!");
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

        InputStream templateStream = new ByteArrayInputStream(templateContainer.getCompiledResultAsBytes());
        Source params = null;
        if (BooleanUtils.isTrue(saxonParameters.isXmlDatasetCompressed())) {
            params = new StreamSource(new ByteArrayInputStream(GZIPUtil.decompress(saxonParameters.getXmlDataset())));
        } else {
            params = new StreamSource(new ByteArrayInputStream(saxonParameters.getXmlDataset()));
        }

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

            // a user agent is needed for transformation
            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

            // Construct fop with desired output format
            Fop fop;
            if (digitalSigningDto != null) {
                fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, signatureGenerator.getOutputStreamForUnsignedPdf());
            } else {
                fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, outputStream);
            }

            // Setup XSLT
            TransformerFactory factory = TransformerFactory
                    .newInstance(TransformerFactoryImpl.class.getName(), TransformerFactoryImpl.class.getClassLoader());
            factory.setFeature(XMLConstants.ACCESS_EXTERNAL_DTD, false);
            factory.setFeature(XMLConstants.ACCESS_EXTERNAL_SCHEMA, false);
            factory.setFeature(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, false);
            Transformer transformer = factory.newTransformer(new StreamSource(templateStream));

            // Resulting SAX events (the generated FO) must be piped through to FOP
            Result result = new SAXResult(fop.getDefaultHandler());

            // Start XSLT transformation and FOP processing
            // That's where the XML is first transformed to XSL-FO and then PDF is created
            transformer.setParameter(xsltLangVarOpt.get(), getLanguage(generatorSetup).toUpperCase());
            transformer.transform(params, result);

            signatureGenerator.addDigitalSignatureIfNeeded(outputStream, digitalSigningDto);

        } catch (Exception e) {
            throw new TechnicalException(
                    CoffeeFaultType.OPERATION_FAILED,
                    MessageFormat.format("XSLT transformation to PDF failed with error: [{0}]", e.getLocalizedMessage()),
                    e);
        } finally {
            // ezt mindig hivjuk meg a finally blokkban, hogy ha barmilyen hiba tortenne a ket signatureGenerator hivas kozott, akkor is lezarjuk az
            // eroforrasokat
            signatureGenerator.closeStreams();
        }
    }

    private String getLanguage(BaseGeneratorSetupType baseGeneratorSetup) {
        String requestLanguage = null;
        if (baseGeneratorSetup instanceof StoredTemplateGeneratorSetupType generatorSetup) {
            requestLanguage = generatorSetup.getTemplate().getTemplateLanguage() == null ? null
                    : generatorSetup.getTemplate().getTemplateLanguage().name();
        } else if (baseGeneratorSetup instanceof InlineGeneratorSetupType generatorSetup) {
            requestLanguage = generatorSetup.getTemplateLanguage() == null ? null : generatorSetup.getTemplateLanguage().name();
        }

        if (StringUtils.isBlank(requestLanguage)) {
            return xsltLangDefaultOpt.get();
        }
        return requestLanguage;
    }
}
