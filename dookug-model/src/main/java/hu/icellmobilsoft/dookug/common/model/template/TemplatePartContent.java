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
 * Table entity of template blob data
 *
 * @author laszlo.padar
 *
 * @since 0.1.0
 */
@Entity
@Table(name = "TEMPLATE_PART_CONTENT")
public class TemplatePartContent extends AbstractIdentifiedAuditEntity {

    /**
     * serial
     */
    public static final long serialVersionUID = 143234L;

    /**
     * ID of template
     */
    @NotNull
    @Size(max = 30)
    @Column(name = "TEMPLATE_PART_ID", length = 30, nullable = false)
    private String templatePartId;

    /**
     * Binary content of template
     */
    @NotNull
    @Column(name = "CONTENT", nullable = false)
    private byte[] content;

    /**
     * The content of the template is compressed
     */
    @NotNull
    @Column(name = "COMPRESSED", nullable = false)
    private boolean compressed;

    /**
     * Getter of templatePartId
     * 
     * @return templatePartId
     */
    public String getTemplatePartId() {
        return templatePartId;
    }

    /**
     * Setter of templatePartId
     * 
     * @param templatePartId
     *            templatePartId
     */
    public void setTemplatePartId(String templatePartId) {
        this.templatePartId = templatePartId;
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
     * Getter of compressed
     * 
     * @return compressed
     */
    public boolean isCompressed() {
        return compressed;
    }

    /**
     * Setter of compressed
     * 
     * @param compressed
     *            compressed
     */
    public void setCompressed(boolean compressed) {
        this.compressed = compressed;
    }
}
