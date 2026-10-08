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
package hu.icellmobilsoft.dookug.engine.pdfbox;

import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.openhtmltopdf.slf4j.Slf4jLogger;
import com.openhtmltopdf.util.XRLog;

import hu.icellmobilsoft.coffee.cdi.logger.ThisLogger;
import hu.icellmobilsoft.coffee.cdi.trace.annotation.Traced;
import hu.icellmobilsoft.coffee.cdi.trace.constants.SpanAttribute;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.se.api.exception.TechnicalException;
import hu.icellmobilsoft.coffee.se.logging.Logger;
import hu.icellmobilsoft.dookug.common.cdi.DocumentGeneratorQualifier;
import hu.icellmobilsoft.dookug.common.cdi.constants.QualifierConstants;
import hu.icellmobilsoft.dookug.common.cdi.template.IDocumentGenerator;
import hu.icellmobilsoft.dookug.common.cdi.template.TemplateContainer;
import hu.icellmobilsoft.dookug.engine.pdfbox.signing.SignatureGenerator;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.ParametersDataType;

/**
 * PdfBox implementation for document generation
 *
 * @author laszlo.padar
 * @author imre.scheffer
 * @author tamas.cserhati
 * @since 0.1.0
 */
@DocumentGeneratorQualifier(QualifierConstants.DocumentGeneratorType.PDF_BOX)
@ApplicationScoped
public class PdfBoxDocumentGenerator implements IDocumentGenerator {
    // protected static final String FONT_PATH = "/home/icellmobilsoft/fonts/NotoSansThai/NotoSansThai-Regular.ttf";

    @Inject
    @ThisLogger
    private Logger logger;

    @Inject
    private TemplateContainer templateContainer;

    @Inject
    private SignatureGenerator signatureGenerator;

    @Override
    public void generateToOutputStream(OutputStream outputStream, Map<String, String> parameters, String digitalSignatureProfile)
            throws BaseException {
        generateToOutputStream(outputStream, new ParametersDataType(), digitalSignatureProfile);
    }

    @Override
    @Traced(component = "generate-with-pdfbox", kind = SpanAttribute.INTERNAL)
    public void generateToOutputStream(OutputStream outputStream, ParametersDataType parameterData, String digitalSignatureProfile)
            throws BaseException {

        PdfRendererBuilder pdfRendererBuilder = new PdfRendererBuilder();

        // set openhtmltopdf logger (without this its logging to System.err) https://github.com/danfickle/openhtmltopdf/wiki/Logging
        XRLog.setLoggerImpl(new Slf4jLogger());

        // must be HTML format we dont care about escape handling
        String compiledHtml = templateContainer.getCompiledResult();
        pdfRendererBuilder.withHtmlContent(compiledHtml, "");
        pdfRendererBuilder.withProducer(StringUtils.EMPTY);
        // pdfRendererBuilder.useFont(new File(FONT_PATH), "notosansthai-regular");
        pdfRendererBuilder.toStream(outputStream);

        if (StringUtils.isBlank(digitalSignatureProfile)) {
            // we dont need digital signature
            pdfRendererBuilder.toStream(outputStream);
            // generated pdf -> outputstream
            pdfRender(pdfRendererBuilder);
        } else {
            try {
                // original PDF is going to a temporal stream (persisted into temporaly file)
                pdfRendererBuilder.toStream(signatureGenerator.getOutputStreamForUnsignedPdf());
                pdfRender(pdfRendererBuilder);
                // adding signature
                signatureGenerator.addDigitalSignatureIfNeeded(outputStream, digitalSignatureProfile);
            } finally {
                signatureGenerator.closeStreams();
            }
        }
    }

    private void pdfRender(PdfRendererBuilder pdfRendererBuilder) throws BaseException {
        try {
            pdfRendererBuilder.run();
        } catch (Exception e) {
            throw new TechnicalException(
                    CoffeeFaultType.OPERATION_FAILED,
                    MessageFormat.format("An error occurred while generate PDF file with Pdfbox: [{0}]", e.getLocalizedMessage()),
                    e);
        }
    }
}
