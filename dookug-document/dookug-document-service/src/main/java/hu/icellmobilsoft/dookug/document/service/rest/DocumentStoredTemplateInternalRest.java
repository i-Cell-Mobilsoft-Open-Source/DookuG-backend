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
import hu.icellmobilsoft.dookug.api.rest.document.IDocumentGenerateStoredTemplateInternalRest;
import hu.icellmobilsoft.dookug.api.rest.document.IDocumentStoredTemplateInternalRest;
import hu.icellmobilsoft.dookug.common.system.rest.rest.BaseRestService;
import hu.icellmobilsoft.dookug.document.service.action.DocumentMetadataQueryAction;
import hu.icellmobilsoft.dookug.document.service.action.StoredTemplateDocumentGenerateAction;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataQueryRequest;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataQueryResponse;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataResponse;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.StoredTemplateDocumentGenerateRequest;

/**
 * Stored template service rest implementation
 * 
 * @author laszlo.padar
 *
 * @since 0.1.0
 */
@Model
public class DocumentStoredTemplateInternalRest extends BaseRestService implements IDocumentStoredTemplateInternalRest {

    @Inject
    private DocumentMetadataQueryAction documentMetadataQueryAction;


    @Override
    public DocumentMetadataQueryResponse postDocumentMetadataQuery(DocumentMetadataQueryRequest queryRequest) throws BaseException {
        return wrapPathParam1(documentMetadataQueryAction::postDocumentMetadataQuery, queryRequest, "postDocumentMetadataQuery", "queryRequest");
    }
}
