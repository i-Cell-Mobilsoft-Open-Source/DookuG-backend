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
package hu.icellmobilsoft.dookug.document.service.cache.dto;

import hu.icellmobilsoft.dookug.common.cdi.template.Template;

/**
 * Template part item DTO
 *
 * @author istvan.peli 
 * @since 0.5.0
 */
public class TemplatePartCacheItem {
    private final boolean initialTemplate;
    private Template template;

    /**
     * default constructor
     * 
     * @param initialTemplate
     *            is initial template?
     * @param template
     *            {@link Template} object
     */
    public TemplatePartCacheItem(boolean initialTemplate, Template template) {
        this.initialTemplate = initialTemplate;
        this.setTemplate(template);
    }

    @Override
    public String toString() {
        return "TemplatePartCacheItem{" + "initialTemplate=" + isInitialTemplate() + ", templateName=" + getTemplate().getTemplateName() + '}';
    }

    /**
     * @return the initialTemplate
     */
    public boolean isInitialTemplate() {
        return initialTemplate;
    }

    /**
     * @return the template
     */
    public Template getTemplate() {
        return template;
    }

    /**
     * @param template
     *            the template to set
     */
    public void setTemplate(Template template) {
        this.template = template;
    }
}
