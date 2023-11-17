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
package hu.icellmobilsoft.dookug.common.system.jpa.service;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import hu.icellmobilsoft.coffee.model.base.javatime.AbstractIdentifiedAuditEntity;
import hu.icellmobilsoft.coffee.tool.utils.date.DateUtil;
import hu.icellmobilsoft.dookug.common.system.jpa.jpa.EntityHelper;

/**
 * Batch mentessekkel foglalkozo osztaly
 * 
 * @author imre.scheffer
 * @since 0.1.0
 */
@Dependent
public class BatchService extends hu.icellmobilsoft.coffee.jpa.sql.batch.BatchService {

    @Inject
    private EntityHelper entityHelper;

    @Override
    protected <E> void handleInsertAudit(E entity) {
        if (entity instanceof AbstractIdentifiedAuditEntity) {
            AbstractIdentifiedAuditEntity e = (AbstractIdentifiedAuditEntity) entity;
            e.setCreationDate(DateUtil.nowUTC());
            e.setCreatorUser(entityHelper.currentUser());
        }
    }

    @Override
    protected <E> void handleUpdateAudit(E entity) {
        if (entity instanceof AbstractIdentifiedAuditEntity) {
            AbstractIdentifiedAuditEntity e = (AbstractIdentifiedAuditEntity) entity;
            e.setModificationDate(DateUtil.nowUTC());
            e.setModifierUser(entityHelper.currentUser());
        }
    }
}
