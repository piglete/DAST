package club.emergency.project.manager;

import club.emergency.project.model.ByPlusTipMessage;
import club.map.core.manager.GenericManager;

import java.util.List;

/**
 * 项目提示消息管理
 */
public interface ByPlusTipMessageManager extends GenericManager<ByPlusTipMessage, Integer> {
    /**
     * 提示消息新增
     *
     * @param byPlusTipMessage
     */
    void upperSave(ByPlusTipMessage byPlusTipMessage);

    /**
     * 提示消息删除
     *
     * @param projectId
     */
    void deleteMessageByProject(Integer projectId);

    /**
     * 提示消息查询
     *
     * @param projectId
     * @return
     */
    List<ByPlusTipMessage> search(Integer projectId);
}