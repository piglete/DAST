package club.emergency.dictionary.model;

import club.map.core.mapper.ColumnTransient;
import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("by_b_dictionary_child")
public class ByBDictionaryChild extends RootObject {
    private String keyName;//键名 （查询用）

    private String alias;//别名

    private String dictionaryCode;//父外键code

    private String dictionaryName;//父字典中文名称

    private String iconUrl;//图标url

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDictionaryCode() {
        return dictionaryCode;
    }

    public void setDictionaryCode(String dictionaryCode) {
        this.dictionaryCode = dictionaryCode;
    }

    @ColumnTransient
    public String getDictionaryName() {
        return dictionaryName;
    }

    public void setDictionaryName(String dictionaryName) {
        this.dictionaryName = dictionaryName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", keyName=").append(keyName);
        sb.append(", alias=").append(alias);
        sb.append(", dictionaryCode=").append(dictionaryCode);
        sb.append(", iconUrl=").append(iconUrl);
        sb.append("]");
        return sb.toString();
    }
}