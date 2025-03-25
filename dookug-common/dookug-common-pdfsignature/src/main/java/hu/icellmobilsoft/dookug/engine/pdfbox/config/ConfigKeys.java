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

import hu.icellmobilsoft.coffee.module.configdoc.ConfigDoc;

/**
 * Configuration keys used in the Pdf engine
 * 
 * @author tamas.cserhati
 * @since 0.0.1
 */
@ConfigDoc
public interface ConfigKeys {

    /**
     * prefix value {@value #PREFIX_DOOKUG}
     */
    @ConfigDoc(exclude = true)
    String PREFIX_DOOKUG = "dookug.";

    /**
     * Configuration keys and default values for Pdf signature (v1)
     */
    interface PdfSignature {
        /**
         * the (root) key to retrieve the signature profile, where the first parameter is the profile name:
         * {@value #DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_PROFILE_0}
         */
        String DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_PROFILE_0 = PREFIX_DOOKUG + "service.engine.pdf.digitalsign.{0}.";

        /**
         * default signature name configuration key of signature profile {@value #NAME}
         */
        String NAME = DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_PROFILE_0 + "name";

        /**
         * default signature reason configuration key of signature profile {@value #REASON}
         */
        String REASON = DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_PROFILE_0 + "reason";

        /**
         * keystore configuration key of signature profile {@value #KEYSTORE}
         */
        String KEYSTORE = DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_PROFILE_0 + "keystore";

        /**
         * keystore password configuration key of signature profile {@value #KEYSTORE_PASSWORD}
         */
        String KEYSTORE_PASSWORD = DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_PROFILE_0 + "keystorePass";

        /**
         * keystore type configuration key of signature profile {@value #KEYSTORE_TYPE}
         */
        String KEYSTORE_TYPE = DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_PROFILE_0 + "keystoreType";

        /**
         * key alias configuration key of signature profile {@value #KEY_ALIAS}
         */
        String KEY_ALIAS = DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_PROFILE_0 + "keyAlias";

        /**
         * use the eu-dss-sig library for pdf signature {@value #USE_EUDSSSIG}
         */
        String USE_EUDSSSIG = DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_PROFILE_0 + "useEuDssSig";

        /**
         * Configuration keys and default values for DSS esig (pdfsign v2)
         */
        interface DSS {

            /**
             * prefix for dss keys: {@value #DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_PROFILE_0_DSS}
             */
            @ConfigDoc(exclude = true)
            String DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_PROFILE_0_DSS = DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_PROFILE_0 + "dss.";

            /**
             * The digest algorithm {@value #DIGEST_ALGORITHM}
             */
            String DIGEST_ALGORITHM = DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_PROFILE_0_DSS + "digestAlgorithm";

            /**
             * The encryption algorithm {@value #ENCRYPTION_ALGORITHM}
             */
            String ENCRYPTION_ALGORITHM = DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_PROFILE_0_DSS + "encryptionAlgorithm";

            /**
             * Image to be placed in signature block {@value #IMAGE_FILE}
             */
            String IMAGE_FILE = DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_PROFILE_0_DSS + "imageFile";

            /**
             * Page where the signature block should be placed. [-1] for last page, 0: invisible {@value #SHOW_ON_PAGE}
             */
            String SHOW_ON_PAGE = DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_PROFILE_0_DSS + "showOnPage";

            /**
             * Y coordinate of the signature block in cm {@value #POSITION_TOP}
             */
            String POSITION_TOP = DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_PROFILE_0_DSS + "position.top";

            /**
             * X coordinate of the signature block in cm {@value #POSITION_LEFT}
             */
            String POSITION_LEFT = DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_PROFILE_0_DSS + "position.left";

            /**
             * Width of the signature block in cm {@value #WIDTH}
             */
            String WIDTH = DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_PROFILE_0_DSS + "width";

