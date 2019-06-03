package club.emergency.project.manager.impl;

import club.emergency.dictionary.manager.ByBDictionaryChildManager;
import club.emergency.project.manager.ByPlusRecordModelManager;
import club.emergency.project.mapper.ByPlusRecordModelMapper;
import club.emergency.project.model.ByPlusRecordModel;
import club.emergency.project.model.ByPlusTaskFile;
import club.map.base.util.FtpUtils;
import club.map.core.manager.impl.GenericManagerImpl;
import club.map.core.model.FlipFilter;
import club.map.core.model.Operate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class ByPlusRecordModelManagerImpl extends GenericManagerImpl<ByPlusRecordModel, Integer> implements ByPlusRecordModelManager {

    private ByBDictionaryChildManager byBDictionaryChildManager;

    @Autowired
    public ByPlusRecordModelManagerImpl(ByPlusRecordModelMapper mapper,
                                        ByBDictionaryChildManager byBDictionaryChildManager
    ) {
        super(mapper, ByPlusRecordModel.class);
        this.byBDictionaryChildManager = byBDictionaryChildManager;
    }

    @Override
    public List<ByPlusRecordModel> search(Integer recordTypeId) {
        FlipFilter flipFilter=new FlipFilter(ByPlusRecordModel.class);
        flipFilter.addSearch(recordTypeId,Operate.EQUAL,"recordTypeId");
        List<ByPlusRecordModel> byPlusRecordModelList=this.list(flipFilter);
        for (ByPlusRecordModel byPlusRecordModel : byPlusRecordModelList) {
            this.searchInfo(byPlusRecordModel);
        }
        return byPlusRecordModelList;
    }

    @Transactional
    @Override
    public void upperSave(ByPlusRecordModel byPlusRecordModel) {
        this.save(byPlusRecordModel);
    }

    @Override
    public ByPlusRecordModel searchDetail(Integer id) {
        ByPlusRecordModel byPlusRecordModel=this.get(id);
        this.searchInfo(byPlusRecordModel);
        return byPlusRecordModel;
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
     * 查询详情(字典项具体内容)
     * @param byPlusRecordModel
     */
    private void searchInfo(ByPlusRecordModel byPlusRecordModel) {
        Integer recordTypeId=byPlusRecordModel.getRecordTypeId();
        if(recordTypeId != null){
            String recordType=byBDictionaryChildManager.get(recordTypeId).getAlias();
            byPlusRecordModel.setRecordType(recordType);
        }else{
            byPlusRecordModel.setRecordType("");
        }
    }
}