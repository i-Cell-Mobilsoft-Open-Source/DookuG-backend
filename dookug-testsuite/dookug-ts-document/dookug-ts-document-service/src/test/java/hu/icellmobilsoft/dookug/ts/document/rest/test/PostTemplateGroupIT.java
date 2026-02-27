/*-
 * #%L
 * DookuG
 * %%
 * Copyright (C) 2023 - 2026 i-Cell Mobilsoft Zrt.
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
package hu.icellmobilsoft.dookug.ts.document.rest.test;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import hu.icellmobilsoft.coffee.dto.common.commonservice.FunctionCodeType;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.TemplateEngineType;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.CreateTemplateGroupRequest;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.CreateTemplateGroupResponse;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplatePartDataType;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplatePartType;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplatePartTypeType;
import hu.icellmobilsoft.dookug.ts.base.BaseIT;
import hu.icellmobilsoft.dookug.ts.common.builder.CreateTemplateGroupRequestBuilder;
import hu.icellmobilsoft.dookug.ts.common.config.TsConfigKey;
import hu.icellmobilsoft.dookug.ts.common.rest.mprestclient.test.IDocumentStoredTemplateGroupTestRestClient;
import hu.icellmobilsoft.roaster.api.TestSuiteGroup;
import hu.icellmobilsoft.roaster.common.util.FileUtil;

/**
 * Test for creating new template group with multipart form data request.
 * 
 * @author levente.prehoda
 * @since 2.2.0
 */
@Tag(TestSuiteGroup.JAXRS)
@DisplayName("Save new template group")
class PostTemplateGroupIT extends BaseIT {

    private static final String TEMPLATE = "TEMPLATE";
    private static final String TEMPLATE_FILE_ID = "TEMPLATE_FILE_ID";
    private static final String TEMPLATE_DATA = "TEMPLATE_DATA";
    private static final List<String> LANGUAGES = List.of("HU", "EN", "DE");
    private static final String MAIN_FILE_ID = "main_file_id";
    private static final String FILE_ID_1 = "file_id_1";
    private static final String FILE_ID_2 = "file_id_2";

    @Inject
    @ConfigProperty(name = TsConfigKey.DOOKUG_SERVICE_DOCUMENT_BASE_URI)
    private String documentBaseUri;

    @Inject
    private CreateTemplateGroupRequestBuilder createTemplateGroupRequestBuilder;

    @Test
    @DisplayName("Create XSLT template group")
    void createTemplateGroup() throws BaseException {
        IDocumentStoredTemplateGroupTestRestClient client = RestClientBuilder.newBuilder()
                .baseUri(URI.create(documentBaseUri))
                .build(IDocumentStoredTemplateGroupTestRestClient.class);

        MultipartFormDataOutput multipart = new MultipartFormDataOutput();

        ByteArrayInputStream mainTemplatePartInputStream = new ByteArrayInputStream(
                FileUtil.readFileFromResource("saxon/xslt/template_main.xslt").getBytes());
        ByteArrayInputStream templatePart1InputStream = new ByteArrayInputStream(
                FileUtil.readFileFromResource("saxon/xslt/template_part_1.xslt").getBytes());
        ByteArrayInputStream templatePart2InputStream = new ByteArrayInputStream(
                FileUtil.readFileFromResource("saxon/xslt/template_part_2.xslt").getBytes());

        multipart
                .addFormData(TEMPLATE, mainTemplatePartInputStream, jakarta.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM_TYPE, "template_main.xslt");
        multipart
                .addFormData(TEMPLATE, templatePart1InputStream, jakarta.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM_TYPE, "template_part_1.xslt");
        multipart
                .addFormData(TEMPLATE, templatePart2InputStream, jakarta.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM_TYPE, "template_part_2.xslt");

        multipart.addFormData(TEMPLATE_FILE_ID, MAIN_FILE_ID, MediaType.TEXT_PLAIN_TYPE);
        multipart.addFormData(TEMPLATE_FILE_ID, FILE_ID_1, MediaType.TEXT_PLAIN_TYPE);
        multipart.addFormData(TEMPLATE_FILE_ID, FILE_ID_2, MediaType.TEXT_PLAIN_TYPE);

        TemplatePartType mainTemplatePartType = new TemplatePartType();
        mainTemplatePartType.setTemplateFileId(MAIN_FILE_ID);
        mainTemplatePartType.setTemplatePartData(
                new TemplatePartDataType().withDescription("main template part").withTemplatePartType(TemplatePartTypeType.MAIN));

        TemplatePartType templatePartType1 = new TemplatePartType();
        templatePartType1.setTemplateFileId(FILE_ID_1);
        templatePartType1.setTemplatePartData(
                new TemplatePartDataType().withTemplatePartType(TemplatePartTypeType.CONTENT)
                        .withKey(RandomStringUtils.randomAlphabetic(8))
                        .withDescription("template part 1"));

        TemplatePartType templatePartType2 = new TemplatePartType();
        templatePartType2.setTemplateFileId(FILE_ID_2);
        templatePartType2.setTemplatePartData(
                new TemplatePartDataType().withTemplatePartType(TemplatePartTypeType.CONTENT)
                        .withKey(RandomStringUtils.randomAlphabetic(8))
                        .withDescription("template part 2"));

        CreateTemplateGroupRequest request = createTemplateGroupRequestBuilder
                .withName("test_template_group_" + RandomStringUtils.randomAlphabetic(8))
                .withDescription(RandomStringUtils.randomAlphabetic(16))
                .withLanguage(LANGUAGES)
                .withValidityStart(OffsetDateTime.now())
                .withValidityEnd(OffsetDateTime.now().plusYears(1))
                .withTemplateEngine(TemplateEngineType.NONE)
                .withTemplatePartList(mainTemplatePartType, templatePartType1, templatePartType2)
                .getDto();

        multipart.addFormData(TEMPLATE_DATA, request, MediaType.APPLICATION_JSON_TYPE);

        CreateTemplateGroupResponse response = client.postStoredTemplateGroup(multipart);
        Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode());
        Assertions.assertTrue(CollectionUtils.isNotEmpty(response.getTemplateId()));
    }
}
