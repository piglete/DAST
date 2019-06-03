package club.emergency.project.manager;

import club.emergency.project.model.ByPlusTask;
import club.emergency.project.model.ByRRecord;
import club.map.core.manager.GenericManager;
import club.map.core.model.Page;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 任务管理接口
 */
public interface ByPlusTaskManager extends GenericManager<ByPlusTask, Integer> {

    /**
     * 任务新增
     *
     * @param byPlusTask
     * @return
     */
    int upperSave(ByPlusTask byPlusTask);

    /**
     * 任务详情
     *
     * @param id
     * @return
     */
    ByPlusTask searchDetail(Integer id);

    /**
     * 任务删除
     *
     * @param ids
     */
    void removeByIds(String ids);

    /**
     * 任务查询
     *
     * @param projectId
     * @param departmentCode
     * @param taskStateId
     * @param employeeId
     * @param workGroupEmployeeId
     * @param taskName
     * @param oneInspectionUserId
     * @param twoInspectionUserId
     * @param threeInspectionUserId
     * @param recordInspectionUserId
     * @param outCheckUserId
     * @param outCheckState
     * @param workGroupCode
     * @param flag
     * @param invalidFlag
     * @param fileNumber
     * @return
     */
    List<ByPlusTask> search(Integer projectId, String departmentCode, Integer taskStateId, Integer employeeId, Integer workGroupEmployeeId, String taskName, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId, Integer recordInspectionUserId, Integer outCheckUserId, Integer outCheckState, String workGroupCode, Integer flag, Integer invalidFlag, String fileNumber);

    /**
     * 任务回退
     *
     * @param returnReasonId
     * @param taskStateId
     * @param id
     */
    void updateForTaskReturn(Integer returnReasonId, Integer taskStateId, Integer id);

    /**
     * 任务下达给部门
     *
     * @param code
     * @param taskStateId
     * @param departmentRemark
     * @param id
     */
    void updateForDepartmentReleaseTask(String code, Integer taskStateId, String departmentRemark, Integer id);

    /**
     * 部门回退任务
     *
     * @param messageId
     * @param taskStateId
     * @param id
     */
    void updateForDepartmentRevert(Integer messageId, Integer taskStateId, Integer id);

    /**
     * 小组接收
     *
     * @param messageId
     * @param taskStateId
     * @param id
     */
    void updateForGroupRevert(Integer messageId, Integer taskStateId, Integer id);

    /**
     * 小组完成
     *
     * @param taskStateId
     * @param groupRemark
     * @param id
     * @return
     */
    int updateForGroupFinish(Integer taskStateId, String groupRemark, Integer id);

    /**
     * 一检接收
     *
     * @param messageId
     * @param taskStateId
     * @param id
     * @param username
     * @param oneInspectionUserId
     * @return
     */
    int updateForOneRevert(Integer messageId, Integer taskStateId, Integer id, String username, Integer oneInspectionUserId);

    /**
     * 一检完成
     *
     * @param fileNumber
     * @param taskStateId
     * @param oneInspectionRemark
     * @param id
     * @return
     */
    int updateForOneCheck(String fileNumber, Integer taskStateId, String oneInspectionRemark, Integer id);

    /**
     * 二检接收
     *
     * @param messageId
     * @param taskStateId
     * @param id
     * @param username
     * @param twoInspectionUserId
     * @return
     */
    int updateForTwoRevert(Integer messageId, Integer taskStateId, Integer id, String username, Integer twoInspectionUserId);

    /**
     * 二检完成
     *
     * @param taskStateId
     * @param twoInspectionRemark
     * @param id
     * @return
     */
    int updateForTwoCheck(Integer taskStateId, String twoInspectionRemark, Integer id);

    /**
     * 三检接收
     *
     * @param messageId
     * @param taskStateId
     * @param id
     * @param username
     * @param threeInspectionUserId
     * @return
     */
    int updateForThreeRevert(Integer messageId, Integer taskStateId, Integer id, String username, Integer threeInspectionUserId);

