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
package hu.icellmobilsoft.dookug.api.rest.document.form;

import java.io.InputStream;

import javax.enterprise.inject.Model;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentsign.DocumentSignRequest;

/**
 * DocumentSign REST Multipart form
 * 
 * @author tamas.cserhati
 * @since 1.1.0
 */
@Model
public class DocumentSignMultipartForm {

    @FormParam("DOCUMENT")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    private InputStream document;

    @FormParam("REQUEST")
    @PartType(MediaType.APPLICATION_XML)
    private DocumentSignRequest request;

    /**
     * @return the request
     */
    public DocumentSignRequest getRequest() {
        return request;
    }

    /**
     * @param request
     *            the request to set
     */
    public void setRequest(DocumentSignRequest request) {
        this.request = request;
    }

    /**
     * @return the document
     */
    public InputStream getDocument() {
        return document;
    }

    /**
     * @param document
     *            the document to set
     */
    public void setDocument(InputStream document) {
        this.document = document;
    }

}
