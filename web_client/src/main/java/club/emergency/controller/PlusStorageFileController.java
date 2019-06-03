package club.emergency.controller;

import club.emergency.project.manager.ByPlusStorageFileManager;
import club.emergency.project.model.ByPlusStorageFile;
import club.map.core.model.Page;
import club.map.core.web.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhaojinxiong
 * Date: 2018-12-29
 * Time: 14:40
 * Description:
 */
@Api("任务入库文件管理")
@CrossOrigin
@RestController
@RequestMapping("/plus-storage-file")
public class PlusStorageFileController {

    private ByPlusStorageFileManager byPlusStorageFileManager;

    public PlusStorageFileController(ByPlusStorageFileManager byPlusStorageFileManager) {
        this.byPlusStorageFileManager = byPlusStorageFileManager;
    }

    @ApiOperation("查询项目入库文件信息(无分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "fileType", value = "文件类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "pinyin", value = "拼音", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "recordTypeId", value = "档案类型", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipList")
    public Result flipList(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "projectId", defaultValue = "") Integer projectId,
            @RequestParam(value = "fileType", defaultValue = "") Integer fileType,
            @RequestParam(value = "pinyin", defaultValue = "") String pinyin,
            @RequestParam(value = "recordTypeId", defaultValue = "") Integer recordTypeId
    ) {
        Page page = byPlusStorageFileManager.search(projectId, fileType, pinyin, recordTypeId, pageNo, pageSize);
        return Result.ok(page);
    }

    @ApiOperation("项目入库文件管理 - 编辑项目入库文件信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "fileName", value = "文件名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fileRename", value = "文件重命名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fileSize", value = "文件大小", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fileUrl", value = "文件路径", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "commitUser", value = "提交人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fileType", value = "文件类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "pinyin", value = "拼音", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "recordTypeId", value = "档案类型", dataType = "int", paramType = "query")
    })
    @PostMapping("/edit")
    public Result edit(
            @RequestParam(value = "id", defaultValue = "") Integer id,
            @RequestParam(value = "projectId", defaultValue = "") Integer projectId,
            @RequestParam(value = "fileName", defaultValue = "") String fileName,
            @RequestParam(value = "fileRename", defaultValue = "") String fileRename,
            @RequestParam(value = "fileSize", defaultValue = "") String fileSize,
            @RequestParam(value = "fileUrl", defaultValue = "") String fileUrl,
            @RequestParam(value = "commitUser", defaultValue = "") String commitUser,
            @RequestParam(value = "remark", defaultValue = "") String remark,
            @RequestParam(value = "fileType", defaultValue = "") String fileType,
            @RequestParam(value = "pinyin", defaultValue = "") String pinyin,
            @RequestParam(value = "recordTypeId", defaultValue = "") Integer recordTypeId,
            MultipartFile file
    ) {
        ByPlusStorageFile byPlusStorageFile = new ByPlusStorageFile();
        if (id != null) {
            byPlusStorageFile.setId(id);
        }
        byPlusStorageFile.setProjectId(projectId);
        byPlusStorageFile.setFileName(fileName);
        byPlusStorageFile.setFileRename(fileRename);
        byPlusStorageFile.setFileSize(fileSize);
        byPlusStorageFile.setFileUrl(fileUrl);
        byPlusStorageFile.setCommitUser(commitUser);
        byPlusStorageFile.setRemark(remark);
        byPlusStorageFile.setFileType(fileType);
        byPlusStorageFile.setPinyin(pinyin);
        byPlusStorageFile.setRecordTypeId(recordTypeId);
        byPlusStorageFileManager.upperSave(byPlusStorageFile, file);
        return Result.ok();
    }

    @ApiOperation("项目入库文件信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @PostMapping("/detail")
    public Result detail(
            @RequestParam(value = "id", defaultValue = "") Integer id
    ) {
        ByPlusStorageFile byPlusStorageFile = byPlusStorageFileManager.searchById(id);
        return Result.ok(byPlusStorageFile);
    }

    @ApiOperation("项目入库文件管理 - 删除项目入库文件信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", dataType = "string", paramType = "query")
    })
    @PostMapping("/remove")
    public Result remove(
            @RequestParam(value = "ids") String ids
    ) {
        byPlusStorageFileManager.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("任务文件管理 - 下载任务文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @PostMapping("/download")
    public Result download(
            @RequestParam(value = "id") Integer id,
            HttpServletResponse response
    ) {
        boolean flag = byPlusStorageFileManager.downloadFile(id, response);
        if (flag == true) {
            return Result.ok();
        } else {
            return Result.error("文件下载失败!");
        }
    }

    @ApiOperation("文件夹上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "commitUser", value = "提交人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query")
    })
    @PostMapping("/moreFileUpload")
    public Result moreFileUpload(
            @RequestParam(value = "projectId", defaultValue = "") Integer projectId,
            @RequestParam(value = "commitUser", defaultValue = "") String commitUser,
            @RequestParam(value = "remark", defaultValue = "") String remark,
            HttpServletRequest request
    ) {
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        List<MultipartFile> files = params.getFiles("fileFolder");
        if (files.size() > 0) {
            byPlusStorageFileManager.uploadFile(files, projectId, commitUser, remark);
        }
        return Result.ok();
    }
}
