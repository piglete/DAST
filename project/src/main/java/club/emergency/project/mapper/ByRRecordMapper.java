package club.emergency.project.mapper;

import club.emergency.project.model.ByRRecord;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ByRRecordMapper extends GenericMapper<ByRRecord, Integer> {

    @Update("update by_r_record set is_storeroom = 1 where id = #{arg0} and using_type = 1")
    void updateIsStoreroomById(Integer recordId);

    @Select("select count(*) from by_r_record where record_type_id = #{arg0} and year_type = #{arg1} and using_type = 1")
    int selectRecordTypeCount(Integer recordTypeId, Integer yearFlag);

    @Select("select count(*) from by_r_record where month_type = #{arg0} and year_type = #{arg1} and using_type = 1")
    int searchMonthType(Integer month, Integer yearFlag);

    @Select("select count(*) from by_r_record where region_code = #{arg0} and year_type = #{arg1} and using_type = 1")
    int searchRegionType(String regionCode, Integer yearFlag);
}