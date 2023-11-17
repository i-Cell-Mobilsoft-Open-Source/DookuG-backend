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
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.dookug.api.rest.document.IDocumentGenerateInlineInternalRest;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentGenerateWithTemplatesRequest;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentStorageMethodType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.GeneratorEngineType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.InlineGeneratorSetupType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.ResponseFormatType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.TemplateEngineType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.TemplateType;
import hu.icellmobilsoft.dookug.ts.common.builder.DocumentGenerateWithTemplatesRequestBuilder;
import hu.icellmobilsoft.dookug.ts.common.config.TsConfigKey;
import hu.icellmobilsoft.dookug.ts.common.constants.DocumentServiceTestConstant;
import hu.icellmobilsoft.dookug.ts.common.rest.AbstractGenerateDocumentIT;
import hu.icellmobilsoft.dookug.ts.common.rest.mprestclient.IDocumentGenerateInlineInternalRestClient;
import hu.icellmobilsoft.roaster.api.TestSuiteGroup;
import hu.icellmobilsoft.roaster.common.util.FileUtil;

/**
 * DookuG service {@link IDocumentGenerateInlineInternalRest} test
 *
 * @author tamas.cserhati
 * @since 0.1.0
 */
@DisplayName("Generate document inline with Pdfbox only - entity body request - simple template with custom font")
@Tag(TestSuiteGroup.JAXRS)
class PostInlinePdfboxHtmlTemplateCustomFontIT extends AbstractGenerateDocumentIT {

    private static final Map<String, String> PROPERTY_TEXT = Map.of("TEXT_TO_SHOW", "őúüóí ฉะนั้น บัดนี้", "TEXT_CAIRO", "یا جلاوطن نہیں کیا جائ");

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
        String template = FileUtil.readFileFromResource(DocumentServiceTestConstant.PDF_BOX_TEMPLATE_CUSTOM_FONT);
        request.setGeneratorSetup(
                new InlineGeneratorSetupType() //
                        .withParametersData(templateParameterDataFromObject(PROPERTY_TEXT)) //
                        .withGeneratorEngine(GeneratorEngineType.PDF_BOX) //
                        .withResponseFormat(ResponseFormatType.PDF) //
                        .withTemplateEngine(TemplateEngineType.HANDLEBARS) //
                        .withDocumentStorageMethod(DocumentStorageMethodType.NONE));
        request.getTemplates()
                .add(
                        new TemplateType().withTemplateName("pdfbox_template")
                                .withTemplateContent(template.getBytes(StandardCharsets.UTF_8))
                                .withInitial(true));
        Response response = client.postDocumentGenerateEntityBody(request);

        Assertions.assertEquals(200, response.getStatus());
        InputStream responseStream = (InputStream) response.getEntity();
        String generatedPdfString = new String(responseStream.readAllBytes(), StandardCharsets.UTF_8);
        Assertions.assertTrue(StringUtils.startsWith(generatedPdfString, "%PDF"), "Response object is not PDF!");
        writeFileIfEnabled(responseStream, getFilename(response));
    }
}
