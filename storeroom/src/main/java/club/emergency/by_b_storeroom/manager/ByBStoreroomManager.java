package club.emergency.by_b_storeroom.manager;

import club.emergency.by_b_storeroom.model.ByBStoreroom;
import club.map.core.manager.GenericManager;

import java.util.List;

/**
 * 库房管理接口
 */
public interface ByBStoreroomManager extends GenericManager<ByBStoreroom, Integer> {

    /**
     * 库房编辑
     *
     * @param byBStoreroom
     */
    void upperSave(ByBStoreroom byBStoreroom);

    /**
     * 库房详情
     *
     * @param id
     * @return
     */
    ByBStoreroom searchDetails(Integer id);

    /**
     * 库房删除
     *
     * @param ids
     */
    void removeByIds(String ids);

    /**
     * 根据库房code查询详细信息
     *
     * @param code
     * @return
     */
    ByBStoreroom searchByCode(String code);

    /**
     * 查询全部,无分页
     *
     * @return
     */
    List<ByBStoreroom> search();

    /**
     * 库房树查询(库房表是自关联设计)
     *
     * @return
     */
    List<ByBStoreroom> treeSearch();
}