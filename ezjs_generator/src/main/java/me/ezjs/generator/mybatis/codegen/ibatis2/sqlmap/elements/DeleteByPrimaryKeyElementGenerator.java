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
package me.ezjs.generator.mybatis.codegen.ibatis2.sqlmap.elements;

import me.ezjs.generator.mybatis.api.IntrospectedColumn;
import me.ezjs.generator.mybatis.api.dom.xml.Attribute;
import me.ezjs.generator.mybatis.codegen.ibatis2.Ibatis2FormattingUtilities;
import me.ezjs.generator.mybatis.api.dom.xml.TextElement;
import me.ezjs.generator.mybatis.api.dom.xml.XmlElement;

/**
 *
 * @author Jeff Butler
 *
 */
public class DeleteByPrimaryKeyElementGenerator extends
        AbstractXmlElementGenerator {

    public DeleteByPrimaryKeyElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("delete"); //$NON-NLS-1$

        answer.addAttribute(new Attribute(
                "id", introspectedTable.getDeleteByPrimaryKeyStatementId())); //$NON-NLS-1$
        String parameterClass;
        if (introspectedTable.getRules().generatePrimaryKeyClass()) {
            parameterClass = introspectedTable.getPrimaryKeyType();
        } else {
            parameterClass = introspectedTable.getBaseRecordType();
        }
        answer.addAttribute(new Attribute("parameterClass", //$NON-NLS-1$
                parameterClass));

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("delete from "); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));

        boolean and = false;
        for (IntrospectedColumn introspectedColumn : introspectedTable
                .getPrimaryKeyColumns()) {
            sb.setLength(0);
            if (and) {
                sb.append("  and "); //$NON-NLS-1$
            } else {
                sb.append("where "); //$NON-NLS-1$
                and = true;
            }

            sb.append(Ibatis2FormattingUtilities
                    .getEscapedColumnName(introspectedColumn));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(Ibatis2FormattingUtilities
                    .getParameterClause(introspectedColumn));
            answer.addElement(new TextElement(sb.toString()));
        }

        if (context.getPlugins()
                .sqlMapDeleteByPrimaryKeyElementGenerated(answer,
                        introspectedTable)) {
            parentElement.addElement(answer);
        }
    }
}
