package club.emergency.project.manager;

import club.emergency.project.model.ByPlusTaskQuality;
import club.map.core.manager.GenericManager;

import java.util.List;

/**
 * 任务质量评定管理接口
 */
public interface ByPlusTaskQualityManager extends GenericManager<ByPlusTaskQuality, Integer> {
    /**
     * 质量评定详情
     *
     * @param taskId
     * @param checkDepartment
     * @return
     */
    ByPlusTaskQuality searchInfo(Integer taskId, Integer checkDepartment);

    /**
     * 质量评定编辑(新增和修改)
     *
     * @param byPlusTaskQuality
     * @return
     */
    String upperSave(ByPlusTaskQuality byPlusTaskQuality);

    /**
     * 质量评定删除
     *
     * @param ids
     */
    void removeByIds(String ids);

    /**
     * 质量评定初始化设置(二检接收任务时,创建一个初始评定分数,默认没有错误,得分为100)
     *
     * @param id
     * @param checkDepartment
     * @param username
     * @param addTime
     */
    void createRecord(Integer id, Integer checkDepartment, String username, String addTime);

    /**
     * 质量评定查询
     *
     * @param taskId
     * @param checkDepartment
     * @return
     */
    List<ByPlusTaskQuality> search(Integer taskId, Integer checkDepartment);
}