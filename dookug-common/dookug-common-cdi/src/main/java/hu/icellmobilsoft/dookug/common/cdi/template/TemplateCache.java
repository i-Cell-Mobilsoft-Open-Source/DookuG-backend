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

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import hu.icellmobilsoft.coffee.cdi.logger.AppLogger;
import hu.icellmobilsoft.coffee.cdi.logger.ThisLogger;
import hu.icellmobilsoft.dookug.api.dto.constants.ConfigKeys;
import hu.icellmobilsoft.dookug.common.cdi.template.dto.TemplateCacheAction;
import hu.icellmobilsoft.dookug.common.cdi.template.dto.TemplateCacheItem;

/**
 * Template cache functions
 *
 * @author istvan.peli
 * @since 0.5.0
 */
@ApplicationScoped
public class TemplateCache {
    private final Map<String, TemplateCacheItem> cachedTemplate = new HashMap<>();

    private static TemplateCacheItem firstExpiringCacheItem = null;

    @Inject
    @ThisLogger
    private AppLogger log;

    @Inject
    @ConfigProperty(name = ConfigKeys.Cache.DOOKUG_SERVICE_TEMPLATE_MEMORY_CACHE_TTL,
            defaultValue = ConfigKeys.Cache.DEFAULT_DOOKUG_SERVICE_TEMPLATE_MEMORY_CACHE_TTL_IN_MINUTES)
    private Integer cacheTTLInMinutes;

    /**
     * Add a new {@link TemplateCacheItem} to the Map
     *
     * @param {@link
     *            TemplateCacheItem} added element
     */
    public void newTemplateCacheItem(TemplateCacheItem templateCacheItem) {
        if (templateCacheItem != null && StringUtils.isNotBlank(templateCacheItem.getTemplateId())) {
            modifyCacheTemplateMap(templateCacheItem, TemplateCacheAction.ADD);
            log.debug("New templateCacheItem [{0}]", templateCacheItem);
        }
    }

    /**
     * Returns the searched {@link TemplateCacheItem}, no expiry check
     * 
     * @param templateId
     *            searched template id
     * @return searched {@link TemplateCacheItem}
     * @throws InvalidParameterException
     *             if templateId is not present in map
     * 
     */
    public TemplateCacheItem getTemplateCacheItem(String templateId) {
        if (cachedTemplate.containsKey(templateId)) {
            return modifyCacheTemplateMap(cachedTemplate.get(templateId), TemplateCacheAction.REFRESH);
        }

        throw new InvalidParameterException("TemplateId (" + templateId + ") is invalid!");
    }

    /**
     * check template in the map
     * 
     * @param templateId
     *            searched tempalet id
     * @return true if present and not expired
     */
    public boolean containNotExpiredTemplate(String templateId) {
        if (cachedTemplate.containsKey(templateId)) {
            TemplateCacheItem templateCacheItem = cachedTemplate.get(templateId);
            if (isExpired(templateCacheItem, LocalDateTime.now())) {
                return false;
            }

            return true;
        }

        return false;
    }

    private synchronized TemplateCacheItem modifyCacheTemplateMap(TemplateCacheItem templateCacheItem, TemplateCacheAction templateCacheAction) {
        LocalDateTime now = LocalDateTime.now();
        templateCacheItem.setTtlDateTime(now.plusMinutes(cacheTTLInMinutes));

        if (templateCacheAction == TemplateCacheAction.ADD) {
            return addTemplateCacheItem(templateCacheItem);
        }

        if (templateCacheAction == TemplateCacheAction.REFRESH) {
            return refreshTemplateCahceItem(templateCacheItem, now);
        }

        throw new InvalidParameterException("TemplateCacheAction (" + templateCacheAction + ") is invalid!");
    }

    private TemplateCacheItem refreshTemplateCahceItem(TemplateCacheItem templateCacheItem, LocalDateTime now) {
        if (needCheckExpiredCacheItems(templateCacheItem, now)) {
            refreshCacheItems(now);

            log.debug(
                    "Now: [{0}], templateItemTTL: [{1}]. The earliest templateCacheItem: [{2}]",
                    now,
                    firstExpiringCacheItem.getTtlDateTime(),
                    firstExpiringCacheItem);

        }

        return templateCacheItem;
    }

    private TemplateCacheItem addTemplateCacheItem(TemplateCacheItem templateCacheItem) {
        if (firstExpiringCacheItem == null) {
            firstExpiringCacheItem = templateCacheItem;
        }

        cachedTemplate.put(templateCacheItem.getTemplateId(), templateCacheItem);

        return templateCacheItem;
    }

    private void refreshCacheItems(LocalDateTime now) {
        Iterator<Map.Entry<String, TemplateCacheItem>> cacheItemIterator = cachedTemplate.entrySet().iterator();
        TemplateCacheItem newFirstExpiringCacheItem = null;

        while (cacheItemIterator.hasNext()) {
            Map.Entry<String, TemplateCacheItem> item = cacheItemIterator.next();
            if (isValidItem(item)) {
                TemplateCacheItem actualTemplateCacheItem = item.getValue();

                if (log.isTraceEnabled()) {
                    log.trace(
                            "Key: [{0}], TTL: [{1}], now: [{2}], templateCacheItem: [{3}]",
                            item.getKey(),
                            actualTemplateCacheItem.getTtlDateTime(),
                            now,
                            actualTemplateCacheItem);
                }

                if (isExpired(actualTemplateCacheItem, now)) {
                    log.debug(
                            "Now: [{0}], templateItemTTL: [{1}], now: [{2}] . Delete templateCacheItem: [{3}]",
                            now,
                            actualTemplateCacheItem.getTtlDateTime(),
                            now,
                            actualTemplateCacheItem);
                    cacheItemIterator.remove();
                } else {
                    if (newFirstExpiringCacheItem == null
                            || newFirstExpiringCacheItem.getTtlDateTime().isAfter(actualTemplateCacheItem.getTtlDateTime())) {
                        newFirstExpiringCacheItem = actualTemplateCacheItem;
                    }
                }
            }
        }

        firstExpiringCacheItem = newFirstExpiringCacheItem;
    }

    private boolean isValidItem(Map.Entry<String, TemplateCacheItem> item) {
        return item.getValue() != null && item.getValue().getTtlDateTime() != null;
    }

    private boolean needCheckExpiredCacheItems(TemplateCacheItem templateCacheItem, LocalDateTime now) {
        if (firstExpiringCacheItem.getTemplateId().equals(templateCacheItem.getTemplateId())) {
            return false;
        }

        return firstExpiringCacheItem.getTtlDateTime() == null || firstExpiringCacheItem.getTtlDateTime().isBefore(now);
    }

    private boolean isExpired(TemplateCacheItem templateCacheItem, LocalDateTime now) {
        return now.isAfter(templateCacheItem.getTtlDateTime());
    }
}
