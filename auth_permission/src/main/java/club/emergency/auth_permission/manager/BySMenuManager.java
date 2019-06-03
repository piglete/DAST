package club.emergency.auth_permission.manager;

import club.emergency.auth_permission.model.BySMenu;
import club.map.core.manager.GenericManager;
import club.map.core.model.Page;

import java.util.List;

/**
 * 菜单管理接口
 */
public interface BySMenuManager extends GenericManager<BySMenu, Integer> {
    /**
     * 获取菜单树
     *
     * @return
     */
    List treeData();

    /**
     * 编辑方法
     *
     * @param bySMenu
     */
    void upperSave(BySMenu bySMenu);

    /**
     * 分页查询
     *
     * @param menuName
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page search(String menuName, Integer pageNo, Integer pageSize);

    /**
     * 删除
     *
     * @param ids
     */
    void removeByIds(String ids);

    /**
     * 查询所有菜单
     *
     * @return
     */
    List<BySMenu> searchAllMenu();

    /**
     * 查询该角色对应菜单集合
     *
     * @param id
     * @return
     */
    List<BySMenu> detailForRoleMenu(Integer id);
}