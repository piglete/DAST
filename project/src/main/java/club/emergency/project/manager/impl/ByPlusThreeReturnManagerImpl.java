package club.emergency.project.manager.impl;

import club.emergency.by_b_return_reason.manager.ByBReturnReasonManager;
import club.emergency.by_b_return_reason.model.ByBReturnReason;
import club.emergency.project.manager.ByPlusThreeReturnManager;
import club.emergency.project.mapper.ByPlusThreeReturnMapper;
import club.emergency.project.model.ByPlusThreeReturn;
import club.map.core.manager.impl.GenericManagerImpl;
import club.map.core.model.FlipFilter;
import club.map.core.model.Operate;
import com.wanqing.util.StringHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ByPlusThreeReturnManagerImpl extends GenericManagerImpl<ByPlusThreeReturn, Integer> implements ByPlusThreeReturnManager {

    private ByBReturnReasonManager byBReturnReasonManager;

    @Autowired
    public ByPlusThreeReturnManagerImpl(ByPlusThreeReturnMapper mapper,
                                        ByBReturnReasonManager byBReturnReasonManager
    ) {
        super(mapper, ByPlusThreeReturn.class);
        this.byBReturnReasonManager = byBReturnReasonManager;
    }

    @Override
    public List<ByPlusThreeReturn> search(Integer taskId) {
        FlipFilter flipFilter = new FlipFilter(ByPlusThreeReturn.class);
        flipFilter.addSearch(taskId, Operate.EQUAL, "taskId");
        List<ByPlusThreeReturn> byPlusThreeReturnList = this.list(flipFilter);
        for (ByPlusThreeReturn byPlusThreeReturn : byPlusThreeReturnList) {
            this.searchInfo(byPlusThreeReturn);
        }
        return byPlusThreeReturnList;
    }

    /**
     * 处理退回原因
     *
     * @param byPlusThreeReturn
     */
    private void searchInfo(ByPlusThreeReturn byPlusThreeReturn) {
        String threeReturnReasonIds = byPlusThreeReturn.getThreeReturnReasonIds();
        String str = "";
        if (StringHandler.isNotEmptyOrNull(threeReturnReasonIds)) {
            String[] idArr = threeReturnReasonIds.split(",");
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
        byPlusThreeReturn.setThreeReturnReason(str);
    }

    @Transactional
    @Override
    public void upperSave(ByPlusThreeReturn byPlusThreeReturn) {
        this.save(byPlusThreeReturn);
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
    public ByPlusThreeReturn searchDetail(Integer id) {
        ByPlusThreeReturn byPlusThreeReturn = this.get(id);
        this.searchInfo(byPlusThreeReturn);
        return byPlusThreeReturn;
    }
}