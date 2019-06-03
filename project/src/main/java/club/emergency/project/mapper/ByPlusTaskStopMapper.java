package club.emergency.project.mapper;

import club.emergency.project.model.ByPlusTaskStop;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ByPlusTaskStopMapper extends GenericMapper<ByPlusTaskStop, Integer> {

    @Update("update by_plus_task_stop set restart_date = #{arg0} \n" +
            "where task_id = #{arg1} and stop_order = #{arg2}")
    void updateRestartDateById(String operateDate, Integer id, Integer stopCount);
}