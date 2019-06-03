package club.emergency.project.manager.impl;

import club.emergency.by_b_department.manager.ByBDepartmentManager;
import club.emergency.by_b_department.mapper.ByBDepartmentMapper;
import club.emergency.by_b_department.model.ByBDepartment;
import club.emergency.by_plus_unit_config.manager.ByPlusUnitConfigManager;
import club.emergency.by_plus_unit_config.model.ByPlusUnitConfig;
import club.emergency.dictionary.manager.ByBDictionaryChildManager;
import club.emergency.dictionary.model.ByBDictionaryChild;
import club.emergency.project.manager.ByPlusTaskWorkloadManager;
import club.emergency.project.mapper.ByPlusTaskWorkloadMapper;
import club.emergency.project.model.ByPlusTaskWorkload;
import club.map.base.util.ExcelData;
import club.map.base.util.ExportExcelUtils;
import club.map.core.manager.impl.GenericManagerImpl;
import club.map.core.model.FlipFilter;
import club.map.core.model.Operate;
import com.wanqing.util.StringHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ByPlusTaskWorkloadManagerImpl extends GenericManagerImpl<ByPlusTaskWorkload, Integer> implements ByPlusTaskWorkloadManager {

    private ByBDictionaryChildManager byBDictionaryChildManager;
    private ByBDepartmentMapper byBDepartmentMapper;
    private ByBDepartmentManager byBDepartmentManager;
    private ByPlusTaskWorkloadMapper byPlusTaskWorkloadMapper;
    protected final Log log = LogFactory.getLog(getClass());
    @Autowired
    private ByPlusUnitConfigManager byPlusUnitConfigManager;
    @Autowired
    public ByPlusTaskWorkloadManagerImpl(ByPlusTaskWorkloadMapper mapper,
                                         ByBDictionaryChildManager byBDictionaryChildManager,
                                         ByBDepartmentMapper byBDepartmentMapper,
                                         ByBDepartmentManager byBDepartmentManager,
                                         ByPlusTaskWorkloadMapper byPlusTaskWorkloadMapper
    ) {
        super(mapper, ByPlusTaskWorkload.class);
        this.byBDictionaryChildManager = byBDictionaryChildManager;
        this.byBDepartmentMapper = byBDepartmentMapper;
        this.byBDepartmentManager = byBDepartmentManager;
        this.byPlusTaskWorkloadMapper = byPlusTaskWorkloadMapper;
    }

    @Override
    public List<ByPlusTaskWorkload> search(Integer taskId, Integer unitTypeId, Integer itemApplicationId, String departmentCode, Integer flag) {
        FlipFilter flipFilter = new FlipFilter(ByPlusTaskWorkload.class);
        flipFilter.addSearch(taskId, Operate.EQUAL, "taskId");
        flipFilter.addSearch(unitTypeId, Operate.EQUAL, "unitTypeId");
        flipFilter.addSearch(itemApplicationId, Operate.EQUAL, "itemApplicationId");
        flipFilter.addSearch(departmentCode + "%", Operate.LIKE, "departmentCode");
        flipFilter.addSearch(flag, Operate.EQUAL, "flag");
        List<ByPlusTaskWorkload> byPlusTaskWorkloadList = this.list(flipFilter);
        for (ByPlusTaskWorkload byPlusTaskWorkload : byPlusTaskWorkloadList) {
            this.searchInfo(byPlusTaskWorkload);
        }
        return byPlusTaskWorkloadList;
    }

    @Transactional
    @Override
    public void upperSave(ByPlusTaskWorkload byPlusTaskWorkload,BigDecimal internalVal) {
        try{
            //如果临时单价无值，则去配置表里查找单价计算产值
            if (byPlusTaskWorkload.getTemp_internal_val() == null) {
//                ByPlusUnitConfig byPlusUnitConfig = byPlusUnitConfigManager.search(byPlusTaskWorkload.getItemApplicationId());
                byPlusTaskWorkload.setInternalWorkOutput(internalVal.multiply(byPlusTaskWorkload.getWorkCount()));
            }else{
                byPlusTaskWorkload.setInternalWorkOutput(byPlusTaskWorkload.getTemp_internal_val().multiply(byPlusTaskWorkload.getWorkCount()));
            }
            this.update(byPlusTaskWorkload);
        }catch (Exception e){
            log.error("更新操作有误",e);
        }

    }

    @Override
    public ByPlusTaskWorkload searchById(Integer id) {
        ByPlusTaskWorkload byPlusTaskWorkload = this.get(id);
        this.searchInfo(byPlusTaskWorkload);
        return byPlusTaskWorkload;
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
     * 小组工作量统计
     * 1.按不同条件统计
     *
     * @param taskId
     * @param departmentCode
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public Map<String, Object> searchGroupWorkGroup(Integer taskId, String departmentCode, String startDate, String endDate) {
        Map<String, Object> objectMap = new HashMap<>();
        Map<String, BigDecimal> map = new HashMap<>();
        FlipFilter flipFilter = new FlipFilter(ByPlusTaskWorkload.class);
        flipFilter.addSearch(taskId, Operate.EQUAL, "taskId");
        if (StringHandler.isNotEmptyOrNull(startDate) && StringHandler.isNotEmptyOrNull(endDate)) {
            flipFilter.addRegion("createTime", startDate, endDate);
        }
        flipFilter.addSearch(departmentCode + "%", Operate.LIKE, "departmentCode");
        List<ByPlusTaskWorkload> byPlusTaskWorkloadList = this.list(flipFilter);
        map = this.searchByTaskId(byPlusTaskWorkloadList);
        objectMap.put(departmentCode, map);
        return objectMap;
    }

    /**
     * 工作量导出设置
     *
     * @param taskId
     * @param departmentCode
     * @param groupCode
     * @param oneInspectionUserId
     * @param twoInspectionUserId
     * @param threeInspectionUserId
     * @param itemApplicationId
     * @param flag
     * @param startDate
     * @param endDate
     * @param response
     */
    @Override
    public void taskWorkloadExport(Integer taskId, String departmentCode, String groupCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId, Integer itemApplicationId, Integer flag, String startDate, String endDate, HttpServletResponse response) {
        List<ByPlusTaskWorkload> byPlusTaskWorkloadList = byPlusTaskWorkloadMapper.searchTaskWorkload(taskId, departmentCode, groupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId, itemApplicationId, flag, startDate, endDate);
        ExcelData excelData = new ExcelData();
        //表名设置
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm:ss");
        String localTime = dtf.format(localDateTime).replace("-", "");
        String str = df.format(localDateTime).replace(":", "");
        String name = localTime + str + "工作量信息表";
        //设置excel标题
        List<String> titles = new ArrayList<>();
        //项目表信息
        titles.add("任务名称");
        titles.add("作业内容");
        titles.add("数量");
        titles.add("单位");
        titles.add("部门(小组)名称");
        titles.add("工作量类型");
        titles.add("备注");
        titles.add("操作时间");
        excelData.setTitles(titles);
        List<List<Object>> rows = new ArrayList();
        for (ByPlusTaskWorkload byPlusTaskWorkload : byPlusTaskWorkloadList) {
            this.searchInfo(byPlusTaskWorkload);
            this.searchTaskName(byPlusTaskWorkload);
            List<Object> row = new ArrayList();
            row.add(byPlusTaskWorkload.getTaskName());
            row.add(byPlusTaskWorkload.getItemApplication());
            row.add(byPlusTaskWorkload.getWorkCount());
            row.add(byPlusTaskWorkload.getUnitType());
            row.add(byPlusTaskWorkload.getDepartmentName());
            row.add(this.getWorkFlag(byPlusTaskWorkload.getFlag()));
            row.add(byPlusTaskWorkload.getRemark());
            row.add(this.dateFormat(byPlusTaskWorkload.getCreateTime()));
            rows.add(row);
        }
        excelData.setRows(rows);
        try {
            ExportExcelUtils.exportExcel(response, name, excelData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 日期时间格式转换
     *
     * @param createTime
     * @return
     */
    private Object dateFormat(Date createTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String localDateTime = simpleDateFormat.format(createTime);
        return localDateTime;
    }

    /**
     * 任务表标识转换
     *
     * @param flag
     * @return
     */
    private Object getWorkFlag(Integer flag) {
        String str = "";
        if (flag == 0) {
            str = "部门任务";
        } else if (flag == 1) {
            str = "内协任务";
        }
        return str;
    }

    /**
     * 查询任务名称
     *
     * @param byPlusTaskWorkload
     */
    private void searchTaskName(ByPlusTaskWorkload byPlusTaskWorkload) {
        Integer taskId = byPlusTaskWorkload.getTaskId();
        if (taskId != null) {
            String taskName = byPlusTaskWorkloadMapper.searchTaskName(taskId);
            byPlusTaskWorkload.setTaskName(taskName);
        } else {
            byPlusTaskWorkload.setTaskName("");
        }
    }


    /**
     * 查询不同部门下的工作量
     *
     * @param flipFilter
     * @param s
     * @return
     */
    private List<ByPlusTaskWorkload> searchList(FlipFilter flipFilter, String s) {
        flipFilter.addSearch(s, Operate.EQUAL, "departmentCode");
        List<ByPlusTaskWorkload> byPlusTaskWorkloadList = this.list(flipFilter);
        return byPlusTaskWorkloadList;
    }

    /**
     * @param byPlusTaskWorkloadList
     * @return
     */
    private Map<String, BigDecimal> searchByTaskId(List<ByPlusTaskWorkload> byPlusTaskWorkloadList) {
        Map<String, String> applicationTypeMap = this.getItemApplicationId();
        Map<String, BigDecimal> applicationCountMap = new HashMap<String, BigDecimal>();
        for (ByPlusTaskWorkload byPlusTaskWorkload : byPlusTaskWorkloadList) {
            String applicationId = String.valueOf(byPlusTaskWorkload.getItemApplicationId());
            BigDecimal workCount = byPlusTaskWorkload.getWorkCount();
            String applicationName = applicationTypeMap.get(applicationId);
            if (applicationCountMap.containsKey(applicationName)) {
                BigDecimal count = applicationCountMap.get(applicationName);
                BigDecimal addCount = count.add(workCount);
                applicationCountMap.put(applicationName, addCount);
            } else {
                applicationCountMap.put(applicationName, workCount);
            }
        }
        //其他未查到的类型的工作量和初始默认为0
        for (String s : applicationTypeMap.values()) {
            if (!applicationCountMap.containsKey(s)) {
                applicationCountMap.put(s, new BigDecimal(0));
            }
        }
        return applicationCountMap;
    }

    /**
     * 工作量申报类型集合
     *
     * @return
     */
    private Map<String, String> getItemApplicationId() {
        //工作量申报parentCode=100107107
        String parentCode = "100107107";
        Map<String, String> map = new HashMap<String, String>();
        List<ByBDictionaryChild> byBDictionaryChildList = byBDictionaryChildManager.searchByDictionaryCode(parentCode);
        for (ByBDictionaryChild byBDictionaryChild : byBDictionaryChildList) {
            String applicationId = String.valueOf(byBDictionaryChild.getId());
            String applicationName = byBDictionaryChild.getAlias();
            map.put(applicationId, applicationName);
        }
        return map;
    }

    /**
     * 查询小组
     *
     * @param
     */
    private List<String> groupType() {
        //查询可以下达的部门下的小组的工作量
        List<String> list = new ArrayList<>();
        Integer release = 1;
        Integer invalidFlag = 0;
        List<ByBDepartment> byBDepartmentList = byBDepartmentManager.searchForAllGroup(release, invalidFlag);
        for (ByBDepartment byBDepartment : byBDepartmentList) {
            list.add(byBDepartment.getCode());
        }
        return list;
    }


    /**
     * 查询详情 modified by lxl 190522
     *
     * @param byPlusTaskWorkload
     */
    private void searchInfo(ByPlusTaskWorkload byPlusTaskWorkload) {
        //获取单位
        Integer unitTypeId = byPlusTaskWorkload.getUnitTypeId();
        if (unitTypeId != null) {
            String unitType = byBDictionaryChildManager.get(unitTypeId).getAlias();
            byPlusTaskWorkload.setUnitType(unitType);
        } else {
            byPlusTaskWorkload.setUnitType("");
        }
        //获取项目申报
        Integer itemApplicationId = byPlusTaskWorkload.getItemApplicationId();
        if (itemApplicationId != null) {
            String itemApplication = byBDictionaryChildManager.get(itemApplicationId).getAlias();
            byPlusTaskWorkload.setItemApplication(itemApplication);
        } else {
            byPlusTaskWorkload.setItemApplication("");
        }

        //获取部门
        String departmentCode = byPlusTaskWorkload.getDepartmentCode();
        if (StringHandler.isNotEmptyOrNull(departmentCode)) {
            String departmentName = byBDepartmentMapper.searchNameByCode(departmentCode);
            byPlusTaskWorkload.setDepartmentName(departmentName);
        } else {
            byPlusTaskWorkload.setDepartmentName("");
        }

        //根据任务编号获取项目类型配置列表
        Integer taskId = byPlusTaskWorkload.getTaskId();
//        if (taskId != null) {
//            List<ByPlusUnitConfig> byPlusUnitConfigList = byPlusUnitConfigManager.searchByTaskId(taskId);
//            byPlusTaskWorkload.setRecordTypeName("");
//        } else {
//            byPlusTaskWorkload.setDepartmentName("");
//        }
        //如果内部产值为空，则按照默认规则计算产值
        if (byPlusTaskWorkload.getInternalWorkOutput() == null) {
            //如果项目类型编号与单位id都不为空，则查询产值配置表计算工作量产值
            if (itemApplicationId != null && unitTypeId != null) {
                ByPlusUnitConfig byPlusUnitConfig = byPlusUnitConfigManager.search(taskId,itemApplicationId,unitTypeId);
                if (byPlusUnitConfig != null) {
                    Integer recordTypeId = byPlusUnitConfig.getRecordTypeId();
                    if (recordTypeId!=null) {
                        String recordTypeName = byBDictionaryChildManager.get(recordTypeId).getAlias();
                        byPlusTaskWorkload.setRecordTypeName(recordTypeName);
                    } else {
                        byPlusTaskWorkload.setRecordTypeName("");
                    }
                    //按照表内规则计算内部产值
                    byPlusTaskWorkload.setInternalWorkOutput(byPlusUnitConfig.getInternalVal().multiply(byPlusTaskWorkload.getWorkCount()));
                }else{
                    byPlusTaskWorkload.setInternalWorkOutput(new BigDecimal("0"));
                }
            }
        }

    }
}