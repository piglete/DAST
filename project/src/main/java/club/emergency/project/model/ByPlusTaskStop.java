package club.emergency.project.model;

import club.map.core.mapper.ColumnTransient;
import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("by_plus_task_stop")
public class ByPlusTaskStop extends RootObject {
    private Integer taskId;//任务id

    private Integer stopOperatorId;//暂停操作人id

    private String stopOperator;//暂停操作人

    private String stopDate;//暂停时间

    private String restartDate;//重新开始时间

    private Integer stopOrder;//当前暂停次序

    private String remark;//备注

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getStopOperatorId() {
        return stopOperatorId;
    }

    public void setStopOperatorId(Integer stopOperatorId) {
        this.stopOperatorId = stopOperatorId;
    }

    public String getStopDate() {
        return stopDate;
    }

    public void setStopDate(String stopDate) {
        this.stopDate = stopDate;
    }

    public String getRestartDate() {
        return restartDate;
    }

    public void setRestartDate(String restartDate) {
        this.restartDate = restartDate;
    }

    public Integer getStopOrder() {
        return stopOrder;
    }

    public void setStopOrder(Integer stopOrder) {
        this.stopOrder = stopOrder;
    }

    @ColumnTransient
    public String getStopOperator() {
        return stopOperator;
    }

    public void setStopOperator(String stopOperator) {
        this.stopOperator = stopOperator;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", taskId=").append(taskId);
        sb.append(", stopOperatorId=").append(stopOperatorId);
        sb.append(", stopDate=").append(stopDate);
        sb.append(", restartDate=").append(restartDate);
        sb.append(", stopOrder=").append(stopOrder);
        sb.append(", remark=").append(stopOrder);
        sb.append("]");
        return sb.toString();
    }
}