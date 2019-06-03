package club.emergency.auth_permission.manager.impl;

import club.emergency.auth_permission.manager.BySMenuManager;
import club.emergency.auth_permission.manager.BySRoleManager;
import club.emergency.auth_permission.manager.BySRoleMenuManager;
import club.emergency.auth_permission.mapper.BySRoleMapper;
import club.emergency.auth_permission.mapper.BySRoleMenuMapper;
import club.emergency.auth_permission.model.BySRole;
import club.emergency.auth_permission.model.BySRoleMenu;
import club.map.core.manager.impl.GenericManagerImpl;
import club.map.core.model.FlipFilter;
import club.map.core.model.Operate;
import club.map.core.model.Page;
import com.wanqing.util.StringHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class BySRoleManagerImpl extends GenericManagerImpl<BySRole, Integer> implements BySRoleManager {

    private BySRoleMenuManager bySRoleMenuManager;
    private BySMenuManager bySMenuManager;
    private BySRoleMenuMapper bySRoleMenuMapper;
    private BySRoleMapper bySRoleMapper;

    @Autowired
    public BySRoleManagerImpl(BySRoleMapper mapper,
                              BySRoleMenuManager bySRoleMenuManager,
                              BySMenuManager bySMenuManager,
                              BySRoleMenuMapper bySRoleMenuMapper,
                              BySRoleMapper bySRoleMapper
    ) {
        super(mapper, BySRole.class);
        this.bySRoleMenuManager = bySRoleMenuManager;
        this.bySMenuManager = bySMenuManager;
        this.bySRoleMenuMapper = bySRoleMenuMapper;
        this.bySRoleMapper = bySRoleMapper;
    }

    @Override
    public Page search(String roleName, Integer roleType, Integer pageNo, Integer pageSize) {
        FlipFilter flipFilter = new FlipFilter(BySRole.class);
        flipFilter.addSearch("%" + roleName + "%", Operate.LIKE, "roleName");
        flipFilter.addSearch(roleType, Operate.EQUAL, "roleType");
        flipFilter.updatePageNo(pageNo);
        flipFilter.setPageSize(pageSize);
        return this.flipUsingInPage(flipFilter);
    }

    @Transactional
    @Override
    public void upperSave(BySRole bySRole) {
        this.save(bySRole);
    }

    @Override
    public List<BySRole> searchWithOutPage(String roleName) {
        FlipFilter flipFilter = new FlipFilter(BySRole.class);
        flipFilter.addSearch("%" + roleName + "%", Operate.LIKE, "roleName");
        return this.list(flipFilter);
    }

    /**
     * 删除角色
     * 1.判断是否还有用户在使用该角色,若没有则可以删除该角色;若仍存在,则提示操作用户不能删除该角色,需先解绑该角色
     * 2.若果删除该角色,则删除该角色对应的菜单
     *
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Boolean removeForRole(Integer id) {
        Boolean flag = false;
        Integer roleId = Integer.valueOf(id);
        int count = bySRoleMapper.searchExistUser(roleId);
        if (count > 0) {
            flag = true;
            return flag;
        }
        //删除角色表中该条记录
        this.remove(roleId);
        //删除角色菜单关联表中的信息
        bySRoleMenuMapper.removeByRoleId(roleId);
        return flag;
    }

    /**
     * 角色菜单关联新增
     * @param id
     * @param menuIds
     */
    @Transactional
    @Override
    public void upperSaveMenu(Integer id, String menuIds) {
        //如果id和menuIds均不为空,则说明需要修改该角色菜单权限,先删除该角色所有菜单权限,然后重新添加新菜单权限
        if (id != null && StringHandler.isNotEmptyOrNull(menuIds)) {
            bySRoleMenuMapper.removeByRoleId(id);
        }
        //如果menuId不为空,添加新的菜单权限
        if (StringHandler.isNotEmptyOrNull(menuIds)) {
            BySRoleMenu bySRoleMenu = new BySRoleMenu();
            bySRoleMenu.setRoleId(id);
            String[] idArr = menuIds.split(",");
            Arrays.asList(idArr).forEach(menuId -> {
                bySRoleMenu.setId(null);
                bySRoleMenu.setMenuId(Integer.valueOf(menuId));
                bySRoleMenuManager.upperSave(bySRoleMenu);
            });
        }
    }
}