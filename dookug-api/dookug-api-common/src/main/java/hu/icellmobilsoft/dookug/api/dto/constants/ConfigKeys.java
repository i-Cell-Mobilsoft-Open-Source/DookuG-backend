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

import hu.icellmobilsoft.coffee.module.configdoc.ConfigDoc;

/**
 * Application config keys
 *
 * @author mate.biro
 * @since 0.2.0
 */
@ConfigDoc
public interface ConfigKeys {

    /**
     * Configuration keys and default values required for the interface
     *
     */
    interface Interface {

        /**
         * {@code #DOOKUG_SERVICE_INTERFACE_PARAMETERSDATA_GZIPPED} default value
         */
        @ConfigDoc(exclude = true)
        String DOOKUG_SERVICE_INTERFACE_PARAMETERSDATA_GZIPPED_DEFAULT = "true";

        /**
         * *Interface configuration*
         *
         * Logical config key.
         *
         * The module enables the incoming "parametersData" field in the request to be compressed using gzip.
         */
        @ConfigDoc(since = "0.1.0", defaultValue = DOOKUG_SERVICE_INTERFACE_PARAMETERSDATA_GZIPPED_DEFAULT)
        String DOOKUG_SERVICE_INTERFACE_PARAMETERSDATA_GZIPPED = "dookug.service.interface.parametersdata.gzipped";
    }

    /**
     * Configuration keys and default values required for the client
     *
     */
    interface Client {

        /**
         * *Client configuration*
         *
         * In the application using the DookuG client, you need to set:
         *
         * - the server REST URL (e.g., dookug.client.document/mp-rest/url: http://localhost:8082)
         *
         * - optionally the REST connectTimeout (e.g., dookug.client.document/mp-rest/connectTimeout: 5000)
         *
         * - optionally the REST readTimeout (e.g., dookug.client.document/mp-rest/readTimeout: 60000)
         */
        @ConfigDoc(since = "0.1.0", defaultValue = "-")
        String DOOKUG_CLIENT_DOCUMENT = "dookug.client.document";
    }

    /**
     * Configuration keys and default values required for Handlebars
     *
     */
    interface Handlebars {

        /**
         * Configuration keys and default values required for the helpers
         *
         */
        interface Helper {

            /**
             * {@code #DOOKUG_SERVICE_ENGINE_HANDLEBARS_HELPER_JAVASCRIPT_DIRECTORY} default value
             */
            @ConfigDoc(exclude = true)
            String DEFAULT_DOOKUG_SERVICE_ENGINE_HANDLEBARS_HELPER_JAVASCRIPT_DIRECTORY = "/home/icellmobilsoft/handlebars/helper/js";

            /**
             * *Handlebars configuration*
             *
             * Stores the path to the directory containing JavaScript files with Handlebars handlers in the Docker container.
             */
            @ConfigDoc(since = "0.1.0", defaultValue = DEFAULT_DOOKUG_SERVICE_ENGINE_HANDLEBARS_HELPER_JAVASCRIPT_DIRECTORY)
            String DOOKUG_SERVICE_ENGINE_HANDLEBARS_HELPER_JAVASCRIPT_DIRECTORY = "dookug.service.engine.handlebars.helper.javascript.directory";

        }

        /**
         * Configuration keys and default values required for string escape strategies
         *
         */
        interface EscapingStrategy {
            /**
             * *Handlebars configuration*
             *
             * Configuration storing the `com.github.jknack.handlebars.EscapingStrategy` key.
             */
            @ConfigDoc(since = "0.1.0",
                    defaultValue = "In the Handlebars engine, HTML will be the default strategy if no value is configured with this key.")
            String DOOKUG_SERVICE_ENGINE_HANDLEBARS_ESCAPINGSTRATEGY = "dookug.service.engine.handlebars.escapingstrategy";
        }
    }

    /**
     * Configuration keys and default values required for SAXON engine
     *
     */
    interface Saxon {

        /**
         * {@code #DOOKUG_SERVICE_ENGINE_SAXON_FOPCONFIG} default value
         */
        @ConfigDoc(exclude = true)
        String DEFAULT_DOOKUG_SERVICE_ENGINE_SAXON_FOPCONFIG = "/home/icellmobilsoft/fop-config/fop-config.xml";

