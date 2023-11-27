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
/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package hu.icellmobilsoft.dookug.common.rest.locale;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.annotation.Priority;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Alternative;
import javax.interceptor.Interceptor;

import org.apache.deltaspike.core.api.message.MessageContext;
import org.apache.deltaspike.core.impl.message.DefaultMessageResolver;
import org.apache.deltaspike.core.util.ClassUtils;

/**
 * {@code MessageResolver} for resolving localized messages with custom fallback locale logic
 *
 * @author martin.nagy
 * @since 0.3.0
 */
@Dependent
@Alternative
@Priority(Interceptor.Priority.APPLICATION + 10)
public class ProjectMessageResolver extends DefaultMessageResolver {

    private static final long serialVersionUID = 1L;

    /**
     * Resource bundle controller instance
     */
    private final ResourceBundleControl resourceBundleControl = new ResourceBundleControl();

    @Override
    public String getMessage(MessageContext messageContext, String messageTemplate, String category) {
        // we can use {{ as escaping for now
        if (messageTemplate.startsWith("{{")) {
            // in which case we just cut of the first '{'
            return messageTemplate.substring(1);
        }

        if (messageTemplate.startsWith("{") && messageTemplate.endsWith("}")) {
            String resourceKey = messageTemplate.substring(1, messageTemplate.length() - 1);

            List<String> messageSources = getMessageSources(messageContext);

            if (messageSources == null || messageSources.isEmpty()) {
                // using {} without a bundle is always an error
                return null;
            }

            // NOTE deltaspike nem kezeli a konkurens message olvasast, copy lista kell hogy ne legyen ConcurrentModificationException
            // nagy terhelés esetén jöhet elő ha több faultType is keletkezik 1 service példányon belül
            List<String> copy = new ArrayList<>(messageSources);
            Iterator<String> messageSourceIterator = copy.iterator();

            Locale locale = messageContext.getLocale();

            String currentMessageSource;
            while (messageSourceIterator.hasNext()) {
                currentMessageSource = messageSourceIterator.next();

                try {
                    // this is the line we want to override from the DefaultMessageResolver
                    ResourceBundle messageBundle = getResourceBundle(locale, currentMessageSource);

                    if (category != null && category.length() > 0) {
                        try {
                            return messageBundle.getString(resourceKey + "_" + category);
                        } catch (MissingResourceException e) {
                            // we fallback on the version without the category
                            return messageBundle.getString(resourceKey);
                        }
                    }

                    return messageBundle.getString(resourceKey);
                } catch (MissingResourceException e) {
                    if (!messageSourceIterator.hasNext()) {
                        return null;
                    }
                }
            }
        }

        return messageTemplate;
    }

    private ResourceBundle getResourceBundle(Locale locale, String currentMessageSource) {
        return ResourceBundle.getBundle(currentMessageSource, locale, ClassUtils.getClassLoader(null), resourceBundleControl);
    }

    private static class ResourceBundleControl extends ResourceBundle.Control {
        @Override
        public Locale getFallbackLocale(String baseName, Locale locale) {
            return isDefault(locale) ? null : getDefaultLocale();
        }

        private boolean isDefault(Locale locale) {
            return locale.toString().equals(ProjectLocaleResolver.DEFAULT_LANGUAGE.toLowerCase());
        }

        private Locale getDefaultLocale() {
            return new Locale(ProjectLocaleResolver.DEFAULT_LANGUAGE);
        }
    }
}
