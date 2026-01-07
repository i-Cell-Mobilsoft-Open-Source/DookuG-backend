/*-
 * #%L
 * DookuG
 * %%
 * Copyright (C) 2023 - 2025 i-Cell Mobilsoft Zrt.
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
package hu.icellmobilsoft.dookug.document.service.action.test;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.coffee.dto.common.common.OrderByTypeType;
import hu.icellmobilsoft.coffee.rest.validation.xml.JaxbTool;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.tool.utils.enums.EnumUtil;
import hu.icellmobilsoft.dookug.common.dto.constant.XsdConstants;
import hu.icellmobilsoft.dookug.common.model.template.Template;
import hu.icellmobilsoft.dookug.common.system.rest.action.BaseAction;
import hu.icellmobilsoft.dookug.document.service.service.test.TemplateQueryService;
import hu.icellmobilsoft.dookug.schemas.common._1_0.common.GeneratorEngineType;
import hu.icellmobilsoft.dookug.schemas.common._1_0.common.TemplateEngineType;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplateQueryOrderByType;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplateQueryOrderType;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplateQueryParamsType;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplateQueryResponse;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplateType;

/**
 * Stored template action
 *
 * @author mate.biro
 * @since 2.2.0
 */
@Model
public class StoredTemplateAction extends BaseAction {

    @Inject
    private TemplateQueryService templateQueryService;

    @Inject
    private JaxbTool jaxbTool;

    /**
     * Template query, can be filtered and paginated
     * 
     * @param name
     *            template name filter
     * @param language
     *            template language filter
     * @param validityStart
     *            validity start filter
     * @param validityEnd
     *            validity end filter
     * @param sort
     *            sorting criteria
     * 
     * @return {@link TemplateQueryResponse}
     * @throws BaseException
     *             on error
     */
    public TemplateQueryResponse getTemplateMetaDataQuery(String name, String language, String validityStart, String validityEnd, String sort)
            throws BaseException {

        // TODO converter
        TemplateQueryParamsType queryParams = new TemplateQueryParamsType();
        if (StringUtils.isNotBlank(name)) {
            queryParams.setName(name);
        }
        if (StringUtils.isNotBlank(language)) {
            queryParams.setLanguage(language);
        }
        if (StringUtils.isNotBlank(validityStart)) {
            try {
                queryParams.setValidityStart(OffsetDateTime.parse(validityStart));
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Invalid 'validityStart', expected ISO-8601 OffsetDateTime.", e);
            }
        }
        if (StringUtils.isNotBlank(validityEnd)) {
            try {
                queryParams.setValidityEnd(OffsetDateTime.parse(validityEnd));
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Invalid 'validityEnd', expected ISO-8601 OffsetDateTime.", e);
            }
        }

        // validate by XSD
        jaxbTool.marshalXML(queryParams, XsdConstants.SUPER_XSD_PATH);

        List<TemplateQueryOrderType> queryOrders = parseSort(sort);

        List<Template> templates = templateQueryService.findByQueryParams(queryParams, queryOrders);

        return toTemplateQueryResponse(templates);
    }

    private List<TemplateQueryOrderType> parseSort(String sort) {
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

            TemplateQueryOrderByType orderBy = SORT_FIELD_MAP.get(field);
            TemplateQueryOrderType orderType = getTemplateQueryOrderType(orderBy, field, direction);
            orders.add(orderType);
        }
        return orders;
    }

    private TemplateQueryOrderType getTemplateQueryOrderType(TemplateQueryOrderByType orderBy, String field, String direction) {
        if (orderBy == null) {
            throw new IllegalArgumentException("Unsupported sort field: " + field);
        }

        OrderByTypeType type = switch (direction.toUpperCase()) {
            case "ASC" -> OrderByTypeType.ASC;
            case "DESC" -> OrderByTypeType.DESC;
            default -> throw new IllegalArgumentException("Invalid sort direction for '" + field + "': " + direction);
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

    private TemplateQueryResponse toTemplateQueryResponse(List<Template> templates) {
        TemplateQueryResponse response = new TemplateQueryResponse();
        handleSuccessResultType(response);

        for (Template template : templates) {
            // TODO converter
            TemplateType templateType = new TemplateType();
            templateType.setTemplateId(template.getId());
            templateType.setLanguage(template.getLanguage());
            templateType.setName(template.getName());
            templateType.setDescription(template.getDescription());
            templateType.setTemplateEngine(EnumUtil.convert(template.getTemplateEngine(), TemplateEngineType.class));
            templateType.setGeneratorEngine(EnumUtil.convert(template.getGeneratorEngine(), GeneratorEngineType.class));
            templateType.setValidityStart(template.getValidityStart());
            templateType.setValidityEnd(template.getValidityEnd());
            templateType.setLastUpdatedAt(template.getModificationDate() != null ? template.getModificationDate() : template.getCreationDate());
            response.withRowList(templateType);
        }

        return response;
    }

}
