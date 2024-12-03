/*-
 * #%L
 * DookuG
 * %%
 * Copyright (C) 2023 - 2024 i-Cell Mobilsoft Zrt.
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
package hu.icellmobilsoft.dookug.engine.pdfbox.signing.types;

/**
 * Page type for pdf documents
 * 
 * @author tamas.cserhati
 * @since 1.1.0
 */
public enum ShowOnPageType {

    /**
     * 
     */
    FIRST_PAGE(1),
    /**
     * 
     */
    LAST_PAGE(-1),
    /**
     * 
     */
    DISABLED(0);

    int pageValue;

    ShowOnPageType(int pageValue) {
        this.pageValue = pageValue;
    }

    /**
     * get page
     * 
     * @return the page as int
     */
    public int getPageValue() {
        return this.pageValue;
    }
}