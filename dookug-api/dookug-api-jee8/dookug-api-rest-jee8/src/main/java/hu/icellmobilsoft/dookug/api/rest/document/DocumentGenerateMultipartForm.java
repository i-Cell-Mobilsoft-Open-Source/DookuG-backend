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
package hu.icellmobilsoft.dookug.api.rest.document;

import java.io.InputStream;

import javax.enterprise.inject.Model;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DocumentGenerateRequest;

/**
 * DocumentGenerate REST Multipart form
 * 
 * @author laszlo.padar
 * @since 0.1.0
 */
@Model
public class DocumentGenerateMultipartForm {

    @FormParam("TEMPLATE")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    private InputStream template;

    @FormParam("REQUEST")
    @PartType(MediaType.APPLICATION_XML)
    private DocumentGenerateRequest request;

    /**
     * Multipart template part getter
     * 
     * @return template part
     */
    public InputStream getTemplate() {
        return template;
    }

    /**
     * Multipart template part setter
     * 
     * @param template
     *            template part
     */
    public void setTemplate(InputStream template) {
        this.template = template;
    }

    /**
     * Multipart request part getter
     * 
     * @return request part
     */
    public DocumentGenerateRequest getRequest() {
        return request;
    }

    /**
     * Multipart request part setter
     * 
     * @param request
     *            request part
     */
    public void setRequest(DocumentGenerateRequest request) {
        this.request = request;
    }
}
