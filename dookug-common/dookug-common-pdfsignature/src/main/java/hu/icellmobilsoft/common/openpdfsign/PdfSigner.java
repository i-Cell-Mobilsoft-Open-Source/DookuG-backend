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

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.text.MessageFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureOptions;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;

import eu.europa.esig.dss.enumerations.CertificationPermission;
import eu.europa.esig.dss.enumerations.DigestAlgorithm;
import eu.europa.esig.dss.enumerations.SignatureLevel;
import eu.europa.esig.dss.model.DSSDocument;
import eu.europa.esig.dss.model.InMemoryDocument;
import eu.europa.esig.dss.model.SignatureValue;
import eu.europa.esig.dss.model.ToBeSigned;
import eu.europa.esig.dss.pades.PAdESSignatureParameters;
import eu.europa.esig.dss.pades.SignatureImageParameters;
import eu.europa.esig.dss.pades.signature.PAdESService;
import eu.europa.esig.dss.pdf.pdfbox.PdfBoxNativeObjectFactory;
import eu.europa.esig.dss.service.crl.OnlineCRLSource;
import eu.europa.esig.dss.service.ocsp.OnlineOCSPSource;
import eu.europa.esig.dss.service.tsp.OnlineTSPSource;
import eu.europa.esig.dss.spi.x509.CommonCertificateSource;
import eu.europa.esig.dss.spi.x509.CommonTrustedCertificateSource;
import eu.europa.esig.dss.spi.x509.aia.DefaultAIASource;
import eu.europa.esig.dss.spi.x509.tsp.CompositeTSPSource;
import eu.europa.esig.dss.spi.x509.tsp.TSPSource;
import eu.europa.esig.dss.token.JKSSignatureToken;
import eu.europa.esig.dss.token.KSPrivateKeyEntry;
import eu.europa.esig.dss.validation.CommonCertificateVerifier;
import hu.icellmobilsoft.coffee.cdi.logger.AppLogger;
import hu.icellmobilsoft.coffee.cdi.logger.ThisLogger;
import hu.icellmobilsoft.coffee.dto.exception.InvalidParameterException;
import hu.icellmobilsoft.coffee.dto.exception.TechnicalException;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.common.openpdfsign.dss.PdfBoxNativeTableObjectFactory;
import hu.icellmobilsoft.common.openpdfsign.types.PathDocument;
import hu.icellmobilsoft.dookug.api.dto.exception.enums.FaultType;
import hu.icellmobilsoft.dookug.engine.pdfbox.signing.types.SignatureProfileDto;

/**
 * opendf-sign pdf signer action
 * 
 * @author tamas.cserhati
 * @since 1.1.0
 */
@Model
public class PdfSigner {

    private static final String DOCUMENT_SIGNING_COMPLETE = "Document signing complete";

    private static final String SIGNATURE_PNG = "signature.png";

    @Inject
    @ThisLogger
    private AppLogger log;

    @Inject
    private KeystoreLoader keystoreLoader;

    // see PDRectangle
    private static final float POINTS_PER_INCH = 72;
    private static final float POINTS_PER_MM = 1 / (10 * 2.54f) * POINTS_PER_INCH;

    private static final String ESIGNED_BY = "e-signed by {0}";

    /**
     * Sign the pdf and save into a file
     * 
     * @param pdfFile
     *            the pdf file to sign
     * @param profile
     *            the configuration to override the server default configuration
     * @param outputFile
     *            the file represented by this {@link Path} will be saved with the content of the signed PDF document
     * @throws BaseException
     *             on error
     */
    public void signPdf(Path pdfFile, SignatureProfileDto profile, Path outputFile) throws BaseException {
        if (pdfFile == null || profile == null || outputFile == null) {
            throw new InvalidParameterException("Parameters cannot be empty");
        }
        DSSDocument signedDocument = signPdf(pdfFile, profile);
        log.debug(DOCUMENT_SIGNING_COMPLETE);
        try {
            signedDocument.save(outputFile.toAbsolutePath().toString());
        } catch (IOException e) {
            throw new TechnicalException(CoffeeFaultType.OPERATION_FAILED, e.getLocalizedMessage(), e);
        }
    }

