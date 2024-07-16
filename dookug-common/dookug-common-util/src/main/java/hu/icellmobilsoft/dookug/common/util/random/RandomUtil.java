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
package hu.icellmobilsoft.dookug.common.util.random;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.coffee.se.logging.Logger;

/**
 * Util class for generating random Strings
 *
 * @author tamas.cserhati
 * @since 1.1.0
 */
public class RandomUtil extends hu.icellmobilsoft.coffee.tool.utils.string.RandomUtil {

    private static Logger LOGGER = Logger.getLogger(RandomUtil.class);

    /**
     * Generating random {@link String} token
     * 
     * @return generated token
     */
    public static String generateToken() {
        String token = StringUtils.left(UUID.randomUUID() + generateId(), 48);
        LOGGER.trace("Generated token: [{0}]", token);
        return token;
    }

}
