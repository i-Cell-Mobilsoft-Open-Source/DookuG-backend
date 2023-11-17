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
package hu.icellmobilsoft.dookug.ts.common.builder;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;

import hu.icellmobilsoft.coffee.dto.common.common.OrderByTypeType;
import hu.icellmobilsoft.coffee.dto.common.common.QueryRequestDetails;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataQueryOrderByType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataQueryOrderType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataQueryParamsType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataQueryRequest;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentStorageMethodType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.ResponseFormatType;
import hu.icellmobilsoft.dookug.ts.common.rest.DtoHelper;
import hu.icellmobilsoft.roaster.api.dto.BaseBuilder;

/**
 * Builder for {@link DocumentMetadataQueryRequest} class
 *
 * @author szabolcs.gemesi
 * @since 0.0.1
 */
@Model
public class DocumentMetadataQueryRequestBuilder extends BaseBuilder<DocumentMetadataQueryRequest> {

    @Override
    public DocumentMetadataQueryRequest createEmpty() {
        DocumentMetadataQueryRequest queryRequest = new DocumentMetadataQueryRequest();
        queryRequest.setContext(DtoHelper.createContext());
        return queryRequest;
    }

    @PostConstruct
    public void init() {
        setDto(createEmpty());
    }

    /**
     * Construct request using pdf format and database storage method query params
     * 
     * @return {@link DocumentMetadataQueryRequest}
     */
    public DocumentMetadataQueryRequest fullFillDatabasePdf() {
        QueryRequestDetails requestDetails = new QueryRequestDetails();
        requestDetails.setPage(1);
        requestDetails.setRows(10);

        List<DocumentMetadataQueryOrderType> orderTypes = new ArrayList<>();
        orderTypes.add(new DocumentMetadataQueryOrderType().withType(OrderByTypeType.ASC).withOrder(DocumentMetadataQueryOrderByType.FILENAME));
        orderTypes.add(new DocumentMetadataQueryOrderType().withType(OrderByTypeType.DESC).withOrder(DocumentMetadataQueryOrderByType.STATUS));

        DocumentMetadataQueryParamsType queryParamsType = new DocumentMetadataQueryParamsType();
        queryParamsType.setFormat(ResponseFormatType.PDF);
        queryParamsType.setStorageMethod(DocumentStorageMethodType.DATABASE);
        return getDto().withPaginationParams(requestDetails).withQueryParams(queryParamsType).withQueryOrders(orderTypes);
    }

    /**
     * Construct request using the given filename query params
     * 
     * @param filename
     *            the generated document's filename
     *
     * @return {@link DocumentMetadataQueryRequest}
     */
    public DocumentMetadataQueryRequest fullFillByFilename(String filename) {
        QueryRequestDetails requestDetails = new QueryRequestDetails();
        requestDetails.setPage(1);
        requestDetails.setRows(10);

        DocumentMetadataQueryParamsType queryParamsType = new DocumentMetadataQueryParamsType();
        queryParamsType.setFilename(filename);
        return getDto().withPaginationParams(requestDetails).withQueryParams(queryParamsType);
    }

    /**
     * Construct request using the given metadata
     *
     * @param metadataType
     *            {@link DocumentMetadataType}
     *
     * @return {@link DocumentMetadataQueryRequest}
     */
    public DocumentMetadataQueryRequest fullFillByMetadataType(DocumentMetadataType metadataType) {
        QueryRequestDetails requestDetails = new QueryRequestDetails();
        requestDetails.setPage(1);
        requestDetails.setRows(10);

        DocumentMetadataQueryParamsType queryParamsType = new DocumentMetadataQueryParamsType();
        queryParamsType.setFilename(metadataType.getFilename());
        queryParamsType.setStatus(metadataType.getStatus());
        queryParamsType.setFormat(metadataType.getFormat());
        queryParamsType.setStorageMethod(metadataType.getStorageMethod());
        return getDto().withPaginationParams(requestDetails).withQueryParams(queryParamsType);
    }
}
