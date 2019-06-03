package club.emergency.project.manager;

import club.emergency.project.model.ByRRecord;
import club.map.core.manager.GenericManager;
import club.map.core.model.Page;

import java.util.List;

/**
 * 档案管理接口
 */
public interface ByRRecordManager extends GenericManager<ByRRecord, Integer> {

    /**
     * 批量删除方法
     *
     * @param ids
     */
    void removeByIds(String ids);

    /**
     * 编辑(新增或者修改)方法
     *
     * @param byRRecord
     */
    void upperSave(ByRRecord byRRecord);

    /**
     * 查询详情方法
     *
     * @param id
     * @return
     */
    ByRRecord searchDetails(Integer id);

    /**
     * 按条件查询所有
     *
     * @param isStoreroom
     * @return
     */
    List<ByRRecord> searchWithoutPage(Integer isStoreroom);

    /**
     * 分页查询
     *
     * @param isStoreroom
     * @param fileNumber
     * @param companyName
     * @param projectName
     * @param recordTypeId
     * @param fileDate
     * @param departmentCode
     * @param retentionPeriodId
     * @param regionCode
     * @param yearType
     * @param monthType
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page search(Integer isStoreroom, String fileNumber, String companyName, String projectName, Integer recordTypeId, String fileDate, String departmentCode, Integer retentionPeriodId, String regionCode, Integer yearType, Integer monthType, Integer pageNo, Integer pageSize);

}