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

/**
 * Document generation service REST url path storage
 * 
 * @author laszlo.padar
 * @since 0.1.0
 */
public class DocumentGeneratePath extends ServicePath {

    /**
     * {@value #DOCUMENT}
     */
    public static final String DOCUMENT = "/dookug/document";
    /**
     * {@value #GENERATE}
     */
    public static final String GENERATE = "/generate";
    /**
     * {@value #SIGN}
     */
    public static final String SIGN = "/sign";
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
     * {@value #INTERNAL_DOCUMENT}
     */
    public static final String INTERNAL_DOCUMENT = INTERNAL + DOCUMENT;
    /**
     * {@value INTERNAL_DOCUMENT_GENERATE}
     */
    public static final String INTERNAL_DOCUMENT_GENERATE = INTERNAL_DOCUMENT + GENERATE;
    /**
     * {@value INTERNAL_DOCUMENT_SIGN}
     */
    public static final String INTERNAL_DOCUMENT_SIGN = INTERNAL_DOCUMENT + SIGN;
    /**
     * {@value #INTERNAL_DOCUMENT_GENERATE_INLINE}
     */
    public static final String INTERNAL_DOCUMENT_GENERATE_INLINE = INTERNAL_DOCUMENT_GENERATE + INLINE;
    /**
     * {@value #INTERNAL_DOCUMENT_SIGN_INLINE}
     */
    public static final String INTERNAL_DOCUMENT_SIGN_INLINE = INTERNAL_DOCUMENT_SIGN + INLINE;
    /**
     * {@value #INTERNAL_DOCUMENT_GENERATE_STOREDTEMPLATE}
     */
    public static final String INTERNAL_DOCUMENT_GENERATE_STOREDTEMPLATE = INTERNAL_DOCUMENT_GENERATE + STOREDTEMPLATE;
    /**
     * {@value #INTERNAL_DOCUMENT_STOREDTEMPLATE}
     */
    public static final String INTERNAL_DOCUMENT_STOREDTEMPLATE = INTERNAL_DOCUMENT + STOREDTEMPLATE;
    /**
     * {@value #INTERNAL_DOCUMENT_CONTENT}
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
     * {@value #METADATA_QUERY}
     */
    public static final String METADATA_QUERY = METADATA + QUERY;

    /**
     * {@value #MULTIPART_METADATA}
     */
    public static final String MULTIPART_METADATA = MULTIPART + METADATA;

    /**
     * Rest path common constant for META-INF/MANIFET.MF file content in response
     */
    public static final String VERSION_INFO = "/versionInfo";

}
