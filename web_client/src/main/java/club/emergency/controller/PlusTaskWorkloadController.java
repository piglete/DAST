package club.emergency.controller;

import club.emergency.project.manager.ByPlusTaskWorkloadManager;
import club.emergency.project.model.ByPlusTaskWorkload;
import club.map.core.web.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhaojinxiong
 * Date: 2018-12-29
 * Time: 16:22
 * Description:
 */
@Api("任务工作量管理")
@CrossOrigin
@RestController
@RequestMapping("/plus-task-workload")
public class PlusTaskWorkloadController {

    private ByPlusTaskWorkloadManager byPlusTaskWorkloadManager;

    public PlusTaskWorkloadController(ByPlusTaskWorkloadManager byPlusTaskWorkloadManager) {
        this.byPlusTaskWorkloadManager = byPlusTaskWorkloadManager;
    }

    @ApiOperation("查询任务工作量信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "unitTypeId", value = "单位id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "itemApplicationId", value = "项目申报id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "小组code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "flag", value = "工作量标识(0为主查工作量,1为外查工作量)", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipList")
    public Result flipList(
            @RequestParam(value = "taskId", defaultValue = "") Integer taskId,
            @RequestParam(value = "unitTypeId", defaultValue = "") Integer unitTypeId,
            @RequestParam(value = "itemApplicationId", defaultValue = "") Integer itemApplicationId,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode,
            @RequestParam(value = "flag", defaultValue = "") Integer flag
    ) {
        List<ByPlusTaskWorkload> byPlusTaskWorkloadList = byPlusTaskWorkloadManager.search(taskId, unitTypeId, itemApplicationId, departmentCode, flag);
        return Result.ok(byPlusTaskWorkloadList);
    }

    @ApiOperation("任务工作量管理 - 编辑任务工作量信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", required = true,paramType = "query"),
            @ApiImplicitParam(name = "taskId", value = "任务id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "workCount", value = "数量", dataType = "decimal", paramType = "query"),
            @ApiImplicitParam(name = "itemApplicationId", value = "项目申报id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "itemApplication", value = "项目申报(查询时显示取该字段,不需要填写)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "unitTypeId", value = "单位id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "小组code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "internalVal", value = "内部产值单价", dataType = "decimal", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "flag", value = "工作量标识(0为主查工作量,1为外查工作量)", dataType = "int", paramType = "query")
    })
    @PostMapping("/edit")
    public Result edit(
            @RequestParam(value = "id", defaultValue = "") Integer id,
            @RequestParam(value = "taskId", defaultValue = "") Integer taskId,
            @RequestParam(value = "workCount", defaultValue = "") BigDecimal workCount,
            @RequestParam(value = "itemApplicationId", defaultValue = "") Integer itemApplicationId,
            @RequestParam(value = "unitTypeId", defaultValue = "") Integer unitTypeId,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode,
            @RequestParam(value = "internalVal", defaultValue = "") BigDecimal internalVal,
            @RequestParam(value = "remark", defaultValue = "") String remark,
            @RequestParam(value = "flag", defaultValue = "") Integer flag
    ) {
        ByPlusTaskWorkload byPlusTaskWorkload = new ByPlusTaskWorkload();
        if (id != null) {
            byPlusTaskWorkload.setId(id);
        }
        byPlusTaskWorkload.setTaskId(taskId);
        byPlusTaskWorkload.setWorkCount(workCount);
        byPlusTaskWorkload.setItemApplicationId(itemApplicationId);
        byPlusTaskWorkload.setUnitTypeId(unitTypeId);
        byPlusTaskWorkload.setDepartmentCode(departmentCode);
        byPlusTaskWorkload.setRemark(remark);
        byPlusTaskWorkload.setFlag(flag);
        byPlusTaskWorkloadManager.upperSave(byPlusTaskWorkload,internalVal);
        return Result.ok();
    }

    @ApiOperation("任务工作量信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @PostMapping("/detail")
    public Result detail(
            @RequestParam(value = "id", defaultValue = "") Integer id
    ) {
        ByPlusTaskWorkload byPlusTaskWorkload = byPlusTaskWorkloadManager.searchById(id);
        return Result.ok(byPlusTaskWorkload);
    }

    @ApiOperation("任务工作量管理 - 删除任务工作量信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", dataType = "string", paramType = "query")
    })
    @PostMapping("/remove")
    public Result remove(
            @RequestParam(value = "ids") String ids
    ) {
        byPlusTaskWorkloadManager.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("小组工作量统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "小组code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "开始日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束日期", dataType = "string", paramType = "query")
    })
    @PostMapping("/flipListForGroupWorkload")
    public Map<String, Object> flipListForGroupWorkload(
            @RequestParam(value = "taskId", defaultValue = "") Integer taskId,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode,
            @RequestParam(value = "startDate", defaultValue = "") String startDate,
            @RequestParam(value = "endDate", defaultValue = "") String endDate
    ) {
        Map<String, Object> map = byPlusTaskWorkloadManager.searchGroupWorkGroup(taskId, departmentCode, startDate, endDate);
        return map;
    }

    @ApiOperation("任务工作量管理 - 导出工作量信息(excel)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "部门code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "groupCode", value = "小组code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "oneInspectionUserId", value = "一检人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "twoInspectionUserId", value = "二检人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "threeInspectionUserId", value = "三检人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "itemApplicationId", value = "项目申报id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "flag", value = "工作量标识(0为主查工作量,1为外查工作量)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "开始日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束日期", dataType = "string", paramType = "query")
    })
    @GetMapping("/taskWorkloadExport")
    public Result taskWorkloadExport(
            @RequestParam(value = "taskId", defaultValue = "") Integer taskId,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode,
            @RequestParam(value = "groupCode", defaultValue = "") String groupCode,
            @RequestParam(value = "oneInspectionUserId", defaultValue = "") Integer oneInspectionUserId,
            @RequestParam(value = "twoInspectionUserId", defaultValue = "") Integer twoInspectionUserId,
            @RequestParam(value = "threeInspectionUserId", defaultValue = "") Integer threeInspectionUserId,
            @RequestParam(value = "itemApplicationId", defaultValue = "") Integer itemApplicationId,
            @RequestParam(value = "flag", defaultValue = "") Integer flag,
            @RequestParam(value = "startDate", defaultValue = "") String startDate,
            @RequestParam(value = "endDate", defaultValue = "") String endDate,
            HttpServletResponse response
    ) {
        byPlusTaskWorkloadManager.taskWorkloadExport(taskId, departmentCode, groupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId, itemApplicationId, flag, startDate, endDate, response);
        return Result.ok();
    }
}
