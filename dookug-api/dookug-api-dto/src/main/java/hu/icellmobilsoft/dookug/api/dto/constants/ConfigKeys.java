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
     * Interface-hez szükséges konfigurációs kulcsok és default értékek
     *
     */
    interface Interface {

        /**
         * {@code #DOOKUG_SERVICE_INTERFACE_PARAMETERSDATA_GZIPPED} default value
         */
        @ConfigDoc(exclude = true)
        String DOOKUG_SERVICE_INTERFACE_PARAMETERSDATA_GZIPPED_DEFAULT = "false";

        /**
         * *Interface konfiguráció*
         *
         * Logikai congfig kulcs.
         *
         * Requestben bejövő "parametersData" mezőt gzip-el tömörítve várja a modul.
         */
        @ConfigDoc(since = "0.1.0", defaultValue = DOOKUG_SERVICE_INTERFACE_PARAMETERSDATA_GZIPPED_DEFAULT)
        String DOOKUG_SERVICE_INTERFACE_PARAMETERSDATA_GZIPPED = "dookug.service.interface.parametersdata.gzipped";
    }

    /**
     * Klienshez szükséges konfigurációs kulcsok
     *
     */
    interface Client {

        /**
         * *Kliens konfiguráció*
         *
         * A DookuG klienst használó alkalmazásban kell beállítani:
         *
         * - a szerver REST url-jét (pl: dookug.client.document/mp-rest/url: http://localhost:8082)
         *
         * - opcionálisan a REST connectTimeout-ot (pl: dookug.client.document/mp-rest/connectTimeout: 5000)
         *
         * - opcionálisan a REST readTimeout-ot: (pl: dookug.client.document/mp-rest/readTimeout: 60000)
         */
        @ConfigDoc(since = "0.1.0", defaultValue = "-")
        String DOOKUG_CLIENT_DOCUMENT = "dookug.client.document";
    }

    /**
     * Handlebars-hoz szükséges konfigurációs kulcsok és default értékek
     *
     */
    interface Handlebars {

        /**
         * Helperekhez szükséges konfigurációs kulcsok és default értékek
         *
         */
        interface Helper {

            /**
             * {@code #DOOKUG_SERVICE_ENGINE_HANDLEBARS_HELPER_JAVASCRIPT_DIRECTORY} default value
             */
            @ConfigDoc(exclude = true)
            String DEFAULT_DOOKUG_SERVICE_ENGINE_HANDLEBARS_HELPER_JAVASCRIPT_DIRECTORY = "/home/icellmobilsoft/handlebars/helper/js";

            /**
             * *Handlebars konfiguráció*
             *
             * Handlebars handlereket tartalmazó javascript fájlok docker conteiner-be elhelyezett könyvtárának elérési útvonalát tárolja.
             */
            @ConfigDoc(since = "0.1.0", defaultValue = DEFAULT_DOOKUG_SERVICE_ENGINE_HANDLEBARS_HELPER_JAVASCRIPT_DIRECTORY)
            String DOOKUG_SERVICE_ENGINE_HANDLEBARS_HELPER_JAVASCRIPT_DIRECTORY = "dookug.service.engine.handlebars.helper.javascript.directory";

        }

        /**
         * String escape stratégiákhoz szükséges konfigurációs kulcsok és default értékek
         *
         */
        interface EscapingStrategy {
            /**
             * *Handlebars konfiguráció*
             *
             * `com.github.jknack.handlebars.EscapingStrategy` kulcsot tároló config.
             */
            @ConfigDoc(since = "0.1.0",
                    defaultValue = "A Handlebars engine-ben default a HTML lesz a stratégia, ha evvel a kulccsal nincs érték configolva.")
            String DOOKUG_SERVICE_ENGINE_HANDLEBARS_ESCAPINGSTRATEGY = "dookug.service.engine.handlebars.escapingstrategy";
        }
    }

    /**
     * Saxon-hoz szükséges konfigurációs kulcsok és default értékek
     *
     */
    interface Saxon {

        /**
         * {@code #DOOKUG_SERVICE_ENGINE_SAXON_FOPCONFIG} default value
         */
        @ConfigDoc(exclude = true)
        String DEFAULT_DOOKUG_SERVICE_ENGINE_SAXON_FOPCONFIG = "/home/icellmobilsoft/fop-config/fop-config.xml";

        /**
         * *Saxon konfiguráció*
         *
         * Fop config file elérési útvonala a container-en belül
         */
        @ConfigDoc(since = "0.1.0", defaultValue = DEFAULT_DOOKUG_SERVICE_ENGINE_SAXON_FOPCONFIG)
        String DOOKUG_SERVICE_ENGINE_SAXON_FOPCONFIG = "dookug.service.engine.saxon.fopconfig";

        /**
         * {@code #DOOKUG_SERVICE_ENGINE_SAXON_XSLT_LANGUAGE_VARIABLE} default value
         */
        @ConfigDoc(exclude = true)
        String DEFAULT_DOOKUG_SERVICE_ENGINE_SAXON_XSLT_LANGUAGE_VARIABLE = "lang";

        /**
         * *Saxon konfiguráció*
         *
         * XSLT template-ben a nyelvi változó neve
         */
        @ConfigDoc(since = "0.1.0", defaultValue = DEFAULT_DOOKUG_SERVICE_ENGINE_SAXON_XSLT_LANGUAGE_VARIABLE)
        String DOOKUG_SERVICE_ENGINE_SAXON_XSLT_LANGUAGE_VARIABLE = "dookug.service.engine.saxon.xslt.language.variable";

        /**
         * {@code #DOOKUG_SERVICE_ENGINE_SAXON_XSLT_LANGUAGE_DEFAULT} default value
         */
        @ConfigDoc(exclude = true)
        String DEFAULT_DOOKUG_SERVICE_ENGINE_SAXON_XSLT_LANGUAGE_DEFAULT = "HU";

        /**
         * *Saxon konfiguráció*
         *
         * Alapértelmezett nyelv
         */
        @ConfigDoc(since = "0.1.0", defaultValue = DEFAULT_DOOKUG_SERVICE_ENGINE_SAXON_XSLT_LANGUAGE_DEFAULT)
        String DOOKUG_SERVICE_ENGINE_SAXON_XSLT_LANGUAGE_DEFAULT = "dookug.service.engine.saxon.xslt.language.default";
    }

    interface Cache {
        /**
         * Default cache TTL in minutes
         */
        @ConfigDoc(exclude = true)
        int DEFAULT_DOOKUG_SERVICE_CACHE_TEMPLATE_TTL_IN_MINUTES = 60;

        /**
         * Define to make metrics
         */
        @ConfigDoc(since = "0.5.0", description = "Generálódjanak metrikák a Template cache-hez köthetően. Alapértelmezetten nem generál")
        String DOOKUG_SERVICE_CACHE_TEMPLATE_ENABLESTATISTIC = "dookug.service.cache.template.enablestatistic";
    }

}
