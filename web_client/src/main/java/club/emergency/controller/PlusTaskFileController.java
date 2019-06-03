package club.emergency.controller;

import club.emergency.project.manager.ByPlusTaskFileManager;
import club.emergency.project.model.ByPlusTaskFile;
import club.map.core.model.Page;
import club.map.core.web.util.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhaojinxiong
 * Date: 2018-12-29
 * Time: 14:53
 * Description:
 */
@Api("任务文件管理")
@CrossOrigin
@RestController
@RequestMapping("/plus-task-file")
public class PlusTaskFileController  {

    private ByPlusTaskFileManager byPlusTaskFileManager;

    public PlusTaskFileController(ByPlusTaskFileManager byPlusTaskFileManager) {
        this.byPlusTaskFileManager = byPlusTaskFileManager;
    }

    @ApiOperation("查询任务文件信息(无分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "int", paramType = "query")
    })
    @PostMapping("/flipList")
    public Result flipList(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "projectId", defaultValue = "") Integer projectId
    ) {
        Page page = byPlusTaskFileManager.search(projectId, pageNo, pageSize);
        return Result.ok(page);
    }
    @ApiOperation("档案库管理-查询档案列表-modified by lxl")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "recordTypeId", value = "项目类型id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "regionCode", value = "区域code", dataType = "string", paramType = "query")
    })
    @PostMapping("/recordList")
    public Result recordList(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "recordTypeId", defaultValue = "") Integer recordTypeId,
            @RequestParam(value = "regionCode", defaultValue = "") String regionCode
    ) {
        if (pageSize != null) {
            PageHelper.startPage(pageNo,pageSize);
        }
        List<Map<String,Object>> docs = byPlusTaskFileManager.recordList(recordTypeId,regionCode);
        PageInfo page= new PageInfo<>(docs);
        return Result.ok(page);
    }
    @ApiOperation("档案库管理-查询子列表-modified by lxl")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "当前id", dataType = "int", paramType = "query")
    })
    @PostMapping("/getByParentId")
    public Result recordFolderList(
            @RequestParam(value = "id", defaultValue = "") Integer parentId
    ) {
        List<Map<String,Object>> flipList = byPlusTaskFileManager.getByParentId(parentId);
        return Result.ok(flipList);
    }
    @ApiOperation("任务文件管理 - 编辑任务文件信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "fileName", value = "文件名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fileRename", value = "文件重命名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fileSize", value = "文件大小", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fileUrl", value = "文件路径", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "resultTypeId", value = "成果类型id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "commitUser", value = "提交人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "pinyin", value = "拼音", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fileType", value = "文件类型", dataType = "string", paramType = "query")
    })
    @PostMapping("/edit")
    public Result edit(
            @RequestParam(value = "id", defaultValue = "") Integer id,
            @RequestParam(value = "projectId", defaultValue = "") Integer projectId,
            @RequestParam(value = "fileName", defaultValue = "") String fileName,
            @RequestParam(value = "fileRename", defaultValue = "") String fileRename,
            @RequestParam(value = "fileSize", defaultValue = "") String fileSize,
            @RequestParam(value = "fileUrl", defaultValue = "") String fileUrl,
            @RequestParam(value = "resultTypeId", defaultValue = "") Integer resultTypeId,
            @RequestParam(value = "commitUser", defaultValue = "") String commitUser,
            @RequestParam(value = "pinyin", defaultValue = "") String pinyin,
            @RequestParam(value = "fileType", defaultValue = "") String fileType,
            MultipartFile file
    ) {
        ByPlusTaskFile byPlusTaskFile = new ByPlusTaskFile();
        if (id != null) {
            byPlusTaskFile.setId(id);
        }
//        byPlusTaskFile.setProjectId(projectId);
        byPlusTaskFile.setFileName(fileName);
        byPlusTaskFile.setFileRename(fileRename);
        byPlusTaskFile.setFileSize(fileSize);
        byPlusTaskFile.setFileUrl(fileUrl);
        byPlusTaskFile.setResultTypeId(resultTypeId);
        byPlusTaskFile.setCommitUser(commitUser);
        byPlusTaskFile.setPinyin(pinyin);
        byPlusTaskFile.setFileType(fileType);
        byPlusTaskFileManager.upperSave(byPlusTaskFile, file);
        return Result.ok();
    }

    @ApiOperation("任务文件信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @PostMapping("/detail")
    public Result detail(
            @RequestParam(value = "id", defaultValue = "") Integer id
    ) {
        ByPlusTaskFile byPlusTaskFile = byPlusTaskFileManager.searchById(id);
        return Result.ok(byPlusTaskFile);
    }

    @ApiOperation("任务文件管理 - 删除任务文件信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", dataType = "string", paramType = "query")
    })
    @PostMapping("/remove")
    public Result remove(
            @RequestParam(value = "ids") String ids
    ) {
        byPlusTaskFileManager.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("任务文件管理 - 下载任务文件/文件夹-modified by lxl")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "文件类型(folder:文件夹,file:文件 查询会返回type，下载需要带上)", dataType = "string", paramType = "query"),
    })
    @GetMapping("/download")
    public Result download(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "type") String type,
            HttpServletResponse response
    )throws FileNotFoundException {
        boolean flag = byPlusTaskFileManager.downloadFile(id,type, response);
        if (flag) {
            return Result.ok();
        } else {
            return Result.error("文件下载失败!");
        }
    }
//
//    @ApiOperation("文件夹上传")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "int", paramType = "query"),
//            @ApiImplicitParam(name = "commitUser", value = "提交人", dataType = "string", paramType = "query"),
//            @ApiImplicitParam(name = "resultTypeId", value = "成果类型id", dataType = "int", paramType = "query")
//    })
//    @PostMapping("/moreFileUpload")
//    public Result moreFileUpload(
//            @RequestParam(value = "projectId", defaultValue = "") Integer projectId,
//            @RequestParam(value = "commitUser", defaultValue = "") String commitUser,
//            @RequestParam(value = "resultTypeId", defaultValue = "") Integer resultTypeId,
//            HttpServletRequest request
//    ) {
//        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
//        List<MultipartFile> files = params.getFiles("fileFolder");
//        if (files.size() > 0) {
//            byPlusTaskFileManager.uploadFolder(files, projectId, commitUser, resultTypeId);
//        }
//        return Result.ok();
//    }

    @ApiOperation("多文件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commitUser", value = "提交人", dataType = "string", paramType = "query"),
    })
    @PostMapping("/fileUpload")
    public Result handleFileUpload(
            @RequestParam(value = "user", defaultValue = "") String user,
            HttpServletRequest request
    ) {
        List<MultipartFile> filelist =((MultipartHttpServletRequest)request).getFiles("files");
        System.out.print(filelist.size());
        if (filelist.size() > 0) {
            byPlusTaskFileManager.uploadFolder(filelist, null, user, null);
        }
        return Result.ok();
    }
}
