package club.emergency.controller;

import club.emergency.project.manager.ByPlusRecordModelManager;
import club.emergency.project.model.ByPlusProject;
import club.emergency.project.model.ByPlusRecordModel;
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
 * Time: 15:12
 * Description:
 */
@Api("档案资料模板管理")
@CrossOrigin
@RestController
@RequestMapping("/record-model")
public class PlusRecordModelController {

    private ByPlusRecordModelManager byPlusRecordModelManager;

    public PlusRecordModelController(ByPlusRecordModelManager byPlusRecordModelManager) {
        this.byPlusRecordModelManager = byPlusRecordModelManager;
    }

    @ApiOperation("查询档案资料模板信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "recordTypeId", value = "档案类型id", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipList")
    public Result flipList(
            @RequestParam(value = "recordTypeId", defaultValue = "") Integer recordTypeId
    ) {
        List<ByPlusRecordModel> byPlusRecordModelList = byPlusRecordModelManager.search(recordTypeId);
        return Result.ok(byPlusRecordModelList);
    }

    @ApiOperation("档案资料模板管理 - 编辑档案资料模板信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "recordTypeId", value = "档案类型id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "catalogContent", value = "目录内容", dataType = "string", paramType = "query")
    })
    @PostMapping("/edit")
    public Result edit(
            @RequestParam(value = "id", defaultValue = "") Integer id,
            @RequestParam(value = "recordTypeId", defaultValue = "") Integer recordTypeId,
            @RequestParam(value = "catalogContent", defaultValue = "") String catalogContent
    ) {
        ByPlusRecordModel byPlusRecordModel=new ByPlusRecordModel();
        if(id != null){
            byPlusRecordModel.setId(id);
        }
        byPlusRecordModel.setRecordTypeId(recordTypeId);
        byPlusRecordModel.setCatalogContent(catalogContent);
        byPlusRecordModelManager.upperSave(byPlusRecordModel);
        return Result.ok();
    }

    @ApiOperation("档案资料模板详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @PostMapping("/detail")
    public Result detail(
            @RequestParam(value = "id", defaultValue = "") Integer id
    ) {
        ByPlusRecordModel byPlusRecordModel = byPlusRecordModelManager.searchDetail(id);
        return Result.ok(byPlusRecordModel);
    }

    @ApiOperation("档案资料模板管理 - 删除档案资料模板信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", dataType = "string", paramType = "query")
    })
    @PostMapping("/remove")
    public Result remove(
            @RequestParam(value = "ids") String ids
    ) {
        byPlusRecordModelManager.removeByIds(ids);
        return Result.ok();
    }
}
