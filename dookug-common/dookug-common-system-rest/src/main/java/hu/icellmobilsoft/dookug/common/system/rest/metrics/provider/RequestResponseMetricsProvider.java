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
package hu.icellmobilsoft.dookug.common.system.rest.metrics.provider;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.ext.WriterInterceptor;
import jakarta.ws.rs.ext.WriterInterceptorContext;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.metrics.Metadata;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.MetricType;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.Tag;
import org.eclipse.microprofile.metrics.Timer;

import hu.icellmobilsoft.coffee.cdi.logger.AppLogger;
import hu.icellmobilsoft.coffee.cdi.logger.ThisLogger;
import hu.icellmobilsoft.coffee.dto.url.BaseServicePath;

/**
 * JAXRS provider that handles the metrics around request/response
 * 
 * @author mark.petrenyi
 * @since 0.1.0
 */
@Provider
public class RequestResponseMetricsProvider implements ContainerRequestFilter, WriterInterceptor {

    @Inject
    @ThisLogger
    private AppLogger log;

    @Inject
    private MetricsContainer metricsContainer;

    @Inject
    private MetricRegistry metricRegistry;

    @Context
    private HttpServletResponse httpServletResponse;

    @Context
    private UriInfo uriInfo;

    /**
     * Manage metrics running at the beginning of a request or prepare data
     * 
     * @param requestContext
     *            ContainerRequestContext
     * @throws IOException
     *             if error
     */
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String url = uriInfo.getAbsolutePath().toASCIIString();
        boolean standardHttp = StringUtils
                .containsAny(requestContext.getMethod(), HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE);
        if (standardHttp && StringUtils.contains(url, BaseServicePath.SYSTEM)) {
            metricsContainer.setStartTime(LocalDateTime.now());
        }
    }

    /**
     * Create metrics after response creation
     * 
     * @param context
     *            WriterInterceptorContext
     * @throws IOException
     *             if error
     * @throws WebApplicationException
     *             if error
     */
    @Override
    public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
        String url = uriInfo.getAbsolutePath().toASCIIString();
        if (metricsContainer.getStartTime() != null && StringUtils.contains(url, BaseServicePath.SYSTEM)) {
            updateResponseTimer(url);
        }
        context.proceed();
    }

    /**
     * Refresh metrics
     *
     * @param url
     *            url
     */
    private void updateResponseTimer(String url) {
        Metadata metadata = Metadata.builder() //
                .withName("sample_http_response_time")
                .withDescription("Beérkező HTTP kérések száma és a hozzájuk tartozó válaszidők.")
                .withType(MetricType.TIMER)
                .withUnit(MetricUnits.MILLISECONDS)
                .build();
        Tag responseCodeTag = new Tag("url", url);

        Timer timer = metricRegistry.timer(metadata, responseCodeTag);
        timer.update(Duration.between(metricsContainer.getStartTime(), LocalDateTime.now()));
    }
}
