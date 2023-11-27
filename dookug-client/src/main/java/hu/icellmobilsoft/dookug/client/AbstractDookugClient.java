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
import java.util.Collection;

import javax.inject.Inject;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.coffee.dto.exception.BusinessException;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.dookug.api.rest.builder.ParametersDataBuilder;
import hu.icellmobilsoft.dookug.api.rest.document.DocumentGenerateMultipartForm;
import hu.icellmobilsoft.dookug.client.rest.IDocumentContentInternalRest;
import hu.icellmobilsoft.dookug.client.rest.IDocumentGenerateInlineInternalRest;
import hu.icellmobilsoft.dookug.client.rest.IDocumentGenerateStoredTemplateInternalRest;
import hu.icellmobilsoft.dookug.client.rest.IDocumentStoredTemplateInternalRest;
import hu.icellmobilsoft.dookug.client.type.GeneratedDocumentDto;
import hu.icellmobilsoft.dookug.schemas.common._1_0.common.ParameterType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentGenerateWithTemplatesRequest;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataQueryRequest;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataQueryResponse;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataResponse;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.ParametersDataType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.StoredTemplateDocumentGenerateRequest;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.StoredTemplateGeneratorSetupType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.StoredTemplateType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.TemplateLanguageType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.TemplateStorageMethodType;

/**
 * DookuG client ososztaly
 * 
 * @author tamas.cserhati
 * @since 0.0.1
 */
public abstract class AbstractDookugClient extends AbstractBaseDookugClient {

    @Inject
    @RestClient
    private IDocumentGenerateInlineInternalRest iDocumentGenerateInlineInternalRest;

    @Inject
    @RestClient
    private IDocumentGenerateStoredTemplateInternalRest iDocumentGenerateStoredTemplateInternalRest;

    @Inject
    @RestClient
    private IDocumentContentInternalRest iDocumentContentInternalRest;

    @Inject
    @RestClient
    private IDocumentStoredTemplateInternalRest iDocumentStoredTemplateInternalRest;

    /**
     * The API call for postDocumentGenerateEntityBody
     * 
     * @param request
     *            request object
     * @return {@link GeneratedDocumentDto} object with the response
     * @throws BaseException
     *             on error
     */
    protected GeneratedDocumentDto postDocumentGenerateEntityBody(DocumentGenerateWithTemplatesRequest request) throws BaseException {
        try {
            request.getGeneratorSetup().setDocumentStorageMethod(getDocumentStorageMethodType());
            request.getGeneratorSetup().setAddDigitalSignature(getDigitalSigningType());
            Response serviceResponse = iDocumentGenerateInlineInternalRest.postDocumentGenerateEntityBody(request);

            GeneratedDocumentDto response = new GeneratedDocumentDto();
            response.setInputStream((InputStream) serviceResponse.getEntity());
            response.setFileName(getResponseFileName(serviceResponse));
            response.setHttpStatus(serviceResponse.getStatus());
            return response;
        } catch (ProcessingException e) {
            throw newDookugClientException(e);
        }
    }

    /**
     * The API call for postDocumentGenerateMultipart
     * 
     * @param request
     *            request object
     * @return {@link GeneratedDocumentDto} object with the response
     * @throws BaseException
     *             on error
     */
    protected GeneratedDocumentDto postDocumentGenerateMultipart(DocumentGenerateMultipartForm request) throws BaseException {
        try {
            request.getRequest().getGeneratorSetup().setDocumentStorageMethod(getDocumentStorageMethodType());
            request.getRequest().getGeneratorSetup().setAddDigitalSignature(getDigitalSigningType());
            Response serviceResponse = iDocumentGenerateInlineInternalRest.postDocumentGenerateMultipart(request);

            GeneratedDocumentDto response = new GeneratedDocumentDto();
            response.setInputStream((InputStream) serviceResponse.getEntity());
            response.setFileName(getResponseFileName(serviceResponse));
            response.setHttpStatus(serviceResponse.getStatus());
            return response;
        } catch (ProcessingException e) {
            throw newDookugClientException(e);
        }
    }

    /**
     * The API call for postDocumentGenerateEntityBodyMetadata
     *
     * @param request
     *            request object
     * @return {@link DocumentMetadataResponse} object with the document's metadata
     * @throws BaseException
     *             on error
     */
    protected DocumentMetadataResponse postDocumentGenerateEntityBodyMetadata(DocumentGenerateWithTemplatesRequest request) throws BaseException {
        try {

            request.getGeneratorSetup().setDocumentStorageMethod(getDocumentStorageMethodType());
            return iDocumentGenerateInlineInternalRest.postDocumentGenerateEntityBodyMetadata(request);
        } catch (ProcessingException e) {
            throw newDookugClientException(e);
        }
    }

    /**
     * The API call for postDocumentGenerateMultipartMetadata
     *
     * @param request
     *            request object
     * @return {@link DocumentMetadataResponse} object with the document's metadata
     * @throws BaseException
     *             on error
     */
    protected DocumentMetadataResponse postDocumentGenerateMultipartMetadata(DocumentGenerateMultipartForm request) throws BaseException {
        try {
            request.getRequest().getGeneratorSetup().setDocumentStorageMethod(getDocumentStorageMethodType());
            return iDocumentGenerateInlineInternalRest.postDocumentGenerateMultipartMetadata(request);
        } catch (ProcessingException e) {
            throw newDookugClientException(e);
        }
    }

