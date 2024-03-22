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
        String DOCUMENT_GENERATE = "Dokumentumgenerálási műveletek";

        /**
         * {@value #DOCUMENT_CONTENT}
         */
        String DOCUMENT_CONTENT = "Generált dokumentum lekérdezése";

        /**
         * {@value #STORED_TEMPLATE}
         */
        String STORED_TEMPLATE = "Tárolt template műveletek";
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
        String DOCUMENT_GENERATE = "Dokumentum generálásával kapcsolatos REST operációk request-ben kapott beállítások és paraméterek, "
                + "valamint request-ben kapott vagy modulban tárolt template alapján.";

        /**
         * {@value #DOCUMENT_CONTENT}
         */
        String DOCUMENT_CONTENT = "Modul adatbázisában tárolt generált dokumentum lekérdezése.";

        /**
         * {@value #STORED_TEMPLATE}
         */
        String STORED_TEMPLATE = "Modulban tárolt template-tel kapcsolatos REST operációk.";
    }
}
