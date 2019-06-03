package club.emergency.controller;

import club.emergency.by_s_user_information.manager.BySUserInformationManager;
import club.emergency.by_s_user_information.model.BySUserInformation;
import club.map.core.model.Page;
import club.map.core.web.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhaojinxiong
 * Date: 2018-11-19
 * Time: 10:41
 * Description:
 */
@Api("用户管理")
@CrossOrigin
@RestController
@RequestMapping("/user-information")
public class UserInformationController {

    private BySUserInformationManager bySUserInformationManager;

    public UserInformationController(BySUserInformationManager bySUserInformationManager) {
        this.bySUserInformationManager = bySUserInformationManager;
    }

    @ApiOperation("查询用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "loginName", value = "用户账号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "员工部门code", dataType = "string", paramType = "query")
    })
    @PostMapping("/flipList")
    public Result flipList(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "loginName", defaultValue = "") String loginName,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode
    ) {
        Page page = bySUserInformationManager.search(loginName, departmentCode, pageNo, pageSize);
        return Result.ok(page);
    }

    @ApiOperation("用户管理 - 编辑用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "loginName", value = "用户账号", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "用户密码", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "employeeId", value = "员工编号", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "roleId", value = "角色编号", required = true, dataType = "int", paramType = "query")
    })
    @PostMapping("/edit")
    public String edit(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "loginName", defaultValue = "") String loginName,
            @RequestParam(value = "password", defaultValue = "123456") String password,
            @RequestParam(value = "employeeId", defaultValue = "") Integer employeeId,
            @RequestParam(value = "roleId", defaultValue = "") Integer roleId
    ) {
        BySUserInformation bySUserInformation = new BySUserInformation();
        if (id != null) {
            bySUserInformation.setId(id);
        }
        bySUserInformation.setLoginName(loginName);
        bySUserInformation.setPassword(password);
        bySUserInformation.setEmployeeId(employeeId);
        bySUserInformation.setRoleId(roleId);
        int a = bySUserInformationManager.upperSave(bySUserInformation);
        if (a == 1) {
            return "该用户名已被使用,请更换用户名!";
        }
        return "success";
    }

    @ApiOperation("用户信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @PostMapping("/detail")
    public Result detail(
            @RequestParam(value = "id", defaultValue = "") Integer id
    ) {
        BySUserInformation bySUserInformation = bySUserInformationManager.searchDetails(id);
        return Result.ok(bySUserInformation);
    }

    @ApiOperation("用户管理 - 删除用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", dataType = "string", paramType = "query")
    })
    @PostMapping("/remove")
    public Result remove(
            @RequestParam(value = "ids") String ids
    ) {
        bySUserInformationManager.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("前台用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginName", value = "登录账号", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "登录密码", required = true, dataType = "string", paramType = "query")
    })
    @PostMapping("/loginForFront")
    public Result loginForFront(
            @RequestParam(value = "loginName") String loginName,
            @RequestParam(value = "password") String password
    ) {
        BySUserInformation bySUserInformation = bySUserInformationManager.login(loginName, password);
        if (bySUserInformation != null) {
            Integer roleType = bySUserInformation.getRoleType();
            if (roleType != 1) {
                return Result.error("该账号不存在!");
            } else {
                return Result.ok(bySUserInformation);
            }
        } else {
            return Result.error("用户名或密码错误!");
        }
    }

    @ApiOperation("后台用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginName", value = "登录账号", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "登录密码", required = true, dataType = "string", paramType = "query")
    })
    @PostMapping("/loginForBack")
    public Result loginForBack(
            @RequestParam(value = "loginName") String loginName,
            @RequestParam(value = "password") String password
    ) {
        BySUserInformation bySUserInformation = bySUserInformationManager.login(loginName, password);
        if (bySUserInformation != null) {
            Integer roleType = bySUserInformation.getRoleType();
            if (roleType != 2) {
                return Result.error("该账号不存在!");
            } else {
                return Result.ok(bySUserInformation);
            }
        } else {
            return Result.error("用户名密码错误!");
        }
    }

    @ApiOperation("用户管理 - 修改用户角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "roleId", value = "角色编号", required = true, dataType = "int", paramType = "query"),
    })
    @PostMapping("/editForRole")
    public Result editForRole(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "roleId") Integer roleId
    ) {
        Boolean result = bySUserInformationManager.updateForRole(id, roleId);
        if (result) {
            return Result.ok();
        } else {
            return Result.error("修改失败,请检查是否选择了角色!");
        }
    }

    @ApiOperation("用户管理 - 修改密码")
    @PostMapping("/editForPassword")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginName", value = "登录名", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "oldPassword", value = "旧密码", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "newPassword", value = "新密码", required = true, dataType = "string", paramType = "query")
    })
    public Result editForPassword(
            @RequestParam(value = "loginName") String loginName,
            @RequestParam(value = "oldPassword") String oldPassword,
            @RequestParam(value = "newPassword") String newPassword
    ) {
        Boolean result = bySUserInformationManager.updatePassword(loginName, oldPassword, newPassword);
        if (result) {
            return Result.ok();
        } else {
            return Result.error("用户名密码错误,修改密码失败");
        }
    }


}
