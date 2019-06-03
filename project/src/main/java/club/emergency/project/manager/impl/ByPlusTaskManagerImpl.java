package club.emergency.project.manager.impl;

import club.emergency.by_b_department.mapper.ByBDepartmentMapper;
import club.emergency.by_b_employee.manager.ByBEmployeeManager;
import club.emergency.by_b_employee.mapper.ByBEmployeeMapper;
import club.emergency.by_b_employee.model.ByBEmployee;
import club.emergency.by_plus_unit_config.manager.ByPlusUnitConfigManager;
import club.emergency.by_plus_unit_config.model.ByPlusUnitConfig;
import club.emergency.dictionary.manager.ByBDictionaryChildManager;
import club.emergency.project.manager.*;
import club.emergency.project.mapper.ByPlusProjectMapper;
import club.emergency.project.mapper.ByPlusTaskDepartmentAssistMapper;
import club.emergency.project.mapper.ByPlusTaskMapper;
import club.emergency.project.mapper.ByPlusTaskStopMapper;
import club.emergency.project.model.*;
import club.map.base.util.ExcelData;
import club.map.base.util.ExportExcelUtils;
import club.map.core.manager.impl.GenericManagerImpl;
import club.map.core.model.FlipFilter;
import club.map.core.model.Operate;
import club.map.core.model.Page;
import com.wanqing.util.StringHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ByPlusTaskManagerImpl extends GenericManagerImpl<ByPlusTask, Integer> implements ByPlusTaskManager {

    @Autowired
    private ByPlusUnitConfigManager byPlusUnitConfigManager;
    private ByPlusTaskMapper byPlusTaskMapper;
    private ByBDepartmentMapper byBDepartmentMapper;
    private ByBEmployeeManager byBEmployeeManager;
    private ByBEmployeeMapper byBEmployeeMapper;
    private ByPlusProjectManager byPlusProjectManager;
    private ByBDictionaryChildManager byBDictionaryChildManager;
    private ByPlusTwoReturnManager byPlusTwoReturnManager;
    private ByPlusThreeReturnManager byPlusThreeReturnManager;
    private ByPlusTaskQualityManager byPlusTaskQualityManager;
    private ByPlusProjectMapper byPlusProjectMapper;
    private ByPlusRecordReturnManager byPlusRecordReturnManager;
    private ByRRecordManager byRRecordManager;
    private ByPlusMessageManager byPlusMessageManager;
    private ByPlusTipMessageManager byPlusTipMessageManager;
    private ByPlusOutCheckMessageManager byPlusOutCheckMessageManager;
    private ByPlusTaskWorkloadManager byPlusTaskWorkloadManager;
    private ByPlusThreeReturnTwoManager byPlusThreeReturnTwoManager;
    private ByPlusRecordComparisonManager byPlusRecordComparisonManager;
    private ByPlusTaskStopManager byPlusTaskStopManager;
    private ByPlusTaskStopMapper byPlusTaskStopMapper;
    private ByPlusTaskDepartmentAssistMapper byPlusTaskDepartmentAssistMapper;
    private ByPlusTaskAssistReturnManager byPlusTaskAssistReturnManager;

    @Autowired
    public ByPlusTaskManagerImpl(ByPlusTaskMapper mapper,
                                 ByPlusTaskMapper byPlusTaskMapper,
                                 ByBDepartmentMapper byBDepartmentMapper,
                                 ByBEmployeeManager byBEmployeeManager,
                                 ByBEmployeeMapper byBEmployeeMapper,
                                 ByPlusProjectManager byPlusProjectManager,
                                 ByBDictionaryChildManager byBDictionaryChildManager,
                                 ByPlusTwoReturnManager byPlusTwoReturnManager,
                                 ByPlusThreeReturnManager byPlusThreeReturnManager,
                                 ByPlusTaskQualityManager byPlusTaskQualityManager,
                                 ByPlusProjectMapper byPlusProjectMapper,
                                 ByPlusRecordReturnManager byPlusRecordReturnManager,
                                 ByRRecordManager byRRecordManager,
                                 ByPlusMessageManager byPlusMessageManager,
                                 ByPlusTipMessageManager byPlusTipMessageManager,
                                 ByPlusOutCheckMessageManager byPlusOutCheckMessageManager,
                                 ByPlusTaskWorkloadManager byPlusTaskWorkloadManager,
                                 ByPlusThreeReturnTwoManager byPlusThreeReturnTwoManager,
                                 ByPlusRecordComparisonManager byPlusRecordComparisonManager,
                                 ByPlusTaskStopManager byPlusTaskStopManager,
                                 ByPlusTaskStopMapper byPlusTaskStopMapper,
                                 ByPlusTaskDepartmentAssistMapper byPlusTaskDepartmentAssistMapper,
                                 ByPlusTaskAssistReturnManager byPlusTaskAssistReturnManager
    ) {
        super(mapper, ByPlusTask.class);
        this.byPlusTaskMapper = byPlusTaskMapper;
        this.byBDepartmentMapper = byBDepartmentMapper;
        this.byBEmployeeManager = byBEmployeeManager;
        this.byBEmployeeMapper = byBEmployeeMapper;
        this.byPlusProjectManager = byPlusProjectManager;
        this.byBDictionaryChildManager = byBDictionaryChildManager;
        this.byPlusTwoReturnManager = byPlusTwoReturnManager;
        this.byPlusThreeReturnManager = byPlusThreeReturnManager;
        this.byPlusTaskQualityManager = byPlusTaskQualityManager;
        this.byPlusProjectMapper = byPlusProjectMapper;
        this.byPlusRecordReturnManager = byPlusRecordReturnManager;
        this.byRRecordManager = byRRecordManager;
        this.byPlusMessageManager = byPlusMessageManager;
        this.byPlusTipMessageManager = byPlusTipMessageManager;
        this.byPlusOutCheckMessageManager = byPlusOutCheckMessageManager;
        this.byPlusTaskWorkloadManager = byPlusTaskWorkloadManager;
        this.byPlusThreeReturnTwoManager = byPlusThreeReturnTwoManager;
        this.byPlusRecordComparisonManager = byPlusRecordComparisonManager;
        this.byPlusTaskStopManager = byPlusTaskStopManager;
        this.byPlusTaskStopMapper = byPlusTaskStopMapper;
        this.byPlusTaskDepartmentAssistMapper = byPlusTaskDepartmentAssistMapper;
        this.byPlusTaskAssistReturnManager = byPlusTaskAssistReturnManager;
    }

    /**
     * 办公室创建任务
     * 1.创建任务时创建通知消息
     * 2.一个项目只允许创建一个任务
     *
     * @param byPlusTask
     */
    @Transactional
    @Override
    public int upperSave(ByPlusTask byPlusTask) {
        //判断该项目下是否已下达任务
        int a = 0;
        Integer projectId = byPlusTask.getProjectId();
        ByPlusProject byPlusProject = byPlusProjectManager.get(projectId);
        Integer taskFlag = byPlusProject.getTaskFlag();
        if (taskFlag == 1) {
            a = 1;
            return a;
        }
        //根据部门code获取员工负责人id
        Integer id = byPlusTask.getId();
        String departmentCode = byPlusTask.getDepartmentCode();
        String positionState = "在职";
        String isLeader = "是";
        Integer employeeId = byBEmployeeManager.searchDepartmentLeader(departmentCode, positionState, isLeader).getId();
        byPlusTask.setEmployeeId(employeeId);
        String taskName = byPlusProject.getName();
        byPlusTask.setTaskName(taskName);
        byPlusTask.setTaskStateId(86);
        this.save(byPlusTask);
        if (id == null) {
            //如果该任务是新增,将项目的任务下达标识改为1,任务状态变成进行中 project_state_id=77
            Integer taskFlagState = 1;
            byPlusProjectMapper.updateTaskFlag(taskFlagState, projectId);
            //添加通知消息,办公室创建任务后发送通知消息给部门负责人
            Integer projectAddUserId = byPlusProject.getProjectAddUserId();
            Integer taskId = byPlusTask.getId();
            String message = "办公室下达新任务:" + byPlusTask.getTaskName() + ",请您及时处理!";
            ByPlusMessage byPlusMessage = new ByPlusMessage();
            byPlusMessage.setProjectId(projectId);
            byPlusMessage.setTaskId(taskId);
            byPlusMessage.setMessage(message);
            byPlusMessage.setMessageUserId(employeeId);
            byPlusMessage.setMessageCreateId(projectAddUserId);
            byPlusMessage.setMessageType(1);
            //部门接收状态 taskStateId=87
            byPlusMessage.setTaskStateId(87);
            byPlusMessageManager.upperSave(byPlusMessage);
            //任务新增时，读取项目类型下所有申报内容，入库（工作量表）
            List<ByPlusUnitConfig> byPlusUnitConfigList =byPlusUnitConfigManager.searchByTaskId(taskId);
            ByPlusTaskWorkload byPlusTaskWorkload = null;
            for (ByPlusUnitConfig byPlusUnitConfig: byPlusUnitConfigList) {
                byPlusTaskWorkload = new ByPlusTaskWorkload();
                byPlusTaskWorkload.setInternalWorkOutput(new BigDecimal("0"));
                byPlusTaskWorkload.setDepartmentCode(departmentCode);
                byPlusTaskWorkload.setItemApplicationId(byPlusUnitConfig.getItemApplicationId());
                byPlusTaskWorkload.setWorkCount(new BigDecimal("0"));
                byPlusTaskWorkload.setTaskId(taskId);
                byPlusTaskWorkload.setUnitTypeId(byPlusUnitConfig.getUnitTypeId());
                byPlusTaskWorkload.setFlag(0);
                byPlusTaskWorkloadManager.save(byPlusTaskWorkload);
            }
        }
        return a;
    }

    @Override
    public ByPlusTask searchDetail(Integer id) {
        ByPlusTask byPlusTask = this.get(id);
        this.searchInfo(byPlusTask);
        return byPlusTask;
    }

    @Transactional
    @Override
    public void removeByIds(String ids) {
        String[] idArr = ids.split(",");
        Arrays.asList(idArr).forEach(id -> {
            this.remove(Integer.valueOf(id));
        });
    }

    @Override
    public List<ByPlusTask> search(Integer projectId, String departmentCode, Integer taskStateId, Integer employeeId, Integer workGroupEmployeeId, String taskName, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId, Integer recordInspectionUserId, Integer outCheckUserId, Integer outCheckState, String workGroupCode, Integer flag, Integer invalidFlag, String fileNumber) {
        FlipFilter flipFilter = new FlipFilter(ByPlusTask.class);
        flipFilter.addSearch(projectId, Operate.EQUAL, "projectId");
        flipFilter.addSearch(departmentCode, Operate.EQUAL, "departmentCode");
        flipFilter.addSearch(taskStateId, Operate.EQUAL, "taskStateId");
        flipFilter.addSearch(employeeId, Operate.EQUAL, "employeeId");
        flipFilter.addSearch(workGroupEmployeeId, Operate.EQUAL, "workGroupEmployeeId");
        flipFilter.addSearch("%" + taskName + "%", Operate.LIKE, "taskName");
        flipFilter.addSearch(workGroupCode, Operate.EQUAL, "workGroupCode");
        flipFilter.addSearch(oneInspectionUserId, Operate.EQUAL, "oneInspectionUserId");
        flipFilter.addSearch(twoInspectionUserId, Operate.EQUAL, "twoInspectionUserId");
        flipFilter.addSearch(threeInspectionUserId, Operate.EQUAL, "threeInspectionUserId");
        flipFilter.addSearch(recordInspectionUserId, Operate.EQUAL, "recordInspectionUserId");
        flipFilter.addSearch(outCheckUserId, Operate.EQUAL, "outCheckUserId");
        flipFilter.addSearch(outCheckState, Operate.EQUAL, "outCheckState");
        flipFilter.addSearch(flag, Operate.EQUAL, "flag");
        flipFilter.addSearch(invalidFlag, Operate.EQUAL, "invalidFlag");
        flipFilter.addSearch("%" + fileNumber + "%", Operate.LIKE, "fileNumber");
        List<ByPlusTask> byPlusTaskList = this.list(flipFilter);
        for (ByPlusTask byPlusTask : byPlusTaskList) {
            this.searchInfo(byPlusTask);
        }
        return byPlusTaskList;
    }

    /**
     * 部门任务回退
     * 1.退回给办公室,修改任务状态
     * 2.创建回退消息(2019-1-25 作废该消息,不做消息提示)
     * 3.将任务作废改为1(作废状态)
     * 4.修改项目中的回退次数
     * 5.删除该任务下的消息
     *
     * @param returnReason
     * @param taskStateId
     * @param id
     */
    @Transactional
    @Override
    public void updateForTaskReturn(Integer returnReasonId, Integer taskStateId, Integer id) {
        String addTime = this.getNowDate();
        byPlusTaskMapper.updateTaskReturnById(returnReasonId, taskStateId, addTime, id);
        //修改项目中的回退次数,并且修改项目下任务下达的标识,改为0
        ByPlusTask byPlusTask = this.get(id);
        Integer projectId = byPlusTask.getProjectId();
        ByPlusProject byPlusProject = byPlusProjectManager.get(projectId);
        Integer count = byPlusProject.getTaskReturnCount();
        Integer taskReturnCount = count.intValue() + 1;
        byPlusProjectManager.updateTaskReturnCountById(taskReturnCount, projectId);
        //删除通知消息
        byPlusMessageManager.removeByTaskId(id);
        //添加消息通知
        String taskName = byPlusTask.getTaskName();
        //任务创建用户
        Integer messageUserId = byPlusProjectManager.get(projectId).getProjectAddUserId();
        String departmentName = byBDepartmentMapper.searchNameByCode(byPlusTask.getDepartmentCode());
        Integer messageCreateId = byPlusTask.getEmployeeId();
        String message = departmentName + "退回了任务:" + taskName + ",请注意查看!";
        ByPlusMessage byPlusMessage = new ByPlusMessage();
        byPlusMessage.setProjectId(projectId);
        byPlusMessage.setTaskId(id);
        byPlusMessage.setMessageUserId(messageUserId);
        byPlusMessage.setMessage(message);
        byPlusMessage.setMessageCreateId(messageCreateId);
        //办公室接收状态 taskStateId=86
        byPlusMessage.setTaskStateId(86);
        //messageType=2,为提示消息
        byPlusMessage.setMessageType(2);
        byPlusMessageManager.upperSave(byPlusMessage);
    }

    /**
     * 部门下达任务给小组
     * 1.修改任务信息
     * 2.创建通知消息
     *
     * @param code
     * @param taskStateId
     * @param id
     */
    @Transactional
    @Override
    public void updateForDepartmentReleaseTask(String code, Integer taskStateId, String departmentRemark, Integer id) {
        //根据小组code查询员工表小组负责人姓名,id
        ByPlusTask byPlusTask = this.get(id);
        ByBEmployee byBEmployee = byBEmployeeManager.searchIdAndNameByCode(code);
        String workGroupUser = byBEmployee.getUsername();
        Integer workGroupEmployeeId = byBEmployee.getId();
        String addTime = this.getNowDate();
        byPlusTaskMapper.updateDepartmentReleaseTask(code, taskStateId, workGroupUser, addTime, workGroupEmployeeId, departmentRemark, id);
        //创建通知给小组的消息
        Integer projectId = byPlusTask.getProjectId();
        Integer messageCreateId = byPlusTask.getEmployeeId();
        String taskName = byPlusTask.getTaskName();
        String message = taskName + "任务已下达给您,请您及时处理!";
        ByPlusMessage byPlusMessage = new ByPlusMessage();
        byPlusMessage.setProjectId(projectId);
        byPlusMessage.setTaskId(id);
        byPlusMessage.setMessageUserId(workGroupEmployeeId);
        byPlusMessage.setMessage(message);
        byPlusMessage.setMessageCreateId(messageCreateId);
        byPlusMessage.setMessageType(1);
        //小组接收状态 taskStateId=98
        byPlusMessage.setTaskStateId(98);
        byPlusMessageManager.upperSave(byPlusMessage);
    }

    /**
     * 部门接收任务
     * 1.部门接收消息后,删除该消息
     * 2.修改当前状态
     *
     * @param taskStateId
     * @param id
     */
    @Transactional
    @Override
    public void updateForDepartmentRevert(Integer messageId, Integer taskStateId, Integer id) {
        String addTime = this.getNowDate();
        byPlusTaskMapper.updateDepartmentRevertById(taskStateId, addTime, id);
        byPlusMessageManager.remove(messageId);
    }

    /**
     * 小组接收任务
     * 1.删除通知消息
     *
     * @param taskStateId
     * @param id
     */
    @Transactional
    @Override
    public void updateForGroupRevert(Integer messageId, Integer taskStateId, Integer id) {
        String addTime = this.getNowDate();
        byPlusTaskMapper.updateGroupRevertById(taskStateId, addTime, id);
        byPlusMessageManager.remove(messageId);
    }

    /**
     * 小组完成任务
     * 1.小组完成时必须已经填写小组工作量,否则不能提交
     * 2.判断该任务是否有外协,如果有外协,则所有外协完成,小组才能提交到一检,否则不允许提交
     * 3.小组的完成周期为小组接收时间到小组最终一次提交时间
     * 4.创建通知消息,小组完成后消息推送给一检
     *
     * @param taskStateId
     * @param id
     */
    @Transactional
    @Override
    public int updateForGroupFinish(Integer taskStateId, String groupRemark, Integer id) {
        int a = 0;
        ByPlusTask byPlusTask = this.get(id);
        String workGroupCode = byPlusTask.getWorkGroupCode();
        FlipFilter flipFilter = new FlipFilter(ByPlusTaskWorkload.class);
        flipFilter.addSearch(id, Operate.EQUAL, "taskId");
        flipFilter.addSearch(workGroupCode, Operate.EQUAL, "departmentCode");
        List<ByPlusTaskWorkload> byPlusTaskWorkloadList = byPlusTaskWorkloadManager.list(flipFilter);
        //判断小组是否填写了任务量,如果没有,查到的结果为0
        if (byPlusTaskWorkloadList.size() == 0) {
            a = 1;
            return a;
        }
        //判断该任务是否有外协,如果有的话,外协全部完成,小组才可提交到一检
        Integer assistFlag = byPlusTask.getAssistFlag();
        if (assistFlag == 1) {
            Integer finishFlag = 0;//未完成标识
            int finishCount = byPlusTaskDepartmentAssistMapper.searchFinishCount(id, finishFlag);
            //判断所有外协部门是否完成
            if (finishCount > 0) {
                a = 2;
                return a;
            }
        }
        //修改任务信息
        String addTime = this.getNowDate();
        String groupRevertDate = byPlusTask.getGroupRevertDate();
        String groupFinishPeriod = this.getFinishPeriod(groupRevertDate, addTime);
        byPlusTaskMapper.updateGroupFinishById(taskStateId, addTime, groupFinishPeriod, groupRemark, id);
        //添加通知消息(如果是回退的任务,消息通知发送给之前一检人员;如果是正常进行的任务,消息发送给所有一检人员)
        Integer projectId = byPlusTask.getProjectId();
        String taskName = byPlusTask.getTaskName();
        //一检(一检角色roleId=7)
        Integer messageRoleId = 7;
        Integer messageCreateId = byPlusTask.getWorkGroupEmployeeId();
        String message = "小组已完成任务:" + taskName + ",需要你核对检查,请您及时处理!";
        Integer oneInspectionUserId = byPlusTask.getOneInspectionUserId();
        String departmentCode = byPlusTask.getDepartmentCode();
        ByPlusMessage byPlusMessage = new ByPlusMessage();
        byPlusMessage.setProjectId(projectId);
        byPlusMessage.setTaskId(id);
        byPlusMessage.setMessageType(1);
        if (oneInspectionUserId == null) {
            byPlusMessage.setMessageRoleId(messageRoleId);
        } else {
            byPlusMessage.setMessageUserId(oneInspectionUserId);
        }
        byPlusMessage.setMessage(message);
        byPlusMessage.setMessageCreateId(messageCreateId);
        //一检接收状态 taskStateId=100
        byPlusMessage.setTaskStateId(100);
        byPlusMessage.setDepartmentCode(departmentCode);
        byPlusMessageManager.upperSave(byPlusMessage);
        return a;
    }

    /**
     * 一检接收任务
     * 1.判断是否已有人接收,如果已有人接收,则不可再重复接收,给出提示
     *
     * @param username
     * @param taskStateId
     * @param id
     */
    @Transactional
    @Override
    public int updateForOneRevert(Integer messageId, Integer taskStateId, Integer id, String username, Integer oneInspectionUserId) {
        ByPlusTask byPlusTask = this.get(id);
        Integer taskStateId1 = byPlusTask.getTaskStateId();
        int a = 0;
        //一检接收状态 taskStateId=100
        if (taskStateId1 == 100) {
            a = 1;
            return a;
        } else {
            String addTime = this.getNowDate();
            byPlusTaskMapper.updateOneRevertById(taskStateId, addTime, username, oneInspectionUserId, id);
            byPlusMessageManager.remove(messageId);
            return a;
        }
    }

    /**
     * 一检审核完成
     * 1.资料编号唯一性验证
     * 2.修改任务状态
     * 3.判断该项目是否是其他部门协助研发部的项目(通过项目表develop_flag 字段识别)
     * 4.创建通知二检消息(如果该任务之前被二检人员接收过,则通知消息发送给曾经接收人;
     * 如果未被接收过,则通知消息发送给所有二检人员)
     *
     * @param taskStateId
     * @param id
     */
    @Transactional
    @Override
    public int updateForOneCheck(String fileNumber, Integer taskStateId, String oneInspectionRemark, Integer id) {
        int a = 0;
        ByPlusTask byPlusTask = this.get(id);
        String fileNumber1 = byPlusTask.getFileNumber();
        if (StringHandler.isEmptyOrNull(fileNumber1)) {
            if (StringHandler.isNotEmptyOrNull(fileNumber)) {
                int count = byPlusTaskMapper.searchByFileNumber(fileNumber);
                if (count > 0) {
                    a = 1;
                    return a;
                }
            } else {
                return a = 2;
            }
        }
        String addTime = this.getNowDate();
        String departmentCode = byPlusTask.getDepartmentCode();
        Integer projectId = byPlusTask.getProjectId();
        //获取项目信息
        ByPlusProject byPlusProject = byPlusProjectManager.get(projectId);
        String oneRevertDate = byPlusTask.getOneRevertDate();
        String oneFinishPeriod = this.getFinishPeriod(oneRevertDate, addTime);
        Integer flag = 0;
        //研发部=100104 判断该部门是否是研发部,如果是研发部,则说明该任务是下达给研发部的任务,一检完成时则已完成
        if ("100104".equals(departmentCode)) {
            flag = 2;
        }
        byPlusTaskMapper.updateOneCheckById(taskStateId, addTime, oneFinishPeriod, fileNumber, flag, oneInspectionRemark, id);
        //添加通知消息
        String taskName = byPlusTask.getTaskName();
        Integer oneInspectionUserId = byPlusTask.getOneInspectionUserId();
        ByPlusMessage byPlusMessage = new ByPlusMessage();
        byPlusMessage.setProjectId(projectId);
        byPlusMessage.setTaskId(id);
        byPlusMessage.setMessageCreateId(oneInspectionUserId);
        if ("100104".equals(departmentCode)) {
            //如果该任务为其他部门协作研发部的任务,一检完成时通知综合部任务完成
            Integer projectCreateUserId = byPlusProject.getProjectAddUserId();
            String message = "当前下达给研发部的任务:" + taskName + ",一检已完成,请您查看!";
            byPlusMessage.setMessage(message);
            byPlusMessage.setMessageUserId(projectCreateUserId);
            byPlusMessage.setMessageType(10);
            //任务状态修改为三检完成
            byPlusMessage.setTaskStateId(93);
        } else {
            //一检审核完成后要通知二检用户,二检对应的角色为 roleId=5
            Integer messageRoleId = 5;
            String message = "一检已完成任务:" + taskName + ",需要您继续检查,请您及时处理!";
            byPlusMessage.setMessage(message);
            Integer twoInspectionUserId = byPlusTask.getTwoInspectionUserId();
            if (twoInspectionUserId == null) {
                byPlusMessage.setMessageRoleId(messageRoleId);
            } else {
                byPlusMessage.setMessageUserId(twoInspectionUserId);
            }
            byPlusMessage.setMessageType(1);
            //二检接收状态 taskStateId=90
            byPlusMessage.setTaskStateId(90);
        }
        byPlusMessageManager.upperSave(byPlusMessage);
        return a;
    }

    /**
     * 二检接收任务
     * 1.判断当前任务是否已被接收
     * 2.修改task对应数据
     * 3.新增一条任务质量评定分数
     *
     * @param username
     * @param taskStateId
     * @param id
     */
    @Transactional
    @Override
    public int updateForTwoRevert(Integer messageId, Integer taskStateId, Integer id, String username, Integer twoInspectionUserId) {
        ByPlusTask byPlusTask = this.get(id);
        Integer taskStateId1 = byPlusTask.getTaskStateId();
        int a = 0;
        if (taskStateId1 == 90) {
            a = 1;
            return a;
        } else {
            //修改task对应数据
            String addTime = this.getNowDate();
            byPlusTaskMapper.updateTwoRevertById(taskStateId, addTime, username, twoInspectionUserId, id);
            //创建一条任务质量评定分数
            Integer checkDepartment = 2;
            FlipFilter flipFilter = new FlipFilter(ByPlusTaskQuality.class);
            flipFilter.addSearch(id, Operate.EQUAL, "taskId");
            flipFilter.addSearch(2, Operate.EQUAL, "checkDepartment");
            List<ByPlusTaskQuality> byPlusTaskQualityList = byPlusTaskQualityManager.list(flipFilter);
            if (byPlusTaskQualityList.size() == 0) {
                byPlusTaskQualityManager.createRecord(id, checkDepartment, username, addTime);
            }
            byPlusMessageManager.remove(messageId);
            return a;
        }
    }

    /**
     * 二检审核完成
     * 1.审核完成时需要计算二检处理周期
     * 2.二检审核完成前必须计算出二检得分,否则不能进行审核完成提交
     * 3.判断该任务是否需要外查,如果需要外查,则判断外查接收和完成情况,做出对应提示,外查完成二检才算审核完成
     * 4.创建通知消息
     *
     * @param taskStateId
     * @param id
     */
    @Transactional
    @Override
    public int updateForTwoCheck(Integer taskStateId, String twoInspectionRemark, Integer id) {
        int a = 0;
        //获取审核时间和二检处理周期
        String addTime = this.getNowDate();
        ByPlusTask byPlusTask = this.get(id);
        String twoRevertDate = byPlusTask.getTwoRevertDate();
        String twoFinishPeriod = this.getFinishPeriod(twoRevertDate, addTime);
        //判断该任务是否需要外查,如果需要外查,则在二检提交时需要判断外查完成情况
        Integer outCheckState = byPlusTask.getOutCheckState();
        if (outCheckState != 0) {
            //判断外查是否接收,如果没有接收,二检完成时需要进行提示
            String outCheckRevertDate = byPlusTask.getOutCheckRevertDate();
            if (StringHandler.isEmptyOrNull(outCheckRevertDate)) {
                a = 1;
                return a;
            }
            //判断外查是否完成,如果没有完成,二检完成时需要进行提示
            String outCheckFinishDate = byPlusTask.getOutCheckFinishDate();
            if (StringHandler.isEmptyOrNull(outCheckFinishDate)) {
                a = 2;
                return a;
            }
        }
        //二检完成时修改任务状态
        byPlusTaskMapper.updateTwoCheckById(taskStateId, addTime, twoFinishPeriod, twoInspectionRemark, id);
        //添加通知消息,二检审核完成后要通知三检,roleId=6
        Integer projectId = byPlusTask.getProjectId();
        Integer messageRoleId = 6;
        String taskName = byPlusTask.getTaskName();
        Integer twoInspectionUserId = byPlusTask.getTwoInspectionUserId();
        String message = "二检已审核完任务:" + taskName + ",现需您继续检查,请您及时处理!";
        Integer threeInspectionUserId = byPlusTask.getThreeInspectionUserId();
        ByPlusMessage byPlusMessage = new ByPlusMessage();
        byPlusMessage.setProjectId(projectId);
        byPlusMessage.setTaskId(id);
        if (threeInspectionUserId == null) {
            byPlusMessage.setMessageRoleId(messageRoleId);
        } else {
            byPlusMessage.setMessageUserId(threeInspectionUserId);
        }
        byPlusMessage.setMessage(message);
        byPlusMessage.setMessageCreateId(twoInspectionUserId);
        byPlusMessage.setMessageType(1);
        //三检接收状态 taskStateId=92
        byPlusMessage.setTaskStateId(92);
        byPlusMessageManager.upperSave(byPlusMessage);
        return a;
    }

    /**
     * 查看该任务有没有进行质量评分
     *
     * @param id
     * @param checkDepartment
     * @return
     */
    private Boolean checkTaskQuality(Integer id, Integer checkDepartment) {
        Boolean flag = false;
        FlipFilter flipFilter = new FlipFilter(ByPlusTaskQuality.class);
        flipFilter.addSearch(id, Operate.EQUAL, "taskId");
        flipFilter.addSearch(checkDepartment, Operate.EQUAL, "checkDepartment");
        List<ByPlusTaskQuality> byPlusTaskQualityList = byPlusTaskQualityManager.list(flipFilter);
        if (byPlusTaskQualityList.size() > 0) {
            flag = true;
        }
        return flag;
    }

    /**
     * 三检接收任务
     * 1.判断当前任务是否已被接收
     * 2.修改task对应数据
     * 3.如果是第一次接收,则新增一条任务质量评定分数
     * 4.删除通知消息
     *
     * @param username
     * @param taskStateId
     * @param id
     */
    @Transactional
    @Override
    public int updateForThreeRevert(Integer messageId, Integer taskStateId, Integer id, String username, Integer threeInspectionUserId) {
        ByPlusTask byPlusTask = this.get(id);
        Integer taskStateId1 = byPlusTask.getTaskStateId();
        Integer threeReturnCount = byPlusTask.getThreeReturnCount();
        int a = 0;
        if (taskStateId1 == 92) {
            a = 1;
            return a;
        } else {
            String addTime = this.getNowDate();
            byPlusTaskMapper.updateThreeRevertById(taskStateId, addTime, username, threeInspectionUserId, id);
            //创建一条任务质量评定分数
            Integer checkDepartment = 3;
            FlipFilter flipFilter = new FlipFilter(ByPlusTaskQuality.class);
            flipFilter.addSearch(id, Operate.EQUAL, "taskId");
            flipFilter.addSearch(3, Operate.EQUAL, "checkDepartment");
            List<ByPlusTaskQuality> byPlusTaskQualityList = byPlusTaskQualityManager.list(flipFilter);
            if (byPlusTaskQualityList.size() == 0) {
                byPlusTaskQualityManager.createRecord(id, checkDepartment, username, addTime);
            }
            byPlusMessageManager.remove(messageId);
            return a;
        }
    }

    /**
     * 三检审核完成
     * 1.审核完成时需要计算二检处理周期和三检评定分数,工程超期处理(如果有暂停,则需要特别处理)
     * 2.审核完成前必须要确保三检得分已计算出来,否则不能进行审核完成提交
     * 3.计算任务综合得分(如果有A类错误(二检或者三检),最终分数直接为0)
     * 4.创建通知消息
     * 5.删除项目日期提示信息
     * 6.三检完成时,判断项目是否完成(如果该项目下就一个任务,该任务完成即该项目完成,如果不是,则该项目未完成)
     * 7.删除超期提醒消息
     *
     * @param taskStateId
     * @param id
     */
    @Transactional
    @Override
    public void updateForThreeCheck(Integer taskStateId, String threeInspectionRemark, Integer id) {
        //获取审核时间和三检处理周期
        String addTime = this.getNowDate();
        ByPlusTask byPlusTask = this.get(id);
        String threeRevertDate = byPlusTask.getThreeRevertDate();
        String threeFinishPeriod = this.getFinishPeriod(threeRevertDate, addTime);
        //任务超期时间(任务中途没有暂停时:实际完成时间 = 三检审核完成时间 - 任务创建时间,任务超期时间 = 实际完成时间-预计完成时间,有暂停时:实际完成时间-中途暂停时间才为真实的实际完成时间)
        Integer projectId = byPlusTask.getProjectId();
        //项目预期开始时间
        ByPlusProject byPlusProject = byPlusProjectManager.get(projectId);
        String projectStartDate = byPlusProject.getStartDate();
        //预计工程周期(天)
        Integer projectPeriod = byPlusProject.getProjectPeriod();
        //任务暂停
        Integer stopCount = byPlusTask.getStopCount();
        Integer overDayCount = this.getOverDate(projectStartDate, addTime, projectPeriod, stopCount, id);
        //计算最终得分(二检和三检分数权重分配后的综合得分) 二检权重60%,三检权重40%,如果二检或者三检有A类错误,分数直接为0
        //判断该任务二检或者三检是否有A类错误,errorFlag = 0 为没有,errorFlag = 1为有A类错误
        int errorFlag = this.searchIsATypeErrorByTaskId(id);
        Double taskScore = 0.0;
        if (errorFlag == 0) {
            Double taskTwoScore = byPlusTask.getTaskTwoScore();
            Double taskThreeScore = byPlusTask.getTaskThreeScore();
            BigDecimal a1 = new BigDecimal(taskTwoScore * 0.6);
            BigDecimal a2 = new BigDecimal(taskThreeScore * 0.4);
            taskScore = Double.valueOf((a1.add(a2)).toString());
        }
        byPlusTaskMapper.updateThreeCheckById(taskStateId, addTime, threeFinishPeriod, overDayCount, taskScore, threeInspectionRemark, id);
        //项目是否完成处置,如果项目完成,则修改项目状态,并且删除已有的提示消息
        this.finishProject(id);
        //添加通知消息,三检审核完成之后通知档案部接收,roleId=2
        String taskName = byPlusTask.getTaskName();
        Integer threeInspectionUserId = byPlusTask.getThreeInspectionUserId();
        Integer messageRoleId = 2;
        String message = "三检已完成任务:" + taskName + ",现需整理入档,请您及时处理!";
        Integer recordInspectionUserId = byPlusTask.getRecordInspectionUserId();
        ByPlusMessage byPlusMessage = new ByPlusMessage();
        byPlusMessage.setProjectId(projectId);
        byPlusMessage.setTaskId(id);
        if (recordInspectionUserId == null) {
            byPlusMessage.setMessageRoleId(messageRoleId);
        } else {
            byPlusMessage.setMessageUserId(recordInspectionUserId);
        }
        byPlusMessage.setMessage(message);
        byPlusMessage.setMessageCreateId(threeInspectionUserId);
        byPlusMessage.setMessageType(1);
        //档案部接收状态 taskStateId=108
        byPlusMessage.setTaskStateId(108);
        byPlusMessageManager.upperSave(byPlusMessage);
        //删除超期提醒消息
        byPlusTipMessageManager.deleteMessageByProject(projectId);
    }

    /**
     * 判断是否有A类错误(二检或者三检)
     *
     * @param id
     * @return
     */
    private int searchIsATypeErrorByTaskId(Integer id) {
        int errorFlag = 0;
        FlipFilter flipFilter = new FlipFilter(ByPlusTaskQuality.class);
        flipFilter.addSearch(id, Operate.EQUAL, "taskId");
        flipFilter.addSearch(0, Operate.EQUAL, "dataOneError");
        flipFilter.addSearch(0, Operate.EQUAL, "geographicOneError");
        flipFilter.addSearch(0, Operate.EQUAL, "groomOneError");
        flipFilter.addSearch(0, Operate.EQUAL, "attachmentOneError");
        List<ByPlusTaskQuality> byPlusTaskQualityList = byPlusTaskQualityManager.list(flipFilter);
        if (byPlusTaskQualityList.size() < 2) {
            errorFlag = 1;
        }
        return errorFlag;
    }

    /**
     * 获取任务超期天数
     * 1.计算项目预期开始到三检完成这段时间的时间差
     * 2.判断任务中途是否有暂停,如果存在暂停,则需要减去这部分暂停时间
     *
     * @param projectStartDate 项目预期开始时间
     * @param addTime          三检完成时间
     * @param projectPeriod    预计完成周期
     * @param stopCount        任务暂停次数
     * @param id               任务id
     * @return
     */
    private Integer getOverDate(String projectStartDate, String addTime, Integer projectPeriod, Integer stopCount, Integer id) {
        Integer overDate = 0;
        long nd = 1000 * 24 * 60 * 60; //天数
        long nh = 1000 * 60 * 60;//小时
        long nm = 1000 * 60;//分钟
        Integer differDay = 0;
        //获取总共相差毫秒数
        String startDate = projectStartDate + " 00:00:00";
        Long diff = this.getTimeDifference(startDate, addTime);
        Long stopDiff = 0L;
        if (stopCount != 0) {
            FlipFilter flipFilter = new FlipFilter(ByPlusTaskStop.class);
            flipFilter.addSearch(id, Operate.EQUAL, "taskId");
            List<ByPlusTaskStop> byPlusTaskStopList = byPlusTaskStopManager.list(flipFilter);
            for (ByPlusTaskStop byPlusTaskStop : byPlusTaskStopList) {
                stopDiff += this.getTimeDifference(byPlusTaskStop.getStopDate(), byPlusTaskStop.getRestartDate());
            }
        }
        //真实相差时间
        Long finaDiff = diff - stopDiff;
        Long day = (finaDiff / nd) + 1;//相差天数
        Integer actual = Integer.valueOf(day.toString());
        Integer actualDifferDay = actual.intValue() - projectPeriod.intValue();
        if (actualDifferDay > 0) {
            differDay = actualDifferDay;
        }
        return differDay;
    }

    /**
     * 计算两个时间差
     *
     * @param projectStartDate
     * @param addTime
     * @return
     */
    private Long getTimeDifference(String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long start = null;
        Long diff = null;
        try {
            start = sdf.parse(startDate).getTime();
            Long end = sdf.parse(endDate).getTime();
            diff = end - start;//时间差值(毫秒)
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return diff;
    }

    /**
     * 1.判断项目是否完成,如果任务总数和已完成任务数相等,则说明项目完成(修补,
     * 现由于一个项目下就一个任务,该任务完成,则该项目就完成)
     * 2.如果项目完成,删除项目超期提示消息
     *
     * @param id
     */
    private void finishProject(Integer id) {
        Integer projectId = this.get(id).getProjectId();
//        //判断该项目下是否有未完成的任务,如果存在未完成的,则list的数量不为0
//        FlipFilter flipFilter = new FlipFilter(ByPlusTask.class);
//        flipFilter.addSearch(projectId, Operate.EQUAL, "projectId");
//        flipFilter.addSearch(0, Operate.NOTEQUAL, "flag");
//        List<ByPlusTask> byPlusTaskList = this.list(flipFilter);
//        if (byPlusTaskList.size() == 0) {
        //项目完成状态 字典表 projectStateId=78
        Integer projectStateId = 78;
        byPlusProjectMapper.updateProjectStateById(projectStateId, projectId);
        //删除项目超期提示信息
        byPlusTipMessageManager.deleteMessageByProject(projectId);
//        }
    }

    /**
     * 二检回退任务
     * 1.回退数据记录
     * 2.task相关数据清空,二检回退次数加1
     * 3.创建通知消息
     *
     * @param id
     * @param returnUser
     * @param returnReason
     */
    @Transactional
    @Override
    public void updateForTwoReturn(Integer id, String returnUser, String twoReturnReasonIds) {
        //获取当前时间(格式化)
        String addTime = this.getNowDate();
        //回退进行记录
        ByPlusTask byPlusTask = this.get(id);
        ByPlusTwoReturn byPlusTwoReturn = new ByPlusTwoReturn();
        byPlusTwoReturn.setTaskId(id);
        byPlusTwoReturn.setOneInspectionUser(byPlusTask.getOneInspectionUser());
        byPlusTwoReturn.setOneRevertDate(byPlusTask.getOneRevertDate());
        byPlusTwoReturn.setOneCheckDate(byPlusTask.getOneCheckDate());
        byPlusTwoReturn.setTwoInspectionUser(byPlusTask.getTwoInspectionUser());
        byPlusTwoReturn.setTwoRevertDate(byPlusTask.getTwoRevertDate());
        byPlusTwoReturn.setTwoReturnDate(addTime);
        byPlusTwoReturn.setGroupRevertDate(byPlusTask.getGroupRevertDate());
        byPlusTwoReturn.setGroupCommitDate(byPlusTask.getGroupCommitDate());
        byPlusTwoReturn.setReturnUser(returnUser);
        byPlusTwoReturn.setTwoReturnReasonIds(twoReturnReasonIds);
        byPlusTwoReturnManager.upperSave(byPlusTwoReturn);
        //任务状态改为二检回退小组(小组重新接收时修改任务状态)
        Integer taskStateId = 194;
        //回退次数加1,二检当前回退状态变为回退(回退=2)
        int twoCount = byPlusTask.getTwoReturnCount().intValue() + 1;
        Integer twoReturnCount = new Integer(twoCount);
        byPlusTaskMapper.updateTwoReturnById(taskStateId, twoReturnCount, id);
        //添加通知消息(通知消息需要发送给小组负责人)
        Integer projectId = byPlusTask.getProjectId();
        Integer workGroupEmployeeId = byPlusTask.getWorkGroupEmployeeId();
        String taskName = byPlusTask.getTaskName();
        Integer twoInspectionUserId = byPlusTask.getTwoInspectionUserId();
        String message = "二检核对当前任务:" + taskName + ",发现重大错误,现回退给您,请您及时处理!";
        ByPlusMessage byPlusMessage = new ByPlusMessage();
        byPlusMessage.setProjectId(projectId);
        byPlusMessage.setTaskId(id);
        byPlusMessage.setMessageUserId(workGroupEmployeeId);
        byPlusMessage.setMessage(message);
        byPlusMessage.setMessageCreateId(twoInspectionUserId);
        byPlusMessage.setMessageType(0);
        //小组接收状态 taskStateId=98
        byPlusMessage.setTaskStateId(98);
        byPlusMessageManager.upperSave(byPlusMessage);
    }

    /**
     * 三检回退任务
     * 1.回退数据记录
     * 2.task相关数据清空,三检回退次数加1
     * 3.二检评定质量作废处理
     * 4.创建通知消息
     *
     * @param id
     * @param returnUser
     * @param returnReason
     */
    @Transactional
    @Override
    public void updateForThreeReturn(Integer id, String returnUser, String threeReturnReasonIds) {
        //获取当前时间(格式化)
        String addTime = this.getNowDate();
        //回退进行记录
        ByPlusTask byPlusTask = this.get(id);
        ByPlusThreeReturn byPlusThreeReturn = new ByPlusThreeReturn();
        byPlusThreeReturn.setTaskId(id);
        byPlusThreeReturn.setOneInspectionUser(byPlusTask.getOneInspectionUser());
        byPlusThreeReturn.setOneRevertDate(byPlusTask.getOneRevertDate());
        byPlusThreeReturn.setOneCheckDate(byPlusTask.getOneCheckDate());
        byPlusThreeReturn.setTwoInspectionUser(byPlusTask.getTwoInspectionUser());
        byPlusThreeReturn.setTwoRevertDate(byPlusTask.getTwoRevertDate());
        byPlusThreeReturn.setTwoCheckDate(byPlusTask.getTwoCheckDate());
        byPlusThreeReturn.setThreeInspectionUser(byPlusTask.getThreeInspectionUser());
        byPlusThreeReturn.setThreeRevertDate(byPlusTask.getThreeRevertDate());
        byPlusThreeReturn.setThreeReturnDate(addTime);
        byPlusThreeReturn.setGroupRevertDate(byPlusTask.getGroupRevertDate());
        byPlusThreeReturn.setGroupCommitDate(byPlusTask.getGroupCommitDate());
        byPlusThreeReturn.setTaskTwoScore(byPlusTask.getTaskTwoScore());
        byPlusThreeReturn.setReturnUser(returnUser);
        byPlusThreeReturn.setThreeReturnReasonIds(threeReturnReasonIds);
        byPlusThreeReturnManager.upperSave(byPlusThreeReturn);
        //任务状态改为三检回退到小组(小组重新接收时修改任务状态)
        Integer taskStateId = 193;
        //回退次数加1,三检当前回退状态变为回退(回退=3)
        int threeCount = byPlusTask.getThreeReturnCount().intValue() + 1;
        Integer threeReturnCount = new Integer(threeCount);
        byPlusTaskMapper.updateThreeReturnById(taskStateId, threeReturnCount, id);
        //添加通知消息,三检回退通知小组负责人
        Integer projectId = byPlusTask.getProjectId();
        Integer workGroupEmployeeId = byPlusTask.getWorkGroupEmployeeId();
        String taskName = byPlusTask.getTaskName();
        Integer threeInspectionUserId = byPlusTask.getThreeInspectionUserId();
        String message = "三检核对完当前任务:" + taskName + ",发现重大错误,现回退给您,请您及时处理!";
        ByPlusMessage byPlusMessage = new ByPlusMessage();
        byPlusMessage.setProjectId(projectId);
        byPlusMessage.setTaskId(id);
        byPlusMessage.setMessageUserId(workGroupEmployeeId);
        byPlusMessage.setMessage(message);
        byPlusMessage.setMessageCreateId(threeInspectionUserId);
        byPlusMessage.setMessageType(0);
        //小组接收状态 taskStateId=98
        byPlusMessage.setTaskStateId(98);
        byPlusMessageManager.upperSave(byPlusMessage);
    }

    /**
     * 三检退回二检
     * 1.添加回退记录
     * 2.修改任务状态
     * 3.添加通知消息
     *
     * @param id
     * @param returnReason
     */
    @Override
    public void updateForThreeReturnTwo(Integer id, String returnReasonIds) {
        //获取当前时间(格式化)
        String addTime = this.getNowDate();
        //回退进行记录
        ByPlusTask byPlusTask = this.get(id);
        String twoInspectionUser = byPlusTask.getTwoInspectionUser();
        String threeInspectionUser = byPlusTask.getThreeInspectionUser();
        Double taskTwoScore = byPlusTask.getTaskTwoScore();
        ByPlusThreeReturnTwo byPlusThreeReturnTwo = new ByPlusThreeReturnTwo();
        byPlusThreeReturnTwo.setTaskId(id);
        byPlusThreeReturnTwo.setTwoInspectionUser(twoInspectionUser);
        byPlusThreeReturnTwo.setThreeInspectionUser(threeInspectionUser);
        byPlusThreeReturnTwo.setReturnReasonIds(returnReasonIds);
        byPlusThreeReturnTwo.setTaskTwoScore(taskTwoScore);
        byPlusThreeReturnTwo.setThreeReturnDate(addTime);
        byPlusThreeReturnTwoManager.upperSave(byPlusThreeReturnTwo);
        //任务状态改为三检回退到二检(二检重新接收时修改时间)
        Integer taskStateId = 192;
        //回退次数加1,三检回退二检次数
        int threeReturnTwo = byPlusTask.getThreeReturnTwoFlag().intValue() + 1;
        Integer threeReturnTwoCount = new Integer(threeReturnTwo);
        byPlusTaskMapper.updateThreeReturnTwoById(taskStateId, threeReturnTwoCount, id);
        //添加通知消息,三检回退二检通知二检当时的接收人
        Integer projectId = byPlusTask.getProjectId();
        Integer twoInspectionUserId = byPlusTask.getTwoInspectionUserId();
        String taskName = byPlusTask.getTaskName();
        Integer threeInspectionUserId = byPlusTask.getThreeInspectionUserId();
        String message = "三检核对当前任务:" + taskName + ",发现有问题,现回退给您,请您及时处理!";
        ByPlusMessage byPlusMessage = new ByPlusMessage();
        byPlusMessage.setProjectId(projectId);
        byPlusMessage.setTaskId(id);
        byPlusMessage.setMessageUserId(twoInspectionUserId);
        byPlusMessage.setMessage(message);
        byPlusMessage.setMessageCreateId(threeInspectionUserId);
        byPlusMessage.setMessageType(0);
        //二检接收状态 taskStateId=90
        byPlusMessage.setTaskStateId(90);
        byPlusMessageManager.upperSave(byPlusMessage);
    }

    /**
     * 回退接收
     * 1.修改任务状态
     * 2.删除消息
     *
     * @param taskStateId
     * @param id
     */
    @Transactional
    @Override
    public void updateForReturn(Integer taskStateId, Integer messageId, Integer id) {
        byPlusTaskMapper.updateTaskState(taskStateId, id);
        byPlusMessageManager.remove(messageId);
    }

    /**
     * 资料打印处理
     * 1.添加打印人,打印时间
     * 2.删除消息
     *
     * @param printUserId
     * @param messageId
     * @param id
     */
    @Transactional
    @Override
    public void updateForPrintManage(Integer printUserId, Integer messageId, String printDate, String printRemark, Integer id) {
        byPlusTaskMapper.updatePrintManage(printUserId, printDate, printRemark, id);
        byPlusMessageManager.remove(messageId);
    }

    /**
     * 工程部资料交付
     * 1.添加资料交付时间
     * 2.删除消息
     *
     * @param dataDeliveryDate
     * @param messageId
     * @param id
     */
    @Override
    public void updateForDataDelivery(Integer dataRevertUserId, String dataDeliveryDate, String dataRevertRemark, Integer id, Integer messageId) {
        byPlusTaskMapper.updateDataDelivery(dataRevertUserId, dataDeliveryDate, dataRevertRemark, id);
        byPlusMessageManager.remove(messageId);
    }

    /**
     * 三检指定工程部门打印资料
     * 1.修改打印提醒标识和打印操作时间
     * 2.创建一个提示消息(提示工程部进行资料打印)
     *
     * @param id
     */
    @Transactional
    @Override
    public void updateForSanjianPrint(Integer id) {
        String addDate = this.getNowDate();
        byPlusTaskMapper.updatePrintOperateById(addDate, id);
        ByPlusTask byPlusTask = this.get(id);
        String taskName = byPlusTask.getTaskName();
        Integer projectId = byPlusTask.getProjectId();
        String message = "现需要您安排任务:" + taskName + "的资料打印,望您及时处理!";
        Integer messageUserId = byPlusTask.getEmployeeId();
        Integer userId = byPlusTask.getThreeInspectionUserId();
        ByPlusMessage byPlusMessage = new ByPlusMessage();
        byPlusMessage.setProjectId(projectId);
        byPlusMessage.setTaskId(id);
        byPlusMessage.setMessageUserId(messageUserId);
        byPlusMessage.setMessage(message);
        byPlusMessage.setMessageCreateId(userId);
        byPlusMessage.setMessageType(3);
        //消息类型为3,表示为打印提醒消息
        byPlusMessage.setTaskStateId(null);
        byPlusMessageManager.upperSave(byPlusMessage);
    }

    /**
     * 档案部最终项目入库前进行资料审核判断
     *
     * @param id
     */
    @Override
    public int checkInfo(Integer projectId) {
        int flag = 0;
        FlipFilter flipFilter = new FlipFilter(ByPlusRecordComparison.class);
        flipFilter.addSearch(projectId, Operate.EQUAL, "projectId");
        flipFilter.addSearch(0, Operate.EQUAL, "existFlag");
        List<ByPlusRecordComparison> byPlusRecordComparisonList = byPlusRecordComparisonManager.list(flipFilter);
        if (byPlusRecordComparisonList.size() > 0) {
            return flag;
        } else {
            return flag = 1;
        }
    }

    /**
     * 三检指定综合部接收资料
     * 1.修改资料交付提醒标识和资料交付操作时间
     * 2.创建一个提示消息(提示综合部进行资料接收)
     *
     * @param id
     */
    @Transactional
    @Override
    public void updateForZonghebuRevert(Integer id) {
        String addDate = this.getNowDate();
        byPlusTaskMapper.updateDataOperateById(addDate, id);
        ByPlusTask byPlusTask = this.get(id);
        String taskName = byPlusTask.getTaskName();
        Integer projectId = byPlusTask.getProjectId();
        String message = "现需要您安排任务:" + taskName + "的资料接收,望您及时处理!";
        Integer userId = byPlusTask.getThreeInspectionUserId();
        Integer messageUserId = byPlusProjectMapper.searchProjectCreateUserIdById(projectId);
        ByPlusMessage byPlusMessage = new ByPlusMessage();
        byPlusMessage.setProjectId(projectId);
        byPlusMessage.setTaskId(id);
        byPlusMessage.setMessageUserId(messageUserId);
        byPlusMessage.setMessage(message);
        byPlusMessage.setMessageCreateId(userId);
        byPlusMessage.setMessageType(4);
        //消息类型为4,表示为资料交付提醒消息
        byPlusMessage.setTaskStateId(null);
        byPlusMessageManager.upperSave(byPlusMessage);
    }

    /**
     * 1.任务暂停,再启用设置(默认只允许部门负责人操作)
     * 2.flag=2,添加暂停记录
     * 3.flag=0,修改当前暂停记录的重新开始时间
     *
     * @param flag
     * @param id
     */
    @Transactional
    @Override
    public void updateForTaskFlag(Integer flag, Integer stopOperatorId, String remark, Integer id) {
        ByPlusTask byPlusTask = this.get(id);
        String operateDate = this.getNowDate();
        Integer stopCount = byPlusTask.getStopCount();
        if (flag == 1) {
            int newCount = stopCount.intValue() + 1;
            Integer newStopCount = new Integer(newCount);
            byPlusTaskMapper.updateTaskFlag(flag, newStopCount, id);
            ByPlusTaskStop byPlusTaskStop = new ByPlusTaskStop();
            byPlusTaskStop.setTaskId(id);
            byPlusTaskStop.setStopOperatorId(stopOperatorId);
            byPlusTaskStop.setStopDate(operateDate);
            byPlusTaskStop.setRestartDate("");
            byPlusTaskStop.setStopOrder(newStopCount);
            byPlusTaskStop.setRemark(remark);
            byPlusTaskStopManager.upperSave(byPlusTaskStop);
        } else if (flag == 0) {
            byPlusTaskMapper.updateTaskFlag(flag, stopCount, id);
            //修改当前暂停次序的恢复启用时间
            byPlusTaskStopMapper.updateRestartDateById(operateDate, id, stopCount);
        }
    }

    /**
     * 综合部接收任务(该任务为其他部门协作研发部的任务)
     *
     * @param id
     * @param comprehensiveRevertFlag
     */
    @Transactional
    @Override
    public void updateForComprehensiveTaskRevert(Integer id, Integer comprehensiveRevertFlag) {
        byPlusTaskMapper.updateComprehensiveRevertFlag(comprehensiveRevertFlag, id);
    }

    /**
     * 外协部门接收
     * 1.修改外协记录表对应信息(接收时间)
     * 2.删除消息id
     *
     * @param assistId
     * @param messageId
     */
    @Transactional
    @Override
    public void updateForTaskAssistRevert(Integer taskId, String departmentCode, Integer messageId) {
        String nowTime = this.getNowDate();
        //修改外协部门记录接收时间
        byPlusTaskDepartmentAssistMapper.updateRevertTime(nowTime, taskId, departmentCode);
        byPlusMessageManager.remove(messageId);
    }

    /**
     * 外协部门下达
     * 1.修改外协记录表信息
     * 2.创建通知消息给外协小组
     *
     * @param assistId
     */
    @Transactional
    @Override
    public void updateForTaskAssistReleaseGroup(Integer assistId, String assistGroupCode) {
        ByPlusTaskDepartmentAssist byPlusTaskDepartmentAssist = byPlusTaskDepartmentAssistMapper.searchInfo(assistId);
        //修改外协记录信息
        String revertTime = byPlusTaskDepartmentAssist.getDepartmentRevertDate();
        String finishTime = this.getNowDate();
        String finishPeriod = this.getFinishPeriod(revertTime, finishTime);
        //获取下达小组负责人id
        Integer groupUserId = byBEmployeeManager.searchIdAndNameByCode(assistGroupCode).getId();
        byPlusTaskDepartmentAssistMapper.updateFinishTime(finishTime, finishPeriod, assistGroupCode, groupUserId, assistId);
        //创建通知消息
        Integer taskId = byPlusTaskDepartmentAssist.getTaskId();
        String departmentCode = byPlusTaskDepartmentAssist.getDepartmentCode();
        ByPlusTask byPlusTask = this.get(taskId);
        Integer projectId = byPlusTask.getProjectId();
        String departmentName = byBDepartmentMapper.searchNameByCode(departmentCode);
        String taskName = byPlusTask.getTaskName();
        String message = departmentName + "下达给您一个关于外协任务:" + taskName + "下的协助任务,请您及时处理!";
        Integer createUserId = byPlusTaskDepartmentAssist.getRevertUserId();
        ByPlusMessage byPlusMessage = new ByPlusMessage();
        byPlusMessage.setProjectId(projectId);
        byPlusMessage.setTaskId(taskId);
        byPlusMessage.setMessage(message);
        byPlusMessage.setMessageUserId(groupUserId);
        byPlusMessage.setMessageCreateId(createUserId);
        //由于外协不需要记录状态 taskStateId=-1
        byPlusMessage.setTaskStateId(-1);
        byPlusMessage.setMessageType(6);
        byPlusMessageManager.upperSave(byPlusMessage);
    }

    /**
     * 外协小组接收
     * 1.修改外协记录信息
     * 2.删除消息
     *
     * @param taskId
     * @param groupCode
     * @param messageId
     */
    @Transactional
    @Override
    public void updateForTaskAssistGroupRevert(Integer taskId, String groupCode, Integer messageId) {
        String addTime = this.getNowDate();
        byPlusTaskDepartmentAssistMapper.updateGroupRevert(addTime, groupCode, taskId);
        byPlusMessageManager.remove(messageId);
    }

    /**
     * 外协小组完成
     * 1.判断小组工作量是否填写,没有填写则直接退回
     * 2.修改外协部门记录信息
     * 3.创建通知消息
     *
     * @param assistId
     * @return
     */
    @Transactional
    @Override
    public int updateForTaskAssistGroupFinish(Integer assistId) {
        int a = 0;
        String groupFinishDate = this.getNowDate();
        ByPlusTaskDepartmentAssist byPlusTaskDepartmentAssist = byPlusTaskDepartmentAssistMapper.searchInfo(assistId);
        //判断外协小组工作量是否填写
        Integer taskId = byPlusTaskDepartmentAssist.getTaskId();
        String groupCode = byPlusTaskDepartmentAssist.getGroupCode();
        FlipFilter flipFilter = new FlipFilter(ByPlusTaskWorkload.class);
        flipFilter.addSearch(taskId, Operate.EQUAL, "taskId");
        flipFilter.addSearch(groupCode, Operate.EQUAL, "departmentCode");
        List<ByPlusTaskWorkload> byPlusTaskWorkloadList = byPlusTaskWorkloadManager.list(flipFilter);
        if (byPlusTaskWorkloadList.size() == 0) {
            a = 1;
            return a;
        }
        //修改外协记录信息
        String groupRevertDate = byPlusTaskDepartmentAssist.getGroupRevertDate();
        String groupFinishPeriod = this.getFinishPeriod(groupRevertDate, groupFinishDate);
        byPlusTaskDepartmentAssistMapper.updateGroupFinish(groupFinishDate, groupFinishPeriod, assistId);
        //通知消息(通知外协一检人员接收)
        ByPlusTask byPlusTask = this.get(taskId);
        Integer projectId = byPlusTask.getProjectId();
        Integer assistGroupUserId = byPlusTaskDepartmentAssist.getGroupRevertUserId();
        String taskName = byPlusTask.getTaskName();
        String message = "外协小组已完成任务:" + taskName + "的外协工作,请您及时查看!";
        String departmentCode = byPlusTaskDepartmentAssist.getDepartmentCode();
        ByPlusMessage byPlusMessage = new ByPlusMessage();
        byPlusMessage.setProjectId(projectId);
        byPlusMessage.setTaskId(taskId);
        byPlusMessage.setMessage(message);
        byPlusMessage.setMessageRoleId(7);
        //外协任务不记录任务状态,taskStateId=-1
        byPlusMessage.setTaskStateId(-1);
        byPlusMessage.setMessageCreateId(assistGroupUserId);
        byPlusMessage.setMessageType(7);
        byPlusMessage.setDepartmentCode(departmentCode);
        byPlusMessageManager.upperSave(byPlusMessage);
        return a;
    }

    /**
     * 外协一检接收
     * 1.修改外协部门记录信息
     * 2.删除通知消息
     *
     * @param departmentCode
     * @param messageId
     * @param taskId
     * @param oneInspectionUserId
     */
    @Transactional
    @Override
    public void updateForAssistOneInspectionRevert(String departmentCode, Integer messageId, Integer taskId, Integer oneInspectionUserId) {
        String nowTime = this.getNowDate();
        //修改外协一检接收信息
        byPlusTaskDepartmentAssistMapper.updateAssistOneInspectionRevertTime(oneInspectionUserId, nowTime, taskId, departmentCode);
        byPlusMessageManager.remove(messageId);
    }

    /**
     * 外协一检完成
     * 1.修改外协部门记录信息
     * 2.创建通知消息给小组
     *
     * @param assistId
     */
    @Transactional
    @Override
    public void updateForAssistOneInspectionFinish(Integer assistId) {
        String finishDate = this.getNowDate();
        ByPlusTaskDepartmentAssist byPlusTaskDepartmentAssist = byPlusTaskDepartmentAssistMapper.searchInfo(assistId);
        String revertDate = byPlusTaskDepartmentAssist.getOneInspectionRevertDate();
        String oneInspectionFinishPeriod = this.getFinishPeriod(revertDate, finishDate);
        //修改外协记录信息
        byPlusTaskDepartmentAssistMapper.updateAssistOneInspectionFinishTime(finishDate, oneInspectionFinishPeriod, assistId);
        Integer taskId = byPlusTaskDepartmentAssist.getTaskId();
        ByPlusTask byPlusTask = this.get(taskId);
        Integer projectId = byPlusTask.getProjectId();
        String departmentName = byBDepartmentMapper.searchNameByCode(byPlusTaskDepartmentAssist.getDepartmentCode());
        String taskName = byPlusTask.getTaskName();
        String message = "外协" + departmentName + "一检已完成任务:" + taskName + "的协助工作,请您查看!";
        //任务部门下的小组负责人
        Integer groupUserId = byPlusTask.getWorkGroupEmployeeId();
        //外协一检人员
        Integer assistOneUserId = byPlusTaskDepartmentAssist.getOneInspectionUserId();
        //创建通知消息
        ByPlusMessage byPlusMessage = new ByPlusMessage();
        byPlusMessage.setProjectId(projectId);
        byPlusMessage.setTaskId(taskId);
        byPlusMessage.setMessage(message);
        byPlusMessage.setMessageUserId(groupUserId);
        //由于是外协消息,任务状态 taskStateId=-1
        byPlusMessage.setTaskStateId(-1);
        byPlusMessage.setMessageCreateId(assistOneUserId);
        byPlusMessage.setMessageType(8);
        byPlusMessageManager.upperSave(byPlusMessage);
    }

    /**
     * 小组回退
     * 1.修改外协部门记录信息
     * 2.新增该部门回退记录
     * 3.通知被回退部门消息
     *
     * @param departmentAssistId
     */
    @Transactional
    @Override
    public void updateForGroupReturn(Integer assistId, String returnReasonIds, String remark) {
        ByPlusTaskDepartmentAssist byPlusTaskDepartmentAssist = byPlusTaskDepartmentAssistMapper.searchInfo(assistId);
        //修改外协记录信息(回退次数和完成标识)
        Integer returnCount = byPlusTaskDepartmentAssist.getReturnCount();
        Integer newReturnCount = returnCount.intValue() + 1;
        byPlusTaskDepartmentAssistMapper.updateReturnCountAndFinishFlag(newReturnCount, assistId);
        //新增回退记录
        Integer taskId = byPlusTaskDepartmentAssist.getTaskId();
        ByPlusTask byPlusTask = this.get(taskId);
        String departmentCode = byPlusTask.getWorkGroupCode();
        Integer returnUserId = byPlusTask.getWorkGroupEmployeeId();
        String returnDate = this.getNowDate();
        ByPlusTaskAssistReturn byPlusTaskAssistReturn = new ByPlusTaskAssistReturn();
        byPlusTaskAssistReturn.setDepartmentAssistId(assistId);
        byPlusTaskAssistReturn.setTaskId(taskId);
        byPlusTaskAssistReturn.setDepartmentCode(departmentCode);
        byPlusTaskAssistReturn.setReturnUserId(returnUserId);
        byPlusTaskAssistReturn.setReturnDate(returnDate);
        byPlusTaskAssistReturn.setReturnReasonIds(returnReasonIds);
        byPlusTaskAssistReturn.setRemark(remark);
        byPlusTaskAssistReturnManager.upperSave(byPlusTaskAssistReturn);
        //创建通知消息
        Integer projectId = byPlusTask.getProjectId();
        String taskName = byPlusTask.getTaskName();
        Integer messageUserId = byPlusTaskDepartmentAssist.getGroupRevertUserId();
        String message = "你们小组协助完成任务:" + taskName + "时,提交的资料有问题,现回退给您,请您及时处理!";
        ByPlusMessage byPlusMessage = new ByPlusMessage();
        byPlusMessage.setProjectId(projectId);
        byPlusMessage.setTaskId(taskId);
        byPlusMessage.setMessage(message);
        byPlusMessage.setMessageUserId(messageUserId);
        //当前消息发送给外协部门时,taskStateId=-1
        byPlusMessage.setTaskStateId(-1);
        byPlusMessage.setMessageCreateId(returnUserId);
        byPlusMessage.setMessageType(9);
        byPlusMessageManager.upperSave(byPlusMessage);
    }

    /**
     * 主部门下达外协任务
     * 1.修改外协记录信息
     * 2.修改任务信息(是否有外协)
     * 2.创建通知消息
     *
     * @param assistId
     */
    @Transactional
    @Override
    public void updateForTaskReleaseAssistDepartment(Integer assistId) {
        //修改外协信息
        String releaseDate = this.getNowDate();
        byPlusTaskDepartmentAssistMapper.updateReleaseDate(releaseDate, assistId);
        ByPlusTaskDepartmentAssist byPlusTaskDepartmentAssist = byPlusTaskDepartmentAssistMapper.searchInfo(assistId);
        Integer taskId = byPlusTaskDepartmentAssist.getTaskId();
        ByPlusTask byPlusTask = this.get(taskId);
        //判断该任务下是否已修改外协标识(如果已修改,则不需再处理,如果未修改,则进行处理)
        Integer assistFlag = byPlusTask.getAssistFlag();
        if (assistFlag == 0) {
            byPlusTaskMapper.updateAssistFlagById(taskId);
        }
        String taskName = byPlusTask.getTaskName();
        Integer projectId = byPlusTask.getProjectId();
        //消息处理
        Integer revertUserId = byPlusTaskDepartmentAssist.getRevertUserId();
        Integer releaseUserId = byPlusTask.getEmployeeId();
        String tipMessage = taskName + "任务需要您部门协助,请您及时处理!";
        ByPlusMessage byPlusMessage = new ByPlusMessage();
        byPlusMessage.setProjectId(projectId);
        byPlusMessage.setTaskId(taskId);
        byPlusMessage.setMessageUserId(revertUserId);
        byPlusMessage.setMessage(tipMessage);
        byPlusMessage.setMessageCreateId(releaseUserId);
        byPlusMessage.setMessageType(5);
        //由于外协没有任务状态,此处设置为 taskStateId=-1
        byPlusMessage.setTaskStateId(-1);
        byPlusMessageManager.upperSave(byPlusMessage);
    }

    /**
     * 小组接收外协一检提交的
     * 1.修改外协记录信息
     * 2.删除通知消息
     *
     * @param taskId
     * @param oneInspectionUserId
     * @param messageId
     */
    @Transactional
    @Override
    public void updateForGroupRevertAssistOneInspection(Integer taskId, Integer oneInspectionUserId, Integer messageId) {
        String departmentCode = byBEmployeeMapper.searchInfoById(oneInspectionUserId).getDepartmentCode();
        //修改外协消息
        byPlusTaskDepartmentAssistMapper.updateStateFlag(taskId, departmentCode);
        byPlusMessageManager.remove(messageId);
    }

    /**
     * 小组确认外协一检提交的
     * 1.修改外协记录state_flag
     *
     * @param assistId
     */
    @Transactional
    @Override
    public void updateForGroupConfirmAssistOneInspection(Integer assistId) {
        byPlusTaskDepartmentAssistMapper.updateStateFlagById(assistId);
    }

    /**
     * 工程资料打印完成提示(给三检一个消息提示)
     *
     * @param id
     */
    @Transactional
    @Override
    public void updateForPrintFinishFlag(Integer id) {
        byPlusTaskMapper.updatePrintFinishFlag(id);
        ByPlusTask byPlusTask = this.get(id);
        Integer projectId = byPlusTask.getProjectId();
        String taskName = byPlusTask.getTaskName();
        Integer messageCreateId = byPlusTask.getEmployeeId();
        String message = "任务:" + taskName + "的资料已打印完成,请您及时处理!";
        ByPlusMessage byPlusMessage = new ByPlusMessage();
        byPlusMessage.setProjectId(projectId);
        byPlusMessage.setTaskId(id);
        byPlusMessage.setMessage(message);
        //三检角色 roleId=6
        byPlusMessage.setMessageRoleId(6);
        //消息通知 taskStateId=-1
        byPlusMessage.setTaskStateId(-1);
        byPlusMessage.setMessageCreateId(messageCreateId);
        byPlusMessage.setMessageType(11);
        byPlusMessageManager.upperSave(byPlusMessage);
    }

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
     * @return
     */
    @Override
    public Page searchWithProjectInfo(Integer pageNo, Integer pageSize, String name, Integer recordTypeId, String unitName, String linkman, String telephone, Integer projectPeriod, String regionCode, String startDate, String endDate, String departmentCode, Integer taskStateId, Integer employeeId, Integer workGroupEmployeeId, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId, Integer recordInspectionUserId, Integer outCheckUserId, Integer outCheckState, String workGroupCode, Integer flag, Integer invalidFlag, Integer assistFlag, String fileNumber) {
        //项目表信息查询
        FlipFilter flipFilter = new FlipFilter(ByPlusProject.class);
        flipFilter.addSearch("%" + name + "%", Operate.LIKE, "name");
        flipFilter.addSearch(recordTypeId, Operate.EQUAL, "recordTypeId");
        flipFilter.addSearch("%" + unitName + "%", Operate.LIKE, "unitName");
        flipFilter.addSearch("%" + linkman + "%", Operate.LIKE, "linkman");
        flipFilter.addSearch("%" + telephone + "%", Operate.LIKE, "telephone");
        flipFilter.addSearch(projectPeriod, Operate.EQUAL, "projectPeriod");
        flipFilter.addSearch(regionCode + "%", Operate.LIKE, "regionCode");
        if (!"".equals(startDate) && !"".equals(endDate)) {
            flipFilter.addRegion("startDate", startDate, endDate);
        }
        List<ByPlusProject> byPlusProjectList = byPlusProjectManager.list(flipFilter);
        List<Integer> list = new ArrayList<Integer>();
        for (ByPlusProject byPlusProject : byPlusProjectList) {
            list.add(byPlusProject.getId());
        }
        //判断是否根据项目表信息查到结果
        if (!"".equals(name) || recordTypeId != null || !"".equals(unitName) || !"".equals(linkman) || !"".equals(telephone) || projectPeriod != null || !"".equals(regionCode) || !"".equals(startDate) || !"".equals(endDate)) {
            if (byPlusProjectList.size() == 0) {
                list.add(0);
            }
        }
        FlipFilter flipFilter1 = new FlipFilter(ByPlusTask.class);
        //判断是否根据项目信息查询过,list.siza()=0,说明没有根据项目信息进行过查询,查询时不需要带上该条件
        if (list.size() > 0) {
            flipFilter1.addSearch(list, Operate.IN, "projectId");
        }
        //外协表信息查询(查询外协任务)
        List<Integer> list1 = new ArrayList<Integer>();
        if (assistFlag == 1) {
            List<ByPlusTaskDepartmentAssist> byPlusTaskDepartmentAssistList = byPlusTaskDepartmentAssistMapper.search(departmentCode, employeeId, workGroupCode, workGroupEmployeeId, oneInspectionUserId);
            for (ByPlusTaskDepartmentAssist byPlusTaskDepartmentAssist : byPlusTaskDepartmentAssistList) {
                list1.add(byPlusTaskDepartmentAssist.getTaskId());
            }
            if (list1.size() == 0) {
                list1.add(0);
            }
        }
        if (assistFlag == 1) {
            flipFilter1.addSearch(list1, Operate.IN, "id");
        }
        flipFilter1.addSearch(taskStateId, Operate.EQUAL, "taskStateId");
        if (assistFlag == 0) {
            flipFilter1.addSearch(departmentCode, Operate.EQUAL, "departmentCode");
            flipFilter1.addSearch(employeeId, Operate.EQUAL, "employeeId");
            flipFilter1.addSearch(workGroupEmployeeId, Operate.EQUAL, "workGroupEmployeeId");
            flipFilter1.addSearch(workGroupCode, Operate.EQUAL, "workGroupCode");
            flipFilter1.addSearch(oneInspectionUserId, Operate.EQUAL, "oneInspectionUserId");
        }
        flipFilter1.addSearch(twoInspectionUserId, Operate.EQUAL, "twoInspectionUserId");
        flipFilter1.addSearch(threeInspectionUserId, Operate.EQUAL, "threeInspectionUserId");
        flipFilter1.addSearch(recordInspectionUserId, Operate.EQUAL, "recordInspectionUserId");
        flipFilter1.addSearch(outCheckUserId, Operate.EQUAL, "outCheckUserId");
        flipFilter1.addSearch(outCheckState, Operate.EQUAL, "outCheckState");
        flipFilter1.addSearch(flag, Operate.EQUAL, "flag");
        flipFilter1.addSearch(invalidFlag, Operate.EQUAL, "invalidFlag");
        flipFilter.addSearch("%" + fileNumber + "%", Operate.LIKE, "fileNumber");
        flipFilter1.setPageSize(pageSize);
        flipFilter1.setPageNo(pageNo);
        Page page = this.flipUsingInPage(flipFilter1);
        List<ByPlusTask> byPlusTaskList = page.getListInfo();
        for (ByPlusTask byPlusTask : byPlusTaskList) {
            this.searchInfo(byPlusTask);
        }
        //关联表如果自己独立写sql可以借鉴注释部分
        /*
        List<ByPlusTask> byPlusTaskList = byPlusTaskMapper.searchProjectAndTaskInfo(name, recordTypeId, unitName, linkman, telephone, projectPeriod, regionCode, startDate, endDate, departmentCode, taskStateId, employeeId, workGroupEmployeeId, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId, recordInspectionUserId, outCheckUserId, outCheckState, workGroupCode, flag, invalidFlag, assistFlag);
        FlipFilter flipFilter = new FlipFilter(ByPlusTask.class);
        flipFilter.setPageNo(pageNo);
        flipFilter.setPageSize(pageSize);
        int totalCount = byPlusTaskList.size();
        Page page = new Page(flipFilter, totalCount, byPlusTaskList);
        */
        return page;
    }

    /**
     * 任务表数据导出
     *
     * @param departmentCode
     * @param workGroupCode
     * @param startDate
     * @param endDate
     * @param response
     */
    @Override
    public void taskExport(String departmentCode, String workGroupCode, String startDate, String endDate, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId, HttpServletResponse response) {
        ExcelData excelData = new ExcelData();
        //根据条件查询结果后,导出数据
        FlipFilter flipFilter = new FlipFilter(ByPlusTask.class);
        flipFilter.addSearch(departmentCode, Operate.EQUAL, "departmentCode");
        flipFilter.addSearch(workGroupCode, Operate.EQUAL, "workGroupCode");
        flipFilter.addSearch(oneInspectionUserId, Operate.EQUAL, "oneInspectionUserId");
        flipFilter.addSearch(twoInspectionUserId, Operate.EQUAL, "twoInspectionUserId");
        flipFilter.addSearch(threeInspectionUserId, Operate.EQUAL, "threeInspectionUserId");
        if (!"".equals(startDate) && !"".equals(endDate)) {
            FlipFilter flipFilter1 = new FlipFilter(ByPlusProject.class);
            flipFilter1.addRegion("startDate", startDate, endDate);
            List<ByPlusProject> byPlusProjectList = byPlusProjectManager.list(flipFilter1);
            List<Integer> list = new ArrayList<Integer>();
            for (ByPlusProject byPlusProject : byPlusProjectList) {
                list.add(byPlusProject.getId());
            }
            if (list.size() == 0) {
                list.add(0);
            }
            flipFilter.addSearch(list, Operate.IN, "projectId");
        }
        List<ByPlusTask> byPlusTaskList = this.list(flipFilter);
        //表名设置
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm:ss");
        String localTime = dtf.format(localDateTime).replace("-", "");
        String str = df.format(localDateTime).replace(":", "");
        String name = localTime + str + "项目(任务)信息表";
        //设置excel标题
        List<String> titles = new ArrayList<>();
        //项目表信息
        titles.add("项目名称");
        titles.add("项目计划开始日期");
        titles.add("项目计划结束日期");
        titles.add("项目地址");
        titles.add("项目状态");
        titles.add("项目类型");
        titles.add("区域");
        titles.add("单位名称");
        titles.add("项目费用支付状态");
        titles.add("资料提交情况");
        titles.add("联系人");
        titles.add("联系方式");
        titles.add("经度");
        titles.add("纬度");
        titles.add("资料交付状态");
        titles.add("项目预计周期(天)");
        titles.add("项目创建人");
        titles.add("入库用户");
        titles.add("入库时间");
        titles.add("项目创建时间");
        //任务表信息
        titles.add("部门");
        titles.add("部门负责人");
        titles.add("下达任务人员");
        titles.add("任务状态");
        titles.add("部门接收任务时间");
        titles.add("部门下达任务时间");
        titles.add("部门退回任务时间");
        titles.add("部门退回原因");
        titles.add("部门备注");
        titles.add("小组负责人");
        titles.add("作业小组");
        titles.add("小组接收时间");
        titles.add("小组提交时间");
        titles.add("小组完成周期");
        titles.add("小组备注");
        titles.add("一检人员");
        titles.add("一检接收时间");
        titles.add("一检核对时间");
        titles.add("一检完成周期");
        titles.add("一检备注");
        titles.add("二检人员");
        titles.add("二检接收时间");
        titles.add("二检核对时间");
        titles.add("二检完成周期");
        titles.add("二检评定分数");
        titles.add("二检回退小组次数");
        titles.add("二检备注");
        titles.add("三检人员");
        titles.add("三检接收时间");
        titles.add("三检核对时间");
        titles.add("三检完成周期");
        titles.add("三检评定分数");
        titles.add("三检回退小组次数");
        titles.add("三检退回二检次数");
        titles.add("三检备注");
        titles.add("综合分数");
        titles.add("外查人员");
        titles.add("外查接收时间");
        titles.add("外查完成时间");
        titles.add("外查周期");
        titles.add("外查描述");
        titles.add("二检指定外查开始时间");
        titles.add("外查状态(0为不需要外查,1为需要外查,2为外查接收,3为外查完成)");
        titles.add("档案部接收人员");
        titles.add("档案部接收时间");
        titles.add("档案部核对时间");
        titles.add("档案部回退次数");
        titles.add("超期天数");
        titles.add("任务作废(针对工程部回退,0为不作废,1为作废)");
        titles.add("资料编号");
        titles.add("资料打印标识(0未提醒打印,1已提醒打印)");
        titles.add("打印人");
        titles.add("打印时间");
        titles.add("打印备注");
        titles.add("三检指定打印操作日期");
        titles.add("资料交付时间");
        titles.add("资料接收人");
        titles.add("资料接收备注");
        titles.add("资料交付标识(0未提醒交付,1已提醒交付)");
        titles.add("三检指定资料交付操作日期");
        titles.add("任务标识(0为进行中,1为暂停,2已完成未入库,3为完成且入库)");
        titles.add("任务中途暂停次数(0为没有暂停,其他为暂停次数)");
        titles.add("外协标识(0为没有外协,1为有外协)");
        titles.add("工程部打印完成提醒标识(0为未提醒三检,1为已提醒三检)");
        excelData.setTitles(titles);
        //填充数据
        List<List<Object>> rows = new ArrayList();
        for (ByPlusTask byPlusTask : byPlusTaskList) {
            this.searchInfo(byPlusTask);
            List<Object> row = new ArrayList();
            //项目表信息
            row.add(byPlusTask.getByPlusProject().getName());
            row.add(byPlusTask.getByPlusProject().getStartDate());
            row.add(byPlusTask.getByPlusProject().getFinishDate());
            row.add(byPlusTask.getByPlusProject().getAddress());
            row.add(byPlusTask.getByPlusProject().getProjectState());
            row.add(byPlusTask.getByPlusProject().getRecordTypeName());
            row.add(byPlusTask.getByPlusProject().getRegionName());
            row.add(byPlusTask.getByPlusProject().getUnitName());
            row.add(byPlusTask.getByPlusProject().getProjectCost());
            row.add(byPlusTask.getByPlusProject().getDataCommit());
            row.add(byPlusTask.getByPlusProject().getLinkman());
            row.add(byPlusTask.getByPlusProject().getTelephone());
            row.add(byPlusTask.getByPlusProject().getLongitude());
            row.add(byPlusTask.getByPlusProject().getLatitude());
            row.add(byPlusTask.getByPlusProject().getDataState());
            row.add(byPlusTask.getByPlusProject().getProjectPeriod());
            row.add(byPlusTask.getByPlusProject().getProjectAddUser());
            row.add(byPlusTask.getByPlusProject().getStorageUser());
            row.add(byPlusTask.getByPlusProject().getStorageDate());
            row.add(byPlusTask.getByPlusProject().getAddDate());
            //任务表部门信息
            row.add(byPlusTask.getDepartmentName());
            row.add(byPlusTask.getEmployeeName());
            row.add(byPlusTask.getAssignTaskUser());
            row.add(byPlusTask.getTaskState());
            row.add(byPlusTask.getDepartmentRevertDate());
            row.add(byPlusTask.getDepartmentReleaseDate());
            row.add(byPlusTask.getDepartmentReturnDate());
            row.add(byPlusTask.getReturnReason());
            row.add(byPlusTask.getDepartmentRemark());
            //任务表小组信息
            row.add(byPlusTask.getWorkGroupName());
            row.add(byPlusTask.getWorkGroupName());
            row.add(byPlusTask.getGroupRevertDate());
            row.add(byPlusTask.getGroupCommitDate());
            row.add(byPlusTask.getGroupFinishPeriod());
            row.add(byPlusTask.getGroupRemark());
            //任务表一检信息
            row.add(byPlusTask.getOneInspectionUser());
            row.add(byPlusTask.getOneRevertDate());
            row.add(byPlusTask.getOneCheckDate());
            row.add(byPlusTask.getOneFinishPeriod());
            row.add(byPlusTask.getOneInspectionRemark());
            //任务表二检信息
            row.add(byPlusTask.getTwoInspectionUser());
            row.add(byPlusTask.getTwoRevertDate());
            row.add(byPlusTask.getTwoCheckDate());
            row.add(byPlusTask.getTwoFinishPeriod());
            row.add(byPlusTask.getTaskTwoScore());
            row.add(byPlusTask.getTwoReturnCount());
            row.add(byPlusTask.getTwoInspectionRemark());
            //任务表三检信息
            row.add(byPlusTask.getThreeInspectionUser());
            row.add(byPlusTask.getThreeRevertDate());
            row.add(byPlusTask.getThreeCheckDate());
            row.add(byPlusTask.getThreeFinishPeriod());
            row.add(byPlusTask.getTaskThreeScore());
            row.add(byPlusTask.getThreeReturnCount());
            row.add(byPlusTask.getThreeReturnTwoFlag());
            row.add(byPlusTask.getThreeInspectionRemark());
            //任务表外查信息
            row.add(byPlusTask.getTaskScore());
            row.add(byPlusTask.getOutCheckUser());
            row.add(byPlusTask.getOutCheckRevertDate());
            row.add(byPlusTask.getOutCheckFinishDate());
            row.add(byPlusTask.getOutCheckFinishPeriod());
            row.add(byPlusTask.getOutCheckDescription());
            row.add(byPlusTask.getOutCheckDate());
            row.add(byPlusTask.getOutCheckState());
            //任务表档案信息
            row.add(byPlusTask.getRecordInspectionUser());
            row.add(byPlusTask.getRecordRevertDate());
            row.add(byPlusTask.getRecordCheckDate());
            row.add(byPlusTask.getRecordReturnCount());
            //任务表其他信息
            row.add(byPlusTask.getOverDateCount());
            row.add(byPlusTask.getInvalidFlag());
            row.add(byPlusTask.getFileNumber());
            row.add(byPlusTask.getPrintFlag());
            row.add(byPlusTask.getPrintUserName());
            row.add(byPlusTask.getPrintDate());
            row.add(byPlusTask.getPrintRemark());
            row.add(byPlusTask.getPrintOperateDate());
            row.add(byPlusTask.getDataDeliveryDate());
            row.add(byPlusTask.getDataRevertUserName());
            row.add(byPlusTask.getDataRevertRemark());
            row.add(byPlusTask.getDataDeliveryFlag());
            row.add(byPlusTask.getDataOperateDate());
            row.add(byPlusTask.getFlag());
            row.add(byPlusTask.getStopCount());
            row.add(byPlusTask.getAssistFlag());
            row.add(byPlusTask.getPrintFinishFlag());
            rows.add(row);
        }
        excelData.setRows(rows);
        try {
            ExportExcelUtils.exportExcel(response, name, excelData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, Object> searchForRemark(Integer id) {
        Map<String, Object> map = new HashMap<String, Object>();
        ByPlusTask byPlusTask = this.get(id);
        map.put("departmentRemark", byPlusTask.getDepartmentRemark());
        map.put("groupRemark", byPlusTask.getGroupRemark());
        map.put("remark", byPlusTask.getRemark());
        map.put("oneInspectionRemark", byPlusTask.getOneInspectionRemark());
        map.put("twoInspectionRemark", byPlusTask.getTwoInspectionRemark());
        map.put("threeInspectionRemark", byPlusTask.getThreeInspectionRemark());
        map.put("fileNumber", byPlusTask.getFileNumber());
        return map;
    }

    @Override
    public List<ByPlusTask> searchByProjectId(Integer pid) {

        return byPlusTaskMapper.searchByProjectId(pid);
    }

    /**
     * 小组撤回(部门负责人可以撤回未进行一检的小组的任务)
     * 小组撤回备注记录被撤回小组的名称
     * 1.修改任务状态为部门接收 taskStateId=87;清空小组接收信息;添加小组撤回备注
     * 2.如果小组有工作量,则清空工作量
     *
     * @param id
     * @param groupReturnRemark
     */
    @Transactional
    @Override
    public void updateForGroupRevoke(Integer id, String groupReturnRemark) {
        ByPlusTask byPlusTask = this.get(id);
        String groupCode = byPlusTask.getWorkGroupCode();
        String groupName = byBDepartmentMapper.searchNameByCode(groupCode);
        //将部门下达信息和小组接收信息清空
        Integer workGroupEmployeeId = null;
        byPlusTaskMapper.updateGroupRevoke(groupName, workGroupEmployeeId, id);
        //删除工作量()
        byPlusTaskMapper.removeTaskWorkload(id);
    }

    /**
     * 档案接收
     * 1.判断是否有人接收
     * 2.数据处理
     * 3.删除通知消息
     *
     * @param taskStateId
     * @param id
     * @param username
     */
    @Override
    public int updateForRecordRevert(Integer messageId, Integer taskStateId, Integer id, String username, Integer recordInspectionUserId) {
        Integer taskStateId1 = this.get(id).getTaskStateId();
        int a = 0;
        if (taskStateId1 == 108) {
            a = 1;
            return a;
        } else {
            //获取当前时间(格式化)
            String addTime = this.getNowDate();
            byPlusTaskMapper.updateRecordRevertById(taskStateId, username, addTime, recordInspectionUserId, id);
            byPlusMessageManager.remove(messageId);
            return a;
        }
    }

    /**
     * 档案部资料核对完成
     * 1.档案部核对完成,添加档案信息,项目状态改为已入档
     * 2.创建通知消息(现已取消)
     * 3.删除该任务之前的所有通知消息
     *
     * @param taskStateId
     * @param id
     */
    @Override
    public void updateForRecordCheck(Integer taskStateId, Integer id, ByRRecord byRRecord) {
        //获取当前时间(格式化)
        String addTime = this.getNowDate();
        ByPlusTask byPlusTask = this.get(id);
        Integer projectId = byPlusTask.getProjectId();
        //添加档案记录
        byRRecord.setProjectId(projectId);
        byRRecordManager.upperSave(byRRecord);
        //修改任务状态和任务完成标识
        byPlusTaskMapper.updateRecordCheckById(taskStateId, addTime, id);
        //项目入档完成,修改项目状态为项目入档(79),项目完成flag变成1
        Integer projectStateId = 79;
        Integer flag = 1;
        byPlusProjectManager.updateProjectStateFinish(projectId, projectStateId, flag);
        //删除该任务下所有提示消息
        byPlusMessageManager.removeByTaskId(id);
        //添加通知消息,档案部入档完成后发送消息给办公室创建项目人
        Integer messageUserId = byPlusProjectManager.get(projectId).getProjectAddUserId();
        String taskName = byPlusTask.getTaskName();
        Integer recordInspectionUserId = byPlusTask.getRecordInspectionUserId();
        String message = "档案部针对当前任务:" + taskName + ",已完成入档,请您及时查看!";
        ByPlusMessage byPlusMessage = new ByPlusMessage();
        byPlusMessage.setProjectId(projectId);
        byPlusMessage.setTaskId(id);
        byPlusMessage.setMessageUserId(messageUserId);
        byPlusMessage.setMessage(message);
        byPlusMessage.setMessageCreateId(recordInspectionUserId);
        byPlusMessage.setMessageType(12);
        //办公室接收已入档项目 taskStateId=-1
        byPlusMessage.setTaskStateId(-1);
        byPlusMessageManager.upperSave(byPlusMessage);
    }

    /**
     * 档案回退
     * 1.创建消息通知
     *
     * @param taskStateId
     * @param username
     * @param returnReason
     * @param id
     */
    @Transactional
    @Override
    public void updateForRecordReturn(String username, Integer recordReturnReasonId, Integer id) {
        //获取当前时间(格式化)
        String addTime = this.getNowDate();
        ByPlusTask byPlusTask = this.get(id);
        //任务状态改为二检完成(等到消息通知时,三检去接收)
        Integer taskStateId = 91;
        //回退次数加1,档案回退当前回退状态变为回退(回退=4)
        int threeCount = byPlusTask.getRecordReturnCount().intValue() + 1;
        Integer recordReturnCount = new Integer(threeCount);
        byPlusTaskMapper.updateRecordReturnById(taskStateId, recordReturnCount, id);
        //添加回退记录
        ByPlusRecordReturn byPlusRecordReturn = new ByPlusRecordReturn();
        byPlusRecordReturn.setReturnDate(addTime);
        byPlusRecordReturn.setTaskId(id);
        byPlusRecordReturn.setRecordReturnReasonId(recordReturnReasonId);
        byPlusRecordReturn.setReturnUser(username);
        byPlusRecordReturnManager.upperSave(byPlusRecordReturn);
        //添加消息通知
        Integer projectId = byPlusTask.getProjectId();
        Integer threeInspectionUserId = byPlusTask.getThreeInspectionUserId();
        Integer recordInspectionUserId = byPlusTask.getRecordInspectionUserId();
        String taskName = byPlusTask.getTaskName();
        String message = "档案部入档时发现任务:" + taskName + ",资料不全,现回退给您,请及时处理!";
        ByPlusMessage byPlusMessage = new ByPlusMessage();
        byPlusMessage.setProjectId(projectId);
        byPlusMessage.setTaskId(id);
        byPlusMessage.setMessageUserId(threeInspectionUserId);
        byPlusMessage.setMessage(message);
        byPlusMessage.setMessageCreateId(recordInspectionUserId);
        byPlusMessage.setMessageType(0);
        //三检接收状态 taskStateId=92
        byPlusMessage.setTaskStateId(92);
        byPlusMessageManager.upperSave(byPlusMessage);
    }

    /**
     * 外查接收
     * 1.删除通知消息
     *
     * @param outCheckUser
     * @param outCheckUserId
     * @param id
     */
    @Transactional
    @Override
    public int updateForOutRevert(Integer messageId, String outCheckUser, Integer outCheckUserId, Integer id) {
        int a = 0;
        ByPlusTask byPlusTask = this.get(id);
        String outCheckRevertDate = byPlusTask.getOutCheckRevertDate();
        if (StringHandler.isNotEmptyOrNull(outCheckRevertDate)) {
            a = 1;
            return a;
        } else {
            //获取当前时间(格式化)
            String addTime = this.getNowDate();
            byPlusTaskMapper.updateOutRevertById(outCheckUser, outCheckUserId, addTime, id);
            byPlusMessageManager.remove(messageId);
            return a;
        }
    }

    /**
     * 外查审核完成
     * 1.创建消息通知
     * 2.删除外查提示消息
     *
     * @param taskStateId
     * @param id
     */
    @Transactional
    @Override
    public void updateForOutCheck(String outCheckDescription, Integer id) {
        //获取当前时间(格式化)
        String addTime = this.getNowDate();
        ByPlusTask byPlusTask = this.get(id);
        String outCheckRevertDate = byPlusTask.getOutCheckRevertDate();
        String outCheckFinishPeriod = this.getFinishPeriod(outCheckRevertDate, addTime);
        byPlusTaskMapper.updateOutCheckById(outCheckDescription, outCheckFinishPeriod, addTime, id);
        //添加通知消息,外查完成后通知消息给二检人员
        Integer projectId = byPlusTask.getProjectId();
        //二检处理人员id
        Integer twoInspectionUserId = byPlusTask.getTwoInspectionUserId();
        String taskName = byPlusTask.getTaskName();
        Integer outCheckUserId = byPlusTask.getOutCheckUserId();
        String message = "外查已完成当前任务:" + taskName + ",请您及时查看";
        ByPlusMessage byPlusMessage = new ByPlusMessage();
        byPlusMessage.setProjectId(projectId);
        byPlusMessage.setTaskId(id);
        byPlusMessage.setMessageUserId(twoInspectionUserId);
        byPlusMessage.setMessage(message);
        byPlusMessage.setMessageCreateId(outCheckUserId);
        byPlusMessage.setMessageType(2);
        //二检接收状态 taskStateId=90,如果消息类型是2的话,任务状态为0
        byPlusMessage.setTaskStateId(0);
        byPlusMessageManager.upperSave(byPlusMessage);
        //删除外查提示消息
        byPlusOutCheckMessageManager.removeByTaskId(id);
    }

    /**
     * 二检指定任务进行外查
     * 1.创建通知消息
     * 2.创建外查超期消息
     *
     * @param outCheckDate
     * @param id
     */
    @Transactional
    @Override
    public void updateForRequireOutRevert(String outCheckDate, Integer id) {
        ByPlusTask byPlusTask = this.get(id);
        Integer projectId = byPlusTask.getProjectId();
        Integer isOutCheck = byPlusTask.getOutCheckState();
        String oldOutCheckDate = byPlusTask.getOutCheckDate();
        Integer twoInspectionUserId = byPlusTask.getTwoInspectionUserId();
        byPlusTaskMapper.updateRequireOutCheckById(outCheckDate, id);
        //判断二检是否有修改外查时间,如果是第一次则创建通知消息
        if (isOutCheck == 0) {
            //添加通知消息
            String taskName = byPlusTask.getTaskName();
            //二检确定该任务需要外查,发送通知给外查用户 roleId=9;
            Integer messageRoleId = 9;
            String message = "二检判定该任务:" + taskName + ",需要外查,请您及时处理!";
            Integer outCheckUserId = byPlusTask.getOutCheckUserId();
            ByPlusMessage byPlusMessage = new ByPlusMessage();
            byPlusMessage.setProjectId(projectId);
            byPlusMessage.setTaskId(id);
            if (outCheckUserId == null) {
                byPlusMessage.setMessageRoleId(messageRoleId);
            } else {
                byPlusMessage.setMessageUserId(outCheckUserId);
            }
            byPlusMessage.setMessage(message);
            byPlusMessage.setMessageCreateId(twoInspectionUserId);
            byPlusMessage.setMessageType(1);
            //外查接收状态 为二检接收状态taskStateId=90
            byPlusMessage.setTaskStateId(90);
            byPlusMessageManager.upperSave(byPlusMessage);
        }
        //创建外查提醒消息(针对给定的日期,提示是否超期或者还有多少天需要提交(查询时处理))
        byPlusOutCheckMessageManager.removeByTaskId(id);
        ByPlusOutCheckMessage byPlusOutCheckMessage = new ByPlusOutCheckMessage();
        byPlusOutCheckMessage.setTaskId(id);
        byPlusOutCheckMessage.setMessageContent("");
        byPlusOutCheckMessage.setOutCheckFinishDate(outCheckDate);
        byPlusOutCheckMessage.setTwoCheckUserId(twoInspectionUserId);
        if (StringHandler.isEmptyOrNull(oldOutCheckDate)) {
            byPlusOutCheckMessage.setIsUpdate(0);
        } else {
            byPlusOutCheckMessage.setIsUpdate(1);
        }
        byPlusOutCheckMessageManager.upperSave(byPlusOutCheckMessage);

    }

    /**
     * 查询任务详情
     *
     * @param byPlusTask
     */
    private void searchInfo(ByPlusTask byPlusTask) { //部门名称
        String departmentCode = byPlusTask.getDepartmentCode();
        if (StringHandler.isNotEmptyOrNull(departmentCode)) {
            String departmentName = byBDepartmentMapper.getByCode(departmentCode).getDepartmentName();
            byPlusTask.setDepartmentName(departmentName);
        } else {
            byPlusTask.setDepartmentName("");
        }
        //小组名称
        String workGroupCode = byPlusTask.getWorkGroupCode();
        if (StringHandler.isNotEmptyOrNull(workGroupCode)) {
            String workGroupName = byBDepartmentMapper.getByCode(workGroupCode).getDepartmentName();
            byPlusTask.setWorkGroupName(workGroupName);
        } else {
            byPlusTask.setWorkGroupName("");
        }

        //负责人
        Integer employeeId = byPlusTask.getEmployeeId();
        if (employeeId != null) {
            String employeeName = byBEmployeeManager.get(employeeId).getUsername();
            byPlusTask.setEmployeeName(employeeName);
        } else {
            byPlusTask.setEmployeeName("");
        }
        //项目名称
        Integer projectId = byPlusTask.getProjectId();
        if (projectId != null) {
            ByPlusProject byPlusProject = byPlusProjectManager.searchById(projectId);
            byPlusTask.setByPlusProject(byPlusProject);
        } else {
            byPlusTask.setByPlusProject(null);
        }
        //任务状态
        Integer taskStateId = byPlusTask.getTaskStateId();
        if (taskStateId != null) {
            String taskState = byBDictionaryChildManager.get(taskStateId).getAlias();
            byPlusTask.setTaskState(taskState);
        } else {
            byPlusTask.setTaskState("");
        }
        //退回原因
        Integer returnReasonId = byPlusTask.getReturnReasonId();
        if (returnReasonId != null) {
            String returnReason = byBDictionaryChildManager.get(returnReasonId).getAlias();
            byPlusTask.setReturnReason(returnReason);
        } else {
            byPlusTask.setReturnReason("");
        }
        //打印人
        Integer printUserId = byPlusTask.getPrintUserId();
        if (printUserId != null) {
            String printUserName = byBEmployeeManager.get(printUserId).getUsername();
            byPlusTask.setPrintUserName(printUserName);
        } else {
            byPlusTask.setPrintUserName("");
        }
        //资料接收人
        Integer dataRevertUserId = byPlusTask.getDataRevertUserId();
        if (dataRevertUserId != null) {
            String dataRevertUserName = byBEmployeeManager.get(dataRevertUserId).getUsername();
            byPlusTask.setDataRevertUserName(dataRevertUserName);
        } else {
            byPlusTask.setDataRevertUserName("");
        }
    }

    /**
     * 获取当前时间
     *
     * @return addTime
     */
    private String getNowDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String addTime = sdf.format(new Date());
        return addTime;
    }

    /**
     * 获取两个时间差,并转换为天数和小时
     * 格式 : a天b小时
     *
     * @param groupRevertDate
     * @param addTime
     * @return
     */
    private String getFinishPeriod(String revertDate, String addTime) {
        long nd = 1000 * 24 * 60 * 60; //天数
        long nh = 1000 * 60 * 60;//小时
        long nm = 1000 * 60;//分钟
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String groupFinishPeriod = "";
        try {
            Long start = sdf.parse(revertDate).getTime();
            Long end = sdf.parse(addTime).getTime();
            Long diff = end - start;//时间差值(毫秒)
            Long day = diff / nd;//相差天数
            Long hour = diff % nd / nh;//相差小时
            Long min = diff % nd % nh / nm;//相差分钟
            if (day > 0) {
                groupFinishPeriod = day + "天";
            }
            if (hour > 0) {
                groupFinishPeriod = groupFinishPeriod + hour + "小时";
            }
            if (min > 0) {
                int min1 = min.intValue() + 1;
                groupFinishPeriod = groupFinishPeriod + min1 + "分钟";
            }
            if ("".equals(groupFinishPeriod)) {
                groupFinishPeriod = "1分钟";
            }
//            groupFinishPeriod = day + "天" + hour + "小时" + min + "分钟";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return groupFinishPeriod;
    }
}