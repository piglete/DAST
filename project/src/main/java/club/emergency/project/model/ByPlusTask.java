package club.emergency.project.model;

import club.map.core.mapper.ColumnTransient;
import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("by_plus_task")
public class ByPlusTask extends RootObject {

    private Integer projectId;//项目编号

    private ByPlusProject byPlusProject;//项目详情

    private String taskName;//任务名称

    private String departmentCode;//部门code(关联部门)

    private String departmentName;//部门名称

    private Integer employeeId;//负责人id(关联员工表)

    private String employeeName;//负责人名称

    private Integer taskStateId;//任务状态id

    private String taskState;//任务状态

    private Integer overDateCount;//超期天数

    private String oneInspectionUser;//一检人员

    private String twoInspectionUser;//二检人员

    private String threeInspectionUser;//三检人员

    private String oneRevertDate;//一检接收时间

    private String twoRevertDate;//二检接收时间

    private String threeRevertDate;//三检接收时间

    private String oneCheckDate;//一检核对时间

    private String twoCheckDate;//二检核对时间

    private String threeCheckDate;//三检核对时间

    private String assignTaskUser;//下达任务人员

    private String workGroupCode;//作业小组code

    private String workGroupName;//小组名称

    private String workGroupUser;//小组负责人

    private Integer workGroupEmployeeId;//小组负责人id

    private String groupRevertDate;//小组接收时间

    private String groupCommitDate;//小组提交时间

    private Double taskScore;//任务评定分数

    private Integer returnReasonId;//退回原因id

    private String returnReason;//退回原因

    private String departmentRevertDate;//部门接收任务时间

    private String departmentReleaseDate;//部门下达任务时间

    private String departmentReturnDate;//部门退回任务时间

    private String groupFinishPeriod;//小组完成周期

    private String oneFinishPeriod;//一检完成周期

    private String twoFinishPeriod;//二检完成周期

    private String threeFinishPeriod;//三检完成周期

    private Double taskTwoScore;//二检评定分数

    private Double taskThreeScore;//三检评定分数

    private Integer twoReturnCount;//二检回退次数

    private Integer threeReturnCount;//三检回退次数

    private Integer returnState;//当前回退状态(0为未回退,1为回退)

    private Integer recordReturnCount;//档案部回退次数

    private String recordRevertDate;//档案部接收时间

    private String recordCheckDate;//档案部核对时间

    private String recordInspectionUser;//档案部接收人员

    private Integer oneInspectionUserId;//一检人员id

    private Integer twoInspectionUserId;//二检人员id

    private Integer threeInspectionUserId;//三检人员id

    private Integer recordInspectionUserId;//档案部接收人员id

    private String outCheckUser;//外查人员

    private Integer outCheckUserId;//外查人员id

    private String outCheckRevertDate;//外查接收时间

    private String outCheckFinishDate;//外查完成时间

    private String outCheckFinishPeriod;//外查周期

    private Integer outCheckState;//外查状态(0为不需要外查,1为需要外查,2为外查接收,3为外查完成)

    private String outCheckDescription;//外查描述

    private String outCheckDate;//外查时间

    private Integer flag;//任务标识(0为进行中,1为暂停,2已完成未入库,3为完成且入库)

    private Integer invalidFlag;//任务作废标识(针对工程部回退,0为不作废,1为作废)

    private Integer threeReturnTwoFlag;//三检退回二检标识(0为没有回退,其他为回退次数)

    private String fileNumber;//资料编号

    private Integer printUserId;//打印人id

    private String printUserName;//打印人

    private String printDate;//打印时间

    private String printRemark;//打印备注

    private String printOperateDate;//三检指定打印操作日期

    private Integer printFlag;//资料打印标识(0未打印,1已打印)

    private Integer dataRevertUserId;//资料接收人id

    private String dataRevertUserName;//资料接收人

    private String dataDeliveryDate;//资料交付时间

