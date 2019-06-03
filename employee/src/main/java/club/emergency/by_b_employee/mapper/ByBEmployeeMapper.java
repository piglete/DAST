package club.emergency.by_b_employee.mapper;

import club.emergency.by_b_employee.model.ByBEmployee;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

@Mapper
public interface ByBEmployeeMapper extends GenericMapper<ByBEmployee, Integer> {

    @Update("update by_b_employee set position_state = #{arg0}, leave_date = #{arg1} WHERE id = #{arg2} and using_type = 1")
    void updatePositionById(String positionState, String leaveDate, Integer id);

    @Select("select id as id,username as username from by_b_employee where department_code = #{arg0} and using_type = 1")
    List<ByBEmployee> getUsernameAndIdByDepartmentCode(String code);

    @Update("update by_b_employee set icon = #{arg0},modify_time = #{arg1} where id = #{arg2} and using_type = 1")
    void updateIconById(String icon, Date modifyTime, Integer id);

    @Select("select id as id,username as username from by_b_employee \n" +
            "where position_state = #{arg0} \n" +
            "and department_code like concat(#{arg1},'%') \n" +
            "and using_type = 1")
    List<ByBEmployee> searchUsername(String positionState ,String departmentCode);

    @Select("select id as id,username as username from by_b_employee where position_state = #{arg0} and is_leader = #{arg1} and using_type = 1")
    List<ByBEmployee> searchUsernameByIsLeader(String positionState, String isLeader);

    @Select("select id as id,username as username from by_b_employee where department_code = #{arg0} and position_state = #{arg1} and is_leader = #{arg2} and using_type = 1")
    ByBEmployee searchIdByDepartmentCodeInfo(String departmentCode, String positionState, String isLeader);

    @Select("select username as username from by_b_employee \n" +
            "where position_state = #{arg0} \n" +
            "and is_group_leader = #{arg1} \n" +
            "and department_code = #{arg2} \n" +
            "and using_type = 1")
    String searchGroupUsername(String positionState, String isGroupLeader, String code);

    @Select("select id as id,username as username from by_b_employee \n" +
            "where position_state = #{arg0} \n" +
            "and is_group_leader = #{arg1} \n" +
            "and department_code = #{arg2} \n" +
            "and using_type = 1")
    ByBEmployee searchGroupUsernameAndId(String positionState, String isGroupLeader, String code);

    @Select("select username as username,department_code as departmentCode from by_b_employee \n" +
            "where id = #{arg0} and using_type = 1")
    ByBEmployee searchUsernameAndDepartmentCodeById(Integer messageCreateId);

    @Select("select id as id,username as username,department_code as departmentCode," +
            "sex as sex,is_leader as isLeader from by_b_employee \n" +
            "where id = #{arg0} and using_type = 1")
    ByBEmployee searchInfoById(Integer employeeId);
}