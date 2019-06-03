package club.emergency.project.manager;

import club.emergency.project.model.ByPlusThreeReturn;
import club.map.core.manager.GenericManager;

import java.util.List;

/**
 * 三检回退管理
 */
public interface ByPlusThreeReturnManager extends GenericManager<ByPlusThreeReturn, Integer> {
    /**
     * 根据任务id查询全部
     *
     * @param taskId
     * @return
     */
    List<ByPlusThreeReturn> search(Integer taskId);

    /**
     * 编辑
     *
     * @param byPlusThreeReturn
     */
    void upperSave(ByPlusThreeReturn byPlusThreeReturn);

    /**
     * 删除
     *
     * @param ids
     */
    void removeByIds(String ids);

    /**
     * 查询单个详情
     *
     * @param id
     * @return
     */
    ByPlusThreeReturn searchDetail(Integer id);
}