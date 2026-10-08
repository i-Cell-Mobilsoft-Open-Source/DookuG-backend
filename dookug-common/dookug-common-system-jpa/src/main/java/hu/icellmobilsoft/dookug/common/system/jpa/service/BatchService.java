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
package hu.icellmobilsoft.dookug.common.system.jpa.service;

import java.time.OffsetDateTime;

import jakarta.persistence.EntityManager;

import hu.icellmobilsoft.coffee.model.base.javatime.AbstractIdentifiedAuditEntity;
import hu.icellmobilsoft.dookug.common.system.jpa.jpa.EntityHelper;
import hu.icellmobilsoft.frappee.hibernate.batch.HibernateBatchService;
import hu.icellmobilsoft.frappee.hibernate.util.HibernateEntityHelper;

/**
 * Batch service with audit handling
 *
 * @author levente.prehoda
 * @since 2.2.0
 */
public class BatchService extends HibernateBatchService {

    private final EntityHelper entityHelper;

    /**
     * Constructor
     * 
     * @param entityHelper
     *            entity helper
     * @param em
     *            entity manager
     * @param hibernateEntityHelper
     *            hibernate entity helper
     */
    public BatchService(EntityHelper entityHelper, EntityManager em, HibernateEntityHelper hibernateEntityHelper) {
        super(em, hibernateEntityHelper);
        this.entityHelper = entityHelper;
    }

    @Override
    protected <E> void handleInsertAudit(E entity) {
        if (entity instanceof AbstractIdentifiedAuditEntity e) {
            e.setCreationDate(OffsetDateTime.now());
            e.setCreatorUser(entityHelper.currentUser());
        }
    }

    @Override
    protected <E> void handleUpdateAudit(E entity) {
        if (entity instanceof AbstractIdentifiedAuditEntity e) {
            e.setModificationDate(OffsetDateTime.now());
            e.setModifierUser(entityHelper.currentUser());
        }
    }
}
