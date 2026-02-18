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

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.github.jknack.handlebars.HandlebarsException;
import com.openhtmltopdf.util.XRRuntimeException;

import hu.icellmobilsoft.coffee.dto.exception.InvalidParameterException;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.se.api.exception.TechnicalException;
import hu.icellmobilsoft.dookug.api.dto.exception.enums.FaultType;
import hu.icellmobilsoft.dookug.common.cdi.document.Document;
import hu.icellmobilsoft.dookug.common.cdi.template.Template;
import hu.icellmobilsoft.dookug.common.cdi.template.TemplateContainer;
import hu.icellmobilsoft.dookug.common.cdi.template.TemplateDataContainer;
import hu.icellmobilsoft.dookug.common.rest.cdi.RequestContainer;
import hu.icellmobilsoft.dookug.common.system.rest.util.ResponseUtil;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentStorageMethodType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.GeneratorEngineType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.InlineGeneratorSetupType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.ParametersDataType;
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

    private static final Pattern FILENAME_PATTERN = Pattern.compile("filename=\"?([^\";]+)\"?");

    private final String EXTENSION_JSON = "json";
    private final String EXTENSION_XML = "xml";
    private final String EXTENSION_XSLT = "xslt";
    private final String EXTENSION_HTML = "html";
    private final String EXTENSION_TXT = "txt";
    private final List<String> ACCEPTED_TEMPLATE_EXTENSIONS = List.of(EXTENSION_TXT, EXTENSION_HTML, EXTENSION_XSLT);

    private static final String FORM_DATA_NAME_TEMPLATE = "TEMPLATE";
    private static final String FORM_DATA_NAME_PARAMETERS_TEMPLATE_ENGINE = "PARAMETERS_TEMPLATE_ENGINE";
    private static final String FORM_DATA_NAME_PARAMETERS_GENERATOR_ENGINE = "PARAMETERS_GENERATOR_ENGINE";
    private static final String FORM_DATA_NAME_SUBTEMPLATE = "SUBTEMPLATE";
    private static final String FORM_DATA_NAME_TEMPLATE_LANGUAGE = "TEMPLATE_LANGUAGE";

    @Inject
    private TemplateContainer templateContainer;

    @Inject
    private TemplateDataContainer templateData;

    @Inject
    private RequestContainer requestContainer;

    /**
     * Multipart based document generation for test purposes.
     *
     * @param input
     *            multipart input
     * @param responseContentGzipped
     *            if true, the response content will be GZIP compressed
     * @return generated document
     * @throws BaseException
     *             on error
     */
    public Response postDocumentGenerateMultipart(MultipartFormDataInput input, Boolean responseContentGzipped) throws BaseException {
        if (input == null) {
            throw new InvalidParameterException("MultipartFormDataInput is null!");
        }

        Map<String, List<InputPart>> formDataMap = input.getFormDataMap();

        // template
        TemplateRecord templateRecord = handleTemplate(formDataMap);

        // template engine parameters
        InputPart templateEngineParamsPart = handleEngineParameters(
                formDataMap,
                FORM_DATA_NAME_PARAMETERS_TEMPLATE_ENGINE,
                EXTENSION_JSON);

        // generator engine parameters
        InputPart generatorEngineParamsPart = handleEngineParameters(
                formDataMap,
                FORM_DATA_NAME_PARAMETERS_GENERATOR_ENGINE,
                EXTENSION_XML);

        // template engine type
        TemplateEngineType templateEngine = templateEngineParamsPart != null ? TemplateEngineType.HANDLEBARS : TemplateEngineType.NONE;

        // generator engine type
        GeneratorEngineType generatorEngine = getGeneratorEngineType(templateRecord);

        // response format type
        ResponseFormatType responseFormat = getResponseFormatType(templateRecord);

        // subtemplates
        handleSubTemplates(formDataMap, templateRecord);

        // Generator setup
        InlineGeneratorSetupType generatorSetup = getGeneratorSetup(
                generatorEngine,
                templateEngine,
                responseFormat,
                templateRecord,
                formDataMap,
                templateEngineParamsPart,
                generatorEngineParamsPart);

        requestContainer.setGeneratorSetup(generatorSetup);

        String templateName = toTemplateName(templateRecord.templateFileName());
        templateData.setTemplateName(templateName);

        templateContainer.addTemplate(new Template(templateName, readPartBytes(templateRecord.templatePart())), true);

        try {
            Document document = generateDocument(generatorSetup);
            return ResponseUtil.getFileResponse(document, responseContentGzipped);
        } catch (Exception e) {
            throw wrapToReadableFault(e);
        }
    }

    private InlineGeneratorSetupType getGeneratorSetup(GeneratorEngineType generatorEngine, TemplateEngineType templateEngine,
            ResponseFormatType responseFormat, TemplateRecord templateRecord, Map<String, List<InputPart>> formDataMap,
            InputPart templateEngineParamsPart, InputPart generatorEngineParamsPart) throws BaseException {

        InlineGeneratorSetupType generatorSetup = new InlineGeneratorSetupType();
        generatorSetup.setGeneratorEngine(generatorEngine);
        generatorSetup.setTemplateEngine(templateEngine);
        generatorSetup.setResponseFormat(responseFormat);
        generatorSetup.setDocumentStorageMethod(DocumentStorageMethodType.NONE);

        // validation and set template language for xslt
        if (EXTENSION_XSLT.equals(templateRecord.templateExt())) {
            String templateLanguage = StringUtils
                    .trimToNull(readOptionalTextPart(formDataMap.get(FORM_DATA_NAME_TEMPLATE_LANGUAGE), FORM_DATA_NAME_TEMPLATE_LANGUAGE));

            if (templateLanguage == null) {
                throw new InvalidParameterException("TEMPLATE_LANGUAGE: missing templateRecord language.");
            }
            if (templateLanguage.length() > 30) {
                throw new InvalidParameterException("TEMPLATE_LANGUAGE: max 30 characters.");
            }
            generatorSetup.setTemplateLanguage(templateLanguage);
        }

        // set parameters data if any
        ParametersDataType parametersData = new ParametersDataType();
        if (templateEngineParamsPart != null) {
            parametersData.setTemplateParameters(readPartBytes(templateEngineParamsPart));
        }
        if (generatorEngineParamsPart != null) {
            parametersData.setGeneratorParameters(readPartBytes(generatorEngineParamsPart));
        }
        if (parametersData.getTemplateParameters() != null || parametersData.getGeneratorParameters() != null) {
            generatorSetup.setParametersData(parametersData);
        }
        return generatorSetup;
    }

    private void handleSubTemplates(Map<String, List<InputPart>> formDataMap, TemplateRecord templateRecord)
            throws BaseException {

        List<InputPart> subTemplates = formDataMap.get(FORM_DATA_NAME_SUBTEMPLATE);

        if (CollectionUtils.isEmpty(subTemplates)) {
            return;
        }

        for (InputPart part : subTemplates) {

            String subFileName = getFileName(part).orElseThrow(
                    () -> new InvalidParameterException(
                            MessageFormat.format(
                                    "Missing filename from Content-Disposition header for form-data: [{0}]!",
                                    FORM_DATA_NAME_SUBTEMPLATE)));

            String subExt = getExtension(subFileName).orElseThrow(
                    () -> new InvalidParameterException(
                            MessageFormat.format("Missing file extension for form-data: [{0}]!", FORM_DATA_NAME_SUBTEMPLATE)));

            if (!templateRecord.templateExt().equals(subExt)) {
                throw new InvalidParameterException(
                        MessageFormat.format(
                                "The file extensions of the [{0}] parts must match the extension of the main [{1}].",
                                FORM_DATA_NAME_SUBTEMPLATE,
                                FORM_DATA_NAME_TEMPLATE));
            }

            String name = toTemplateName(subFileName);
            templateContainer.addTemplate(new Template(name, readPartBytes(part)), false);
        }
    }

    private ResponseFormatType getResponseFormatType(TemplateRecord templateRecord) throws InvalidParameterException {
        return switch (templateRecord.templateExt()) {
            case EXTENSION_TXT -> ResponseFormatType.STRING;
            case EXTENSION_HTML, EXTENSION_XSLT -> ResponseFormatType.PDF;
            default -> throw new InvalidParameterException(
                    MessageFormat.format("Unsupported extension: [{0}] for: [{1}]", templateRecord.templateExt(), FORM_DATA_NAME_TEMPLATE));
        };
    }

    private GeneratorEngineType getGeneratorEngineType(TemplateRecord templateRecord) throws InvalidParameterException {
        return switch (templateRecord.templateExt()) {
            case EXTENSION_TXT -> GeneratorEngineType.NONE;
            case EXTENSION_HTML -> GeneratorEngineType.PDF_BOX;
            case EXTENSION_XSLT -> GeneratorEngineType.SAXON;
            default -> throw new InvalidParameterException(
                    MessageFormat.format("Unsupported extension: [{0}] for: [{1}]", templateRecord.templateExt(), FORM_DATA_NAME_TEMPLATE));
        };
    }

    private InputPart handleEngineParameters(Map<String, List<InputPart>> formDataMap, String formDataNameParameters,
            String requiredFileExtension) throws BaseException {

        InputPart engineParamsPart = getSingleOptionalFilePart(
                formDataMap.get(formDataNameParameters),
                formDataNameParameters);

        if (engineParamsPart == null) {
            return null;
        }

        String paramsFileName = getFileName(engineParamsPart).orElseThrow(
                () -> new InvalidParameterException(
                        MessageFormat.format(
                                "Missing filename from Content-Disposition header for form-data: [{0}]!",
                                formDataNameParameters)));

        String paramsExt = getExtension(paramsFileName).orElseThrow(
                () -> new InvalidParameterException(
                        MessageFormat.format("Missing file extension for form-data: [{0}]!", formDataNameParameters)));

        if (!requiredFileExtension.equals(paramsExt)) {
            throw new InvalidParameterException(
                    MessageFormat.format(
                            "Only [{0}] parameter file can be specified for from-data: [{1}]!",
                            requiredFileExtension,
                            formDataNameParameters));
        }

        return engineParamsPart;
    }

    private TemplateRecord handleTemplate(Map<String, List<InputPart>> formDataMap) throws BaseException {
        List<InputPart> templateInputParts = formDataMap.get(FORM_DATA_NAME_TEMPLATE);
        InputPart templatePart = getSingleRequiredFilePart(templateInputParts, FORM_DATA_NAME_TEMPLATE);
        String templateFileName = getFileName(templatePart).orElseThrow(
                () -> new InvalidParameterException(
                        MessageFormat.format("Missing filename from Content-Disposition header for form-data: [{0}]!", FORM_DATA_NAME_TEMPLATE)));

        String templateExt = getExtension(templateFileName)
                .orElseThrow(
                        () -> new InvalidParameterException(
                                MessageFormat.format("Missing file extension for form-data: [{0}]!", FORM_DATA_NAME_TEMPLATE)));

        if (!ACCEPTED_TEMPLATE_EXTENSIONS.contains(templateExt)) {
            throw new InvalidParameterException(
                    MessageFormat.format(
                            "Only [{0}] extensions are accepted for form-data: [{1}]",
                            ACCEPTED_TEMPLATE_EXTENSIONS,
                            FORM_DATA_NAME_TEMPLATE));
        }
        return new TemplateRecord(templatePart, templateFileName, templateExt);
    }

    private record TemplateRecord(InputPart templatePart, String templateFileName, String templateExt) {
    }

    private TechnicalException wrapToReadableFault(Throwable e) {
        String message = e.getMessage();
        if (e instanceof HandlebarsException) {
            return new TechnicalException(FaultType.TEMPLATE_ENGINE_ERROR, message, e);
        }
        if (e.getCause() instanceof XRRuntimeException || e.getCause() instanceof SaxonApiException) {
            return new TechnicalException(FaultType.GENERATOR_ENGINE_ERROR, message, e);
        }
        return new TechnicalException(FaultType.DOCUMENT_GENERATION_ERROR, message, e);
    }

    private byte[] readPartBytes(InputPart part) throws BaseException {
        try {
            InputStream is = part.getBody(InputStream.class, null);
            return is == null ? new byte[0] : is.readAllBytes();
        } catch (IOException e) {
            throw new TechnicalException(CoffeeFaultType.OPERATION_FAILED, "Error while reading multipart input!", e);
        }
    }

    private String readOptionalTextPart(List<InputPart> parts, String fieldName) throws BaseException {
        if (CollectionUtils.isEmpty(parts)) {
            return null;
        }
        if (parts.size() > 1) {
            throw new InvalidParameterException(fieldName + ": only one value can be specified.");
        }
        try {
            return parts.get(0).getBodyAsString();
        } catch (Exception e) {
            throw new TechnicalException(CoffeeFaultType.OPERATION_FAILED, "Error while reading multipart text input!", e);
        }
    }

    private InputPart getSingleRequiredFilePart(List<InputPart> parts, String fieldName) throws BaseException {
        InputPart part = getSingleOptionalFilePart(parts, fieldName);
        if (part == null) {
            throw new InvalidParameterException(MessageFormat.format("Missing file part: [{0}]!", fieldName));
        }
        return part;
    }

    private InputPart getSingleOptionalFilePart(List<InputPart> parts, String fieldName) throws BaseException {
        if (CollectionUtils.isEmpty(parts)) {
            return null;
        }
        if (parts.size() > 1) {
            throw new InvalidParameterException(MessageFormat.format("Only one file can be specified for part: [{0}]!", fieldName));
        }
        return parts.get(0);
    }

    private Optional<String> getFileName(InputPart part) {
        if (part == null) {
            return Optional.empty();
        }
        MultivaluedMap<String, String> headers = part.getHeaders();
        if (headers == null) {
            return Optional.empty();
        }
        String contentDisposition = headers.getFirst("Content-Disposition");
        if (StringUtils.isBlank(contentDisposition)) {
            return Optional.empty();
        }
        Matcher matcher = FILENAME_PATTERN.matcher(contentDisposition);
        if (matcher.find()) {
            return Optional.ofNullable(matcher.group(1));
        }
        return Optional.empty();
    }

    private Optional<String> getExtension(String filename) {
        if (StringUtils.isBlank(filename)) {
            return Optional.empty();
        }
        int idx = filename.lastIndexOf('.');
        if (idx < 0 || idx == filename.length() - 1) {
            return Optional.empty();
        }
        return Optional.of(filename.substring(idx + 1).toLowerCase(Locale.ROOT));
    }

    private String toTemplateName(String filename) {
        if (StringUtils.isBlank(filename)) {
            return "template";
        }
        String name = filename;
        int slash = Math.max(name.lastIndexOf('/'), name.lastIndexOf('\\'));
        int dot = name.lastIndexOf('.');
        if (slash >= 0) {
            name = name.substring(slash + 1);
        }
        if (dot > 0) {
            name = name.substring(0, dot);
        }
        name = name.trim();
        return StringUtils.isBlank(name) ? "template" : name;
    }
}
