/**
 * Copyright 2006-2016 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.ezjs.generator.mybatis.codegen.mybatis3.xmlmapper.elements;

import me.ezjs.generator.mybatis.api.dom.xml.Attribute;
import me.ezjs.generator.mybatis.api.dom.xml.XmlElement;
import me.ezjs.generator.mybatis.api.dom.xml.TextElement;

/**
 *
 * @author Jeff Butler
 *
 */
public class CountByExampleElementGenerator extends AbstractXmlElementGenerator {

    public CountByExampleElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

        String fqjt = introspectedTable.getExampleType();

        answer.addAttribute(new Attribute(
                "id", introspectedTable.getCountByExampleStatementId())); //$NON-NLS-1$
        answer.addAttribute(new Attribute("parameterType", fqjt)); //$NON-NLS-1$
        answer.addAttribute(new Attribute("resultType", "java.lang.Integer")); //$NON-NLS-1$ //$NON-NLS-2$

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("select count(*) from "); //$NON-NLS-1$
        sb.append(introspectedTable
                .getAliasedFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));
        answer.addElement(getExampleIncludeElement());

        if (context.getPlugins().sqlMapCountByExampleElementGenerated(
                answer, introspectedTable)) {
            parentElement.addElement(answer);
        }
    }
}
