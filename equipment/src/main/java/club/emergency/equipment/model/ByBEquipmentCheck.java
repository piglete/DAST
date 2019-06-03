package club.emergency.equipment.model;

import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("by_b_equipment_check")
public class ByBEquipmentCheck extends RootObject {
    private Integer equipmentRegistrationId;//设备登记id

    private String checkPerson;//鉴定人

    private String checkDate;//鉴定日期

    private String checkResult;//鉴定结果

    private String remark;//备注

    private Integer equipmentTypeId;//设备类别id

    private String equipmentTypeName;//设备类别

    private String name;//设备名称

    private String model;//设备型号

    private String equipmentNumber;//设备编号

    private Integer count;//数量

    private String unit;//单位

    private String purchaseDate;//采购时间

    private String durableYear;//使用年限

    private String responsiblePerson;//负责人

    private String departmentName;//部门

    public Integer getEquipmentRegistrationId() {
        return equipmentRegistrationId;
    }

    public void setEquipmentRegistrationId(Integer equipmentRegistrationId) {
        this.equipmentRegistrationId = equipmentRegistrationId;
    }

    public String getCheckPerson() {
        return checkPerson;
    }

    public void setCheckPerson(String checkPerson) {
        this.checkPerson = checkPerson;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getEquipmentTypeId() {
        return equipmentTypeId;
    }

    public void setEquipmentTypeId(Integer equipmentTypeId) {
        this.equipmentTypeId = equipmentTypeId;
    }

    public String getEquipmentTypeName() {
        return equipmentTypeName;
    }

    public void setEquipmentTypeName(String equipmentTypeName) {
        this.equipmentTypeName = equipmentTypeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getEquipmentNumber() {
        return equipmentNumber;
    }

    public void setEquipmentNumber(String equipmentNumber) {
        this.equipmentNumber = equipmentNumber;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getDurableYear() {
        return durableYear;
    }

    public void setDurableYear(String durableYear) {
        this.durableYear = durableYear;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", equipmentRegistrationId=").append(equipmentRegistrationId);
        sb.append(", checkPerson=").append(checkPerson);
        sb.append(", checkDate=").append(checkDate);
        sb.append(", checkResult=").append(checkResult);
        sb.append(", remark=").append(remark);
        sb.append(", equipmentTypeId=").append(equipmentTypeId);
        sb.append(", equipmentTypeName=").append(equipmentTypeName);
        sb.append(", name=").append(name);
        sb.append(", model=").append(model);
        sb.append(", equipmentNumber=").append(equipmentNumber);
        sb.append(", count=").append(count);
        sb.append(", unit=").append(unit);
        sb.append(", purchaseDate=").append(purchaseDate);
        sb.append(", durableYear=").append(durableYear);
        sb.append(", responsiblePerson=").append(responsiblePerson);
        sb.append(", departmentName=").append(departmentName);
        sb.append("]");
        return sb.toString();
    }
}