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
package hu.icellmobilsoft.dookug.common.rest.cdi;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * Collection for containers in different applications
 * 
 * @author imre.scheffer
 *
 */
@Named
@Dependent
public class AppContainer {

    @Inject
    private RequestContainer requestContainer;

    /**
     * Get Request scope container
     * 
     * @return Request scope container
     */
    public RequestContainer getRequestContainer() {
        return requestContainer;
    }

    /**
     * Set Request scope container
     * 
     * @param requestContainer
     *            Request scope container
     */
    public void setRequestContainer(RequestContainer requestContainer) {
        this.requestContainer = requestContainer;
    }
}
