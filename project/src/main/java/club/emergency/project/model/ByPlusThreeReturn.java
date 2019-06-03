package club.emergency.project.model;

import club.emergency.by_b_return_reason.model.ByBReturnReason;
import club.map.core.mapper.ColumnTransient;
import club.map.core.model.RootObject;
import club.map.core.model.TableName;

import java.util.List;

@TableName("by_plus_three_return")
public class ByPlusThreeReturn extends RootObject {
    private Integer taskId;//任务id

    private String oneInspectionUser;//一检人员

    private String oneRevertDate;//一检接收时间

    private String oneCheckDate;//一检核对时间

    private String twoInspectionUser;//二检人员

    private String twoRevertDate;//二检接收时间

    private String twoCheckDate;//二检核对时间

    private String threeInspectionUser;//三检人员

    private String threeRevertDate;//三检接收时间

    private String threeReturnDate;//三检回退时间

    private String returnUser;//回退人员

    private String threeReturnReasonIds;//回退理由ids(多个)

    private String threeReturnReason;//回退理由

    private String groupRevertDate;//小组接收时间

    private String groupCommitDate;//小组完成时间

    private Double taskTwoScore;//二检评定分数

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getOneInspectionUser() {
        return oneInspectionUser;
    }

    public void setOneInspectionUser(String oneInspectionUser) {
        this.oneInspectionUser = oneInspectionUser;
    }

    public String getOneRevertDate() {
        return oneRevertDate;
    }

    public void setOneRevertDate(String oneRevertDate) {
        this.oneRevertDate = oneRevertDate;
    }

    public String getOneCheckDate() {
        return oneCheckDate;
    }

    public void setOneCheckDate(String oneCheckDate) {
        this.oneCheckDate = oneCheckDate;
    }

    public String getTwoInspectionUser() {
        return twoInspectionUser;
    }

    public void setTwoInspectionUser(String twoInspectionUser) {
        this.twoInspectionUser = twoInspectionUser;
    }

    public String getTwoRevertDate() {
        return twoRevertDate;
    }

    public void setTwoRevertDate(String twoRevertDate) {
        this.twoRevertDate = twoRevertDate;
    }

    public String getTwoCheckDate() {
        return twoCheckDate;
    }

    public void setTwoCheckDate(String twoCheckDate) {
        this.twoCheckDate = twoCheckDate;
    }

    public String getThreeInspectionUser() {
        return threeInspectionUser;
    }

    public void setThreeInspectionUser(String threeInspectionUser) {
        this.threeInspectionUser = threeInspectionUser;
    }

    public String getThreeRevertDate() {
        return threeRevertDate;
    }

    public void setThreeRevertDate(String threeRevertDate) {
        this.threeRevertDate = threeRevertDate;
    }

    public String getThreeReturnDate() {
        return threeReturnDate;
    }

    public void setThreeReturnDate(String threeReturnDate) {
        this.threeReturnDate = threeReturnDate;
    }

    public String getReturnUser() {
        return returnUser;
    }

    public void setReturnUser(String returnUser) {
        this.returnUser = returnUser;
    }

    public String getThreeReturnReasonIds() {
        return threeReturnReasonIds;
    }

    public void setThreeReturnReasonIds(String threeReturnReasonIds) {
        this.threeReturnReasonIds = threeReturnReasonIds;
    }

    public String getGroupRevertDate() {
        return groupRevertDate;
    }

    public void setGroupRevertDate(String groupRevertDate) {
        this.groupRevertDate = groupRevertDate;
    }

    public String getGroupCommitDate() {
        return groupCommitDate;
    }

    public void setGroupCommitDate(String groupCommitDate) {
        this.groupCommitDate = groupCommitDate;
    }

    public Double getTaskTwoScore() {
        return taskTwoScore;
    }

    public void setTaskTwoScore(Double taskTwoScore) {
        this.taskTwoScore = taskTwoScore;
    }

    @ColumnTransient
    public String getThreeReturnReason() {
        return threeReturnReason;
    }

    public void setThreeReturnReason(String threeReturnReason) {
        this.threeReturnReason = threeReturnReason;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", taskId=").append(taskId);
        sb.append(", oneInspectionUser=").append(oneInspectionUser);
        sb.append(", oneRevertDate=").append(oneRevertDate);
        sb.append(", oneCheckDate=").append(oneCheckDate);
        sb.append(", twoInspectionUser=").append(twoInspectionUser);
        sb.append(", twoRevertDate=").append(twoRevertDate);
        sb.append(", twoCheckDate=").append(twoCheckDate);
        sb.append(", threeInspectionUser=").append(threeInspectionUser);
        sb.append(", threeRevertDate=").append(threeRevertDate);
        sb.append(", threeReturnDate=").append(threeReturnDate);
        sb.append(", returnUser=").append(returnUser);
        sb.append(", threeReturnReasonIds=").append(threeReturnReasonIds);
        sb.append(", groupRevertDate=").append(groupRevertDate);
        sb.append(", groupCommitDate=").append(groupCommitDate);
        sb.append(", taskTwoScore=").append(taskTwoScore);
        sb.append("]");
        return sb.toString();
    }
}