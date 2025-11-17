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
package hu.icellmobilsoft.dookug.ts.client.rest.generate;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import hu.icellmobilsoft.coffee.dto.exception.BONotFoundException;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.se.util.string.RandomUtil;
import hu.icellmobilsoft.dookug.client.DookugClient;
import hu.icellmobilsoft.dookug.client.type.GeneratedDocumentDto;
import hu.icellmobilsoft.dookug.ts.common.builder.StoredTemplateDocumentGenerateRequestBuilder;
import hu.icellmobilsoft.dookug.ts.common.constants.DocumentServiceTestConstant;
import hu.icellmobilsoft.dookug.ts.common.rest.AbstractGenerateDocumentIT;
import hu.icellmobilsoft.roaster.api.TestSuiteGroup;

/**
 * {@link DookugClient} stored template client test
 *
 * @author szabolcs.gemesi
 * @since 0.0.1
 */
@Tag(TestSuiteGroup.JAXRS)
@DisplayName("Generate document with stored template client test")
class PostStoredTemplateDocumentGenerateIT extends AbstractGenerateDocumentIT {

    @Inject
    private DookugClient client;

    @Test
    @DisplayName("Generate PDF document with stored template")
    void storedTemplateDocumentGenerate() throws BaseException {
        GeneratedDocumentDto documentDto = client.postDatabaseStoredTemplateDocumentGenerate(
                DocumentServiceTestConstant.DEV_TEMPLATE_NAME,
                DocumentServiceTestConstant.DEFAULT_LANGUAGE_HU,
                OffsetDateTime.now().truncatedTo(ChronoUnit.MICROS),
                templateParameterDataFromObject(StoredTemplateDocumentGenerateRequestBuilder.getDevTemplateMainParameterData()),
                false);
        Assertions.assertNotNull(documentDto.getFileName());
        Assertions.assertTrue(documentDto.getFileName().contains("pdf"));
        writeFileIfEnabled(documentDto.getInputStream(), documentDto.getFileName());
    }

    @Test
    @DisplayName("Generate PDF document with non-stored template - BONotFound")
    void storedTemplateBONotFound() {
        try {
            client.postDatabaseStoredTemplateDocumentGenerate(
                    RandomUtil.generateId(),
                    DocumentServiceTestConstant.DEFAULT_LANGUAGE_HU,
                    OffsetDateTime.now().truncatedTo(ChronoUnit.MICROS),
                    templateParameterDataFromObject(StoredTemplateDocumentGenerateRequestBuilder.getDevTemplateMainParameterData()),
                    false);
        } catch (BaseException e) {
            Assertions.assertTrue(e.getCause() instanceof BONotFoundException);
            Assertions.assertEquals(CoffeeFaultType.ENTITY_NOT_FOUND, ((BONotFoundException) e.getCause()).getFaultTypeEnum());
        }
    }
}
