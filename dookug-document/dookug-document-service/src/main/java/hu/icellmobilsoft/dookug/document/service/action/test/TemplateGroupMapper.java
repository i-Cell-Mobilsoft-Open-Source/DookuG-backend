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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;

import hu.icellmobilsoft.coffee.model.base.generator.EntityIdGenerator;
import hu.icellmobilsoft.coffee.tool.utils.enums.EnumUtil;
import hu.icellmobilsoft.dookug.common.model.template.Template;
import hu.icellmobilsoft.dookug.common.model.template.TemplatePart;
import hu.icellmobilsoft.dookug.common.model.template.TemplatePartContent;
import hu.icellmobilsoft.dookug.common.model.template.TemplateTemplatePart;
import hu.icellmobilsoft.dookug.common.model.template.enums.GeneratorEngine;
import hu.icellmobilsoft.dookug.common.model.template.enums.TemplateEngine;
import hu.icellmobilsoft.dookug.common.model.template.enums.TemplateType;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.CreateTemplateGroupRequest;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplatePartDataType;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplatePartType;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplatePartTypeType;

/**
 * Mapper for creating template group related entities from the request
 *
 * @author levente.prehoda
 * @since 2.2.0
 */
@ApplicationScoped
public class TemplateGroupMapper {

    /**
     * Creates template gorup related entities from the request
     * 
     * @param request
     *            the request dto
     * @param extension
     *            the file(s) extension
     * @param filesByFileIds
     *            map of fileId - file content
     * @return record containing the created entities
     */
    public TemplateGroupRecord createTemplateGroup(CreateTemplateGroupRequest request, String extension, Map<String, byte[]> filesByFileIds) {
        List<Template> templates = createTemplates(request, extension);
        TemplatePartsAndTemplatePartContents templatePartsAndTemplatePartContents = createTemplatePartsAndContents(
                request.getTemplatePartList().getTemplatePart(),
                request.getName(),
                filesByFileIds);
        List<TemplateTemplatePart> templateTemplateParts = createTemplateTemplateParts(
                templates,
                templatePartsAndTemplatePartContents.templateParts());
        return new TemplateGroupRecord(
                templates,
                templatePartsAndTemplatePartContents.templateParts(),
                templatePartsAndTemplatePartContents.templatePartContents(),
                templateTemplateParts);
    }

    private List<TemplateTemplatePart> createTemplateTemplateParts(List<Template> templates, List<TemplatePart> templateParts) {
        List<TemplateTemplatePart> templateTemplateParts = new ArrayList<>();
        for (Template template : templates) {
            for (TemplatePart templatePart : templateParts) {
                TemplateTemplatePart templateTemplatePart = new TemplateTemplatePart();
                templateTemplatePart.setTemplateId(template.getId());
                templateTemplatePart.setTemplatePartId(templatePart.getId());
                templateTemplateParts.add(templateTemplatePart);
            }
        }

        return templateTemplateParts;
    }

    private TemplatePartsAndTemplatePartContents createTemplatePartsAndContents(List<TemplatePartType> templatePartTypes, String name,
            Map<String, byte[]> filesByFileIds) {
        List<TemplatePart> templateParts = new ArrayList<>();
        List<TemplatePartContent> templatePartContents = new ArrayList<>();

        for (TemplatePartType templatePartType : templatePartTypes) {
            TemplatePart templatePart = new TemplatePart();
            templatePart.setId(EntityIdGenerator.generateId());
            templatePart.setDescription(templatePartType.getTemplatePartData().getDescription());
            templatePart.setTemplatePartType(EnumUtil.convert(templatePartType.getTemplatePartData().getTemplatePartType(), TemplateType.class));
            String key = getKey(templatePartType.getTemplatePartData(), name);
            templatePart.setKey(key);
            templateParts.add(templatePart);

            TemplatePartContent templatePartContent = new TemplatePartContent();
            templatePartContent.setTemplatePartId(templatePart.getId());
            templatePartContent.setContent(filesByFileIds.get(templatePartType.getTemplateFileId()));
            templatePartContent.setCompressed(false);
            templatePartContents.add(templatePartContent);
        }

        return new TemplatePartsAndTemplatePartContents(templateParts, templatePartContents);
    }

    private String getKey(TemplatePartDataType templatePartDataType, String name) {
        if (templatePartDataType.getTemplatePartType() == TemplatePartTypeType.MAIN) {
            return name + TemplateType.MAIN.name();
        }
        return templatePartDataType.getKey();
    }

    private List<Template> createTemplates(CreateTemplateGroupRequest request, String extension) {
        List<Template> templates = new ArrayList<>();
        for (String language : request.getLanguage()) {
            Template template = new Template();
            template.setId(EntityIdGenerator.generateId());
            template.setName(request.getName());
            template.setDescription(request.getDescription());
            template.setTemplateEngine(EnumUtil.convert(request.getTemplateEngine(), TemplateEngine.class));
            template.setGeneratorEngine(getGeneratorEngine(extension));
            template.setLanguage(language);
            template.setValidityStart(request.getValidityStart());
            template.setValidityEnd(request.getValidityEnd());
            templates.add(template);
        }

        return templates;
    }

    private GeneratorEngine getGeneratorEngine(String extension) {
        return switch (extension) {
            case GeneratorConstants.EXTENSION_TXT -> GeneratorEngine.NONE;
            case GeneratorConstants.EXTENSION_HTML -> GeneratorEngine.PDF_BOX;
            case GeneratorConstants.EXTENSION_XSLT -> GeneratorEngine.SAXON;
            default -> null;
        };
    }

    private record TemplatePartsAndTemplatePartContents(List<TemplatePart> templateParts, List<TemplatePartContent> templatePartContents) {
    }
}
