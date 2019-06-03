package club.emergency.project.manager;

import club.emergency.project.model.ByPlusRecordComparison;
import club.map.core.manager.GenericManager;

import java.util.List;

/**
 * 项目归档审核记录管理
 */
public interface ByPlusRecordComparisonManager extends GenericManager<ByPlusRecordComparison, Integer> {
    /**
     * 查询全部
     *
     * @param projectId
     * @return
     */
    List<ByPlusRecordComparison> search(Integer projectId);

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    ByPlusRecordComparison searchDetail(Integer id);

    /**
     * 删除
     *
     * @param ids
     */
    void removeByIds(String ids);

    /**
     * 编辑
     *
     * @param byPlusRecordComparison
     */
    void upperSave(ByPlusRecordComparison byPlusRecordComparison);

    /**
     * 条件修改
     *
     * @param ids
     */
    void updateExistFlagByIds(String ids);

    /**
     * 资料提交处理(记录该项目下哪类资料已提交,哪类资料未提交)
     *
     * @param byPlusRecordComparison
     * @param trueRecordIds
     * @param falseRecordIds
     */
    void upperMoreSave(ByPlusRecordComparison byPlusRecordComparison, String trueRecordIds, String falseRecordIds);
}