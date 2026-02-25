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
package hu.icellmobilsoft.dookug.document.service.action.test;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import hu.icellmobilsoft.coffee.se.api.exception.BusinessException;
import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.github.jknack.handlebars.HandlebarsException;
import com.openhtmltopdf.util.XRRuntimeException;

import hu.icellmobilsoft.coffee.dto.exception.InvalidParameterException;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.se.api.exception.TechnicalException;
import hu.icellmobilsoft.dookug.api.dto.exception.enums.FaultType;
import hu.icellmobilsoft.dookug.common.cdi.document.Document;
import hu.icellmobilsoft.dookug.common.cdi.template.Template;
import hu.icellmobilsoft.dookug.common.cdi.template.TemplateContainer;
import hu.icellmobilsoft.dookug.common.cdi.template.TemplateDataContainer;
import hu.icellmobilsoft.dookug.common.rest.cdi.RequestContainer;
import hu.icellmobilsoft.dookug.common.system.rest.util.ResponseUtil;
import hu.icellmobilsoft.dookug.document.service.action.BaseDocumentGenerateAction;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.GeneratorEngineType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.InlineGeneratorSetupType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.ResponseFormatType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.TemplateEngineType;
import net.sf.saxon.s9api.SaxonApiException;

/**
 * Test endpoint action for inline multipart generation.
 *
 * @author mate.biro
 * @since 2.2.0
 */
@Model
public class DocumentGenerateInlineTestMultipartAction extends BaseDocumentGenerateAction {

    @Inject
    private TemplateContainer templateContainer;

    @Inject
    private TemplateDataContainer templateData;

    @Inject
    private RequestContainer requestContainer;

    @Inject
    private FileNameHelper fileNameHelper;

    @Inject
    private InputPartHelper inputPartHelper;

    @Inject
    private GeneratorSetupHelper generatorSetupHelper;

    @Inject
    private SubTemplateHelper subTemplateHelper;

    @Inject
    private TemplateHelper templateHelper;

    @Inject
    private EngineParametersHelper engineParametersHelper;

    /**
     * Multipart based document generation for test purposes.
     *
     * @param input
     *            multipart input
     * @return generated document
     * @throws BaseException
     *             on error
     */
    public Response postDocumentGenerateMultipart(MultipartFormDataInput input) throws BaseException {
        if (input == null) {
            throw new InvalidParameterException("MultipartFormDataInput is null!");
        }

        Map<String, List<InputPart>> formDataMap = input.getFormDataMap();

        // template
        TemplateRecord templateRecord = templateHelper.handleTemplate(formDataMap);

        // template engine parameters
        InputPart templateEngineParamsPart = engineParametersHelper.handleEngineParameters(
                formDataMap,
                GeneratorConstants.FORM_DATA_NAME_PARAMETERS_TEMPLATE_ENGINE,
                GeneratorConstants.EXTENSION_JSON,
                FaultType.INVALID_TEMPLATE_ENGINE_PARAMETERS_EXTENSION);

        // generator engine parameters
        InputPart generatorEngineParamsPart = engineParametersHelper.handleEngineParameters(
                formDataMap,
                GeneratorConstants.FORM_DATA_NAME_PARAMETERS_GENERATOR_ENGINE,
                GeneratorConstants.EXTENSION_XML,
                FaultType.INVALID_GENERATOR_ENGINE_PARAMETERS_EXTENSION);

        // template engine type
        TemplateEngineType templateEngine = templateEngineParamsPart != null ? TemplateEngineType.HANDLEBARS : TemplateEngineType.NONE;

        // generator engine type
        GeneratorEngineType generatorEngine = getGeneratorEngineType(templateRecord);

        // response format type
        ResponseFormatType responseFormat = getResponseFormatType(templateRecord);

        // subtemplates
        subTemplateHelper.handleSubTemplates(formDataMap, templateRecord);

        // Generator setup
        InlineGeneratorSetupType generatorSetup = generatorSetupHelper.getGeneratorSetup(
                generatorEngine,
                templateEngine,
                responseFormat,
                templateRecord,
                formDataMap,
                templateEngineParamsPart,
                generatorEngineParamsPart);

        requestContainer.setGeneratorSetup(generatorSetup);

        String templateName = fileNameHelper.toTemplateName(templateRecord.templateFileName());
        templateData.setTemplateName(templateName);

        templateContainer.addTemplate(new Template(templateName, inputPartHelper.readPartBytes(templateRecord.templatePart())), true);

        try {
            Document document = generateDocument(generatorSetup);
            return ResponseUtil.getFileResponse(document, getIsResponseContentGzipped(formDataMap));
        } catch (Exception e) {
            throw wrapToReadableFault(e);
        }
    }

    private boolean getIsResponseContentGzipped(Map<String, List<InputPart>> formDataMap) throws BaseException {
        String responseContentGzipped = inputPartHelper
                .readOptionalTextPart(
                        formDataMap.get(GeneratorConstants.FORM_DATA_NAME_RESPONSE_CONTENT_GZIPPED),
                        GeneratorConstants.FORM_DATA_NAME_RESPONSE_CONTENT_GZIPPED);
        return Boolean.parseBoolean(responseContentGzipped);
    }

    private ResponseFormatType getResponseFormatType(TemplateRecord templateRecord) throws InvalidParameterException {
        return switch (templateRecord.templateExt()) {
            case GeneratorConstants.EXTENSION_TXT -> ResponseFormatType.STRING;
            case GeneratorConstants.EXTENSION_HTML, GeneratorConstants.EXTENSION_XSLT -> ResponseFormatType.PDF;
            default -> throw new InvalidParameterException(
                    MessageFormat.format(
                            "Unsupported extension: [{0}] for: [{1}]",
                            templateRecord.templateExt(),
                            GeneratorConstants.FORM_DATA_NAME_TEMPLATE));
        };
    }

    private GeneratorEngineType getGeneratorEngineType(TemplateRecord templateRecord) throws InvalidParameterException {
        return switch (templateRecord.templateExt()) {
            case GeneratorConstants.EXTENSION_TXT -> GeneratorEngineType.NONE;
            case GeneratorConstants.EXTENSION_HTML -> GeneratorEngineType.PDF_BOX;
            case GeneratorConstants.EXTENSION_XSLT -> GeneratorEngineType.SAXON;
            default -> throw new InvalidParameterException(
                    MessageFormat.format(
                            "Unsupported extension: [{0}] for: [{1}]",
                            templateRecord.templateExt(),
                            GeneratorConstants.FORM_DATA_NAME_TEMPLATE));
        };
    }

    private BusinessException wrapToReadableFault(Exception e) {
        String message = e.getMessage();
        if (e instanceof HandlebarsException) {
            return new BusinessException(FaultType.TEMPLATE_ENGINE_ERROR, message, e);
        }
        if (e.getCause() instanceof XRRuntimeException || e.getCause() instanceof SaxonApiException) {
            return new BusinessException(FaultType.GENERATOR_ENGINE_ERROR, message, e);
        }
        return new BusinessException(FaultType.DOCUMENT_GENERATION_ERROR, message, e);
    }
}
