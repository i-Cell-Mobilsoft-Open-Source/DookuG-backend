/*-
 * #%L
 * Coffee
 * %%
 * Copyright (C) 2020 - 2024 i-Cell Mobilsoft Zrt.
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
package hu.icellmobilsoft.dookug.ts.common.rest;

import java.util.Collection;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.coffee.dto.fault.provider.spi.IFaultTypeProvider;
import hu.icellmobilsoft.coffee.module.mp.restclient.exception.FaultTypeParser;
import hu.icellmobilsoft.dookug.api.dto.exception.enums.FaultType;

/**
 * FaultType provider - provides every FaultType enumeration used in the project
 * 
 * @author tamas.cserhati
 * @since 2.5.0
 * @see FaultTypeParser
 */
@ApplicationScoped
public class ProjectFaultTypeProvider implements IFaultTypeProvider {

    @Override
    public Collection<Class<? extends Enum>> faultTypeEnums() {
        return List.of(CoffeeFaultType.class, FaultType.class);
    }
}