            /**
             * Text to be displayed in hint field {@value #HINT_TEXT}
             */
            String HINT_TEXT = DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_PROFILE_0_DSS + "hintText";

            /**
             * Add hint row in signature table {@value #USE_HINT}
             */
            String USE_HINT = DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_PROFILE_0_DSS + "useHint";

            /**
             * Include signed timestamp in the signature table {@value #USE_TIMESTAMP}
             */
            String USE_TIMESTAMP = DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_PROFILE_0_DSS + "useTimestamp";

            /**
             * Skip timestamp on error (only raise log entry instead of exception) {@value #SKIP_TIMESTAMP_ON_ERROR}
             */
            String SKIP_TIMESTAMP_ON_ERROR = DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_PROFILE_0_DSS + "skipTimestampOnError";

            /**
             * Use PAdES profile with long-term validation material {@value #USE_LT}
             */
            String USE_LT = DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_PROFILE_0_DSS + "useLT";

            /**
             * Use PAdES profile with long term availability and integrity of validation material {@value #USE_LTA}
             */
            String USE_LTA = DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_PROFILE_0_DSS + "useLTA";

            /**
             * Label for the 'hint' row {@value #LABEL_HINT}
             */
            String LABEL_HINT = DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_PROFILE_0_DSS + "labelHint";

            /**
             * Label for the 'timestamp' row {@value #LABEL_TIMESTAMP}
             */
            String LABEL_TIMESTAMP = DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_PROFILE_0_DSS + "labelTimestamp";

            /**
             * Label for the 'signee' row {@value #LABEL_SIGNEE}
             */
            String LABEL_SIGNEE = DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_PROFILE_0_DSS + "labelSignee";

            /**
             * Show 'signee' row {@value #SHOW_SIGNEE}
             */
            String SHOW_SIGNEE = DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_PROFILE_0_DSS + "showSignee";

            /**
             * Use specific time stamping authority as source (if multiple given, will be used in given order as fallback) {@value #TSP_SOURCES}
             */
            String TSP_SOURCES = DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_PROFILE_0_DSS + "tspSources";

            /**
             * Use specific timezone for time info, e.g. Europe/Budapest {@value #TIMEZONE}
             */
            String TIMEZONE = DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_PROFILE_0_DSS + "timezone";

            /**
             * URL list for getting trusted certificates {@value #TRUSTED_CERTIFICATES}
             */
            String TRUSTED_CERTIFICATES = DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_PROFILE_0_DSS + "trustedCertificates";

            /**
             * The certificate permission (Pdf will be either 'certificated' or - without this - 'signed') {@value #CERTIFICATE_PERMISSION}
             */
            String CERTIFICATE_PERMISSION = DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_PROFILE_0_DSS + "certificatePermission";
        }

        /**
         * Configuration keys and default values for PdfBox digital signature
         */
        interface PdfBox {

            /**
             * prefix for pdfbox config keys: {@value #DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_PROFILE_0_PDFBOX}
             */
            @ConfigDoc(exclude = true)
            String DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_PROFILE_0_PDFBOX = DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_PROFILE_0 + "pdfbox.";

            /**
             * The signature algorithm {@value #SIGNATURE_ALGORITHM}
             */
            String SIGNATURE_ALGORITHM = DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_PROFILE_0_PDFBOX + "signatureAlgorithm";
        }

        /**
         * keys for DSS server default values
         */
        interface Default {
            /**
             * Default value for {@value PdfSignature.DSS#SHOW_ON_PAGE}
             */
            @ConfigDoc(exclude = true)
            String PAGE_DEFAULT_VALUE = "-1";

            /**
             * Default value for {@value PdfSignature.DSS#POSITION_TOP}
             */
            @ConfigDoc(exclude = true)
            String TOP_DEFAULT_VALUE = "1.0";

            /**
             * Default value for {@value PdfSignature.DSS#POSITION_LEFT}
             */
            @ConfigDoc(exclude = true)
            String LEFT_DEFAULT_VALUE = "1.0";

