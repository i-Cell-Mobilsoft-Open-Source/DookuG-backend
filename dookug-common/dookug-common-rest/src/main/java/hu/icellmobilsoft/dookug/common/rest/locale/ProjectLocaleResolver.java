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
package hu.icellmobilsoft.dookug.common.rest.locale;

import java.util.Locale;

import javax.annotation.Priority;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.interceptor.Interceptor;

import org.apache.commons.lang3.StringUtils;
import org.apache.deltaspike.core.impl.message.DefaultLocaleResolver;

import hu.icellmobilsoft.coffee.cdi.logger.AppLogger;
import hu.icellmobilsoft.coffee.cdi.logger.ThisLogger;
import hu.icellmobilsoft.dookug.common.rest.header.ProjectHeader;

/**
 * Locale resolver by header {@value ProjectHeader#HEADER_LANGUAGE} value
 * 
 * @author imre.scheffer
 *
 */
@Dependent
@Alternative
@Priority(Interceptor.Priority.APPLICATION + 10)
public class ProjectLocaleResolver extends DefaultLocaleResolver {

    private static final long serialVersionUID = 1L;

    /**
     * {@value #DEFAULT_LANGUAGE} value
     */
    public static final String DEFAULT_LANGUAGE = "hu";

    /**
     * Bean of http header
     */
    @Inject
    private ProjectHeader header;

    /**
     * Logger
     */
    @Inject
    @ThisLogger
    private AppLogger log;

    @Override
    public Locale getLocale() {
        if (header != null) {
            log.debug("header language: [{0}]", header.getLanguage());
            String language = header.getLanguage();
            if (StringUtils.isNotBlank(language)) {
                return new Locale(language);
            }
        }
        return new Locale(DEFAULT_LANGUAGE);
    }
}
