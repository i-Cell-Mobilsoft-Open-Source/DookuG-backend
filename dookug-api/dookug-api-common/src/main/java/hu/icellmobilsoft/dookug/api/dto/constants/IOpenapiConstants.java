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

        /**
         * {@value #DEV_TOOLS}
         */
        String DEV_TOOLS = "WebAppDevTools";
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

        /**
         * {@value #DEV_TOOLS}
         */
        String DEV_TOOLS = "Internal endpoints supporting the web application. Enables template upload and document " +
                "generation testing before committing templates to the service database. " +
                "Not intended for external or production use.";
    }

    /**
     * OpenAPI "Operation" constants
     */
    interface Operation {

        /**
         * OpenAPI "Summary" constants
         */
        interface Summary {

            /**
             * {@value #TEST_DOCUMENT_GENERATE_INLINE}
             */
            String TEST_DOCUMENT_GENERATE_INLINE = "Generates document based on the template sent in a multipart request, and returns it.";
        }

        /**
         * OpenAPI "Description" constants
         */
        interface Description {

            /**
             * {@value #TEST_DOCUMENT_GENERATE_INLINE}
             */
            String TEST_DOCUMENT_GENERATE_INLINE = "The request must include the data related to the template and the document generation process:\n\n"
                    +
                    "* Multiple hierarchically ordered templates can be processed using the Handlebars template engine.\n" +
                    "* Template parameters are received as a JSON file\n" +
                    "* SAXON generator parameters are received as an XML file\n" +
                    "* PDF files are generated without an electronic signature.\n\n" +
                    "The multipart request must contain the following parts:\n\n" +
                    "* `TEMPLATE`: the main template file (required).\n" +
                    "  * Accepted extensions: `.txt`, `.html`, `.xslt`\n" +
                    "  * The input part's Content-Disposition header should contain the filename with the extension. " +
                    "Validation and the response filename is based on this.\n" +
                    "* `SUBTEMPLATE`: partial template files (optional, multiple parts allowed).\n" +
                    "  * Accepted extensions: `.txt`, `.html`, `.xslt` (should be the same as the TEMPLATE's)\n" +
                    "  * Each input part's Content-Disposition header should contain the filename with the extension. " +
                    "Validation is based on this and should match the partial name in the template case sensitively.\n" +
                    "* `TEMPLATE_LANGUAGE`: Required only if the TEMPLATE file extension is `.xslt`\n" +
                    "* `PARAMETERS_TEMPLATE_ENGINE`: `.json` file containing the template engine parameters (optional)\n" +
                    "  * The input part's Content-Disposition header should contain the filename with the extension. " +
                    "Validation is based on this.\n" +
                    "* `PARAMETERS_GENERATOR_ENGINE`: `.xml` file containing the generator parameters (optional)\n" +
                    "  * The input part's Content-Disposition header should contain the filename with the extension. " +
                    "Validation is based on this.";
        }
    }
}
