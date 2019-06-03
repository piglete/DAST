package club.emergency.project.mapper;

import club.emergency.project.model.ByPlusTipMessage;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ByPlusTipMessageMapper extends GenericMapper<ByPlusTipMessage, Integer> {

    @Update("update by_plus_tip_message set using_type = 0 where project_id = #{arg0} and using_type = 1")
    void removeMessage(Integer projectId);

    @Select("select name as name from by_plus_project where id = #{arg0} and using_type = 1")
    String searchProjectName(Integer projectId);
}