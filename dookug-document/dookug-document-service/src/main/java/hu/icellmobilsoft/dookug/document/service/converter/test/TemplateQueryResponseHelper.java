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

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import hu.icellmobilsoft.dookug.common.model.template.Template;
import hu.icellmobilsoft.dookug.common.model.template.enums.GeneratorEngine;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplateType;

/**
 * Helper for building template query responses
 *
 * @author mate.biro
 * @since 2.2.0
 */
@ApplicationScoped
public class TemplateQueryResponseHelper {

    @Inject
    private TemplateTypeConverter templateTypeConverter;

    /**
     * Builds the list of TemplateType rows for the response, applying grouping rules for SAXON templates
     *
     * @param templates
     *            list of templates to convert
     * @return list of TemplateType for the response
     */
    public List<TemplateType> buildRowList(List<Template> templates) {

        List<TemplateType> result = new ArrayList<>();
        // Track SAXON groups in insertion order (first encounter decides output position)
        Map<TemplateGroupKey, GroupAcc> saxonGroups = new LinkedHashMap<>();

        for (Template template : templates) {
            if (template.getGeneratorEngine() == GeneratorEngine.SAXON) {
                // SAXON: group by name and validity period
                TemplateGroupKey key = new TemplateGroupKey(template.getName(), template.getValidityStart(), template.getValidityEnd());
                GroupAcc acc = saxonGroups.computeIfAbsent(key, k -> {
                    TemplateType templateType = templateTypeConverter.convert(template);
                    // append to result at first encounter to keep order
                    result.add(templateType);
                    return new GroupAcc(templateType);
                });
                // accumulate ids and languages for the group
                acc.templateType.withTemplateIds(template.getId());
                if (template.getLanguage() != null && acc.languages.add(template.getLanguage())) {
                    acc.templateType.withLanguages(template.getLanguage());
                }
            } else {
                // non-SAXON: add directly in encounter order
                TemplateType templateType = templateTypeConverter.convert(template);
                templateType.withTemplateIds(template.getId());
                if (template.getLanguage() != null) {
                    templateType.withLanguages(template.getLanguage());
                }
                result.add(templateType);
            }
        }
        return result;
    }

    // helper accumulator for a group
    private record GroupAcc(TemplateType templateType, LinkedHashSet<String> languages) {
        GroupAcc(TemplateType templateType) {
            this(templateType, new LinkedHashSet<>());
        }
    }

    // used only for grouping SAXON templates
    private record TemplateGroupKey(String name, OffsetDateTime validityStart, OffsetDateTime validityEnd) {

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof TemplateGroupKey that)) {
                return false;
            }
            return Objects.equals(name, that.name)
                    && Objects.equals(validityStart, that.validityStart)
                    && Objects.equals(validityEnd, that.validityEnd);
        }
    }
}
