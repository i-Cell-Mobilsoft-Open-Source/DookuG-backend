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

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.TimeZone;

import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureInterface;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureOptions;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;

import hu.icellmobilsoft.coffee.cdi.logger.AppLogger;
import hu.icellmobilsoft.coffee.cdi.logger.ThisLogger;
import hu.icellmobilsoft.coffee.cdi.trace.annotation.Traced;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.se.api.exception.TechnicalException;
import hu.icellmobilsoft.common.openpdfsign.PdfSigner;
import hu.icellmobilsoft.dookug.engine.pdfbox.signing.types.CMSPrivateKey;
import hu.icellmobilsoft.dookug.engine.pdfbox.signing.types.CMSProcessableInputStream;
import hu.icellmobilsoft.dookug.engine.pdfbox.signing.types.SignatureProfileDto;

/**
 * PDF signature generator class
 * 
 * @author tamas.cserhati
 * @since 0.1.0
 */
@Model
public class SignatureGenerator implements SignatureInterface {

    private static final String TIMEZONE_UTC = "UTC";

    @Inject
    @ThisLogger
    private AppLogger log;

    @Inject
    private CMSPrivateKeyLoader privateKeyLoader;

    @Inject
    private SignatureProfileLoader signatureProfileLoader;

    @Inject
    private PdfSigner pdfSigner;

    private Path tempFile;
    private OutputStream tempFileStream;
    private PDDocument pdDocument;
    private SignatureProfileDto signatureProfile;

    /**
     * Return a temporal output stream where the generator engine the original/unsigned PDF file is able to write to. This stream will be used for the
     * pdf signing later and must be closed by the developer with {@link SignatureGenerator#closeStreams()}!
     * 
     * @return the output stream for the original pdf
     * @throws BaseException
     *             on error
     */
    public OutputStream getOutputStreamForUnsignedPdf() throws BaseException {
        try {
            // creating temporal file for the signing
            tempFile = Files.createTempFile(null, null);
            tempFileStream = new BufferedOutputStream(Files.newOutputStream(tempFile));
            return tempFileStream;
        } catch (IOException e) {
            throw new TechnicalException(
                    CoffeeFaultType.OPERATION_FAILED,
                    MessageFormat.format("Cannot create temp file for unsigned PDF output: [{0}]", e.getLocalizedMessage()),
                    e);
        }
    }

    /**
     * @return a {@link Path} representation of the unsigned PDF temp file
     * @throws BaseException
     *             on error
     */
    /*
     * public Path getPathForUnsignedPdf() throws BaseException { try { // creating temporal file for the signing return Files.createTempFile(null,
     * null); } catch (IOException e) { throw new TechnicalException( CoffeeFaultType.OPERATION_FAILED,
     * MessageFormat.format("Cannot create temp file for unsigned PDF output: [{0}]", e.getLocalizedMessage()), e); } }
     */

    /**
     * Add the digital signature in case it needed. Doesnt free up resources!
     * 
     * @param signedPdfOutputStream
     *            the result pdf with the signature
     * @param digitalSignatureProfile
     *            the signature profile name
     * @throws BaseException
     *             on error
     */
    public void addDigitalSignatureIfNeeded(OutputStream signedPdfOutputStream, String digitalSignatureProfile) throws BaseException {
        if (StringUtils.isNotBlank(digitalSignatureProfile)) {
            signatureProfile = signatureProfileLoader.getSignatureProfile(digitalSignatureProfile);
            // content of tempFileStream (unsigned pdf) is written to temporal file
            flushAndCloseTempFileStream();
            if (signatureProfile.isUseEuDssSig()) {
                addDssESignature(signedPdfOutputStream, signatureProfile);
            } else {
                addPdfBoxDetachedSignature(signedPdfOutputStream, signatureProfile.getName(), signatureProfile.getReason());
            }
        }
    }

    /**
     * Add the detached digital signature with pdf box! Should be private but we need trace
     * 
     * @param signedPdfOutputStream
     *            the result pdf with the signature
     * @param name
     *            the signature name
     * @param reason
     *            the signature reason
     * @throws BaseException
     *             on error
     */
    @Traced(component = "sign-pdfbox-detached", kind = "INTERNAL")
    public void addPdfBoxDetachedSignature(OutputStream signedPdfOutputStream, String name, String reason) throws BaseException {
        signDetached(signedPdfOutputStream, name, reason);
    }

    /**
     * Add the digital signature with use of eu dss esig library. Doesnt free up resources! Should be private but we need trace
     * 
     * @param signedPdfOutputStream
     *            the result pdf with the signature
     * @param signatureProfile
     *            the signature configuration for given profile
     * @throws BaseException
     *             on error
     */
    @Traced(component = "sign-dss-esig", kind = "INTERNAL")
    public void addDssESignature(OutputStream signedPdfOutputStream, SignatureProfileDto signatureProfile) throws BaseException {
        pdfSigner.signPdf(tempFile, signatureProfile, signedPdfOutputStream);
    }

