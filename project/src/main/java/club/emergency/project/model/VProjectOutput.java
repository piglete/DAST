package club.emergency.project.model;

import club.map.core.model.RootObject;
import club.map.core.model.TableName;

import java.math.BigDecimal;

@TableName("v_project_output")
public class VProjectOutput extends RootObject {
    private Integer taskId;//任务id

    private BigDecimal workCount;//数量

    private BigDecimal internalWorkOutput;//内部计算产值结果

    private Integer projectId;//项目编号

    private BigDecimal internalOutput;//内部产值(20190523-lxl)

    private String itemApplicationName;//别名

    private String unitTypeName;//别名

    private String recordTypeName;//别名

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public BigDecimal getWorkCount() {
        return workCount;
    }

    public void setWorkCount(BigDecimal workCount) {
        this.workCount = workCount;
    }

    public BigDecimal getInternalWorkOutput() {
        return internalWorkOutput;
    }

    public void setInternalWorkOutput(BigDecimal internalWorkOutput) {
        this.internalWorkOutput = internalWorkOutput;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public BigDecimal getInternalOutput() {
        return internalOutput;
    }

    public void setInternalOutput(BigDecimal internalOutput) {
        this.internalOutput = internalOutput;
    }

    public String getItemApplicationName() {
        return itemApplicationName;
    }

    public void setItemApplicationName(String itemApplicationName) {
        this.itemApplicationName = itemApplicationName;
    }

    public String getUnitTypeName() {
        return unitTypeName;
    }

    public void setUnitTypeName(String unitTypeName) {
        this.unitTypeName = unitTypeName;
    }

    public String getRecordTypeName() {
        return recordTypeName;
    }

    public void setRecordTypeName(String recordTypeName) {
        this.recordTypeName = recordTypeName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", taskId=").append(taskId);
        sb.append(", workCount=").append(workCount);
        sb.append(", internalWorkOutput=").append(internalWorkOutput);
        sb.append(", projectId=").append(projectId);
        sb.append(", internalOutput=").append(internalOutput);
        sb.append(", itemApplicationName=").append(itemApplicationName);
        sb.append(", unitTypeName=").append(unitTypeName);
        sb.append(", recordTypeName=").append(recordTypeName);
        sb.append("]");
        return sb.toString();
    }
}