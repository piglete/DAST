package club.emergency.project.manager.impl;

import club.emergency.by_b_department.mapper.ByBDepartmentMapper;
import club.emergency.by_b_employee.mapper.ByBEmployeeMapper;
import club.emergency.by_b_employee.model.ByBEmployee;
import club.emergency.dictionary.manager.ByBDictionaryChildManager;
import club.emergency.project.manager.ByPlusTaskDepartmentAssistManager;
import club.emergency.project.manager.ByPlusTaskManager;
import club.emergency.project.mapper.ByPlusTaskDepartmentAssistMapper;
import club.emergency.project.model.ByPlusTask;
import club.emergency.project.model.ByPlusTaskDepartmentAssist;
import club.map.core.manager.impl.GenericManagerImpl;
import club.map.core.model.FlipFilter;
import club.map.core.model.Operate;
import com.wanqing.util.StringHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ByPlusTaskDepartmentAssistManagerImpl extends GenericManagerImpl<ByPlusTaskDepartmentAssist, Integer> implements ByPlusTaskDepartmentAssistManager {

    private ByBEmployeeMapper byBEmployeeMapper;
    private ByBDepartmentMapper byBDepartmentMapper;
    private ByPlusTaskManager byPlusTaskManager;
    private ByBDictionaryChildManager byBDictionaryChildManager;

    @Autowired
    public ByPlusTaskDepartmentAssistManagerImpl(ByPlusTaskDepartmentAssistMapper mapper,
                                                 ByBEmployeeMapper byBEmployeeMapper,
                                                 ByBDepartmentMapper byBDepartmentMapper,
                                                 ByPlusTaskManager byPlusTaskManager,
                                                 ByBDictionaryChildManager byBDictionaryChildManager
    ) {
        super(mapper, ByPlusTaskDepartmentAssist.class);
        this.byBEmployeeMapper = byBEmployeeMapper;
        this.byBDepartmentMapper = byBDepartmentMapper;
        this.byPlusTaskManager = byPlusTaskManager;
        this.byBDictionaryChildManager = byBDictionaryChildManager;
    }

    /**
     * 外协信息查询(查询该任务下的外协部门 flag=0,查询该外协部门的所有外协任务 flag=1)
     *
     * @param taskId
     * @param departmentCode
     * @param groupCode
     * @param revertUserId
     * @param groupRevertUserId
     * @param flag
     * @param stateFlag
     * @param finishFlag
     * @return
     */
    @Override
    public List<ByPlusTaskDepartmentAssist> search(Integer taskId, String departmentCode, String groupCode, Integer revertUserId, Integer groupRevertUserId, Integer flag, Integer stateFlag, Integer finishFlag) {
        FlipFilter flipFilter = new FlipFilter(ByPlusTaskDepartmentAssist.class);
        flipFilter.addSearch(taskId, Operate.EQUAL, "taskId");
        flipFilter.addSearch(departmentCode, Operate.EQUAL, "departmentCode");
        flipFilter.addSearch(groupCode, Operate.EQUAL, "groupCode");
        flipFilter.addSearch(revertUserId, Operate.EQUAL, "revertUserId");
        flipFilter.addSearch(groupRevertUserId, Operate.EQUAL, "groupRevertUserId");
        flipFilter.addSearch(stateFlag, Operate.EQUAL, "stateFlag");
        flipFilter.addSearch(finishFlag, Operate.EQUAL, "finishFlag");
        List<ByPlusTaskDepartmentAssist> byPlusTaskDepartmentAssistList = this.list(flipFilter);
        for (ByPlusTaskDepartmentAssist byPlusTaskDepartmentAssist : byPlusTaskDepartmentAssistList) {
            this.searchInfo(byPlusTaskDepartmentAssist);
            if (flag == 1) {
                this.searchTaskInfo(byPlusTaskDepartmentAssist);
            }
        }
        return byPlusTaskDepartmentAssistList;
    }

    /**
     * 查询该外协任务详情
     *
     * @param byPlusTaskDepartmentAssist
     */
    private void searchTaskInfo(ByPlusTaskDepartmentAssist byPlusTaskDepartmentAssist) {
        //任务表信息
        Integer taskId = byPlusTaskDepartmentAssist.getTaskId();
        ByPlusTask byPlusTask = byPlusTaskManager.searchDetail(taskId);
        byPlusTaskDepartmentAssist.setByPlusTask(byPlusTask);
    }

    @Transactional
    @Override
    public int upperSave(ByPlusTaskDepartmentAssist byPlusTaskDepartmentAssist) {
        int a = 0;
        Integer taskId = byPlusTaskDepartmentAssist.getTaskId();
        String departmentCode = byPlusTaskDepartmentAssist.getDepartmentCode();
        FlipFilter flipFilter = new FlipFilter(ByPlusTaskDepartmentAssist.class);
        flipFilter.addSearch(taskId, Operate.EQUAL, "taskId");
        flipFilter.addSearch(departmentCode, Operate.EQUAL, "departmentCode");
        List<ByPlusTaskDepartmentAssist> byPlusTaskDepartmentAssistList = this.list(flipFilter);
        if (byPlusTaskDepartmentAssistList.size() > 0) {
            a = 1;
            return a;
        }
        Integer id = byPlusTaskDepartmentAssist.getId();
        if (id == null) {
            //部门负责人参数设置
            String positionState = "在职";
            String isLeader = "是";
            //查询部门负责人
            ByBEmployee byBEmployee = byBEmployeeMapper.searchIdByDepartmentCodeInfo(departmentCode, positionState, isLeader);
            Integer revertUserId = byBEmployee.getId();
            byPlusTaskDepartmentAssist.setRevertUserId(revertUserId);
        }
        this.save(byPlusTaskDepartmentAssist);
        return a;
    }

    @Override
    public ByPlusTaskDepartmentAssist searchDetail(Integer id) {
        ByPlusTaskDepartmentAssist byPlusTaskDepartmentAssist = this.get(id);
        this.searchInfo(byPlusTaskDepartmentAssist);
        return byPlusTaskDepartmentAssist;
    }

    @Transactional
    @Override
    public void removeByIds(String ids) {
        String[] idArr = ids.split(",");
        Arrays.asList(idArr).forEach(id -> {
            this.remove(Integer.valueOf(id));
        });
    }

    /**
     * 查询详情
     *
     * @param byPlusTaskDepartmentAssist
     */
    private void searchInfo(ByPlusTaskDepartmentAssist byPlusTaskDepartmentAssist) {
        //外协部门
        String departmentCode = byPlusTaskDepartmentAssist.getDepartmentCode();
        if (StringHandler.isNotEmptyOrNull(departmentCode)) {
            String departmentName = byBDepartmentMapper.searchNameByCode(departmentCode);
            byPlusTaskDepartmentAssist.setDepartmentName(departmentName);
        } else {
            byPlusTaskDepartmentAssist.setDepartmentName("");
        }
        //部门接收人
        Integer revertUserId = byPlusTaskDepartmentAssist.getRevertUserId();
        if (revertUserId != null) {
            String revertUser = byBEmployeeMapper.searchUsernameAndDepartmentCodeById(revertUserId).getUsername();
            byPlusTaskDepartmentAssist.setRevertUser(revertUser);
        } else {
            byPlusTaskDepartmentAssist.setRevertUser("");
        }
        //小组名称
        String groupCode = byPlusTaskDepartmentAssist.getGroupCode();
        if (StringHandler.isNotEmptyOrNull(groupCode)) {
            String groupName = byBDepartmentMapper.searchNameByCode(groupCode);
            byPlusTaskDepartmentAssist.setGroupName(groupName);
        } else {
            byPlusTaskDepartmentAssist.setGroupName("");
        }
        //小组负责人
        Integer groupRevertUserId = byPlusTaskDepartmentAssist.getGroupRevertUserId();
        if (groupRevertUserId != null) {
            String groupRevertUser = byBEmployeeMapper.searchUsernameAndDepartmentCodeById(groupRevertUserId).getUsername();
            byPlusTaskDepartmentAssist.setGroupRevertUser(groupRevertUser);
        } else {
            byPlusTaskDepartmentAssist.setGroupRevertUser("");
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
}