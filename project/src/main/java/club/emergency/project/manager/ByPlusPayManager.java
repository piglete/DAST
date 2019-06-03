package club.emergency.project.manager;

import club.emergency.project.model.ByPlusPay;
import club.map.core.manager.GenericManager;

import java.util.List;

/**
 * 项目交付管理
 */
public interface ByPlusPayManager extends GenericManager<ByPlusPay, Integer> {
    /**
     * 查询全部
     *
     * @param projectId
     * @return
     */
    List<ByPlusPay> search(Integer projectId);

    /**
     * 编辑方法(新增和修改)
     *
     * @param byPlusPay
     */
    void upperSave(ByPlusPay byPlusPay);

    /**
     * 删除
     *
     * @param ids
     */
    void removeByIds(String ids);

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    ByPlusPay searchById(Integer id);
}