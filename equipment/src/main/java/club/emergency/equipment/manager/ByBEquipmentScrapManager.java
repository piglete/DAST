package club.emergency.equipment.manager;

import club.emergency.equipment.model.ByBEquipmentScrap;
import club.map.core.manager.GenericManager;
import club.map.core.model.Page;

public interface ByBEquipmentScrapManager extends GenericManager<ByBEquipmentScrap, Integer> {
    Page search(String name, String departmentName, Integer equipmentTypeId, Integer pageNo, Integer pageSize);

    void upperSave(ByBEquipmentScrap byBEquipmentScrap);

    ByBEquipmentScrap searchDetail(Integer id);

    void removeByIds(String ids);
}