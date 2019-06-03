package club.emergency.project.model;

import club.map.core.mapper.ColumnTransient;
import club.map.core.model.RootObject;
import club.map.core.model.TableName;

import java.math.BigDecimal;

@TableName("by_plus_task_workload")
public class ByPlusTaskWorkload extends RootObject {
    private Integer taskId;//任务id

    private String taskName;//任务名称

    private BigDecimal workCount;//数量
    /**
     * update by lxl 20190521
     */
    private BigDecimal internalWorkOutput;//内部产值计算结果

    private BigDecimal temp_internal_val;//临时产值单价

    private Integer itemApplicationId;//项目申报id

    private String itemApplication;//项目申报内容

    private Integer unitTypeId;//申报单位id

    private String unitType;//申报单位

    private String departmentCode;//部门code

    private String departmentName;//部门名称

    private String recordTypeName;//项目类型名称

    private String remark;//备注

    private Integer flag;//工作量标识(0为主查工作量,1为外查工作量)

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getItemApplicationId() {
        return itemApplicationId;
    }

    public void setItemApplicationId(Integer itemApplicationId) {
        this.itemApplicationId = itemApplicationId;
    }

    public Integer getUnitTypeId() {
        return unitTypeId;
    }

    public void setUnitTypeId(Integer unitTypeId) {
        this.unitTypeId = unitTypeId;
    }

    public BigDecimal getTemp_internal_val() {
        return temp_internal_val;
    }

    public void setTemp_internal_val(BigDecimal temp_internal_val) {
        this.temp_internal_val = temp_internal_val;
    }
//    @ColumnTransient
//    public List<ByPlusUnitConfig> getUnitConfigList() {
//        return unitConfigList;
//    }
//
//    public void setUnitConfigList(List<ByPlusUnitConfig> unitConfigList) {
//        this.unitConfigList = unitConfigList;
//    }
    @ColumnTransient
    public String getRecordTypeName() {
        return recordTypeName;
    }

    public void setRecordTypeName(String recordTypeName) {
        this.recordTypeName = recordTypeName;
    }

    @ColumnTransient
    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    @ColumnTransient
    public String getItemApplication() {
        return itemApplication;
    }

    public void setItemApplication(String itemApplication) {
        this.itemApplication = itemApplication;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    @ColumnTransient
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @ColumnTransient
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public BigDecimal getInternalWorkOutput() {
        return internalWorkOutput;
    }

    public void setInternalWorkOutput(BigDecimal internalWorkOutput) {
        this.internalWorkOutput = internalWorkOutput;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", taskId=").append(taskId);
        sb.append(", workCount=").append(workCount);
        sb.append(", itemApplicationId=").append(itemApplicationId);
        sb.append(", unitTypeId=").append(unitTypeId);
        sb.append(", remark=").append(remark);
        sb.append(", departmentCode=").append(departmentCode);
        sb.append(", flag=").append(flag);
        sb.append("]");
        return sb.toString();
    }
}