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
package hu.icellmobilsoft.dookug.client.rest;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import hu.icellmobilsoft.coffee.cdi.annotation.xml.ValidateXML;
import hu.icellmobilsoft.coffee.rest.log.annotation.LogSpecifier;
import hu.icellmobilsoft.coffee.rest.log.annotation.LogSpecifiers;
import hu.icellmobilsoft.coffee.rest.log.annotation.enumeration.LogSpecifierTarget;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.dookug.api.dto.constants.ConfigKeys;
import hu.icellmobilsoft.dookug.api.url.DocumentGeneratePath;
import hu.icellmobilsoft.dookug.client.rest.jsonb.CustomJsonbContextResolver;
import hu.icellmobilsoft.dookug.common.dto.constant.XsdConstants;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataResponse;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.StoredTemplateDocumentGenerateRequest;

/**
 * REST client interface for template based document generation
 *
 * @author tamas.cserhati
 * @since 1.0.0
 */
@RegisterRestClient(configKey = ConfigKeys.Client.DOOKUG_CLIENT_DOCUMENT)
@RegisterProvider(CustomJsonbContextResolver.class)
@Path(DocumentGeneratePath.INTERNAL_DOCUMENT_GENERATE_STOREDTEMPLATE)
public interface IDocumentGenerateStoredTemplateInternalRest extends AutoCloseable {

    /**
     * Default entity log size
     */
    int LOG_ENTITY_SIZE = 1000;

    /**
     * REST interface definition for document generation by structured input
     * 
     * @param request
     *            structured input
     * @param responseContentGzipped
     *            if true, the response content will be GZIP compressed
     * @return Generated document content output stream
     * @throws BaseException
     *             on error
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM })
    @LogSpecifiers({ @LogSpecifier(target = LogSpecifierTarget.RESPONSE, maxEntityLogSize = LogSpecifier.NO_LOG),
            @LogSpecifier(target = LogSpecifierTarget.CLIENT_RESPONSE, maxEntityLogSize = LogSpecifier.NO_LOG) })
    Response postStoredTemplateDocumentGenerate(@ValidateXML(xsdPath = XsdConstants.SUPER_XSD_PATH) StoredTemplateDocumentGenerateRequest request,
            @QueryParam(DocumentGeneratePath.PARAM_RESPONSE_CONTENT_GZIPPED) @Parameter(name = DocumentGeneratePath.PARAM_RESPONSE_CONTENT_GZIPPED,
                    description = "If true, the response content will be GZIP compressed") Boolean responseContentGzipped)
            throws BaseException;

    /**
     * REST interface definition for document generation by structured input
     * 
     * @param request
     *            structured input
     * @return Generated document metadata
     * @throws BaseException
     *             on error
     */
    @POST
    @Path(DocumentGeneratePath.METADATA)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = { MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.APPLICATION_JSON })
    @LogSpecifiers({ @LogSpecifier(target = LogSpecifierTarget.REQUEST, maxEntityLogSize = LOG_ENTITY_SIZE),
            @LogSpecifier(target = LogSpecifierTarget.CLIENT_REQUEST, maxEntityLogSize = LOG_ENTITY_SIZE) })
    DocumentMetadataResponse postStoredTemplateDocumentGenerateMetadata(
            @ValidateXML(xsdPath = XsdConstants.SUPER_XSD_PATH) StoredTemplateDocumentGenerateRequest request) throws BaseException;

}
