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
package hu.icellmobilsoft.dookug.document.service.repository;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

import hu.icellmobilsoft.dookug.common.model.template.DocumentContent;

/**
 * {@link DocumentContent} database operations
 *
 * @author szabolcs.gemesi
 * @since 0.0.1
 */
@Repository
public interface DocumentContentRepository extends EntityRepository<DocumentContent, String>, CriteriaSupport<DocumentContent> {

    @Query("SELECT dc FROM DocumentContent dc JOIN Document d on d.id = dc.documentId WHERE d.id = ?1")
    DocumentContent findByDocumentId(String documentId);
}
