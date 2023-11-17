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
package hu.icellmobilsoft.dookug.common.system.rest.validation.catalog;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of URLStreamHandlerFactory, delegates every call to its members
 *
 * @author mark.petrenyi
 * @since 0.3.0
 */
public class UrlStreamHandlerDelegator implements URLStreamHandlerFactory {

    private List<URLStreamHandlerFactory> urlStreamHandlerFactories = new ArrayList<>();

    /**
     * Creates a new {@code URLStreamHandler} instance with the specified protocol.
     *
     * @param protocol the protocol ("{@code ftp}", "{@code http}", "{@code nntp}", etc.).
     * @return a {@code URLStreamHandler} for the specific protocol, or {@code
     * null} if this factory cannot create a handler for the specific protocol
     * @see URLStreamHandler
     */
    @Override
    public URLStreamHandler createURLStreamHandler(String protocol) {
        for (URLStreamHandlerFactory urlStreamHandlerFactory : urlStreamHandlerFactories) {
            URLStreamHandler urlStreamHandler = urlStreamHandlerFactory.createURLStreamHandler(protocol);
            if (urlStreamHandler != null) {
                return urlStreamHandler;
            }
        }
        return null;
    }

    public List<URLStreamHandlerFactory> getUrlStreamHandlerFactories() {
        return urlStreamHandlerFactories;
    }

    public void addUrlStreamHandlerFactory(URLStreamHandlerFactory urlStreamHandlerFactory) {
        this.urlStreamHandlerFactories.add(urlStreamHandlerFactory);
    }
}
