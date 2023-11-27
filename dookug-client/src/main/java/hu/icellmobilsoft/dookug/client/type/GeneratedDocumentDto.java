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
package hu.icellmobilsoft.dookug.client.type;

import java.io.InputStream;

/**
 * kliens metodusok visszateresehez sajat DTO
 * 
 * @author tamas.cserhati
 * @since 0.0.1
 */
public class GeneratedDocumentDto {
    private InputStream inputStream;
    private String fileName;
    private int httpStatus;

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * @param inputStream
     *            the inputStream to set
     */
    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName
     *            the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the httpStatus
     */
    public int getHttpStatus() {
        return httpStatus;
    }

    /**
     * @param httpStatus
     *            the httpStatus to set
     */
    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }
}
