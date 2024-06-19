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
 * @since 1.0.0
 */
public class OpenAPIFilter implements OASFilter {

    /**
     * mp-openapi takes objects with a pattern (not String, e.g., date types) as object type, so it does not generate the pattern into the definition.
     * 
     * @param schema
     *            the {@link org.eclipse.microprofile.openapi.annotations.media.Schema}
     * @return the {@link org.eclipse.microprofile.openapi.annotations.media.Schema}
     */
    @Override
    public Schema filterSchema(Schema schema) {
        if (schema != null && StringUtils.isNotBlank(schema.getPattern())) {
            // If there is a pattern, it must be a string at request/response level (in dto it can be an object e.g. XmlGregorianCalendar)
            schema.setType(Schema.SchemaType.STRING);
        }
        return schema;
    }
}
