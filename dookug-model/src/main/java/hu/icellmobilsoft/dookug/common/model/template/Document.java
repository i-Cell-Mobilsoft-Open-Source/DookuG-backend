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

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import hu.icellmobilsoft.coffee.model.base.javatime.AbstractIdentifiedAuditEntity;
import hu.icellmobilsoft.dookug.common.model.template.enums.DocumentStatus;

/**
 * Table entity of documents Print means the summary of templates used during document generation.
 *
 * @author laszlo.padar
 *
 * @since 0.1.0
 */
@Entity
@Table(name = "DOCUMENT")
public class Document extends AbstractIdentifiedAuditEntity {

    /**
     * serial
     */
    public static final long serialVersionUID = 143238L;

    /**
     * ID of template
     */
    @Size(max = 30)
    @Column(name = "TEMPLATE_ID", length = 30)
    private String templateId;

    /**
     * Status of document
     */
    @NotNull
    @Column(name = "STATUS", length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private DocumentStatus status;

    /**
     * Format of document
     */
    @NotNull
    @Size(max = 30)
    @Column(name = "FORMAT", length = 30, nullable = false)
    private String format;

    /**
     * The message in case of error
     */
    @Size(max = 512)
    @Column(name = "ERROR_MESSAGE", length = 512)
    private String errorMessage;

    /**
     * Type of storage
     */
    @NotNull
    @Size(max = 30)
    @Column(name = "STORAGE_TYPE", length = 30, nullable = false)
    private String storageType;

    /**
     * Storage id
     */
    @Size(max = 512)
    @Column(name = "STORAGE_ID", length = 128)
    private String storageId;

    /**
     * Parameter key-value pairs
     */
    @Column(name = "PARAMETERS")
    private byte[] parameters;

    /**
     * Parameter data structure
     */
    @Column(name = "PARAMETER_DATA")
    private byte[] parameterData;

    /**
     * Configuration data of document
     */
    @Column(name = "config")
    private String config;

    /**
     * Configuration data of document
     */
    @Size(max = 100)
    @Column(name = "FILENAME", length = 100)
    private String filename;

    /**
     * Getter of templateId
     * 
     * @return templateId
     */
    public String getTemplateId() {
        return templateId;
    }

    /**
     * Setter of templateId
     * 
     * @param templateId
     *            templateId
     */
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    /**
     * Getter of status
     * 
     * @return status
     */
    public DocumentStatus getStatus() {
        return status;
    }

    /**
     * Setter of status
     * 
     * @param status
     *            status
     */
    public void setStatus(DocumentStatus status) {
        this.status = status;
    }

    /**
     * Getter of format
     * 
     * @return format
     */
    public String getFormat() {
        return format;
    }

    /**
     * Setter of format
     * 
     * @param format
     *            format
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * Getter of errorMessage
     * 
     * @return errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Setter of errorMessage
     * 
     * @param errorMessage
     *            errorMessage
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Getter of storageType
     * 
     * @return storageType
     */
    public String getStorageType() {
        return storageType;
    }

    /**
     * Setter of storageType
     * 
     * @param storageType
     *            storageType
     */
    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    /**
     * Getter of storageId
     * 
     * @return storageId
     */
    public String getStorageId() {
        return storageId;
    }

    /**
     * Setter of storageId
     * 
     * @param storageId
     *            storageId
     */
    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }

    /**
     * Getter of parameters
     * 
     * @return parameters
     */
    public byte[] getParameters() {
        return parameters;
    }

    /**
     * Setter of parameters
     * 
     * @param parameters
     *            parameters
     */
    public void setParameters(byte[] parameters) {
        this.parameters = parameters;
    }

    /**
     * Getter of parameterData
     * 
     * @return parameterData
     */
    public byte[] getParameterData() {
        return parameterData;
    }

    /**
     * Setter of parameterData
     * 
     * @param parameterData
     *            parameterData
     */
    public void setParameterData(byte[] parameterData) {
        this.parameterData = parameterData;
    }

    /**
     * Getter of config
     * 
     * @return config
     */
    public String getConfig() {
        return config;
    }

    /**
     * Setter of config
     * 
     * @param config
     *            config
     */
    public void setConfig(String config) {
        this.config = config;
    }

    /**
     * Getter of filename
     * 
     * @return filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Setter of filename
     * 
     * @param filename
     *            filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }
}
