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
package hu.icellmobilsoft.dookug.ts.common.config;

/**
 * Testsuite configuration konstant by "/dookug-backend/etc/config/testsuite/META-INF/roaster-defaults.yml"
 * 
 * @author imre.scheffer
 * @since 0.1.0
 *
 */
public interface TsConfigKey {

    /**
     * {@value #DOOKUG_SERVICE_DOCUMENT_BASE_URI}
     */
    String DOOKUG_SERVICE_DOCUMENT_BASE_URI = "dookug.service.document.base.uri";

    /**
     * {@value #DOOKUG_WRITE_DOCUMENT}
     */
    String DOOKUG_WRITE_DOCUMENT = "dookug.write.document";
}
