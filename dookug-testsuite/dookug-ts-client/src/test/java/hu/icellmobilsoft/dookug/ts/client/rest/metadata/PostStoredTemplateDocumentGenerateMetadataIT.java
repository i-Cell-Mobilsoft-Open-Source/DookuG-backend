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
package hu.icellmobilsoft.dookug.ts.client.rest.metadata;

import java.time.OffsetDateTime;

import javax.inject.Inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import hu.icellmobilsoft.coffee.dto.common.commonservice.FunctionCodeType;
import hu.icellmobilsoft.coffee.dto.exception.BONotFoundException;
import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.coffee.dto.exception.RestClientResponseException;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.coffee.tool.utils.string.RandomUtil;
import hu.icellmobilsoft.dookug.client.DookugClient;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataResponse;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.TemplateLanguageType;
import hu.icellmobilsoft.dookug.ts.common.builder.StoredTemplateDocumentGenerateRequestBuilder;
import hu.icellmobilsoft.dookug.ts.common.constants.DocumentServiceTestConstant;
import hu.icellmobilsoft.dookug.ts.common.rest.AbstractGenerateDocumentIT;
import hu.icellmobilsoft.roaster.api.TestSuiteGroup;

/**
 * {@link DookugClient} stored template metadata client test
 *
 * @author szabolcs.gemesi
 * @since 0.0.1
 */
@Tag(TestSuiteGroup.JAXRS)
@DisplayName("Generate document with stored template client test")
class PostStoredTemplateDocumentGenerateMetadataIT extends AbstractGenerateDocumentIT {

    @Inject
    private DookugClient client;

    @Test
    @DisplayName("Generate PDF document with stored template and get the document's metadata")
    void testStoredTemplateDocumentGenerateMetadata() throws BaseException {
        DocumentMetadataResponse response = client.postDatabaseStoredTemplateDocumentGenerateMetadata(
                DocumentServiceTestConstant.DEV_TEMPLATE_NAME,
                TemplateLanguageType.HU,
                OffsetDateTime.now(),
                templateParameterDataFromObject(StoredTemplateDocumentGenerateRequestBuilder.getDevTemplateMainParameterData()));
        Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode());
        Assertions.assertTrue(response.getMetadata().getFilename().contains("pdf"));
    }

    @Test
    @DisplayName("Generate PDF document with non-stored template - BONotFound")
    void testStoredTemplateBONotFound() throws BaseException {
        try {
            client.postDatabaseStoredTemplateDocumentGenerateMetadata(
                    RandomUtil.generateId(),
                    TemplateLanguageType.HU,
                    OffsetDateTime.now(),
                    templateParameterDataFromObject(StoredTemplateDocumentGenerateRequestBuilder.getDevTemplateMainParameterData()));
        } catch (RestClientResponseException e) {
            Assertions.assertTrue(e.getCause() instanceof BONotFoundException);
            Assertions.assertEquals(CoffeeFaultType.ENTITY_NOT_FOUND, ((BONotFoundException) e.getCause()).getFaultTypeEnum());
        }
    }
}
