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

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.text.MessageFormat;
import java.util.List;

import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;

import org.bouncycastle.asn1.x509.KeyPurposeId;

import hu.icellmobilsoft.coffee.cdi.logger.AppLogger;
import hu.icellmobilsoft.coffee.cdi.logger.ThisLogger;
import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.coffee.dto.exception.TechnicalException;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.dookug.engine.pdfbox.signing.types.CMSPrivateKey;
import hu.icellmobilsoft.dookug.engine.pdfbox.signing.types.SignatureProfile;

/**
 * Private Key producer
 * 
 * @author tamas.cserhati
 * @since 0.1.0
 */
@Model
public class CMSPrivateKeyLoader {

    /**
     * {@value #KEYSTORE_PASSWORD_CANNOT_BE_EMPTY}
     */
    private static final String KEYSTORE_PASSWORD_CANNOT_BE_EMPTY = "Keystore password cannot be empty!";
    /**
     * {@value #CERTIFICATE_EXTENDED_KEY_PROBLEM}
     */
    private static final String CERTIFICATE_EXTENDED_KEY_PROBLEM = "Certificate extended key usage does not include emailProtection, nor codeSigning, nor anyExtendedKeyUsage, nor 'Adobe Authentic Documents Trust'";
    /**
     * {@value #CERTIFICATE_KEY_PROBLEM}
     */
    private static final String CERTIFICATE_KEY_PROBLEM = "Certificate key usage does not include digitalSignature nor nonRepudiation";
    /**
     * {@value #COULD_NOT_FIND_CERTIFICATE}
     */
    private static final String COULD_NOT_FIND_CERTIFICATE = "Could not find certificate [{0}]";
    /**
     * {@value #OID_KP_DOCUMENT_SIGNING} Signer of documents -- szOID_KP_DOCUMENT_SIGNING
     * 
     * @see <a href="https://oid-rep.orange-labs.fr/get/1.3.6.1.4.1.311.10.3.12">OID Repository</a> (external url)
     */
    private static final String OID_KP_DOCUMENT_SIGNING = "1.3.6.1.4.1.311.10.3.12";
    /**
     * {@value #OID_AUTHENTIC_DOCUMENTS_TRUST} AuthenticDocumentsTrust: Indicates that the particular credential can be used for the Certified
     * Document Services (CDS)
     * 
     * @see <a href="https://oid-rep.orange-labs.fr/get/1.2.840.113583.1.1.5">OID Repository</a> (external url)
     */
    private static final String OID_AUTHENTIC_DOCUMENTS_TRUST = "1.2.840.113583.1.1.5";

    @Inject
    @ThisLogger
    private AppLogger log;

    /**
     * Creates or gets {@link CMSPrivateKey} with the given alias
     * 
     * @param signatureProfile
     *            signature profile
     * @return private key for digital signing
     * @throws BaseException
     *             on error
     */
    public CMSPrivateKey privateKeyResolver(SignatureProfile signatureProfile) throws BaseException {

        if (signatureProfile.getKeystorePassword().isEmpty()) {
            throw new TechnicalException(CoffeeFaultType.OPERATION_FAILED, KEYSTORE_PASSWORD_CANNOT_BE_EMPTY);
        }

        CMSPrivateKey cmsPrivateKey = new CMSPrivateKey();
        Certificate actualCert = null;
        char[] keystorePassword = signatureProfile.getKeystorePassword().get().toCharArray();
        try {
            KeyStore keystore = KeyStore.getInstance(signatureProfile.getKeystoreType());
            keystore.load(new FileInputStream(signatureProfile.getKeystore()), keystorePassword);
            // TODO: feature - kulcshoz meg lehessen adni masik jelszot is
            PrivateKey privateKey = ((PrivateKey) keystore.getKey(signatureProfile.getKeyAlias(), keystorePassword));
            cmsPrivateKey.setPrivateKey(privateKey);

            Certificate[] certChain = keystore.getCertificateChain(signatureProfile.getKeyAlias());
            cmsPrivateKey.setCertificateChain(certChain);
            if (certChain != null) {
                actualCert = certChain[0];
                if (actualCert instanceof X509Certificate) {
                    // avoid expired certificate
                    ((X509Certificate) actualCert).checkValidity();
                    checkCertificateUsage((X509Certificate) actualCert);
                }
            }
            if (actualCert == null) {
                throw new TechnicalException(
                        CoffeeFaultType.OPERATION_FAILED,
                        MessageFormat.format(COULD_NOT_FIND_CERTIFICATE, signatureProfile.getKeyAlias()));
            }
            return cmsPrivateKey;
        } catch (GeneralSecurityException | IOException e) {
            throw new TechnicalException(CoffeeFaultType.OPERATION_FAILED, e.getLocalizedMessage(), e);
        }
    }

    /**
     * Log if the certificate is not valid for signature usage. Doing this anyway results in Adobe Reader failing to validate the PDF.
     *
     * @param x509Certificate
     *            the certificate to check
     * @throws java.security.cert.CertificateParsingException
     *             on parsing error
     */
    public void checkCertificateUsage(X509Certificate x509Certificate) throws CertificateParsingException {
        // Check whether signer certificate is "valid for usage"
        // https://stackoverflow.com/a/52765021/535646
        // https://www.adobe.com/devnet-docs/acrobatetk/tools/DigSig/changes.html#id1
        boolean[] keyUsage = x509Certificate.getKeyUsage();
        if (keyUsage != null && !keyUsage[0] && !keyUsage[1]) {
            // (unclear what "signTransaction" is)
            // https://tools.ietf.org/html/rfc5280#section-4.2.1.3
            log.error(CERTIFICATE_KEY_PROBLEM);
        }
        List<String> extendedKeyUsage = x509Certificate.getExtendedKeyUsage();
        if (extendedKeyUsage != null && !extendedKeyUsage.contains(KeyPurposeId.id_kp_emailProtection.toString())
                && !extendedKeyUsage.contains(KeyPurposeId.id_kp_codeSigning.toString())
                && !extendedKeyUsage.contains(KeyPurposeId.anyExtendedKeyUsage.toString())
                && !extendedKeyUsage.contains(OID_AUTHENTIC_DOCUMENTS_TRUST) &&
                // not mentioned in Adobe document, but tolerated in practice
                !extendedKeyUsage.contains(OID_KP_DOCUMENT_SIGNING)) {
            log.error(CERTIFICATE_EXTENDED_KEY_PROBLEM);
        }
    }

}
