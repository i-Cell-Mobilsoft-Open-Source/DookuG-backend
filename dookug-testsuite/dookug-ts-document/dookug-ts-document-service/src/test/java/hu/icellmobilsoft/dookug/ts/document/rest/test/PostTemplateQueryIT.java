/*-
 * #%L
 * DookuG
 * %%
 * Copyright (C) 2023 - 2025 i-Cell Mobilsoft Zrt.
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

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import jakarta.inject.Inject;

import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import hu.icellmobilsoft.coffee.dto.common.commonservice.FunctionCodeType;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.tool.utils.date.DateUtil;
import hu.icellmobilsoft.dookug.common.model.template.enums.GeneratorEngine;
import hu.icellmobilsoft.dookug.common.model.template.enums.TemplateEngine;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplateQueryResponse;
import hu.icellmobilsoft.dookug.ts.base.BaseIT;
import hu.icellmobilsoft.dookug.ts.common.config.TsConfigKey;
import hu.icellmobilsoft.dookug.ts.common.constants.DocumentServiceTestConstant;
import hu.icellmobilsoft.dookug.ts.common.rest.mprestclient.test.IDocumentStoredTemplateTestRestClient;
import hu.icellmobilsoft.roaster.api.TestSuiteGroup;
import hu.icellmobilsoft.roaster.common.util.FileUtil;

/**
 * {@link IDocumentStoredTemplateTestRestClient#getTemplateMetaDataQuery} test
 *
 * @author mate.biro
 * @since 2.2.0
 */
@Tag(TestSuiteGroup.INTEGRATION)
@DisplayName("Template query test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostTemplateQueryIT extends BaseIT {

    @Inject
    @ConfigProperty(name = TsConfigKey.DOOKUG_SERVICE_DOCUMENT_BASE_URI)
    private String documentBaseUri;

    @Inject
    private TemplateDBHelper templateDbHelper;

    private final List<TemplateDBHelper.TemplateHierarchy> templateHierarchies = new ArrayList<>();

    @BeforeAll
    public void insert() {
        for (int i = 0; i < 2; i++) {
            TemplateDBHelper.TemplateHierarchy templateHierarchy = templateDbHelper
                    .createTemplateHierarchies(
                            "DEV_TEMPLATE_XSLT_" + i,
                            TemplateEngine.NONE,
                            GeneratorEngine.SAXON,
                            List.of("HU", "EN", "DE"),
                            List.of("DEV_TEMPLATE_XSLT_FULL_" + i),
                            FileUtil.readFileFromResource(DocumentServiceTestConstant.XSLT_PDF_TEMPLATE_MAIN).getBytes(),
                            DateUtil.nowUTC().minusDays(1),
                            DateUtil.nowUTC().plusDays(1));
            templateHierarchies.add(templateHierarchy);
        }
    }

    @AfterAll
    public void delete() {
        templateHierarchies.forEach(t -> templateDbHelper.deleteTemplateHierarchy(t));
    }

    @Test
    @DisplayName("Template query test")
    void testTemplateQuery() throws BaseException {

        IDocumentStoredTemplateTestRestClient storedTemplateTestRestClient = RestClientBuilder.newBuilder()
                .baseUri(URI.create(documentBaseUri))
                .build(IDocumentStoredTemplateTestRestClient.class);

        TemplateQueryResponse response = storedTemplateTestRestClient.getTemplateMetaDataQuery(null, null, null, null, "lastUpdatedAt:DESC,name");

        Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode());
        Assertions.assertTrue(CollectionUtils.isNotEmpty(response.getRowList()));
    }
}
