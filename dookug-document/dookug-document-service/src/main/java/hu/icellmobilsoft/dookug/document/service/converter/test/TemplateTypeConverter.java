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
package hu.icellmobilsoft.dookug.document.service.converter.test;

import jakarta.enterprise.context.ApplicationScoped;

import hu.icellmobilsoft.coffee.tool.utils.enums.EnumUtil;
import hu.icellmobilsoft.dookug.common.model.template.Template;
import hu.icellmobilsoft.dookug.schemas.common._1_0.common.GeneratorEngineType;
import hu.icellmobilsoft.dookug.schemas.common._1_0.common.TemplateEngineType;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplateType;

/**
 * Converter for Template to TemplateType
 *
 * @author mate.biro
 * @since 2.2.0
 */
@ApplicationScoped
public class TemplateTypeConverter {

    /**
     * Converts Template to TemplateType
     *
     * @param template
     *            template to convert
     * @return converted TemplateType
     */
    public TemplateType convert(Template template) {

        TemplateType templateType = new TemplateType();

        templateType.setName(template.getName());
        templateType.setDescription(template.getDescription());
        templateType.setTemplateEngine(EnumUtil.convert(template.getTemplateEngine(), TemplateEngineType.class));
        templateType.setGeneratorEngine(EnumUtil.convert(template.getGeneratorEngine(), GeneratorEngineType.class));
        templateType.setValidityStart(template.getValidityStart());
        templateType.setValidityEnd(template.getValidityEnd());
        templateType.setLastUpdatedAt(template.getModificationDate() != null ? template.getModificationDate() : template.getCreationDate());
        // template ids and languages should be set by the caller
        // templateType.withTemplateIds();
        // templateType.withLanguages();

        return templateType;
    }
}
