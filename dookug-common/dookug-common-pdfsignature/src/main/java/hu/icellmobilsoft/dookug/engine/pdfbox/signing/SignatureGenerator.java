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
import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.coffee.dto.exception.TechnicalException;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.dookug.common.cdi.sign.DigitalSigningDto;
import hu.icellmobilsoft.dookug.engine.pdfbox.signing.types.CMSPrivateKey;
import hu.icellmobilsoft.dookug.engine.pdfbox.signing.types.CMSProcessableInputStream;
import hu.icellmobilsoft.dookug.engine.pdfbox.signing.types.SignatureProfile;

/**
 * PDF signature generator class
 * 
 * @author tamas.cserhati
 * @since 0.1.0
 */
@Model
public class SignatureGenerator implements SignatureInterface {

    private static final String SHA256_WITH_RSA = "SHA256WithRSA";
    private static final String TIMEZONE_UTC = "UTC";

    @Inject
    @ThisLogger
    private AppLogger log;

    @Inject
    private CMSPrivateKeyLoader privateKeyLoader;

    @Inject
    private SignatureProfileLoader signatureProfileLoader;

    private Path tempFile;
    private OutputStream tempFileStream;
    private PDDocument pdDocument;
    private SignatureProfile signatureProfile;

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
            // letrehozzuk a temporalis fajlt az alairando pdf szamara
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
     * Add the digital signature in case it needed. Doesnt free up resources!
     * 
     * @param signedPdfOutputStream
     *            the result pdf with the signature
     * @param digitalSigningDto
     *            the signature details
     * @throws BaseException
     *             on error
     */
    public void addDigitalSignatureIfNeeded(OutputStream signedPdfOutputStream, DigitalSigningDto digitalSigningDto) throws BaseException {
        if (digitalSigningDto != null) {
            pdDocument = null;
            try {
                // a tempFileStream tartalmat - ami a legeneralt alairatlan pdf - kiirjuk egy temporalis fajlba
                tempFileStream.flush();
                IOUtils.closeQuietly(tempFileStream);
                // ...es alairjuk
                pdDocument = PDDocument.load(tempFile.toFile());
                signDetached(pdDocument, signedPdfOutputStream, digitalSigningDto);
            } catch (IOException e) {
                throw new TechnicalException(
                        CoffeeFaultType.OPERATION_FAILED,
                        MessageFormat.format("An error occurred while sign PDF file with Pdfbox: [{0}]", e.getLocalizedMessage()),
                        e);
            }
        }
    }

    /**
     * Free up resources
     */
    public void closeStreams() {
        IOUtils.closeQuietly(tempFileStream);
        if (pdDocument != null) {
            IOUtils.closeQuietly(pdDocument);
        }
        if (tempFile != null && tempFile.toFile() != null) {
            try {
                // ha nem kell tobbe toroljuk le, ne varjunk a JVM exitre
                Files.delete(tempFile);
            } catch (IOException e) {
                log.warn("Cannot delete the temporaly pdf file: [{0}]", tempFile);
            }
        }
    }

    /**
     * Signs the given PDF file.
     * 
     * @param document
     *            in-memory representation of the PDF document
     * @param output
     *            signed pdf as output stream
     * @param digitalSigningDto
     *            signature parameters
     * @throws IOException
     *             on error
     */
    private void signDetached(PDDocument document, OutputStream output, DigitalSigningDto digitalSigningDto) throws BaseException, IOException {
        signatureProfile = signatureProfileLoader.loadSignatureProfile(digitalSigningDto.getSignatureProfile());
        document.addSignature(createPDSignature(digitalSigningDto), this, createSignatureOptions());
        // write incremental (only for signing purpose)
        document.saveIncremental(output);
        output.flush();
        output.close();
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
            ContentSigner shaSigner = new JcaContentSignerBuilder(SHA256_WITH_RSA).build(cmsPrivateKey.getPrivateKey());

            gen.addSignerInfoGenerator(new JcaSignerInfoGeneratorBuilder(new JcaDigestCalculatorProviderBuilder().build()).build(shaSigner, cert));
            gen.addCertificates(new JcaCertStore(Arrays.asList(cmsPrivateKey.getCertificateChain())));
            CMSProcessableInputStream msg = new CMSProcessableInputStream(content);
            CMSSignedData signedData = gen.generate(msg, false);
            // ha timestamp validationt is akarunk:
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

    private PDSignature createPDSignature(DigitalSigningDto digitalSigningDto) {
        PDSignature pdSignature = new PDSignature();
        pdSignature.setFilter(PDSignature.FILTER_ADOBE_PPKLITE);
        pdSignature.setSubFilter(PDSignature.SUBFILTER_ADBE_PKCS7_DETACHED);
        pdSignature.setName(getSignatureName(digitalSigningDto));
        pdSignature.setReason(getSignatureReason(digitalSigningDto));
        pdSignature.setSignDate(Calendar.getInstance(TimeZone.getTimeZone(TIMEZONE_UTC)));
        return pdSignature;
    }

    private String getSignatureName(DigitalSigningDto digitalSigningDto) {
        if (digitalSigningDto == null || StringUtils.isBlank(digitalSigningDto.getSignatureName())) {
            return signatureProfile.getName();
        }
        return digitalSigningDto.getSignatureName();
    }

    private String getSignatureReason(DigitalSigningDto digitalSigningDto) {
        if (digitalSigningDto == null || StringUtils.isBlank(digitalSigningDto.getSignatureReason())) {
            return signatureProfile.getReason();
        }
        return digitalSigningDto.getSignatureReason();
    }

    private SignatureOptions createSignatureOptions() {
        SignatureOptions signatureOptions = new SignatureOptions();
        // Size can vary, but should be enough for purpose.
        signatureOptions.setPreferredSignatureSize(SignatureOptions.DEFAULT_SIGNATURE_SIZE * 2);
        return signatureOptions;
    }
}