    /**
     * 三检完成
     *
     * @param taskStateId
     * @param threeInspectionRemark
     * @param id
     */
    void updateForThreeCheck(Integer taskStateId, String threeInspectionRemark, Integer id);

    /**
     * 二检回退
     *
     * @param id
     * @param returnUser
     * @param twoReturnReasonIds
     */
    void updateForTwoReturn(Integer id, String returnUser, String twoReturnReasonIds);

    /**
     * 三检回退
     *
     * @param id
     * @param returnUser
     * @param threeReturnReasonIds
     */
    void updateForThreeReturn(Integer id, String returnUser, String threeReturnReasonIds);

    /**
     * 档案部接收
     *
     * @param messageId
     * @param taskStateId
     * @param id
     * @param username
     * @param recordInspectionUserId
     * @return
     */
    int updateForRecordRevert(Integer messageId, Integer taskStateId, Integer id, String username, Integer recordInspectionUserId);

    /**
     * 档案部完成
     *
     * @param taskStateId
     * @param id
     * @param byRRecord
     */
    void updateForRecordCheck(Integer taskStateId, Integer id, ByRRecord byRRecord);

    /**
     * 档案部回退
     *
     * @param username
     * @param recordReturnReasonId
     * @param id
     */
    void updateForRecordReturn(String username, Integer recordReturnReasonId, Integer id);

    /**
     * 外查接收(二检部分任务需要外查核对)
     *
     * @param messageId
     * @param outCheckUser
     * @param outCheckUserId
     * @param id
     * @return
     */
    int updateForOutRevert(Integer messageId, String outCheckUser, Integer outCheckUserId, Integer id);

    /**
     * 外查完成
     *
     * @param outCheckDescription
     * @param id
     */
    void updateForOutCheck(String outCheckDescription, Integer id);

    /**
     * 二检指定该任务外查
     *
     * @param outCheckDate 指定外查时间
     * @param id           任务id
     */
    void updateForRequireOutRevert(String outCheckDate, Integer id);

    /**
     * 三检退回二检
     *
     * @param id
     * @param returnReasonIds
     */
    void updateForThreeReturnTwo(Integer id, String returnReasonIds);

    /**
     * 任务回退
     *
     * @param taskStateId
     * @param messageId
     * @param id
     */
    void updateForReturn(Integer taskStateId, Integer messageId, Integer id);

    /**
     * 任务中资料打印处理(三检完成)
     *
     * @param printUserId
     * @param messageId
     * @param printDate
     * @param printRemark
     * @param id
     */
    void updateForPrintManage(Integer printUserId, Integer messageId, String printDate, String printRemark, Integer id);

    /**
     * 任务中资料交付处理(三检完成时,资料打印完成后)
     *
     * @param dataRevertUserId
     * @param dataDeliveryDate
     * @param dataRevertRemark
     * @param id
     * @param messageId
     */
    void updateForDataDelivery(Integer dataRevertUserId, String dataDeliveryDate, String dataRevertRemark, Integer id, Integer messageId);

    /**
     * 三检指定工程部门打印资料
     *
     * @param id
     */
    void updateForSanjianPrint(Integer id);

    /**
     * 档案部资料审核
     *
     * @param projectId
     * @return
     */
    int checkInfo(Integer projectId);

    /**
     * 三检指定综合部接收资料
     *
     * @param id
     */
    void updateForZonghebuRevert(Integer id);

    /**
     * 任务暂停设置
     *
     * @param flag
     * @param stopOperatorId
     * @param remark
     * @param id
     */
    void updateForTaskFlag(Integer flag, Integer stopOperatorId, String remark, Integer id);

    /**
     * 综合部接收任务(该任务为其他部门协作研发部的任务)
     *
     * @param id
     * @param comprehensiveRevertFlag
     */
    void updateForComprehensiveTaskRevert(Integer id, Integer comprehensiveRevertFlag);

    /**
     * 外协部门接收(任务需要外协处理时)
     *
     * @param taskId
     * @param departmentCode
     * @param messageId
     */
    void updateForTaskAssistRevert(Integer taskId, String departmentCode, Integer messageId);

