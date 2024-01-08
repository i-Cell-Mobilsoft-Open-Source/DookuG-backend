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
package hu.icellmobilsoft.dookug.ts.common.builder;

import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.inject.Model;

import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.coffee.tool.utils.date.DateUtil;
import hu.icellmobilsoft.dookug.api.rest.builder.ParametersDataBuilder;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentStorageMethodType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.GeneratorEngineType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.ResponseFormatType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.StoredTemplateDocumentGenerateRequest;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.StoredTemplateGeneratorSetupType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.StoredTemplateType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.TemplateEngineType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.TemplateLanguageType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.TemplateStorageMethodType;
import hu.icellmobilsoft.dookug.ts.common.rest.DtoHelper;
import hu.icellmobilsoft.roaster.api.dto.BaseBuilder;

/**
 * Sample query {@link StoredTemplateDocumentGenerateRequest} builder class
 * 
 * @author laszlo.padar
 * @since 0.1.0
 */
@Model
public class StoredTemplateDocumentGenerateRequestBuilder extends BaseBuilder<StoredTemplateDocumentGenerateRequest> {

    @Override
    public StoredTemplateDocumentGenerateRequest createEmpty() {
        StoredTemplateDocumentGenerateRequest request = new StoredTemplateDocumentGenerateRequest();
        return request;
    }

    /**
     * default init
     */
    @PostConstruct
    public void init() {
        setDto(createEmpty());
        getDto().setContext(DtoHelper.createContext());
    }

    /**
     * create a {@link StoredTemplateDocumentGenerateRequest} for pdf+database+handlebars
     * 
     * @return the request constructed
     * @throws BaseException
     *             on error
     */
    public StoredTemplateDocumentGenerateRequest fullFill() throws BaseException {
        StoredTemplateDocumentGenerateRequest request = getDto();
        StoredTemplateGeneratorSetupType generatorSetup = new StoredTemplateGeneratorSetupType();
        generatorSetup.setGeneratorEngine(GeneratorEngineType.PDF_BOX);
        generatorSetup.setTemplateEngine(TemplateEngineType.HANDLEBARS);
        generatorSetup.setTemplateStorageMethod(TemplateStorageMethodType.DATABASE);
        StoredTemplateType template = new StoredTemplateType();
        template.setTemplateName("TMPL01");
        template.setTemplateLanguage(TemplateLanguageType.HU);
        generatorSetup.setTemplate(template);
        generatorSetup.setParametersData(ParametersDataBuilder.newBuilder().build());
        request.setGeneratorSetup(generatorSetup);
        return request;
    }

    /**
     * Construct request using pdf box generator engine, handlebars template engine, pdf response format and database storage method with the given
     * template name
     * 
     * @param templateName
     *            name of the template
     *
     * @return {@link StoredTemplateDocumentGenerateRequest}
     */
    public StoredTemplateDocumentGenerateRequest fullFillDatabaseStorage(String templateName) {
        StoredTemplateDocumentGenerateRequest request = getDto();
        StoredTemplateGeneratorSetupType generatorSetupType = new StoredTemplateGeneratorSetupType();
        generatorSetupType.setGeneratorEngine(GeneratorEngineType.PDF_BOX);
        StoredTemplateType templateType = new StoredTemplateType().withTemplateName(templateName)
                .withValidityDate(DateUtil.nowUTCTruncatedToMillis())
                .withTemplateLanguage(TemplateLanguageType.HU);
        generatorSetupType.setTemplate(templateType);
        generatorSetupType.setTemplateEngine(TemplateEngineType.HANDLEBARS);
        generatorSetupType.withResponseFormat(ResponseFormatType.PDF);
        generatorSetupType.setParametersData(ParametersDataBuilder.newBuilder().withTemplateParameters(getDevTemplateMainParameterData()).build());
        generatorSetupType.setTemplateStorageMethod(TemplateStorageMethodType.DATABASE);
        generatorSetupType.setDocumentStorageMethod(DocumentStorageMethodType.DATABASE);
        request.setGeneratorSetup(generatorSetupType);
        return request;
    }

    /**
     * Returns parameter data for the stored dev template
     * 
     * @return json parameter data
     */
    public static Map<String, Object> getDevTemplateMainParameterData() {
        List<Object> personList = List
                .of(Map.of("name", "John Doe", "age", "12"), Map.of("name", "Jane Doe", "age", "23"), Map.of("name", "Little John", "age", "0"));
        return Map.of("title", "pelda cim", "currentYear", "2023", "personList", personList);
    }
}
