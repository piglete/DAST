package club.emergency.project.manager.impl;

import club.emergency.by_b_department.mapper.ByBDepartmentMapper;
import club.emergency.by_b_region.mapper.ByBRegionMapper;
import club.emergency.dictionary.manager.ByBDictionaryChildManager;
import club.emergency.dictionary.model.ByBDictionaryChild;
import club.emergency.project.manager.VProjectTaskManager;
import club.emergency.project.manager.VProjectTotalOutputManager;
import club.emergency.project.mapper.VProjectTaskMapper;
import club.emergency.project.model.VProjectTask;
import club.emergency.project.model.VProjectTotalOutput;
import club.map.base.util.ExcelData;
import club.map.base.util.ExportExcelUtils;
import club.map.core.Constants;
import club.map.core.manager.impl.GenericManagerImpl;
import com.wanqing.util.StringHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class VProjectTaskManagerImpl extends GenericManagerImpl<VProjectTask, Integer> implements VProjectTaskManager {

    @Autowired
    private VProjectTaskMapper vProjectTaskMapper;
    @Autowired
    private ByBDepartmentMapper byBDepartmentMapper;
    @Autowired
    private VProjectTotalOutputManager vProjectTotalOutputManager;
    @Autowired
    private ByBDictionaryChildManager byBDictionaryChildManager;
    @Autowired
    private ByBRegionMapper byBRegionMapper;

    @Autowired
    public VProjectTaskManagerImpl(VProjectTaskMapper mapper) {
        super(mapper, VProjectTask.class);
    }

    @Override
    public List<VProjectTask> search(VProjectTask vProjectTask, Integer year, Integer month) {
        List<VProjectTask> vProjectTaskList= vProjectTaskMapper.search(vProjectTask,year,month);
        for (VProjectTask vProjectTask1:vProjectTaskList) {
            this.searchInfo(vProjectTask1);
        }
        return vProjectTaskList;
    }

    @Override
    public void outPutExport(VProjectTask vProjectTask, Integer year, Integer month, HttpServletResponse response) {
        ExcelData excelData = new ExcelData();
        //根据条件查询结果后,导出数据
        List<VProjectTask> vProjectTaskList= vProjectTaskMapper.search(vProjectTask,year,month);
        //表名设置
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm:ss");
        String localTime = dtf.format(localDateTime).replace("-", "");
        String str = df.format(localDateTime).replace(":", "");
        String name = localTime + str + "项目产值信息表";
        //设置excel标题
        List<String> titles = new ArrayList<>();
        //项目表信息
        titles.add("单位名称");
        titles.add("项目名称");
        titles.add("项目地址");
        titles.add("部门名称");
        titles.add("作业小组");
        titles.add("工作量");
        titles.add("质量分");
        titles.add("内部产值");
        excelData.setTitles(titles);
        //填充数据
        List<List<Object>> rows = new ArrayList();
        for (VProjectTask vProjectTask1 : vProjectTaskList) {
            this.searchInfo(vProjectTask1);
            List<Object> row = new ArrayList();
            //项目表信息
            row.add(vProjectTask1.getUnitName());
            row.add(vProjectTask1.getName());
            row.add(vProjectTask1.getAddress());
            row.add(vProjectTask1.getDepartmentName());
            row.add(vProjectTask1.getWorkGroupName());
            row.add(vProjectTask1.getWorkloadInfo());
            row.add(vProjectTask1.getTaskScore());
            row.add(Constants.decimalFormat.format(vProjectTask1.getInternalOutput()));
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
     * 查询详情
     *
     * @param vProjectTask
     */
    private void searchInfo(VProjectTask vProjectTask) {
        //获取项目类型
        Integer recordTypeId = vProjectTask.getRecordTypeId();
        if (recordTypeId != null) {
            ByBDictionaryChild byBDictionaryChild = byBDictionaryChildManager.get(recordTypeId);
            vProjectTask.setRecordTypeName(byBDictionaryChild.getAlias());
        } else {
            vProjectTask.setRecordTypeName("");
        }

        //部门名称
        String departmentCode = vProjectTask.getDepartmentCode();
        if (StringHandler.isNotEmptyOrNull(departmentCode)) {
            String departmentName = byBDepartmentMapper.getByCode(departmentCode).getDepartmentName();
            vProjectTask.setDepartmentName(departmentName);
        } else {
            vProjectTask.setDepartmentName("");
        }
        //小组名称
        String workGroupCode = vProjectTask.getWorkGroupCode();
        if (StringHandler.isNotEmptyOrNull(workGroupCode)) {
            String workGroupName = byBDepartmentMapper.getByCode(workGroupCode).getDepartmentName();
            vProjectTask.setWorkGroupName(workGroupName);
        } else {
            vProjectTask.setWorkGroupName("");
        }
        //计算内外部产值
        Integer id=vProjectTask.getId();
        BigDecimal internalTotal =new BigDecimal("0");
        StringBuilder workloadStr= new StringBuilder(vProjectTask.getRecordTypeName()+"(");
        if (vProjectTask.getInternalOutput() == null) {
            List<VProjectTotalOutput> vProjectTotalOutputList = vProjectTotalOutputManager.searchByProjectId(id);
            for (VProjectTotalOutput vProjectTotalOutput:vProjectTotalOutputList) {
                //工作量字符串拼接
                workloadStr.append(vProjectTotalOutput.getItemApplicationName()+":"+Constants.decimalFormat.format(vProjectTotalOutput.getWorkCount())+vProjectTotalOutput.getUnitTypeName()+",");
                internalTotal=internalTotal.add(vProjectTotalOutput.getInternalWorkOutput());
            }
            vProjectTask.setInternalOutput((internalTotal==null)?new BigDecimal("0"):new BigDecimal("0"));
            vProjectTask.setWorkloadInfo(workloadStr.toString().substring(0,workloadStr.toString().length()-1)+")");
        }


    }
}