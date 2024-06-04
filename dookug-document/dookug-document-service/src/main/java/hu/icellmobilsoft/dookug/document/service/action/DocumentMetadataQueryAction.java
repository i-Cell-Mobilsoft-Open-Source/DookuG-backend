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
package hu.icellmobilsoft.dookug.document.service.action;

import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;

import hu.icellmobilsoft.coffee.dto.common.common.QueryRequestDetails;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.dto.exception.InvalidParameterException;
import hu.icellmobilsoft.coffee.jpa.sql.paging.PagingResult;
import hu.icellmobilsoft.coffee.tool.utils.enums.EnumUtil;
import hu.icellmobilsoft.dookug.common.model.template.Document;
import hu.icellmobilsoft.dookug.common.system.rest.action.BaseAction;
import hu.icellmobilsoft.dookug.document.service.service.DocumentQueryService;
import hu.icellmobilsoft.dookug.schemas.common._1_0.rest.common.BaseRequestType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataQueryRequest;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataQueryResponse;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentStatusType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentStorageMethodType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.ResponseFormatType;

/**
 * Document metadata query action
 * 
 * @author szabolcs.gemesi
 * @since 0.0.1
 */
@Model
public class DocumentMetadataQueryAction extends BaseAction {

    @Inject
    private DocumentQueryService documentQueryService;

    /**
     * Dokument metadata query, can be filtered and paged
     * 
     * @param queryRequest
     *            {@link DocumentMetadataQueryRequest}
     * @return {@link DocumentMetadataQueryResponse}
     * @throws BaseException
     *             on error
     */
    public DocumentMetadataQueryResponse postDocumentMetadataQuery(DocumentMetadataQueryRequest queryRequest) throws BaseException {
        if (queryRequest == null) {
            throw new InvalidParameterException("queryRequest cannot be null!");
        }

        PagingResult<Document> pagingResult = documentQueryService.findByQueryParams(queryRequest.getQueryParams(),
                defaultPaginationParams(queryRequest.getPaginationParams()), queryRequest.getQueryOrders());

        return toDocumentMetadataQueryResponse(pagingResult, queryRequest);
    }

    private DocumentMetadataQueryResponse toDocumentMetadataQueryResponse(PagingResult<Document> pagingResult, BaseRequestType baseRequestType) {
        DocumentMetadataQueryResponse response = new DocumentMetadataQueryResponse();
        handleSuccessResultType(response, baseRequestType);

        for (Document document : pagingResult.getResults()) {
            DocumentMetadataType metadataType = new DocumentMetadataType();
            metadataType.setDocumentId(document.getId());
            metadataType.setFormat(ResponseFormatType.fromValue(document.getFormat()));
            metadataType.setStorageMethod(DocumentStorageMethodType.fromValue(document.getStorageType()));
            metadataType.setFilename(document.getFilename());
            metadataType.setStatus(EnumUtil.convert(document.getStatus(), DocumentStatusType.class));
            response.withRowList(metadataType);
        }

        return response;
    }

    private QueryRequestDetails defaultPaginationParams(QueryRequestDetails queryRequestDetails) {
        if (queryRequestDetails == null) {
            queryRequestDetails = new QueryRequestDetails();
            queryRequestDetails.setPage(1);
            queryRequestDetails.setRows(15);
        }
        return queryRequestDetails;

    }
}
