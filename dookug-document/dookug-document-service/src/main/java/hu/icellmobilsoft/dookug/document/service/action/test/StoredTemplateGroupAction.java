/*-
 * #%L
 * DookuG
 * %%
 * Copyright (C) 2023 - 2026 i-Cell Mobilsoft Zrt.
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
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import hu.icellmobilsoft.coffee.dto.exception.InvalidParameterException;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.se.api.exception.BusinessException;
import hu.icellmobilsoft.dookug.common.model.template.Template;
import hu.icellmobilsoft.dookug.common.model.template.TemplatePart;
import hu.icellmobilsoft.dookug.common.model.template.TemplatePartContent;
import hu.icellmobilsoft.dookug.common.model.template.TemplateTemplatePart;
import hu.icellmobilsoft.dookug.common.system.rest.action.BaseAction;
import hu.icellmobilsoft.dookug.document.service.service.TemplatePartContentService;
import hu.icellmobilsoft.dookug.document.service.service.TemplatePartService;
import hu.icellmobilsoft.dookug.document.service.service.TemplateService;
import hu.icellmobilsoft.dookug.document.service.service.TemplateTemplatePartService;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.CreateTemplateGroupRequest;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.CreateTemplateGroupResponse;

/**
 * Stored template group action
 *
 * @author levente.prehoda
 * @since 2.2.0
 */
@Model
public class StoredTemplateGroupAction extends BaseAction {

    private static final String FORM_DATA_NAME_TEMPLATE = "TEMPLATE";
    private static final String FORM_DATA_NAME_TEMPLATE_FILE_ID = "TEMPLATE_FILE_ID";
    private static final String FORM_DATA_NAME_TEMPLATE_DATA = "TEMPLATE_DATA";

    @Inject
    private InputPartHelper inputPartHelper;

    @Inject
    private TemplateService templateService;

    @Inject
    private TemplatePartService templatePartService;

    @Inject
    private TemplatePartContentService templatePartContentService;

    @Inject
    private TemplateTemplatePartService templateTemplatePartService;

    @Inject
    private TemplateGroupMapper templateGroupMapper;

    @Inject
    private TemplateHelper templateHelper;

    /**
     * Creates templates group
     * 
     * @param input
     *            the multipart input
     * @return response dto
     * @throws BaseException
     *             in case of error
     */
    public CreateTemplateGroupResponse createStoredTemplateGroup(MultipartFormDataInput input) throws BaseException {
        if (input == null) {
            throw new InvalidParameterException("form is required.");
        }

        // validate request format
        Map<String, List<InputPart>> formDataMap = input.getFormDataMap();
        List<InputPart> templatePart = formDataMap.get(FORM_DATA_NAME_TEMPLATE);
        String extension = validateTemplates(templatePart);

        List<String> templateFileIds = inputPartHelper.readAllTextParts(formDataMap.get(FORM_DATA_NAME_TEMPLATE_FILE_ID));
        if (templateFileIds.size() != templatePart.size()) {
            throw new BusinessException(
                    CoffeeFaultType.INVALID_INPUT,
                    MessageFormat.format(
                            "The number of [{0}] and [{1}] form-data parts must be the same!",
                            FORM_DATA_NAME_TEMPLATE,
                            FORM_DATA_NAME_TEMPLATE_FILE_ID));
        }

        // validate TEMPLATE_DATA
        CreateTemplateGroupRequest request = inputPartHelper.getSingleRequiredRequestPart(
                formDataMap.get(FORM_DATA_NAME_TEMPLATE_DATA),
                CreateTemplateGroupRequest.class,
                FORM_DATA_NAME_TEMPLATE_DATA);
        validateTemplatePartListSize(request, templatePart);
        

        // check if template already exists
        checkTemplateExists(request, extension);

        // create and save entities
        Map<String, byte[]> filesByFileIds = createFileMap(templatePart);
        TemplateGroupRecord templateGroup = templateGroupMapper.createTemplateGroup(request, extension, filesByFileIds);
        saveEntities(templateGroup);

        // create response
        List<String> templateIds = templateGroup.templates().stream().map(Template::getId).toList();
        CreateTemplateGroupResponse response = new CreateTemplateGroupResponse();
        response.withTemplateId(templateIds);
        handleSuccessResultType(response, request);

        return response;
    }
    
