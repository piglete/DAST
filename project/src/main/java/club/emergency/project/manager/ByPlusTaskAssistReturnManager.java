package club.emergency.project.manager;

import club.emergency.project.model.ByPlusTaskAssistReturn;
import club.map.core.manager.GenericManager;

import java.util.List;

/**
 * 外协部门回退管理接口
 */
public interface ByPlusTaskAssistReturnManager extends GenericManager<ByPlusTaskAssistReturn, Integer> {
    /**
     * 查询全部
     *
     * @param taskId
     * @param departmentAssistId
     * @param departmentCode
     * @return
     */
    List<ByPlusTaskAssistReturn> search(Integer taskId, Integer departmentAssistId, String departmentCode);

    /**
     * 编辑方法
     *
     * @param byPlusTaskAssistReturn
     */
    void upperSave(ByPlusTaskAssistReturn byPlusTaskAssistReturn);

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    ByPlusTaskAssistReturn searchDetail(Integer id);

    /**
     * 删除
     *
     * @param ids
     */
    void removeByIds(String ids);
}