package club.emergency.project.manager;

import club.emergency.project.model.ByPlusRecordModel;
import club.map.core.manager.GenericManager;

import java.util.List;

/**
 * 档案资料模板管理
 */
public interface ByPlusRecordModelManager extends GenericManager<ByPlusRecordModel, Integer> {
    /**
     * 查询全部
     *
     * @param recordTypeId
     * @return
     */
    List<ByPlusRecordModel> search(Integer recordTypeId);

    /**
     * 编辑方法(包含新增和修改)
     *
     * @param byPlusRecordModel
     */
    void upperSave(ByPlusRecordModel byPlusRecordModel);

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    ByPlusRecordModel searchDetail(Integer id);

    /**
     * 删除
     *
     * @param ids
     */
    void removeByIds(String ids);
}