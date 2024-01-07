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

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

/**
 * Provides rediness and liveness check for microprofile-health api
 * 
 * @author laszlo.czencz
 * @since 1.0.0
 */
public interface IHealth {

    /**
     * check rediness
     * 
     * @return health check invocation response
     */
    HealthCheckResponse checkReadiness();

    /**
     * default liveness check - we dont have liveness logic
     * 
     * @return the default liveness check
     */
    default HealthCheck checkLiveness() {
        return () -> HealthCheckResponse.builder().up().build();
    }

    /**
     * get the checked resource name
     * 
     * @return the builder name
     */
    String getBuilderName();

}
