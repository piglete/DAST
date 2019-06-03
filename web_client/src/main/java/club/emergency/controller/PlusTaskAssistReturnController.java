package club.emergency.controller;

import club.emergency.project.manager.ByPlusTaskAssistReturnManager;
import club.emergency.project.model.ByPlusTaskAssistReturn;
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
 * Date: 2019-02-28
 * Time: 9:26
 * Description:
 */
@Api("外协部门回退管理")
@CrossOrigin
@RestController
@RequestMapping("/plus-task-assist-return")
public class PlusTaskAssistReturnController {

    private ByPlusTaskAssistReturnManager byPlusTaskAssistReturnManager;

    public PlusTaskAssistReturnController(ByPlusTaskAssistReturnManager byPlusTaskAssistReturnManager) {
        this.byPlusTaskAssistReturnManager = byPlusTaskAssistReturnManager;
    }

    @ApiOperation("查询外协部门回退信息(无分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "departmentAssistId", value = "协助部门id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "外协部门code", dataType = "string", paramType = "query")
    })
    @PostMapping("/flipList")
    public Result flipList(
            @RequestParam(value = "taskId", defaultValue = "") Integer taskId,
            @RequestParam(value = "departmentAssistId", defaultValue = "") Integer departmentAssistId,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode
    ) {
        List<ByPlusTaskAssistReturn> byPlusTaskAssistReturnList = byPlusTaskAssistReturnManager.search(taskId, departmentAssistId, departmentCode);
        return Result.ok(byPlusTaskAssistReturnList);
    }

    @ApiOperation("外协部门回退管理 - 编辑外协部门回退信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "departmentAssistId", value = "协助部门id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "taskId", value = "任务id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "回退小组code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "returnDate", value = "回退日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "returnUserId", value = "退回人id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "returnReasonIds", value = "回退原因(可多个原因,存原因id,中间\",\"隔开)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query")
    })
    @PostMapping("/edit")
    public Result edit(
            @RequestParam(value = "id", defaultValue = "") Integer id,
            @RequestParam(value = "departmentAssistId", defaultValue = "") Integer departmentAssistId,
            @RequestParam(value = "taskId", defaultValue = "") Integer taskId,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode,
            @RequestParam(value = "returnDate", defaultValue = "") String returnDate,
            @RequestParam(value = "returnUserId", defaultValue = "") Integer returnUserId,
            @RequestParam(value = "returnReasonIds", defaultValue = "") String returnReasonIds,
            @RequestParam(value = "remark", defaultValue = "") String remark
    ) {
        ByPlusTaskAssistReturn byPlusTaskAssistReturn = new ByPlusTaskAssistReturn();
        if (id != null) {
            byPlusTaskAssistReturn.setId(id);
        }
        byPlusTaskAssistReturn.setDepartmentAssistId(departmentAssistId);
        byPlusTaskAssistReturn.setTaskId(taskId);
        byPlusTaskAssistReturn.setDepartmentCode(departmentCode);
        byPlusTaskAssistReturn.setReturnDate(returnDate);
        byPlusTaskAssistReturn.setReturnUserId(returnUserId);
        byPlusTaskAssistReturn.setReturnReasonIds(returnReasonIds);
        byPlusTaskAssistReturn.setRemark(remark);
        byPlusTaskAssistReturnManager.upperSave(byPlusTaskAssistReturn);
        return Result.ok();
    }

    @ApiOperation("外协部门回退信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @PostMapping("/detail")
    public Result detail(
            @RequestParam(value = "id", defaultValue = "") Integer id
    ) {
        ByPlusTaskAssistReturn byPlusTaskAssistReturn = byPlusTaskAssistReturnManager.searchDetail(id);
        return Result.ok(byPlusTaskAssistReturn);
    }

    @ApiOperation("外协部门回退管理 - 删除外协部门回退信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", dataType = "string", paramType = "query")
    })
    @PostMapping("/remove")
    public Result remove(
            @RequestParam(value = "ids") String ids
    ) {
        byPlusTaskAssistReturnManager.removeByIds(ids);
        return Result.ok();
    }
}
