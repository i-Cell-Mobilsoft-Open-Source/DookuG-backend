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
package hu.icellmobilsoft.dookug.common.cdi.template.dto;

import hu.icellmobilsoft.dookug.common.cdi.template.Template;

/**
 * Template part item DTO
 *
 * @author istvan.peli *
 * @since 0.5.0
 */
public class TemplatePartCacheItem {
    private boolean initialTemplate;
    private Template template;

    public TemplatePartCacheItem(boolean initialTemplate, Template template) {
        this.initialTemplate = initialTemplate;
        this.template = template;
    }

    public boolean isInitialTemplate() {
        return initialTemplate;
    }

    public void setInitialTemplate(boolean initialTemplate) {
        this.initialTemplate = initialTemplate;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    @Override
    public String toString() {
        return "TemplatePartCacheItem{" +
                "initialTemplate=" + initialTemplate +
                ", templateName=" + template.getTemplateName() +
                '}';
    }
}
