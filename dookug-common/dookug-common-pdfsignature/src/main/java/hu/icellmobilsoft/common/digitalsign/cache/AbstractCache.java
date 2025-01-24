/*-
 * #%L
 * DookuG
 * %%
 * Copyright (C) 2023 - 2024 i-Cell Mobilsoft Zrt.
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
package hu.icellmobilsoft.common.digitalsign.cache;

import java.text.MessageFormat;
import java.time.Duration;
import java.util.concurrent.ExecutionException;

import jakarta.inject.Inject;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import hu.icellmobilsoft.coffee.cdi.logger.AppLogger;
import hu.icellmobilsoft.coffee.cdi.logger.ThisLogger;
import hu.icellmobilsoft.coffee.dto.exception.InvalidParameterException;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.se.api.exception.TechnicalException;
import hu.icellmobilsoft.dookug.common.core.evictable.Evictable;
import hu.icellmobilsoft.dookug.common.system.rest.cache.CacheMetricsCollector;

/**
 * Abstract Cache class
 * 
 * @author tamas.cserhati
 * @since 1.1.0
 * 
 * @param <K>
 *            cache key
 * @param <V>
 *            cache value
 */
public abstract class AbstractCache<K, V> implements Evictable {

    @Inject
    @ThisLogger
    private AppLogger log;

    @Inject
    private CacheMetricsCollector metricsCollector;

    private final LoadingCache<K, V> cache = createCacheBuilder().build(new CacheLoader<>() {
        @Override
        public V load(K key) throws Exception {
            return AbstractCache.this.load(key);
        }
    });

    /**
     * load the cache
     *
     * @param key
     *            the cache key
     * @return the value to cache
     * @throws BaseException
     *             on error
     */
    protected abstract V load(K key) throws BaseException;

    /**
     * Create a {@link CacheBuilder} with default settings
     *
     * @return the {@link CacheBuilder} created
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
     * Get the cache name
     *
     * @return the name used in cache
     */
    protected String getCacheName() {
        return getClass().getSimpleName();
    }

    /**
     * get the cached value
     * 
     * @param key
     *            the cache key to look up for
     * @return the value found
     * @throws BaseException
     *             on error
     */
    public V get(K key) throws BaseException {
        if (!isCacheEnabled()) {
            return load(key);
        }
        if (key == null) {
            throw new InvalidParameterException("key is missing");
        }

        try {
            return cache.get(key);
        } catch (ExecutionException e) {
            if (e.getCause() instanceof BaseException be) {
                throw be;
            } else {
                throw new TechnicalException(CoffeeFaultType.GENERIC_EXCEPTION, MessageFormat.format("Error reading from cache: [{0}]", key), e);
            }
        } finally {
            updateMetrics();
        }
    }

    @Override
    public void evict() {
        cache.invalidateAll();
    }

    /**
     * Invalidate cache for key
     * 
     * @param key
     *            key to invalidate
     * @throws BaseException
     *             on error
     */
    protected void evict(K key) throws BaseException {
        if (key == null) {
            throw new InvalidParameterException("key is missing");
        }
        cache.invalidate(key);
    }

    private void updateMetrics() {
        if (!isStatisticsEnabled()) {
            return;
        }
        try {
            metricsCollector.updateMetrics(cache, getCacheName());
        } catch (BaseException e) {
            log.error("Error during cache metric collection", e);
        }
    }

    /**
     * Checks enabling of metrics/statistics
     * 
     * @return true, if enabled
     */
    protected abstract boolean isStatisticsEnabled();

    /**
     * get the value of TTL
     * 
     * @return TTL value
     */
    protected abstract long getTtl();

    /**
     * return whether the cache enabled is
     * 
     * @return true/false
     */
    public abstract boolean isCacheEnabled();

}
