package club.emergency.equipment.model;

import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("by_b_equipment_repair")
public class ByBEquipmentRepair extends RootObject {
    private Integer equipmentRegistrationId;//设备登记id

    private Integer equipmentTypeId;//设备类别id(字典表)

    private String equipmentTypeName;//设备类别

    private String name;//设备名称

    private String model;//设备型号

    private String equipmentNumber;//设备编号

    private Integer count;//数量

    private String unit;//单位

    private String repairDate;//维修时间

    private String replaceInformation;//更换主要零部件

    private Integer repairFee;//维修费（元）

    private String departmentName;//部门名称

    private String responsiblePerson;//部门负责人

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

    public String getRepairDate() {
        return repairDate;
    }

    public void setRepairDate(String repairDate) {
        this.repairDate = repairDate;
    }

    public String getReplaceInformation() {
        return replaceInformation;
    }

    public void setReplaceInformation(String replaceInformation) {
        this.replaceInformation = replaceInformation;
    }

    public Integer getRepairFee() {
        return repairFee;
    }

    public void setRepairFee(Integer repairFee) {
        this.repairFee = repairFee;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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
        sb.append(", equipmentNumber=").append(equipmentNumber);
        sb.append(", count=").append(count);
        sb.append(", repairDate=").append(repairDate);
        sb.append(", replaceInformation=").append(replaceInformation);
        sb.append(", repairFee=").append(repairFee);
        sb.append(", departmentName=").append(departmentName);
        sb.append(", responsiblePerson=").append(responsiblePerson);
        sb.append(", remark=").append(remark);
        sb.append(", unit=").append(unit);
        sb.append("]");
        return sb.toString();
    }
}