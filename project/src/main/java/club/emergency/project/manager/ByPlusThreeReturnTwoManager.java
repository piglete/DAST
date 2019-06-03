package club.emergency.project.manager;

import club.emergency.project.model.ByPlusThreeReturnTwo;
import club.map.core.manager.GenericManager;

import java.util.List;

/**
 * 三检回退二检管理
 */
public interface ByPlusThreeReturnTwoManager extends GenericManager<ByPlusThreeReturnTwo, Integer> {
    /**
     * 编辑方法
     *
     * @param byPlusThreeReturnTwo
     */
    void upperSave(ByPlusThreeReturnTwo byPlusThreeReturnTwo);

    /**
     * 根据任务id查询所有
     *
     * @param taskId
     * @return
     */
    List<ByPlusThreeReturnTwo> search(Integer taskId);

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
    ByPlusThreeReturnTwo searchDetail(Integer id);
}