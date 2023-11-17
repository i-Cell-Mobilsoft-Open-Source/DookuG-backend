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

import java.security.PrivateKey;
import java.security.cert.Certificate;

/**
 * custom DTO privatekey + certchainhez
 * 
 * @author tamas.cserhati
 * @since 0.0.1
 */
public class CMSPrivateKey {

    private PrivateKey privateKey;
    private Certificate[] certificateChain;

    /**
     * @return the privateKey
     */
    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    /**
     * @param privateKey
     *            the privateKey to set
     */
    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    /**
     * @return the certificateChain
     */
    public Certificate[] getCertificateChain() {
        return certificateChain;
    }

    /**
     * @param certificateChain
     *            the certificateChain to set
     */
    public void setCertificateChain(Certificate[] certificateChain) {
        this.certificateChain = certificateChain;
    }

}
