package club.emergency.by_s_user_information.manager;

import club.emergency.by_s_user_information.model.BySUserInformation;
import club.map.core.manager.GenericManager;
import club.map.core.model.Page;

/**
 * 用户管理接口
 */
public interface BySUserInformationManager extends GenericManager<BySUserInformation, Integer> {
    /**
     * 用户新增
     *
     * @param bySUserInformation
     * @return
     */
    int upperSave(BySUserInformation bySUserInformation);

    /**
     * 用户查询分页方法
     *
     * @param loginName
     * @param departmentCode
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page search(String loginName, String departmentCode, Integer pageNo, Integer pageSize);

    /**
     * 用户批量删除
     *
     * @param ids
     */
    void removeByIds(String ids);

    /**
     * 用户登陆用户名和密码验证
     *
     * @param loginName
     * @param password
     * @return
     */
    BySUserInformation simpleSearch(String loginName, String password);

    /**
     * 用户登陆
     *
     * @param loginName
     * @param password
     * @return
     */
    BySUserInformation login(String loginName, String password);

    /**
     * 用户对应角色和权限(角色对应的菜单)信息
     *
     * @param bySUserInformation
     * @return
     */
    BySUserInformation userInformationRoleDesc(BySUserInformation bySUserInformation);

    /**
     * 修改用户对应角色信息
     *
     * @param id
     * @param roleId
     * @return
     */
    Boolean updateForRole(Integer id, Integer roleId);

    /**
     * 用户密码修改
     *
     * @param loginName
     * @param oldPassword
     * @param newPassword
     * @return
     */
    Boolean updatePassword(String loginName, String oldPassword, String newPassword);

    /**
     * 用户详情
     *
     * @param id
     * @return
     */
    BySUserInformation searchDetails(Integer id);

}