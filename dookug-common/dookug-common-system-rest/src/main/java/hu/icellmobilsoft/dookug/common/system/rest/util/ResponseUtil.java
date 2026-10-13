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

import org.apache.commons.lang3.BooleanUtils;

import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.tool.utils.compress.GZIPUtil;
import hu.icellmobilsoft.dookug.common.cdi.document.Document;

/**
 * Response util
 *
 * @author mate.biro
 * @since 2.1.0
 */
public final class ResponseUtil extends hu.icellmobilsoft.coffee.rest.utils.ResponseUtil {

    private static final String APPLICATION_GZIP_TYPE = "application/gzip";
    private static final String FILE_EXTENSION_GZ = ".gz";

    private ResponseUtil() {
    }

    /**
     * Creates file response from the given document. If responseContentGzipped is true, the content will be GZIP compressed.
     *
     * @param document
     *            document
     * @param responseContentGzipped
     *            if true, the response content will be GZIP compressed
     * @return file response
     * @throws BaseException
     *             in case of error
     */
    public static Response getFileResponse(Document document, Boolean responseContentGzipped) throws BaseException {
        boolean gzip = BooleanUtils.isTrue(responseContentGzipped);
        byte[] payload = gzip ? GZIPUtil.compress(document.getContent()) : document.getContent();
        String filename = gzip ? document.getFilename() + FILE_EXTENSION_GZ : document.getFilename();
        String mediaType = gzip ? APPLICATION_GZIP_TYPE : MediaType.APPLICATION_OCTET_STREAM;
        return getFileResponse(payload, filename, mediaType);
    }
}
