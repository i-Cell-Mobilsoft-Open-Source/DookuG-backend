/*-
 * #%L
 * DookuG
 * %%
 * Copyright (C) 2023 - 2026 i-Cell Mobilsoft Zrt.
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
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.dookug.api.dto.constants.IOpenapiConstants;
import hu.icellmobilsoft.dookug.api.url.TemplatePath;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.CreateTemplateGroupResponse;

/**
 * REST endpoint for stored template group
 *
 * @author levente.prehoda
 * @since 2.2.0
 */
@Tag(name = IOpenapiConstants.Tag.DEV_TOOLS, description = IOpenapiConstants.Description.DEV_TOOLS)
@Path(TemplatePath.TEST_DOCUMENT_STORED_TEMPLATE_GROUP)
public interface IDocumentStoredTemplateGroupTest {

    /**
     * Creates a new template group.
     *
     * @param input
     *            multipart form data input containing the template files, filed ids, and request json
     * @return response containing the created template ids
     * @throws BaseException
     *             in case of any error during the creation of the template group
     */
    @POST
    @Operation(summary = "Creates a new template group.",
            description = "Insert new template group to the TEMPLATE, TEMPLATE_PART, TEMPLATE_TEMPLATE_PART, TEMPLATE_PART_CONTENT tables.")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(value = { MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.APPLICATION_JSON })
    CreateTemplateGroupResponse postStoredTemplateGroup(MultipartFormDataInput input) throws BaseException;
}
