package club.emergency.project.mapper;

import club.emergency.project.mapper.sqlprovider.ByPlusTaskWorkloadProvider;
import club.emergency.project.model.ByPlusTaskWorkload;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface ByPlusTaskWorkloadMapper extends GenericMapper<ByPlusTaskWorkload, Integer> {

    @Select("select task_name as taskName from by_plus_task where id = #{arg0} and using_type = 1")
    String searchTaskName(Integer taskId);

    @SelectProvider(type = ByPlusTaskWorkloadProvider.class, method = "getTaskWorkloadList")
    List<ByPlusTaskWorkload> searchTaskWorkload(Integer taskId, String departmentCode, String groupCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId, Integer itemApplicationId, Integer flag, String startDate, String endDate);

}