package club.emergency.project.model;

import club.map.core.mapper.ColumnTransient;
import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("by_plus_out_check_message")
public class ByPlusOutCheckMessage extends RootObject {
    private Integer taskId;//任务id

    private String taskName;//任务名称

    private String messageContent;//消息内容

    private String outCheckFinishDate;//外查预期完成日期

    private Integer isUpdate;//二检是否修改过预期完成日期(0为没有修改过,1为修改过)

    private Integer twoCheckUserId;//二检人员id

    private String twoCheckUser;//二检人员

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getOutCheckFinishDate() {
        return outCheckFinishDate;
    }

    public void setOutCheckFinishDate(String outCheckFinishDate) {
        this.outCheckFinishDate = outCheckFinishDate;
    }

    public Integer getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(Integer isUpdate) {
        this.isUpdate = isUpdate;
    }

    public Integer getTwoCheckUserId() {
        return twoCheckUserId;
    }

    public void setTwoCheckUserId(Integer twoCheckUserId) {
        this.twoCheckUserId = twoCheckUserId;
    }

    @ColumnTransient
    public String getTwoCheckUser() {
        return twoCheckUser;
    }

    public void setTwoCheckUser(String twoCheckUser) {
        this.twoCheckUser = twoCheckUser;
    }

    @ColumnTransient
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", taskId=").append(taskId);
        sb.append(", messageContent=").append(messageContent);
        sb.append(", outCheckFinishDate=").append(outCheckFinishDate);
        sb.append(", isUpdate=").append(isUpdate);
        sb.append(", twoCheckUserId=").append(twoCheckUserId);
        sb.append("]");
        return sb.toString();
    }
}