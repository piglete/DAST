package club.emergency.controller;

import club.emergency.project.manager.ByPlusDocumentManager;
import club.emergency.project.model.ByPlusDocument;
import club.map.core.web.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhaojinxiong
 * Date: 2018-12-29
 * Time: 11:08
 * Description:
 */
@Api("客户资料管理")
@CrossOrigin
@RestController
@RequestMapping("/plus-document")
public class PlusDocumentController {

    private ByPlusDocumentManager byPlusDocumentManager;

    public PlusDocumentController(ByPlusDocumentManager byPlusDocumentManager) {
        this.byPlusDocumentManager = byPlusDocumentManager;
    }

    @ApiOperation("查询客户资料信息(无分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipList")
    public Result flipList(
            @RequestParam(value = "projectId", defaultValue = "") Integer projectId
    ) {
        List<ByPlusDocument> byPlusDocumentList = byPlusDocumentManager.search(projectId);
        return Result.ok(byPlusDocumentList);
    }

    @ApiOperation("客户资料管理 - 编辑客户资料信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "title", value = "标题", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fileName", value = "文件名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fileRename", value = "文件重命名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fileSize", value = "文件大小", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "departmentCode", value = "上传部门code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "uploadUser", value = "上传用户", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fileUrl", value = "文件路径", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fileType", value = "文件类型", dataType = "string", paramType = "query")
    })
    @PostMapping("/edit")
    public Result edit(
            @RequestParam(value = "id", defaultValue = "") Integer id,
            @RequestParam(value = "projectId", defaultValue = "") Integer projectId,
            @RequestParam(value = "title", defaultValue = "") String title,
            @RequestParam(value = "fileName", defaultValue = "") String fileName,
            @RequestParam(value = "fileRename", defaultValue = "") String fileRename,
            @RequestParam(value = "fileSize", defaultValue = "") String fileSize,
            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode,
            @RequestParam(value = "uploadUser", defaultValue = "") String uploadUser,
            @RequestParam(value = "fileUrl", defaultValue = "") String fileUrl,
            @RequestParam(value = "remark", defaultValue = "") String remark,
            @RequestParam(value = "fileType", defaultValue = "") String fileType,
            MultipartFile file
    ) {
        ByPlusDocument byPlusDocument = new ByPlusDocument();
        if (id != null) {
            byPlusDocument.setId(id);
        }
        byPlusDocument.setProjectId(projectId);
        byPlusDocument.setTitle(title);
        byPlusDocument.setFileName(fileName);
        byPlusDocument.setFileRename(fileRename);
        byPlusDocument.setFileSize(fileSize);
        byPlusDocument.setDepartmentCode(departmentCode);
        byPlusDocument.setUploadUser(uploadUser);
        byPlusDocument.setFileUrl(fileUrl);
        byPlusDocument.setRemark(remark);
        byPlusDocument.setFileType(fileType);
        byPlusDocumentManager.upperSave(byPlusDocument, file);
        return Result.ok();
    }

    @ApiOperation("客户资料信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @PostMapping("/detail")
    public Result detail(
            @RequestParam(value = "id", defaultValue = "") Integer id
    ) {
        ByPlusDocument byPlusDocument = byPlusDocumentManager.searchById(id);
        return Result.ok(byPlusDocument);
    }

    @ApiOperation("客户资料管理 - 删除客户资料信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", dataType = "string", paramType = "query")
    })
    @PostMapping("/remove")
    public Result remove(
            @RequestParam(value = "ids") String ids
    ) {
        byPlusDocumentManager.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("客户资料管理 - 下载客户资料信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @PostMapping("/download")
    public Result download(
            @RequestParam(value = "id") Integer id,
            HttpServletResponse response
    ) {
        boolean flag = byPlusDocumentManager.downloadFile(id, response);
        if (flag == true) {
            return Result.ok();
        } else {
            return Result.error("文件下载失败!");
        }
    }
}
