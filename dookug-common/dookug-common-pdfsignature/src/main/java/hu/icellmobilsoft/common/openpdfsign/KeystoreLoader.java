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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.inject.Model;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JceOpenSSLPKCS8DecryptorProviderBuilder;
import org.bouncycastle.pkcs.PKCS8EncryptedPrivateKeyInfo;

import hu.icellmobilsoft.coffee.dto.exception.InvalidParameterException;
import hu.icellmobilsoft.coffee.dto.exception.TechnicalException;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.se.api.exception.BusinessException;
import hu.icellmobilsoft.dookug.common.util.random.RandomUtil;

/**
 * Keystore helper for creating PKCS12 keystore from PKCS8 files
 * 
 * @author tamas.cserhati
 * @since 1.1.0
 */
@Model
public class KeystoreLoader {

    private static final String X_509 = "X.509";
    private static final String PKCS12 = "PKCS12";
    /**
     * default key alias
     */
    public static final String DEFAULT_ALIAS = "alias";

    /**
     * this password is used for encrypting the temporaly generated PKCS12 keystore
     */
    private final char[] currentKeystorePassword = (RandomUtil.generateId() + RandomUtil.generateToken()).toCharArray();

    /**
     * Create a keystore object from the given with default alias: {@value #DEFAULT_ALIAS}
     * 
     * @param certificate
     *            the certificate (chain)
     * @param privateKeyData
     *            private key we use for signing
     * @param privateKeyPassword
     *            the password for the private key
     * @return PKCS12 keystore as byte array
     * @throws BaseException
     *             on error
     */
    public byte[] create(byte[] certificate, byte[] privateKeyData, char[] privateKeyPassword) throws BaseException {
        try {
            addBouncyCastleProvider();
            List<X509Certificate> certs = getX509Certificates(certificate);
            PEMParser privateKeyPEMParser = getPrivateKeyPEMParser(privateKeyData);
            return createKeystore(certs, privateKeyPEMParser, privateKeyPassword);
        } catch (IOException | CertificateException e) {
            throw new BusinessException(CoffeeFaultType.OPERATION_FAILED, e.getLocalizedMessage(), e);
        }
    }

    /**
     * Generate a PKCS12 Keystore from public and private key
     * 
     * @param certificatePath
     *            the certificate (chain) file path
     * @param privateKeyPath
     *            the private key path we use for signing
     * @param privateKeyPassword
     *            the password for the private key
     * @return PKCS12 keystore as byte array
     * @throws BaseException
     *             on error
     */
    public byte[] create(Path certificatePath, Path privateKeyPath, char[] privateKeyPassword) throws BaseException {
        try {
            addBouncyCastleProvider();
            List<X509Certificate> certs = certificatePath != null ? getX509Certificates(certificatePath) : new ArrayList<>();
            PEMParser privateKeyPEMParser = getPrivateKeyPEMParser(privateKeyPath);
            return createKeystore(certs, privateKeyPEMParser, privateKeyPassword);
        } catch (IOException | CertificateException e) {
            throw new BusinessException(CoffeeFaultType.OPERATION_FAILED, e.getLocalizedMessage(), e);
        }
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
    public byte[] loadConfiguredFile(String keystoreType, String keystorePath, char[] keystorePassword) throws BaseException {
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

    /**
     * Get the currently generated keystore password
     * 
     * @return the password as a char array
     */
    public char[] getCurrentKeystorePassword() {
        return currentKeystorePassword;
    }

    private void addBouncyCastleProvider() {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    private byte[] createKeystore(List<X509Certificate> certs, PEMParser privateKeyPEMparser, char[] privateKeyPassword) throws BaseException {
        try {
            Object readObject = privateKeyPEMparser.readObject();
            PrivateKeyInfo privateKeyInfo = null;
            if (readObject instanceof PKCS8EncryptedPrivateKeyInfo) {
                // throw exception if key is needed but no passphrase provided
                if (privateKeyPassword == null) {
                    throw new TechnicalException(CoffeeFaultType.OPERATION_FAILED, "passphrase is needed");
                }
                PKCS8EncryptedPrivateKeyInfo o = (PKCS8EncryptedPrivateKeyInfo) readObject;
                JceOpenSSLPKCS8DecryptorProviderBuilder builder = new JceOpenSSLPKCS8DecryptorProviderBuilder();
                privateKeyInfo = o.decryptPrivateKeyInfo(builder.build(privateKeyPassword));
            } else if (readObject instanceof PEMKeyPair) {
                PEMKeyPair pair = (PEMKeyPair) readObject;
                privateKeyInfo = pair.getPrivateKeyInfo();
            } else if (readObject instanceof PrivateKeyInfo) {
                privateKeyInfo = (PrivateKeyInfo) readObject;
            }

            PrivateKey privateKey = (new JcaPEMKeyConverter()).getPrivateKey(privateKeyInfo);

            // Put them into a PKCS12 keystore and write it to a byte[]
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                KeyStore keystore = KeyStore.getInstance(PKCS12);
                keystore.load(null);
                keystore.setKeyEntry(DEFAULT_ALIAS, privateKey, getCurrentKeystorePassword(), certs.toArray(new java.security.cert.Certificate[] {}));
                keystore.store(bos, getCurrentKeystorePassword());
                return bos.toByteArray();
            }
        } catch (Exception e) {
            throw new BusinessException(CoffeeFaultType.OPERATION_FAILED, e.getLocalizedMessage(), e);
        }
    }

    private PEMParser getPrivateKeyPEMParser(byte[] privateKeyData) {
        return new PEMParser(new InputStreamReader(new ByteArrayInputStream(privateKeyData)));
    }

    private PEMParser getPrivateKeyPEMParser(Path privateKeyPemFilePath) throws BaseException {
        try {
            return new PEMParser(Files.newBufferedReader(privateKeyPemFilePath));
        } catch (IOException e) {
            throw new TechnicalException(CoffeeFaultType.OPERATION_FAILED, e.getLocalizedMessage(), e);
        }
    }

    private List<X509Certificate> getX509Certificates(byte[] certificate) throws CertificateException, IOException {
        X509Certificate cert = null;
        List<X509Certificate> certs = new ArrayList<>();
        try {
            PEMParser pemParser = new PEMParser(new InputStreamReader(new ByteArrayInputStream(certificate)));
            while (true) {
                X509CertificateHolder certHolder = (X509CertificateHolder) pemParser.readObject();
                if (certHolder == null) {
                    break;
                }
                cert = new JcaX509CertificateConverter().getCertificate(certHolder);
                certs.add(cert);
            }
        } catch (IOException e) {
            CertificateFactory factory = CertificateFactory.getInstance(X_509);
            cert = (X509Certificate) factory.generateCertificate(new ByteArrayInputStream(certificate));
            certs.add(cert);
        }
        return certs;
    }

    private List<X509Certificate> getX509Certificates(Path certificatePath) throws CertificateException, IOException {
        X509Certificate cert = null;
        List<X509Certificate> certs = new ArrayList<>();
        try {
            PEMParser pemParser = new PEMParser(Files.newBufferedReader(certificatePath));
            while (true) {
                X509CertificateHolder certHolder = (X509CertificateHolder) pemParser.readObject();
                if (certHolder == null) {
                    break;
                }
                cert = new JcaX509CertificateConverter().getCertificate(certHolder);
                certs.add(cert);
            }
        } catch (IOException e) {
            CertificateFactory factory = CertificateFactory.getInstance(X_509);
            cert = (X509Certificate) factory.generateCertificate(Files.newInputStream(certificatePath));
            certs.add(cert);
        }
        return certs;
    }
}
