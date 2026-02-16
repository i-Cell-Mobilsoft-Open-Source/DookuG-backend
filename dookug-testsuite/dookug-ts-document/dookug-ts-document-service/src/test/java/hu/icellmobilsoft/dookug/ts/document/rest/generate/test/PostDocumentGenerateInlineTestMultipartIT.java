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
package hu.icellmobilsoft.dookug.ts.document.rest.generate.test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;

import hu.icellmobilsoft.dookug.ts.common.constants.DocumentServiceTestConstant;
import hu.icellmobilsoft.roaster.common.util.FileUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.dookug.ts.common.config.TsConfigKey;
import hu.icellmobilsoft.dookug.ts.common.rest.AbstractGenerateDocumentIT;
import hu.icellmobilsoft.dookug.ts.common.rest.mprestclient.test.IDocumentGenerateInlineTestMultipartRestClient;
import hu.icellmobilsoft.roaster.api.TestSuiteGroup;

/**
 * {@link IDocumentGenerateInlineTestMultipartRestClient} test.
 *
 * @author mate.biro
 * @since 2.2.0
 */
@Tag(TestSuiteGroup.JAXRS)
@DisplayName("Generate document with test inline multipart endpoint")
class PostDocumentGenerateInlineTestMultipartIT extends AbstractGenerateDocumentIT {

    @Inject
    @ConfigProperty(name = TsConfigKey.DOOKUG_SERVICE_DOCUMENT_BASE_URI)
    private String documentBaseUri;

    @Test
    @DisplayName("Generate STRING with .txt template")
    void generateStringTxtTemplate() throws BaseException {
        IDocumentGenerateInlineTestMultipartRestClient client = RestClientBuilder.newBuilder()
                .baseUri(URI.create(documentBaseUri))
                .build(IDocumentGenerateInlineTestMultipartRestClient.class);

        MultipartFormDataOutput output = new MultipartFormDataOutput();

        ByteArrayInputStream templateInputStream = new ByteArrayInputStream(FileUtil.readFileFromResource("pdfbox/pdfbox_with_partials_template.html").getBytes());
        ByteArrayInputStream partial1InputStream = new ByteArrayInputStream(FileUtil.readFileFromResource("pdfbox/pdfbox_partial_1.html").getBytes());
        ByteArrayInputStream partial2InputStream = new ByteArrayInputStream(FileUtil.readFileFromResource("pdfbox/pdfbox_partial_2.html").getBytes());
        ByteArrayInputStream templateParameters = new ByteArrayInputStream(FileUtil.readFileFromResource("pdfbox/pdfbox_with_partials_template_parameters.json").getBytes());

        output.addFormData("TEMPLATE", templateInputStream, jakarta.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM_TYPE,
                "pdfbox_with_partials_template.html");

        output.addFormData("SUBTEMPLATE", partial1InputStream, jakarta.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM_TYPE,
                "PARTIAL_1.html");

        output.addFormData("SUBTEMPLATE", partial2InputStream, jakarta.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM_TYPE,
                "PARTIAL_2.html");

        output.addFormData("PARAMETERS_TEMPLATE_ENGINE", templateParameters, jakarta.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM_TYPE,
                "pdfbox_with_partials_template_parameters.json");

        try (Response response = client.postDocumentGenerateMultipart(output, false)) {
            Assertions.assertEquals(200, response.getStatus());
            String filename = getFilename(response);
            Assertions.assertNotNull(filename);
            writeFileIfEnabled((InputStream) response.getEntity(), filename);
        }
    }
}
