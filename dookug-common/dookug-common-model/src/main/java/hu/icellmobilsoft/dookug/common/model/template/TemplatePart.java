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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import hu.icellmobilsoft.coffee.model.base.javatime.AbstractIdentifiedAuditEntity;
import hu.icellmobilsoft.dookug.common.model.template.enums.GeneratorEngine;
import hu.icellmobilsoft.dookug.common.model.template.enums.TemplateEngine;
import hu.icellmobilsoft.dookug.common.model.template.enums.TemplateType;

/**
 * Table entity of template parts
 *
 * @author laszlo.padar
 *
 * @since 0.1.0
 */
@Entity
@Table(name = "TEMPLATE_PART")
public class TemplatePart extends AbstractIdentifiedAuditEntity {

    /**
     * serial
     */
    public static final long serialVersionUID = 143234L;

    /**
     * Unique name of template. Template expressions can refer to the name as well as templates can refer to each other with it.
     */
    @NotNull
    @Size(max = 255)
    @Column(name = "KEY", length = 255, nullable = false)
    private String key;

    /**
     * Description about the template. What does it contain, purpose...
     */
    @NotNull
    @Size(max = 1024)
    @Column(name = "DESCRIPTION", length = 1024, nullable = false)
    private String description;

    /**
     * Template engine
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "TEMPLATE_ENGINE", length = 30, nullable = false)
    private TemplateEngine templateEngine;

    /**
     * Generator engine
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "GENERATOR_ENGINE", length = 30, nullable = false)
    private GeneratorEngine generatorEngine;

    /**
     * Type of template
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "TEMPLATE_PART_TYPE", length = 30, nullable = false)
    private TemplateType templatePartType;

    /**
     * Getter of key
     * 
     * @return key
     */
    public String getKey() {
        return key;
    }

    /**
     * Setter of key
     * 
     * @param key
     *            key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Getter of description
     * 
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter of description
     * 
     * @param description
     *            description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter of templateEngine
     * 
     * @return templateEngine
     */
    public TemplateEngine getTemplateEngine() {
        return templateEngine;
    }

    /**
     * Setter of templateEngine
     * 
     * @param templateEngine
     *            templateEngine
     */
    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    /**
     * Getter of generatorEngine
     * 
     * @return generatorEngine
     */
    public GeneratorEngine getGeneratorEngine() {
        return generatorEngine;
    }

    /**
     * Setter of generatorEngine
     * 
     * @param generatorEngine
     *            generatorEngine
     */
    public void setGeneratorEngine(GeneratorEngine generatorEngine) {
        this.generatorEngine = generatorEngine;
    }

    /**
     * Getter of templatePartType
     * 
     * @return templatePartType
     */
    public TemplateType getTemplatePartType() {
        return templatePartType;
    }

    /**
     * Setter of templatePartType
     * 
     * @param templatePartType
     *            templatePartType
     */
    public void setTemplatePartType(TemplateType templatePartType) {
        this.templatePartType = templatePartType;
    }

}
