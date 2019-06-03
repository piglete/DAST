package club.emergency.controller;

import club.emergency.equipment.manager.ByBEquipmentAppraisalManager;
import club.emergency.equipment.manager.ByBEquipmentManager;
import club.emergency.equipment.model.ByBEquipment;
import club.emergency.equipment.model.ByBEquipmentAppraisal;
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
 * Date: 2018-11-14
 * Time: 9:19
 * Description:
 */
@Api("固定资产管理")
@CrossOrigin
@RestController
@RequestMapping("/equipment")
public class EquipmentController {

    private ByBEquipmentManager byBEquipmentManager;
    private ByBEquipmentAppraisalManager byBEquipmentAppraisalManager;

    public EquipmentController(ByBEquipmentManager byBEquipmentManager,
                               ByBEquipmentAppraisalManager byBEquipmentAppraisalManager) {
        this.byBEquipmentManager = byBEquipmentManager;
        this.byBEquipmentAppraisalManager = byBEquipmentAppraisalManager;
    }

    @ApiOperation("查询固定资产信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "assetName", value = "资产名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "assetModel", value = "资产型号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "storeTypeId", value = "存放地点", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "equipmentStateId", value = "现状id", dataType = "int", paramType = "query"),
    })
    @PostMapping("/flipListForEquipment")
    public Result flipListForEquipment(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "assetName", defaultValue = "") String assetName,
            @RequestParam(value = "assetModel", defaultValue = "") String assetModel,
            @RequestParam(value = "storeTypeId", defaultValue = "") Integer storeTypeId,
            @RequestParam(value = "equipmentStateId", defaultValue = "") Integer equipmentStateId
    ) {
        Page page = byBEquipmentManager.search(assetName, assetModel, storeTypeId, equipmentStateId, pageNo, pageSize);
        return Result.ok(page);
    }

    @ApiOperation("固定资产管理 - 编辑固定资产信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "assetName", value = "资产名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "assetModel", value = "资产型号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "unitTypeId", value = "单位id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "equipmentCount", value = "数量", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "unitPrice", value = "单价(元)", dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "storeTypeId", value = "存放地点id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "equipmentStateId", value = "现状id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "usePerson", value = "使用人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "responsiblePerson", value = "负责人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "checkDate", value = "盘点日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "checkPerson", value = "盘点人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "auditor", value = "审核人", dataType = "string", paramType = "query")
    })
    @PostMapping("/edit")
    public Result edit(
            @RequestParam(value = "id", defaultValue = "") Integer id,
            @RequestParam(value = "assetName", defaultValue = "") String assetName,
            @RequestParam(value = "assetModel", defaultValue = "") String assetModel,
            @RequestParam(value = "unitTypeId", defaultValue = "") Integer unitTypeId,
            @RequestParam(value = "equipmentCount", defaultValue = "") Integer equipmentCount,
            @RequestParam(value = "unitPrice", defaultValue = "") Double unitPrice,
            @RequestParam(value = "storeTypeId", defaultValue = "") Integer storeTypeId,
            @RequestParam(value = "equipmentStateId", defaultValue = "") Integer equipmentStateId,
            @RequestParam(value = "usePerson", defaultValue = "") String usePerson,
            @RequestParam(value = "responsiblePerson", defaultValue = "") String responsiblePerson,
            @RequestParam(value = "remark", defaultValue = "") String remark,
            @RequestParam(value = "checkDate", defaultValue = "") String checkDate,
            @RequestParam(value = "checkPerson", defaultValue = "") String checkPerson,
            @RequestParam(value = "auditor", defaultValue = "") String auditor
    ) {
        ByBEquipment byBEquipment = new ByBEquipment();
        if (id != null) {
            byBEquipment.setId(id);
        }
        byBEquipment.setAssetName(assetName);
        byBEquipment.setAssetModel(assetModel);
        byBEquipment.setUnitTypeId(unitTypeId);
        byBEquipment.setEquipmentCount(equipmentCount);
        byBEquipment.setUnitPrice(unitPrice);
        byBEquipment.setStoreTypeId(storeTypeId);
        byBEquipment.setEquipmentStateId(equipmentStateId);
        byBEquipment.setUsePerson(usePerson);
        byBEquipment.setResponsiblePerson(responsiblePerson);
        byBEquipment.setRemark(remark);
        byBEquipment.setCheckDate(checkDate);
        byBEquipment.setCheckPerson(checkPerson);
        byBEquipment.setAuditor(auditor);
        byBEquipmentManager.upperSave(byBEquipment);
        return Result.ok();
    }

    @ApiOperation("固定资产信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id(单个id,例如:1)", dataType = "int", paramType = "query")
    })
    @PostMapping("/detail")
    public Result detail(
            @RequestParam(value = "id", defaultValue = "") Integer id
    ) {
        ByBEquipment byBEquipment = byBEquipmentManager.searchDetail(id);
        return Result.ok(byBEquipment);
    }

    @ApiOperation("固定资产管理 - 删除固定资产信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids(例如: 一个:1 ; 多个:1,2 )", dataType = "string", paramType = "query")
    })
    @PostMapping("/remove")
    public Result remove(
            @RequestParam(value = "ids") String ids
    ) {
        byBEquipmentManager.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("查询固定资产清点信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "equipmentStateId", value = "现状", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "storeTypeName", value = "存放地点", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "assetName", value = "资产名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "所属部门code", dataType = "string", paramType = "query"),
    })
    @PostMapping("/flipListForEquipmentAppraisal")
    public Result flipListForEquipmentAppraisal(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "equipmentStateId", defaultValue = "") Integer equipmentStateId,
            @RequestParam(value = "storeTypeName", defaultValue = "") String storeTypeName,
            @RequestParam(value = "assetName", defaultValue = "") String assetName,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode
    ) {
        Page page = byBEquipmentAppraisalManager.search(equipmentStateId, storeTypeName, assetName, departmentCode, pageNo, pageSize);
        return Result.ok(page);
    }

    @ApiOperation("固定资产清点管理 - 编辑固定资产清点信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "equipmentId", value = "仪器编号", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "assetName", value = "资产名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "assetModel", value = "资产型号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "unit", value = "单位", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "equipmentCount", value = "数量", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "storeTypeName", value = "存放地点", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "所属部门code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "responsiblePerson", value = "负责人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "usePerson", value = "使用人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "equipmentStateId", value = "现状id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "inventoryCondition", value = "清点状况", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "inventoryDate", value = "清点日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "inventoryPerson", value = "清点人", dataType = "string", paramType = "query")
    })
    @PostMapping("/editForEquipmentAppraisal")
    public Result editForEquipmentAppraisal(
            @RequestParam(value = "id", defaultValue = "") Integer id,
            @RequestParam(value = "equipmentId", defaultValue = "") Integer equipmentId,
            @RequestParam(value = "assetName", defaultValue = "") String assetName,
            @RequestParam(value = "assetModel", defaultValue = "") String assetModel,
            @RequestParam(value = "unit", defaultValue = "") String unit,
            @RequestParam(value = "equipmentCount", defaultValue = "") Integer equipmentCount,
            @RequestParam(value = "storeTypeName", defaultValue = "") String storeTypeName,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode,
            @RequestParam(value = "responsiblePerson", defaultValue = "") String responsiblePerson,
            @RequestParam(value = "usePerson", defaultValue = "") String usePerson,
            @RequestParam(value = "equipmentStateId", defaultValue = "") Integer equipmentStateId,
            @RequestParam(value = "inventoryCondition", defaultValue = "") String inventoryCondition,
            @RequestParam(value = "remark", defaultValue = "") String remark,
            @RequestParam(value = "inventoryDate", defaultValue = "") String inventoryDate,
            @RequestParam(value = "inventoryPerson", defaultValue = "") String inventoryPerson
    ) {
        ByBEquipmentAppraisal byBEquipmentAppraisal = new ByBEquipmentAppraisal();
        if (id != null) {
            byBEquipmentAppraisal.setId(id);
        }
        byBEquipmentAppraisal.setEquipmentId(equipmentId);
        byBEquipmentAppraisal.setAssetName(assetName);
        byBEquipmentAppraisal.setAssetModel(assetModel);
        byBEquipmentAppraisal.setUnit(unit);
        byBEquipmentAppraisal.setEquipmentCount(equipmentCount);
        byBEquipmentAppraisal.setStoreTypeName(storeTypeName);
        byBEquipmentAppraisal.setDepartmentCode(departmentCode);
        byBEquipmentAppraisal.setResponsiblePerson(responsiblePerson);
        byBEquipmentAppraisal.setUsePerson(usePerson);
        byBEquipmentAppraisal.setEquipmentStateId(equipmentStateId);
        byBEquipmentAppraisal.setInventoryCondition(inventoryCondition);
        byBEquipmentAppraisal.setInventoryDate(inventoryDate);
        byBEquipmentAppraisal.setInventoryPerson(inventoryPerson);
        byBEquipmentAppraisal.setRemark(remark);
        byBEquipmentAppraisalManager.upperSave(byBEquipmentAppraisal);
        return Result.ok();
    }

    @ApiOperation("固定资产清点信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id(单个id,例如:1)", dataType = "int", paramType = "query")
    })
    @PostMapping("/detailForEquipmentAppraisal")
    public Result detailForEquipmentAppraisal(
            @RequestParam(value = "id", defaultValue = "") Integer id
    ) {
        ByBEquipmentAppraisal byBEquipmentAppraisal = byBEquipmentAppraisalManager.searchDetail(id);
        return Result.ok(byBEquipmentAppraisal);
    }

    @ApiOperation("固定资产清点管理 - 删除固定资产清点信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids(例如: 一个:1 ; 多个:1,2 )", dataType = "string", paramType = "query")
    })
    @PostMapping("/removeForEquipmentAppraisal")
    public Result removeForEquipmentAppraisal(
            @RequestParam(value = "ids") String ids
    ) {
        byBEquipmentAppraisalManager.removeByIds(ids);
        return Result.ok();
    }

}
