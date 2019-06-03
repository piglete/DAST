package club.emergency.controller;

import club.emergency.project.manager.ByPlusThreeReturnTwoManager;
import club.emergency.project.model.ByPlusThreeReturnTwo;
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
 * Date: 2019-02-13
 * Time: 8:08
 * Description:
 */
@Api("三检回退二检管理")
@CrossOrigin
@RestController
@RequestMapping("/three-return-two")
public class ByPlusThreeReturnTwoController {

    private ByPlusThreeReturnTwoManager byPlusThreeReturnTwoManager;

    public ByPlusThreeReturnTwoController(ByPlusThreeReturnTwoManager byPlusThreeReturnTwoManager) {
        this.byPlusThreeReturnTwoManager = byPlusThreeReturnTwoManager;
    }

    @ApiOperation("查询三检回退二检记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务id", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipList")
    public Result flipList(
            @RequestParam(value = "taskId", defaultValue = "") Integer taskId
    ) {
        List<ByPlusThreeReturnTwo> byPlusThreeReturnTwoList = byPlusThreeReturnTwoManager.search(taskId);
        return Result.ok(byPlusThreeReturnTwoList);
    }

    @ApiOperation("三检回退二检管理 - 编辑三检回退二检信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "taskId", value = "任务id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "twoInspectionUser", value = "二检人员", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "threeInspectionUser", value = "三检回退人员", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "returnReasonIds", value = "回退理由ids(多个)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "returnReason", value = "回退理由(查询显示时读取字段,不需要填写)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "taskTwoScore", value = "二检评定分数", dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "threeReturnDate", value = "三检回退时间", dataType = "string", paramType = "query")
    })
    @PostMapping("/edit")
    public Result edit(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "taskId", defaultValue = "") Integer taskId,
            @RequestParam(value = "twoInspectionUser", defaultValue = "") String twoInspectionUser,
            @RequestParam(value = "threeInspectionUser", defaultValue = "") String threeInspectionUser,
            @RequestParam(value = "returnReasonIds", defaultValue = "") String returnReasonIds,
            @RequestParam(value = "taskTwoScore", defaultValue = "") Double taskTwoScore,
            @RequestParam(value = "threeReturnDate", defaultValue = "") String threeReturnDate
    ) {
        ByPlusThreeReturnTwo byPlusThreeReturnTwo = new ByPlusThreeReturnTwo();
        if (id != null) {
            byPlusThreeReturnTwo.setId(id);
        }
        byPlusThreeReturnTwo.setTaskId(taskId);
        byPlusThreeReturnTwo.setTwoInspectionUser(twoInspectionUser);
        byPlusThreeReturnTwo.setThreeInspectionUser(threeInspectionUser);
        byPlusThreeReturnTwo.setReturnReasonIds(returnReasonIds);
        byPlusThreeReturnTwo.setTaskTwoScore(taskTwoScore);
        byPlusThreeReturnTwo.setThreeReturnDate(threeReturnDate);
        byPlusThreeReturnTwoManager.upperSave(byPlusThreeReturnTwo);
        return Result.ok();
    }

    @ApiOperation("三检回退二检记录信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @PostMapping("/detail")
    public Result detail(
            @RequestParam(value = "id", defaultValue = "") Integer id
    ) {
        ByPlusThreeReturnTwo byPlusThreeReturnTwo = byPlusThreeReturnTwoManager.searchDetail(id);
        return Result.ok(byPlusThreeReturnTwo);
    }

    @ApiOperation("三检回退二检管理 - 删除三检回退二检信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", dataType = "string", paramType = "query")
    })
    @PostMapping("/remove")
    public Result remove(
            @RequestParam(value = "ids") String ids
    ) {
        byPlusThreeReturnTwoManager.removeByIds(ids);
        return Result.ok();
    }
}
