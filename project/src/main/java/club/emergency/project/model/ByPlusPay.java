package club.emergency.project.model;

import club.map.core.mapper.ColumnTransient;
import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("by_plus_pay")
public class ByPlusPay extends RootObject {
    private Integer projectId;//项目id

    private Integer payTypeId;//交付类型id

    private String payType;//交付类型

    private String payUser;//办理人员

    private String departmentCode;//所属部门code

    private String departmentName;//所属部门

    private String payCondition;//交付情况

    private String remark;//备注

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getPayTypeId() {
        return payTypeId;
    }

    public void setPayTypeId(Integer payTypeId) {
        this.payTypeId = payTypeId;
    }

    public String getPayUser() {
        return payUser;
    }

    public void setPayUser(String payUser) {
        this.payUser = payUser;
    }

    @ColumnTransient
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getPayCondition() {
        return payCondition;
    }

    public void setPayCondition(String payCondition) {
        this.payCondition = payCondition;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @ColumnTransient
    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", projectId=").append(projectId);
        sb.append(", payTypeId=").append(payTypeId);
        sb.append(", payUser=").append(payUser);
        sb.append(", departmentCode=").append(departmentCode);
        sb.append(", payCondition=").append(payCondition);
        sb.append(", remark=").append(remark);
        sb.append("]");
        return sb.toString();
    }
}