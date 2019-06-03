
package club.emergency.controller;

import club.emergency.dictionary.manager.ByBDictionaryChildManager;
import club.emergency.dictionary.model.ByBDictionaryChild;
import club.emergency.project.manager.ByPlusProjectManager;
import club.map.core.web.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhaojinxiong
 * Date: 2019-03-22
 * Time: 8:17
 * Description:
 */
@Api("项目统计管理")
@CrossOrigin
@RestController
@RequestMapping("/project-count")
public class ProjectCountController {

    private ByPlusProjectManager byPlusProjectManager;
    private ByBDictionaryChildManager byBDictionaryChildManager;

    public ProjectCountController(ByPlusProjectManager byPlusProjectManager,
                                  ByBDictionaryChildManager byBDictionaryChildManager
    ) {
        this.byPlusProjectManager = byPlusProjectManager;
        this.byBDictionaryChildManager = byBDictionaryChildManager;
    }

    @ApiOperation("按月份统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始日期(月份开始日期)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束日期(月份结束日期)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "部门code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "workGroupCode", value = "作业小组code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "oneInspectionUserId", value = "一检人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "twoInspectionUserId", value = "二检人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "threeInspectionUserId", value = "三检人员id", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipListByMonth")
    public Result flipListByMonth(
            @RequestParam(value = "startDate", defaultValue = "") String startDate,
            @RequestParam(value = "endDate", defaultValue = "") String endDate,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode,
            @RequestParam(value = "workGroupCode", defaultValue = "") String workGroupCode,
            @RequestParam(value = "oneInspectionUserId", defaultValue = "") Integer oneInspectionUserId,
            @RequestParam(value = "twoInspectionUserId", defaultValue = "") Integer twoInspectionUserId,
            @RequestParam(value = "threeInspectionUserId", defaultValue = "") Integer threeInspectionUserId
    ) {
        Map<String, Object> map = byPlusProjectManager.searchByMonth(startDate, endDate, departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        return Result.ok(map);
    }

    @ApiOperation("按类型统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "regionCode", value = "区域code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "部门code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "workGroupCode", value = "作业小组code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "oneInspectionUserId", value = "一检人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "twoInspectionUserId", value = "二检人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "threeInspectionUserId", value = "三检人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "flag", value = "查询标识(flag为0,查询所有;flag为1,只查询某个区域下的所有类型)", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipListByRecordType")
    public Result flipListByRecordType(
            @RequestParam(value = "year", defaultValue = "") String year,
            @RequestParam(value = "regionCode", defaultValue = "") String regionCode,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode,
            @RequestParam(value = "workGroupCode", defaultValue = "") String workGroupCode,
            @RequestParam(value = "oneInspectionUserId", defaultValue = "") Integer oneInspectionUserId,
            @RequestParam(value = "twoInspectionUserId", defaultValue = "") Integer twoInspectionUserId,
            @RequestParam(value = "threeInspectionUserId", defaultValue = "") Integer threeInspectionUserId,
            @RequestParam(value = "flag", defaultValue = "0") Integer flag
    ) {
        Map<String, Object> map = byPlusProjectManager.searchByRecordType(year, regionCode, departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId, flag);
        return Result.ok(map);
    }


    @ApiOperation("按区域统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "recordTypeId", value = "项目类型id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "部门code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "workGroupCode", value = "作业小组code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "oneInspectionUserId", value = "一检人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "twoInspectionUserId", value = "二检人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "threeInspectionUserId", value = "三检人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "flag", value = "查询标识(flag为0,查询所有;flag为1,只查询某个类型下的区域)", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipListByRegion")
    public Result flipListByRegion(
            @RequestParam(value = "year", defaultValue = "") String year,
            @RequestParam(value = "recordTypeId", defaultValue = "") Integer recordTypeId,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode,
            @RequestParam(value = "workGroupCode", defaultValue = "") String workGroupCode,
            @RequestParam(value = "oneInspectionUserId", defaultValue = "") Integer oneInspectionUserId,
            @RequestParam(value = "twoInspectionUserId", defaultValue = "") Integer twoInspectionUserId,
            @RequestParam(value = "threeInspectionUserId", defaultValue = "") Integer threeInspectionUserId,
            @RequestParam(value = "flag", defaultValue = "0") Integer flag
    ) {
        Map<String, Object> map = byPlusProjectManager.searchByRegion(year, recordTypeId, departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId, flag);
        return Result.ok(map);
    }

    @ApiOperation("按评分统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "部门code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "workGroupCode", value = "作业小组code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "oneInspectionUserId", value = "一检人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "twoInspectionUserId", value = "二检人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "threeInspectionUserId", value = "三检人员id", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipListByScore")
    public Result flipListByScore(
            @RequestParam(value = "year", defaultValue = "") String year,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode,
            @RequestParam(value = "workGroupCode", defaultValue = "") String workGroupCode,
            @RequestParam(value = "oneInspectionUserId", defaultValue = "") Integer oneInspectionUserId,
            @RequestParam(value = "twoInspectionUserId", defaultValue = "") Integer twoInspectionUserId,
            @RequestParam(value = "threeInspectionUserId", defaultValue = "") Integer threeInspectionUserId
    ) {
        Map<String, Object> map = byPlusProjectManager.searchByScore(year, departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        return Result.ok(map);
    }

    @ApiOperation("按超期统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "部门code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "workGroupCode", value = "作业小组code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "oneInspectionUserId", value = "一检人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "twoInspectionUserId", value = "二检人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "threeInspectionUserId", value = "三检人员id", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipListByTaskOverdue")
    public Result flipListByTaskOverdue(
            @RequestParam(value = "year", defaultValue = "") String year,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode,
            @RequestParam(value = "workGroupCode", defaultValue = "") String workGroupCode,
            @RequestParam(value = "oneInspectionUserId", defaultValue = "") Integer oneInspectionUserId,
            @RequestParam(value = "twoInspectionUserId", defaultValue = "") Integer twoInspectionUserId,
            @RequestParam(value = "threeInspectionUserId", defaultValue = "") Integer threeInspectionUserId
    ) {
        Map<String, Object> map = byPlusProjectManager.searchByTaskOverdue(year, departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        return Result.ok(map);
    }

    @ApiOperation("按小组统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "workGroupCode", value = "作业小组code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "部门code", dataType = "string", paramType = "query")
    })
    @PostMapping("/flipListByGroup")
    public Result flipListByGroup(
            @RequestParam(value = "year", defaultValue = "") String year,
            @RequestParam(value = "workGroupCode", defaultValue = "") String workGroupCode,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode
    ) {
        Map<String, Object> map = byPlusProjectManager.searchByGroup(year, departmentCode, workGroupCode);
        return Result.ok(map);
    }

    @ApiOperation("汇总统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "regionCode", value = "区域code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "recordTypeId", value = "项目类型id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "flag", value = "查询标识(flag为0,查询所有;flag为1,按区域或类型)", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipListByCollect")
    public Result flipListByCollect(
            @RequestParam(value = "year", defaultValue = "") String year,
            @RequestParam(value = "regionCode", defaultValue = "") String regionCode,
            @RequestParam(value = "recordTypeId", defaultValue = "") Integer recordTypeId,
            @RequestParam(value = "flag", defaultValue = "0") Integer flag
    ) {
        Map<String, Object> map = byPlusProjectManager.searchByTaskCollect(year, regionCode, recordTypeId, flag);
        return Result.ok(map);
    }

    @ApiOperation("查询年份(年份进行过滤,返回结果是不大于当前时间年份的数据集合)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dictionaryCode", value = "父字典code", dataType = "string", paramType = "query")
    })
    @PostMapping("/flipListForChildByParentCode")
    public Result flipListForChildByParentCode(
            @RequestParam(value = "dictionaryCode", required = false, defaultValue = "") String dictionaryCode
    ) {
        List<ByBDictionaryChild> dictionaryChildrenList = byBDictionaryChildManager.searchLessNowYearByDictionaryCode(dictionaryCode);
        return Result.ok(dictionaryChildrenList);
    }
}
