package club.emergency.auth_permission.manager.impl;

import club.emergency.auth_permission.manager.BySMenuManager;
import club.emergency.auth_permission.manager.BySRoleMenuManager;
import club.emergency.auth_permission.mapper.BySMenuMapper;
import club.emergency.auth_permission.mapper.BySRoleMenuMapper;
import club.emergency.auth_permission.model.BySMenu;
import club.emergency.auth_permission.model.BySRoleMenu;
import club.emergency.auth_permission.model.MenuTreeUtil;
import club.map.base.util.AppTreeUtil;
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

@Service
public class BySMenuManagerImpl extends GenericManagerImpl<BySMenu, Integer> implements BySMenuManager {

    private BySMenuMapper bySMenuMapper;
    private BySRoleMenuManager bySRoleMenuManager;

    @Autowired
    public BySMenuManagerImpl(BySMenuMapper mapper,
                              BySMenuMapper bySMenuMapper,
                              BySRoleMenuManager bySRoleMenuManager
                              ) {
        super(mapper, BySMenu.class);
        this.bySMenuMapper = bySMenuMapper;
        this.bySRoleMenuManager=bySRoleMenuManager;
    }

    @Override
    public List treeData() {
        FlipFilter flipFilter = new FlipFilter(BySMenu.class);
        List list = this.list(flipFilter);
        return MenuTreeUtil.getTreeData(list);
    }

    @Transactional
    @Override
    public void upperSave(BySMenu bySMenu) {
        if(bySMenu.getId() == null){
            Integer parentId=bySMenu.getParentId();
            if(parentId == 0){
                bySMenu.setSortNum(1);
            }else{
                Integer count=bySMenuMapper.searchByParentId(parentId);
                if(count == 0){
                    bySMenu.setSortNum(1);
                }else{
                    bySMenu.setSortNum(++count);
                }
            }
        }
        this.save(bySMenu);
    }

    @Override
    public Page search(String menuName, Integer pageNo, Integer pageSize) {
        FlipFilter flipFilter = new FlipFilter(BySMenu.class);
        flipFilter.addSearch("%" + menuName + "%", Operate.LIKE, "menuName");
        flipFilter.updatePageNo(pageNo);
        flipFilter.setPageSize(pageSize);
        return this.flipUsingInPage(flipFilter);
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
    public List<BySMenu> searchAllMenu() {
        List<BySMenu> bySMenuList=bySMenuMapper.searchAll();
        return bySMenuList;
    }

    @Override
    public List<BySMenu> detailForRoleMenu(Integer id) {
        //查询角色和菜单关联的中间表,获取角色对应的菜单id
        FlipFilter flipFilter = new FlipFilter(BySRoleMenu.class);
        flipFilter.addSearch(id, Operate.EQUAL, "roleId");
        List<BySRoleMenu> bySRoleMenuList = bySRoleMenuManager.list(flipFilter);
        List<Integer> menuIdList = new ArrayList<>();
        for (BySRoleMenu bySRoleMenu : bySRoleMenuList) {
            menuIdList.add(bySRoleMenu.getMenuId());
        }
        //根据菜单id查询菜单详情
        List<BySMenu> bySMenuList=new ArrayList<>();
        FlipFilter flipFilterMenu = new FlipFilter(BySMenu.class);
        if (menuIdList.size() != 0) {
            flipFilterMenu.addSearch(menuIdList, Operate.IN, "id");
            bySMenuList = this.list(flipFilterMenu);
        }
        return bySMenuList;
    }
}