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
package hu.icellmobilsoft.dookug.document.service.rest.test;

import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.dookug.api.rest.test.IDocumentStoredTemplateGroupTest;
import hu.icellmobilsoft.dookug.common.system.rest.rest.BaseRestService;
import hu.icellmobilsoft.dookug.document.service.action.test.StoredTemplateGroupAction;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.CreateTemplateGroupResponse;

/**
 * Rest service for creating new template group with multipart form data request.
 *
 * @author levente.prehoda
 * @since 2.2.0
 */
@Model
public class DocumentStoredTemplateGroupTest extends BaseRestService implements IDocumentStoredTemplateGroupTest {

    @Inject
    private StoredTemplateGroupAction storedTemplateGroupAction;

    @Override
    public CreateTemplateGroupResponse postStoredTemplateGroup(MultipartFormDataInput input) throws BaseException {
        return wrapPathParam1(storedTemplateGroupAction::createStoredTemplateGroup, input, "postStoredTemplateGroup", "input");
    }
}
