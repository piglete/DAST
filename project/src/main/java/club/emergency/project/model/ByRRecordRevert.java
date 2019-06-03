package club.emergency.project.model;

import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("by_r_record_revert")
public class ByRRecordRevert extends RootObject {
    private Integer recordId;//档案id

    private String companyName;//单位名称

    private String fileNumber;//档案号

    private String recordTypeName;//档案类型名称

    private String departmentName;//部门名称

    private String revertPerson;//归还人

    private String revertDate;//归还日期

    private String revertState;//归还状态(按期归还/逾期归还)

    private String operateDate;//操作时间

    private String manager;//管理员

    private String remark;//备注

    private Integer storeRecordId;//档案入库记录id

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public String getRevertPerson() {
        return revertPerson;
    }

    public void setRevertPerson(String revertPerson) {
        this.revertPerson = revertPerson;
    }

    public String getRevertDate() {
        return revertDate;
    }

    public void setRevertDate(String revertDate) {
        this.revertDate = revertDate;
    }

    public String getRevertState() {
        return revertState;
    }

    public void setRevertState(String revertState) {
        this.revertState = revertState;
    }

    public String getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(String operateDate) {
        this.operateDate = operateDate;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Integer getStoreRecordId() {
        return storeRecordId;
    }

    public void setStoreRecordId(Integer storeRecordId) {
        this.storeRecordId = storeRecordId;
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
        sb.append(", revertPerson=").append(revertPerson);
        sb.append(", revertDate=").append(revertDate);
        sb.append(", revertState=").append(revertState);
        sb.append(", operateDate=").append(operateDate);
        sb.append(", manager=").append(manager);
        sb.append(", remark=").append(remark);
        sb.append(", fileNumber=").append(fileNumber);
        sb.append(", recordTypeName=").append(recordTypeName);
        sb.append(", departmentName=").append(departmentName);
        sb.append(", storeRecordId=").append(storeRecordId);
        sb.append(", companyName=").append(companyName);
        sb.append("]");
        return sb.toString();
    }
}