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
package hu.icellmobilsoft.dookug.common.rest.filter;

import java.io.IOException;

import javax.annotation.Priority;
import javax.enterprise.inject.spi.CDI;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.dookug.common.rest.cdi.RequestContainer;
import hu.icellmobilsoft.dookug.common.rest.header.ProjectHeader;

/**
 * Altalanos util filter, a request header-ek feldolgozása az elsődleges feladata
 * <p>
 * Az exception handler-ek előtt kell futnia, hogy a hibaüzenetek nyelvesítése megfelelő legyen. (lásd: {@link PreMatching})
 *
 * @author imre.scheffer
 */
@Provider
@PreMatching
@Priority(CustomPriorities.PRE_AUTHENTICATION)
public class RequestFilter implements ContainerRequestFilter {

    @Context
    private HttpServletRequest request;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        RequestContainer requestContainer = CDI.current().select(RequestContainer.class).get();
        ProjectHeader header = ProjectHeader.readHeaders(requestContext.getHeaders());

        // Set remote IP if in Forwarded header not specified.
        if (StringUtils.isBlank(header.getForwarded()) && request != null) {
            header.setForwarded(request.getRemoteAddr());
        }
        requestContainer.setProjectHeader(header);
    }
}
