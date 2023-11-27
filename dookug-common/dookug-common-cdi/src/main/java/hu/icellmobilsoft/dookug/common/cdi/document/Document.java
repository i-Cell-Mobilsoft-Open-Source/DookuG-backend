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

import javax.enterprise.inject.Vetoed;

/**
 * Class for containing fields of document
 *
 * @author szabolcs.gemesi
 * @since 0.0.1
 */
@Vetoed
public class Document {

    private final String documentId;

    private final String documentFormat;

    private String filename;

    private String storageType;

    private final byte[] content;

    private final String documentStatus;

    public Document(String documentId, String documentFormat, byte[] content, String documentStatus) {
        this.documentId = documentId;
        this.documentFormat = documentFormat;
        this.content = content;
        this.documentStatus = documentStatus;
    }

    public String getDocumentId() {
        return documentId;
    }

    public String getDocumentFormat() {
        return documentFormat;
    }

    public byte[] getContent() {
        return content;
    }

    public String getFilename() {
        return filename;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }
}
