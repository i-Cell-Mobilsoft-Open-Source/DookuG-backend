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
import java.io.OutputStream;
import java.text.MessageFormat;

import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.StreamingOutput;

import hu.icellmobilsoft.coffee.dto.exception.InvalidParameterException;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.se.api.exception.TechnicalException;
import hu.icellmobilsoft.dookug.api.dto.exception.IOExceptionBaseExceptionWrapper;
import hu.icellmobilsoft.dookug.api.rest.document.form.DocumentSignMultipartForm;
import hu.icellmobilsoft.dookug.engine.pdfbox.signing.SignatureGenerator;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentsign.DocumentSignRequest;

/**
 * PDF electronic signing action
 *
 * @author tamas.cserhati
 * @since 1.1.0
 */
@Model
public class DocumentSignAction extends BaseDocumentGenerateAction {

    private static final String ATTACHMENT_FILENAME_SIGNED_DOCUMENT_PDF = "attachment; filename=\"signed-document.pdf\"";

    @Inject
    private SignatureGenerator signatureGenerator;

    /**
     * Multipart form based document signing. The form contains all required data.
     *
     * @param form
     *            {@link DocumentSignMultipartForm} The multipart form object
     * @return Signed document
     * @throws BaseException
     *             if any error occurs
     */
    public Response postSignDocument(DocumentSignMultipartForm form) throws BaseException {
        if (form == null) {
            throw new InvalidParameterException("form is null!");
        }
        if (form.getRequest() == null || !form.getRequest().isSetDigitalSignatureProfile()) {
            throw new InvalidParameterException("Request part is invalid!");
        }
        try {
            OutputStream outputStream = signatureGenerator.getOutputStreamForUnsignedPdf();
            // saving input stream to a temporaly file
            form.getDocument().transferTo(outputStream);
            // create signed pdf response
            StreamingOutput streamingOutput = createDocumentStreamingOutput(form.getRequest());
            return Response.ok(streamingOutput).header("Content-Disposition", ATTACHMENT_FILENAME_SIGNED_DOCUMENT_PDF).build();
        } catch (IOException e) {
            throw new TechnicalException(
                    CoffeeFaultType.OPERATION_FAILED,
                    MessageFormat.format("Error occured while working with input stream: [{0}]", e.getLocalizedMessage()));
        }
    }

    private StreamingOutput createDocumentStreamingOutput(DocumentSignRequest request) {
        return new StreamingOutput() {
            public void write(OutputStream output) throws IOException, WebApplicationException {
                try {
                    // adding signature
                    signatureGenerator.addDigitalSignatureIfNeeded(output, request.getDigitalSignatureProfile());
                } catch (BaseException e) {
                    throw new IOExceptionBaseExceptionWrapper(e);
                } finally {
                    signatureGenerator.closeStreams();
                }
            }
        };
    }

}
