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
package hu.icellmobilsoft.dookug.engine.pdfbox.config;

/**
 * A Pdf engine-ben használt konfigurációs kulcsok
 * 
 * @author tamas.cserhati
 * @since 0.0.1
 */
public interface ConfigKeys {

    /**
     * A Pdf aláíráshoz szükséges konfigurációs kulcsok és default értékek
     *
     */
    public interface PdfDefaultSignature {
        /**
         * signature profile lekérdezéséhez tartozó (root) kulcs, ahol az első paraméter a profile neve, a második a lekérdezni kívánt kulcs:
         * {@value #DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_SIGNATURE_PROFILE_0_1}
         */
        static final String DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_SIGNATURE_PROFILE_0_1 = "dookug.service.engine.pdf.digitalsign.signature.{0}.{1}";

        /**
         * default signature name configuration key of signature profile {@value #NAME}
         */
        static final String NAME = "name";

        /**
         * default signature reason configuration key of signature profile {@value #REASON}
         */
        static final String REASON = "reason";

        /**
         * keystore configuration key of signature profile {@value #KEYSTORE}
         */
        static final String KEYSTORE = "keystore";

        /**
         * keystore password configuration key of signature profile {@value #KEYSTORE_PASSWORD}
         */
        static final String KEYSTORE_PASSWORD = "keystorePass";

        /**
         * keystore type configuration key of signature profile {@value #KEYSTORE_TYPE}
         */
        static final String KEYSTORE_TYPE = "keystoreType";

        /**
         * key alias configuration key of signature profile {@value #KEY_ALIAS}
         */
        static final String KEY_ALIAS = "keyAlias";

    }
}
