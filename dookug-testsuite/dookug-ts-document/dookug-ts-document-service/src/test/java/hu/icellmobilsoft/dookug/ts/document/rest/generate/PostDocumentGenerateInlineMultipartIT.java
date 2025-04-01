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
package hu.icellmobilsoft.dookug.ts.document.rest.generate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.tool.utils.json.JsonUtil;
import hu.icellmobilsoft.dookug.api.rest.document.IDocumentGenerateInlineInternalRest;
import hu.icellmobilsoft.dookug.api.rest.document.form.DocumentGenerateMultipartForm;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentGenerateRequest;
import hu.icellmobilsoft.dookug.ts.common.builder.DocumentGenerateRequestBuilder;
import hu.icellmobilsoft.dookug.ts.common.config.TsConfigKey;
import hu.icellmobilsoft.dookug.ts.common.constants.DocumentServiceTestConstant;
import hu.icellmobilsoft.dookug.ts.common.rest.AbstractGenerateDocumentIT;
import hu.icellmobilsoft.dookug.ts.common.rest.mprestclient.IDocumentGenerateInlineInternalRestClient;
import hu.icellmobilsoft.roaster.api.TestSuiteGroup;
import hu.icellmobilsoft.roaster.common.util.FileUtil;

/**
 * {@link IDocumentGenerateInlineInternalRest#postDocumentGenerateMultipart(DocumentGenerateMultipartForm)} test
 *
 * @author szabolcs.gemesi
 * @since 0.0.1
 */
@Tag(TestSuiteGroup.JAXRS)
@DisplayName("Generate document with non-stored template multipart form request - multipart request")
class PostDocumentGenerateInlineMultipartIT extends AbstractGenerateDocumentIT {

    @Inject
    @ConfigProperty(name = TsConfigKey.DOOKUG_SERVICE_DOCUMENT_BASE_URI)
    private String documentBaseUri;

    @Inject
    private DocumentGenerateRequestBuilder generateRequestBuilder;

    @Test
    @DisplayName("Generate PDF document with non-stored template multipart form request")
    void testDocumentGenerateNonStoredTemplateMultipart() throws BaseException, IOException {
        IDocumentGenerateInlineInternalRestClient client = RestClientBuilder.newBuilder()
                .baseUri(URI.create(documentBaseUri))
                .build(IDocumentGenerateInlineInternalRestClient.class);
        DocumentGenerateRequest request = generateRequestBuilder.fullFillHandlebarsPdfBoxDatabase();
        request.getGeneratorSetup()
                .setParametersData(
                        templateParameterDataFromFile(DocumentServiceTestConstant.PDF_BOX_TEMPLATE_PARAMETERS));
        DocumentGenerateMultipartForm form = new DocumentGenerateMultipartForm();
        form.setRequest(request);
        System.out.println(JsonUtil.toJson(request));
        ByteArrayInputStream bis = new ByteArrayInputStream(FileUtil.readFileFromResource(DocumentServiceTestConstant.PDF_BOX_TEMPLATE).getBytes());
        form.setTemplate(bis);
        Response response = client.postDocumentGenerateMultipart(form);
        Assertions.assertEquals(200, response.getStatus());
        String filename = getFilename(response);
        Assertions.assertNotNull(filename);
        Assertions.assertTrue(filename.contains("pdf"));
        writeFileIfEnabled((InputStream) response.getEntity(), filename);
        response.close();
    }
    
    public static void main(String[] args) throws Exception {
        PostDocumentGenerateInlineMultipartIT a = new PostDocumentGenerateInlineMultipartIT();
        Calendar c = Calendar.getInstance();
        //c.set(2025, 02, 28, 0, 0, 0);
        //c.clear(Calendar.MILLISECOND);
        long s = a.getNextBankingDay(c);
        java.util.Date d2 = new Date();
        d2.setTime(s);
        System.out.println(d2);
        
    }
    
    public long getNextBankingDay(Calendar date) throws hu.icellmobilsoft.coffee.se.api.exception.TechnicalException {
        boolean ok = false;
        Calendar cal = (Calendar) date.clone();
        
        // Normalize time to midnight
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        
        java.sql.Date d = java.sql.Date.valueOf("2025-03-31");
        List<Long> nonWorkingDays = List.of(d.getTime());
        while (!ok) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
            if (!isWeekend(cal) && !nonWorkingDays.contains(cal.getTimeInMillis())) {
                ok = true;
            }
        }

        return cal.getTimeInMillis();
    }
    

    public boolean isWeekend(Calendar date) {
        int day = date.get(Calendar.DAY_OF_WEEK);
        if (Calendar.SATURDAY == day || Calendar.SUNDAY == day) {
            return true;
        }
        return false;
    }
}
