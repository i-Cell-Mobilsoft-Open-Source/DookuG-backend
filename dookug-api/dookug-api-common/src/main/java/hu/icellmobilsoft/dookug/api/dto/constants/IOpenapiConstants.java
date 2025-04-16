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
package hu.icellmobilsoft.dookug.api.dto.constants;

/**
 * Openapi constants for tags and descriptions
 *
 * @author mate.biro
 * @since 0.1.0
 */
public interface IOpenapiConstants {

    /**
     * OpenAPI "Tag" constants
     * 
     * @author Imre Scheffer
     *
     */
    interface Tag {

        /**
         * {@value #DOCUMENT_GENERATE}
         */
        String DOCUMENT_GENERATE = "Document generation";

        /**
         * {@value #QUERY}
         */
        String QUERY = "Querying generated document";

        /**
         * {@value #DOCUMENT_SIGNING}
         */
        String DOCUMENT_SIGNING = "Document signing";

        /**
         * {@value #MAINTENANCE}
         */
        String MAINTENANCE = "Maintenance";
    }

    /**
     * OpenAPI "Description" constants
     * 
     * @author Imre Scheffer
     *
     */
    interface Description {
        /**
         * {@value #DOCUMENT_GENERATE}
         */
        String DOCUMENT_GENERATE = "Document generation based on a template, using the settings from the requests and the parameters provided for the template.";

        /**
         * {@value #QUERY}
         */
        String QUERY = "Downloading files or querying metadata.";

        /**
         * {@value #DOCUMENT_SIGNING}
         */
        String DOCUMENT_SIGNING = "Electronic signing of the received document.";

        /**
         * {@value #MAINTENANCE}
         */
        String MAINTENANCE = "Clearing internal state.";
    }
}
