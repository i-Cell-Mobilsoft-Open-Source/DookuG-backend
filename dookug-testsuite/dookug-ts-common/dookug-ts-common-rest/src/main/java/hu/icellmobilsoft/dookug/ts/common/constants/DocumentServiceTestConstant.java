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
package hu.icellmobilsoft.dookug.ts.common.constants;

/**
 * Constants for tests
 * 
 * @author imre.scheffer
 * @since 0.1.0
 *
 */
public interface DocumentServiceTestConstant {

    /**
     * {@value #PDF_BOX_TEMPLATE}
     */
    String PDF_BOX_TEMPLATE = "pdfbox/pdfbox_template.html";
    /**
     * {@value #PDF_BOX_TEMPLATE_CUSTOM_FONT}
     */
    String PDF_BOX_TEMPLATE_CUSTOM_FONT = "pdfbox/pdfbox_template_customfont.html";
    /**
     * {@value #PDF_BOX_TEMPLATE_ERROR}
     */
    String PDF_BOX_TEMPLATE_ERROR = "pdfbox/pdfbox_template_error.html";

    /**
     * {@value #PDF_BOX_TEMPLATE_PARAMETERS}
     */
    String PDF_BOX_TEMPLATE_PARAMETERS = "pdfbox/pdfbox_template_parameters.json";

    /**
     * {@value #EXPECTED_500}
     */
    String EXPECTED_500 = "HTTP 500 státusznak kell lennie!";

    /**
     * {@value #DEV_TEMPLATE_NAME}
     */
    String DEV_TEMPLATE_NAME = "DEV_TEMPLATE_HANDLEBARS";

    /**
     * {@value #PROJECT_STORED_TEMPLATE_NAME}
     */
    String PROJECT_STORED_TEMPLATE_NAME = "HUGO_INVOICE_TEMPLATE";

    /**
     * {@value #DEV_ERROR_TEMPLATE_NAME}
     */
    String DEV_ERROR_TEMPLATE_NAME = "DEV_TEMPLATE_ERROR_HANDLEBARS";

    /**
     * {@value #MAIN_TEMPLATE_NAME}
     */
    String MAIN_TEMPLATE_NAME = "main_template";

    /**
     * {@value #HEAD_TEMPLATE_NAME}
     */
    String HEAD_TEMPLATE_NAME = "head_template";

//    /**
//     * {@value #XSLT_PDF_TEMPLATE}
//     */
//    String XSLT_PDF_TEMPLATE = "saxon/xslt/template/InvoiceDataTemplate.xslt";

    /**
     * XSLT PDF XSLT_PDF_TEMPLATE_MAIN
     */
    String XSLT_PDF_TEMPLATE_MAIN = "saxon/xslt/template_main.xslt";

    /**
     * {@value #TEMPLATE_PART_1_NAME}
     */
    String TEMPLATE_PART_1_NAME = "template_part_1";

    /**
     * {@value #TEMPLATE_PART_2_NAME}
     */
    String TEMPLATE_PART_2_NAME = "template_part_2";

    /**
     * XSLT PDF TEMPLATE_PART_1
     */
    String XSLT_PDF_TEMPLATE_PART1 = "saxon/xslt/template_part_1.xslt";

    /**
     * XSLT PDF TEMPLATE_PART_2
     */
    String XSLT_PDF_TEMPLATE_PART2 = "saxon/xslt/template_part_2.xslt";

    /**
     * XSLT HTML TEMPLATE PARAMS
     */
    String XSLT_TEMPLATE_PARAMS = "saxon/xslt/Belfoldi egyszerusitett szamla_working.xml";

    /**
     * FOP_CONFIG
     */
    String FOP_CONFIG = "saxon/xslt/fop-config.xml";

    /**
     * default hungarian locale
     */
    String DEFAULT_LANGUAGE_HU = "HU";

}
