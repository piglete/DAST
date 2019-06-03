package club.emergency.project.model;

import club.map.core.mapper.ColumnTransient;
import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("by_plus_record_return")
public class ByPlusRecordReturn extends RootObject {

    private Integer taskId;//任务id

    private String returnUser;//回退人员

    private Integer recordReturnReasonId;//回退理由id

    private String recordReturnReason;//回退理由

    private String returnDate;//回退时间

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getReturnUser() {
        return returnUser;
    }

    public void setReturnUser(String returnUser) {
        this.returnUser = returnUser;
    }

    public Integer getRecordReturnReasonId() {
        return recordReturnReasonId;
    }

    public void setRecordReturnReasonId(Integer recordReturnReasonId) {
        this.recordReturnReasonId = recordReturnReasonId;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    @ColumnTransient
    public String getRecordReturnReason() {
        return recordReturnReason;
    }

    public void setRecordReturnReason(String recordReturnReason) {
        this.recordReturnReason = recordReturnReason;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", taskId=").append(taskId);
        sb.append(", returnUser=").append(returnUser);
        sb.append(", recordReturnReasonId=").append(recordReturnReasonId);
        sb.append(", returnDate=").append(returnDate);
        sb.append("]");
        return sb.toString();
    }
}