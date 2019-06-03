package club.emergency.by_b_contract.manager.impl;

import club.emergency.by_b_contract.manager.ByBContractManager;
import club.emergency.by_b_contract.mapper.ByBContractMapper;
import club.emergency.by_b_contract.model.ByBContract;
import club.map.core.manager.impl.GenericManagerImpl;
import club.map.core.model.FlipFilter;
import club.map.core.model.Operate;
import club.map.core.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class ByBContractManagerImpl extends GenericManagerImpl<ByBContract, Integer> implements ByBContractManager {

    @Autowired
    public ByBContractManagerImpl(ByBContractMapper mapper) {
        super(mapper, ByBContract.class);
    }

    @Override
    public Page search(String projectName, String putFileTime, String companyName, Float contractAmount, Integer contractTypeId, Integer pageNo, Integer pageSize) {
        FlipFilter flipFilter = new FlipFilter(ByBContract.class);
        flipFilter.addSearch("%" + projectName + "%", Operate.LIKE, "projectName");
        flipFilter.addSearch(putFileTime + "%", Operate.LIKE, "putFileTime");
        flipFilter.addSearch("%" + companyName + "%", Operate.LIKE, "companyName");
        flipFilter.addSearch(contractAmount, Operate.EQUAL, "contractAmount");
        flipFilter.addSearch(contractTypeId, Operate.EQUAL, "contractTypeId");
        flipFilter.updatePageNo(pageNo);
        flipFilter.setPageSize(pageSize);
        return this.flipUsingInPage(flipFilter);
    }

    @Transactional
    @Override
    public void upperSave(ByBContract byBContract) {
        this.save(byBContract);
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