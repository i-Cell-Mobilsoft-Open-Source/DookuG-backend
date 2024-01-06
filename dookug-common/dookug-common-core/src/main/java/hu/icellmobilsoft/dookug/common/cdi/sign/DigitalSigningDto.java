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
package hu.icellmobilsoft.dookug.common.cdi.sign;

/**
 * Custom DTO digitalis alairas parameterekhez
 * 
 * @author tamas.cserhati
 * @since 0.1.0
 */
public class DigitalSigningDto {

    private String signatureProfile;
    private String signatureName;
    private String signatureReason;

    /**
     * @return the signatureProfile
     */
    public String getSignatureProfile() {
        return signatureProfile;
    }

    /**
     * @param signatureProfile
     *            the signatureProfile to set
     */
    public void setSignatureProfile(String signatureProfile) {
        this.signatureProfile = signatureProfile;
    }

    /**
     * @return the signatureName
     */
    public String getSignatureName() {
        return signatureName;
    }

    /**
     * @param signatureName
     *            the signatureName to set
     */
    public void setSignatureName(String signatureName) {
        this.signatureName = signatureName;
    }

    /**
     * @return the signatureReason
     */
    public String getSignatureReason() {
        return signatureReason;
    }

    /**
     * @param signatureReason
     *            the signatureReason to set
     */
    public void setSignatureReason(String signatureReason) {
        this.signatureReason = signatureReason;
    }
}
