/*-
 * #%L
 * DookuG
 * %%
 * Copyright (C) 2023 - 2025 i-Cell Mobilsoft Zrt.
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
package hu.icellmobilsoft.dookug.document.service.action.test;

import java.util.List;

import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;

import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.dookug.common.model.template.Template;
import hu.icellmobilsoft.dookug.common.system.rest.action.BaseAction;
import hu.icellmobilsoft.dookug.document.service.converter.test.TemplateQueryHelper;
import hu.icellmobilsoft.dookug.document.service.converter.test.TemplateQueryResponseHelper;
import hu.icellmobilsoft.dookug.document.service.service.test.TemplateQueryService;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplateQueryOrderType;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplateQueryParams;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplateQueryResponse;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplateType;

/**
 * Stored template action
 *
 * @author mate.biro
 * @since 2.2.0
 */
@Model
public class StoredTemplateAction extends BaseAction {

    @Inject
    private TemplateQueryService templateQueryService;

    @Inject
    private TemplateQueryHelper templateQueryHelper;

    @Inject
    private TemplateQueryResponseHelper templateQueryResponseHelper;

    /**
     * Template query, can be filtered and paginated
     * 
     * @param name
     *            template name filter
     * @param language
     *            template language filter
     * @param validityStart
     *            validity start filter
     * @param validityEnd
     *            validity end filter
     * @param sort
     *            sorting criteria
     * 
     * @return {@link TemplateQueryResponse}
     * @throws BaseException
     *             on error
     */
    public TemplateQueryResponse getTemplateMetaDataQuery(String name, String language, String validityStart, String validityEnd, String sort)
            throws BaseException {

        TemplateQueryParams queryParams = templateQueryHelper.getTemplateQueryParams(name, language, validityStart, validityEnd);
        List<TemplateQueryOrderType> queryOrders = templateQueryHelper.getTemplateQueryOrders(sort);

        List<Template> templates = templateQueryService.findByQueryParams(queryParams, queryOrders);

        return toTemplateQueryResponse(templates);
    }

    private TemplateQueryResponse toTemplateQueryResponse(List<Template> templates) {
        TemplateQueryResponse response = new TemplateQueryResponse();
        handleSuccessResultType(response);

        List<TemplateType> rowList = templateQueryResponseHelper.buildRowList(templates);
        response.withRowList(rowList);

        return response;
    }
}