    /**
     * Sign a PDF
     * 
     * @param originalPdfFile
     *            the PDF file to sign
     * @param profile
     *            signature configuration
     * @param outputStream
     *            the result as an {@link OutputStream}
     * @throws BaseException
     *             on error
     */
    public void signPdf(Path originalPdfFile, SignatureProfileDto profile, OutputStream outputStream) throws BaseException {
        if (originalPdfFile == null || profile == null || outputStream == null) {
            throw new InvalidParameterException("Parameters cannot be empty");
        }
        DSSDocument signedDocument = signPdf(originalPdfFile, profile);
        log.debug(DOCUMENT_SIGNING_COMPLETE);
        try {
            signedDocument.writeTo(outputStream);
        } catch (IOException e) {
            throw new TechnicalException(CoffeeFaultType.OPERATION_FAILED, e.getLocalizedMessage(), e);
        } finally {
            boolean result;
            try {
                result = Files.deleteIfExists(originalPdfFile);
                if (!result) {
                    log.warn("original PDF file [{0}] couldnt be removed!", originalPdfFile);
                }
            } catch (IOException e) {
                log.warn("original PDF file [{0}] couldnt be removed! [{1}]", originalPdfFile, e.getLocalizedMessage());
            }
        }
    }

    /**
     * Sign the pdf and return as stream
     * 
     * @param pdfFile
     *            the pdf file to sign
     * @param keyStore
     *            the keystore with the signer key (ideally with keyAlias: {@value KeystoreLoader#DEFAULT_ALIAS})
     * @param keyStorePassword
     *            the password of the keystore file
     * @param profile
     *            the configuration to override the server default configuration
     * @param binaryOutput
     *            the signed PDF document as an {@link OutputStream}
     * @throws BaseException
     *             on error
     */
    public void signPdf(Path pdfFile, byte[] keyStore, char[] keyStorePassword, SignatureProfileDto profile, OutputStream binaryOutput)
            throws BaseException {
        if (pdfFile == null || keyStore == null || keyStorePassword == null || profile == null || binaryOutput == null) {
            throw new InvalidParameterException("Parameters cannot be empty");
        }
        DSSDocument signedDocument = signPdf(pdfFile, profile);
        log.debug(DOCUMENT_SIGNING_COMPLETE);
        try {
            signedDocument.writeTo(binaryOutput);
        } catch (IOException e) {
            throw new TechnicalException(CoffeeFaultType.OPERATION_FAILED, e.getLocalizedMessage(), e);
        }
    }

