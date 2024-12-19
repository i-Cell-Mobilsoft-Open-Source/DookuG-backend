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
package hu.icellmobilsoft.dookug.api.rest.builder;

import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.se.api.exception.JsonConversionException;
import hu.icellmobilsoft.coffee.tool.utils.json.JsonUtil;
import hu.icellmobilsoft.coffee.tool.utils.marshalling.MarshallingUtil;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.ParametersDataType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.generator.saxon.SaxonGeneratorParametersData;

/**
 * {@link ParametersDataType} builder
 * 
 * @author tamas.cserhati
 * @since 0.0.1
 */
public class ParametersDataBuilder {

    private ParametersDataType parametersData;

    /**
     * Instantiate a new builder
     * 
     * @return a new builder instance
     */
    public static ParametersDataBuilder newBuilder() {
        ParametersDataBuilder builder = new ParametersDataBuilder();
        builder.parametersData = new ParametersDataType();
        return builder;
    }

    /**
     * get ParametersDataType.withGeneratorParameters(saxonParameters coming in method parameters)
     * 
     * @param fopConfig
     *            the fopConfig
     * @param xmlDataset
     *            the xml dataset
     * @param isCompressed
     *            if xml is compressed with gzip
     * @return the configured {@link ParametersDataType} object
     */
    public static ParametersDataType getSaxonParameters(byte[] fopConfig, byte[] xmlDataset, boolean isCompressed) {
        ParametersDataBuilder builder = new ParametersDataBuilder();
        SaxonGeneratorParametersData saxonParameters = new SaxonGeneratorParametersData().withFopConfig(fopConfig)
                .withXmlDataset(xmlDataset)
                .withXmlDatasetCompressed(isCompressed);
        builder.parametersData = new ParametersDataType();
        builder.withGeneratorParameters(saxonParameters);
        return builder.build();
    }

    /**
     * get ParametersDataType.withGeneratorParameters(fopConfig, xmlDataset)
     * 
     * @param fopConfig
     *            the fopConfig
     * @param xmlDataset
     *            the xml dataset uncompressed
     * @return the configured {@link ParametersDataType} object
     */
    public static ParametersDataType getSaxonParameters(byte[] fopConfig, byte[] xmlDataset) {
        return getSaxonParameters(fopConfig, xmlDataset, false);
    }

    /**
     * get ParametersDataType.withGeneratorParameters(xmlDataset)
     * 
     * @param xmlDataset
     *            the xml dataset uncompressed
     * @return the configured {@link ParametersDataType} object
     */
    public static ParametersDataType getSaxonParameters(byte[] xmlDataset) {
        return getSaxonParameters(null, xmlDataset);
    }

    /**
     * build the requested object
     * 
     * @return the {@link ParametersDataType} set up
     */
    public ParametersDataType build() {
        return parametersData;
    }

    /**
     * build the requested object as a marshalled byte array
     * 
     * @return the configured parametersData as a byte array
     */
    public byte[] binaryBuild() {
        return MarshallingUtil.marshall(parametersData).getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Create template parameters from (JSON) object
     * 
     * @param templateParameters
     *            the object to add as binary template parameters
     * @return the builder
     * @throws JsonConversionException
     *             if json conversion error occurs
     */
    public ParametersDataBuilder withTemplateParameters(Object templateParameters) throws BaseException {
        if (templateParameters == null) {
            return this;
        }
        parametersData.setTemplateParameters(JsonUtil.toJson(templateParameters).getBytes(StandardCharsets.UTF_8));
        return this;
    }

    /**
     * Create template parameters from an UTF-8 JSON string
     * 
     * @param templateParametersJson
     *            the json string uses for template parameters
     * @return the builder
     */
    public ParametersDataBuilder withTemplateParameters(String templateParametersJson) {
        if (StringUtils.isBlank(templateParametersJson)) {
            return this;
        }
        parametersData.setTemplateParameters(templateParametersJson.getBytes(StandardCharsets.UTF_8));
        return this;
    }

    /**
     * Create a {@link SaxonGeneratorParametersData} object to set as SAXON XSLT generator parameter for the DookuG service
     * 
     * @param saxonParameters
     *            the saxon parameters
     * @return the builder
     */
    public ParametersDataBuilder withGeneratorParameters(SaxonGeneratorParametersData saxonParameters) {
        if (saxonParameters == null) {
            return this;
        }
        String saxonParametersXml = MarshallingUtil.marshall(saxonParameters);
        parametersData.setGeneratorParameters(saxonParametersXml.getBytes(StandardCharsets.UTF_8));
        return this;
    }
}
