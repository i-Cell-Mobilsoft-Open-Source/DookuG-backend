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

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.coffee.rest.log.annotation.LogSpecifier;
import hu.icellmobilsoft.coffee.rest.log.annotation.LogSpecifiers;
import hu.icellmobilsoft.coffee.rest.log.annotation.enumeration.LogSpecifierTarget;
import hu.icellmobilsoft.dookug.api.dto.constants.ConfigKeys;
import hu.icellmobilsoft.dookug.api.url.DocumentGeneratePath;
import hu.icellmobilsoft.dookug.client.rest.jsonb.CustomJsonbContextResolver;

/**
 * REST client interface for requesting generated document content
 *
 * @author szabolcs.gemesi
 * @since 0.0.1
 */
@RegisterRestClient(configKey = ConfigKeys.Client.DOOKUG_CLIENT_DOCUMENT)
@RegisterProvider(CustomJsonbContextResolver.class)
@Path(DocumentGeneratePath.INTERNAL_DOCUMENT_CONTENT)
public interface IDocumentContentInternalRest extends AutoCloseable {

    /**
     * REST interface definition for getting document content
     * 
     * @param documentId
     *            document entity unique identifier
     * @param compressed
     *            if true, the response content will be GZIP compressed
     * @return Document content output stream
     * @throws BaseException
     *             on error
     */
    @GET
    @Path(DocumentGeneratePath.ID)
    @Produces(value = { MediaType.APPLICATION_OCTET_STREAM })
    @LogSpecifiers({ @LogSpecifier(target = LogSpecifierTarget.RESPONSE, maxEntityLogSize = LogSpecifier.NO_LOG),
            @LogSpecifier(target = LogSpecifierTarget.CLIENT_RESPONSE, maxEntityLogSize = LogSpecifier.NO_LOG) })
    Response getDocumentContent(@PathParam(DocumentGeneratePath.PARAM_ID) @Parameter(name = DocumentGeneratePath.PARAM_ID,
            description = "Generated document ID") String documentId,
            @QueryParam(DocumentGeneratePath.PARAM_COMPRESSED) @Parameter(name = DocumentGeneratePath.PARAM_COMPRESSED,
                    description = "If true, the response content will be GZIP compressed") Boolean compressed)
            throws BaseException;

}
