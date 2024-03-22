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
package hu.icellmobilsoft.dookug.document.service.action;

import jakarta.enterprise.inject.Model;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.coffee.dto.exception.InvalidParameterException;
import hu.icellmobilsoft.coffee.rest.utils.ResponseUtil;
import hu.icellmobilsoft.dookug.common.cdi.StorageMethodQualifier;
import hu.icellmobilsoft.dookug.common.cdi.document.Document;
import hu.icellmobilsoft.dookug.common.cdi.document.IDocumentStore;
import hu.icellmobilsoft.dookug.common.system.rest.action.BaseAction;
import hu.icellmobilsoft.dookug.document.service.container.DocumentContainer;
import hu.icellmobilsoft.dookug.document.service.service.DocumentService;

/**
 * Generated document content action
 * 
 * @author szabolcs.gemesi
 * @since 0.0.1
 */
@Model
public class DocumentContentAction extends BaseAction {

    @Inject
    private DocumentService documentService;

    @Inject
    private DocumentContainer documentContainer;

    /**
     * Get the generated document content by the given document id.
     * 
     * @param documentId
     *            document id
     * @return Generated document
     * @throws BaseException
     *             on error
     */
    public Response getDocumentContent(String documentId) throws BaseException {
        if (StringUtils.isBlank(documentId)) {
            throw new InvalidParameterException("Document id cannot be blank!");
        }

        hu.icellmobilsoft.dookug.common.model.template.Document databaseDocument = documentService
                .findById(documentId, hu.icellmobilsoft.dookug.common.model.template.Document.class);
        documentContainer.setDocument(databaseDocument);
        IDocumentStore documentStore = CDI.current()
                .select(IDocumentStore.class, new StorageMethodQualifier.Literal(databaseDocument.getStorageType()))
                .get();
        Document document = documentStore.getDocumentById(documentId);
        return ResponseUtil.getFileResponse(document.getContent(), document.getFilename(), MediaType.APPLICATION_OCTET_STREAM);
    }
}
