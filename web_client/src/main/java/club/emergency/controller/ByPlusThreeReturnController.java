package club.emergency.controller;

import club.emergency.project.manager.ByPlusThreeReturnManager;
import club.emergency.project.model.ByPlusThreeReturn;
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
 * Time: 11:47
 * Description: edit方法只是提供给前端查看对应参数的中文
 */
@Api("三检回退管理")
@CrossOrigin
@RestController
@RequestMapping("/three-return")
public class ByPlusThreeReturnController {

    private ByPlusThreeReturnManager byPlusThreeReturnManager;

    public ByPlusThreeReturnController(ByPlusThreeReturnManager byPlusThreeReturnManager) {
        this.byPlusThreeReturnManager = byPlusThreeReturnManager;
    }

    @ApiOperation("查询三检回退信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务id", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipList")
    public Result flipList(
            @RequestParam(value = "taskId", defaultValue = "") Integer taskId
    ) {
        List<ByPlusThreeReturn> byPlusThreeReturnList = byPlusThreeReturnManager.search(taskId);
        return Result.ok(byPlusThreeReturnList);
    }

    @ApiOperation("三检回退管理 - 编辑三检回退记录信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "taskId", value = "任务id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "oneInspectionUser", value = "一检人员", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "oneRevertDate", value = "一检接收时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "oneCheckDate", value = "一检核对时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "twoInspectionUser", value = "二检人员", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "twoRevertDate", value = "二检接收时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "twoCheckDate", value = "二检核对时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "threeInspectionUser", value = "三检人员", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "threeRevertDate", value = "三检接收时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "threeReturnDate", value = "三检回退时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "returnUser", value = "回退人员", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "threeReturnReasonIds", value = "回退理由ids(多个)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "groupRevertDate", value = "小组接收时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "groupCommitDate", value = "小组提交时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "taskTwoScore", value = "二检评定分数", dataType = "double", paramType = "query")
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
            @RequestParam(value = "twoCheckDate", defaultValue = "") String twoCheckDate,
            @RequestParam(value = "threeInspectionUser", defaultValue = "") String threeInspectionUser,
            @RequestParam(value = "threeRevertDate", defaultValue = "") String threeRevertDate,
            @RequestParam(value = "threeReturnDate", defaultValue = "") String threeReturnDate,
            @RequestParam(value = "returnUser", defaultValue = "") String returnUser,
            @RequestParam(value = "threeReturnReasonIds", defaultValue = "") String threeReturnReasonIds,
            @RequestParam(value = "groupRevertDate", defaultValue = "") String groupRevertDate,
            @RequestParam(value = "groupCommitDate", defaultValue = "") String groupCommitDate,
            @RequestParam(value = "taskTwoScore", defaultValue = "") Double taskTwoScore
    ) {
        ByPlusThreeReturn byPlusThreeReturn = new ByPlusThreeReturn();
        if (id != null) {
            byPlusThreeReturn.setId(id);
        }
        byPlusThreeReturn.setTaskId(taskId);
        byPlusThreeReturn.setOneInspectionUser(oneInspectionUser);
        byPlusThreeReturn.setOneRevertDate(oneRevertDate);
        byPlusThreeReturn.setOneCheckDate(oneCheckDate);
        byPlusThreeReturn.setTwoInspectionUser(twoInspectionUser);
        byPlusThreeReturn.setTwoRevertDate(twoRevertDate);
        byPlusThreeReturn.setTwoCheckDate(twoCheckDate);
        byPlusThreeReturn.setThreeInspectionUser(threeInspectionUser);
        byPlusThreeReturn.setThreeRevertDate(threeRevertDate);
        byPlusThreeReturn.setThreeReturnDate(threeReturnDate);
        byPlusThreeReturn.setReturnUser(returnUser);
        byPlusThreeReturn.setThreeReturnReasonIds(threeReturnReasonIds);
        byPlusThreeReturn.setGroupRevertDate(groupRevertDate);
        byPlusThreeReturn.setGroupCommitDate(groupCommitDate);
        byPlusThreeReturn.setTaskTwoScore(taskTwoScore);
        byPlusThreeReturnManager.upperSave(byPlusThreeReturn);
        return Result.ok();
    }

    @ApiOperation("三检回退记录信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @PostMapping("/detail")
    public Result detail(
            @RequestParam(value = "id", defaultValue = "") Integer id
    ) {
        ByPlusThreeReturn byPlusThreeReturn=byPlusThreeReturnManager.searchDetail(id);
        return Result.ok(byPlusThreeReturn);
    }

    @ApiOperation("三检回退管理 - 删除三检回退记录信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", dataType = "string", paramType = "query")
    })
    @PostMapping("/remove")
    public Result remove(
            @RequestParam(value = "ids") String ids
    ) {
        byPlusThreeReturnManager.removeByIds(ids);
        return Result.ok();
    }
}
