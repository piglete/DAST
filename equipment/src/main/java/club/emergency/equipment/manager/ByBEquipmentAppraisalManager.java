package club.emergency.equipment.manager;

import club.emergency.equipment.model.ByBEquipmentAppraisal;
import club.map.core.manager.GenericManager;
import club.map.core.model.Page;

public interface ByBEquipmentAppraisalManager extends GenericManager<ByBEquipmentAppraisal, Integer> {
    Page search(Integer equipmentStateId, String storeTypeName, String assetName, String departmentCode, Integer pageNo, Integer pageSize);

    void upperSave(ByBEquipmentAppraisal byBEquipmentAppraisal);

    void removeByIds(String ids);

    ByBEquipmentAppraisal searchDetail(Integer id);
}