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

/**
 * RuntimeException wrapper for lambda
 * 
 * @author tamas.cserhati
 * @since 1.1.0
 */
public class WrappedRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * the wrapped exception
     */
    private final Exception exception;

    /**
     * constructor
     * 
     * @param e
     *            exception to wrap
     */
    public WrappedRuntimeException(Exception e) {
        this.exception = e;
    }

    /**
     * @return the exception
     */
    public Exception getException() {
        return exception;
    }
}
