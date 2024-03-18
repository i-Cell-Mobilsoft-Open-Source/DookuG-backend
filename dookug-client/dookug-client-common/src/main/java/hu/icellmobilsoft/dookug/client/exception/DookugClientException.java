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
package hu.icellmobilsoft.dookug.client.exception;

import hu.icellmobilsoft.coffee.dto.exception.TechnicalException;

/**
 * DookuG Client exception
 * 
 * @author tamas.cserhati
 * @since 0.0.1
 */
public class DookugClientException extends TechnicalException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor for TechnicalException.
     * 
     * @param faultTypeEnum
     *            faultTypeEnum
     * @param message
     *            message
     */
    public DookugClientException(Enum<?> faultTypeEnum, String message) {
        super(faultTypeEnum, message);
    }

    /**
     * Constructor for TechnicalException.
     * 
     * @param faultTypeEnum
     *            faultTypeEnum
     * @param message
     *            message
     * @param e
     *            e
     */
    public DookugClientException(Enum<?> faultTypeEnum, String message, Throwable e) {
        super(faultTypeEnum, message, e);
    }
}
