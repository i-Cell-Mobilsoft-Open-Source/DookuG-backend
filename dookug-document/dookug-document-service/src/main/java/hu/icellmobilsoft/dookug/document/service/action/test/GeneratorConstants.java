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

import java.util.List;

/**
 * Constants for document generation tests
 *
 * @author mate.biro
 * @since 2.2.0
 */
public interface GeneratorConstants {

    /**
     * Template file extensions
     */
    String EXTENSION_JSON = "json";

    /**
     * XML-based template extensions
     */
    String EXTENSION_XML = "xml";

    /**
     * XSLT template extension
     */
    String EXTENSION_XSLT = "xslt";

    /**
     * HTML template extension
     */
    String EXTENSION_HTML = "html";

    /**
     * Plain text template extension
     */
    String EXTENSION_TXT = "txt";

    /**
     * List of accepted template file extensions
     */
    List<String> ACCEPTED_TEMPLATE_EXTENSIONS = List.of(EXTENSION_TXT, EXTENSION_HTML, EXTENSION_XSLT);

    /**
     * Form data field names
     */
    String FORM_DATA_NAME_TEMPLATE = "TEMPLATE";

    /**
     * Form data field name for engine parameters (e.g., JSON file with parameters for the template engine)
     */
    String FORM_DATA_NAME_PARAMETERS_TEMPLATE_ENGINE = "PARAMETERS_TEMPLATE_ENGINE";

    /**
     * Form data field name for parameters for the document generation engine (e.g., JSON file with parameters for the document generation engine)
     */
    String FORM_DATA_NAME_PARAMETERS_GENERATOR_ENGINE = "PARAMETERS_GENERATOR_ENGINE";

    /**
     * Form data field name for sub-templates (e.g., additional templates that can be used within the main template)
     */
    String FORM_DATA_NAME_SUBTEMPLATE = "SUBTEMPLATE";

    /**
     * Form data field name for template language (e.g., the template engine to use, such as "freemarker" or "velocity")
     */
    String FORM_DATA_NAME_TEMPLATE_LANGUAGE = "TEMPLATE_LANGUAGE";

    /**
     * Form data field name for response content Gzipped option (If true, the response content will be GZIP compressed)
     */
    String FORM_DATA_NAME_RESPONSE_CONTENT_GZIPPED = "RESPONSE_CONTENT_GZIPPED";
}
