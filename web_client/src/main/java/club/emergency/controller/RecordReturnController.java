package club.emergency.controller;

import club.emergency.project.manager.ByPlusRecordReturnManager;
import club.emergency.project.model.ByPlusPay;
import club.emergency.project.model.ByPlusRecordReturn;
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
 * Date: 2019-01-11
 * Time: 9:18
 * Description:
 */
@Api("档案回退管理")
@CrossOrigin
@RestController
@RequestMapping("/record-return")
public class RecordReturnController {

    private ByPlusRecordReturnManager byPlusRecordReturnManager;

    public RecordReturnController(ByPlusRecordReturnManager byPlusRecordReturnManager) {
        this.byPlusRecordReturnManager = byPlusRecordReturnManager;
    }

    @ApiOperation("查询档案回退信息(无分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "recordReturnReasonId", value = "回退理由id", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipList")
    public Result flipList(
            @RequestParam(value = "taskId", defaultValue = "") Integer taskId,
            @RequestParam(value = "recordReturnReasonId", defaultValue = "") Integer recordReturnReasonId
    ) {
        List<ByPlusRecordReturn> byPlusRecordReturnList = byPlusRecordReturnManager.search(taskId, recordReturnReasonId);
        return Result.ok(byPlusRecordReturnList);
    }

    @ApiOperation("档案回退管理 - 编辑档案回退信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "taskId", value = "任务id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "returnUser", value = "回退人员", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "recordReturnReasonId", value = "回退理由id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "recordReturnReason", value = "回退理由(读取数据显示时需要,无需填写)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "returnDate", value = "回退时间", dataType = "string", paramType = "query")
    })
    @PostMapping("/edit")
    public Result edit(
            @RequestParam(value = "id", defaultValue = "") Integer id,
            @RequestParam(value = "taskId", defaultValue = "") Integer taskId,
            @RequestParam(value = "returnUser", defaultValue = "") String returnUser,
            @RequestParam(value = "recordReturnReasonId", defaultValue = "") Integer recordReturnReasonId,
            @RequestParam(value = "returnDate", defaultValue = "") String returnDate
    ) {
        ByPlusRecordReturn byPlusRecordReturn = new ByPlusRecordReturn();
        if (id != null) {
            byPlusRecordReturn.setId(id);
        }
        byPlusRecordReturn.setTaskId(taskId);
        byPlusRecordReturn.setReturnUser(returnUser);
        byPlusRecordReturn.setRecordReturnReasonId(recordReturnReasonId);
        byPlusRecordReturn.setReturnDate(returnDate);
        byPlusRecordReturnManager.upperSave(byPlusRecordReturn);
        return Result.ok();
    }

    @ApiOperation("档案回退信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @PostMapping("/detail")
    public Result detail(
            @RequestParam(value = "id", defaultValue = "") Integer id
    ) {
        ByPlusRecordReturn byPlusRecordReturn = byPlusRecordReturnManager.searchDetail(id);
        return Result.ok(byPlusRecordReturn);
    }

    @ApiOperation("档案回退管理 - 删除档案回退信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", dataType = "string", paramType = "query"),
    })
    @PostMapping("/remove")
    public Result remove(
            @RequestParam(value = "ids") String ids
    ) {
        byPlusRecordReturnManager.removeByIds(ids);
        return Result.ok();
    }

}
