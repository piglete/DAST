package club.emergency.equipment.model;

import club.map.core.mapper.ColumnTransient;
import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("by_b_equipment_registration")
public class ByBEquipmentRegistration extends RootObject {
    private Integer equipmentTypeId;//设备类别id(字典表)

    private String equipmentTypeName;//设备类别

    private String equipmentName;//设备名称

    private String equipmentModel;//设备型号

    private String equipmentNumber;//设备编号

    private Integer equipmentCount;//数量

    private String unit;//单位

    private String purchaseDate;//采购时间

    private String departmentCode;//申请部门code

    private String departmentName;//部门

    private Integer responsiblePersonId;//负责人id

    private String responsiblePersonName;//负责人

    private Integer particularYearId;//年份id(字典表)

    private String particularYear;//年份

    private String remark;//备注

    private String durableYear;//使用年限

    private Integer equipmentStateId;//现状id

    private String equipmentState;//现状

    public Integer getEquipmentTypeId() {
        return equipmentTypeId;
    }

    public void setEquipmentTypeId(Integer equipmentTypeId) {
        this.equipmentTypeId = equipmentTypeId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentModel() {
        return equipmentModel;
    }

    public void setEquipmentModel(String equipmentModel) {
        this.equipmentModel = equipmentModel;
    }

    public String getEquipmentNumber() {
        return equipmentNumber;
    }

    public void setEquipmentNumber(String equipmentNumber) {
        this.equipmentNumber = equipmentNumber;
    }

    public Integer getEquipmentCount() {
        return equipmentCount;
    }

    public void setEquipmentCount(Integer equipmentCount) {
        this.equipmentCount = equipmentCount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public Integer getResponsiblePersonId() {
        return responsiblePersonId;
    }

    public void setResponsiblePersonId(Integer responsiblePersonId) {
        this.responsiblePersonId = responsiblePersonId;
    }

    public Integer getParticularYearId() {
        return particularYearId;
    }

    public void setParticularYearId(Integer particularYearId) {
        this.particularYearId = particularYearId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @ColumnTransient
    public String getEquipmentTypeName() {
        return equipmentTypeName;
    }

    public void setEquipmentTypeName(String equipmentTypeName) {
        this.equipmentTypeName = equipmentTypeName;
    }

    @ColumnTransient
    public String getResponsiblePersonName() {
        return responsiblePersonName;
    }

    public void setResponsiblePersonName(String responsiblePersonName) {
        this.responsiblePersonName = responsiblePersonName;
    }

    @ColumnTransient
    public String getParticularYear() {
        return particularYear;
    }

    public void setParticularYear(String particularYear) {
        this.particularYear = particularYear;
    }

    @ColumnTransient
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDurableYear() {
        return durableYear;
    }

    public void setDurableYear(String durableYear) {
        this.durableYear = durableYear;
    }

    public Integer getEquipmentStateId() {
        return equipmentStateId;
    }

    public void setEquipmentStateId(Integer equipmentStateId) {
        this.equipmentStateId = equipmentStateId;
    }

    @ColumnTransient
    public String getEquipmentState() {
        return equipmentState;
    }

    public void setEquipmentState(String equipmentState) {
        this.equipmentState = equipmentState;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", equipmentTypeId=").append(equipmentTypeId);
        sb.append(", equipmentName=").append(equipmentName);
        sb.append(", equipmentModel=").append(equipmentModel);
        sb.append(", equipmentNumber=").append(equipmentNumber);
        sb.append(", equipmentCount=").append(equipmentCount);
        sb.append(", unit=").append(unit);
        sb.append(", purchaseDate=").append(purchaseDate);
        sb.append(", departmentCode=").append(departmentCode);
        sb.append(", responsiblePersonId=").append(responsiblePersonId);
        sb.append(", particularYearId=").append(particularYearId);
        sb.append(", remark=").append(remark);
        sb.append(", durableYear=").append(durableYear);
        sb.append(", equipmentStateId=").append(equipmentStateId);
        sb.append("]");
        return sb.toString();
    }
}