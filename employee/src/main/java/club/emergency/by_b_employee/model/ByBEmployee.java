package club.emergency.by_b_employee.model;

import club.map.core.mapper.ColumnTransient;
import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("by_b_employee")
public class ByBEmployee extends RootObject {
    private String username;//姓名

    private String sex;//性别(男/女)

    private String telephone;//联系方式

    private String icon;//微信图标

    private String entryTime;//入职日期

    private Integer educationTypeId;//学历id

    private String educationTypeName;//学历

    private String graduated;//毕业院校

    private String idNumber;//身份证号

    private String socialSecurity;//是否缴纳社保(未缴纳/缴纳)

    private String pinyin;//拼音

    private String workStates;//转正状态(转正/未转正)

    private String promotionTime;//转正日期

    private String contractSigning;//合同签订日期

    private String contractRenewalDate;//合同续签日期

    private String expirationContract;//到期日期

    private String socialSecurityDate;//缴纳社保日期

    private String major;//专业

    private String graduatedDate;//毕业时间

    private String departmentCode;//部门code

    private String departmentName;//部门

    private String positionState;//在职状态

    private String isLeader;//是否是部门负责人

    private String isGroupLeader;//是否是小组负责人

    private Integer positionalTitleTypeId;//职称id

    private String  positionalTitleTypeName;//职称

    private Integer positionTypeId;//职位id

    private String positionTypeName;//职位

    private String workPermit;//作业证办理情况(办理/未办理)

    private String positionalTitleReviewDate;//职称评审时间

    private String accidentInsurancePayment;//意外保险缴纳(已购买/未购买)

    private String physicalExamination;//体检情况(体检/未体检)

    private String remark;//备注

    private String probationTime;//试用期时间

    private String leaveDate;//离职日期

    private String maritalStatus;//婚姻状况(已婚/未婚)

    private String workContent;//工作内容

    private String email;//邮箱

    private String emergencyContact;//紧急联系人

    private String emergencyContactTelephone;//紧急电话

    private String address;//住址

    private String isConfidentialityAgreement;//是否签订保密协议(是/否)

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public Integer getEducationTypeId() {
        return educationTypeId;
    }

    public void setEducationTypeId(Integer educationTypeId) {
        this.educationTypeId = educationTypeId;
    }

    public String getGraduated() {
        return graduated;
    }

