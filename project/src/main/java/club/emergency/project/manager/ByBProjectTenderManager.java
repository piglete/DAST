package club.emergency.project.manager;

import club.emergency.project.model.ByBProjectTender;
import club.map.core.manager.GenericManager;
import club.map.core.model.Page;

/**
 * 项目标书管理接口
 */
public interface ByBProjectTenderManager extends GenericManager<ByBProjectTender, Integer> {
    /**
     * 分页查询方法
     *
     * @param projectName
     * @param agentCompany
     * @param openTime
     * @param openAddress
     * @param bond
     * @param bondTime
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page search(String projectName, String agentCompany, String openTime, String openAddress, String bond, String bondTime, Integer pageNo, Integer pageSize);

    /**
     * 编辑方法(新增和修改)
     *
     * @param byBProjectTender
     */
    void upperSave(ByBProjectTender byBProjectTender);

    /**
     * 批量删除
     *
     * @param ids
     */
    void removeByIds(String ids);
}