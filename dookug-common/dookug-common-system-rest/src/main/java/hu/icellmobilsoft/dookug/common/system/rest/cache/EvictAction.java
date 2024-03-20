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
package hu.icellmobilsoft.dookug.common.system.rest.cache;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;

import hu.icellmobilsoft.coffee.cdi.util.ProxyUtils;
import hu.icellmobilsoft.coffee.configuration.ApplicationConfiguration;
import hu.icellmobilsoft.coffee.tool.utils.date.DateUtil;
import hu.icellmobilsoft.dookug.common.core.evictable.Evictable;
import hu.icellmobilsoft.dookug.common.system.rest.action.BaseAction;
import hu.icellmobilsoft.dookug.schemas.common._1_0.config.evict.EvictResponse;

/**
 * {@link Evictable} szolgáltatások igény szerint törölhetik az állapotaikat Az action iterál végig ezeken a szolgáltatásokon
 *
 * @author tamas.cserhati
 * @since 0.6.0
 */
@Model
public class EvictAction extends BaseAction {

    @Inject
    private ApplicationConfiguration applicationConfiguration;

    @Any
    @Inject
    private Instance<Evictable> evictables;

    /**
     * Evict operation iterates through the {@link Evictable} interface implementations for known framework level services explicitly calling the
     * 'clear' function
     *
     * @return {@link EvictResponse} dto, list of names of classes implementing {@link Evictable} with the names of the known framework level services
     */
    public EvictResponse evict() {
        EvictResponse response = new EvictResponse();
        response.setEvictionStart(DateUtil.nowUTC());

        List<String> evicted = new ArrayList<>();

        applicationConfiguration.clear();
        evicted.add(getName(applicationConfiguration));

        if (!evictables.isUnsatisfied()) {
            evictables.forEach(evictable -> {
                evictable.evict();
                evicted.add(getName(evictable));
            });
        }

        response.setEvictionEnd(DateUtil.nowUTC());
        response.withEvicted(evicted);
        handleSuccessResultType(response);
        return response;
    }

    private String getName(Object evictable) {
        return ProxyUtils.getUnproxiedClass(evictable.getClass()).getName();
    }

}
