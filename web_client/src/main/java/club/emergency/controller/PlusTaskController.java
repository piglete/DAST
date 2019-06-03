package club.emergency.controller;

import club.emergency.project.manager.ByPlusTaskManager;
import club.emergency.project.model.ByPlusTask;
import club.emergency.project.model.ByRRecord;
import club.map.core.model.Page;
import club.map.core.web.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhaojinxiong
 * Date: 2018-11-20
 * Time: 14:52
 * Description: 按照任务流程
 * <1> </1>.办公室创建项目
 * 1.1创建任务，下达给部门->
 * <2>.部门接收任务->1.部门退回任务
 * ->2.1部门下达任务(下达任务至小组)->
 * ->2.2判断是否需要部门外协,如果需要外协,则指定具体的外协部门;任务暂停设置(可选)
 * <3>.小组接收任务(小组退回任务需自己内部和部门负责人协商)->
 * ->3.1小组完成任务->
 * ->3.2如果有外协部门,则小组提交之前外协部门必须先提交给小组,小组确认后,才可提交下一步,小组需要填写项目的部分信息(位置,经纬度,项目名称等);
 * <4>.一检接收任务(部门内自查,一般由部门负责人或者小组负责人审核),如果审核不通过,自己内部商议修改->
 * ->4.1一检核对完成->
 * ->4.2 一检填写资料编号(唯一)
 * <5>.二检接收任务(由二检部门审核)->
 * ->5.1二检退回任务(二检发现重大错误问题,直接退回到小组,退回进行记录)
 * ->5.2二检核对完成(评分)->
 * ->5.3二检判断是否需要外查,外查处理
 * <6>.三检接收任务(由三检部门审核)
 * ->6.1三检退回任务(三检发现重大错误问题,直接退回到小组,退回进行记录)
 * ->6.2三检核对完成(评分,任务超期计算)->
 * <7>.如果所有任务都完成,项目才可完成;项目是否超期;当前系统中设置为一个项目对应一个任务,则当任务完成时,项目则流转完成
 * <8>.档案部进行档案录入
 * ->8.1档案部接收,判断资料是否已经全部交付
 * ->8.2资料上传,档案信息录入
 * <9>.项目完成(档案部提交完成后项目完成)
 */
@Api("任务管理")
@CrossOrigin
@RestController
@RequestMapping("/plus-task")
public class PlusTaskController {

    private ByPlusTaskManager byPlusTaskManager;

    public PlusTaskController(ByPlusTaskManager byPlusTaskManager) {
        this.byPlusTaskManager = byPlusTaskManager;
    }

