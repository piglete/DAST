package club.emergency.by_b_department.model;

import club.map.base.util.AppTreeNodeInterface;
import club.map.core.mapper.ColumnTransient;
import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("by_b_department")
public class ByBDepartment extends RootObject implements AppTreeNodeInterface {
    private String departmentName;//部门名称

    private String code;//部门code

    private Integer parentId;//父id

    private String description;//部门描述

    private Integer sortNum;//排序

    private Integer releaseFlag;//可下达任务部门标识(0为不可以下达,1为可以下达)

    private String release;//可下达任务部门标识

    private Integer invalidFlag;//部门作废标识(0为未作废,1为已作废)

    private String invalid;//部门作废标识

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Integer getReleaseFlag() {
        return releaseFlag;
    }

    public void setReleaseFlag(Integer releaseFlag) {
        this.releaseFlag = releaseFlag;
    }

    public Integer getInvalidFlag() {
        return invalidFlag;
    }

    public void setInvalidFlag(Integer invalidFlag) {
        this.invalidFlag = invalidFlag;
    }

    @ColumnTransient
    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    @ColumnTransient
    public String getInvalid() {
        return invalid;
    }

    public void setInvalid(String invalid) {
        this.invalid = invalid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", departmentName=").append(departmentName);
        sb.append(", code=").append(code);
        sb.append(", parentId=").append(parentId);
        sb.append(", description=").append(description);
        sb.append(", sortNum=").append(sortNum);
        sb.append(", releaseFlag=").append(releaseFlag);
        sb.append(", invalidFlag=").append(invalidFlag);
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
        return departmentName;
    }

    @ColumnTransient
    @Override
    public Integer getTreeParentId() {
        return parentId;
    }
}