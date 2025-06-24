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

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import hu.icellmobilsoft.coffee.dto.common.commonservice.ContextType;
import hu.icellmobilsoft.coffee.dto.exception.InvalidParameterException;
import hu.icellmobilsoft.coffee.dto.exception.TechnicalException;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.coffee.rest.utils.ResponseUtil;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.tool.utils.compress.GZIPUtil;
import hu.icellmobilsoft.dookug.api.rest.document.form.DocumentGenerateMultipartForm;
import hu.icellmobilsoft.dookug.common.cdi.document.Document;
import hu.icellmobilsoft.dookug.common.cdi.template.Template;
import hu.icellmobilsoft.dookug.common.cdi.template.TemplateContainer;
import hu.icellmobilsoft.dookug.common.cdi.template.TemplateDataContainer;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.BaseGeneratorSetupType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentGenerateWithTemplatesRequest;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentMetadataResponse;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.InlineGeneratorSetupType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.TemplateType;

/**
 * Template based document generation action
 *
 * @author imre.scheffer
 * @since 0.1.0
 */
@Model
public class DocumentGenerateAction extends BaseDocumentGenerateAction {

    private static final String MULTIPART_INLINE_TEMPLATE_NAME = "MULTIPART_INLINE_TEMPLATE";
    private static final String INLINE_TEMPLATE_NAME = "INLINE_TEMPLATE";

    @Inject
    private TemplateContainer templateContainer;

    @Inject
    private TemplateDataContainer templateData;

    /**
     * Multipart form based document generation. The form contains all required data.
     *
     * @param form
     *            {@link DocumentGenerateMultipartForm} The multipart form object
     * @return Generated document by request
     * @throws BaseException
     *             if error
     */
    public Response postDocumentGenerate(DocumentGenerateMultipartForm form) throws BaseException {
        if (form == null) {
            throw new InvalidParameterException("form is null!");
        }
        BaseGeneratorSetupType generatorSetup = form.getRequest().getGeneratorSetup();
        byte[] template = readInputStream(form.getTemplate());
        templateData.setTemplateName(MULTIPART_INLINE_TEMPLATE_NAME);
        // TODO a multipart inputnak valoszinu gzip csomagolt szerepe lesz, egyelore 1 inputot varunk el tole.
        return documentGenerate(
                List.of(new TemplateType().withTemplateName("simple").withTemplateContent(template).withInitial(true)),
                generatorSetup);
    }

    /**
     * Entity body based document generation. The body contains all required data.
     *
     * @param request
     *            {@link DocumentGenerateWithTemplatesRequest} The multipart form object
     * @return Generated document by request
     * @throws BaseException
     *             if error
     */
    public Response postDocumentGenerate(DocumentGenerateWithTemplatesRequest request) throws BaseException {
        if (request == null) {
            throw new InvalidParameterException("request is null!");
        }
        templateData.setTemplateName(INLINE_TEMPLATE_NAME);
        InlineGeneratorSetupType generatorSetup = request.getGeneratorSetup();
        return documentGenerate(request.getTemplates(), generatorSetup);
    }

    /**
     * @param form
     *            multipart form
     * @return the document metadata
     * @throws BaseException
     *             on error
     */
    public DocumentMetadataResponse postDocumentGenerateMetadata(DocumentGenerateMultipartForm form) throws BaseException {
        if (form == null) {
            throw new InvalidParameterException("form is null!");
        }
        BaseGeneratorSetupType generatorSetup = form.getRequest().getGeneratorSetup();
        byte[] template = readInputStream(form.getTemplate());
        templateData.setTemplateName(MULTIPART_INLINE_TEMPLATE_NAME);
        // TODO a multipart inputnak valoszinu gzip csomagolt szerepe lesz, egyelore 1 inputot varunk el tole.
        return documentGenerateMetadata(
                List.of(new TemplateType().withTemplateName("simple").withTemplateContent(template).withInitial(true)),
                generatorSetup,
                form.getRequest().getContext());
    }

    /**
     * @param request
     *            request object
     * @return the document metadata
     * @throws BaseException
     *             on error
     */
    public DocumentMetadataResponse postDocumentGenerateMetadata(DocumentGenerateWithTemplatesRequest request) throws BaseException {
        if (request == null) {
            throw new InvalidParameterException("request is null!");
        }
        templateData.setTemplateName(INLINE_TEMPLATE_NAME);
        InlineGeneratorSetupType generatorSetup = request.getGeneratorSetup();
        return documentGenerateMetadata(request.getTemplates(), generatorSetup, request.getContext());
    }

    private Response documentGenerate(List<TemplateType> templates, BaseGeneratorSetupType generatorSetup) throws BaseException {

        for (TemplateType templateType : templates) {
            templateContainer.addTemplate(new Template(templateType.getTemplateName(), templateType.getTemplateContent()), templateType.isInitial());
        }

        Document document = generateDocument(generatorSetup);

        return ResponseUtil.getFileResponse(document.getContent(), document.getFilename(), MediaType.APPLICATION_OCTET_STREAM);
    }

    private DocumentMetadataResponse documentGenerateMetadata(List<TemplateType> templates, BaseGeneratorSetupType generatorSetup,
            ContextType context) throws BaseException {

        for (TemplateType templateType : templates) {
            templateContainer.addTemplate(new Template(templateType.getTemplateName(), templateType.getTemplateContent()), templateType.isInitial());
        }

        Document document = generateDocument(generatorSetup);

        return toDocumentMetadataResponse(document, context);
    }

    private byte[] readInputStream(InputStream is) throws BaseException {
        if (is == null) {
            return new byte[0];
        }
        try {
            byte[] input = is.readAllBytes();
            if (GZIPUtil.isCompressed(input)) {
                return GZIPUtil.decompress(input);
            }
            return input;
        } catch (IOException e) {
            throw new TechnicalException(CoffeeFaultType.INVALID_INPUT, "Error during read template inputstream!", e);
        }
    }
}
