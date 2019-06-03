package club.emergency.project.mapper;

import club.emergency.project.mapper.sqlprovider.ByPlusProjectProvider;
import club.emergency.project.model.*;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import java.util.List;


@Mapper
public interface ByPlusProjectMapper extends GenericMapper<ByPlusProject, Integer> {
    @Select("select * from by_plus_project where id = #{arg0} and using_type = 1")
    ByPlusProject search(Integer id);

    @Update("update by_plus_project set project_cost_id = #{arg1} where id = #{arg0} and using_type = 1")
    void updateProjectCostById(Integer id, Integer projectCostId);

    @Update("update by_plus_project set data_state_id = #{arg1} where id = #{arg0} and using_type = 1")
    void updateDataStateById(Integer id, Integer dataStateId);

    @Update("update by_plus_project set project_state_id = #{arg0} where id = #{arg1} and using_type = 1")
    void updateProjectStateById(Integer projectStateId, Integer projectId);

    @Update("update by_plus_task set using_type = 0 where project_id = #{arg0} and using_type = 1")
    void removeTask(Integer pid);

    @Select("select id as id from by_plus_task where project_id = #{arg0} and using_type = 1")
    List<ByPlusTask> searchTask(Integer pid);

    @Update("update by_plus_message set using_type = 0 where project_id = #{arg0} and using_type = 1")
    void removeMessage(Integer taskId);

    @Update("update by_plus_tip_message set using_type = 0 where project_id = #{arg0} and using_type = 1")
    void removeTipMessage(Integer pid);

    @Select("select id as id,file_url as fileUrl,file_rename as fileRename from by_plus_document where project_id = #{arg0} and using_type = 1")
    List<ByPlusDocument> searchDocument(Integer pid);

    @Update("update by_plus_document set using_type = 0 where project_id = #{arg0} and using_type = 1")
    void removeDocument(Integer pid);

    @Select("select id as id,file_url as fileUrl,file_rename as fileRename from by_plus_task_file where project_id = #{arg0} and using_type = 1")
    List<ByPlusTaskFile> searchTaskFile(Integer taskId);

    @Update("update by_plus_task_file set using_type = 0 where project_id = #{arg0} and using_type = 1")
    void removeTaskFile(Integer taskId);

    @Select("select id as id,file_url as fileUrl,file_rename as fileRename from by_plus_storage_file where project_id = #{arg0} and using_type = 1")
    List<ByPlusStorageFile> searchStorageFile(Integer pid);

    @Update("update by_plus_storage_file set using_type = 0 where project_id = #{arg0} and using_type = 1")
    void removeStorageFile(Integer pid);

    @Update("update by_plus_out_check_message set using_type = 0 where task_id = #{arg0} and using_type = 1")
    void removeOutCheckMessage(Integer taskId);

    @Update("update by_plus_pay set using_type = 0 where project_id = #{arg0} and using_type = 1")
    void removePay(Integer pid);

    @Update("update by_plus_record_return set using_type = 0 where task_id = #{arg0} and using_type = 1")
    void removeRecordReturn(Integer taskId);

    @Update("update by_plus_task_quality set using_type = 0 where task_id = #{arg0} and using_type = 1")
    void removeTaskQuality(Integer taskId);

    @Update("update by_plus_task_workload set using_type = 0 where task_id = #{arg0} and using_type = 1")
    void removeTaskWorkload(Integer taskId);

    @Update("update by_plus_two_return set using_type = 0 where task_id = #{arg0} and using_type = 1")
    void removeTwoReturn(Integer taskId);

    @Update("update by_plus_three_return set using_type = 0 where task_id = #{arg0} and using_type = 1")
    void removeThreeReturn(Integer taskId);

    @Select("select id as id,project_id as projectId from by_plus_task where department_code = #{arg0} and using_type = 1")
    List<ByPlusTask> searchTaskByDepartment(String departmentCode);

    @Select("select id as id,project_id as projectId from by_plus_task where work_group_code = #{arg0} and using_type = 1")
    List<ByPlusTask> searchTaskByGroupCode(String groupCode);

    @Update("update by_plus_project set project_state_id = #{arg0},flag = #{arg2} where id = #{arg1} and using_type = 1")
    void updateProjectStateAndFlagById(Integer projectStateId, Integer projectId, Integer flag);

    @Update("update by_plus_project set task_flag = 0,task_return_count = #{arg0} where id = #{arg1} and using_type = 1")
    void updateTaskReturnCountById(Integer taskReturnCount, Integer projectId);

