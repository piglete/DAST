package club.emergency.controller;

import club.emergency.project.manager.ByRRecordUseManager;
import club.emergency.project.model.ByRRecordUse;
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
 * Time: 16:14
 * Description:
 */
@Api("档案借出记录管理")
@CrossOrigin
@RestController
@RequestMapping("/record-use")
public class RecordBorrowController {

    private ByRRecordUseManager byRRecordUseManager;

    public RecordBorrowController(ByRRecordUseManager byRRecordUseManager) {
        this.byRRecordUseManager = byRRecordUseManager;
    }

    @ApiOperation("查询档案借出记录信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "recordTypeName", value = "档案类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "purpose", value = "借阅用途", dataType = "string", paramType = "query"),
    })
    @PostMapping("/flipList")
    public Result flipList(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "recordTypeName", defaultValue = "") String recordTypeName,
            @RequestParam(value = "purpose", defaultValue = "") String purpose
    ) {
        Page page = byRRecordUseManager.search(recordTypeName, purpose, pageNo, pageSize);
        return Result.ok(page);
    }

    @ApiOperation("档案借出记录管理 - 编辑档案借出记录信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "storeRecordId", value = "档案入库记录id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "recordId", value = "档案id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "companyName", value = "单位名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fileNumber", value = "档案号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "recordTypeName", value = "档案类型名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "departmentName", value = "部门名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "borrower", value = "借用人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "purpose", value = "借阅用途", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "borrowDate", value = "借阅日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "manager", value = "管理员", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "expectedReturnDate", value = "应归还日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query")
    })
    @PostMapping("/edit")
    public Result edit(
            @RequestParam(value = "id", defaultValue = "") Integer id,
            @RequestParam(value = "storeRecordId", defaultValue = "") Integer storeRecordId,
            @RequestParam(value = "recordId", defaultValue = "") Integer recordId,
            @RequestParam(value = "companyName", defaultValue = "") String companyName,
            @RequestParam(value = "fileNumber", defaultValue = "") String fileNumber,
            @RequestParam(value = "recordTypeName", defaultValue = "") String recordTypeName,
            @RequestParam(value = "departmentName", defaultValue = "") String departmentName,
            @RequestParam(value = "borrower", defaultValue = "") String borrower,
            @RequestParam(value = "purpose", defaultValue = "") String purpose,
            @RequestParam(value = "borrowDate", defaultValue = "") String borrowDate,
            @RequestParam(value = "manager", defaultValue = "") String manager,
            @RequestParam(value = "expectedReturnDate", defaultValue = "") String expectedReturnDate,
            @RequestParam(value = "remark", defaultValue = "") String remark
    ) {
        ByRRecordUse byRRecordUse = new ByRRecordUse();
        if (id != null) {
            byRRecordUse.setId(id);
        }
        byRRecordUse.setStoreRecordId(storeRecordId);
        byRRecordUse.setRecordId(recordId);
        byRRecordUse.setCompanyName(companyName);
        byRRecordUse.setFileNumber(fileNumber);
        byRRecordUse.setRecordTypeName(recordTypeName);
        byRRecordUse.setDepartmentName(departmentName);
        byRRecordUse.setBorrower(borrower);
        byRRecordUse.setPurpose(purpose);
        byRRecordUse.setBorrowDate(borrowDate);
        byRRecordUse.setManager(manager);
        byRRecordUse.setExpectedReturnDate(expectedReturnDate);
        byRRecordUse.setRemark(remark);
        byRRecordUseManager.upperSave(byRRecordUse);
        return Result.ok();
    }

    @ApiOperation("档案借出记录详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @PostMapping("/detail")
    public Result detail(
            @RequestParam(value = "id", defaultValue = "") Integer id
    ) {
        ByRRecordUse byRRecordUse = byRRecordUseManager.get(id);
        return Result.ok(byRRecordUse);
    }

    @ApiOperation("档案借出记录管理 - 删除档案借出记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", dataType = "string", paramType = "query")
    })
    @PostMapping("/remove")
    public Result remove(
            @RequestParam(value = "ids") String ids
    ) {
        byRRecordUseManager.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("档案借出记录管理 - 借出记录导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileName", value = "导出文件名称", dataType = "string", paramType = "query")
    })
    @GetMapping("/export")
    public Result export(
            @RequestParam(value = "fileName", defaultValue = "借出记录") String fileName,
            HttpServletResponse response
    ) {
        byRRecordUseManager.export(fileName, response);
        return Result.ok();
    }
}
