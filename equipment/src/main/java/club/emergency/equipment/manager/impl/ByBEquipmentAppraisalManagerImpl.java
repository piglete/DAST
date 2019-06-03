package club.emergency.equipment.manager.impl;

import club.emergency.by_b_department.mapper.ByBDepartmentMapper;
import club.emergency.dictionary.manager.ByBDictionaryChildManager;
import club.emergency.equipment.manager.ByBEquipmentAppraisalManager;
import club.emergency.equipment.mapper.ByBEquipmentAppraisalMapper;
import club.emergency.equipment.model.ByBEquipmentAppraisal;
import club.map.core.manager.impl.GenericManagerImpl;
import club.map.core.model.FlipFilter;
import club.map.core.model.Operate;
import club.map.core.model.Page;
import com.wanqing.util.StringHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class ByBEquipmentAppraisalManagerImpl extends GenericManagerImpl<ByBEquipmentAppraisal, Integer> implements ByBEquipmentAppraisalManager {

    private ByBDepartmentMapper byBDepartmentMapper;
    private ByBDictionaryChildManager byBDictionaryChildManager;

    @Autowired
    public ByBEquipmentAppraisalManagerImpl(ByBEquipmentAppraisalMapper mapper,
                                            ByBDepartmentMapper byBDepartmentMapper,
                                            ByBDictionaryChildManager byBDictionaryChildManager) {
        super(mapper, ByBEquipmentAppraisal.class);
        this.byBDepartmentMapper = byBDepartmentMapper;
        this.byBDictionaryChildManager = byBDictionaryChildManager;
    }

    @Override
    public Page search(Integer equipmentStateId, String storeTypeName, String assetName, String departmentCode, Integer pageNo, Integer pageSize) {
        FlipFilter flipFilter = new FlipFilter(ByBEquipmentAppraisal.class);
        flipFilter.addSearch(equipmentStateId, Operate.EQUAL, "equipmentStateId");
        flipFilter.addSearch("%" + assetName + "%", Operate.LIKE, "assetName");
        flipFilter.addSearch(storeTypeName, Operate.EQUAL, "storeTypeName");
        flipFilter.addSearch(departmentCode, Operate.EQUAL, "departmentCode");
        flipFilter.updatePageNo(pageNo);
        flipFilter.setPageSize(pageSize);
        Page page = this.flipUsingInPage(flipFilter);
        List<ByBEquipmentAppraisal> byBEquipmentAppraisalList = page.getListInfo();
        for (ByBEquipmentAppraisal byBEquipmentAppraisal : byBEquipmentAppraisalList) {
            this.searchInfo(byBEquipmentAppraisal);
        }
        return page;
    }

    @Transactional
    @Override
    public void upperSave(ByBEquipmentAppraisal byBEquipmentAppraisal) {
        this.save(byBEquipmentAppraisal);
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
    public ByBEquipmentAppraisal searchDetail(Integer id) {
        ByBEquipmentAppraisal byBEquipmentAppraisal = this.get(id);
        this.searchInfo(byBEquipmentAppraisal);
        return byBEquipmentAppraisal;
    }

    private void searchInfo(ByBEquipmentAppraisal byBEquipmentAppraisal) {
        String departmentCode = byBEquipmentAppraisal.getDepartmentCode();
        if (StringHandler.isNotEmptyOrNull(departmentCode)) {
            String departmentName = byBDepartmentMapper.getByCode(departmentCode).getDepartmentName();
            byBEquipmentAppraisal.setDepartmentName(departmentName);
        } else {
            byBEquipmentAppraisal.setDepartmentName("");
        }
        Integer equipmentStateId = byBEquipmentAppraisal.getEquipmentStateId();
        if (equipmentStateId != null) {
            String equipmentState = byBDictionaryChildManager.get(equipmentStateId).getAlias();
            byBEquipmentAppraisal.setEquipmentState(equipmentState);
        } else {
            byBEquipmentAppraisal.setEquipmentState("");
        }
    }
}