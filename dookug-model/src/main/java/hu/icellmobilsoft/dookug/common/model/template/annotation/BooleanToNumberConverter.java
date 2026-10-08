/*-
 * #%L
 * DookuG
 * %%
 * Copyright (C) 2023 - 2026 i-Cell Mobilsoft Zrt.
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
package hu.icellmobilsoft.dookug.common.model.template.annotation;

import java.text.MessageFormat;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter for mapping boolean values to numbers in the database
 *
 * @author levente.prehoda
 * @since 2.2.0
 */
@Converter
public class BooleanToNumberConverter implements AttributeConverter<Boolean, Integer> {

    private static final Integer ONE = 1;
    private static final Integer ZERO = 0;

    @Override
    public Integer convertToDatabaseColumn(Boolean value) {
        if (value == null) {
            return null;
        }
        return value ? ONE : ZERO;
    }

    @Override
    public Boolean convertToEntityAttribute(Integer value) {
        if (value == null) {
            return null;
        } else if (ONE.equals(value)) {
            return true;
        } else if (ZERO.equals(value)) {
            return false;
        } else {
            String msg = MessageFormat.format("Invalid boolean candidate: [{0}]. Accepted values are: [{1}], [{2}]", value, ZERO, ONE);
            throw new IllegalArgumentException(msg);
        }
    }

}
