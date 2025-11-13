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

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.coffee.rest.log.annotation.LogSpecifier;
import hu.icellmobilsoft.coffee.rest.log.annotation.LogSpecifiers;
import hu.icellmobilsoft.coffee.rest.log.annotation.enumeration.LogSpecifierTarget;
import hu.icellmobilsoft.coffee.rest.validation.xml.annotation.ValidateXML;
import hu.icellmobilsoft.dookug.api.dto.constants.ConfigKeys;
import hu.icellmobilsoft.dookug.api.rest.document.form.DocumentGenerateMultipartForm;
import hu.icellmobilsoft.dookug.api.url.DocumentGeneratePath;
import hu.icellmobilsoft.dookug.client.rest.jsonb.CustomJsonbContextResolver;
import hu.icellmobilsoft.dookug.common.dto.constant.XsdConstants;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentGenerateWithTemplatesRequest;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataResponse;

/**
 * REST client interface for template based document generation
 * 
 * @author tamas.cserhati
 * @since 0.0.1
 */
@RegisterRestClient(configKey = ConfigKeys.Client.DOOKUG_CLIENT_DOCUMENT)
@RegisterProvider(CustomJsonbContextResolver.class)
@Path(DocumentGeneratePath.INTERNAL_DOCUMENT_GENERATE_INLINE)
public interface IDocumentGenerateInlineInternalRest extends AutoCloseable {

    /**
     * Default entity log size
     */
    int LOG_ENTITY_SIZE = 1000;

    /**
     * REST interface definition for document generation by multipart form input
     * 
     * @param form
     *            multipart form input
     * @param compressed
     *            compressed (GZIP) content indication
     * @return Generated document content output stream
     * @throws BaseException
     *             on error
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path(DocumentGeneratePath.MULTIPART)
    @LogSpecifiers({ @LogSpecifier(target = LogSpecifierTarget.REQUEST, maxEntityLogSize = LOG_ENTITY_SIZE),
            @LogSpecifier(target = LogSpecifierTarget.CLIENT_REQUEST, maxEntityLogSize = LOG_ENTITY_SIZE),
            @LogSpecifier(target = LogSpecifierTarget.RESPONSE, maxEntityLogSize = LogSpecifier.NO_LOG),
            @LogSpecifier(target = LogSpecifierTarget.CLIENT_RESPONSE, maxEntityLogSize = LogSpecifier.NO_LOG) })
    Response postDocumentGenerateMultipart(@MultipartForm DocumentGenerateMultipartForm form,
            @QueryParam(DocumentGeneratePath.PARAM_COMPRESSED) @Parameter(name = DocumentGeneratePath.PARAM_COMPRESSED,
                    description = "Query parameter name for compressed (GZIP) content indication") Boolean compressed)
            throws BaseException;

    /**
     * REST interface definition for document generation by structured input
     * 
     * @param request
     *            structured input
     * @param compressed
     *            compressed (GZIP) content indication
     * @return Generated document content output stream
     * @throws BaseException
     *             on error
     */
    @POST
    @Consumes(value = { MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.APPLICATION_JSON })
    @Produces(value = { MediaType.APPLICATION_OCTET_STREAM })
    @LogSpecifiers({ @LogSpecifier(target = LogSpecifierTarget.REQUEST, maxEntityLogSize = LOG_ENTITY_SIZE),
            @LogSpecifier(target = LogSpecifierTarget.CLIENT_REQUEST, maxEntityLogSize = LOG_ENTITY_SIZE),
            @LogSpecifier(target = LogSpecifierTarget.RESPONSE, maxEntityLogSize = LogSpecifier.NO_LOG),
            @LogSpecifier(target = LogSpecifierTarget.CLIENT_RESPONSE, maxEntityLogSize = LogSpecifier.NO_LOG) })
    Response postDocumentGenerateEntityBody(@ValidateXML(xsdPath = XsdConstants.SUPER_XSD_PATH) DocumentGenerateWithTemplatesRequest request,
            @QueryParam(DocumentGeneratePath.PARAM_COMPRESSED) @Parameter(name = DocumentGeneratePath.PARAM_COMPRESSED,
                    description = "Query parameter name for compressed (GZIP) content indication") Boolean compressed)
            throws BaseException;

    /**
     * REST interface definition for document generation by multipart form input
     * 
     * @param form
     *            multipart form input
     * @return Generated document metadata
     * @throws BaseException
     *             on error
     */
    @Path(DocumentGeneratePath.MULTIPART_METADATA)
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(value = { MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.APPLICATION_JSON })
    @LogSpecifiers({ @LogSpecifier(target = LogSpecifierTarget.REQUEST, maxEntityLogSize = LOG_ENTITY_SIZE),
            @LogSpecifier(target = LogSpecifierTarget.CLIENT_REQUEST, maxEntityLogSize = LOG_ENTITY_SIZE) })
    DocumentMetadataResponse postDocumentGenerateMultipartMetadata(@MultipartForm DocumentGenerateMultipartForm form) throws BaseException;

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
    @Consumes(value = { MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.APPLICATION_JSON })
    @Produces(value = { MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.APPLICATION_JSON })
    @LogSpecifiers({ @LogSpecifier(target = LogSpecifierTarget.REQUEST, maxEntityLogSize = LOG_ENTITY_SIZE),
            @LogSpecifier(target = LogSpecifierTarget.CLIENT_REQUEST, maxEntityLogSize = LOG_ENTITY_SIZE) })
    DocumentMetadataResponse postDocumentGenerateEntityBodyMetadata(
            @ValidateXML(xsdPath = XsdConstants.SUPER_XSD_PATH) DocumentGenerateWithTemplatesRequest request) throws BaseException;

}
