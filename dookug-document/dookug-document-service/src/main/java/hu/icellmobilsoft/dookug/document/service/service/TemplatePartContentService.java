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
import hu.icellmobilsoft.coffee.cdi.trace.annotation.Traced;
import hu.icellmobilsoft.coffee.cdi.trace.constants.SpanAttribute;
import hu.icellmobilsoft.coffee.jpa.service.BaseService;
import hu.icellmobilsoft.dookug.common.model.template.TemplatePart;
import hu.icellmobilsoft.dookug.common.model.template.TemplatePartContent;
import hu.icellmobilsoft.dookug.document.service.repository.TemplatePartContentRepository;

/**
 * {@link TemplatePartContent} database operations
 *
 * @author laszlo.padar
 * @since 0.1.0
 */
@Model
public class TemplatePartContentService extends BaseService<TemplatePartContent> {

    @Inject
    private TemplatePartContentRepository templatePartContentRepository;

    /**
     * Find all {@link TemplatePartContent} by {@link TemplatePart} ids
     * 
     * @param templatePartIds
     *            ID list of {@link TemplatePart}
     * @return {@link TemplatePartContent} list
     * @throws BaseException
     *             on error
     */
    @Traced(component = SpanAttribute.Database.COMPONENT, kind = SpanAttribute.Database.KIND, dbType = SpanAttribute.Database.DB_TYPE)
    public List<TemplatePartContent> findAllByTemplatePartList(List<String> templatePartIds) throws BaseException {
        return wrap(templatePartContentRepository::findAllByTemplatePartList, templatePartIds, "findAllByTemplatePartList", "templatePartIds");
    }

}
