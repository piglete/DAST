package club.emergency.project.manager.impl;

import club.emergency.by_b_department.mapper.ByBDepartmentMapper;
import club.emergency.by_b_employee.mapper.ByBEmployeeMapper;
import club.emergency.dictionary.manager.ByBDictionaryChildManager;
import club.emergency.project.manager.ByPlusTaskAssistReturnManager;
import club.emergency.project.mapper.ByPlusTaskAssistReturnMapper;
import club.emergency.project.model.ByPlusTaskAssistReturn;
import club.map.core.manager.impl.GenericManagerImpl;
import club.map.core.model.FlipFilter;
import club.map.core.model.Operate;
import com.wanqing.util.StringHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class ByPlusTaskAssistReturnManagerImpl extends GenericManagerImpl<ByPlusTaskAssistReturn, Integer> implements ByPlusTaskAssistReturnManager {

    private ByBDepartmentMapper byBDepartmentMapper;
    private ByBEmployeeMapper byBEmployeeMapper;
    private ByBDictionaryChildManager byBDictionaryChildManager;

    @Autowired
    public ByPlusTaskAssistReturnManagerImpl(ByPlusTaskAssistReturnMapper mapper,
                                             ByBDepartmentMapper byBDepartmentMapper,
                                             ByBEmployeeMapper byBEmployeeMapper,
                                             ByBDictionaryChildManager byBDictionaryChildManager
    ) {
        super(mapper, ByPlusTaskAssistReturn.class);
        this.byBDepartmentMapper = byBDepartmentMapper;
        this.byBEmployeeMapper = byBEmployeeMapper;
        this.byBDictionaryChildManager = byBDictionaryChildManager;
    }

    @Override
    public List<ByPlusTaskAssistReturn> search(Integer taskId, Integer departmentAssistId, String departmentCode) {
        FlipFilter flipFilter = new FlipFilter(ByPlusTaskAssistReturn.class);
        flipFilter.addSearch(taskId, Operate.EQUAL, "taskId");
        flipFilter.addSearch(departmentAssistId, Operate.EQUAL, "departmentAssistId");
        flipFilter.addSearch(departmentCode, Operate.EQUAL, "departmentCode");
        List<ByPlusTaskAssistReturn> byPlusTaskAssistReturnList = this.list(flipFilter);
        for (ByPlusTaskAssistReturn byPlusTaskAssistReturn : byPlusTaskAssistReturnList) {
            this.searchInfo(byPlusTaskAssistReturn);
        }
        return byPlusTaskAssistReturnList;
    }

    @Transactional
    @Override
    public void upperSave(ByPlusTaskAssistReturn byPlusTaskAssistReturn) {
        this.save(byPlusTaskAssistReturn);
    }

    @Override
    public ByPlusTaskAssistReturn searchDetail(Integer id) {
        ByPlusTaskAssistReturn byPlusTaskAssistReturn = this.get(id);
        this.searchInfo(byPlusTaskAssistReturn);
        return byPlusTaskAssistReturn;
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
     * 查询详情,注入其他信息(根据id获取对应的名称)
     *
     * @param byPlusTaskAssistReturn
     */
    private void searchInfo(ByPlusTaskAssistReturn byPlusTaskAssistReturn) {
        //外协部门
        String departmentCode = byPlusTaskAssistReturn.getDepartmentCode();
        if (StringHandler.isNotEmptyOrNull(departmentCode)) {
            String departmentName = byBDepartmentMapper.searchNameByCode(departmentCode);
            byPlusTaskAssistReturn.setDepartmentName(departmentName);
        } else {
            byPlusTaskAssistReturn.setDepartmentName("");
        }
        //退回人
        Integer returnUserId = byPlusTaskAssistReturn.getReturnUserId();
        if (returnUserId != null) {
            String returnUser = byBEmployeeMapper.searchUsernameAndDepartmentCodeById(returnUserId).getUsername();
            byPlusTaskAssistReturn.setReturnUser(returnUser);
        } else {
            byPlusTaskAssistReturn.setReturnUser("");
        }
        //退回原因
        String returnReasonIds = byPlusTaskAssistReturn.getReturnReasonIds();
        if (StringHandler.isNotEmptyOrNull(returnReasonIds)) {
            String returnReason = "";
            String[] idArr = returnReasonIds.split(",");
            for (String s : idArr) {
                String reasonName = byBDictionaryChildManager.get(Integer.valueOf(s)).getAlias();
                returnReason += reasonName + "、";
            }
            returnReason = returnReason.substring(0, returnReason.length() - 1);
            byPlusTaskAssistReturn.setReturnReason(returnReason);
        } else {
            byPlusTaskAssistReturn.setReturnReason("");
        }
    }
}