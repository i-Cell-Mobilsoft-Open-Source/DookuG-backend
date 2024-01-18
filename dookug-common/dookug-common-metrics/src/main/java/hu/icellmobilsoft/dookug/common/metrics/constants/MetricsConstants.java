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
package hu.icellmobilsoft.dookug.common.metrics.constants;

/**
 * Every microprofile-metrics constants
 *
 * @author tamas.cserhati
 * @since 1.0.0
 */
public interface MetricsConstants {

    /**
     * microprofile-metrics with @Timed annotation or code-only metrics timer constants
     */
    interface Timer {

        /**
         * application_http_response_time_ms <br>
         * Number of incoming HTTP requests and their response times measuring metric name<br>
         * {@value Description#HTTP_RESPONSE_TIME_DESCRIPTION}
         *
         * @see Tag#RESPONSE_CODE
         */
        String HTTP_RESPONSE_TIME = "http_response_time";
    }

    /**
     * tag constants used with microprofile-metrics
     */
    interface Tag {

        /**
         * {@value #RESPONSE_CODE}
         */
        String RESPONSE_CODE = "response_code";
        /**
         * {@value #OPERATION}
         */
        String OPERATION = "operation";
        /**
         * {@value #STORAGE_HANDLER}
         */
        String STORAGE_HANDLER = "storage_handler";
        /**
         * {@value #CONFIG_KEY}
         */
        String CONFIG_KEY = "configKey";
    }

    /**
     * description constants used with microprofile-metrics
     */
    interface Description {

        /**
         * Number of incoming HTTP requests and their response times.
         */
        String HTTP_RESPONSE_TIME_DESCRIPTION = "Beérkező HTTP kérések száma és a hozzájuk tartozó válaszidők.";

        /**
         * Number of incoming HTTP requests and their response times.
         */
        String STORAGE_DELETE_OBJECT_ERROR_TOTAL_DESCRIPTION = "Hibás törlési események számlálója.";

        /**
         * Active redis pool connections
         */
        String JEDIS_POOL_ACTIVE_DESCRIPTION = "Active connection number";

        /**
         * Idle redis pool connections
         */
        String JEDIS_POOL_IDLE_DESCRIPTION = "Idle connection number";
    }

    /**
     * metrics counter constants handled in microprofile-metrics @Counted
     */
    interface Counter {

        /**
         * storage_delete_object_error_total<br>
         * Incorrect delete events<br>
         * {@value Description#STORAGE_DELETE_OBJECT_ERROR_TOTAL_DESCRIPTION}
         */
        String STORAGE_DELETE_OBJECT_ERROR_TOTAL = "storage_delete_object_error_total";
    }

    /**
     * Gauge constants
     */
    interface Gauge {

        /**
         * Active redis pool connections
         */
        String JEDIS_POOL_ACTIVE = "jedis_pool_active";

        /**
         * Idle redis pool connections
         */
        String JEDIS_POOL_IDLE = "jedis_pool_idle";

    }

}
