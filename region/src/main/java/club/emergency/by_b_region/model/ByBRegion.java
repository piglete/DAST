package club.emergency.by_b_region.model;

import club.map.core.mapper.ColumnTransient;
import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("by_b_region")
public class ByBRegion extends RootObject implements RegionTreeNodeInterface {
    private String name;//区域名称

    private String code;//区域code

    private Integer parentId;//父id

    private String parentName;//上级区域名称

    private Integer sortNum;//排序

    private String fullName;//区域全名

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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
        return name;
    }

    @ColumnTransient
    @Override
    public Integer getTreeParentId() {
        return parentId;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    @ColumnTransient
    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", name=").append(name);
        sb.append(", code=").append(code);
        sb.append(", parentId=").append(parentId);
        sb.append(", sortNum=").append(sortNum);
        sb.append(", fullName=").append(fullName);
        sb.append("]");
        return sb.toString();
    }
}