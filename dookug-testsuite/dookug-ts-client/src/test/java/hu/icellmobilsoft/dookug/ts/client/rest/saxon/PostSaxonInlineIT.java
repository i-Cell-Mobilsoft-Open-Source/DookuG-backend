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
package hu.icellmobilsoft.dookug.ts.client.rest.saxon;

import jakarta.inject.Inject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

import hu.icellmobilsoft.dookug.client.DookugClient;
import hu.icellmobilsoft.dookug.ts.common.rest.AbstractGenerateDocumentIT;
import hu.icellmobilsoft.roaster.api.TestSuiteGroup;

/**
 * Sample {@link DookugClient} test
 *
 * @author tamas.cserhati
 * @since 0.1.0
 */
@DisplayName("Generate document inline with saxon - entity body request")
@Tag(TestSuiteGroup.JAXRS)
class PostSaxonInlineIT extends AbstractGenerateDocumentIT {

    @Inject
    private DookugClient client;

//    @Test
//    @DisplayName("input xslt+xml dataset, output pdf")
//    void inlineTest() throws BaseException, IOException {
//        client.setTemplateEngineType(TemplateEngineType.NONE);
//        client.setGeneratorEngineType(GeneratorEngineType.SAXON);
//        GeneratedDocumentDto resp = client.postDocumentGenerateEntityBody(
//                // template
//                List.of(
//                        new TemplateType().withTemplateName("SAMPLE_INVOICE")
//                                .withTemplateContent(
//                                        FileUtil.readFileFromResource(DocumentServiceTestConstant.XSLT_PDF_TEMPLATE)
//                                                .getBytes(StandardCharsets.UTF_8))),
//                // parameters
//                ParametersDataBuilder.getSaxonParameters(
//                        FileUtil.readFileFromResource(DocumentServiceTestConstant.XSLT_TEMPLATE_PARAMS).getBytes(StandardCharsets.UTF_8)));
//
//        Assertions.assertEquals(200, resp.getHttpStatus());
//        InputStream responseStream = resp.getInputStream();
//        writeFileIfEnabled(responseStream, resp.getFileName());
//        assertIsResultPdf();
//    }

}
