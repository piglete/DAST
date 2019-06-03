package club.emergency.project.manager.impl;

import club.emergency.by_b_employee.mapper.ByBEmployeeMapper;
import club.emergency.project.manager.ByPlusTaskStopManager;
import club.emergency.project.mapper.ByPlusTaskStopMapper;
import club.emergency.project.model.ByPlusTaskStop;
import club.map.core.manager.impl.GenericManagerImpl;
import club.map.core.model.FlipFilter;
import club.map.core.model.Operate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class ByPlusTaskStopManagerImpl extends GenericManagerImpl<ByPlusTaskStop, Integer> implements ByPlusTaskStopManager {

    private ByBEmployeeMapper byBEmployeeMapper;

    @Autowired
    public ByPlusTaskStopManagerImpl(ByPlusTaskStopMapper mapper,
                                     ByBEmployeeMapper byBEmployeeMapper) {
        super(mapper, ByPlusTaskStop.class);
        this.byBEmployeeMapper = byBEmployeeMapper;
    }

    @Override
    public List<ByPlusTaskStop> search(Integer taskId, Integer stopOperatorId) {
        FlipFilter flipFilter = new FlipFilter(ByPlusTaskStop.class);
        flipFilter.addSearch(taskId, Operate.EQUAL, "taskId");
        flipFilter.addSearch(stopOperatorId, Operate.EQUAL, "stopOperatorId");
        List<ByPlusTaskStop> byPlusTaskStopList = this.list(flipFilter);
        for (ByPlusTaskStop byPlusTaskStop : byPlusTaskStopList) {
            this.searchInfo(byPlusTaskStop);
        }
        return byPlusTaskStopList;
    }

    @Transactional
    @Override
    public void upperSave(ByPlusTaskStop byPlusTaskStop) {
        this.save(byPlusTaskStop);
    }

    /**
     * 查询任务暂停详情
     *
     * @param taskId
     * @param stopOrder
     * @return
     */
    @Override
    public ByPlusTaskStop searchDetail(Integer taskId, Integer stopOrder) {
        ByPlusTaskStop byPlusTaskStop = null;
        FlipFilter flipFilter = new FlipFilter(ByPlusTaskStop.class);
        flipFilter.addSearch(taskId, Operate.EQUAL, "taskId");
        flipFilter.addSearch(stopOrder, Operate.EQUAL, "stopOrder");
        List<ByPlusTaskStop> byPlusTaskStopList = this.list(flipFilter);
        if (byPlusTaskStopList.size() == 1) {
            byPlusTaskStop = byPlusTaskStopList.get(0);
        }
        return byPlusTaskStop;
    }

    @Transactional
    @Override
    public void removeByIds(String ids) {
        String[] idArr = ids.split(",");
        Arrays.asList(idArr).forEach(id -> {
            this.remove(Integer.valueOf(id));
        });
    }

    /**
     * @param byPlusTaskStop
     */
    private void searchInfo(ByPlusTaskStop byPlusTaskStop) {
        Integer stopOperatorId = byPlusTaskStop.getStopOperatorId();
        if (stopOperatorId != null) {
            String stopOperator = byBEmployeeMapper.searchUsernameAndDepartmentCodeById(stopOperatorId).getUsername();
            byPlusTaskStop.setStopOperator(stopOperator);
        } else {
            byPlusTaskStop.setStopOperator("");
        }
    }
}