package club.emergency.project.model;

import club.emergency.by_b_return_reason.model.ByBReturnReason;
import club.map.core.mapper.ColumnTransient;
import club.map.core.model.RootObject;
import club.map.core.model.TableName;

import java.util.List;

@TableName("by_plus_three_return_two")
public class ByPlusThreeReturnTwo extends RootObject {
    private Integer taskId;//任务id

    private String twoInspectionUser;//二检人员

    private String threeInspectionUser;//三检回退人员

    private String returnReasonIds;//回退理由ids(多个)

    private String returnReason;//回退理由

    private Double taskTwoScore;//二检评定分数

    private String threeReturnDate;//三检回退时间

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTwoInspectionUser() {
        return twoInspectionUser;
    }

    public void setTwoInspectionUser(String twoInspectionUser) {
        this.twoInspectionUser = twoInspectionUser;
    }

    public String getThreeInspectionUser() {
        return threeInspectionUser;
    }

    public void setThreeInspectionUser(String threeInspectionUser) {
        this.threeInspectionUser = threeInspectionUser;
    }

    public String getReturnReasonIds() {
        return returnReasonIds;
    }

    public void setReturnReasonIds(String returnReasonIds) {
        this.returnReasonIds = returnReasonIds;
    }

    public Double getTaskTwoScore() {
        return taskTwoScore;
    }

    public void setTaskTwoScore(Double taskTwoScore) {
        this.taskTwoScore = taskTwoScore;
    }

    public String getThreeReturnDate() {
        return threeReturnDate;
    }

    public void setThreeReturnDate(String threeReturnDate) {
        this.threeReturnDate = threeReturnDate;
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
        sb.append(", taskId=").append(taskId);
        sb.append(", twoInspectionUser=").append(twoInspectionUser);
        sb.append(", threeInspectionUser=").append(threeInspectionUser);
        sb.append(", returnReasonIds=").append(returnReasonIds);
        sb.append(", taskTwoScore=").append(taskTwoScore);
        sb.append(", threeReturnDate=").append(threeReturnDate);
        sb.append("]");
        return sb.toString();
    }
}