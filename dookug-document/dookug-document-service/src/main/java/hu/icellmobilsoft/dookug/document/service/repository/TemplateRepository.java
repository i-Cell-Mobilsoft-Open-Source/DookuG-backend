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
package hu.icellmobilsoft.dookug.document.service.repository;

import java.time.OffsetDateTime;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

import hu.icellmobilsoft.dookug.common.model.template.Template;

/**
 * {@link Template} entitás adatbázis mūveletei
 *
 * @author laszlo.padar
 * @since 0.1.0
 */
@Repository
public interface TemplateRepository extends EntityRepository<Template, String>, CriteriaSupport<Template> {

    /**
     * @param templateName
     *            name of template
     * @param templateLanguage
     *            language of template
     * @param validityDate
     *            date of validity
     * @return {@link Template} object
     */
    @Query("SELECT t FROM Template t WHERE t.name = ?1 AND t.language=?2 AND "
            + " (?3 BETWEEN t.validityStart AND t.validityEnd OR (t.validityStart < ?3 AND t.validityEnd is null))")
    Template findByNameAndValidity(String templateName, String templateLanguage, OffsetDateTime validityDate);

}
