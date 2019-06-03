package club.emergency.project.manager;

import club.emergency.project.model.ByPlusRecordReturn;
import club.map.core.manager.GenericManager;

import java.util.List;

/**
 * 档案回退管理接口
 */
public interface ByPlusRecordReturnManager extends GenericManager<ByPlusRecordReturn, Integer> {
    /**
     * 查询方法
     *
     * @param taskId
     * @param recordReturnReasonId
     * @return
     */
    List<ByPlusRecordReturn> search(Integer taskId, Integer recordReturnReasonId);

    /**
     * 编辑方法
     *
     * @param byPlusRecordReturn
     */
    void upperSave(ByPlusRecordReturn byPlusRecordReturn);

    /**
     * 批量删除方法
     *
     * @param ids
     */
    void removeByIds(String ids);

    /**
     * 详情查询方法
     *
     * @param id
     * @return
     */
    ByPlusRecordReturn searchDetail(Integer id);
}