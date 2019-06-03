package club.emergency.controller;

import club.emergency.dictionary.manager.ByBDictionaryChildManager;
import club.emergency.dictionary.manager.ByBDictionaryParentManager;
import club.emergency.dictionary.model.ByBDictionaryChild;
import club.emergency.dictionary.model.ByBDictionaryParent;
import club.map.core.model.Page;
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
 * Time: 10:43
 * Description:
 */
@Api("字典管理")
@CrossOrigin
@RestController
@RequestMapping("/dictionary")
public class DictionaryController {

    private ByBDictionaryParentManager byBDictionaryParentManager;
    private ByBDictionaryChildManager byBDictionaryChildManager;

    public DictionaryController(ByBDictionaryParentManager byBDictionaryParentManager,
                                ByBDictionaryChildManager byBDictionaryChildManager) {
        this.byBDictionaryParentManager = byBDictionaryParentManager;
        this.byBDictionaryChildManager = byBDictionaryChildManager;
    }

    @ApiOperation("查询父字典数据（树状结构）")
    @PostMapping("/treeDataForParent")
    public Result treeDataForParent() {
        List treeDataForParent = byBDictionaryParentManager.treeDataForParent();
        return Result.ok(treeDataForParent);
    }

    @ApiOperation("字典管理 - 编辑父字典表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "keyName", value = "键名（查询用）", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "alias", value = "中文名(别名)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "parentId", value = "父id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "description", value = "描述", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "code", value = "字典code", dataType = "string", paramType = "query"),
    })
    @PostMapping("/editForParent")
    public Result editForParent(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "keyName", defaultValue = "") String keyName,
            @RequestParam(value = "alias", defaultValue = "") String alias,
            @RequestParam(value = "parentId", defaultValue = "") Integer parentId,
            @RequestParam(value = "description", required = false, defaultValue = "") String description,
            @RequestParam(value = "code", required = false, defaultValue = "") String code
    ) {
        ByBDictionaryParent byBDictionaryParent = new ByBDictionaryParent();
        if (id != null) {
            byBDictionaryParent.setId(id);
        }
        byBDictionaryParent.setKeyName(keyName);
        byBDictionaryParent.setAlias(alias);
        byBDictionaryParent.setParentId(parentId);
        byBDictionaryParent.setDescription(description);
        byBDictionaryParent.setCode(code);
        byBDictionaryParentManager.upperSave(byBDictionaryParent);
        return Result.ok();
    }

    @ApiOperation("查看父字典详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @PostMapping("/detailForParent")
    public Result detailForParent(
            @RequestParam("id") Integer id
    ) {
        return Result.ok(byBDictionaryParentManager.get(id));
    }

    @ApiOperation("字典管理 - 删除父字典表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "父字典id", dataType = "int", paramType = "query")
    })
    @PostMapping("/removeForParent")
    public Result removeForParent(
            @RequestParam("id") Integer id
    ) {
        byBDictionaryParentManager.removeById(id);
        return Result.ok();
    }

    @ApiOperation("查询子字典表信息(通过父字典表code)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dictionaryCode", value = "父字典code", dataType = "string", paramType = "query")
    })
    @PostMapping("/flipListForChildByParentCode")
    public Result flipListForChildByParentCode(
            @RequestParam(value = "dictionaryCode", required = false, defaultValue = "") String dictionaryCode
    ) {
        List<ByBDictionaryChild> dictionaryChildrenList = byBDictionaryChildManager.searchByDictionaryCode(dictionaryCode);
        return Result.ok(dictionaryChildrenList);
    }

    @ApiOperation("查询子字典表信息(根据父表名称获取,此方法只针对获取成果类型)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "alias", value = "名称", dataType = "string", paramType = "query")
    })
    @PostMapping("/flipListForChildByParentAlias")
    public Result flipListForChildByParentAlias(
            @RequestParam(value = "alias", required = false, defaultValue = "") String alias
    ) {
        List<ByBDictionaryChild> dictionaryChildrenList = byBDictionaryChildManager.searchByParentAlias(alias);
        return Result.ok(dictionaryChildrenList);
    }

    @ApiOperation("查询子字典表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "alias", value = "别名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "dictionaryCode", value = "父字典code", dataType = "string", paramType = "query")
    })
    @PostMapping("/flipListForChild")
    public Result flipListForChild(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "alias", required = false, defaultValue = "") String alias,
            @RequestParam(value = "dictionaryCode", required = false, defaultValue = "") String dictionaryCode
    ) {
        Page page = byBDictionaryChildManager.searchChild(alias, dictionaryCode, pageNo, pageSize);
        return Result.ok(page);
    }


    @ApiOperation("字典管理 - 编辑子字典")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "keyName", value = "键名（查询用）", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "alias", value = "别名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "dictionaryCode", value = "父字典表code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "iconUrl", value = "图标url", dataType = "string", paramType = "query")
    })
    @PostMapping("/editForChild")
    public Result editForChild(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "keyName", defaultValue = "") String keyName,
            @RequestParam(value = "alias", defaultValue = "") String alias,
            @RequestParam(value = "dictionaryCode", defaultValue = "") String dictionaryCode,
            @RequestParam(value = "iconUrl", defaultValue = "") String iconUrl
    ) {
        ByBDictionaryChild byBDictionaryChild = new ByBDictionaryChild();
        if (id != null) {
            byBDictionaryChild.setId(id);
        }
        byBDictionaryChild.setKeyName(keyName);
        byBDictionaryChild.setAlias(alias);
        byBDictionaryChild.setDictionaryCode(dictionaryCode);
        byBDictionaryChild.setIconUrl(iconUrl);
        byBDictionaryChildManager.upperSave(byBDictionaryChild);
        return Result.ok();
    }

    @ApiOperation("获得子字典详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "id", paramType = "query")
    })
    @PostMapping("/detailForChild")
    public Result detailForChild(
            @RequestParam("id") Integer id
    ) {
        return Result.ok(byBDictionaryChildManager.get(id));
    }

    @ApiOperation("字典管理 - 删除子字典表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", dataType = "string", paramType = "query")
    })
    @PostMapping("/removeForChild")
    public Result removeForChild(
            @RequestParam("ids") String ids
    ) {
        byBDictionaryChildManager.removeByIds(ids);
        return Result.ok();
    }
}
