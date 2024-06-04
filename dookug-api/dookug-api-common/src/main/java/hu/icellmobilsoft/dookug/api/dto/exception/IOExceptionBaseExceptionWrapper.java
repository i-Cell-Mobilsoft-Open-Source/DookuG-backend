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
package hu.icellmobilsoft.dookug.api.dto.exception;

import java.io.IOException;

import hu.icellmobilsoft.coffee.dto.exception.BaseExceptionWrapper;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;

/**
 * {@code IOException} csomagolása {@code BaseException} formára hogy a hibakezelés automatama lehessen
 * 
 * @author imre.scheffer
 * @since 0.1.0
 *
 */
public class IOExceptionBaseExceptionWrapper extends IOException implements BaseExceptionWrapper<BaseException> {

    private static final long serialVersionUID = 1L;

    /**
     * Wrapped BaseException
     */
    private BaseException exception;

    /**
     * Becsomagol egy BaseException típusú kivételt
     *
     * @param cause
     *            Az eredeti BaseException
     */
    public IOExceptionBaseExceptionWrapper(BaseException cause) {
        super(cause);
        this.exception = cause;
    }

    @Override
    public void setException(BaseException exception) {
        this.exception = exception;
    }

    @Override
    public BaseException getException() {
        return exception;
    }
}