    private String dataRevertRemark;//资料接收备注

    private String dataOperateDate;//三检指定资料交付操作日期

    private Integer dataDeliveryFlag;//资料交付标识(0未提醒交付,1已提醒交付)

    private Integer stopCount;//任务中途暂停次数(0为没有暂停,其他为暂停次数)

    private Integer assistFlag;//外协标识(0为没有外协,1为有外协)

    private Integer printFinishFlag;//工程部打印完成提醒标识(0为未提醒三检,1为已提醒三检)

    private String oneInspectionRemark;//一检备注

    private String twoInspectionRemark;//二检备注

    private String threeInspectionRemark;//三检备注

    private String departmentRemark;//部门备注

    private String groupRemark;//小组备注

    private String remark;//办公室下达备注

    private String groupReturnRemark;//小组撤回备注

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
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

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getTaskStateId() {
        return taskStateId;
    }

    public void setTaskStateId(Integer taskStateId) {
        this.taskStateId = taskStateId;
    }

    public Integer getOverDateCount() {
        return overDateCount;
    }

    public void setOverDateCount(Integer overDateCount) {
        this.overDateCount = overDateCount;
    }

    @ColumnTransient
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @ColumnTransient
    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @ColumnTransient
    public ByPlusProject getByPlusProject() {
        return byPlusProject;
    }

    public void setByPlusProject(ByPlusProject byPlusProject) {
        this.byPlusProject = byPlusProject;
    }

    public String getTwoInspectionUser() {
        return twoInspectionUser;
    }

    public void setTwoInspectionUser(String twoInspectionUser) {
        this.twoInspectionUser = twoInspectionUser;
    }

    public String getThreeInspectionUser() {
        return threeInspectionUser;
    }

    public void setThreeInspectionUser(String threeInspectionUser) {
        this.threeInspectionUser = threeInspectionUser;
    }

