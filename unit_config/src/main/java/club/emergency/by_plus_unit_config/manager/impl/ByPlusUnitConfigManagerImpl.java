package club.emergency.by_plus_unit_config.manager.impl;

import club.emergency.by_plus_unit_config.manager.ByPlusUnitConfigManager;
import club.emergency.by_plus_unit_config.mapper.ByPlusUnitConfigMapper;
import club.emergency.by_plus_unit_config.model.ByPlusUnitConfig;
import club.emergency.dictionary.manager.ByBDictionaryChildManager;
import club.map.core.manager.impl.GenericManagerImpl;
import club.map.core.model.FlipFilter;
import club.map.core.model.Operate;
import club.map.core.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class ByPlusUnitConfigManagerImpl extends GenericManagerImpl<ByPlusUnitConfig, Integer> implements ByPlusUnitConfigManager {

    @Autowired
    private ByPlusUnitConfigMapper byPlusUnitConfigMapper;
    @Autowired
    private ByBDictionaryChildManager byBDictionaryChildManager;

    @Autowired
    public ByPlusUnitConfigManagerImpl(ByPlusUnitConfigMapper mapper) {
        super(mapper, ByPlusUnitConfig.class);
    }

    @Override
    public void upperSave(ByPlusUnitConfig byPlusUnitConfig) {
        this.upperSave(byPlusUnitConfig);
    }

    @Override
    public Page search(Integer itemApplicationId, Integer workContentId, Integer pageNo, Integer pageSize) {
        FlipFilter flipFilter = new FlipFilter(ByPlusUnitConfig.class);
        flipFilter.addSearch(itemApplicationId, Operate.EQUAL, "itemApplicationId");
        flipFilter.addSearch(workContentId, Operate.EQUAL, "workContentId");

        Page page = this.flipListInPage(flipFilter);
        List<ByPlusUnitConfig> byPlusUnitConfigList = page.getListInfo();
        for (ByPlusUnitConfig byPlusUnitConfig : byPlusUnitConfigList) {
            this.searchInfo(byPlusUnitConfig);
        }
        return page;
    }

    @Override
    public ByPlusUnitConfig search(Integer taskId,Integer itemApplicationId,Integer unitTypeId) {
        return byPlusUnitConfigMapper.search(taskId,itemApplicationId,unitTypeId);
    }

    @Override
    public BigDecimal workloadOperation(String unitPrice, String count) {
        BigDecimal unitPrice1=new BigDecimal(unitPrice);
        BigDecimal count1=new BigDecimal(count);
        return unitPrice1.multiply(count1);
    }

    @Override
    public List<ByPlusUnitConfig> searchByTaskId(Integer taskId) {
        return byPlusUnitConfigMapper.searchByTaskId(taskId);
    }

    @Transactional
    @Override
    public void removeByIds(String ids) {
        String[] idArr = ids.split(",");
        Arrays.asList(idArr).forEach(id -> {
            this.remove(Integer.valueOf(id));
        });
    }
    /**
     * 查询详情 modified by lxl 190522
     *
     * @param byPlusUnitConfig
     */
    private void searchInfo(ByPlusUnitConfig byPlusUnitConfig) {
        //获取单位
        Integer unitTypeId = byPlusUnitConfig.getUnitTypeId();
        if (unitTypeId != null) {
            String unitType = byBDictionaryChildManager.get(unitTypeId).getAlias();
            byPlusUnitConfig.setUnitTypeName(unitType);
        } else {
            byPlusUnitConfig.setUnitTypeName("");
        }
        //获取项目申报
        Integer itemApplicationId = byPlusUnitConfig.getItemApplicationId();
        if (itemApplicationId != null) {
            String itemApplication = byBDictionaryChildManager.get(itemApplicationId).getAlias();
            byPlusUnitConfig.setItemApplicationName(itemApplication);
        } else {
            byPlusUnitConfig.setItemApplicationName("");
        }

        //获取项目类型
        Integer recordTypeId = byPlusUnitConfig.getRecordTypeId();
        if (recordTypeId!=null) {
            String recordTypeName = byBDictionaryChildManager.get(recordTypeId).getAlias();
            byPlusUnitConfig.setRecordTypeName(recordTypeName);
        } else {
            byPlusUnitConfig.setRecordTypeName("");
        }

    }
}