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
package hu.icellmobilsoft.dookug.common.rest.header;

import hu.icellmobilsoft.coffee.dto.common.LogConstants;

/**
 * Basic REST HTTP header constants
 * 
 * @author Imre Scheffer
 * 
 * @since 0.1.0
 */

public interface IProjectHeader {
    /**
     * Session identifier over service. Value is main part of logging
     */
    String HEADER_SID = LogConstants.LOG_SESSION_ID;

    /**
     * Authentication username
     */
    String HEADER_USERNAME = "username";

    /**
     * Source host 1
     */
    String HEADER_HOST = "HOST";

    /**
     * Source host 2
     */
    String HEADER_XHOST = "X-HOST";

    /**
     * Request source: WEB, MOBIL, SERVICE...
     */
    String HEADER_SOURCE = "X-SOURCE";

    /**
     * Application language
     */
    String HEADER_LANGUAGE = "X-LANGUAGE";

    /**
     * Forwarded header
     */
    String HEADER_FORWARDED = "FORWARDED";
}
