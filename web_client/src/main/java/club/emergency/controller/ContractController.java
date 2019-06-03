package club.emergency.controller;

import club.emergency.by_b_contract.manager.ByBContractManager;
import club.emergency.by_b_contract.model.ByBContract;
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
 * Time: 8:35
 * Description:
 */
@Api("合同管理")
@CrossOrigin
@RestController
@RequestMapping("/contract")
public class ContractController {

    private ByBContractManager byBContractManager;

    public ContractController(ByBContractManager byBContractManager) {
        this.byBContractManager = byBContractManager;
    }

    @ApiOperation("查询合同信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "projectName", value = "项目名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "putFileTime", value = "入档日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "companyName", value = "单位名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "contractAmount", value = "合同金额", dataType = "float", paramType = "query"),
            @ApiImplicitParam(name = "contractTypeId", value = "合同类型id", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipList")
    public Result flipList(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "projectName", defaultValue = "") String projectName,
            @RequestParam(value = "putFileTime", defaultValue = "") String putFileTime,
            @RequestParam(value = "companyName", defaultValue = "") String companyName,
            @RequestParam(value = "contractAmount", defaultValue = "") Float contractAmount,
            @RequestParam(value = "contractTypeId", defaultValue = "") Integer contractTypeId
    ) {
        Page page = byBContractManager.search(projectName, putFileTime, companyName, contractAmount, contractTypeId, pageNo, pageSize);
        return Result.ok(page);
    }

    @ApiOperation("合同管理 - 编辑合同信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "contractNumber", value = "合同编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "companyName", value = "单位名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "projectName", value = "项目名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "signTime", value = "签订日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "putFileTime", value = "入档日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "contractAmount", value = "合同金额", dataType = "float", paramType = "query"),
            @ApiImplicitParam(name = "contractType", value = "合同类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "contractTypeId", value = "合同类型id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "projectType", value = "项目类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "projectTypeId", value = "项目类型id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "contractFile", value = "电子附件", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "storeAddress", value = "存放位置", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "描述", dataType = "string", paramType = "query")
    })
    @PostMapping("/edit")
    public Result edit(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "contractNumber", defaultValue = "") String contractNumber,
            @RequestParam(value = "companyName", defaultValue = "") String companyName,
            @RequestParam(value = "projectName", defaultValue = "") String projectName,
            @RequestParam(value = "signTime", defaultValue = "") String signTime,
            @RequestParam(value = "putFileTime", defaultValue = "") String putFileTime,
            @RequestParam(value = "contractAmount", defaultValue = "") Float contractAmount,
            @RequestParam(value = "contractType", defaultValue = "") String contractType,
            @RequestParam(value = "contractTypeId", defaultValue = "") Integer contractTypeId,
            @RequestParam(value = "projectType", defaultValue = "") String projectType,
            @RequestParam(value = "projectTypeId", defaultValue = "") Integer projectTypeId,
            @RequestParam(value = "contractFile", defaultValue = "") String contractFile,
            @RequestParam(value = "storeAddress", defaultValue = "") String storeAddress,
            @RequestParam(value = "remark", defaultValue = "") String remark
    ) {
        ByBContract byBContract = new ByBContract();
        if (id != null) {
            byBContract.setId(id);
        }
        byBContract.setContractNumber(contractNumber);
        byBContract.setCompanyName(companyName);
        byBContract.setProjectName(projectName);
        byBContract.setSignTime(signTime);
        byBContract.setPutFileTime(putFileTime);
        byBContract.setContractAmount(contractAmount);
        byBContract.setContractType(contractType);
        byBContract.setContractTypeId(contractTypeId);
        byBContract.setProjectType(projectType);
        byBContract.setProjectTypeId(projectTypeId);
        byBContract.setContractFile(contractFile);
        byBContract.setStoreAddress(storeAddress);
        byBContract.setRemark(remark);
        byBContractManager.upperSave(byBContract);
        return Result.ok();
    }

    @ApiOperation("合同信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @PostMapping("/detail")
    public Result detail(
            @RequestParam(value = "id", defaultValue = "") Integer id
    ) {
        return Result.ok(byBContractManager.get(id));
    }

    @ApiOperation("合同管理 - 删除合同信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", dataType = "string", paramType = "query")
    })
    @PostMapping("/remove")
    public Result remove(
            @RequestParam(value = "ids") String ids
    ) {
        byBContractManager.removeByIds(ids);
        return Result.ok();
    }

}
