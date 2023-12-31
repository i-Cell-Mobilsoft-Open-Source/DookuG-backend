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
package hu.icellmobilsoft.dookug.api.url;

import hu.icellmobilsoft.coffee.dto.url.BaseServicePath;

/**
 * Document generation service REST url path storage
 * 
 * @author laszlo.padar
 * @since 0.1.0
 */
public class DocumentGeneratePath extends BaseServicePath implements IServicePath {

    /**
     * {@value #DOCUMENT}
     */
    public static final String DOCUMENT = "/dookug/document";
    /**
     * {@value #GENERATE}
     */
    public static final String GENERATE = "/generate";
    /**
     * {@value #CONTENT}
     */
    public static final String CONTENT = "/content";
    /**
     * {@value #INLINE}
     */
    public static final String INLINE = "/inline";
    /**
     * {@value #STOREDTEMPLATE}
     */
    public static final String STOREDTEMPLATE = "/storedTemplate";
    /**
     * {@value IServicePath#PREFIX_INTERNAL} + {@value #DOCUMENT}
     */
    public static final String INTERNAL_DOCUMENT = PREFIX_INTERNAL + DOCUMENT;
    /**
     * {@value INTERNAL_DOCUMENT} + {@value #GENERATE}
     */
    public static final String INTERNAL_DOCUMENT_GENERATE = INTERNAL_DOCUMENT + GENERATE;
    /**
     * {@value #INTERNAL_DOCUMENT_GENERATE} + {@value #INLINE}
     */
    public static final String INTERNAL_DOCUMENT_GENERATE_INLINE = INTERNAL_DOCUMENT_GENERATE + INLINE;
    /**
     * {@value #INTERNAL_DOCUMENT_GENERATE} + {@value #STOREDTEMPLATE}
     */
    public static final String INTERNAL_DOCUMENT_GENERATE_STOREDTEMPLATE = INTERNAL_DOCUMENT_GENERATE + STOREDTEMPLATE;
    /**
     * {@value #INTERNAL_DOCUMENT} + {@value #STOREDTEMPLATE}
     */
    public static final String INTERNAL_DOCUMENT_STOREDTEMPLATE = INTERNAL_DOCUMENT + STOREDTEMPLATE;
    /**
     * {@value #INTERNAL_DOCUMENT} + {@value #CONTENT}
     */
    public static final String INTERNAL_DOCUMENT_CONTENT = INTERNAL_DOCUMENT + CONTENT;
    /**
     * {@value #MULTIPART}
     */
    public static final String MULTIPART = "/multipart";

    /**
     * {@value #METADATA}
     */
    public static final String METADATA = "/metadata";

    /**
     * {@value #METADATA} + {@value IServicePath#QUERY}
     */
    public static final String METADATA_QUERY = METADATA + IServicePath.QUERY;

    /**
     * {@value #METADATA}
     */
    public static final String MULTIPART_METADATA = MULTIPART + METADATA;
}
