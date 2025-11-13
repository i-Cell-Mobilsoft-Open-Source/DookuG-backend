/*-
 * #%L
 * DookuG
 * %%
 * Copyright (C) 2023 - 2025 i-Cell Mobilsoft Zrt.
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
package hu.icellmobilsoft.dookug.common.system.rest.util;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Response util
 *
 * @author mate.biro
 * @since 2.1.0
 */
public class ResponseUtil extends hu.icellmobilsoft.coffee.rest.utils.ResponseUtil {

    /**
     * Returns GZIP {@link Response} with given entity and file.
     *
     * @param entity
     *            response entity
     * @param fileName
     *            name of the file
     * @return {@code Response} with gzip content encoding
     */
    public static Response getGZipResponse(Object entity, String fileName) {
        Response response = getFileResponse(entity, fileName, MediaType.APPLICATION_OCTET_STREAM);
        response.getHeaders().add("Content-Encoding", "gzip");
        return response;
    }
}
