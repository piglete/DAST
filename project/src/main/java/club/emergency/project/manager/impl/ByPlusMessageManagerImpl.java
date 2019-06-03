package club.emergency.project.manager.impl;

import club.emergency.by_b_employee.manager.ByBEmployeeManager;
import club.emergency.by_b_employee.model.ByBEmployee;
import club.emergency.project.manager.ByPlusMessageManager;
import club.emergency.project.manager.ByPlusProjectManager;
import club.emergency.project.manager.ByPlusTipMessageManager;
import club.emergency.project.mapper.ByPlusMessageMapper;
import club.emergency.project.model.ByPlusMessage;
import club.map.core.manager.impl.GenericManagerImpl;
import club.map.core.model.FlipFilter;
import club.map.core.model.Operate;
import club.map.core.model.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ByPlusMessageManagerImpl extends GenericManagerImpl<ByPlusMessage, Integer> implements ByPlusMessageManager {

    private ByPlusMessageMapper byPlusMessageMapper;
    private ByBEmployeeManager byBEmployeeManager;
    private ByPlusTipMessageManager byPlusTipMessageManager;
    private ByPlusProjectManager byPlusProjectManager;

    @Autowired
    public ByPlusMessageManagerImpl(ByPlusMessageMapper mapper,
                                    ByPlusMessageMapper byPlusMessageMapper,
                                    ByBEmployeeManager byBEmployeeManager,
                                    ByPlusTipMessageManager byPlusTipMessageManager,
                                    ByPlusProjectManager byPlusProjectManager) {
        super(mapper, ByPlusMessage.class);
        this.byPlusMessageMapper = byPlusMessageMapper;
        this.byBEmployeeManager = byBEmployeeManager;
        this.byPlusTipMessageManager = byPlusTipMessageManager;
        this.byPlusProjectManager = byPlusProjectManager;
    }

    @Transactional
    @Override
    public void upperSave(ByPlusMessage byPlusMessage) {
        this.save(byPlusMessage);
    }

    /**
     * 查询做过滤
     * 1.判断messageRoleId的角色是不是对应一检(roleId=7),二检(roleId=5),三检(roleId=6),外查(roleId=9),档案整理(roleId=2)
     * ,如果是,查询条件用messageRoleId去匹配;如果不是,用messageUserId去匹配
     * 2.二检或者三检首次接收消息时是以自己对应的角色接收,如果后面有退回,则消息需要再通知给当时接收任务的人员
     *
     * @param projectId
     * @param messageUserId
     * @param messageRoleId
     * @param taskStateId
     * @return
     */
    @Override
    public List<ByPlusMessage> search(Integer projectId, Integer messageUserId, Integer messageRoleId, Integer taskStateId, String departmentCode) {
        Long start = System.currentTimeMillis();
        //查询or连接的(主要是对通知给二检的和二检某个人的)
        FlipFilter flipFilter = new FlipFilter(ByPlusMessage.class);
        flipFilter.addSearch(projectId, Operate.EQUAL, "projectId");
        flipFilter.addSearch(taskStateId, Operate.EQUAL, "taskStateId");
        flipFilter.addSearch(departmentCode, Operate.EQUAL, "departmentCode");
        List<Parameter> parameterList = new ArrayList<>();
        if (messageUserId != null) {
            Parameter p1 = new Parameter("messageUserId", messageUserId, Operate.EQUAL);
            parameterList.add(p1);
        }
        if (messageRoleId != null) {
            Parameter p2 = new Parameter("messageRoleId", messageRoleId, Operate.EQUAL);
            parameterList.add(p2);
        }
        flipFilter.addOrSearch(parameterList);
        List<ByPlusMessage> byPlusMessageList = this.list(flipFilter);
        for (ByPlusMessage byPlusMessage : byPlusMessageList) {
            this.searchInfo(byPlusMessage);
        }
        return byPlusMessageList;
    }

    /**
     * 消息详情
     *
     * @param byPlusMessage
     */
    private void searchInfo(ByPlusMessage byPlusMessage) {
        //获取任务名称
        Integer taskId = byPlusMessage.getTaskId();
        String taskName = byPlusMessageMapper.getTaskNameByTaskId(taskId);
        byPlusMessage.setTaskName(taskName);
        //获取员工姓名和部门
        Integer messageCreateId = byPlusMessage.getMessageCreateId();
        ByBEmployee byBEmployee = byBEmployeeManager.searchUsernameAndDepartmentNameById(messageCreateId);
        String employeeName = byBEmployee.getUsername();
        String departmentName = byBEmployee.getDepartmentName();
        String messageCreateInfo = employeeName + "(" + departmentName + ")";
        byPlusMessage.setMessageCreateInfo(messageCreateInfo);
    }

    @Transactional
    @Override
    public void removeByIds(String ids) {
        String[] idArr = ids.split(",");
        Arrays.asList(idArr).forEach(id -> {
            this.remove(Integer.valueOf(id));
        });
    }

    @Transactional
    @Override
    public void removeByTaskId(Integer id) {
        byPlusMessageMapper.removeByTaskId(id);
    }

}