    public void setGraduated(String graduated) {
        this.graduated = graduated;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getSocialSecurity() {
        return socialSecurity;
    }

    public void setSocialSecurity(String socialSecurity) {
        this.socialSecurity = socialSecurity;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getWorkStates() {
        return workStates;
    }

    public void setWorkStates(String workStates) {
        this.workStates = workStates;
    }

    public String getPromotionTime() {
        return promotionTime;
    }

    public void setPromotionTime(String promotionTime) {
        this.promotionTime = promotionTime;
    }

    public String getContractSigning() {
        return contractSigning;
    }

    public void setContractSigning(String contractSigning) {
        this.contractSigning = contractSigning;
    }

    public String getContractRenewalDate() {
        return contractRenewalDate;
    }

    public void setContractRenewalDate(String contractRenewalDate) {
        this.contractRenewalDate = contractRenewalDate;
    }

    public String getExpirationContract() {
        return expirationContract;
    }

    public void setExpirationContract(String expirationContract) {
        this.expirationContract = expirationContract;
    }

    public String getSocialSecurityDate() {
        return socialSecurityDate;
    }

    public void setSocialSecurityDate(String socialSecurityDate) {
        this.socialSecurityDate = socialSecurityDate;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getGraduatedDate() {
        return graduatedDate;
    }

    public void setGraduatedDate(String graduatedDate) {
        this.graduatedDate = graduatedDate;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getPositionState() {
        return positionState;
    }

    public void setPositionState(String positionState) {
        this.positionState = positionState;
    }

    public String getIsLeader() {
        return isLeader;
    }

    public void setIsLeader(String isLeader) {
        this.isLeader = isLeader;
    }

    public Integer getPositionalTitleTypeId() {
        return positionalTitleTypeId;
    }

    public void setPositionalTitleTypeId(Integer positionalTitleTypeId) {
        this.positionalTitleTypeId = positionalTitleTypeId;
    }

    public Integer getPositionTypeId() {
        return positionTypeId;
    }

    public void setPositionTypeId(Integer positionTypeId) {
        this.positionTypeId = positionTypeId;
    }

    public String getWorkPermit() {
        return workPermit;
    }

    public void setWorkPermit(String workPermit) {
        this.workPermit = workPermit;
    }

    public String getPositionalTitleReviewDate() {
        return positionalTitleReviewDate;
    }

    public void setPositionalTitleReviewDate(String positionalTitleReviewDate) {
        this.positionalTitleReviewDate = positionalTitleReviewDate;
    }

    public String getAccidentInsurancePayment() {
        return accidentInsurancePayment;
    }

    public void setAccidentInsurancePayment(String accidentInsurancePayment) {
        this.accidentInsurancePayment = accidentInsurancePayment;
    }

    public String getPhysicalExamination() {
        return physicalExamination;
    }

    public void setPhysicalExamination(String physicalExamination) {
        this.physicalExamination = physicalExamination;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProbationTime() {
        return probationTime;
    }

    public void setProbationTime(String probationTime) {
        this.probationTime = probationTime;
    }

    public String getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(String leaveDate) {
        this.leaveDate = leaveDate;
    }

    @ColumnTransient
    public String getEducationTypeName() {
        return educationTypeName;
    }

    public void setEducationTypeName(String educationTypeName) {
        this.educationTypeName = educationTypeName;
    }

    @ColumnTransient
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @ColumnTransient
    public String getPositionalTitleTypeName() {
        return positionalTitleTypeName;
    }

    public void setPositionalTitleTypeName(String positionalTitleTypeName) {
        this.positionalTitleTypeName = positionalTitleTypeName;
    }

    @ColumnTransient
    public String getPositionTypeName() {
        return positionTypeName;
    }

    public void setPositionTypeName(String positionTypeName) {
        this.positionTypeName = positionTypeName;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getWorkContent() {
        return workContent;
    }

    public void setWorkContent(String workContent) {
        this.workContent = workContent;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getEmergencyContactTelephone() {
        return emergencyContactTelephone;
    }

    public void setEmergencyContactTelephone(String emergencyContactTelephone) {
        this.emergencyContactTelephone = emergencyContactTelephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIsConfidentialityAgreement() {
        return isConfidentialityAgreement;
    }

    public void setIsConfidentialityAgreement(String isConfidentialityAgreement) {
        this.isConfidentialityAgreement = isConfidentialityAgreement;
    }

    public String getIsGroupLeader() {
        return isGroupLeader;
    }

    public void setIsGroupLeader(String isGroupLeader) {
        this.isGroupLeader = isGroupLeader;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", username=").append(username);
        sb.append(", sex=").append(sex);
        sb.append(", telephone=").append(telephone);
        sb.append(", icon=").append(icon);
        sb.append(", entryTime=").append(entryTime);
        sb.append(", educationTypeId=").append(educationTypeId);
        sb.append(", graduated=").append(graduated);
        sb.append(", idNumber=").append(idNumber);
        sb.append(", socialSecurity=").append(socialSecurity);
        sb.append(", pinyin=").append(pinyin);
        sb.append(", workStates=").append(workStates);
        sb.append(", promotionTime=").append(promotionTime);
        sb.append(", contractSigning=").append(contractSigning);
        sb.append(", contractRenewalDate=").append(contractRenewalDate);
        sb.append(", expirationContract=").append(expirationContract);
        sb.append(", socialSecurityDate=").append(socialSecurityDate);
        sb.append(", major=").append(major);
        sb.append(", graduatedDate=").append(graduatedDate);
        sb.append(", departmentCode=").append(departmentCode);
        sb.append(", positionState=").append(positionState);
        sb.append(", isLeader=").append(isLeader);
        sb.append(", isGroupLeader=").append(isGroupLeader);
        sb.append(", positionalTitleTypeId=").append(positionalTitleTypeId);
        sb.append(", positionTypeId=").append(positionTypeId);
        sb.append(", workPermit=").append(workPermit);
        sb.append(", positionalTitleReviewDate=").append(positionalTitleReviewDate);
        sb.append(", accidentInsurancePayment=").append(accidentInsurancePayment);
        sb.append(", physicalExamination=").append(physicalExamination);
        sb.append(", remark=").append(remark);
        sb.append(", probationTime=").append(probationTime);
        sb.append(", leaveDate=").append(leaveDate);
        sb.append(", maritalStatus=").append(maritalStatus);
        sb.append(", workContent=").append(workContent);
        sb.append(", email=").append(email);
        sb.append(", emergencyContact=").append(emergencyContact);
        sb.append(", emergencyContactTelephone=").append(emergencyContactTelephone);
        sb.append(", address=").append(address);
        sb.append(", isConfidentialityAgreement=").append(isConfidentialityAgreement);
        sb.append("]");
        return sb.toString();
    }
}