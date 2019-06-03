package club.emergency.controller;

import club.emergency.project.manager.ByRRecordManager;
import club.emergency.project.model.ByRRecord;
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
 * Date: 2018-11-22
 * Time: 16:47
 * Description:
 */
@Api("档案管理")
@CrossOrigin
@RestController
@RequestMapping("/record")
public class RecordController {

    private ByRRecordManager byRRecordManager;

    public RecordController(ByRRecordManager byRRecordManager) {
        this.byRRecordManager = byRRecordManager;
    }

    @ApiOperation("查询档案管理信息(分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "isStoreroom", value = "是否入库(0表示未入库,1表示已入库)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "fileNumber", value = "档案号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "companyName", value = "单位名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "projectName", value = "项目名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "recordTypeId", value = "档案类型id(项目类型id)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "fileDate", value = "归档日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "部门code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "retentionPeriodId", value = "保管期限id(字典)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "regionCode", value = "区域code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "yearType", value = "年份", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "monthType", value = "月份", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipList")
    public Result flipList(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "isStoreroom", defaultValue = "") Integer isStoreroom,
            @RequestParam(value = "fileNumber", defaultValue = "") String fileNumber,
            @RequestParam(value = "companyName", defaultValue = "") String companyName,
            @RequestParam(value = "projectName", defaultValue = "") String projectName,
            @RequestParam(value = "recordTypeId", defaultValue = "") Integer recordTypeId,
            @RequestParam(value = "fileDate", defaultValue = "") String fileDate,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode,
            @RequestParam(value = "retentionPeriodId", defaultValue = "") Integer retentionPeriodId,
            @RequestParam(value = "regionCode", defaultValue = "") String regionCode,
            @RequestParam(value = "yearType", defaultValue = "") Integer yearType,
            @RequestParam(value = "monthType", defaultValue = "") Integer monthType
    ) {
        Page page = byRRecordManager.search(isStoreroom, fileNumber, companyName, projectName, recordTypeId, fileDate, departmentCode, retentionPeriodId, regionCode, yearType, monthType, pageNo, pageSize);
        return Result.ok(page);
    }

    @ApiOperation("查询档案管理信息(未分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isStoreroom", value = "是否入库(0表示未入库,1表示已入库)", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipListWithoutPage")
    public Result flipListWithoutPage(
            @RequestParam(value = "isStoreroom", defaultValue = "") Integer isStoreroom
    ) {
        List<ByRRecord> byRRecordList = byRRecordManager.searchWithoutPage(isStoreroom);
        return Result.ok(byRRecordList);
    }

    @ApiOperation("档案管理 - 编辑档案信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "projectName", value = "项目名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "recordTypeId", value = "档案类型id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "recordTypeName", value = "档案类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fileNumber", value = "档案号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "dataNumber", value = "资料编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "来源部门code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "companyName", value = "单位名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "retentionPeriodId", value = "保管期限id(字典)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "retentionPeriod", value = "保管期限(仅前端页面显示时需要)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "volumeNumber", value = "卷数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageNumber", value = "页数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "scrollPerson", value = "立卷人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fileDate", value = "归档日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "examiner", value = "审核人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "responsiblePerson", value = "责任者", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "rankTypeId", value = "密级id(字典)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "rankType", value = "密级(仅前端页面显示时需要)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isStoreroom", value = "是否入库房(0为未入库,1为已入库)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "yearType", value = "年份", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "monthType", value = "月份", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "regionCode", value = "区域code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "regionName", value = "区域名称", dataType = "string", paramType = "query")
    })
    @PostMapping("/edit")
    public Result edit(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "projectId", defaultValue = "") Integer projectId,
            @RequestParam(value = "projectName", defaultValue = "") String projectName,
            @RequestParam(value = "recordTypeId", defaultValue = "") Integer recordTypeId,
            @RequestParam(value = "recordTypeName", defaultValue = "") String recordTypeName,
            @RequestParam(value = "fileNumber", defaultValue = "") String fileNumber,
            @RequestParam(value = "dataNumber", defaultValue = "") String dataNumber,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode,
            @RequestParam(value = "companyName", defaultValue = "") String companyName,
            @RequestParam(value = "retentionPeriodId", defaultValue = "") Integer retentionPeriodId,
            @RequestParam(value = "volumeNumber", defaultValue = "") Integer volumeNumber,
            @RequestParam(value = "pageNumber", defaultValue = "") Integer pageNumber,
            @RequestParam(value = "scrollPerson", defaultValue = "") String scrollPerson,
            @RequestParam(value = "fileDate", defaultValue = "") String fileDate,
            @RequestParam(value = "examiner", defaultValue = "") String examiner,
            @RequestParam(value = "responsiblePerson", defaultValue = "") String responsiblePerson,
            @RequestParam(value = "rankTypeId", defaultValue = "") Integer rankTypeId,
            @RequestParam(value = "remark", defaultValue = "") String remark,
            @RequestParam(value = "isStoreroom", defaultValue = "0") Integer isStoreroom,
            @RequestParam(value = "yearType", defaultValue = "") Integer yearType,
            @RequestParam(value = "monthType", defaultValue = "") Integer monthType,
            @RequestParam(value = "regionCode", defaultValue = "") String regionCode,
            @RequestParam(value = "regionName", defaultValue = "") String regionName
    ) {
        ByRRecord byRRecord = new ByRRecord();
        if (id != null) {
            byRRecord.setId(id);
        }
        byRRecord.setProjectId(projectId);
        byRRecord.setProjectName(projectName);
        byRRecord.setRecordTypeId(recordTypeId);
        byRRecord.setRecordTypeName(recordTypeName);
        byRRecord.setFileNumber(fileNumber);
        byRRecord.setDataNumber(dataNumber);
        byRRecord.setDepartmentCode(departmentCode);
        byRRecord.setCompanyName(companyName);
        byRRecord.setRetentionPeriodId(retentionPeriodId);
        byRRecord.setVolumeNumber(volumeNumber);
        byRRecord.setPageNumber(pageNumber);
        byRRecord.setScrollPerson(scrollPerson);
        byRRecord.setFileDate(fileDate);
        byRRecord.setExaminer(examiner);
        byRRecord.setResponsiblePerson(responsiblePerson);
        byRRecord.setRankTypeId(rankTypeId);
        byRRecord.setRemark(remark);
        byRRecord.setIsStoreroom(isStoreroom);
        byRRecord.setYearType(yearType);
        byRRecord.setMonthType(monthType);
        byRRecord.setRegionCode(regionCode);
        byRRecord.setRegionName(regionName);
        byRRecordManager.upperSave(byRRecord);
        return Result.ok();
    }

    @ApiOperation("档案管理信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @PostMapping("/detail")
    public Result detail(
            @RequestParam(value = "id", defaultValue = "") Integer id
    ) {
        ByRRecord rRecord = byRRecordManager.searchDetails(id);
        return Result.ok(rRecord);
    }

    @ApiOperation("档案管理 - 删除档案信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", dataType = "string", paramType = "query")
    })
    @PostMapping("/remove")
    public Result remove(
            @RequestParam(value = "ids") String ids
    ) {
        byRRecordManager.removeByIds(ids);
        return Result.ok();
    }


}
