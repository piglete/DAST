package club.emergency.by_b_region.manager;

import club.emergency.by_b_region.model.ByBRegion;
import club.map.core.manager.GenericManager;

import java.util.List;
import java.util.Map;

/**
 * 区域管理接口
 */
public interface ByBRegionManager extends GenericManager<ByBRegion, Integer> {
    /**
     * 区域树状结构查询
     *
     * @return
     */
    List treeSearch();

    /**
     * 区域编辑(新增和修改)
     *
     * @param byBRegion
     */
    void upperSave(ByBRegion byBRegion);

    /**
     * 区域批量删除
     *
     * @param ids
     */
    void removeByIds(String ids);

    /**
     * 区域查询不分页
     *
     * @return
     */
    List<ByBRegion> search();

    /**
     * 查询单个详情
     *
     * @param id
     * @return
     */
    ByBRegion searchDetails(Integer id);

    /**
     * 根据区域code查询某个区域信息
     *
     * @param code
     * @return
     */
    ByBRegion searchByCode(String code);

    /**
     * 区域树状结构查询
     *
     * @return
     */
    List<ByBRegion> treeList();

    /**
     * 根据区域父节点查询所有子节点信息(查询统计需要该接口)
     *
     * @param parentId
     * @return
     */
    Map<String, String> searchRegion(Integer parentId);

    /**
     * 查询该code下所有子节点
     *
     * @param code
     * @return
     */
    List<ByBRegion> searchChildByCode(String code);
}