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
package hu.icellmobilsoft.dookug.common.cdi.document;

import java.io.ByteArrayOutputStream;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.coffee.tool.utils.marshalling.MarshallingUtil;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.ParametersDataType;

/**
 * Interface for document storage
 *
 * @author szabolcs.gemesi
 * @since 0.0.1
 */
public interface IDocumentStore {

    /**
     * Returns the document by the given id and storage identifier
     * 
     * @param documentId
     *            document identifier
     * @return {@link Document}
     * @throws BaseException
     *             on error
     */
    Document getDocumentById(String documentId) throws BaseException;

    /**
     * Save and returns the document object
     *
     * @param content
     *            document content
     * @param format
     *            document format
     * @param parameters
     *            document parameters
     * @return {@link Document}
     * @throws BaseException
     *             on error
     */
    Document saveDocumentWithParameters(byte[] content, String format, byte[] parameters) throws BaseException;

    /**
     * Save and returns the document object
     *
     * @param content
     *            document content
     * @param format
     *            document format
     * @param parameterData
     *            document parameter data
     * @return {@link Document}
     * @throws BaseException
     *             on error
     */
    Document saveDocumentWithParameterData(byte[] content, String format, byte[] parameterData) throws BaseException;

    /**
     * Save and returns the document object
     *
     * @param content
     *            document content
     * @param format
     *            document format
     * @param parameterData
     *            document parameter data
     * @return {@link Document}
     * @throws BaseException
     *             on error
     */
    default Document saveDocumentWithParameterData(byte[] content, String format, ParametersDataType parameterData) throws BaseException {
        if (parameterData == null) {
            return saveDocumentWithParameterData(content, format, (byte[]) null);
        }
        JAXBElement<ParametersDataType> element = new JAXBElement<>(
                new QName("http://schemas.dookug.icellmobilsoft.hu/DOCUMENT/1.0/rest/generator", "ParametersDataType"),
                ParametersDataType.class,
                parameterData);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        MarshallingUtil.marshall(element, baos, ParametersDataType.class);
        return saveDocumentWithParameterData(content, format, baos.size() == 0 ? null : baos.toByteArray());
    }

}
