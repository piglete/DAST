package club.emergency.by_b_storeroom.model;


import club.map.core.mapper.ColumnTransient;
import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("by_b_storeroom")
public class ByBStoreroom extends RootObject implements StoreTreeNodeInterface{
    private String name;//库房位置名称

    private String code;//code(模糊查询)

    private Integer parentId;//父id

    private String parentName;//父名称

    private Integer sortNum;//排序

    private String fullName;//库房全称

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
    public String getTreeFullName() {
        return fullName;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @ColumnTransient
    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
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