package club.emergency.controller;

import club.emergency.by_b_region.manager.ByBRegionManager;
import club.emergency.by_b_region.model.ByBRegion;
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
 * Date: 2018-11-17
 * Time: 9:10
 * Description:
 */
@Api("区域管理")
@CrossOrigin
@RestController
@RequestMapping("/region")
public class RegionController {

    private ByBRegionManager byBRegionManager;

    public RegionController(ByBRegionManager byBRegionManager) {
        this.byBRegionManager = byBRegionManager;
    }

    @ApiOperation("查询区域（树形数据)")
    @PostMapping("/treeData")
    public Result treeData() {
        List<ByBRegion> byBRegionList = byBRegionManager.treeSearch();
        return Result.ok(byBRegionList);
    }

    /**
     * 查询区域树
     *
     * @return
     */
    @ApiOperation("查询区域（区域管理树list)")
    @PostMapping("/treeDataList")
    public Result treeDataList() {
        List<ByBRegion> byBRegionList = byBRegionManager.treeList();
        return Result.ok(byBRegionList);
    }

    /**
     * 查询所有区,在service查询时加了参数设置
     *
     * @return
     */
    @ApiOperation("查询所有区（无分页)")
    @PostMapping("/flipList")
    public Result flipList() {
        List<ByBRegion> byBRegionList = byBRegionManager.search();
        return Result.ok(byBRegionList);
    }

    @ApiOperation("区域管理 - 编辑区域信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "区域名称", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "code", value = "上级区域code", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "parentId", value = "父id", required = true, dataType = "int", paramType = "query"),
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
        ByBRegion byBRegion = new ByBRegion();
        if (id != null) {
            byBRegion.setId(id);
        }
        byBRegion.setName(name);
        byBRegion.setCode(code);
        byBRegion.setParentId(parentId);
        byBRegion.setSortNum(sortNum);
        byBRegionManager.upperSave(byBRegion);
        return Result.ok();
    }

    @ApiOperation("区域信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @PostMapping("/detail")
    public Result detail(
            @RequestParam(value = "id") Integer id
    ) {
        ByBRegion byBRegion = byBRegionManager.searchDetails(id);
        return Result.ok(byBRegion);
    }

    @ApiOperation("区域管理 - 删除区域信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", dataType = "string", paramType = "query")
    })
    @PostMapping("/remove")
    public Result remove(
            @RequestParam(value = "ids") String ids
    ) {
        byBRegionManager.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("根据code查询区域")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "区域code", dataType = "string", paramType = "query")
    })
    @PostMapping("/detailByCode")
    public Result detailByCode(
            @RequestParam(value = "code", defaultValue = "") String code
    ) {
        ByBRegion byBRegion = byBRegionManager.searchByCode(code);
        return Result.ok(byBRegion);
    }

    @ApiOperation("查询该code区域下的所有子节点")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "父级区域code", dataType = "string", paramType = "query")
    })
    @PostMapping("/flipListChildRegion")
    public Result flipListChildRegion(
            @RequestParam(value = "code", defaultValue = "") String code
    ) {
        List<ByBRegion> byBRegionList = byBRegionManager.searchChildByCode(code);
        return Result.ok(byBRegionList);
    }
}
