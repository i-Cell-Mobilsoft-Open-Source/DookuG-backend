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
import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.coffee.dto.common.common.OrderByTypeType;
import hu.icellmobilsoft.coffee.dto.common.common.QueryRequestDetails;
import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.coffee.dto.exception.InvalidParameterException;
import hu.icellmobilsoft.coffee.jpa.sql.paging.PagingResult;
import hu.icellmobilsoft.coffee.jpa.sql.paging.PagingUtil;
import hu.icellmobilsoft.coffee.tool.utils.enums.EnumUtil;
import hu.icellmobilsoft.dookug.common.model.template.Document;
import hu.icellmobilsoft.dookug.common.model.template.Document_;
import hu.icellmobilsoft.dookug.common.model.template.enums.DocumentStatus;
import hu.icellmobilsoft.dookug.common.system.jpa.service.BaseService;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataQueryOrderType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataQueryParamsType;

/**
 * {@link Document} database query operations
 *
 * @author szabolcs.gemesi
 * @since 0.0.1
 */
@Model
public class DocumentQueryService extends BaseService<Document> {

    /**
     * {@link Document} lister that can be filtered and paged
     *
     * @param queryParams
     *            {@link DocumentMetadataQueryParamsType}
     * @param paginationParams
     *            {@link QueryRequestDetails}
     * @param queryOrders
     *            {@link List} of {@link DocumentMetadataQueryOrderType}
     * @return {@link PagingResult} of {@link Document}
     * @throws BaseException
     *             if database error occurs
     */
    public PagingResult<Document> findByQueryParams(DocumentMetadataQueryParamsType queryParams, QueryRequestDetails paginationParams,
            List<DocumentMetadataQueryOrderType> queryOrders) throws BaseException {
        String methodInfo = getCalledMethodWithParamsBase("findByQueryParams", "queryParams", "paginationParams", "queryOrders");
        logEnter(methodInfo, queryParams, paginationParams, queryOrders);
        try {
            if (queryParams == null || paginationParams == null || queryOrders == null) {
                throw new InvalidParameterException("queryParams, paginationParams or queryOrders is null!");
            }
            TypedQuery<Document> query = createDocumentQuery(queryParams, queryOrders, false, Document.class);
            TypedQuery<Long> countQuery = createDocumentQuery(queryParams, queryOrders, true, Long.class);
            return PagingUtil.getPagingResult(query, countQuery.getSingleResult(), paginationParams.getPage(), paginationParams.getRows());
        } finally {
            logReturn(methodInfo, queryParams, paginationParams, queryOrders);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> TypedQuery<T> createDocumentQuery(DocumentMetadataQueryParamsType queryParams, List<DocumentMetadataQueryOrderType> queryOrders,
            boolean countQuery, Class<T> rootClass) {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(rootClass);
        Root<Document> root = query.from(Document.class);

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

    private void addQueryFilters(DocumentMetadataQueryParamsType queryParams, CriteriaBuilder builder, Root<Document> root,
            List<Predicate> predicates) {
        if (StringUtils.isNotBlank(queryParams.getTemplateId())) {
            predicates.add(builder.equal(root.get(Document_.templateId), queryParams.getTemplateId()));
        }

        if (queryParams.isSetFormat()) {
            predicates.add(builder.equal(builder.lower(root.get(Document_.format)), queryParams.getFormat().value().toLowerCase()));
        }

        if (queryParams.isSetStorageMethod()) {
            predicates.add(builder.equal(builder.lower(root.get(Document_.storageType)), queryParams.getStorageMethod().value().toLowerCase()));
        }

        if (StringUtils.isNotBlank(queryParams.getStorageId())) {
            predicates.add(builder.equal(root.get(Document_.storageId), queryParams.getStorageId()));
        }

        if (StringUtils.isNotBlank(queryParams.getFilename())) {
            predicates.add(builder.equal(builder.lower(root.get(Document_.filename)), queryParams.getFilename().toLowerCase()));
        }

        if (queryParams.isSetStatus()) {
            predicates.add(builder.equal(root.get(Document_.status), EnumUtil.convert(queryParams.getStatus(), DocumentStatus.class)));
        }
    }

    private List<Order> createOrdering(List<DocumentMetadataQueryOrderType> queryOrders, CriteriaBuilder builder, Root<Document> root) {

        List<Order> orders = new ArrayList<>();
        if (!CollectionUtils.isEmpty(queryOrders)) {
            for (DocumentMetadataQueryOrderType order : queryOrders) {
                Path<?> attr = null;
                switch (order.getOrder()) {
                case DOCUMENT_STORAGE_METHOD:
                    attr = root.get(Document_.storageType);
                    break;
                case STATUS:
                    attr = root.get(Document_.status);
                    break;
                case FILENAME:
                    attr = root.get(Document_.filename);
                    break;
                case FORMAT:
                    attr = root.get(Document_.format);
                    break;

                }
                handleAttr(attr, builder, orders, order.getType() == null ? OrderByTypeType.ASC : order.getType());
            }
        }
        orders.add(builder.asc(root.get(Document_.id)));
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
