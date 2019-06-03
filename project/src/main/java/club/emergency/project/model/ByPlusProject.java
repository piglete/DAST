package club.emergency.project.model;

import club.map.core.mapper.ColumnTransient;
import club.map.core.model.RootObject;
import club.map.core.model.TableName;

import java.math.BigDecimal;

@TableName("by_plus_project")
public class ByPlusProject extends RootObject {

    private String name;//项目名称

    private String startDate;//项目计划开始日期

    private String finishDate;//项目计划结束日期

    private String address;//项目地址

    private Integer projectStateId;//项目状态id

    private String projectState;//项目状态

    private Integer recordTypeId;//项目类型id

    private String recordTypeName;//项目类型名称

    private String recordIconUrl;//项目类型图标

    private String regionCode;//区域code

    private String regionName;//区域名称

    private String regionFullName;//区域全称

    private String unitName;//甲方单位名称

    private Integer projectCostId;//项目费用支付状态id

    private String projectCost;//项目费用支付状态

    private Integer dataStateId;//资料交付状态id

    private String dataState;//资料交付状态

    private String dataCommit;//资料提交情况

    private String linkman;//联系人

    private String telephone;//联系方式

    private String longitude;//经度

    private String latitude;//纬度

    private Integer projectPeriod;//项目周期(天)

    private String projectAddUser;//项目创建人

    private Integer projectAddUserId;//项目创建人id

    private String storageUser;//入库用户

    private String storageDate;//入库时间

    private String addDate;//项目创建时间

    private Integer flag;//项目完成标识(0为进行中,1为已完成)

    private Integer taskReturnCount;//该项目下任务回退数量(0为没有回退,其他为回退次数)

    private Integer taskFlag;//任务下达标识(0为未下达,1为已下达)

    private Integer count;//数量

    private Double taskScore;//分数

    private Integer overDateCount;//超期天数

    private String groupCode;//小组code

    private ByPlusTask byPlusTask;//项目对应任务信息

    private ByRRecord byRRecord;//项目对应档案信息

    private String taskGroupCode;//任务小组code

    private String taskGroupName;//任务小组名称

    private BigDecimal externalOutput;//关联任务外部产值

    private BigDecimal internalOutput;//关联任务内部产值

    private String department;//关联任务作业小组部门

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getProjectStateId() {
        return projectStateId;
    }

    public void setProjectStateId(Integer projectStateId) {
        this.projectStateId = projectStateId;
    }

    public Integer getRecordTypeId() {
        return recordTypeId;
    }

