package club.emergency.by_b_employee.manager.impl;

import club.emergency.by_b_department.mapper.ByBDepartmentMapper;
import club.emergency.by_b_department.model.ByBDepartment;
import club.emergency.by_b_employee.manager.ByBEmployeeManager;
import club.emergency.by_b_employee.mapper.ByBEmployeeMapper;
import club.emergency.by_b_employee.model.ByBEmployee;
import club.emergency.dictionary.manager.ByBDictionaryChildManager;
import club.map.core.manager.impl.GenericManagerImpl;
import club.map.core.model.FlipFilter;
import club.map.core.model.Operate;
import club.map.core.model.Page;
import club.map.core.util.PinyinUtil;
import com.wanqing.util.StringHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ByBEmployeeManagerImpl extends GenericManagerImpl<ByBEmployee, Integer> implements ByBEmployeeManager {

    private ByBDepartmentMapper byBDepartmentMapper;
    private ByBEmployeeMapper byBEmployeeMapper;
    private ByBDictionaryChildManager byBDictionaryChildManager;

    @Autowired
    public ByBEmployeeManagerImpl(ByBEmployeeMapper mapper,
                                  ByBDepartmentMapper byBDepartmentMapper,
                                  ByBEmployeeMapper byBEmployeeMapper,
                                  ByBDictionaryChildManager byBDictionaryChildManager) {
        super(mapper, ByBEmployee.class);
        this.byBDepartmentMapper = byBDepartmentMapper;
        this.byBEmployeeMapper = byBEmployeeMapper;
        this.byBDictionaryChildManager = byBDictionaryChildManager;
    }

    @Override
    public Page search(String username, String telephone, String sex, String pinyin, String positionState, String departmentCode, String isLeader, Integer pageNo, Integer pageSize) {
        FlipFilter flipFilter = new FlipFilter(ByBEmployee.class);
        flipFilter.addSearch("%" + username + "%", Operate.LIKE, "username");
        flipFilter.addSearch("%" + telephone + "%", Operate.LIKE, "telephone");
        flipFilter.addSearch(sex, Operate.EQUAL, "sex");
        flipFilter.addSearch("%" + pinyin + "%", Operate.LIKE, "pinyin");
        flipFilter.addSearch(positionState, Operate.EQUAL, "positionState");
        flipFilter.addSearch(departmentCode + "%", Operate.LIKE, "departmentCode");
        flipFilter.addSearch(isLeader, Operate.EQUAL, "isLeader");
        flipFilter.setPageNo(pageNo);
        flipFilter.setPageSize(pageSize);
        Page page = this.flipUsingInPage(flipFilter);
        List<ByBEmployee> byBEmployeeList = page.getListInfo();
        byBEmployeeList.forEach(byBEmployee -> {
            this.searchInfo(byBEmployee);
        });
        return page;
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
    public void upperSave(ByBEmployee byBEmployee) {
        String username = byBEmployee.getUsername();
        String pinyin = PinyinUtil.getFirstLetter(username);
        byBEmployee.setPinyin(pinyin);
        this.save(byBEmployee);
    }

    @Override
    public ByBEmployee searchDetails(Integer id) {
        ByBEmployee byBEmployee = this.get(id);
        this.searchInfo(byBEmployee);
        return byBEmployee;
    }

    /**
     * 查询详情,注入其他依赖信息
     *
     * @param byBEmployee
     */
    private void searchInfo(ByBEmployee byBEmployee) {
        //注入部门信息
        String departmentCode = byBEmployee.getDepartmentCode();
        if (StringHandler.isNotEmptyOrNull(departmentCode)) {
            ByBDepartment byBDepartment = byBDepartmentMapper.getByCode(byBEmployee.getDepartmentCode());
            byBEmployee.setDepartmentName(byBDepartment.getDepartmentName());
        } else {
            byBEmployee.setDepartmentName("");
        }
        //注入字典数据学历信息
        Integer educationTypeId = byBEmployee.getEducationTypeId();
        if (educationTypeId != null) {
            String educationTypeName = byBDictionaryChildManager.get(educationTypeId).getAlias();
            byBEmployee.setEducationTypeName(educationTypeName);
        } else {
            byBEmployee.setEducationTypeName("");
        }
        //注入字典数据职称信息
        Integer positionalTitleTypeId = byBEmployee.getPositionalTitleTypeId();
        if (positionalTitleTypeId != null) {
            String positionalTitleTypeName = byBDictionaryChildManager.get(positionalTitleTypeId).getAlias();
            byBEmployee.setPositionalTitleTypeName(positionalTitleTypeName);
        } else {
            byBEmployee.setPositionalTitleTypeName("");
        }
        //注入字典信息职位信息
        Integer positionTypeId = byBEmployee.getPositionTypeId();
        if (positionTypeId != null) {
            String positionTypeName = byBDictionaryChildManager.get(positionTypeId).getAlias();
            byBEmployee.setPositionTypeName(positionTypeName);
        } else {
            byBEmployee.setPositionTypeName("");
        }
    }

    @Transactional
    @Override
    public void updateByIdForPositionState(Integer id, String positionState) {
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
        String leaveDate = formatter2.format(new Date());
        byBEmployeeMapper.updatePositionById(positionState, leaveDate, id);
    }

    @Transactional
    @Override
    public void updateByIdForIcon(Integer id, String icon) {
        Date modifyTime = new Date();
        byBEmployeeMapper.updateIconById(icon, modifyTime, id);
    }

    @Override
    public List<ByBEmployee> searchForUsername(String departmentCode) {
        String positionState = "在职";
        List<ByBEmployee> byBEmployeeList = byBEmployeeMapper.searchUsername(positionState, departmentCode);
        return byBEmployeeList;
    }

    @Override
    public List<ByBEmployee> searchForLeader() {
        String positionState = "在职";
        String isLeader = "是";
        List<ByBEmployee> byBEmployeeList = byBEmployeeMapper.searchUsernameByIsLeader(positionState, isLeader);
        return byBEmployeeList;
    }

    @Override
    public ByBEmployee searchDepartmentLeader(String departmentCode, String positionState, String isLeader) {
        //获取用户id
        ByBEmployee byBEmployee = byBEmployeeMapper.searchIdByDepartmentCodeInfo(departmentCode, positionState, isLeader);
        return byBEmployee;
    }

    /**
     * 根据code 查询单个小组负责人名称
     *
     * @param code
     * @return
     */
    @Override
    public String searchByCode(String code) {
        String positionState = "在职";
        String isGroupLeader = "是";
        String username = byBEmployeeMapper.searchGroupUsername(positionState, isGroupLeader, code);
        return username;
    }

    @Override
    public ByBEmployee searchIdAndNameByCode(String code) {
        String positionState = "在职";
        String isGroupLeader = "是";
        ByBEmployee byBEmployee = byBEmployeeMapper.searchGroupUsernameAndId(positionState, isGroupLeader, code);
        return byBEmployee;
    }

    /**
     * 根据id 获取姓名和部门名称
     *
     * @param messageCreateId
     * @return
     */
    @Override
    public ByBEmployee searchUsernameAndDepartmentNameById(Integer messageCreateId) {
        ByBEmployee byBEmployee = byBEmployeeMapper.searchUsernameAndDepartmentCodeById(messageCreateId);
        String departmentCode = byBEmployee.getDepartmentCode();
        String departmentName = byBDepartmentMapper.searchNameByCode(departmentCode);
        byBEmployee.setDepartmentName(departmentName);
        return byBEmployee;
    }

    /**
     * 查询不带分页
     *
     * @param username
     * @param telephone
     * @param sex
     * @param pinyin
     * @param positionState
     * @param departmentCode
     * @param isLeader
     * @return
     */
    @Override
    public List<ByBEmployee> searchWithoutPage(String username, String telephone, String sex, String pinyin, String positionState, String departmentCode, String isLeader) {
        FlipFilter flipFilter = new FlipFilter(ByBEmployee.class);
        flipFilter.addSearch("%" + username + "%", Operate.LIKE, "username");
        flipFilter.addSearch("%" + telephone + "%", Operate.LIKE, "telephone");
        flipFilter.addSearch(sex, Operate.EQUAL, "sex");
        flipFilter.addSearch("%" + pinyin + "%", Operate.LIKE, "pinyin");
        flipFilter.addSearch(positionState, Operate.EQUAL, "positionState");
        flipFilter.addSearch(departmentCode + "%", Operate.LIKE, "departmentCode");
        flipFilter.addSearch(isLeader, Operate.EQUAL, "isLeader");
        List<ByBEmployee> byBEmployeeList = this.list(flipFilter);
        byBEmployeeList.forEach(byBEmployee -> {
            this.searchInfo(byBEmployee);
        });
        return byBEmployeeList;
    }

}