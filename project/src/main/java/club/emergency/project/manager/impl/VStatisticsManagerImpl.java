package club.emergency.project.manager.impl;

import club.emergency.project.manager.VProjectTaskManager;
import club.emergency.project.manager.VStatisticsManager;
import club.emergency.project.mapper.VProjectTotalOutputMapper;
import club.emergency.project.mapper.VStatisticsMapper;
import club.emergency.project.model.VProjectOutput;
import club.emergency.project.model.VProjectTask;
import club.emergency.project.model.VStatistics;
import club.map.core.Constants;
import club.map.core.manager.impl.GenericManagerImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VStatisticsManagerImpl extends GenericManagerImpl<VStatistics, Integer> implements VStatisticsManager {

    @Autowired
    private VStatisticsMapper vStatisticsMapper;
    @Autowired
    private VProjectTaskManager vProjectTaskManager;
    @Autowired
    private VProjectTotalOutputMapper vProjectTotalOutputMapper;
    @Autowired
    public VStatisticsManagerImpl(VStatisticsMapper mapper) {
        super(mapper, VStatistics.class);
    }

    @Override
    public Map<String, Object> taskAndScore(Integer pageNo, Integer pageSize, Integer year, Integer month, String regionCode, Integer recordTypeId, String departmentCode, String workGroupCode,String taskType) {
        Map<String, Object> objectMap =new HashMap<>();
        objectMap.put("remark","total:总任务; " +
                "completed:已完成; " +
                "underway:进行中; " +
                "unstart:未启动; " +
                "pause:暂停; " +
                "overdue:超期; " +
                "output: 内部产值; " +
                "g90: 大于95; " +
                "l60: 小于60; " +
                "taskList: 项目任务列表");
        Object taskCount =vStatisticsMapper.taskCount("total",year, month, regionCode, recordTypeId, departmentCode, workGroupCode);
        objectMap.put("total",taskCount);
        Object finishCount =vStatisticsMapper.taskCount("completed",year, month, regionCode, recordTypeId, departmentCode, workGroupCode);
        objectMap.put("completed",finishCount);
        Object underwayCount =vStatisticsMapper.taskCount("underway",year, month, regionCode, recordTypeId, departmentCode, workGroupCode);
        objectMap.put("underway",underwayCount);
        Object unstartCount =vStatisticsMapper.taskCount("unstart",year, month, regionCode, recordTypeId, departmentCode, workGroupCode);
        objectMap.put("unstart",unstartCount);
        Object pauseCount =vStatisticsMapper.taskCount("pause",year, month, regionCode, recordTypeId, departmentCode, workGroupCode);
        objectMap.put("pause",pauseCount);
        Object outtimeCount =vStatisticsMapper.taskCount("overdue",year, month, regionCode, recordTypeId, departmentCode, workGroupCode);
        objectMap.put("overdue",outtimeCount);
        Object outPutValue =vStatisticsMapper.taskCount("output",year, month, regionCode, recordTypeId, departmentCode, workGroupCode);
        objectMap.put("output",(outPutValue==null)?0:0);
        Object g95Count =vStatisticsMapper.taskCount("g95",year, month, regionCode, recordTypeId, departmentCode, workGroupCode);
        objectMap.put("g95",g95Count);
        Object l60Count =vStatisticsMapper.taskCount("l60",year, month, regionCode, recordTypeId, departmentCode, workGroupCode);
        objectMap.put("l60",l60Count);
        if (pageSize != null) {
            PageHelper.startPage(pageNo,pageSize);
        }
        List<Map<String,Object>> taskList =vStatisticsMapper.taskInfoWithWorkloads(year, month, regionCode, recordTypeId, departmentCode, workGroupCode,taskType);
        for (Map<String,Object> map: taskList) {
            this.searchInfo(map);
        }
        PageInfo page= new PageInfo<>(taskList);
        objectMap.put("taskList",page);

        return objectMap;
    }

    private void searchInfo(Map<String,Object> map) {
        StringBuilder workloadStr= new StringBuilder(map.get("recordTypeName")+"(");
        List<VProjectOutput> vProjectOutputs=vProjectTotalOutputMapper.getByProjectId(Integer.parseInt(map.get("id").toString()));
        for (VProjectOutput vProjectOutput: vProjectOutputs) {
            workloadStr.append(vProjectOutput.getItemApplicationName()+":"+Constants.decimalFormat.format(vProjectOutput.getWorkCount())+vProjectOutput.getUnitTypeName()+",");
        }
        map.put("workloadInfo",workloadStr.toString().substring(0,workloadStr.toString().length()-1)+")");
    }

    @Override
    public List<VProjectTask> search(VProjectTask vProjectTask, Integer year, Integer month) {
        return vProjectTaskManager.search(vProjectTask,year,month);
    }

    @Override
    public void outPutExport(VProjectTask vProjectTask, Integer year, Integer month, HttpServletResponse response) {
        vProjectTaskManager.outPutExport(vProjectTask,year,month,response);
    }

    @Override
    public List<Map<String,Object>> groupByParamStatistic(String groupByParam, Integer year, Integer month, String regionCode, Integer recordTypeId) {
        return vStatisticsMapper.taskGroupByParam(groupByParam, year, month, regionCode, recordTypeId);
    }

    @Override
    public Map<String, Object> departmentStatistic(Integer year, Integer month, String regionCode, Integer recordTypeId, String departmentCode, String workGroupCode) {
        return null;
    }
}