    private DSSDocument signPdf(Path pdfFile, SignatureProfileDto profile) throws BaseException {

        boolean visibleSignature = profile.getDssPage() != 0;

        // load PDF file in DSSDocument format
        DSSDocument toSignDocument = new PathDocument(pdfFile);

        // load certificate and private key
        byte[] keystore = keystoreLoader
                .loadConfiguredFile(profile.getKeystoreType(), profile.getKeystore(), profile.getKeystorePassword().toCharArray());
        try (JKSSignatureToken signingToken = new JKSSignatureToken(
                keystore,
                // new KeyStore.PasswordProtection(keystoreLoader.getCurrentKeystorePassword()))) {
                new KeyStore.PasswordProtection(profile.getKeystorePassword().toCharArray()))) {
            log.debug("Keystore created for signing pdf [{0}] with profile [{1}]", pdfFile, profile.getProfileName());
            // PAdES parameters
            PAdESSignatureParameters signatureParameters = new PAdESSignatureParameters();
            // signatureParameters.bLevel().setSigningDate(new Date());
            String keyAlias = profile.getKeyAlias();
            if (signingToken.getKeys().get(0) instanceof KSPrivateKeyEntry ksPrivateKeyEntry) {
                keyAlias = ksPrivateKeyEntry.getAlias();
            }
            signatureParameters.setSigningCertificate(signingToken.getKey(keyAlias).getCertificate());
            signatureParameters.setCertificateChain(signingToken.getKey(keyAlias).getCertificateChain());

            if (profile.isDssUseLT()) {
                // extra signature space for the use of a timestamped signature
                signatureParameters.setSignatureLevel(SignatureLevel.PAdES_BASELINE_LT);
                signatureParameters.setContentSize((int) (SignatureOptions.DEFAULT_SIGNATURE_SIZE * 1.5));
            } else if (profile.isDssUseLTA()) {
                signatureParameters.setSignatureLevel(SignatureLevel.PAdES_BASELINE_LTA);
                signatureParameters.setContentSize((int) (SignatureOptions.DEFAULT_SIGNATURE_SIZE * 1.75));
            } else if (profile.isDssUseTimestamp() || CollectionUtils.isNotEmpty(profile.getDssTsaList())) {
                signatureParameters.setSignatureLevel(SignatureLevel.PAdES_BASELINE_T);
                signatureParameters.setContentSize((int) (SignatureOptions.DEFAULT_SIGNATURE_SIZE * 1.5));
            } else {
                signatureParameters.setSignatureLevel(SignatureLevel.PAdES_BASELINE_B);
            }
            signatureParameters.setPermission(CertificationPermission.NO_CHANGE_PERMITTED);

            // Create common certificate verifier
            CommonCertificateVerifier commonCertificateVerifier = new CommonCertificateVerifier();

            if (signatureParameters.getSignatureLevel() == SignatureLevel.PAdES_BASELINE_LT
                    || signatureParameters.getSignatureLevel() == SignatureLevel.PAdES_BASELINE_LTA) {
                // Capability to download resources from AIA
                commonCertificateVerifier.setAIASource(new DefaultAIASource());

                // Capability to request OCSP Responders
                commonCertificateVerifier.setOcspSource(new OnlineOCSPSource());

                // Capability to download CRL
                commonCertificateVerifier.setCrlSource(new OnlineCRLSource());

                // Still fetch revocation data for signing, even if a certificate chain is not trusted
                commonCertificateVerifier.setCheckRevocationForUntrustedChains(true);

                // Create an instance of a trusted certificate source
                CommonTrustedCertificateSource trustedCertSource = new CommonTrustedCertificateSource();

                // Import defaults
                // CommonCertificateSource commonCertificateSource = trustedCertificatesLoader.getDefaults();
                CommonCertificateSource commonCertificateSource = new CommonCertificateSource();

                // import the keystore as trusted
                trustedCertSource.importAsTrusted(commonCertificateSource);

                commonCertificateVerifier.addTrustedCertSources(trustedCertSource);
            }

            // Create PAdESService for signature
            PAdESService service = new PAdESService(commonCertificateVerifier);

            log.debug("Signature service initialized");

            // Initialize visual signature and configure
            if (visibleSignature) {
                SignatureImageParameters imageParameters = new SignatureImageParameters();
                TableSignatureFieldParameters fieldParameters = new TableSignatureFieldParameters();
                imageParameters.setFieldParameters(fieldParameters);

                try {
                    Optional<String> imageFile = profile.getDssImageFile();
                    if (imageFile.isPresent()) {
                        imageParameters.setImage(new InMemoryDocument(Files.readAllBytes(Paths.get(imageFile.get()))));
                    } else {
                        imageParameters.setImage(
                                new InMemoryDocument((IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream(SIGNATURE_PNG)))));
                    }

                    if (profile.getDssPage() < 0) {
                        try (PDDocument pdDocument = PDDocument.load(toSignDocument.openStream())) {
                            int pageCount = pdDocument.getNumberOfPages();
                            fieldParameters.setPage(pageCount + (1 + profile.getDssPage()));
                            log.debug("PDF [{0}] page count: [{1}]", pdfFile, pageCount);
                        }

                    } else {
                        fieldParameters.setPage(profile.getDssPage());
                    }
                } catch (IOException e) {
                    throw new TechnicalException(CoffeeFaultType.OPERATION_FAILED, e.getLocalizedMessage(), e);
                }
                fieldParameters.setOriginX(profile.getDssLeft() * POINTS_PER_MM * 10f);
                fieldParameters.setOriginY(profile.getDssTop() * POINTS_PER_MM * 10f);
                fieldParameters.setWidth(profile.getDssWidth() * POINTS_PER_MM * 10f);

                // Get the SignedInfo segment that need to be signed.
                DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of(profile.getDssTimezone()));

                if (profile.isDssUseTimestamp()) {
                    fieldParameters.setSignatureDate(formatter.format(signatureParameters.getSigningDate().toInstant()));
                }
                fieldParameters.setLabelHint(profile.getDssLabelHint());
                fieldParameters.setLabelSignee(profile.getDssLabelSignee());
                fieldParameters.setLabelTimestamp(profile.getDssLabelTimestamp());
                if (profile.isDssShowSignee()) {
                    X500Name x500Name = getCertificateSubject(signingToken, keyAlias);
                    fieldParameters.setEsignee(getESignee(x500Name));
                }

                if (profile.isDssUseHint()) {
                    fieldParameters.setHint(profile.getDssHintText());
                } else {
                    fieldParameters.setHint(null);
                }
                signatureParameters.setImageParameters(imageParameters);
                PdfBoxNativeObjectFactory pdfBoxNativeObjectFactory = new PdfBoxNativeTableObjectFactory();
                service.setPdfObjFactory(pdfBoxNativeObjectFactory);
                log.debug("Visible signature parameters set");
            }
            // https://gist.github.com/Manouchehri/fd754e402d98430243455713efada710
            // only use TSP source, if parameter is set
            // if it is set to an url, use this
            // otherwise, default
            if (profile.isDssUseLT() || profile.isDssUseLTA() || CollectionUtils.isNotEmpty(profile.getDssTsaList())) {
                CompositeTSPSource compositeTSPSource = new CompositeTSPSource();
                Map<String, TSPSource> tspSources = new HashMap<>();
                compositeTSPSource.setTspSources(tspSources);
                profile.getDssTsaList().stream().forEach(source -> tspSources.put(source, new OnlineTSPSource(source)));
                service.setTspSource(compositeTSPSource);
            }

