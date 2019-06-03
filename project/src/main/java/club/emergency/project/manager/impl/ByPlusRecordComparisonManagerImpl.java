package club.emergency.project.manager.impl;

import club.emergency.project.manager.ByPlusRecordComparisonManager;
import club.emergency.project.manager.ByPlusRecordModelManager;
import club.emergency.project.mapper.ByPlusProjectMapper;
import club.emergency.project.mapper.ByPlusRecordComparisonMapper;
import club.emergency.project.model.ByPlusRecordComparison;
import club.map.core.manager.impl.GenericManagerImpl;
import club.map.core.model.FlipFilter;
import club.map.core.model.Operate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class ByPlusRecordComparisonManagerImpl extends GenericManagerImpl<ByPlusRecordComparison, Integer> implements ByPlusRecordComparisonManager {

    private ByPlusRecordModelManager byPlusRecordModelManager;
    private ByPlusRecordComparisonMapper byPlusRecordComparisonMapper;
    private ByPlusProjectMapper byPlusProjectMapper;

    @Autowired
    public ByPlusRecordComparisonManagerImpl(ByPlusRecordComparisonMapper mapper,
                                             ByPlusRecordModelManager byPlusRecordModelManager,
                                             ByPlusRecordComparisonMapper byPlusRecordComparisonMapper,
                                             ByPlusProjectMapper byPlusProjectMapper
    ) {
        super(mapper, ByPlusRecordComparison.class);
        this.byPlusRecordModelManager = byPlusRecordModelManager;
        this.byPlusRecordComparisonMapper = byPlusRecordComparisonMapper;
        this.byPlusProjectMapper = byPlusProjectMapper;
    }

    @Override
    public List<ByPlusRecordComparison> search(Integer projectId) {
        FlipFilter flipFilter = new FlipFilter(ByPlusRecordComparison.class);
        flipFilter.addSearch(projectId, Operate.EQUAL, "projectId");
        List<ByPlusRecordComparison> byPlusRecordComparisonList = this.list(flipFilter);
        for (ByPlusRecordComparison byPlusRecordComparison : byPlusRecordComparisonList) {
            this.searchInfo(byPlusRecordComparison);
        }
        return byPlusRecordComparisonList;
    }

    @Override
    public ByPlusRecordComparison searchDetail(Integer id) {
        ByPlusRecordComparison byPlusRecordComparison = this.get(id);
        this.searchInfo(byPlusRecordComparison);
        return byPlusRecordComparison;
    }

    @Transactional
    @Override
    public void removeByIds(String ids) {
        String[] idArr = ids.split(",");
        Arrays.asList(idArr).forEach(id -> {
            this.remove(Integer.valueOf(id));
        });
    }

    @Transactional
    @Override
    public void upperSave(ByPlusRecordComparison byPlusRecordComparison) {
        this.save(byPlusRecordComparison);
    }

    /**
     * 修改资料提交状态
     *
     * @param ids
     */
    @Transactional
    @Override
    public void updateExistFlagByIds(String ids) {
        String[] idArr = ids.split(",");
        Arrays.asList(idArr).forEach(id -> {
            Integer pid = Integer.valueOf(id);
            byPlusRecordComparisonMapper.updateExistFlag(pid);
        });
    }

    /**
     * 1.先删除之前的资料模板提交记录
     * 2.分别添加已提交的资料模板id和未提交的资料模板id
     *
     * @param byPlusRecordComparison
     * @param trueRecordIds
     * @param falseRecordIds
     */
    @Transactional
    @Override
    public void upperMoreSave(ByPlusRecordComparison byPlusRecordComparison, String trueRecordIds, String falseRecordIds) {
        Integer projectId = byPlusRecordComparison.getProjectId();
        byPlusRecordComparisonMapper.removeByProjectId(projectId);
        String[] trueIds = trueRecordIds.split(",");
        Arrays.asList(trueIds).forEach(trueId -> {
            Integer pid = Integer.valueOf(trueId);
            byPlusRecordComparison.setId(null);
            byPlusRecordComparison.setExistFlag(1);
            byPlusRecordComparison.setRecordModelId(pid);
            this.save(byPlusRecordComparison);
        });
        String[] falseIds = falseRecordIds.split(",");
        Arrays.asList(falseIds).forEach(falseId -> {
            Integer sid = Integer.valueOf(falseId);
            byPlusRecordComparison.setId(null);
            byPlusRecordComparison.setExistFlag(0);
            byPlusRecordComparison.setRecordModelId(sid);
            this.save(byPlusRecordComparison);
        });
    }


    /**
     * 查询档案资料模板详情
     *
     * @param byPlusRecordComparison
     */
    private void searchInfo(ByPlusRecordComparison byPlusRecordComparison) {
        Integer recordModelId = byPlusRecordComparison.getRecordModelId();
        if (recordModelId != null) {
            String recordModel = byPlusRecordModelManager.get(recordModelId).getCatalogContent();
            byPlusRecordComparison.setRecordModel(recordModel);
        } else {
            byPlusRecordComparison.setRecordModel("");
        }
        Integer projectId = byPlusRecordComparison.getProjectId();
        if (projectId != null) {
            String projectName = byPlusProjectMapper.searchProjectNameById(projectId);
            byPlusRecordComparison.setProjectName(projectName);
        } else {
            byPlusRecordComparison.setProjectName("");
        }
    }
}