package club.emergency.project.manager;

import club.emergency.project.model.ByPlusTaskWorkload;
import club.map.core.manager.GenericManager;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 任务工作量管理接口
 */
public interface ByPlusTaskWorkloadManager extends GenericManager<ByPlusTaskWorkload, Integer> {
    /**
     * 任务工作量查询
     *
     * @param taskId
     * @param unitTypeId
     * @param itemApplicationId
     * @param departmentCode
     * @param flag
     * @return
     */
    List<ByPlusTaskWorkload> search(Integer taskId, Integer unitTypeId, Integer itemApplicationId, String departmentCode, Integer flag);

    /**
     * 工作量编辑(新增或者修改)
     *
     * @param byPlusTaskWorkload
     */
    void upperSave(ByPlusTaskWorkload byPlusTaskWorkload, BigDecimal internalVal);

    /**
     * 任务工作量详情
     *
     * @param id
     * @return
     */
    ByPlusTaskWorkload searchById(Integer id);

    /**
     * 任务工作量删除
     *
     * @param ids
     */
    void removeByIds(String ids);

    /**
     * 任务工作量分组查询
     *
     * @param taskId
     * @param departmentCode
     * @param startDate
     * @param endDate
     * @return
     */
    Map<String, Object> searchGroupWorkGroup(Integer taskId, String departmentCode, String startDate, String endDate);

    /**
     * 任务工作量按条件导出
     *
     * @param taskId
     * @param departmentCode
     * @param groupCode
     * @param oneInspectionUserId
     * @param twoInspectionUserId
     * @param threeInspectionUserId
     * @param itemApplicationId
     * @param flag
     * @param startDate
     * @param endDate
     * @param response
     */
    void taskWorkloadExport(Integer taskId, String departmentCode, String groupCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId, Integer itemApplicationId, Integer flag, String startDate, String endDate, HttpServletResponse response);
}