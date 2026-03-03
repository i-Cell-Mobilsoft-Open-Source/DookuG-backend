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
package hu.icellmobilsoft.dookug.ts.common.builder;

import java.time.OffsetDateTime;
import java.util.Collection;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.inject.Model;

import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.TemplateEngineType;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.CreateTemplateGroupRequest;
import hu.icellmobilsoft.dookug.schemas.template._2_2.test.template.TemplatePartType;
import hu.icellmobilsoft.dookug.ts.common.rest.DtoHelper;
import hu.icellmobilsoft.roaster.api.dto.BaseBuilder;

/**
 * Builder class for {@link CreateTemplateGroupRequest}
 *
 * @author levente.prehoda
 * @since 2.2.0
 */
@Model
public class CreateTemplateGroupRequestBuilder extends BaseBuilder<CreateTemplateGroupRequest> {

    @Override
    public CreateTemplateGroupRequest createEmpty() {
        CreateTemplateGroupRequest createTemplateGroupRequest = new CreateTemplateGroupRequest();
        createTemplateGroupRequest.setContext(DtoHelper.createContext());
        return createTemplateGroupRequest;
    }

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        setDto(createEmpty());
    }

    /**
     * With name create template group request builder.
     *
     * @param value
     *            the value
     * @return the create template group request builder
     */
    public CreateTemplateGroupRequestBuilder withName(String value) {
        getDto().setName(value);
        return this;
    }

    /**
     * With description create template group request builder.
     *
     * @param value
     *            the value
     * @return the create template group request builder
     */
    public CreateTemplateGroupRequestBuilder withDescription(String value) {
        getDto().setDescription(value);
        return this;
    }

    /**
     * With template engine create template group request builder.
     *
     * @param value
     *            the value
     * @return the create template group request builder
     */
    public CreateTemplateGroupRequestBuilder withTemplateEngine(TemplateEngineType value) {
        getDto().setTemplateEngine(value);
        return this;
    }

    /**
     * With language create template group request builder.
     *
     * @param values
     *            the values
     * @return the create template group request builder
     */
    public CreateTemplateGroupRequestBuilder withLanguage(String... values) {
        if (values != null) {
            for (String value : values) {
                getDto().getLanguage().add(value);
            }
        }
        return this;
    }

    /**
     * With language create template group request builder.
     *
     * @param values
     *            the values
     * @return the create template group request builder
     */
    public CreateTemplateGroupRequestBuilder withLanguage(Collection<String> values) {
        if (values != null) {
            getDto().getLanguage().addAll(values);
        }
        return this;
    }

    /**
     * With validity start create template group request builder.
     *
     * @param value
     *            the value
     * @return the create template group request builder
     */
    public CreateTemplateGroupRequestBuilder withValidityStart(OffsetDateTime value) {
        getDto().setValidityStart(value);
        return this;
    }

    /**
     * With validity end create template group request builder.
     *
     * @param value
     *            the value
     * @return the create template group request builder
     */
    public CreateTemplateGroupRequestBuilder withValidityEnd(OffsetDateTime value) {
        getDto().setValidityEnd(value);
        return this;
    }

    /**
     * With template part list create template group request builder.
     *
     * @param values
     *            the values
     * @return the create template group request builder
     */
    public CreateTemplateGroupRequestBuilder withTemplatePartList(Collection<TemplatePartType> values) {
        getDto().withTemplatePartList(values);
        return this;
    }

    /**
     * With template part list create template group request builder.
     *
     * @param values
     *            the values
     * @return the create template group request builder
     */
    public CreateTemplateGroupRequestBuilder withTemplatePartList(TemplatePartType... values) {
        getDto().withTemplatePartList(values);
        return this;
    }
}
