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
package hu.icellmobilsoft.dookug.ts.client.rest.builder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

import hu.icellmobilsoft.dookug.api.rest.builder.ParametersDataBuilder;
import hu.icellmobilsoft.dookug.schemas.common._1_0.common.ParameterType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.ParametersDataType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.TemplateType;
import hu.icellmobilsoft.roaster.common.util.FileUtil;

/**
 * DookugClient Request Helper class
 * 
 * @author tamas.cserhati
 * @since 0.1.0
 */
public interface DookugClientRequestHelper {

    public static final String TEMPLATE = "DookuG client simple test with prameters first: [{{first}}], second: [{{second}}]";
    public static final String SIMPLE_PARAMETERS_JSON = "{\"first\": \"első\", \"second\": \"í123456789öüóőúűáé-.,<>#&@{};*¤ß$\", \"three\": [{\"sub1\": \"level2-1\"},{\"sub1\": \"level2-2\"}]}";

    String BUILT_IN_HELPER = "{{join \"a\" \"b\" \"c\"}} {{substring \"0123456789\" 2 5}}";

    /**
     * methods for simple key-value tests
     */
    public interface SimpleKeyValue {
        /**
         * @return
         */
        static Collection<ParameterType> createParameters() {
            ParameterType parameter1 = new ParameterType().withKey("first").withValue("első");
            ParameterType parameter2 = new ParameterType().withKey("second").withValue("í123456789öüóőúűáé-.,<>#&@{};*¤ß$");
            return List.of(parameter1, parameter2);
        }

        /**
         * @return
         */
        static Collection<TemplateType> createTemplate() {
            TemplateType template = new TemplateType().withTemplateName("main")
                    .withTemplateContent(TEMPLATE.getBytes(StandardCharsets.UTF_8))
                    .withInitial(true);
            return List.of(template);
        }

        static InputStream createTemplateAsStream() {
            return new ByteArrayInputStream(TEMPLATE.getBytes(StandardCharsets.UTF_8));
        }

    }

    /**
     * methods for simple json tests
     */
    public interface SimpleJson {
        /**
         * @return
         */
        static ParametersDataType createParametersData() {
            return ParametersDataBuilder.newBuilder().withTemplateParameters(SIMPLE_PARAMETERS_JSON).build();
        }

        /**
         * @return
         */
        static Collection<TemplateType> createTemplate() {
            return SimpleKeyValue.createTemplate();
        }

        static InputStream createTemplateAsStream() {
            return SimpleKeyValue.createTemplateAsStream();
        }
    }

    /**
     * methods for PDF generation tests
     */
    public interface PdfBoxTest {
        public static final String PDF_BOX_TEMPLATE = "pdfbox/pdfbox_template.html";
        public static final String PDF_BOX_TEMPLATE_ERROR = "pdfbox/pdfbox_template_error.html";

        static Collection<TemplateType> readPdfTemplate() {
            TemplateType template = new TemplateType().withTemplateName("pdfbox_template")
                    .withTemplateContent(FileUtil.readFileFromResource(PDF_BOX_TEMPLATE).getBytes(StandardCharsets.UTF_8));
            return List.of(template);
        }

        static Collection<TemplateType> readPdfTemplateError() {
            TemplateType template = new TemplateType().withTemplateName("pdfbox_template")
                    .withTemplateContent(FileUtil.readFileFromResource(PDF_BOX_TEMPLATE_ERROR).getBytes(StandardCharsets.UTF_8));
            return List.of(template);
        }
    }

    /**
     * methods for BuiltIn helpers
     */
    interface BuiltInHelpers {
        static Collection<TemplateType> createTemplate() {
            TemplateType template = new TemplateType().withTemplateName("main")
                    .withTemplateContent(BUILT_IN_HELPER.getBytes(StandardCharsets.UTF_8))
                    .withInitial(true);
            return List.of(template);
        }
    }
}
