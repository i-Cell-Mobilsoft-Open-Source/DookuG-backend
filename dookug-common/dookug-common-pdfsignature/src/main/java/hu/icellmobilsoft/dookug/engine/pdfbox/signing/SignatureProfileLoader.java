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
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.coffee.configuration.ApplicationConfiguration;
import hu.icellmobilsoft.coffee.dto.exception.InvalidParameterException;
import hu.icellmobilsoft.coffee.dto.exception.TechnicalException;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.dookug.engine.pdfbox.config.ConfigKeys;
import hu.icellmobilsoft.dookug.engine.pdfbox.signing.types.SignatureProfileDto;

/**
 * SignatureProfile DTO loader
 * 
 * @author tamas.cserhati
 * @since 0.2.0
 */
@Model
public class SignatureProfileLoader {

    @Inject
    private ApplicationConfiguration applicationConfiguration;

    private Map<String, SignatureProfileDto> signatureProfiles = new HashMap<>();

    /**
     * {@value #SIGNATURE_PROFILE_CANNOT_BE_EMPTY}
     */
    private static final String SIGNATURE_PROFILE_CANNOT_BE_EMPTY = "Signature profile cannot be empty";

    /**
     * return with {@link SignatureProfileDto} for the given profile name from cache. If its empty it tries to load from configuration
     * 
     * @param profileName
     *            Signature profile name in the configuration
     * @return Signature profile filled with the configuration data
     * @throws BaseException
     *             on error
     */
    public SignatureProfileDto getSignatureProfile(String profileName) throws BaseException {
        if (StringUtils.isBlank(profileName)) {
            throw new TechnicalException(CoffeeFaultType.INVALID_INPUT, SIGNATURE_PROFILE_CANNOT_BE_EMPTY);
        }
        if (signatureProfiles.containsKey(profileName)) {
            return signatureProfiles.get(profileName);
        } else {
            SignatureProfileDto signatureProfile = loadSignatureProfile(profileName);
            signatureProfiles.put(profileName, signatureProfile);
            return signatureProfile;
        }

    }

