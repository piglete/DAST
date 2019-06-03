package club.emergency.project.manager.impl;

import club.emergency.dictionary.manager.ByBDictionaryChildManager;
import club.emergency.project.manager.VProjectTotalOutputManager;
import club.emergency.project.mapper.VProjectTotalOutputMapper;
import club.emergency.project.model.VProjectTotalOutput;
import club.map.core.manager.impl.GenericManagerImpl;
import club.map.core.model.FlipFilter;
import club.map.core.model.Operate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class VProjectTotalOutputManagerImpl extends GenericManagerImpl<VProjectTotalOutput, Integer> implements VProjectTotalOutputManager {

    @Autowired
    private ByBDictionaryChildManager byBDictionaryChildManager;
    @Autowired
    public VProjectTotalOutputManagerImpl(VProjectTotalOutputMapper mapper) {
        super(mapper, VProjectTotalOutput.class);
    }

    @Override
    public List<VProjectTotalOutput> searchByProjectId(Integer id) {
        FlipFilter flipFilter = new FlipFilter(VProjectTotalOutput.class);
        flipFilter.addSearch(id, Operate.EQUAL, "projectId");
        List<VProjectTotalOutput> vProjectTotalOutputList = this.list(flipFilter);
        for (VProjectTotalOutput vProjectTotalOutput : vProjectTotalOutputList) {
            this.searchInfo(vProjectTotalOutput);
        }
        return vProjectTotalOutputList;
    }

    private void searchInfo(VProjectTotalOutput vProjectTotalOutput) {
        //计算工作量的产值
        try{
            if (vProjectTotalOutput.getTempInternalVal() == null) {
                BigDecimal internalWorkOutput=vProjectTotalOutput.getWorkCount().multiply(vProjectTotalOutput.getInternalVal());
                vProjectTotalOutput.setInternalWorkOutput(internalWorkOutput);
            }else{
                BigDecimal internalWorkOutput=vProjectTotalOutput.getWorkCount().multiply(vProjectTotalOutput.getTempInternalVal());
                vProjectTotalOutput.setInternalWorkOutput(internalWorkOutput);
            }

        }catch (Exception e){

        }
        //获取单位
        Integer unitTypeId = vProjectTotalOutput.getUnitTypeId();
        if (unitTypeId != null) {
            String unitType = byBDictionaryChildManager.get(unitTypeId).getAlias();
            vProjectTotalOutput.setUnitTypeName(unitType);
        } else {
            vProjectTotalOutput.setUnitTypeName("");
        }
        //获取项目申报
        Integer itemApplicationId = vProjectTotalOutput.getItemApplicationId();
        if (itemApplicationId != null) {
            String itemApplication = byBDictionaryChildManager.get(itemApplicationId).getAlias();
            vProjectTotalOutput.setItemApplicationName(itemApplication);
        } else {
            vProjectTotalOutput.setItemApplicationName("");
        }

    }
}