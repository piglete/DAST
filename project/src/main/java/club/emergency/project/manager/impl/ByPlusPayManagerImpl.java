package club.emergency.project.manager.impl;

import club.emergency.by_b_department.mapper.ByBDepartmentMapper;
import club.emergency.dictionary.manager.ByBDictionaryChildManager;
import club.emergency.project.manager.ByPlusPayManager;
import club.emergency.project.mapper.ByPlusPayMapper;
import club.emergency.project.model.ByPlusPay;
import club.map.core.manager.impl.GenericManagerImpl;
import club.map.core.model.FlipFilter;
import club.map.core.model.Operate;
import com.wanqing.util.StringHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class ByPlusPayManagerImpl extends GenericManagerImpl<ByPlusPay, Integer> implements ByPlusPayManager {

    private ByBDictionaryChildManager byBDictionaryChildManager;
    private ByBDepartmentMapper byBDepartmentMapper;

    @Autowired
    public ByPlusPayManagerImpl(ByPlusPayMapper mapper,
                                ByBDictionaryChildManager byBDictionaryChildManager,
                                ByBDepartmentMapper byBDepartmentMapper) {
        super(mapper, ByPlusPay.class);
        this.byBDictionaryChildManager = byBDictionaryChildManager;
        this.byBDepartmentMapper = byBDepartmentMapper;
    }

    @Override
    public List<ByPlusPay> search(Integer projectId) {
        FlipFilter flipFilter = new FlipFilter(ByPlusPay.class);
        flipFilter.addSearch(projectId, Operate.EQUAL, "projectId");
        List<ByPlusPay> byPlusPayList = this.list(flipFilter);
        for (ByPlusPay byPlusPay : byPlusPayList) {
            this.searchInfo(byPlusPay);
        }
        return byPlusPayList;
    }

    @Transactional
    @Override
    public void upperSave(ByPlusPay byPlusPay) {
        this.save(byPlusPay);
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
    public ByPlusPay searchById(Integer id) {
        ByPlusPay byPlusPay = this.get(id);
        this.searchInfo(byPlusPay);
        return byPlusPay;
    }

    /**
     * 查询详情(其他信息注入)
     *
     * @param byPlusPay
     */
    private void searchInfo(ByPlusPay byPlusPay) {
        //获取部门名称
        String departmentCode = byPlusPay.getDepartmentCode();
        if (StringHandler.isNotEmptyOrNull(departmentCode)) {
            String departmentName = byBDepartmentMapper.searchNameByCode(departmentCode);
            byPlusPay.setDepartmentName(departmentName);
        } else {
            byPlusPay.setDepartmentName("");
        }
        //获取交付类型名称
        Integer payTypeId = byPlusPay.getPayTypeId();
        if (payTypeId != null) {
            String payType = byBDictionaryChildManager.get(payTypeId).getAlias();
            byPlusPay.setPayType(payType);
        } else {
            byPlusPay.setPayType("");
        }
    }
}