package club.emergency.project.model;

import club.map.core.mapper.ColumnTransient;
import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("by_b_store_record")
public class ByBStoreRecord extends RootObject {

    private Integer recordId;//档案id

    private String fileNumber;//档案号

    private String companyName;//单位名称

    private String recordTypeName;//档案类型名称

    private String departmentName;//部门名称

    private String storeroomCode;//库房code

    private String storeroomFullName;//库房名称

    private String operator;//操作员

    private String operateDate;//操作时间

    private String remark;//备注

    private String recordState;//档案状态(存在/借出)

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public String getStoreroomCode() {
        return storeroomCode;
    }

    public void setStoreroomCode(String storeroomCode) {
        this.storeroomCode = storeroomCode;
    }

    public String getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(String operateDate) {
        this.operateDate = operateDate;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    public String getRecordTypeName() {
        return recordTypeName;
    }

    public void setRecordTypeName(String recordTypeName) {
        this.recordTypeName = recordTypeName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @ColumnTransient
    public String getStoreroomFullName() {
        return storeroomFullName;
    }

    public void setStoreroomFullName(String storeroomFullName) {
        this.storeroomFullName = storeroomFullName;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRecordState() {
        return recordState;
    }

    public void setRecordState(String recordState) {
        this.recordState = recordState;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", recordId=").append(recordId);
        sb.append(", fileNumber=").append(fileNumber);
        sb.append(", recordTypeName=").append(recordTypeName);
        sb.append(", departmentName=").append(departmentName);
        sb.append(", storeroomCode=").append(storeroomCode);
        sb.append(", operator=").append(operator);
        sb.append(", operateDate=").append(operateDate);
        sb.append(", remark=").append(remark);
        sb.append(", recordState=").append(recordState);
        sb.append(", companyName=").append(companyName);
        sb.append("]");
        return sb.toString();
    }
}