package club.emergency.project.manager.impl;

import club.emergency.project.manager.ByPlusProjectManager;
import club.emergency.project.manager.ByPlusStorageFileManager;
import club.emergency.project.mapper.ByPlusStorageFileMapper;
import club.emergency.project.model.ByPlusStorageFile;
import club.map.base.util.FileSizeUnitTransform;
import club.map.base.util.FtpUtils;
import club.map.core.manager.impl.GenericManagerImpl;
import club.map.core.model.FlipFilter;
import club.map.core.model.Operate;
import club.map.core.model.Page;
import club.map.core.util.PinyinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class ByPlusStorageFileManagerImpl extends GenericManagerImpl<ByPlusStorageFile, Integer> implements ByPlusStorageFileManager {

    private ByPlusProjectManager byPlusProjectManager;
    private ByPlusStorageFileMapper byPlusStorageFileMapper;

    @Autowired
    public ByPlusStorageFileManagerImpl(ByPlusStorageFileMapper mapper,
                                        ByPlusProjectManager byPlusProjectManager,
                                        ByPlusStorageFileMapper byPlusStorageFileMapper
    ) {
        super(mapper, ByPlusStorageFile.class);
        this.byPlusProjectManager = byPlusProjectManager;
        this.byPlusStorageFileMapper = byPlusStorageFileMapper;
    }

    @Transactional
    @Override
    public void upperSave(ByPlusStorageFile byPlusStorageFile, MultipartFile file) {
        //创建文件保存目录
        String path = this.createFolder();
        //如果是修改操作,判断文件是否修改
        if (file != null) {
            String fileName = file.getOriginalFilename();
            String fileRename = FtpUtils.fileReName(fileName);
            String fileSize = FileSizeUnitTransform.GetFileSize(file.getSize());
            //获取文件类型
            int index = fileName.lastIndexOf(".");
            String fileType = fileName.substring(index + 1);
            //修改文件时,先删除之前的文件,在进行新的文件上传处理
            if (byPlusStorageFile.getId() != null) {
                String oldFileName = byPlusStorageFile.getFileRename();
                String oldFileUrl = byPlusStorageFile.getFileUrl();
                FtpUtils.deleteFtpFile(oldFileUrl, oldFileName);
            }
            try {
                FtpUtils.uploadFtpFile(file, path, fileRename);
            } catch (Exception e) {
                e.printStackTrace();
            }
            byPlusStorageFile.setFileUrl(path);
            byPlusStorageFile.setFileName(fileName);
            byPlusStorageFile.setFileSize(fileSize);
            byPlusStorageFile.setFileRename(fileRename);
            byPlusStorageFile.setFileType(fileType);
        }
        Integer projectId = byPlusStorageFile.getProjectId();
        Integer recordTypeId = byPlusProjectManager.get(projectId).getRecordTypeId();
        byPlusStorageFile.setRecordTypeId(recordTypeId);
        //文件名pinyin 处理,取名称第一个子的首字母
        String name = byPlusStorageFile.getFileRename();
        String pinyin = PinyinUtil.getFirstLetter(name);
        byPlusStorageFile.setPinyin(pinyin);
        this.save(byPlusStorageFile);
    }

    /**
     * 创建文件目录
     * 文件保存路径为: 硬盘//固定定级目录//文件类型//年份//月份//天
     *
     * @param byPlusStorageFile
     * @return
     */
    private String createFolder() {
        //获取年份、月份
        LocalDateTime localDate = LocalDateTime.now();
        String year = String.valueOf(localDate.getYear());
        String month = String.valueOf(localDate.getMonthValue());
//        String day = String.valueOf(localDate.getDayOfMonth());
        //文件路径
        String dir = null;
        try {
            dir = FtpUtils.URL_PATH + "/" + FtpUtils.RECORD + "/" + year + "/" + month;
        } catch (Exception e) {
            e.printStackTrace();
        }
        File filePath = new File(dir);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        return dir;
    }

    @Override
    public ByPlusStorageFile searchById(Integer id) {
        ByPlusStorageFile byPlusStorageFile = this.get(id);
        return byPlusStorageFile;
    }

    /**
     * 删除文件和记录
     *
     * @param ids
     */
    @Transactional
    @Override
    public void removeByIds(String ids) {
        String[] idArr = ids.split(",");
        Arrays.asList(idArr).forEach(id -> {
            Integer sid = Integer.valueOf(id);
            //删除文件
            ByPlusStorageFile byPlusStorageFile = this.get(sid);
            String path = byPlusStorageFile.getFileUrl();
            String name = byPlusStorageFile.getFileRename();
            FtpUtils.deleteFtpFile(path, name);
            //删除记录
            this.remove(sid);
        });
    }

    @Override
    public boolean downloadFile(Integer id, HttpServletResponse response) {
        ByPlusStorageFile byPlusStorageFile = this.get(id);
        String fileName = byPlusStorageFile.getFileName();
        String fileUrl = byPlusStorageFile.getFileUrl();
        String fileRename = byPlusStorageFile.getFileRename();
        FtpUtils ftpUtils = new FtpUtils();
        boolean flag = false;
        try {
            flag = ftpUtils.downloadByFtp(response,null, fileUrl, fileName, fileRename);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public Page search(Integer projectId, Integer fileType, String pinyin, Integer recordTypeId, Integer pageNo, Integer pageSize) {
        FlipFilter flipFilter = new FlipFilter(ByPlusStorageFile.class);
        flipFilter.addSearch(projectId, Operate.EQUAL, "projectId");
        flipFilter.addSearch(fileType, Operate.EQUAL, "fileType");
        flipFilter.addSearch(pinyin, Operate.EQUAL, "pinyin");
        flipFilter.addSearch(recordTypeId, Operate.EQUAL, "recordTypeId");
        flipFilter.setPageSize(pageSize);
        flipFilter.setPageNo(pageNo);
        return this.flipUsingInPage(flipFilter);
    }

    /**
     * 文件上传
     *
     * @param file
     * @param projectId
     * @param commitUser
     * @param remark
     */
    @Transactional
    @Override
    public void uploadFile(List<MultipartFile> file, Integer projectId, String commitUser, String remark) {
        //获取项目类型
        Integer recordTypeId = byPlusProjectManager.get(projectId).getRecordTypeId();
        for (MultipartFile multipartFile : file) {
            ByPlusStorageFile byPlusStorageFile = new ByPlusStorageFile();
            //获取文件名称
            String fileName = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("/") + 1);
            //判断该文件是否已经存在,如果存在,则删除之前的
            ByPlusStorageFile byPlusStorageFile1 = byPlusStorageFileMapper.searchFileName(fileName);
            if (byPlusStorageFile1 != null) {
                String oldPath = byPlusStorageFile1.getFileUrl();
                String name = byPlusStorageFile1.getFileRename();
                FtpUtils.deleteFtpFile(oldPath, name);
                byPlusStorageFile.setId(byPlusStorageFile1.getId());
            }
            byPlusStorageFile.setFileName(fileName);
            //获取文件类型
            int index = fileName.lastIndexOf(".");
            String fileType = fileName.substring(index + 1);
            byPlusStorageFile.setFileType(fileType);
            //获取拼音
            String pinyin = PinyinUtil.getFirstLetter(fileName);
            byPlusStorageFile.setPinyin(pinyin);
            //文件重命名
            String fileRename = FtpUtils.fileReName(fileName);
            byPlusStorageFile.setFileRename(fileRename);
            //获取文件大小
            String fileSize = FileSizeUnitTransform.GetFileSize(multipartFile.getSize());
            byPlusStorageFile.setFileSize(fileSize);
            //文件下载访问路径
            String path = this.createFolder();
            byPlusStorageFile.setFileUrl(path);
            byPlusStorageFile.setProjectId(projectId);
            byPlusStorageFile.setRecordTypeId(recordTypeId);
            byPlusStorageFile.setCommitUser(commitUser);
            byPlusStorageFile.setRemark(remark);
            try {
                FtpUtils.uploadFtpFile(multipartFile, path, fileRename);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.save(byPlusStorageFile);
        }
    }
}