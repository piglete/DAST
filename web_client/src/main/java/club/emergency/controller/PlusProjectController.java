package club.emergency.controller;

import club.emergency.project.manager.ByPlusProjectManager;
import club.emergency.project.manager.VProjectTaskManager;
import club.emergency.project.model.ByPlusProject;
import club.map.core.model.Page;
import club.map.core.web.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("项目管理")
@CrossOrigin
@RestController
@RequestMapping("/plus-project")
public class PlusProjectController {

    @Autowired
    private VProjectTaskManager vProjectTaskManager;
    private ByPlusProjectManager byPlusProjectManager;

    public PlusProjectController(ByPlusProjectManager byPlusProjectManager) {
        this.byPlusProjectManager = byPlusProjectManager;
    }

    @ApiOperation("查询项目信息(项目统计展示),带分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "项目名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "regionName", value = "区域名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "recordName", value = "项目类型名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "unitName", value = "单位名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "linkman", value = "联系人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "telephone", value = "联系电话", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "score", value = "分数(区间或者单个数子)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "dayCount", value = "超期天数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "开始日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "oneInspectionUserId", value = "一检人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "twoInspectionUserId", value = "二检人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "threeInspectionUserId", value = "三检人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "部门code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "workGroupCode", value = "作业小组code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "recordOrRegionOverdueFlag", value = "项目类型或者区域超期标识(0:未超期;1超期)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "regionCode", value = "区域code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "recordTypeId", value = "项目类型id", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipListForProjectCensusWithPage")
    public Result flipListForProjectCensusWithPage(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "regionName", defaultValue = "") String regionName,
            @RequestParam(value = "recordName", defaultValue = "") String recordName,
            @RequestParam(value = "unitName", defaultValue = "") String unitName,
            @RequestParam(value = "linkman", defaultValue = "") String linkman,
            @RequestParam(value = "telephone", defaultValue = "") String telephone,
            @RequestParam(value = "score", defaultValue = "") String score,
            @RequestParam(value = "dayCount", defaultValue = "") String dayCount,
            @RequestParam(value = "startDate", defaultValue = "") String startDate,
            @RequestParam(value = "endDate", defaultValue = "") String endDate,
            @RequestParam(value = "oneInspectionUserId", defaultValue = "") Integer oneInspectionUserId,
            @RequestParam(value = "twoInspectionUserId", defaultValue = "") Integer twoInspectionUserId,
            @RequestParam(value = "threeInspectionUserId", defaultValue = "") Integer threeInspectionUserId,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode,
            @RequestParam(value = "workGroupCode", defaultValue = "") String workGroupCode,
            @RequestParam(value = "recordOrRegionOverdueFlag", defaultValue = "0") Integer recordOrRegionOverdueFlag,
            @RequestParam(value = "regionCode", defaultValue = "") String regionCode,
            @RequestParam(value = "recordTypeId", defaultValue = "") Integer recordTypeId
    ) {
        Page page = byPlusProjectManager.searchProjectCensusWithPage(name, regionName, recordName, unitName, linkman, telephone, score, dayCount, startDate, endDate, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId, departmentCode, workGroupCode, recordOrRegionOverdueFlag, regionCode, recordTypeId, pageNo, pageSize);
        return Result.ok(page);
    }


