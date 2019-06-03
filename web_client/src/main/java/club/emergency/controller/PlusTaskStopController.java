package club.emergency.controller;

import club.emergency.project.manager.ByPlusTaskStopManager;
import club.emergency.project.model.ByPlusTaskStop;
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
 * Date: 2019-02-25
 * Time: 14:47
 * Description:
 */
@Api("任务暂停管理")
@CrossOrigin
@RestController
@RequestMapping("/plus-task-stop")
public class PlusTaskStopController {

    private ByPlusTaskStopManager byPlusTaskStopManager;

    public PlusTaskStopController(ByPlusTaskStopManager byPlusTaskStopManager) {
        this.byPlusTaskStopManager = byPlusTaskStopManager;
    }

    @ApiOperation("任务暂停信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "stopOperatorId", value = "暂停操作人id", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipList")
    public Result flipList(
            @RequestParam(value = "taskId", defaultValue = "") Integer taskId,
            @RequestParam(value = "stopOperatorId", defaultValue = "") Integer stopOperatorId
    ) {
        List<ByPlusTaskStop> byPlusTaskStopList = byPlusTaskStopManager.search(taskId, stopOperatorId);
        return Result.ok(byPlusTaskStopList);
    }

    @ApiOperation("任务暂停管理 - 编辑任务暂停信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "taskId", value = "任务id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "stopOperatorId", value = "暂停操作人id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "stopDate", value = "暂停时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "restartDate", value = "重新开始时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "stopOrder", value = "当前暂停次序", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query")
    })
    @PostMapping("/edit")
    public Result edit(
            @RequestParam(value = "id", defaultValue = "") Integer id,
            @RequestParam(value = "taskId", defaultValue = "") Integer taskId,
            @RequestParam(value = "stopOperatorId", defaultValue = "0") Integer stopOperatorId,
            @RequestParam(value = "stopDate", defaultValue = "0") String stopDate,
            @RequestParam(value = "restartDate", defaultValue = "0") String restartDate,
            @RequestParam(value = "stopOrder", defaultValue = "0") Integer stopOrder,
            @RequestParam(value = "remark", defaultValue = "") String remark
    ) {
        ByPlusTaskStop byPlusTaskStop = new ByPlusTaskStop();
        if (id != null) {
            byPlusTaskStop.setId(id);
        }
        byPlusTaskStop.setTaskId(taskId);
        byPlusTaskStop.setStopOperatorId(stopOperatorId);
        byPlusTaskStop.setStopDate(stopDate);
        byPlusTaskStop.setRestartDate(restartDate);
        byPlusTaskStop.setStopOrder(stopOrder);
        byPlusTaskStop.setRemark(remark);
        byPlusTaskStopManager.upperSave(byPlusTaskStop);
        return Result.ok();
    }

    @ApiOperation("任务暂停信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "stopOrder", value = "当前暂停次序", dataType = "int", paramType = "query")
    })
    @PostMapping("/detail")
    public Result detail(
            @RequestParam(value = "taskId", defaultValue = "") Integer taskId,
            @RequestParam(value = "stopOrder", defaultValue = "") Integer stopOrder
    ) {
        ByPlusTaskStop byPlusTaskStop = byPlusTaskStopManager.searchDetail(taskId, stopOrder);
        if (byPlusTaskStop == null) {
            return Result.ok("系统数据异常,请联系管理员!");
        } else {
            return Result.ok(byPlusTaskStop);
        }
    }

    @ApiOperation("任务暂停管理 - 删除任务暂停信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", dataType = "string", paramType = "query")
    })
    @PostMapping("/remove")
    public Result remove(
            @RequestParam(value = "ids") String ids
    ) {
        byPlusTaskStopManager.removeByIds(ids);
        return Result.ok();
    }
}
