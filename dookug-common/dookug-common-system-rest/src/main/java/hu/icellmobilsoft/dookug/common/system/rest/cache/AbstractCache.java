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

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import hu.icellmobilsoft.coffee.cdi.logger.AppLogger;
import hu.icellmobilsoft.coffee.cdi.logger.ThisLogger;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.dookug.api.dto.constants.ConfigKeys;
import hu.icellmobilsoft.dookug.common.core.evictable.Evictable;
import hu.icellmobilsoft.dookug.common.system.rest.action.BaseAction;

/**
 * Class supporting general caching
 * 
 * @param <KEY>
 *            Type of cache key used for lookup
 * @param <VALUE>
 *            Type of elements stored in the cache
 *
 * @author istvan.peli
 * @since 0.5.0
 */
public abstract class AbstractCache<KEY, VALUE> extends BaseAction implements Evictable {

    @Inject
    @ThisLogger
    private AppLogger log;

    @Inject
    private CacheMetricsCollector metricsCollector;

    /**
     * Returns the used Guava cache object
     *
     * @return the guava cache object
     */
    protected abstract Cache<KEY, VALUE> getCache();

    /**
     * Creates a cache builder which contains the settings
     * 
     * @return the {@link CacheBuilder}
     */
    protected CacheBuilder<Object, Object> createCacheBuilder() {
        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();

        if (isStatisticsEnabled()) {
            cacheBuilder = cacheBuilder.recordStats().removalListener(notification -> updateMetrics());
        }
        cacheBuilder.expireAfterWrite(Duration.ofMinutes(getTtl()));
        return cacheBuilder;
    }

    /**
     * get the value of TTL
     * 
     * @return TTL value
     */
    protected abstract long getTtl();

    /**
     * Checks enabling of metrics/statistics
     * 
     * @return true, if enabled
     */
    protected abstract boolean isStatisticsEnabled();

    /**
     * Get the cache name from configuration
     *
     * @return the name used for cache
     */
    protected abstract String getCacheName();

    /**
     * return whether the cache enabled is
     * 
     * @return true/false
     */
    public abstract boolean isCacheEnabled();

    /**
     * invalidate cache
     */
    public void evict() {
        getCache().invalidateAll();
    }

    /**
     * Removes the data from cache stored under the key
     *
     * @param key
     *            the key to delete
     * @throws BaseException
     *             on error
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

    /**
     * cache initialization
     */
    @PostConstruct
    public void init() {
        log.info("Enable caching of [{0}]: [{1}]", getCacheName(), isCacheEnabled());
    }
}
