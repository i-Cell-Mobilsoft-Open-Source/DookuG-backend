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
package hu.icellmobilsoft.dookug.ts.common.builder;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.inject.Model;

import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentsign.DocumentSignRequest;
import hu.icellmobilsoft.dookug.ts.common.rest.DtoHelper;
import hu.icellmobilsoft.roaster.api.dto.BaseBuilder;

/**
 * Builder for {@link DocumentSignRequest} class
 * 
 * @author imre.scheffer
 * @since 0.1.0
 */
@Model
public class DocumentSignRequestBuilder extends BaseBuilder<DocumentSignRequest> {

    @Override
    public DocumentSignRequest createEmpty() {
        return new DocumentSignRequest();
    }

    /**
     * default init
     */
    @PostConstruct
    public void init() {
        setDto(createEmpty());
    }

    /**
     * Construct key-value filled request
     * 
     * @return Key-value filled request
     * @throws BaseException
     *             on error
     */
    public DocumentSignRequest parametersKeyValue() throws BaseException {
        DocumentSignRequest request = getDto();
        request.setContext(DtoHelper.createContext());
        request.setDigitalSignatureProfile("sample");
        return request;
    }

}
