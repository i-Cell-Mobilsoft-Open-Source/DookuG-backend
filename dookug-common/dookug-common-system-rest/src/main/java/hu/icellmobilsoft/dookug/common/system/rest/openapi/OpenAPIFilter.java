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
package hu.icellmobilsoft.dookug.common.system.rest.openapi;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.openapi.OASFilter;
import org.eclipse.microprofile.openapi.models.media.Schema;

/**
 * Openapi filter.<br>
 *
 * @author mark.petrenyi
 * @since 1.5.0
 */
public class OpenAPIFilter implements OASFilter {

    /**
     * Az mp-openapi a pattern-nel rendelkező, de nem String típusu objektumokat (pl.dátum típusok) object type-nak veszi, így nem generálja le a
     * pattern-t a definícióba.
     * 
     * @param schema
     *            the {@link org.eclipse.microprofile.openapi.annotations.media.Schema}
     * @return the {@link org.eclipse.microprofile.openapi.annotations.media.Schema}
     */
    @Override
    public Schema filterSchema(Schema schema) {
        if (schema != null && StringUtils.isNotBlank(schema.getPattern())) {
            // Ha van pattern, akkor request/response szinten string kell legyen (a dto-ban lehet object pl. XmlGregorianCalendar)
            schema.setType(Schema.SchemaType.STRING);
        }
        return schema;
    }
}
