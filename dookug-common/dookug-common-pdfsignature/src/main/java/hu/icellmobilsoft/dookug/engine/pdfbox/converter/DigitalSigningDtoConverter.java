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
package hu.icellmobilsoft.dookug.engine.pdfbox.converter;

import hu.icellmobilsoft.dookug.common.cdi.sign.DigitalSigningDto;
import hu.icellmobilsoft.dookug.schemas.document._1_0.rest.documentgenerate.DigitalSigningType;

/**
 * DigitalSigningType -&gt; DigitalSigningFto converter
 * 
 * @author tamas.cserhati
 * @since 0.1.0
 */
public class DigitalSigningDtoConverter {

    /**
     * conversion
     * 
     * @param source
     *            the {@link DigitalSigningDto} input object
     * @return result of {@link DigitalSigningDto}, can be null
     */
    public static DigitalSigningDto toDigitalSigningDto(DigitalSigningType source) {
        if (source == null) {
            return null;
        }
        DigitalSigningDto dest = new DigitalSigningDto();
        dest.setSignatureProfile(source.getSignatureProfile());
        dest.setSignatureName(source.getSignatureName());
        dest.setSignatureReason(source.getSignatureReason());
        return dest;
    }

}
