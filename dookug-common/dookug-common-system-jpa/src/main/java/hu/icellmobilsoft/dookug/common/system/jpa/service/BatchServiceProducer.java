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

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.persistence.EntityManager;

import hu.icellmobilsoft.dookug.common.system.jpa.jpa.EntityHelper;
import hu.icellmobilsoft.frappee.hibernate.batch.HibernateBatchService;
import hu.icellmobilsoft.frappee.hibernate.util.HibernateEntityHelper;

/**
 * Producer of the {@link BatchService}.
 *
 * @author levente.prehoda
 * @since 2.2.0
 */
@ApplicationScoped
public class BatchServiceProducer {

    /**
     * Default constructor.
     */
    public BatchServiceProducer() {
        super();
    }

    /**
     * The default producer method of the {@link BatchService}.
     * 
     * @param entityHelper
     *            the {@link EntityHelper}
     * @param em
     *            the {@link EntityManager}
     * @param hibernateEntityHelper
     *            the {@link HibernateEntityHelper}
     * @return a new {@link HibernateBatchService} instance
     */
    @Produces
    @Dependent
    public BatchService createBatchService(EntityHelper entityHelper, EntityManager em, HibernateEntityHelper hibernateEntityHelper) {
        return new BatchService(entityHelper, em, hibernateEntityHelper);
    }

    /**
     * The default disposer method of the {@link BatchService}.
     * 
     * @param batchService
     *            the batchService instance
     */
    public void destroy(@Disposes BatchService batchService) {
        if (batchService != null) {
            CDI.current().destroy(batchService);
        }
    }
}