    @ApiOperation("查询项目信息(地图展示),无分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dataNumber", value = "档案编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fileNumber", value = "资料编号(任务)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "项目名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "regionCode", value = "区域code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "recordTypeId", value = "档案类型id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "unitName", value = "单位名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "linkman", value = "联系人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "telephone", value = "联系电话", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "startTime", value = "开始日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "作业部门", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "groupCode", value = "作业小组", dataType = "string", paramType = "query")
    })
    @PostMapping("/flipListForMapWithoutPage")
    public Result flipListWithoutPage(
            @RequestParam(value = "dataNumber", defaultValue = "") String dataNumber,
            @RequestParam(value = "fileNumber", defaultValue = "") String fileNumber,
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "regionCode", defaultValue = "") String regionCode,
            @RequestParam(value = "recordTypeId", defaultValue = "") Integer recordTypeId,
            @RequestParam(value = "unitName", defaultValue = "") String unitName,
            @RequestParam(value = "linkman", defaultValue = "") String linkman,
            @RequestParam(value = "telephone", defaultValue = "") String telephone,
            @RequestParam(value = "startTime", defaultValue = "") String startTime,
            @RequestParam(value = "endTime", defaultValue = "") String endTime,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode,
            @RequestParam(value = "groupCode", defaultValue = "") String groupCode
    ) {
        List<ByPlusProject> byPlusProjectList = byPlusProjectManager.searchWithoutPage(name, startTime, endTime, unitName, linkman, telephone, regionCode, recordTypeId, fileNumber, dataNumber, departmentCode, groupCode);
        return Result.ok(byPlusProjectList);
    }

    @ApiOperation("查询项目信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "项目名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "flag", value = "项目完成标识(0为进行中,1为已完成)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "startTime", value = "开始日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "unitName", value = "甲方单位名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "linkman", value = "联系人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "projectPeriod", value = "项目周期(天)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "regionCode", value = "区域code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "telephone", value = "联系方式", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "recordTypeId", value = "档案类型id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "projectStateId", value = "项目状态id", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipList")
    public Result flipList(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "flag", defaultValue = "") Integer flag,
            @RequestParam(value = "startTime", defaultValue = "") String startTime,
            @RequestParam(value = "endTime", defaultValue = "") String endTime,
            @RequestParam(value = "unitName", defaultValue = "") String unitName,
            @RequestParam(value = "linkman", defaultValue = "") String linkman,
            @RequestParam(value = "projectPeriod", defaultValue = "") Integer projectPeriod,
            @RequestParam(value = "regionCode", defaultValue = "") String regionCode,
            @RequestParam(value = "telephone", defaultValue = "") String telephone,
            @RequestParam(value = "recordTypeId", defaultValue = "") Integer recordTypeId,
            @RequestParam(value = "projectStateId", defaultValue = "") Integer projectStateId
    ) {
        Page page = byPlusProjectManager.searchWithPage(name, flag, startTime, endTime, unitName, linkman, projectPeriod, regionCode, telephone, recordTypeId, projectStateId,pageNo, pageSize);
        return Result.ok(page);
    }

    @ApiOperation("查询项目信息(按部门)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "项目名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "部门code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "groupCode", value = "小组code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "projectStateId", value = "项目状态id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "flag", value = "项目完成标识(0为进行中,1为已完成)", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipListForDepartment")
    public Result flipListForDepartment(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode,
            @RequestParam(value = "groupCode", defaultValue = "") String groupCode,
            @RequestParam(value = "projectStateId", defaultValue = "") Integer projectStateId,
            @RequestParam(value = "flag", defaultValue = "") Integer flag
    ) {
        Page page = byPlusProjectManager.search(name, departmentCode, groupCode, projectStateId, flag, pageNo, pageSize);
        return Result.ok(page);
    }


    @ApiOperation("项目管理 - 编辑项目信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "项目名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "计划开始时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "finishDate", value = "计划结束时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "address", value = "项目地址", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "projectStateId", value = "项目状态id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "recordTypeId", value = "档案类型id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "regionCode", value = "区域code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "unitName", value = "单位名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "projectCostId", value = "项目费用支付状态id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "dataStateId", value = "资料交付状态id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "dataCommit", value = "资料提交情况", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "linkman", value = "联系人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "telephone", value = "联系方式", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "longitude", value = "经度", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "latitude", value = "纬度", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "projectPeriod", value = "项目周期(天)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "projectAddUser", value = "项目创建人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "projectAddUserId", value = "项目创建人id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "storageUser", value = "入库用户", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "storageDate", value = "入库时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "addDate", value = "项目创建时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "flag", value = "项目完成标识(0为进行中,1为已完成)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "taskReturnCount", value = "该项目下任务回退数量(0为没有回退,其他为回退次数)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "taskFlag", value = "任务下达标识(0为未下达,1为已下达)", dataType = "int", paramType = "query")
    })
    @PostMapping("/edit")
    public Result edit(
            @RequestParam(value = "id", defaultValue = "") Integer id,
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "startDate", defaultValue = "") String startDate,
            @RequestParam(value = "finishDate", defaultValue = "") String finishDate,
            @RequestParam(value = "address", defaultValue = "") String address,
            @RequestParam(value = "projectStateId", defaultValue = "") Integer projectStateId,
            @RequestParam(value = "recordTypeId", defaultValue = "") Integer recordTypeId,
            @RequestParam(value = "regionCode", defaultValue = "") String regionCode,
            @RequestParam(value = "unitName", defaultValue = "") String unitName,
            @RequestParam(value = "projectCostId", defaultValue = "") Integer projectCostId,
            @RequestParam(value = "dataStateId", defaultValue = "") Integer dataStateId,
            @RequestParam(value = "dataCommit", defaultValue = "") String dataCommit,
            @RequestParam(value = "linkman", defaultValue = "") String linkman,
            @RequestParam(value = "telephone", defaultValue = "") String telephone,
            @RequestParam(value = "longitude", defaultValue = "") String longitude,
            @RequestParam(value = "latitude", defaultValue = "") String latitude,
            @RequestParam(value = "projectPeriod", defaultValue = "") Integer projectPeriod,
            @RequestParam(value = "projectAddUser", defaultValue = "") String projectAddUser,
            @RequestParam(value = "projectAddUserId", defaultValue = "") Integer projectAddUserId,
            @RequestParam(value = "storageUser", defaultValue = "") String storageUser,
            @RequestParam(value = "storageDate", defaultValue = "") String storageDate,
            @RequestParam(value = "addDate", defaultValue = "") String addDate,
            @RequestParam(value = "flag", defaultValue = "0") Integer flag,
            @RequestParam(value = "taskReturnCount", defaultValue = "0") Integer taskReturnCount,
            @RequestParam(value = "taskFlag", defaultValue = "0") Integer taskFlag
    ) {
        ByPlusProject byPlusProject = new ByPlusProject();
        if (id != null) {
            byPlusProject.setId(id);
        }
        byPlusProject.setName(name);
        byPlusProject.setStartDate(startDate);
        byPlusProject.setFinishDate(finishDate);
        byPlusProject.setAddress(address);
        byPlusProject.setProjectStateId(projectStateId);
        byPlusProject.setRecordTypeId(recordTypeId);
        byPlusProject.setRegionCode(regionCode);
        byPlusProject.setUnitName(unitName);
        byPlusProject.setProjectCostId(projectCostId);
        byPlusProject.setDataStateId(dataStateId);
        byPlusProject.setDataCommit(dataCommit);
        byPlusProject.setLinkman(linkman);
        byPlusProject.setTelephone(telephone);
        byPlusProject.setLongitude(longitude);
        byPlusProject.setLatitude(latitude);
        byPlusProject.setProjectPeriod(projectPeriod);
        byPlusProject.setProjectAddUser(projectAddUser);
        byPlusProject.setProjectAddUserId(projectAddUserId);
        byPlusProject.setStorageUser(storageUser);
        byPlusProject.setStorageDate(storageDate);
        byPlusProject.setAddDate(addDate);
        byPlusProject.setFlag(flag);
        byPlusProject.setTaskReturnCount(taskReturnCount);
        byPlusProject.setTaskFlag(taskFlag);
        byPlusProjectManager.upperSave(byPlusProject);
        return Result.ok();
    }

    @ApiOperation("项目管理 - 修改项目信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "项目名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "unitName", value = "单位名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "linkman", value = "联系人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "telephone", value = "联系方式", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "address", value = "项目地址", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "longitude", value = "经度", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "latitude", value = "纬度", dataType = "string", paramType = "query")
    })
    @PostMapping("/editForProjectInfo")
    public Result editForProjectInfo(
            @RequestParam(value = "id", defaultValue = "") Integer id,
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "unitName", defaultValue = "") String unitName,
            @RequestParam(value = "linkman", defaultValue = "") String linkman,
            @RequestParam(value = "telephone", defaultValue = "") String telephone,
            @RequestParam(value = "address", defaultValue = "") String address,
            @RequestParam(value = "longitude", defaultValue = "") String longitude,
            @RequestParam(value = "latitude", defaultValue = "") String latitude
    ) {
        byPlusProjectManager.updateProjectLocationInfo(name, unitName, linkman, telephone, address, longitude, latitude, id);
        return Result.ok();
    }

    @ApiOperation("项目信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @PostMapping("/detail")
    public Result detail(
            @RequestParam(value = "id", defaultValue = "") Integer id
    ) {
        ByPlusProject byPlusProject = byPlusProjectManager.searchById(id);
        return Result.ok(byPlusProject);
    }

    @ApiOperation("项目管理 - 删除项目信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", dataType = "string", paramType = "query")
    })
    @PostMapping("/remove")
    public Result remove(
            @RequestParam(value = "ids") String ids
    ) {
        byPlusProjectManager.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("项目管理 - 收款完成")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "项目id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "projectCostId", value = "项目费用支付状态id", dataType = "int", paramType = "query")
    })
    @PostMapping("/editForProjectCost")
    public Result editForProjectCost(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "projectCostId") Integer projectCostId
    ) {
        byPlusProjectManager.updateProjectCostById(id, projectCostId);
        return Result.ok();
    }

    @ApiOperation("项目管理 - 交付资料完成")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "项目id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "dataStateId", value = "资料交付状态id", dataType = "int", paramType = "query")
    })
    @PostMapping("/editForDataState")
    public Result editForDataState(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "dataStateId") Integer dataStateId
    ) {
        byPlusProjectManager.updateDataStateById(id, dataStateId);
        return Result.ok();
    }

    @ApiOperation("项目管理 - 修改项目坐标(经纬度转城建)")
    @PostMapping("/transCJ")
    public Result transCJ() {
        byPlusProjectManager.updateCoordinateByIds();
        return Result.ok();
    }

    @ApiOperation("项目管理 - 查询单个项目信息(包含档案号和任务作业信息)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "项目id", dataType = "int", paramType = "query")
    })
    @PostMapping("/searchForProjectAllInformation")
    public Result searchForProjectAllInformation(
            @RequestParam(value = "id") Integer id
    ) {
        ByPlusProject byPlusProject = byPlusProjectManager.searchProjectInformationById(id);
        return Result.ok(byPlusProject);
    }

}


