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
package hu.icellmobilsoft.dookug.common.util.filename;

import java.time.OffsetDateTime;

import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.ResponseFormatType;

/**
 * File util
 * 
 * @author szabolcs.gemesi
 * @since 0.0.1
 */
public class FileUtil {

    /**
     * Filename separator {@value #FILENAME_PART_SEPARATOR}
     */
    public static final String FILENAME_PART_SEPARATOR = "_";

    /**
     * Creates filename by the given parameters
     * 
     * @param documentId
     *            document identifier
     * @param templateName
     *            template name
     * @param format
     *            document format
     * @param creationData
     *            document creation date
     * @return filename string
     * @throws BaseException
     *             if error
     */
    public static String createFilename(String documentId, String templateName, ResponseFormatType format, OffsetDateTime creationData)
            throws BaseException {
        return createFilename(documentId, templateName, getFileExtension(format), creationData);
    }

    /**
     * Creates filename by the given parameters
     *
     * @param documentId
     *            document identifier
     * @param templateName
     *            template name
     * @param extension
     *            document extension
     * @param creationData
     *            document creation date
     * @return filename string
     * @throws BaseException
     *             if error
     */
    public static String createFilename(String documentId, String templateName, String extension, OffsetDateTime creationData) throws BaseException {
        if (creationData == null) {
            throw new BaseException(CoffeeFaultType.INVALID_INPUT, "Creation date must be not null!");
        }
        String dateString = String.valueOf(creationData.toInstant().toEpochMilli());
        String filename = StringUtils.EMPTY;
        if (StringUtils.isNotBlank(documentId)) {
            filename = filename.concat(documentId).concat(FILENAME_PART_SEPARATOR);
        }
        if (StringUtils.isNotBlank(templateName)) {
            filename = filename.concat(templateName).concat(FILENAME_PART_SEPARATOR);
        }
        if (extension == null) {
            extension = StringUtils.EMPTY;
        }
        return StringUtils.join(filename, dateString, extension);
    }

    /**
     * Returns the extension by the given response format
     * 
     * @param responseFormat
     *            {@link ResponseFormatType}
     * @return file extension
     */
    public static String getFileExtension(ResponseFormatType responseFormat) {
        if (responseFormat == null) {
            return "";
        }
        switch (responseFormat) {
        case PDF:
            return ".pdf";
        default:
            return "";
        }
    }
}
