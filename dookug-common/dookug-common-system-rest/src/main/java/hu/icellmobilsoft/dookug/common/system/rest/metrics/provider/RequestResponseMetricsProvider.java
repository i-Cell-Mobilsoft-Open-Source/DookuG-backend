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
import java.util.concurrent.TimeUnit;

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

import hu.icellmobilsoft.coffee.cdi.logger.AppLogger;
import hu.icellmobilsoft.coffee.cdi.logger.ThisLogger;
import hu.icellmobilsoft.coffee.dto.url.BaseServicePath;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

/**
 * JAXRS provider that handles the metrics around request/response
 * 
 * @author mark.petrenyi
 * @since 0.1.0
 */
@Provider
public class RequestResponseMetricsProvider implements ContainerRequestFilter, WriterInterceptor {

    private static final String DESC_RESPONSE_TIME = "Number of incoming HTTP requests and their response times.";

    @Inject
    @ThisLogger
    private AppLogger log;

    @Inject
    private MetricsContainer metricsContainer;

    @Inject
    private MeterRegistry meterRegistry;

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
        Timer timer = Timer.builder("document_service_http_response_time").description(DESC_RESPONSE_TIME).tags("url", url).register(meterRegistry);
        timer.record(Duration.between(metricsContainer.getStartTime(), LocalDateTime.now()).toMillis(), TimeUnit.MILLISECONDS);
    }
}
