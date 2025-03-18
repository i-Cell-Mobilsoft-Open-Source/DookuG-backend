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
package hu.icellmobilsoft.dookug.engine.pdfbox.signing;

import java.text.MessageFormat;
import java.util.Optional;

import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.coffee.configuration.ApplicationConfiguration;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.dto.exception.TechnicalException;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.dookug.engine.pdfbox.config.ConfigKeys;
import hu.icellmobilsoft.dookug.engine.pdfbox.signing.types.SignatureProfile;

/**
 * SignatureProfile DTO loader
 * 
 * @author tamas.cserhati
 * @since 0.2.0
 */
@Model
public class SignatureProfileLoader {

    private static final String SHA256_WITH_RSA = "SHA256WithRSA";

    @Inject
    private ApplicationConfiguration applicationConfiguration;

    /**
     * {@value #SIGNATURE_PROFILE_CANNOT_BE_EMPTY}
     */
    private static final String SIGNATURE_PROFILE_CANNOT_BE_EMPTY = "Signature profile cannot be empty";

    /**
     * Fill {@link SignatureProfile} with the given alias
     * 
     * @param profileName
     *            Signature profile name in the configuration
     * @return Signature profile filled with the configuration data
     * @throws BaseException
     *             on error
     */
    public SignatureProfile loadSignatureProfile(String profileName) throws BaseException {

        if (StringUtils.isBlank(profileName)) {
            throw new TechnicalException(CoffeeFaultType.INVALID_INPUT, SIGNATURE_PROFILE_CANNOT_BE_EMPTY);
        }
        SignatureProfile signatureProfileDto = new SignatureProfile();
        signatureProfileDto.setKeystore(getConfigurationValue(profileName, ConfigKeys.PdfDefaultSignature.KEYSTORE));
        signatureProfileDto.setKeystorePassword(getOptionalConfigurationValue(profileName, ConfigKeys.PdfDefaultSignature.KEYSTORE_PASSWORD));
        signatureProfileDto.setKeystoreType(getConfigurationValue(profileName, ConfigKeys.PdfDefaultSignature.KEYSTORE_TYPE));
        signatureProfileDto.setKeyAlias(getConfigurationValue(profileName, ConfigKeys.PdfDefaultSignature.KEY_ALIAS));
        signatureProfileDto.setName(getConfigurationValue(profileName, ConfigKeys.PdfDefaultSignature.NAME));
        signatureProfileDto.setReason(getConfigurationValue(profileName, ConfigKeys.PdfDefaultSignature.REASON));
        signatureProfileDto.setSignatureAlgorithm(
                getOptionalConfigurationValue(profileName, ConfigKeys.PdfDefaultSignature.SIGNATURE_ALGORITHM).orElse(SHA256_WITH_RSA));

        return signatureProfileDto;
    }

    private String getConfigurationValue(String profileName, String keyName) throws BaseException {
        return applicationConfiguration.getString(
                MessageFormat
                        .format(ConfigKeys.PdfDefaultSignature.DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_SIGNATURE_PROFILE_0_1, profileName, keyName));
    }

    private Optional<String> getOptionalConfigurationValue(String profileName, String keyName) throws BaseException {
        return applicationConfiguration.getOptionalString(
                MessageFormat
                        .format(ConfigKeys.PdfDefaultSignature.DOOKUG_SERVICE_ENGINE_PDF_DIGITALSIGN_SIGNATURE_PROFILE_0_1, profileName, keyName));
    }

}
