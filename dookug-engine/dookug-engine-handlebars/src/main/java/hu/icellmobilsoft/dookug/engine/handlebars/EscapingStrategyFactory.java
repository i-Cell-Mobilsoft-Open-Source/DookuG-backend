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

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import com.github.jknack.handlebars.EscapingStrategy;

import hu.icellmobilsoft.coffee.cdi.logger.AppLogger;
import hu.icellmobilsoft.coffee.cdi.logger.ThisLogger;

/**
 * Handlebars {@link EscapingStrategy} creator class
 *
 * @author laszlo.padar
 * @since 0.1.0
 */
@ApplicationScoped
public class EscapingStrategyFactory {

    @Inject
    @ThisLogger
    private AppLogger log;

    /**
     * Creates a Handlebars escaping strategy. {@link EscapingStrategy} interface contains its built-in implementation instances. This method finds
     * and returns them by field name. It returns the default strategy if filed name is not present.
     * 
     * @param strategyKeyOptional
     *            Optional field name of strategy.
     * @return Found strategy instance
     */
    public EscapingStrategy createEscapingStrategy(Optional<String> strategyKeyOptional) {
        if (strategyKeyOptional.isEmpty()) {
            return EscapingStrategy.DEF;
        }
        String strategyKey = strategyKeyOptional.get();
        Class<EscapingStrategy> clazz = EscapingStrategy.class;
        try {
            Field field = clazz.getDeclaredField(strategyKey);
            if (clazz.equals(field.getType())) {
                log.info("EscapingStrategy: [{0}]", strategyKey);
                return clazz.cast(field.get(field));
            } else {
                String msg = MessageFormat.format("Invalid field reference [{0}]!", strategyKey);
                throw new IllegalStateException(msg);
            }
        } catch (Exception e) {
            String msg = MessageFormat.format("Invalid strategy key [{0}]!", strategyKey);
            throw new IllegalStateException(msg, e);
        }
    }

}
