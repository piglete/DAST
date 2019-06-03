package club.emergency.equipment.manager.impl;

import club.emergency.dictionary.manager.ByBDictionaryChildManager;
import club.emergency.equipment.manager.ByBEquipmentManager;
import club.emergency.equipment.mapper.ByBEquipmentMapper;
import club.emergency.equipment.model.ByBEquipment;
import club.map.core.manager.impl.GenericManagerImpl;
import club.map.core.model.FlipFilter;
import club.map.core.model.Operate;
import club.map.core.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class ByBEquipmentManagerImpl extends GenericManagerImpl<ByBEquipment, Integer> implements ByBEquipmentManager {

    private ByBDictionaryChildManager byBDictionaryChildManager;

    @Autowired
    public ByBEquipmentManagerImpl(ByBEquipmentMapper mapper,
                                   ByBDictionaryChildManager byBDictionaryChildManager) {
        super(mapper, ByBEquipment.class);
        this.byBDictionaryChildManager = byBDictionaryChildManager;
    }

    @Override
    public Page search(String assetName, String assetModel, Integer storeTypeId, Integer equipmentStateId, Integer pageNo, Integer pageSize) {
        FlipFilter flipFilter = new FlipFilter(ByBEquipment.class);
        flipFilter.addSearch(assetName, Operate.EQUAL, "assetName");
        flipFilter.addSearch("%" + assetModel + "%", Operate.LIKE, "assetModel");
        flipFilter.addSearch(storeTypeId, Operate.EQUAL, "storeTypeId");
        flipFilter.addSearch(equipmentStateId, Operate.EQUAL, "equipmentStateId");
        flipFilter.setPageSize(pageSize);
        flipFilter.updatePageNo(pageNo);
        Page page = this.flipUsingInPage(flipFilter);
        List<ByBEquipment> byBEquipmentList = page.getListInfo();
        for (ByBEquipment byBEquipment : byBEquipmentList) {
            this.searchInfo(byBEquipment);
        }
        return page;
    }

    @Override
    public ByBEquipment searchDetail(Integer id) {
        ByBEquipment byBEquipment = this.get(id);
        this.searchInfo(byBEquipment);
        return byBEquipment;
    }

    private void searchInfo(ByBEquipment byBEquipment) {
        //存放地点
        Integer storeTypeId = byBEquipment.getStoreTypeId();
        if (storeTypeId != null) {
            String storeTypeName = byBDictionaryChildManager.get(storeTypeId).getAlias();
            byBEquipment.setStoreTypeName(storeTypeName);
        } else {
            byBEquipment.setStoreTypeName("");
        }
        //现状
        Integer equipmentStateId = byBEquipment.getEquipmentStateId();
        if (equipmentStateId != null) {
            String equipmentStateName = byBDictionaryChildManager.get(equipmentStateId).getAlias();
            byBEquipment.setEquipmentStateName(equipmentStateName);
        } else {
            byBEquipment.setEquipmentStateName("");
        }
        //单位
        Integer unitTypeId=byBEquipment.getUnitTypeId();
        if(unitTypeId != null){
            String unitType=byBDictionaryChildManager.get(unitTypeId).getAlias();
            byBEquipment.setUnitType(unitType);
        }else{
            byBEquipment.setUnitType("");
        }
    }

    @Transactional
    @Override
    public void upperSave(ByBEquipment byBEquipment) {
        this.save(byBEquipment);
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