package club.emergency.project.manager;

import club.emergency.project.model.ByPlusMessage;
import club.map.core.manager.GenericManager;

import java.util.List;

/**
 * 通知消息管理接口
 */
public interface ByPlusMessageManager extends GenericManager<ByPlusMessage, Integer> {
    /**
     * 通知消息新增
     *
     * @param byPlusMessage
     */
    void upperSave(ByPlusMessage byPlusMessage);

    /**
     * 通知消息查询
     *
     * @param projectId
     * @param messageUserId
     * @param messageRoleId
     * @param taskStateId
     * @param departmentCode
     * @return
     */
    List<ByPlusMessage> search(Integer projectId, Integer messageUserId, Integer messageRoleId, Integer taskStateId, String departmentCode);

    /**
     * 通知消息删除
     *
     * @param ids
     */
    void removeByIds(String ids);

    /**
     * 根据任务id删除
     *
     * @param id
     */
    void removeByTaskId(Integer id);
}