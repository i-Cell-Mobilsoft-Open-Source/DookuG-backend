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
package hu.icellmobilsoft.dookug.common.jpa.health;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Startup;

import hu.icellmobilsoft.coffee.jpa.health.DatabaseHealth;
import hu.icellmobilsoft.coffee.jpa.health.DatabaseHealthResourceConfig;
import hu.icellmobilsoft.coffee.se.logging.Logger;

/**
 * Health check for database availability
 * 
 * @author tamas.cserhati
 * @since 0.5.0
 *
 */
@ApplicationScoped
public class DatabaseHealthCheck {

    public static final String ORACLE_DIALECT = "Oracle";
    public static final String DEFAULT_PU = "defaultPU";
    public static final String HIBERNATE_DIALECT = "HIBERNATE_DIALECT";
    public static final String ORACLE_DS_CONNECTION_URL = "ORACLE_DS_CONNECTION_URL";
    public static final String POSTGRESQL_DS_CONNECTION_URL = "POSTGRESQL_DS_CONNECTION_URL";

    @Inject
    private Logger logger;

    @Inject
    private DatabaseHealth databaseHealth;

    @Inject
    private Config config;

    private DatabaseHealthResourceConfig healthConfig;

    /**
     * Init {@link DatabaseHealthResourceConfig} by using the given database dialect
     */
    @PostConstruct
    public void initHealthConfig() {
        // get db type by dialect information, we should extract somehow from hibernate...
        healthConfig = new DatabaseHealthResourceConfig();
        // e. g. org.hibernate.dialect.OracleDialect
        String dialect = config.getValue(HIBERNATE_DIALECT, String.class);
        if (dialect.contains(ORACLE_DIALECT)) {
            healthConfig.setBuilderName("oracle");
            healthConfig.setDatasourceUrl(config.getOptionalValue(ORACLE_DS_CONNECTION_URL, String.class).orElse(DEFAULT_PU));
        } else {
            healthConfig.setBuilderName("postgre");
            healthConfig.setDatasourceUrl(config.getOptionalValue(POSTGRESQL_DS_CONNECTION_URL, String.class).orElse(DEFAULT_PU));
        }
        healthConfig.setDsName("icellmobilsoftDS");

    }

    /**
     * Check database resource, using the env HIBERNATE_DIALECT property to determine the database type
     * 
     * @return The created {@link HealthCheckResponse} contains information about whether the database is reachable.
     */
    public HealthCheckResponse checkDatabase() {
        try {
            return databaseHealth.checkDatabaseConnection(healthConfig);
        } catch (Throwable e) {
            // we catch every exception and error so that the probe doesn't encounter any unhandled errors or exceptions
            logger.error("Error occured while checking database resource.", e);
            return HealthCheckResponse.builder().name(healthConfig.getBuilderName()).up().build();
        }
    }

    @Produces
    @Startup
    public HealthCheck produceDatabaseStartup() {
        return this::checkDatabase;
    }

}