    public void setRecordTypeId(Integer recordTypeId) {
        this.recordTypeId = recordTypeId;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    @ColumnTransient
    public String getRecordTypeName() {
        return recordTypeName;
    }

    public void setRecordTypeName(String recordTypeName) {
        this.recordTypeName = recordTypeName;
    }

    @ColumnTransient
    public String getRecordIconUrl() {
        return recordIconUrl;
    }

    public void setRecordIconUrl(String recordIconUrl) {
        this.recordIconUrl = recordIconUrl;
    }

    @ColumnTransient
    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @ColumnTransient
    public String getRegionFullName() {
        return regionFullName;
    }

    public void setRegionFullName(String regionFullName) {
        this.regionFullName = regionFullName;
    }
    @ColumnTransient
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Integer getProjectCostId() {
        return projectCostId;
    }

    public void setProjectCostId(Integer projectCostId) {
        this.projectCostId = projectCostId;
    }

    public String getDataCommit() {
        return dataCommit;
    }

    public void setDataCommit(String dataCommit) {
        this.dataCommit = dataCommit;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getDataStateId() {
        return dataStateId;
    }

    public void setDataStateId(Integer dataStateId) {
        this.dataStateId = dataStateId;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @ColumnTransient
    public String getProjectState() {
        return projectState;
    }

    public void setProjectState(String projectState) {
        this.projectState = projectState;
    }

    @ColumnTransient
    public String getProjectCost() {
        return projectCost;
    }

    public void setProjectCost(String projectCost) {
        this.projectCost = projectCost;
    }

    @ColumnTransient
    public String getDataState() {
        return dataState;
    }

    public void setDataState(String dataState) {
        this.dataState = dataState;
    }
    @ColumnTransient
    public BigDecimal getExternalOutput() {
        return externalOutput;
    }

    public void setExternalOutput(BigDecimal externalOutput) {
        this.externalOutput = externalOutput;
    }
    @ColumnTransient
    public BigDecimal getInternalOutput() {
        return internalOutput;
    }

    public void setInternalOutput(BigDecimal internalOutput) {
        this.internalOutput = internalOutput;
    }
    public Integer getProjectPeriod() {
        return projectPeriod;
    }

    public void setProjectPeriod(Integer projectPeriod) {
        this.projectPeriod = projectPeriod;
    }

    public String getProjectAddUser() {
        return projectAddUser;
    }

    public void setProjectAddUser(String projectAddUser) {
        this.projectAddUser = projectAddUser;
    }

    public String getStorageUser() {
        return storageUser;
    }

    public void setStorageUser(String storageUser) {
        this.storageUser = storageUser;
    }

    public String getStorageDate() {
        return storageDate;
    }

    public void setStorageDate(String storageDate) {
        this.storageDate = storageDate;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public Integer getProjectAddUserId() {
        return projectAddUserId;
    }

    public void setProjectAddUserId(Integer projectAddUserId) {
        this.projectAddUserId = projectAddUserId;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getTaskReturnCount() {
        return taskReturnCount;
    }

    public void setTaskReturnCount(Integer taskReturnCount) {
        this.taskReturnCount = taskReturnCount;
    }

    public Integer getTaskFlag() {
        return taskFlag;
    }

    public void setTaskFlag(Integer taskFlag) {
        this.taskFlag = taskFlag;
    }

    @ColumnTransient
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @ColumnTransient
    public Double getTaskScore() {
        return taskScore;
    }

    public void setTaskScore(Double taskScore) {
        this.taskScore = taskScore;
    }

    @ColumnTransient
    public Integer getOverDateCount() {
        return overDateCount;
    }

    public void setOverDateCount(Integer overDateCount) {
        this.overDateCount = overDateCount;
    }

    @ColumnTransient
    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    @ColumnTransient
    public ByPlusTask getByPlusTask() {
        return byPlusTask;
    }

    public void setByPlusTask(ByPlusTask byPlusTask) {
        this.byPlusTask = byPlusTask;
    }

    @ColumnTransient
    public ByRRecord getByRRecord() {
        return byRRecord;
    }

    public void setByRRecord(ByRRecord byRRecord) {
        this.byRRecord = byRRecord;
    }

    @ColumnTransient
    public String getTaskGroupCode() {
        return taskGroupCode;
    }

    public void setTaskGroupCode(String taskGroupCode) {
        this.taskGroupCode = taskGroupCode;
    }

    @ColumnTransient
    public String getTaskGroupName() {
        return taskGroupName;
    }

    public void setTaskGroupName(String taskGroupName) {
        this.taskGroupName = taskGroupName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", name=").append(name);
        sb.append(", startDate=").append(startDate);
        sb.append(", finishDate=").append(finishDate);
        sb.append(", address=").append(address);
        sb.append(", projectStateId=").append(projectStateId);
        sb.append(", recordTypeId=").append(recordTypeId);
        sb.append(", regionCode=").append(regionCode);
        sb.append(", unitName=").append(unitName);
        sb.append(", projectCostId=").append(projectCostId);
        sb.append(", dataCommit=").append(dataCommit);
        sb.append(", linkman=").append(linkman);
        sb.append(", telephone=").append(telephone);
        sb.append(", dataStateId=").append(dataStateId);
        sb.append(", longitude=").append(longitude);
        sb.append(", latitude=").append(latitude);
        sb.append(", projectPeriod=").append(projectPeriod);
        sb.append(", projectAddUser=").append(projectAddUser);
        sb.append(", projectAddUserId=").append(projectAddUserId);
        sb.append(", storageUser=").append(storageUser);
        sb.append(", storageDate=").append(storageDate);
        sb.append(", addDate=").append(addDate);
        sb.append(", flag=").append(flag);
        sb.append(", taskReturnCount=").append(taskReturnCount);
        sb.append(", taskFlag=").append(taskFlag);
        sb.append("]");
        return sb.toString();
    }
}