    /**
     * 外协部门下达(部门负责人下达给小组)
     *
     * @param assistId
     * @param assistGroupCode
     */
    void updateForTaskAssistReleaseGroup(Integer assistId, String assistGroupCode);

    /**
     * 外协小组接收
     *
     * @param taskId
     * @param groupCode
     * @param messageId
     */
    void updateForTaskAssistGroupRevert(Integer taskId, String groupCode, Integer messageId);

    /**
     * 外协小组完成
     *
     * @param assistId
     * @return
     */
    int updateForTaskAssistGroupFinish(Integer assistId);

    /**
     * 外协一检接收
     *
     * @param departmentCode
     * @param messageId
     * @param taskId
     * @param oneInspectionUserId
     */
    void updateForAssistOneInspectionRevert(String departmentCode, Integer messageId, Integer taskId, Integer oneInspectionUserId);

    /**
     * 外协一检完成
     *
     * @param assistId
     */
    void updateForAssistOneInspectionFinish(Integer assistId);

    /**
     * 小组回退
     *
     * @param assistId
     * @param returnReasonIds
     * @param remark
     */
    void updateForGroupReturn(Integer assistId, String returnReasonIds, String remark);

    /**
     * 主部门下达外协任务
     *
     * @param assistId
     */
    void updateForTaskReleaseAssistDepartment(Integer assistId);

    /**
     * 小组接收外协一检提交的工作
     *
     * @param taskId
     * @param oneInspectionUserId
     * @param messageId
     */
    void updateForGroupRevertAssistOneInspection(Integer taskId, Integer oneInspectionUserId, Integer messageId);

    /**
     * 小组确认外协一检提交的工作
     *
     * @param assistId
     */
    void updateForGroupConfirmAssistOneInspection(Integer assistId);

    /**
     * 工程资料打印完成提示
     *
     * @param id
     */
    void updateForPrintFinishFlag(Integer id);

    /**
     * 分页查询(项目表和任务表关联查询)
     *
     * @param pageNo
     * @param pageSize
     * @param name
     * @param recordTypeId
     * @param unitName
     * @param linkman
     * @param telephone
     * @param projectPeriod
     * @param regionCode
     * @param startDate
     * @param endDate
     * @param departmentCode
     * @param taskStateId
     * @param employeeId
     * @param workGroupEmployeeId
     * @param oneInspectionUserId
     * @param twoInspectionUserId
     * @param threeInspectionUserId
     * @param recordInspectionUserId
     * @param outCheckUserId
     * @param outCheckState
     * @param workGroupCode
     * @param flag
     * @param invalidFlag
     * @param assistFlag
     * @param fileNumber
     * @return
     */
    Page searchWithProjectInfo(Integer pageNo, Integer pageSize, String name, Integer recordTypeId, String unitName, String linkman, String telephone, Integer projectPeriod, String regionCode, String startDate, String endDate, String departmentCode, Integer taskStateId, Integer employeeId, Integer workGroupEmployeeId, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId, Integer recordInspectionUserId, Integer outCheckUserId, Integer outCheckState, String workGroupCode, Integer flag, Integer invalidFlag, Integer assistFlag, String fileNumber);

    /**
     * 任务导出excel
     *
     * @param departmentCode
     * @param workGroupCode
     * @param startDate
     * @param endDate
     * @param oneInspectionUserId
     * @param twoInspectionUserId
     * @param threeInspectionUserId
     * @param response
     */
    void taskExport(String departmentCode, String workGroupCode, String startDate, String endDate, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId, HttpServletResponse response);

    /**
     * 查询任务部分信息
     *
     * @param id
     * @return
     */
    Map<String, Object> searchForRemark(Integer id);

    List<ByPlusTask> searchByProjectId(Integer pid);
    /**
     * 小组撤回任务
     *
     * @param id
     * @param groupReturnRemark
     */
    void updateForGroupRevoke(Integer id, String groupReturnRemark);
}