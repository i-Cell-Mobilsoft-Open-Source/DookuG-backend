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
package hu.icellmobilsoft.dookug.ts.common.builder.test;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.inject.Model;

import hu.icellmobilsoft.coffee.dto.common.common.OrderByTypeType;
import hu.icellmobilsoft.coffee.dto.common.common.QueryRequestDetails;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplateQueryOrderByType;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplateQueryOrderType;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplateQueryParamsType;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplateQueryRequest;
import hu.icellmobilsoft.dookug.ts.common.rest.DtoHelper;
import hu.icellmobilsoft.roaster.api.dto.BaseBuilder;

/**
 * Builder for {@link TemplateQueryRequest} class
 *
 * @author mate.biro
 * @since 2.2.0
 */
@Model
public class TemplateQueryRequestBuilder extends BaseBuilder<TemplateQueryRequest> {

    @Override
    public TemplateQueryRequest createEmpty() {

        TemplateQueryRequest queryRequest = new TemplateQueryRequest();
        queryRequest.setContext(DtoHelper.createContext());

        return queryRequest;
    }

    /**
     * default init
     */
    @PostConstruct
    public void init() {
        setDto(createEmpty());
    }

    /**
     * Construct request query params
     *
     * @return {@link TemplateQueryRequest}
     */
    public TemplateQueryRequest fullFill() {
        QueryRequestDetails requestDetails = new QueryRequestDetails();
        requestDetails.setPage(1);
        requestDetails.setRows(10);

        List<TemplateQueryOrderType> orderTypes = new ArrayList<>();
        orderTypes.add(new TemplateQueryOrderType().withType(OrderByTypeType.ASC).withOrder(TemplateQueryOrderByType.NAME));
        orderTypes.add(new TemplateQueryOrderType().withType(OrderByTypeType.DESC).withOrder(TemplateQueryOrderByType.LAST_UPDATED_AT));

        TemplateQueryParamsType queryParamsType = new TemplateQueryParamsType();
        queryParamsType.setLanguage("FR");
        queryParamsType.setName("TEST_TEMPLATE_BODY_FRA");
        return getDto().withPaginationParams(requestDetails).withQueryParams(queryParamsType).withQueryOrders(orderTypes);
    }
}
