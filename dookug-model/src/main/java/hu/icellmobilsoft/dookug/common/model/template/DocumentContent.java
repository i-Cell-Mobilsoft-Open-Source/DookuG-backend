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
package hu.icellmobilsoft.dookug.common.model.template;

import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import hu.icellmobilsoft.coffee.model.base.javatime.AbstractIdentifiedAuditEntity;

/**
 * Table entity of document blob data
 *
 * @author laszlo.padar
 *
 * @since 0.1.0
 */
@Entity
@Table(name = "DOCUMENT_CONTENT")
public class DocumentContent extends AbstractIdentifiedAuditEntity {
    /**
     * serial
     */
    public static final long serialVersionUID = 143239L;

    /**
     * ID of document content
     */
    @NotNull
    @Size(max = 30)
    @Column(name = "DOCUMENT_ID", length = 30, nullable = false)
    private String documentId;

    /**
     * Compressed binary content of document
     */
    @NotNull
    @Column(name = "CONTENT", nullable = false)
    private byte[] content;

    /**
     * Expiration of document content
     */
    @Column(name = "EXPIRY")
    private OffsetDateTime expiry;

    /**
     * Getter of documentId
     * 
     * @return documentId
     */
    public String getDocumentId() {
        return documentId;
    }

    /**
     * Setter of documentId
     * 
     * @param documentId
     *            documentId
     */
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    /**
     * Getter of content
     * 
     * @return content
     */
    public byte[] getContent() {
        return content;
    }

    /**
     * Setter of content
     * 
     * @param content
     *            content
     */
    public void setContent(byte[] content) {
        this.content = content;
    }

    /**
     * Getter of expiry
     * 
     * @return expiry
     */
    public OffsetDateTime getExpiry() {
        return expiry;
    }

    /**
     * Setter of expiry
     * 
     * @param expiry
     *            expiry
     */
    public void setExpiry(OffsetDateTime expiry) {
        this.expiry = expiry;
    }

}
