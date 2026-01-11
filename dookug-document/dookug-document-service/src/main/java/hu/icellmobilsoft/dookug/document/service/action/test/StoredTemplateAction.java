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

import java.text.MessageFormat;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.coffee.dto.common.common.OrderByTypeType;
import hu.icellmobilsoft.coffee.rest.validation.xml.JaxbTool;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.tool.utils.enums.EnumUtil;
import hu.icellmobilsoft.dookug.common.dto.constant.XsdConstants;
import hu.icellmobilsoft.dookug.common.model.template.Template;
import hu.icellmobilsoft.dookug.common.model.template.enums.GeneratorEngine;
import hu.icellmobilsoft.dookug.common.system.rest.action.BaseAction;
import hu.icellmobilsoft.dookug.document.service.converter.test.TemplateQueryParamsConverter;
import hu.icellmobilsoft.dookug.document.service.service.test.TemplateQueryService;
import hu.icellmobilsoft.dookug.schemas.common._1_0.common.GeneratorEngineType;
import hu.icellmobilsoft.dookug.schemas.common._1_0.common.TemplateEngineType;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplateQueryOrderByType;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplateQueryOrderType;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplateQueryParams;
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

    @Inject
    private TemplateQueryParamsConverter templateQueryParamsConverter;

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

        TemplateQueryParams queryParams = getTemplateQueryParams(name, language, validityStart, validityEnd);
        List<TemplateQueryOrderType> queryOrders = getTemplateQueryOrders(sort);

        List<Template> templates = templateQueryService.findByQueryParams(queryParams, queryOrders);

        return toTemplateQueryResponse(templates);
    }

    private TemplateQueryParams getTemplateQueryParams(String name, String language, String validityStart, String validityEnd) throws BaseException {

        TemplateQueryParams queryParams = templateQueryParamsConverter.convert(name, language, validityStart, validityEnd);

        // validate by XSD
        jaxbTool.marshalXML(queryParams, XsdConstants.SUPER_XSD_PATH);

        return queryParams;
    }

    private List<TemplateQueryOrderType> getTemplateQueryOrders(String sort) {

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

    private TemplateQueryResponse toTemplateQueryResponse(List<Template> templates) {
        TemplateQueryResponse response = new TemplateQueryResponse();
        handleSuccessResultType(response);

        List<TemplateType> rowList = buildRowList(templates);
        response.withRowList(rowList);

        return response;
    }

    /**
     * Builds the row list for the response, grouping SAXON templates by name and validity period.
     */
    private List<TemplateType> buildRowList(List<Template> templates) {
        List<TemplateType> result = new ArrayList<>();
        // Track SAXON groups in insertion order (first encounter decides output position)
        Map<TemplateGroupKey, GroupAcc> saxonGroups = new LinkedHashMap<>();

        for (Template template : templates) {
            if (template.getGeneratorEngine() == GeneratorEngine.SAXON) {
                TemplateGroupKey key = new TemplateGroupKey(template.getName(), template.getValidityStart(), template.getValidityEnd());
                GroupAcc acc = saxonGroups.computeIfAbsent(key, k -> {
                    TemplateType tt = new TemplateType();
                    tt.setName(template.getName());
                    tt.setDescription(template.getDescription());
                    tt.setTemplateEngine(EnumUtil.convert(template.getTemplateEngine(), TemplateEngineType.class));
                    tt.setGeneratorEngine(EnumUtil.convert(template.getGeneratorEngine(), GeneratorEngineType.class));
                    tt.setValidityStart(template.getValidityStart());
                    tt.setValidityEnd(template.getValidityEnd());
                    tt.setLastUpdatedAt(template.getModificationDate() != null ? template.getModificationDate() : template.getCreationDate());
                    // append to result at first encounter to keep order
                    result.add(tt);
                    return new GroupAcc(tt);
                });
                // accumulate ids and languages for the group
                acc.templateType.withTemplateIds(template.getId());
                if (template.getLanguage() != null && acc.languages.add(template.getLanguage())) {
                    acc.templateType.withLanguages(template.getLanguage());
                }
                // update lastUpdatedAt if a newer modification/creation date is seen
                OffsetDateTime updatedAt = template.getModificationDate() != null ? template.getModificationDate() : template.getCreationDate();
                OffsetDateTime current = acc.templateType.getLastUpdatedAt();
                if (current == null || (updatedAt != null && updatedAt.isAfter(current))) {
                    acc.templateType.setLastUpdatedAt(updatedAt);
                }
            } else {
                // non-SAXON: add directly in encounter order
                TemplateType templateType = new TemplateType();
                templateType.withTemplateIds(template.getId());
                if (template.getLanguage() != null) {
                    templateType.withLanguages(template.getLanguage());
                }
                templateType.setName(template.getName());
                templateType.setDescription(template.getDescription());
                templateType.setTemplateEngine(EnumUtil.convert(template.getTemplateEngine(), TemplateEngineType.class));
                templateType.setGeneratorEngine(EnumUtil.convert(template.getGeneratorEngine(), GeneratorEngineType.class));
                templateType.setValidityStart(template.getValidityStart());
                templateType.setValidityEnd(template.getValidityEnd());
                templateType.setLastUpdatedAt(template.getModificationDate() != null ? template.getModificationDate() : template.getCreationDate());
                result.add(templateType);
            }
        }
        return result;
    }

    // helper accumulator for a group
    private static final class GroupAcc {
        final TemplateType templateType;
        final LinkedHashSet<String> languages = new LinkedHashSet<>();
        GroupAcc(TemplateType templateType) {
            this.templateType = templateType;
        }
    }

    // used only for grouping SAXON templates
    private record TemplateGroupKey(String name, OffsetDateTime validityStart, OffsetDateTime validityEnd) {

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof TemplateGroupKey that)) {
                return false;
            }
            return Objects.equals(name, that.name)
                    && Objects.equals(validityStart, that.validityStart)
                    && Objects.equals(validityEnd, that.validityEnd);
        }

    }

}
