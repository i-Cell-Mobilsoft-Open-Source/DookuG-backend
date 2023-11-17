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
package hu.icellmobilsoft.dookug.ts.document.rest.metadata;

import java.net.URI;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.apache.commons.collections.CollectionUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import hu.icellmobilsoft.coffee.dto.common.commonservice.FunctionCodeType;
import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.dookug.api.rest.document.IDocumentGenerateStoredTemplateInternalRest;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentGenerateWithTemplatesRequest;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataQueryRequest;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataQueryResponse;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataResponse;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.ResponseFormatType;
import hu.icellmobilsoft.dookug.ts.common.builder.DocumentGenerateWithTemplatesRequestBuilder;
import hu.icellmobilsoft.dookug.ts.common.builder.DocumentMetadataQueryRequestBuilder;
import hu.icellmobilsoft.dookug.ts.common.builder.StoredTemplateDocumentGenerateRequestBuilder;
import hu.icellmobilsoft.dookug.ts.common.config.TsConfigKey;
import hu.icellmobilsoft.dookug.ts.common.constants.DocumentServiceTestConstant;
import hu.icellmobilsoft.dookug.ts.common.rest.AbstractGenerateDocumentIT;
import hu.icellmobilsoft.dookug.ts.common.rest.mprestclient.IDocumentGenerateInlineInternalRestClient;
import hu.icellmobilsoft.dookug.ts.common.rest.mprestclient.IDocumentStoredTemplateInternalRestClient;
import hu.icellmobilsoft.roaster.api.TestSuiteGroup;

/**
 * {@link IDocumentStoredTemplateInternalRestClient#postDocumentMetadataQuery(DocumentMetadataQueryRequest)} test
 *
 * @author szabolcs.gemesi
 * @since 0.0.1
 */
@Tag(TestSuiteGroup.JAXRS)
@DisplayName("Document metadata query test")
class PostDocumentMetadataQueryIT extends AbstractGenerateDocumentIT {

    @Inject
    @ConfigProperty(name = TsConfigKey.DOOKUG_SERVICE_DOCUMENT_BASE_URI)
    private String documentBaseUri;

    @Inject
    private DocumentMetadataQueryRequestBuilder requestBuilder;

    @Inject
    private StoredTemplateDocumentGenerateRequestBuilder generateRequestBuilder;

    @Inject
    private DocumentGenerateWithTemplatesRequestBuilder templatesRequestBuilder;

    @Test
    @DisplayName("Document metadata query test - format PDF, storageType DATABASE")
    void testDocumentMetadataQuery() throws BaseException {
        IDocumentStoredTemplateInternalRestClient queryRestClient = RestClientBuilder.newBuilder()
                .baseUri(URI.create(documentBaseUri))
                .build(IDocumentStoredTemplateInternalRestClient.class);

        DocumentMetadataQueryRequest queryRequest = requestBuilder.fullFillDatabasePdf();
        DocumentMetadataQueryResponse queryResponse = queryRestClient.postDocumentMetadataQuery(queryRequest);
        Assertions.assertEquals(FunctionCodeType.OK, queryResponse.getFuncCode());
        Assertions.assertTrue(CollectionUtils.isNotEmpty(queryResponse.getRowList()));
        Set<ResponseFormatType> responseFormatTypes = queryResponse.getRowList().stream().map(DocumentMetadataType::getFormat)
                .collect(Collectors.toSet());
        Assertions.assertEquals(1, responseFormatTypes.size());
        Assertions.assertEquals(ResponseFormatType.PDF, responseFormatTypes.toArray()[0]);
    }

    @Test
    @DisplayName("Document metadata query test - Generate document with stored template and query by the generation metadata response")
    void testCreateStoredDocumentAndQuery() throws BaseException {
        IDocumentGenerateStoredTemplateInternalRest generateRestClient = RestClientBuilder.newBuilder()
                .baseUri(URI.create(documentBaseUri))
                .build(IDocumentGenerateStoredTemplateInternalRest.class);
        IDocumentStoredTemplateInternalRestClient queryRestClient = RestClientBuilder.newBuilder()
                .baseUri(URI.create(documentBaseUri))
                .build(IDocumentStoredTemplateInternalRestClient.class);

        // document generation with stored template
        DocumentMetadataResponse metadataResponse = generateRestClient.postStoredTemplateDocumentGenerateMetadata(
                generateRequestBuilder.fullFillDatabaseStorage(DocumentServiceTestConstant.DEV_TEMPLATE_NAME));
        Assertions.assertEquals(FunctionCodeType.OK, metadataResponse.getFuncCode());
        Assertions.assertNotNull(metadataResponse.getMetadata());

        // query by the metadata response
        DocumentMetadataQueryRequest queryRequest = requestBuilder.fullFillByMetadataType(metadataResponse.getMetadata());
        DocumentMetadataQueryResponse queryResponse = queryRestClient.postDocumentMetadataQuery(queryRequest);
        Assertions.assertEquals(FunctionCodeType.OK, queryResponse.getFuncCode());
        Assertions.assertTrue(CollectionUtils.isNotEmpty(queryResponse.getRowList()));
        Assertions.assertEquals(1, queryResponse.getRowList().size());
        Assertions.assertEquals(metadataResponse.getMetadata().getDocumentId(), queryResponse.getRowList().get(0).getDocumentId());
    }

    @Test
    @DisplayName("Document metadata query test - Generate document with stored template and query by the generation metadata response")
    void testCreateStoredDocumentAndQueryByFilename() throws BaseException {
        IDocumentGenerateStoredTemplateInternalRest generateRestClient = RestClientBuilder.newBuilder()
                .baseUri(URI.create(documentBaseUri))
                .build(IDocumentGenerateStoredTemplateInternalRest.class);
        IDocumentStoredTemplateInternalRestClient queryRestClient = RestClientBuilder.newBuilder()
                .baseUri(URI.create(documentBaseUri))
                .build(IDocumentStoredTemplateInternalRestClient.class);

        // document generation with stored template
        DocumentMetadataResponse metadataResponse = generateRestClient.postStoredTemplateDocumentGenerateMetadata(
                generateRequestBuilder.fullFillDatabaseStorage(DocumentServiceTestConstant.DEV_TEMPLATE_NAME));
        Assertions.assertEquals(FunctionCodeType.OK, metadataResponse.getFuncCode());
        Assertions.assertNotNull(metadataResponse.getMetadata());

        // query by the metadata response
        DocumentMetadataQueryRequest queryRequest = requestBuilder.fullFillByMetadataType(metadataResponse.getMetadata());
        DocumentMetadataQueryResponse queryResponse = queryRestClient.postDocumentMetadataQuery(queryRequest);
        Assertions.assertEquals(FunctionCodeType.OK, queryResponse.getFuncCode());
        Assertions.assertTrue(CollectionUtils.isNotEmpty(queryResponse.getRowList()));
        Assertions.assertEquals(1, queryResponse.getRowList().size());
        Assertions.assertEquals(metadataResponse.getMetadata().getDocumentId(), queryResponse.getRowList().get(0).getDocumentId());
    }
}
