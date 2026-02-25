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

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;

import hu.icellmobilsoft.coffee.dto.exception.InvalidParameterException;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.se.api.exception.BusinessException;
import hu.icellmobilsoft.dookug.api.dto.exception.enums.FaultType;

/**
 * Helper for handling engine parameter files in document generation
 *
 * @author mate.biro
 * @since 2.2.0
 */
@ApplicationScoped
public class EngineParametersHelper {

    @Inject
    private FileNameHelper fileNameHelper;

    @Inject
    private InputPartHelper inputPartHelper;

    /**
     * Process the engine parameter file from multipart form data and return the InputPart if valid
     *
     * @param formDataMap
     *            Map of form data with field names as keys and lists of InputPart as values
     * @param formDataNameParameters
     *            Name of the form data field for engine parameters
     * @param requiredFileExtension
     *            Required file extension for the engine parameter file (e.g., "json")
     * @param extensionFaultType
     *            FaultType to use in case of invalid file extension
     * @return InputPart of the engine parameter file, or null if not provided
     * @throws BaseException
     *             if an error occurs while processing the engine parameters or if validation fails
     */
    protected InputPart handleEngineParameters(Map<String, List<InputPart>> formDataMap, String formDataNameParameters,
            String requiredFileExtension, FaultType extensionFaultType) throws BaseException {

        InputPart engineParamsPart = inputPartHelper.getSingleOptionalFilePart(
                formDataMap.get(formDataNameParameters),
                formDataNameParameters);

        if (engineParamsPart == null) {
            return null;
        }

        String paramsFileName = fileNameHelper.getFileName(engineParamsPart)
                .orElseThrow(
                        () -> new InvalidParameterException(
                                MessageFormat.format(
                                        "Missing filename from Content-Disposition header for form-data: [{0}]!",
                                        formDataNameParameters)));

        String paramsExt = fileNameHelper.getExtension(paramsFileName)
                .orElseThrow(
                        () -> new InvalidParameterException(
                                MessageFormat.format("Missing file extension for form-data: [{0}]!", formDataNameParameters)));

        if (!requiredFileExtension.equals(paramsExt)) {
            throw new BusinessException(
                    extensionFaultType,
                    MessageFormat.format(
                            "Only [{0}] parameter file can be specified for from-data: [{1}]!",
                            requiredFileExtension,
                            formDataNameParameters));
        }

        return engineParamsPart;
    }
}
