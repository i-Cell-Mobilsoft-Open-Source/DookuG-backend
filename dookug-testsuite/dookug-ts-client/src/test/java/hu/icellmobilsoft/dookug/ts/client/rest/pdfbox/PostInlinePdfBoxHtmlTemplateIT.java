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
package hu.icellmobilsoft.dookug.ts.client.rest.pdfbox;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import jakarta.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.dookug.client.DookugClient;
import hu.icellmobilsoft.dookug.client.type.GeneratedDocumentDto;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.TemplateEngineType;
import hu.icellmobilsoft.dookug.ts.client.rest.builder.DookugClientRequestHelper;
import hu.icellmobilsoft.dookug.ts.common.rest.AbstractGenerateDocumentIT;
import hu.icellmobilsoft.roaster.api.TestSuiteGroup;

/**
 * Sample {@link DookugClient} test
 *
 * @author tamas.cserhati
 * @since 0.1.0
 */
@DisplayName("Generate document inline with Handlebars only - entity body request")
@Tag(TestSuiteGroup.JAXRS)
class PostInlinePdfBoxHtmlTemplateIT extends AbstractGenerateDocumentIT {

    @Inject
    private DookugClient client;

    @Test
    @DisplayName("input html template, output pdf")
    void inputHtmlTest() throws BaseException, IOException {
        client.setTemplateEngineType(TemplateEngineType.NONE);
        Boolean compressed = false;
        GeneratedDocumentDto resp = client.postDocumentGenerateEntityBody(DookugClientRequestHelper.PdfBoxTest.readPdfTemplate(), compressed);

        Assertions.assertEquals(200, resp.getHttpStatus());
        InputStream responseStream = resp.getInputStream();
        // File pdf = new File("target/pdfbox.pdf");
        // FileUtils.copyInputStreamToFile(responseStream, pdf);
        String generatedPdfString = new String(responseStream.readAllBytes(), StandardCharsets.UTF_8);
        Assertions.assertTrue(StringUtils.startsWith(generatedPdfString, "%PDF"), "Response object is not PDF!");
    }

    @Test
    @DisplayName("error html template")
    void errorHtmlTest() {
        client.setTemplateEngineType(TemplateEngineType.NONE);

        try {
            Boolean compressed = false;
            client.postDocumentGenerateEntityBody(DookugClientRequestHelper.PdfBoxTest.readPdfTemplateError(), compressed);
            Assertions.fail("Nem hibás válasz (elvárt http státuszkód: 500)");
        } catch (BaseException e) {
            Assertions.assertNotNull(e.getCause());
            Assertions.assertTrue(
                    StringUtils.contains(
                            e.getCause().getLocalizedMessage(),
                            "lineNumber: 32; columnNumber: 17; XML document structures must start and end within the same entity."),
                    "Hibás template nem az elvárt értékkel tért vissza!");
        }
    }
}
