package club.emergency.by_b_return_reason.manager;

import club.emergency.by_b_return_reason.model.ByBReturnReason;
import club.map.core.manager.GenericManager;

import java.util.List;

/**
 * 回退原因管理接口
 */
public interface ByBReturnReasonManager extends GenericManager<ByBReturnReason, Integer> {
    /**
     * 查询全部
     *
     * @return
     */
    List<ByBReturnReason> searchWithRelation();

    /**
     * 编辑
     *
     * @param byBReturnReason
     */
    void upperSave(ByBReturnReason byBReturnReason);

    /**
     * 查询单个详情
     *
     * @param id
     * @return
     */
    ByBReturnReason searchDetails(Integer id);

    /**
     * 删除
     *
     * @param ids
     */
    void removeByIds(String ids);

    /**
     * 根据父id查询
     *
     * @param parentId
     * @return
     */
    List<ByBReturnReason> search(Integer parentId);
}