package club.emergency.project.model;

import club.map.core.model.RootObject;
import club.map.core.model.TableName;

import java.math.BigDecimal;

@TableName("v_statistics")
public class VStatistics extends RootObject {
    private String startDate;//项目计划开始日期

    private String name;//项目名称

    private String finishDate;//项目计划结束日期

    private String regionCode;//区域code

    private String unitName;//甲方单位名称

    private Integer recordTypeId;//项目类型id

    private Integer projectStateId;//项目状态id

    private Integer dataStateId;//资料交付状态id

    private String address;//项目地址

    private Integer flag;//项目完成标识(0为进行中,1为已完成)

    private Integer taskFlag;//任务下达标识(0为未下达,1为已下达)

    private String dataCommit;//资料提交情况

    private String latitude;//纬度

    private String departmentCode;//部门code(关联部门)

    private Integer employeeId;//部门负责人id(关联员工表)

    private String departmentRevertDate;//部门接收任务时间

    private String remark;//备注

    private Integer taskStateId;//任务状态id

    private String workGroupUser;//小组负责人

    private String workGroupCode;//作业小组code

    private Integer workGroupEmployeeId;//小组负责人id

    private String groupRevertDate;//小组接收时间

    private String groupCommitDate;//小组提交时间

    private String groupFinishPeriod;//小组完成周期

    private Double taskScore;//任务评定分数

    private Integer invalidFlag;//任务作废标识(针对工程部回退,0为不作废,1为作废)

    private Integer tFlag;//任务标识(0为进行中,1为暂停,2已完成未入库,3为完成且入库)

    private Integer stopCount;//任务中途暂停次数(0为没有暂停,其他为暂停次数)

    private Integer taskUsingType;

    private BigDecimal internalOutput;//内部产值(20190523-lxl)

    private String longitude;//经度

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Integer getRecordTypeId() {
        return recordTypeId;
    }

    public void setRecordTypeId(Integer recordTypeId) {
        this.recordTypeId = recordTypeId;
    }

    public Integer getProjectStateId() {
        return projectStateId;
    }

    public void setProjectStateId(Integer projectStateId) {
        this.projectStateId = projectStateId;
    }

    public Integer getDataStateId() {
        return dataStateId;
    }

    public void setDataStateId(Integer dataStateId) {
        this.dataStateId = dataStateId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getTaskFlag() {
        return taskFlag;
    }

    public void setTaskFlag(Integer taskFlag) {
        this.taskFlag = taskFlag;
    }

    public String getDataCommit() {
        return dataCommit;
    }

    public void setDataCommit(String dataCommit) {
        this.dataCommit = dataCommit;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getDepartmentRevertDate() {
        return departmentRevertDate;
    }

    public void setDepartmentRevertDate(String departmentRevertDate) {
        this.departmentRevertDate = departmentRevertDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getTaskStateId() {
        return taskStateId;
    }

    public void setTaskStateId(Integer taskStateId) {
        this.taskStateId = taskStateId;
    }

    public String getWorkGroupUser() {
        return workGroupUser;
    }

    public void setWorkGroupUser(String workGroupUser) {
        this.workGroupUser = workGroupUser;
    }

    public String getWorkGroupCode() {
        return workGroupCode;
    }

    public void setWorkGroupCode(String workGroupCode) {
        this.workGroupCode = workGroupCode;
    }

    public Integer getWorkGroupEmployeeId() {
        return workGroupEmployeeId;
    }

    public void setWorkGroupEmployeeId(Integer workGroupEmployeeId) {
        this.workGroupEmployeeId = workGroupEmployeeId;
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

    public String getGroupFinishPeriod() {
        return groupFinishPeriod;
    }

    public void setGroupFinishPeriod(String groupFinishPeriod) {
        this.groupFinishPeriod = groupFinishPeriod;
    }

    public Double getTaskScore() {
        return taskScore;
    }

    public void setTaskScore(Double taskScore) {
        this.taskScore = taskScore;
    }

    public Integer getInvalidFlag() {
        return invalidFlag;
    }

    public void setInvalidFlag(Integer invalidFlag) {
        this.invalidFlag = invalidFlag;
    }

    public Integer gettFlag() {
        return tFlag;
    }

    public void settFlag(Integer tFlag) {
        this.tFlag = tFlag;
    }

    public Integer getStopCount() {
        return stopCount;
    }

    public void setStopCount(Integer stopCount) {
        this.stopCount = stopCount;
    }

    public Integer getTaskUsingType() {
        return taskUsingType;
    }

    public void setTaskUsingType(Integer taskUsingType) {
        this.taskUsingType = taskUsingType;
    }

    public BigDecimal getInternalOutput() {
        return internalOutput;
    }

    public void setInternalOutput(BigDecimal internalOutput) {
        this.internalOutput = internalOutput;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", startDate=").append(startDate);
        sb.append(", name=").append(name);
        sb.append(", finishDate=").append(finishDate);
        sb.append(", regionCode=").append(regionCode);
        sb.append(", unitName=").append(unitName);
        sb.append(", recordTypeId=").append(recordTypeId);
        sb.append(", projectStateId=").append(projectStateId);
        sb.append(", dataStateId=").append(dataStateId);
        sb.append(", address=").append(address);
        sb.append(", flag=").append(flag);
        sb.append(", taskFlag=").append(taskFlag);
        sb.append(", dataCommit=").append(dataCommit);
        sb.append(", latitude=").append(latitude);
        sb.append(", departmentCode=").append(departmentCode);
        sb.append(", employeeId=").append(employeeId);
        sb.append(", departmentRevertDate=").append(departmentRevertDate);
        sb.append(", remark=").append(remark);
        sb.append(", taskStateId=").append(taskStateId);
        sb.append(", workGroupUser=").append(workGroupUser);
        sb.append(", workGroupCode=").append(workGroupCode);
        sb.append(", workGroupEmployeeId=").append(workGroupEmployeeId);
        sb.append(", groupRevertDate=").append(groupRevertDate);
        sb.append(", groupCommitDate=").append(groupCommitDate);
        sb.append(", groupFinishPeriod=").append(groupFinishPeriod);
        sb.append(", taskScore=").append(taskScore);
        sb.append(", invalidFlag=").append(invalidFlag);
        sb.append(", tFlag=").append(tFlag);
        sb.append(", stopCount=").append(stopCount);
        sb.append(", taskUsingType=").append(taskUsingType);
        sb.append(", internalOutput=").append(internalOutput);
        sb.append(", longitude=").append(longitude);
        sb.append("]");
        return sb.toString();
    }
}