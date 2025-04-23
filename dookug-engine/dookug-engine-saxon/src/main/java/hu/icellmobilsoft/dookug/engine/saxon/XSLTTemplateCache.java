/*-
 * #%L
 * DookuG
 * %%
 * Copyright (C) 2023 - 2025 i-Cell Mobilsoft Zrt.
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
package hu.icellmobilsoft.dookug.engine.saxon;

import java.io.ByteArrayInputStream;
import java.text.MessageFormat;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.apache.commons.lang3.BooleanUtils;
import org.eclipse.microprofile.config.ConfigProvider;

import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.se.api.exception.TechnicalException;
import hu.icellmobilsoft.common.digitalsign.cache.AbstractCache;
import hu.icellmobilsoft.dookug.api.dto.constants.ConfigKeys;
import hu.icellmobilsoft.dookug.common.cdi.template.TemplateContainer;

/**
 * {@link Templates} cache for saxon
 * 
 * @author tamas.cserhati
 * @since 2.0.0
 */
@ApplicationScoped
public class XSLTTemplateCache extends AbstractCache<String, Templates> {

    @Inject
    private TemplateContainer templateContainer;

    @Override
    protected long getTtl() {
        return ConfigProvider.getConfig()
                .getOptionalValue(ConfigKeys.Cache.Xslt.TTL, Long.class)
                .orElse(Long.parseLong(ConfigKeys.Cache.Keystore.Defaults.TTL_IN_MINUTES));
    }

    @Override
    protected boolean isStatisticsEnabled() {
        return ConfigProvider.getConfig()
                .getOptionalValue(ConfigKeys.Cache.Xslt.ENABLESTATISTIC, Boolean.class)
                .orElse(BooleanUtils.toBooleanObject(ConfigKeys.Cache.Keystore.Defaults.ENABLESTATISTIC));

    }

    @Override
    public boolean isCacheEnabled() {
        return ConfigProvider.getConfig()
                .getOptionalValue(ConfigKeys.Cache.Xslt.ENABLED, Boolean.class)
                .orElse(BooleanUtils.toBooleanObject(ConfigKeys.Cache.Keystore.Defaults.ENABLED));
    }

    @Override
    protected Templates load(String key) throws BaseException {
        Source xsltSource = new StreamSource(new ByteArrayInputStream(templateContainer.getCompiledResultAsBytes()));
        TransformerFactory factory = newTransformerFactory();
        try {
            return factory.newTemplates(xsltSource);
        } catch (TransformerConfigurationException e) {
            throw new TechnicalException(
                    CoffeeFaultType.OPERATION_FAILED,
                    MessageFormat.format("XSLT FO transformation failed with error: [{0}]", e.getLocalizedMessage()),
                    e);
        }
    }

    private TransformerFactory newTransformerFactory() {
        TransformerFactory factory = TransformerFactory.newInstance();
        factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
        //
        // factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        // Unfortunately, this generates an error: 'XTSE0010: xsl:result-document is disabled when extension functions are disabled'
        //
        return factory;
    }

}