        /**
         * *Saxon configuration*
         *
         * Path to the Fop config file within the container
         */
        @ConfigDoc(since = "0.1.0", defaultValue = DEFAULT_DOOKUG_SERVICE_ENGINE_SAXON_FOPCONFIG)
        String DOOKUG_SERVICE_ENGINE_SAXON_FOPCONFIG = "dookug.service.engine.saxon.fopconfig";

        /**
         * {@code #DOOKUG_SERVICE_ENGINE_SAXON_XSLT_LANGUAGE_VARIABLE} default value
         */
        @ConfigDoc(exclude = true)
        String DEFAULT_DOOKUG_SERVICE_ENGINE_SAXON_XSLT_LANGUAGE_VARIABLE = "lang";

        /**
         * *Saxon configuration*
         *
         * The name of the language variable in the XSLT template
         */
        @ConfigDoc(since = "0.1.0", defaultValue = DEFAULT_DOOKUG_SERVICE_ENGINE_SAXON_XSLT_LANGUAGE_VARIABLE)
        String DOOKUG_SERVICE_ENGINE_SAXON_XSLT_LANGUAGE_VARIABLE = "dookug.service.engine.saxon.xslt.language.variable";

        /**
         * {@code #DOOKUG_SERVICE_ENGINE_SAXON_XSLT_LANGUAGE_DEFAULT} default value
         */
        @ConfigDoc(exclude = true)
        String DEFAULT_DOOKUG_SERVICE_ENGINE_SAXON_XSLT_LANGUAGE_DEFAULT = "HU";

        /**
         * *Saxon configuration*
         *
         * Default language
         */
        @ConfigDoc(since = "0.1.0", defaultValue = DEFAULT_DOOKUG_SERVICE_ENGINE_SAXON_XSLT_LANGUAGE_DEFAULT)
        String DOOKUG_SERVICE_ENGINE_SAXON_XSLT_LANGUAGE_DEFAULT = "dookug.service.engine.saxon.xslt.language.default";
    }

    /**
     * Configuration keys and default values required for caching
     *
     */
    interface Cache {

        /**
         * The key
         */
        @ConfigDoc(exclude = true)
        String DOOKUG_SERVICE_CACHE = "dookug.service.cache";

        /**
         * Possible values for cache configuration keys
         */
        interface Keys {

            /**
             * enabled key
             */
            @ConfigDoc(exclude = true)
            String ENABLED = ".enabled";

            /**
             * ttl key
             */
            @ConfigDoc(exclude = true)
            String TTL = ".ttl";

            /**
             * enablestatistic key
             */
            @ConfigDoc(exclude = true)
            String ENABLESTATISTIC = ".enablestatistic";
        }

        /**
         * TemplateCache configuration keys
         */
        interface Template {

            /**
             * The key
             */
            @ConfigDoc(exclude = true)
            String DOOKUG_SERVICE_CACHE_TEMPLATE = DOOKUG_SERVICE_CACHE + ".template";

            /**
             * Default values
             */
            interface Defaults {

                /**
                 * Default cache TTL in minutes
                 */
                @ConfigDoc(exclude = true)
                String TTL_IN_MINUTES = "60";

                /**
                 * Default make metrics value
                 */
                @ConfigDoc(exclude = true)
                String ENABLESTATISTIC = "false";

                /**
                 * Default of enabling caching
                 */
                @ConfigDoc(exclude = true)
                String ENABLED = "false";
            }

            /**
             * Cache TTL in minutes
             */
            @ConfigDoc(since = "0.5.0", defaultValue = Defaults.TTL_IN_MINUTES,
                    description = "How long until the system invalidates the cache content. By default, " + Defaults.TTL_IN_MINUTES + " minutes.")
            String TTL = DOOKUG_SERVICE_CACHE_TEMPLATE + Keys.TTL;

            /**
             * Define to make metrics
             */
            @ConfigDoc(since = "0.5.0",
                    description = "Metrics related to the Template cache should be generated. By default, they are not generated.",
                    defaultValue = Defaults.ENABLESTATISTIC)
            String ENABLESTATISTIC = DOOKUG_SERVICE_CACHE_TEMPLATE + Keys.ENABLESTATISTIC;

