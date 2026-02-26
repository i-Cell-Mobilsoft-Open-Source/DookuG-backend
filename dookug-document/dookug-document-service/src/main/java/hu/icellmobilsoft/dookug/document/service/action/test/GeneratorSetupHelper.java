/*-
 * #%L
 * DookuG
 * %%
 * Copyright (C) 2023 - 2026 i-Cell Mobilsoft Zrt.
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
package hu.icellmobilsoft.dookug.document.service.action.test;

import java.util.List;
import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;

import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.se.api.exception.BusinessException;
import hu.icellmobilsoft.dookug.api.dto.exception.enums.FaultType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentStorageMethodType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.GeneratorEngineType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.InlineGeneratorSetupType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.ParametersDataType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.ResponseFormatType;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.TemplateEngineType;

/**
 * Helper for setting up the generator configuration based on the input parameters and template record.
 *
 * @author mate.biro
 * @since 2.2.0
 */
@ApplicationScoped
public class GeneratorSetupHelper {

    @Inject
    private InputPartHelper inputPartHelper;

    /**
     * Create an InlineGeneratorSetupType based on the provided parameters and template record.
     *
     * @param generatorEngine
     *            The type of generator engine to use
     * @param templateEngine
     *            The type of template engine to use
     * @param responseFormat
     *            The desired response format for the generated document
     * @param templateRecord
     *            The template record containing information about the template being used
     * @param formDataMap
     *            Map of form data with field names as keys and lists of InputPart as values
     * @param templateEngineParamsPart
     *            InputPart containing parameters for the template engine, if any
     * @param generatorEngineParamsPart
     *            InputPart containing parameters for the generator engine, if any
     * @return An InlineGeneratorSetupType configured based on the input parameters and template record
     * @throws BaseException
     *             if an error occurs while processing the input parameters or if validation fails
     */
    protected InlineGeneratorSetupType getGeneratorSetup(GeneratorEngineType generatorEngine, TemplateEngineType templateEngine,
            ResponseFormatType responseFormat, TemplateRecord templateRecord,
            Map<String, List<InputPart>> formDataMap,
            InputPart templateEngineParamsPart, InputPart generatorEngineParamsPart) throws BaseException {

        InlineGeneratorSetupType generatorSetup = new InlineGeneratorSetupType();
        generatorSetup.setGeneratorEngine(generatorEngine);
        generatorSetup.setTemplateEngine(templateEngine);
        generatorSetup.setResponseFormat(responseFormat);
        generatorSetup.setDocumentStorageMethod(DocumentStorageMethodType.NONE);

        // validate and set template language for xslt
        if (GeneratorConstants.EXTENSION_XSLT.equals(templateRecord.templateExt())) {
            String templateLanguage = inputPartHelper
                    .readOptionalTextPart(
                            formDataMap.get(GeneratorConstants.FORM_DATA_NAME_TEMPLATE_LANGUAGE),
                            GeneratorConstants.FORM_DATA_NAME_TEMPLATE_LANGUAGE);

            if (templateLanguage == null) {
                throw new BusinessException(FaultType.MISSING_TEMPLATE_LANGUAGE, "Missing form-data TEMPLATE_LANGUAGE for xslt template!");
            }

            generatorSetup.setTemplateLanguage(templateLanguage);
        }

        // set parameters data if any
        ParametersDataType parametersData = new ParametersDataType();
        if (templateEngineParamsPart != null) {
            parametersData.setTemplateParameters(inputPartHelper.readPartBytes(templateEngineParamsPart));
        }
        if (generatorEngineParamsPart != null) {
            parametersData.setGeneratorParameters(inputPartHelper.readPartBytes(generatorEngineParamsPart));
        }
        if (parametersData.getTemplateParameters() != null || parametersData.getGeneratorParameters() != null) {
            generatorSetup.setParametersData(parametersData);
        }
        return generatorSetup;
    }
}
