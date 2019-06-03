package club.emergency.auth_permission.mapper;

import club.emergency.auth_permission.model.BySMenu;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BySMenuMapper extends GenericMapper<BySMenu, Integer> {

    @Select("select id as id,\n" +
            "menu_name as menuName,\n" +
            "parent_id as parentId,\n" +
            "menu_icon as menuIcon,\n" +
            "menu_url as menuUrl \n" +
            "from by_s_menu where using_type = 1")
    List<BySMenu> searchAll();

    @Select("select count(*) from by_s_menu where parent_id = #{arg0} and using_type = 1")
    Integer searchByParentId(Integer parentId);
}