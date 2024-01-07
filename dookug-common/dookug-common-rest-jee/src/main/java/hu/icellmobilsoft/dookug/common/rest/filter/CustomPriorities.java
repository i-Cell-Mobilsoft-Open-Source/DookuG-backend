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
package hu.icellmobilsoft.dookug.common.rest.filter;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;

/**
 * A collection of values used in {@link Priority} annotations, similar to the {@link Priorities} class.
 *
 * @author martin.nagy
 * @since 1.0.0
 */
public interface CustomPriorities {

    /**
     * Filter/interceptor priority running before authentication
     */
    int PRE_AUTHENTICATION = 500;

}
