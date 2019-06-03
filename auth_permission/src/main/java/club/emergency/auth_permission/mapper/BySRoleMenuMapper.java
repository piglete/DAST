package club.emergency.auth_permission.mapper;

import club.emergency.auth_permission.model.BySRoleMenu;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface BySRoleMenuMapper extends GenericMapper<BySRoleMenu, Integer> {

    @Update("update by_s_role_menu set using_type = 0 WHERE role_id = #{arg0} and using_type = 1")
    void removeByRoleId(Integer roleId);
}