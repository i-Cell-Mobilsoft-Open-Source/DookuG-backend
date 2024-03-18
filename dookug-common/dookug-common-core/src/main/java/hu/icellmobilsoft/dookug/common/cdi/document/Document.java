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
package hu.icellmobilsoft.dookug.common.cdi.document;

/**
 * Class for containing fields of document
 *
 * @author szabolcs.gemesi
 * @since 0.0.1
 */
public class Document {

    private final String documentId;
    private final String documentFormat;
    private final byte[] content;
    private final String documentStatus;

    private String filename;
    private String storageType;

    /**
     * default constructor
     * 
     * @param documentId
     *            the documentId
     * @param documentFormat
     *            the format
     * @param content
     *            content as byte array
     * @param documentStatus
     *            status of document
     */
    public Document(String documentId, String documentFormat, byte[] content, String documentStatus) {
        this.documentId = documentId;
        this.documentFormat = documentFormat;
        this.content = content;
        this.documentStatus = documentStatus;
    }

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename
     *            the filename to set
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * @return the storageType
     */
    public String getStorageType() {
        return storageType;
    }

    /**
     * @param storageType
     *            the storageType to set
     */
    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    /**
     * @return the documentId
     */
    public String getDocumentId() {
        return documentId;
    }

    /**
     * @return the documentFormat
     */
    public String getDocumentFormat() {
        return documentFormat;
    }

    /**
     * @return the content
     */
    public byte[] getContent() {
        return content;
    }

    /**
     * @return the documentStatus
     */
    public String getDocumentStatus() {
        return documentStatus;
    }

}
