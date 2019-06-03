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
package me.ezjs.generator.mybatis.plugins;

import java.util.List;
import java.util.StringTokenizer;

import me.ezjs.generator.mybatis.api.IntrospectedTable;
import me.ezjs.generator.mybatis.api.PluginAdapter;

/**
 * This plugin can be used to specify columns that act as a primary key, even if
 * they are not strictly defined as primary keys in the database.
 *
 * To use the plugin, add a property to the table configuration specifying a
 * comma delimited list of column names to use as a primary key:
 *
 * <br><br>
 * &lt;property name="virtualKeyColumns" value="ID1,ID2"&gt;
 *
 * @author Jeff Butler
 *
 */
public class VirtualPrimaryKeyPlugin extends PluginAdapter {

    /* (non-Javadoc)
     * @see Plugin#validate(java.util.List)
     */
    public boolean validate(List<String> warnings) {
        return true;
    }

    /* (non-Javadoc)
     * @see PluginAdapter#initialized(IntrospectedTable)
     */
    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        String virtualKey = introspectedTable.getTableConfiguration()
                .getProperty("virtualKeyColumns"); //$NON-NLS-1$

        if (virtualKey != null) {
            StringTokenizer st = new StringTokenizer(virtualKey, ", ", false); //$NON-NLS-1$
            while (st.hasMoreTokens()) {
                String column = st.nextToken();
                introspectedTable.addPrimaryKeyColumn(column);
            }
        }
    }
}