    @ApiOperation("查询任务信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目编号", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "部门code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "taskStateId", value = "任务状态id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "employeeId", value = "负责人id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "workGroupEmployeeId", value = "小组负责人id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "taskName", value = "任务名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "oneInspectionUserId", value = "一检人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "twoInspectionUserId", value = "二检人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "threeInspectionUserId", value = "三检人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "recordInspectionUserId", value = "档案部接收人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "outCheckUserId", value = "外查人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "outCheckState", value = "外查状态(0为不需要外查,1为需要外查,2为外查接收,3为外查完成)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "workGroupCode", value = "作业小组code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "flag", value = "任务完成标识(0为进行中,1为已完成)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "invalidFlag", value = "任务作废标识(针对工程部回退,0为不作废,1为作废)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "employeeId", value = "负责人id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "workGroupEmployeeId", value = "小组负责人id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "taskName", value = "任务名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "oneInspectionUserId", value = "一检人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "twoInspectionUserId", value = "二检人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "threeInspectionUserId", value = "三检人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "recordInspectionUserId", value = "档案部接收人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "outCheckUserId", value = "外查人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "outCheckState", value = "外查状态(0为不需要外查,1为需要外查,2为外查接收,3为外查完成)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "workGroupCode", value = "作业小组code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "flag", value = "任务标识(0为进行中,1为暂停,2已完成未入库,3为完成且入库)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "invalidFlag", value = "任务作废标识(针对工程部回退,0为不作废,1为作废)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "fileNumber", value = "资料编号", dataType = "string", paramType = "query")
    })
    @PostMapping("/flipList")
    public Result flipList(
            @RequestParam(value = "projectId", defaultValue = "") Integer projectId,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode,
            @RequestParam(value = "taskStateId", defaultValue = "") Integer taskStateId,
            @RequestParam(value = "employeeId", defaultValue = "") Integer employeeId,
            @RequestParam(value = "workGroupEmployeeId", defaultValue = "") Integer workGroupEmployeeId,
            @RequestParam(value = "taskName", defaultValue = "") String taskName,
            @RequestParam(value = "oneInspectionUserId", defaultValue = "") Integer oneInspectionUserId,
            @RequestParam(value = "twoInspectionUserId", defaultValue = "") Integer twoInspectionUserId,
            @RequestParam(value = "threeInspectionUserId", defaultValue = "") Integer threeInspectionUserId,
            @RequestParam(value = "recordInspectionUserId", defaultValue = "") Integer recordInspectionUserId,
            @RequestParam(value = "outCheckUserId", defaultValue = "") Integer outCheckUserId,
            @RequestParam(value = "outCheckState", defaultValue = "") Integer outCheckState,
            @RequestParam(value = "workGroupCode", defaultValue = "") String workGroupCode,
            @RequestParam(value = "flag", defaultValue = "") Integer flag,
            @RequestParam(value = "invalidFlag", defaultValue = "") Integer invalidFlag,
            @RequestParam(value = "fileNumber", defaultValue = "") String fileNumber
    ) {
        List<ByPlusTask> byPlusTaskList = byPlusTaskManager.search(projectId, departmentCode, taskStateId, employeeId, workGroupEmployeeId, taskName, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId, recordInspectionUserId, outCheckUserId, outCheckState, workGroupCode, flag, invalidFlag, fileNumber);
        return Result.ok(byPlusTaskList);
    }

    @ApiOperation("查询项目和任务综合信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "项目名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "recordTypeId", value = "项目类型id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "unitName", value = "甲方单位名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "linkman", value = "联系人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "telephone", value = "联系方式", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "projectPeriod", value = "项目周期(天)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "regionCode", value = "区域code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "开始日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "部门code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "taskStateId", value = "任务状态id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "employeeId", value = "负责人id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "workGroupEmployeeId", value = "小组负责人id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "oneInspectionUserId", value = "一检人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "twoInspectionUserId", value = "二检人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "threeInspectionUserId", value = "三检人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "recordInspectionUserId", value = "档案部接收人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "outCheckUserId", value = "外查人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "outCheckState", value = "外查状态(0为不需要外查,1为需要外查,2为外查接收,3为外查完成)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "workGroupCode", value = "作业小组code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "flag", value = "任务标识(0为进行中,1为暂停,2已完成未入库,3为完成且入库)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "invalidFlag", value = "任务作废标识(针对工程部回退,0为不作废,1为作废)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "assistFlag", value = "外协标识(0为没有外协,1为有外协)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "fileNumber", value = "资料编号", dataType = "string", paramType = "query")
    })
    @PostMapping("/flipListWithProjectInfo")
    public Result flipListWithProjectInfo(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "recordTypeId", defaultValue = "") Integer recordTypeId,
            @RequestParam(value = "unitName", defaultValue = "") String unitName,
            @RequestParam(value = "linkman", defaultValue = "") String linkman,
            @RequestParam(value = "telephone", defaultValue = "") String telephone,
            @RequestParam(value = "projectPeriod", defaultValue = "") Integer projectPeriod,
            @RequestParam(value = "regionCode", defaultValue = "") String regionCode,
            @RequestParam(value = "startDate", defaultValue = "") String startDate,
            @RequestParam(value = "endDate", defaultValue = "") String endDate,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode,
            @RequestParam(value = "taskStateId", defaultValue = "") Integer taskStateId,
            @RequestParam(value = "employeeId", defaultValue = "") Integer employeeId,
            @RequestParam(value = "workGroupEmployeeId", defaultValue = "") Integer workGroupEmployeeId,
            @RequestParam(value = "oneInspectionUserId", defaultValue = "") Integer oneInspectionUserId,
            @RequestParam(value = "twoInspectionUserId", defaultValue = "") Integer twoInspectionUserId,
            @RequestParam(value = "threeInspectionUserId", defaultValue = "") Integer threeInspectionUserId,
            @RequestParam(value = "recordInspectionUserId", defaultValue = "") Integer recordInspectionUserId,
            @RequestParam(value = "outCheckUserId", defaultValue = "") Integer outCheckUserId,
            @RequestParam(value = "outCheckState", defaultValue = "") Integer outCheckState,
            @RequestParam(value = "workGroupCode", defaultValue = "") String workGroupCode,
            @RequestParam(value = "flag", defaultValue = "") Integer flag,
            @RequestParam(value = "invalidFlag", defaultValue = "") Integer invalidFlag,
            @RequestParam(value = "assistFlag", defaultValue = "0") Integer assistFlag,
            @RequestParam(value = "fileNumber", defaultValue = "") String fileNumber
    ) {
        Page page = byPlusTaskManager.searchWithProjectInfo(pageNo, pageSize, name, recordTypeId, unitName, linkman, telephone, projectPeriod, regionCode, startDate, endDate, departmentCode, taskStateId, employeeId, workGroupEmployeeId, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId, recordInspectionUserId, outCheckUserId, outCheckState, workGroupCode, flag, invalidFlag, assistFlag, fileNumber);
        return Result.ok(page);
    }

    @ApiOperation("任务管理 - 导出项目任务信息(excel)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "departmentCode", value = "部门code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "workGroupCode", value = "作业小组code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "开始日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "oneInspectionUserId", value = "一检人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "twoInspectionUserId", value = "二检人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "threeInspectionUserId", value = "三检人员id", dataType = "int", paramType = "query")
    })
    @GetMapping("/taskExport")
    public Result taskExport(
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode,
            @RequestParam(value = "workGroupCode", defaultValue = "") String workGroupCode,
            @RequestParam(value = "startDate", defaultValue = "") String startDate,
            @RequestParam(value = "endDate", defaultValue = "") String endDate,
            @RequestParam(value = "oneInspectionUserId", defaultValue = "") Integer oneInspectionUserId,
            @RequestParam(value = "twoInspectionUserId", defaultValue = "") Integer twoInspectionUserId,
            @RequestParam(value = "threeInspectionUserId", defaultValue = "") Integer threeInspectionUserId,
            HttpServletResponse response
    ) {
        byPlusTaskManager.taskExport(departmentCode, workGroupCode, startDate, endDate, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId, response);
        return Result.ok();
    }

    @ApiOperation("任务管理 - 编辑任务信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "projectId", value = "项目编号", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "employeeId", value = "负责人id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "taskName", value = "任务名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "部门code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "taskStateId", value = "任务状态id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "overDateCount", value = "超期天数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "oneInspectionUser", value = "一检人员", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "twoInspectionUser", value = "二检人员", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "threeInspectionUser", value = "三检人员", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "oneRevertDate", value = "一检接收时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "twoRevertDate", value = "二检接收时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "threeRevertDate", value = "三检接收时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "oneCheckDate", value = "一检核对时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "twoCheckDate", value = "二检核对时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "threeCheckDate", value = "三检核对时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "assignTaskUser", value = "下达任务人员", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "workGroupCode", value = "作业小组code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "workGroupUser", value = "小组负责人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "workGroupEmployeeId", value = "小组负责人id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "groupRevertDate", value = "小组接收时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "groupCommitDate", value = "小组提交时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "taskScore", value = "任务评定分数", dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "returnReasonId", value = "退回原因id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "returnReason", value = "退回原因(查看时显示需要,不需要填写)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "groupFinishPeriod", value = "小组完成周期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "oneFinishPeriod", value = "一检完成周期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "twoFinishPeriod", value = "二检完成周期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "threeFinishPeriod", value = "三检完成周期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "departmentRevertDate", value = "部门接收任务时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "departmentReleaseDate", value = "部门下达任务时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "departmentReturnDate", value = "部门退回任务时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "taskTwoScore", value = "二检评定分数", dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "taskThreeScore", value = "三检评定分数", dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "twoReturnCount", value = "二检回退次数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "threeReturnCount", value = "三检回退次数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "returnState", value = "当前回退状态(0为未回退,2为二检回退,3为三检回退)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "recordReturnCount", value = "档案部回退次数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "recordRevertDate", value = "档案部接收时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "recordCheckDate", value = "档案部核对时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "recordInspectionUser", value = "档案部接收人员", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "oneInspectionUserId", value = "一检人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "twoInspectionUserId", value = "二检人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "threeInspectionUserId", value = "三检人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "recordInspectionUserId", value = "档案部接收人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "outCheckUser", value = "外查人员", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "outCheckUserId", value = "外查人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "outCheckRevertDate", value = "外查接收时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "outCheckFinishDate", value = "外查完成时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "outCheckFinishPeriod", value = "外查周期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "outCheckState", value = "外查状态(0为不需要外查,1为需要外查,2为外查接收,3为外查完成)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "outCheckDescription", value = "外查描述", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "outCheckDate", value = "外查时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "flag", value = "任务标识(0为进行中,1为暂停,2已完成未入库,3为完成且入库)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "invalidFlag", value = "任务作废标识(针对工程部回退,0为不作废,1为作废)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "threeReturnTwoFlag", value = "三检退回二检标识(0为没有回退,其他为回退次数)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "fileNumber", value = "资料编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "printUserId", value = "打印人id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "printDate", value = "打印时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "printFlag", value = "资料打印标识(0未提醒打印,1已提醒打印))", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "printRemark", value = "打印备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "printOperateDate", value = "三检指定打印操作日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "dataDeliveryDate", value = "资料交付时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "dataRevertUserId", value = "资料接收人id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "dataDeliveryFlag", value = "资料交付标识(0未提醒交付,1已提醒交付)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "dataOperateDate", value = "三检指定资料交付操作日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "dataRevertRemark", value = "资料接收备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "stopCount", value = "任务中途暂停次数(0为没有暂停,其他为暂停次数)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "assistFlag", value = "外协标识(0为没有外协,1为有外协)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "printFinishFlag", value = "工程部打印完成提醒标识(0为未提醒三检,1为已提醒三检)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "oneInspectionRemark", value = "一检备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "twoInspectionRemark", value = "二检备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "threeInspectionRemark", value = "三检备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "departmentRemark", value = "部门备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "groupRemark", value = "小组备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "办公室下达备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "groupReturnRemark", value = "小组撤回备注", dataType = "string", paramType = "query")
    })
    @PostMapping("/edit")
    public String edit(
            @RequestParam(value = "id", defaultValue = "") Integer id,
            @RequestParam(value = "projectId", defaultValue = "") Integer projectId,
            @RequestParam(value = "employeeId", defaultValue = "") Integer employeeId,
            @RequestParam(value = "taskName", defaultValue = "") String taskName,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode,
            @RequestParam(value = "taskStateId", defaultValue = "") Integer taskStateId,
            @RequestParam(value = "overDateCount", defaultValue = "0") Integer overDateCount,
            @RequestParam(value = "oneInspectionUser", defaultValue = "") String oneInspectionUser,
            @RequestParam(value = "twoInspectionUser", defaultValue = "") String twoInspectionUser,
            @RequestParam(value = "threeInspectionUser", defaultValue = "") String threeInspectionUser,
            @RequestParam(value = "oneRevertDate", defaultValue = "") String oneRevertDate,
            @RequestParam(value = "twoRevertDate", defaultValue = "") String twoRevertDate,
            @RequestParam(value = "threeRevertDate", defaultValue = "") String threeRevertDate,
            @RequestParam(value = "oneCheckDate", defaultValue = "") String oneCheckDate,
            @RequestParam(value = "twoCheckDate", defaultValue = "") String twoCheckDate,
            @RequestParam(value = "threeCheckDate", defaultValue = "") String threeCheckDate,
            @RequestParam(value = "assignTaskUser", defaultValue = "") String assignTaskUser,
            @RequestParam(value = "workGroupCode", defaultValue = "") String workGroupCode,
            @RequestParam(value = "workGroupUser", defaultValue = "") String workGroupUser,
            @RequestParam(value = "workGroupEmployeeId", defaultValue = "") Integer workGroupEmployeeId,
            @RequestParam(value = "groupRevertDate", defaultValue = "") String groupRevertDate,
            @RequestParam(value = "groupCommitDate", defaultValue = "") String groupCommitDate,
            @RequestParam(value = "taskScore", defaultValue = "0") Double taskScore,
            @RequestParam(value = "returnReasonId", defaultValue = "") Integer returnReasonId,
            @RequestParam(value = "groupFinishPeriod", defaultValue = "") String groupFinishPeriod,
            @RequestParam(value = "oneFinishPeriod", defaultValue = "") String oneFinishPeriod,
            @RequestParam(value = "twoFinishPeriod", defaultValue = "") String twoFinishPeriod,
            @RequestParam(value = "threeFinishPeriod", defaultValue = "") String threeFinishPeriod,
            @RequestParam(value = "departmentRevertDate", defaultValue = "") String departmentRevertDate,
            @RequestParam(value = "departmentReleaseDate", defaultValue = "") String departmentReleaseDate,
            @RequestParam(value = "departmentReturnDate", defaultValue = "") String departmentReturnDate,
            @RequestParam(value = "taskTwoScore", defaultValue = "0") Double taskTwoScore,
            @RequestParam(value = "taskThreeScore", defaultValue = "0") Double taskThreeScore,
            @RequestParam(value = "twoReturnCount", defaultValue = "0") Integer twoReturnCount,
            @RequestParam(value = "threeReturnCount", defaultValue = "0") Integer threeReturnCount,
            @RequestParam(value = "returnState", defaultValue = "0") Integer returnState,
            @RequestParam(value = "recordReturnCount", defaultValue = "0") Integer recordReturnCount,
            @RequestParam(value = "recordRevertDate", defaultValue = "") String recordRevertDate,
            @RequestParam(value = "recordCheckDate", defaultValue = "") String recordCheckDate,
            @RequestParam(value = "recordInspectionUser", defaultValue = "") String recordInspectionUser,
            @RequestParam(value = "oneInspectionUserId", defaultValue = "") Integer oneInspectionUserId,
            @RequestParam(value = "twoInspectionUserId", defaultValue = "") Integer twoInspectionUserId,
            @RequestParam(value = "threeInspectionUserId", defaultValue = "") Integer threeInspectionUserId,
            @RequestParam(value = "recordInspectionUserId", defaultValue = "") Integer recordInspectionUserId,
            @RequestParam(value = "outCheckUser", defaultValue = "") String outCheckUser,
            @RequestParam(value = "outCheckUserId", defaultValue = "") Integer outCheckUserId,
            @RequestParam(value = "outCheckRevertDate", defaultValue = "") String outCheckRevertDate,
            @RequestParam(value = "outCheckFinishDate", defaultValue = "") String outCheckFinishDate,
            @RequestParam(value = "outCheckFinishPeriod", defaultValue = "") String outCheckFinishPeriod,
            @RequestParam(value = "outCheckState", defaultValue = "0") Integer outCheckState,
            @RequestParam(value = "outCheckDescription", defaultValue = "") String outCheckDescription,
            @RequestParam(value = "outCheckDate", defaultValue = "") String outCheckDate,
            @RequestParam(value = "flag", defaultValue = "0") Integer flag,
            @RequestParam(value = "invalidFlag", defaultValue = "0") Integer invalidFlag,
            @RequestParam(value = "threeReturnTwoFlag", defaultValue = "0") Integer threeReturnTwoFlag,
            @RequestParam(value = "fileNumber", defaultValue = "") String fileNumber,
            @RequestParam(value = "printUserId", defaultValue = "") Integer printUserId,
            @RequestParam(value = "printDate", defaultValue = "") String printDate,
            @RequestParam(value = "printFlag", defaultValue = "0") Integer printFlag,
            @RequestParam(value = "printRemark", defaultValue = "") String printRemark,
            @RequestParam(value = "printOperateDate", defaultValue = "") String printOperateDate,
            @RequestParam(value = "dataDeliveryDate", defaultValue = "") String dataDeliveryDate,
            @RequestParam(value = "dataRevertUserId", defaultValue = "") Integer dataRevertUserId,
            @RequestParam(value = "dataDeliveryFlag", defaultValue = "0") Integer dataDeliveryFlag,
            @RequestParam(value = "dataOperateDate", defaultValue = "") String dataOperateDate,
            @RequestParam(value = "dataRevertRemark", defaultValue = "") String dataRevertRemark,
            @RequestParam(value = "stopCount", defaultValue = "0") Integer stopCount,
            @RequestParam(value = "assistFlag", defaultValue = "0") Integer assistFlag,
            @RequestParam(value = "printFinishFlag", defaultValue = "0") Integer printFinishFlag,
            @RequestParam(value = "oneInspectionRemark", defaultValue = "") String oneInspectionRemark,
            @RequestParam(value = "twoInspectionRemark", defaultValue = "") String twoInspectionRemark,
            @RequestParam(value = "threeInspectionRemark", defaultValue = "") String threeInspectionRemark,
            @RequestParam(value = "departmentRemark", defaultValue = "") String departmentRemark,
            @RequestParam(value = "groupRemark", defaultValue = "") String groupRemark,
            @RequestParam(value = "remark", defaultValue = "") String remark,
            @RequestParam(value = "groupReturnRemark", defaultValue = "") String groupReturnRemark
    ) {
        ByPlusTask byPlusTask = new ByPlusTask();
        if (id != null) {
            byPlusTask.setId(id);
        }
        byPlusTask.setProjectId(projectId);
        byPlusTask.setEmployeeId(employeeId);
        byPlusTask.setTaskName(taskName);
        byPlusTask.setDepartmentCode(departmentCode);
        byPlusTask.setTaskStateId(taskStateId);
        byPlusTask.setOverDateCount(overDateCount);
        byPlusTask.setOneInspectionUser(oneInspectionUser);
        byPlusTask.setTwoInspectionUser(twoInspectionUser);
        byPlusTask.setThreeInspectionUser(threeInspectionUser);
        byPlusTask.setOneRevertDate(oneRevertDate);
        byPlusTask.setTwoRevertDate(twoRevertDate);
        byPlusTask.setThreeRevertDate(threeRevertDate);
        byPlusTask.setOneCheckDate(oneCheckDate);
        byPlusTask.setTwoCheckDate(twoCheckDate);
        byPlusTask.setThreeCheckDate(threeCheckDate);
        byPlusTask.setAssignTaskUser(assignTaskUser);
        byPlusTask.setWorkGroupCode(workGroupCode);
        byPlusTask.setWorkGroupUser(workGroupUser);
        byPlusTask.setWorkGroupEmployeeId(workGroupEmployeeId);
        byPlusTask.setGroupRevertDate(groupRevertDate);
        byPlusTask.setGroupCommitDate(groupCommitDate);
        byPlusTask.setTaskScore(taskScore);
        byPlusTask.setReturnReasonId(returnReasonId);
        byPlusTask.setGroupFinishPeriod(groupFinishPeriod);
        byPlusTask.setOneFinishPeriod(oneFinishPeriod);
        byPlusTask.setTwoFinishPeriod(twoFinishPeriod);
        byPlusTask.setThreeFinishPeriod(threeFinishPeriod);
        byPlusTask.setDepartmentRevertDate(departmentRevertDate);
        byPlusTask.setDepartmentReleaseDate(departmentReleaseDate);
        byPlusTask.setDepartmentReturnDate(departmentReturnDate);
        byPlusTask.setTaskTwoScore(taskTwoScore);
        byPlusTask.setTaskThreeScore(taskThreeScore);
        byPlusTask.setTwoReturnCount(twoReturnCount);
        byPlusTask.setThreeReturnCount(threeReturnCount);
        byPlusTask.setReturnState(returnState);
        byPlusTask.setRecordReturnCount(recordReturnCount);
        byPlusTask.setRecordRevertDate(recordRevertDate);
        byPlusTask.setRecordCheckDate(recordCheckDate);
        byPlusTask.setRecordInspectionUser(recordInspectionUser);
        byPlusTask.setOneInspectionUserId(oneInspectionUserId);
        byPlusTask.setTwoInspectionUserId(twoInspectionUserId);
        byPlusTask.setThreeInspectionUserId(threeInspectionUserId);
        byPlusTask.setRecordInspectionUserId(recordInspectionUserId);
        byPlusTask.setOutCheckUser(outCheckUser);
        byPlusTask.setOutCheckUserId(outCheckUserId);
        byPlusTask.setOutCheckRevertDate(outCheckRevertDate);
        byPlusTask.setOutCheckFinishDate(outCheckFinishDate);
        byPlusTask.setOutCheckFinishPeriod(outCheckFinishPeriod);
        byPlusTask.setOutCheckState(outCheckState);
        byPlusTask.setOutCheckDescription(outCheckDescription);
        byPlusTask.setOutCheckDate(outCheckDate);
        byPlusTask.setFlag(flag);
        byPlusTask.setInvalidFlag(invalidFlag);
        byPlusTask.setThreeReturnTwoFlag(threeReturnTwoFlag);
        byPlusTask.setFileNumber(fileNumber);
        byPlusTask.setPrintUserId(printUserId);
        byPlusTask.setPrintDate(printDate);
        byPlusTask.setPrintFlag(printFlag);
        byPlusTask.setPrintRemark(printRemark);
        byPlusTask.setDataRevertUserId(dataRevertUserId);
        byPlusTask.setDataDeliveryDate(dataDeliveryDate);
        byPlusTask.setDataRevertRemark(dataRevertRemark);
        byPlusTask.setPrintOperateDate(printOperateDate);
        byPlusTask.setDataDeliveryFlag(dataDeliveryFlag);
        byPlusTask.setDataOperateDate(dataOperateDate);
        byPlusTask.setStopCount(stopCount);
        byPlusTask.setAssistFlag(assistFlag);
        byPlusTask.setPrintFinishFlag(printFinishFlag);
        byPlusTask.setOneInspectionRemark(oneInspectionRemark);
        byPlusTask.setTwoInspectionRemark(twoInspectionRemark);
        byPlusTask.setThreeInspectionRemark(threeInspectionRemark);
        byPlusTask.setDepartmentRemark(departmentRemark);
        byPlusTask.setGroupRemark(groupRemark);
        byPlusTask.setRemark(remark);
        byPlusTask.setGroupReturnRemark(groupReturnRemark);
        int a = byPlusTaskManager.upperSave(byPlusTask);
        if (a == 1) {
            return "该项目下已创建了新任务,不能重复创建!";
        } else {
            return "创建任务成功!";
        }
    }

    @ApiOperation("任务信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "任务id", dataType = "int", paramType = "query")
    })
    @PostMapping("/detail")
    public Result detail(
            @RequestParam(value = "id", defaultValue = "") Integer id
    ) {
        ByPlusTask byPlusTask = byPlusTaskManager.searchDetail(id);
        return Result.ok(byPlusTask);
    }

    @ApiOperation("任务管理 - 删除任务信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", dataType = "string", paramType = "query")
    })
    @PostMapping("/remove")
    public Result remove(
            @RequestParam(value = "ids") String ids
    ) {
        byPlusTaskManager.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("任务管理 - 部门接收任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "messageId", value = "通知消息id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "taskStateId", value = "任务状态id", dataType = "int", paramType = "query")
    })
    @PostMapping("/editForDepartmentRevert")
    public Result editForDepartmentRevert(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "messageId", defaultValue = "") Integer messageId,
            @RequestParam(value = "taskStateId", defaultValue = "") Integer taskStateId
    ) {
        byPlusTaskManager.updateForDepartmentRevert(messageId, taskStateId, id);
        return Result.ok();
    }

    @ApiOperation("任务管理 - 部门退回任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "taskStateId", value = "任务状态id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "returnReasonId", value = "退回原因id", dataType = "int", paramType = "query")
    })
    @PostMapping("/editForTaskReturn")
    public Result editForTaskReturn(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "taskStateId", defaultValue = "") Integer taskStateId,
            @RequestParam(value = "returnReasonId", defaultValue = "") Integer returnReasonId
    ) {
        byPlusTaskManager.updateForTaskReturn(returnReasonId, taskStateId, id);
        return Result.ok();
    }

    @ApiOperation("任务管理 - 部门下达任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "taskStateId", value = "任务状态id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "code", value = "作业小组code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "departmentRemark", value = "部门备注", dataType = "string", paramType = "query")
    })
    @PostMapping("/editForDepartmentReleaseTask")
    public Result editForDepartmentReleaseTask(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "taskStateId", defaultValue = "") Integer taskStateId,
            @RequestParam(value = "code", defaultValue = "") String code,
            @RequestParam(value = "departmentRemark", defaultValue = "") String departmentRemark
    ) {
        byPlusTaskManager.updateForDepartmentReleaseTask(code, taskStateId, departmentRemark, id);
        return Result.ok();
    }

    @ApiOperation("任务管理 - 小组接收任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "messageId", value = "通知消息id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "taskStateId", value = "任务状态id", dataType = "int", paramType = "query")
    })
    @PostMapping("/editForGroupRevert")
    public Result editForGroupRevert(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "messageId", defaultValue = "") Integer messageId,
            @RequestParam(value = "taskStateId", defaultValue = "") Integer taskStateId
    ) {
        byPlusTaskManager.updateForGroupRevert(messageId, taskStateId, id);
        return Result.ok();
    }

    @ApiOperation("任务管理 - 小组完成任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "taskStateId", value = "任务状态id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "groupRemark", value = "小组备注", dataType = "string", paramType = "query")
    })
    @PostMapping("/editForGroupFinish")
    public String editForGroupFinish(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "taskStateId", defaultValue = "") Integer taskStateId,
            @RequestParam(value = "groupRemark", defaultValue = "") String groupRemark
    ) {
        int a = byPlusTaskManager.updateForGroupFinish(taskStateId, groupRemark, id);
        if (a == 1) {
            return "您当前未填写工作量,请先填写完成工作量,再进行提交!";
        } else if (a == 2) {
            return "当前任务有外协部门未完成,请您及时联系相关外协部门处理!";
        } else {
            return "提交成功!";
        }
    }

    @ApiOperation("任务管理 - 一检接收任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "messageId", value = "通知消息id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "taskStateId", value = "任务状态id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "username", value = "检查人名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "oneInspectionUserId", value = "一检人员id", dataType = "int", paramType = "query")
    })
    @PostMapping("/editForOneRevert")
    public String editForOneRevert(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "messageId", defaultValue = "") Integer messageId,
            @RequestParam(value = "taskStateId", defaultValue = "") Integer taskStateId,
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "oneInspectionUserId", defaultValue = "") Integer oneInspectionUserId
    ) {
        int a = byPlusTaskManager.updateForOneRevert(messageId, taskStateId, id, username, oneInspectionUserId);
        if (a == 1) {
            return "当前任务已被其他人接收处理!";
        } else {
            return "接收成功";
        }
    }

    @ApiOperation("任务管理 - 一检审核完成")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "taskStateId", value = "任务状态id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "fileNumber", value = "资料编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "oneInspectionRemark", value = "一检备注", dataType = "string", paramType = "query")
    })
    @PostMapping("/editForOneCheck")
    public String editForOneCheck(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "taskStateId", defaultValue = "") Integer taskStateId,
            @RequestParam(value = "fileNumber", defaultValue = "") String fileNumber,
            @RequestParam(value = "oneInspectionRemark", defaultValue = "") String oneInspectionRemark
    ) {
        int a = byPlusTaskManager.updateForOneCheck(fileNumber, taskStateId, oneInspectionRemark, id);
        if (a == 1) {
            return "当前资料编号已被使用,请重新填写!";
        } else if (a == 2) {
            return "资料编号不能为空,请重新填写!";
        } else {
            return "操作成功!";
        }
    }

    @ApiOperation("任务管理 - 二检接收任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "messageId", value = "通知消息id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "taskStateId", value = "任务状态id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "username", value = "检查人名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "twoInspectionUserId", value = "二检人员id", dataType = "int", paramType = "query")
    })
    @PostMapping("/editForTwoRevert")
    public String editForTwoRevert(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "messageId", defaultValue = "") Integer messageId,
            @RequestParam(value = "taskStateId", defaultValue = "") Integer taskStateId,
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "twoInspectionUserId", defaultValue = "") Integer twoInspectionUserId
    ) {
        int a = byPlusTaskManager.updateForTwoRevert(messageId, taskStateId, id, username, twoInspectionUserId);
        if (a == 1) {
            return "当前任务已被其他人接收处理!";
        } else {
            return "接收完成!";
        }
    }

    @ApiOperation("任务管理 - 二检回退")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "returnUser", value = "回退人员", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "twoReturnReasonIds", value = "二检回退理由id(多个)", dataType = "string", paramType = "query")
    })
    @PostMapping("/editForTwoReturn")
    public Result editForTwoReturn(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "returnUser", defaultValue = "") String returnUser,
            @RequestParam(value = "twoReturnReasonIds", defaultValue = "") String twoReturnReasonIds
    ) {
        byPlusTaskManager.updateForTwoReturn(id, returnUser, twoReturnReasonIds);
        return Result.ok();
    }

    @ApiOperation("任务管理 - 二检审核完成")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "taskStateId", value = "任务状态id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "twoInspectionRemark", value = "二检备注", dataType = "string", paramType = "query")
    })
    @PostMapping("/editForTwoCheck")
    public String editForTwoCheck(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "taskStateId", defaultValue = "") Integer taskStateId,
            @RequestParam(value = "twoInspectionRemark", defaultValue = "") String twoInspectionRemark
    ) {
        int a = byPlusTaskManager.updateForTwoCheck(taskStateId, twoInspectionRemark, id);
        if (a == 1) {
            return "当前任务外查并未接收,请联系外查人员处理";
        } else if (a == 2) {
            return "当前任务外查并未完成,请联系外查人员处理";
        } else {
            return "二检核对完成完成";
        }
    }

    @ApiOperation("任务管理 - 二检指定当前任务外查")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "outCheckDate", value = "外查时间", dataType = "string", paramType = "query")
    })
    @PostMapping("/editForRequireOutCheck")
    public Result editForRequireOutCheck(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "outCheckDate", defaultValue = "") String outCheckDate
    ) {
        byPlusTaskManager.updateForRequireOutRevert(outCheckDate, id);
        return Result.ok();
    }

    @ApiOperation("任务管理 - 外查接收")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "messageId", value = "通知消息id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "outCheckUser", value = "外查接收人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "outCheckUserId", value = "外查接收人id", dataType = "int", paramType = "query")
    })
    @PostMapping("/editForOutCheckRevert")
    public String editForOutCheckRevert(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "messageId", defaultValue = "") Integer messageId,
            @RequestParam(value = "outCheckUser", defaultValue = "") String outCheckUser,
            @RequestParam(value = "outCheckUserId", defaultValue = "") Integer outCheckUserId
    ) {
        int a = byPlusTaskManager.updateForOutRevert(messageId, outCheckUser, outCheckUserId, id);
        if (a == 1) {
            return "当前任务已被其他外查人员接收处理!";
        } else {
            return "外查接收完成";
        }
    }

    @ApiOperation("任务管理 - 外查审核完成")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "outCheckDescription", value = "外查描述", dataType = "string", paramType = "query")
    })
    @PostMapping("/editForOutCheckFinish")
    public Result editForOutCheckFinish(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "outCheckDescription", defaultValue = "") String outCheckDescription
    ) {
        byPlusTaskManager.updateForOutCheck(outCheckDescription, id);
        return Result.ok();
    }

    @ApiOperation("任务管理 - 三检接收任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "messageId", value = "通知消息id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "taskStateId", value = "任务状态id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "username", value = "三检人名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "threeInspectionUserId", value = "三检人员id", dataType = "int", paramType = "query")
    })
    @PostMapping("/editForThreeRevert")
    public String editForThreeRevert(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "messageId", defaultValue = "") Integer messageId,
            @RequestParam(value = "taskStateId", defaultValue = "") Integer taskStateId,
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "threeInspectionUserId", defaultValue = "") Integer threeInspectionUserId
    ) {
        int a = byPlusTaskManager.updateForThreeRevert(messageId, taskStateId, id, username, threeInspectionUserId);
        if (a == 1) {
            return "当前任务已被其他人接收处理!";
        } else {
            return "接收完成!";
        }
    }

    @ApiOperation("任务管理 - 三检回退(回退到小组)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "任务id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "returnUser", value = "回退人员", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "threeReturnReasonIds", value = "回退理由ids(多个)", dataType = "string", paramType = "query")
    })
    @PostMapping("/editForThreeReturn")
    public Result editForThreeReturn(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "returnUser", defaultValue = "") String returnUser,
            @RequestParam(value = "threeReturnReasonIds", defaultValue = "") String threeReturnReasonIds
    ) {
        byPlusTaskManager.updateForThreeReturn(id, returnUser, threeReturnReasonIds);
        return Result.ok();
    }

    @ApiOperation("任务管理 - 三检回退(回退到二检)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "任务id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "returnReasonIds", value = "回退理由ids(多个)", dataType = "string", paramType = "query")
    })
    @PostMapping("/editForThreeReturnTwo")
    public Result editForThreeReturnTwo(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "returnReasonIds", defaultValue = "") String returnReasonIds
    ) {
        byPlusTaskManager.updateForThreeReturnTwo(id, returnReasonIds);
        return Result.ok();
    }

    @ApiOperation("任务管理 - 三检审核完成")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "taskStateId", value = "任务状态id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "threeInspectionRemark", value = "三检备注", dataType = "string", paramType = "query")
    })
    @PostMapping("/editForThreeCheck")
    public Result editForThreeCheck(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "taskStateId", defaultValue = "") Integer taskStateId,
            @RequestParam(value = "threeInspectionRemark", defaultValue = "") String threeInspectionRemark
    ) {
        byPlusTaskManager.updateForThreeCheck(taskStateId, threeInspectionRemark, id);
        return Result.ok();
    }

    @ApiOperation("任务管理 - 档案部接收任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "messageId", value = "通知消息id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "taskStateId", value = "任务状态id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "username", value = "档案部接收人员", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "recordInspectionUserId", value = "档案部接收人员id", dataType = "int", paramType = "query")
    })
    @PostMapping("/editForRecordRevert")
    public String editForRecordRevert(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "messageId", defaultValue = "") Integer messageId,
            @RequestParam(value = "taskStateId", defaultValue = "") Integer taskStateId,
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "recordInspectionUserId", defaultValue = "") Integer recordInspectionUserId
    ) {
        int a = byPlusTaskManager.updateForRecordRevert(messageId, taskStateId, id, username, recordInspectionUserId);
        if (a == 1) {
            return "当前任务已被其他人接收处理!";
        } else {
            return "接收完成!";
        }
    }

    @ApiOperation("任务管理 - 档案部核对完成")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "任务id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "taskStateId", value = "任务状态id(入库完成 = 109)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "projectName", value = "项目名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fileNumber", value = "档案号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "dataNumber", value = "资料编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "来源部门code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "companyName", value = "单位名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "retentionPeriod", value = "保管期限", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "volumeNumber", value = "卷数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageNumber", value = "页数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "scrollPerson", value = "立卷人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fileDate", value = "归档日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "examiner", value = "审核人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "responsiblePerson", value = "责任者", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "rankTypeId", value = "密级id(字典)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query")
    })
    @PostMapping("/editForRecordCheck")
    public Result editForRecordCheck(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "taskStateId", defaultValue = "") Integer taskStateId,
            @RequestParam(value = "projectName", defaultValue = "") String projectName,
            @RequestParam(value = "fileNumber", defaultValue = "") String fileNumber,
            @RequestParam(value = "dataNumber", defaultValue = "") String dataNumber,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode,
            @RequestParam(value = "companyName", defaultValue = "") String companyName,
            @RequestParam(value = "retentionPeriod", defaultValue = "") String retentionPeriod,
            @RequestParam(value = "volumeNumber", defaultValue = "") Integer volumeNumber,
            @RequestParam(value = "pageNumber", defaultValue = "") Integer pageNumber,
            @RequestParam(value = "scrollPerson", defaultValue = "") String scrollPerson,
            @RequestParam(value = "fileDate", defaultValue = "") String fileDate,
            @RequestParam(value = "examiner", defaultValue = "") String examiner,
            @RequestParam(value = "responsiblePerson", defaultValue = "") String responsiblePerson,
            @RequestParam(value = "rankTypeId", defaultValue = "") Integer rankTypeId,
            @RequestParam(value = "remark", defaultValue = "") String remark
    ) {
        ByRRecord byRRecord = new ByRRecord();
        byRRecord.setProjectName(projectName);
        byRRecord.setFileNumber(fileNumber);
        byRRecord.setDataNumber(dataNumber);
        byRRecord.setDepartmentCode(departmentCode);
        byRRecord.setCompanyName(companyName);
        byRRecord.setRetentionPeriod(retentionPeriod);
        byRRecord.setVolumeNumber(volumeNumber);
        byRRecord.setPageNumber(pageNumber);
        byRRecord.setScrollPerson(scrollPerson);
        byRRecord.setFileDate(fileDate);
        byRRecord.setExaminer(examiner);
        byRRecord.setResponsiblePerson(responsiblePerson);
        byRRecord.setRankTypeId(rankTypeId);
        byRRecord.setRemark(remark);
        byRRecord.setIsStoreroom(0);
        byPlusTaskManager.updateForRecordCheck(taskStateId, id, byRRecord);
        return Result.ok();
    }

    @ApiOperation("任务管理 - 档案部提交前审核(返回值data=0为有未提交的,data=1没有未提交的)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "int", paramType = "query")
    })
    @PostMapping("/editForRecordRevertCheck")
    public Result editForRecordRevertCheck(
            @RequestParam(value = "projectId") Integer projectId
    ) {
        int flag = byPlusTaskManager.checkInfo(projectId);
        return Result.ok(flag);
    }

    @ApiOperation("任务管理 - 档案部回退")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "username", value = "退回人员", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "recordReturnReasonId", value = "档案部回退理由id", dataType = "int", paramType = "query")
    })
    @PostMapping("/editForRecordReturn")
    public Result editForRecordReturn(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "recordReturnReasonId", defaultValue = "") Integer recordReturnReasonId
    ) {
        byPlusTaskManager.updateForRecordReturn(username, recordReturnReasonId, id);
        return Result.ok();
    }

    @ApiOperation("任务管理 - 所有回退接收")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "messageId", value = "通知消息id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "taskStateId", value = "任务状态id", dataType = "int", paramType = "query")
    })
    @PostMapping("/editForReturnRevert")
    public Result editForReturnRevert(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "messageId", defaultValue = "") Integer messageId,
            @RequestParam(value = "taskStateId", defaultValue = "") Integer taskStateId
    ) {
        byPlusTaskManager.updateForReturn(taskStateId, messageId, id);
        return Result.ok();
    }

    @ApiOperation("任务管理 - 三检指定工程部打印资料")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @PostMapping("/editForThreePrintManage")
    public Result editForThreePrintManage(
            @RequestParam(value = "id") Integer id
    ) {
        byPlusTaskManager.updateForSanjianPrint(id);
        return Result.ok();
    }

    @ApiOperation("任务管理 - 三检指定综合部接收资料")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @PostMapping("/editForRevertFileManage")
    public Result editForRevertFileManage(
            @RequestParam(value = "id") Integer id
    ) {
        byPlusTaskManager.updateForZonghebuRevert(id);
        return Result.ok();
    }

    @ApiOperation("任务管理 - 工程部指定资料打印人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "messageId", value = "通知消息id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "printUserId", value = "打印人id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "printDate", value = "打印时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "printRemark", value = "打印备注", dataType = "string", paramType = "query")
    })
    @PostMapping("/editForPrintManage")
    public Result editForPrintManage(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "messageId", defaultValue = "") Integer messageId,
            @RequestParam(value = "printUserId", defaultValue = "") Integer printUserId,
            @RequestParam(value = "printDate", defaultValue = "") String printDate,
            @RequestParam(value = "printRemark", defaultValue = "") String printRemark
    ) {
        byPlusTaskManager.updateForPrintManage(printUserId, messageId, printDate, printRemark, id);
        return Result.ok();
    }

    @ApiOperation("任务管理 - 工程部打印完提醒三检资料已打印")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "任务id", dataType = "int", paramType = "query")
    })
    @PostMapping("/editForPrintFinishTip")
    public Result editForPrintFinishTip(
            @RequestParam(value = "id") Integer id
    ) {
        byPlusTaskManager.updateForPrintFinishFlag(id);
        return Result.ok();
    }

    @ApiOperation("任务管理 - 综合部指定资料接收人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "messageId", value = "通知消息id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "dataRevertUserId", value = "资料接收人id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "dataDeliveryDate", value = "资料交付时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "dataRevertRemark", value = "资料接收备注", dataType = "string", paramType = "query")
    })
    @PostMapping("/editForDataDelivery")
    public Result editForDataDelivery(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "messageId", defaultValue = "") Integer messageId,
            @RequestParam(value = "dataRevertUserId", defaultValue = "") Integer dataRevertUserId,
            @RequestParam(value = "dataDeliveryDate", defaultValue = "") String dataDeliveryDate,
            @RequestParam(value = "dataRevertRemark", defaultValue = "") String dataRevertRemark
    ) {
        byPlusTaskManager.updateForDataDelivery(dataRevertUserId, dataDeliveryDate, dataRevertRemark, id, messageId);
        return Result.ok();
    }

    @ApiOperation("任务管理 - 任务启停设置(当前任务暂停或者继续进行)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "stopOperatorId", value = "暂停操作人id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "flag", value = "任务标识(0为进行中,1为暂停)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query")
    })
    @PostMapping("/editForTaskStopOrStart")
    public Result editForTaskStopOrStart(
            @RequestParam(value = "id", defaultValue = "") Integer id,
            @RequestParam(value = "stopOperatorId", defaultValue = "") Integer stopOperatorId,
            @RequestParam(value = "flag", defaultValue = "0") Integer flag,
            @RequestParam(value = "remark", defaultValue = "") String remark
    ) {
        byPlusTaskManager.updateForTaskFlag(flag, stopOperatorId, remark, id);
        return Result.ok();
    }

    @ApiOperation("任务管理 - 综合部接收(下达给其他部门协作研发部的)任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "comprehensiveRevertFlag", value = "综合部接收标识(0为没有接收,1为已接收)", dataType = "int", paramType = "query")
    })
    @PostMapping("/editForComprehensiveRevert")
    public Result editForComprehensiveRevert(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "comprehensiveRevertFlag", defaultValue = "") Integer comprehensiveRevertFlag
    ) {
        byPlusTaskManager.updateForComprehensiveTaskRevert(id, comprehensiveRevertFlag);
        return Result.ok();
    }

    @ApiOperation("任务管理 - 外协下达(确定任务下达给外协部门)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "assistId", value = "外协记录id", dataType = "int", paramType = "query")
    })
    @PostMapping("/editForTaskReleaseAssistDepartment")
    public Result editForTaskReleaseAssistDepartment(
            @RequestParam(value = "assistId", defaultValue = "") Integer assistId
    ) {
        byPlusTaskManager.updateForTaskReleaseAssistDepartment(assistId);
        return Result.ok();
    }

    @ApiOperation("任务管理 - 外协部门接收")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "部门code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "messageId", value = "通知消息id", dataType = "int", paramType = "query")
    })
    @PostMapping("/editForAssistRevert")
    public Result editForAssistRevert(
            @RequestParam(value = "taskId", defaultValue = "") Integer taskId,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode,
            @RequestParam(value = "messageId", defaultValue = "") Integer messageId
    ) {
        byPlusTaskManager.updateForTaskAssistRevert(taskId, departmentCode, messageId);
        return Result.ok();
    }

    @ApiOperation("任务管理 - 外协部门下达(下达任务给自己部门下的小组)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "assistId", value = "外协记录id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "assistGroupCode", value = "外协小组code", dataType = "string", paramType = "query")
    })
    @PostMapping("/editForAssistDepartmentRelease")
    public Result editForAssistDepartmentRelease(
            @RequestParam(value = "assistId", defaultValue = "") Integer assistId,
            @RequestParam(value = "assistGroupCode", defaultValue = "") String assistGroupCode
    ) {
        byPlusTaskManager.updateForTaskAssistReleaseGroup(assistId, assistGroupCode);
        return Result.ok();
    }

    @ApiOperation("任务管理 - 外协小组接收")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "groupCode", value = "外协小组code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "messageId", value = "通知消息id", dataType = "int", paramType = "query")
    })
    @PostMapping("/editForAssistGroupRevert")
    public Result editForAssistGroupRevert(
            @RequestParam(value = "taskId", defaultValue = "") Integer taskId,
            @RequestParam(value = "groupCode", defaultValue = "") String groupCode,
            @RequestParam(value = "messageId", defaultValue = "") Integer messageId
    ) {
        byPlusTaskManager.updateForTaskAssistGroupRevert(taskId, groupCode, messageId);
        return Result.ok();
    }

    @ApiOperation("任务管理 - 外协小组完成(提交到一检)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "assistId", value = "外协记录id", dataType = "int", paramType = "query")
    })
    @PostMapping("/editForAssistGroupFinish")
    public String editForAssistGroupFinish(
            @RequestParam(value = "assistId", defaultValue = "") Integer assistId
    ) {
        int a = byPlusTaskManager.updateForTaskAssistGroupFinish(assistId);
        if (a == 0) {
            return "小组已完成!";
        } else {
            return "小组未提交工作量,不能提交!";
        }
    }

    @ApiOperation("任务管理 - 外协一检接收任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "部门code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "messageId", value = "通知消息id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "oneInspectionUserId", value = "外协一检人员id", dataType = "int", paramType = "query")
    })
    @PostMapping("/editForAssistOneInspectionRevert")
    public Result editForAssistOneInspectionRevert(
            @RequestParam(value = "taskId") Integer taskId,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode,
            @RequestParam(value = "messageId", defaultValue = "") Integer messageId,
            @RequestParam(value = "oneInspectionUserId", defaultValue = "") Integer oneInspectionUserId
    ) {
        byPlusTaskManager.updateForAssistOneInspectionRevert(departmentCode, messageId, taskId, oneInspectionUserId);
        return Result.ok();
    }

    @ApiOperation("任务管理 - 外协一检完成任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "assistId", value = "外协记录id", dataType = "int", paramType = "query")
    })
    @PostMapping("/editForAssistOneInspectionFinish")
    public Result editForAssistOneInspectionFinish(
            @RequestParam(value = "assistId", defaultValue = "") Integer assistId
    ) {
        byPlusTaskManager.updateForAssistOneInspectionFinish(assistId);
        return Result.ok();
    }

    @ApiOperation("任务管理 - 小组接收外协一检提交的资料")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "oneInspectionUserId", value = "外协一检人员id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "messageId", value = "通知消息id", dataType = "int", paramType = "query")
    })
    @PostMapping("/editForGroupRevertAssistOneInspection")
    public Result editForGroupRevertAssistOneInspection(
            @RequestParam(value = "taskId", defaultValue = "") Integer taskId,
            @RequestParam(value = "oneInspectionUserId", defaultValue = "") Integer oneInspectionUserId,
            @RequestParam(value = "messageId", defaultValue = "") Integer messageId
    ) {
        byPlusTaskManager.updateForGroupRevertAssistOneInspection(taskId, oneInspectionUserId, messageId);
        return Result.ok();
    }

    @ApiOperation("任务管理 - 小组确认外协一检提交的资料")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "assistId", value = "外协记录id", dataType = "int", paramType = "query")
    })
    @PostMapping("/editForGroupConfirmAssistOneInspection")
    public Result editForGroupConfirmAssistOneInspection(
            @RequestParam(value = "assistId", defaultValue = "") Integer assistId
    ) {
        byPlusTaskManager.updateForGroupConfirmAssistOneInspection(assistId);
        return Result.ok();
    }

    @ApiOperation("任务管理 - 小组回退(外协部门)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "assistId", value = "外协记录id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "returnReasonIds", value = "回退原因(可多个原因,存原因id,中间\",\"隔开)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query")
    })
    @PostMapping("/editForAssistReturn")
    public Result editForAssistReturn(
            @RequestParam(value = "assistId", defaultValue = "") Integer assistId,
            @RequestParam(value = "returnReasonIds", defaultValue = "") String returnReasonIds,
            @RequestParam(value = "remark", defaultValue = "") String remark
    ) {
        byPlusTaskManager.updateForGroupReturn(assistId, returnReasonIds, remark);
        return Result.ok();
    }

    @ApiOperation("任务管理 - 小组撤回(部门负责人权限)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "任务id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "groupReturnRemark", value = "小组撤回备注", dataType = "string", paramType = "query")
    })
    @PostMapping("/editForGroupRevoke")
    public Result editForGroupRevoke(
            @RequestParam(value = "id", defaultValue = "") Integer id,
            @RequestParam(value = "groupReturnRemark", defaultValue = "") String groupReturnRemark
    ) {
        byPlusTaskManager.updateForGroupRevoke(id, groupReturnRemark);
        return Result.ok();
    }

    @ApiOperation("任务管理 - 查询备注")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "任务id", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipListForRemark")
    public Map<String, Object> flipListForRemark(
            @RequestParam(value = "id", defaultValue = "") Integer id
    ) {
        Map<String, Object> map = byPlusTaskManager.searchForRemark(id);
        return map;
    }

    //lxl-20190528
//    @ApiOperation("统计管理 - 查询备注")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "任务id", dataType = "int", paramType = "query")
//    })
//    @PostMapping("/flipListForRemark")
//    public Map<String, Object> flipListForRemark(
//            @RequestParam(value = "id", defaultValue = "") Integer id
//    ) {
//        Map<String, Object> map = byPlusTaskManager.searchForRemark(id);
//        return map;
//    }

}
