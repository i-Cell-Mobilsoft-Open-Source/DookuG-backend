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
package hu.icellmobilsoft.dookug.client;

import java.io.InputStream;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

import jakarta.enterprise.context.Dependent;

import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.dookug.api.rest.builder.ParametersDataBuilder;
import hu.icellmobilsoft.dookug.api.rest.document.form.DocumentGenerateMultipartForm;
import hu.icellmobilsoft.dookug.client.type.GeneratedDocumentDto;
import hu.icellmobilsoft.dookug.schemas.common._1_0.common.ParameterType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentGenerateRequest;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentGenerateWithTemplatesRequest;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataQueryParamsType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataQueryRequest;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataQueryResponse;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataResponse;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.InlineGeneratorSetupType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.ParametersDataType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.TemplateStorageMethodType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.TemplateType;

/**
 * Client for handling DookuG Service methods
 *
 * @author tamas.cserhati
 * @since 1.0.0
 */
@Dependent
public class DookugClient extends AbstractDookugClient {

    /**
     * Document generation with given templates and key-value parameters.
     * 
     * @param templates
     *            template list used for generation
     * @param parameters
     *            parameter list used by generation
     * @param compressed
     *            compressed (GZIP) content indication
     * @return {@link GeneratedDocumentDto} object with the response
     * @throws BaseException
     *             on error
     */
    public GeneratedDocumentDto postDocumentGenerateEntityBody(Collection<TemplateType> templates, Collection<ParameterType> parameters,
            Boolean compressed)
            throws BaseException {
        DocumentGenerateWithTemplatesRequest request = new DocumentGenerateWithTemplatesRequest()
                .withGeneratorSetup(createGeneratorSetup().withParameters(parameters))
                .withTemplates(templates);
        request.setContext(createContext());
        return postDocumentGenerateEntityBody(request, compressed);
    }

    /**
     * Document generation with given templates and json parameters.
     * 
     * @param templates
     *            template list used for generation
     * @param parametersData
     *            parameters which can be built by the {@link ParametersDataBuilder}
     * @param compressed
     *            compressed (GZIP) content indication
     * @return {@link GeneratedDocumentDto} object with the response
     * @throws BaseException
     *             on error
     */
    public GeneratedDocumentDto postDocumentGenerateEntityBody(Collection<TemplateType> templates, ParametersDataType parametersData,
            Boolean compressed)
            throws BaseException {
        DocumentGenerateWithTemplatesRequest request = new DocumentGenerateWithTemplatesRequest()
                .withGeneratorSetup(createGeneratorSetup().withParametersData(parametersData))
                .withTemplates(templates);
        request.setContext(createContext());
        return postDocumentGenerateEntityBody(request, compressed);
    }

    /**
     * Document generation with given templates
     * 
     * @param templates
     *            template list used for generation
     * @param compressed
     *            compressed (GZIP) content indication
     * @return {@link GeneratedDocumentDto} object with the response
     * @throws BaseException
     *             on error
     */
    public GeneratedDocumentDto postDocumentGenerateEntityBody(Collection<TemplateType> templates, Boolean compressed) throws BaseException {
        DocumentGenerateWithTemplatesRequest request = new DocumentGenerateWithTemplatesRequest()
                .withGeneratorSetup(createGeneratorSetup().withParametersData(null))
                .withTemplates(templates);
        request.setContext(createContext());
        return postDocumentGenerateEntityBody(request, compressed);
    }

    /**
     * Document generation with given templates and key-value parameters.
     *
     * @param templates
     *            template list used for generation
     * @param parameters
     *            parameter list used by generation
     * @return {@link DocumentMetadataResponse} object with the document's metadata
     * @throws BaseException
     *             on error
     */
    public DocumentMetadataResponse postDocumentGenerateEntityBodyMetadata(Collection<TemplateType> templates, Collection<ParameterType> parameters)
            throws BaseException {
        DocumentGenerateWithTemplatesRequest request = new DocumentGenerateWithTemplatesRequest()
                .withGeneratorSetup(createGeneratorSetup().withParameters(parameters))
                .withTemplates(templates);
        request.setContext(createContext());
        return postDocumentGenerateEntityBodyMetadata(request);
    }

