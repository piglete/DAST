package club.emergency.auth_permission.model;

import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("by_s_role_menu")
public class BySRoleMenu extends RootObject {
    private Integer roleId;//角色编号

    private Integer menuId;//菜单编号

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", roleId=").append(roleId);
        sb.append(", menuId=").append(menuId);
        sb.append("]");
        return sb.toString();
    }
}