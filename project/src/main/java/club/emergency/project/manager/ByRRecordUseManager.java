package club.emergency.project.manager;

import club.emergency.project.model.ByRRecordUse;
import club.map.core.manager.GenericManager;
import club.map.core.model.Page;

import javax.servlet.http.HttpServletResponse;

/**
 * 档案借出管理接口
 */
public interface ByRRecordUseManager extends GenericManager<ByRRecordUse, Integer> {
    /**
     * 分页查询方法
     *
     * @param recordTypeName
     * @param purpose
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page search(String recordTypeName, String purpose, Integer pageNo, Integer pageSize);

    /**
     * 新增方法
     *
     * @param byRRecordUse
     */
    void upperSave(ByRRecordUse byRRecordUse);

    /**
     * 批量删除方法
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