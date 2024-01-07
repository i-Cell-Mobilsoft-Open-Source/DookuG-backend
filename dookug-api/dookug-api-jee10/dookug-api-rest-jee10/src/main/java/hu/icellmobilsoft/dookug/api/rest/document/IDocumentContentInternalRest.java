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

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.coffee.rest.log.annotation.LogSpecifier;
import hu.icellmobilsoft.coffee.rest.log.annotation.LogSpecifiers;
import hu.icellmobilsoft.coffee.rest.log.annotation.enumeration.LogSpecifierTarget;
import hu.icellmobilsoft.dookug.api.rest.IOpenapiConstants;
import hu.icellmobilsoft.dookug.api.url.DocumentGeneratePath;

/**
 * REST endpoint for requesting generated document content
 *
 * @author szabolcs.gemesi
 * @since 0.0.1
 */
@Tag(name = IOpenapiConstants.Tag.DOCUMENT_CONTENT, description = IOpenapiConstants.Description.DOCUMENT_CONTENT)
@Path(DocumentGeneratePath.INTERNAL_DOCUMENT_CONTENT)
public interface IDocumentContentInternalRest {

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
    @Operation(summary = "Modulban tárolt generált dokumentum lekérdezése azonosító alapján.",
            description = "Generált és modulban tárolt dokumentum visszaadása octet-stream formátumban, illetve a fájl nevének visszaadása HTTP header-ben.\n\n"
                    + "Nem található azonosító esetén ENTITY_NOT_FOUND hibával térünk vissza.\n\n"
                    + "Adatbázisban történő dokumentumtároláshoz a dokumentum generálása során a generatorSetup.documentStorageMethod=DATABASE beállítást kell használni.")
    @Path(DocumentGeneratePath.ID)
    @Produces(value = { MediaType.APPLICATION_OCTET_STREAM })
    @LogSpecifiers({ @LogSpecifier(target = LogSpecifierTarget.RESPONSE, maxEntityLogSize = LogSpecifier.NO_LOG),
            @LogSpecifier(target = LogSpecifierTarget.CLIENT_RESPONSE, maxEntityLogSize = LogSpecifier.NO_LOG) })
    Response getDocumentContent(@PathParam(DocumentGeneratePath.PARAM_ID) @Parameter(name = DocumentGeneratePath.PARAM_ID,
            description = "Generált dokumentum azonosító") String documentId) throws BaseException;
}
