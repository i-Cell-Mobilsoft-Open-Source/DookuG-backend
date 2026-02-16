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
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.MultivaluedMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import hu.icellmobilsoft.coffee.dto.exception.InvalidParameterException;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.se.api.exception.BusinessException;
import hu.icellmobilsoft.coffee.se.api.exception.TechnicalException;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
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

/**
 * Test endpoint action for inline multipart generation.
 *
 * @author mate.biro
 * @since 2.2.0
 */
@Model
public class DocumentGenerateInlineTestMultipartAction extends BaseDocumentGenerateAction {

    private static final Pattern FILENAME_PATTERN = Pattern.compile("filename=\"?([^\";]+)\"?");

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
            throw new InvalidParameterException("input is null!");
        }
        Map<String, List<InputPart>> formDataMap = input.getFormDataMap();

        InputPart templatePart = getSingleRequiredFilePart(formDataMap.get("TEMPLATE"), "TEMPLATE");
        String templateFileName = getFileName(templatePart).orElse("template");
        String templateExt = getExtension(templateFileName)
                .orElseThrow(() -> new InvalidParameterException("TEMPLATE: missing file extension!"));

        if (!List.of("txt", "html", "xslt").contains(templateExt)) {
            throw new InvalidParameterException("TEMPLATE: only .html, .xslt or .txt template can be specified.");
        }

        InputPart templateEngineParamsPart = getSingleOptionalFilePart(formDataMap.get("PARAMETERS_TEMPLATE_ENGINE"), "PARAMETERS_TEMPLATE_ENGINE");
        if (templateEngineParamsPart != null) {
            String paramsFileName = getFileName(templateEngineParamsPart).orElse("parameters.json");
            String paramsExt = getExtension(paramsFileName).orElse(StringUtils.EMPTY);
            if (!"json".equals(paramsExt)) {
                throw new InvalidParameterException("PARAMETERS_TEMPLATE_ENGINE: only .json template parameter file can be specified.");
            }
        }

        InputPart generatorEngineParamsPart = getSingleOptionalFilePart(formDataMap.get("PARAMETERS_GENERATOR_ENGINE"), "PARAMETERS_GENERATOR_ENGINE");
        if (generatorEngineParamsPart != null) {
            String paramsFileName = getFileName(generatorEngineParamsPart).orElse("parameters.xml");
            String paramsExt = getExtension(paramsFileName).orElse(StringUtils.EMPTY);
            if (!"xml".equals(paramsExt)) {
                throw new InvalidParameterException("PARAMETERS_GENERATOR_ENGINE: only .xml generator parameter file can be specified.");
            }
        }

        GeneratorEngineType generatorEngine = switch (templateExt) {
        case "txt" -> GeneratorEngineType.NONE;
        case "html" -> GeneratorEngineType.PDF_BOX;
        case "xslt" -> GeneratorEngineType.SAXON;
        default -> throw new InvalidParameterException("Unsupported TEMPLATE extension: [" + templateExt + "]");
        };

        ResponseFormatType responseFormat = switch (templateExt) {
        case "txt" -> ResponseFormatType.STRING;
        case "html", "xslt" -> ResponseFormatType.PDF;
        default -> throw new InvalidParameterException("Unsupported TEMPLATE extension: [" + templateExt + "]");
        };

        TemplateEngineType templateEngine = templateEngineParamsPart != null ? TemplateEngineType.HANDLEBARS : TemplateEngineType.NONE;

        List<InputPart> subTemplates = formDataMap.get("SUBTEMPLATE");
        if (CollectionUtils.isNotEmpty(subTemplates)) {
            for (InputPart part : subTemplates) {
                String subFileName = getFileName(part).orElse("subtemplate");
                String subExt = getExtension(subFileName).orElse(StringUtils.EMPTY);
                if (!templateExt.equals(subExt)) {
                    throw new InvalidParameterException("SUBTEMPLATE: the file extensions of the template parts must match the extension of the main template.");
                }
            }
        }

        InlineGeneratorSetupType generatorSetup = new InlineGeneratorSetupType();
        generatorSetup.setGeneratorEngine(generatorEngine);
        generatorSetup.setTemplateEngine(templateEngine);
        generatorSetup.setResponseFormat(responseFormat);
        generatorSetup.setDocumentStorageMethod(DocumentStorageMethodType.NONE);

        if ("xslt".equals(templateExt)) {
            String templateLanguage = StringUtils.trimToNull(readOptionalTextPart(formDataMap.get("TEMPLATE_LANGUAGE"), "TEMPLATE_LANGUAGE"));
            if (templateLanguage == null) {
                throw new InvalidParameterException("TEMPLATE_LANGUAGE: missing template language.");
            }
            if (templateLanguage.length() > 30) {
                throw new InvalidParameterException("TEMPLATE_LANGUAGE: max 30 characters.");
            }
            generatorSetup.setTemplateLanguage(templateLanguage);
        }

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

        requestContainer.setGeneratorSetup(generatorSetup);

        String templateName = toTemplateName(templateFileName);
        templateData.setTemplateName(templateName);

        templateContainer.addTemplate(new Template(templateName, readPartBytes(templatePart)), true);
        addSubTemplates(subTemplates, templateExt, templateName);

        try {
            Document document = generateDocument(generatorSetup);
            return ResponseUtil.getFileResponse(document, responseContentGzipped);
        } catch (BaseException e) {
            throw wrapToReadableFault(e, generatorSetup);
        }
    }

    private void addSubTemplates(List<InputPart> subTemplates, String templateExt, String mainTemplateName) throws BaseException {
        if (CollectionUtils.isEmpty(subTemplates)) {
            return;
        }
        if (templateExt.equals("xslt") || templateExt.equals("html")) {
            // allow but template engine must handle multi templates, otherwise generation will fail with a readable error
        }
        for (int i = 0; i < subTemplates.size(); i++) {
            InputPart part = subTemplates.get(i);
            String subFileName = getFileName(part).orElse(MessageFormat.format("subtemplate_{0}.{1}", i + 1, templateExt));
            String name = toTemplateName(subFileName);
            if (StringUtils.equals(name, mainTemplateName)) {
                name = name + "_" + (i + 1);
            }
            templateContainer.addTemplate(new Template(name, readPartBytes(part)), false);
        }
    }

    private BusinessException wrapToReadableFault(BaseException e, InlineGeneratorSetupType setup) {
        String message = e.getMessage();
        if (setup != null && setup.getTemplateEngine() == TemplateEngineType.HANDLEBARS && StringUtils.containsIgnoreCase(message, "handlebars")) {
            return new BusinessException(FaultType.TEMPLATE_ENGINE_ERROR, message, e);
        }
        if (setup != null && setup.getGeneratorEngine() != GeneratorEngineType.NONE
                && (StringUtils.containsIgnoreCase(message, "pdf") || StringUtils.containsIgnoreCase(message, "xslt")
                        || StringUtils.containsIgnoreCase(message, "pdfbox"))) {
            return new BusinessException(FaultType.GENERATOR_ENGINE_ERROR, message, e);
        }
        return new BusinessException(FaultType.DOCUMENT_GENERATION_ERROR, message, e);
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
            return parts.get(0).getBody(String.class, null);
        } catch (Exception e) {
            throw new TechnicalException(CoffeeFaultType.OPERATION_FAILED, "Error while reading multipart text input!", e);
        }
    }

    private InputPart getSingleRequiredFilePart(List<InputPart> parts, String fieldName) throws BaseException {
        InputPart part = getSingleOptionalFilePart(parts, fieldName);
        if (part == null) {
            throw new InvalidParameterException(fieldName + ": missing file part!");
        }
        return part;
    }

    private InputPart getSingleOptionalFilePart(List<InputPart> parts, String fieldName) throws BaseException {
        if (CollectionUtils.isEmpty(parts)) {
            return null;
        }
        if (parts.size() > 1) {
            throw new InvalidParameterException(fieldName + ": only one file can be specified.");
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
