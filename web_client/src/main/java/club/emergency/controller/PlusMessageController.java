package club.emergency.controller;

import club.emergency.project.manager.ByPlusMessageManager;
import club.emergency.project.model.ByPlusMessage;
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
 * Date: 2019-01-18
 * Time: 16:58
 * Description:
 */
@Api("通知消息管理")
@CrossOrigin
@RestController
@RequestMapping("/plus-message")
public class PlusMessageController {

    private ByPlusMessageManager byPlusMessageManager;

    public PlusMessageController(ByPlusMessageManager byPlusMessageManager) {
        this.byPlusMessageManager = byPlusMessageManager;
    }

    @ApiOperation("查询通知消息(无分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "messageUserId", value = "通知人id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "messageRoleId", value = "通知角色id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "taskStateId", value = "通知任务状态", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "部门code(消息推送部门)", dataType = "string", paramType = "query")
    })
    @PostMapping("/flipList")
    public Result flipList(
            @RequestParam(value = "projectId", defaultValue = "") Integer projectId,
            @RequestParam(value = "messageUserId", defaultValue = "") Integer messageUserId,
            @RequestParam(value = "messageRoleId", defaultValue = "") Integer messageRoleId,
            @RequestParam(value = "taskStateId", defaultValue = "") Integer taskStateId,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode
    ) {
        List<ByPlusMessage> byPlusMessageList = byPlusMessageManager.search(projectId, messageUserId, messageRoleId, taskStateId, departmentCode);
        return Result.ok(byPlusMessageList);
    }

    @ApiOperation("通知消息管理 - 编辑通知消息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "taskId", value = "任务id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "message", value = "通知内容", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "messageUserId", value = "通知人id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "messageRoleId", value = "通知角色id(主要是对应二检,三检,档案部,外查)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "taskStateId", value = "通知任务状态", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "messageCreateId", value = "消息创建用户", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "messageType", value = "消息类型(0为需要修改任务状态提的示消息,1为处理消息,2为外查提示消息,3为打印提示消息,4为交付提示消息,5为外协部门提示消息,6为外协小组提示消息,7为外协小组提交一检提示消息,8为外协一检完成提示消息,9外协小组退回提示消息)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "部门code(消息推送部门)", dataType = "string", paramType = "query")
    })
    @PostMapping("/edit")
    public Result edit(
            @RequestParam(value = "id", defaultValue = "") Integer id,
            @RequestParam(value = "projectId", defaultValue = "") Integer projectId,
            @RequestParam(value = "taskId", defaultValue = "") Integer taskId,
            @RequestParam(value = "message", defaultValue = "") String message,
            @RequestParam(value = "messageUserId", defaultValue = "") Integer messageUserId,
            @RequestParam(value = "messageRoleId", defaultValue = "") Integer messageRoleId,
            @RequestParam(value = "taskStateId", defaultValue = "") Integer taskStateId,
            @RequestParam(value = "messageCreateId", defaultValue = "") Integer messageCreateId,
            @RequestParam(value = "messageType", defaultValue = "0") Integer messageType,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode
    ) {
        ByPlusMessage byPlusMessage = new ByPlusMessage();
        if (id != null) {
            byPlusMessage.setId(id);
        }
        byPlusMessage.setProjectId(projectId);
        byPlusMessage.setTaskId(taskId);
        byPlusMessage.setMessage(message);
        byPlusMessage.setMessageUserId(messageUserId);
        byPlusMessage.setMessageRoleId(messageRoleId);
        byPlusMessage.setTaskStateId(taskStateId);
        byPlusMessage.setMessageCreateId(messageCreateId);
        byPlusMessage.setMessageType(messageType);
        byPlusMessage.setDepartmentCode(departmentCode);
        byPlusMessageManager.upperSave(byPlusMessage);
        return Result.ok();
    }

    @ApiOperation("通知消息管理详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @PostMapping("/detail")
    public Result detail(
            @RequestParam(value = "id", defaultValue = "") Integer id
    ) {
        ByPlusMessage byPlusMessage = byPlusMessageManager.get(id);
        return Result.ok(byPlusMessage);
    }

    @ApiOperation("通知消息管理 - 删除通知消息(多个)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", dataType = "string", paramType = "query")
    })
    @PostMapping("/remove")
    public Result remove(
            @RequestParam(value = "ids", defaultValue = "") String ids
    ) {
        byPlusMessageManager.removeByIds(ids);
        return Result.ok();
    }

    /**
     * 办公室收到档案部入档的消息后,执行该方法,删除该任务下所有的通知消息
     *
     * @param id
     * @return
     */
    @ApiOperation("通知消息管理 - 删除该任务下所有通知消息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "任务id", dataType = "int", paramType = "query")
    })
    @PostMapping("/removeByTaskId")
    public Result removeByTaskId(
            @RequestParam(value = "id") Integer id
    ) {
        byPlusMessageManager.removeByTaskId(id);
        return Result.ok();
    }
}
