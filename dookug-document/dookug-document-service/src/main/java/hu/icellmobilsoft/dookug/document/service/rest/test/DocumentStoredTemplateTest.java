/*-
 * #%L
 * DookuG
 * %%
 * Copyright (C) 2023 - 2025 i-Cell Mobilsoft Zrt.
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
package hu.icellmobilsoft.dookug.document.service.rest.test;

import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;

import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.dookug.api.rest.test.IDocumentStoredTemplateTest;
import hu.icellmobilsoft.dookug.common.system.rest.rest.BaseRestService;
import hu.icellmobilsoft.dookug.document.service.action.test.StoredTemplateAction;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplateQueryResponse;

/**
 * Stored template test service rest implementation
 *
 * @author mate.biro
 * @since 2.2.0
 */
@Model
public class DocumentStoredTemplateTest extends BaseRestService implements IDocumentStoredTemplateTest {

    @Inject
    private StoredTemplateAction storedTemplateAction;

    @Override
    public TemplateQueryResponse getTemplateMetaDataQuery(String name, String language, String validityStart, String validityEnd, String sort)
            throws BaseException {
        return wrapPathParam5(
                storedTemplateAction::getTemplateMetaDataQuery,
                name,
                language,
                validityStart,
                validityEnd,
                sort,
                "getTemplateMetaDataQuery",
                "name",
                "language",
                "validityStart",
                "validityEnd",
                "sort");
    }
}
