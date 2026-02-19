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
package hu.icellmobilsoft.dookug.document.service.rest.test;

import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.dookug.api.rest.test.IDocumentGenerateInlineTest;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import hu.icellmobilsoft.dookug.common.system.rest.rest.BaseRestService;
import hu.icellmobilsoft.dookug.document.service.action.test.DocumentGenerateInlineTestMultipartAction;

/**
 * Document generate inline service rest implementation
 * 
 * @author mate.biro
 * @since 2.2.0
 */
@Model
public class DocumentGenerateInlineTest extends BaseRestService implements IDocumentGenerateInlineTest {

    @Inject
    private DocumentGenerateInlineTestMultipartAction documentGenerateInlineTestMultipartAction;

    @Override
    public Response postDocumentGenerateMultipart(MultipartFormDataInput input, Boolean responseContentGzipped) throws BaseException {
        return wrapPathParam2(
                documentGenerateInlineTestMultipartAction::postDocumentGenerateMultipart,
                input,
                responseContentGzipped,
                "postDocumentGenerateMultipart",
                "input",
                "responseContentGzipped");
    }

}