    private void validateTemplatePartListSize(CreateTemplateGroupRequest request, List<InputPart> templatePart) throws BaseException {
        if (request.getTemplatePartList() == null || CollectionUtils.isEmpty(request.getTemplatePartList().getTemplatePart())
                || templatePart.size() != request.getTemplatePartList().getTemplatePart().size()) {
            throw new InvalidParameterException("The number of template files and template part definitions must be the same!");
        }
    }

    private void saveEntities(TemplateGroupRecord templateGroupRecord) throws BaseException {
        for (Template template : templateGroupRecord.templates()) {
            templateService.save(template);
        }
        for (TemplatePart templatePart : templateGroupRecord.templateParts()) {
            templatePartService.save(templatePart);
        }
        for (TemplatePartContent templatePartContent : templateGroupRecord.templatePartContents()) {
            templatePartContentService.save(templatePartContent);
        }
        for (TemplateTemplatePart templateTemplatePart : templateGroupRecord.templateTemplateParts()) {
            templateTemplatePartService.save(templateTemplatePart);
        }
    }

    private Map<String, byte[]> createFileMap(List<InputPart> templatePart) throws BaseException {
        Map<String, byte[]> filesByFileIds = new HashMap<>(templatePart.size());
        for (InputPart inputPart : templatePart) {
            String fileName = templateHelper.getFileName(inputPart);
            byte[] fileContent = inputPartHelper.readPartBytes(inputPart);
            filesByFileIds.put(fileName, fileContent);
        }
        return filesByFileIds;
    }

    private void checkTemplateExists(CreateTemplateGroupRequest request, String extension) throws BaseException {
        String name = request.getName();
        List<String> languages = request.getLanguage();
        OffsetDateTime validityStart = request.getValidityStart();
        OffsetDateTime validityEnd = request.getValidityEnd();

        validateLanguages(languages, extension);

        long count;
        if (StringUtils.equals(extension, GeneratorConstants.EXTENSION_XSLT)) {
            count = templateService.countByNameAndValidity(name, validityStart, validityEnd);
        } else {
            count = templateService.countByNameAndLanguageAndValidity(name, languages.get(0), validityStart, validityEnd);
        }

        if (count != 0) {
            // TODO http422 ALREADY_EXISTS
            throw new BusinessException(CoffeeFaultType.ALREADY_EXIST, "Template already exists!");
        }
    }

    private void validateLanguages(List<String> languages, String extension) throws BaseException {
        if (CollectionUtils.isEmpty(languages)) {
            throw new InvalidParameterException("Languages is null or empty!");
        }
        if (!StringUtils.equals(extension, GeneratorConstants.EXTENSION_XSLT) && languages.size() > 1) {
            throw new InvalidParameterException("Multiple languages can only be specified for XSLT templates!");
        }
    }

    private String validateTemplates(List<InputPart> templatePart) throws BaseException {
        if (CollectionUtils.isEmpty(templatePart)) {
            throw new BusinessException(CoffeeFaultType.INVALID_INPUT, "At least one file is required!");
        }
        String firstExtension = templateHelper.getFileExtension(templatePart.get(0));
        for (InputPart inputPart : templatePart) {
            validateTemplate(inputPart, firstExtension);
        }
        return firstExtension;
    }

    private void validateTemplate(InputPart inputPart, String firstExtension) throws BaseException {
        String extension = templateHelper.getFileExtension(inputPart);
        if (!GeneratorConstants.ACCEPTED_TEMPLATE_EXTENSIONS.contains(extension)) {
            throw new BusinessException(
                    CoffeeFaultType.INVALID_INPUT,
                    MessageFormat.format(
                            "Only [{0}] extensions are accepted for form-data: [{1}]!",
                            GeneratorConstants.ACCEPTED_TEMPLATE_EXTENSIONS,
                            GeneratorConstants.FORM_DATA_NAME_TEMPLATE));
        }
        if (!StringUtils.equals(extension, firstExtension)) {
            throw new BusinessException(CoffeeFaultType.INVALID_INPUT, "All template files must have the same extension!");
        }
    }
}
