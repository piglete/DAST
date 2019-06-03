package club.emergency.controller;

import club.emergency.Util.ImageTools;
import club.emergency.by_b_employee.manager.ByBEmployeeManager;
import club.emergency.by_b_employee.model.ByBEmployee;
import club.map.core.model.Page;
import club.map.core.web.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhaojinxiong
 * Date: 2018-11-13
 * Time: 14:27
 * Description:
 */
@Api("员工管理")
@CrossOrigin
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private ByBEmployeeManager byBEmployeeManager;

    public EmployeeController(ByBEmployeeManager byBEmployeeManager) {
        this.byBEmployeeManager = byBEmployeeManager;
    }

    @ApiOperation("查询员工信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "username", value = "姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "telephone", value = "电话", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sex", value = "性别(男/女)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "pinyin", value = "拼音(例如: z)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "positionState", value = "在职状态(在职/离职)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "部门code", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "isLeader", value = "是否是负责人(是/不是)", dataType = "string", paramType = "query")
    })
    @PostMapping("/flipList")
    public Result flipList(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "telephone", defaultValue = "") String telephone,
            @RequestParam(value = "sex", defaultValue = "") String sex,
            @RequestParam(value = "pinyin", defaultValue = "") String pinyin,
            @RequestParam(value = "positionState", defaultValue = "") String positionState,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode,
            @RequestParam(value = "isLeader", defaultValue = "") String isLeader
    ) {
        Page page = byBEmployeeManager.search(username, telephone, sex, pinyin, positionState, departmentCode, isLeader, pageNo, pageSize);
        return Result.ok(page);
    }

    @ApiOperation("查询员工信息(无分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "telephone", value = "电话", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sex", value = "性别(男/女)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "pinyin", value = "拼音(例如: z)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "positionState", value = "在职状态(在职/离职)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "部门code", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "isLeader", value = "是否是负责人(是/不是)", dataType = "string", paramType = "query")
    })
    @PostMapping("/flipListWithoutPage")
    public Result flipListWithoutPage(
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "telephone", defaultValue = "") String telephone,
            @RequestParam(value = "sex", defaultValue = "") String sex,
            @RequestParam(value = "pinyin", defaultValue = "") String pinyin,
            @RequestParam(value = "positionState", defaultValue = "") String positionState,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode,
            @RequestParam(value = "isLeader", defaultValue = "") String isLeader
    ) {
        List<ByBEmployee> byBEmployeeList = byBEmployeeManager.searchWithoutPage(username, telephone, sex, pinyin, positionState, departmentCode, isLeader);
        return Result.ok(byBEmployeeList);
    }

    @ApiOperation("查询员工姓名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "departmentCode", value = "部门code", dataType = "string", paramType = "query")
    })
    @PostMapping("/flipListForUsername")
    public Result flipListForUsername(
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode
    ) {
        List<ByBEmployee> byBEmployeeList = byBEmployeeManager.searchForUsername(departmentCode);
        return Result.ok(byBEmployeeList);
    }

    @ApiOperation("查询负责人姓名")
    @PostMapping("/flipListForLeaderUsername")
    public Result flipListForLeaderUsername() {
        List<ByBEmployee> byBEmployeeList = byBEmployeeManager.searchForLeader();
        return Result.ok(byBEmployeeList);
    }


    @ApiOperation("员工管理 - 编辑员工信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "username", value = "姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sex", value = "性别(男/女)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "telephone", value = "联系方式", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "icon", value = "微信图标", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "entryTime", value = "入职日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "educationTypeId", value = "学历id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "graduated", value = "毕业院校", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "idNumber", value = "身份证号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "socialSecurity", value = "是否缴纳社保(未缴纳/缴纳)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "pinyin", value = "拼音", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "workStates", value = "转正状态(已转正/未转正)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "promotionTime", value = "转正日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "contractSigning", value = "合同签订日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "contractRenewalDate", value = "合同续签日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "expirationContract", value = "到期日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "socialSecurityDate", value = "缴纳社保日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "major", value = "专业", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "graduatedDate", value = "毕业时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "部门code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "positionState", value = "在职状态(在职/离职)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isLeader", value = "是否是部门负责人(是/不是)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isGroupLeader", value = "是否是小组负责人(是/不是)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "positionalTitleTypeId", value = "职称id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "positionTypeId", value = "职位id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "workPermit", value = "作业证办理情况(办理/未办理)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "positionalTitleReviewDate", value = "职称评审时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "accidentInsurancePayment", value = "意外保险缴纳(已购买/未购买)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "physicalExamination", value = "体检情况(体检/未体检)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "probationTime", value = "试用期时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "leaveDate", value = "离职日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "maritalStatus", value = "婚姻状况(已婚/未婚)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "workContent", value = "工作内容", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "email", value = "邮箱", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "emergencyContact", value = "紧急联系人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "emergencyContactTelephone", value = "紧急电话", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "address", value = "住址", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isConfidentialityAgreement", value = "是否签订保密协议(是/否)", dataType = "string", paramType = "query")
    })
    @PostMapping("/edit")
    public Result edit(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "sex", defaultValue = "") String sex,
            @RequestParam(value = "telephone", defaultValue = "") String telephone,
            @RequestParam(value = "icon", defaultValue = "") String icon,
            @RequestParam(value = "entryTime", defaultValue = "") String entryTime,
            @RequestParam(value = "educationTypeId", defaultValue = "") Integer educationTypeId,
            @RequestParam(value = "graduated", defaultValue = "") String graduated,
            @RequestParam(value = "idNumber", defaultValue = "") String idNumber,
            @RequestParam(value = "socialSecurity", defaultValue = "") String socialSecurity,
            @RequestParam(value = "pinyin", defaultValue = "") String pinyin,
            @RequestParam(value = "workStates", defaultValue = "") String workStates,
            @RequestParam(value = "promotionTime", defaultValue = "") String promotionTime,
            @RequestParam(value = "contractSigning", defaultValue = "") String contractSigning,
            @RequestParam(value = "contractRenewalDate", defaultValue = "") String contractRenewalDate,
            @RequestParam(value = "expirationContract", defaultValue = "") String expirationContract,
            @RequestParam(value = "socialSecurityDate", defaultValue = "") String socialSecurityDate,
            @RequestParam(value = "major", defaultValue = "") String major,
            @RequestParam(value = "graduatedDate", defaultValue = "") String graduatedDate,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode,
            @RequestParam(value = "positionState", defaultValue = "在职") String positionState,
            @RequestParam(value = "isLeader", defaultValue = "") String isLeader,
            @RequestParam(value = "isGroupLeader", defaultValue = "") String isGroupLeader,
            @RequestParam(value = "positionalTitleTypeId", defaultValue = "") Integer positionalTitleTypeId,
            @RequestParam(value = "positionTypeId", defaultValue = "") Integer positionTypeId,
            @RequestParam(value = "workPermit", defaultValue = "") String workPermit,
            @RequestParam(value = "positionalTitleReviewDate", defaultValue = "") String positionalTitleReviewDate,
            @RequestParam(value = "accidentInsurancePayment", defaultValue = "") String accidentInsurancePayment,
            @RequestParam(value = "physicalExamination", defaultValue = "") String physicalExamination,
            @RequestParam(value = "probationTime", defaultValue = "") String probationTime,
            @RequestParam(value = "leaveDate", defaultValue = "") String leaveDate,
            @RequestParam(value = "remark", defaultValue = "") String remark,
            @RequestParam(value = "maritalStatus", defaultValue = "") String maritalStatus,
            @RequestParam(value = "workContent", defaultValue = "") String workContent,
            @RequestParam(value = "email", defaultValue = "") String email,
            @RequestParam(value = "emergencyContact", defaultValue = "") String emergencyContact,
            @RequestParam(value = "emergencyContactTelephone", defaultValue = "") String emergencyContactTelephone,
            @RequestParam(value = "address", defaultValue = "") String address,
            @RequestParam(value = "isConfidentialityAgreement", defaultValue = "") String isConfidentialityAgreement,
            MultipartFile file
    ) {
        ByBEmployee byBEmployee = new ByBEmployee();
        //图标处理
        if (file == null) {
            byBEmployee.setIcon(icon);
        } else {
            Long size = file.getSize();
            Integer fileSize = Integer.valueOf(size.toString()) / 1024;
            if (fileSize > 100) {
                String msg = "图片太大,请更换图片";
                return Result.ok(msg);
            }
            String img = ImageTools.getImageStr(file);
            byBEmployee.setIcon(img);
        }
        if (id != null) {
            byBEmployee.setId(id);
        }
        byBEmployee.setUsername(username);
        byBEmployee.setSex(sex);
        byBEmployee.setTelephone(telephone);
        byBEmployee.setEntryTime(entryTime);
        byBEmployee.setEducationTypeId(educationTypeId);
        byBEmployee.setGraduated(graduated);
        byBEmployee.setIdNumber(idNumber);
        byBEmployee.setSocialSecurity(socialSecurity);
        byBEmployee.setWorkStates(workStates);
        byBEmployee.setPromotionTime(promotionTime);
        byBEmployee.setContractSigning(contractSigning);
        byBEmployee.setContractRenewalDate(contractRenewalDate);
        byBEmployee.setExpirationContract(expirationContract);
        byBEmployee.setSocialSecurityDate(socialSecurityDate);
        byBEmployee.setMajor(major);
        byBEmployee.setGraduatedDate(graduatedDate);
        byBEmployee.setDepartmentCode(departmentCode);
        byBEmployee.setPositionState(positionState);
        byBEmployee.setIsLeader(isLeader);
        byBEmployee.setIsGroupLeader(isGroupLeader);
        byBEmployee.setPositionalTitleTypeId(positionalTitleTypeId);
        byBEmployee.setPositionTypeId(positionTypeId);
        byBEmployee.setWorkPermit(workPermit);
        byBEmployee.setPositionalTitleReviewDate(positionalTitleReviewDate);
        byBEmployee.setAccidentInsurancePayment(accidentInsurancePayment);
        byBEmployee.setPhysicalExamination(physicalExamination);
        byBEmployee.setRemark(remark);
        byBEmployee.setProbationTime(probationTime);
        byBEmployee.setLeaveDate(leaveDate);
        byBEmployee.setMaritalStatus(maritalStatus);
        byBEmployee.setWorkContent(workContent);
        byBEmployee.setEmail(email);
        byBEmployee.setEmergencyContact(emergencyContact);
        byBEmployee.setEmergencyContactTelephone(emergencyContactTelephone);
        byBEmployee.setAddress(address);
        byBEmployee.setIsConfidentialityAgreement(isConfidentialityAgreement);
        byBEmployeeManager.upperSave(byBEmployee);
        return Result.ok();
    }

    @ApiOperation("员工管理 - 修改员工在职状态信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "positionState", value = "在职状态(在职/离职)", dataType = "string", paramType = "query")
    })
    @PostMapping("/editForPositionState")
    public Result editForPositionState(
            @RequestParam(value = "id", defaultValue = "") Integer id,
            @RequestParam(value = "positionState", defaultValue = "") String positionState
    ) {
        byBEmployeeManager.updateByIdForPositionState(id, positionState);
        return Result.ok();
    }

    @ApiOperation("员工管理 - 上传微信图标")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @PostMapping("/editForIcon")
    public Result editForIcon(
            @RequestParam(value = "id", defaultValue = "") Integer id,
            @RequestParam MultipartFile file
    ) {
        if (file.isEmpty()) {
            return Result.ok("未选择图片");
        } else {
            Long size = file.getSize();
            Integer fileSize = Integer.valueOf(size.toString()) / 1024;
            if (fileSize > 100) {
                String msg = "图片太大,请更换图片";
                return Result.ok(msg);
            }
            String img = ImageTools.getImageStr(file);
            byBEmployeeManager.updateByIdForIcon(id, img);
            return Result.ok();
        }
    }

    @ApiOperation("员工信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "departmentCode", value = "部门code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "positionState", value = "在职状态(在职/离职)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isLeader", value = "是否是部门负责人(是/不是)", dataType = "string", paramType = "query")
    })
    @PostMapping("/detailForDepartmentLeader")
    public Result detailForDepartmentLeader(
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode,
            @RequestParam(value = "positionState", defaultValue = "在职") String positionState,
            @RequestParam(value = "isLeader", defaultValue = "是") String isLeader
    ) {
        ByBEmployee byBEmployee = byBEmployeeManager.searchDepartmentLeader(departmentCode, positionState, isLeader);
        return Result.ok(byBEmployee);
    }

    @ApiOperation("获取部门负责人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "", dataType = "int", paramType = "query")
    })
    @PostMapping("/detail")
    public Result detail(
            @RequestParam(value = "id", defaultValue = "") Integer id
    ) {
        ByBEmployee byBEmployee = byBEmployeeManager.searchDetails(id);
        return Result.ok(byBEmployee);
    }

    @ApiOperation("员工管理 - 删除员工信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids(例如: 一个:1 ; 多个:1,2 )", dataType = "string", paramType = "query")
    })
    @PostMapping("/remove")
    public Result remove(
            @RequestParam(value = "ids") String ids
    ) {
        byBEmployeeManager.removeByIds(ids);
        return Result.ok();
    }

}
