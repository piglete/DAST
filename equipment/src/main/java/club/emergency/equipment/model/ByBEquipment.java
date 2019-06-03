package club.emergency.equipment.model;

import club.map.core.mapper.ColumnTransient;
import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("by_b_equipment")
public class ByBEquipment extends RootObject {
    private String assetName;//资产名称

    private String assetModel;//资产型号

    private Integer unitTypeId;//单位id

    private String unitType;//单位

    private Integer equipmentCount;//数量

    private Double unitPrice;//单价

    private Integer storeTypeId;//存放地点id(字典表存储)

    private String  storeTypeName;//存放地点

    private Integer equipmentStateId;//现状id(字典表存储)

    private String equipmentStateName;//现状

    private String usePerson;//使用人

    private String responsiblePerson;//负责人

    private String remark;//备注

    private String checkDate;//盘点日期

    private String checkPerson;//盘点人

    private String auditor;//审核人

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

    public Integer getUnitTypeId() {
        return unitTypeId;
    }

    public void setUnitTypeId(Integer unitTypeId) {
        this.unitTypeId = unitTypeId;
    }

    public Integer getEquipmentCount() {
        return equipmentCount;
    }

    public void setEquipmentCount(Integer equipmentCount) {
        this.equipmentCount = equipmentCount;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getStoreTypeId() {
        return storeTypeId;
    }

    public void setStoreTypeId(Integer storeTypeId) {
        this.storeTypeId = storeTypeId;
    }

    public Integer getEquipmentStateId() {
        return equipmentStateId;
    }

    public void setEquipmentStateId(Integer equipmentStateId) {
        this.equipmentStateId = equipmentStateId;
    }

    public String getUsePerson() {
        return usePerson;
    }

    public void setUsePerson(String usePerson) {
        this.usePerson = usePerson;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @ColumnTransient
    public String getStoreTypeName() {
        return storeTypeName;
    }

    public void setStoreTypeName(String storeTypeName) {
        this.storeTypeName = storeTypeName;
    }

    @ColumnTransient
    public String getEquipmentStateName() {
        return equipmentStateName;
    }

    public void setEquipmentStateName(String equipmentStateName) {
        this.equipmentStateName = equipmentStateName;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public String getCheckPerson() {
        return checkPerson;
    }

    public void setCheckPerson(String checkPerson) {
        this.checkPerson = checkPerson;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    @ColumnTransient
    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", assetName=").append(assetName);
        sb.append(", assetModel=").append(assetModel);
        sb.append(", unitTypeId=").append(unitTypeId);
        sb.append(", equipmentCount=").append(equipmentCount);
        sb.append(", unitPrice=").append(unitPrice);
        sb.append(", storeTypeId=").append(storeTypeId);
        sb.append(", equipmentStateId=").append(equipmentStateId);
        sb.append(", usePerson=").append(usePerson);
        sb.append(", responsiblePerson=").append(responsiblePerson);
        sb.append(", remark=").append(remark);
        sb.append(", checkDate=").append(checkDate);
        sb.append(", checkPerson=").append(checkPerson);
        sb.append(", auditor=").append(auditor);
        sb.append("]");
        return sb.toString();
    }
}