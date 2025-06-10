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
package hu.icellmobilsoft.dookug.common.system.rest.rest;

import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;

import hu.icellmobilsfoft.coffee.module.wildfly.rest.system.AbstractWildFlySystemRest;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.dookug.common.system.rest.cache.EvictAction;

/**
 * Default system REST operations
 * 
 * @author Imre Scheffer
 * @since 0.1.0
 */
@Model
public class SystemRest extends AbstractWildFlySystemRest {

    @Inject
    private EvictAction evictAction;

    @Override
    public hu.icellmobilsoft.coffee.dto.common.config.evict.EvictResponse getEvict() throws BaseException {
        return wrapNoParam(evictAction::evict, "evict");
    }

}
