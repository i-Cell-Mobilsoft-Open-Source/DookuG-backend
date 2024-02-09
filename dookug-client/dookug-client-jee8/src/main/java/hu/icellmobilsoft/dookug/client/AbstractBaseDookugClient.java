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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.coffee.dto.common.commonservice.ContextType;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.coffee.tool.utils.date.DateUtil;
import hu.icellmobilsoft.dookug.client.exception.DookugClientException;
import hu.icellmobilsoft.dookug.client.type.GeneratedDocumentDto;
import hu.icellmobilsoft.dookug.client.util.RandomUtil;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DigitalSigningType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentStorageMethodType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.GeneratorEngineType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.ResponseFormatType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.TemplateEngineType;

/**
 * DookuG client ososztaly
 * 
 * @author tamas.cserhati
 * @since 0.0.1
 */
public abstract class AbstractBaseDookugClient {

    private static final Pattern FILENAME_PATTERN = Pattern.compile("filename=\"([^\"]*)\"");

    private GeneratorEngineType generatorEngineType = GeneratorEngineType.PDF_BOX;
    private TemplateEngineType templateEngineType = TemplateEngineType.HANDLEBARS;
    private ResponseFormatType responseFormatType = ResponseFormatType.PDF;
    private String templateLanguage = "HU";
    private DigitalSigningType digitalSigningType;

    private DocumentStorageMethodType documentStorageMethodType = DocumentStorageMethodType.NONE;

    /**
     * create default {@link ContextType}
     * 
     * @return the default {@link ContextType}
     */
    protected ContextType createContext() {
        ContextType context = new ContextType();
        String rand = RandomUtil.generateId();
        context.setRequestId(rand);
        context.setTimestamp(DateUtil.nowUTCTruncatedToMillis());
        return context;
    }

    /**
     * Create new {@link DookugClientException}
     * 
     * @param e
     *            the exception
     * @return the new {@link DookugClientException}
     */
    protected DookugClientException newDookugClientException(Throwable e) {
        return new DookugClientException(CoffeeFaultType.SERVICE_CALL_FAILED, e.getLocalizedMessage(), e);
    }

    /**
     * Get filename from http response
     * 
     * @param response
     *            the http response
     * @return the file name or null
     */
    protected String getResponseFileName(Response response) {
        if (response == null) {
            return null;
        }
        String headerContentDispostition = response.getHeaderString(HttpHeaders.CONTENT_DISPOSITION);
        if (StringUtils.isBlank(headerContentDispostition)) {
            return null;
        }
        Matcher m = FILENAME_PATTERN.matcher(headerContentDispostition);
        if (m.find()) {
            return m.group(1);
        }
        return null;
    }

    /**
     * Convert {@link Response} to {@link GeneratedDocumentDto}
     * 
     * @param serviceResponse
     *            the rest response
     * @return the dto
     */
    protected GeneratedDocumentDto convertResponseToDto(Response serviceResponse) {
        GeneratedDocumentDto response = new GeneratedDocumentDto();
        response.setInputStream((InputStream) serviceResponse.getEntity());
        response.setFileName(getResponseFileName(serviceResponse));
        response.setHttpStatus(serviceResponse.getStatus());
        return response;
    }

    /**
     * @return the generatorEngineType
     */
    public GeneratorEngineType getGeneratorEngineType() {
        return generatorEngineType;
    }

    /**
     * @param generatorEngineType
     *            the generatorEngineType to set, its PDF_BOX by default
     */
    public void setGeneratorEngineType(GeneratorEngineType generatorEngineType) {
        this.generatorEngineType = generatorEngineType;
    }

    /**
     * @return the templateEngineType
     */
    public TemplateEngineType getTemplateEngineType() {
        return templateEngineType;
    }

    /**
     * @param templateEngineType
     *            the templateEngineType to set, its HANDLEBARS by default
     */
    public void setTemplateEngineType(TemplateEngineType templateEngineType) {
        this.templateEngineType = templateEngineType;
    }

    /**
     * @return the responseFormatType
     */
    public ResponseFormatType getResponseFormatType() {
        return responseFormatType;
    }

    /**
     * @param responseFormatType
     *            the responseFormatType to set, its PDF by default
     */
    public void setResponseFormatType(ResponseFormatType responseFormatType) {
        this.responseFormatType = responseFormatType;
    }

    /**
     * @return the documentStorageMethodType
     */
    public DocumentStorageMethodType getDocumentStorageMethodType() {
        return documentStorageMethodType;
    }

    /**
     * @param documentStorageMethodType
     *            the documentStorageMethodType to set, its NONE by default
     */
    public void setDocumentStorageMethodType(DocumentStorageMethodType documentStorageMethodType) {
        this.documentStorageMethodType = documentStorageMethodType;
    }

    /**
     * @return the templateLanguageType
     */
    public String getTemplateLanguage() {
        return templateLanguage;
    }

    /**
     * @param templateLanguage
     *            the templateLanguage to set
     */
    public void setTemplateLanguage(String templateLanguage) {
        this.templateLanguage = templateLanguage;
    }

    /**
     * @return the digitalSigningType
     */
    public DigitalSigningType getDigitalSigningType() {
        return digitalSigningType;
    }

    /**
     * @param digitalSigningType
     *            the digitalSigningType to set
     */
    public void setDigitalSigningType(DigitalSigningType digitalSigningType) {
        this.digitalSigningType = digitalSigningType;
    }
}
