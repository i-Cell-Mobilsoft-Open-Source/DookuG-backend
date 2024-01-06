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
package hu.icellmobilsoft.dookug.common.cdi.template;

/**
 * Class for containing fields of template
 * 
 * @author laszlo.padar
 * @since 0.1.0
 */
public class Template {

    private String templateName;
    private byte[] templateContent;

    /**
     * empty constructor
     */
    public Template() {
    }

    /**
     * @return the templateName
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     * @param templateName
     *            the templateName to set
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    /**
     * @return the templateContent
     */
    public byte[] getTemplateContent() {
        return templateContent;
    }

    /**
     * @param templateContent
     *            the templateContent to set
     */
    public void setTemplateContent(byte[] templateContent) {
        this.templateContent = templateContent;
    }

    /**
     * constructor
     * 
     * @param templateName
     *            name of the template
     * @param templateContent
     *            content of the template
     */
    public Template(String templateName, byte[] templateContent) {
        super();
        this.templateName = templateName;
        this.templateContent = templateContent;
    }

}
