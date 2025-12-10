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
package hu.icellmobilsoft.dookug.document.service.action.test;

import jakarta.enterprise.inject.Model;

import hu.icellmobilsoft.coffee.dto.exception.InvalidParameterException;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.dookug.common.system.rest.action.BaseAction;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplateQueryRequest;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplateQueryResponse;

/**
 * Stored template action
 *
 * @author mate.biro
 * @since 2.2.0
 */
@Model
public class StoredTemplateAction extends BaseAction {

    /**
     * Template query, can be filtered and paginated
     *
     * @param request
     *            {@link TemplateQueryRequest}
     * @return {@link TemplateQueryResponse}
     * @throws BaseException
     *             on error
     */
    public TemplateQueryResponse postTemplateQuery(TemplateQueryRequest request) throws BaseException {
        if (request == null) {
            throw new InvalidParameterException("request cannot be null!");
        }

        // TODO implement template query logic

        TemplateQueryResponse response = new TemplateQueryResponse();
        handleSuccessResultType(response, request);
        return response;

    }
}
