package club.emergency.auth_permission.model;

import club.map.core.mapper.ColumnTransient;
import club.map.core.model.RootObject;
import club.map.core.model.TableName;

import java.util.List;

@TableName("by_s_role")
public class BySRole extends RootObject {
    private String roleName;//角色名称

    private String remark;//角色描述

    private Integer roleType;//角色类型(1为业务处理系统,2为管理系统)

    private List<BySMenu> bySMenuList;//角色权限

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @ColumnTransient
    public List<BySMenu> getBySMenuList() {
        return bySMenuList;
    }

    public void setBySMenuList(List<BySMenu> bySMenuList) {
        this.bySMenuList = bySMenuList;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", roleName=").append(roleName);
        sb.append(", remark=").append(remark);
        sb.append(", bySMenuList=").append(bySMenuList);
        sb.append(", roleType=").append(roleType);
        sb.append("]");
        return sb.toString();
    }
}