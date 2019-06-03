package club.emergency.dictionary.manager;

import club.emergency.dictionary.model.ByBDictionaryChild;
import club.map.core.manager.GenericManager;
import club.map.core.model.Page;

import java.util.List;


/**
 * 子字典表管理接口
 */
public interface ByBDictionaryChildManager extends GenericManager<ByBDictionaryChild, Integer> {
    /**
     * 根据父表字典code查询子字典对应的值集合
     *
     * @param dictionaryCode
     * @return
     */
    List<ByBDictionaryChild> searchByDictionaryCode(String dictionaryCode);

    /**
     * 子字典表编辑
     *
     * @param byBDictionaryChild
     */
    void upperSave(ByBDictionaryChild byBDictionaryChild);

    /**
     * 删除
     *
     * @param ids
     */
    void removeByIds(String ids);

    /**
     * 分页查询
     *
     * @param alias
     * @param dictionaryCode
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page searchChild(String alias, String dictionaryCode, Integer pageNo, Integer pageSize);

    /**
     * 根据字典名称获取其code(项目统计时需要,前端获取的为对应名称,查询时需要进行数据转换)
     *
     * @param alias
     * @return
     */
    List<ByBDictionaryChild> searchByParentAlias(String alias);

    /**
     * 查询年份
     * 过滤:在查询到的字典表的年份类型中过滤掉大于当前时间年份的值
     *
     * @param dictionaryCode
     * @return
     */
    List<ByBDictionaryChild> searchLessNowYearByDictionaryCode(String dictionaryCode);
}