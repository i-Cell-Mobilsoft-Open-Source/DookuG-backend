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

import hu.icellmobilsoft.coffee.se.api.exception.BusinessException;
import hu.icellmobilsoft.dookug.api.dto.exception.enums.FaultType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;

import hu.icellmobilsoft.coffee.dto.exception.InvalidParameterException;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.dookug.common.cdi.template.Template;
import hu.icellmobilsoft.dookug.common.cdi.template.TemplateContainer;

/**
 * Helper for handling sub-templates in document generation
 *
 * @author mate.biro
 * @since 2.2.0
 */
@ApplicationScoped
public class SubTemplateHelper {

    @Inject
    private FileNameHelper fileNameHelper;

    @Inject
    private TemplateContainer templateContainer;

    @Inject
    private InputPartHelper inputPartHelper;

    /**
     * Process sub-templates from multipart form data and add them to the template container
     *
     * @param formDataMap
     *            Map of form data with field names as keys and lists of InputPart as values
     * @param templateRecord
     *            TemplateRecord containing information about the main template
     * @throws BaseException
     *             if an error occurs while processing the sub-templates
     */
    protected void handleSubTemplates(Map<String, List<InputPart>> formDataMap, TemplateRecord templateRecord)
            throws BaseException {

        List<InputPart> subTemplates = formDataMap.get(GeneratorConstants.FORM_DATA_NAME_SUBTEMPLATE);

        if (CollectionUtils.isEmpty(subTemplates)) {
            return;
        }

        for (InputPart part : subTemplates) {

            String subFileName = fileNameHelper.getFileName(part)
                    .orElseThrow(
                            () -> new InvalidParameterException(
                                    MessageFormat.format(
                                            "Missing filename from Content-Disposition header for form-data: [{0}]!",
                                            GeneratorConstants.FORM_DATA_NAME_SUBTEMPLATE)));

            String subExt = fileNameHelper.getExtension(subFileName)
                    .orElseThrow(
                            () -> new InvalidParameterException(
                                    MessageFormat
                                            .format("Missing file extension for form-data: [{0}]!", GeneratorConstants.FORM_DATA_NAME_SUBTEMPLATE)));

            if (!templateRecord.templateExt().equals(subExt)) {
                throw new BusinessException(
                        FaultType.INVALID_SUB_TEMPLATE_EXTENSION,
                        MessageFormat.format(
                                "The file extensions of the [{0}] parts must match the extension of the main [{1}]!",
                                GeneratorConstants.FORM_DATA_NAME_SUBTEMPLATE,
                                GeneratorConstants.FORM_DATA_NAME_TEMPLATE));
            }

            String name = fileNameHelper.toTemplateName(subFileName);
            templateContainer.addTemplate(new Template(name, inputPartHelper.readPartBytes(part)), false);
        }
    }
}
