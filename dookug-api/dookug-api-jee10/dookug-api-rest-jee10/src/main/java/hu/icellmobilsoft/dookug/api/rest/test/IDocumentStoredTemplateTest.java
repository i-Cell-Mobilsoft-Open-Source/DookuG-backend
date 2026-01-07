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
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.dookug.api.dto.constants.IOpenapiConstants;
import hu.icellmobilsoft.dookug.api.url.TemplatePath;
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
     * REST interface definition for template metadata querying with filtering, sorting
     *
     * @param name
     *            template name filter
     * @param language
     *            template language filter
     * @param validityStart
     *            template validity start filter
     * @param validityEnd
     *            template validity end filter
     * @param sort
     *            sorting criteria
     * @return list of template metadata
     * @throws BaseException
     *             on error
     */
    @GET
    @Operation(summary = "Retrieval of template metadata with filtering and sorting.",
            description = "Returns template metadata from the TEMPLATE table according to the specified filtering and sorting parameters.")
    @Path(TemplatePath.METADATA)
    @Consumes(value = { MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.APPLICATION_JSON })
    @Produces(value = { MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.APPLICATION_JSON })
    TemplateQueryResponse getTemplateMetaDataQuery(@QueryParam(TemplatePath.PARAM_NAME) @Parameter(name = TemplatePath.PARAM_NAME,
            description = "Template name") String name,
            @QueryParam(TemplatePath.PARAM_LANGUAGE) @Parameter(name = TemplatePath.PARAM_LANGUAGE,
                    description = "Template language") String language,
            @QueryParam(TemplatePath.PARAM_VALIDITY_START) @Parameter(name = TemplatePath.PARAM_VALIDITY_START,
                    description = "Template validity start") String validityStart,
            @QueryParam(TemplatePath.PARAM_VALIDITY_END) @Parameter(name = TemplatePath.PARAM_VALIDITY_END,
                    description = "Template validity end") String validityEnd,
            @QueryParam(TemplatePath.PARAM_SORT) @Parameter(
                    name = TemplatePath.PARAM_SORT,
                    description = "Sorting, e.g. `name:asc,language:desc,validityStart:asc`") String sort)
            throws BaseException;
}
