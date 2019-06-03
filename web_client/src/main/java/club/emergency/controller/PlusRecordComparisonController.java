package club.emergency.controller;

import club.emergency.project.manager.ByPlusRecordComparisonManager;
import club.emergency.project.model.ByPlusRecordComparison;
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
 * Time: 16:22
 * Description:
 */
@Api("项目归档审核记录管理")
@CrossOrigin
@RestController
@RequestMapping("/record-comparison")
public class PlusRecordComparisonController {

    private ByPlusRecordComparisonManager byPlusRecordComparisonManager;

    public PlusRecordComparisonController(ByPlusRecordComparisonManager byPlusRecordComparisonManager) {
        this.byPlusRecordComparisonManager = byPlusRecordComparisonManager;
    }

    @ApiOperation("查询项目归档审核记录信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipList")
    public Result flipList(
            @RequestParam(value = "projectId", defaultValue = "") Integer projectId
    ) {
        List<ByPlusRecordComparison> byPlusRecordComparisonList = byPlusRecordComparisonManager.search(projectId);
        return Result.ok(byPlusRecordComparisonList);
    }

    @ApiOperation("项目归档审核记录管理 - 编辑项目归档审核记录信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "recordModelId", value = "档案资料模板id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "existFlag", value = "资料提交标示(0为未提交,1为已提交)", dataType = "int", paramType = "query")
    })
    @PostMapping("/edit")
    public Result edit(
            @RequestParam(value = "id", defaultValue = "") Integer id,
            @RequestParam(value = "projectId", defaultValue = "") Integer projectId,
            @RequestParam(value = "recordModelId", defaultValue = "") Integer recordModelId,
            @RequestParam(value = "existFlag", defaultValue = "0") Integer existFlag
    ) {
        ByPlusRecordComparison byPlusRecordComparison = new ByPlusRecordComparison();
        if (id != null) {
            byPlusRecordComparison.setId(id);
        }
        byPlusRecordComparison.setProjectId(projectId);
        byPlusRecordComparison.setRecordModelId(recordModelId);
        byPlusRecordComparison.setExistFlag(existFlag);
        byPlusRecordComparisonManager.upperSave(byPlusRecordComparison);
        return Result.ok();
    }

    @ApiOperation("项目归档审核记录管理 - 批量编辑项目归档审核记录信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "trueRecordIds", value = "已提交资料目录", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "falseRecordIds", value = "未提交资料目录", dataType = "string", paramType = "query")
    })
    @PostMapping("/editForRecordComparison")
    public Result editForRecordComparison(
            @RequestParam(value = "projectId", defaultValue = "") Integer projectId,
            @RequestParam(value = "trueRecordIds", defaultValue = "") String trueRecordIds,
            @RequestParam(value = "falseRecordIds", defaultValue = "0") String falseRecordIds
    ) {
        ByPlusRecordComparison byPlusRecordComparison = new ByPlusRecordComparison();
        byPlusRecordComparison.setProjectId(projectId);
        byPlusRecordComparisonManager.upperMoreSave(byPlusRecordComparison, trueRecordIds, falseRecordIds);
        return Result.ok();
    }

    @ApiOperation("项目归档审核记录详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @PostMapping("/detail")
    public Result detail(
            @RequestParam(value = "id", defaultValue = "") Integer id
    ) {
        ByPlusRecordComparison byPlusRecordComparison = byPlusRecordComparisonManager.searchDetail(id);
        return Result.ok(byPlusRecordComparison);
    }

    @ApiOperation("项目归档审核记录管理 - 删除项目归档审核记录信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", dataType = "string", paramType = "query")
    })
    @PostMapping("/remove")
    public Result remove(
            @RequestParam(value = "ids") String ids
    ) {
        byPlusRecordComparisonManager.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("项目归档审核记录管理 - 修改项目归档审核记录信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", dataType = "string", paramType = "query")
    })
    @PostMapping("/editForExistFlag")
    public Result editForExistFlag(
            @RequestParam(value = "ids") String ids
    ) {
        byPlusRecordComparisonManager.updateExistFlagByIds(ids);
        return Result.ok();
    }
}
