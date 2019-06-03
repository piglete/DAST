package club.emergency.project.manager;

import club.emergency.project.model.ByPlusTaskStop;
import club.map.core.manager.GenericManager;

import java.util.List;

/**
 * 任务暂停管理接口
 */
public interface ByPlusTaskStopManager extends GenericManager<ByPlusTaskStop, Integer> {
    /**
     * 任务暂停信息查询
     *
     * @param taskId
     * @param stopOperatorId
     * @return
     */
    List<ByPlusTaskStop> search(Integer taskId, Integer stopOperatorId);

    /**
     * 任务暂停编辑
     *
     * @param byPlusTaskStop
     */
    void upperSave(ByPlusTaskStop byPlusTaskStop);

    /**
     * 当前任务暂停信息详情
     *
     * @param taskId
     * @param stopOrder
     * @return
     */
    ByPlusTaskStop searchDetail(Integer taskId, Integer stopOrder);

    /**
     * 暂停信息删除
     *
     * @param ids
     */
    void removeByIds(String ids);
}