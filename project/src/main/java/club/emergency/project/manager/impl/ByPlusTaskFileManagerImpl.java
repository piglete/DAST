package club.emergency.project.manager.impl;

import club.emergency.dictionary.manager.ByBDictionaryChildManager;
import club.emergency.project.manager.ByPlusProjectManager;
import club.emergency.project.manager.ByPlusTaskFileManager;
import club.emergency.project.manager.ByPlusTaskManager;
import club.emergency.project.mapper.ByPlusTaskFileMapper;
import club.emergency.project.model.ByPlusTaskFile;
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
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ByPlusTaskFileManagerImpl extends GenericManagerImpl<ByPlusTaskFile, Integer> implements ByPlusTaskFileManager {

    private ByBDictionaryChildManager byBDictionaryChildManager;
    private ByPlusTaskManager byPlusTaskManager;
    private ByPlusProjectManager byPlusProjectManager;
    private ByPlusTaskFileMapper byPlusTaskFileMapper;

    @Autowired
    public ByPlusTaskFileManagerImpl(ByPlusTaskFileMapper mapper,
                                     ByBDictionaryChildManager byBDictionaryChildManager,
                                     ByPlusTaskManager byPlusTaskManager,
                                     ByPlusProjectManager byPlusProjectManager,
                                     ByPlusTaskFileMapper byPlusTaskFileMapper
    ) {
        super(mapper, ByPlusTaskFile.class);
        this.byBDictionaryChildManager = byBDictionaryChildManager;
        this.byPlusTaskManager = byPlusTaskManager;
        this.byPlusProjectManager = byPlusProjectManager;
        this.byPlusTaskFileMapper = byPlusTaskFileMapper;
    }

    @Override
    public Page search(Integer projectId, Integer pageNo, Integer pageSize) {
        FlipFilter flipFilter = new FlipFilter(ByPlusTaskFile.class);
        flipFilter.addSearch(projectId, Operate.EQUAL, "projectId");
        flipFilter.setPageSize(pageSize);
        flipFilter.setPageNo(pageNo);
        Page page = this.flipUsingInPage(flipFilter);
        List<ByPlusTaskFile> byPlusTaskFileList = page.getListInfo();
        for (ByPlusTaskFile byPlusTaskFile : byPlusTaskFileList) {
            this.searchInfo(byPlusTaskFile);
        }
        return page;
    }

    @Override
    public List<Map<String, Object>> recordList(Integer recordTypeId,String regionCode) {
        return byPlusTaskFileMapper.recordList(recordTypeId,regionCode);
    }

    @Override
    public List<Map<String, Object>> getByParentId(Integer parentId) {
        List<Map<String, Object>> folderList= byPlusTaskFileMapper.getFolderByParentId(parentId);
        //查看文件表中有没有该节点下文件
        List<Map<String, Object>> fileList=byPlusTaskFileMapper.getFilesByFolderId(parentId);
        if (fileList != null) {
            folderList.addAll(fileList);
        }
        return folderList;
    }

    /**
     * 文件上传(文件名称重命名,避免中文乱码,将新文件名和原始文件名保存在数据库中)
     * 1.路径获取和生成对应文件夹
     * 2.判断是修改操作还是新增操作;修改时文件是否更新
     *
     * @param byPlusTaskFile
     * @param file
     */
    @Transactional
    @Override
    public void upperSave(ByPlusTaskFile byPlusTaskFile, MultipartFile file) {
        //创建文件保存目录
        String path = this.createFolder(byPlusTaskFile);
        //如果是修改操作,判断文件是否修改
        if (file != null) {
            String fileName = file.getOriginalFilename();
            String fileRename = FtpUtils.fileReName(fileName);
            String fileSize = FileSizeUnitTransform.GetFileSize(file.getSize());
            //获取文件类型
            int index = fileName.lastIndexOf(".");
            String fileType = fileName.substring(index + 1);
            //修改文件时,先删除之前的文件,在进行新的文件上传处理
            if (byPlusTaskFile.getId() != null) {
                String oldFileName = byPlusTaskFile.getFileRename();
                String oldFileUrl = byPlusTaskFile.getFileUrl();
                FtpUtils.deleteFtpFile(oldFileUrl, oldFileName);
            }
            try {
                FtpUtils.uploadFtpFile(file, path, fileRename);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String pinyin = PinyinUtil.getFirstLetter(fileName);
            byPlusTaskFile.setPinyin(pinyin);
            byPlusTaskFile.setFileUrl(path);
            byPlusTaskFile.setFileName(fileName);
            byPlusTaskFile.setFileSize(fileSize);
            byPlusTaskFile.setFileRename(fileRename);
            byPlusTaskFile.setFileType(fileType);
        }
        this.save(byPlusTaskFile);
    }

    /**
     * 创建文件目录,上传单个文件
     * 文件保存路径为: 硬盘//固定定级目录//文件类型//年份//月份//天
     *
     * @param byPlusTaskFile
     * @return
     */
    private String createFolder(ByPlusTaskFile byPlusTaskFile) {
        //获取年份、月份、天
        LocalDateTime localDate = LocalDateTime.now();
        String year = String.valueOf(localDate.getYear());
        String month = String.valueOf(localDate.getMonthValue());
        String day = String.valueOf(localDate.getDayOfMonth());
        //文件路径
        String dir = null;
        try {
            dir = FtpUtils.URL_PATH + "/" + FtpUtils.TASK + "/" + year + "/" + month + "/" + day;
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
    public ByPlusTaskFile searchById(Integer id) {
        ByPlusTaskFile byPlusTaskFile = this.get(id);
        this.searchInfo(byPlusTaskFile);
        return byPlusTaskFile;
    }

    /**
     * 文件删除
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
            ByPlusTaskFile byPlusTaskFile = this.get(sid);
            String path = byPlusTaskFile.getFileUrl();
            String name = byPlusTaskFile.getFileRename();
            FtpUtils.deleteFtpFile(path, name);
            //删除记录
            this.remove(sid);
        });
    }
    @Transactional
    @Override
    public boolean downloadFile(Integer id, String type, HttpServletResponse response) throws FileNotFoundException {
        //根据type判断下载文件类型
        if (type.equals("folder")) {
            boolean flag = false;
            Map<String,Object> folder= byPlusTaskFileMapper.getFolderById(id);
            if (folder != null) {
                String folderPath =  folder.get("folderFullName").toString();
                //对文件夹压缩
                String zipName = System.currentTimeMillis()+".zip";
                //制定zip包路径
                String zipPath = FtpUtils.BASE_PATH+"TEMP";

                //调用下载文件
                FtpUtils ftpUtils = new FtpUtils();
                try {
                    flag = ftpUtils.downloadByFtp(response, folderPath, "TEMP", zipName, zipName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (new File(zipPath,zipName).exists())
                    new File(zipPath,zipName).delete();
            }
            return flag;
        }else{
            ByPlusTaskFile byPlusTaskFile = this.get(id);
            String fileName = byPlusTaskFile.getFileName();
            String fileUrl = byPlusTaskFile.getFileUrl();
            String fileRename = byPlusTaskFile.getFileRename();
            FtpUtils ftpUtils = new FtpUtils();
            boolean flag = false;
            try {
                flag = ftpUtils.downloadByFtp(response,null, fileUrl, fileName, fileRename);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return flag;
        }
    }


    /**
     * 文件夹上传,如果有重复的文件则直接替换
     *
     * @param files
     * @param projectId
     * @param commitUser
     * @param resultTypeId
     */
    @Transactional
    @Override
    public void uploadFolder(List<MultipartFile> files, Integer projectId, String commitUser, Integer resultTypeId) {
        for (MultipartFile multipartFile : files) {
            ByPlusTaskFile byPlusTaskFile = new ByPlusTaskFile();
            //获取文件名称
            String fileOriginName = multipartFile.getOriginalFilename();
            String[] str = fileOriginName.split("/");
            String fileName = str[str.length - 1];
            String fileDir = fileOriginName.replace("/" + fileName, "");
            //判断该文件是否已经存在,如果存在,则删除之前的
            if (checkFileIsExisted(fileName) != null) {
                byPlusTaskFile.setId(checkFileIsExisted(fileName));
            }
            byPlusTaskFile.setFileName(fileName);
            //获取文件类型
            int index = fileName.lastIndexOf(".");
            String fileType = fileName.substring(index + 1);
            byPlusTaskFile.setFileType(fileType);
            //获取拼音
            String pinyin = PinyinUtil.getFirstLetter(fileName);
            //文件重命名
            String fileRename = FtpUtils.fileReName(fileName);
            byPlusTaskFile.setFileRename(fileRename);
            //获取文件大小
            String fileSize = FileSizeUnitTransform.GetFileSize(multipartFile.getSize());
            byPlusTaskFile.setFileSize(fileSize);
            //文件下载访问路径
            String path = this.createFolder(fileRename);
            byPlusTaskFile.setFileUrl(path+fileRename);
            byPlusTaskFile.setCommitUser(commitUser);
            byPlusTaskFile.setPinyin(pinyin);
            byPlusTaskFile.setResultTypeId(resultTypeId);
            try {
                FtpUtils.uploadFtpFile(multipartFile, path, fileRename);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.save(byPlusTaskFile);
        }
    }

    private void searchInfo(ByPlusTaskFile byPlusTaskFile) {
        Integer resultTypeId = byPlusTaskFile.getResultTypeId();
        if (resultTypeId != null) {
            String resultType = byBDictionaryChildManager.get(resultTypeId).getAlias();
            byPlusTaskFile.setResultType(resultType);
        } else {
            byPlusTaskFile.setResultType("");
        }
    }
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    /**
     * 创建文件目录
     * 文件保存路径为: 硬盘//固定定级目录//文件类型//年份//月份//天
     *
     * @param fileDir
     * @return
     */
    private String createFolder(String fileDir) {
        //获取年份、月份
        LocalDateTime localDate = LocalDateTime.now();
        //文件路径
        String dir = null;
        try {
            dir = FtpUtils.MDB_PATH+"/" + localDate.format(formatter) + "/";
        } catch (Exception e) {
            e.printStackTrace();
        }
        File filePath = new File(dir);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        return dir;
    }
    public Integer checkFileIsExisted(String fileName){
        ByPlusTaskFile byPlusTaskFile1 = byPlusTaskFileMapper.searchFileName(fileName);
        if (byPlusTaskFile1 != null) {
            String oldPath = byPlusTaskFile1.getFileUrl();
            String name = byPlusTaskFile1.getFileRename();
            FtpUtils.deleteFtpFile(oldPath, name);
           return byPlusTaskFile1.getId();
        }
        return null;
    }
}