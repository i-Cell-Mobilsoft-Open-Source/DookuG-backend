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
package hu.icellmobilsoft.dookug.document.service.service;

import java.time.OffsetDateTime;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.coffee.jpa.service.BaseService;
import hu.icellmobilsoft.dookug.common.model.template.Template;
import hu.icellmobilsoft.dookug.document.service.repository.TemplateRepository;

/**
 * {@link Template} database operations
 *
 * @author laszlo.padar
 * @since 0.1.0
 */
@Model
public class TemplateService extends BaseService<Template> {

    @Inject
    private TemplateRepository templateRepository;

    /**
     * Finds single {@link Template} record by name, language and validity
     * 
     * @param templateName
     *            Name of the template
     * @param templateLanguage
     *            language of template
     * @param validityDate
     *            Validity of template
     * @return Found {@link Template} record
     * @throws BaseException
     *             on error
     */
    public Template findByNameLanguageAndValidity(String templateName, String templateLanguage, OffsetDateTime validityDate) throws BaseException {
        return wrapValidated(
                templateRepository::findByNameLanguageAndValidity,
                templateName,
                templateLanguage,
                validityDate,
                "findByNameAndValidity",
                "templateName",
                "templateLanguage",
                "validityDate");
    }

    /**
     * Finds the x__id of the {@link Template} record by name, language and validity
     * 
     * @param templateName
     *            Name of the template
     * @param templateLanguage
     *            language of template
     * @param validityDate
     *            Validity of template
     * @return Found {@link Template} record
     * @throws BaseException
     *             on error
     */
    public String findTemplateIdByNameLanguageAndValidity(String templateName, String templateLanguage, OffsetDateTime validityDate)
            throws BaseException {
        return wrapValidated(
                templateRepository::findTemplateIdByNameLanguageAndValidity,
                templateName,
                templateLanguage,
                validityDate,
                "findTemplateIdByNameLanguageAndValidity",
                "templateName",
                "templateLanguage",
                "validityDate");
    }

}
