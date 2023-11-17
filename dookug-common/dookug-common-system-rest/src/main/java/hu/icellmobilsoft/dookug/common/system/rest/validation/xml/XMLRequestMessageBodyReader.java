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
package hu.icellmobilsoft.dookug.common.system.rest.validation.xml;

import javax.annotation.Priority;
import javax.ws.rs.Consumes;
import javax.ws.rs.Priorities;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseRequestType;
import hu.icellmobilsoft.coffee.rest.validation.xml.XmlMessageBodyReaderBase;

/**
 * MessageBodyReader for xml requests
 *
 * @author Imre Scheffer
 * @since 0.1.0
 */
@Provider
@Consumes({ MediaType.APPLICATION_XML, MediaType.TEXT_XML })
@Priority(Priorities.ENTITY_CODER)
public class XMLRequestMessageBodyReader extends XmlMessageBodyReaderBase<BaseRequestType> {

}
