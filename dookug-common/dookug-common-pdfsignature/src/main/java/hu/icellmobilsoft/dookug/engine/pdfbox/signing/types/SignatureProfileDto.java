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

import java.util.List;
import java.util.Optional;

/**
 * SignatureProfile DTO
 * 
 * @author tamas.cserhati
 * @since 0.2.0
 */
public class SignatureProfileDto {

    private String profileName;

    private String name;
    private String reason;
    private String keystore;
    private String keystorePassword;
    private String keystoreType;
    private String keystoreAlias;
    private String keyAlias;

    private Integer dssShowOnPage;
    private Float dssPositionTop;
    private Float dssPositionLeft;
    private Float dssWidth;
    private String dssHintText;
    private boolean dssUseHint;
    private boolean dssUseTimestamp;
    private boolean dssSkipTimestampOnError;
    private boolean dssUseLT;
    private boolean dssUseLTA;
    private String dssLabelHint;
    private String dssLabelTimestamp;
    private String dssLabelSignee;
    private List<String> dssTsaList;
    private String dssTimezone;
    private List<String> dssTrustedCertificateListUrl;
    private Optional<String> dssImageFile;
    private boolean useEuDssSig;
    private boolean dssShowSignee;
    private String digestAlgorithm;
    private String encryptionAlgorithm;

    /**
     * constructor
     * 
     * @param profileName
     *            name of the profile
     */
    public SignatureProfileDto(String profileName) {
        this.profileName = profileName;
    }

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
    public String getKeystorePassword() {
        return keystorePassword;
    }

