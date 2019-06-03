package club.emergency.controller;

import club.emergency.project.manager.ByPlusTaskDepartmentAssistManager;
import club.emergency.project.model.ByPlusTaskDepartmentAssist;
import club.map.core.web.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhaojinxiong
 * Date: 2019-02-26
 * Time: 10:07
 * Description:
 */
@Api("外协部门管理")
@CrossOrigin
@RestController
@RequestMapping("/plus-task-department-assist")
public class PlusTaskDepartmentAssistController {

    private ByPlusTaskDepartmentAssistManager byPlusTaskDepartmentAssistManager;

    public PlusTaskDepartmentAssistController(ByPlusTaskDepartmentAssistManager byPlusTaskDepartmentAssistManager) {
        this.byPlusTaskDepartmentAssistManager = byPlusTaskDepartmentAssistManager;
    }

    @ApiOperation("查询外协部门信息(无分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "协助部门code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "groupCode", value = "外协部门小组code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "revertUserId", value = "部门接收人id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "groupRevertUserId", value = "外协部门小组接收人id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "flag", value = "标识(查询该任务下的外协部门 flag=0,查询该外协部门的所有外协任务 flag=1)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "stateFlag", value = "外协任务进行状态标识(-1为外协任务未下达,0为外协任务下达,1为外协部门接收,2为外协部门下达,3为外协小组接收,4为外协小组完成,5为外协一检接收,6为外协一检完成,7为(主部门下的)小组接收,8为(主部门下的)小组确定完成,9为(主部门下的)小组回退)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "finishFlag", value = "外协任务完成标识(0为未完成,1为已完成)", dataType = "int", paramType = "query"),
    })
    @PostMapping("/flipList")
    public Result flipList(
            @RequestParam(value = "taskId", defaultValue = "") Integer taskId,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode,
            @RequestParam(value = "groupCode", defaultValue = "") String groupCode,
            @RequestParam(value = "revertUserId", defaultValue = "") Integer revertUserId,
            @RequestParam(value = "groupRevertUserId", defaultValue = "") Integer groupRevertUserId,
            @RequestParam(value = "flag", defaultValue = "") Integer flag,
            @RequestParam(value = "stateFlag", defaultValue = "") Integer stateFlag,
            @RequestParam(value = "finishFlag", defaultValue = "") Integer finishFlag
    ) {
        List<ByPlusTaskDepartmentAssist> byPlusTaskDepartmentAssistList = byPlusTaskDepartmentAssistManager.search(taskId, departmentCode, groupCode, revertUserId, groupRevertUserId, flag, stateFlag, finishFlag);
        return Result.ok(byPlusTaskDepartmentAssistList);
    }

    @ApiOperation("外协部门管理 - 编辑外协部门信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "taskId", value = "任务id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "协助部门code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "revertUserId", value = "部门接收人id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "releaseDate", value = "部门任务下达时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "releaseRequire", value = "协作要求", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "departmentRevertDate", value = "部门接收时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "departmentFinishDate", value = "部门完成时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "departmentFinishPeriod", value = "完成周期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "needDayCount", value = "作业要求天数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "stateFlag", value = "外协任务进行状态标识(-1为外协任务未下达,0为外协任务下达,1为外协部门接收,2为外协部门下达,3为外协小组接收,4为外协小组完成,5为外协一检接收,6为外协一检完成,7为(主部门下的)小组接收,8为(主部门下的)小组确定完成,9为(主部门下的)小组回退)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "finishFlag", value = "外协任务完成标识(0为未完成,1为已完成)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "外协任务开始时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "returnCount", value = "回退次数(0为没有回退,其他为回退次数)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "groupCode", value = "外协部门小组code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "groupRevertUserId", value = "外协部门小组接收人id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "groupRevertDate", value = "小组接收时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "groupFinishDate", value = "小组完成时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "groupFinishPeriod", value = "小组完成周期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "oneInspectionUserId", value = "一检人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "oneInspectionRevertDate", value = "一检接收时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "oneInspectionFinishDate", value = "一检完成时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "oneInspectionFinishPeriod", value = "一检完成周期", dataType = "string", paramType = "query")
    })
    @PostMapping("/edit")
    public String edit(
            @RequestParam(value = "id", defaultValue = "") Integer id,
            @RequestParam(value = "taskId", defaultValue = "") Integer taskId,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode,
            @RequestParam(value = "revertUserId", defaultValue = "") Integer revertUserId,
            @RequestParam(value = "releaseDate", defaultValue = "") String releaseDate,
            @RequestParam(value = "releaseRequire", defaultValue = "") String releaseRequire,
            @RequestParam(value = "departmentRevertDate", defaultValue = "") String departmentRevertDate,
            @RequestParam(value = "departmentFinishDate", defaultValue = "") String departmentFinishDate,
            @RequestParam(value = "departmentFinishPeriod", defaultValue = "") String departmentFinishPeriod,
            @RequestParam(value = "needDayCount", defaultValue = "") Integer needDayCount,
            @RequestParam(value = "stateFlag", defaultValue = "-1") Integer stateFlag,
            @RequestParam(value = "finishFlag", defaultValue = "0") Integer finishFlag,
            @RequestParam(value = "startDate", defaultValue = "") String startDate,
            @RequestParam(value = "returnCount", defaultValue = "0") Integer returnCount,
            @RequestParam(value = "groupCode", defaultValue = "") String groupCode,
            @RequestParam(value = "groupRevertUserId", defaultValue = "") Integer groupRevertUserId,
            @RequestParam(value = "groupRevertDate", defaultValue = "") String groupRevertDate,
            @RequestParam(value = "groupFinishDate", defaultValue = "") String groupFinishDate,
            @RequestParam(value = "groupFinishPeriod", defaultValue = "") String groupFinishPeriod,
            @RequestParam(value = "oneInspectionUserId", defaultValue = "") Integer oneInspectionUserId,
            @RequestParam(value = "oneInspectionRevertDate", defaultValue = "") String oneInspectionRevertDate,
            @RequestParam(value = "oneInspectionFinishDate", defaultValue = "") String oneInspectionFinishDate,
            @RequestParam(value = "oneInspectionFinishPeriod", defaultValue = "") String oneInspectionFinishPeriod
    ) {
        ByPlusTaskDepartmentAssist byPlusTaskDepartmentAssist = new ByPlusTaskDepartmentAssist();
        if (id != null) {
            byPlusTaskDepartmentAssist.setId(id);
        }
        byPlusTaskDepartmentAssist.setTaskId(taskId);
        byPlusTaskDepartmentAssist.setDepartmentCode(departmentCode);
        byPlusTaskDepartmentAssist.setRevertUserId(revertUserId);
        byPlusTaskDepartmentAssist.setReleaseDate(releaseDate);
        byPlusTaskDepartmentAssist.setReleaseRequire(releaseRequire);
        byPlusTaskDepartmentAssist.setDepartmentRevertDate(departmentRevertDate);
        byPlusTaskDepartmentAssist.setDepartmentFinishDate(departmentFinishDate);
        byPlusTaskDepartmentAssist.setDepartmentFinishPeriod(departmentFinishPeriod);
        byPlusTaskDepartmentAssist.setNeedDayCount(needDayCount);
        byPlusTaskDepartmentAssist.setStateFlag(stateFlag);
        byPlusTaskDepartmentAssist.setFinishFlag(finishFlag);
        byPlusTaskDepartmentAssist.setStartDate(startDate);
        byPlusTaskDepartmentAssist.setReturnCount(returnCount);
        byPlusTaskDepartmentAssist.setGroupCode(groupCode);
        byPlusTaskDepartmentAssist.setGroupRevertUserId(groupRevertUserId);
        byPlusTaskDepartmentAssist.setGroupRevertDate(groupRevertDate);
        byPlusTaskDepartmentAssist.setGroupFinishDate(groupFinishDate);
        byPlusTaskDepartmentAssist.setGroupFinishPeriod(groupFinishPeriod);
        byPlusTaskDepartmentAssist.setOneInspectionUserId(oneInspectionUserId);
        byPlusTaskDepartmentAssist.setOneInspectionRevertDate(oneInspectionRevertDate);
        byPlusTaskDepartmentAssist.setOneInspectionFinishDate(oneInspectionFinishDate);
        byPlusTaskDepartmentAssist.setOneInspectionFinishPeriod(oneInspectionFinishPeriod);
        int a = byPlusTaskDepartmentAssistManager.upperSave(byPlusTaskDepartmentAssist);
        if (a == 0) {
            return "部门下达外协任务成功!";
        } else {
            return "此任务已下达给该外协部门,请勿重复下达!";
        }
    }

    @ApiOperation("外协部门信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @PostMapping("/detail")
    public Result detail(
            @RequestParam(value = "id", defaultValue = "") Integer id
    ) {
        ByPlusTaskDepartmentAssist byPlusTaskDepartmentAssist = byPlusTaskDepartmentAssistManager.searchDetail(id);
        return Result.ok(byPlusTaskDepartmentAssist);
    }

    @ApiOperation("外协部门管理 - 删除外协部门信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", dataType = "string", paramType = "query")
    })
    @PostMapping("/remove")
    public Result remove(
            @RequestParam(value = "ids") String ids
    ) {
        byPlusTaskDepartmentAssistManager.removeByIds(ids);
        return Result.ok();
    }
}
