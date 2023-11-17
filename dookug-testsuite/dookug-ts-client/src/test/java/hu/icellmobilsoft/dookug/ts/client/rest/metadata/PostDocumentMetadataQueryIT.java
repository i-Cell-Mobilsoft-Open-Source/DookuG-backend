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

import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import hu.icellmobilsoft.coffee.dto.common.commonservice.FunctionCodeType;
import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.dookug.client.DookugClient;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataQueryParamsType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataQueryResponse;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.ResponseFormatType;
import hu.icellmobilsoft.roaster.api.TestSuiteGroup;
import hu.icellmobilsoft.roaster.restassured.BaseConfigurableWeldIT;

/**
 * {@link DookugClient} metadata query test
 *
 * @author szabolcs.gemesi
 * @since 0.0.1
 */
@Tag(TestSuiteGroup.JAXRS)
@DisplayName("Document metadata query client test")
class PostDocumentMetadataQueryIT extends BaseConfigurableWeldIT {

    @Inject
    private DookugClient client;

    @Test
    @DisplayName("Document metadata query client test - format PDF")
    void testDocumentMetadataQueryClient() throws BaseException {
        DocumentMetadataQueryParamsType queryParams = new DocumentMetadataQueryParamsType().withFormat(ResponseFormatType.PDF);

        DocumentMetadataQueryResponse response = client.postDocumentMetadataQuery(queryParams);
        Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode());
        Assertions.assertTrue(CollectionUtils.isNotEmpty(response.getRowList()));
        Set<ResponseFormatType> responseFormatTypes = response.getRowList().stream().map(DocumentMetadataType::getFormat).collect(Collectors.toSet());
        Assertions.assertEquals(1, responseFormatTypes.size());
        Assertions.assertEquals(ResponseFormatType.PDF, responseFormatTypes.toArray()[0]);
    }
}
