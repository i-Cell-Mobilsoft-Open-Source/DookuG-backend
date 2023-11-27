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
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.EscapingStrategy;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import hu.icellmobilsoft.coffee.cdi.logger.AppLogger;
import hu.icellmobilsoft.coffee.cdi.logger.ThisLogger;
import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.coffee.dto.exception.BusinessException;
import hu.icellmobilsoft.coffee.dto.exception.TechnicalException;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.coffee.tool.gson.JsonUtil;
import hu.icellmobilsoft.dookug.api.dto.constants.ConfigKeys;
import hu.icellmobilsoft.dookug.common.cdi.TemplateCompilerQualifier;
import hu.icellmobilsoft.dookug.common.cdi.constants.QualifierConstants;
import hu.icellmobilsoft.dookug.common.cdi.template.ITemplateCompiler;
import hu.icellmobilsoft.dookug.common.cdi.template.TemplateContainer;

/**
 * Handlebars implementation for value replacer
 *
 * @author imre.scheffer
 * @since 0.1.0
 */
@TemplateCompilerQualifier(QualifierConstants.TemplateCompilerType.HANDLEBARS)
@ApplicationScoped
public class HandlebarsTemplateCompiler implements ITemplateCompiler {

    @Inject
    @ThisLogger
    private AppLogger log;

    @Inject
    private TemplateContainer templateContainer;

    @Inject
    private DookugHandlebarsTemplateLoader templateLoader;

    @Inject
    private HelperRegister helperRegister;

    @Inject
    private EscapingStrategyFactory escapingStrategyFactory;

    private Handlebars handlebars;

    @Inject
    @ConfigProperty(name = ConfigKeys.Handlebars.EscapingStrategy.DOOKUG_SERVICE_ENGINE_HANDLEBARS_ESCAPINGSTRATEGY)
    private Optional<String> strategyKeyOptional;

    @Override
    public void compile(Map<String, String> parameters) throws BaseException {
        Template compiledTemplate = compile();
        if (parameters != null && !parameters.isEmpty()) {

            try {
                templateContainer.setCompiledResult(compiledTemplate.apply(parameters));
            } catch (IOException e) {
                throw new TechnicalException(CoffeeFaultType.OPERATION_FAILED, MessageFormat.format(
                        "Handlebars parameters map [{0}] apply failed with error: [{1}]", StringUtils.join(parameters), e.getLocalizedMessage()), e);
            }
        } else {
            try {
                templateContainer.setCompiledResult(compiledTemplate.apply(getEmptyContext()));
            } catch (IOException e) {
                throw new TechnicalException(CoffeeFaultType.OPERATION_FAILED, MessageFormat.format(
                        "Handlebars parameters map [{0}] apply failed with error: [{1}]", StringUtils.join(parameters), e.getLocalizedMessage()), e);
            }

        }

    }

    @Override
    public void compile(byte[] parameterData) throws BaseException {
        Template compiledTemplate = compile();

        Context context;
        if (parameterData != null) {
            String parametersString = new String(parameterData, StandardCharsets.UTF_8);
            Type type = TypeToken.get(Map.class).getType();
            Gson gson = new Gson();
            Object obj = gson.fromJson(parametersString, type);
            context = Context.newBuilder(obj).build();
        } else {
            context = getEmptyContext();
        }
        try {
            templateContainer.setCompiledResult(compiledTemplate.apply(context));
        } catch (IOException e) {
            String msg;
            if (parameterData != null) {
                msg = MessageFormat.format("Handlebars parameterData [{0}] apply failed with error: [{1}]",
                        new String(parameterData, StandardCharsets.UTF_8), e.getLocalizedMessage());
            } else {
                msg = MessageFormat.format("Handlebars empty parameterData apply failed with error: [{0}]", e.getLocalizedMessage());
            }
            throw new TechnicalException(CoffeeFaultType.OPERATION_FAILED, msg, e);
        }
    }

    @PostConstruct
    public void init() throws BaseException {
        EscapingStrategy escapingStrategy = escapingStrategyFactory.createEscapingStrategy(strategyKeyOptional);
        handlebars = new Handlebars(templateLoader).with(escapingStrategy);
        helperRegister.findAndRegisterHelpers(handlebars);
    }

    private Template compile() throws BaseException {
        String initTemplateName = templateContainer.getInitTemplateName()
                .orElseThrow(() -> new BusinessException(CoffeeFaultType.INVALID_INPUT, "Initial template not found!"));

        log.debug("Handlebars starting compile [{0}] templates, main template name [{1}]...", templateContainer.length(), initTemplateName);

        try {
            Template compiledTemplate = handlebars.compile(initTemplateName);
            log.debug("...Handlebars compile done.");
            return compiledTemplate;
        } catch (IOException e) {
            throw new TechnicalException(CoffeeFaultType.OPERATION_FAILED,
                    MessageFormat.format("Handlebars templates compile failed with error: [{0}]", e.getLocalizedMessage()), e);
        }
    }

    private Context getEmptyContext() {
        Object obj = JsonUtil.toObject("{}", Map.class);
        return Context.newBuilder(obj).build();
    }
}
