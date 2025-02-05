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
package hu.icellmobilsoft.dookug.client.rest.jsonb;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.config.BinaryDataStrategy;
import javax.json.bind.config.PropertyVisibilityStrategy;
import javax.ws.rs.ext.ContextResolver;

/**
 * Custom default JSON-B config for MP-rest client
 *
 * @author mate.biro
 * @since 0.2.0
 */
//@Dependent
public class CustomJsonbContextResolver implements ContextResolver<Jsonb> {

    private static Jsonb jsonb;

    @Override
    public Jsonb getContext(Class<?> type) {
        JsonbConfig config = new JsonbConfig().withPropertyVisibilityStrategy(new PropertyVisibilityStrategy() {
            @Override
            public boolean isVisible(Field f) {
                return true;
            }

            @Override
            public boolean isVisible(Method m) {
                return false;
            }
        }).withBinaryDataStrategy(BinaryDataStrategy.BASE_64);

        return getJsonbContext(config);
    }

    /**
     * Get jsonb context with lazy initialization
     *
     * @return {@link Jsonb}
     */
    private Jsonb getJsonbContext(JsonbConfig config) {
        if (jsonb == null) {
            jsonb = JsonbBuilder.newBuilder().withConfig(config).build();
        }
        return jsonb;
    }
}
