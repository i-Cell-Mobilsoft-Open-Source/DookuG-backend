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

import hu.icellmobilsoft.dookug.schemas.common._1_0.common.GeneratorEngineType;
import hu.icellmobilsoft.dookug.schemas.common._1_0.common.TemplateEngineType;
import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;

import hu.icellmobilsoft.coffee.dto.common.common.QueryRequestDetails;
import hu.icellmobilsoft.coffee.dto.exception.InvalidParameterException;
import hu.icellmobilsoft.coffee.jpa.sql.paging.PagingResult;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.tool.utils.enums.EnumUtil;
import hu.icellmobilsoft.dookug.common.model.template.Template;
import hu.icellmobilsoft.dookug.common.system.rest.action.BaseAction;
import hu.icellmobilsoft.dookug.document.service.service.test.TemplateQueryService;
import hu.icellmobilsoft.dookug.schemas.common._1_0.rest.common.BaseRequestType;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplateQueryRequest;
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

    /**
     * Template query, can be filtered and paginated
     *
     * @param request
     *            {@link TemplateQueryRequest}
     * @return {@link TemplateQueryResponse}
     * @throws BaseException
     *             on error
     */
    public TemplateQueryResponse postTemplateQuery(TemplateQueryRequest request) throws BaseException {
        if (request == null) {
            throw new InvalidParameterException("request cannot be null!");
        }

        PagingResult<Template> pagingResult = templateQueryService
                .findByQueryParams(request.getQueryParams(), defaultPaginationParams(request.getPaginationParams()), request.getQueryOrders());

        return toTemplateQueryResponse(pagingResult, request);
    }

    private TemplateQueryResponse toTemplateQueryResponse(PagingResult<Template> pagingResult, BaseRequestType baseRequestType) {
        TemplateQueryResponse response = new TemplateQueryResponse();
        handleSuccessResultType(response, baseRequestType);

        for (Template template : pagingResult.getResults()) {
            TemplateType templateType = new TemplateType();
            templateType.setTemplateId(template.getId());
            templateType.setLanguage(template.getLanguage());
            templateType.setName(template.getName());
            templateType.setDescription(template.getDescription());
            templateType.setTemplateEngine(EnumUtil.convert(template.getTemplateEngine(), TemplateEngineType.class));
            templateType.setGeneratorEngine(EnumUtil.convert(template.getGeneratorEngine(), GeneratorEngineType.class));
            templateType.setValidityStart(template.getValidityStart());
            templateType.setValidityEnd(template.getValidityEnd());
            templateType.setLastUpdatedAt(template.getModificationDate() != null ? template.getModificationDate() : template.getCreationDate());
            response.withRowList(templateType);
        }

        return response;
    }

    // TODO: common utility?
    private QueryRequestDetails defaultPaginationParams(QueryRequestDetails queryRequestDetails) {
        if (queryRequestDetails == null) {
            queryRequestDetails = new QueryRequestDetails();
            queryRequestDetails.setPage(1);
            queryRequestDetails.setRows(15);
        }
        return queryRequestDetails;

    }
}
