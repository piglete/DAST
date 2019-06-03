package club.emergency.project.mapper;

import club.emergency.project.mapper.sqlprovider.ByPlusTaskProvider;
import club.emergency.project.model.ByPlusTask;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ByPlusTaskMapper extends GenericMapper<ByPlusTask, Integer> {

    @Update("update by_plus_task set task_state_id = #{arg0},department_revert_date = #{arg1} \n" +
            "where id = #{arg2} \n" +
            "and using_type = 1")
    void updateDepartmentRevertById(Integer taskStateId, String addTime, Integer id);

    @Update("update by_plus_task set return_reason_id = #{arg0},task_state_id = #{arg1},\n" +
            "department_return_date = #{arg2},invalid_flag = 1 \n" +
            "where id = #{arg3} \n" +
            "and using_type = 1")
    void updateTaskReturnById(Integer returnReasonId, Integer taskStateId, String addTime, Integer id);

    @Update("update by_plus_task set work_group_code = #{arg0},task_state_id = #{arg1},\n" +
            "work_group_user = #{arg2},department_release_date = #{arg3},\n" +
            "work_group_employee_id = #{arg4},department_remark = #{arg5} \n" +
            "where id = #{arg6} \n" +
            "and using_type = 1")
    void updateDepartmentReleaseTask(String code, Integer taskStateId, String workGroupUser, String addTime, Integer workGroupEmployeeId, String departmentRemark, Integer id);

    @Update("update by_plus_task set task_state_id = #{arg0},group_revert_date = #{arg1} \n" +
            "where id = #{arg2} \n" +
            "and using_type = 1")
    void updateGroupRevertById(Integer taskStateId, String addTime, Integer id);

    @Update("update by_plus_task set task_state_id = #{arg0},group_commit_date = #{arg1},\n" +
            "group_finish_period = #{arg2},group_remark = #{arg3} \n" +
            "where id = #{arg4} \n" +
            "and using_type = 1")
    void updateGroupFinishById(Integer taskStateId, String addTime, String groupFinishPeriod, String groupRemark, Integer id);

    @Update("update by_plus_task set task_state_id = #{arg0},one_revert_date = #{arg1},one_inspection_user = #{arg2},\n" +
            "one_inspection_user_id = #{arg3} \n" +
            "where id = #{arg4} \n" +
            "and using_type = 1")
    void updateOneRevertById(Integer taskStateId, String addTime, String username, Integer oneInspectionUserId, Integer id);

    @Update("update by_plus_task set task_state_id = #{arg0},one_check_date = #{arg1},\n" +
            "one_finish_period = #{arg2},file_number = #{arg3},flag = #{arg4},one_inspection_remark = #{arg5} \n" +
            "where id = #{arg6} \n" +
            "and using_type = 1")
    void updateOneCheckById(Integer taskStateId, String addTime, String oneFinishPeriod, String fileNumber, Integer flag, String oneInspectionRemark, Integer id);

    @Update("update by_plus_task set task_state_id = #{arg0},two_revert_date = #{arg1},two_inspection_user = #{arg2},\n" +
            "two_inspection_user_id = #{arg3} \n" +
            "where id = #{arg4} \n" +
            "and using_type = 1")
    void updateTwoRevertById(Integer taskStateId, String addTime, String username, Integer twoInspectionUserId, Integer id);

    @Update("update by_plus_task set task_state_id = #{arg0},two_check_date = #{arg1},\n" +
            "return_state = 0,two_finish_period = #{arg2},two_inspection_remark = #{arg3} \n" +
            "where id = #{arg4} \n" +
            "and using_type = 1")
    void updateTwoCheckById(Integer taskStateId, String addTime, String twoFinishPeriod, String twoInspectionRemark, Integer id);

    @Update("update by_plus_task set task_state_id = #{arg0},three_revert_date = #{arg1},three_inspection_user = #{arg2},\n" +
            "three_inspection_user_id = #{arg3} \n" +
            "where id = #{arg4} \n" +
            "and using_type = 1")
    void updateThreeRevertById(Integer taskStateId, String addTime, String username, Integer threeInspectionUserId, Integer id);

    @Update("update by_plus_task set task_state_id = #{arg0},three_check_date = #{arg1},three_finish_period = #{arg2},\n" +
            "return_state = 0,over_date_count = #{arg3},task_score = #{arg4},flag = 2,three_inspection_remark = #{arg5} \n" +
            "where id = #{arg6} \n" +
            "and using_type = 1")
    void updateThreeCheckById(Integer taskStateId, String addTime, String threeFinishPeriod, Integer overDayCount, Double taskScore, String threeInspectionRemark, Integer id);

    @Update("update by_plus_task set task_state_id = #{arg0},\n" +
            "return_state = 2,two_return_count = #{arg1} \n" +
            "where id = #{arg2} \n" +
            "and using_type = 1")
    void updateTwoReturnById(Integer taskStateId, Integer twoReturnCount, Integer id);

    @Update("update by_plus_task set task_state_id = #{arg0},\n" +
            "return_state = 3,three_return_count = #{arg1} \n" +
            "where id = #{arg2} \n" +
            "and using_type = 1")
    void updateThreeReturnById(Integer taskStateId, Integer threeReturnCount, Integer id);

    @Update("update by_plus_task set task_two_score = #{arg0} where id = #{arg1} and using_type = 1")
    void updateTwoScore(Double taskScore, Integer taskId);

    @Update("update by_plus_task set task_three_score = #{arg0} where id = #{arg1} and using_type = 1")
    void updateThreeScore(Double taskScore, Integer taskId);

    @Update("update by_plus_task set task_state_id = #{arg0},record_inspection_user = #{arg1},\n" +
            "record_revert_date = #{arg2},record_inspection_user_id = #{arg3} \n" +
            "where id = #{arg4} \n" +
            "and using_type = 1")
    void updateRecordRevertById(Integer taskStateId, String username, String addTime, Integer recordInspectionUserId, Integer id);

    @Update("update by_plus_task set task_state_id = #{arg0},record_check_date = #{arg1},return_state = 0,flag = 3 \n" +
            "where id = #{arg2} and using_type = 1")
    void updateRecordCheckById(Integer taskStateId, String addTime, Integer id);

    @Update("update by_plus_task set task_state_id = #{arg0},\n" +
            "return_state = 4,record_return_count = #{arg1},flag = 0 \n" +
            "where id = #{arg2} \n" +
            "and using_type = 1")
    void updateRecordReturnById(Integer taskStateId, Integer recordReturnCount, Integer id);

    @Update("update by_plus_task set out_check_state = 2,out_check_user = #{arg0},\n" +
            "out_check_user_id = #{arg1},out_check_revert_date = #{arg2} \n" +
            "where id = #{arg3} \n" +
            "and using_type = 1")
    void updateOutRevertById(String outCheckUser, Integer outCheckUserId, String addTime, Integer id);

    @Update("update by_plus_task set out_check_state = 3,out_check_description = #{arg0},\n" +
            "out_check_finish_period = #{arg1},out_check_finish_date = #{arg2} \n" +
            "where id = #{arg3} \n" +
            "and using_type = 1")
    void updateOutCheckById(String outCheckDescription, String outCheckFinishPeriod, String addTime, Integer id);

    @Update("update by_plus_task set out_check_date = #{arg0},out_check_state = 1 \n" +
            "where id = #{arg1} and using_type = 1")
    void updateRequireOutCheckById(String outCheckDate, Integer id);

    @Update("update by_plus_task set task_state_id = #{arg0},three_return_two_flag = #{arg1} \n" +
            "where id = #{arg2} and using_type = 1")
    void updateThreeReturnTwoById(Integer taskStateId, Integer threeReturnTwoCount, Integer id);

    @Update("update by_plus_task set task_state_id = #{arg0} where id = #{arg1} and using_type = 1")
    void updateTaskState(Integer taskStateId, Integer id);

    @Update("update by_plus_task set print_user_id = #{arg0},print_date = #{arg1},print_remark = #{arg2} \n" +
            "where id = #{arg3} and using_type = 1")
    void updatePrintManage(Integer printUserId, String printDate, String printRemark, Integer id);

    @Update("update by_plus_task set data_revert_user_id = #{arg0},data_delivery_date = #{arg1},\n" +
            "data_revert_remark = #{arg2} \n" +
            "where id = #{arg3} and using_type = 1")
    void updateDataDelivery(Integer dataRevertUserId, String dataDeliveryDate, String dataRevertRemark, Integer id);

    @Update("update by_plus_task set print_flag = 1,print_operate_date = #{arg0} \n" +
            "where id = #{arg1} and using_type = 1")
    void updatePrintOperateById(String addDate, Integer id);

    @Update("update by_plus_task set data_delivery_flag = 1,data_operate_date = #{arg0} \n" +
            "where id = #{arg1} and using_type = 1")
    void updateDataOperateById(String addDate, Integer id);

    @Update("update by_plus_task set flag = #{arg0},stop_count = #{arg1} \n" +
            "where id = #{arg2} and using_type = 1")
    void updateTaskFlag(Integer flag, Integer stopCount, Integer id);

    @Update("update by_plus_task set comprehensive_revert_flag = #{arg0} \n" +
            "where id = #{arg1} and using_type = 1")
    void updateComprehensiveRevertFlag(Integer comprehensiveRevertFlag, Integer id);

    @Update("update by_plus_task set assist_flag = 1 where id = #{arg0} and using_type = 1")
    void updateAssistFlagById(Integer taskId);

    @Select("select count(*) from by_plus_task where file_number = #{arg0} and using_type = 1")
    int searchByFileNumber(String fileNumber);

    @Update("update by_plus_task set print_finish_flag = 1 where id = #{arg0} and using_type = 1")
    void updatePrintFinishFlag(Integer id);

    @SelectProvider(type = ByPlusTaskProvider.class, method = "getProjectAndTaskInfo")
    List<ByPlusTask> searchProjectAndTaskInfo(String name, Integer recordTypeId, String unitName, String linkman, String telephone, Integer projectPeriod, String regionCode, String startDate, String endDate, String departmentCode, Integer taskStateId, Integer employeeId, Integer workGroupEmployeeId, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId, Integer recordInspectionUserId, Integer outCheckUserId, Integer outCheckState, String workGroupCode, Integer flag, Integer invalidFlag, Integer assistFlag);

    @Update("update by_plus_task set task_state_id = 87,department_release_date='',department_remark ='',\n" +
            "work_group_user = '',work_group_code = '',group_revert_date ='',group_return_remark = #{arg0},\n" +
            "work_group_employee_id = #{arg1} \n" +
            "where id = #{arg2} and using_type = 1")
    void updateGroupRevoke(String groupName, Integer workGroupEmployeeId, Integer id);

    @Update("update by_plus_task_workload set using_type = 0 where task_id = #{arg0} and using_type = 1")
    void removeTaskWorkload(Integer id);
    @Select("select department_code,work_group_user,work_group_code from by_plus_task where project_id = #{arg0} and using_type = 1")
    List<ByPlusTask> searchByProjectId(Integer pid);
}

