package club.emergency.equipment.manager.impl;

import club.emergency.by_b_department.mapper.ByBDepartmentMapper;
import club.emergency.equipment.manager.ByBEquipmentRepairManager;
import club.emergency.equipment.mapper.ByBEquipmentRepairMapper;
import club.emergency.equipment.model.ByBEquipmentRepair;
import club.map.core.manager.impl.GenericManagerImpl;
import club.map.core.mapper.ColumnTransient;
import club.map.core.model.FlipFilter;
import club.map.core.model.Operate;
import club.map.core.model.Page;
import com.wanqing.util.StringHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class ByBEquipmentRepairManagerImpl extends GenericManagerImpl<ByBEquipmentRepair, Integer> implements ByBEquipmentRepairManager {

    private ByBDepartmentMapper byBDepartmentMapper;

    @Autowired
    public ByBEquipmentRepairManagerImpl(ByBEquipmentRepairMapper mapper,
                                         ByBDepartmentMapper byBDepartmentMapper) {
        super(mapper, ByBEquipmentRepair.class);
        this.byBDepartmentMapper = byBDepartmentMapper;
    }

    @Override
    public Page search(String name, String departmentName, Integer equipmentTypeId, Integer pageNo, Integer pageSize) {
        FlipFilter flipFilter = new FlipFilter(ByBEquipmentRepair.class);
        flipFilter.addSearch("%" + name + "%", Operate.LIKE, "name");
        flipFilter.addSearch(departmentName, Operate.EQUAL, "departmentName");
        flipFilter.addSearch(equipmentTypeId, Operate.EQUAL, "equipmentTypeId");
        flipFilter.updatePageNo(pageNo);
        flipFilter.setPageSize(pageSize);
        return this.flipUsingInPage(flipFilter);
    }

    @ColumnTransient
    @Override
    public void upperSave(ByBEquipmentRepair byBEquipmentRepair) {
        this.save(byBEquipmentRepair);
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
    public ByBEquipmentRepair searchDetail(Integer id) {
        ByBEquipmentRepair byBEquipmentRepair = this.get(id);
        return byBEquipmentRepair;
    }
}