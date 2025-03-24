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
package hu.icellmobilsoft.dookug.ts.document.rest.sign;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.dookug.api.rest.document.form.DocumentSignMultipartForm;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentsign.DocumentSignRequest;
import hu.icellmobilsoft.dookug.ts.common.builder.DocumentSignRequestBuilder;
import hu.icellmobilsoft.dookug.ts.common.config.TsConfigKey;
import hu.icellmobilsoft.dookug.ts.common.constants.DocumentServiceTestConstant;
import hu.icellmobilsoft.dookug.ts.common.rest.AbstractGenerateDocumentIT;
import hu.icellmobilsoft.dookug.ts.common.rest.DtoHelper;
import hu.icellmobilsoft.dookug.ts.common.rest.mprestclient.IDocumentSigningInternalRestClient;
import hu.icellmobilsoft.roaster.api.TestSuiteGroup;
import hu.icellmobilsoft.roaster.common.util.FileUtil;

/**
 * {@link IDocumentSignInternalRest#postSignDocument(DocumentSignMultipartForm)} test
 *
 * @author tamas.cserhati
 * @since 1.1.0
 */
@Tag(TestSuiteGroup.JAXRS)
@DisplayName("Sign document - multipart request")
class PostDocumentSignMultipartIT extends AbstractGenerateDocumentIT {

    @Inject
    @ConfigProperty(name = TsConfigKey.DOOKUG_SERVICE_DOCUMENT_BASE_URI)
    private String documentBaseUri;

    @Inject
    private DocumentSignRequestBuilder signRequestBuilder;

    @Test
    @DisplayName("Sign PDF document multipart form request")
    void testDocumentGenerateNonStoredTemplateMultipart() throws BaseException, IOException {
        IDocumentSigningInternalRestClient client = RestClientBuilder.newBuilder()
                .baseUri(URI.create(documentBaseUri))
                .build(IDocumentSigningInternalRestClient.class);
        DocumentSignRequest request = (DocumentSignRequest) signRequestBuilder.createEmpty()
                .withDigitalSignatureProfile("sampleProfile")
                .withContext(DtoHelper.createContext());
        DocumentSignMultipartForm form = new DocumentSignMultipartForm();
        form.setRequest(request);
        ByteArrayInputStream bis = new ByteArrayInputStream(
                FileUtil.readFileFromResource(DocumentServiceTestConstant.PDF_DOCUMENT_TO_SIGN).getBytes());
        form.setDocument(bis);
        Response response = client.postSignDocumentMultipart(form);
        Assertions.assertEquals(200, response.getStatus());
        String filename = getFilename(response);
        Assertions.assertNotNull(filename);
        Assertions.assertTrue(filename.contains("pdf"));
        writeFileIfEnabled((InputStream) response.getEntity(), filename);
        
        try (PDDocument document = PDDocument.load(new File(filename))) {
            List<PDSignature> signatures = document.getSignatureDictionaries();
            Assertions.assertFalse(signatures.isEmpty());
            System.out.println(signatures.get(0).getName());
        }
        
        response.close();
    }
}
