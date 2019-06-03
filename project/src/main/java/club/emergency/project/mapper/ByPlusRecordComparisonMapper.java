package club.emergency.project.mapper;

import club.emergency.project.model.ByPlusRecordComparison;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ByPlusRecordComparisonMapper extends GenericMapper<ByPlusRecordComparison, Integer> {

    @Update("update by_plus_record_comparison set exist_flag = 1 where id = #{arg0} and using_type = 1")
    void updateExistFlag(Integer id);

    @Update("update by_plus_record_comparison set using_type = 0 where project_id = #{arg0} and using_type = 1")
    void removeByProjectId(Integer projectId);
}