            ToBeSigned dataToSign = service.getDataToSign(toSignDocument, signatureParameters);

            // This function obtains the signature value for signed information using the
            // private key and specified algorithm
            DigestAlgorithm digestAlgorithm = signatureParameters.getDigestAlgorithm();
            log.debug("Data to be signed loaded (document:[{0}] profile:[{1}])", pdfFile, profile.getProfileName());
            SignatureValue signatureValue = signingToken.sign(dataToSign, digestAlgorithm, signingToken.getKey(keyAlias));

            if (service.isValidSignatureValue(dataToSign, signatureValue, signingToken.getKey(profile.getKeyAlias()).getCertificate())) {
                log.debug("signature value is VALID (document:[{0}] profile:[{1}])", pdfFile, profile.getProfileName());
            }

            log.debug("Signature value calculated (document:[{0}] profile:[{1}])", pdfFile, profile.getProfileName());
            return service.signDocument(toSignDocument, signatureParameters, signatureValue);
        } finally {
            clear(keystore);
        }
    }

    private X500Name getCertificateSubject(JKSSignatureToken signingToken, String keyAlias) throws BaseException {
        try {
            return new JcaX509CertificateHolder(signingToken.getKey(keyAlias).getCertificate().getCertificate()).getSubject();
        } catch (Exception e) {
            throw new BaseException(FaultType.PDF_SIGN_FAILED, e.getLocalizedMessage(), e);
        }
    }

    private String getESignee(X500Name x500Name) {
        if (x500Name == null) {
            return null;
        }
        RDN cn = x500Name.getRDNs(BCStyle.CN)[0];
        return MessageFormat
                .format(ESIGNED_BY, IETFUtils.valueToString(cn.getFirst().getValue()).replace("\\#", "#").replace("\\;", ";").replace("\\,", ","));
    }

    /**
     * @param bytes
     */
    private static void clear(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return;
        }
        Arrays.fill(bytes, (byte) 0);
    }
}
