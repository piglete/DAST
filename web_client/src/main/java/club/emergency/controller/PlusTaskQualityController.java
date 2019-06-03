package club.emergency.controller;

import club.emergency.project.manager.ByPlusTaskQualityManager;
import club.emergency.project.model.ByPlusTaskQuality;
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
 * Date: 2018-12-29
 * Time: 15:54
 * Description:
 */
@Api("任务质量评定管理")
@CrossOrigin
@RestController
@RequestMapping("/plus-task-quality")
public class PlusTaskQualityController {

    private ByPlusTaskQualityManager byPlusTaskQualityManager;

    public PlusTaskQualityController(ByPlusTaskQualityManager byPlusTaskQualityManager) {
        this.byPlusTaskQualityManager = byPlusTaskQualityManager;
    }

    @ApiOperation("任务质量评定信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "checkDepartment", value = "质检部门(2为二检,3为三检)", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipList")
    public Result flipList(
            @RequestParam(value = "taskId", defaultValue = "") Integer taskId,
            @RequestParam(value = "checkDepartment", defaultValue = "") Integer checkDepartment
    ) {
        List<ByPlusTaskQuality> byPlusTaskQualityList = byPlusTaskQualityManager.search(taskId, checkDepartment);
        return Result.ok(byPlusTaskQualityList);
    }

    @ApiOperation("任务质量评定管理 - 编辑任务质量评定信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "taskId", value = "任务id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "dataOneError", value = "数据及结构正确性A类错误数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "dataTwoError", value = "数据及结构正确性B类错误数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "dataThreeError", value = "数据及结构正确性C类错误数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "dataFourError", value = "数据及结构正确性D类错误数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "geographicOneError", value = "地理精度A类错误数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "geographicTwoError", value = "地理精度B类错误数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "geographicThreeError", value = "地理精度C类错误数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "geographicFourError", value = "地理精度D类错误数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "groomOneError", value = "整饰质量A类错误数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "groomTwoError", value = "整饰质量B类错误数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "groomThreeError", value = "整饰质量C类错误数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "groomFourError", value = "整饰质量D类错误数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "attachmentOneError", value = "附件质量A类错误数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "attachmentTwoError", value = "附件质量B类错误数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "attachmentThreeError", value = "附件质量C类错误数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "attachmentFourError", value = "附件质量D类错误数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "assessUser", value = "评定人员", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "assessDate", value = "评定日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "checkDepartment", value = "质检部门(2为二检,3为三检)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "score", value = "得分", dataType = "double", paramType = "query")
    })
    @PostMapping("/edit")
    public String edit(
            @RequestParam(value = "id", defaultValue = "") Integer id,
            @RequestParam(value = "taskId", defaultValue = "") Integer taskId,
            @RequestParam(value = "dataOneError", defaultValue = "0") Integer dataOneError,
            @RequestParam(value = "dataTwoError", defaultValue = "0") Integer dataTwoError,
            @RequestParam(value = "dataThreeError", defaultValue = "0") Integer dataThreeError,
            @RequestParam(value = "dataFourError", defaultValue = "0") Integer dataFourError,
            @RequestParam(value = "geographicOneError", defaultValue = "0") Integer geographicOneError,
            @RequestParam(value = "geographicTwoError", defaultValue = "0") Integer geographicTwoError,
            @RequestParam(value = "geographicThreeError", defaultValue = "0") Integer geographicThreeError,
            @RequestParam(value = "geographicFourError", defaultValue = "0") Integer geographicFourError,
            @RequestParam(value = "groomOneError", defaultValue = "0") Integer groomOneError,
            @RequestParam(value = "groomTwoError", defaultValue = "0") Integer groomTwoError,
            @RequestParam(value = "groomThreeError", defaultValue = "0") Integer groomThreeError,
            @RequestParam(value = "groomFourError", defaultValue = "0") Integer groomFourError,
            @RequestParam(value = "attachmentOneError", defaultValue = "0") Integer attachmentOneError,
            @RequestParam(value = "attachmentTwoError", defaultValue = "0") Integer attachmentTwoError,
            @RequestParam(value = "attachmentThreeError", defaultValue = "0") Integer attachmentThreeError,
            @RequestParam(value = "attachmentFourError", defaultValue = "0") Integer attachmentFourError,
            @RequestParam(value = "assessUser", defaultValue = "") String assessUser,
            @RequestParam(value = "assessDate", defaultValue = "") String assessDate,
            @RequestParam(value = "checkDepartment", defaultValue = "") Integer checkDepartment,
            @RequestParam(value = "score", defaultValue = "100") Double score
    ) {
        ByPlusTaskQuality byPlusTaskQuality = new ByPlusTaskQuality();
        if (id != null) {
            byPlusTaskQuality.setId(id);
        }
        byPlusTaskQuality.setTaskId(taskId);
        byPlusTaskQuality.setDataOneError(dataOneError);
        byPlusTaskQuality.setDataTwoError(dataTwoError);
        byPlusTaskQuality.setDataThreeError(dataThreeError);
        byPlusTaskQuality.setDataFourError(dataFourError);
        byPlusTaskQuality.setGeographicOneError(geographicOneError);
        byPlusTaskQuality.setGeographicTwoError(geographicTwoError);
        byPlusTaskQuality.setGeographicThreeError(geographicThreeError);
        byPlusTaskQuality.setGeographicFourError(geographicFourError);
        byPlusTaskQuality.setGroomOneError(groomOneError);
        byPlusTaskQuality.setGroomTwoError(groomTwoError);
        byPlusTaskQuality.setGroomThreeError(groomThreeError);
        byPlusTaskQuality.setGroomFourError(groomFourError);
        byPlusTaskQuality.setAttachmentOneError(attachmentOneError);
        byPlusTaskQuality.setAttachmentTwoError(attachmentTwoError);
        byPlusTaskQuality.setAttachmentThreeError(attachmentThreeError);
        byPlusTaskQuality.setAttachmentFourError(attachmentFourError);
        byPlusTaskQuality.setAssessUser(assessUser);
        byPlusTaskQuality.setAssessDate(assessDate);
        byPlusTaskQuality.setCheckDepartment(checkDepartment);
        byPlusTaskQuality.setScore(score);
        String finaScore = byPlusTaskQualityManager.upperSave(byPlusTaskQuality);
        return finaScore;
    }

    @ApiOperation("任务质量评定信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "checkDepartment", value = "质检部门(2为二检,3为三检)", dataType = "int", paramType = "query")
    })
    @PostMapping("/detail")
    public Result detail(
            @RequestParam(value = "taskId", defaultValue = "") Integer taskId,
            @RequestParam(value = "checkDepartment", defaultValue = "") Integer checkDepartment
    ) {
        ByPlusTaskQuality byPlusTaskQuality = byPlusTaskQualityManager.searchInfo(taskId, checkDepartment);
        if (byPlusTaskQuality == null) {
            return Result.ok("系统数据异常,请联系管理员!");
        } else {
            return Result.ok(byPlusTaskQuality);
        }
    }

    @ApiOperation("任务质量评定管理 - 删除任务质量评定信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", dataType = "string", paramType = "query")
    })
    @PostMapping("/remove")
    public Result remove(
            @RequestParam(value = "ids") String ids
    ) {
        byPlusTaskQualityManager.removeByIds(ids);
        return Result.ok();
    }
}
