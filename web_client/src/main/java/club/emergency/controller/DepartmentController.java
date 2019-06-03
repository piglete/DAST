package club.emergency.controller;

import club.emergency.by_b_department.manager.ByBDepartmentManager;
import club.emergency.by_b_department.model.ByBDepartment;
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
 * Date: 2018-11-13
 * Time: 15:36
 * Description:
 */
@Api("部门管理")
@CrossOrigin
@RestController
@RequestMapping("/department")
public class DepartmentController {

    private ByBDepartmentManager byBDepartmentManager;

    public DepartmentController(ByBDepartmentManager byBDepartmentManager) {
        this.byBDepartmentManager = byBDepartmentManager;
    }

    @ApiOperation("查询部门（树形数据)")
    @PostMapping("/treeData")
    public Result treeData() {
        List<ByBDepartment> byBDepartmentList = byBDepartmentManager.treeSearch();
        return Result.ok(byBDepartmentList);
    }

    @ApiOperation("查询部门（无分页)")
    @PostMapping("/flipList")
    public Result flipList() {
        List<ByBDepartment> byBDepartmentList = byBDepartmentManager.search();
        return Result.ok(byBDepartmentList);
    }

    @ApiOperation("查询部门所有子节点（无分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "invalidFlag", value = "部门作废标识(0为未作废,1为已作废),默认为0", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipListForChild")
    public Result flipListForChild(
            @RequestParam(value = "invalidFlag", defaultValue = "0") Integer invalidFlag
    ) {
        List<ByBDepartment> byBDepartmentList = byBDepartmentManager.searchForChild(invalidFlag);
        return Result.ok(byBDepartmentList);
    }

    @ApiOperation("查询一级部门（无分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "releaseFlag", value = "可下达任务部门标识(0为不可以下达,1为可以下达)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "去除的部门code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "invalidFlag", value = "部门作废标识(0为未作废,1为已作废),默认为0", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipListForParent")
    public Result flipListForParent(
            @RequestParam(value = "releaseFlag", defaultValue = "") Integer releaseFlag,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode,
            @RequestParam(value = "invalidFlag", defaultValue = "0") Integer invalidFlag
    ) {
        List<ByBDepartment> byBDepartmentList = byBDepartmentManager.searchForParent(releaseFlag, departmentCode, invalidFlag);
        return Result.ok(byBDepartmentList);
    }

    @ApiOperation("查询部门下的小组（无分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "部门code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "invalidFlag", value = "部门作废标识(0为在使用,1为已作废),默认为0,查询在使用的", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipListForGroup")
    public Result flipListForGroup(
            @RequestParam(value = "code", defaultValue = "") String code,
            @RequestParam(value = "invalidFlag", defaultValue = "0") Integer invalidFlag
    ) {
        List<ByBDepartment> byBDepartmentList = byBDepartmentManager.searchForGroup(code, invalidFlag);
        return Result.ok(byBDepartmentList);
    }

    @ApiOperation("查询所有的小组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "releaseFlag", value = "可下达任务部门标识(0为不可以下达,1为可以下达)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "invalidFlag", value = "部门作废标识(0为在使用,1为已作废),默认为0,查询在使用的", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipListForAllGroup")
    public Result flipListForAllGroup(
            @RequestParam(value = "releaseFlag", defaultValue = "1") Integer releaseFlag,
            @RequestParam(value = "invalidFlag", defaultValue = "0") Integer invalidFlag
    ) {
        List<ByBDepartment> byBDepartmentList = byBDepartmentManager.searchForAllGroup(releaseFlag, invalidFlag);
        return Result.ok(byBDepartmentList);
    }


    @ApiOperation("部门管理 - 编辑部门信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "departmentName", value = "部门名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "code", value = "部门code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "parentId", value = "父id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "description", value = "部门描述", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sortNum", value = "排序", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "releaseFlag", value = "可下达任务部门标识(0为不可以下达,1为可以下达)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "invalidFlag", value = "部门作废标识(0为未作废,1为已作废)", dataType = "int", paramType = "query")
    })
    @PostMapping("/edit")
    public Result edit(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "departmentName", defaultValue = "") String departmentName,
            @RequestParam(value = "code", defaultValue = "") String code,
            @RequestParam(value = "parentId", defaultValue = "") Integer parentId,
            @RequestParam(value = "description", defaultValue = "") String description,
            @RequestParam(value = "sortNum", defaultValue = "") Integer sortNum,
            @RequestParam(value = "releaseFlag", defaultValue = "0") Integer releaseFlag,
            @RequestParam(value = "invalidFlag", defaultValue = "0") Integer invalidFlag
    ) {
        ByBDepartment byBDepartment = new ByBDepartment();
        if (id != null) {
            byBDepartment.setId(id);
        }
        byBDepartment.setDepartmentName(departmentName);
        byBDepartment.setCode(code);
        byBDepartment.setParentId(parentId);
        byBDepartment.setSortNum(sortNum);
        byBDepartment.setDescription(description);
        byBDepartment.setReleaseFlag(releaseFlag);
        byBDepartment.setInvalidFlag(invalidFlag);
        byBDepartmentManager.upperSave(byBDepartment);
        return Result.ok();
    }

    @ApiOperation("部门信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @PostMapping("/detail")
    public Result detail(
            @RequestParam(value = "id", defaultValue = "") Integer id
    ) {
        return Result.ok(byBDepartmentManager.get(id));
    }

    @ApiOperation("部门作废处理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @PostMapping("/editForDepartmentInvalid")
    public Result editForDepartmentInvalid(
            @RequestParam(value = "id", defaultValue = "") Integer id
    ) {
        byBDepartmentManager.updateDepartmentInvalid(id);
        return Result.ok();
    }

    @ApiOperation("部门管理 - 删除部门信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", dataType = "string", paramType = "query")
    })
    @PostMapping("/remove")
    public Result remove(
            @RequestParam(value = "ids") String ids
    ) {
        byBDepartmentManager.removeByIds(ids);
        return Result.ok();
    }

}
