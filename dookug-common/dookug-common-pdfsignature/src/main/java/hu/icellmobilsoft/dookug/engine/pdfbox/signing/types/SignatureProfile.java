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
package hu.icellmobilsoft.dookug.engine.pdfbox.signing.types;

import java.util.Optional;

/**
 * SignatureProfile DTO
 * 
 * @author tamas.cserhati
 * @since 0.2.0
 */
public class SignatureProfile {

    private String name;
    private String reason;
    private String keystore;
    private Optional<String> keystorePassword;
    private String keystoreType;
    private String keystoreAlias;
    private String keyAlias;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * @param reason
     *            the reason to set
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * @return the keystore
     */
    public String getKeystore() {
        return keystore;
    }

    /**
     * @param keystore
     *            the keystore to set
     */
    public void setKeystore(String keystore) {
        this.keystore = keystore;
    }

    /**
     * @return the keystorePassword
     */
    public Optional<String> getKeystorePassword() {
        return keystorePassword;
    }

    /**
     * @param keystorePassword
     *            the keystorePassword to set
     */
    public void setKeystorePassword(Optional<String> keystorePassword) {
        this.keystorePassword = keystorePassword;
    }

    /**
     * @return the keystoreType
     */
    public String getKeystoreType() {
        return keystoreType;
    }

    /**
     * @param keystoreType
     *            the keystoreType to set
     */
    public void setKeystoreType(String keystoreType) {
        this.keystoreType = keystoreType;
    }

    /**
     * @return the keystoreAlias
     */
    public String getKeystoreAlias() {
        return keystoreAlias;
    }

    /**
     * @param keystoreAlias
     *            the keystoreAlias to set
     */
    public void setKeystoreAlias(String keystoreAlias) {
        this.keystoreAlias = keystoreAlias;
    }

    /**
     * @return the keyAlias
     */
    public String getKeyAlias() {
        return keyAlias;
    }

    /**
     * @param keyAlias
     *            the keyAlias to set
     */
    public void setKeyAlias(String keyAlias) {
        this.keyAlias = keyAlias;
    }

}
