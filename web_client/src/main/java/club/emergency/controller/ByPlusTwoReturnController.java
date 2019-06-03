package club.emergency.controller;

import club.emergency.project.manager.ByPlusTwoReturnManager;
import club.emergency.project.model.ByPlusTwoReturn;
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
 * Date: 2019-01-09
 * Time: 11:30
 * Description: edit方法只是提供给前端查看对应参数的中文
 */
@Api("二检回退管理")
@CrossOrigin
@RestController
@RequestMapping("/two-return")
public class ByPlusTwoReturnController {

    private ByPlusTwoReturnManager byPlusTwoReturnManager;

    public ByPlusTwoReturnController(ByPlusTwoReturnManager byPlusTwoReturnManager) {
        this.byPlusTwoReturnManager = byPlusTwoReturnManager;
    }

    @ApiOperation("查询二检回退信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务id", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipList")
    public Result flipList(
            @RequestParam(value = "taskId", defaultValue = "") Integer taskId
    ) {
        List<ByPlusTwoReturn> byPlusTwoReturnList = byPlusTwoReturnManager.search(taskId);
        return Result.ok(byPlusTwoReturnList);
    }

    @ApiOperation("二检回退管理 - 编辑二检回退记录信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "taskId", value = "任务id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "oneInspectionUser", value = "一检人员", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "oneRevertDate", value = "一检接收时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "oneCheckDate", value = "一检核对时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "twoInspectionUser", value = "二检人员", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "twoRevertDate", value = "二检接收时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "twoReturnDate", value = "二检回退时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "returnUser", value = "回退人员", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "twoReturnReasonIds", value = "回退理由ids(多个)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "groupRevertDate", value = "小组接收时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "groupCommitDate", value = "小组提交时间", dataType = "string", paramType = "query")
    })
    @PostMapping("/edit")
    public Result edit(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "taskId", defaultValue = "") Integer taskId,
            @RequestParam(value = "oneInspectionUser", defaultValue = "") String oneInspectionUser,
            @RequestParam(value = "oneRevertDate", defaultValue = "") String oneRevertDate,
            @RequestParam(value = "oneCheckDate", defaultValue = "") String oneCheckDate,
            @RequestParam(value = "twoInspectionUser", defaultValue = "") String twoInspectionUser,
            @RequestParam(value = "twoRevertDate", defaultValue = "") String twoRevertDate,
            @RequestParam(value = "twoReturnDate", defaultValue = "") String twoReturnDate,
            @RequestParam(value = "returnUser", defaultValue = "") String returnUser,
            @RequestParam(value = "twoReturnReasonIds", defaultValue = "") String twoReturnReasonIds,
            @RequestParam(value = "groupRevertDate", defaultValue = "") String groupRevertDate,
            @RequestParam(value = "groupCommitDate", defaultValue = "") String groupCommitDate
    ) {
        ByPlusTwoReturn byPlusTwoReturn = new ByPlusTwoReturn();
        if (id != null) {
            byPlusTwoReturn.setId(id);
        }
        byPlusTwoReturn.setTaskId(taskId);
        byPlusTwoReturn.setOneInspectionUser(oneInspectionUser);
        byPlusTwoReturn.setOneRevertDate(oneRevertDate);
        byPlusTwoReturn.setOneCheckDate(oneCheckDate);
        byPlusTwoReturn.setTwoInspectionUser(twoInspectionUser);
        byPlusTwoReturn.setTwoRevertDate(twoRevertDate);
        byPlusTwoReturn.setTwoReturnDate(twoReturnDate);
        byPlusTwoReturn.setReturnUser(returnUser);
        byPlusTwoReturn.setTwoReturnReasonIds(twoReturnReasonIds);
        byPlusTwoReturn.setGroupRevertDate(groupRevertDate);
        byPlusTwoReturn.setGroupCommitDate(groupCommitDate);
        byPlusTwoReturnManager.upperSave(byPlusTwoReturn);
        return Result.ok();
    }

    @ApiOperation("二检回退记录信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @PostMapping("/detail")
    public Result detail(
            @RequestParam(value = "id", defaultValue = "") Integer id
    ) {
        ByPlusTwoReturn byPlusTwoReturn=byPlusTwoReturnManager.searchDetails(id);
        return Result.ok(byPlusTwoReturn);
    }

    @ApiOperation("二检回退管理 - 删除二检回退记录信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", dataType = "string", paramType = "query")
    })
    @PostMapping("/remove")
    public Result remove(
            @RequestParam(value = "ids") String ids
    ) {
        byPlusTwoReturnManager.removeByIds(ids);
        return Result.ok();
    }
}
