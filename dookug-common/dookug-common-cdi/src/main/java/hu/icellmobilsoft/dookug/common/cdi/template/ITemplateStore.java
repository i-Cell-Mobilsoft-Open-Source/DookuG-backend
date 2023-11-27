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
package hu.icellmobilsoft.dookug.common.cdi.template;

import java.time.OffsetDateTime;

import hu.icellmobilsoft.coffee.dto.exception.BaseException;

/**
 * Interface for template storage
 * 
 * @author laszlo.padar
 * @since 0.1.0
 */
public interface ITemplateStore {

    /**
     * Finds templates by name and validity.
     *
     * @param templateName
     *            template name
     * @param templateLanguage
     *            language of template
     * @param validityDate
     *            validity date
     * @throws BaseException
     *             on error
     */
    public void loadTemplatesByNameAndValidity(String templateName, String templateLanguage, OffsetDateTime validityDate) throws BaseException;
}