    /**
     * The API call for postDocumentMetadataQuery
     *
     * @param queryRequest
     *            request object
     * @return {@link DocumentMetadataQueryResponse}
     * @throws BaseException
     *             on error
     */
    public DocumentMetadataQueryResponse postDocumentMetadataQuery(DocumentMetadataQueryRequest queryRequest) throws BaseException {
        if (queryRequest == null) {
            throw new BaseException(CoffeeFaultType.INVALID_INPUT, "queryRequest is required!");
        }
        try {
            return iDocumentStoredTemplateInternalRest.postDocumentMetadataQuery(queryRequest);
        } catch (ProcessingException e) {
            throw newDookugClientException(e);
        }
    }

    /**
     * Document generation with stored template data. Storage of template depends on implementation.
     *
     * @param templateName
     *            the unique name of the main template used for generation
     * @param templateLanguage
     *            required, the language of the template
     * @param templateValidity
     *            optional. the validity of the given print request. The dookug module has to find the template for this timestamp. Current timestamp
     *            is used by default.
     * @param templateStorageMethodType
     *            how is the template stored
     * @param parameters
     *            parameter list used by generation
     * @param parametersData
     *            the complex parameter type which can be built by the {@link ParametersDataBuilder}
     * @return {@link GeneratedDocumentDto} object with the response
     * @throws BaseException
     *             on error
     */
    public GeneratedDocumentDto postStoredTemplateDocumentGenerate(String templateName, TemplateLanguageType templateLanguage,
            OffsetDateTime templateValidity, TemplateStorageMethodType templateStorageMethodType, Collection<ParameterType> parameters,
            ParametersDataType parametersData) throws BaseException {
        if (StringUtils.isBlank(templateName) || templateStorageMethodType == null) {
            throw new BusinessException(CoffeeFaultType.INVALID_INPUT, "templateName, storageMethodType are required!");
        }
        try {
            StoredTemplateDocumentGenerateRequest request = createRequest(
                    templateStorageMethodType,
                    parameters,
                    parametersData,
                    templateName,
                    templateLanguage,
                    templateValidity);
            Response serviceResponse = iDocumentGenerateStoredTemplateInternalRest.postStoredTemplateDocumentGenerate(request);

            return convertResponseToDto(serviceResponse);
        } catch (ProcessingException e) {
            throw newDookugClientException(e);
        }
    }

    /**
     * Gets the document content by the given document id and storage method
     *
     * @param documentId
     *            document identifier
     * @return {@link GeneratedDocumentDto}
     * @throws BaseException
     *             on error
     */
    public GeneratedDocumentDto getDocumentContent(String documentId) throws BaseException {
        if (StringUtils.isBlank(documentId)) {
            throw new BusinessException(CoffeeFaultType.INVALID_INPUT, "Document id is required!");
        }

        try {
            Response response = iDocumentContentInternalRest.getDocumentContent(documentId);
            return convertResponseToDto(response);
        } catch (ProcessingException e) {
            throw newDookugClientException(e);
        }
    }

    /**
     * Document generation with stored template data. Storage of template depends on implementation.
     *
     * @param templateName
     *            the unique name of the main template used for generation
     * @param templateLanguage
     *            required, the language of the template
     * @param templateValidity
     *            optional. the validity of the given print request. The dookug module has to find the template for this timestamp. Current timestamp
     *            is used by default.
     * @param templateStorageMethodType
     *            how is the template stored
     * @param parameters
     *            parameter list used by generation
     * @param parametersData
     *            the complex parameter type which can be built by the {@link ParametersDataBuilder}
     * @return {@link GeneratedDocumentDto}
     * @throws BaseException
     *             on error
     */
    public DocumentMetadataResponse postStoredTemplateDocumentGenerateMetadata(String templateName, TemplateLanguageType templateLanguage,
            OffsetDateTime templateValidity, TemplateStorageMethodType templateStorageMethodType, Collection<ParameterType> parameters,
            ParametersDataType parametersData) throws BaseException {
        if (StringUtils.isBlank(templateName) || templateStorageMethodType == null) {
            throw new BusinessException(CoffeeFaultType.INVALID_INPUT, "templateName, storageMethodType are required!");
        }
        try {
            StoredTemplateDocumentGenerateRequest request = createRequest(
                    templateStorageMethodType,
                    parameters,
                    parametersData,
                    templateName,
                    templateLanguage,
                    templateValidity);
            return iDocumentGenerateStoredTemplateInternalRest.postStoredTemplateDocumentGenerateMetadata(request);
        } catch (ProcessingException e) {
            throw newDookugClientException(e);
        }
    }

    protected StoredTemplateDocumentGenerateRequest createRequest(TemplateStorageMethodType templateStorageMethodType,
            Collection<ParameterType> parameters, ParametersDataType parametersData, String templateName, TemplateLanguageType templateLanguage,
            OffsetDateTime templateValidity) {
        StoredTemplateDocumentGenerateRequest request = new StoredTemplateDocumentGenerateRequest();
        request.setContext(createContext());
        StoredTemplateGeneratorSetupType generatorSetup = new StoredTemplateGeneratorSetupType();
        generatorSetup.setGeneratorEngine(getGeneratorEngineType());
        generatorSetup.setTemplateEngine(getTemplateEngineType());
        generatorSetup.setTemplateStorageMethod(templateStorageMethodType);
        generatorSetup.setDocumentStorageMethod(getDocumentStorageMethodType());
        generatorSetup.setResponseFormat(getResponseFormatType());
        generatorSetup.setAddDigitalSignature(getDigitalSigningType());
        if (parameters == null || parameters.isEmpty()) {
            generatorSetup.setParametersData(parametersData);
        } else {
            generatorSetup.getParameters().addAll(parameters);
        }
        generatorSetup.setTemplate(
                new StoredTemplateType().withTemplateName(templateName).withValidityDate(templateValidity).withTemplateLanguage(templateLanguage));
        request.setGeneratorSetup(generatorSetup);
        return request;
    }
}
