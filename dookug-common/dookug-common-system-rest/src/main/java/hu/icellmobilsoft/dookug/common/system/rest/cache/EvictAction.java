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

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.apache.deltaspike.core.util.ProxyUtils;

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
     * Evict művelet Az {@link Evictable} interface implementációkon iterál végig Az ismert keret szintű szolgáltatásoknál explicit hívja meg az
     * ürítés funkcíót
     *
     * @return {@link EvictResponse} dto, az {@link Evictable}-t implementáló osztályok neveinek listája, az ismert keret szintű szolgáltatások
     *         neveivel kiegészítve
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
