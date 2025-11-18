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
package hu.icellmobilsoft.dookug.common.system.rest.action;

import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import hu.icellmobilsoft.coffee.cdi.logger.AppLogger;
import hu.icellmobilsoft.coffee.cdi.logger.ThisLogger;
import hu.icellmobilsoft.coffee.dto.common.commonservice.ContextType;
import hu.icellmobilsoft.coffee.rest.action.AbstractBaseAction;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.se.util.string.RandomUtil;
import hu.icellmobilsoft.coffee.tool.utils.compress.GZIPUtil;
import hu.icellmobilsoft.coffee.tool.utils.date.DateUtil;
import hu.icellmobilsoft.dookug.common.cdi.document.Document;

/**
 * Base class for all other business logic action class
 * 
 * @author imre.scheffer
 * @since 0.1.0
 */
@Model
public class BaseAction extends AbstractBaseAction {

    @Inject
    @ThisLogger
    private AppLogger log;

    /**
     * Create a new Context for responses
     * 
     * @return filled ContextType with current time and random requestId
     */
    public ContextType createContext() {
        ContextType context = new ContextType();
        context.setRequestId(RandomUtil.generateId());
        context.setTimestamp(DateUtil.nowUTCTruncatedToMillis());
        return context;
    }
}
