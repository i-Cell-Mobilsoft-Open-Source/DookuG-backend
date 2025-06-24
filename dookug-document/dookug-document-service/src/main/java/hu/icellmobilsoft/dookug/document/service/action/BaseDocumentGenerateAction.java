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
package hu.icellmobilsoft.dookug.document.service.action;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.enterprise.inject.Model;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.StreamingOutput;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import hu.icellmobilsoft.coffee.cdi.trace.annotation.Traced;
import hu.icellmobilsoft.coffee.cdi.trace.constants.SpanAttribute;
import hu.icellmobilsoft.coffee.dto.common.commonservice.ContextType;
import hu.icellmobilsoft.coffee.dto.exception.InvalidParameterException;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.se.api.exception.BusinessException;
import hu.icellmobilsoft.coffee.tool.utils.compress.GZIPUtil;
import hu.icellmobilsoft.coffee.tool.utils.date.DateUtil;
import hu.icellmobilsoft.coffee.tool.utils.json.JsonUtil;
import hu.icellmobilsoft.dookug.api.dto.constants.ConfigKeys;
import hu.icellmobilsoft.dookug.api.dto.exception.IOExceptionBaseExceptionWrapper;
import hu.icellmobilsoft.dookug.common.cdi.DocumentGeneratorQualifier;
import hu.icellmobilsoft.dookug.common.cdi.StorageMethodQualifier;
import hu.icellmobilsoft.dookug.common.cdi.TemplateCompilerQualifier;
import hu.icellmobilsoft.dookug.common.cdi.document.Document;
import hu.icellmobilsoft.dookug.common.cdi.document.IDocumentStore;
import hu.icellmobilsoft.dookug.common.cdi.template.IDocumentGenerator;
import hu.icellmobilsoft.dookug.common.cdi.template.ITemplateCompiler;
import hu.icellmobilsoft.dookug.common.cdi.template.Template;
import hu.icellmobilsoft.dookug.common.cdi.template.TemplateContainer;
import hu.icellmobilsoft.dookug.common.cdi.template.TemplateDataContainer;
import hu.icellmobilsoft.dookug.common.model.template.enums.DocumentStatus;
import hu.icellmobilsoft.dookug.common.rest.cdi.RequestContainer;
import hu.icellmobilsoft.dookug.common.system.rest.action.BaseAction;
import hu.icellmobilsoft.dookug.common.util.filename.FileUtil;
import hu.icellmobilsoft.dookug.document.service.converter.DocumentConverter;
import hu.icellmobilsoft.dookug.schemas.common._1_0.common.ParameterType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.BaseGeneratorSetupType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataResponse;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentStorageMethodType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.GeneratorEngineType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.ResponseFormatType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.TemplateEngineType;

/**
 * Base document generation action
 *
 * @author laszlo.padar
 * @since 0.1.0
 */
@Model
public class BaseDocumentGenerateAction extends BaseAction {

    @Inject
    private TemplateContainer templateContainer;

    @Inject
    private TemplateDataContainer templateDataContainer;

    @Inject
    private DocumentConverter documentConverter;

    @Inject
    private RequestContainer requestContainer;

    @Inject
    @ConfigProperty(name = ConfigKeys.Interface.DOOKUG_SERVICE_INTERFACE_PARAMETERSDATA_GZIPPED,
            defaultValue = ConfigKeys.Interface.DOOKUG_SERVICE_INTERFACE_PARAMETERSDATA_GZIPPED_DEFAULT)
    private boolean parametersDataGzipped;

