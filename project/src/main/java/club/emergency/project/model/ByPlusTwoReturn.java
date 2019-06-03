package club.emergency.project.model;

import club.emergency.by_b_return_reason.model.ByBReturnReason;
import club.map.core.mapper.ColumnTransient;
import club.map.core.model.RootObject;
import club.map.core.model.TableName;

import java.util.List;

@TableName("by_plus_two_return")
public class ByPlusTwoReturn extends RootObject {
    private Integer taskId;//任务id

    private String oneInspectionUser;//一检人员

    private String oneRevertDate;//一检接收时间

    private String oneCheckDate;//一检核对时间

    private String twoInspectionUser;//二检人员

    private String twoRevertDate;//二检接收时间

    private String twoReturnDate;//二检回退时间

    private String returnUser;//回退人员

    private String twoReturnReasonIds;//回退理由ids(多个)

    private String twoReturnReason;//回退理由

    private String groupRevertDate;//小组接收时间

    private String groupCommitDate;//小组提交时间

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

    public String getTwoReturnDate() {
        return twoReturnDate;
    }

    public void setTwoReturnDate(String twoReturnDate) {
        this.twoReturnDate = twoReturnDate;
    }

    public String getReturnUser() {
        return returnUser;
    }

    public void setReturnUser(String returnUser) {
        this.returnUser = returnUser;
    }

    public String getTwoReturnReasonIds() {
        return twoReturnReasonIds;
    }

    public void setTwoReturnReasonIds(String twoReturnReasonIds) {
        this.twoReturnReasonIds = twoReturnReasonIds;
    }

    @ColumnTransient
    public String getTwoReturnReason() {
        return twoReturnReason;
    }

    public void setTwoReturnReason(String twoReturnReason) {
        this.twoReturnReason = twoReturnReason;
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
        sb.append(", twoReturnDate=").append(twoReturnDate);
        sb.append(", returnUser=").append(returnUser);
        sb.append(", twoReturnReasonIds=").append(twoReturnReasonIds);
        sb.append(", groupRevertDate=").append(groupRevertDate);
        sb.append(", groupCommitDate=").append(groupCommitDate);
        sb.append("]");
        return sb.toString();
    }
}