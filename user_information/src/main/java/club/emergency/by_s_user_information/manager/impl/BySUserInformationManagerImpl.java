package club.emergency.by_s_user_information.manager.impl;

import club.emergency.auth_permission.manager.BySMenuManager;
import club.emergency.auth_permission.manager.BySRoleManager;
import club.emergency.auth_permission.manager.BySRoleMenuManager;
import club.emergency.auth_permission.model.BySMenu;
import club.emergency.auth_permission.model.BySRole;
import club.emergency.auth_permission.model.BySRoleMenu;
import club.emergency.auth_permission.model.MenuTreeUtil;
import club.emergency.by_b_department.mapper.ByBDepartmentMapper;
import club.emergency.by_b_employee.manager.ByBEmployeeManager;
import club.emergency.by_b_employee.mapper.ByBEmployeeMapper;
import club.emergency.by_b_employee.model.ByBEmployee;
import club.emergency.by_s_user_information.manager.BySUserInformationManager;
import club.emergency.by_s_user_information.mapper.BySUserInformationMapper;
import club.emergency.by_s_user_information.model.BySUserInformation;
import club.map.core.manager.impl.GenericManagerImpl;
import club.map.core.model.FlipFilter;
import club.map.core.model.Operate;
import club.map.core.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class BySUserInformationManagerImpl extends GenericManagerImpl<BySUserInformation, Integer> implements BySUserInformationManager {

    private BySRoleManager bySRoleManager;
    private BySRoleMenuManager bySRoleMenuManager;
    private BySMenuManager bySMenuManager;
    private BySUserInformationMapper bySUserInformationMapper;
    private ByBEmployeeManager byBEmployeeManager;
    private ByBEmployeeMapper byBEmployeeMapper;
    private ByBDepartmentMapper byBDepartmentMapper;

    @Autowired
    public BySUserInformationManagerImpl(BySUserInformationMapper mapper,
                                         BySRoleManager bySRoleManager,
                                         BySRoleMenuManager bySRoleMenuManager,
                                         BySMenuManager bySMenuManager,
                                         BySUserInformationMapper bySUserInformationMapper,
                                         ByBEmployeeManager byBEmployeeManager,
                                         ByBEmployeeMapper byBEmployeeMapper,
                                         ByBDepartmentMapper byBDepartmentMapper
    ) {
        super(mapper, BySUserInformation.class);
        this.bySRoleManager = bySRoleManager;
        this.bySRoleMenuManager = bySRoleMenuManager;
        this.bySMenuManager = bySMenuManager;
        this.bySUserInformationMapper = bySUserInformationMapper;
        this.byBEmployeeManager = byBEmployeeManager;
        this.byBEmployeeMapper = byBEmployeeMapper;
        this.byBDepartmentMapper = byBDepartmentMapper;
    }

    /**
     * 用户类型分为是企业员工,和不是企业员工;如果为企业员工的话,该员工有对应部门,如果非企业员工,则没有员工信息(没有部门)
     * 查询时,判断是否按部门查,分为下面两种条件处理
     *
     * @param loginName
     * @param departmentCode
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Page search(String loginName, String departmentCode, Integer pageNo, Integer pageSize) {
        FlipFilter flipFilter = new FlipFilter(BySUserInformation.class);
        flipFilter.addSearch("%" + loginName + "%", Operate.LIKE, "loginName");
        Page page = null;
        if (departmentCode.isEmpty() || "".equals(departmentCode)) {
            //查询时不按部门条件查询时,可以直接查询
            flipFilter.updatePageNo(pageNo);
            flipFilter.setPageSize(pageSize);
            page = this.flipUsingInPage(flipFilter);
            List<BySUserInformation> bySUserInformationList = page.getListInfo();
            for (BySUserInformation bySUserInformation : bySUserInformationList) {
                this.getRoleInfo(bySUserInformation);
                this.getEmployeeInfo(bySUserInformation);
            }
        } else {
            //按部门条件查询时,先查出所有企业员工,再按部门筛选
            List<BySUserInformation> bySUserInformationList = this.list(flipFilter);
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < bySUserInformationList.size(); i++) {
                Integer employeeId = bySUserInformationList.get(i).getEmployeeId();
                if (employeeId != null) {
                    list.add(employeeId);
                }
            }
            FlipFilter flipFilter1 = new FlipFilter(ByBEmployee.class);
            flipFilter1.addSearch(departmentCode + "%", Operate.LIKE, "departmentCode");
            flipFilter1.addSearch(list, Operate.IN, "id");
            List<ByBEmployee> byBEmployeeList = byBEmployeeManager.list(flipFilter1);
            list.clear();
            for (int i = 0; i < byBEmployeeList.size(); i++) {
                list.add(byBEmployeeList.get(i).getId());
            }
            if (list.size() > 0) {
                FlipFilter flipFilter2 = new FlipFilter(BySUserInformation.class);
                flipFilter2.addSearch(list, Operate.IN, "employeeId");
                flipFilter2.updatePageNo(pageNo);
                flipFilter2.setPageSize(pageSize);
                page = this.flipUsingInPage(flipFilter2);
                List<BySUserInformation> byList = page.getListInfo();
                for (BySUserInformation bySUserInformation : byList) {
                    this.getRoleInfo(bySUserInformation);
                    this.getEmployeeInfo(bySUserInformation);
                }
            }
        }
        return page;
    }

    /**
     * 用户新增时需判断用户名唯一性
     *
     * @param bySUserInformation
     * @return
     */
    @Transactional
    @Override
    public int upperSave(BySUserInformation bySUserInformation) {
        int a = 0;
        if (bySUserInformation.getId() == null) {
            String loginName = bySUserInformation.getLoginName();
            //判断该用户名是否已存在
            Boolean flag = this.searchLoginNameExist(loginName);
            if (flag == true) {
                a = 1;
                return a;
            }
        }
        this.save(bySUserInformation);
        return a;
    }

    /**
     * 查询用户绑定员工是否重复
     *
     * @param employeeId
     * @return
     */
    private Boolean searchEmployeeIdSelected(Integer employeeId) {
        Boolean flag = false;
        FlipFilter flipFilter = new FlipFilter(BySUserInformation.class);
        flipFilter.addSearch(employeeId, Operate.EQUAL, "employeeId");
        List<BySUserInformation> bySUserInformationList = this.list(flipFilter);
        if (bySUserInformationList.size() > 0) {
            flag = true;
        }
        return flag;
    }

    /**
     * 用户名唯一性查询
     *
     * @param loginName
     * @return
     */
    private Boolean searchLoginNameExist(String loginName) {
        Boolean flag = false;
        FlipFilter flipFilter = new FlipFilter(BySUserInformation.class);
        flipFilter.addSearch(loginName, Operate.EQUAL, "loginName");
        List<BySUserInformation> bySUserInformationList = this.list(flipFilter);
        if (bySUserInformationList.size() > 0) {
            flag = true;
        }
        return flag;
    }

    /**
     * 删除用户,一般不允许删除,因为被其他模块依赖,会导致其他模块信息查询时错误
     *
     * @param ids
     */
    @Transactional
    @Override
    public void removeByIds(String ids) {
        String[] idArr = ids.split(",");
        Arrays.asList(idArr).forEach(id -> {
            this.remove(Integer.valueOf(id));
        });
    }

    /**
     * 用户登陆成功不需要返回密码
     *
     * @param loginName
     * @param password
     * @return
     */
    @Override
    public BySUserInformation login(String loginName, String password) {
        BySUserInformation bySUserInformation = this.simpleSearch(loginName, password);
        if (Objects.nonNull(bySUserInformation)) {
            //返回时密码不需要返回
            bySUserInformation.setPassword("");
            //添加员工姓名,部门code,微信图标
            Integer employeeId = bySUserInformation.getEmployeeId();
            if (employeeId != null) {
                ByBEmployee byBEmployee = byBEmployeeManager.get(employeeId);
                String employeeName = byBEmployee.getUsername();
                bySUserInformation.setEmployeeName(employeeName);
                String departmentCode = byBEmployee.getDepartmentCode();
                bySUserInformation.setDepartmentCode(departmentCode);
                String icon = byBEmployee.getIcon();
                bySUserInformation.setIcon(icon);
            }
            //获取用户角色,权限
            bySUserInformation = userInformationRoleDesc(bySUserInformation);
            return bySUserInformation;
        } else {
            return null;
        }
    }

    @Override
    public BySUserInformation userInformationRoleDesc(BySUserInformation bySUserInformation) {
        bySUserInformation = this.getRoleInfo(bySUserInformation);
        Integer roleId = bySUserInformation.getRoleId();
        FlipFilter flipFilter = new FlipFilter(BySRoleMenu.class);
        flipFilter.addSearch(roleId, Operate.EQUAL, "roleId");
        List<BySRoleMenu> bySRoleMenuList = bySRoleMenuManager.list(flipFilter);
        List<Integer> list = new ArrayList<>();
        for (BySRoleMenu bySRoleMenu : bySRoleMenuList) {
            list.add(bySRoleMenu.getMenuId());
        }
        FlipFilter flipFilterMenu = new FlipFilter(BySMenu.class);
        flipFilterMenu.addSearch(list, Operate.IN, "id");
        flipFilterMenu.setSortField("sortNum");
        List bySMenuList = bySMenuManager.list(flipFilterMenu);
        List<BySMenu> byMenuTree = MenuTreeUtil.getTreeData(bySMenuList);
        bySUserInformation.setBySMenuList(byMenuTree);
        return bySUserInformation;
    }

    @Transactional
    @Override
    public Boolean updateForRole(Integer id, Integer roleId) {
        BySUserInformation bySUserInformation = bySUserInformationMapper.updateRoleById(roleId, id);
        if (Objects.nonNull(bySUserInformation)) {
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    @Override
    public Boolean updatePassword(String loginName, String oldPassword, String newPassword) {
        BySUserInformation bySUserInformation = simpleSearch(loginName, oldPassword);
        if (Objects.nonNull(bySUserInformation)) {
            bySUserInformationMapper.updatePasswordById(newPassword, bySUserInformation.getId());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public BySUserInformation searchDetails(Integer id) {
        BySUserInformation bySUserInformation = this.get(id);
        this.getRoleInfo(bySUserInformation);
        this.getEmployeeInfo(bySUserInformation);
        return bySUserInformation;
    }

    @Override
    public BySUserInformation simpleSearch(String loginName, String password) {
        FlipFilter flipFilter = new FlipFilter(BySUserInformation.class);
        flipFilter.addSearch(loginName, Operate.EQUAL, "loginName");
        flipFilter.addSearch(password, Operate.EQUAL, "password");
        return this.get(flipFilter);
    }

    /**
     * 查询用户对应角色信息
     *
     * @param bySUserInformation
     * @return
     */
    private BySUserInformation getRoleInfo(BySUserInformation bySUserInformation) {
        //获取角色名称
        Integer roleId = bySUserInformation.getRoleId();
        if (roleId != null) {
            try {
                BySRole bySRole = bySRoleManager.get(roleId);
                bySUserInformation.setRoleName(bySRole.getRoleName());
                bySUserInformation.setRoleType(bySRole.getRoleType());
            } catch (Exception e) {
                bySUserInformation.setRoleName(null);
            }
        }
        return bySUserInformation;
    }

    /**
     * 获取员工详情(因为之前员工有图片字段,以text字段保存,所以为了用户查询速度快,不加载图片,
     * 只返回部分重要字段,所以用单独sql处理)
     *
     * @param bySUserInformation
     * @return
     */
    private BySUserInformation getEmployeeInfo(BySUserInformation bySUserInformation) {
        //获取员工信息
        Integer employeeId = bySUserInformation.getEmployeeId();
        if (employeeId != null) {
            try {
//                ByBEmployee byBEmployee = byBEmployeeManager.get(employeeId);
                //由于此处查询只需要获取员工信息的个别字段值,不需要查询全部字段信息
                ByBEmployee byBEmployee = byBEmployeeMapper.searchInfoById(employeeId);
                String departmentName = byBDepartmentMapper.searchNameByCode(byBEmployee.getDepartmentCode());
                byBEmployee.setDepartmentName(departmentName);
                bySUserInformation.setByBEmployee(byBEmployee);
            } catch (Exception e) {
                bySUserInformation.setRoleName(null);
            }
        }
        return bySUserInformation;
    }

}