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

/**
 * Helper for handling the main template in document generation
 *
 * @author mate.biro
 * @since 2.2.0
 */
@ApplicationScoped
public class TemplateHelper {

    @Inject
    private FileNameHelper fileNameHelper;

    @Inject
    private InputPartHelper inputPartHelper;

    /**
     * Process the main template from multipart form data and return a TemplateRecord with its information
     *
     * @param formDataMap
     *            Map of form data with field names as keys and lists of InputPart as values
     * @return TemplateRecord containing the InputPart, filename, and extension of the main template
     * @throws BaseException
     *             if an error occurs while processing the template or if validation fails
     */
    protected TemplateRecord handleTemplate(Map<String, List<InputPart>> formDataMap) throws BaseException {

        List<InputPart> templateInputParts = formDataMap.get(GeneratorConstants.FORM_DATA_NAME_TEMPLATE);

        InputPart templatePart = inputPartHelper.getSingleRequiredFilePart(templateInputParts, GeneratorConstants.FORM_DATA_NAME_TEMPLATE);

        String templateFileName = fileNameHelper.getFileName(templatePart)
                .orElseThrow(
                        () -> new InvalidParameterException(
                                MessageFormat
                                        .format(
                                                "Missing filename from Content-Disposition header for form-data: [{0}]!",
                                                GeneratorConstants.FORM_DATA_NAME_TEMPLATE)));

        String templateExt = fileNameHelper.getExtension(templateFileName)
                .orElseThrow(
                        () -> new InvalidParameterException(
                                MessageFormat.format("Missing file extension for form-data: [{0}]!", GeneratorConstants.FORM_DATA_NAME_TEMPLATE)));

        if (!GeneratorConstants.ACCEPTED_TEMPLATE_EXTENSIONS.contains(templateExt)) {
            throw new InvalidParameterException(
                    MessageFormat.format(
                            "Only [{0}] extensions are accepted for form-data: [{1}]!",
                            GeneratorConstants.ACCEPTED_TEMPLATE_EXTENSIONS,
                            GeneratorConstants.FORM_DATA_NAME_TEMPLATE));
        }

        return new TemplateRecord(templatePart, templateFileName, templateExt);
    }
}
