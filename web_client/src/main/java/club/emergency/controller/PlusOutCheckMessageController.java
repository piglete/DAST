package club.emergency.controller;

import club.emergency.project.manager.ByPlusOutCheckMessageManager;
import club.emergency.project.model.ByPlusOutCheckMessage;
import club.emergency.project.model.ByPlusTipMessage;
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
 * Date: 2019-01-23
 * Time: 16:07
 * Description:
 */
@Api("外查提示消息管理")
@CrossOrigin
@RestController
@RequestMapping("/out-check-message")
public class PlusOutCheckMessageController {

    private ByPlusOutCheckMessageManager byPlusOutCheckMessageManager;

    public PlusOutCheckMessageController(ByPlusOutCheckMessageManager byPlusOutCheckMessageManager) {
        this.byPlusOutCheckMessageManager = byPlusOutCheckMessageManager;
    }

    @ApiOperation("查询外查提示消息(无分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务id", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipList")
    public Result flipList(
            @RequestParam(value = "taskId", defaultValue = "") Integer taskId
    ) {
        List<ByPlusOutCheckMessage> byPlusOutCheckMessageList = byPlusOutCheckMessageManager.search(taskId);
        return Result.ok(byPlusOutCheckMessageList);
    }
}
