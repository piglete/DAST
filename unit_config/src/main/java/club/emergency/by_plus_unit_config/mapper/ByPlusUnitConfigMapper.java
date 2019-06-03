package club.emergency.by_plus_unit_config.mapper;

import club.emergency.by_plus_unit_config.model.ByPlusUnitConfig;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ByPlusUnitConfigMapper extends GenericMapper<ByPlusUnitConfig, Integer> {
    @Select("select * from by_plus_unit_config where record_type_id =(select record_type_id from by_plus_project where id=(select project_id from by_plus_task where id=#{arg0} and using_type=1) and item_application_id=#{arg1} and unit_type_id=#{arg2}  and using_type=1) and using_type=1")
    ByPlusUnitConfig search(Integer taskId,Integer itemApplicationIdm,Integer unitTypeId);
    @Select("select * from by_plus_unit_config where record_type_id =(select record_type_id from by_plus_project where id=(select project_id from by_plus_task where id=#{arg0} and using_type=1) and using_type=1) and using_type=1")
    List<ByPlusUnitConfig> searchByTaskId(Integer taskId);
}