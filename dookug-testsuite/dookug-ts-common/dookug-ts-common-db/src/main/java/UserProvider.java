/*-
 * #%L
 * DookuG
 * %%
 * Copyright (C) 2023 - 2025 i-Cell Mobilsoft Zrt.
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
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import hu.icellmobilsoft.coffee.model.base.annotation.CurrentUser;
import hu.icellmobilsoft.coffee.model.base.javatime.AbstractAuditEntity;

/**
 * Test entity provider for {@link AbstractAuditEntity#getCreatorUser()}
 *
 * @author mate.biro
 * @since 2.2.0
 */
@ApplicationScoped
public class UserProvider {

    private static final String DEFAULT_SYSTEM_USER = "0";

    /**
     * Returns the value {@value DEFAULT_SYSTEM_USER} used during testing.
     *
     * @return {@value DEFAULT_SYSTEM_USER}
     */
    @Produces
    @CurrentUser
    public String currentUser() {
        return DEFAULT_SYSTEM_USER;
    }

}
