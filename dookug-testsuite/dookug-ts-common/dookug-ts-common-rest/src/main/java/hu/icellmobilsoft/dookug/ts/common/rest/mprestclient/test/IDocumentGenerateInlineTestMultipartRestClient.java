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
package hu.icellmobilsoft.dookug.ts.common.rest.mprestclient.test;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;

import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.dookug.api.url.DocumentGeneratePath;

/**
 * MP REST Client for inline multipart generation test endpoint.
 *
 * @author mate.biro
 * @since 2.2.0
 */
@RegisterRestClient
@Path(DocumentGeneratePath.TEST_DOCUMENT_GENERATE_INLINE)
public interface IDocumentGenerateInlineTestMultipartRestClient {

    /**
     * Test multipart endpoint.
     *
     * @param multipart
     *            multipart form-data payload
     * @param responseContentGzipped
     *            if true, the response content will be GZIP compressed
     * @return response
     * @throws BaseException
     *             on error
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path(DocumentGeneratePath.MULTIPART)
    Response postDocumentGenerateMultipart(MultipartFormDataOutput multipart,
            @QueryParam(DocumentGeneratePath.PARAM_RESPONSE_CONTENT_GZIPPED) Boolean responseContentGzipped) throws BaseException;
}
