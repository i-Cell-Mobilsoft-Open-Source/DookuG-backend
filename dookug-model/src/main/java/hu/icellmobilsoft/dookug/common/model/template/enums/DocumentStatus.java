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
package hu.icellmobilsoft.dookug.common.model.template.enums;

/**
 * Document status
 * 
 * @author szabolcs.gemesi
 * @since 0.0.1
 */
public enum DocumentStatus {

    /**
     * DONE The content of the document saved successfully
     */
    DONE,
    /**
     * FAILED Failed to save document contents
     */
    FAILED,
    /**
     * PENDING The content of the document has been generated and is waiting to be saved
     */
    PENDING,
    /**
     * SYNCING The content of the document is being saved
     */
    SYNCING,
}
