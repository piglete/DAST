package club.emergency.controller;

import club.emergency.by_plus_unit_config.manager.ByPlusUnitConfigManager;
import club.emergency.by_plus_unit_config.model.ByPlusUnitConfig;
import club.map.core.model.Page;
import club.map.core.web.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @Description: TODO
 * @Author: lxl
 * @CreateDate: 2019/05/21
 * @Version: v1.0
 */
@Api("产值配置")
@CrossOrigin
@RestController
@RequestMapping("/unit_config")
public class UnitConfigController {

    @Autowired
    private ByPlusUnitConfigManager byPlusUnitConfigManager;


    @ApiOperation("产值配置 - 编辑产值配置信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "recordTypeId", value = "项目类型id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "itemApplicationId", value = "申报内容id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "unitTypeId", value = "申报单位id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "internalVal", value = "内部产值单价", dataType = "decimal", paramType = "query")
    })
    @PostMapping("/edit")
    public Result editForParent(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "recordTypeId", defaultValue = "") Integer recordTypeId,
            @RequestParam(value = "itemApplicationId", defaultValue = "") Integer itemApplicationId,
            @RequestParam(value = "unitTypeId", defaultValue = "") Integer unitTypeId,
            @RequestParam(value = "internalVal", defaultValue = "") BigDecimal internalVal
    ) {
        ByPlusUnitConfig byPlusUnitConfig = new ByPlusUnitConfig();
        if (id != null) {
            byPlusUnitConfig.setId(id);
        }
        byPlusUnitConfig.setItemApplicationId(itemApplicationId);
        byPlusUnitConfig.setUnitTypeId(unitTypeId);
        byPlusUnitConfig.setInternalVal(internalVal);
        byPlusUnitConfig.setRecordTypeId(recordTypeId);
        byPlusUnitConfigManager.upperSave(byPlusUnitConfig);
        return Result.ok();
    }
    @ApiOperation("产值配置 - 查看产值配置详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @PostMapping("/detail")
    public Result detailForParent(
            @RequestParam("id") Integer id
    ) {
        return Result.ok(byPlusUnitConfigManager.get(id));
    }
    @ApiOperation("产值配置 - 通过任务编号查找项目类型配置关联信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "taskId", dataType = "int", paramType = "query")
    })
    @PostMapping("/searchByTaskId")
    public Result searchByTaskId(
            @RequestParam("taskId") Integer taskId
    ) {
        return Result.ok(byPlusUnitConfigManager.searchByTaskId(taskId));
    }
//    @ApiOperation("产值配置 -  删除配置")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
//    })
//    @PostMapping("/remove")
//    public Result removeForParent(
//            @RequestParam("id") Integer id
//    ) {
//        byPlusUnitConfigManager.removeById(id);
//        return Result.ok();
//    }
    @ApiOperation("产值配置 -  删除配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids(例如: 一个:1 ; 多个:1,2 )", dataType = "string", paramType = "query")
    })
    @PostMapping("/remove")
    public Result remove(
            @RequestParam(value = "ids") String ids
    ) {
        byPlusUnitConfigManager.removeByIds(ids);
        return Result.ok();
    }
    @ApiOperation("产值配置 - 查询配置信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "itemApplicationId", value = "项目类型id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "workContentId", value = "工作内容id", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipList")
    public Result flipListForChild(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "itemApplicationId", required = false, defaultValue = "") Integer itemApplicationId,
            @RequestParam(value = "workContentId", required = false, defaultValue = "") Integer workContentId
    ) {
        Page page = byPlusUnitConfigManager.search(itemApplicationId, workContentId, pageNo, pageSize);
        return Result.ok(page);
    }

}
