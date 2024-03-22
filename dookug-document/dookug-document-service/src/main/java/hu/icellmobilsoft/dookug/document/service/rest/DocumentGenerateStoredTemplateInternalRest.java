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

import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.dookug.api.rest.document.IDocumentGenerateStoredTemplateInternalRest;
import hu.icellmobilsoft.dookug.common.system.rest.rest.BaseRestService;
import hu.icellmobilsoft.dookug.document.service.action.StoredTemplateDocumentGenerateAction;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataResponse;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.StoredTemplateDocumentGenerateRequest;

/**
 * Document generate stored template service rest implementation
 * 
 * @author laszlo.padar
 *
 * @since 0.1.0
 */
@Model
public class DocumentGenerateStoredTemplateInternalRest extends BaseRestService implements IDocumentGenerateStoredTemplateInternalRest {

    @Inject
    private StoredTemplateDocumentGenerateAction storedTemplateDocumentGenerateAction;

    @Override
    public Response postStoredTemplateDocumentGenerate(StoredTemplateDocumentGenerateRequest request) throws BaseException {
        saveGeneratorSetup(request);
        return wrapPathParam1(storedTemplateDocumentGenerateAction::postStoredTemplateDocumentGenerate, request, "postStoredTemplateDocumentGenerate",
                "request");
    }

    @Override
    public DocumentMetadataResponse postStoredTemplateDocumentGenerateMetadata(StoredTemplateDocumentGenerateRequest request) throws BaseException {
        return wrapPathParam1(storedTemplateDocumentGenerateAction::postStoredTemplateDocumentGenerateMetadata, request,
                "postStoredTemplateDocumentGenerateMetadata", "request");
    }
}