    @ColumnTransient
    public String getTaskState() {
        return taskState;
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState;
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

    public String getTwoRevertDate() {
        return twoRevertDate;
    }

    public void setTwoRevertDate(String twoRevertDate) {
        this.twoRevertDate = twoRevertDate;
    }

    public String getThreeRevertDate() {
        return threeRevertDate;
    }

    public void setThreeRevertDate(String threeRevertDate) {
        this.threeRevertDate = threeRevertDate;
    }

    public String getOneCheckDate() {
        return oneCheckDate;
    }

    public void setOneCheckDate(String oneCheckDate) {
        this.oneCheckDate = oneCheckDate;
    }

    public String getTwoCheckDate() {
        return twoCheckDate;
    }

    public void setTwoCheckDate(String twoCheckDate) {
        this.twoCheckDate = twoCheckDate;
    }

    public String getThreeCheckDate() {
        return threeCheckDate;
    }

    public void setThreeCheckDate(String threeCheckDate) {
        this.threeCheckDate = threeCheckDate;
    }

    public String getAssignTaskUser() {
        return assignTaskUser;
    }

    public void setAssignTaskUser(String assignTaskUser) {
        this.assignTaskUser = assignTaskUser;
    }

    public String getWorkGroupCode() {
        return workGroupCode;
    }

    public void setWorkGroupCode(String workGroupCode) {
        this.workGroupCode = workGroupCode;
    }

    @ColumnTransient
    public String getWorkGroupName() {
        return workGroupName;
    }

    public void setWorkGroupName(String workGroupName) {
        this.workGroupName = workGroupName;
    }

    public String getWorkGroupUser() {
        return workGroupUser;
    }

    public void setWorkGroupUser(String workGroupUser) {
        this.workGroupUser = workGroupUser;
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

    public Double getTaskScore() {
        return taskScore;
    }

    public void setTaskScore(Double taskScore) {
        this.taskScore = taskScore;
    }

    public Integer getReturnReasonId() {
        return returnReasonId;
    }

    public void setReturnReasonId(Integer returnReasonId) {
        this.returnReasonId = returnReasonId;
    }

    @ColumnTransient
    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }

    public String getDepartmentRevertDate() {
        return departmentRevertDate;
    }

    public void setDepartmentRevertDate(String departmentRevertDate) {
        this.departmentRevertDate = departmentRevertDate;
    }

    public String getDepartmentReleaseDate() {
        return departmentReleaseDate;
    }

    public void setDepartmentReleaseDate(String departmentReleaseDate) {
        this.departmentReleaseDate = departmentReleaseDate;
    }

    public String getDepartmentReturnDate() {
        return departmentReturnDate;
    }

    public void setDepartmentReturnDate(String departmentReturnDate) {
        this.departmentReturnDate = departmentReturnDate;
    }

    public String getGroupFinishPeriod() {
        return groupFinishPeriod;
    }

    public void setGroupFinishPeriod(String groupFinishPeriod) {
        this.groupFinishPeriod = groupFinishPeriod;
    }

    public String getOneFinishPeriod() {
        return oneFinishPeriod;
    }

    public void setOneFinishPeriod(String oneFinishPeriod) {
        this.oneFinishPeriod = oneFinishPeriod;
    }

    public String getTwoFinishPeriod() {
        return twoFinishPeriod;
    }

    public void setTwoFinishPeriod(String twoFinishPeriod) {
        this.twoFinishPeriod = twoFinishPeriod;
    }

    public String getThreeFinishPeriod() {
        return threeFinishPeriod;
    }

    public void setThreeFinishPeriod(String threeFinishPeriod) {
        this.threeFinishPeriod = threeFinishPeriod;
    }

    public Double getTaskTwoScore() {
        return taskTwoScore;
    }

    public void setTaskTwoScore(Double taskTwoScore) {
        this.taskTwoScore = taskTwoScore;
    }

    public Double getTaskThreeScore() {
        return taskThreeScore;
    }

    public void setTaskThreeScore(Double taskThreeScore) {
        this.taskThreeScore = taskThreeScore;
    }

    public Integer getTwoReturnCount() {
        return twoReturnCount;
    }

    public void setTwoReturnCount(Integer twoReturnCount) {
        this.twoReturnCount = twoReturnCount;
    }

    public Integer getThreeReturnCount() {
        return threeReturnCount;
    }

    public void setThreeReturnCount(Integer threeReturnCount) {
        this.threeReturnCount = threeReturnCount;
    }

    public Integer getReturnState() {
        return returnState;
    }

    public void setReturnState(Integer returnState) {
        this.returnState = returnState;
    }

    public Integer getRecordReturnCount() {
        return recordReturnCount;
    }

    public void setRecordReturnCount(Integer recordReturnCount) {
        this.recordReturnCount = recordReturnCount;
    }

    public String getRecordRevertDate() {
        return recordRevertDate;
    }

    public void setRecordRevertDate(String recordRevertDate) {
        this.recordRevertDate = recordRevertDate;
    }

    public String getRecordCheckDate() {
        return recordCheckDate;
    }

    public void setRecordCheckDate(String recordCheckDate) {
        this.recordCheckDate = recordCheckDate;
    }

    public String getRecordInspectionUser() {
        return recordInspectionUser;
    }

    public void setRecordInspectionUser(String recordInspectionUser) {
        this.recordInspectionUser = recordInspectionUser;
    }

    public Integer getWorkGroupEmployeeId() {
        return workGroupEmployeeId;
    }

    public void setWorkGroupEmployeeId(Integer workGroupEmployeeId) {
        this.workGroupEmployeeId = workGroupEmployeeId;
    }

    public Integer getOneInspectionUserId() {
        return oneInspectionUserId;
    }

    public void setOneInspectionUserId(Integer oneInspectionUserId) {
        this.oneInspectionUserId = oneInspectionUserId;
    }

    public Integer getTwoInspectionUserId() {
        return twoInspectionUserId;
    }

    public void setTwoInspectionUserId(Integer twoInspectionUserId) {
        this.twoInspectionUserId = twoInspectionUserId;
    }

    public Integer getThreeInspectionUserId() {
        return threeInspectionUserId;
    }

    public void setThreeInspectionUserId(Integer threeInspectionUserId) {
        this.threeInspectionUserId = threeInspectionUserId;
    }

    public Integer getRecordInspectionUserId() {
        return recordInspectionUserId;
    }

    public void setRecordInspectionUserId(Integer recordInspectionUserId) {
        this.recordInspectionUserId = recordInspectionUserId;
    }

    public String getOutCheckUser() {
        return outCheckUser;
    }

    public void setOutCheckUser(String outCheckUser) {
        this.outCheckUser = outCheckUser;
    }

    public Integer getOutCheckUserId() {
        return outCheckUserId;
    }

    public void setOutCheckUserId(Integer outCheckUserId) {
        this.outCheckUserId = outCheckUserId;
    }

    public String getOutCheckRevertDate() {
        return outCheckRevertDate;
    }

    public void setOutCheckRevertDate(String outCheckRevertDate) {
        this.outCheckRevertDate = outCheckRevertDate;
    }

    public String getOutCheckFinishDate() {
        return outCheckFinishDate;
    }

    public void setOutCheckFinishDate(String outCheckFinishDate) {
        this.outCheckFinishDate = outCheckFinishDate;
    }

    public String getOutCheckFinishPeriod() {
        return outCheckFinishPeriod;
    }

    public void setOutCheckFinishPeriod(String outCheckFinishPeriod) {
        this.outCheckFinishPeriod = outCheckFinishPeriod;
    }

    public Integer getOutCheckState() {
        return outCheckState;
    }

    public void setOutCheckState(Integer outCheckState) {
        this.outCheckState = outCheckState;
    }

    public String getOutCheckDescription() {
        return outCheckDescription;
    }

    public void setOutCheckDescription(String outCheckDescription) {
        this.outCheckDescription = outCheckDescription;
    }

    public String getOutCheckDate() {
        return outCheckDate;
    }

    public void setOutCheckDate(String outCheckDate) {
        this.outCheckDate = outCheckDate;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getInvalidFlag() {
        return invalidFlag;
    }

    public void setInvalidFlag(Integer invalidFlag) {
        this.invalidFlag = invalidFlag;
    }

    public Integer getThreeReturnTwoFlag() {
        return threeReturnTwoFlag;
    }

    public void setThreeReturnTwoFlag(Integer threeReturnTwoFlag) {
        this.threeReturnTwoFlag = threeReturnTwoFlag;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    public Integer getPrintUserId() {
        return printUserId;
    }

    public void setPrintUserId(Integer printUserId) {
        this.printUserId = printUserId;
    }

    public String getPrintDate() {
        return printDate;
    }

    public void setPrintDate(String printDate) {
        this.printDate = printDate;
    }

    public String getDataDeliveryDate() {
        return dataDeliveryDate;
    }

    public void setDataDeliveryDate(String dataDeliveryDate) {
        this.dataDeliveryDate = dataDeliveryDate;
    }

    public Integer getPrintFlag() {
        return printFlag;
    }

    public void setPrintFlag(Integer printFlag) {
        this.printFlag = printFlag;
    }

    public String getPrintRemark() {
        return printRemark;
    }

    public void setPrintRemark(String printRemark) {
        this.printRemark = printRemark;
    }

    public Integer getDataRevertUserId() {
        return dataRevertUserId;
    }

    public void setDataRevertUserId(Integer dataRevertUserId) {
        this.dataRevertUserId = dataRevertUserId;
    }

    public String getDataRevertRemark() {
        return dataRevertRemark;
    }

    public void setDataRevertRemark(String dataRevertRemark) {
        this.dataRevertRemark = dataRevertRemark;
    }

    public String getPrintOperateDate() {
        return printOperateDate;
    }

    public void setPrintOperateDate(String printOperateDate) {
        this.printOperateDate = printOperateDate;
    }

    public String getDataOperateDate() {
        return dataOperateDate;
    }

    public void setDataOperateDate(String dataOperateDate) {
        this.dataOperateDate = dataOperateDate;
    }

    public Integer getDataDeliveryFlag() {
        return dataDeliveryFlag;
    }

    public void setDataDeliveryFlag(Integer dataDeliveryFlag) {
        this.dataDeliveryFlag = dataDeliveryFlag;
    }

    @ColumnTransient
    public String getPrintUserName() {
        return printUserName;
    }

    public void setPrintUserName(String printUserName) {
        this.printUserName = printUserName;
    }

    @ColumnTransient
    public String getDataRevertUserName() {
        return dataRevertUserName;
    }

    public void setDataRevertUserName(String dataRevertUserName) {
        this.dataRevertUserName = dataRevertUserName;
    }

    public Integer getStopCount() {
        return stopCount;
    }

    public void setStopCount(Integer stopCount) {
        this.stopCount = stopCount;
    }

    public Integer getAssistFlag() {
        return assistFlag;
    }

    public void setAssistFlag(Integer assistFlag) {
        this.assistFlag = assistFlag;
    }

    public Integer getPrintFinishFlag() {
        return printFinishFlag;
    }

    public void setPrintFinishFlag(Integer printFinishFlag) {
        this.printFinishFlag = printFinishFlag;
    }

    public String getOneInspectionRemark() {
        return oneInspectionRemark;
    }

    public void setOneInspectionRemark(String oneInspectionRemark) {
        this.oneInspectionRemark = oneInspectionRemark;
    }

    public String getTwoInspectionRemark() {
        return twoInspectionRemark;
    }

    public void setTwoInspectionRemark(String twoInspectionRemark) {
        this.twoInspectionRemark = twoInspectionRemark;
    }

    public String getThreeInspectionRemark() {
        return threeInspectionRemark;
    }

    public void setThreeInspectionRemark(String threeInspectionRemark) {
        this.threeInspectionRemark = threeInspectionRemark;
    }

    public String getDepartmentRemark() {
        return departmentRemark;
    }

    public void setDepartmentRemark(String departmentRemark) {
        this.departmentRemark = departmentRemark;
    }

    public String getGroupRemark() {
        return groupRemark;
    }

    public void setGroupRemark(String groupRemark) {
        this.groupRemark = groupRemark;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGroupReturnRemark() {
        return groupReturnRemark;
    }

    public void setGroupReturnRemark(String groupReturnRemark) {
        this.groupReturnRemark = groupReturnRemark;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", taskName=").append(taskName);
        sb.append(", departmentCode=").append(departmentCode);
        sb.append(", employeeId=").append(employeeId);
        sb.append(", projectId=").append(projectId);
        sb.append(", taskStateId=").append(taskStateId);
        sb.append(", overDateCount=").append(overDateCount);
        sb.append(", oneInspectionUser=").append(oneInspectionUser);
        sb.append(", twoInspectionUser=").append(twoInspectionUser);
        sb.append(", threeInspectionUser=").append(threeInspectionUser);
        sb.append(", oneRevertDate=").append(oneRevertDate);
        sb.append(", twoRevertDate=").append(twoRevertDate);
        sb.append(", threeRevertDate=").append(threeRevertDate);
        sb.append(", oneCheckDate=").append(oneCheckDate);
        sb.append(", twoCheckDate=").append(twoCheckDate);
        sb.append(", threeCheckDate=").append(threeCheckDate);
        sb.append(", assignTaskUser=").append(assignTaskUser);
        sb.append(", workGroupCode=").append(workGroupCode);
        sb.append(", workGroupUser=").append(workGroupUser);
        sb.append(", groupRevertDate=").append(groupRevertDate);
        sb.append(", groupCommitDate=").append(groupCommitDate);
        sb.append(", taskScore=").append(taskScore);
        sb.append(", returnReasonId=").append(returnReasonId);
        sb.append(", departmentRevertDate=").append(departmentRevertDate);
        sb.append(", departmentReleaseDate=").append(departmentReleaseDate);
        sb.append(", departmentReturnDate=").append(departmentReturnDate);
        sb.append(", groupFinishPeriod=").append(groupFinishPeriod);
        sb.append(", oneFinishPeriod=").append(oneFinishPeriod);
        sb.append(", twoFinishPeriod=").append(twoFinishPeriod);
        sb.append(", threeFinishPeriod=").append(threeFinishPeriod);
        sb.append(", taskTwoScore=").append(taskTwoScore);
        sb.append(", taskThreeScore=").append(taskThreeScore);
        sb.append(", twoReturnCount=").append(twoReturnCount);
        sb.append(", threeReturnCount=").append(threeReturnCount);
        sb.append(", returnState=").append(returnState);
        sb.append(", recordReturnCount=").append(recordReturnCount);
        sb.append(", recordRevertDate=").append(recordRevertDate);
        sb.append(", recordCheckDate=").append(recordCheckDate);
        sb.append(", recordInspectionUser=").append(recordInspectionUser);
        sb.append(", workGroupEmployeeId=").append(workGroupEmployeeId);
        sb.append(", oneInspectionUserId=").append(oneInspectionUserId);
        sb.append(", twoInspectionUserId=").append(twoInspectionUserId);
        sb.append(", threeInspectionUserId=").append(threeInspectionUserId);
        sb.append(", recordInspectionUserId=").append(recordInspectionUserId);
        sb.append(", outCheckUser=").append(outCheckUser);
        sb.append(", outCheckUserId=").append(outCheckUserId);
        sb.append(", outCheckRevertDate=").append(outCheckRevertDate);
        sb.append(", outCheckFinishDate=").append(outCheckFinishDate);
        sb.append(", outCheckFinishPeriod=").append(outCheckFinishPeriod);
        sb.append(", outCheckState=").append(outCheckState);
        sb.append(", outCheckDescription=").append(outCheckDescription);
        sb.append(", outCheckDate=").append(outCheckDate);
        sb.append(", flag=").append(flag);
        sb.append(", invalidFlag=").append(invalidFlag);
        sb.append(", threeReturnTwoFlag=").append(threeReturnTwoFlag);
        sb.append(", fileNumber=").append(fileNumber);
        sb.append(", printUserId=").append(printUserId);
        sb.append(", printDate=").append(printDate);
        sb.append(", dataDeliveryDate=").append(dataDeliveryDate);
        sb.append(", printFlag=").append(printFlag);
        sb.append(", printRemark=").append(printRemark);
        sb.append(", dataRevertUserId=").append(dataRevertUserId);
        sb.append(", dataRevertRemark=").append(dataRevertRemark);
        sb.append(", printOperateDate=").append(printOperateDate);
        sb.append(", dataOperateDate=").append(dataOperateDate);
        sb.append(", dataDeliveryFlag=").append(dataDeliveryFlag);
        sb.append(", stopCount=").append(stopCount);
        sb.append(", assistFlag=").append(assistFlag);
        sb.append(", printFinishFlag=").append(printFinishFlag);
        sb.append(", oneInspectionRemark=").append(oneInspectionRemark);
        sb.append(", twoInspectionRemark=").append(twoInspectionRemark);
        sb.append(", threeInspectionRemark=").append(threeInspectionRemark);
        sb.append(", departmentRemark=").append(departmentRemark);
        sb.append(", groupRemark=").append(groupRemark);
        sb.append(", remark=").append(remark);
        sb.append("]");
        return sb.toString();
    }
}