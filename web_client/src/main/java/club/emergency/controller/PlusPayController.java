package club.emergency.controller;

import club.emergency.project.manager.ByPlusPayManager;
import club.emergency.project.model.ByPlusPay;
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
 * Date: 2018-12-29
 * Time: 10:05
 * Description:
 */
@Api("项目交付管理")
@CrossOrigin
@RestController
@RequestMapping("/plus-pay")
public class PlusPayController {

    private ByPlusPayManager byPlusPayManager;

    public PlusPayController(ByPlusPayManager byPlusPayManager) {
        this.byPlusPayManager = byPlusPayManager;
    }

    @ApiOperation("查询项目交付信息(无分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipList")
    public Result flipList(
            @RequestParam(value = "projectId", defaultValue = "") Integer projectId
    ) {
        List<ByPlusPay> byPlusPayList = byPlusPayManager.search(projectId);
        return Result.ok(byPlusPayList);
    }

    @ApiOperation("项目交付管理 - 编辑项目交付信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "payTypeId", value = "交付类型id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "payUser", value = "办理人员", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "所属部门code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "payCondition", value = "交付情况", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query")
    })
    @PostMapping("/edit")
    public Result edit(
            @RequestParam(value = "id", defaultValue = "") Integer id,
            @RequestParam(value = "projectId", defaultValue = "") Integer projectId,
            @RequestParam(value = "payTypeId", defaultValue = "") Integer payTypeId,
            @RequestParam(value = "payUser", defaultValue = "") String payUser,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode,
            @RequestParam(value = "payCondition", defaultValue = "") String payCondition,
            @RequestParam(value = "remark", defaultValue = "") String remark
    ) {
        ByPlusPay byPlusPay = new ByPlusPay();
        if (id != null) {
            byPlusPay.setId(id);
        }
        byPlusPay.setProjectId(projectId);
        byPlusPay.setPayTypeId(payTypeId);
        byPlusPay.setPayUser(payUser);
        byPlusPay.setDepartmentCode(departmentCode);
        byPlusPay.setPayCondition(payCondition);
        byPlusPay.setRemark(remark);
        byPlusPayManager.upperSave(byPlusPay);
        return Result.ok();
    }

    @ApiOperation("项目交付信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @PostMapping("/detail")
    public Result detail(
            @RequestParam(value = "id", defaultValue = "") Integer id
    ) {
        ByPlusPay byPlusPay = byPlusPayManager.searchById(id);
        return Result.ok(byPlusPay);
    }

    @ApiOperation("项目交付管理 - 删除项目交付信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", dataType = "string", paramType = "query"),
    })
    @PostMapping("/remove")
    public Result remove(
            @RequestParam(value = "ids") String ids
    ) {
        byPlusPayManager.removeByIds(ids);
        return Result.ok();
    }
}