            /**
             * Enabling template cache
             */
            @ConfigDoc(since = "0.6.0", description = "Does the module use template caching?", defaultValue = Defaults.ENABLED)
            String ENABLED = DOOKUG_SERVICE_CACHE_TEMPLATE + Keys.ENABLED;

        }

        /**
         * KeystoreCache configuration keys
         */
        interface Keystore {

            /**
             * The key
             */
            @ConfigDoc(exclude = true)
            String DOOKUG_SERVICE_CACHE_KEYSTORE = DOOKUG_SERVICE_CACHE + ".keystore";

            /**
             * Default values
             */
            interface Defaults {

                /**
                 * Default cache TTL in minutes
                 */
                @ConfigDoc(exclude = true)
                String TTL_IN_MINUTES = "1440";

                /**
                 * Default make metrics value
                 */
                @ConfigDoc(exclude = true)
                String ENABLESTATISTIC = "false";

                /**
                 * Default of enabling caching
                 */
                @ConfigDoc(exclude = true)
                String ENABLED = "true";
            }

            /**
             * Keystore cache TTL in minutes
             */
            @ConfigDoc(since = "1.1.0", defaultValue = Defaults.TTL_IN_MINUTES,
                    description = "How long until the system invalidates the cache content. By default, " + Defaults.TTL_IN_MINUTES + " minutes.")
            String TTL = DOOKUG_SERVICE_CACHE_KEYSTORE + Keys.TTL;

            /**
             * Define to make keystore cache metrics
             */
            @ConfigDoc(since = "1.1.0",
                    description = "Metrics related to the Template cache should be generated. By default, they are not generated.",
                    defaultValue = Defaults.ENABLESTATISTIC)
            String ENABLESTATISTIC = DOOKUG_SERVICE_CACHE_KEYSTORE + Keys.ENABLESTATISTIC;

            /**
             * Enabling keystore cache
             */
            @ConfigDoc(since = "1.1.0", description = "Does the module use keystore caching for document signing?", defaultValue = Defaults.ENABLED)
            String ENABLED = DOOKUG_SERVICE_CACHE_KEYSTORE + Keys.ENABLED;
        }

        /**
         * Xslt template configuration keys
         */
        interface Xslt {

            /**
             * The key
             */
            @ConfigDoc(exclude = true)
            String DOOKUG_SERVICE_CACHE_XSLT = DOOKUG_SERVICE_CACHE + ".xslt";

            /**
             * Default values
             */
            interface Defaults {

                /**
                 * Default cache TTL in minutes
                 */
                @ConfigDoc(exclude = true)
                String TTL_IN_MINUTES = "1440";

                /**
                 * Default make metrics value
                 */
                @ConfigDoc(exclude = true)
                String ENABLESTATISTIC = "false";

                /**
                 * Default of enabling caching
                 */
                @ConfigDoc(exclude = true)
                String ENABLED = "true";
            }

            /**
             * Xslt template cache TTL in minutes
             */
            @ConfigDoc(since = "1.3.0", defaultValue = Defaults.TTL_IN_MINUTES,
                    description = "How long until the system invalidates the cache content. By default, " + Defaults.TTL_IN_MINUTES + " minutes.")
            String TTL = DOOKUG_SERVICE_CACHE_XSLT + Keys.TTL;

            /**
             * Define to make xslt cache metrics
             */
            @ConfigDoc(since = "1.3.0",
                    description = "Metrics related to the Template cache should be generated. By default, they are not generated.",
                    defaultValue = Defaults.ENABLESTATISTIC)
            String ENABLESTATISTIC = DOOKUG_SERVICE_CACHE_XSLT + Keys.ENABLESTATISTIC;

            /**
             * Enabling keystore cache
             */
            @ConfigDoc(since = "1.3.0", description = "Does the module use template caching for saxon?", defaultValue = Defaults.ENABLED)
            String ENABLED = DOOKUG_SERVICE_CACHE_XSLT + Keys.ENABLED;
        }

    }

}
