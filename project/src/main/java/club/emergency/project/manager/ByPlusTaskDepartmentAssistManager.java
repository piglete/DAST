package club.emergency.project.manager;

import club.emergency.project.model.ByPlusTaskDepartmentAssist;
import club.map.core.manager.GenericManager;

import java.util.List;

/**
 * 外协任务管理接口
 */
public interface ByPlusTaskDepartmentAssistManager extends GenericManager<ByPlusTaskDepartmentAssist, Integer> {
    /**
     * 外协任务查询
     *
     * @param taskId
     * @param departmentCode
     * @param groupCode
     * @param revertUserId
     * @param groupRevertUserId
     * @param flag
     * @param stateFlag
     * @param finishFlag
     * @return
     */
    List<ByPlusTaskDepartmentAssist> search(Integer taskId, String departmentCode, String groupCode, Integer revertUserId, Integer groupRevertUserId, Integer flag, Integer stateFlag, Integer finishFlag);

    /**
     * 外协任务编辑(新增和修改)
     *
     * @param byPlusTaskDepartmentAssist
     * @return
     */
    int upperSave(ByPlusTaskDepartmentAssist byPlusTaskDepartmentAssist);

    /**
     * 外协任务详情查询
     *
     * @param id
     * @return
     */
    ByPlusTaskDepartmentAssist searchDetail(Integer id);

    /**
     * 外协任务删除
     *
     * @param ids
     */
    void removeByIds(String ids);
}