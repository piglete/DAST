package club.emergency.project.model;

import club.map.core.mapper.ColumnTransient;
import club.map.core.model.RootObject;
import club.map.core.model.TableName;

import java.math.BigDecimal;

@TableName("v_project_task")
public class VProjectTask extends RootObject {
    private String startDate;//项目计划开始日期

    private String name;//项目名称

//    private String finishDate;//项目计划结束日期

    private String regionCode;//区域code

    private String unitName;//甲方单位名称

//    private String linkman;//联系人
//
//    private String telephone;//联系方式

//    private Integer projectPeriod;//项目预计周期(天)
//
//    private Integer projectCostId;//项目费用支付状态id

    private Integer recordTypeId;//项目类型id

    private Integer projectStateId;//项目状态id

//    private Integer dataStateId;//资料交付状态id

    private String departmentCode;//部门code(关联部门)

//    private String workGroupUser;//小组负责人

    private String workGroupCode;//作业小组code

    private Double taskScore;//任务评定分数

//    private BigDecimal externalOutput;//外部产值(20190523-lxl)

    private BigDecimal internalOutput;//内部产值(20190523-lxl)

    private String workloadInfo;
    private Integer employeeId;//部门负责人id(关联员工表)

//    private Integer taskStateId;//任务状态id

//    private Integer taskUsingType;//任务状态id
    private String address;//项目地址

    private Integer taskId;

    private Integer fileId;

    private String fileName;//文件名称

    private String fileUrl;//文件路径

    private String recordTypeName; //项目类型

    private String departmentName; //部门名称


    private String workGroupName; //小组名称

//    public Integer getTaskUsingType() {
//        return taskUsingType;
//    }
//
//    public void setTaskUsingType(Integer taskUsingType) {
//        this.taskUsingType = taskUsingType;
//    }

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
//
//    public String getFinishDate() {
//        return finishDate;
//    }
//
//    public void setFinishDate(String finishDate) {
//        this.finishDate = finishDate;
//    }
//
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

    public String getWorkloadInfo() {
        return workloadInfo;
    }

    public void setWorkloadInfo(String workloadInfo) {
        this.workloadInfo = workloadInfo;
    }

//    public String getLinkman() {
//        return linkman;
//    }
//
//    public void setLinkman(String linkman) {
//        this.linkman = linkman;
//    }
//
//    public String getTelephone() {
//        return telephone;
//    }
//
//    public void setTelephone(String telephone) {
//        this.telephone = telephone;
//    }

//    public Integer getProjectPeriod() {
//        return projectPeriod;
//    }
//
//    public void setProjectPeriod(Integer projectPeriod) {
//        this.projectPeriod = projectPeriod;
//    }
//
//    public Integer getProjectCostId() {
//        return projectCostId;
//    }
//
//    public void setProjectCostId(Integer projectCostId) {
//        this.projectCostId = projectCostId;
//    }

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
//
//    public Integer getDataStateId() {
//        return dataStateId;
//    }
//
//    public void setDataStateId(Integer dataStateId) {
//        this.dataStateId = dataStateId;
//    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

//    public String getWorkGroupUser() {
//        return workGroupUser;
//    }
//
//    public void setWorkGroupUser(String workGroupUser) {
//        this.workGroupUser = workGroupUser;
//    }

    public String getWorkGroupCode() {
        return workGroupCode;
    }

    public void setWorkGroupCode(String workGroupCode) {
        this.workGroupCode = workGroupCode;
    }

    public Double getTaskScore() {
        return taskScore;
    }

    public void setTaskScore(Double taskScore) {
        this.taskScore = taskScore;
    }

//    public BigDecimal getExternalOutput() {
//        return externalOutput;
//    }
//
//    public void setExternalOutput(BigDecimal externalOutput) {
//        this.externalOutput = externalOutput;
//    }

    public BigDecimal getInternalOutput() {
        return internalOutput;
    }

    public void setInternalOutput(BigDecimal internalOutput) {
        this.internalOutput = internalOutput;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

//    public Integer getTaskStateId() {
//        return taskStateId;
//    }
//
//    public void setTaskStateId(Integer taskStateId) {
//        this.taskStateId = taskStateId;
//    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
    @ColumnTransient
    public String getWorkGroupName() {
        return workGroupName;
    }

    public void setWorkGroupName(String workGroupName) {
        this.workGroupName = workGroupName;
    }

    @ColumnTransient
    public String getRecordTypeName() {
        return recordTypeName;
    }

    public void setRecordTypeName(String recordTypeName) {
        this.recordTypeName = recordTypeName;
    }
    @ColumnTransient
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", startDate=").append(startDate);
        sb.append(", name=").append(name);
//        sb.append(", finishDate=").append(finishDate);
//        sb.append(", regionCode=").append(regionCode);
        sb.append(", unitName=").append(unitName);
//        sb.append(", linkman=").append(linkman);
//        sb.append(", telephone=").append(telephone);
//        sb.append(", projectPeriod=").append(projectPeriod);
//        sb.append(", projectCostId=").append(projectCostId);
        sb.append(", recordTypeId=").append(recordTypeId);
        sb.append(", projectStateId=").append(projectStateId);
//        sb.append(", dataStateId=").append(dataStateId);
        sb.append(", departmentCode=").append(departmentCode);
//        sb.append(", workGroupUser=").append(workGroupUser);
        sb.append(", workGroupCode=").append(workGroupCode);
        sb.append(", taskScore=").append(taskScore);
//        sb.append(", externalOutput=").append(externalOutput);
        sb.append(", internalOutput=").append(internalOutput);
        sb.append(", employeeId=").append(employeeId);
//        sb.append(", taskStateId=").append(taskStateId);
        sb.append(", address=").append(address);
        sb.append(", taskId=").append(taskId);
        sb.append(", fileId=").append(fileId);
        sb.append(", fileName=").append(fileName);
        sb.append(", fileUrl=").append(fileUrl);
        sb.append("]");
        return sb.toString();
    }
}