    @Update("update by_plus_project set task_flag = #{arg0},project_state_id = 77 where id = #{arg1} and using_type = 1")
    void updateTaskFlag(Integer taskFlagState, Integer projectId);

    @Update("update by_plus_project set name = #{arg0},unit_name = #{arg1},linkman = #{arg2},\n" +
            "telephone = #{arg3},address = #{arg4},longitude = #{arg5},latitude = #{arg6} \n" +
            "where id = #{arg7} and using_type = 1")
    void updateProjectLocationInfo(String name, String unitName, String linkman, String telephone, String address, String longitude, String latitude, Integer id);

    @Select("select id from by_plus_task where project_id = #{arg0} and invalid_flag = 0 and using_type = 1")
    Integer searchTaskId(Integer id);

    @Update("update by_plus_task set task_name = #{arg0} where id = #{arg1} and using_type = 1 and invalid_flag = 0")
    void updateTaskNameByTaskId(String name, Integer taskId);

    @Select("select name from by_plus_project where id = #{arg0} and using_type = 1")
    String searchProjectNameById(Integer projectId);

    @Select("select project_add_user_id as projectAddUserId from by_plus_project where id = #{arg0} and using_type = 1")
    Integer searchProjectCreateUserIdById(Integer projectId);

    @Update("update by_plus_task_department_assist set using_type = 0 where task_id = #{arg0} and using_type = 1")
    void removeTaskDepartmentAssist(Integer taskId);

    @Update("update by_plus_task_assist_return set using_type = 0 where task_id = #{arg0} and using_type = 1")
    void removeTaskAssistReturn(Integer taskId);

    @Update("update by_plus_record_comparison set using_type = 0 where project_id = #{arg0} and using_type = 1")
    void removeRecordComparison(Integer pid);

    @Update("update by_plus_three_return_two set using_type = 0 where task_id = #{arg0} and using_type = 1")
    void removeThreeToTwoReturn(Integer taskId);

    @Update("update by_plus_task_stop set using_type = 0 where task_id = #{arg0} and using_type = 1")
    void removeTaskStop(Integer taskId);

    @SelectProvider(type = ByPlusProjectProvider.class, method = "searchFinishAllProjectCount")
    int searchFinishAllProject(String startDate, String endDate, String workGroupCode, String departmentCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId);

    @SelectProvider(type = ByPlusProjectProvider.class, method = "getNowMonthFinishProject")
    int searchNowMonthFinishProject(String startDate, String endDate, String workGroupCode, String departmentCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId);

    @SelectProvider(type = ByPlusProjectProvider.class, method = "getTaskScore")
    List<ByPlusTask> searchTaskScore(String startDate, String endDate, String workGroupCode, String departmentCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId);

    @SelectProvider(type = ByPlusProjectProvider.class, method = "getAverageTaskScore")
    Double searchAverageScore(String startDate, String endDate, String workGroupCode, String departmentCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId);

    @SelectProvider(type = ByPlusProjectProvider.class, method = "getOverdue")
    Integer searchOverdue(String startDate, String endDate, String workGroupCode, String departmentCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId);

    @SelectProvider(type = ByPlusProjectProvider.class, method = "getOverdueRecordTypeList")
    List<ByPlusProject> searchOverdueRecordTypeList(String startDate, String endDate, String workGroupCode, String departmentCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId);

    @SelectProvider(type = ByPlusProjectProvider.class, method = "getRecordTypeAverageScoreList")
    List<ByPlusProject> searchRecordTypeAverageScore(String startDate, String endDate, String workGroupCode, String departmentCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId);

    @SelectProvider(type = ByPlusProjectProvider.class, method = "getOverdueRegionList")
    List<ByPlusProject> searchOverdueRegionCount(String startDate, String endDate, String workGroupCode, String departmentCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId);

    @SelectProvider(type = ByPlusProjectProvider.class, method = "getRegionCountList")
    List<ByPlusProject> searchRegionCount(String startDate, String endDate, String workGroupCode, String departmentCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId);

    @SelectProvider(type = ByPlusProjectProvider.class, method = "getRecordTypeCountList")
    List<ByPlusProject> searchRecordTypeCount(String startDate, String endDate, String workGroupCode, String departmentCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId);

    @SelectProvider(type = ByPlusProjectProvider.class, method = "getRegionAverageScoreList")
    List<ByPlusProject> searchRegionAverageScore(String startDate, String endDate, String workGroupCode, String departmentCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId);

