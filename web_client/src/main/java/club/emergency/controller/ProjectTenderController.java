package club.emergency.controller;

import club.emergency.project.manager.ByBProjectTenderManager;
import club.emergency.project.model.ByBProjectTender;
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
 * Date: 2018-11-15
 * Time: 10:16
 * Description:
 */
@Api("项目标书管理")
@CrossOrigin
@RestController
@RequestMapping("/project-tender")
public class ProjectTenderController {

    private ByBProjectTenderManager byBProjectTenderManager;

    public ProjectTenderController(ByBProjectTenderManager byBProjectTenderManager) {
        this.byBProjectTenderManager = byBProjectTenderManager;
    }

    @ApiOperation("查询项目标书信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "projectName", value = "项目名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "agentCompany", value = "代理公司", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "openTime", value = "开标时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "openAddress", value = "开标地址", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "bond", value = "保证金", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "bondTime", value = "保证金时间", dataType = "string", paramType = "query")
    })
    @PostMapping("/flipList")
    public Result flipList(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "projectName", defaultValue = "") String projectName,
            @RequestParam(value = "agentCompany", defaultValue = "") String agentCompany,
            @RequestParam(value = "openTime", defaultValue = "") String openTime,
            @RequestParam(value = "openAddress", defaultValue = "") String openAddress,
            @RequestParam(value = "bond", defaultValue = "") String bond,
            @RequestParam(value = "bondTime", defaultValue = "") String bondTime
    ) {
        Page page = byBProjectTenderManager.search(projectName, agentCompany, openTime, openAddress, bond, bondTime, pageNo, pageSize);
        return Result.ok(page);
    }

    @ApiOperation("项目标书管理 - 编辑项目标书信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "projectName", value = "项目名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "agentCompany", value = "代理公司", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "bondTime", value = "保证金时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "bond", value = "保证金", dataType = "float", paramType = "query"),
            @ApiImplicitParam(name = "openTime", value = "开标时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "openAddress", value = "开标地址", dataType = "string", paramType = "query")
    })
    @PostMapping("/edit")
    public Result edit(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "projectName", defaultValue = "") String projectName,
            @RequestParam(value = "agentCompany", defaultValue = "") String agentCompany,
            @RequestParam(value = "bondTime", defaultValue = "") String bondTime,
            @RequestParam(value = "bond", defaultValue = "") Float bond,
            @RequestParam(value = "openTime", defaultValue = "") String openTime,
            @RequestParam(value = "openAddress", defaultValue = "") String openAddress
    ) {
        ByBProjectTender byBProjectTender=new ByBProjectTender();
        if(id != null){
            byBProjectTender.setId(id);
        }
        byBProjectTender.setProjectName(projectName);
        byBProjectTender.setAgentCompany(agentCompany);
        byBProjectTender.setBondTime(bondTime);
        byBProjectTender.setBond(bond);
        byBProjectTender.setOpenTime(openTime);
        byBProjectTender.setOpenAddress(openAddress);
        byBProjectTenderManager.upperSave(byBProjectTender);
        return Result.ok();
    }

    @ApiOperation("项目标书信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @PostMapping("/detail")
    public Result detail(
            @RequestParam(value = "id", defaultValue = "") Integer id
    ) {
        return Result.ok(byBProjectTenderManager.get(id));
    }

    @ApiOperation("项目标书管理 - 删除项目标书信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", dataType = "string", paramType = "query")
    })
    @PostMapping("/remove")
    public Result remove(
            @RequestParam(value = "ids") String ids
    ) {
        byBProjectTenderManager.removeByIds(ids);
        return Result.ok();
    }

}
