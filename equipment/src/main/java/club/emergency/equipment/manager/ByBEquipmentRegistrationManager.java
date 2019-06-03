package club.emergency.equipment.manager;

import club.emergency.equipment.model.ByBEquipmentRegistration;
import club.map.core.manager.GenericManager;
import club.map.core.model.Page;

/**
 * 设备管理接口
 */
public interface ByBEquipmentRegistrationManager extends GenericManager<ByBEquipmentRegistration, Integer> {
    /**
     * 分页查询
     *
     * @param equipmentName
     * @param equipmentModel
     * @param equipmentTypeId
     * @param equipmentStateId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page search(String equipmentName, String equipmentModel, Integer equipmentTypeId, Integer equipmentStateId, Integer pageNo, Integer pageSize);

    /**
     * 编辑
     *
     * @param byBEquipmentRegistration
     */
    void upperSave(ByBEquipmentRegistration byBEquipmentRegistration);

    /**
     * 单个详情查询
     *
     * @param id
     * @return
     */
    ByBEquipmentRegistration searchDetail(Integer id);

    /**
     * 删除
     *
     * @param ids
     */
    void removeByIds(String ids);
}