            /**
             * Default value for {@value PdfSignature.DSS#WIDTH}
             */
            @ConfigDoc(exclude = true)
            String WIDTH_DEFAULT_VALUE = "5.0";

            /**
             * Default value for {@value PdfSignature.DSS#HINT_TEXT}
             */
            @ConfigDoc(exclude = true)
            String HINT_TEXT_DEFAULT_VALUE = "Signed by {0}";

            /**
             * Default value for {@value PdfSignature.DSS#USE_HINT}
             */
            @ConfigDoc(exclude = true)
            String USE_HINT_DEFAULT_VALUE = "false";

            /**
             * Default value for {@value PdfSignature.DSS#USE_TIMESTAMP}
             */
            @ConfigDoc(exclude = true)
            String USE_TIMESTAMP_DEFAULT_VALUE = "false";

            /**
             * Default value for {@value PdfSignature.DSS#SKIP_TIMESTAMP_ON_ERROR}
             */
            @ConfigDoc(exclude = true)
            String SKIP_TIMESTAMP_DEFAULT_VALUE = "true";

            /**
             * Default value for {@value PdfSignature.DSS#USE_LT}
             */
            @ConfigDoc(exclude = true)
            String USE_LT_DEFAULT_VALUE = "false";

            /**
             * Default value for {@value PdfSignature.DSS#USE_LTA}
             */
            @ConfigDoc(exclude = true)
            String USE_LTA_DEFAULT_VALUE = "false";

            /**
             * Default value for {@value PdfSignature.DSS#SHOW_SIGNEE}
             */
            @ConfigDoc(exclude = true)
            String SHOW_SIGNEE_DEFAULT_VALUE = "false";

            /**
             * Default value for {@value PdfSignature.DSS#LABEL_HINT}
             */
            @ConfigDoc(exclude = true)
            String LABEL_HINT_DEFAULT_VALUE = "Hint";

            /**
             * Default value for {@value PdfSignature.DSS#LABEL_TIMESTAMP}
             */
            @ConfigDoc(exclude = true)
            String LABEL_TIMESTAMP_DEFAULT_VALUE = "Timestamp";

            /**
             * Default value for {@value PdfSignature.DSS#LABEL_SIGNEE}
             */
            @ConfigDoc(exclude = true)
            String LABEL_SIGNEE_DEFAULT_VALUE = "Signee";

            /**
             * Default value for {@value PdfSignature.DSS#TSP_SOURCES}
             */
            @ConfigDoc(exclude = true)
            String TSP_SOURCES_DEFAULT_VALUE = "";

            /**
             * Default value for {@value PdfSignature.DSS#TRUSTED_CERTIFICATES}
             */
            @ConfigDoc(exclude = true)
            String TRUSTED_CERTIFICATES_DEFAULT_VALUE = "";

            /**
             * The DEFAULT private key alias in the pkcs12 keystore: {@value #DEFAULT_KEYSTORE_PRIVATE_KEY_ALIAS}
             */
            @ConfigDoc(exclude = true)
            String DEFAULT_KEYSTORE_PRIVATE_KEY_ALIAS = "alias";

            /**
             * Default value for {@value PdfSignature.DSS#DIGEST_ALGORITHM}
             */
            @ConfigDoc(exclude = true)
            String DIGEST_ALGORITHM_DEFAULT_VALUE = "SHA-256";

            /**
             * Default value for {@value PdfSignature.DSS#ENCRYPTION_ALGORITHM}
             */
            @ConfigDoc(exclude = true)
            String ENCRYPTION_ALGORITHM_DEFAULT_VALUE = "RSA";

            /**
             * Default value for {@value PdfSignature.PdfBox#SIGNATURE_ALGORITHM}
             */
            @ConfigDoc(exclude = true)
            String PDFBOX_DEFAULT_SIGNATURE_ALGORITHM = "SHA256WithRSA";
        }

    }

}
