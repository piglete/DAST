package club.emergency.controller;

import club.emergency.by_b_return_reason.manager.ByBReturnReasonManager;
import club.emergency.by_b_return_reason.model.ByBReturnReason;
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
 * Date: 2019-02-14
 * Time: 10:11
 * Description:
 */
@Api("回退原因管理")
@CrossOrigin
@RestController
@RequestMapping("/return-reason")
public class ByBReturnReasonController {

    private ByBReturnReasonManager byBReturnReasonManager;

    public ByBReturnReasonController(ByBReturnReasonManager byBReturnReasonManager) {
        this.byBReturnReasonManager = byBReturnReasonManager;
    }

    @ApiOperation("查询回退原因信息(列表)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentId", value = "父节点", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipList")
    public Result flipList(
            @RequestParam(value = "parentId", defaultValue = "") Integer parentId
    ) {
        List<ByBReturnReason> byBReturnReasonList = byBReturnReasonManager.search(parentId);
        return Result.ok(byBReturnReasonList);
    }

    @ApiOperation("查询回退原因信息(子父节点关联的)")
    @PostMapping("/flipListForRelation")
    public Result flipListForRelation() {
        List<ByBReturnReason> byBReturnReasonList = byBReturnReasonManager.searchWithRelation();
        return Result.ok(byBReturnReasonList);
    }

    @ApiOperation("回退原因管理 - 编辑回退原因信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "content", value = "原因内容", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "parentId", value = "父节点", dataType = "int", paramType = "query")
    })
    @PostMapping("/edit")
    public Result edit(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "content", defaultValue = "") String content,
            @RequestParam(value = "parentId", defaultValue = "0") Integer parentId
    ) {
        ByBReturnReason byBReturnReason = new ByBReturnReason();
        if (id != null) {
            byBReturnReason.setId(id);
        }
        byBReturnReason.setContent(content);
        byBReturnReason.setParentId(parentId);
        byBReturnReasonManager.upperSave(byBReturnReason);
        return Result.ok();
    }

    @ApiOperation("回退原因信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @PostMapping("/detail")
    public Result detail(
            @RequestParam(value = "id", defaultValue = "") Integer id
    ) {
        ByBReturnReason byBReturnReason = byBReturnReasonManager.searchDetails(id);
        return Result.ok(byBReturnReason);
    }

    @ApiOperation("回退原因管理 - 删除回退原因信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", dataType = "string", paramType = "query")
    })
    @PostMapping("/remove")
    public Result remove(
            @RequestParam(value = "ids") String ids
    ) {
        byBReturnReasonManager.removeByIds(ids);
        return Result.ok();
    }
}
