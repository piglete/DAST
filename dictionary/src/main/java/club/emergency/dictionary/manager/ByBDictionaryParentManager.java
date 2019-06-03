package club.emergency.dictionary.manager;

import club.emergency.dictionary.model.ByBDictionaryParent;
import club.map.core.manager.GenericManager;

import java.util.List;

/**
 * 父字典管理接口
 */
public interface ByBDictionaryParentManager extends GenericManager<ByBDictionaryParent, Integer> {

    /**
     * 父字典树
     *
     * @return
     */
    List treeDataForParent();

    /**
     * 编辑
     *
     * @param byBDictionaryParent
     */
    void upperSave(ByBDictionaryParent byBDictionaryParent);

    /**
     * 删除(删除父节点时一并删除其下所有子节点)
     *
     * @param id
     */
    void removeById(Integer id);
}