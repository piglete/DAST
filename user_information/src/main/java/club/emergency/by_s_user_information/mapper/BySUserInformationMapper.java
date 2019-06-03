package club.emergency.by_s_user_information.mapper;

import club.emergency.by_s_user_information.model.BySUserInformation;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface BySUserInformationMapper extends GenericMapper<BySUserInformation, Integer> {

    @Update("update by_s_user_information set role_id = #{arg0} where id = #{arg1} and using_type = 1")
    BySUserInformation updateRoleById(Integer roleId, Integer id);

    @Update("update by_s_user_information set password = #{arg0} where id = #{arg1} and using_type = 1")
    void updatePasswordById(String newPassword, Integer id);
}