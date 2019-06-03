package club.emergency.by_b_return_reason.model;

import club.map.core.mapper.ColumnTransient;
import club.map.core.model.RootObject;
import club.map.core.model.TableName;

import java.util.List;

@TableName("by_b_return_reason")
public class ByBReturnReason extends RootObject {
    private String content;//原因内容

    private Integer parentId;//父节点

    private String parentName;//父节点名称

    List<ByBReturnReason> childReason;//子节点

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @ColumnTransient
    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    @ColumnTransient
    public List<ByBReturnReason> getChildReason() {
        return childReason;
    }

    public void setChildReason(List<ByBReturnReason> childReason) {
        this.childReason = childReason;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", content=").append(content);
        sb.append(", parentId=").append(parentId);
        sb.append("]");
        return sb.toString();
    }
}