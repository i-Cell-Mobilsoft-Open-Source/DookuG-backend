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
package hu.icellmobilsoft.dookug.ts.document.rest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import hu.icellmobilsoft.coffee.dto.common.commonservice.FunctionCodeType;
import hu.icellmobilsoft.coffee.dto.exception.BONotFoundException;
import hu.icellmobilsoft.coffee.dto.exception.RestClientResponseException;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.se.util.string.RandomUtil;
import hu.icellmobilsoft.coffee.tool.utils.compress.GZIPUtil;
import hu.icellmobilsoft.dookug.api.rest.document.IDocumentContentInternalRest;
import hu.icellmobilsoft.dookug.api.rest.document.IDocumentStoredTemplateInternalRest;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataQueryRequest;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataQueryResponse;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataResponse;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.StoredTemplateDocumentGenerateRequest;
import hu.icellmobilsoft.dookug.ts.common.builder.DocumentMetadataQueryRequestBuilder;
import hu.icellmobilsoft.dookug.ts.common.builder.StoredTemplateDocumentGenerateRequestBuilder;
import hu.icellmobilsoft.dookug.ts.common.config.TsConfigKey;
import hu.icellmobilsoft.dookug.ts.common.constants.DocumentServiceTestConstant;
import hu.icellmobilsoft.dookug.ts.common.rest.AbstractGenerateDocumentIT;
import hu.icellmobilsoft.dookug.ts.common.rest.mprestclient.IDocumentContentInternalRestClient;
import hu.icellmobilsoft.dookug.ts.common.rest.mprestclient.IDocumentGenerateStoredTemplateInternalRestClient;
import hu.icellmobilsoft.roaster.api.TestSuiteGroup;

/**
 * {@link IDocumentContentInternalRest#getDocumentContent(String, Boolean)} test
 * 
 * @author szabolcs.gemesi
 * @since 0.0.1
 */
@Tag(TestSuiteGroup.JAXRS)
@DisplayName("Document content request test")
class GetDocumentContentIT extends AbstractGenerateDocumentIT {

    @Inject
    @ConfigProperty(name = TsConfigKey.DOOKUG_SERVICE_DOCUMENT_BASE_URI)
    private String documentBaseUri;

    @Inject
    private StoredTemplateDocumentGenerateRequestBuilder requestBuilder;

    @Inject
    private DocumentMetadataQueryRequestBuilder queryRequestBuilder;

    @Test
    @DisplayName("Get document content by random id - BONotFound")
    void getBONotFoundDocumentContent() throws BaseException {
        IDocumentContentInternalRestClient contentRestClient = RestClientBuilder.newBuilder()
                .baseUri(URI.create(documentBaseUri))
                .build(IDocumentContentInternalRestClient.class);
        try {
            contentRestClient.getDocumentContent(RandomUtil.generateId(), false);
        } catch (RestClientResponseException e) {
            Assertions.assertTrue(e.getCause() instanceof BONotFoundException);
            Assertions.assertEquals(CoffeeFaultType.ENTITY_NOT_FOUND, ((BONotFoundException) e.getCause()).getFaultTypeEnum());
        }
    }

    @Test
    @DisplayName("Get document content by invalid documentum id")
    void getInvalidInputDocumentContent() {
        IDocumentContentInternalRestClient contentRestClient = RestClientBuilder.newBuilder()
                .baseUri(URI.create(documentBaseUri))
                .build(IDocumentContentInternalRestClient.class);
        try {
            contentRestClient.getDocumentContent("   ", false);
        } catch (BaseException e) {
            Assertions.assertTrue(e.getCause() instanceof BaseException);
            Assertions.assertEquals(CoffeeFaultType.WRONG_OR_MISSING_PARAMETERS, ((BaseException) e.getCause()).getFaultTypeEnum());
        }
    }

