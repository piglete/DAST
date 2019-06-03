package club.emergency.equipment.model;

import club.map.core.mapper.ColumnTransient;
import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("by_b_equipment_scrap")
public class ByBEquipmentScrap extends RootObject {
    private Integer equipmentRegistrationId;//设备登记id

    private Integer equipmentTypeId;//设备类别id(字典表)

    private String equipmentTypeName;//设备类别

    private String name;//资产名称

    private String model;//资产型号

    private Integer count;//数量

    private String unit;//单位

    private String departmentName;//部门

    private String responsiblePerson;//部门负责人

    private String scrapDate;//报废日期

    private String remark;//备注

    public Integer getEquipmentRegistrationId() {
        return equipmentRegistrationId;
    }

    public void setEquipmentRegistrationId(Integer equipmentRegistrationId) {
        this.equipmentRegistrationId = equipmentRegistrationId;
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public String getScrapDate() {
        return scrapDate;
    }

    public void setScrapDate(String scrapDate) {
        this.scrapDate = scrapDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        sb.append(", equipmentTypeId=").append(equipmentTypeId);
        sb.append(", equipmentTypeName=").append(equipmentTypeName);
        sb.append(", name=").append(name);
        sb.append(", model=").append(model);
        sb.append(", count=").append(count);
        sb.append(", departmentName=").append(departmentName);
        sb.append(", responsiblePerson=").append(responsiblePerson);
        sb.append(", scrapDate=").append(scrapDate);
        sb.append(", remark=").append(remark);
        sb.append(", unit=").append(unit);
        sb.append("]");
        return sb.toString();
    }
}