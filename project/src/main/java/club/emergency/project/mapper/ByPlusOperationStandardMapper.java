package club.emergency.project.mapper;

import club.emergency.project.model.ByPlusOperationStandard;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ByPlusOperationStandardMapper extends GenericMapper<ByPlusOperationStandard, Integer> {

    @Select("select max(id) from by_plus_operation_standard where using_type = 1")
    Integer searchMaxId();
}