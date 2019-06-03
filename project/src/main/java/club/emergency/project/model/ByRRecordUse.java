package club.emergency.project.model;

import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("by_r_record_use")
public class ByRRecordUse extends RootObject {

    private Integer recordId;//档案id

    private String companyName;//单位名称

    private String fileNumber;//档案号

    private String recordTypeName;//档案类型名称

    private String departmentName;//部门名称

    private String borrower;//借用人

    private String purpose;//借阅用途

    private String borrowDate;//借阅日期

    private String manager;//管理员

    private String expectedReturnDate;//应归还日期

    private String addDate;//记录添加时间

    private String remark;//备注

    private Integer storeRecordId;//档案入库记录id

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public void setExpectedReturnDate(String expectedReturnDate) {
        this.expectedReturnDate = expectedReturnDate;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    public String getRecordTypeName() {
        return recordTypeName;
    }

    public void setRecordTypeName(String recordTypeName) {
        this.recordTypeName = recordTypeName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Integer getStoreRecordId() {
        return storeRecordId;
    }

    public void setStoreRecordId(Integer storeRecordId) {
        this.storeRecordId = storeRecordId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", recordId=").append(recordId);
        sb.append(", borrower=").append(borrower);
        sb.append(", purpose=").append(purpose);
        sb.append(", borrowDate=").append(borrowDate);
        sb.append(", manager=").append(manager);
        sb.append(", expectedReturnDate=").append(expectedReturnDate);
        sb.append(", addDate=").append(addDate);
        sb.append(", remark=").append(remark);
        sb.append(", fileNumber=").append(fileNumber);
        sb.append(", recordTypeName=").append(recordTypeName);
        sb.append(", departmentName=").append(departmentName);
        sb.append(", storeRecordId=").append(storeRecordId);
        sb.append(", companyName=").append(companyName);
        sb.append("]");
        return sb.toString();
    }
}