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
package hu.icellmobilsoft.dookug.document.service.action.test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;

import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseRequestType;
import hu.icellmobilsoft.coffee.dto.exception.InvalidParameterException;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.coffee.rest.validation.xml.JaxbTool;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.se.api.exception.BusinessException;
import hu.icellmobilsoft.coffee.se.api.exception.TechnicalException;
import hu.icellmobilsoft.dookug.api.dto.exception.enums.FaultType;
import hu.icellmobilsoft.coffee.tool.utils.json.JsonUtil;
import hu.icellmobilsoft.dookug.common.dto.constant.XsdConstants;

/**
 * Helper for reading multipart form data
 *
 * @author mate.biro
 * @since 2.2.0
 */
@ApplicationScoped
public class InputPartHelper {

    @Inject
    private JaxbTool jaxbTool;

    /**
     * Read bytes from multipart form data
     *
     * @param part
     *            InputPart
     * @return byte array of the file content
     * @throws BaseException
     *             if an error occurs while reading the input stream
     */
    protected byte[] readPartBytes(InputPart part) throws BaseException {
        try {
            InputStream is = part.getBody(InputStream.class, null);
            return is == null ? new byte[0] : is.readAllBytes();
        } catch (IOException e) {
            throw new TechnicalException(CoffeeFaultType.OPERATION_FAILED, "Error while reading multipart input!", e);
        }
    }

    /**
     * Read text from multipart form data
     *
     * @param parts
     *            List of InputPart
     * @param fieldName
     *            Name of the field for error messages
     * @return String value of the text part, or null if not present
     * @throws BaseException
     *             if an error occurs while reading the input stream or if multiple values are present
     */
    protected String readOptionalTextPart(List<InputPart> parts, String fieldName) throws BaseException {
        if (CollectionUtils.isEmpty(parts)) {
            return null;
        }
        if (parts.size() > 1) {
            throw new InvalidParameterException(MessageFormat.format("Only one value can be specified for part: [{0}]!", fieldName));
        }
        try {
            return parts.get(0).getBodyAsString();
        } catch (Exception e) {
            throw new TechnicalException(CoffeeFaultType.OPERATION_FAILED, "Error while reading multipart text input!", e);
        }
    }

    /**
     * Reads all text parts from multipart form data
     * 
     * @param parts
     *            List of InputPart
     * @return List of String values of the text parts, empty list if no parts are present
     * @throws BaseException
     *             if an error occurs while reading any of the input streams
     */
    protected List<String> readAllTextParts(List<InputPart> parts) throws BaseException {
        if (CollectionUtils.isEmpty(parts)) {
            return new ArrayList<>();
        }
        List<String> bodyStrings = new ArrayList<>();
        for (InputPart part : parts) {
            try {
                bodyStrings.add(part.getBodyAsString());
            } catch (IOException e) {
                throw new TechnicalException(CoffeeFaultType.OPERATION_FAILED, "Error while reading multipart text input!", e);
            }
        }
        return bodyStrings;
    }

    /**
     * Get single required file part from multipart form data
     *
     * @param parts
     *            List of InputPart
     * @param fieldName
     *            Name of the field for error messages
     * @return InputPart of the file, never null
     * @throws BaseException
     *             if the file part is missing or if multiple values are present
     */
    protected InputPart getSingleRequiredFilePart(List<InputPart> parts, String fieldName) throws BaseException {
        InputPart part = getSingleOptionalFilePart(parts, fieldName);
        if (part == null) {
            throw new InvalidParameterException(MessageFormat.format("Missing file part: [{0}]!", fieldName));
        }
        return part;
    }

    /**
     * Get single optional file part from multipart form data
     *
     * @param parts
     *            List of InputPart
     * @param fieldName
     *            Name of the field for error messages
     * @return InputPart of the file, or null if not present
     * @throws BaseException
     *             if multiple values are present
     */
    protected InputPart getSingleOptionalFilePart(List<InputPart> parts, String fieldName) throws BaseException {
        if (CollectionUtils.isEmpty(parts)) {
            return null;
        }
        if (parts.size() > 1) {
            throw new BusinessException(
                    FaultType.TOO_MANNY_FILES,
                    MessageFormat.format("Only one file can be specified for part: [{0}]!", fieldName));
        }
        return parts.get(0);
    }

    /**
     * Get single required JSON request part from multipart form data and unmarshall it into the specified request class, then validates it
     * 
     * @param parts
     *            List of InputPart containing the JSON body
     * @param requestClass
     *            Class of the request object to be created from the JSON content
     * @param fieldName
     *            Name of the field for error messages
     * @return An instance of the specified request class populated with data from the JSON content of the InputPart, never null
     * @param <REQUEST>
     *            Type of the request object, must extend BaseRequestType
     * @throws BaseException
     *             if the JSON part is missing, if multiple values are present, or if an error occurs while reading the InputPart or unmarshalling the
     *             JSON content
     */
    protected <REQUEST extends BaseRequestType> REQUEST getAndValidateSingleRequiredRequestPart(List<InputPart> parts, Class<REQUEST> requestClass,
            String fieldName) throws BaseException {
        REQUEST request = getSingleRequiredRequestPart(parts, requestClass, fieldName);
        String xml = jaxbTool.marshalXML(request, XsdConstants.SUPER_XSD_PATH);
        return jaxbTool.unmarshalXML(requestClass, xml.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Get single required JSON request part from multipart form data and unmarshall it into the specified request class
     *
     * @param parts
     *            List of InputPart containing the JSON body
     * @param requestClass
     *            Class of the request object to be created from the JSON content
     * @param fieldName
     *            Name of the field for error messages
     * @return An instance of the specified request class populated with data from the JSON content of the InputPart, never null
     * @param <REQUEST>
     *            Type of the request object, must extend BaseRequestType
     * @throws BaseException
     *             if the JSON part is missing, if multiple values are present, or if an error occurs while reading the InputPart or unmarshalling the
     *             JSON content
     */
    protected <REQUEST extends BaseRequestType> REQUEST getSingleRequiredRequestPart(List<InputPart> parts, Class<REQUEST> requestClass,
            String fieldName) throws BaseException {
        if (CollectionUtils.isEmpty(parts) || parts.size() > 1) {
            throw new BusinessException(CoffeeFaultType.INVALID_INPUT, "Only one value can be specified for part: [" + fieldName + "]!");
        }
        return unmarshallJsonRequest(parts.get(0), requestClass);
    }

    /**
     * Unmarshalls JSON content from the given InputPart into an instance of the specified request class
     * 
     * @param inputPart
     *            InputPart containing the JSON body to be unmarshalled
     * @param requestClass
     *            Class of the request object to be created from the JSON content
     * @return An instance of the specified request class populated with data from the JSON content of the InputPart
     * @param <REQUEST>
     *            Type of the request object, must extend BaseRequestType
     * @throws BaseException
     *             if an error occurs while reading the InputPart or unmarshalling the JSON content
     */
    protected <REQUEST extends BaseRequestType> REQUEST unmarshallJsonRequest(InputPart inputPart, Class<REQUEST> requestClass) throws BaseException {
        try {
            String bodyAsString = inputPart.getBodyAsString();
            return JsonUtil.toObject(bodyAsString, requestClass);
        } catch (IOException e) {
            throw new TechnicalException(CoffeeFaultType.OPERATION_FAILED, e.getMessage());
        }
    }
}
