package club.emergency.project.mapper;

import club.emergency.project.mapper.sqlprovider.ByPlusTaskDepartmentAssistProvider;
import club.emergency.project.model.ByPlusTaskDepartmentAssist;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ByPlusTaskDepartmentAssistMapper extends GenericMapper<ByPlusTaskDepartmentAssist, Integer> {

    @Update("update by_plus_task_department_assist set department_revert_date = #{arg0},state_flag = 1 \n" +
            "where task_id = #{arg1} and department_code = #{arg2} and using_type = 1")
    void updateRevertTime(String nowTime, Integer taskId, String departmentCode);

    @Update("update by_plus_task_department_assist set department_finish_date = #{arg0},state_flag = 2,\n" +
            "department_finish_period = #{arg1},group_code = #{arg2},group_revert_user_id = #{arg3} \n" +
            "where id = #{arg4} and using_type = 1")
    void updateFinishTime(String finishTime, String finishPeriod, String assistGroupCode, Integer groupUserId, Integer assistId);

    @Update("update by_plus_task_department_assist set release_date = #{arg0},state_flag = 0 \n" +
            "where id = #{arg1} and using_type = 1")
    void updateReleaseDate(String releaseDate, Integer assistId);

    @Update("update by_plus_task_department_assist set return_count = #{arg0},finish_flag = 0,state_flag = 9 \n" +
            "where id = #{arg1} and using_type = 1")
    void updateReturnCountAndFinishFlag(Integer newReturnCount, Integer departmentAssistId);

    @Select("select id as id,task_id as taskId,department_code as departmentCode,revert_user_id as revertUserId,\n" +
            "return_count as returnCount,department_revert_date as departmentRevertDate,group_code as groupCode,\n" +
            "group_revert_user_id as groupRevertUserId,group_revert_date as groupRevertDate,\n" +
            "one_inspection_revert_date as oneInspectionRevertDate,one_inspection_user_id as oneInspectionUserId \n" +
            "from by_plus_task_department_assist \n" +
            "where id = #{arg0} and using_type = 1")
    ByPlusTaskDepartmentAssist searchInfo(Integer departmentAssistId);

    @Select("select count(id) from by_plus_task_department_assist \n" +
            "where task_id = #{arg0} and finish_flag = #{arg1} and \n" +
            "using_type = 1")
    int searchFinishCount(Integer id, Integer finishFlag);

    @Select("select id as id,task_id as taskId,department_code as departmentCode,revert_user_id as revertUserId,\n" +
            "return_count as returnCount,department_revert_date as departmentRevertDate \n" +
            "from by_plus_task_department_assist \n" +
            "where task_id = #{arg0} and using_type = 1")
    List<ByPlusTaskDepartmentAssist> searchList(Integer id);

    @Update("update by_plus_task_department_assist set group_revert_date = #{arg0},state_flag = 3 \n" +
            "where group_code = #{arg1} and task_id = #{arg2} and using_type = 1")
    void updateGroupRevert(String addTime, String groupCode, Integer taskId);

    @Update("update by_plus_task_department_assist set group_finish_date = #{arg0},group_finish_period = #{arg1},\n" +
            "state_flag = 4 \n" +
            "where id = #{arg2} and using_type = 1")
    void updateGroupFinish(String groupFinishDate, String groupFinishPeriod, Integer assistId);

    @Update("update by_plus_task_department_assist set one_inspection_user_id = #{arg0},\n" +
            "one_inspection_revert_date = #{arg1},state_flag = 5 \n" +
            "where task_id = #{arg2} and department_code = #{arg3} and using_type = 1")
    void updateAssistOneInspectionRevertTime(Integer oneInspectionUserId, String nowTime, Integer taskId, String departmentCode);

    @Update("update by_plus_task_department_assist set one_inspection_finish_date = #{arg0},\n" +
            "one_inspection_finish_period = #{arg1},state_flag = 6\n" +
            "where id = #{arg2} and using_type = 1")
    void updateAssistOneInspectionFinishTime(String finishDate, String oneInspectionFinishPeriod, Integer assistId);

    @Update("update by_plus_task_department_assist set state_flag = 7 \n" +
            "where task_id = #{arg0} and department_code = #{arg1} and using_type = 1")
    void updateStateFlag(Integer taskId, String departmentCode);

    @Update("update by_plus_task_department_assist set state_flag = 8,finish_flag = 1 \n" +
            "where id = #{arg0} and using_type = 1")
    void updateStateFlagById(Integer assistId);

    @SelectProvider(type = ByPlusTaskDepartmentAssistProvider.class, method = "getList")
    List<ByPlusTaskDepartmentAssist> search(String departmentCode, Integer employeeId, String workGroupCode, Integer workGroupEmployeeId, Integer oneInspectionUserId);
}