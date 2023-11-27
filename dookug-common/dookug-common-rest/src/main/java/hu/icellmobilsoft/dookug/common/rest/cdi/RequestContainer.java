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

import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import hu.icellmobilsoft.coffee.rest.cdi.BaseRequestContainer;
import hu.icellmobilsoft.dookug.common.rest.header.ProjectHeader;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.BaseGeneratorSetupType;

/**
 * Common request scope container
 * 
 * @author imre.scheffer
 *
 */
@Model
public class RequestContainer {

    @Inject
    private BaseRequestContainer baseRequestContainer;

    private ProjectHeader projectHeader;
    private ProjectHeader defaultProjectHeader = new ProjectHeader();
    private BaseGeneratorSetupType generatorSetup;

    /**
     * Producer for project header bean
     * 
     * @return project header bean
     */
    @Produces
    @RequestScoped
    public ProjectHeader getProjectHeader() {
        // vannak esetek, amikor a header nincs betoltve, es megis szotarhoz nyulunk
        // ebben az esetben elszalt WELD-000052 hibaval
        if (projectHeader == null) {
            return defaultProjectHeader;
        }
        return projectHeader;
    }

    /**
     * Setter for project header bean
     * 
     * @param projectHeader
     *            project header bean
     */
    public void setProjectHeader(ProjectHeader projectHeader) {
        this.projectHeader = projectHeader;
    }

    /**
     * Getter of request scope common object map
     * 
     * @return Request scope common object map
     */
    public Map<String, Object> getObjectMap() {
        return baseRequestContainer.getObjectMap();
    }

    /**
     * Getter of request object/entity
     * 
     * @return Request object/entity
     */
    public Object getRequestObject() {
        return baseRequestContainer.getRequestObject();
    }

    /**
     * Setter of Request object/entity
     * 
     * @param requestObject
     *            Request object/entity
     */
    public void setRequestObject(Object requestObject) {
        baseRequestContainer.setRequestObject(requestObject);
    }

    /**
     * Getter of base generator setup
     * 
     * @return the generatorSetup
     */
    public BaseGeneratorSetupType getGeneratorSetup() {
        return generatorSetup;
    }

    /**
     * Setter of base generator setup
     * 
     * @param generatorSetup
     *            the generatorSetup to set
     */
    public void setGeneratorSetup(BaseGeneratorSetupType generatorSetup) {
        this.generatorSetup = generatorSetup;
    }
}
