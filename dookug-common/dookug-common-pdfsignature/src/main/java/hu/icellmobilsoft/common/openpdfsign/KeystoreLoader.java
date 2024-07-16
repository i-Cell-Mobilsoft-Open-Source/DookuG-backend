/*-
 * #%L
 * DookuG
 * %%
 * Copyright (C) 2023 - 2024 i-Cell Mobilsoft Zrt.
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
package hu.icellmobilsoft.common.openpdfsign;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.security.KeyStore;

import jakarta.enterprise.context.ApplicationScoped;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.ConfigProvider;

import hu.icellmobilsoft.coffee.dto.exception.InvalidParameterException;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.se.api.exception.BusinessException;
import hu.icellmobilsoft.common.digitalsign.cache.AbstractCache;
import hu.icellmobilsoft.dookug.api.dto.constants.ConfigKeys;
import hu.icellmobilsoft.dookug.engine.pdfbox.signing.types.SignatureProfileDto;

/**
 * Helper for loading keystore files
 * 
 * @author tamas.cserhati
 * @since 1.1.0
 */
@ApplicationScoped
public class KeystoreLoader extends AbstractCache<SignatureProfileDto, byte[]> {

    /**
     * default key alias
     */
    public static final String DEFAULT_ALIAS = "alias";

    /**
     * Load the keystore from the signature profile configured via MP
     * 
     * @param profile
     *            the signature profile dto
     * @return the keystore as a byte array
     * @throws BaseException
     *             on error
     */
    private byte[] loadConfiguredFile(SignatureProfileDto profile) throws BaseException {
        if (profile == null) {
            throw new InvalidParameterException("loadKeystore: profile cannot be empty!");
        }

        return loadConfiguredFile(profile.getKeystoreType(), profile.getKeystore(), profile.getKeystorePassword().toCharArray());
    }

    /**
     * Load the keystore from the path configured via MP
     * 
     * @param keystoreType
     *            type of keystore (e.g., PKCS12)
     * @param keystorePath
     *            the keystore
     * @param keystorePassword
     *            password of the keystore
     * @return the keystore as byte array
     * @throws BaseException
     *             on error
     */
    private byte[] loadConfiguredFile(String keystoreType, String keystorePath, char[] keystorePassword) throws BaseException {
        if (StringUtils.isAnyBlank(keystoreType, keystorePath) || keystorePassword == null) {
            throw new InvalidParameterException("loadKeystore: parameters cannot be empty!");
        }
        try {
            KeyStore jks = KeyStore.getInstance(keystoreType);
            jks.load(new FileInputStream(keystorePath), keystorePassword);
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                jks.store(bos, keystorePassword);
                return bos.toByteArray();
            }
        } catch (Exception e) {
            throw new BusinessException(CoffeeFaultType.OPERATION_FAILED, e.getLocalizedMessage(), e);
        }
    }

    @Override
    protected long getTtl() {
        return ConfigProvider.getConfig()
                .getOptionalValue(ConfigKeys.Cache.Keystore.TTL, Long.class)
                .orElse(Long.parseLong(ConfigKeys.Cache.Keystore.Defaults.TTL_IN_MINUTES));
    }

    @Override
    protected boolean isStatisticsEnabled() {
        return ConfigProvider.getConfig()
                .getOptionalValue(ConfigKeys.Cache.Keystore.ENABLESTATISTIC, Boolean.class)
                .orElse(BooleanUtils.toBooleanObject(ConfigKeys.Cache.Keystore.Defaults.ENABLESTATISTIC));

    }

    @Override
    public boolean isCacheEnabled() {
        return ConfigProvider.getConfig()
                .getOptionalValue(ConfigKeys.Cache.Keystore.ENABLED, Boolean.class)
                .orElse(BooleanUtils.toBooleanObject(ConfigKeys.Cache.Keystore.Defaults.ENABLED));
    }

    @Override
    protected byte[] load(SignatureProfileDto key) throws BaseException {
        return loadConfiguredFile(key);
    }

}
