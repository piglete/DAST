package club.emergency.by_b_storeroom.mapper;

import club.emergency.by_b_storeroom.model.ByBStoreroom;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ByBStoreroomMapper extends GenericMapper<ByBStoreroom, Integer> {

    @Select("select max(code) from by_b_storeroom where parent_id = #{arg0} and using_type = 1")
    String queryMaxCodeByParentId(Integer parentId);

    @Select("select max(sort_num) from by_b_storeroom where parent_id = #{arg0} and using_type = 1")
    Integer queryMaxSortNumByParentId(Integer parentId);
}