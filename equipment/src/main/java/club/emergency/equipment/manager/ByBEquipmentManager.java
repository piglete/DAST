package club.emergency.equipment.manager;

import club.emergency.equipment.model.ByBEquipment;
import club.map.core.manager.GenericManager;
import club.map.core.model.Page;

/**
 * 固定资产管理接口
 */
public interface ByBEquipmentManager extends GenericManager<ByBEquipment, Integer> {
    /**
     * 编辑
     *
     * @param byBEquipment
     */
    void upperSave(ByBEquipment byBEquipment);

    /**
     * 删除
     *
     * @param ids
     */
    void removeByIds(String ids);

    /**
     * 分页查询
     *
     * @param assetName
     * @param assetModel
     * @param storeTypeId
     * @param equipmentStateId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page search(String assetName, String assetModel, Integer storeTypeId, Integer equipmentStateId, Integer pageNo, Integer pageSize);

    /**
     * 单个详情查询
     *
     * @param id
     * @return
     */
    ByBEquipment searchDetail(Integer id);
}