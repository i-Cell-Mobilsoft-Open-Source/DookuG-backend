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
import jakarta.ws.rs.PathParam;

import hu.icellmobilsoft.coffee.cdi.logger.AppLogger;
import hu.icellmobilsoft.coffee.cdi.logger.ThisLogger;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.se.function.BaseExceptionFunction5;
import hu.icellmobilsoft.dookug.api.rest.document.form.DocumentGenerateMultipartForm;
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

    /**
     * Wraps the business logic method call in order to handle common logging and exception handling with three {@link PathParam}s
     *
     * @param <PARAM1>
     *            type of {@code param1}
     * @param <PARAM2>
     *            type of {@code param2}
     * @param <PARAM3>
     *            type of {@code param3}
     * @param <PARAM4>
     *            type of {@code param4}
     * @param <PARAM5>
     *            type of {@code param5}
     * @param <RESPONSE>
     *            type of the response
     * @param function
     *            the function doing the real business logic
     * @param param1
     *            first parameter value to pass to the business logic
     * @param param2
     *            second parameter value to pass to the business logic
     * @param param3
     *            third parameter value to pass to the business logic
     * @param param4
     *            fourth parameter value to pass to the business logic
     * @param param5
     *            fifth parameter value to pass to the business logic
     * @param methodName
     *            the REST method name to log
     * @param param1Name
     *            the first REST method parameter name to log
     * @param param2Name
     *            the second REST method parameter name to log
     * @param param3Name
     *            the third REST method parameter name to log
     * @param param4Name
     *            the fourth REST method parameter name to log
     * @param param5Name
     *            the fifth REST method parameter name to log
     * @return what the function returns
     * @throws BaseException
     *             thrown by {@code function}
     */
    protected <PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, RESPONSE> RESPONSE wrapPathParam5(
            BaseExceptionFunction5<PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, RESPONSE> function, PARAM1 param1, PARAM2 param2, PARAM3 param3,
            PARAM4 param4, PARAM5 param5,
            String methodName, String param1Name, String param2Name, String param3Name, String param4Name, String param5Name) throws BaseException {
        String methodInfo = getCalledMethodWithOnlyPathParams(methodName, param1Name, param2Name, param3Name, param4Name, param5Name);
        logEnter(methodInfo, param1, param2, param3, param4, param5);
        try {
            return function.apply(param1, param2, param3, param4, param5);
        } finally {
            logReturn(methodInfo, param1, param2, param3, param4, param5);
        }
    }

    /**
     * Returns given method name concatenated with given {@link PathParam} names.
     *
     * @param methodName
     *            the REST method name e.g. getCustomerInfoByUserId
     * @param paramNames
     *            the REST param names of {@link PathParam}s e.g. userId,balanceId
     * @return e.g. " getCustomerInfoByUserId(userId: [{0}])"
     */
    private String getCalledMethodWithOnlyPathParams(String methodName, String... paramNames) {
        return getCalledMethodWithParamsBase(methodName, paramNames) + ")";
    }

}
