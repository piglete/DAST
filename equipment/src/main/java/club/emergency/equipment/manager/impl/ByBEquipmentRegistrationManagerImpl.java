package club.emergency.equipment.manager.impl;

import club.emergency.by_b_department.manager.ByBDepartmentManager;
import club.emergency.by_b_department.mapper.ByBDepartmentMapper;
import club.emergency.by_b_employee.manager.ByBEmployeeManager;
import club.emergency.dictionary.manager.ByBDictionaryChildManager;
import club.emergency.equipment.manager.ByBEquipmentRegistrationManager;
import club.emergency.equipment.mapper.ByBEquipmentRegistrationMapper;
import club.emergency.equipment.model.ByBEquipmentRegistration;
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
public class ByBEquipmentRegistrationManagerImpl extends GenericManagerImpl<ByBEquipmentRegistration, Integer> implements ByBEquipmentRegistrationManager {

    private ByBDictionaryChildManager byBDictionaryChildManager;
    private ByBEmployeeManager byBEmployeeManager;
    private ByBDepartmentMapper byBDepartmentMapper;

    @Autowired
    public ByBEquipmentRegistrationManagerImpl(ByBEquipmentRegistrationMapper mapper,
                                               ByBDictionaryChildManager byBDictionaryChildManager,
                                               ByBEmployeeManager byBEmployeeManager,
                                               ByBDepartmentMapper byBDepartmentMapper) {
        super(mapper, ByBEquipmentRegistration.class);
        this.byBDictionaryChildManager = byBDictionaryChildManager;
        this.byBEmployeeManager = byBEmployeeManager;
        this.byBDepartmentMapper = byBDepartmentMapper;
    }

    @Override
    public Page search(String equipmentName, String equipmentModel, Integer equipmentTypeId, Integer equipmentStateId, Integer pageNo, Integer pageSize) {
        FlipFilter flipFilter = new FlipFilter(ByBEquipmentRegistration.class);
        flipFilter.addSearch("%" + equipmentName + "%", Operate.LIKE, "equipmentName");
        flipFilter.addSearch("%" + equipmentName + "%", Operate.LIKE, "equipmentName");
        flipFilter.addSearch(equipmentTypeId, Operate.EQUAL, "equipmentTypeId");
        flipFilter.addSearch(equipmentStateId, Operate.EQUAL, "equipmentStateId");
        flipFilter.setPageSize(pageSize);
        flipFilter.updatePageNo(pageNo);
        Page page = this.flipUsingInPage(flipFilter);
        List<ByBEquipmentRegistration> byBEquipmentRegistrationList = page.getListInfo();
        for (ByBEquipmentRegistration byBEquipmentRegistration : byBEquipmentRegistrationList) {
            this.searchInfo(byBEquipmentRegistration);
        }
        return page;
    }

    @Transactional
    @Override
    public void upperSave(ByBEquipmentRegistration byBEquipmentRegistration) {
        this.save(byBEquipmentRegistration);
    }

    @Override
    public ByBEquipmentRegistration searchDetail(Integer id) {
        ByBEquipmentRegistration byBEquipmentRegistration = this.get(id);
        this.searchInfo(byBEquipmentRegistration);
        return byBEquipmentRegistration;
    }

    @Transactional
    @Override
    public void removeByIds(String ids) {
        String[] idArr = ids.split(",");
        Arrays.asList(idArr).forEach(id -> {
            this.remove(Integer.valueOf(id));
        });
    }

    private void searchInfo(ByBEquipmentRegistration byBEquipmentRegistration) {
        //仪器类型
        Integer equipmentTypeId = byBEquipmentRegistration.getEquipmentTypeId();
        if (equipmentTypeId != null) {
            String equipmentTypeName = byBDictionaryChildManager.get(equipmentTypeId).getAlias();
            byBEquipmentRegistration.setEquipmentTypeName(equipmentTypeName);
        } else {
            byBEquipmentRegistration.setEquipmentTypeName("");
        }
        //负责人
        Integer responsiblePersonId = byBEquipmentRegistration.getResponsiblePersonId();
        if (responsiblePersonId != null) {
            String responsiblePersonName = byBEmployeeManager.get(responsiblePersonId).getUsername();
            byBEquipmentRegistration.setResponsiblePersonName(responsiblePersonName);
        } else {
            byBEquipmentRegistration.setResponsiblePersonName("");
        }
        //部门
        String departmentCode = byBEquipmentRegistration.getDepartmentCode();
        if (StringHandler.isNotEmptyOrNull(departmentCode)) {
            String departmentName = byBDepartmentMapper.searchNameByCode(departmentCode);
            byBEquipmentRegistration.setDepartmentName(departmentName);
        } else {
            byBEquipmentRegistration.setDepartmentName("");
        }
        //年份
        Integer particularYearId = byBEquipmentRegistration.getParticularYearId();
        if (particularYearId != null) {
            String particularYear = byBDictionaryChildManager.get(particularYearId).getAlias();
            byBEquipmentRegistration.setParticularYear(particularYear);
        } else {
            byBEquipmentRegistration.setParticularYear("");
        }
        //现状
        Integer equipmentStateId = byBEquipmentRegistration.getEquipmentStateId();
        if (equipmentStateId != null) {
            String equipmentState = byBDictionaryChildManager.get(equipmentStateId).getAlias();
            byBEquipmentRegistration.setEquipmentState(equipmentState);
        } else {
            byBEquipmentRegistration.setEquipmentState("");
        }
    }
}