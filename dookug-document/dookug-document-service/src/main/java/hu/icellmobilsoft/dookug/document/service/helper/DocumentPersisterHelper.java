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
package hu.icellmobilsoft.dookug.document.service.helper;

import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;

import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.jpa.annotation.Transactional;
import hu.icellmobilsoft.dookug.common.model.template.Document;
import hu.icellmobilsoft.dookug.common.model.template.DocumentContent;
import hu.icellmobilsoft.dookug.document.service.service.DocumentContentService;
import hu.icellmobilsoft.dookug.document.service.service.DocumentService;

/**
 * Generated document and its contents persister class
 *
 * @author szabolcs.gemesi
 * @since 0.0.1
 */
@Model
public class DocumentPersisterHelper {

    @Inject
    private DocumentContentService documentContentService;

    @Inject
    private DocumentService documentService;

    /**
     * {@link Document} and {@link DocumentContent} database save
     *
     * @param document
     *            {@link Document}
     * @param documentContent
     *            {@link DocumentContent}
     * @return saved {@link DocumentContent}
     * @throws BaseException
     *             if the save fails
     */
    @Transactional
    public DocumentContent saveDocument(Document document, DocumentContent documentContent) throws BaseException {
        Document savedDocument = documentService.save(document);
        documentContent.setDocumentId(savedDocument.getId());
        return documentContentService.save(documentContent);
    }
}
