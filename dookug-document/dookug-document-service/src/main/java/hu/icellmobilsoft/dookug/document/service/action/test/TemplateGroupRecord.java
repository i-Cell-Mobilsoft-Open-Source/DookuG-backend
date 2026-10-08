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

import hu.icellmobilsoft.dookug.common.model.template.Template;
import hu.icellmobilsoft.dookug.common.model.template.TemplatePart;
import hu.icellmobilsoft.dookug.common.model.template.TemplatePartContent;
import hu.icellmobilsoft.dookug.common.model.template.TemplateTemplatePart;

/**
 * Record for holding template group related entities
 *
 * @param templates
 *            the TEMPLATE entities
 * @param templateParts
 *            the TEMPLATE_PART entities
 * @param templatePartContents
 *            the TEMPLATE_PART_CONTENT entities
 * @param templateTemplateParts
 *            the TEMPLATE - TEMPLATE_PART part relations
 *
 * @author levente.prehoda
 * @since 2.2.0
 */
public record TemplateGroupRecord(List<Template> templates, List<TemplatePart> templateParts, List<TemplatePartContent> templatePartContents,
        List<TemplateTemplatePart> templateTemplateParts) {
}
