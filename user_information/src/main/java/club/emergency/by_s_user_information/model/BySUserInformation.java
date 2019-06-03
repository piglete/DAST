package club.emergency.by_s_user_information.model;

import club.emergency.auth_permission.model.BySMenu;
import club.emergency.by_b_employee.model.ByBEmployee;
import club.map.core.mapper.ColumnTransient;
import club.map.core.model.RootObject;
import club.map.core.model.TableName;

import java.util.List;

@TableName("by_s_user_information")
public class BySUserInformation extends RootObject {
    private String loginName;//用户账号

    private String password;//用户密码

    private Integer employeeId;//员工编号

    private String employeeName;//员工名称

    private String departmentCode;//员工部门

    private String icon;//图标

    private Integer roleId;//角色编号

    private String roleName;//角色名称

    private Integer roleType;//角色类型

    private List<BySMenu> bySMenuList;//权限列表

    private ByBEmployee byBEmployee;//员工信息

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @ColumnTransient
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @ColumnTransient
    public List<BySMenu> getBySMenuList() {
        return bySMenuList;
    }

    public void setBySMenuList(List<BySMenu> bySMenuList) {
        this.bySMenuList = bySMenuList;
    }

    @ColumnTransient
    public ByBEmployee getByBEmployee() {
        return byBEmployee;
    }

    public void setByBEmployee(ByBEmployee byBEmployee) {
        this.byBEmployee = byBEmployee;
    }

    @ColumnTransient
    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @ColumnTransient
    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    @ColumnTransient
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @ColumnTransient
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
        sb.append(", loginName=").append(loginName);
        sb.append(", password=").append(password);
        sb.append(", employeeId=").append(employeeId);
        sb.append(", roleId=").append(roleId);
        sb.append(", roleName=").append(roleName);
        sb.append(", byBEmployee=").append(byBEmployee);
        sb.append("]");
        return sb.toString();
    }
}