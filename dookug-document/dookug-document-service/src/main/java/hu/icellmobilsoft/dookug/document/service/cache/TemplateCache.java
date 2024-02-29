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
package hu.icellmobilsoft.dookug.document.service.cache;

import java.security.InvalidParameterException;
import java.time.Duration;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.ConfigProvider;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import hu.icellmobilsoft.coffee.cdi.logger.AppLogger;
import hu.icellmobilsoft.coffee.cdi.logger.ThisLogger;
import hu.icellmobilsoft.coffee.configuration.ApplicationConfiguration;
import hu.icellmobilsoft.dookug.api.dto.constants.ConfigKeys;
import hu.icellmobilsoft.dookug.common.system.rest.cache.AbstractCache;
import hu.icellmobilsoft.dookug.document.service.cache.dto.TemplateCacheItem;

/**
 * Template cache functions
 *
 * @author istvan.peli
 * @since 0.5.0
 */
@ApplicationScoped
public class TemplateCache extends AbstractCache<String, TemplateCacheItem> {

    @Inject
    @ThisLogger
    private AppLogger log;

    private final Cache<String, TemplateCacheItem> cache = createCacheBuilder().build();

    @Inject
    private ApplicationConfiguration applicationConfiguration;

    /**
     * Add a new {@link TemplateCacheItem} to the Map
     *
     * @param templateCacheItem
     *            {@link TemplateCacheItem} added element
     */
    public void newTemplateCacheItem(TemplateCacheItem templateCacheItem) {
        if (templateCacheItem != null && StringUtils.isNotBlank(templateCacheItem.getTemplateId())) {
            cache.put(templateCacheItem.getTemplateId(), templateCacheItem);
            updateMetrics();
            log.debug("New templateCacheItem [{0}]", templateCacheItem);
        } else {
            throw new InvalidParameterException("templateCacheItem is invalid: " + templateCacheItem);
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
        updateMetrics();
        return getCache().getIfPresent(templateId);
    }

    @Override
    protected Cache<String, TemplateCacheItem> getCache() {
        return cache;
    }

    @Override
    protected String getCacheName() {
        return "template";
    }

    @Override
    protected long getTtl() {
        return applicationConfiguration.getOptionalValue(ConfigKeys.Cache.Template.TTL, Long.class)
                .orElse(Long.parseLong(ConfigKeys.Cache.Template.Defaults.TTL_IN_MINUTES));
    }

    @Override
    protected boolean isStatisticsEnabled() {
        return applicationConfiguration.getOptionalValue(ConfigKeys.Cache.Template.ENABLESTATISTIC, Boolean.class).orElse(false);
    }

    @Override
    public boolean isCacheEnabled() {
        return applicationConfiguration.getOptionalValue(ConfigKeys.Cache.Template.ENABLED, Boolean.class).orElse(true);
    }

}
