package club.emergency.equipment.manager;

import club.emergency.equipment.model.ByBEquipmentRepair;
import club.map.core.manager.GenericManager;
import club.map.core.model.Page;

public interface ByBEquipmentRepairManager extends GenericManager<ByBEquipmentRepair, Integer> {
    Page search(String name, String departmentName, Integer equipmentTypeId, Integer pageNo, Integer pageSize);

    void upperSave(ByBEquipmentRepair byBEquipmentRepair);

    void removeByIds(String ids);

    ByBEquipmentRepair searchDetail(Integer id);
}