    @SelectProvider(type = ByPlusProjectProvider.class, method = "getTaskOverdueList")
    List<ByPlusTask> searchListForTaskOverdue(String startDate, String endDate, String workGroupCode, String departmentCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId);

    @SelectProvider(type = ByPlusProjectProvider.class, method = "getDistinctRecordTypeId")
    int searchDistinctRecordTypeId(String startDate, String endDate);

    @SelectProvider(type = ByPlusProjectProvider.class, method = "getDistinctRegionCode")
    int searchDistinctRegionCode(String startDate, String endDate);

    @Select("select count(*) from by_plus_project where data_state_id = 83")
    int searchDataStateCount();

    @Select("select count(*) from by_plus_project where project_cost_id = 80")
    int searchProjectCostCount();

    @SelectProvider(type = ByPlusProjectProvider.class, method = "getMonthTaskCount")
    int searchMonthCount(String regionCode, Integer recordTypeId, String startDate, String endDate);

    @SelectProvider(type = ByPlusProjectProvider.class, method = "getProjectList")
    List<ByPlusProject> searchProjectList(String startDate, String endDate);

    @SelectProvider(type = ByPlusProjectProvider.class, method = "getRecordTypeAndRegionCountList")
    List<ByPlusProject> searchRecordTypeAndRegionCount(String startDate, String endDate, String regionCode, String workGroupCode, String departmentCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId);

    @SelectProvider(type = ByPlusProjectProvider.class, method = "getRegionAndRecordTypeCountList")
    List<ByPlusProject> searchRegionAndRecordTypeCount(String startDate, String endDate, Integer recordTypeId, String workGroupCode, String departmentCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId);

    @SelectProvider(type = ByPlusProjectProvider.class, method = "getTaskCountByGroupList")
    List<ByPlusProject> searchTaskCountByGroup(String startDate, String endDate);

    @SelectProvider(type = ByPlusProjectProvider.class, method = "getTaskScoreByGroupList")
    List<ByPlusProject> searchTaskScoreByGroup(String startDate, String endDate);

    @SelectProvider(type = ByPlusProjectProvider.class, method = "getTaskOverdueByGroupList")
    List<ByPlusProject> searchTaskOverdueByGroup(String startDate, String endDate);

    @SelectProvider(type = ByPlusProjectProvider.class, method = "getTaskPeriodByGroupList")
    List<ByPlusProject> searchTaskPeriodByGroup(String startDate, String endDate);

    @Update("update by_plus_project set longitude = #{arg0},latitude = #{arg1} where id = #{arg2} and using_type = 1")
    void updateCoordinateById(String log, String lat, Integer id);

    @SelectProvider(type = ByPlusProjectProvider.class, method = "getLessScore")
    Integer searchLessScore(String startDate, String endDate, String workGroupCode, String departmentCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId);

    @SelectProvider(type = ByPlusProjectProvider.class, method = "getProjectInformationList")
    List<ByPlusProject> searchProjectAndRecordList(String name, String startTime, String endTime, String unitName, String linkman, String telephone, String regionCode, Integer recordTypeId, String fileNumber, String dataNumber, String departmentCode, String groupCode);

    @Select("select distinct p.id as id,p.department_code as departmentCode,p.work_group_code as workGroupCode \n" +
            "from by_plus_task p \n" +
            "where p.project_id = #{arg0} and p.using_type = 1")
    ByPlusTask searchByTaskInformation(Integer id);

    @Select("select distinct r.file_number as fileNumber from by_r_record r where r.project_id = #{arg0} and r.using_type = 1")
    ByRRecord searchRecordInformation(Integer id);

    @SelectProvider(type = ByPlusProjectProvider.class, method = "getProjectCollectInformationList")
    List<ByPlusProject> searchProjectCollectInformation(String name, String regionCode, Integer recordTypeId, String unitName, String linkman, String telephone, String score, Integer overdueFlag, String dayCount, String startDate, String endDate, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId, Integer startPage, Integer pageSize, String departmentCode, String workGroupCode, Integer recordOrRegionOverdueFlag);

    @SelectProvider(type = ByPlusProjectProvider.class, method = "getProjectCollectInformationCount")
    Integer searchProjectCollectInformationCount(String name, String regionCode, Integer recordTypeId, String unitName, String linkman, String telephone, String score, Integer overdueFlag, String dayCount, String startDate, String endDate, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId, String departmentCode, String workGroupCode, Integer recordOrRegionOverdueFlag);
}