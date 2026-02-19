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
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import hu.icellmobilsoft.coffee.rest.log.annotation.LogSpecifier;
import hu.icellmobilsoft.coffee.rest.log.annotation.LogSpecifiers;
import hu.icellmobilsoft.coffee.rest.log.annotation.enumeration.LogSpecifierTarget;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.dookug.api.dto.constants.IOpenapiConstants;
import hu.icellmobilsoft.dookug.api.url.DocumentGeneratePath;

/**
 * REST endpoint for template based document generation
 *
 * @author mate.biro
 * @since 2.2.0
 */
@Tag(name = IOpenapiConstants.Tag.DEV_TOOLS, description = IOpenapiConstants.Description.DEV_TOOLS)
@Path(DocumentGeneratePath.TEST_DOCUMENT_GENERATE_INLINE)
public interface IDocumentGenerateInlineTest {

    /**
     * Default entity log size
     */
    int LOG_ENTITY_SIZE = 1000;

    /**
     * REST interface definition for document generation by multipart form input
     *
     * @param input
     *            multipart input
     * @param responseContentGzipped
     *            if true, the response content will be GZIP compressed
     * @return Generated document content output stream
     * @throws BaseException
     *             on error
     */
    @Operation(summary = "Generates document based on the template sent in a multipart request, and returns it.",
            description = "The request must include the data related to the template and the document generation process:\n\n" +
                    "* Multiple hierarchically ordered templates can be processed using the Handlebars template engine.\n" +
                    "* Template parameters are received as a JSON file\n" +
                    "* SAXON generator parameters are received as an XML file\n" +
                    "* PDF files are generated without an electronic signature.\n\n" +
                    "The multipart request must contain the following parts:\n\n" +
                    "* `TEMPLATE`: the main template file (required).\n" +
                    "** Accepted extensions: `.txt`, `.html`, `.xslt`\n" +
                    "** The input part's Content-Disposition header should contain the filename with the extension. " +
                    "Validation and the response filename is based on this.\n" +
                    "* `SUBTEMPLATE`: partial template files (optional, multiple parts allowed).\n" +
                    "** Accepted extensions: `.txt`, `.html`, `.xslt` (should be the same as the TEMPLATE's)\n" +
                    "** Each input part's Content-Disposition header should contain the filename with the extension. " +
                    "Validation is based on this and should match the partial name in the template case sensitively.\n" +
                    "* `TEMPLATE_LANGUAGE`: Required only if the TEMPLATE file extension is `.xslt`\n" +
                    "* `PARAMETERS_TEMPLATE_ENGINE`: `.json` file containing the template engine parameters (optional)\n" +
                    "** The input part's Content-Disposition header should contain the filename with the extension. " +
                    "Validation is based on this.\n" +
                    "* `PARAMETERS_GENERATOR_ENGINE`: `.xml` file containing the generator parameters (optional)\n" +
                    "** The input part's Content-Disposition header should contain the filename with the extension. " +
                    "Validation is based on this.")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path(DocumentGeneratePath.MULTIPART)
    @LogSpecifiers({ @LogSpecifier(target = LogSpecifierTarget.REQUEST, maxEntityLogSize = LOG_ENTITY_SIZE),
            @LogSpecifier(target = LogSpecifierTarget.CLIENT_REQUEST, maxEntityLogSize = LOG_ENTITY_SIZE),
            @LogSpecifier(target = LogSpecifierTarget.RESPONSE, maxEntityLogSize = LogSpecifier.NO_LOG),
            @LogSpecifier(target = LogSpecifierTarget.CLIENT_RESPONSE, maxEntityLogSize = LogSpecifier.NO_LOG) })
    Response postDocumentGenerateMultipart(MultipartFormDataInput input,
            @QueryParam(DocumentGeneratePath.PARAM_RESPONSE_CONTENT_GZIPPED) @Parameter(name = DocumentGeneratePath.PARAM_RESPONSE_CONTENT_GZIPPED,
                    description = "If true, the response content will be GZIP compressed") Boolean responseContentGzipped)
            throws BaseException;
}
