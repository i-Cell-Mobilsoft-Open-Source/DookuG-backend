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
package hu.icellmobilsoft.dookug.common.system.rest.cache;

import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.time.Duration;
import java.util.Optional;

import jakarta.inject.Inject;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import hu.icellmobilsoft.coffee.cdi.logger.AppLogger;
import hu.icellmobilsoft.coffee.cdi.logger.ThisLogger;
import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.dookug.common.core.evictable.Evictable;
import hu.icellmobilsoft.dookug.common.system.rest.action.BaseAction;

/**
 * Class supporting common caching
 * 
 * @param <KEY>
 *            cache key type, ami alapján keresünk
 * @param <VALUE>
 *            the type of items stored in the cache
 *
 * @author istvan.peli
 * @since 0.5.0
 */
public abstract class AbstractCache<KEY, VALUE> extends BaseAction implements Evictable {
    /**
     * {@value #CONFIG_PATTERN}
     */
    protected static final String CONFIG_PATTERN = "dookug.service.cache.{0}.{1}";
    /**
     * {@value #EXPIRE_AFTER_WRITE_IN_MINUTES}
     */
    protected static final String EXPIRE_AFTER_WRITE_IN_MINUTES = "ttl";
    /**
     * {@value #ENABLE_STATISTICS}
     */
    protected static final String ENABLE_STATISTICS = "enablestatistic";

    @Inject
    @ThisLogger
    private AppLogger log;

    @Inject
    private CacheMetricsCollector metricsCollector;

    private final Config config = ConfigProvider.getConfig();

    /**
     * Return the guava cache object
     *
     * @return the guava cache object
     */
    protected abstract Cache<KEY, VALUE> getCache();

    /**
     * Create a cache builder that contains the cache settings
     *
     * @return the cache builder
     */
    protected CacheBuilder<Object, Object> createCacheBuilder() {
        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();

        if (isStatisticsEnabled()) {
            cacheBuilder = cacheBuilder.recordStats().removalListener(notification -> updateMetrics());
        }

        Optional<Long> expireAfterWriteInMinutes = config.getOptionalValue(formatKey(EXPIRE_AFTER_WRITE_IN_MINUTES), Long.class);
        if (expireAfterWriteInMinutes.isPresent()) {
            cacheBuilder.expireAfterWrite(Duration.ofMinutes(expireAfterWriteInMinutes.get()));
        } else {
            configureDefault(cacheBuilder);
        }

        return cacheBuilder;
    }

    /**
     * Set default values on cacheBuilder
     *
     * @param cacheBuilder
     *            cacheBuilder to configure
     */
    protected void configureDefault(CacheBuilder<Object, Object> cacheBuilder) {
        cacheBuilder.expireAfterWrite(Duration.ofHours(12));
    }

    /**
     * Return the name used in the config
     *
     * @return the name
     */
    protected abstract String getCacheName();

    /**
     * invalidate cache
     */
    public void evict() {
        getCache().invalidateAll();
    }

    /**
     * Remove the data stored for the key from the cache
     *
     * @param key
     *            the key to remove
     * @throws BaseException
     *             if key is empty
     */
    protected void evict(KEY key) throws BaseException {
        if (key == null) {
            throw new InvalidParameterException("key is missing");
        }
        getCache().invalidate(key);
    }

    /**
     * update metrics
     */
    protected void updateMetrics() {
        if (isStatisticsEnabled()) {
            try {
                metricsCollector.updateMetrics(getCache(), getCacheName());
            } catch (BaseException e) {
                log.error("Error during cache metric collection", e);
            }
        }
    }

    private boolean isStatisticsEnabled() {
        return config.getOptionalValue(formatKey(ENABLE_STATISTICS), Boolean.class).orElse(false);
    }

    private String formatKey(String key) {
        return MessageFormat.format(CONFIG_PATTERN, getCacheName(), key);
    }
}
