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
package hu.icellmobilsoft.dookug.common.cdi.template;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import jakarta.enterprise.inject.Model;

import org.apache.commons.lang3.StringUtils;

/**
 * Common accessible template container for the scope of the request. Templates are name identified input streams.
 *
 * @author laszlo.padar
 * @since 0.1.0
 */
@Model
public class TemplateContainer {

    /**
     * Name identified templates of the request
     */
    private Map<String, Template> templateMap = new HashMap<>();

    /**
     * Result of the template engine
     */
    private String compiledResult;

    /**
     * The name of initial (root) template of the requests
     */
    private String initTemplateName;

    /**
     * Name identified getter of the container.
     *
     * @param templateName
     *            Name of template
     * @return Optional template input stream
     */
    public Optional<Template> getTemplate(String templateName) {
        if (StringUtils.isBlank(templateName)) {
            return Optional.empty();
        }
        return templateMap.containsKey(templateName) ? Optional.of(templateMap.get(templateName)) : Optional.empty();
    }

    /**
     * Adds a {@link Template} to the map
     *
     * @param template
     *            {@link Template}
     * @param init
     *            If the template is initial
     */
    public void addTemplate(Template template, boolean init) {
        templateMap.put(template.getTemplateName(), template);
        if (init) {
            initTemplateName = template.getTemplateName();
        }
    }

    /**
     * Returns the name of initial template.
     * 
     * @return Optional name of {@link Template}
     */
    public Optional<String> getInitTemplateName() {
        return initTemplateName != null ? Optional.of(initTemplateName) : Optional.empty();
    }

    /**
     * Returns the number of existing templates
     * 
     * @return Number of existing {@link Template} instances in the container
     */
    public int length() {
        return templateMap.size();
    }

    /**
     * Returns the initial {@link Template} of the container
     *
     * @return Optional first {@link Template}
     */
    public Optional<Template> getInitialTemplate() {
        if (templateMap.isEmpty()) {
            return Optional.empty();
        }
        Optional<String> initTemplateName = getInitTemplateName();
        return initTemplateName.isEmpty() ? Optional.of(templateMap.entrySet().iterator().next().getValue())
                : Optional.of(templateMap.get(initTemplateName.get()));
    }

    /**
     * get the compiledResult field
     * 
     * @return the compiled template
     */
    public String getCompiledResult() {
        return compiledResult;
    }

    /**
     * get the compiledResult field
     * 
     * @return the compiled template as byte array
     */
    public byte[] getCompiledResultAsBytes() {
        return compiledResult.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * set the compiledResult field
     * 
     * @param compiledResult
     *            the value to set
     */
    public void setCompiledResult(String compiledResult) {
        this.compiledResult = compiledResult;
    }

}
