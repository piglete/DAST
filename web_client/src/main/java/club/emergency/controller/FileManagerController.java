package club.emergency.controller;

import club.emergency.file.manager.PluploadManager;
import club.emergency.file.model.FileInfo;
import club.emergency.file.model.Plupload;
import club.emergency.project.manager.ByPlusDocumentManager;
import club.map.core.web.util.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: zhaojinxiong
 * Date: 2018-12-07
 * Time: 11:38
 * Description: 项目使用ftp对文件处理,该模块暂未使用
 */
//@Api("文件上传下载管理")
//@CrossOrigin
//@RestController
//@RequestMapping("/file")
public class FileManagerController {

    private PluploadManager pluploadManager;
    private ByPlusDocumentManager byPlusDocumentManager;

    public FileManagerController(PluploadManager pluploadManager) {
        this.pluploadManager = pluploadManager;
    }

    @ApiOperation("文件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileId", value = "文件编号", dataType = "int", paramType = "query"),
    })
    @PostMapping("/uploadFile")
    public Result uploadFile(
            @RequestParam(value = "fileId", defaultValue = "") Integer fileId,
            Plupload plupload, HttpServletRequest request, HttpServletResponse response
    ) {
        String FileDir = "D://upload";// 文件保存的文件夹
        plupload.setRequest(request);// 手动传入Plupload对象HttpServletRequest属性
        // 文件存储绝对路径,会是一个文件夹，项目相应Servlet容器下的"pluploadDir"文件夹，还会以用户唯一id作划分
        File dir = new File(FileDir);
        if (!dir.exists()) {
            dir.mkdirs();// 可创建多级目录，而mkdir()只能创建一级目录
        }
        // 开始上传文件
        FileInfo fileInfo = pluploadManager.upload(plupload, dir);
//        if(fileInfo == null){
//            return Result.ok(null);
//        }else{
//            String name=fileInfo.getFileName();
//            String size=fileInfo.getFileSize();
//            String url=fileInfo.getFileUrl();
//            ByPlusDocument byPlusDocument=new ByPlusDocument();
//            byPlusDocument.setFileUrl(url);
//            byPlusDocument.setFileSize(size);
//            byPlusDocument.setFileName(name);
//            byPlusDocument.setProjectId(fileId);
//            byPlusDocumentManager.upperSave(byPlusDocument);
//            return Result.ok(fileInfo);
//        }
        return Result.ok();
    }

    @ApiOperation("文件下载")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "文件id", dataType = "int", paramType = "query"),
    })
    @PostMapping("/downFile")
    public Result downFile(
            @RequestParam(value = "id", defaultValue = "") Integer id,
            HttpServletResponse response
    ) {
//        String filename="1545270813332mysql-5.6.40-winx64";
        String name = "mysql-5.6.40-winx64.zip";
        String filePath = "D:\\upload\\1545270813332mysql-5.6.40-winx64.zip";
        boolean flag = pluploadManager.downFile(name, filePath, response);
        if (flag == true) {
            return Result.ok();
        } else {
            return Result.error("文件下载失败!");
        }
    }

    @ApiOperation("文件删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "url", value = "文件路径url", dataType = "string", paramType = "query"),
    })
    @PostMapping("/removeFile")
    public Result removeFile(
            @RequestParam(value = "url", defaultValue = "") String url
    ) {
//        String path="D://upload//aa.txt";
        Integer flag = pluploadManager.remove(url);
        if (flag == 0) {
            return Result.ok();
        } else if (flag == 1) {
            return Result.error("文件不存在");
        } else {
            return Result.error("文件删除失败!");
        }
    }
}
