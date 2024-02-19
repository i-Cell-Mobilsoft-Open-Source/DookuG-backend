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
package hu.icellmobilsoft.dookug.ts.client.rest.handlebars;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.inject.Inject;

import org.apache.commons.text.StringEscapeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.dookug.client.DookugClient;
import hu.icellmobilsoft.dookug.client.type.GeneratedDocumentDto;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.GeneratorEngineType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.ResponseFormatType;
import hu.icellmobilsoft.dookug.ts.client.rest.builder.DookugClientRequestHelper;
import hu.icellmobilsoft.roaster.api.TestSuiteGroup;
import hu.icellmobilsoft.roaster.restassured.BaseConfigurableWeldIT;

/**
 * Sample {@link DookugClient} test
 *
 * @author tamas.cserhati
 * @since 0.1.0
 */
@DisplayName("Generate document inline with Handlebars only - entity body request")
@Tag(TestSuiteGroup.JAXRS)
class PostDocumentGenerateInlineEntityBodyIT extends BaseConfigurableWeldIT {

    public static final String EXPECTED_RESPONSE_STRING = "DookuG client simple test with prameters first: [első], second: [í123456789öüóőúűáé-.,<>#&@{};*¤ß$]";
    public static final String EXPECTED_EMPTY_RESPONSE_STRING = "DookuG client simple test with prameters first: [], second: []";

    public static final String EXPECTED_RESPONSE_STRING_BY_BUILT_IN_HELPER = "acb 234";

    @Inject
    private DookugClient client;

    @Test
    @DisplayName("simple Key-Value test")
    void simpleKeyValueTest() throws BaseException, IOException {
        client.setResponseFormatType(ResponseFormatType.STRING);
        client.setGeneratorEngineType(GeneratorEngineType.NONE);
        GeneratedDocumentDto response = client.postDocumentGenerateEntityBody(DookugClientRequestHelper.SimpleKeyValue.createTemplate(), //
                DookugClientRequestHelper.SimpleKeyValue.createParameters());

        Assertions.assertEquals(200, response.getHttpStatus());
        String replacedTemplate = new String(response.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        String replacedTemplateUnescaped = StringEscapeUtils.unescapeXml(replacedTemplate);
        Assertions.assertEquals(EXPECTED_RESPONSE_STRING, replacedTemplateUnescaped);
    }

    @Test
    @DisplayName("simple json test")
    void simpleJsonTest() throws BaseException, IOException {
        client.setResponseFormatType(ResponseFormatType.STRING);
        client.setGeneratorEngineType(GeneratorEngineType.NONE);
        GeneratedDocumentDto response = client.postDocumentGenerateEntityBody(DookugClientRequestHelper.SimpleJson.createTemplate(), //
                DookugClientRequestHelper.SimpleJson.createParametersData());

        Assertions.assertEquals(200, response.getHttpStatus());
        String replacedTemplate = new String(response.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        String replacedTemplateUnescaped = StringEscapeUtils.unescapeXml(replacedTemplate);
        Assertions.assertEquals(EXPECTED_RESPONSE_STRING, replacedTemplateUnescaped);
    }

    @Test
    @DisplayName("no parameter test")
    void noParamTest() throws BaseException, IOException {
        client.setResponseFormatType(ResponseFormatType.STRING);
        client.setGeneratorEngineType(GeneratorEngineType.NONE);
        GeneratedDocumentDto response = client.postDocumentGenerateEntityBody(DookugClientRequestHelper.SimpleJson.createTemplate());

        Assertions.assertEquals(200, response.getHttpStatus());
        String replacedTemplate = new String(response.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        Assertions.assertEquals(EXPECTED_EMPTY_RESPONSE_STRING, replacedTemplate);
    }

    @Test
    @DisplayName("built-in helpers test")
    void BuiltInHelperTest() throws BaseException, IOException {
        client.setResponseFormatType(ResponseFormatType.STRING);
        client.setGeneratorEngineType(GeneratorEngineType.NONE);
        GeneratedDocumentDto response = client.postDocumentGenerateEntityBody(DookugClientRequestHelper.BuiltInHelpers.createTemplate(), //
                DookugClientRequestHelper.SimpleKeyValue.createParameters());

        Assertions.assertEquals(200, response.getHttpStatus());
        String replacedTemplate = new String(response.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        String replacedTemplateUnescaped = StringEscapeUtils.unescapeXml(replacedTemplate);
        Assertions.assertEquals(EXPECTED_RESPONSE_STRING_BY_BUILT_IN_HELPER, replacedTemplateUnescaped);
    }
}
