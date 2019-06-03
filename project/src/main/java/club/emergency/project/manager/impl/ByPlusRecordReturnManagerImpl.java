package club.emergency.project.manager.impl;

import club.emergency.dictionary.manager.ByBDictionaryChildManager;
import club.emergency.project.manager.ByPlusRecordReturnManager;
import club.emergency.project.mapper.ByPlusRecordReturnMapper;
import club.emergency.project.model.ByPlusRecordReturn;
import club.map.core.manager.impl.GenericManagerImpl;
import club.map.core.model.FlipFilter;
import club.map.core.model.Operate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class ByPlusRecordReturnManagerImpl extends GenericManagerImpl<ByPlusRecordReturn, Integer> implements ByPlusRecordReturnManager {

    private ByBDictionaryChildManager byBDictionaryChildManager;

    @Autowired
    public ByPlusRecordReturnManagerImpl(ByPlusRecordReturnMapper mapper,
                                         ByBDictionaryChildManager byBDictionaryChildManager
    ) {
        super(mapper, ByPlusRecordReturn.class);
        this.byBDictionaryChildManager = byBDictionaryChildManager;
    }

    /**
     * 多条件查询
     *
     * @param taskId
     * @param recordReturnReasonId
     * @return
     */
    @Override
    public List<ByPlusRecordReturn> search(Integer taskId, Integer recordReturnReasonId) {
        FlipFilter flipFilter = new FlipFilter(ByPlusRecordReturn.class);
        flipFilter.addSearch(taskId, Operate.EQUAL, "taskId");
        flipFilter.addSearch(recordReturnReasonId, Operate.EQUAL, "recordReturnReasonId");
        List<ByPlusRecordReturn> byPlusRecordReturnList = this.list(flipFilter);
        for (ByPlusRecordReturn byPlusRecordReturn : byPlusRecordReturnList) {
            this.searchInfo(byPlusRecordReturn);
        }
        return byPlusRecordReturnList;
    }

    /**
     * 依赖模块属性注入,例如依赖字典数据,需要注入字典id对应的名称
     *
     * @param byPlusRecordReturn
     */
    private void searchInfo(ByPlusRecordReturn byPlusRecordReturn) {
        Integer recordReturnReasonId = byPlusRecordReturn.getRecordReturnReasonId();
        if (recordReturnReasonId != null) {
            String recordReturnReason = byBDictionaryChildManager.get(recordReturnReasonId).getAlias();
            byPlusRecordReturn.setRecordReturnReason(recordReturnReason);
        } else {
            byPlusRecordReturn.setRecordReturnReason("");
        }
    }

    @Transactional
    @Override
    public void upperSave(ByPlusRecordReturn byPlusRecordReturn) {
        this.save(byPlusRecordReturn);
    }

    @Transactional
    @Override
    public void removeByIds(String ids) {
        String[] idArr = ids.split(",");
        Arrays.asList(idArr).forEach(id -> {
            this.remove(Integer.valueOf(id));
        });
    }

    @Override
    public ByPlusRecordReturn searchDetail(Integer id) {
        ByPlusRecordReturn byPlusRecordReturn = this.get(id);
        this.searchInfo(byPlusRecordReturn);
        return byPlusRecordReturn;
    }
}