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
package hu.icellmobilsoft.dookug.api.rest.test.form;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.multipart.PartType;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;

/**
 * Multipart form for inline document generation test endpoint.
 *
 * @author mate.biro
 * @since 2.2.0
 */
@Model
public class DocumentGenerateInlineTestMultipartForm {

    @FormParam("TEMPLATE")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    private List<InputPart> template;

    @FormParam("SUBTEMPLATE")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    private List<InputPart> subTemplate;

    @FormParam("TEMPLATE_LANGUAGE")
    @PartType(MediaType.TEXT_PLAIN)
    private String templateLanguage;

    @FormParam("PARAMETERS_TEMPLATE_ENGINE")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    private List<InputPart> parametersTemplateEngine;

    @FormParam("PARAMETERS_GENERATOR_ENGINE")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    private List<InputPart> parametersGeneratorEngine;

    /**
     * Multipart template part getter.
     *
     * @return template parts
     */
    public List<InputPart> getTemplate() {
        return template;
    }

    /**
     * Multipart template part setter.
     *
     * @param template
     *            template parts
     */
    public void setTemplate(List<InputPart> template) {
        this.template = template;
    }

    /**
     * Multipart subtemplate part getter.
     *
     * @return subtemplate parts
     */
    public List<InputPart> getSubTemplate() {
        return subTemplate;
    }

    /**
     * Multipart subtemplate part setter.
     *
     * @param subTemplate
     *            subtemplate parts
     */
    public void setSubTemplate(List<InputPart> subTemplate) {
        this.subTemplate = subTemplate;
    }

    /**
     * Template language getter.
     *
     * @return template language
     */
    public String getTemplateLanguage() {
        return templateLanguage;
    }

    /**
     * Template language setter.
     *
     * @param templateLanguage
     *            template language
     */
    public void setTemplateLanguage(String templateLanguage) {
        this.templateLanguage = templateLanguage;
    }

    /**
     * Multipart template engine parameters part getter.
     *
     * @return template engine parameters parts
     */
    public List<InputPart> getParametersTemplateEngine() {
        return parametersTemplateEngine;
    }

    /**
     * Multipart template engine parameters part setter.
     *
     * @param parametersTemplateEngine
     *            template engine parameters parts
     */
    public void setParametersTemplateEngine(List<InputPart> parametersTemplateEngine) {
        this.parametersTemplateEngine = parametersTemplateEngine;
    }

    /**
     * Multipart generator engine parameters part getter.
     *
     * @return generator engine parameters parts
     */
    public List<InputPart> getParametersGeneratorEngine() {
        return parametersGeneratorEngine;
    }

    /**
     * Multipart generator engine parameters part setter.
     *
     * @param parametersGeneratorEngine
     *            generator engine parameters parts
     */
    public void setParametersGeneratorEngine(List<InputPart> parametersGeneratorEngine) {
        this.parametersGeneratorEngine = parametersGeneratorEngine;
    }
}
