/*-
 * #%L
 * DookuG
 * %%
 * Copyright (C) 2023 - 2025 i-Cell Mobilsoft Zrt.
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
package hu.icellmobilsoft.dookug.api.rest.test;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import hu.icellmobilsoft.coffee.cdi.annotation.xml.ValidateXML;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.dookug.api.dto.constants.IOpenapiConstants;
import hu.icellmobilsoft.dookug.api.url.DocumentGeneratePath;
import hu.icellmobilsoft.dookug.api.url.TemplatePath;
import hu.icellmobilsoft.dookug.common.dto.constant.XsdConstants;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplateQueryRequest;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplateQueryResponse;

/**
 * REST endpoint for stored template
 *
 * @author mate.biro
 * @since 2.2.0
 */
@Tag(name = IOpenapiConstants.Tag.DEV_TOOLS, description = IOpenapiConstants.Description.DEV_TOOLS)
@Path(TemplatePath.TEST_DOCUMENT_STOREDTEMPLATE)
public interface IDocumentStoredTemplateTest {

    /**
     * Retrieval of template metadata with filtering, sorting, and pagination.
     *
     * @param request
     *            TemplateQueryRequest object containing filtering, sorting, and pagination parameters
     * @return TemplateQueryResponse containing the requested template metadata
     * @throws BaseException
     *             on error
     */
    @POST
    @Operation(summary = "Retrieval of template metadata with filtering, sorting, and pagination.",
            description = "Returns template metadata from the TEMPLATE table according to the specified filtering, sorting, and pagination parameters.")
    @Path(DocumentGeneratePath.METADATA_QUERY)
    @Consumes(value = { MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.APPLICATION_JSON })
    @Produces(value = { MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.APPLICATION_JSON })
    TemplateQueryResponse postTemplateQuery(@ValidateXML(xsdPath = XsdConstants.SUPER_XSD_PATH) TemplateQueryRequest request)
            throws BaseException;
}
