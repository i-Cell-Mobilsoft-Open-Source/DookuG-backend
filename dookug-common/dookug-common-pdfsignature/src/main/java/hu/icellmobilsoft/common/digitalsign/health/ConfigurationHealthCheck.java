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
package hu.icellmobilsoft.common.digitalsign.health;

import java.util.Iterator;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.operator.DefaultSignatureAlgorithmIdentifierFinder;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Startup;

import hu.icellmobilsoft.coffee.se.logging.Logger;

/**
 * Health check for database availability
 * 
 * @author tamas.cserhati
 * @since 1.1.0
 *
 */
@ApplicationScoped
public class ConfigurationHealthCheck {

    private static final String OK = "OK";

    private static final String SIGNATURE_CONFIGURATION = "signatureConfiguration";

    private HealthCheckResponseBuilder builder = HealthCheckResponse.builder().name(SIGNATURE_CONFIGURATION).up();

    /**
     * Bouncycastle (PdfBox) signature algorithm identifier finder for checking name resolution
     */
    private static final DefaultSignatureAlgorithmIdentifierFinder signatureFinder = new DefaultSignatureAlgorithmIdentifierFinder();

    @Inject
    private Logger logger;

    /**
     * Configuration check
     */
    @PostConstruct
    public void init() {
        Config config = ConfigProvider.getConfig();
        Iterator<String> propertyNames = config.getPropertyNames().iterator();
        while (propertyNames.hasNext()) {
            String propertyName = propertyNames.next();
            if (StringUtils.endsWithIgnoreCase(propertyName, "signatureAlgorithm") && StringUtils.containsIgnoreCase(propertyName, "pdfBox")) {
                builder = checkPdfBoxSignatureAlgorithm(builder, propertyName);
            }
        }
    }

    /**
     * Check pdf signature configuration
     * 
     * @return The created {@link HealthCheckResponse} contains information about whether the database is reachable.
     */
    public HealthCheckResponse checkPdfSignatureConfiguration() {
        try {
            return builder.build();
        } catch (Throwable e) {
            // we catch every exception and error so that the probe doesn't encounter any unhandled errors or exceptions
            logger.error("Error occured while checking configuration.", e);
            return builder.withData("warning", e.getLocalizedMessage()).up().build();
        }
    }

    private HealthCheckResponseBuilder checkPdfBoxSignatureAlgorithm(HealthCheckResponseBuilder builder, String propertyName) {
        try {
            signatureFinder.find(ConfigProvider.getConfig().getConfigValue(propertyName).getValue());
        } catch (Exception e) {
            builder = builder.withData(propertyName, e.getLocalizedMessage()).down();
        }
        return builder;
    }

    /**
     * HealthCheck producer
     * 
     * @return the {@link HealthCheck}
     */
    @Produces
    @Startup
    public HealthCheck producePdfSignatureConfigStart() {
        return this::checkPdfSignatureConfiguration;
    }

}
