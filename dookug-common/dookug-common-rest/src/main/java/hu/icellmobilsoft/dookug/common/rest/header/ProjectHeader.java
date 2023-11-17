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
package hu.icellmobilsoft.dookug.common.rest.header;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.coffee.se.logging.Logger;

/**
 * Project header class
 * 
 * @author imre.scheffer
 *
 */
public class ProjectHeader implements IHttpHeaderConstant {

    private static final String HOST_PORT_SEPARATOR = ":";
    public static final String EMPTY_VALUE = "empty";
    private static final String FORWARDED_FOR_TAG = "for=";
    private static final int HOST_PORT_MAX_LENGTH = 15;

    private String sessionToken;
    private String refreshToken;
    private String host;
    private String source;
    private String language;
    private String forwarded;
    private String forwardedForHost;
    private String forwardedForPort;

    public static ProjectHeader readHeaders(HttpHeaders headers) {
        if (headers == null) {
            return null;
        }
        return readHeaders(headers.getRequestHeaders());
    }

    public static ProjectHeader readHeaders(MultivaluedMap<String, String> headerMap) {
        ProjectHeader projectHeader = new ProjectHeader();
        if (headerMap != null) {

            String host = getHeaderValue(headerMap, HEADER_HOST, false);
            if (StringUtils.isBlank(host)) {
                host = getHeaderValue(headerMap, HEADER_XHOST, false);
            }
            projectHeader.setHost(host);

            projectHeader.setLanguage(getHeaderValue(headerMap, HEADER_LANGUAGE, false));
            projectHeader.setSource(getHeaderValue(headerMap, HEADER_SOURCE, false));
            projectHeader.setForwarded(getHeaderValue(headerMap, HEADER_FORWARDED, false));
        }
        return projectHeader;
    }

    private void handleForwardedHeader(String headerValue) {
        String host = null;
        String port = null;
        String hostPort = headerValue;
        if (StringUtils.isNotBlank(headerValue) && StringUtils.contains(headerValue, FORWARDED_FOR_TAG)) {
            String[] forwarded = StringUtils.split(headerValue, ";");
            Optional<String> o = Arrays.stream(forwarded).filter(s -> StringUtils.startsWithIgnoreCase(StringUtils.trim(s), FORWARDED_FOR_TAG))
                    .findFirst();
            if (o.isPresent()) {
                hostPort = StringUtils.substringAfter(o.get(), FORWARDED_FOR_TAG);
            }
        }
        if (StringUtils.contains(hostPort, HOST_PORT_SEPARATOR)) {
            host = StringUtils.trim(StringUtils.substringBefore(hostPort, HOST_PORT_SEPARATOR));
            port = StringUtils.trim(StringUtils.substringAfter(hostPort, HOST_PORT_SEPARATOR));
        } else {
            host = StringUtils.trim(hostPort);
        }
        setForwardedForHost(StringUtils.defaultString(StringUtils.left(host, HOST_PORT_MAX_LENGTH), EMPTY_VALUE));
        setForwardedForPort(StringUtils.defaultString(StringUtils.left(port, HOST_PORT_MAX_LENGTH), EMPTY_VALUE));
    }

    public static String getHeaderValue(HttpHeaders headers, String key, boolean required) {
        if (headers == null || key == null) {
            return null;
        }
        return getHeaderValue(headers.getRequestHeaders(), key, required);
    }

    public static String getHeaderValue(MultivaluedMap<String, String> headerMap, String key, boolean required) {
        Logger log = Logger.getLogger(ProjectHeader.class);
        try {
            if (headerMap == null) {
                return null;
            }

            List<String> values = headerMap.get(key);
            if (values == null || values.isEmpty()) {
                String msg = "Request header doesnt contain (" + key + ") key";
                if (required) {
                    log.warn(msg);
                } else {
                    log.debug(msg);
                }
                return null;
            } else {
                return values.get(0);
            }
        } catch (Exception e) {
            log.warn("Error in getHeaderValue(" + key + ")", e);
            return null;
        }
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getForwarded() {
        return forwarded;
    }

    public void setForwarded(String forwarded) {
        this.forwarded = forwarded;
    }

    public String getForwardedForHost() {
        if (forwardedForHost == null && forwarded != null) {
            handleForwardedHeader(forwarded);
        }
        return forwardedForHost;
    }

    public void setForwardedForHost(String forwardedForHost) {
        this.forwardedForHost = forwardedForHost;
    }

    public String getForwardedForPort() {
        if (forwardedForPort == null && forwarded != null) {
            handleForwardedHeader(forwarded);
        }
        return forwardedForPort;
    }

    public void setForwardedForPort(String forwardedForPort) {
        this.forwardedForPort = forwardedForPort;
    }
}
