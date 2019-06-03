package club.emergency.equipment.manager;

import club.emergency.equipment.model.ByBEquipmentCheck;
import club.map.core.manager.GenericManager;
import club.map.core.model.Page;

public interface ByBEquipmentCheckManager extends GenericManager<ByBEquipmentCheck, Integer> {
    Page search(String name, Integer equipmentTypeId, String departmentName, Integer pageNo, Integer pageSize);

    void upperSave(ByBEquipmentCheck byBEquipmentCheck);

    void removeByIds(String ids);
}