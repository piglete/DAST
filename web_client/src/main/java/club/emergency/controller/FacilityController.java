package club.emergency.controller;

import club.emergency.equipment.manager.ByBEquipmentCheckManager;
import club.emergency.equipment.manager.ByBEquipmentRegistrationManager;
import club.emergency.equipment.manager.ByBEquipmentRepairManager;
import club.emergency.equipment.manager.ByBEquipmentScrapManager;
import club.emergency.equipment.model.ByBEquipmentCheck;
import club.emergency.equipment.model.ByBEquipmentRegistration;
import club.emergency.equipment.model.ByBEquipmentRepair;
import club.emergency.equipment.model.ByBEquipmentScrap;
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
 * Date: 2018-12-21
 * Time: 17:31
 * Description:
 */
@Api("设备管理")
@CrossOrigin
@RestController
@RequestMapping("/facility")
public class FacilityController {

    private ByBEquipmentRegistrationManager byBEquipmentRegistrationManager;
    private ByBEquipmentScrapManager byBEquipmentScrapManager;
    private ByBEquipmentRepairManager byBEquipmentRepairManager;
    private ByBEquipmentCheckManager byBEquipmentCheckManager;

    public FacilityController(ByBEquipmentRegistrationManager byBEquipmentRegistrationManager,
                              ByBEquipmentScrapManager byBEquipmentScrapManager,
                              ByBEquipmentRepairManager byBEquipmentRepairManager,
                              ByBEquipmentCheckManager byBEquipmentCheckManager) {
        this.byBEquipmentRegistrationManager = byBEquipmentRegistrationManager;
        this.byBEquipmentScrapManager = byBEquipmentScrapManager;
        this.byBEquipmentRepairManager = byBEquipmentRepairManager;
        this.byBEquipmentCheckManager = byBEquipmentCheckManager;
    }

