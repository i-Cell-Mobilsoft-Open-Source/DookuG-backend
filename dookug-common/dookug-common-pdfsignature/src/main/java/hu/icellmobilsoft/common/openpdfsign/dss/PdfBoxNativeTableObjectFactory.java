/*-
 * #%L
 * DookuG
 * %%
 * Copyright (C) 2023 - 2024 i-Cell Mobilsoft Zrt.
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
package hu.icellmobilsoft.common.openpdfsign.dss;

import eu.europa.esig.dss.pdf.PDFServiceMode;
import eu.europa.esig.dss.pdf.PDFSignatureService;
import eu.europa.esig.dss.pdf.pdfbox.PdfBoxNativeObjectFactory;
import eu.europa.esig.dss.pdf.pdfbox.PdfBoxSignatureService;

/**
 * opendf-sign visible signature drawer
 * 
 * @author tamas.cserhati
 * @since 1.1.0
 */
public class PdfBoxNativeTableObjectFactory extends PdfBoxNativeObjectFactory {

    @Override
    public PDFSignatureService newPAdESSignatureService() {
        // could be loaded from comfiguration
        return new PdfBoxSignatureService(PDFServiceMode.SIGNATURE, imageParameters -> new VisibleSignaturePdfBoxDrawer());
    }

}
