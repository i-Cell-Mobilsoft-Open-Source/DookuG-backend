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

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.helper.StringHelpers;

import hu.icellmobilsoft.coffee.cdi.logger.AppLogger;
import hu.icellmobilsoft.coffee.cdi.logger.ThisLogger;
import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.coffee.dto.exception.TechnicalException;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.dookug.api.dto.constants.ConfigKeys;
import hu.icellmobilsoft.dookug.engine.handlebars.helper.DookugHelpers;

/**
 * Handlebars - Helper handler action
 *
 * @author laszlo.padar
 * @since 0.1.0
 */
@ApplicationScoped
public class HelperRegister {

    private static final String JS_EXTENSION = ".js";

    @Inject
    @ThisLogger
    private AppLogger log;

    @Inject
    @ConfigProperty(name = ConfigKeys.Handlebars.Helper.DOOKUG_SERVICE_ENGINE_HANDLEBARS_HELPER_JAVASCRIPT_DIRECTORY)
    private String javascriptDirectoryPath;

    /**
     * * Searches all Helpers available for application and register them into {@link Handlebars} instance.
     *
     * @param handlebars
     *            {@link Handlebars} instance
     * @throws BaseException
     *             In case of fault
     */
    public void findAndRegisterHelpers(Handlebars handlebars) throws BaseException {
        handlebars.registerHelpers(DookugHelpers.class);
        handlebars.registerHelpers(StringHelpers.class);
    }

    /**
     * Unusable until Jknack solve Nashorn javascript engine problem (DKG-97)
     *
     * @param handlebars
     *            {@link Handlebars} instance
     * @throws BaseException
     *             In case of fault
     */
    private void registerJavascriptHelpers(Handlebars handlebars) throws BaseException {
        List<File> files = findJavascriptsInDirectory(javascriptDirectoryPath);
        for (File file : files) {
            try {
                handlebars.registerHelpers(file);
                log.info("Helper registered: [0]", file.getName());
            } catch (Exception e) {
                String msg = MessageFormat.format("Helper registration failed! [{0}]", file.getName());
                throw new TechnicalException(CoffeeFaultType.OPERATION_FAILED, msg, e);
            }
        }
    }

    private List<File> findJavascriptsInDirectory(String dirPath) throws BaseException {
        List<File> list = new ArrayList<>();
        File classDir = new File(dirPath);
        if (!classDir.exists() || !classDir.isDirectory()) {
            String msg = MessageFormat.format("Path of Javascript Helpers [{0}] must be an existing directory!", dirPath);
            throw new TechnicalException(CoffeeFaultType.OPERATION_FAILED, msg);
        }
        File[] classFiles = classDir.listFiles();
        for (File file : classFiles) {
            if (file.isFile() && file.getName().endsWith(JS_EXTENSION)) {
                list.add(file);
            }
        }
        return list;
    }
}
