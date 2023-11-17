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
package hu.icellmobilsoft.dookug.common.cdi.template;

import java.util.Map;

import hu.icellmobilsoft.coffee.dto.exception.BaseException;

/**
 * Interface for compiling templates. Performs the variable -&gt; value replacement and recursive template composition. Uses a specified template
 * engine.
 *
 * @author laszlo.padar
 * @author imre.scheffer
 * @since 0.1.0
 */
public interface ITemplateCompiler {

    /**
     * Compiles the template structure of the request. Replaces all template expressions with key/value data.
     *
     * @param parameters
     *            Key value data map
     * @throws BaseException
     *             on error
     */
    public void compile(Map<String, String> parameters) throws BaseException;

    /**
     * Compiles the template structure of the request. Replaces all template expressions with values of data structure.
     *
     * @param parameterData
     *            Data structure of values (JSON, XML, ...)
     * @throws BaseException
     *             on error
     */
    public void compile(byte[] parameterData) throws BaseException;
}
