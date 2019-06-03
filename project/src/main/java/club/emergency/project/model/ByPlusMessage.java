package club.emergency.project.model;

import club.map.core.mapper.ColumnTransient;
import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("by_plus_message")
public class ByPlusMessage extends RootObject {
    private Integer projectId;//项目id

    private Integer taskId;//任务id

    private String taskName;//任务名称

    private String message;//通知内容

    private Integer messageUserId;//通知人id

    private Integer messageRoleId;//通知角色id(主要是对应二检,三检,档案部)

    private Integer taskStateId;//通知任务状态

    private Integer messageCreateId;//消息创建用户

    private String messageCreateInfo;//消息创建用户详情

    /**
     * 消息类型(消息类型(0为需要修改任务状态提的示消息,1为处理消息,2为外查提示消息,3为打印提示消息,4为交付提示消息,
     * 5为外协部门提示消息,6为外协小组提示消息,7为外协小组提交一检提示消息,8为外协一检完成提示消息,9外协小组退回提示消息)
     */
    private Integer messageType;

    private String departmentCode;//部门code(消息推送部门)

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getMessageUserId() {
        return messageUserId;
    }

    public void setMessageUserId(Integer messageUserId) {
        this.messageUserId = messageUserId;
    }

    public Integer getMessageRoleId() {
        return messageRoleId;
    }

    public void setMessageRoleId(Integer messageRoleId) {
        this.messageRoleId = messageRoleId;
    }

    public Integer getTaskStateId() {
        return taskStateId;
    }

    public void setTaskStateId(Integer taskStateId) {
        this.taskStateId = taskStateId;
    }

    public Integer getMessageCreateId() {
        return messageCreateId;
    }

    public void setMessageCreateId(Integer messageCreateId) {
        this.messageCreateId = messageCreateId;
    }

    @ColumnTransient
    public String getMessageCreateInfo() {
        return messageCreateInfo;
    }

    public void setMessageCreateInfo(String messageCreateInfo) {
        this.messageCreateInfo = messageCreateInfo;
    }

    @ColumnTransient
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
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
        sb.append(", taskId=").append(taskId);
        sb.append(", message=").append(message);
        sb.append(", messageUserId=").append(messageUserId);
        sb.append(", messageRoleId=").append(messageRoleId);
        sb.append(", taskStateId=").append(taskStateId);
        sb.append(", messageCreateId=").append(messageCreateId);
        sb.append(", messageType=").append(messageType);
        sb.append(", departmentCode=").append(departmentCode);
        sb.append("]");
        return sb.toString();
    }
}