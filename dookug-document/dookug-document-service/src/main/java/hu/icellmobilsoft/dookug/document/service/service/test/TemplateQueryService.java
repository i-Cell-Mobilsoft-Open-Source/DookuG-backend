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
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;

import org.apache.commons.collections.CollectionUtils;

import hu.icellmobilsoft.coffee.cdi.trace.annotation.Traced;
import hu.icellmobilsoft.coffee.cdi.trace.constants.SpanAttribute;
import hu.icellmobilsoft.coffee.dto.common.common.OrderByTypeType;
import hu.icellmobilsoft.dookug.common.model.template.Template;
import hu.icellmobilsoft.dookug.common.model.template.Template_;
import hu.icellmobilsoft.dookug.common.system.jpa.service.BaseService;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplateQueryOrderType;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplateQueryParamsType;

/**
 * {@link Template} database query operations TODO refactor common query service
 *
 * @author mate.biro
 * @since 2.2.0
 */
@Model
public class TemplateQueryService extends BaseService<Template> {

    /**
     * Find {@link Template} by query parameters
     *
     * @param queryParams
     *            query parameters
     * @param queryOrders
     *            query ordering
     * @return list of {@link Template}
     */
    @Traced(component = SpanAttribute.Database.COMPONENT, kind = SpanAttribute.Database.KIND, dbType = SpanAttribute.Database.DB_TYPE)
    public List<Template> findByQueryParams(TemplateQueryParamsType queryParams, List<TemplateQueryOrderType> queryOrders) {

        String methodInfo = getCalledMethodWithParamsBase("findByQueryParams", "templateMetaDataQueryParams", "sort");
        logEnter(methodInfo, queryParams, queryOrders);
        try {
            TypedQuery<Template> query = createTemplateQuery(queryParams, queryOrders, Template.class);
            return query.getResultList();
        } finally {
            logReturn(methodInfo, queryParams, queryOrders);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> TypedQuery<T> createTemplateQuery(TemplateQueryParamsType queryParams, List<TemplateQueryOrderType> queryOrders,
            Class<T> rootClass) {

        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(rootClass);
        Root<Template> root = query.from(Template.class);

        ArrayList<Predicate> predicates = new ArrayList<>();
        addQueryFilters(queryParams, builder, root, predicates);

        query.select((Selection<? extends T>) root);
        List<Order> os = createOrdering(queryOrders, builder, root);
        query.orderBy(os);

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
        if (CollectionUtils.isNotEmpty(queryOrders)) {
            for (TemplateQueryOrderType order : queryOrders) {
                Expression<?> attr = switch (order.getOrder()) {
                    case NAME -> root.get(Template_.name);
                    case LAST_UPDATED_AT -> getLastUpdatedAt(builder, root);
                    case DESCRIPTION -> root.get(Template_.description);
                    case LANGUAGE -> root.get(Template_.language);
                    case VALIDITY_START -> root.get(Template_.validityStart);
                    case VALIDITY_END -> root.get(Template_.validityEnd);
                };
                handleAttr(attr, builder, orders, order.getType() == null ? OrderByTypeType.ASC : order.getType());
            }
        } else {
            orders.add(builder.desc(getLastUpdatedAt(builder, root)));
        }
        orders.add(builder.asc(root.get(Template_.id)));
        return orders;
    }

    private Expression<?> getLastUpdatedAt(CriteriaBuilder builder, Root<Template> root) {

        return builder.coalesce(root.get(Template_.modificationDate), root.get(Template_.creationDate));
    }

    private void handleAttr(Expression<?> attr, CriteriaBuilder builder, List<Order> orders, OrderByTypeType orderType) {

        if (attr == null) {
            return;
        }

        Order orderBy;
        if (orderType == OrderByTypeType.ASC) {
            orderBy = builder.asc(attr);
        } else {
            orderBy = builder.desc(attr);
        }
        orders.add(orderBy);

    }
}
