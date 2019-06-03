package club.emergency.project.manager.impl;

import club.emergency.project.manager.ByBProjectTenderManager;
import club.emergency.project.mapper.ByBProjectTenderMapper;
import club.emergency.project.model.ByBProjectTender;
import club.map.core.manager.impl.GenericManagerImpl;
import club.map.core.model.FlipFilter;
import club.map.core.model.Operate;
import club.map.core.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class ByBProjectTenderManagerImpl extends GenericManagerImpl<ByBProjectTender, Integer> implements ByBProjectTenderManager {

    @Autowired
    public ByBProjectTenderManagerImpl(ByBProjectTenderMapper mapper) {
        super(mapper, ByBProjectTender.class);
    }

    @Override
    public Page search(String projectName, String agentCompany, String openTime, String openAddress, String bond, String bondTime, Integer pageNo, Integer pageSize) {
        FlipFilter flipFilter = new FlipFilter(ByBProjectTender.class);
        flipFilter.addSearch("%" + projectName + "%", Operate.LIKE, "projectName");
        flipFilter.addSearch("%" + agentCompany + "%", Operate.LIKE, "agentCompany");
        flipFilter.addSearch("%" + openTime + "%", Operate.LIKE, "openTime");
        flipFilter.addSearch("%" + openAddress + "%", Operate.LIKE, "openAddress");
        //查询条件处理
        if (bond.contains(",")) {
            String[] bondField = bond.split(",");
            //范围查询 between ... and ...
            flipFilter.addRegion("bond", bondField[0], bondField[1]);
        } else if (bond.contains("<")) {
            Integer bondField = Integer.valueOf(bond.substring(1, bond.length()));
            //范围查询  <
            flipFilter.addSearch(bondField, Operate.LESS, "bond");
        } else if (bond.contains(">")) {
            Integer bondField = Integer.valueOf(bond.substring(1, bond.length()));
            //范围查询  >
            flipFilter.addSearch(bondField, Operate.GREAT, "bond");
        }
        flipFilter.addSearch(bondTime, Operate.EQUAL, "bondTime");
        flipFilter.updatePageNo(pageNo);
        flipFilter.setPageSize(pageSize);
        return this.flipUsingInPage(flipFilter);
    }

    @Transactional
    @Override
    public void upperSave(ByBProjectTender byBProjectTender) {
        this.save(byBProjectTender);
    }

    @Transactional
    @Override
    public void removeByIds(String ids) {
        String[] idArr = ids.split(",");
        Arrays.asList(idArr).forEach(id -> {
            this.remove(Integer.valueOf(id));
        });
    }
}