package club.emergency.controller;

import club.emergency.project.manager.ByRRecordRevertManager;
import club.emergency.project.model.ByRRecordRevert;
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
 * Date: 2018-12-14
 * Time: 8:33
 * Description:
 */
@Api("档案归还记录管理")
@CrossOrigin
@RestController
@RequestMapping("/record-revert")
public class RecordRevertController {

    private ByRRecordRevertManager byRRecordRevertManager;

    public RecordRevertController(ByRRecordRevertManager byRRecordRevertManager) {
        this.byRRecordRevertManager = byRRecordRevertManager;
    }

    @ApiOperation("查询档案归还记录信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "recordTypeName", value = "档案类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "revertPerson", value = "归还人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "revertState", value = "归还状态(按期归还/逾期归还)", dataType = "string", paramType = "query")
    })
    @PostMapping("/flipList")
    public Result flipList(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "recordTypeName", defaultValue = "") String recordTypeName,
            @RequestParam(value = "revertPerson", defaultValue = "") String revertPerson,
            @RequestParam(value = "revertState", defaultValue = "") String revertState
    ) {
        Page page = byRRecordRevertManager.search(recordTypeName, revertPerson, revertState, pageNo, pageSize);
        return Result.ok(page);
    }

    @ApiOperation("档案归还记录管理 - 编辑档案归还记录信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "storeRecordId", value = "档案入库记录id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "recordId", value = "档案id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "companyName", value = "单位名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fileNumber", value = "档案号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "recordTypeName", value = "档案类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "departmentName", value = "部门名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "revertPerson", value = "归还人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "revertDate", value = "归还日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "revertState", value = "归还状态(按期归还/逾期归还)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "manager", value = "管理员", dataType = "string", paramType = "query"),
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
            @RequestParam(value = "revertPerson", defaultValue = "") String revertPerson,
            @RequestParam(value = "revertDate", defaultValue = "") String revertDate,
            @RequestParam(value = "revertState", defaultValue = "") String revertState,
            @RequestParam(value = "manager", defaultValue = "") String manager,
            @RequestParam(value = "remark", defaultValue = "") String remark
    ) {
        ByRRecordRevert byRRecordRevert = new ByRRecordRevert();
        if (id != null) {
            byRRecordRevert.setId(id);
        }
        byRRecordRevert.setStoreRecordId(storeRecordId);
        byRRecordRevert.setRecordId(recordId);
        byRRecordRevert.setCompanyName(companyName);
        byRRecordRevert.setFileNumber(fileNumber);
        byRRecordRevert.setRecordTypeName(recordTypeName);
        byRRecordRevert.setDepartmentName(departmentName);
        byRRecordRevert.setRevertPerson(revertPerson);
        byRRecordRevert.setRevertDate(revertDate);
        byRRecordRevert.setRevertState(revertState);
        byRRecordRevert.setManager(manager);
        byRRecordRevert.setRemark(remark);
        byRRecordRevertManager.upperSave(byRRecordRevert);
        return Result.ok();
    }

    @ApiOperation("档案归还记录详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @PostMapping("/detail")
    public Result detail(
            @RequestParam(value = "id", defaultValue = "") Integer id
    ) {
        ByRRecordRevert byRRecordRevert = byRRecordRevertManager.get(id);
        return Result.ok(byRRecordRevert);
    }

    @ApiOperation("档案归还记录管理 - 删除档案归还记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", dataType = "string", paramType = "query")
    })
    @PostMapping("/remove")
    public Result remove(
            @RequestParam(value = "ids") String ids
    ) {
        byRRecordRevertManager.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("档案归还记录管理 - 归还记录导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileName", value = "导出文件名称", dataType = "string", paramType = "query")
    })
    @GetMapping("/export")
    public Result export(
            @RequestParam(value = "fileName", defaultValue = "归还记录") String fileName,
            HttpServletResponse response
    ) {
        byRRecordRevertManager.export(fileName, response);
        return Result.ok();
    }
}
