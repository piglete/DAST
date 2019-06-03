package club.emergency.dictionary.mapper;

import club.emergency.dictionary.model.ByBDictionaryChild;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ByBDictionaryChildMapper extends GenericMapper<ByBDictionaryChild, Integer> {

    @Select("select id as id from by_b_dictionary_child where alias = #{arg0} and dictionary_code = '100100100' and using_type = 1")
    Integer searchRecordTypeId(String recordName);
}