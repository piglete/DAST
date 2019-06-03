package club.emergency.project.manager.impl;

import club.emergency.by_b_return_reason.manager.ByBReturnReasonManager;
import club.emergency.by_b_return_reason.model.ByBReturnReason;
import club.emergency.dictionary.manager.ByBDictionaryChildManager;
import club.emergency.project.manager.ByPlusTwoReturnManager;
import club.emergency.project.mapper.ByPlusTwoReturnMapper;
import club.emergency.project.model.ByPlusTwoReturn;
import club.map.core.manager.impl.GenericManagerImpl;
import club.map.core.model.FlipFilter;
import club.map.core.model.Operate;
import com.wanqing.util.StringHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ByPlusTwoReturnManagerImpl extends GenericManagerImpl<ByPlusTwoReturn, Integer> implements ByPlusTwoReturnManager {

    private ByBReturnReasonManager byBReturnReasonManager;

    @Autowired
    public ByPlusTwoReturnManagerImpl(ByPlusTwoReturnMapper mapper,
                                      ByBReturnReasonManager byBReturnReasonManager
    ) {
        super(mapper, ByPlusTwoReturn.class);
        this.byBReturnReasonManager = byBReturnReasonManager;
    }

    @Override
    public List<ByPlusTwoReturn> search(Integer taskId) {
        FlipFilter flipFilter = new FlipFilter(ByPlusTwoReturn.class);
        flipFilter.addSearch(taskId, Operate.EQUAL, "taskId");
        List<ByPlusTwoReturn> byPlusTwoReturnList = this.list(flipFilter);
        for (ByPlusTwoReturn byPlusTwoReturn : byPlusTwoReturnList) {
            this.searchInfo(byPlusTwoReturn);
        }
        return byPlusTwoReturnList;
    }

    /**
     * 处理退回原因
     *
     * @param byPlusTwoReturn
     */
    private void searchInfo(ByPlusTwoReturn byPlusTwoReturn) {
        String twoReturnReasonIds = byPlusTwoReturn.getTwoReturnReasonIds();
        String str = "";
        if (StringHandler.isNotEmptyOrNull(twoReturnReasonIds)) {
            String[] idArr = twoReturnReasonIds.split(",");
            Map<Integer, String> map = new HashMap<>();
            for (String id : idArr) {
                Integer pid = Integer.valueOf(id);
                ByBReturnReason byBReturnReason = byBReturnReasonManager.searchDetails(pid);
                String content = byBReturnReason.getContent();
                Integer parentId = byBReturnReason.getParentId();
                String returnReason = byBReturnReason.getParentName() + "-" + content;
                if (map.containsKey(parentId)) {
                    String value = map.get(parentId);
                    value += "、" + content;
                    map.put(parentId, value);
                } else {
                    map.put(parentId, returnReason);
                }
            }
            for (Map.Entry entry : map.entrySet()) {
                String value = entry.getValue().toString();
                str += value + ",";
            }
            str = str.substring(0, str.length() - 1);
        }
        byPlusTwoReturn.setTwoReturnReason(str);
    }

    @Transactional
    @Override
    public void upperSave(ByPlusTwoReturn byPlusTwoReturn) {
        this.save(byPlusTwoReturn);
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
    public ByPlusTwoReturn searchDetails(Integer id) {
        ByPlusTwoReturn byPlusTwoReturn = this.get(id);
        this.searchInfo(byPlusTwoReturn);
        return byPlusTwoReturn;
    }
}