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
package hu.icellmobilsoft.dookug.ts.document.rest.pdfbox;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import hu.icellmobilsoft.coffee.dto.exception.RestClientResponseException;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.dookug.api.rest.document.IDocumentGenerateInlineInternalRest;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentGenerateWithTemplatesRequest;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.TemplateType;
import hu.icellmobilsoft.dookug.ts.common.builder.DocumentGenerateWithTemplatesRequestBuilder;
import hu.icellmobilsoft.dookug.ts.common.config.TsConfigKey;
import hu.icellmobilsoft.dookug.ts.common.constants.DocumentServiceTestConstant;
import hu.icellmobilsoft.dookug.ts.common.rest.AbstractGenerateDocumentIT;
import hu.icellmobilsoft.dookug.ts.common.rest.mprestclient.IDocumentGenerateInlineInternalRestClient;
import hu.icellmobilsoft.roaster.api.TestSuiteGroup;
import hu.icellmobilsoft.roaster.common.util.FileUtil;

/**
 * Sample service {@link IDocumentGenerateInlineInternalRest} test
 *
 * @author imre.scheffer
 * @since 0.1.0
 */
@DisplayName("Generate document inline with Pdfbox only - entity body request - simple template")
@Tag(TestSuiteGroup.JAXRS)
class PostInlinePdfboxHtmlTemplateIT extends AbstractGenerateDocumentIT {

    @Inject
    @ConfigProperty(name = TsConfigKey.DOOKUG_SERVICE_DOCUMENT_BASE_URI)
    private String documentBaseUri;

    @Inject
    private DocumentGenerateWithTemplatesRequestBuilder documentGenerateWithTemplatesRequestBuilder;

    @Test
    @DisplayName("input html template")
    void inputHtmlTest() throws BaseException, IOException {
        IDocumentGenerateInlineInternalRestClient client = RestClientBuilder.newBuilder()
                // set URI
                .baseUri(URI.create(documentBaseUri))
                // build API interface
                .build(IDocumentGenerateInlineInternalRestClient.class);

        DocumentGenerateWithTemplatesRequest request = documentGenerateWithTemplatesRequestBuilder.emptyPdfboxRequest();
        String template = FileUtil.readFileFromResource(DocumentServiceTestConstant.PDF_BOX_TEMPLATE);
        request.getTemplates()
                .add(new TemplateType().withTemplateName("pdfbox_template").withTemplateContent(template.getBytes(StandardCharsets.UTF_8)));
        request.getGeneratorSetup().setAddDigitalSignature(documentGenerateWithTemplatesRequestBuilder.digitalSigningType());

        Response response = client.postDocumentGenerateEntityBody(request);
        Assertions.assertEquals(200, response.getStatus());
        String filename = getFilename(response);
        Assertions.assertNotNull(filename);
        Assertions.assertTrue(filename.contains("pdf"));
        writeFileIfEnabled((InputStream) response.getEntity(), filename);
        response.close();
    }

    @Test
    @DisplayName("error html template")
    void errorHtmlTest() throws BaseException, IOException {
        IDocumentGenerateInlineInternalRestClient client = RestClientBuilder.newBuilder()
                // set URI
                .baseUri(URI.create(documentBaseUri))
                // build API interface
                .build(IDocumentGenerateInlineInternalRestClient.class);

        DocumentGenerateWithTemplatesRequest request = documentGenerateWithTemplatesRequestBuilder.emptyPdfboxRequest();
        String template = FileUtil.readFileFromResource(DocumentServiceTestConstant.PDF_BOX_TEMPLATE_ERROR);
        request.getTemplates()
                .add(new TemplateType().withTemplateName("pdfbox_template").withTemplateContent(template.getBytes(StandardCharsets.UTF_8)));

        try {
            client.postDocumentGenerateEntityBody(request);
            Assertions.fail(DocumentServiceTestConstant.EXPECTED_500);
        } catch (RestClientResponseException e) {
            Assertions.assertTrue(
                    StringUtils.contains(
                            e.getCause().getLocalizedMessage(),
                            "lineNumber: 32; columnNumber: 17; XML document structures must start and end within the same entity."),
                    "Hibás template nem az elvárt értékkel tért vissza!");
        }
    }
}
