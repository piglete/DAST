package club.emergency.project.manager;

import club.emergency.project.model.VProjectTask;
import club.emergency.project.model.VStatistics;
import club.map.core.manager.GenericManager;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface VStatisticsManager extends GenericManager<VStatistics, Integer> {
    /**
     * 当月统计页
     * @param year
     * @param month
     * @param regionCode
     * @param recordTypeId
     * @param departmentCode
     * @param workGroupCode
     * @return
     */
    Map<String,Object> taskAndScore(Integer pageNo, Integer pageSize,Integer year,Integer month,String regionCode,Integer recordTypeId,String departmentCode,String workGroupCode,String taskType);

    /**
     * 部门统计
     * @param year
     * @param month
     * @param regionCode
     * @param recordTypeId
     * @param departmentCode
     * @param workGroupCode
     * @return
     */
    Map<String,Object> departmentStatistic(Integer year,Integer month,String regionCode,Integer recordTypeId,String departmentCode,String workGroupCode);

    /**
     * 项目产值统计
     * @param vProjectTask
     * @param year
     * @param month
     * @return
     */
    List<VProjectTask> search(VProjectTask vProjectTask, Integer year, Integer month);

    /**
     * 产值信息导出
     * @param vProjectTask
     * @param year
     * @param month
     * @param response
     */
    void outPutExport(VProjectTask vProjectTask, Integer year, Integer month, HttpServletResponse response);

    /**
     * 分组统计
     * @param groupByParam 分组参数字典 VStatisticsProvider中 map
     * @param year
     * @param month
     * @param regionCode
     * @param recordTypeId
     * @return
     */
    List<Map<String,Object>> groupByParamStatistic(String groupByParam,Integer year,Integer month,String regionCode,Integer recordTypeId);
}