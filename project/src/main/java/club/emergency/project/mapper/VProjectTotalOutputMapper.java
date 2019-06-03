package club.emergency.project.mapper;

import club.emergency.project.model.VProjectOutput;
import club.emergency.project.model.VProjectTotalOutput;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface VProjectTotalOutputMapper extends GenericMapper<VProjectTotalOutput, Integer> {
    @Select("select * from v_project_output where project_id = #{arg0}")
    List<VProjectOutput> getByProjectId(Integer projectId);
}