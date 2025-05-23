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
package hu.icellmobilsoft.dookug.ts.common.rest.mprestclient;

import jakarta.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import hu.icellmobilsoft.dookug.api.rest.document.IDocumentSignInternalRest;
import hu.icellmobilsoft.dookug.api.url.DocumentGeneratePath;

/**
 * MP REST Client interface extension on REST interface
 * 
 * @author tamas.cserhati
 * @since 1.1.0
 */
@RegisterRestClient
@Path(DocumentGeneratePath.INTERNAL_DOCUMENT_SIGN_INLINE)
public interface IDocumentSigningInternalRestClient extends IDocumentSignInternalRest {

}
