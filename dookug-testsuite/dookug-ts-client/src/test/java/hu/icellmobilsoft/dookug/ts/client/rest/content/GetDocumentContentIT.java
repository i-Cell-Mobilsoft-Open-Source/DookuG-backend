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
package hu.icellmobilsoft.dookug.ts.client.rest.content;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

import jakarta.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import hu.icellmobilsoft.coffee.dto.common.commonservice.FunctionCodeType;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.se.util.string.RandomUtil;
import hu.icellmobilsoft.dookug.client.DookugClient;
import hu.icellmobilsoft.dookug.client.type.GeneratedDocumentDto;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataQueryParamsType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataQueryResponse;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataResponse;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentStorageMethodType;
import hu.icellmobilsoft.dookug.ts.common.builder.StoredTemplateDocumentGenerateRequestBuilder;
import hu.icellmobilsoft.dookug.ts.common.constants.DocumentServiceTestConstant;
import hu.icellmobilsoft.dookug.ts.common.rest.AbstractGenerateDocumentIT;
import hu.icellmobilsoft.roaster.api.TestSuiteGroup;

/**
 * {@link DookugClient} get document content test
 *
 * @author szabolcs.gemesi
 * @since 0.0.1
 */
@Tag(TestSuiteGroup.JAXRS)
@DisplayName("Document content client test")
class GetDocumentContentIT extends AbstractGenerateDocumentIT {

    @Inject
    private DookugClient client;

    @Test
    @DisplayName("Get document content by a random id - BO Not Found")
    void getDocumentContentNotExistingDocumentId() {
        try {
            Boolean compressed = false;
            client.getDocumentContent(RandomUtil.generateId(), compressed);
        } catch (BaseException e) {
            Assertions.assertEquals(CoffeeFaultType.REST_CLIENT_EXCEPTION, e.getFaultTypeEnum());
            Assertions.assertInstanceOf(BaseException.class, e.getCause());
            Assertions.assertEquals(CoffeeFaultType.ENTITY_NOT_FOUND, ((BaseException) e.getCause()).getFaultTypeEnum());
        }
    }

    @Test
    @DisplayName("Create document by stored template and get document by the generation response's metadata")
    void testGetDocumentByStoredResponseMetadata() throws BaseException {

        // generate document with stored template
        client.setDocumentStorageMethodType(DocumentStorageMethodType.DATABASE);
        DocumentMetadataResponse metadataResponse = client.postDatabaseStoredTemplateDocumentGenerateMetadata(
                DocumentServiceTestConstant.DEV_TEMPLATE_NAME,
                DocumentServiceTestConstant.DEFAULT_LANGUAGE_HU,
                OffsetDateTime.now().truncatedTo(ChronoUnit.MICROS),
                templateParameterDataFromObject(StoredTemplateDocumentGenerateRequestBuilder.getDevTemplateMainParameterData()));
        Assertions.assertEquals(FunctionCodeType.OK, metadataResponse.getFuncCode());
        Assertions.assertNotNull(metadataResponse.getMetadata());

        // get document content
        Boolean compressed = false;
        GeneratedDocumentDto documentDto = client.getDocumentContent(metadataResponse.getMetadata().getDocumentId(), compressed);
        Assertions.assertEquals(metadataResponse.getMetadata().getFilename(), documentDto.getFileName());
        writeFileIfEnabled(documentDto.getInputStream(), documentDto.getFileName());
    }

    @Test
    @DisplayName("Create document by stored template, query by the filename and get document by query response's metadata")
    void testGetDocumentByStoredResponseAndQuery() throws BaseException {

        // generate document with stored template
        client.setDocumentStorageMethodType(DocumentStorageMethodType.DATABASE);
        Boolean compressed = false;
        GeneratedDocumentDto documentDto = client.postDatabaseStoredTemplateDocumentGenerate(
                DocumentServiceTestConstant.DEV_TEMPLATE_NAME,
                DocumentServiceTestConstant.DEFAULT_LANGUAGE_HU,
                OffsetDateTime.now().truncatedTo(ChronoUnit.MICROS),
                templateParameterDataFromObject(StoredTemplateDocumentGenerateRequestBuilder.getDevTemplateMainParameterData()),
                compressed);
        Assertions.assertNotNull(documentDto.getFileName());

        // metadata query
        DocumentMetadataQueryParamsType queryParams = new DocumentMetadataQueryParamsType().withFilename(documentDto.getFileName());

        DocumentMetadataQueryResponse queryResponse = client.postDocumentMetadataQuery(queryParams);
        Assertions.assertEquals(FunctionCodeType.OK, queryResponse.getFuncCode());
        Assertions.assertTrue(CollectionUtils.isNotEmpty(queryResponse.getRowList()));

        // get document content
        GeneratedDocumentDto generatedDocumentDto = client.getDocumentContent(queryResponse.getRowList().get(0).getDocumentId(), compressed);
        Assertions.assertEquals(documentDto.getFileName(), generatedDocumentDto.getFileName());
        writeFileIfEnabled(generatedDocumentDto.getInputStream(), generatedDocumentDto.getFileName());
    }
}
