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
import java.time.format.DateTimeParseException;

import jakarta.enterprise.context.ApplicationScoped;

import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplateQueryParams;

/**
 * Converter for template query parameters
 *
 * @author mate.biro
 * @since 2.2.0
 */
@ApplicationScoped
public class TemplateQueryParamsConverter {

    /**
     * Converts string parameters to {@link TemplateQueryParams}
     *
     * @param name
     *            template name
     * @param language
     *            template language
     * @param validityStart
     *            validity start in ISO-8601 OffsetDateTime format
     * @param validityEnd
     *            validity end in ISO-8601 OffsetDateTime format
     * @return converted {@link TemplateQueryParams}
     * @throws IllegalArgumentException
     *             if date parsing fails
     */
    public TemplateQueryParams convert(String name, String language, String validityStart, String validityEnd) {
        TemplateQueryParams queryParams = new TemplateQueryParams();
        if (StringUtils.isNotBlank(name)) {
            queryParams.setName(name);
        }
        if (StringUtils.isNotBlank(language)) {
            queryParams.setLanguage(language);
        }
        if (StringUtils.isNotBlank(validityStart)) {
            try {
                queryParams.setValidityStart(OffsetDateTime.parse(validityStart));
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Invalid 'validityStart', expected ISO-8601 OffsetDateTime.", e);
            }
        }
        if (StringUtils.isNotBlank(validityEnd)) {
            try {
                queryParams.setValidityEnd(OffsetDateTime.parse(validityEnd));
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Invalid 'validityEnd', expected ISO-8601 OffsetDateTime.", e);
            }
        }

        return queryParams;
    }
}
