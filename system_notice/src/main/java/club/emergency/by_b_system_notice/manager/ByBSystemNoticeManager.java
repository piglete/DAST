package club.emergency.by_b_system_notice.manager;

import club.emergency.by_b_system_notice.model.ByBSystemNotice;
import club.map.core.manager.GenericManager;
import club.map.core.model.Page;

/**
 * 系统通知消息管理接口
 */
public interface ByBSystemNoticeManager extends GenericManager<ByBSystemNotice, Integer> {
    /**
     * 系统通知消息分页查询
     *
     * @param content
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page search(String content, Integer pageNo, Integer pageSize);

    /**
     * 系统通知消息编辑(修改和新增,通过id是否为 'null' 判断)
     *
     * @param byBSystemNotice
     */
    void upperSave(ByBSystemNotice byBSystemNotice);

    /**
     * 系统通知消息删除
     *
     * @param ids
     */
    void removeByIds(String ids);
}