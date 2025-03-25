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

import java.io.ByteArrayInputStream;
import java.net.URI;

import jakarta.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import hu.icellmobilsoft.coffee.dto.common.commonservice.FunctionCodeType;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.dookug.api.rest.document.IDocumentGenerateInlineInternalRest;
import hu.icellmobilsoft.dookug.api.rest.document.form.DocumentGenerateMultipartForm;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentGenerateRequest;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataResponse;
import hu.icellmobilsoft.dookug.ts.common.builder.DocumentGenerateRequestBuilder;
import hu.icellmobilsoft.dookug.ts.common.config.TsConfigKey;
import hu.icellmobilsoft.dookug.ts.common.constants.DocumentServiceTestConstant;
import hu.icellmobilsoft.dookug.ts.common.rest.AbstractGenerateDocumentIT;
import hu.icellmobilsoft.dookug.ts.common.rest.mprestclient.IDocumentGenerateInlineInternalRestClient;
import hu.icellmobilsoft.roaster.api.TestSuiteGroup;
import hu.icellmobilsoft.roaster.common.util.FileUtil;

/**
 * {@link IDocumentGenerateInlineInternalRest#postDocumentGenerateMultipartMetadata(DocumentGenerateMultipartForm)} test
 *
 * @author szabolcs.gemesi
 * @since 0.0.1
 */
@Tag(TestSuiteGroup.JAXRS)
@DisplayName("Generate document with non-stored template request - multipart request")
class PostDocumentGenerateInlineMultipartMetadataIT extends AbstractGenerateDocumentIT {

    @Inject
    @ConfigProperty(name = TsConfigKey.DOOKUG_SERVICE_DOCUMENT_BASE_URI)
    private String documentBaseUri;

    @Inject
    private DocumentGenerateRequestBuilder generateRequestBuilder;

    @Test
    @DisplayName("Generate PDF document with non-stored template multipart form request")
    void testDocumentGenerateNonStoredTemplateMultipart() throws BaseException {
        IDocumentGenerateInlineInternalRestClient client = RestClientBuilder.newBuilder()
                .baseUri(URI.create(documentBaseUri))
                .build(IDocumentGenerateInlineInternalRestClient.class);
        DocumentGenerateRequest request = generateRequestBuilder.fullFillHandlebarsPdfBoxDatabase();
        request.getGeneratorSetup()
                .setParametersData(
                        templateParameterDataFromFile(DocumentServiceTestConstant.PDF_BOX_TEMPLATE_PARAMETERS));
        DocumentGenerateMultipartForm form = new DocumentGenerateMultipartForm();
        form.setRequest(request);
        ByteArrayInputStream bis = new ByteArrayInputStream(FileUtil.readFileFromResource(DocumentServiceTestConstant.PDF_BOX_TEMPLATE).getBytes());
        form.setTemplate(bis);
        DocumentMetadataResponse response = client.postDocumentGenerateMultipartMetadata(form);
        Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode());
        Assertions.assertNotNull(response.getMetadata());
        Assertions.assertTrue(response.getMetadata().getFilename().contains("pdf"));
    }
}