    @ApiOperation("查询设备信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "equipmentName", value = "设备名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "equipmentModel", value = "设备型号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "equipmentTypeId", value = "设备类别", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "equipmentStateId", value = "现状id", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipListForRegistration")
    public Result flipListForRegistration(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "equipmentName", defaultValue = "") String equipmentName,
            @RequestParam(value = "equipmentModel", defaultValue = "") String equipmentModel,
            @RequestParam(value = "equipmentTypeId", defaultValue = "") Integer equipmentTypeId,
            @RequestParam(value = "equipmentStateId", defaultValue = "") Integer equipmentStateId
    ) {
        Page page = byBEquipmentRegistrationManager.search(equipmentName, equipmentModel, equipmentTypeId, equipmentStateId, pageNo, pageSize);
        return Result.ok(page);
    }

    @ApiOperation("设备管理 - 编辑设备信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "equipmentTypeId", value = "设备类别id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "equipmentName", value = "设备名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "equipmentModel", value = "设备型号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "equipmentNumber", value = "设备编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "equipmentCount", value = "数量", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "unit", value = "单位", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "purchaseDate", value = "采购时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "申请部门code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "responsiblePersonId", value = "负责人id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "particularYearId", value = "年份id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "durableYear", value = "使用年限", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "equipmentStateId", value = "现状id", dataType = "int", paramType = "query")
    })
    @PostMapping("/editForRegistration")
    public Result editForRegistration(
            @RequestParam(value = "id", defaultValue = "") Integer id,
            @RequestParam(value = "equipmentTypeId", defaultValue = "") Integer equipmentTypeId,
            @RequestParam(value = "equipmentName", defaultValue = "") String equipmentName,
            @RequestParam(value = "equipmentModel", defaultValue = "") String equipmentModel,
            @RequestParam(value = "equipmentNumber", defaultValue = "") String equipmentNumber,
            @RequestParam(value = "equipmentCount", defaultValue = "") Integer equipmentCount,
            @RequestParam(value = "unit", defaultValue = "") String unit,
            @RequestParam(value = "purchaseDate", defaultValue = "") String purchaseDate,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode,
            @RequestParam(value = "responsiblePersonId", defaultValue = "") Integer responsiblePersonId,
            @RequestParam(value = "particularYearId", defaultValue = "") Integer particularYearId,
            @RequestParam(value = "durableYear", defaultValue = "") String durableYear,
            @RequestParam(value = "remark", defaultValue = "") String remark,
            @RequestParam(value = "equipmentStateId", defaultValue = "") Integer equipmentStateId
    ) {
        ByBEquipmentRegistration byBEquipmentRegistration = new ByBEquipmentRegistration();
        if (id != null) {
            byBEquipmentRegistration.setId(id);
        }
        byBEquipmentRegistration.setEquipmentTypeId(equipmentTypeId);
        byBEquipmentRegistration.setEquipmentName(equipmentName);
        byBEquipmentRegistration.setEquipmentModel(equipmentModel);
        byBEquipmentRegistration.setEquipmentNumber(equipmentNumber);
        byBEquipmentRegistration.setEquipmentCount(equipmentCount);
        byBEquipmentRegistration.setUnit(unit);
        byBEquipmentRegistration.setPurchaseDate(purchaseDate);
        byBEquipmentRegistration.setDepartmentCode(departmentCode);
        byBEquipmentRegistration.setResponsiblePersonId(responsiblePersonId);
        byBEquipmentRegistration.setParticularYearId(particularYearId);
        byBEquipmentRegistration.setDurableYear(durableYear);
        byBEquipmentRegistration.setRemark(remark);
        byBEquipmentRegistration.setEquipmentStateId(equipmentStateId);
        byBEquipmentRegistrationManager.upperSave(byBEquipmentRegistration);
        return Result.ok();
    }

    @ApiOperation("设备信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id(单个id,例如:1)", dataType = "int", paramType = "query")
    })
    @PostMapping("/detailForRegistration")
    public Result detailForRegistration(
            @RequestParam(value = "id", defaultValue = "") Integer id
    ) {
        ByBEquipmentRegistration byBEquipmentRegistration = byBEquipmentRegistrationManager.searchDetail(id);
        return Result.ok(byBEquipmentRegistration);
    }

    @ApiOperation("设备管理 - 删除设备信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids(例如: 一个:1 ; 多个:1,2 )", dataType = "string", paramType = "query")
    })
    @PostMapping("/removeForRegistration")
    public Result removeForRegistration(
            @RequestParam(value = "ids") String ids
    ) {
        byBEquipmentRegistrationManager.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("查询报废设备信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "资产名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "departmentName", value = "报废部门", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "equipmentTypeId", value = "设备类别", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipListForScrap")
    public Result flipListForScrap(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "departmentName", defaultValue = "") String departmentName,
            @RequestParam(value = "equipmentTypeId", defaultValue = "") Integer equipmentTypeId
    ) {
        Page page = byBEquipmentScrapManager.search(name, departmentName, equipmentTypeId, pageNo, pageSize);
        return Result.ok(page);
    }

    @ApiOperation("设备管理 - 编辑报废设备信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "equipmentRegistrationId", value = "设备登记id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "equipmentTypeId", value = "设备类别id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "equipmentTypeName", value = "设备类别", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "资产名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "model", value = "资产型号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "count", value = "数量", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "unit", value = "单位", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "departmentName", value = "报废部门", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "responsiblePerson", value = "部门负责人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "scrapDate", value = "报废日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query")
    })
    @PostMapping("/editForScrap")
    public Result editForScrap(
            @RequestParam(value = "id", defaultValue = "") Integer id,
            @RequestParam(value = "equipmentRegistrationId", defaultValue = "") Integer equipmentRegistrationId,
            @RequestParam(value = "equipmentTypeId", defaultValue = "") Integer equipmentTypeId,
            @RequestParam(value = "equipmentTypeName", defaultValue = "") String equipmentTypeName,
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "model", defaultValue = "") String model,
            @RequestParam(value = "count", defaultValue = "") Integer count,
            @RequestParam(value = "unit", defaultValue = "") String unit,
            @RequestParam(value = "departmentName", defaultValue = "") String departmentName,
            @RequestParam(value = "responsiblePerson", defaultValue = "") String responsiblePerson,
            @RequestParam(value = "scrapDate", defaultValue = "") String scrapDate,
            @RequestParam(value = "remark", defaultValue = "") String remark
    ) {
        ByBEquipmentScrap byBEquipmentScrap = new ByBEquipmentScrap();
        if (id != null) {
            byBEquipmentScrap.setId(id);
        }
        byBEquipmentScrap.setEquipmentRegistrationId(equipmentRegistrationId);
        byBEquipmentScrap.setEquipmentTypeId(equipmentTypeId);
        byBEquipmentScrap.setEquipmentTypeName(equipmentTypeName);
        byBEquipmentScrap.setName(name);
        byBEquipmentScrap.setModel(model);
        byBEquipmentScrap.setCount(count);
        byBEquipmentScrap.setUnit(unit);
        byBEquipmentScrap.setDepartmentName(departmentName);
        byBEquipmentScrap.setResponsiblePerson(responsiblePerson);
        byBEquipmentScrap.setScrapDate(scrapDate);
        byBEquipmentScrap.setRemark(remark);
        byBEquipmentScrapManager.upperSave(byBEquipmentScrap);
        return Result.ok();
    }

    @ApiOperation("报废设备信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id(单个id,例如:1)", dataType = "int", paramType = "query")
    })
    @PostMapping("/detailForScrap")
    public Result detailForScrap(
            @RequestParam(value = "id", defaultValue = "") Integer id
    ) {
        ByBEquipmentScrap byBEquipmentScrap = byBEquipmentScrapManager.searchDetail(id);
        return Result.ok(byBEquipmentScrap);
    }

    @ApiOperation("设备管理 - 删除报废设备信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids(例如: 一个:1 ; 多个:1,2 )", dataType = "string", paramType = "query")
    })
    @PostMapping("/removeForScrap")
    public Result removeForScrap(
            @RequestParam(value = "ids") String ids
    ) {
        byBEquipmentScrapManager.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("查询设备维修记录信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "设备名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "departmentName", value = "部门", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "equipmentTypeId", value = "设备类别", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipListForRepair")
    public Result flipListForRepair(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "departmentName", defaultValue = "") String departmentName,
            @RequestParam(value = "equipmentTypeId", defaultValue = "") Integer equipmentTypeId
    ) {
        Page page = byBEquipmentRepairManager.search(name, departmentName, equipmentTypeId, pageNo, pageSize);
        return Result.ok(page);
    }

    @ApiOperation("设备管理 - 编辑设备维修信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "equipmentRegistrationId", value = "设备登记id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "equipmentTypeId", value = "设备类别id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "equipmentTypeName", value = "设备类别", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "设备名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "model", value = "设备型号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "equipmentNumber", value = "设备编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "count", value = "数量", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "unit", value = "单位", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "repairDate", value = "维修时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "replaceInformation", value = "更换主要零部件", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "repairFee", value = "维修费（元）", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "departmentName", value = "部门", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "responsiblePerson", value = "部门负责人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query")
    })
    @PostMapping("/editForRepair")
    public Result editForRepair(
            @RequestParam(value = "id", defaultValue = "") Integer id,
            @RequestParam(value = "equipmentRegistrationId", defaultValue = "") Integer equipmentRegistrationId,
            @RequestParam(value = "equipmentTypeId", defaultValue = "") Integer equipmentTypeId,
            @RequestParam(value = "equipmentTypeName", defaultValue = "") String equipmentTypeName,
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "model", defaultValue = "") String model,
            @RequestParam(value = "equipmentNumber", defaultValue = "") String equipmentNumber,
            @RequestParam(value = "count", defaultValue = "") Integer count,
            @RequestParam(value = "unit", defaultValue = "") String unit,
            @RequestParam(value = "repairDate", defaultValue = "") String repairDate,
            @RequestParam(value = "replaceInformation", defaultValue = "") String replaceInformation,
            @RequestParam(value = "repairFee", defaultValue = "") Integer repairFee,
            @RequestParam(value = "departmentName", defaultValue = "") String departmentName,
            @RequestParam(value = "responsiblePerson", defaultValue = "") String responsiblePerson,
            @RequestParam(value = "remark", defaultValue = "") String remark
    ) {
        ByBEquipmentRepair byBEquipmentRepair = new ByBEquipmentRepair();
        if (id != null) {
            byBEquipmentRepair.setId(id);
        }
        byBEquipmentRepair.setEquipmentRegistrationId(equipmentRegistrationId);
        byBEquipmentRepair.setEquipmentTypeId(equipmentTypeId);
        byBEquipmentRepair.setEquipmentTypeName(equipmentTypeName);
        byBEquipmentRepair.setName(name);
        byBEquipmentRepair.setModel(model);
        byBEquipmentRepair.setEquipmentNumber(equipmentNumber);
        byBEquipmentRepair.setCount(count);
        byBEquipmentRepair.setUnit(unit);
        byBEquipmentRepair.setRepairDate(repairDate);
        byBEquipmentRepair.setReplaceInformation(replaceInformation);
        byBEquipmentRepair.setRepairFee(repairFee);
        byBEquipmentRepair.setDepartmentName(departmentName);
        byBEquipmentRepair.setResponsiblePerson(responsiblePerson);
        byBEquipmentRepair.setRemark(remark);
        byBEquipmentRepairManager.upperSave(byBEquipmentRepair);
        return Result.ok();
    }

    @ApiOperation("设备维修信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id(单个id,例如:1)", dataType = "int", paramType = "query")
    })
    @PostMapping("/detailForRepair")
    public Result detailForRepair(
            @RequestParam(value = "id", defaultValue = "") Integer id
    ) {
        ByBEquipmentRepair byBEquipmentRepair = byBEquipmentRepairManager.searchDetail(id);
        return Result.ok(byBEquipmentRepair);
    }

    @ApiOperation("设备管理 - 删除设备维修信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids(例如: 一个:1 ; 多个:1,2 )", dataType = "string", paramType = "query")
    })
    @PostMapping("/removeForRepair")
    public Result removeForRepair(
            @RequestParam(value = "ids") String ids
    ) {
        byBEquipmentRepairManager.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("查询设备鉴定信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "设备名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "equipmentTypeId", value = "设备类别id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "departmentName", value = "部门", dataType = "string", paramType = "query")
    })
    @PostMapping("/flipListForCheck")
    public Result flipListForCheck(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "equipmentTypeId", defaultValue = "") Integer equipmentTypeId,
            @RequestParam(value = "departmentName", defaultValue = "") String departmentName
    ) {
        Page page = byBEquipmentCheckManager.search(name, equipmentTypeId, departmentName, pageNo, pageSize);
        return Result.ok(page);
    }

    @ApiOperation("设备管理 - 编辑设备鉴定信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "equipmentRegistrationId", value = "设备登记id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "equipmentTypeId", value = "设备类别id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "equipmentTypeName", value = "设备类别", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "设备名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "model", value = "设备型号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "equipmentNumber", value = "设备编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "count", value = "数量", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "unit", value = "单位", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "purchaseDate", value = "采购时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "durableYear", value = "使用年限", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "responsiblePerson", value = "负责人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "departmentName", value = "部门", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "checkPerson", value = "鉴定人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "checkDate", value = "鉴定日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "checkResult", value = "鉴定结果", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query")
    })
    @PostMapping("/editForCheck")
    public Result editForCheck(
            @RequestParam(value = "id", defaultValue = "") Integer id,
            @RequestParam(value = "equipmentRegistrationId", defaultValue = "") Integer equipmentRegistrationId,
            @RequestParam(value = "equipmentTypeId", defaultValue = "") Integer equipmentTypeId,
            @RequestParam(value = "equipmentTypeName", defaultValue = "") String equipmentTypeName,
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "model", defaultValue = "") String model,
            @RequestParam(value = "equipmentNumber", defaultValue = "") String equipmentNumber,
            @RequestParam(value = "count", defaultValue = "") Integer count,
            @RequestParam(value = "unit", defaultValue = "") String unit,
            @RequestParam(value = "purchaseDate", defaultValue = "") String purchaseDate,
            @RequestParam(value = "durableYear", defaultValue = "") String durableYear,
            @RequestParam(value = "responsiblePerson", defaultValue = "") String responsiblePerson,
            @RequestParam(value = "departmentName", defaultValue = "") String departmentName,
            @RequestParam(value = "checkPerson", defaultValue = "") String checkPerson,
            @RequestParam(value = "checkDate", defaultValue = "") String checkDate,
            @RequestParam(value = "checkResult", defaultValue = "") String checkResult,
            @RequestParam(value = "remark", defaultValue = "") String remark
    ) {
        ByBEquipmentCheck byBEquipmentCheck = new ByBEquipmentCheck();
        if (id != null) {
            byBEquipmentCheck.setId(id);
        }
        byBEquipmentCheck.setEquipmentRegistrationId(equipmentRegistrationId);
        byBEquipmentCheck.setEquipmentTypeId(equipmentTypeId);
        byBEquipmentCheck.setEquipmentTypeName(equipmentTypeName);
        byBEquipmentCheck.setName(name);
        byBEquipmentCheck.setModel(model);
        byBEquipmentCheck.setEquipmentNumber(equipmentNumber);
        byBEquipmentCheck.setCount(count);
        byBEquipmentCheck.setUnit(unit);
        byBEquipmentCheck.setPurchaseDate(purchaseDate);
        byBEquipmentCheck.setDurableYear(durableYear);
        byBEquipmentCheck.setResponsiblePerson(responsiblePerson);
        byBEquipmentCheck.setDepartmentName(departmentName);
        byBEquipmentCheck.setCheckPerson(checkPerson);
        byBEquipmentCheck.setCheckDate(checkDate);
        byBEquipmentCheck.setCheckResult(checkResult);
        byBEquipmentCheck.setRemark(remark);
        byBEquipmentCheckManager.upperSave(byBEquipmentCheck);
        return Result.ok();
    }

    @ApiOperation("设备鉴定信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id(单个id,例如:1)", dataType = "int", paramType = "query")
    })
    @PostMapping("/detailForCheck")
    public Result detailForCheck(
            @RequestParam(value = "id", defaultValue = "") Integer id
    ) {
        ByBEquipmentCheck byBEquipmentCheck = byBEquipmentCheckManager.get(id);
        return Result.ok(byBEquipmentCheck);
    }

    @ApiOperation("设备管理 - 删除设备鉴定信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids(例如: 一个:1 ; 多个:1,2 )", dataType = "string", paramType = "query")
    })
    @PostMapping("/removeForCheck")
    public Result removeForCheck(
            @RequestParam(value = "ids") String ids
    ) {
        byBEquipmentCheckManager.removeByIds(ids);
        return Result.ok();
    }

}
