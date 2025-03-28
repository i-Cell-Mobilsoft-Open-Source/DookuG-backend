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
package hu.icellmobilsoft.dookug.ts.common.rest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Assertions;

import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.se.api.exception.TechnicalException;
import hu.icellmobilsoft.dookug.api.rest.builder.ParametersDataBuilder;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.ParametersDataType;
import hu.icellmobilsoft.dookug.ts.base.BaseIT;
import hu.icellmobilsoft.dookug.ts.common.config.TsConfigKey;
import hu.icellmobilsoft.roaster.common.util.FileUtil;

/**
 * Abstract class for document generation IT tests
 * 
 * @author szabolcs.gemesi
 * @since 0.0.1
 */
public abstract class AbstractGenerateDocumentIT extends BaseIT {

    /**
     * empty json
     */
    protected static final byte[] EMPTY_JSON = "{}".getBytes();
    private static final Pattern FILENAME_PATTERN = Pattern.compile("filename=\"([^\"]*)\"");
    private ByteArrayOutputStream resultContent = new ByteArrayOutputStream();

    @Inject
    @ConfigProperty(name = TsConfigKey.DOOKUG_WRITE_DOCUMENT)
    private Optional<Boolean> writeDocument;

    /**
     * get the file name from HTTP response
     * 
     * @param response
     *            the HTTP response
     * @return the filename
     */
    protected String getFilename(Response response) {
        String header = response.getHeaderString(HttpHeaders.CONTENT_DISPOSITION);

        Matcher m = FILENAME_PATTERN.matcher(header);
        if (m.find()) {
            return m.group(1);
        }
        return null;
    }

    /**
     * @return true if file persising is enabled in configuration
     */
    protected boolean isWriteDocumentEnabled() {
        return writeDocument.isPresent() && BooleanUtils.isTrue(writeDocument.get());
    }

    /**
     * write file to disk when enabled
     * 
     * @param inputStream
     *            the input stream
     * @param filename
     *            the file name
     * @return the optional path of the file written to disk or null
     * @throws BaseException
     *             if any error occurs
     */
    protected Optional<String> writeFileIfEnabled(InputStream inputStream, String filename) throws BaseException {
        try {
            inputStream.transferTo(resultContent);
            if (isWriteDocumentEnabled()) {
                String path = "target/output/" + filename;
                File generatedFile = new File(path);

                FileUtils.copyInputStreamToFile(new ByteArrayInputStream(resultContent.toByteArray()), generatedFile);
                return Optional.of(path);
            }
            return Optional.empty();
        } catch (IOException e) {
            throw new TechnicalException(CoffeeFaultType.OPERATION_FAILED, e.getLocalizedMessage());
        }
    }

    /**
     * check the result whether it PDF is
     */
    protected void assertIsResultPdf() {
        String generatedPdfString = resultContent.toString(StandardCharsets.UTF_8);
        Assertions.assertTrue(StringUtils.startsWith(generatedPdfString, "%PDF"), "Response object is not PDF!");
    }

    /**
     * create a simple parametersData with templateParameters only read from a file
     * 
     * @param fileName
     *            the file with the json content
     * @return the {@link ParametersDataType} constructed
     */
    protected ParametersDataType templateParameterDataFromFile(String fileName) {
        return ParametersDataBuilder.newBuilder().withTemplateParameters(FileUtil.readFileFromResource(fileName)).build();
    }

    /**
     * create a simple parametersData with templateParameters only converted from an (json) object
     * 
     * @param jsonParameter
     *            the object to convert to template parameter
     * @return the {@link ParametersDataType} constructed
     * @throws BaseException
     *             if json converson error occurs
     */
    protected ParametersDataType templateParameterDataFromObject(Object jsonParameter) throws BaseException {
        return ParametersDataBuilder.newBuilder().withTemplateParameters(jsonParameter).build();
    }

    /**
     * create a simple parametersData with empty template parameter
     * 
     * @return the {@link ParametersDataType} constructed
     */
    protected ParametersDataType emptyParameterData() {
        return ParametersDataBuilder.newBuilder().withTemplateParameters(null).build();
    }
}
