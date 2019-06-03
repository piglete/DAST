package club.emergency.controller;

import club.emergency.by_b_system_notice.manager.ByBSystemNoticeManager;
import club.emergency.by_b_system_notice.model.ByBSystemNotice;
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
 * Time: 9:04
 * Description:
 */
@Api("系统通知管理")
@CrossOrigin
@RestController
@RequestMapping("/system-notice")
public class SystemNoticeController {

    private ByBSystemNoticeManager byBSystemNoticeManager;

    public SystemNoticeController(ByBSystemNoticeManager byBSystemNoticeManager) {
        this.byBSystemNoticeManager = byBSystemNoticeManager;
    }

    @ApiOperation("查询系统通知信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "content", value = "内容", dataType = "string", paramType = "query")
    })
    @PostMapping("/flipList")
    public Result flipList(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "content", defaultValue = "") String content
    ) {
        Page page = byBSystemNoticeManager.search(content, pageNo, pageSize);
        return Result.ok(page);
    }

    @ApiOperation("系统通知管理 - 编辑系统通知信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "noticeTitle", value = "通知标题", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "noticeMessage", value = "通知信息", dataType = "string", paramType = "query"),
    })
    @PostMapping("/edit")
    public Result edit(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "noticeTitle", defaultValue = "") String noticeTitle,
            @RequestParam(value = "noticeMessage", defaultValue = "") String noticeMessage
    ) {
        ByBSystemNotice byBSystemNotice=new ByBSystemNotice();
        if(id != null){
            byBSystemNotice.setId(id);
        }
        byBSystemNotice.setNoticeTitle(noticeTitle);
        byBSystemNotice.setNoticeMessage(noticeMessage);
        byBSystemNoticeManager.upperSave(byBSystemNotice);
        return Result.ok();
    }

    @ApiOperation("系统通知信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @PostMapping("/detail")
    public Result detail(
            @RequestParam(value = "id", defaultValue = "") Integer id
    ) {
        ByBSystemNotice byBSystemNotice=byBSystemNoticeManager.get(id);
        return Result.ok(byBSystemNotice);
    }

    @ApiOperation("系统通知管理 - 删除系统通知信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", dataType = "string", paramType = "query")
    })
    @PostMapping("/remove")
    public Result remove(
            @RequestParam(value = "ids") String ids
    ) {
        byBSystemNoticeManager.removeByIds(ids);
        return Result.ok();
    }

}
