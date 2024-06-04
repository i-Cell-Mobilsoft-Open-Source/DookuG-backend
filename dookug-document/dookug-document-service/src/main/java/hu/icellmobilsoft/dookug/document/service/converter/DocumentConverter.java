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
package hu.icellmobilsoft.dookug.document.service.converter;

import java.util.Objects;

import jakarta.enterprise.context.Dependent;

import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.dto.exception.BusinessException;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.dookug.common.cdi.document.Document;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataResponse;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentStatusType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentStorageMethodType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.ResponseFormatType;

/**
 * Document converter {@link Document} &lt;-&gt; {@link DocumentMetadataResponse}
 *
 * @author szabolcs.gemesi
 * @since 0.1.0
 */
@Dependent
public class DocumentConverter {

    /**
     * Convert {@link Document} to {@link DocumentMetadataResponse}
     *
     * @param document
     *            {@link Document}
     * @return {@link DocumentMetadataResponse}
     * @throws BaseException
     *             on error
     */
    public DocumentMetadataResponse convert(Document document) throws BaseException {
        if (Objects.isNull(document)) {
            throw new BusinessException(CoffeeFaultType.INVALID_INPUT, "Document cannot be null!");
        }

        DocumentMetadataResponse response = new DocumentMetadataResponse();
        DocumentMetadataType metadataType = new DocumentMetadataType();
        metadataType.setDocumentId(document.getDocumentId());
        metadataType.setFormat(ResponseFormatType.fromValue(document.getDocumentFormat()));
        metadataType.setStorageMethod(DocumentStorageMethodType.fromValue(document.getStorageType()));
        metadataType.setFilename(document.getFilename());
        metadataType.setStatus(DocumentStatusType.fromValue(document.getDocumentStatus()));
        response.setMetadata(metadataType);
        return response;
    }
}
