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
package hu.icellmobilsoft.dookug.engine.handlebars;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Optional;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import com.github.jknack.handlebars.io.AbstractTemplateLoader;
import com.github.jknack.handlebars.io.StringTemplateSource;
import com.github.jknack.handlebars.io.TemplateSource;

import hu.icellmobilsoft.dookug.common.cdi.template.Template;
import hu.icellmobilsoft.dookug.common.cdi.template.TemplateContainer;

/**
 * Dookug implementation of handlebars template loader interface.
 * 
 * @author laszlo.padar
 * @since 0.1.0
 */
@Model
public class DookugHandlebarsTemplateLoader extends AbstractTemplateLoader {

    @Inject
    private TemplateContainer templateContainer;

    @Override
    public TemplateSource sourceAt(final String uri) throws IOException {
        Optional<Template> templateOptional = templateContainer.getTemplate(uri);
        if (templateOptional.isEmpty()) {
            String msg = MessageFormat.format("DookugHandlebarsTemplateLoader: template with name [{0}] not found!", uri);
            throw new IOException(msg);
        }
        return new StringTemplateSource(uri, new String(templateOptional.get().getTemplateContent(), "UTF-8"));
    }
}
