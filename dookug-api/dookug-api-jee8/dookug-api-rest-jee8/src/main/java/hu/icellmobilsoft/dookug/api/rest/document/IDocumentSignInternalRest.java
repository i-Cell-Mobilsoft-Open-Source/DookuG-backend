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
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.coffee.rest.log.annotation.LogSpecifier;
import hu.icellmobilsoft.coffee.rest.log.annotation.LogSpecifiers;
import hu.icellmobilsoft.coffee.rest.log.annotation.enumeration.LogSpecifierTarget;
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
                    + "The request must use multipart format. The process is synchronous, and the file is not stored. The signing is "
                    + "performed based on the parameters specified in the module configuration")
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
