package club.emergency.project.mapper;

import club.emergency.project.model.ByPlusMessage;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ByPlusMessageMapper extends GenericMapper<ByPlusMessage, Integer> {

    @Update("update by_plus_message set using_type = 0 where task_id = #{arg0} and using_type = 1")
    void removeByTaskId(Integer id);

    @Select("select task_name as taskName from by_plus_task where id = #{arg0} and using_type = 1")
    String getTaskNameByTaskId(Integer taskId);
}