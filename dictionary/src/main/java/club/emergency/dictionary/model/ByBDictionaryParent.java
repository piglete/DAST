package club.emergency.dictionary.model;

import club.map.base.util.AppTreeNodeInterface;
import club.map.core.mapper.ColumnTransient;
import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("by_b_dictionary_parent")
public class ByBDictionaryParent extends RootObject implements AppTreeNodeInterface {
    private String keyName;//字典键（查询用）

    private String alias;//别名

    private String description;//描述

    private Integer parentId;//父ID

    private String code;//父字典code

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", keyName=").append(keyName);
        sb.append(", alias=").append(alias);
        sb.append(", description=").append(description);
        sb.append(", parentId=").append(parentId);
        sb.append(", code=").append(code);
        sb.append("]");
        return sb.toString();
    }

    @ColumnTransient
    @Override
    public Integer getTreeId() {
        return id;
    }

    @ColumnTransient
    @Override
    public String getTreeCode() {
        return code;
    }

    @ColumnTransient
    @Override
    public String getTreeTitle() {
        return alias;
    }

    @ColumnTransient
    @Override
    public Integer getTreeParentId() {
        return parentId;
    }
}