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

import javax.annotation.Priority;
import javax.ws.rs.Priorities;

/**
 * {@link Priority} annotációkban használt értékek gyüjteménye, a {@link Priorities} osztályhoz hasonlóan.
 *
 * @author martin.nagy
 * @since 0.10.0
 */
public interface CustomPriorities {

    /**
     * Authentikáció előtt futó filter/interceptor prioritás
     */
    int PRE_AUTHENTICATION = 500;

}
