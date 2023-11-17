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
package hu.icellmobilsoft.dookug.document.service.rest;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.dookug.api.rest.document.IDocumentContentInternalRest;
import hu.icellmobilsoft.dookug.common.system.rest.rest.BaseRestService;
import hu.icellmobilsoft.dookug.document.service.action.DocumentContentAction;

/**
 * Generated document content service rest implementation
 *
 * @author szabolcs.gemesi
 * @since 0.0.1
 */
@Model
public class DocumentContentInternalRest extends BaseRestService implements IDocumentContentInternalRest {

    @Inject
    private DocumentContentAction documentDataAction;

    @Override
    public Response getDocumentContent(String documentId) throws BaseException {
        return wrapPathParam1(documentDataAction::getDocumentContent, documentId, "getDocumentContent", "documentId");
    }
}
