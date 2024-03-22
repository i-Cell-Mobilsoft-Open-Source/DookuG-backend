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
package hu.icellmobilsoft.dookug.common.system.rest.rest;

import jakarta.inject.Inject;

import hu.icellmobilsoft.coffee.cdi.logger.AppLogger;
import hu.icellmobilsoft.coffee.cdi.logger.ThisLogger;
import hu.icellmobilsoft.dookug.api.rest.document.DocumentGenerateMultipartForm;
import hu.icellmobilsoft.dookug.common.rest.cdi.RequestContainer;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentGenerateWithTemplatesRequest;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.StoredTemplateDocumentGenerateRequest;

/**
 * Base REST service for all REST endpoint
 * 
 * @author imre.scheffer
 *
 */
public abstract class BaseRestService extends hu.icellmobilsoft.coffee.rest.rest.BaseRestService {

    @Inject
    @ThisLogger
    private AppLogger log;

    @Inject
    private RequestContainer requestContainer;

    /**
     * save the generator setup to the {@link RequestContainer}
     * 
     * @param request
     *            the input request
     */
    protected void saveGeneratorSetup(DocumentGenerateWithTemplatesRequest request) {
        if (request != null) {
            requestContainer.setGeneratorSetup(request.getGeneratorSetup());
        }
    }

    /**
     * save the generator setup to the {@link RequestContainer}
     * 
     * @param form
     *            the input form
     */
    protected void saveGeneratorSetup(DocumentGenerateMultipartForm form) {
        if (form != null && form.getRequest() != null) {
            requestContainer.setGeneratorSetup(form.getRequest().getGeneratorSetup());
        }
    }
    
    /**
     * save the generator setup to the {@link RequestContainer}
     * 
     * @param request
     *            the input request
     */
    protected void saveGeneratorSetup(StoredTemplateDocumentGenerateRequest request) {
        if (request != null) {
            requestContainer.setGeneratorSetup(request.getGeneratorSetup());
        }
    }

}
