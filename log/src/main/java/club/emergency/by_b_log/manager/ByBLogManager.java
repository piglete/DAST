package club.emergency.by_b_log.manager;

import club.emergency.by_b_log.model.ByBLog;
import club.map.core.manager.GenericManager;
import club.map.core.model.Page;

/**
 * 日志管理接口
 */
public interface ByBLogManager extends GenericManager<ByBLog, Integer> {
    /**
     * 分页查询
     *
     * @param loginName
     * @param moduleName
     * @param operationName
     * @param ip
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page search(String loginName, String moduleName, String operationName, String ip, Integer pageNo, Integer pageSize);

    /**
     * 删除
     *
     * @param ids
     */
    void removeByIds(String ids);

    /**
     * 新增
     *
     * @param sysLog
     */
    void upperSave(ByBLog sysLog);
}