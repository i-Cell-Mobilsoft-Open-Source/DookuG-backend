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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheStats;

import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.dookug.common.system.rest.action.BaseAction;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;

/**
 * Action for collecting cache metrics
 *
 * @author istvan.peli
 * @since 0.5.0
 */
@ApplicationScoped
public class CacheMetricsCollector extends BaseAction {

    private static final String NAME_TAG = "name";
    private static final String METADATA_PREFIX = "cache_";

    private final Map<String, AtomicLong> metricValues = new ConcurrentHashMap<>();

    @Inject
    private MeterRegistry meterRegistry;

    /**
     * Collects metrics from the passed cache object
     *
     * @param cache
     *            the cache object
     * @param cacheName
     *            name of cache
     * @throws BaseException
     *             on error
     */
    public void updateMetrics(Cache<?, ?> cache, String cacheName) throws BaseException {
        if (cache == null || StringUtils.isBlank(cacheName)) {
            throw new InvalidParameterException("cache or cacheName is missing!");
        }
        CacheStats stats = cache.stats();

        updateGauge(METADATA_PREFIX + "hit_count", stats.hitCount(), cacheName);
        updateGauge(METADATA_PREFIX + "miss_count", stats.missCount(), cacheName);
        updateGauge(METADATA_PREFIX + "size", cache.size(), cacheName);
    }

    private void updateGauge(String metricName, long value, String cacheName) {
        String key = metricName + "_" + cacheName;
        metricValues.computeIfAbsent(key, k -> {
            AtomicLong atomicValue = new AtomicLong(value);
            Gauge.builder(metricName, atomicValue, AtomicLong::get)
                    .description("Cache metric: " + metricName)
                    .tag(NAME_TAG, cacheName)
                    .register(meterRegistry);
            return atomicValue;
        }).set(value);

    }
}
