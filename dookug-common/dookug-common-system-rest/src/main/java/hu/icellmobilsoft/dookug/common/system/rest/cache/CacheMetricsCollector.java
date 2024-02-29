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

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.metrics.Metadata;
import org.eclipse.microprofile.metrics.MetricID;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.Tag;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheStats;
import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.dookug.common.system.rest.action.BaseAction;

/**
 * Cache metrikákat gyűjtő action
 *
 * @author istvan.peli
 * @since 0.5.0
 */
@ApplicationScoped
public class CacheMetricsCollector extends BaseAction {

    private static final String NAME_TAG = "name";
    private static final String METADATA_PREFIX = "cache_";
    private static final Metadata HIT_COUNT_METADATA = Metadata.builder().withName(METADATA_PREFIX + "hit_count").withDescription("").build();
    private static final Metadata MISS_COUNT_METADATA = Metadata.builder().withName(METADATA_PREFIX + "miss_count").withDescription("").build();
    private static final Metadata SIZE_METADATA = Metadata.builder().withName(METADATA_PREFIX + "size").withDescription("").build();

    private final Map<MetricID, Long> metricValueMap = new ConcurrentHashMap<>();

    @Inject
    private MetricRegistry metricRegistry;

    /**
     * Kigyűjti az átadott cache objektumból a metrikákat
     *
     * @param cache
     *            vizsgált cache példány
     * @param cacheName
     *            az átadott cache neve
     * @throws BaseException
     *             hiba esetén
     */
    public void updateMetrics(Cache<?, ?> cache, String cacheName) throws BaseException {
        if (cache == null || StringUtils.isBlank(cacheName)) {
            throw new InvalidParameterException("cache or cacheName is missing!");
        }
        Tag cacheNameTag = new Tag(NAME_TAG, cacheName);

        CacheStats stats = cache.stats();

        updateGauge(HIT_COUNT_METADATA, stats.hitCount(), cacheNameTag);
        updateGauge(MISS_COUNT_METADATA, stats.missCount(), cacheNameTag);
        updateGauge(SIZE_METADATA, cache.size(), cacheNameTag);
    }

    private void updateGauge(Metadata metadata, long value, Tag... tags) {
        MetricID metricID = new MetricID(metadata.getName(), tags);
        metricValueMap.put(metricID, value);
        metricRegistry.gauge(metadata, metricID, metricValueMap::get, tags);
    }
}
