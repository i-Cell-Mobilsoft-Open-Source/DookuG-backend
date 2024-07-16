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

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceDictionary;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceStream;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDSignatureField;
import org.apache.pdfbox.util.Matrix;
import org.vandeseer.easytable.TableDrawer;
import org.vandeseer.easytable.settings.HorizontalAlignment;
import org.vandeseer.easytable.settings.VerticalAlignment;
import org.vandeseer.easytable.structure.Row;
import org.vandeseer.easytable.structure.Table;
import org.vandeseer.easytable.structure.cell.ImageCell;
import org.vandeseer.easytable.structure.cell.TextCell;

import eu.europa.esig.dss.pdf.pdfbox.visible.nativedrawer.NativePdfBoxVisibleSignatureDrawer;
import eu.europa.esig.dss.pdf.visible.ImageRotationUtils;
import eu.europa.esig.dss.pdf.visible.ImageUtils;
import eu.europa.esig.dss.pdf.visible.SignatureFieldDimensionAndPosition;
import hu.icellmobilsoft.common.openpdfsign.TableSignatureFieldParameters;

/**
 * opendf-sign visible signature drawer
 * 
 * @author tamas.cserhati
 * @since 1.1.0
 */
public class VisibleSignaturePdfBoxDrawer extends NativePdfBoxVisibleSignatureDrawer {

    @Override
    public void draw() throws IOException {
        try (PDDocument doc = new PDDocument(); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PDImageXObject imageXObject = PDImageXObject
                    .createFromByteArray(doc, IOUtils.toByteArray(parameters.getImage().openStream()), parameters.getImage().getName());

            // Get information of type TableSignatureFieldParameters
            TableSignatureFieldParameters tableParameters = null;
            if (parameters.getFieldParameters() instanceof TableSignatureFieldParameters) {
                tableParameters = (TableSignatureFieldParameters) parameters.getFieldParameters();
            }

            int pageNumber = parameters.getFieldParameters().getPage() - ImageUtils.DEFAULT_FIRST_PAGE;
            PDPage originalPage = document.getPage(pageNumber);

            // create a new page
            PDPage page = new PDPage(originalPage.getMediaBox());
            doc.addPage(page);
            PDAcroForm acroForm = new PDAcroForm(doc);
            doc.getDocumentCatalog().setAcroForm(acroForm);
            PDSignatureField signatureField = new PDSignatureField(acroForm);
            PDAnnotationWidget widget = signatureField.getWidgets().get(0);
            List<PDField> acroFormFields = acroForm.getFields();
            acroForm.setSignaturesExist(true);
            acroForm.setAppendOnly(true);
            acroForm.getCOSObject().setDirect(true);
            acroFormFields.add(signatureField);

            // disable "missing font / rebuild cache" logging
            java.util.logging.Logger.getLogger("org.apache.pdfbox").setLevel(java.util.logging.Level.OFF);
            java.util.logging.Logger.getLogger("org.apache.fontbox").setLevel(java.util.logging.Level.OFF);

            // calculate dynamic width, if any
            final float imageColumnWidth = tableParameters != null ? tableParameters.getWidth() : 80;
            // Build the table
            Table.TableBuilder myTableBuilder = Table.builder()
                    .addColumnsOfWidth(imageColumnWidth)
                    .backgroundColor(Color.WHITE)
                    // .borderColor(Color.darkGray)
                    // .borderWidth(0f)
                    // .padding(5)
                    .fontSize(7)
                    .verticalAlignment(VerticalAlignment.TOP)
                    .addRow(
                            Row.builder()
                                    .add(
                                            ImageCell.builder()
                                                    .image(imageXObject)
                                                    .maxHeight(100)
                                                    .verticalAlignment(VerticalAlignment.MIDDLE)
                                                    .horizontalAlignment(HorizontalAlignment.CENTER)
                                                    .rowSpan((1))
                                                    .build())
                                    .build());
            if (tableParameters != null && tableParameters.getEsignee() != null) {
                myTableBuilder.addRow(
                        Row.builder()
                                .add(TextCell.builder().text(tableParameters.getEsignee()).horizontalAlignment(HorizontalAlignment.CENTER).build())
                                .build());
            }
            if (tableParameters != null && tableParameters.getSignatureDate() != null) {
                myTableBuilder.addRow(
                        Row.builder()
                                // .height(100f)
                                .add(
                                        TextCell.builder()
                                                .text(tableParameters.getSignatureDate())
                                                .horizontalAlignment(HorizontalAlignment.CENTER)
                                                .build())
                                .build());
            }

            Table myTable = myTableBuilder.build();

            SignatureFieldDimensionAndPosition dimensionAndPosition = buildSignatureFieldBox();

            dimensionAndPosition.setBoxHeight(myTable.getHeight()); //
            dimensionAndPosition.setBoxWidth(myTable.getWidth()); //
            PDRectangle rectangle = getPdRectangle(dimensionAndPosition, page);
            widget.setRectangle(rectangle);

            PDStream stream = new PDStream(doc);
            PDFormXObject form = new PDFormXObject(stream);
            PDResources res = new PDResources();
            form.setResources(res);
            form.setFormType(1);

            form.setBBox(new PDRectangle(rectangle.getWidth(), rectangle.getHeight()));

            PDAppearanceDictionary appearance = new PDAppearanceDictionary();
            appearance.getCOSObject().setDirect(true);
            PDAppearanceStream appearanceStream = new PDAppearanceStream(form.getCOSObject());
            appearance.setNormalAppearance(appearanceStream);
            widget.setAppearance(appearance);

            try (PDPageContentStream cs = new PDPageContentStream(doc, appearanceStream)) {
                // Set up the drawer
                TableDrawer tableDrawer = TableDrawer.builder()
                        .contentStream(cs)
                        .startX(0) // start from left
                        .startY(myTable.getHeight()) // start from bottom because why not (in pdf)
                        .table(myTable)
                        .build();

                // And go for it!
                cs.saveGraphicsState();
                // handle transparent gif
                PDExtendedGraphicsState pdExtGfxState = new PDExtendedGraphicsState();
                pdExtGfxState.getCOSObject().setItem(COSName.BM, COSName.MULTIPLY);
                cs.setGraphicsStateParameters(pdExtGfxState);
                tableDrawer.draw();
                cs.transform(Matrix.getRotateInstance(((double) 360 - ImageRotationUtils.getRotation(parameters.getRotation())), 400, 200));
                cs.restoreGraphicsState();
            }

            doc.save(baos);

            try (ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray())) {
                signatureOptions.setVisualSignature(bais);
                signatureOptions.setPage(pageNumber);
            }

        }
    }

    private PDRectangle getPdRectangle(SignatureFieldDimensionAndPosition dimensionAndPosition, PDPage page) {
        PDRectangle pageRect = page.getMediaBox();
        PDRectangle pdRectangle = new PDRectangle();
        pdRectangle.setLowerLeftX(dimensionAndPosition.getBoxX());
        // because PDF starts to count from bottom
        pdRectangle.setLowerLeftY(pageRect.getHeight() - dimensionAndPosition.getBoxY() - dimensionAndPosition.getBoxHeight());
        pdRectangle.setUpperRightX(dimensionAndPosition.getBoxX() + dimensionAndPosition.getBoxWidth());
        pdRectangle.setUpperRightY(pageRect.getHeight() - dimensionAndPosition.getBoxY());
        return pdRectangle;
    }
}
