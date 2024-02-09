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

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.dookug.api.rest.builder.ParametersDataBuilder;
import hu.icellmobilsoft.dookug.client.DookugClient;
import hu.icellmobilsoft.dookug.client.type.GeneratedDocumentDto;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.GeneratorEngineType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.ResponseFormatType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.TemplateEngineType;
import hu.icellmobilsoft.dookug.ts.common.constants.DocumentServiceTestConstant;
import hu.icellmobilsoft.dookug.ts.common.rest.AbstractGenerateDocumentIT;
import hu.icellmobilsoft.roaster.api.TestSuiteGroup;
import hu.icellmobilsoft.roaster.common.util.FileUtil;

/**
 * Sample {@link DookugClient} test
 *
 * @author tamas.cserhati
 * @since 0.1.0
 */
@DisplayName("Generate document from stored template with Handlebars and Saxon")
@Tag(TestSuiteGroup.JAXRS)
class PostSaxonStoredTemplateTemplateIT extends AbstractGenerateDocumentIT {

    @Inject
    private DookugClient client;

    @Test
    @DisplayName("input xslt template, output pdf")
    void inputHtmlTest() throws BaseException, IOException {
        client.setTemplateEngineType(TemplateEngineType.NONE);
        client.setGeneratorEngineType(GeneratorEngineType.SAXON);
        client.setResponseFormatType(ResponseFormatType.PDF);

        GeneratedDocumentDto resp = client.postDatabaseStoredTemplateDocumentGenerate(
                DocumentServiceTestConstant.PROJECT_STORED_TEMPLATE_NAME,
                DocumentServiceTestConstant.DEFAULT_LANGUAGE_HU,
                ParametersDataBuilder.getSaxonParameters(
                        FileUtil.readFileFromResource(DocumentServiceTestConstant.XSLT_TEMPLATE_PARAMS).getBytes(StandardCharsets.UTF_8)));

        Assertions.assertEquals(200, resp.getHttpStatus());
        InputStream responseStream = resp.getInputStream();
        writeFileIfEnabled(responseStream, resp.getFileName());
        assertIsResultPdf();
    }

}
