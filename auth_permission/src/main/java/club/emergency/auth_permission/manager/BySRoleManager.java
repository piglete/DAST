package club.emergency.auth_permission.manager;

import club.emergency.auth_permission.model.BySRole;
import club.map.core.manager.GenericManager;
import club.map.core.model.Page;

import java.util.List;

/**
 * 角色管理接口
 */
public interface BySRoleManager extends GenericManager<BySRole, Integer> {
    /**
     * 分页查询
     *
     * @param roleName
     * @param roleType
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page search(String roleName, Integer roleType, Integer pageNo, Integer pageSize);

    /**
     * 编辑
     *
     * @param bySRole
     */
    void upperSave(BySRole bySRole);

    /**
     * 查询全部
     *
     * @param roleName
     * @return
     */
    List<BySRole> searchWithOutPage(String roleName);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    Boolean removeForRole(Integer id);

    /**
     * 添加角色菜单关联关系
     *
     * @param id
     * @param menuIds
     */
    void upperSaveMenu(Integer id, String menuIds);
}