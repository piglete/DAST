package club.emergency.controller;

import club.emergency.project.manager.ByBStoreRecordManager;
import club.emergency.project.model.ByBStoreRecord;
import club.map.core.model.Page;
import club.map.core.web.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: zhaojinxiong
 * Date: 2018-12-12
 * Time: 14:31
 * Description:
 */
@Api("档案入库记录管理")
@CrossOrigin
@RestController
@RequestMapping("/store-record")
public class StoreRecordController {

    private ByBStoreRecordManager byBStoreRecordManager;

    public StoreRecordController(ByBStoreRecordManager byBStoreRecordManager) {
        this.byBStoreRecordManager = byBStoreRecordManager;
    }

    @ApiOperation("查询档案入库记录信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "storeroomCode", value = "库房code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fileNumber", value = "档案号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "recordTypeName", value = "档案类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "departmentName", value = "部门名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "recordState", value = "档案状态(存在/借出)", dataType = "string", paramType = "query")
    })
    @PostMapping("/flipList")
    public Result flipList(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "storeroomCode", defaultValue = "") String storeroomCode,
            @RequestParam(value = "fileNumber", defaultValue = "") String fileNumber,
            @RequestParam(value = "recordTypeName", defaultValue = "") String recordTypeName,
            @RequestParam(value = "departmentName", defaultValue = "") String departmentName,
            @RequestParam(value = "recordState", defaultValue = "") String recordState
    ) {
        Page page = byBStoreRecordManager.search(storeroomCode, fileNumber, recordTypeName, departmentName, recordState, pageNo, pageSize);
        return Result.ok(page);
    }

    @ApiOperation("档案入库记录管理 - 编辑档案入库记录信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "recordId", value = "档案id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "companyName", value = "单位名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fileNumber", value = "档案号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "recordTypeName", value = "档案类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "departmentName", value = "部门名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "storeroomCode", value = "库房code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "operator", value = "操作员", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "recordState", value = "档案状态(存在/借出)", dataType = "string", paramType = "query")
    })
    @PostMapping("/edit")
    public Result edit(
            @RequestParam(value = "id", defaultValue = "") Integer id,
            @RequestParam(value = "recordId", defaultValue = "") Integer recordId,
            @RequestParam(value = "companyName", defaultValue = "") String companyName,
            @RequestParam(value = "fileNumber", defaultValue = "") String fileNumber,
            @RequestParam(value = "recordTypeName", defaultValue = "") String recordTypeName,
            @RequestParam(value = "departmentName", defaultValue = "") String departmentName,
            @RequestParam(value = "storeroomCode", defaultValue = "") String storeroomCode,
            @RequestParam(value = "operator", defaultValue = "") String operator,
            @RequestParam(value = "remark", defaultValue = "") String remark,
            @RequestParam(value = "recordState", defaultValue = "存在") String recordState
    ) {
        ByBStoreRecord byBStoreRecord = new ByBStoreRecord();
        if (id != null) {
            byBStoreRecord.setId(id);
        }
        byBStoreRecord.setRecordId(recordId);
        byBStoreRecord.setCompanyName(companyName);
        byBStoreRecord.setFileNumber(fileNumber);
        byBStoreRecord.setRecordTypeName(recordTypeName);
        byBStoreRecord.setDepartmentName(departmentName);
        byBStoreRecord.setStoreroomCode(storeroomCode);
        byBStoreRecord.setOperator(operator);
        byBStoreRecord.setRemark(remark);
        byBStoreRecord.setRecordState(recordState);
        byBStoreRecordManager.upperSave(byBStoreRecord);
        return Result.ok();
    }

    @ApiOperation("档案入库记录详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @PostMapping("/detail")
    public Result detail(
            @RequestParam(value = "id", defaultValue = "") Integer id
    ) {
        ByBStoreRecord byBStoreRecord = byBStoreRecordManager.searchDetails(id);
        return Result.ok(byBStoreRecord);
    }

    @ApiOperation("档案入库记录管理 - 删除档案入库记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", dataType = "string", paramType = "query")
    })
    @PostMapping("/remove")
    public Result remove(
            @RequestParam(value = "ids") String ids
    ) {
        byBStoreRecordManager.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("档案入库记录管理 - 档案入库记录导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileName", value = "导出文件名称", dataType = "string", paramType = "query")
    })
    @GetMapping("/export")
    public Result export(
            @RequestParam(value = "fileName", defaultValue = "档案入库记录") String fileName,
            HttpServletResponse response
    ) {
        byBStoreRecordManager.export(fileName, response);
        return Result.ok();
    }
}
