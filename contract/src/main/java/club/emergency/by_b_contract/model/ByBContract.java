package club.emergency.by_b_contract.model;

import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("by_b_contract")
public class ByBContract extends RootObject {
    private String contractNumber;//合同编号

    private String companyName;//单位名称

    private String projectName;//项目名称

    private String signTime;//签订日期

    private String putFileTime;//入档日期

    private Float contractAmount;//合同金额

    private String contractType;//合同类型

    private Integer contractTypeId;//合同类型id

    private String projectType;//项目类型

    private Integer projectTypeId;//项目类型id

    private String contractFile;//电子附件

    private String storeAddress;//存放位置

    private String remark;//描述

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    public String getPutFileTime() {
        return putFileTime;
    }

    public void setPutFileTime(String putFileTime) {
        this.putFileTime = putFileTime;
    }

    public Float getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(Float contractAmount) {
        this.contractAmount = contractAmount;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getContractFile() {
        return contractFile;
    }

    public void setContractFile(String contractFile) {
        this.contractFile = contractFile;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getContractTypeId() {
        return contractTypeId;
    }

    public void setContractTypeId(Integer contractTypeId) {
        this.contractTypeId = contractTypeId;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public Integer getProjectTypeId() {
        return projectTypeId;
    }

    public void setProjectTypeId(Integer projectTypeId) {
        this.projectTypeId = projectTypeId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", contractNumber=").append(contractNumber);
        sb.append(", companyName=").append(companyName);
        sb.append(", projectName=").append(projectName);
        sb.append(", signTime=").append(signTime);
        sb.append(", putFileTime=").append(putFileTime);
        sb.append(", contractAmount=").append(contractAmount);
        sb.append(", contractType=").append(contractType);
        sb.append(", contractTypeId=").append(contractTypeId);
        sb.append(", projectType=").append(projectType);
        sb.append(", projectTypeId=").append(projectTypeId);
        sb.append(", contractFile=").append(contractFile);
        sb.append(", storeAddress=").append(storeAddress);
        sb.append(", remark=").append(remark);
        sb.append("]");
        return sb.toString();
    }
}