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
package hu.icellmobilsoft.dookug.ts.document.rest.test;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import hu.icellmobilsoft.coffee.model.base.generator.EntityIdGenerator;
import hu.icellmobilsoft.dookug.common.model.template.Template;
import hu.icellmobilsoft.dookug.common.model.template.TemplatePart;
import hu.icellmobilsoft.dookug.common.model.template.TemplatePartContent;
import hu.icellmobilsoft.dookug.common.model.template.TemplateTemplatePart;
import hu.icellmobilsoft.dookug.common.model.template.enums.GeneratorEngine;
import hu.icellmobilsoft.dookug.common.model.template.enums.TemplateEngine;
import hu.icellmobilsoft.dookug.common.model.template.enums.TemplateType;

/**
 * Helper class to create and delete template hierarchies in the database for testing purposes.
 *
 * @author mate.biro
 * @since 2.2.0
 */
@ApplicationScoped
public class TemplateDBHelper {

    @Inject
    private EntityManager em;

    /**
     * Simple DTO holding the created template hierarchy for easier cleanup in tests.
     */
    public static class TemplateHierarchy {
        private List<Template> templates = new ArrayList<>();
        private List<TemplatePart> templateParts = new ArrayList<>();
        private List<TemplatePartContent> templatePartContents = new ArrayList<>();
        private List<TemplateTemplatePart> templateTemplateParts = new ArrayList<>();

        public List<Template> getTemplates() {
            return templates;
        }

        public List<TemplatePart> getTemplateParts() {
            return templateParts;
        }

        public List<TemplatePartContent> getTemplatePartContents() {
            return templatePartContents;
        }

        public List<TemplateTemplatePart> getTemplateTemplateParts() {
            return templateTemplateParts;
        }
    }

    /**
     * Convenience wrapper for creating a hierarchy for a single template.
     */
    public TemplateHierarchy createTemplateHierarchy(String templateName, TemplateEngine templateEngine,
            GeneratorEngine generatorEngine, List<String> languages, List<String> partKeys, byte[] content, OffsetDateTime validityStart,
            OffsetDateTime validityEnd) {
        return createTemplateHierarchies(
                templateName,
                templateEngine,
                generatorEngine,
                languages,
                partKeys,
                content,
                validityStart,
                validityEnd);
    }

    /**
     * Creates one or more templates that all share the same {@link TemplatePart}(s), {@link TemplatePartContent}(s) and corresponding
     * {@link TemplateTemplatePart} link entries.
     *
     * @param templateName
     *            {@link Template#getName()} values
     * @param templateEngine
     *            {@link Template#getTemplateEngine()}}
     * @param generatorEngine
     *            {@link Template#getGeneratorEngine()}}
     * @param languages
     *            list of{@link Template#getLanguage()}
     * @param partKeys
     *            list of {@link TemplatePart#getKey()} values to be created once and shared
     * @param content
     *            byte[] content to be set for all created {@link TemplatePartContent}(s)
     * @return created template hierarchy wrapped in a {@link TemplateHierarchy} instance
     */
    public TemplateHierarchy createTemplateHierarchies(String templateName, TemplateEngine templateEngine,
            GeneratorEngine generatorEngine, List<String> languages, List<String> partKeys, byte[] content, OffsetDateTime validityStart,
            OffsetDateTime validityEnd) {

        TemplateHierarchy hierarchy = new TemplateHierarchy();

        em.getTransaction().begin();

        // Create shared parts + contents first
        for (String partKey : partKeys) {
            TemplatePart templatePart = new TemplatePart();
            templatePart.setDescription("test template part description");
            templatePart.setKey(partKey);
            templatePart.setTemplatePartType(TemplateType.MAIN);

            em.persist(templatePart);
            hierarchy.getTemplateParts().add(templatePart);

            // compressed parameter is numeric in DB, but boolean in entity, so we use native query for insertion
            String templatePartContentId = EntityIdGenerator.generateId();
            em.createNativeQuery(
                    "INSERT INTO TEMPLATE_PART_CONTENT " +
                            "(X__ID, TEMPLATE_PART_ID, CONTENT, COMPRESSED, X__VERSION) " +
                            "VALUES (:id, :templatePartId, :content, :compressed, 0)")
                    .setParameter("id", templatePartContentId)
                    .setParameter("templatePartId", templatePart.getId())
                    .setParameter("content", content)
                    .setParameter("compressed", 0)
                    .executeUpdate();

            TemplatePartContent templatePartContent = em.find(TemplatePartContent.class, templatePartContentId);
            hierarchy.getTemplatePartContents().add(templatePartContent);
        }

        // Create templates and link to all shared parts
        for (String language : languages) {
            Template template = new Template();
            template.setDescription("test template description");
            template.setTemplateEngine(templateEngine);
            template.setGeneratorEngine(generatorEngine);
            template.setName(templateName);
            template.setLanguage(language);
            template.setValidityStart(validityStart);
            template.setValidityEnd(validityEnd);

            em.persist(template);
            hierarchy.getTemplates().add(template);

            for (TemplatePart part : hierarchy.getTemplateParts()) {
                TemplateTemplatePart ttp = new TemplateTemplatePart();
                ttp.setTemplateId(template.getId());
                ttp.setTemplatePartId(part.getId());

                em.persist(ttp);
                hierarchy.getTemplateTemplateParts().add(ttp);
            }
        }

        em.getTransaction().commit();

        return hierarchy;
    }

    /**
     * Deletes the entire template hierarchy created by
     * {@link #createTemplateHierarchy(String, TemplateEngine, GeneratorEngine, List, List, byte[], OffsetDateTime, OffsetDateTime)} or
     * {@link #createTemplateHierarchies(String, TemplateEngine, GeneratorEngine, List, List, byte[], OffsetDateTime, OffsetDateTime)}.
     *
     * @param hierarchy
     *            template hierarchy to delete
     */
    public void deleteTemplateHierarchy(TemplateHierarchy hierarchy) {
        if (hierarchy == null) {
            return;
        }

        em.getTransaction().begin();

        for (TemplatePartContent content : hierarchy.getTemplatePartContents()) {
            TemplatePartContent managedTemplatePartContent = em.contains(content) ? content : em.find(TemplatePartContent.class, content.getId());
            if (managedTemplatePartContent != null) {
                em.remove(managedTemplatePartContent);
            }
        }

        for (TemplateTemplatePart ttp : hierarchy.getTemplateTemplateParts()) {
            TemplateTemplatePart managedTemplateTemplatePart = em.contains(ttp) ? ttp : em.find(TemplateTemplatePart.class, ttp.getId());
            if (managedTemplateTemplatePart != null) {
                em.remove(managedTemplateTemplatePart);
            }
        }

        for (TemplatePart part : hierarchy.getTemplateParts()) {
            TemplatePart managedTemplatePart = em.contains(part) ? part : em.find(TemplatePart.class, part.getId());
            if (managedTemplatePart != null) {
                em.remove(managedTemplatePart);
            }
        }

        for (Template template : hierarchy.getTemplates()) {
            if (template != null) {
                Template managedTemplate = em.contains(template) ? template : em.find(Template.class, template.getId());
                if (managedTemplate != null) {
                    em.remove(managedTemplate);
                }
            }
        }

        em.getTransaction().commit();
    }
}
