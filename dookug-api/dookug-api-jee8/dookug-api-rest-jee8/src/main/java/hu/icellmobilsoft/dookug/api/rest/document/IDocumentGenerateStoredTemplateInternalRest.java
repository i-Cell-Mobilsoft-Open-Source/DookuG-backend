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

import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.coffee.rest.log.annotation.LogSpecifier;
import hu.icellmobilsoft.coffee.rest.log.annotation.LogSpecifiers;
import hu.icellmobilsoft.coffee.rest.log.annotation.enumeration.LogSpecifierTarget;
import hu.icellmobilsoft.coffee.rest.validation.xml.annotation.ValidateXML;
import hu.icellmobilsoft.dookug.api.rest.IOpenapiConstants;
import hu.icellmobilsoft.dookug.api.url.DocumentGeneratePath;
import hu.icellmobilsoft.dookug.common.dto.constant.XsdConstants;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataResponse;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.StoredTemplateDocumentGenerateRequest;

/**
 * REST endpoint for template based document generation
 *
 * @author laszlo.padar
 * @since 0.1.0
 */
@Tag(name = IOpenapiConstants.Tag.DOCUMENT_GENERATE, description = IOpenapiConstants.Description.DOCUMENT_GENERATE)
@Path(DocumentGeneratePath.INTERNAL_DOCUMENT_GENERATE_STOREDTEMPLATE)
public interface IDocumentGenerateStoredTemplateInternalRest {

    /**
     * Default entity log size
     */
    int LOG_ENTITY_SIZE = 1000;

    /**
     * REST interface definition for document generation by structured input
     * 
     * @param request
     *            structured input
     * @return Generated document content output stream
     * @throws BaseException
     *             on error
     */
    @Operation(summary = "Dokumentum generálása tárolt template és request-ben megadott beállítások alapján",
            description = "A modul adatbázisában eltárolt template, illetve a request-ben kapott paraméterek és beállítások alapján dokumentum generálása és visszaadása.")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM })
    @LogSpecifiers({ @LogSpecifier(target = LogSpecifierTarget.RESPONSE, maxEntityLogSize = LogSpecifier.NO_LOG),
            @LogSpecifier(target = LogSpecifierTarget.CLIENT_RESPONSE, maxEntityLogSize = LogSpecifier.NO_LOG) })
    Response postStoredTemplateDocumentGenerate(@ValidateXML(xsdPath = XsdConstants.SUPER_XSD_PATH) StoredTemplateDocumentGenerateRequest request)
            throws BaseException;

    /**
     * REST interface definition for document generation by structured input
     * 
     * @param request
     *            structured input
     * @return Generated document metadata
     * @throws BaseException
     *             on error
     */
    @Operation(summary = "Dokumentum generálása és metaadatok visszaadása tárolt template és request-ben megadott beállítások alapján",
            description = "A POST " + DocumentGeneratePath.INTERNAL_DOCUMENT_GENERATE_STOREDTEMPLATE
                    + " végponthoz hasonló működés, azonban itt a generált dokumentum metaadataival térünk vissza.")
    @POST
    @Path(DocumentGeneratePath.METADATA)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = { MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.APPLICATION_JSON })
    @LogSpecifiers({ @LogSpecifier(target = LogSpecifierTarget.REQUEST, maxEntityLogSize = LOG_ENTITY_SIZE),
            @LogSpecifier(target = LogSpecifierTarget.CLIENT_REQUEST, maxEntityLogSize = LOG_ENTITY_SIZE) })
    DocumentMetadataResponse postStoredTemplateDocumentGenerateMetadata(
            @ValidateXML(xsdPath = XsdConstants.SUPER_XSD_PATH) StoredTemplateDocumentGenerateRequest request) throws BaseException;
}
