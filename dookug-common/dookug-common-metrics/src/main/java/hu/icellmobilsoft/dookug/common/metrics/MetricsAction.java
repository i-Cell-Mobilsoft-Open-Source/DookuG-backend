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
package hu.icellmobilsoft.dookug.common.metrics;

import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.metrics.Metadata;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.MetricType;
import org.eclipse.microprofile.metrics.Tag;

/**
 * Metrics base functions
 *
 * @author mate.biro
 * @since 0.10.0
 */
@Model
public class MetricsAction {

    @Inject
    private MetricRegistry metricRegistry;

    /**
     * Increment metrics counter
     *
     * @param name
     *            metric metadata name
     * @param description
     *            metric metadata description
     * @param tagName
     *            metric metadata tagName
     * @param tagValue
     *            metric metadata tagValue
     */
    public void incMetricsCounter(String name, String description, String tagName, String tagValue) {
        if (StringUtils.isAnyBlank(name, description, tagName, tagValue)) {
            throw new IllegalArgumentException("name, description, tagName and tagValue are required!");
        }
        Metadata metadata = Metadata.builder()//
                .withName(name)//
                .withDescription(description)//
                .withType(MetricType.COUNTER)//
                .build();
        Tag tagStorageHandler = new Tag(tagName, tagValue);
        metricRegistry.counter(metadata, tagStorageHandler).inc();
    }
}
