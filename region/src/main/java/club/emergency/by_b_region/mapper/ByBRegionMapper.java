package club.emergency.by_b_region.mapper;

import club.emergency.by_b_region.model.ByBRegion;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ByBRegionMapper extends GenericMapper<ByBRegion, Integer> {

    @Select("select max(code) from by_b_region where code like #{arg0} and parent_id = #{arg1} and using_type = 1")
    String queryMaxCodeByParentId(String code, Integer parentId);

    @Select("select max(code) from by_b_region where code like '10%' and using_type = 1")
    String queryMaxCodeForRoot();

    @Select("select count(*) from by_b_region where code like CONCAT(#{arg0},'%') and parent_id = #{arg1} and using_type = 1")
    Integer queryMaxSortNumByParentId(String code, Integer parentId);

    @Select("select id as id,code as code,name as name,full_name as fullName from by_b_region where code = #{arg0} and using_type = 1")
    ByBRegion searchByCode(String code);

    @Select("select code as code from by_b_region where name = #{arg0} and using_type = 1")
    String searchCodeByName(String regionName);
}