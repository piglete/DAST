package club.emergency.project.model;

import club.map.core.mapper.ColumnTransient;
import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("by_r_record")
public class ByRRecord extends RootObject {
    private Integer projectId;//项目id

    private String projectName;//项目名称(档案部自己创建的)

    private Integer recordTypeId;//档案类型id

    private String recordTypeName;//档案类型

    private String fileNumber;//档案号

    private String dataNumber;//资料编号

    private String departmentCode;//来源部门code

    private String departmentName;//部门名称

    private String companyName;//单位名称

    private Integer retentionPeriodId;//保管期限id(字典)

    private String retentionPeriod;//保管期限

    private Integer volumeNumber;//卷数

    private Integer pageNumber;//页数

    private String scrollPerson;//立卷人

    private String fileDate;//归档日期

    private String examiner;//审核人

    private String responsiblePerson;//责任者

    private Integer rankTypeId;//密级id(字典)

    private String rankType;//密级

    private String remark;//备注

    private Integer isStoreroom;//是否入库(0表示未入库,1表示已入库)

    private Integer yearType;//年份

    private Integer monthType;//月份

    private String regionCode;//区域code

    private String regionName;//区域名称

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    public String getDataNumber() {
        return dataNumber;
    }

    public void setDataNumber(String dataNumber) {
        this.dataNumber = dataNumber;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getRetentionPeriodId() {
        return retentionPeriodId;
    }

    public void setRetentionPeriodId(Integer retentionPeriodId) {
        this.retentionPeriodId = retentionPeriodId;
    }

    @ColumnTransient
    public String getRetentionPeriod() {
        return retentionPeriod;
    }

    public void setRetentionPeriod(String retentionPeriod) {
        this.retentionPeriod = retentionPeriod;
    }

    public Integer getVolumeNumber() {
        return volumeNumber;
    }

    public void setVolumeNumber(Integer volumeNumber) {
        this.volumeNumber = volumeNumber;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getScrollPerson() {
        return scrollPerson;
    }

    public void setScrollPerson(String scrollPerson) {
        this.scrollPerson = scrollPerson;
    }

    public String getFileDate() {
        return fileDate;
    }

    public void setFileDate(String fileDate) {
        this.fileDate = fileDate;
    }

    public String getExaminer() {
        return examiner;
    }

    public void setExaminer(String examiner) {
        this.examiner = examiner;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public Integer getRankTypeId() {
        return rankTypeId;
    }

    public void setRankTypeId(Integer rankTypeId) {
        this.rankTypeId = rankTypeId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsStoreroom() {
        return isStoreroom;
    }

    public void setIsStoreroom(Integer isStoreroom) {
        this.isStoreroom = isStoreroom;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @ColumnTransient
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @ColumnTransient
    public String getRankType() {
        return rankType;
    }

    public void setRankType(String rankType) {
        this.rankType = rankType;
    }

    public String getRecordTypeName() {
        return recordTypeName;
    }

    public void setRecordTypeName(String recordTypeName) {
        this.recordTypeName = recordTypeName;
    }

    public Integer getYearType() {
        return yearType;
    }

    public void setYearType(Integer yearType) {
        this.yearType = yearType;
    }

    public Integer getMonthType() {
        return monthType;
    }

    public void setMonthType(Integer monthType) {
        this.monthType = monthType;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Integer getRecordTypeId() {
        return recordTypeId;
    }

    public void setRecordTypeId(Integer recordTypeId) {
        this.recordTypeId = recordTypeId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", projectId=").append(projectId);
        sb.append(", projectName=").append(projectName);
        sb.append(", fileNumber=").append(fileNumber);
        sb.append(", dataNumber=").append(dataNumber);
        sb.append(", departmentCode=").append(departmentCode);
        sb.append(", companyName=").append(companyName);
        sb.append(", retentionPeriodId=").append(retentionPeriodId);
        sb.append(", volumeNumber=").append(volumeNumber);
        sb.append(", pageNumber=").append(pageNumber);
        sb.append(", scrollPerson=").append(scrollPerson);
        sb.append(", fileDate=").append(fileDate);
        sb.append(", examiner=").append(examiner);
        sb.append(", responsiblePerson=").append(responsiblePerson);
        sb.append(", rankTypeId=").append(rankTypeId);
        sb.append(", remark=").append(remark);
        sb.append(", isStoreroom=").append(isStoreroom);
        sb.append(", recordTypeId=").append(recordTypeId);
        sb.append(", recordTypeName=").append(recordTypeName);
        sb.append(", yearType=").append(yearType);
        sb.append(", monthType=").append(monthType);
        sb.append(", regionCode=").append(regionCode);
        sb.append(", regionName=").append(regionName);
        sb.append("]");
        return sb.toString();
    }
}