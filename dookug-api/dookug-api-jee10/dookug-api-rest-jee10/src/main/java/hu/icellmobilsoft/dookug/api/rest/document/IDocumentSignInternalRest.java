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

import hu.icellmobilsoft.coffee.rest.log.annotation.LogSpecifier;
import hu.icellmobilsoft.coffee.rest.log.annotation.LogSpecifiers;
import hu.icellmobilsoft.coffee.rest.log.annotation.enumeration.LogSpecifierTarget;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.dookug.api.dto.constants.IOpenapiConstants;
import hu.icellmobilsoft.dookug.api.rest.document.form.DocumentSignMultipartForm;
import hu.icellmobilsoft.dookug.api.url.DocumentGeneratePath;

/**
 * REST endpoint for document signing
 *
 * @author tamas.cserhati
 * @since 1.1.0
 */
@Tag(name = IOpenapiConstants.Tag.DOCUMENT_SIGNING, description = IOpenapiConstants.Description.DOCUMENT_SIGNING)
@Path(DocumentGeneratePath.INTERNAL_DOCUMENT_SIGN_INLINE)
public interface IDocumentSignInternalRest {

    /**
     * Default entity log size
     */
    int LOG_ENTITY_SIZE = 300;

    /**
     * REST interface definition for electronic document signing
     * 
     * @param form
     *            multipart form input
     * @return Generated document content output stream
     * @throws BaseException
     *             if any error occurs
     */
    @Operation(summary = "Digitally signing the document received in a multipart request and returning it in the response.",
            description = "The document received in the request is digitally signed and returned in the response. "
                    + "The request must include the file to be signed and the name of the signing profile. "
                    + "The signing process is performed synchronously based on the parameters configured in the module for the given profile. "
                    + "The file is not stored on the server at any point.\n\n"
                    + "In the response, we receive the signed document and the filename in the HTTP header. The filename can be set in the request.")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path(DocumentGeneratePath.MULTIPART)
    @LogSpecifiers({ @LogSpecifier(target = LogSpecifierTarget.REQUEST, maxEntityLogSize = LOG_ENTITY_SIZE),
            @LogSpecifier(target = LogSpecifierTarget.CLIENT_REQUEST, maxEntityLogSize = LOG_ENTITY_SIZE),
            @LogSpecifier(target = LogSpecifierTarget.RESPONSE, maxEntityLogSize = LogSpecifier.NO_LOG),
            @LogSpecifier(target = LogSpecifierTarget.CLIENT_RESPONSE, maxEntityLogSize = LogSpecifier.NO_LOG) })
    Response postSignDocumentMultipart(@MultipartForm DocumentSignMultipartForm form) throws BaseException;
}
