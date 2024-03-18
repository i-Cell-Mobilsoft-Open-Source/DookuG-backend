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

import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;

import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.dookug.common.model.template.Document;
import hu.icellmobilsoft.dookug.common.model.template.DocumentContent;
import hu.icellmobilsoft.dookug.common.system.jpa.service.BaseService;
import hu.icellmobilsoft.dookug.document.service.repository.DocumentContentRepository;

/**
 * {@link DocumentContent} database operations
 *
 * @author szabolcs.gemesi
 * @since 0.0.1
 */
@Model
public class DocumentContentService extends BaseService<DocumentContent> {

    @Inject
    private DocumentContentRepository documentContentRepository;

    /**
     * Find {@link DocumentContent} by the given document identifier
     * 
     * @param documentId
     *            {@link Document#getId()}
     * @return {@link DocumentContent}
     * @throws BaseException
     *             if DB error occurs
     */
    public DocumentContent findByDocumentId(String documentId) throws BaseException {
        return wrap(documentContentRepository::findByDocumentId, documentId, "findByDocumentId", "documentId");
    }
}