    @Test
    @DisplayName("Create document by stored template and get document by the generation response's metadata")
    void testGetDocumentByStoredResponseMetadata() throws BaseException {
        IDocumentContentInternalRestClient contentRestClient = RestClientBuilder.newBuilder()
                .baseUri(URI.create(documentBaseUri))
                .build(IDocumentContentInternalRestClient.class);
        IDocumentGenerateStoredTemplateInternalRestClient storedTemplateRestClient = RestClientBuilder.newBuilder()
                .baseUri(URI.create(documentBaseUri))
                .build(IDocumentGenerateStoredTemplateInternalRestClient.class);

        // generate document with stored template
        StoredTemplateDocumentGenerateRequest request = requestBuilder.fullFillDatabaseStorage(DocumentServiceTestConstant.DEV_TEMPLATE_NAME);
        DocumentMetadataResponse metadataResponse = storedTemplateRestClient.postStoredTemplateDocumentGenerateMetadata(request);
        Assertions.assertEquals(FunctionCodeType.OK, metadataResponse.getFuncCode());
        Assertions.assertNotNull(metadataResponse.getMetadata());

        // get document content
        Response response = contentRestClient.getDocumentContent(metadataResponse.getMetadata().getDocumentId(), false);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assertions.assertEquals(metadataResponse.getMetadata().getFilename(), getFilename(response));
        writeFileIfEnabled((InputStream) response.getEntity(), metadataResponse.getMetadata().getFilename());
        response.close();
    }

    @Test
    @DisplayName("Create document by stored template, query by the filename and get document by query response's metadata")
    void testGetDocumentByStoredResponseAndQuery() throws BaseException {
        IDocumentContentInternalRestClient contentRestClient = RestClientBuilder.newBuilder()
                .baseUri(URI.create(documentBaseUri))
                .build(IDocumentContentInternalRestClient.class);
        IDocumentGenerateStoredTemplateInternalRestClient storedTemplateRestClient = RestClientBuilder.newBuilder()
                .baseUri(URI.create(documentBaseUri))
                .build(IDocumentGenerateStoredTemplateInternalRestClient.class);
        IDocumentStoredTemplateInternalRest queryRestClient = RestClientBuilder.newBuilder()
                .baseUri(URI.create(documentBaseUri))
                .build(IDocumentStoredTemplateInternalRest.class);

        // generate document with stored template
        StoredTemplateDocumentGenerateRequest request = requestBuilder.fullFillDatabaseStorage(DocumentServiceTestConstant.DEV_TEMPLATE_NAME);
        Response generateResponse = storedTemplateRestClient.postStoredTemplateDocumentGenerate(request, false);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), generateResponse.getStatus());
        String filename = getFilename(generateResponse);
        Assertions.assertNotNull(filename);

        // metadata query
        DocumentMetadataQueryRequest queryRequest = queryRequestBuilder.fullFillByFilename(filename);
        DocumentMetadataQueryResponse queryResponse = queryRestClient.postDocumentMetadataQuery(queryRequest);
        Assertions.assertEquals(FunctionCodeType.OK, queryResponse.getFuncCode());
        Assertions.assertEquals(1, queryResponse.getRowList().size());

        // get document content
        Response response = contentRestClient.getDocumentContent(queryResponse.getRowList().get(0).getDocumentId(), false);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assertions.assertEquals(filename, getFilename(response));
        writeFileIfEnabled((InputStream) response.getEntity(), filename);
        response.close();
    }

    @Test
    @DisplayName("Create document by stored template and get compressed document by the generation response's metadata")
    void testGetDocumentGzippedByStoredResponseMetadata() throws BaseException, IOException {
        IDocumentContentInternalRestClient contentRestClient = RestClientBuilder.newBuilder()
                .baseUri(URI.create(documentBaseUri))
                .build(IDocumentContentInternalRestClient.class);
        IDocumentGenerateStoredTemplateInternalRestClient storedTemplateRestClient = RestClientBuilder.newBuilder()
                .baseUri(URI.create(documentBaseUri))
                .build(IDocumentGenerateStoredTemplateInternalRestClient.class);

        // Generate document and get metadata
        StoredTemplateDocumentGenerateRequest request = requestBuilder.fullFillDatabaseStorage(DocumentServiceTestConstant.DEV_TEMPLATE_NAME);
        DocumentMetadataResponse metadata = storedTemplateRestClient.postStoredTemplateDocumentGenerateMetadata(request);
        Assertions.assertEquals(FunctionCodeType.OK, metadata.getFuncCode());
        Assertions.assertNotNull(metadata.getMetadata());

        // get compressed document content
        try (Response response = contentRestClient.getDocumentContent(metadata.getMetadata().getDocumentId(), true)) {
            Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            Assertions.assertEquals(metadata.getMetadata().getFilename(), getFilename(response));

            // Check compression and write file
            var contentBytes = ((InputStream) response.getEntity()).readAllBytes();
            Assertions.assertTrue(GZIPUtil.isCompressed(contentBytes));
            writeFileIfEnabled(new ByteArrayInputStream(GZIPUtil.decompress(contentBytes)), metadata.getMetadata().getFilename());
        }
    }
}
