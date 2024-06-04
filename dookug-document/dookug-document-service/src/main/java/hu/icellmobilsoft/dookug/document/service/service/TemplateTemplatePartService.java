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
package hu.icellmobilsoft.dookug.document.service.service;

import java.util.List;

import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;

import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.jpa.service.BaseService;
import hu.icellmobilsoft.dookug.common.model.template.Template;
import hu.icellmobilsoft.dookug.common.model.template.TemplateTemplatePart;
import hu.icellmobilsoft.dookug.document.service.repository.TemplateTemplatePartRepository;

/**
 * {@link TemplateTemplatePart} database operations
 *
 * @author laszlo.padar
 * @since 0.1.0
 */
@Model
public class TemplateTemplatePartService extends BaseService<TemplateTemplatePart> {

    @Inject
    private TemplateTemplatePartRepository templateTemplatePartRepository;

    /**
     * Find all {@link TemplateTemplatePart} by id of {@link Template}
     * 
     * @param templateId
     *            ID of {@link Template}
     * @return {@link TemplateTemplatePart} list
     * @throws BaseException
     *             on error
     */
    public List<TemplateTemplatePart> findAllByTemplateId(String templateId) throws BaseException {
        return wrap(templateTemplatePartRepository::findAllByTemplateId, templateId, "findAllByTemplateId", "templateId");
    }

}
