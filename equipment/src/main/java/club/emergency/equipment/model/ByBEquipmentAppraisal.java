package club.emergency.equipment.model;

import club.map.core.mapper.ColumnTransient;
import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("by_b_equipment_appraisal")
public class ByBEquipmentAppraisal extends RootObject {

    private Integer equipmentId;//仪器编号

    private String assetName;//资产名称

    private String assetModel;//资产型号

    private String unit;//单位

    private Integer equipmentCount;//数量

    private String storeTypeName;//存放地点

    private String departmentCode;//所属部门code

    private String departmentName;//部门名称

    private String responsiblePerson;//负责人

    private String usePerson;//使用人

    private Integer equipmentStateId;//现状id

    private String equipmentState;//现状

    private String inventoryCondition;//清点状况

    private String remark;//备注

    private String inventoryDate;//清点日期

    private String inventoryPerson;//清点人

    public Integer getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Integer equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetModel() {
        return assetModel;
    }

    public void setAssetModel(String assetModel) {
        this.assetModel = assetModel;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getEquipmentCount() {
        return equipmentCount;
    }

    public void setEquipmentCount(Integer equipmentCount) {
        this.equipmentCount = equipmentCount;
    }

    public String getStoreTypeName() {
        return storeTypeName;
    }

    public void setStoreTypeName(String storeTypeName) {
        this.storeTypeName = storeTypeName;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public String getUsePerson() {
        return usePerson;
    }

    public void setUsePerson(String usePerson) {
        this.usePerson = usePerson;
    }

    public Integer getEquipmentStateId() {
        return equipmentStateId;
    }

    public void setEquipmentStateId(Integer equipmentStateId) {
        this.equipmentStateId = equipmentStateId;
    }

    public String getInventoryCondition() {
        return inventoryCondition;
    }

    public void setInventoryCondition(String inventoryCondition) {
        this.inventoryCondition = inventoryCondition;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @ColumnTransient
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @ColumnTransient
    public String getEquipmentState() {
        return equipmentState;
    }

    public void setEquipmentState(String equipmentState) {
        this.equipmentState = equipmentState;
    }

    public String getInventoryDate() {
        return inventoryDate;
    }

    public void setInventoryDate(String inventoryDate) {
        this.inventoryDate = inventoryDate;
    }

    public String getInventoryPerson() {
        return inventoryPerson;
    }

    public void setInventoryPerson(String inventoryPerson) {
        this.inventoryPerson = inventoryPerson;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", equipmentId=").append(equipmentId);
        sb.append(", assetName=").append(assetName);
        sb.append(", assetModel=").append(assetModel);
        sb.append(", unit=").append(unit);
        sb.append(", equipmentCount=").append(equipmentCount);
        sb.append(", storeTypeName=").append(storeTypeName);
        sb.append(", departmentCode=").append(departmentCode);
        sb.append(", responsiblePerson=").append(responsiblePerson);
        sb.append(", usePerson=").append(usePerson);
        sb.append(", equipmentStateId=").append(equipmentStateId);
        sb.append(", inventoryCondition=").append(inventoryCondition);
        sb.append(", remark=").append(remark);
        sb.append(", inventoryDate=").append(inventoryDate);
        sb.append(", inventoryPerson=").append(inventoryPerson);
        sb.append("]");
        return sb.toString();
    }
}