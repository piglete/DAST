package club.emergency.project.manager;

import club.emergency.project.model.ByBStoreRecord;
import club.map.core.manager.GenericManager;
import club.map.core.model.Page;

import javax.servlet.http.HttpServletResponse;

/**
 * 档案入库管理接口
 */
public interface ByBStoreRecordManager extends GenericManager<ByBStoreRecord, Integer> {

    /**
     * 入库编辑(新增和修改,通过id判断)
     *
     * @param byBStoreRecord
     */
    void upperSave(ByBStoreRecord byBStoreRecord);

    /**
     * 档案入库信息详细
     *
     * @param id
     * @return
     */
    ByBStoreRecord searchDetails(Integer id);

    /**
     * 批量删除
     *
     * @param ids
     */
    void removeByIds(String ids);

    /**
     * 多条件模糊分页查询
     *
     * @param storeroomCode
     * @param fileNumber
     * @param recordTypeName
     * @param departmentName
     * @param recordState
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page search(String storeroomCode, String fileNumber, String recordTypeName, String departmentName, String recordState, Integer pageNo, Integer pageSize);

    /**
     * 入库信息导出
     *
     * @param fileName
     * @param response
     */
    void export(String fileName, HttpServletResponse response);
}