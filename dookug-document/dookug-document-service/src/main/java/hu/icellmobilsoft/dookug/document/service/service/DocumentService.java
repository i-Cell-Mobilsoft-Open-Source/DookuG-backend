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
package hu.icellmobilsoft.dookug.document.service.service;

import javax.enterprise.inject.Model;

import hu.icellmobilsoft.dookug.common.model.template.Document;
import hu.icellmobilsoft.dookug.common.model.template.TemplatePartContent;
import hu.icellmobilsoft.dookug.common.system.jpa.service.BaseService;

/**
 * {@link Document} database operations
 *
 * @author szabolcs.gemesi
 * @since 0.0.1
 */
@Model
public class DocumentService extends BaseService<Document> {
}
