package club.emergency.by_b_employee.manager;

import club.emergency.by_b_employee.model.ByBEmployee;
import club.map.core.manager.GenericManager;
import club.map.core.model.Page;

import java.util.List;

/**
 * 员工管理接口
 */
public interface ByBEmployeeManager extends GenericManager<ByBEmployee, Integer> {
    /**
     * 分页查询
     *
     * @param username
     * @param telephone
     * @param sex
     * @param pinyin
     * @param positionState
     * @param departmentCode
     * @param isLeader
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page search(String username, String telephone, String sex, String pinyin, String positionState, String departmentCode, String isLeader, Integer pageNo, Integer pageSize);

    /**
     * 删除
     *
     * @param ids
     */
    void removeByIds(String ids);

    /**
     * 编辑
     *
     * @param byBEmployee
     */
    void upperSave(ByBEmployee byBEmployee);

    /**
     * 查询单个详情
     *
     * @param id
     * @return
     */
    ByBEmployee searchDetails(Integer id);

    /**
     * 修改在职状态
     *
     * @param id
     * @param positionState
     */
    void updateByIdForPositionState(Integer id, String positionState);

    /**
     * 修改图标
     *
     * @param id
     * @param icon
     */
    void updateByIdForIcon(Integer id, String icon);

    /**
     * 根据部门查询无分页
     *
     * @param departmentCode
     * @return
     */
    List<ByBEmployee> searchForUsername(String departmentCode);

    /**
     * 查询部门领导
     *
     * @return
     */
    List<ByBEmployee> searchForLeader();

    /**
     * 查询具体的部门负责人
     *
     * @param departmentCode
     * @param positionState
     * @param isLeader
     * @return
     */
    ByBEmployee searchDepartmentLeader(String departmentCode, String positionState, String isLeader);

    /**
     * 查询员工姓名(暂未被使用)
     *
     * @param code
     * @return
     */
    String searchByCode(String code);

    /**
     * 查询小组负责人
     *
     * @param code
     * @return
     */
    ByBEmployee searchIdAndNameByCode(String code);

    /**
     * 根据id 获取姓名和部门名称
     *
     * @param messageCreateId
     * @return
     */
    ByBEmployee searchUsernameAndDepartmentNameById(Integer messageCreateId);

    /**
     * 多条件查询全部员工
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
    List<ByBEmployee> searchWithoutPage(String username, String telephone, String sex, String pinyin, String positionState, String departmentCode, String isLeader);
}