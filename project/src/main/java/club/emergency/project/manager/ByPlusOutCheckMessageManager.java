package club.emergency.project.manager;

import club.emergency.project.model.ByPlusOutCheckMessage;
import club.map.core.manager.GenericManager;

import java.util.List;

/**
 * 外查提示消息管理接口
 */
public interface ByPlusOutCheckMessageManager extends GenericManager<ByPlusOutCheckMessage, Integer> {
    /**
     * 查询全部
     *
     * @param taskId
     * @return
     */
    List<ByPlusOutCheckMessage> search(Integer taskId);

    /**
     * 编辑方法(新增或者修改)
     *
     * @param byPlusOutCheckMessage
     */
    void upperSave(ByPlusOutCheckMessage byPlusOutCheckMessage);

    /**
     * 删除(外查完成后,消息删除)
     *
     * @param id
     */
    void removeByTaskId(Integer id);
}