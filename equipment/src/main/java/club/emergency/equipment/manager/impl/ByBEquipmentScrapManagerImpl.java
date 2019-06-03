package club.emergency.equipment.manager.impl;

import club.emergency.by_b_department.mapper.ByBDepartmentMapper;
import club.emergency.equipment.manager.ByBEquipmentScrapManager;
import club.emergency.equipment.mapper.ByBEquipmentScrapMapper;
import club.emergency.equipment.model.ByBEquipmentScrap;
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
public class ByBEquipmentScrapManagerImpl extends GenericManagerImpl<ByBEquipmentScrap, Integer> implements ByBEquipmentScrapManager {

    private ByBDepartmentMapper byBDepartmentMapper;

    @Autowired
    public ByBEquipmentScrapManagerImpl(ByBEquipmentScrapMapper mapper,
                                        ByBDepartmentMapper byBDepartmentMapper) {
        super(mapper, ByBEquipmentScrap.class);
        this.byBDepartmentMapper = byBDepartmentMapper;
    }

    @Override
    public Page search(String name, String departmentName, Integer equipmentTypeId, Integer pageNo, Integer pageSize) {
        FlipFilter flipFilter = new FlipFilter(ByBEquipmentScrap.class);
        flipFilter.addSearch("%" + name + "%", Operate.LIKE, "name");
        flipFilter.addSearch(departmentName, Operate.EQUAL, "departmentName");
        flipFilter.addSearch(equipmentTypeId, Operate.EQUAL, "equipmentTypeId");
        flipFilter.updatePageNo(pageNo);
        flipFilter.setPageSize(pageSize);
        Page page = this.flipUsingInPage(flipFilter);
        return page;
    }

    @Transactional
    @Override
    public void upperSave(ByBEquipmentScrap byBEquipmentScrap) {
        this.save(byBEquipmentScrap);
    }

    @Override
    public ByBEquipmentScrap searchDetail(Integer id) {
        ByBEquipmentScrap byBEquipmentScrap = this.get(id);
        return byBEquipmentScrap;
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