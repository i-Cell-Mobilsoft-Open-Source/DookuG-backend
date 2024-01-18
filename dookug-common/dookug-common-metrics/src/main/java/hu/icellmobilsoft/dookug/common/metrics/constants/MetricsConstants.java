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
 * Minden microprofile-metrics konstansok gyujtoje
 *
 * @author tamas.cserhati
 * @since 1.0.0
 */
public interface MetricsConstants {

    /**
     * microprofile-metrics @Timed annotacioval vagy csak kodban kezelt metrics
     * timer konstansok
     */
    interface Timer {

        /**
         * application_http_response_time_ms <br>
         * Beérkező HTTP kérések száma és a hozzájuk tartozó válaszidők
         * mérésének metrika neve.<br>
         * {@value Description#HTTP_RESPONSE_TIME_DESCRIPTION}
         *
         * @see Tag#RESPONSE_CODE
         */
        String HTTP_RESPONSE_TIME = "http_response_time";
    }

    /**
     * microprofile-metrics kezelesnel felhasznalt tag konstansok
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
     * microprofile-metrics kezelesnel felhasznalt description konstansok
     */
    interface Description {

        /**
         * Beérkező HTTP kérések száma és a hozzájuk tartozó válaszidők.
         */
        String HTTP_RESPONSE_TIME_DESCRIPTION = "Beérkező HTTP kérések száma és a hozzájuk tartozó válaszidők.";

        /**
         * Beérkező HTTP kérések száma és a hozzájuk tartozó válaszidők.
         */
        String STORAGE_DELETE_OBJECT_ERROR_TOTAL_DESCRIPTION = "Hibás törlési események számlálója.";

        /**
         * Aktiv redis pool kapcsolatok
         */
        String JEDIS_POOL_ACTIVE_DESCRIPTION = "Active connection number";

        /**
         * Idle redis pool kapcsolatok
         */
        String JEDIS_POOL_IDLE_DESCRIPTION = "Idle connection number";
    }

    /**
     * microprofile-metrics @Counted kodban kezelt metrics counter konstansok
     */
    interface Counter {

        /**
         * storage_delete_object_error_total<br>
         * Hibás törlési események<br>
         * {@value Description#STORAGE_DELETE_OBJECT_ERROR_TOTAL_DESCRIPTION}
         */
        String STORAGE_DELETE_OBJECT_ERROR_TOTAL = "storage_delete_object_error_total";
    }

    /**
     * Gauge konstansok
     *
     */
    interface Gauge {

        /**
         * Aktiv redis pool kapcsolatok
         */
        String JEDIS_POOL_ACTIVE = "jedis_pool_active";

        /**
         * Idle redis pool kapcsolatok
         */
        String JEDIS_POOL_IDLE = "jedis_pool_idle";

    }

}
