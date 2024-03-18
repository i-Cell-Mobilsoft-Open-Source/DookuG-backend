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
package hu.icellmobilsoft.dookug.common.jpa.entitymanager;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * Default entityManager producer
 * 
 * @author imre.scheffer
 *
 */
@Dependent
public class EntityManagerProducer {

    @PersistenceContext(unitName = "defaultPU")
    private EntityManager defaultEm;

    /**
     * default entity manager producer
     * 
     * @return the entity manager
     */
    @Produces
    @Dependent
    public EntityManager createDefaultEntityManager() {
        return defaultEm;
    }
}