    /**
     * Generate document
     * 
     * @param generatorSetup
     *            the generator configuration
     * @return the {@link Document}
     * @throws BaseException
     *             if any error occurs
     */
    @Traced(component = SpanAttribute.COMPONENT_KEY, kind = SpanAttribute.INTERNAL)
    protected Document generateDocument(BaseGeneratorSetupType generatorSetup) throws BaseException {
        validateSetup(generatorSetup);

        Template firstTemplate = templateContainer.getInitialTemplate()
                .orElseThrow(() -> new BusinessException(CoffeeFaultType.INVALID_INPUT, "At least one template is needed for generation!"));

        compileTemplate(generatorSetup, firstTemplate);

        if (generatorSetup.getGeneratorEngine() != GeneratorEngineType.NONE) {

            StreamingOutput streamingOutput = createDocumentGeneratorStreamingOutput(generatorSetup);

            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                streamingOutput.write(bos);
                if (generatorSetup.getDocumentStorageMethod() != DocumentStorageMethodType.NONE) {
                    return saveDocument(generatorSetup, bos.toByteArray());
                }
                return createDocument(generatorSetup, bos.toByteArray());
            } catch (IOException e) {
                throw (BaseException) e.getCause();
            }
        } else {
            if (StringUtils.isNotBlank(templateContainer.getCompiledResult())
                    && generatorSetup.getDocumentStorageMethod() != DocumentStorageMethodType.NONE) {
                return saveDocument(generatorSetup, templateContainer.getCompiledResultAsBytes());
            } else if (StringUtils.isNotBlank(templateContainer.getCompiledResult())) {
                return createDocument(generatorSetup, templateContainer.getCompiledResultAsBytes());
            } else if (generatorSetup.getDocumentStorageMethod() != DocumentStorageMethodType.NONE) {
                return saveDocument(generatorSetup, firstTemplate.getTemplateContent());
            }
            return createDocument(generatorSetup, firstTemplate.getTemplateContent());
        }
    }

    private void validateSetup(BaseGeneratorSetupType generatorSetup) throws BaseException {
        if (generatorSetup == null) {
            throw new InvalidParameterException("Generator setup is null!");
        }
        if (generatorSetup.getResponseFormat() != ResponseFormatType.STRING && generatorSetup.getGeneratorEngine() == GeneratorEngineType.NONE) {
            throw new InvalidParameterException(
                    MessageFormat.format("ResponseFormat [{0}] response can only be done using a generator!", generatorSetup.getResponseFormat()));
        }
        if (generatorSetup.getResponseFormat() != ResponseFormatType.PDF && generatorSetup.getGeneratorEngine() == GeneratorEngineType.SAXON) {
            throw new InvalidParameterException(
                    MessageFormat.format(
                            "ResponseFormat [{0}] and generatorEngine [{1}] combination is not defined!",
                            generatorSetup.getResponseFormat(),
                            generatorSetup.getGeneratorEngine()));
        }
        if (generatorSetup.getResponseFormat() == ResponseFormatType.STRING && generatorSetup.getGeneratorEngine() == GeneratorEngineType.PDF_BOX) {
            throw new InvalidParameterException(
                    MessageFormat.format(
                            "ResponseFormat [{0}] and generatorEngine [{1}] combination is not defined!",
                            generatorSetup.getResponseFormat(),
                            generatorSetup.getGeneratorEngine()));
        }
    }

    private Map<String, String> getMapFromParameterTypeList(List<ParameterType> parameterList) {
        if (CollectionUtils.isEmpty(parameterList)) {
            return Collections.emptyMap();
        }
        return parameterList.stream().collect(Collectors.toMap(ParameterType::getKey, ParameterType::getValue));
    }

    private byte[] getParametersAsByteArray(List<ParameterType> parameters) throws BaseException {
        if (CollectionUtils.isEmpty(parameters)) {
            return null;
        }
        return GZIPUtil.compress(JsonUtil.toJson(parameters).getBytes(StandardCharsets.UTF_8));
    }

    private void compileTemplate(BaseGeneratorSetupType generatorSetup, Template firstTemplate) throws BaseException {

        if (generatorSetup.getTemplateEngine() != TemplateEngineType.NONE) {
            ITemplateCompiler templateCompiler = CDI.current()
                    .select(ITemplateCompiler.class, new TemplateCompilerQualifier.Literal(generatorSetup.getTemplateEngine().name()))
                    .get();

            if (generatorSetup.isSetParameters()) {
                templateCompiler.compile(getMapFromParameterTypeList(generatorSetup.getParameters()));
            } else if (generatorSetup.isSetParametersData()) {
                byte[] parametersData = generatorSetup.getParametersData().getTemplateParameters();
                if (parametersDataGzipped) {
                    parametersData = GZIPUtil.decompress(parametersData);
                }
                templateCompiler.compile(parametersData);
            } else {
                templateCompiler.compile((byte[]) null);
            }
        } else {
            if (templateContainer.length() > 1) {
                // In the case of multiple templates, a template engine is absolutely necessary due to the references to each other
                throw new BusinessException(CoffeeFaultType.INVALID_INPUT, "Cannot compile more than one template without template engine!");
            } else {
                // If there is only one template and no parameters are needed, a template engine is not required
                templateContainer.setCompiledResult(new String(firstTemplate.getTemplateContent(), StandardCharsets.UTF_8));
            }
        }
    }

    private Document createDocument(BaseGeneratorSetupType generatorSetup, byte[] content) throws BaseException {
        Document document = new Document(generatorSetup.getResponseFormat().name(), content, DocumentStatus.DONE.name());
        document.setStorageType(generatorSetup.getDocumentStorageMethod().name());
        document.setFilename(
                FileUtil.createFilename(
                        document.getDocumentId(),
                        templateDataContainer.getTemplateName(),
                        generatorSetup.getResponseFormat(),
                        DateUtil.nowUTC()));
        return document;
    }

    @Traced(component = SpanAttribute.COMPONENT_KEY, kind = SpanAttribute.INTERNAL)
    private Document saveDocument(BaseGeneratorSetupType generatorSetup, byte[] content) throws BaseException {
        IDocumentStore iDocumentStore = CDI.current()
                .select(IDocumentStore.class, new StorageMethodQualifier.Literal(generatorSetup.getDocumentStorageMethod().name()))
                .get();

        if (generatorSetup.isSetParameters()) {
            return iDocumentStore.saveDocumentWithParameters(
                    content,
                    generatorSetup.getResponseFormat().name(),
                    getParametersAsByteArray(generatorSetup.getParameters()));
        } else if (generatorSetup.isSetParametersData()) {
            return iDocumentStore
                    .saveDocumentWithParameterData(content, generatorSetup.getResponseFormat().name(), generatorSetup.getParametersData());
        } else {
            return iDocumentStore.saveDocumentWithParameterData(content, generatorSetup.getResponseFormat().name(), (byte[]) null);
        }
    }

    private StreamingOutput createDocumentGeneratorStreamingOutput(BaseGeneratorSetupType generatorSetup) {

        IDocumentGenerator documentGenerator = CDI.current()
                .select(IDocumentGenerator.class, new DocumentGeneratorQualifier.Literal(generatorSetup.getGeneratorEngine().name()))
                .get();
        return new StreamingOutput() {

            public void write(OutputStream output) throws IOException, WebApplicationException {
                try {
                    if (generatorSetup.isSetParameters()) {
                        documentGenerator.generateToOutputStream(
                                output,
                                getMapFromParameterTypeList(generatorSetup.getParameters()),
                                generatorSetup.getDigitalSignatureProfile());
                    } else {
                        documentGenerator
                                .generateToOutputStream(output, generatorSetup.getParametersData(), generatorSetup.getDigitalSignatureProfile());
                    }
                } catch (BaseException e) {
                    throw new IOExceptionBaseExceptionWrapper(e);
                }
            }
        };
    }

    /**
     * {@link Document} to {@link DocumentMetadataResponse} converter
     * 
     * @param document
     *            document to convert
     * @param context
     *            the original request context
     * @return the metadata
     * @throws BaseException
     *             if any error occurs
     */
    protected DocumentMetadataResponse toDocumentMetadataResponse(Document document, ContextType context) throws BaseException {
        DocumentMetadataResponse response = documentConverter.convert(document);
        handleSuccessResultType(response);
        if (context != null && context.isSetRequestId()) {
            response.getContext().setRequestId(context.getRequestId());
        }
        return response;
    }
}
