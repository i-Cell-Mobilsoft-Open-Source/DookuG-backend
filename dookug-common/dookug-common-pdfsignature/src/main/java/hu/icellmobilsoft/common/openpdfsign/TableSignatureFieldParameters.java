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

import eu.europa.esig.dss.pades.SignatureFieldParameters;

/**
 * opendf-sign visible signature parameters
 * 
 * @author tamas.cserhati
 * @since 1.1.0
 */
public class TableSignatureFieldParameters extends SignatureFieldParameters {

    private static final long serialVersionUID = 1L;
    /**
     * date of signature
     */
    private String signatureDate;
    /**
     * hint
     */
    private String hint;
    /**
     * label of hint
     */
    private String labelHint;
    /**
     * signee
     */
    private String labelSignee;
    /**
     * label of timestamp
     */
    private String labelTimestamp;

    /**
     * the esignee
     */
    private String esignee;

    /**
     * @return the signatureDate
     */
    public String getSignatureDate() {
        return signatureDate;
    }

    /**
     * @param signatureDate
     *            the signatureDate to set
     */
    public void setSignatureDate(String signatureDate) {
        this.signatureDate = signatureDate;
    }

    /**
     * @return the hint
     */
    public String getHint() {
        return hint;
    }

    /**
     * @param hint
     *            the hint to set
     */
    public void setHint(String hint) {
        this.hint = hint;
    }

    /**
     * @return the labelHint
     */
    public String getLabelHint() {
        return labelHint;
    }

    /**
     * @param labelHint
     *            the labelHint to set
     */
    public void setLabelHint(String labelHint) {
        this.labelHint = labelHint;
    }

    /**
     * @return the labelSignee
     */
    public String getLabelSignee() {
        return labelSignee;
    }

    /**
     * @param labelSignee
     *            the labelSignee to set
     */
    public void setLabelSignee(String labelSignee) {
        this.labelSignee = labelSignee;
    }

    /**
     * @return the labelTimestamp
     */
    public String getLabelTimestamp() {
        return labelTimestamp;
    }

    /**
     * @param labelTimestamp
     *            the labelTimestamp to set
     */
    public void setLabelTimestamp(String labelTimestamp) {
        this.labelTimestamp = labelTimestamp;
    }

    /**
     * @return the esignee
     */
    public String getEsignee() {
        return esignee;
    }

    /**
     * @param esignee
     *            the esignee to set
     */
    public void setEsignee(String esignee) {
        this.esignee = esignee;
    }
}
