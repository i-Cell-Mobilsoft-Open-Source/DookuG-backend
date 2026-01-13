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
package hu.icellmobilsoft.dookug.document.service.converter.test;

import java.text.MessageFormat;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.coffee.dto.common.common.OrderByTypeType;
import hu.icellmobilsoft.coffee.rest.validation.xml.JaxbTool;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.dookug.common.dto.constant.XsdConstants;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplateQueryOrderByType;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplateQueryOrderType;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplateQueryParams;

/**
 * Helper for template query operations
 *
 * @author mate.biro
 * @since 2.2.0
 */
@ApplicationScoped
public class TemplateQueryHelper {

    @Inject
    private JaxbTool jaxbTool;

    @Inject
    private TemplateQueryParamsConverter templateQueryParamsConverter;

    /**
     * Converts string parameters to {@link TemplateQueryParams} and validates by XSD
     *
     * @param name
     *            template name
     * @param language
     *            template language
     * @param validityStart
     *            validity start in ISO-8601 OffsetDateTime format
     * @param validityEnd
     *            validity end in ISO-8601 OffsetDateTime format
     * @return converted and validated {@link TemplateQueryParams}
     * @throws BaseException
     *             if validation fails
     */
    public TemplateQueryParams getTemplateQueryParams(String name, String language, String validityStart, String validityEnd) throws BaseException {

        TemplateQueryParams queryParams = templateQueryParamsConverter.convert(name, language, validityStart, validityEnd);

        validateOffsetDateTimeInterval(queryParams);

        // validate by XSD
        jaxbTool.marshalXML(queryParams, XsdConstants.SUPER_XSD_PATH);

        return queryParams;
    }

    /**
     * Parses sort string to list of {@link TemplateQueryOrderType}
     *
     * @param sort
     *            sort string, format: "field1:direction1,field2:direction2", direction is optional (default: ASC)
     * @return list of {@link TemplateQueryOrderType}
     * @throws IllegalArgumentException
     *             if sort field or direction is invalid
     */
    public List<TemplateQueryOrderType> getTemplateQueryOrders(String sort) {

        List<TemplateQueryOrderType> orders = new ArrayList<>();
        if (StringUtils.isBlank(sort)) {
            return orders;
        }

        for (String token : sort.split(",")) {
            String part = token.trim();
            if (part.isEmpty()) {
                continue;
            }

            String field;
            String direction;
            int colonIdx = part.indexOf(':');
            if (colonIdx < 0) {
                field = part;
                direction = "ASC";
            } else {
                field = part.substring(0, colonIdx).trim();
                direction = part.substring(colonIdx + 1).trim();
                if (direction.isEmpty()) {
                    direction = "ASC";
                }
            }

            TemplateQueryOrderType orderType = getTemplateQueryOrderType(field, direction);
            orders.add(orderType);
        }
        return orders;
    }

    private TemplateQueryOrderType getTemplateQueryOrderType(String field, String direction) {

        TemplateQueryOrderByType orderBy = SORT_FIELD_MAP.get(field);

        if (orderBy == null) {
            throw new IllegalArgumentException(MessageFormat.format("Unsupported sort field: [{0}]", field));
        }

        OrderByTypeType type = switch (direction.toUpperCase()) {
            case "ASC" -> OrderByTypeType.ASC;
            case "DESC" -> OrderByTypeType.DESC;
            default -> throw new IllegalArgumentException(MessageFormat.format("Invalid sort direction for [{0}]:[{1}]", field, direction));
        };

        TemplateQueryOrderType orderType = new TemplateQueryOrderType();
        orderType.setOrder(orderBy);
        orderType.setType(type);
        return orderType;
    }

    private final Map<String, TemplateQueryOrderByType> SORT_FIELD_MAP = Map.of(
            "name",
            TemplateQueryOrderByType.NAME,
            "lastUpdatedAt",
            TemplateQueryOrderByType.LAST_UPDATED_AT,
            "description",
            TemplateQueryOrderByType.DESCRIPTION,
            "language",
            TemplateQueryOrderByType.LANGUAGE,
            "validityStart",
            TemplateQueryOrderByType.VALIDITY_START,
            "validityEnd",
            TemplateQueryOrderByType.VALIDITY_END
    );

    private void validateOffsetDateTimeInterval(TemplateQueryParams queryParams) {

        OffsetDateTime start = queryParams.getValidityStart();
        OffsetDateTime end = queryParams.getValidityEnd();

        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Validity start must not be after end.");
        }
    }
}
