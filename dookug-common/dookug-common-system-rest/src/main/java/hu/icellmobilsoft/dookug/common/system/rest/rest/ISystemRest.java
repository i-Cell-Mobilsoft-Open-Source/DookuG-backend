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
package hu.icellmobilsoft.dookug.common.system.rest.rest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;

import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.dookug.api.url.DocumentGeneratePath;
import hu.icellmobilsoft.dookug.schemas.common._1_0.config.evict.EvictResponse;

/**
 * REST endpoint for system service functions.
 * 
 * @author Imre Scheffer
 * @since 0.1.0
 */
@Path("")
public interface ISystemRest {

    /**
     * META-INF/MANIFEST.MF content in Rest response
     * 
     * @param servletRequest
     *            system servlet request
     * @return META-INF/MANIFEST.MF content in text format
     * @throws BaseException
     *             if an error occurs
     */
    @Operation(hidden = true)
    @GET
    @Path(DocumentGeneratePath.VERSION_INFO)
    @Produces(MediaType.TEXT_PLAIN)
    public String versionInfo(@Context HttpServletRequest servletRequest) throws BaseException;

    /**
     * Clear caches
     * 
     * @return the evict result
     * @throws BaseException
     *             if an error occurs
     */
    @GET
    @Operation(summary = "Clearing internal states",
            description = """
                    Iterates through implementations of the hu.icellmobilsoft.dookug.common.core.evictable.Evictable interface. \
                    Explicitly invokes the eviction function for known framework-level services.

                    Should be used when a template's content is updated, you can clear the template cache by calling \
                    the /system/evict endpoint. This ensures that the system will use the updated content.""")
    @Path(DocumentGeneratePath.SYSTEM_EVICT)
    @Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML })
    EvictResponse getEvict() throws BaseException;

}
