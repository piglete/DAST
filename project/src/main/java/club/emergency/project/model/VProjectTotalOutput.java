package club.emergency.project.model;

import club.map.core.mapper.ColumnTransient;
import club.map.core.model.RootObject;
import club.map.core.model.TableName;

import java.math.BigDecimal;

@TableName("v_project_total_output")
public class VProjectTotalOutput extends RootObject {
    private BigDecimal workCount;//数量

    private BigDecimal internalWorkOutput;//内部计算产值结果

    private Integer itemApplicationId;//项目申报id

    private Integer workloadUsingType;

    private BigDecimal internalVal;//内部产值

    private Integer unitconfigUsingType;

    private Integer unitTypeId;//申报单位id

    private String taskName;//任务名称

    private BigDecimal tempInternalVal;//临时内部产值单价

    private Integer projectStateId;//项目状态id

    private Integer projectId;//项目编号

    private Integer projectUsingType;

    private Integer recordTypeId;//项目类型id


    private  String itemApplicationName;
    private  String  unitTypeName;
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

    public Integer getItemApplicationId() {
        return itemApplicationId;
    }

    public void setItemApplicationId(Integer itemApplicationId) {
        this.itemApplicationId = itemApplicationId;
    }

    public Integer getWorkloadUsingType() {
        return workloadUsingType;
    }

    public void setWorkloadUsingType(Integer workloadUsingType) {
        this.workloadUsingType = workloadUsingType;
    }

    public BigDecimal getInternalVal() {
        return internalVal;
    }

    public void setInternalVal(BigDecimal internalVal) {
        this.internalVal = internalVal;
    }

    public Integer getUnitconfigUsingType() {
        return unitconfigUsingType;
    }

    public void setUnitconfigUsingType(Integer unitconfigUsingType) {
        this.unitconfigUsingType = unitconfigUsingType;
    }

    public Integer getUnitTypeId() {
        return unitTypeId;
    }

    public void setUnitTypeId(Integer unitTypeId) {
        this.unitTypeId = unitTypeId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public BigDecimal getTempInternalVal() {
        return tempInternalVal;
    }

    public void setTempInternalVal(BigDecimal tempInternalVal) {
        this.tempInternalVal = tempInternalVal;
    }

    public Integer getProjectStateId() {
        return projectStateId;
    }

    public void setProjectStateId(Integer projectStateId) {
        this.projectStateId = projectStateId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getProjectUsingType() {
        return projectUsingType;
    }

    public void setProjectUsingType(Integer projectUsingType) {
        this.projectUsingType = projectUsingType;
    }

    public Integer getRecordTypeId() {
        return recordTypeId;
    }

    public void setRecordTypeId(Integer recordTypeId) {
        this.recordTypeId = recordTypeId;
    }
    @ColumnTransient
    public String getItemApplicationName() {
        return itemApplicationName;
    }

    public void setItemApplicationName(String itemApplicationName) {
        this.itemApplicationName = itemApplicationName;
    }
    @ColumnTransient
    public String getUnitTypeName() {
        return unitTypeName;
    }

    public void setUnitTypeName(String unitTypeName) {
        this.unitTypeName = unitTypeName;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", workCount=").append(workCount);
        sb.append(", internalWorkOutput=").append(internalWorkOutput);
        sb.append(", itemApplicationId=").append(itemApplicationId);
        sb.append(", workloadUsingType=").append(workloadUsingType);
        sb.append(", internalVal=").append(internalVal);
        sb.append(", unitconfigUsingType=").append(unitconfigUsingType);
        sb.append(", unitTypeId=").append(unitTypeId);
        sb.append(", taskName=").append(taskName);
        sb.append(", tempInternalVal=").append(tempInternalVal);
        sb.append(", projectStateId=").append(projectStateId);
        sb.append(", projectId=").append(projectId);
        sb.append(", projectUsingType=").append(projectUsingType);
        sb.append(", recordTypeId=").append(recordTypeId);
        sb.append("]");
        return sb.toString();
    }
}