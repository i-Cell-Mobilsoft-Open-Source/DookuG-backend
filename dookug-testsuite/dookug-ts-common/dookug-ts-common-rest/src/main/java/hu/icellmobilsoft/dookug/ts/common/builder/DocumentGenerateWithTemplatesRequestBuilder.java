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

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.inject.Model;

import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.dookug.api.rest.builder.ParametersDataBuilder;
import hu.icellmobilsoft.dookug.schemas.common._1_0.common.ParameterType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DigitalSigningType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentGenerateRequest;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentGenerateWithTemplatesRequest;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentStorageMethodType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.GeneratorEngineType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.InlineGeneratorSetupType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.ResponseFormatType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.TemplateEngineType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.TemplateType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.generator.saxon.SaxonGeneratorParametersData;
import hu.icellmobilsoft.dookug.ts.common.constants.DocumentServiceTestConstant;
import hu.icellmobilsoft.dookug.ts.common.rest.DtoHelper;
import hu.icellmobilsoft.roaster.api.dto.BaseBuilder;
import hu.icellmobilsoft.roaster.common.util.FileUtil;

/**
 * Builder for {@link DocumentGenerateRequest} class
 * 
 * @author imre.scheffer
 * @since 0.1.0
 */
@Model
public class DocumentGenerateWithTemplatesRequestBuilder extends BaseBuilder<DocumentGenerateWithTemplatesRequest> {

    /**
     * default template
     */
    public static final String TEMPLATE = "DookuG simple test with prameters first: [{{first}}], second: [{{second}}]";
    /**
     * default parameters as map
     */
    public static final Map<String, Object> SIMPLE_PARAMETERS_JSON = Map.of(
            "first",
            "első",
            "second",
            "í123456789öüóőúűáé-.,<>#&@{};*¤ß$",
            "three",
            List.of(Map.of("sub1", "level2-1"), Map.of("sub1", "level2-2")));

    @Override
    public DocumentGenerateWithTemplatesRequest createEmpty() {
        DocumentGenerateWithTemplatesRequest request = new DocumentGenerateWithTemplatesRequest();
        request.setContext(DtoHelper.createContext());
        InlineGeneratorSetupType setup = new InlineGeneratorSetupType();
        request.setGeneratorSetup(setup);
        return request;
    }

    /**
     * default init
     */
    @PostConstruct
    public void init() {
        setDto(createEmpty());
    }

    /**
     * Construct handlebars request without template and parameters
     * 
     * @return request without template and parameters
     * @throws BaseException
     *             on error
     */
    public DocumentGenerateWithTemplatesRequest emptyHandlebarsRequest() throws BaseException {
        DocumentGenerateWithTemplatesRequest request = getDto();
        InlineGeneratorSetupType setup = request.getGeneratorSetup();
        setup.setTemplateEngine(TemplateEngineType.HANDLEBARS);
        setup.setGeneratorEngine(GeneratorEngineType.NONE);
        setup.setResponseFormat(ResponseFormatType.STRING);
        setup.setDocumentStorageMethod(DocumentStorageMethodType.NONE);
        return request;
    }

    /**
     * Construct pdfBox request without template and parameters
     * 
     * @return request without template and parameters
     * @throws BaseException
     *             on error
     */
    public DocumentGenerateWithTemplatesRequest emptyPdfboxRequest() throws BaseException {
        DocumentGenerateWithTemplatesRequest request = getDto();
        InlineGeneratorSetupType setup = request.getGeneratorSetup();
        setup.setTemplateEngine(TemplateEngineType.NONE);
        setup.setGeneratorEngine(GeneratorEngineType.PDF_BOX);
        setup.setResponseFormat(ResponseFormatType.PDF);
        setup.setDocumentStorageMethod(DocumentStorageMethodType.NONE);
        return request;
    }

    /**
     * Construct handlebars key-value filled request
     * 
     * @return Key-value filled request
     * @throws BaseException
     *             on error
     */
    public DocumentGenerateWithTemplatesRequest parametersKeyValue() throws BaseException {
        DocumentGenerateWithTemplatesRequest request = emptyHandlebarsRequest();
        InlineGeneratorSetupType setup = request.getGeneratorSetup();
        setup.getParameters().add(new ParameterType().withKey("first").withValue("első"));
        setup.getParameters().add(new ParameterType().withKey("second").withValue("í123456789öüóőúűáé-.,<>#&@{};*¤ß$"));
        request.getTemplates()
                .add(new TemplateType().withTemplateName("main").withTemplateContent(TEMPLATE.getBytes(StandardCharsets.UTF_8)).withInitial(true));
        return request;
    }

