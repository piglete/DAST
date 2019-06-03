package club.emergency.project.mapper;

import club.emergency.project.model.ByBStoreRecord;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ByBStoreRecordMapper extends GenericMapper<ByBStoreRecord, Integer>{

    @Update("update by_b_store_record set record_state = #{arg0} where id = #{arg1} and using_type = 1")
    void updateRecordStateById(String recordState, Integer id);
}