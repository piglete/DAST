package club.emergency.by_b_log.manager.impl;

import club.emergency.by_b_log.manager.ByBLogManager;
import club.emergency.by_b_log.mapper.ByBLogMapper;
import club.emergency.by_b_log.model.ByBLog;
import club.map.core.manager.impl.GenericManagerImpl;
import club.map.core.model.FlipFilter;
import club.map.core.model.Operate;
import club.map.core.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class ByBLogManagerImpl extends GenericManagerImpl<ByBLog, Integer> implements ByBLogManager {

    @Autowired
    public ByBLogManagerImpl(ByBLogMapper mapper) {
        super(mapper, ByBLog.class);
    }

    @Override
    public Page search(String loginName, String moduleName, String operationName, String ip, Integer pageNo, Integer pageSize) {
        FlipFilter flipFilter = new FlipFilter(ByBLog.class);
        flipFilter.addSearch("%" + loginName + "%", Operate.LIKE, "loginName");
        flipFilter.addSearch("%" + moduleName + "%", Operate.LIKE, "logType");
        flipFilter.addSearch(operationName + "%", Operate.LIKE, "operationName");
        flipFilter.addSearch("%" + ip + "%", Operate.LIKE, "ip");
        flipFilter.updatePageNo(pageNo);
        flipFilter.setPageSize(pageSize);
        return this.flipUsingInPage(flipFilter);
    }

    @Transactional
    @Override
    public void removeByIds(String ids) {
        String[] idArr = ids.split(",");
        Arrays.asList(idArr).forEach(id -> {
            this.remove(Integer.valueOf(id));
        });
    }

    @Transactional
    @Override
    public void upperSave(ByBLog sysLog) {
        this.save(sysLog);
    }
}