    /**
     * Construct handlebars json filled request
     * 
     * @return Json filled request
     * @throws BaseException
     *             on error
     */
    public DocumentGenerateWithTemplatesRequest simpleParametersJson() throws BaseException {
        DocumentGenerateWithTemplatesRequest request = emptyHandlebarsRequest();
        InlineGeneratorSetupType setup = request.getGeneratorSetup();

        setup.setParametersData(ParametersDataBuilder.newBuilder().withTemplateParameters(SIMPLE_PARAMETERS_JSON).build());
        request.getTemplates()
                .add(new TemplateType().withTemplateName("main").withTemplateContent(TEMPLATE.getBytes(StandardCharsets.UTF_8)).withInitial(true));
        return request;
    }

    /**
     * Constructs default {@link DigitalSigningType} object
     * 
     * @return the object we need
     */
    public DigitalSigningType digitalSigningType() {
        DigitalSigningType digitalSigningType = new DigitalSigningType();
        digitalSigningType.setSignatureProfile("sampleProfile");
        digitalSigningType.setSignatureName("signatureName");
        digitalSigningType.setSignatureReason("reason");
        return digitalSigningType;
    }

    // /**
    // * Construct saxon/xslt, database storage method request with templateTypes and parametersData
    // *
    // * @return {@link DocumentGenerateWithTemplatesRequest}
    // */
    // public DocumentGenerateWithTemplatesRequest fullFillSaxonXsltDatabase() {
    //
    // DocumentGenerateWithTemplatesRequest request = getDto();
    // InlineGeneratorSetupType setup = request.getGeneratorSetup();
    // setup.setTemplateEngine(TemplateEngineType.NONE);
    // setup.setGeneratorEngine(GeneratorEngineType.SAXON);
    // request.getTemplates()
    // .add(
    // new TemplateType().withTemplateName(DocumentServiceTestConstant.MAIN_TEMPLATE_NAME)
    // .withInitial(true)
    // .withTemplateContent(
    // FileUtil.readFileFromResource(DocumentServiceTestConstant.XSLT_PDF_TEMPLATE)
    // .getBytes(StandardCharsets.UTF_8)));
    // request.getGeneratorSetup()
    // .setParametersData(
    // ParametersDataBuilder.newBuilder()
    // .withGeneratorParameters(
    // new SaxonGeneratorParametersData().withXmlDataset(
    // FileUtil.readFileFromResource(DocumentServiceTestConstant.XSLT_TEMPLATE_PARAMS)
    // .getBytes(StandardCharsets.UTF_8)))
    // .build());
    // request.getGeneratorSetup().setDocumentStorageMethod(DocumentStorageMethodType.DATABASE);
    // request.getGeneratorSetup().setResponseFormat(ResponseFormatType.PDF);
    // return request;
    // }

    /**
     * Construct saxon/xslt, database storage method request with templateTypes and parametersData
     * 
     * @return {@link DocumentGenerateWithTemplatesRequest}
     */
    public DocumentGenerateWithTemplatesRequest fullFillSaxonXsltMultiTemplate() {

        DocumentGenerateWithTemplatesRequest request = getDto();
        InlineGeneratorSetupType setup = request.getGeneratorSetup();
        setup.setTemplateEngine(TemplateEngineType.HANDLEBARS);
        setup.setGeneratorEngine(GeneratorEngineType.SAXON);
        request.getTemplates().addAll(handlebarsXsltTemplateTypes());
        request.getGeneratorSetup()
                .setParametersData(
                        ParametersDataBuilder.newBuilder()
                                .withGeneratorParameters(
                                        new SaxonGeneratorParametersData().withXmlDataset(
                                                FileUtil.readFileFromResource(DocumentServiceTestConstant.XSLT_TEMPLATE_PARAMS)
                                                        .getBytes(StandardCharsets.UTF_8)))
                                .build());
        request.getGeneratorSetup().setDocumentStorageMethod(DocumentStorageMethodType.DATABASE);
        request.getGeneratorSetup().setResponseFormat(ResponseFormatType.PDF);
        return request;
    }

    /**
     * Construct templates for handlebars template compiler
     * 
     * @return {@link List} of {@link TemplateType}
     */
    public List<TemplateType> handlebarsXsltTemplateTypes() {
        List<TemplateType> templateTypes = new ArrayList<>();
        templateTypes.add(
                new TemplateType().withTemplateName(DocumentServiceTestConstant.XSLT_PDF_TEMPLATE_MAIN)
                        .withInitial(true)
                        .withTemplateContent(FileUtil.readFileFromResource(DocumentServiceTestConstant.XSLT_PDF_TEMPLATE_MAIN).getBytes()));
        templateTypes.add(
                new TemplateType().withTemplateName(DocumentServiceTestConstant.TEMPLATE_PART_1_NAME)
                        .withTemplateContent(FileUtil.readFileFromResource(DocumentServiceTestConstant.XSLT_PDF_TEMPLATE_PART1).getBytes()));
        templateTypes.add(
                new TemplateType().withTemplateName(DocumentServiceTestConstant.TEMPLATE_PART_2_NAME)
                        .withTemplateContent(FileUtil.readFileFromResource(DocumentServiceTestConstant.XSLT_PDF_TEMPLATE_PART2).getBytes()));
        return templateTypes;
    }
}
