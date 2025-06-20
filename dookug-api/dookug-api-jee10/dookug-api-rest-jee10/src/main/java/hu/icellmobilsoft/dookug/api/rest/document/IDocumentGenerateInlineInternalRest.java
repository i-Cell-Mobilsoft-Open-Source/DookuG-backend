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
package hu.icellmobilsoft.dookug.api.rest.document;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import hu.icellmobilsoft.coffee.cdi.annotation.xml.ValidateXML;
import hu.icellmobilsoft.coffee.rest.log.annotation.LogSpecifier;
import hu.icellmobilsoft.coffee.rest.log.annotation.LogSpecifiers;
import hu.icellmobilsoft.coffee.rest.log.annotation.enumeration.LogSpecifierTarget;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.dookug.api.dto.constants.IOpenapiConstants;
import hu.icellmobilsoft.dookug.api.rest.document.form.DocumentGenerateMultipartForm;
import hu.icellmobilsoft.dookug.api.url.DocumentGeneratePath;
import hu.icellmobilsoft.dookug.common.dto.constant.XsdConstants;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentGenerateWithTemplatesRequest;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataResponse;

/**
 * REST endpoint for template based document generation
 *
 * @author imre.scheffer
 * @since 0.1.0
 */
@Tag(name = IOpenapiConstants.Tag.DOCUMENT_GENERATE, description = IOpenapiConstants.Description.DOCUMENT_GENERATE)
@Path(DocumentGeneratePath.INTERNAL_DOCUMENT_GENERATE_INLINE)
public interface IDocumentGenerateInlineInternalRest {

    /**
     * Default entity log size
     */
    int LOG_ENTITY_SIZE = 1000;

    /**
     * REST interface definition for document generation by multipart form input
     * 
     * @param form
     *            multipart form input
     * @return Generated document content output stream
     * @throws BaseException
     *             on error
     */
    @Operation(summary = "Generates document based on the template sent in a multipart request, and returns it.",
            description = "Similar to POST " + DocumentGeneratePath.INTERNAL_DOCUMENT_GENERATE_INLINE + " but request is coming in multipart format.")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path(DocumentGeneratePath.MULTIPART)
    @LogSpecifiers({ @LogSpecifier(target = LogSpecifierTarget.REQUEST, maxEntityLogSize = LOG_ENTITY_SIZE),
            @LogSpecifier(target = LogSpecifierTarget.CLIENT_REQUEST, maxEntityLogSize = LOG_ENTITY_SIZE),
            @LogSpecifier(target = LogSpecifierTarget.RESPONSE, maxEntityLogSize = LogSpecifier.NO_LOG),
            @LogSpecifier(target = LogSpecifierTarget.CLIENT_RESPONSE, maxEntityLogSize = LogSpecifier.NO_LOG) })
    Response postDocumentGenerateMultipart(@MultipartForm DocumentGenerateMultipartForm form) throws BaseException;

    /**
     * REST interface definition for document generation by structured input
     * 
     * @param request
     *            structured input
     * @return Generated document content output stream
     * @throws BaseException
     *             on error
     */
    @Operation(summary = "Generates document based on the template sent in the request, and returns it.",
            description = "The request must include the data related to the template and the document generation process:\n\n" +
                    "* It is possible to process multiple, hierarchically ordered templates, which can be used for " +
                    "HTML-based document generation.\n" +
                    "* Template parameters are received as key-value pairs (generatorSetup/parameters) or in JSON structure " +
                    "in base64binary format (generatorSetup/parametersData).\n" +
                    "* A PDF can also be generated, optionally with an electronic signature. To enable this, the name of " +
                    "the signature profile configured in the system must be provided in the request (generatorSetup/digitalSignatureProfile) " +
                    "— the signature will then be applied to the PDF accordingly.\n" +
                    "* There is a switch (generatorSetup/documentStorageMethod) in the request to store the generated document " +
                    "in the module’s database, but we do not recommend using this feature. If the document needs to be stored, " +
                    "it should be forwarded to a dedicated service (e.g. DocStore) designed for that purpose.")
    @POST
    @Consumes(value = { MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.APPLICATION_JSON })
    @Produces(value = { MediaType.APPLICATION_OCTET_STREAM })
    @LogSpecifiers({ @LogSpecifier(target = LogSpecifierTarget.REQUEST, maxEntityLogSize = LOG_ENTITY_SIZE),
            @LogSpecifier(target = LogSpecifierTarget.CLIENT_REQUEST, maxEntityLogSize = LOG_ENTITY_SIZE),
            @LogSpecifier(target = LogSpecifierTarget.RESPONSE, maxEntityLogSize = LogSpecifier.NO_LOG),
            @LogSpecifier(target = LogSpecifierTarget.CLIENT_RESPONSE, maxEntityLogSize = LogSpecifier.NO_LOG) })
    Response postDocumentGenerateEntityBody(@ValidateXML(xsdPath = XsdConstants.SUPER_XSD_PATH) DocumentGenerateWithTemplatesRequest request)
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
    @Operation(summary = "Generates document based on the template sent in a multipart request, and returns it's metadata.",
            description = "Similar to POST " + DocumentGeneratePath.INTERNAL_DOCUMENT_GENERATE_INLINE
                    + " but request is coming in multipart format, and returns the metadata of the generated document.")
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
    @Operation(summary = "Generates document based on the template sent in the request, and returns it's metadata.",
            description = "Similar to POST " + DocumentGeneratePath.INTERNAL_DOCUMENT_GENERATE_INLINE
                    + " but returns the metadata of the generated document.")
    @POST
    @Path(DocumentGeneratePath.METADATA)
    @Consumes(value = { MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.APPLICATION_JSON })
    @Produces(value = { MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.APPLICATION_JSON })
    @LogSpecifiers({ @LogSpecifier(target = LogSpecifierTarget.REQUEST, maxEntityLogSize = LOG_ENTITY_SIZE),
            @LogSpecifier(target = LogSpecifierTarget.CLIENT_REQUEST, maxEntityLogSize = LOG_ENTITY_SIZE) })
    DocumentMetadataResponse postDocumentGenerateEntityBodyMetadata(
            @ValidateXML(xsdPath = XsdConstants.SUPER_XSD_PATH) DocumentGenerateWithTemplatesRequest request) throws BaseException;
}
