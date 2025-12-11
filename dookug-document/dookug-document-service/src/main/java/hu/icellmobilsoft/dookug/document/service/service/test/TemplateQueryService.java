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
package hu.icellmobilsoft.dookug.document.service.service.test;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.inject.Model;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;

import org.apache.commons.collections.CollectionUtils;

import hu.icellmobilsoft.coffee.cdi.trace.annotation.Traced;
import hu.icellmobilsoft.coffee.cdi.trace.constants.SpanAttribute;
import hu.icellmobilsoft.coffee.dto.common.common.OrderByTypeType;
import hu.icellmobilsoft.coffee.dto.common.common.QueryRequestDetails;
import hu.icellmobilsoft.coffee.dto.exception.InvalidParameterException;
import hu.icellmobilsoft.coffee.jpa.sql.paging.PagingResult;
import hu.icellmobilsoft.coffee.jpa.sql.paging.PagingUtil;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.dookug.common.model.template.Template;
import hu.icellmobilsoft.dookug.common.model.template.Template_;
import hu.icellmobilsoft.dookug.common.system.jpa.service.BaseService;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplateQueryOrderType;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplateQueryParamsType;

/**
 * {@link Template} database query operations
 * TODO refactor common query service
 *
 * @author mate.biro
 * @since 2.2.0
 */
@Model
public class TemplateQueryService extends BaseService<Template> {

    /**
     * {@link Template} lister that can be filtered and paged
     *
     * @param queryParams
     *            {@link TemplateQueryParamsType}
     * @param paginationParams
     *            {@link QueryRequestDetails}
     * @param queryOrders
     *            {@link List} of {@link TemplateQueryOrderType}
     * @return {@link PagingResult} of {@link Template}
     * @throws BaseException
     *             if database error occurs
     */
    @Traced(component = SpanAttribute.Database.COMPONENT, kind = SpanAttribute.Database.KIND, dbType = SpanAttribute.Database.DB_TYPE)
    public PagingResult<Template> findByQueryParams(TemplateQueryParamsType queryParams, QueryRequestDetails paginationParams,
            List<TemplateQueryOrderType> queryOrders) throws BaseException {
        String methodInfo = getCalledMethodWithParamsBase("findByQueryParams", "queryParams", "paginationParams", "queryOrders");
        logEnter(methodInfo, queryParams, paginationParams, queryOrders);
        try {
            if (queryParams == null || paginationParams == null || queryOrders == null) {
                throw new InvalidParameterException("queryParams, paginationParams or queryOrders is null!");
            }
            TypedQuery<Template> query = createTemplateQuery(queryParams, queryOrders, false, Template.class);
            TypedQuery<Long> countQuery = createTemplateQuery(queryParams, queryOrders, true, Long.class);
            return PagingUtil.getPagingResult(query, countQuery.getSingleResult(), paginationParams.getPage(), paginationParams.getRows());
        } finally {
            logReturn(methodInfo, queryParams, paginationParams, queryOrders);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> TypedQuery<T> createTemplateQuery(TemplateQueryParamsType queryParams, List<TemplateQueryOrderType> queryOrders,
            boolean countQuery, Class<T> rootClass) {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(rootClass);
        Root<Template> root = query.from(Template.class);

        ArrayList<Predicate> predicates = new ArrayList<>();
        addQueryFilters(queryParams, builder, root, predicates);

        if (countQuery) {
            query.select((Selection<? extends T>) builder.count(root));
        } else {
            query.select((Selection<? extends T>) root);
            List<Order> os = createOrdering(queryOrders, builder, root);
            query.orderBy(os);
        }
        query.where(builder.and(predicates.toArray(new Predicate[0])));
        return getEntityManager().createQuery(query);
    }

    private void addQueryFilters(TemplateQueryParamsType queryParams, CriteriaBuilder builder, Root<Template> root,
            List<Predicate> predicates) {
        if (queryParams.isSetName()) {
            predicates.add(builder.equal(root.get(Template_.name), queryParams.getName()));
        }

        if (queryParams.isSetLanguage()) {
            predicates.add(builder.equal(builder.lower(root.get(Template_.language)), queryParams.getLanguage().toLowerCase()));
        }

        if (queryParams.isSetValidityStart()) {
            predicates.add(builder.greaterThanOrEqualTo(root.get(Template_.validityStart), queryParams.getValidityStart()));

        }

        if (queryParams.isSetValidityEnd()) {
            predicates.add(
                    builder.lessThan(root.get(Template_.validityEnd), queryParams.getValidityEnd()));
        }
    }

    private List<Order> createOrdering(List<TemplateQueryOrderType> queryOrders, CriteriaBuilder builder, Root<Template> root) {

        List<Order> orders = new ArrayList<>();
        if (!CollectionUtils.isEmpty(queryOrders)) {
            for (TemplateQueryOrderType order : queryOrders) {
                Path<?> attr = switch (order.getOrder()) {
                    case NAME -> root.get(Template_.name);
                    // TODO calculate last updated at (insdate or moddate if not null)??
                    case LAST_UPDATED_AT -> root.get(Template_.validityStart);
                    case DESCRIPTION -> root.get(Template_.description);
                    case LANGUAGE -> root.get(Template_.language);
                    case VALIDITY_START -> root.get(Template_.validityStart);
                    case VALIDITY_END -> root.get(Template_.validityEnd);
                };
                handleAttr(attr, builder, orders, order.getType() == null ? OrderByTypeType.ASC : order.getType());
            }
        }
        orders.add(builder.asc(root.get(Template_.id)));
        return orders;
    }

    private void handleAttr(Path<?> attr, CriteriaBuilder builder, List<Order> orders, OrderByTypeType orderType) {
        if (attr != null) {
            Order orderBy;
            if (orderType == OrderByTypeType.ASC) {
                orderBy = builder.asc(attr);
            } else {
                orderBy = builder.desc(attr);
            }
            orders.add(orderBy);
        }
    }
}
