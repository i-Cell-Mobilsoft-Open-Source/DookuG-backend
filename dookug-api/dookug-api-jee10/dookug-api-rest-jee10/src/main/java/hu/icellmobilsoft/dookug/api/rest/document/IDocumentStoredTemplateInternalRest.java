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

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.coffee.rest.validation.xml.annotation.ValidateXML;
import hu.icellmobilsoft.dookug.api.rest.IOpenapiConstants;
import hu.icellmobilsoft.dookug.api.url.DocumentGeneratePath;
import hu.icellmobilsoft.dookug.common.dto.constant.XsdConstants;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataQueryRequest;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataQueryResponse;

/**
 * REST endpoint for stored template operations
 *
 * @author mate.biro
 * @since 0.2.0
 */
@Tag(name = IOpenapiConstants.Tag.STORED_TEMPLATE, description = IOpenapiConstants.Description.STORED_TEMPLATE)
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
    @Operation(summary = "Megadott szűrési feltételek alapján tárolt template metaadatok visszaadása.",
            description = "A request body-ban megadott szűrési, rendezési és lapozási paraméterek alapján a tárolt template-ek metaadatainak összegyűjtése "
                    + "és visszaadása egy lapozható listában.")
    @POST
    @Path(DocumentGeneratePath.METADATA_QUERY)
    @Consumes(value = { MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.APPLICATION_JSON })
    @Produces(value = { MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.APPLICATION_JSON })
    DocumentMetadataQueryResponse postDocumentMetadataQuery(@ValidateXML(xsdPath = XsdConstants.SUPER_XSD_PATH) DocumentMetadataQueryRequest request)
            throws BaseException;
}
