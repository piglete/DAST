package club.emergency.controller;

import club.emergency.by_b_region.model.ByBRegion;
import club.emergency.by_b_storeroom.manager.ByBStoreroomManager;
import club.emergency.by_b_storeroom.model.ByBStoreroom;
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
 * Date: 2018-12-12
 * Time: 11:01
 * Description:
 */
@Api("库房管理")
@CrossOrigin
@RestController
@RequestMapping("/storeroom")
public class StoreroomController {

    private ByBStoreroomManager byBStoreroomManager;

    public StoreroomController(ByBStoreroomManager byBStoreroomManager) {
        this.byBStoreroomManager = byBStoreroomManager;
    }

    @ApiOperation("查询库房树（树形数据)")
    @PostMapping("/treeData")
    public Result treeData() {
        List<ByBStoreroom> byBStoreroomList = byBStoreroomManager.treeSearch();
        return Result.ok(byBStoreroomList);
    }

    @ApiOperation("查询库房（无分页)")
    @PostMapping("/flipList")
    public Result flipList() {
        List<ByBStoreroom> byBStoreroomList = byBStoreroomManager.search();
        return Result.ok(byBStoreroomList);
    }

    @ApiOperation("库房管理 - 编辑库房信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "库房位置名称", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "code", value = "code(模糊查询)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "parentId", value = "父id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "sortNum", value = "排序", dataType = "int", paramType = "query")
    })
    @PostMapping("/edit")
    public Result edit(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "code", defaultValue = "") String code,
            @RequestParam(value = "parentId", defaultValue = "") Integer parentId,
            @RequestParam(value = "sortNum", defaultValue = "") Integer sortNum
    ) {
        ByBStoreroom byBStoreroom = new ByBStoreroom();
        if (id != null) {
            byBStoreroom.setId(id);
        }
        byBStoreroom.setName(name);
        byBStoreroom.setCode(code);
        byBStoreroom.setParentId(parentId);
        byBStoreroom.setSortNum(sortNum);
        byBStoreroomManager.upperSave(byBStoreroom);
        return Result.ok();
    }

    @ApiOperation("库房信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @PostMapping("/detail")
    public Result detail(
            @RequestParam(value = "id") Integer id
    ) {
        ByBStoreroom byBStoreroom = byBStoreroomManager.searchDetails(id);
        return Result.ok(byBStoreroom);
    }

    @ApiOperation("库房管理 - 删除库房信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", dataType = "string", paramType = "query")
    })
    @PostMapping("/remove")
    public Result remove(
            @RequestParam(value = "ids") String ids
    ) {
        byBStoreroomManager.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("根据code查询库房")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "库房code", dataType = "string", paramType = "query")
    })
    @PostMapping("/detailByCode")
    public Result detailByCode(
            @RequestParam(value = "code", defaultValue = "") String code
    ) {
        ByBStoreroom byBStoreroom = byBStoreroomManager.searchByCode(code);
        return Result.ok(byBStoreroom);
    }
}
