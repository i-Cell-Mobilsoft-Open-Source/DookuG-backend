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
package hu.icellmobilsoft.dookug.document.service.action;

import jakarta.enterprise.inject.Model;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.apache.commons.lang3.BooleanUtils;

import hu.icellmobilsoft.coffee.dto.exception.InvalidParameterException;
import hu.icellmobilsoft.coffee.rest.utils.ResponseUtil;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.tool.utils.compress.GZIPUtil;
import hu.icellmobilsoft.dookug.common.cdi.StorageMethodQualifier;
import hu.icellmobilsoft.dookug.common.cdi.document.Document;
import hu.icellmobilsoft.dookug.common.cdi.template.ITemplateStore;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataResponse;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.StoredTemplateDocumentGenerateRequest;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.StoredTemplateGeneratorSetupType;

/**
 * Stored template based document generation action
 *
 * @author laszlo.padar
 * @since 0.1.0
 */
@Model
public class StoredTemplateDocumentGenerateAction extends BaseDocumentGenerateAction {

    /**
     * Document generation with stored template data. Storage of template depends on implementation.
     * 
     * @param request
     *            {@link StoredTemplateDocumentGenerateRequest} Request dto
     * @param responseContentGzipped
     *            if true, the response content will be GZIP compressed
     * @return Generated PDF
     * @throws BaseException
     *             if any error occurs
     */
    public Response postStoredTemplateDocumentGenerate(StoredTemplateDocumentGenerateRequest request, Boolean responseContentGzipped) throws BaseException {
        if (request == null) {
            throw new InvalidParameterException("StoredTemplateDocumentGenerateRequest cannot be empty!");
        }
        Document document = generateAndGetDocument(request.getGeneratorSetup());

        return ResponseUtil.getFileResponse(
                BooleanUtils.isTrue(responseContentGzipped) ? GZIPUtil.compress(document.getContent()) : document.getContent(),
                document.getFilename(),
                MediaType.APPLICATION_OCTET_STREAM);
    }

    /**
     * Document generation with stored template data. Storage of template depends on implementation.
     *
     * @param request
     *            {@link StoredTemplateDocumentGenerateRequest} Request dto
     * @return {@link DocumentMetadataResponse}
     * @throws BaseException
     *             if any error occurs
     */
    public DocumentMetadataResponse postStoredTemplateDocumentGenerateMetadata(StoredTemplateDocumentGenerateRequest request) throws BaseException {
        if (request == null) {
            throw new InvalidParameterException("StoredTemplateDocumentGenerateRequest cannot be empty!");
        }
        Document document = generateAndGetDocument(request.getGeneratorSetup());

        return toDocumentMetadataResponse(document, request.getContext());
    }

    private Document generateAndGetDocument(StoredTemplateGeneratorSetupType generatorSetup) throws BaseException {

        ITemplateStore store = CDI.current()
                .select(ITemplateStore.class, new StorageMethodQualifier.Literal(generatorSetup.getTemplateStorageMethod().name()))
                .get();

        // This way, we preloaded all the templates needed for the given request. The template engine's resolver/loader then utilizes this container.
        store.loadTemplatesByNameAndValidity(
                generatorSetup.getTemplate().getTemplateName(),
                generatorSetup.getTemplate().getTemplateLanguage(),
                generatorSetup.getTemplate().getValidityDate());

        return generateDocument(generatorSetup);
    }
}
