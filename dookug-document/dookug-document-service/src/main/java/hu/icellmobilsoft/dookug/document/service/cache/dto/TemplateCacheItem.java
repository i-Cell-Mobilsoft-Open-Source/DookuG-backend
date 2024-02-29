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

import java.util.ArrayList;
import java.util.List;

/**
 * Template cache data DTO
 *
 * @author istvan.peli
 * @since 0.5.0
 */
public class TemplateCacheItem {

    private final String templateId;
    private final String templateName;
    private final List<TemplatePartCacheItem> templatePartCacheItems;

    /**
     * default constructor
     * 
     * @param templateId
     *            the template identifier
     * @param templateName
     *            the template name
     */
    public TemplateCacheItem(String templateId, String templateName) {
        this.templateId = templateId;
        this.templateName = templateName;

        templatePartCacheItems = new ArrayList<>();
    }

    /**
     * @param templatePartCacheItem
     *            the cached template part
     */
    public void addNewPartItem(TemplatePartCacheItem templatePartCacheItem) {
        templatePartCacheItems.add(templatePartCacheItem);
    }

    /**
     * @return the templateId
     */
    public String getTemplateId() {
        return templateId;
    }

    /**
     * @return the templateName
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     * @return the templatePartCacheItems
     */
    public List<TemplatePartCacheItem> getTemplatePartCacheItems() {
        return templatePartCacheItems;
    }

    @Override
    public String toString() {
        return "TemplateCacheItem{" + "templateId='" + templateId + '\'' + ", templateName='" + templateName + '\'' + ", templatePartCacheItems="
                + templatePartCacheItems + '}';
    }

}
