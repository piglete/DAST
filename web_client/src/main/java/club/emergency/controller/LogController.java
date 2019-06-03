package club.emergency.controller;

import club.emergency.by_b_log.manager.ByBLogManager;
import club.map.core.model.Page;
import club.map.core.web.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: zhaojinxiong
 * Date: 2018-11-19
 * Time: 14:18
 * Description:
 */
@Api("日志管理")
@CrossOrigin
@RestController
@RequestMapping("/log")
public class LogController {

    private ByBLogManager byBLogManager;

    public LogController(ByBLogManager byBLogManager) {
        this.byBLogManager = byBLogManager;
    }

    @ApiOperation("查询日志")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "loginName", value = "用户账号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "moduleName", value = "模块名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "operationName", value = "操作名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "ip", value = "访问ip", dataType = "string", paramType = "query")
    })
    @PostMapping("/flipList")
    public Result flipList(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "loginName", defaultValue = "") String loginName,
            @RequestParam(value = "moduleName", defaultValue = "") String moduleName,
            @RequestParam(value = "operationName", defaultValue = "") String operationName,
            @RequestParam(value = "ip", defaultValue = "") String ip
    ) {
        Page page = byBLogManager.search(loginName, moduleName, operationName, ip, pageNo, pageSize);
        return Result.ok(page);
    }

    @ApiOperation("日志管理 - 删除日志信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", dataType = "string", paramType = "query")
    })
    @PostMapping("/remove")
    public Result remove(
            @RequestParam(value = "ids") String ids
    ) {
        byBLogManager.removeByIds(ids);
        return Result.ok();
    }
}
