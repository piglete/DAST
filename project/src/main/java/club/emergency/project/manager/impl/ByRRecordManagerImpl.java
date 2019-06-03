package club.emergency.project.manager.impl;

import club.emergency.by_b_department.mapper.ByBDepartmentMapper;
import club.emergency.by_b_region.mapper.ByBRegionMapper;
import club.emergency.dictionary.manager.ByBDictionaryChildManager;
import club.emergency.project.manager.ByPlusProjectManager;
import club.emergency.project.manager.ByRRecordManager;
import club.emergency.project.mapper.ByRRecordMapper;
import club.emergency.project.model.ByPlusProject;
import club.emergency.project.model.ByRRecord;
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
public class ByRRecordManagerImpl extends GenericManagerImpl<ByRRecord, Integer> implements ByRRecordManager {

    private ByPlusProjectManager byPlusProjectManager;
    private ByBDepartmentMapper byBDepartmentMapper;
    private ByBDictionaryChildManager byBDictionaryChildManager;
    private ByBRegionMapper byBRegionMapper;

    @Autowired
    public ByRRecordManagerImpl(ByRRecordMapper mapper,
                                ByPlusProjectManager byPlusProjectManager,
                                ByBDictionaryChildManager byBDictionaryChildManager,
                                ByBDepartmentMapper byBDepartmentMapper,
                                ByBRegionMapper byBRegionMapper
    ) {
        super(mapper, ByRRecord.class);
        this.byPlusProjectManager = byPlusProjectManager;
        this.byBDepartmentMapper = byBDepartmentMapper;
        this.byBDictionaryChildManager = byBDictionaryChildManager;
        this.byBRegionMapper = byBRegionMapper;
    }

    /**
     * 多条件分页查询
     *
     * @param isStoreroom
     * @param fileNumber
     * @param companyName
     * @param projectName
     * @param recordTypeId
     * @param fileDate
     * @param departmentCode
     * @param retentionPeriodId
     * @param regionCode
     * @param yearType
     * @param monthType
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Page search(Integer isStoreroom, String fileNumber, String companyName, String projectName, Integer recordTypeId, String fileDate, String departmentCode, Integer retentionPeriodId, String regionCode, Integer yearType, Integer monthType, Integer pageNo, Integer pageSize) {
        FlipFilter flipFilter = new FlipFilter(ByRRecord.class);
        flipFilter.addSearch(isStoreroom, Operate.EQUAL, "isStoreroom");
        flipFilter.addSearch("%" + fileNumber + "%", Operate.LIKE, "fileNumber");
        flipFilter.addSearch("%" + companyName + "%", Operate.LIKE, "companyName");
        flipFilter.addSearch("%" + projectName + "%", Operate.LIKE, "projectName");
        flipFilter.addSearch(recordTypeId, Operate.EQUAL, "recordTypeId");
        flipFilter.addSearch(fileDate, Operate.EQUAL, "fileDate");
        flipFilter.addSearch(departmentCode, Operate.EQUAL, "departmentCode");
        flipFilter.addSearch(retentionPeriodId, Operate.EQUAL, "retentionPeriodId");
        flipFilter.addSearch(regionCode + "%", Operate.LIKE, "regionCode");
        flipFilter.addSearch(yearType, Operate.EQUAL, "yearType");
        flipFilter.addSearch(monthType, Operate.EQUAL, "monthType");
        flipFilter.updatePageNo(pageNo);
        flipFilter.setPageSize(pageSize);
        Page page = this.flipUsingInPage(flipFilter);
        List<ByRRecord> byRRecordList = page.getListInfo();
        for (ByRRecord byRRecord : byRRecordList) {
            this.searchInfo(byRRecord);
        }
        return page;
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
     * 档案编辑(新增和修改),对文件日期处理,按年份和月份存储
     *
     * @param byRRecord
     */
    @Transactional
    @Override
    public void upperSave(ByRRecord byRRecord) {
        String fileDate = byRRecord.getFileDate();
        String[] str = fileDate.split("-");
        Integer yearType = Integer.valueOf(str[0]);
        //月份如果小于10的话,去除01前面的0
        String month = str[1];
        if (month.contains("0")) {
            month = month.substring(1);
        }
        Integer monthType = Integer.valueOf(month);
        Integer projectId = byRRecord.getProjectId();
        byRRecord.setYearType(yearType);
        byRRecord.setMonthType(monthType);
        if (byRRecord.getId() == null) {
            ByPlusProject byPlusProject = byPlusProjectManager.get(projectId);
            String regionCode = byPlusProject.getRegionCode();
            String regionName = byBRegionMapper.searchByCode(regionCode).getFullName();
            byRRecord.setRegionCode(regionCode);
            byRRecord.setRegionName(regionName);
            Integer recordTypeId = byPlusProject.getRecordTypeId();
            String recordTypeName = byBDictionaryChildManager.get(recordTypeId).getAlias();
            byRRecord.setRecordTypeId(recordTypeId);
            byRRecord.setRecordTypeName(recordTypeName);
        }
        this.save(byRRecord);
    }

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    @Override
    public ByRRecord searchDetails(Integer id) {
        ByRRecord byRRecord = this.get(id);
        this.searchInfo(byRRecord);
        return byRRecord;
    }

    /**
     * 条件查询,
     *
     * @param isStoreroom 入库标识字段(档案是否入档案库标识)
     * @return
     */
    @Override
    public List<ByRRecord> searchWithoutPage(Integer isStoreroom) {
        FlipFilter flipFilter = new FlipFilter(ByRRecord.class);
        flipFilter.addSearch(isStoreroom, Operate.EQUAL, "isStoreroom");
        List<ByRRecord> byRRecordList = this.list(flipFilter);
        for (ByRRecord byRRecord : byRRecordList) {
            this.searchInfo(byRRecord);
        }
        return byRRecordList;
    }

    /**
     * 依赖注入,查询对应关联id的名称信息
     *
     * @param byRRecord
     * @return
     */
    private ByRRecord searchInfo(ByRRecord byRRecord) {
        //获取部门名称
        String departmentCode = byRRecord.getDepartmentCode();
        if (StringHandler.isNotEmptyOrNull(departmentCode)) {
            byRRecord.setDepartmentName(byBDepartmentMapper.getByCode(departmentCode).getDepartmentName());
        } else {
            byRRecord.setDepartmentName("");
        }
        //获取密级
        Integer rankTypeId = byRRecord.getRankTypeId();
        if (rankTypeId != null) {
            byRRecord.setRankType(byBDictionaryChildManager.get(rankTypeId).getAlias());
        } else {
            byRRecord.setRankType("");
        }
        //获取保存年限
        Integer retentionPeriodId = byRRecord.getRetentionPeriodId();
        if (retentionPeriodId != null) {
            byRRecord.setRetentionPeriod(byBDictionaryChildManager.get(retentionPeriodId).getAlias());
        } else {
            byRRecord.setRetentionPeriod("");
        }
        return byRRecord;
    }
}