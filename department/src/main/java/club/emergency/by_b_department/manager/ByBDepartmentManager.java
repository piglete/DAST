package club.emergency.by_b_department.manager;

import club.emergency.by_b_department.model.ByBDepartment;
import club.map.core.manager.GenericManager;

import java.util.List;

/**
 * 部门管理接口
 */
public interface ByBDepartmentManager extends GenericManager<ByBDepartment, Integer> {

    /**
     * 编辑
     *
     * @param byBDepartment
     */
    void upperSave(ByBDepartment byBDepartment);

    /**
     * 删除
     *
     * @param ids
     */
    void removeByIds(String ids);

    /**
     * 部门树
     *
     * @return
     */
    List treeSearch();

    /**
     * 部门查询全部
     *
     * @return
     */
    List<ByBDepartment> search();

    /**
     * 查询子部门
     *
     * @param invalidFlag
     * @return
     */
    List<ByBDepartment> searchForChild(Integer invalidFlag);

    /**
     * 查询父节点部门
     *
     * @param releaseFlag
     * @param departmentCode
     * @param invalidFlag
     * @return
     */
    List<ByBDepartment> searchForParent(Integer releaseFlag, String departmentCode, Integer invalidFlag);

    /**
     * 查询该code下的子部门
     *
     * @param code
     * @param invalidFlag
     * @return
     */
    List<ByBDepartment> searchForGroup(String code, Integer invalidFlag);

    /**
     * 查询所有小组
     *
     * @param releaseFlag
     * @param invalidFlag
     * @return
     */
    List<ByBDepartment> searchForAllGroup(Integer releaseFlag, Integer invalidFlag);

    /**
     * 部门作废处理
     *
     * @param id
     */
    void updateDepartmentInvalid(Integer id);
}