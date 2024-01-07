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
package hu.icellmobilsoft.docstore.common.health;

import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;

/**
 * Provide base functionality for health checks
 * 
 * @author laszlo.czencz
 * @since 1.0.0
 *
 */
public abstract class AbstractBaseHealthCheck {
    /**
     * {@value #NODE_NAME}
     */
    public static final String NODE_NAME = "nodeName";
    /**
     * {@value #JBOSS_NODE_NAME}
     */
    public static final String JBOSS_NODE_NAME = "jboss.node.name";
    /**
     * {@value #URL}
     */
    public static final String URL = "URL";

    /**
     * {@value #CONNECT_TIMEOUT_SEC}
     */
    public static final long CONNECT_TIMEOUT_SEC = 1;

    /**
     * create HealthCheckResponseBuilder
     * 
     * @return response builder
     */
    public HealthCheckResponseBuilder createHealthCheckResponseBuilder() {
        HealthCheckResponseBuilder builder = HealthCheckResponse.builder().name(getBuilderName());
        builder.withData(NODE_NAME, getNodeName());
        return builder;
    }

    /**
     * get unique server identifier
     * 
     * @return node name
     */
    public String getNodeName() {
        return System.getProperty(JBOSS_NODE_NAME);
    }

    /**
     * get the response builder name
     * 
     * @return builder name
     */
    public abstract String getBuilderName();

}
