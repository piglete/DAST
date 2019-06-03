package club.emergency.project.model;

import club.map.core.mapper.ColumnTransient;
import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("by_plus_task_assist_return")
public class ByPlusTaskAssistReturn extends RootObject {
    private Integer departmentAssistId;//协助部门id

    private Integer taskId;//任务id

    private String departmentCode;//回退小组code

    private String departmentName;//外协部门

    private String returnDate;//回退日期

    private Integer returnUserId;//退回人id

    private String returnUser;//退回人

    private String returnReasonIds;//回退原因(可多个原因,存原因id,中间","隔开)

    private String returnReason;//回退原因

    private String remark;//备注

    public Integer getDepartmentAssistId() {
        return departmentAssistId;
    }

    public void setDepartmentAssistId(Integer departmentAssistId) {
        this.departmentAssistId = departmentAssistId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public Integer getReturnUserId() {
        return returnUserId;
    }

    public void setReturnUserId(Integer returnUserId) {
        this.returnUserId = returnUserId;
    }

    public String getReturnReasonIds() {
        return returnReasonIds;
    }

    public void setReturnReasonIds(String returnReasonIds) {
        this.returnReasonIds = returnReasonIds;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @ColumnTransient
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @ColumnTransient
    public String getReturnUser() {
        return returnUser;
    }

    public void setReturnUser(String returnUser) {
        this.returnUser = returnUser;
    }

    @ColumnTransient
    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", departmentAssistId=").append(departmentAssistId);
        sb.append(", taskId=").append(taskId);
        sb.append(", departmentCode=").append(departmentCode);
        sb.append(", returnDate=").append(returnDate);
        sb.append(", returnUserId=").append(returnUserId);
        sb.append(", returnReasonIds=").append(returnReasonIds);
        sb.append(", remark=").append(remark);
        sb.append("]");
        return sb.toString();
    }
}