    private SignatureProfileDto loadSignatureProfile(String profileName) throws BaseException {

        SignatureProfileDto signatureProfile = new SignatureProfileDto(profileName);
        signatureProfile.setKeystore(getConfigurationValue(profileName, ConfigKeys.PdfSignature.KEYSTORE));
        signatureProfile.setKeystorePassword(
                getOptionalConfigurationValue(profileName, ConfigKeys.PdfSignature.KEYSTORE_PASSWORD)
                        .orElseThrow(() -> new InvalidParameterException("keystore password cannot be empty!")));
        signatureProfile.setKeystoreType(getConfigurationValue(profileName, ConfigKeys.PdfSignature.KEYSTORE_TYPE));
        signatureProfile.setKeyAlias(getConfigurationValue(profileName, ConfigKeys.PdfSignature.KEY_ALIAS));
        signatureProfile.setName(getOptionalConfigurationValue(profileName, ConfigKeys.PdfSignature.NAME).orElse(null));
        signatureProfile.setReason(getOptionalConfigurationValue(profileName, ConfigKeys.PdfSignature.REASON).orElse(null));

        signatureProfile.setUseEuDssSig(
                BooleanUtils.toBoolean(getOptionalConfigurationValue(profileName, ConfigKeys.PdfSignature.USE_EUDSSSIG).orElse("false")));
        signatureProfile.setDssImageFile(getOptionalConfigurationValue(profileName, ConfigKeys.PdfSignature.DSS.IMAGE_FILE));
        signatureProfile.setDssHintText(
                getOptionalConfigurationValue(profileName, ConfigKeys.PdfSignature.DSS.HINT_TEXT)
                        .orElse(ConfigKeys.PdfSignature.Default.HINT_TEXT_DEFAULT_VALUE));
        signatureProfile.setDssLabelHint(
                getOptionalConfigurationValue(profileName, ConfigKeys.PdfSignature.DSS.LABEL_HINT)
                        .orElse(ConfigKeys.PdfSignature.Default.LABEL_HINT_DEFAULT_VALUE));
        signatureProfile.setDssLabelSignee(
                getOptionalConfigurationValue(profileName, ConfigKeys.PdfSignature.DSS.LABEL_SIGNEE)
                        .orElse(ConfigKeys.PdfSignature.Default.LABEL_SIGNEE_DEFAULT_VALUE));
        signatureProfile.setDssLabelTimestamp(
                getOptionalConfigurationValue(profileName, ConfigKeys.PdfSignature.DSS.LABEL_TIMESTAMP)
                        .orElse(ConfigKeys.PdfSignature.Default.LABEL_TIMESTAMP_DEFAULT_VALUE));
        signatureProfile.setDssLeft(
                Float.parseFloat(
                        getOptionalConfigurationValue(profileName, ConfigKeys.PdfSignature.DSS.POSITION_LEFT)
                                .orElse(ConfigKeys.PdfSignature.Default.LEFT_DEFAULT_VALUE)));
        signatureProfile.setDssPage(
                Integer.parseInt(
                        getOptionalConfigurationValue(profileName, ConfigKeys.PdfSignature.DSS.PAGE)
                                .orElse(ConfigKeys.PdfSignature.Default.PAGE_DEFAULT_VALUE)));
        signatureProfile.setDssTimezone(
                getOptionalConfigurationValue(profileName, ConfigKeys.PdfSignature.DSS.TIMEZONE).orElse(ZoneId.systemDefault().toString()));
        signatureProfile.setDssTop(
                Float.parseFloat(
                        getOptionalConfigurationValue(profileName, ConfigKeys.PdfSignature.DSS.POSITION_TOP)
                                .orElse(ConfigKeys.PdfSignature.Default.TOP_DEFAULT_VALUE)));
        signatureProfile.setDssTrustedCertificateListUrl(
                List.of(
                        StringUtils.split(
                                getOptionalConfigurationValue(profileName, ConfigKeys.PdfSignature.DSS.TRUSTED_CERTIFICATES)
                                        .orElse(ConfigKeys.PdfSignature.Default.TRUSTED_CERTIFICATES_DEFAULT_VALUE),
                                ",")));
        signatureProfile.setDssTsaList(
                List.of(
                        StringUtils.split(
                                getOptionalConfigurationValue(profileName, ConfigKeys.PdfSignature.DSS.TSP_SOURCES)
                                        .orElse(ConfigKeys.PdfSignature.Default.TSP_SOURCES_DEFAULT_VALUE),
                                ",")));
        signatureProfile.setDssWidth(
                Float.parseFloat(
                        getOptionalConfigurationValue(profileName, ConfigKeys.PdfSignature.DSS.WIDTH)
                                .orElse(ConfigKeys.PdfSignature.Default.WIDTH_DEFAULT_VALUE)));
        signatureProfile.setDssSkipTimestampOnError(
                BooleanUtils.toBoolean(
                        getOptionalConfigurationValue(profileName, ConfigKeys.PdfSignature.DSS.SKIP_TIMESTAMP_ON_ERROR)
                                .orElse(ConfigKeys.PdfSignature.Default.SKIP_TIMESTAMP_DEFAULT_VALUE)));
        signatureProfile.setDssUseHint(
                BooleanUtils.toBoolean(
                        getOptionalConfigurationValue(profileName, ConfigKeys.PdfSignature.DSS.USE_HINT)
                                .orElse(ConfigKeys.PdfSignature.Default.USE_HINT_DEFAULT_VALUE)));
        signatureProfile.setDssUseLT(
                BooleanUtils.toBoolean(
                        getOptionalConfigurationValue(profileName, ConfigKeys.PdfSignature.DSS.USE_LT)
                                .orElse(ConfigKeys.PdfSignature.Default.USE_LT_DEFAULT_VALUE)));
        signatureProfile.setDssUseLTA(
                BooleanUtils.toBoolean(
                        getOptionalConfigurationValue(profileName, ConfigKeys.PdfSignature.DSS.USE_LTA)
                                .orElse(ConfigKeys.PdfSignature.Default.USE_LTA_DEFAULT_VALUE)));
        signatureProfile.setDssUseTimestamp(
                BooleanUtils.toBoolean(
                        getOptionalConfigurationValue(profileName, ConfigKeys.PdfSignature.DSS.USE_TIMESTAMP)
                                .orElse(ConfigKeys.PdfSignature.Default.USE_TIMESTAMP_DEFAULT_VALUE)));
        signatureProfile.setDssShowSignee(
                BooleanUtils.toBoolean(
                        getOptionalConfigurationValue(profileName, ConfigKeys.PdfSignature.DSS.SHOW_SIGNEE)
                                .orElse(ConfigKeys.PdfSignature.Default.SHOW_SIGNEE_DEFAULT_VALUE)));
        return signatureProfile;
    }

    private String getConfigurationValue(String profileName, String key) {
        return applicationConfiguration.getString(MessageFormat.format(key, profileName));
    }

    private Optional<String> getOptionalConfigurationValue(String profileName, String key) {
        return applicationConfiguration.getOptionalString(MessageFormat.format(key, profileName));
    }

}
