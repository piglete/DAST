package club.emergency.controller;

import club.emergency.project.manager.VStatisticsManager;
import club.emergency.project.model.VProjectTask;
import club.map.core.web.util.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wanqing.util.StringHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Description: 统计信息管理
 * @Author: lxl
 * @CreateDate: 2019/05/29
 * @Version: v1.0
 */
@Api("统计分析管理")
@CrossOrigin
@RestController
@RequestMapping("/statistic")
public class StatisticController {

    @Autowired
    private VStatisticsManager vStatisticsManager;
    @ApiOperation("当月统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "pageNo", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "year", value = "年份", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "month", value = "月份", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "regionCode", value = "区域code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "recordTypeId", value = "项目类型id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "部门code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "workGroupCode", value = "小组code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "taskState", value = "任务状态，参数对应字典(\"total:总任务 \" \n" +
                    "                \"completed:已完成 \" \n" +
                    "                \"underway:进行中 \" \n" +
                    "                \"unstart:未启动 \" \n" +
                    "                \"pause:暂停 \" \n" +
                    "                \"overdue:超期 \" \n" +
                    "                \"g90: 大于95 \" \n" +
                    "                \"l60: 小于60 \" )", dataType = "string", paramType = "query")
    })
    @PostMapping("/taskAndScore")
    public Result flipList(
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "") Integer pageSize,
            @RequestParam(value = "year", defaultValue = "") Integer year,
            @RequestParam(value = "month", defaultValue = "") Integer month,
            @RequestParam(value = "regionCode", defaultValue = "") String regionCode,
            @RequestParam(value = "recordTypeId", defaultValue = "") Integer recordTypeId,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode,
            @RequestParam(value = "workGroupCode", defaultValue = "") String workGroupCode,
            @RequestParam(value = "taskState", defaultValue = "") String taskState
    )  {
        Map<String,Object> flipList=vStatisticsManager.taskAndScore(pageNo,pageSize,year,month,regionCode,recordTypeId,departmentCode,workGroupCode,taskState);
        return Result.ok(flipList);
    }
    @ApiOperation("查询产值信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "unitName", value = "单位名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "recordTypeId", value = "项目类型id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "regionCode", value = "区域code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "部门code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "workGroupCode", value = "作业小组code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "year", value = "年份（例：2019）", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "month", value = "月份（例：03或3）", dataType = "int", paramType = "query")

    })
    @PostMapping("/flipListForProjectOutPutWithPage")
    public Result flipListForProjectOutPut(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "unitName", defaultValue = "") String unitName,
            @RequestParam(value = "recordTypeId", required = false) Integer recordTypeId,
            @RequestParam(value = "regionCode", defaultValue = "") String regionCode,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode,
            @RequestParam(value = "workGroupCode", defaultValue = "") String workGroupCode,
            @RequestParam(value = "year", defaultValue = "") Integer year,
            @RequestParam(value = "month", defaultValue = "") Integer month

    ) {
        VProjectTask vProjectTask=new VProjectTask();
        if (recordTypeId != null) {
            vProjectTask.setRecordTypeId(recordTypeId);
        }
        if (!StringHandler.isEmptyOrNull(unitName)) {
            vProjectTask.setUnitName(unitName);
        }
        if (!StringHandler.isEmptyOrNull(regionCode)) {
            vProjectTask.setRegionCode(regionCode);
        }
        if (!StringHandler.isEmptyOrNull(departmentCode)) {
            vProjectTask.setDepartmentCode(departmentCode);
        }
        if (!StringHandler.isEmptyOrNull(workGroupCode)) {
            vProjectTask.setWorkGroupCode(workGroupCode);
        }

        if (pageSize != null) {
            PageHelper.startPage(pageNo,pageSize);
        }
        List<VProjectTask> docs = vStatisticsManager.search(vProjectTask, year, month);
        PageInfo page= new PageInfo<>(docs);
        return Result.ok(page);
    }
    @ApiOperation("任务管理 - 导出项目产值信息(excel)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "unitName", value = "单位名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "部门code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "workGroupCode", value = "作业小组code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "year", value = "年份（例：2019）", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "month", value = "月份（例：03或3）", dataType = "int", paramType = "query")
    })
    @GetMapping("/outPutExport")
    public Result taskExport(
            @RequestParam(value = "unitName", defaultValue = "") String unitName,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode,
            @RequestParam(value = "workGroupCode", defaultValue = "") String workGroupCode,
            @RequestParam(value = "year", defaultValue = "") Integer year,
            @RequestParam(value = "month", defaultValue = "") Integer month,
            HttpServletResponse response
    ) {
        VProjectTask vProjectTask=new VProjectTask();
        if (StringHandler.isEmptyOrNull(unitName)) {
            vProjectTask.setUnitName(unitName);
        }
        if (StringHandler.isEmptyOrNull(departmentCode)) {
            vProjectTask.setDepartmentCode(departmentCode);
        }
        if (StringHandler.isEmptyOrNull(workGroupCode)) {
            vProjectTask.setWorkGroupCode(workGroupCode);
        }

        vStatisticsManager.outPutExport(vProjectTask, year, month, response);
        return Result.ok();
    }
    @ApiOperation("参数分组统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "统计参数(年份:year;业务类型:recordType;项目区域:region;部门:department;小组:group)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "year", value = "年份", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "month", value = "月份", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "regionCode", value = "区域code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "recordTypeId", value = "项目类型id", dataType = "int", paramType = "query")
    })
    @PostMapping("/taskGroupByParam")
    public Result taskGroupByParam(
            @RequestParam(value = "param", defaultValue = "") String param,
            @RequestParam(value = "year", defaultValue = "") Integer year,
            @RequestParam(value = "month", defaultValue = "") Integer month,
            @RequestParam(value = "regionCode", defaultValue = "") String regionCode,
            @RequestParam(value = "recordTypeId", defaultValue = "") Integer recordTypeId
    ) {
        List<Map<String,Object>> taskGroupList=vStatisticsManager.groupByParamStatistic(param,year,month,regionCode,recordTypeId);
        return Result.ok(taskGroupList);
    }
//    @ApiOperation("当月任务统计")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "year", value = "年份", dataType = "int", paramType = "query"),
//            @ApiImplicitParam(name = "month", value = "月份", dataType = "int", paramType = "query"),
//            @ApiImplicitParam(name = "regionCode", value = "区域code", dataType = "string", paramType = "query"),
//            @ApiImplicitParam(name = "recordTypeId", value = "项目类型id", dataType = "int", paramType = "query"),
//            @ApiImplicitParam(name = "departmentCode", value = "部门code", dataType = "string", paramType = "query"),
//            @ApiImplicitParam(name = "workGroupCode", value = "小组code", dataType = "string", paramType = "query")
//    })
//    @PostMapping("/flipList")
//    public Result flipList(
//            @RequestParam(value = "year", required = false, defaultValue = "1") Integer year,
//            @RequestParam(value = "month", required = false, defaultValue = "10") Integer month,
//            @RequestParam(value = "regionCode", defaultValue = "") String regionCode,
//            @RequestParam(value = "recordTypeId", defaultValue = "") String recordTypeId,
//            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode,
//            @RequestParam(value = "workGroupCode", defaultValue = "") String workGroupCode
//
//    ) {
//        Page page = byBStoreRecordManager.search(storeroomCode, fileNumber, recordTypeName, departmentName, recordState, pageNo, pageSize);
//        return Result.ok(page);
//    }
}