    /**
     * Document generation with given templates and json parameters.
     *
     * @param templates
     *            template list used for generation
     * @param parametersData
     *            parameters which can be built by the {@link ParametersDataBuilder}
     * @return {@link DocumentMetadataResponse} object with the document's metadata
     * @throws BaseException
     *             on error
     */
    public DocumentMetadataResponse postDocumentGenerateEntityBodyMetadata(Collection<TemplateType> templates, ParametersDataType parametersData)
            throws BaseException {
        DocumentGenerateWithTemplatesRequest request = new DocumentGenerateWithTemplatesRequest()
                .withGeneratorSetup(createGeneratorSetup().withParametersData(parametersData))
                .withTemplates(templates);
        request.setContext(createContext());
        return postDocumentGenerateEntityBodyMetadata(request);
    }

    /**
     * Document generation with given templates
     *
     * @param templates
     *            template list used for generation
     * @return {@link DocumentMetadataResponse} object with the document's metadata
     * @throws BaseException
     *             on error
     */
    public DocumentMetadataResponse postDocumentGenerateEntityBodyMetadata(Collection<TemplateType> templates) throws BaseException {
        DocumentGenerateWithTemplatesRequest request = new DocumentGenerateWithTemplatesRequest()
                .withGeneratorSetup(createGeneratorSetup().withParametersData(null))
                .withTemplates(templates);
        request.setContext(createContext());
        return postDocumentGenerateEntityBodyMetadata(request);
    }

    /**
     * Document generation with given query parameters
     *
     * @param queryParams
     *            {@link DocumentMetadataQueryParamsType}
     * @return {@link DocumentMetadataQueryResponse}
     * @throws BaseException
     *             on error
     */
    public DocumentMetadataQueryResponse postDocumentMetadataQuery(DocumentMetadataQueryParamsType queryParams) throws BaseException {
        DocumentMetadataQueryRequest request = new DocumentMetadataQueryRequest().withQueryParams(queryParams);
        request.setContext(createContext());
        return postDocumentMetadataQuery(request);
    }

    /**
     * Document generation with given templates and key-value parameters.
     *
     * @param template
     *            template stream used for generation
     * @param parameters
     *            parameter list used by generation
     * @param compressed
     *            compressed (GZIP) content indication
     * @return {@link GeneratedDocumentDto} object with the response
     * @throws BaseException
     *             on error
     */
    public GeneratedDocumentDto postDocumentGenerateMultipart(InputStream template, Collection<ParameterType> parameters, Boolean compressed) throws BaseException {
        DocumentGenerateMultipartForm form = new DocumentGenerateMultipartForm();
        DocumentGenerateRequest request = new DocumentGenerateRequest().withGeneratorSetup(createGeneratorSetup().withParameters(parameters));
        request.setContext(createContext());
        form.setRequest(request);
        form.setTemplate(template);
        return postDocumentGenerateMultipart(form, compressed);
    }

    /**
     * Document generation with given templates and key-value parameters.
     *
     * @param template
     *            template stream used for generation
     * @param parameters
     *            parameter list used by generation
     * @return {@link GeneratedDocumentDto} object with the document's metadata
     * @throws BaseException
     *             on error
     */
    public DocumentMetadataResponse postDocumentGenerateMultipartMetadata(InputStream template, Collection<ParameterType> parameters)
            throws BaseException {
        DocumentGenerateMultipartForm form = new DocumentGenerateMultipartForm();
        DocumentGenerateRequest request = new DocumentGenerateRequest().withGeneratorSetup(createGeneratorSetup().withParameters(parameters));
        request.setContext(createContext());
        form.setRequest(request);
        form.setTemplate(template);
        return postDocumentGenerateMultipartMetadata(form);
    }

