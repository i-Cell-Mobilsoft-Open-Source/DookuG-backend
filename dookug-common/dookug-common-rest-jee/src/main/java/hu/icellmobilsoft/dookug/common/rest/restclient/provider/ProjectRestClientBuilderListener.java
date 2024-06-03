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
package hu.icellmobilsoft.dookug.common.rest.restclient.provider;

import jakarta.enterprise.inject.spi.CDI;

import org.eclipse.microprofile.rest.client.RestClientBuilder;

import hu.icellmobilsoft.coffee.module.mp.restclient.provider.DefaultRestClientBuilderListener;

/**
 * REST client listener on project level
 * 
 * @see DefaultRestClientBuilderListener
 * 
 * @author imre.scheffer
 *
 */
public class ProjectRestClientBuilderListener extends DefaultRestClientBuilderListener {

    @Override
    public void onNewBuilder(RestClientBuilder builder) {
        super.onNewBuilder(builder);
        CDI<Object> cdi = CDI.current();
        builder.register(cdi.select(ProjectSettingClientRequestFilter.class).get());
    }
}
