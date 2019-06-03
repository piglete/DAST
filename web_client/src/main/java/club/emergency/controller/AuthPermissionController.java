package club.emergency.controller;

import club.emergency.auth_permission.manager.BySMenuManager;
import club.emergency.auth_permission.manager.BySRoleManager;
import club.emergency.auth_permission.model.BySMenu;
import club.emergency.auth_permission.model.BySRole;
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
 * Date: 2018-11-13
 * Time: 16:17
 * Description:
 */
@Api("权限管理")
@CrossOrigin
@RestController
@RequestMapping("/permission")
public class AuthPermissionController {

    private BySMenuManager bySMenuManager;
    private BySRoleManager bySRoleManager;

    public AuthPermissionController(BySMenuManager bySMenuManager,
                                    BySRoleManager bySRoleManager) {
        this.bySMenuManager = bySMenuManager;
        this.bySRoleManager = bySRoleManager;
    }

    @ApiOperation("查询角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "roleName", value = "角色名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "roleType", value = "角色类型(1为业务处理系统,2为管理系统)", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipList")
    public Result flipList(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "roleName", defaultValue = "") String roleName,
            @RequestParam(value = "roleType", defaultValue = "") Integer roleType
    ) {
        Page page = bySRoleManager.search(roleName, roleType, pageNo, pageSize);
        return Result.ok(page);
    }

    @ApiOperation("查询角色信息 - 不分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleName", value = "角色名称", dataType = "string", paramType = "query"),
    })
    @PostMapping("/flipListForRoleWithOutPage")
    public Result flipListForRoleWithOutPage(
            @RequestParam(value = "roleName", required = false, defaultValue = "") String roleName
    ) {
        List<BySRole> authRoleList = bySRoleManager.searchWithOutPage(roleName);
        return Result.ok(authRoleList);
    }

    @ApiOperation("角色管理 - 编辑角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "roleName", value = "角色名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "角色描述", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "roleType", value = "角色类型(1为业务处理系统,2为管理系统)", dataType = "int", paramType = "query")
    })
    @PostMapping("/editForRole")
    public Result editForRole(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "roleName", defaultValue = "") String roleName,
            @RequestParam(value = "remark", defaultValue = "") String remark,
            @RequestParam(value = "roleType", defaultValue = "") Integer roleType
    ) {
        BySRole bySRole = new BySRole();
        if (id != null) {
            bySRole.setId(id);
        }
        bySRole.setRoleName(roleName);
        bySRole.setRemark(remark);
        bySRole.setRoleType(roleType);
        bySRoleManager.upperSave(bySRole);
        return Result.ok();
    }

    @ApiOperation("角色管理 - 编辑角色对应菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "menuIds", value = "菜单ids", dataType = "string", paramType = "query")
    })
    @PostMapping("/editForRoleMenu")
    public Result editForRoleMenu(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "menuIds", defaultValue = "") String menuIds
    ) {
        bySRoleManager.upperSaveMenu(id, menuIds);
        return Result.ok();
    }

    @ApiOperation("查询角色详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
    })
    @PostMapping("/detailForRole")
    public Result detailForRole(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id
    ) {
        BySRole bySRole = bySRoleManager.get(id);
        return Result.ok(bySRole);
    }

    @ApiOperation("查询角色对应菜单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", dataType = "int", paramType = "query"),
    })
    @PostMapping("/detailForRolMenu")
    public Result detailForRolMenu(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id
    ) {
        List<BySMenu> bySMenuList = bySMenuManager.detailForRoleMenu(id);
        return Result.ok(bySMenuList);
    }

    @ApiOperation("角色管理 - 删除角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @PostMapping("/removeForRole")
    public String removeForRole(
            @RequestParam(value = "id", defaultValue = "") Integer id
    ) {
        Boolean flag = bySRoleManager.removeForRole(id);
        if (flag == true) {
            return "当前角色还有用户使用,暂不能删除;如果必须删除,则必须解绑该角色下的所有用户!";
        } else {
            return "删除成功!";
        }
    }

    @ApiOperation("查询菜单树")
    @PostMapping("/treeData")
    public Result treeData() {
        List list = bySMenuManager.treeData();
        return Result.ok(list);
    }

    @ApiOperation("查询菜单(无分页)")
    @PostMapping("/flipListMenu")
    public Result flipListMenu() {
        List<BySMenu> bySMenuList = bySMenuManager.searchAllMenu();
        return Result.ok(bySMenuList);
    }

    @ApiOperation("查询菜单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "menuName", value = "菜单名称", dataType = "string", paramType = "query")
    })
    @PostMapping("/flipListForPermission")
    public Result flipListForPermission(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "menuName", defaultValue = "") String menuName
    ) {
        Page page = bySMenuManager.search(menuName, pageNo, pageSize);
        return Result.ok(page);
    }

    @ApiOperation("菜单管理 - 编辑菜单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "menuName", value = "菜单名称", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "parentId", value = "上级菜单id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "menuIcon", value = "图标文件地址", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "menuUrl", value = "菜单地址", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sortNum", value = "排序", dataType = "int", paramType = "query")
    })
    @PostMapping("/editForMenu")
    public Result editForMenu(
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "menuName", defaultValue = "") String menuName,
            @RequestParam(value = "parentId", defaultValue = "") Integer parentId,
            @RequestParam(value = "menuIcon", defaultValue = "") String menuIcon,
            @RequestParam(value = "menuUrl", defaultValue = "") String menuUrl,
            @RequestParam(value = "sortNum", defaultValue = "") Integer sortNum
    ) {
        BySMenu bySMenu = new BySMenu();
        if (id != null) {
            bySMenu.setId(id);
        }
        bySMenu.setMenuName(menuName);
        bySMenu.setParentId(parentId);
        bySMenu.setMenuIcon(menuIcon);
        bySMenu.setMenuUrl(menuUrl);
        bySMenu.setSortNum(sortNum);
        bySMenuManager.upperSave(bySMenu);
        return Result.ok();
    }

    @ApiOperation("查询菜单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
    })
    @PostMapping("/detailForMenu")
    public Result detailForMenu(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id
    ) {
        BySMenu bySMenu = bySMenuManager.get(id);
        return Result.ok(bySMenu);
    }

    @ApiOperation("菜单管理 - 删除菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", required = true, dataType = "string", paramType = "query"),
    })
    @PostMapping("/removeForMenuById")
    public Result removeForMenuById(
            @RequestParam("ids") String ids
    ) {
        bySMenuManager.removeByIds(ids);
        return Result.ok();
    }

}

