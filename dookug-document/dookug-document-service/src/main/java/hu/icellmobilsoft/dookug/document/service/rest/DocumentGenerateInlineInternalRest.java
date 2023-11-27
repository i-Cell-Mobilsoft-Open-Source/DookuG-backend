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
import hu.icellmobilsoft.dookug.api.rest.document.DocumentGenerateMultipartForm;
import hu.icellmobilsoft.dookug.api.rest.document.IDocumentGenerateInlineInternalRest;
import hu.icellmobilsoft.dookug.common.system.rest.rest.BaseRestService;
import hu.icellmobilsoft.dookug.document.service.action.DocumentGenerateAction;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentGenerateWithTemplatesRequest;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataResponse;

/**
 * Document generate inline service rest implementation
 * 
 * @author laszlo.padar
 *
 * @since 0.1.0
 */
@Model
public class DocumentGenerateInlineInternalRest extends BaseRestService implements IDocumentGenerateInlineInternalRest {

    @Inject
    private DocumentGenerateAction documentGenerateAction;

    @Override
    public Response postDocumentGenerateMultipart(DocumentGenerateMultipartForm form) throws BaseException {
        saveGeneratorSetup(form);
        return wrapPathParam1(documentGenerateAction::postDocumentGenerate, form, "postDocumentGenerateMultipart", "form");
    }

    @Override
    public Response postDocumentGenerateEntityBody(DocumentGenerateWithTemplatesRequest request) throws BaseException {
        saveGeneratorSetup(request);
        return wrapPathParam1(documentGenerateAction::postDocumentGenerate, request, "postDocumentGenerateEntityBody", "request");
    }

    @Override
    public DocumentMetadataResponse postDocumentGenerateMultipartMetadata(DocumentGenerateMultipartForm form) throws BaseException {
        saveGeneratorSetup(form);
        return wrapPathParam1(documentGenerateAction::postDocumentGenerateMetadata, form, "postDocumentGenerateMultipartMetadata", "form");
    }

    @Override
    public DocumentMetadataResponse postDocumentGenerateEntityBodyMetadata(DocumentGenerateWithTemplatesRequest request) throws BaseException {
        saveGeneratorSetup(request);
        return wrapPathParam1(documentGenerateAction::postDocumentGenerateMetadata, request, "postDocumentGenerateEntityBodyMetadata", "request");
    }

}
