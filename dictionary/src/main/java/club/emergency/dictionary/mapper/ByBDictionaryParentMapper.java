package club.emergency.dictionary.mapper;

import club.emergency.dictionary.model.ByBDictionaryParent;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ByBDictionaryParentMapper extends GenericMapper<ByBDictionaryParent, Integer> {

    @Select("select max(code) from by_b_dictionary_parent where code like #{arg0} and parent_id = #{arg1} and using_type = 1")
    String queryMaxCodeByParentId(String s, Integer parentId);

    @Select("select alias as alias from by_b_dictionary_parent where code = #{arg0} and using_type = 1")
    ByBDictionaryParent searchByCode(String code);

    @Select("select code as code from by_b_dictionary_parent where alias = #{arg0} and parent_id = 7 and using_type = 1")
    String searchByAlias(String alias);
}