    /**
     * Document generation with given templates and json parameters.
     *
     * @param template
     *            template stream used for generation
     * @param parametersData
     *            parameters which can be built by the {@link ParametersDataBuilder}
     * @param compressed
     *            compressed (GZIP) content indication
     * @return {@link GeneratedDocumentDto} object with the response
     * @throws BaseException
     *             on error
     */
    public GeneratedDocumentDto postDocumentGenerateMultipart(InputStream template, ParametersDataType parametersData, Boolean compressed) throws BaseException {
        DocumentGenerateMultipartForm form = new DocumentGenerateMultipartForm();
        DocumentGenerateRequest request = new DocumentGenerateRequest().withGeneratorSetup(createGeneratorSetup().withParametersData(parametersData));
        request.setContext(createContext());
        form.setRequest(request);
        form.setTemplate(template);
        return postDocumentGenerateMultipart(form, compressed);
    }

    /**
     * Document generation with given templates and json parameters.
     *
     * @param template
     *            template stream used for generation
     * @param parametersData
     *            parameters which can be built by the {@link ParametersDataBuilder}
     * @return {@link GeneratedDocumentDto} object with the document's metadata
     * @throws BaseException
     *             on error
     */
    public DocumentMetadataResponse postDocumentGenerateMultipartMetadata(InputStream template, ParametersDataType parametersData)
            throws BaseException {
        DocumentGenerateMultipartForm form = new DocumentGenerateMultipartForm();
        DocumentGenerateRequest request = new DocumentGenerateRequest().withGeneratorSetup(createGeneratorSetup().withParametersData(parametersData));
        request.setContext(createContext());
        form.setRequest(request);
        form.setTemplate(template);
        return postDocumentGenerateMultipartMetadata(form);
    }

    /**
     * Document generation with given templates
     *
     * @param template
     *            template stream used for generation
     * @param compressed
     *            compressed (GZIP) content indication
     * @return {@link GeneratedDocumentDto} object with the response
     * @throws BaseException
     *             on error
     */
    public GeneratedDocumentDto postDocumentGenerateMultipart(InputStream template, Boolean compressed) throws BaseException {
        DocumentGenerateMultipartForm form = new DocumentGenerateMultipartForm();
        DocumentGenerateRequest request = new DocumentGenerateRequest().withGeneratorSetup(createGeneratorSetup().withParametersData(null));
        request.setContext(createContext());
        form.setRequest(request);
        form.setTemplate(template);
        return postDocumentGenerateMultipart(form, compressed);
    }

    /**
     * Document generation with given templates
     *
     * @param template
     *            template stream used for generation
     * @return {@link DocumentMetadataResponse} object with the document's metadata
     * @throws BaseException
     *             on error
     */
    public DocumentMetadataResponse postDocumentGenerateMultipartMetadata(InputStream template) throws BaseException {
        DocumentGenerateMultipartForm form = new DocumentGenerateMultipartForm();
        DocumentGenerateRequest request = new DocumentGenerateRequest().withGeneratorSetup(createGeneratorSetup().withParametersData(null));
        request.setContext(createContext());
        form.setRequest(request);
        form.setTemplate(template);
        return postDocumentGenerateMultipartMetadata(form);
    }

    private InlineGeneratorSetupType createGeneratorSetup() {
        return new InlineGeneratorSetupType().withTemplateLanguage(getTemplateLanguage())
                .withGeneratorEngine(getGeneratorEngineType())
                .withTemplateEngine(getTemplateEngineType())
                .withTemplateLanguage(getTemplateLanguage())
                .withDigitalSignatureProfile(getDigitalSignatureProfile())
                .withResponseFormat(getResponseFormatType());
    }

    /**
     * Document generation with {@link TemplateStorageMethodType#DATABASE} stored template data and current timestamp as validity
     *
     * @param templateName
     *            the unique name of the main template used for generation
     * @param templateLanguage
     *            required, the language of the template
     * @param templateValidity
     *            optional. the validity of the given print request. The dookug module has to find the template for this timestamp. Current timestamp
     *            is used by default.
     * @param parameters
     *            parameter list used by generation
     * @param compressed
     *            compressed (GZIP) content indication
     * @return {@link GeneratedDocumentDto} object with the response
     * @throws BaseException
     *             on error
     */
    public GeneratedDocumentDto postDatabaseStoredTemplateDocumentGenerate(String templateName, String templateLanguage,
            OffsetDateTime templateValidity, Collection<ParameterType> parameters, Boolean compressed) throws BaseException {
        return postStoredTemplateDocumentGenerate(
                templateName,
                templateLanguage,
                templateValidity,
                TemplateStorageMethodType.DATABASE,
                parameters,
                null,
                compressed);
    }

