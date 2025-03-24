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

import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.dookug.api.rest.document.IDocumentSignInternalRest;
import hu.icellmobilsoft.dookug.api.rest.document.form.DocumentSignMultipartForm;
import hu.icellmobilsoft.dookug.common.system.rest.rest.BaseRestService;
import hu.icellmobilsoft.dookug.document.service.action.DocumentSignAction;

/**
 * Sign document service rest implementation
 *
 * @author tamas.cserhati
 * @since 1.1.0
 */
@Model
public class DocumentSignInternalRest extends BaseRestService implements IDocumentSignInternalRest {

    @Inject
    private DocumentSignAction documentSignAction;

    @Override
    public Response postSignDocumentMultipart(DocumentSignMultipartForm form) throws BaseException {
        return wrapPathParam1(documentSignAction::postSignDocumentMultipart, form, "postSignDocumentMultipart", "form");
    }
}
