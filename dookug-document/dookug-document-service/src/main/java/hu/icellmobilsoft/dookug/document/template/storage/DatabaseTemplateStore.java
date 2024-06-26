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
package hu.icellmobilsoft.dookug.document.template.storage;

import java.text.MessageFormat;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.coffee.dto.exception.BONotFoundException;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.dto.exception.BusinessException;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.coffee.tool.utils.compress.GZIPUtil;
import hu.icellmobilsoft.dookug.common.cdi.StorageMethodQualifier;
import hu.icellmobilsoft.dookug.common.cdi.constants.QualifierConstants;
import hu.icellmobilsoft.dookug.common.cdi.template.ITemplateStore;
import hu.icellmobilsoft.dookug.common.cdi.template.Template;
import hu.icellmobilsoft.dookug.common.cdi.template.TemplateContainer;
import hu.icellmobilsoft.dookug.common.cdi.template.TemplateDataContainer;
import hu.icellmobilsoft.dookug.common.model.template.TemplatePart;
import hu.icellmobilsoft.dookug.common.model.template.TemplatePartContent;
import hu.icellmobilsoft.dookug.common.model.template.TemplateTemplatePart;
import hu.icellmobilsoft.dookug.common.model.template.enums.TemplateType;
import hu.icellmobilsoft.dookug.document.service.cache.TemplateCache;
import hu.icellmobilsoft.dookug.document.service.cache.dto.TemplateCacheItem;
import hu.icellmobilsoft.dookug.document.service.cache.dto.TemplatePartCacheItem;
import hu.icellmobilsoft.dookug.document.service.service.TemplatePartContentService;
import hu.icellmobilsoft.dookug.document.service.service.TemplatePartService;
import hu.icellmobilsoft.dookug.document.service.service.TemplateService;
import hu.icellmobilsoft.dookug.document.service.service.TemplateTemplatePartService;

/**
 * {@link ITemplateStore} database implementation
 *
 * @author laszlo.padar
 * @since 0.1.0
 */
@StorageMethodQualifier(QualifierConstants.StorageMethodType.DATABASE)
@ApplicationScoped
public class DatabaseTemplateStore implements ITemplateStore {
    @Inject
    private TemplateService templateService;

    @Inject
    private TemplateTemplatePartService templateTemplatePartService;

    @Inject
    private TemplatePartService templatePartService;

    @Inject
    private TemplatePartContentService templatePartContentService;

    @Inject
    private TemplateContainer templateContainer;

    @Inject
    private TemplateDataContainer templateDataContainer;

    @Inject
    private TemplateCache templateCache;

    /**
     * Collects the whole template tree and precompile it by the name of root template, language and validity date.
     * 
     * @param templateName
     *            Name of root template
     * @param templateLanguage
     *            language of the root template
     * @param validityDate
     *            Validity of template
     */
    public void loadTemplatesByNameAndValidity(String templateName, String templateLanguage, OffsetDateTime validityDate) throws BaseException {
        if (ObjectUtils.anyNull(templateName, templateLanguage, validityDate)) {
            throw new BusinessException(CoffeeFaultType.INVALID_INPUT, "TemplateName, templateLanguage and validityDate are required!");
        }

        String templateCacheKey = null;
        TemplateCacheItem templateCacheItem = null;
        boolean cacheEnabled = templateCache.isCacheEnabled();
        if (cacheEnabled) {
            templateCacheKey = templateService.findTemplateIdByNameLanguageAndValidity(templateName, templateLanguage, validityDate);
            templateCacheItem = templateCache.getTemplateCacheItem(templateCacheKey);
        }

        if (templateCacheItem != null) {
            templateDataContainer.setTemplateId(templateCacheItem.getTemplateId());
            templateDataContainer.setTemplateName(templateCacheItem.getTemplateName());

            templateCacheItem.getTemplatePartCacheItems()
                    .forEach(
                            templatePartCacheItem -> templateContainer
                                    .addTemplate(templatePartCacheItem.getTemplate(), templatePartCacheItem.isInitialTemplate()));
        } else {
            hu.icellmobilsoft.dookug.common.model.template.Template template = templateService
                    .findByNameLanguageAndValidity(templateName, templateLanguage, validityDate);

            templateDataContainer.setTemplateId(template.getId());
            templateDataContainer.setTemplateName(template.getName());

            selectAndCacheTemplateContent(template, cacheEnabled);
        }
    }

    private void selectAndCacheTemplateContent(hu.icellmobilsoft.dookug.common.model.template.Template template, boolean cacheEnabled)
            throws BaseException {
        List<TemplateTemplatePart> templateTemplatePartList = templateTemplatePartService.findAllByTemplateId(template.getId());
        if (CollectionUtils.isEmpty(templateTemplatePartList)) {
            throw new BONotFoundException(
                    CoffeeFaultType.ENTITY_NOT_FOUND,
                    MessageFormat.format("Template with id=[{0}] has no template part.", template.getId()));
        }

        List<String> templatePartIds = templateTemplatePartList.stream().map(TemplateTemplatePart::getTemplatePartId).collect(Collectors.toList());
        List<TemplatePart> templatePartList = templatePartService.findAllByIdList(templatePartIds);
        if (CollectionUtils.isEmpty(templatePartList)) {
            throw new BONotFoundException(
                    CoffeeFaultType.ENTITY_NOT_FOUND,
                    MessageFormat.format("Template part not found for ids: [{0}]", templatePartIds));
        }

        List<String> existingTemplatePartIds = templatePartList.stream().map(TemplatePart::getId).collect(Collectors.toList());
        List<TemplatePartContent> templatePartContentList = templatePartContentService.findAllByTemplatePartList(existingTemplatePartIds);
        if (CollectionUtils.isEmpty(templatePartContentList)) {
            throw new BONotFoundException(
                    CoffeeFaultType.ENTITY_NOT_FOUND,
                    MessageFormat.format("Content not found for template parts: [{0}]", existingTemplatePartIds));
        }

        for (TemplatePart templatePart : templatePartList) {
            addTemplatePartToContainer(templatePart, templatePartContentList);
        }

        if (cacheEnabled) {
            TemplateCacheItem templateCacheItem = new TemplateCacheItem(template.getId(), template.getName());
            for (TemplatePart templatePart : templatePartList) {
                templateCacheItem.addNewPartItem(
                        new TemplatePartCacheItem(
                                templatePart.getTemplatePartType() == TemplateType.MAIN,
                                templateContainer.getTemplate(templatePart.getKey()).orElse(null)));
            }
            templateCache.newTemplateCacheItem(templateCacheItem);
        }
    }

    private void addTemplatePartToContainer(TemplatePart templatePart, List<TemplatePartContent> templatePartContentList) throws BaseException {
        TemplatePartContent templatePartContent = templatePartContentList.stream()
                .filter(c -> StringUtils.equals(c.getTemplatePartId(), templatePart.getId()))
                .findAny()
                .orElseThrow(
                        () -> new BONotFoundException(
                                CoffeeFaultType.ENTITY_NOT_FOUND,
                                MessageFormat.format("Content not found for template part: [{0}]", templatePart.getId())));

        templateContainer.addTemplate(
                new Template(templatePart.getKey(), getTemplateContent(templatePartContent)),
                templatePart.getTemplatePartType() == TemplateType.MAIN);
    }

    private byte[] getTemplateContent(TemplatePartContent templatePartContent) throws BaseException {
        if (templatePartContent.isCompressed()) {
            return GZIPUtil.decompress(templatePartContent.getContent());
        }
        return templatePartContent.getContent();
    }
}
