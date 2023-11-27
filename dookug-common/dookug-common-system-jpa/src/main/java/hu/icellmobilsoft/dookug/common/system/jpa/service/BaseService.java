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
package hu.icellmobilsoft.dookug.common.system.jpa.service;

import javax.enterprise.context.Dependent;

/**
 * Base service parent for JPA exception handled functions
 * 
 * @author imre.scheffer
 *
 * @param <T>
 *            entity class
 */
@Dependent
public class BaseService<T> extends hu.icellmobilsoft.coffee.jpa.service.BaseService<T> {

}
