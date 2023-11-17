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

/**
 * Table entity of templates Print means the summary of templates used during document generation.
 *
 * @author laszlo.padar
 *
 * @since 0.1.0
 */
@Entity
@Table(name = "TEMPLATE")
public class Template extends AbstractIdentifiedAuditEntity {

    /**
     * serial
     */
    public static final long serialVersionUID = 143235L;

    /**
     * Unique name of print. Requests can refer to prints.
     */
    @NotNull
    @Size(max = 255)
    @Column(name = "NAME", length = 255, nullable = false)
    private String name;

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
     * Language of template
     */
    @NotNull
    @Size(max = 30)
    @Column(name = "LANGUAGE", length = 30, nullable = false)
    private String language;

    /**
     * Start of validity
     */
    @NotNull
    @Column(name = "VALIDITY_START", nullable = false)
    private OffsetDateTime validityStart;

    /**
     * End of validity
     */
    @Column(name = "VALIDITY_END")
    private OffsetDateTime validityEnd;

    /**
     * Getter of name
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter of name
     * 
     * @param name
     *            name
     */
    public void setName(String name) {
        this.name = name;
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
     * Getter of language
     * 
     * @return language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Setter of language
     * 
     * @param language
     *            language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Getter of validityStart
     * 
     * @return validityStart
     */
    public OffsetDateTime getValidityStart() {
        return validityStart;
    }

    /**
     * Setter of validityStart
     * 
     * @param validityStart
     *            validityStart
     */
    public void setValidityStart(OffsetDateTime validityStart) {
        this.validityStart = validityStart;
    }

    /**
     * Getter of validityEnd
     * 
     * @return validityEnd
     */
    public OffsetDateTime getValidityEnd() {
        return validityEnd;
    }

    /**
     * Setter of validityEnd
     * 
     * @param validityEnd
     *            validityEnd
     */
    public void setValidityEnd(OffsetDateTime validityEnd) {
        this.validityEnd = validityEnd;
    }

}
