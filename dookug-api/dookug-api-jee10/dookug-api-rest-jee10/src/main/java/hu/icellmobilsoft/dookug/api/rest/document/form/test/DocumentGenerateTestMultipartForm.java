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
package hu.icellmobilsoft.dookug.api.rest.document.form.test;

import java.io.InputStream;
import java.util.List;

import jakarta.enterprise.inject.Model;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

/**
 * DocumentGenerate REST Multipart form
 * 
 * @author laszlo.padar
 * @since 0.1.0
 */
@Model
public class DocumentGenerateTestMultipartForm {

    @FormParam("TEMPLATE")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    private InputStream template;

    /**
     * Optional, repeatable part: client can send 0..n parts with name \`SUBTEMPLATE\`.
     */
    @FormParam("SUBTEMPLATE")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    private List<InputStream> subtemplates;

    /**
     * Optional parameters for template engine.
     */
    @FormParam("PARAMETERS_TEMPLATE_ENGINE")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    private InputStream parametersTemplateEngine;

    /**
     * Optional parameters for generator engine.
     */
    @FormParam("PARAMETERS_GENERATOR_ENGINE")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    private InputStream parametersGeneratorEngine;

    /**
     * Optional string part for language
     */
    @FormParam("TEMPLATE_LANGUAGE")
    private String templateLanguage;

    /**
     * Multipart template part getter
     * 
     * @return template part
     */
    public InputStream getTemplate() {
        return template;
    }

    /**
     * Multipart template part setter
     * 
     * @param template
     *            template part
     */
    public void setTemplate(InputStream template) {
        this.template = template;
    }

    /**
     * Multipart subtemplates part getter
     * 
     * @return list of subtemplates part
     */
    public List<InputStream> getSubtemplates() {
        return subtemplates;
    }

    /**
     * Multipart subtemplates part setter
     * 
     * @param subtemplates
     *            list of subtemplates part
     */
    public void setSubtemplates(List<InputStream> subtemplates) {
        this.subtemplates = subtemplates;
    }

    /**
     * Multipart parametersTemplateEngine part getter
     * 
     * @return parametersTemplateEngine part
     */
    public InputStream getParametersTemplateEngine() {
        return parametersTemplateEngine;
    }

    /**
     * Multipart parametersTemplateEngine part setter
     * 
     * @param parametersTemplateEngine
     *            parametersTemplateEngine part
     */
    public void setParametersTemplateEngine(InputStream parametersTemplateEngine) {
        this.parametersTemplateEngine = parametersTemplateEngine;
    }

    /**
     * Multipart parametersGeneratorEngine part getter
     * 
     * @return parametersGeneratorEngine part
     */
    public InputStream getParametersGeneratorEngine() {
        return parametersGeneratorEngine;
    }

    /**
     * Multipart parametersGeneratorEngine part setter
     * 
     * @param parametersGeneratorEngine
     *            parametersGeneratorEngine part
     */
    public void setParametersGeneratorEngine(InputStream parametersGeneratorEngine) {
        this.parametersGeneratorEngine = parametersGeneratorEngine;
    }

    /**
     * Multipart templateLanguage part getter
     * 
     * @return templateLanguage part
     */
    public String getTemplateLanguage() {
        return templateLanguage;
    }

    /**
     * Multipart templateLanguage part setter
     * 
     * @param templateLanguage
     *            templateLanguage part
     */
    public void setTemplateLanguage(String templateLanguage) {
        this.templateLanguage = templateLanguage;
    }
}
