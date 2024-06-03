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

import java.io.OutputStream;
import java.util.Map;

import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.dookug.common.cdi.sign.DigitalSigningDto;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.ParametersDataType;

/**
 * Interface for document generation
 *
 * @author laszlo.padar
 * @since 0.1.0
 */
public interface IDocumentGenerator {

    /**
     * Creates a document using a templates and a values. Returns the result as streaming output.
     *
     * @param outputStream
     *            generate output
     * @param parameterData
     *            Key value data map
     * @param digitalSigningDto
     *            nullable, the digital singing parameters in case of digital signing is required
     * @throws BaseException
     *             on error
     */
    public void generateToOutputStream(OutputStream outputStream, Map<String, String> parameterData,
            DigitalSigningDto digitalSigningDto) throws BaseException;

    /**
     * Creates a document using a templates and a values. Returns the result as streaming output.
     *
     * @param outputStream
     *            generate output
     * @param parametersData
     *            Data structure of values (JSON, XML, ...)
     * @param digitalSigningDto
     *            nullable, the digital singing parameters in case of digital signing is required
     * @throws BaseException
     *             on error
     */
    public void generateToOutputStream(OutputStream outputStream, ParametersDataType parametersData,
            DigitalSigningDto digitalSigningDto) throws BaseException;
}
