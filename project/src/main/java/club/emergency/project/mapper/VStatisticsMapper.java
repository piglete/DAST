package club.emergency.project.mapper;

import club.emergency.project.mapper.sqlprovider.VStatisticsProvider;
import club.emergency.project.model.VStatistics;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface VStatisticsMapper extends GenericMapper<VStatistics, Integer> {

    /*
    任务数
     */
    @SelectProvider(type=VStatisticsProvider.class,method = "taskCount")
    Object taskCount(String type, Integer year, Integer month, String regionCode, Integer recordTypeId, String departmentCode, String workGroupCode);

    /*
    任务列表
     */
    @SelectProvider(type=VStatisticsProvider.class,method = "taskInfoWithWorkloads")
    List<Map<String,Object>> taskInfoWithWorkloads(Integer year, Integer month, String regionCode, Integer recordTypeId, String departmentCode, String workGroupCode,String taskState);
    /*
    分组统计
     */
    @SelectProvider(type=VStatisticsProvider.class,method = "taskGroupByParam")
    List<Map<String,Object>> taskGroupByParam(String groupByParam, Integer year, Integer month, String regionCode, Integer recordTypeId);
}