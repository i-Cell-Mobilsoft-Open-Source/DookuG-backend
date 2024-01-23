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
package hu.icellmobilsoft.dookug.ts.document.rest.saxon;

import java.io.InputStream;
import java.net.URI;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentGenerateWithTemplatesRequest;
import hu.icellmobilsoft.dookug.ts.common.builder.DocumentGenerateWithTemplatesRequestBuilder;
import hu.icellmobilsoft.dookug.ts.common.config.TsConfigKey;
import hu.icellmobilsoft.dookug.ts.common.rest.AbstractGenerateDocumentIT;
import hu.icellmobilsoft.dookug.ts.common.rest.mprestclient.IDocumentGenerateInlineInternalRestClient;
import hu.icellmobilsoft.roaster.api.TestSuiteGroup;

/**
 * {@link IDocumentGenerateInlineRest#postDocumentGenerateEntityBody(DocumentGenerateWithTemplatesRequest)} test
 *
 * @author mate.biro
 * @since 0.0.1
 */
@Tag(TestSuiteGroup.JAXRS)
@DisplayName("Generate PDF document with Saxon/XSLT and non-stored template - entity body request")
class PostInlineSaxonXsltPdfTemplateIT extends AbstractGenerateDocumentIT {

    @Inject
    @ConfigProperty(name = TsConfigKey.DOOKUG_SERVICE_DOCUMENT_BASE_URI)
    private String documentBaseUri;

    @Inject
    private DocumentGenerateWithTemplatesRequestBuilder templatesRequestBuilder;

    @Inject
    private DocumentGenerateWithTemplatesRequestBuilder documentGenerateWithTemplatesRequestBuilder;

//    @Test
//    @DisplayName("Generate inline PDF document with xslt")
//    void testInlineDocumentXSLTGenerate() throws BaseException {
//        IDocumentGenerateInlineInternalRestClient client = RestClientBuilder.newBuilder()
//                .baseUri(URI.create(documentBaseUri))
//                .build(IDocumentGenerateInlineInternalRestClient.class);
//        DocumentGenerateWithTemplatesRequest request = templatesRequestBuilder.fullFillSaxonXsltDatabase();
//        request.getGeneratorSetup().setAddDigitalSignature(documentGenerateWithTemplatesRequestBuilder.digitalSigningType());
//        Response response = client.postDocumentGenerateEntityBody(request);
//        Assertions.assertEquals(200, response.getStatus());
//        String filename = getFilename(response);
//        Assertions.assertNotNull(filename);
//        Assertions.assertTrue(filename.contains("pdf"));
//        writeFileIfEnabled((InputStream) response.getEntity(), filename);
//        response.close();
//    }

    @Test
    @DisplayName("Generate inline PDF document with xslt and handlebars")
    void testInlineDocumentXSLTGenerateHandlebars() throws BaseException {
        IDocumentGenerateInlineInternalRestClient client = RestClientBuilder.newBuilder()
                .baseUri(URI.create(documentBaseUri))
                .build(IDocumentGenerateInlineInternalRestClient.class);
        DocumentGenerateWithTemplatesRequest request = templatesRequestBuilder.fullFillSaxonXsltMultiTemplate();
        Response response = client.postDocumentGenerateEntityBody(request);
        Assertions.assertEquals(200, response.getStatus());
        String filename = getFilename(response);
        Assertions.assertNotNull(filename);
        Assertions.assertTrue(filename.contains("pdf"));
        writeFileIfEnabled((InputStream) response.getEntity(), filename);
        response.close();
    }

//    @Test
//    @DisplayName("XSLT - invalid input")
//    void testInlineDocumentXSLTGenerateBadParams() throws BaseException {
//        IDocumentGenerateInlineInternalRestClient client = RestClientBuilder.newBuilder()
//                .baseUri(URI.create(documentBaseUri))
//                .build(IDocumentGenerateInlineInternalRestClient.class);
//        DocumentGenerateWithTemplatesRequest request = templatesRequestBuilder.fullFillSaxonXsltDatabase();
//        request.getGeneratorSetup().setResponseFormat(ResponseFormatType.STRING);
//        BaseException fault = Assertions.assertThrows(BaseException.class, () -> {
//            Response response = client.postDocumentGenerateEntityBody(request);
//            response.close();
//        });
//        Assertions.assertEquals(CoffeeFaultType.INVALID_INPUT, ((BaseException) fault.getCause()).getFaultTypeEnum());
//    }

}
