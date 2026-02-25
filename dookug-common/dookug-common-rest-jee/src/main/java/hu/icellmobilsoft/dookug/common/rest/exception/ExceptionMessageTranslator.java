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
package hu.icellmobilsoft.dookug.common.rest.exception;

import java.text.MessageFormat;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Alternative;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptor;
import jakarta.xml.bind.JAXBException;

import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseExceptionResultType;
import hu.icellmobilsoft.coffee.dto.common.commonservice.FunctionCodeType;
import hu.icellmobilsoft.coffee.dto.exception.RestClientResponseException;
import hu.icellmobilsoft.coffee.rest.cdi.BaseApplicationContainer;
import hu.icellmobilsoft.coffee.rest.exception.DefaultExceptionMessageTranslator;
import hu.icellmobilsoft.coffee.rest.projectstage.ProjectStage;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;

/**
 * Exception translator implementation for exception throwing
 * 
 * @author imre.scheffer
 *
 */
@Dependent
@Alternative
@Priority(Interceptor.Priority.APPLICATION + 10)
public class ExceptionMessageTranslator extends DefaultExceptionMessageTranslator {

    @Inject
    private BaseApplicationContainer baseApplicationContainer;

    @Inject
    private ProjectStage projectStage;

    /** {@inheritDoc} */
    @Override
    public void addCommonInfo(BaseExceptionResultType dto, Exception e, Enum<?> faultType) {
        boolean putExceptionToResponse = !projectStage.isProductionStage();
        if (putExceptionToResponse) {
            if (e instanceof JAXBException) {
                dto.setException(getLinkedExceptionLocalizedMessage((JAXBException) e));
            } else {
                dto.setException(e.getLocalizedMessage());
            }

            if (e.getCause() != null) {
                var causedBy = new BaseExceptionResultType();
                addCausedByInfo(causedBy, e.getCause(), faultType);
                dto.setCausedBy(causedBy);
            }

            dto.setClassName(e.getClass().getName());
        }
        dto.setFaultType(faultType.name());
        dto.setFuncCode(FunctionCodeType.ERROR);

        // A localized response is needed according to the fault type
        String localizedMessage = getLocalizedMessage(faultType);

        if (!projectStage.isProductionStage()) {
            // if not in production stage, add exception message to localized fault type message if it contains a placeholder
            String message = MessageFormat.format(localizedMessage, e.getLocalizedMessage());
            dto.setMessage(message);
        } else {
            dto.setMessage(localizedMessage);
        }

        if (e instanceof RestClientResponseException) {
            var restClientResponseException = (RestClientResponseException) e;
            dto.setService(restClientResponseException.getService());
        } else {
            dto.setService(baseApplicationContainer.getCoffeeAppName());
        }
    }

    private void addCausedByInfo(BaseExceptionResultType dto, Throwable t, Enum<?> faultType) {
        dto.setClassName(t.getClass().getName());
        dto.setMessage(t.getLocalizedMessage());
        if (t instanceof BaseException) {
            dto.setFaultType(((BaseException) t).getFaultTypeEnum().name());
        } else {
            dto.setFaultType(faultType.name());
        }
        dto.setFuncCode(FunctionCodeType.ERROR);

        if (t.getCause() != null) {
            var causedBy = new BaseExceptionResultType();
            addCausedByInfo(causedBy, t.getCause(), faultType);
            dto.setCausedBy(causedBy);
        }
    }
}
