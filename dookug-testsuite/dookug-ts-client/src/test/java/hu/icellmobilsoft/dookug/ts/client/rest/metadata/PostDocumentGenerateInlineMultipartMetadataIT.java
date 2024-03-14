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

import java.io.ByteArrayInputStream;

import javax.inject.Inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.dookug.client.DookugClient;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataResponse;
import hu.icellmobilsoft.dookug.ts.common.constants.DocumentServiceTestConstant;
import hu.icellmobilsoft.dookug.ts.common.rest.AbstractGenerateDocumentIT;
import hu.icellmobilsoft.roaster.api.TestSuiteGroup;
import hu.icellmobilsoft.roaster.common.util.FileUtil;

/**
 * {@link DookugClient} non-stored template multipart metadata client test
 *
 * @author szabolcs.gemesi
 * @since 0.0.1
 */
@Tag(TestSuiteGroup.JAXRS)
@DisplayName("Generate document with non-stored template client test - multipart request")
class PostDocumentGenerateInlineMultipartMetadataIT extends AbstractGenerateDocumentIT {

    @Inject
    private DookugClient client;

    @Test
    @DisplayName("Generate PDF document with non-stored template multipart form request with json parameter data")
    void testDocumentGenerateNonStoredTemplateMultipartWithParameterData() throws BaseException {
        ByteArrayInputStream bis = new ByteArrayInputStream(FileUtil.readFileFromResource(DocumentServiceTestConstant.PDF_BOX_TEMPLATE).getBytes());
        DocumentMetadataResponse metadataResponse = client.postDocumentGenerateMultipartMetadata(
                bis,
                templateParameterDataFromFile(DocumentServiceTestConstant.PDF_BOX_TEMPLATE_PARAMETERS));
        Assertions.assertNotNull(metadataResponse.getMetadata());
        Assertions.assertTrue(metadataResponse.getMetadata().getFilename().contains("pdf"));
    }

    @Test
    @DisplayName("Generate PDF document with non-stored template multipart form request")
    void testDocumentGenerateNonStoredTemplateMultipart() throws BaseException {
        ByteArrayInputStream bis = new ByteArrayInputStream(FileUtil.readFileFromResource(DocumentServiceTestConstant.PDF_BOX_TEMPLATE).getBytes());
        DocumentMetadataResponse metadataResponse = client.postDocumentGenerateMultipartMetadata(bis);
        Assertions.assertNotNull(metadataResponse.getMetadata());
        Assertions.assertTrue(metadataResponse.getMetadata().getFilename().contains("pdf"));
    }
}
