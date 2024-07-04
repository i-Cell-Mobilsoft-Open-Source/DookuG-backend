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
package hu.icellmobilsoft.common.openpdfsign.types;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import eu.europa.esig.dss.enumerations.MimeType;
import eu.europa.esig.dss.model.CommonDocument;
import eu.europa.esig.dss.model.DSSException;

/**
 * PathDocument type for DSS signing
 * 
 * @author tamas.cserhati
 * @since 1.1.0
 */
public class PathDocument extends CommonDocument {

    private static final long serialVersionUID = 1L;
    /**
     * the Path of the document to sign
     */
    private final Path path;

    /**
     * Create a PathDocument
     *
     * @param path
     *            {@code Path}
     */
    public PathDocument(final Path path) {
        if (path == null) {
            throw new NullPointerException();
        }
        if (!Files.exists(path)) {
            throw new DSSException("File Not Found: " + path.toAbsolutePath());
        }
        this.path = path;
        this.name = path.getFileName().toString();
        this.mimeType = MimeType.fromFileName(name);
    }

    @Override
    public InputStream openStream() throws DSSException {
        try {
            return Files.newInputStream(path);
        } catch (IOException e) {
            throw new DSSException(e);
        }
    }

    /**
     * @return true if file exists
     */
    public boolean exists() {
        return Files.exists(path);
    }

    /**
     * @return the parentFile
     */
    public File getParentFile() {
        return path.getParent().toFile();
    }

}
