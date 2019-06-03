package club.emergency.project.model;

import club.map.core.mapper.ColumnTransient;
import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("by_plus_tip_message")
public class ByPlusTipMessage extends RootObject {
    private Integer projectId;//项目id

    private String projectName;//项目名称

    private String messageContent;//提示内容

    private String tipDate;//提示日期(项目预期完成日期)

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getTipDate() {
        return tipDate;
    }

    public void setTipDate(String tipDate) {
        this.tipDate = tipDate;
    }

    @ColumnTransient
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", projectId=").append(projectId);
        sb.append(", messageContent=").append(messageContent);
        sb.append(", tipDate=").append(tipDate);
        sb.append("]");
        return sb.toString();
    }
}