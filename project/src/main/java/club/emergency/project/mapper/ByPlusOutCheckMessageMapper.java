package club.emergency.project.mapper;

import club.emergency.project.model.ByPlusOutCheckMessage;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ByPlusOutCheckMessageMapper extends GenericMapper<ByPlusOutCheckMessage, Integer> {

    @Update("update by_plus_out_check_message set using_type = 0 where task_id = #{arg0} and using_type = 1")
    void removeByTaskId(Integer taskId);

    @Select("select task_name as taskName from by_plus_task where id = #{arg0} and using_type = 1")
    String searchTaskName(Integer taskId);

    @Select("select username as username from by_b_employee where id = #{arg0} and using_type = 1")
    String searchUsername(Integer twoCheckUserId);
}