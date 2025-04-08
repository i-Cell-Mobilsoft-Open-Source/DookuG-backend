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

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.coffee.rest.validation.xml.annotation.ValidateXML;
import hu.icellmobilsoft.dookug.api.dto.constants.IOpenapiConstants;
import hu.icellmobilsoft.dookug.api.url.DocumentGeneratePath;
import hu.icellmobilsoft.dookug.common.dto.constant.XsdConstants;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataQueryRequest;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataQueryResponse;

import static org.eclipse.microprofile.openapi.annotations.enums.SchemaType.STRING;

/**
 * REST endpoint for stored template operations
 *
 * @author mate.biro
 * @since 0.2.0
 */
@Tag(name = IOpenapiConstants.Tag.QUERY, description = IOpenapiConstants.Description.QUERY)
@Path(DocumentGeneratePath.INTERNAL_DOCUMENT_STOREDTEMPLATE)
public interface IDocumentStoredTemplateInternalRest {

    /**
     * REST interface definition for generated document paginated listing
     * 
     * @param request
     *            structured input
     * @return paginated list
     * @throws BaseException
     *             on error
     */
    @Operation(summary = "Retrieve stored template metadata based on specified filtering criteria.",
            description = "Collects and returns the metadata of generated and stored documents in a paginated list based on the filtering, sorting, and pagination parameters provided in the request body.")
    @POST
    @Path(DocumentGeneratePath.METADATA_QUERY)
    @Consumes(value = { MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.APPLICATION_JSON })
    @Produces(value = { MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.APPLICATION_JSON })
    DocumentMetadataQueryResponse postDocumentMetadataQuery(@ValidateXML(xsdPath = XsdConstants.SUPER_XSD_PATH) DocumentMetadataQueryRequest request)
            throws BaseException;
}