    /**
     * Free up resources from outside this class
     */
    public void closeStreams() {
        IOUtils.closeQuietly(tempFileStream);
        if (pdDocument != null) {
            IOUtils.closeQuietly(pdDocument);
        }
        if (tempFile != null && tempFile.toFile() != null) {
            try {
                // dont wait for JVM exit
                Files.delete(tempFile);
            } catch (IOException e) {
                log.warn("Cannot delete the temporaly PDF file: [{0}]", tempFile);
            }
        }
    }

    private void flushAndCloseTempFileStream() throws BaseException {
        try {
            tempFileStream.flush();
            IOUtils.closeQuietly(tempFileStream);
        } catch (IOException e) {
            throw new TechnicalException(
                    CoffeeFaultType.OPERATION_FAILED,
                    MessageFormat.format("Cannot write temporaly PDF file: [{0}]", e.getLocalizedMessage()),
                    e);
        }
    }

    /**
     * Signs the given PDF file.
     * 
     * @param output
     *            signed pdf as output stream
     * @param name
     *            signature name
     * @param reason
     *            signature reason
     * @throws IOException
     *             if any error occurs
     */
    private void signDetached(OutputStream output, String name, String reason) throws BaseException {
        try {
            pdDocument = PDDocument.load(tempFile.toFile());
            pdDocument.addSignature(createPDSignature(name, reason), this, createSignatureOptions());
            // write incremental (only for signing purpose)
            pdDocument.saveIncremental(output);
            output.flush();
            output.close();
        } catch (IOException e) {
            throw new TechnicalException(
                    CoffeeFaultType.OPERATION_FAILED,
                    MessageFormat.format("An error occurred while sign PDF file with Pdfbox: [{0}]", e.getLocalizedMessage()),
                    e);
        } finally {

        }
    }

    /**
     * SignatureInterface implementation. This should be private but it cannot be done because of the interface declaration
     * <p>
     * This method will be called from inside of the pdfbox and create the PKCS #7 signature. The given InputStream contains the bytes that are given
     * by the byte range.
     * <p>
     * This method is for internal use only.
     * <p>
     * Use your favorite cryptographic library to implement PKCS #7 signature creation. If you want to create the hash and the signature separately
     * (e.g. to transfer only the hash to an external application), read <a href="https://stackoverflow.com/questions/41767351">this answer</a> or
     * <a href="https://stackoverflow.com/questions/56867465">this answer</a>.
     *
     * @throws IOException
     *             on error
     */
    @Override
    public byte[] sign(InputStream content) throws IOException {
        try {
            CMSPrivateKey cmsPrivateKey = privateKeyLoader.privateKeyResolver(signatureProfile);
            CMSSignedDataGenerator gen = new CMSSignedDataGenerator();
            X509Certificate cert = (X509Certificate) cmsPrivateKey.getCertificateChain()[0];
            ContentSigner shaSigner = new JcaContentSignerBuilder(signatureProfile.getPdfBoxSignatureAlgorithm())
                    .build(cmsPrivateKey.getPrivateKey());
            gen.addSignerInfoGenerator(new JcaSignerInfoGeneratorBuilder(new JcaDigestCalculatorProviderBuilder().build()).build(shaSigner, cert));
            gen.addCertificates(new JcaCertStore(Arrays.asList(cmsPrivateKey.getCertificateChain())));
            CMSProcessableInputStream msg = new CMSProcessableInputStream(content);
            CMSSignedData signedData = gen.generate(msg, false);
            // if we need timestamp validation
            // https://svn.apache.org/repos/asf/pdfbox/trunk/examples/src/main/java/org/apache/pdfbox/examples/signature/
            //
            // if (tsaUrl != null && tsaUrl.length() > 0) {
            // ValidationTimeStamp validation = new ValidationTimeStamp(tsaUrl);
            // signedData = validation.addSignedTimeStamp(signedData);
            // }
            return signedData.getEncoded();
        } catch (BaseException | GeneralSecurityException | CMSException | OperatorCreationException e) {
            throw new IOException(e);
        }
    }

    private PDSignature createPDSignature(String name, String reason) {
        PDSignature pdSignature = new PDSignature();
        pdSignature.setFilter(PDSignature.FILTER_ADOBE_PPKLITE);
        pdSignature.setSubFilter(PDSignature.SUBFILTER_ADBE_PKCS7_DETACHED);
        pdSignature.setName(name);
        pdSignature.setReason(reason);
        pdSignature.setSignDate(Calendar.getInstance(TimeZone.getTimeZone(TIMEZONE_UTC)));
        return pdSignature;
    }

    private SignatureOptions createSignatureOptions() {
        SignatureOptions signatureOptions = new SignatureOptions();
        // Size can vary, but should be enough for purpose.
        signatureOptions.setPreferredSignatureSize(SignatureOptions.DEFAULT_SIGNATURE_SIZE * 2);
        return signatureOptions;
    }
}