    /**
     * @param keystorePassword
     *            the keystorePassword to set
     */
    public void setKeystorePassword(String keystorePassword) {
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

    /**
     * @return the dssWidth
     */
    public Float getDssWidth() {
        return dssWidth;
    }

    /**
     * @param dssWidth
     *            the dssWidth to set
     */
    public void setDssWidth(Float dssWidth) {
        this.dssWidth = dssWidth;
    }

    /**
     * @return the dssHintText
     */
    public String getDssHintText() {
        return dssHintText;
    }

    /**
     * @param dssHintText
     *            the dssHintText to set
     */
    public void setDssHintText(String dssHintText) {
        this.dssHintText = dssHintText;
    }

    /**
     * @return the dssLabelHint
     */
    public String getDssLabelHint() {
        return dssLabelHint;
    }

    /**
     * @param dssLabelHint
     *            the dssLabelHint to set
     */
    public void setDssLabelHint(String dssLabelHint) {
        this.dssLabelHint = dssLabelHint;
    }

    /**
     * @return the dssLabelTimestamp
     */
    public String getDssLabelTimestamp() {
        return dssLabelTimestamp;
    }

    /**
     * @param dssLabelTimestamp
     *            the dssLabelTimestamp to set
     */
    public void setDssLabelTimestamp(String dssLabelTimestamp) {
        this.dssLabelTimestamp = dssLabelTimestamp;
    }

    /**
     * @return the dssLabelSignee
     */
    public String getDssLabelSignee() {
        return dssLabelSignee;
    }

    /**
     * @param dssLabelSignee
     *            the dssLabelSignee to set
     */
    public void setDssLabelSignee(String dssLabelSignee) {
        this.dssLabelSignee = dssLabelSignee;
    }

    /**
     * @return the dssTsaList
     */
    public List<String> getDssTsaList() {
        return dssTsaList;
    }

    /**
     * @param dssTsaList
     *            the dssTsaList to set
     */
    public void setDssTsaList(List<String> dssTsaList) {
        this.dssTsaList = dssTsaList;
    }

    /**
     * @return the dssTrustedCertificateListUrl
     */
    public List<String> getDssTrustedCertificateListUrl() {
        return dssTrustedCertificateListUrl;
    }

    /**
     * @param dssTrustedCertificateListUrl
     *            the dssTrustedCertificateListUrl to set
     */
    public void setDssTrustedCertificateListUrl(List<String> dssTrustedCertificateListUrl) {
        this.dssTrustedCertificateListUrl = dssTrustedCertificateListUrl;
    }

    /**
     * @return the dssUseHint
     */
    public boolean isDssUseHint() {
        return dssUseHint;
    }

    /**
     * @param dssUseHint
     *            the dssUseHint to set
     */
    public void setDssUseHint(boolean dssUseHint) {
        this.dssUseHint = dssUseHint;
    }

    /**
     * @return the dssUseTimestamp
     */
    public boolean isDssUseTimestamp() {
        return dssUseTimestamp;
    }

    /**
     * @param dssUseTimestamp
     *            the dssUseTimestamp to set
     */
    public void setDssUseTimestamp(boolean dssUseTimestamp) {
        this.dssUseTimestamp = dssUseTimestamp;
    }

    /**
     * @return the dssUseLT
     */
    public boolean isDssUseLT() {
        return dssUseLT;
    }

    /**
     * @param dssUseLT
     *            the dssUseLT to set
     */
    public void setDssUseLT(boolean dssUseLT) {
        this.dssUseLT = dssUseLT;
    }

    /**
     * @return the dssUseLTA
     */
    public boolean isDssUseLTA() {
        return dssUseLTA;
    }

    /**
     * @param dssUseLTA
     *            the dssUseLTA to set
     */
    public void setDssUseLTA(boolean dssUseLTA) {
        this.dssUseLTA = dssUseLTA;
    }

    /**
     * @return the dssImageFile
     */
    public Optional<String> getDssImageFile() {
        return dssImageFile;
    }

    /**
     * @param dssImageFile
     *            the dssImageFile to set
     */
    public void setDssImageFile(Optional<String> dssImageFile) {
        this.dssImageFile = dssImageFile;
    }

    /**
     * @return the dssTimezone
     */
    public String getDssTimezone() {
        return dssTimezone;
    }

    /**
     * @param dssTimezone
     *            the dssTimezone to set
     */
    public void setDssTimezone(String dssTimezone) {
        this.dssTimezone = dssTimezone;
    }

    /**
     * @return the useEuDssSig
     */
    public boolean isUseEuDssSig() {
        return useEuDssSig;
    }

    /**
     * @param useEuDssSig
     *            the useEuDssSig to set
     */
    public void setUseEuDssSig(boolean useEuDssSig) {
        this.useEuDssSig = useEuDssSig;
    }

    /**
     * @return the dssSkipTimestampOnError
     */
    public boolean isDssSkipTimestampOnError() {
        return dssSkipTimestampOnError;
    }

    /**
     * @param dssSkipTimestampOnError
     *            the dssSkipTimestampOnError to set
     */
    public void setDssSkipTimestampOnError(boolean dssSkipTimestampOnError) {
        this.dssSkipTimestampOnError = dssSkipTimestampOnError;
    }

    /**
     * @return the dssShowSignee
     */
    public boolean isDssShowSignee() {
        return dssShowSignee;
    }

    /**
     * @param dssShowSignee
     *            the dssShowSignee to set
     */
    public void setDssShowSignee(boolean dssShowSignee) {
        this.dssShowSignee = dssShowSignee;
    }

    /**
     * @return the profileName
     */
    public String getProfileName() {
        return profileName;
    }

    /**
     * @param profileName
     *            the profileName to set
     */
    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    /**
     * @return the dssShowOnPage
     */
    public Integer getDssShowOnPage() {
        return dssShowOnPage;
    }

    /**
     * @param dssShowOnPage
     *            the dssShowOnPage to set
     */
    public void setDssShowOnPage(Integer dssShowOnPage) {
        this.dssShowOnPage = dssShowOnPage;
    }

    /**
     * @return the dssPositionTop
     */
    public Float getDssPositionTop() {
        return dssPositionTop;
    }

    /**
     * @param dssPositionTop
     *            the dssPositionTop to set
     */
    public void setDssPositionTop(Float dssPositionTop) {
        this.dssPositionTop = dssPositionTop;
    }

    /**
     * @return the dssPositionLeft
     */
    public Float getDssPositionLeft() {
        return dssPositionLeft;
    }

    /**
     * @param dssPositionLeft
     *            the dssPositionLeft to set
     */
    public void setDssPositionLeft(Float dssPositionLeft) {
        this.dssPositionLeft = dssPositionLeft;
    }

    /**
     * @return the encryptionAlgorithm
     */
    public String getEncryptionAlgorithm() {
        return encryptionAlgorithm;
    }

    /**
     * @param encryptionAlgorithm the encryptionAlgorithm to set
     */
    public void setEncryptionAlgorithm(String encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
    }

    /**
     * @return the digestAlgorithm
     */
    public String getDigestAlgorithm() {
        return digestAlgorithm;
    }

    /**
     * @param digestAlgorithm the digestAlgorithm to set
     */
    public void setDigestAlgorithm(String digestAlgorithm) {
        this.digestAlgorithm = digestAlgorithm;
    }

}
