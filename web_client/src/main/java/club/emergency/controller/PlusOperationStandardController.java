package club.emergency.controller;

import club.emergency.project.manager.ByPlusOperationStandardManager;
import club.emergency.project.model.ByPlusOperationStandard;
import club.map.core.model.Page;
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
 * Date: 2019-01-03
 * Time: 16:43
 * Description:
 */
@Api("作业指导文件管理")
@CrossOrigin
@RestController
@RequestMapping("/plus-operation-standard")
public class PlusOperationStandardController {

    private ByPlusOperationStandardManager byPlusOperationStandardManager;

    public PlusOperationStandardController(ByPlusOperationStandardManager byPlusOperationStandardManager) {
        this.byPlusOperationStandardManager = byPlusOperationStandardManager;
    }

    @ApiOperation("查询作业指导文件信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "title", value = "标题", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fileName", value = "文件名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fileClassifyId", value = "文件分类id", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipList")
    public Result flipList(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "title", defaultValue = "") String title,
            @RequestParam(value = "fileName", defaultValue = "") String fileName,
            @RequestParam(value = "fileClassifyId", defaultValue = "") Integer fileClassifyId
    ) {
        Page page = byPlusOperationStandardManager.search(title, fileName, fileClassifyId, pageNo, pageSize);
        return Result.ok(page);
    }

    @ApiOperation("查询作业指导文件信息(没有分页的)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "标题", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fileName", value = "文件名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fileClassifyId", value = "文件分类id", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipListWithoutPage")
    public Result flipListWithoutPage(
            @RequestParam(value = "title", defaultValue = "") String title,
            @RequestParam(value = "fileName", defaultValue = "") String fileName,
            @RequestParam(value = "fileClassifyId", defaultValue = "") Integer fileClassifyId
    ) {
        List<ByPlusOperationStandard> byPlusOperationStandardList = byPlusOperationStandardManager.searchWithoutPage(title, fileName, fileClassifyId);
        return Result.ok(byPlusOperationStandardList);
    }

    @ApiOperation("作业指导文件管理 - 编辑作业指导文件信息(包括文件上传)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "title", value = "标题", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "uploadUser", value = "上传用户", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fileName", value = "原文件名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fileRename", value = "文件重命名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fileSize", value = "文件大小", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fileUrl", value = "文件路径", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fileType", value = "文件类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fileClassifyId", value = "文件分类id", dataType = "int", paramType = "query")
    })
    @PostMapping("/edit")
    public Result edit(
            @RequestParam(value = "id", defaultValue = "") Integer id,
            @RequestParam(value = "title", defaultValue = "") String title,
            @RequestParam(value = "uploadUser", defaultValue = "") String uploadUser,
            @RequestParam(value = "fileName", defaultValue = "") String fileName,
            @RequestParam(value = "fileRename", defaultValue = "") String fileRename,
            @RequestParam(value = "fileSize", defaultValue = "") String fileSize,
            @RequestParam(value = "fileUrl", defaultValue = "") String fileUrl,
            @RequestParam(value = "remark", defaultValue = "") String remark,
            @RequestParam(value = "fileType", defaultValue = "") String fileType,
            @RequestParam(value = "fileClassifyId", defaultValue = "") Integer fileClassifyId,
            MultipartFile file
    ) {
        ByPlusOperationStandard byPlusOperationStandard = new ByPlusOperationStandard();
        if (id != null) {
            byPlusOperationStandard.setId(id);
        }
        byPlusOperationStandard.setTitle(title);
        byPlusOperationStandard.setUploadUser(uploadUser);
        byPlusOperationStandard.setFileName(fileName);
        byPlusOperationStandard.setFileRename(fileRename);
        byPlusOperationStandard.setFileSize(fileSize);
        byPlusOperationStandard.setFileUrl(fileUrl);
        byPlusOperationStandard.setRemark(remark);
        byPlusOperationStandard.setFileType(fileType);
        byPlusOperationStandard.setFileClassifyId(fileClassifyId);
        byPlusOperationStandardManager.upperSave(byPlusOperationStandard, file);
        return Result.ok();
    }

    @ApiOperation("作业指导文件信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @PostMapping("/detail")
    public Result detail(
            @RequestParam(value = "id", defaultValue = "") Integer id
    ) {
        ByPlusOperationStandard byPlusOperationStandard = byPlusOperationStandardManager.searchInfo(id);
        return Result.ok(byPlusOperationStandard);
    }

    @ApiOperation("作业指导文件管理 - 删除作业指导文件信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", dataType = "string", paramType = "query")
    })
    @PostMapping("/remove")
    public Result remove(
            @RequestParam(value = "ids") String ids
    ) {
        byPlusOperationStandardManager.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("作业指导文件管理 - 下载作业指导文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @PostMapping("/download")
    public Result download(
            @RequestParam(value = "id") Integer id,
            HttpServletResponse response
    ) {
        boolean flag = byPlusOperationStandardManager.downloadFile(id, response);
        if (flag == true) {
            return Result.ok();
        } else {
            return Result.error("文件下载失败!");
        }
    }

    @ApiOperation("查询列表中最新的文件")
    @PostMapping("/flipListForNewest")
    public Result flipListForNewest() {
        ByPlusOperationStandard byPlusOperationStandard = byPlusOperationStandardManager.searchNewest();
        return Result.ok(byPlusOperationStandard);
    }
}
