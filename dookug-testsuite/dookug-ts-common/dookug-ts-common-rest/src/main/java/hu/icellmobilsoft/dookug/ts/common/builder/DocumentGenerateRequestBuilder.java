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

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.inject.Model;

import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.dookug.api.rest.builder.ParametersDataBuilder;
import hu.icellmobilsoft.dookug.schemas.common._1_0.common.ParameterType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentGenerateRequest;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentStorageMethodType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.GeneratorEngineType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.InlineGeneratorSetupType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.ResponseFormatType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.TemplateEngineType;
import hu.icellmobilsoft.dookug.ts.common.rest.DtoHelper;
import hu.icellmobilsoft.roaster.api.dto.BaseBuilder;

/**
 * Builder for {@link DocumentGenerateRequest} class
 * 
 * @author imre.scheffer
 * @since 0.1.0
 */
@Model
public class DocumentGenerateRequestBuilder extends BaseBuilder<DocumentGenerateRequest> {

    @Override
    public DocumentGenerateRequest createEmpty() {
        return new DocumentGenerateRequest();
    }

    /**
     * default init
     */
    @PostConstruct
    public void init() {
        setDto(createEmpty());
    }

    /**
     * Construct key-value filled request
     * 
     * @return Key-value filled request
     * @throws BaseException
     *             on error
     */
    public DocumentGenerateRequest parametersKeyValue() throws BaseException {
        DocumentGenerateRequest request = getDto();
        request.setContext(DtoHelper.createContext());
        InlineGeneratorSetupType setup = new InlineGeneratorSetupType();
        request.setGeneratorSetup(setup);
        setup.setTemplateEngine(TemplateEngineType.HANDLEBARS);
        setup.setGeneratorEngine(GeneratorEngineType.NONE);
        setup.setResponseFormat(ResponseFormatType.STRING);
        setup.setDocumentStorageMethod(DocumentStorageMethodType.NONE);
        setup.getParameters().add(new ParameterType().withKey("first").withValue("első"));
        setup.getParameters().add(new ParameterType().withKey("second").withValue("í123456789öüóőúűáé-.,<>#&@{};*¤ß$"));
        return request;
    }

    /**
     * Construct json filled request
     * 
     * @return Json filled request
     * @throws BaseException
     *             on error
     */
    public DocumentGenerateRequest parametersJson() throws BaseException {
        DocumentGenerateRequest request = getDto();
        request.setContext(DtoHelper.createContext());
        InlineGeneratorSetupType setup = new InlineGeneratorSetupType();
        request.setGeneratorSetup(setup);
        setup.setTemplateEngine(TemplateEngineType.HANDLEBARS);
        setup.setGeneratorEngine(GeneratorEngineType.NONE);
        setup.setResponseFormat(ResponseFormatType.STRING);
        setup.setDocumentStorageMethod(DocumentStorageMethodType.NONE);
        setup.setParametersData(
                ParametersDataBuilder.newBuilder()
                        .withTemplateParameters(DocumentGenerateWithTemplatesRequestBuilder.SIMPLE_PARAMETERS_JSON)
                        .build());
        return request;
    }

    /**
     * Construct handlebars, pdfBox, database storage method request
     *
     * @return Json filled {@link DocumentGenerateRequest}
     */
    public DocumentGenerateRequest fullFillHandlebarsPdfBoxDatabase() {
        DocumentGenerateRequest request = getDto();
        request.setContext(DtoHelper.createContext());
        InlineGeneratorSetupType setup = new InlineGeneratorSetupType();
        request.setGeneratorSetup(setup);
        setup.setTemplateEngine(TemplateEngineType.HANDLEBARS);
        setup.setGeneratorEngine(GeneratorEngineType.PDF_BOX);
        setup.setResponseFormat(ResponseFormatType.PDF);
        setup.setDocumentStorageMethod(DocumentStorageMethodType.DATABASE);
        return request;
    }
}
