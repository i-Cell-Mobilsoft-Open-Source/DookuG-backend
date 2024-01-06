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
package hu.icellmobilsoft.dookug.api.url;

/**
 * Base http path constants
 *
 * @author Imre Scheffer
 * @since 0.1.0
 */
public interface IServicePath {

    /**
     * Epmpty String
     */
    String EMPTY = "";
    /**
     * '?' String for http rest paramer concatenation
     */
    String SIGN_QUESTION = "?";
    /**
     * '=' String for http rest paramer value
     */
    String SIGN_EQUALS = "=";
    /**
     * &amp; String for http rest paramer concatenation
     */
    String SIGN_AND = "&";

    /**
     * Rest path prefix for public UI (Frontend, Mobile...) category URL
     */
    String PREFIX_REST = "/rest";

    /**
     * Rest path prefix for component inner communication (another inner service) category URL
     */
    String PREFIX_SYSTEM = "/system";

    /**
     * Rest path prefix for public UI and service withput authentication (Frontend, Mobile, API) category URL
     */
    String PREFIX_PUBLIC = "/public";

    /**
     * Rest path prefix for external partner (ticketing partner, toll system...) category URL
     */
    String PREFIX_EXTERNAL = "/external";

    /**
     * Rest path prefix for internal partner service (document service, paying service) category URL
     */
    String PREFIX_INTERNAL = "/internal";

    /**
     * Rest path prefix for internal test service (data generators, flow accelerator) category URL
     */
    String PREFIX_TEST = "/test";

    /**
     * Rest path prefix for maintenance service (for administrators) category URL
     */
    String PREFIX_MAINTENANCE = "/maintenance";

    /**
     * Rest path prefix for customer domain URL
     */
    String PREFIX_CUSTOMER = "/customer";

    /**
     * Rest path prefix for exemption domain URL
     */
    String PREFIX_EXEMPTION = "/exemption";

    /**
     * Rest path prefix for eobu domain URL
     */
    String PREFIX_EOBU = "/eobu";

    /**
     * Common constant for identifier
     */
    String PARAM_ID = "id";

    /**
     * Common constant for identifier applicated in rest path
     */
    String ID = "/{" + PARAM_ID + "}";

    /**
     * Common constant for user identififier
     */
    String PARAM_USER_ID = "userId";

    /**
     * Common constant for user identifier applicated in rest path
     */
    String USER_ID = "/{" + PARAM_USER_ID + "}";

    /**
     * Common constant for date
     */
    String PARAM_DATE = "date";

    /**
     * Common constant for date applicated in rest path
     */
    String DATE = "/{" + PARAM_DATE + "}";

    /**
     * Common constant for from-date applicated in rest path
     */
    String PARAM_FROM_DATE_TIME = "fromDateTime";
    /**
     * Common constant for to-date applicated in rest path
     */
    String PARAM_TO_DATE_TIME = "toDateTime";
    /**
     * Common constant for size limit applicated in rest path
     */
    String PARAM_LIMIT = "limit";

    /**
     * Rest path common constant for bussiness list content response in request rest path
     */
    String LIST = "/list";

    /**
     * Rest path common constant for bussiness info content response in request rest path
     */
    String INFO = "/info";

    /**
     * Common constant for bussiness paginated query list content response in request rest path
     */
    String QUERY = "/query";

    /**
     * Rest path common constant for META-INF/MANIFET.MF file content in response
     */
    String VERSION_INFO = "/versionInfo";

    /**
     * Rest path common constant for evict all cache data in service
     */
    String EVICT = "/evict";

    /**
     * Rest path common constant for failover
     */
    String FAILOVER = "/failover";

    /**
     * Rest path common constant for reprocess
     */
    String REPROCESS = "/reprocess";

    /**
     * Rest path common constant for checking
     */
    String CHECK = "/check";

    /**
     * Rest path common constant for cleanup
     */
    String CLEANUP = "/cleanup";

    /**
     * Rest path common constant for resend
     */
    String RESEND = "/resend";

    /**
     * Rest path common constant for media
     */
    String MEDIA = "/media";

    /**
     * Customer id param name
     */
    String PARAM_CUSTOMER_ID = "customerId";

}
