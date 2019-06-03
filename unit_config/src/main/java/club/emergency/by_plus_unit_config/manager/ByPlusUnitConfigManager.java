package club.emergency.by_plus_unit_config.manager;

import club.emergency.by_plus_unit_config.model.ByPlusUnitConfig;
import club.map.core.manager.GenericManager;
import club.map.core.model.Page;

import java.math.BigDecimal;
import java.util.List;

public interface ByPlusUnitConfigManager extends GenericManager<ByPlusUnitConfig, Integer> {

    void upperSave(ByPlusUnitConfig byPlusUnitConfig);

//    void removeById(Integer id);

    Page search(Integer itemApplicationId, Integer workContentId, Integer pageNo, Integer pageSize);

    /**
     * 通过任务编号、申报内容、申报单位定位一条配置
     * @param taskId
     * @param itemApplicationId
     * @param unitTypeId
     * @return
     */
    ByPlusUnitConfig search(Integer taskId,Integer itemApplicationId,Integer unitTypeId);

    BigDecimal workloadOperation(String unitPrice,String count);

    List<ByPlusUnitConfig> searchByTaskId(Integer taskId);

    void removeByIds(String ids);
}