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

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import jakarta.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.coffee.dto.exception.RestClientResponseException;
import hu.icellmobilsoft.dookug.api.rest.document.IDocumentGenerateInlineInternalRest;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentGenerateWithTemplatesRequest;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.GeneratorEngineType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.ResponseFormatType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.TemplateType;
import hu.icellmobilsoft.dookug.ts.common.builder.DocumentGenerateWithTemplatesRequestBuilder;
import hu.icellmobilsoft.dookug.ts.common.config.TsConfigKey;
import hu.icellmobilsoft.dookug.ts.common.constants.DocumentServiceTestConstant;
import hu.icellmobilsoft.dookug.ts.common.rest.mprestclient.IDocumentGenerateInlineInternalRestClient;
import hu.icellmobilsoft.roaster.api.TestSuiteGroup;
import hu.icellmobilsoft.roaster.common.util.FileUtil;
import hu.icellmobilsoft.roaster.restassured.BaseConfigurableWeldIT;

/**
 * Sample service {@link IDocumentGenerateInlineInternalRest} test
 *
 * @author imre.scheffer
 * @since 0.1.0
 */
@DisplayName("Generate document inline with Pdfbox only - entity body request - simple template")
@Tag(TestSuiteGroup.JAXRS)
class PostInlineInvalidInputIT extends BaseConfigurableWeldIT {

    @Inject
    @ConfigProperty(name = TsConfigKey.DOOKUG_SERVICE_DOCUMENT_BASE_URI)
    private String documentBaseUri;

    @Inject
    private DocumentGenerateWithTemplatesRequestBuilder documentGenerateWithTemplatesRequestBuilder;

    @Test
    @DisplayName("STRING + PDF_BOX invalid input parameter combination")
    void invalidInputStringPdfBoxTest() throws BaseException, IOException {
        IDocumentGenerateInlineInternalRestClient client = RestClientBuilder.newBuilder()
                // set URI
                .baseUri(URI.create(documentBaseUri))
                // build API interface
                .build(IDocumentGenerateInlineInternalRestClient.class);

        DocumentGenerateWithTemplatesRequest request = documentGenerateWithTemplatesRequestBuilder.emptyPdfboxRequest();
        // szandekosan elrontjuk
        request.getGeneratorSetup().setResponseFormat(ResponseFormatType.STRING);
        String template = FileUtil.readFileFromResource(DocumentServiceTestConstant.PDF_BOX_TEMPLATE);
        request.getTemplates()
                .add(new TemplateType().withTemplateName("pdfbox_template").withTemplateContent(template.getBytes(StandardCharsets.UTF_8)));

        try {
            client.postDocumentGenerateEntityBody(request);
            Assertions.fail(DocumentServiceTestConstant.EXPECTED_500);
        } catch (RestClientResponseException e) {
            String message = e.getCause().getLocalizedMessage();
            Assertions.assertTrue(StringUtils.contains(message, ResponseFormatType.STRING.name()));
            Assertions.assertTrue(StringUtils.contains(message, GeneratorEngineType.PDF_BOX.name()));
        }
    }

    @Test
    @DisplayName("PDF + NONE generator invalid input parameter combination")
    void invalidInputPdfNoneGeneratorTest() throws BaseException, IOException {
        IDocumentGenerateInlineInternalRestClient client = RestClientBuilder.newBuilder()
                // set URI
                .baseUri(URI.create(documentBaseUri))
                // build API interface
                .build(IDocumentGenerateInlineInternalRestClient.class);

        DocumentGenerateWithTemplatesRequest request = documentGenerateWithTemplatesRequestBuilder.emptyHandlebarsRequest();
        // szandekosan elrontjuk
        request.getGeneratorSetup().setResponseFormat(ResponseFormatType.PDF);
        String template = FileUtil.readFileFromResource(DocumentServiceTestConstant.PDF_BOX_TEMPLATE);
        request.getTemplates()
                .add(new TemplateType().withTemplateName("pdfbox_template").withTemplateContent(template.getBytes(StandardCharsets.UTF_8)));

        try {
            client.postDocumentGenerateEntityBody(request);
            Assertions.fail(DocumentServiceTestConstant.EXPECTED_500);
        } catch (RestClientResponseException e) {
            String message = e.getCause().getLocalizedMessage();
            Assertions.assertTrue(StringUtils.contains(message, "ResponseFormat [" + ResponseFormatType.PDF.name()));
        }
    }
}
