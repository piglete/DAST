package club.emergency.auth_permission.mapper;

import club.emergency.auth_permission.model.BySRole;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BySRoleMapper extends GenericMapper<BySRole, Integer> {

    @Select("select count(id) from by_s_user_information where role_id = #{arg0} and using_type = 1")
    int searchExistUser(Integer roleId);
}