    /**
     * Document generation with {@link TemplateStorageMethodType#DATABASE} stored template data.
     *
     * @param templateName
     *            the unique name of the main template used for generation
     * @param templateLanguage
     *            required, the language of the template
     * @param templateValidity
     *            optional. the validity of the given print request. The dookug module has to find the template for this timestamp. Current timestamp
     *            is used by default.
     * @param parametersData
     *            the complex parameter type which can be built by the {@link ParametersDataBuilder}
     * @param compressed
     *            compressed (GZIP) content indication
     * @return {@link GeneratedDocumentDto} object with the response
     * @throws BaseException
     *             on error
     */
    public GeneratedDocumentDto postDatabaseStoredTemplateDocumentGenerate(String templateName, String templateLanguage,
            OffsetDateTime templateValidity, ParametersDataType parametersData, Boolean compressed) throws BaseException {
        return postStoredTemplateDocumentGenerate(
                templateName,
                templateLanguage,
                templateValidity,
                TemplateStorageMethodType.DATABASE,
                null,
                parametersData,
                compressed);
    }

    /**
     * Document generation with {@link TemplateStorageMethodType#DATABASE} stored template data where the validity of the given print request is the
     * current timestamp.
     *
     * @param templateName
     *            the unique name of the main template used for generation
     * @param templateLanguage
     *            required, the language of the template
     * @param parametersData
     *            the complex parameter type which can be built by the {@link ParametersDataBuilder}
     * @param compressed
     *            compressed (GZIP) content indication
     * @return {@link GeneratedDocumentDto} object with the response
     * @throws BaseException
     *             on error
     */
    public GeneratedDocumentDto postDatabaseStoredTemplateDocumentGenerate(String templateName, String templateLanguage,
            ParametersDataType parametersData, Boolean compressed) throws BaseException {
        return postStoredTemplateDocumentGenerate(
                templateName,
                templateLanguage,
                OffsetDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.MICROS),
                TemplateStorageMethodType.DATABASE,
                null,
                parametersData,
                compressed);
    }

    /**
     * Document generation with {@link TemplateStorageMethodType#DATABASE} stored template data and current timestamp as validity
     *
     * @param templateName
     *            the unique name of the main template used for generation
     * @param templateLanguage
     *            required, the language of the template
     * @param templateValidity
     *            optional. the validity of the given print request. The dookug module has to find the template for this timestamp. Current timestamp
     *            is used by default.
     * @param parameters
     *            parameter list used by generation
     * @return {@link DocumentMetadataResponse}
     * @throws BaseException
     *             on error
     */
    public DocumentMetadataResponse postDatabaseStoredTemplateDocumentGenerateMetadata(String templateName, String templateLanguage,
            OffsetDateTime templateValidity, Collection<ParameterType> parameters) throws BaseException {
        return postStoredTemplateDocumentGenerateMetadata(
                templateName,
                templateLanguage,
                templateValidity,
                TemplateStorageMethodType.DATABASE,
                parameters,
                null);
    }

    /**
     * Document generation with {@link TemplateStorageMethodType#DATABASE} stored template data.
     *
     * @param templateName
     *            the unique name of the main template used for generation
     * @param templateLanguage
     *            required, the language of the template
     * @param templateValidity
     *            optional. the validity of the given print request. The dookug module has to find the template for this timestamp. Current timestamp
     *            is used by default.
     * @param parametersData
     *            the complex parameter type which can be built by the {@link ParametersDataBuilder}
     * @return {@link DocumentMetadataResponse} object with the response
     * @throws BaseException
     *             on error
     */
    public DocumentMetadataResponse postDatabaseStoredTemplateDocumentGenerateMetadata(String templateName, String templateLanguage,
            OffsetDateTime templateValidity, ParametersDataType parametersData) throws BaseException {
        return postStoredTemplateDocumentGenerateMetadata(
                templateName,
                templateLanguage,
                templateValidity,
                TemplateStorageMethodType.DATABASE,
                null,
                parametersData);
    }
}
