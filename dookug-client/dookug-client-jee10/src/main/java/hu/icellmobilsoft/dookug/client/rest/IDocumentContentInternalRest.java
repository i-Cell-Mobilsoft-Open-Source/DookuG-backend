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

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import hu.icellmobilsoft.coffee.rest.log.annotation.LogSpecifier;
import hu.icellmobilsoft.coffee.rest.log.annotation.LogSpecifiers;
import hu.icellmobilsoft.coffee.rest.log.annotation.enumeration.LogSpecifierTarget;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.dookug.api.dto.constants.ConfigKeys;
import hu.icellmobilsoft.dookug.api.url.DocumentGeneratePath;
import hu.icellmobilsoft.dookug.client.rest.jsonb.CustomJsonbContextResolver;

/**
 * REST client interface for requesting generated document content
 *
 * @author tamas.cserhati
 * @since 1.0.0
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
            description = "Generated document ID") String documentId) throws BaseException;

}
