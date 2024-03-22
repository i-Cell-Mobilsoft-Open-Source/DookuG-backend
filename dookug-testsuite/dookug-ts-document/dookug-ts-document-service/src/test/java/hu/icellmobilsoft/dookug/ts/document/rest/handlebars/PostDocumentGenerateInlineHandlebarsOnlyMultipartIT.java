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
package hu.icellmobilsoft.dookug.ts.document.rest.handlebars;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import org.apache.commons.text.StringEscapeUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.dookug.api.rest.document.DocumentGenerateMultipartForm;
import hu.icellmobilsoft.dookug.api.rest.document.IDocumentGenerateInlineInternalRest;
import hu.icellmobilsoft.dookug.ts.common.builder.DocumentGenerateRequestBuilder;
import hu.icellmobilsoft.dookug.ts.common.config.TsConfigKey;
import hu.icellmobilsoft.dookug.ts.common.rest.mprestclient.IDocumentGenerateInlineInternalRestClient;
import hu.icellmobilsoft.roaster.api.TestSuiteGroup;
import hu.icellmobilsoft.roaster.restassured.BaseConfigurableWeldIT;

/**
 * Sample service {@link IDocumentGenerateInlineInternalRest} test
 *
 * @author imre.scheffer
 * @since 0.1.0
 */
@DisplayName("Generate document inline with Handlebars only - multipart request")
@Tag(TestSuiteGroup.JAXRS)
class PostDocumentGenerateInlineHandlebarsOnlyMultipartIT extends BaseConfigurableWeldIT {

    public static final String TEMPLATE = "DookuG simple test with prameters first: [{{first}}], second: [{{second}}]";
    public static final String RESPONSE_STRING = "DookuG simple test with prameters first: [első], second: [í123456789öüóőúűáé-.,<>#&@{};*¤ß$]";
    public static final String EMPTY_RESPONSE_STRING = "DookuG simple test with prameters first: [], second: []";

    @Inject
    @ConfigProperty(name = TsConfigKey.DOOKUG_SERVICE_DOCUMENT_BASE_URI)
    private String documentBaseUri;

    @Inject
    private DocumentGenerateRequestBuilder documentGenerateRequestBuilder;

    @Test
    @DisplayName("simple Key-Value test")
    void simpleKeyValueTest() throws BaseException, IOException {
        IDocumentGenerateInlineInternalRestClient client = RestClientBuilder.newBuilder()
                // set URI
                .baseUri(URI.create(documentBaseUri))
                // build API interface
                .build(IDocumentGenerateInlineInternalRestClient.class);
        DocumentGenerateMultipartForm form = new DocumentGenerateMultipartForm();

        form.setTemplate(toInputStream(TEMPLATE));

        form.setRequest(documentGenerateRequestBuilder.parametersKeyValue());

        Response response = client.postDocumentGenerateMultipart(form);
        Assertions.assertEquals(200, response.getStatus());
        InputStream responseStream = (InputStream) response.getEntity();
        String replacedTemplate = StringEscapeUtils.unescapeXml(new String(responseStream.readAllBytes(), StandardCharsets.UTF_8));
        Assertions.assertEquals(RESPONSE_STRING, replacedTemplate);
    }

    @Test
    @DisplayName("simple json test")
    void simpleJsonTest() throws BaseException, IOException {
        IDocumentGenerateInlineInternalRestClient client = RestClientBuilder.newBuilder()
                // set URI
                .baseUri(URI.create(documentBaseUri))
                // build API interface
                .build(IDocumentGenerateInlineInternalRestClient.class);
        DocumentGenerateMultipartForm form = new DocumentGenerateMultipartForm();

        form.setTemplate(toInputStream(TEMPLATE));

        form.setRequest(documentGenerateRequestBuilder.parametersJson());

        Response response = client.postDocumentGenerateMultipart(form);
        Assertions.assertEquals(200, response.getStatus());
        InputStream responseStream = (InputStream) response.getEntity();
        String replacedTemplate = StringEscapeUtils.unescapeXml(new String(responseStream.readAllBytes(), StandardCharsets.UTF_8));
        Assertions.assertEquals(RESPONSE_STRING, replacedTemplate);
    }

    @Test
    @DisplayName("no parameter test")
    void noParamTest() throws BaseException, IOException {
        IDocumentGenerateInlineInternalRestClient client = RestClientBuilder.newBuilder()
                // set URI
                .baseUri(URI.create(documentBaseUri))
                // build API interface
                .build(IDocumentGenerateInlineInternalRestClient.class);
        DocumentGenerateMultipartForm form = new DocumentGenerateMultipartForm();

        form.setTemplate(toInputStream(TEMPLATE));

        form.setRequest(documentGenerateRequestBuilder.parametersJson());
        form.getRequest().getGeneratorSetup().setParametersData(null);

        Response response = client.postDocumentGenerateMultipart(form);
        Assertions.assertEquals(200, response.getStatus());
        InputStream responseStream = (InputStream) response.getEntity();
        String replacedTemplate = new String(responseStream.readAllBytes(), StandardCharsets.UTF_8);
        Assertions.assertEquals(EMPTY_RESPONSE_STRING, replacedTemplate);
    }

    private InputStream toInputStream(String text) throws BaseException {
        return new ByteArrayInputStream(text.getBytes());
    }
}
