package club.emergency.project.manager.impl;

import club.emergency.by_b_return_reason.manager.ByBReturnReasonManager;
import club.emergency.by_b_return_reason.model.ByBReturnReason;
import club.emergency.project.manager.ByPlusThreeReturnTwoManager;
import club.emergency.project.mapper.ByPlusThreeReturnTwoMapper;
import club.emergency.project.model.ByPlusThreeReturnTwo;
import club.map.core.manager.impl.GenericManagerImpl;
import club.map.core.model.FlipFilter;
import club.map.core.model.Operate;
import com.wanqing.util.StringHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ByPlusThreeReturnTwoManagerImpl extends GenericManagerImpl<ByPlusThreeReturnTwo, Integer> implements ByPlusThreeReturnTwoManager {

    private ByBReturnReasonManager byBReturnReasonManager;

    @Autowired
    public ByPlusThreeReturnTwoManagerImpl(ByPlusThreeReturnTwoMapper mapper,
                                           ByBReturnReasonManager byBReturnReasonManager) {
        super(mapper, ByPlusThreeReturnTwo.class);
        this.byBReturnReasonManager = byBReturnReasonManager;
    }

    @Transactional
    @Override
    public void upperSave(ByPlusThreeReturnTwo byPlusThreeReturnTwo) {
        this.save(byPlusThreeReturnTwo);
    }

    @Override
    public List<ByPlusThreeReturnTwo> search(Integer taskId) {
        FlipFilter flipFilter = new FlipFilter(ByPlusThreeReturnTwo.class);
        flipFilter.addSearch(taskId, Operate.EQUAL, "taskId");
        List<ByPlusThreeReturnTwo> byPlusThreeReturnTwoList = this.list(flipFilter);
        for (ByPlusThreeReturnTwo byPlusThreeReturnTwo : byPlusThreeReturnTwoList) {
            this.searchInfo(byPlusThreeReturnTwo);
        }
        return byPlusThreeReturnTwoList;
    }

    /**
     * 退回原因详情
     *
     * @param byPlusThreeReturnTwo
     */
    private void searchInfo(ByPlusThreeReturnTwo byPlusThreeReturnTwo) {
        String returnReasonIds = byPlusThreeReturnTwo.getReturnReasonIds();
        String str="";
        if (StringHandler.isNotEmptyOrNull(returnReasonIds)) {
            String[] idArr = returnReasonIds.split(",");
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
        byPlusThreeReturnTwo.setReturnReason(str);
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
    public ByPlusThreeReturnTwo searchDetail(Integer id) {
        ByPlusThreeReturnTwo byPlusThreeReturnTwo = this.get(id);
        this.searchInfo(byPlusThreeReturnTwo);
        return byPlusThreeReturnTwo;
    }
}