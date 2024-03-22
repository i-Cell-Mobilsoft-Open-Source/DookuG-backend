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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.pdfbox.io.IOUtils;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSTypedData;

/**
 * Wraps a InputStream into a CMSProcessable object for bouncy castle. It's a memory saving alternative to the
 * {@link org.bouncycastle.cms.CMSProcessableByteArray CMSProcessableByteArray} class.
 *
 * @author Thomas Chojecki
 * @since 0.0.1
 */
public class CMSProcessableInputStream implements CMSTypedData {
    private final InputStream in;
    private final ASN1ObjectIdentifier contentType;

    /**
     * constructor
     * 
     * @param is
     *            the input stream
     */
    public CMSProcessableInputStream(InputStream is) {
        this(new ASN1ObjectIdentifier(CMSObjectIdentifiers.data.getId()), is);
    }

    /**
     * constructor
     * 
     * @param type
     *            the {@link ASN1ObjectIdentifier}
     * @param is
     *            the input stream
     */
    public CMSProcessableInputStream(ASN1ObjectIdentifier type, InputStream is) {
        contentType = type;
        in = is;
    }

    @Override
    public Object getContent() {
        return in;
    }

    @Override
    public void write(OutputStream out) throws IOException, CMSException {
        // read the content only one time
        IOUtils.copy(in, out);
        in.close();
    }

    @Override
    public ASN1ObjectIdentifier getContentType() {
        return contentType;
    }
}
