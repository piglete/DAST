package club.emergency.controller;

import club.emergency.project.manager.ByPlusTipMessageManager;
import club.emergency.project.model.ByPlusMessage;
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
 * Date: 2019-01-22
 * Time: 17:02
 * Description:
 */
@Api("项目提示消息管理")
@CrossOrigin
@RestController
@RequestMapping("/plus-tip-message")
public class PlusTipMessageController {

    private ByPlusTipMessageManager byPlusTipMessageManager;

    public PlusTipMessageController(ByPlusTipMessageManager byPlusTipMessageManager) {
        this.byPlusTipMessageManager = byPlusTipMessageManager;
    }

    @ApiOperation("查询项目提示消息(无分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipList")
    public Result flipList(
            @RequestParam(value = "projectId", defaultValue = "") Integer projectId
    ) {
        List<ByPlusTipMessage> byPlusTipMessageList = byPlusTipMessageManager.search(projectId);
        return Result.ok(byPlusTipMessageList);
    }
}
