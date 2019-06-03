package club.emergency.equipment.manager.impl;

import club.emergency.equipment.manager.ByBEquipmentCheckManager;
import club.emergency.equipment.mapper.ByBEquipmentCheckMapper;
import club.emergency.equipment.model.ByBEquipmentCheck;
import club.map.core.manager.impl.GenericManagerImpl;
import club.map.core.model.FlipFilter;
import club.map.core.model.Operate;
import club.map.core.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class ByBEquipmentCheckManagerImpl extends GenericManagerImpl<ByBEquipmentCheck, Integer> implements ByBEquipmentCheckManager {

    @Autowired
    public ByBEquipmentCheckManagerImpl(ByBEquipmentCheckMapper mapper) {
        super(mapper, ByBEquipmentCheck.class);
    }

    @Override
    public Page search(String name, Integer equipmentTypeId, String departmentName, Integer pageNo, Integer pageSize) {
        FlipFilter flipFilter = new FlipFilter(ByBEquipmentCheck.class);
        flipFilter.addSearch("%" + name + "%", Operate.LIKE, "name");
        flipFilter.addSearch(equipmentTypeId, Operate.EQUAL, "equipmentTypeId");
        flipFilter.addSearch(departmentName, Operate.EQUAL, "departmentName");
        flipFilter.updatePageNo(pageNo);
        flipFilter.setPageSize(pageSize);
        return this.flipUsingInPage(flipFilter);
    }

    @Transactional
    @Override
    public void upperSave(ByBEquipmentCheck byBEquipmentCheck) {
        this.save(byBEquipmentCheck);
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