/*-
 * #%L
 * DookuG
 * %%
 * Copyright (C) 2023 - 2026 i-Cell Mobilsoft Zrt.
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
package hu.icellmobilsoft.dookug.document.service.action.test;

import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;

/**
 * Helper for file name extraction from multipart form data
 *
 * @author mate.biro
 * @since 2.2.0
 */
@ApplicationScoped
public class FileNameHelper {

    private static final Pattern FILENAME_PATTERN = Pattern.compile("filename=\"?([^\";]+)\"?");

    /**
     * Extract file name from multipart form data
     *
     * @param part
     *            InputPart
     * @return Optional of file name
     */
    protected Optional<String> getFileName(InputPart part) {
        if (part == null) {
            return Optional.empty();
        }
        MultivaluedMap<String, String> headers = part.getHeaders();
        if (headers == null) {
            return Optional.empty();
        }
        String contentDisposition = headers.getFirst("Content-Disposition");
        if (StringUtils.isBlank(contentDisposition)) {
            return Optional.empty();
        }
        Matcher matcher = FILENAME_PATTERN.matcher(contentDisposition);
        if (matcher.find()) {
            return Optional.ofNullable(matcher.group(1));
        }
        return Optional.empty();
    }

    /**
     * Extract file extension from file name
     *
     * @param filename
     *            String
     * @return Optional of file extension
     */
    protected Optional<String> getExtension(String filename) {
        if (StringUtils.isBlank(filename)) {
            return Optional.empty();
        }
        int idx = filename.lastIndexOf('.');
        if (idx < 0 || idx == filename.length() - 1) {
            return Optional.empty();
        }
        return Optional.of(filename.substring(idx + 1).toLowerCase(Locale.ROOT));
    }

    /**
     * Extract template name from file name
     *
     * @param filename
     *            String
     * @return template name
     */
    protected String toTemplateName(String filename) {

        int dot = filename.lastIndexOf('.');

        if (dot > 0) {
            filename = filename.substring(0, dot);
        }

        return filename.trim();
    }
}
