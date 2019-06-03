package club.emergency.project.manager;

import club.emergency.project.model.ByRRecordRevert;
import club.map.core.manager.GenericManager;
import club.map.core.model.Page;

import javax.servlet.http.HttpServletResponse;

/**
 * 档案归还记录管理接口
 */
public interface ByRRecordRevertManager extends GenericManager<ByRRecordRevert, Integer> {

    /**
     * 分页查询方法
     *
     * @param recordTypeName
     * @param revertPerson
     * @param revertState
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page search(String recordTypeName, String revertPerson, String revertState, Integer pageNo, Integer pageSize);

    /**
     * 编辑方法(新增和修改)
     *
     * @param byRRecordRevert
     */
    void upperSave(ByRRecordRevert byRRecordRevert);

    /**
     * 批量删除
     *
     * @param ids
     */
    void removeByIds(String ids);

    /**
     * 导出excel
     *
     * @param fileName
     * @param response
     */
    void export(String fileName, HttpServletResponse response);
}