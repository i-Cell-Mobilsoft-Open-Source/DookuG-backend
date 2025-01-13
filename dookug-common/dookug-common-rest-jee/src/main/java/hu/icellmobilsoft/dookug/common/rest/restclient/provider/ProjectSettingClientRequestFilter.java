/*-
 * #%L
 * DookuG
 * %%
 * Copyright (C) 2023 - 2024 i-Cell Mobilsoft Zrt.
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
package hu.icellmobilsoft.dookug.common.rest.restclient.provider;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;

import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.coffee.module.mp.restclient.RestClientPriority;
import hu.icellmobilsoft.dookug.common.rest.header.IProjectHeader;
import hu.icellmobilsoft.dookug.common.rest.header.ProjectHeader;

/**
 * Project level Rest Client request setting filter
 *
 * @author tamas.cserhati
 * @since 1.0.0
 */
@Priority(value = RestClientPriority.REQUEST_SETTING - 100)
@Dependent
public class ProjectSettingClientRequestFilter implements ClientRequestFilter {

    @Override
    public void filter(ClientRequestContext requestContext) {
        Instance<ProjectHeader> instance = CDI.current().select(ProjectHeader.class);
        if (instance.isResolvable()) {
            ProjectHeader projectHeader = instance.get();
            if (!requestContext.getHeaders().containsKey(IProjectHeader.HEADER_LANGUAGE) && StringUtils.isNotBlank(projectHeader.getLanguage())) {
                requestContext.getHeaders().add(IProjectHeader.HEADER_LANGUAGE, projectHeader.getLanguage());
            }
            if (!requestContext.getHeaders().containsKey(IProjectHeader.HEADER_FORWARDED) && StringUtils.isNotBlank(projectHeader.getForwarded())) {
                requestContext.getHeaders().add(IProjectHeader.HEADER_FORWARDED, projectHeader.getForwarded());
            }
        }
    }
}
