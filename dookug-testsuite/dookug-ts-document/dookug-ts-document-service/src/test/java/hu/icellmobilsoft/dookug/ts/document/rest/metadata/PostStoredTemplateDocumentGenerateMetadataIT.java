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

import jakarta.inject.Inject;

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
import hu.icellmobilsoft.coffee.tool.utils.string.RandomUtil;
import hu.icellmobilsoft.dookug.api.rest.document.IDocumentGenerateStoredTemplateInternalRest;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataResponse;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.StoredTemplateDocumentGenerateRequest;
import hu.icellmobilsoft.dookug.ts.common.builder.StoredTemplateDocumentGenerateRequestBuilder;
import hu.icellmobilsoft.dookug.ts.common.config.TsConfigKey;
import hu.icellmobilsoft.dookug.ts.common.constants.DocumentServiceTestConstant;
import hu.icellmobilsoft.dookug.ts.common.rest.AbstractGenerateDocumentIT;
import hu.icellmobilsoft.dookug.ts.common.rest.mprestclient.IDocumentGenerateStoredTemplateInternalRestClient;
import hu.icellmobilsoft.roaster.api.TestSuiteGroup;

/**
 * {@link IDocumentGenerateStoredTemplateInternalRest#postStoredTemplateDocumentGenerateMetadata(StoredTemplateDocumentGenerateRequest)} test
 *
 * @author szabolcs.gemesi
 * @since 0.0.1
 */
@Tag(TestSuiteGroup.JAXRS)
@DisplayName("Generate document with stored template")
class PostStoredTemplateDocumentGenerateMetadataIT extends AbstractGenerateDocumentIT {

    @Inject
    @ConfigProperty(name = TsConfigKey.DOOKUG_SERVICE_DOCUMENT_BASE_URI)
    private String documentBaseUri;

    @Inject
    private StoredTemplateDocumentGenerateRequestBuilder requestBuilder;

    @Test
    @DisplayName("Generate PDF document with stored template and get the document's metadata")
    void testStoredTemplateDocumentGenerateMetadata() throws BaseException {
        IDocumentGenerateStoredTemplateInternalRestClient client = RestClientBuilder.newBuilder()
                .baseUri(URI.create(documentBaseUri))
                .build(IDocumentGenerateStoredTemplateInternalRestClient.class);
        StoredTemplateDocumentGenerateRequest request = requestBuilder.fullFillDatabaseStorage(DocumentServiceTestConstant.DEV_TEMPLATE_NAME);
        DocumentMetadataResponse response = client.postStoredTemplateDocumentGenerateMetadata(request);
        Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode());
        Assertions.assertTrue(response.getMetadata().getFilename().contains("pdf"));
    }

    @Test
    @DisplayName("Generate PDF document with non-stored template - BONotFound")
    void testStoredTemplateBONotFound() throws BaseException {
        IDocumentGenerateStoredTemplateInternalRestClient client = RestClientBuilder.newBuilder()
                .baseUri(URI.create(documentBaseUri))
                .build(IDocumentGenerateStoredTemplateInternalRestClient.class);
        StoredTemplateDocumentGenerateRequest request = requestBuilder.fullFillDatabaseStorage(RandomUtil.generateId());
        try {
            client.postStoredTemplateDocumentGenerateMetadata(request);
        } catch (RestClientResponseException e) {
            Assertions.assertTrue(e.getCause() instanceof BONotFoundException);
            Assertions.assertEquals(CoffeeFaultType.ENTITY_NOT_FOUND, ((BONotFoundException) e.getCause()).getFaultTypeEnum());
        }
    }
}
