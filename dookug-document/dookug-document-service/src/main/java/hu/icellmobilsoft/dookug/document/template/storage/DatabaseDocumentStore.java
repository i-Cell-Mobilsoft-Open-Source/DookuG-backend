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
package hu.icellmobilsoft.dookug.document.template.storage;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.dto.exception.BusinessException;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.coffee.se.util.string.RandomUtil;
import hu.icellmobilsoft.dookug.common.cdi.StorageMethodQualifier;
import hu.icellmobilsoft.dookug.common.cdi.constants.QualifierConstants;
import hu.icellmobilsoft.dookug.common.cdi.document.Document;
import hu.icellmobilsoft.dookug.common.cdi.document.IDocumentStore;
import hu.icellmobilsoft.dookug.common.cdi.template.TemplateDataContainer;
import hu.icellmobilsoft.dookug.common.model.template.DocumentContent;
import hu.icellmobilsoft.dookug.common.model.template.enums.DocumentStatus;
import hu.icellmobilsoft.dookug.common.util.filename.FileUtil;
import hu.icellmobilsoft.dookug.document.service.container.DocumentContainer;
import hu.icellmobilsoft.dookug.document.service.helper.DocumentPersisterHelper;
import hu.icellmobilsoft.dookug.document.service.service.DocumentContentService;
import hu.icellmobilsoft.dookug.document.service.service.DocumentService;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.ResponseFormatType;

/**
 * {@link IDocumentStore} database implementation
 *
 * @author szabolcs.gemesi
 * @since 0.0.1
 */
@ApplicationScoped
@StorageMethodQualifier(QualifierConstants.StorageMethodType.DATABASE)
public class DatabaseDocumentStore implements IDocumentStore {

    @Inject
    private DocumentContentService documentContentService;

    @Inject
    private DocumentService documentService;

    @Inject
    private DocumentContainer documentContainer;

    @Inject
    private DocumentPersisterHelper persisterHelper;

    @Inject
    private TemplateDataContainer templateContainer;

    /**
     * Gets the document by the given identifier
     * 
     * @param documentId
     *            Document identifier
     * @return {@link Document}
     * @throws BaseException
     *             if there aren't {@link hu.icellmobilsoft.dookug.common.model.template.Document} for the given documentId
     */
    @Override
    public Document getDocumentById(String documentId) throws BaseException {
        if (StringUtils.isBlank(documentId)) {
            throw new BusinessException(CoffeeFaultType.INVALID_INPUT, "Document id is blank!");
        }
        hu.icellmobilsoft.dookug.common.model.template.Document databaseDocument = documentContainer.getDocument();
        if (databaseDocument == null) {
            databaseDocument = documentService.findById(documentId, hu.icellmobilsoft.dookug.common.model.template.Document.class);
        }
        DocumentContent documentContent = documentContentService.findByDocumentId(documentId);
        Document document = new Document(documentContent.getDocumentId(), databaseDocument.getFormat(), documentContent.getContent(),
                databaseDocument.getStatus().name());
        document.setFilename(databaseDocument.getFilename());
        document.setStorageType(databaseDocument.getStorageType());
        return document;
    }

    /**
     * Save and returns the document object
     *
     * @param content
     *            Document content
     * @param format
     *            Document format
     * @param parameters
     *            {@link hu.icellmobilsoft.dookug.common.model.template.Document#getParameters()}
     * @return {@link Document}
     * @throws BaseException
     *             if database error occurs
     */
    @Override
    public Document saveDocumentWithParameters(byte[] content, String format, byte[] parameters) throws BaseException {
        hu.icellmobilsoft.dookug.common.model.template.Document databaseDocument = fillDocument(format);
        databaseDocument.setParameters(parameters);
        DocumentContent documentContent = new DocumentContent();
        documentContent.setContent(content);
        DocumentContent savedDocumentContent = persisterHelper.saveDocument(databaseDocument, documentContent);
        return createDocument(savedDocumentContent, databaseDocument);
    }

    /**
     * Save and returns the document object
     *
     * @param content
     *            Document content
     * @param format
     *            Document format
     * @param parameterData
     *            {@link hu.icellmobilsoft.dookug.common.model.template.Document#getParameterData()}
     * @return {@link Document}
     * @throws BaseException
     *             if database error occurs
     */
    @Override
    public Document saveDocumentWithParameterData(byte[] content, String format, byte[] parameterData) throws BaseException {
        hu.icellmobilsoft.dookug.common.model.template.Document databaseDocument = fillDocument(format);
        databaseDocument.setParameterData(parameterData);
        DocumentContent documentContent = new DocumentContent();
        documentContent.setContent(content);
        DocumentContent savedDocumentContent = persisterHelper.saveDocument(databaseDocument, documentContent);
        return createDocument(savedDocumentContent, databaseDocument);
    }

    private hu.icellmobilsoft.dookug.common.model.template.Document fillDocument(String format) throws BaseException {
        hu.icellmobilsoft.dookug.common.model.template.Document databaseDocument = new hu.icellmobilsoft.dookug.common.model.template.Document();
        databaseDocument.setId(RandomUtil.generateId());
        if (EnumUtils.isValidEnumIgnoreCase(ResponseFormatType.class, format)) {
            databaseDocument.setFilename(FileUtil.createFilename(databaseDocument.getId(), templateContainer.getTemplateName(),
                    ResponseFormatType.fromValue(format), OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC)));
        } else {
            databaseDocument.setFilename(FileUtil.createFilename(databaseDocument.getId(), templateContainer.getTemplateName(), format,
                    OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC)));
        }
        databaseDocument.setFormat(format);
        databaseDocument.setStorageType(QualifierConstants.StorageMethodType.DATABASE);
        databaseDocument.setStatus(DocumentStatus.DONE);
        databaseDocument.setTemplateId(templateContainer.getTemplateId());
        return databaseDocument;
    }

    private Document createDocument(DocumentContent documentContent, hu.icellmobilsoft.dookug.common.model.template.Document databaseDocument) {
        Document document = new Document(documentContent.getDocumentId(), databaseDocument.getFormat(), documentContent.getContent(),
                databaseDocument.getStatus().name());
        document.setFilename(databaseDocument.getFilename());
        document.setStorageType(databaseDocument.getStorageType());
        return document;
    }
}
