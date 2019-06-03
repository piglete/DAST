package club.emergency.project.manager;

import club.emergency.project.model.ByPlusTwoReturn;
import club.map.core.manager.GenericManager;

import java.util.List;

/**
 * 二检回退管理接口
 */
public interface ByPlusTwoReturnManager extends GenericManager<ByPlusTwoReturn, Integer> {
    /**
     * 根据任务id查询全部
     *
     * @param taskId
     * @return
     */
    List<ByPlusTwoReturn> search(Integer taskId);

    /**
     * 编辑方法
     *
     * @param byPlusTwoReturn
     */
    void upperSave(ByPlusTwoReturn byPlusTwoReturn);

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
    ByPlusTwoReturn searchDetails(Integer id);
}