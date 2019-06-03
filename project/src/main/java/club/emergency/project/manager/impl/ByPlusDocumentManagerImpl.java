package club.emergency.project.manager.impl;

import club.emergency.by_b_department.mapper.ByBDepartmentMapper;
import club.emergency.project.manager.ByPlusDocumentManager;
import club.emergency.project.manager.ByPlusProjectManager;
import club.emergency.project.mapper.ByPlusDocumentMapper;
import club.emergency.project.model.ByPlusDocument;
import club.map.base.util.FileSizeUnitTransform;
import club.map.base.util.FtpUtils;
import club.map.core.manager.impl.GenericManagerImpl;
import club.map.core.model.FlipFilter;
import club.map.core.model.Operate;
import com.wanqing.util.StringHandler;
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
public class ByPlusDocumentManagerImpl extends GenericManagerImpl<ByPlusDocument, Integer> implements ByPlusDocumentManager {

    private ByBDepartmentMapper byBDepartmentMapper;
    private ByPlusProjectManager byPlusProjectManager;

    @Autowired
    public ByPlusDocumentManagerImpl(ByPlusDocumentMapper mapper,
                                     ByBDepartmentMapper byBDepartmentMapper,
                                     ByPlusProjectManager byPlusProjectManager) {
        super(mapper, ByPlusDocument.class);
        this.byBDepartmentMapper = byBDepartmentMapper;
        this.byPlusProjectManager = byPlusProjectManager;
    }

    @Override
    public List<ByPlusDocument> search(Integer projectId) {
        FlipFilter flipFilter = new FlipFilter(ByPlusDocument.class);
        flipFilter.addSearch(projectId, Operate.EQUAL, "projectId");
        List<ByPlusDocument> byPlusDocumentList = this.list(flipFilter);
        for (ByPlusDocument byPlusDocument : byPlusDocumentList) {
            this.searchInfo(byPlusDocument);
        }
        return byPlusDocumentList;
    }

    /**
     * 文件上传
     * 1.路径获取和生成对应文件夹
     * 2.判断是修改操作还是新增操作;修改时文件更新
     *
     * @param byPlusDocument
     * @param file
     */
    @Transactional
    @Override
    public void upperSave(ByPlusDocument byPlusDocument, MultipartFile file) {
        //创建文件保存目录
        String path = this.createFolder(byPlusDocument);
        //如果是修改操作,判断文件是否修改
        if (file != null) {
            String fileName = file.getOriginalFilename();
            String fileRename = FtpUtils.fileReName(fileName);
            String fileSize = FileSizeUnitTransform.GetFileSize(file.getSize());
            //获取文件类型
            int index = fileName.lastIndexOf(".");
            String fileType = fileName.substring(index + 1);
            //修改文件时,先删除之前的文件,在进行新的文件上传处理
            if (byPlusDocument.getId() != null) {
                String oldFileName = byPlusDocument.getFileRename();
                String oldFileUrl = byPlusDocument.getFileUrl();
                FtpUtils.deleteFtpFile(oldFileUrl, oldFileName);
            }
            try {
                FtpUtils.uploadFtpFile(file, path, fileRename);
            } catch (Exception e) {
                e.printStackTrace();
            }
            byPlusDocument.setFileUrl(path);
            byPlusDocument.setFileName(fileName);
            byPlusDocument.setFileSize(fileSize);
            byPlusDocument.setFileRename(fileRename);
            byPlusDocument.setFileType(fileType);
        }
        this.save(byPlusDocument);
    }

    /**
     * 创建文件目录
     * 文件保存路径为: 硬盘//固定定级目录//文件类型//年份//月份//天
     *
     * @param byPlusDocument
     * @return
     */
    private String createFolder(ByPlusDocument byPlusDocument) {
        //获取年份、月份、天
        LocalDateTime localDate = LocalDateTime.now();
        String year = String.valueOf(localDate.getYear());
        String month = String.valueOf(localDate.getMonthValue());
        String day = String.valueOf(localDate.getDayOfMonth());
        //文件保存路径
        String dir = null;
        try {
            dir = FtpUtils.URL_PATH + "/" + FtpUtils.CUSTOMER_DOCUMENT_PATH + "/" + year + "/" + month + "/" + day;
        } catch (Exception e) {
            e.printStackTrace();
        }
        File filePath = new File(dir);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        return dir;
    }

    @Transactional
    @Override
    public void removeByIds(String ids) {
        String[] idArr = ids.split(",");
        Arrays.asList(idArr).forEach(id -> {
            Integer sid = Integer.valueOf(id);
            //删除文件
            ByPlusDocument byPlusDocument = this.get(sid);
            String path = byPlusDocument.getFileUrl();
            String name = byPlusDocument.getFileRename();
            FtpUtils.deleteFtpFile(path, name);
            //删除记录
            this.remove(sid);
        });
    }

    @Override
    public ByPlusDocument searchById(Integer id) {
        ByPlusDocument byPlusDocument = this.get(id);
        this.searchInfo(byPlusDocument);
        return byPlusDocument;
    }

    @Transactional
    @Override
    public boolean downloadFile(Integer id, HttpServletResponse response) {
        ByPlusDocument byPlusDocument = this.get(id);
        String fileName = byPlusDocument.getFileName();
        String fileUrl = byPlusDocument.getFileUrl();
        String fileRename = byPlusDocument.getFileRename();
        FtpUtils ftpUtils = new FtpUtils();
        boolean flag = false;
        try {
            flag = ftpUtils.downloadByFtp(response, null,fileUrl, fileName, fileRename);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 查询详情
     *
     * @param byPlusDocument
     */
    private void searchInfo(ByPlusDocument byPlusDocument) {
        String departmentCode = byPlusDocument.getDepartmentCode();
        if (StringHandler.isNotEmptyOrNull(departmentCode)) {
            String departmentName = byBDepartmentMapper.searchNameByCode(departmentCode);
            byPlusDocument.setDepartmentName(departmentName);
        } else {
            byPlusDocument.setDepartmentName("");
        }
    }
}