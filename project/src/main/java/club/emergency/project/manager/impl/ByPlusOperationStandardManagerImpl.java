package club.emergency.project.manager.impl;

import club.emergency.dictionary.manager.ByBDictionaryChildManager;
import club.emergency.project.manager.ByPlusOperationStandardManager;
import club.emergency.project.mapper.ByPlusOperationStandardMapper;
import club.emergency.project.model.ByPlusOperationStandard;
import club.map.base.util.FileSizeUnitTransform;
import club.map.base.util.FtpUtils;
import club.map.core.manager.impl.GenericManagerImpl;
import club.map.core.model.FlipFilter;
import club.map.core.model.Operate;
import club.map.core.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Service
public class ByPlusOperationStandardManagerImpl extends GenericManagerImpl<ByPlusOperationStandard, Integer> implements ByPlusOperationStandardManager {

    private ByBDictionaryChildManager byBDictionaryChildManager;
    private ByPlusOperationStandardMapper byPlusOperationStandardMapper;

    @Autowired
    public ByPlusOperationStandardManagerImpl(ByPlusOperationStandardMapper mapper,
                                              ByBDictionaryChildManager byBDictionaryChildManager,
                                              ByPlusOperationStandardMapper byPlusOperationStandardMapper
    ) {
        super(mapper, ByPlusOperationStandard.class);
        this.byBDictionaryChildManager = byBDictionaryChildManager;
        this.byPlusOperationStandardMapper = byPlusOperationStandardMapper;
    }

    @Override
    public Page search(String title, String fileName, Integer fileClassifyId, Integer pageNo, Integer pageSize) {
        FlipFilter flipFilter = new FlipFilter(ByPlusOperationStandard.class);
        flipFilter.addSearch("%" + title + "%", Operate.LIKE, "title");
        flipFilter.addSearch("%" + fileName + "%", Operate.LIKE, "fileName");
        flipFilter.addSearch(fileClassifyId, Operate.EQUAL, "fileClassifyId");
        flipFilter.updatePageNo(pageNo);
        flipFilter.setPageSize(pageSize);
        Page page = this.flipUsingInPage(flipFilter);
        List<ByPlusOperationStandard> byPlusOperationStandardList = page.getListInfo();
        for (ByPlusOperationStandard byPlusOperationStandard : byPlusOperationStandardList) {
            Integer id = byPlusOperationStandard.getId();
            byPlusOperationStandard = this.searchInfo(id);
        }
        return page;
    }

    @Transactional
    @Override
    public void upperSave(ByPlusOperationStandard byPlusOperationStandard, MultipartFile file) {
        if (file != null) {
            String fileName = file.getOriginalFilename();
            String fileRename = FtpUtils.fileReName(fileName);
            String fileSize = FileSizeUnitTransform.GetFileSize(file.getSize());
            //获取文件类型
            int index = fileName.lastIndexOf(".");
            String fileType = fileName.substring(index + 1);
            String path = FtpUtils.URL_PATH + "/" + FtpUtils.STR_PATH;
            //修改文件时,先删除之前的文件,在进行新的文件上传处理
            if (byPlusOperationStandard.getId() != null) {
                String oldFileName = byPlusOperationStandard.getFileRename();
                String oldFileUrl = byPlusOperationStandard.getFileUrl();
                FtpUtils.deleteFtpFile(oldFileUrl, oldFileName);
            }
            try {
                FtpUtils.uploadFtpFile(file, path, fileRename);
            } catch (Exception e) {
                e.printStackTrace();
            }
            byPlusOperationStandard.setFileUrl(path);
            byPlusOperationStandard.setFileName(fileName);
            byPlusOperationStandard.setFileSize(fileSize);
            byPlusOperationStandard.setFileRename(fileRename);
            byPlusOperationStandard.setFileType(fileType);
        }
        this.save(byPlusOperationStandard);
    }

    /**
     * 删除文件时先删除文件和文件记录
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
            ByPlusOperationStandard byPlusOperationStandard = this.get(sid);
            String path = byPlusOperationStandard.getFileUrl();
            String name = byPlusOperationStandard.getFileRename();
            FtpUtils.deleteFtpFile(path, name);
            //删除记录
            this.remove(sid);
        });
    }

    @Override
    public boolean downloadFile(Integer id, HttpServletResponse response) {
        ByPlusOperationStandard byPlusOperationStandard = this.get(id);
        String fileName = byPlusOperationStandard.getFileName();
        String fileUrl = byPlusOperationStandard.getFileUrl();
        String fileNewName = byPlusOperationStandard.getFileRename();
        FtpUtils ftpUtils = new FtpUtils();
        boolean flag = false;
        try {
            flag = ftpUtils.downloadByFtp(response, null,fileUrl, fileName, fileNewName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public ByPlusOperationStandard searchInfo(Integer id) {
        ByPlusOperationStandard byPlusOperationStandard = this.get(id);
        Integer fileClassifyId = byPlusOperationStandard.getFileClassifyId();
        if (fileClassifyId != null) {
            String name = byBDictionaryChildManager.get(fileClassifyId).getAlias();
            byPlusOperationStandard.setFileClassifyName(name);
        } else {
            byPlusOperationStandard.setFileClassifyName("");
        }
        return byPlusOperationStandard;
    }

    /**
     * 查询无分页的
     *
     * @param title
     * @param fileName
     * @param fileClassifyId
     * @return
     */
    @Override
    public List<ByPlusOperationStandard> searchWithoutPage(String title, String fileName, Integer fileClassifyId) {
        FlipFilter flipFilter = new FlipFilter(ByPlusOperationStandard.class);
        flipFilter.addSearch("%" + title + "%", Operate.LIKE, "title");
        flipFilter.addSearch("%" + fileName + "%", Operate.LIKE, "fileName");
        flipFilter.addSearch(fileClassifyId, Operate.EQUAL, "fileClassifyId");
        List<ByPlusOperationStandard> byPlusOperationStandardList = this.list(flipFilter);
        for (ByPlusOperationStandard byPlusOperationStandard : byPlusOperationStandardList) {
            Integer id = byPlusOperationStandard.getId();
            this.searchInfo(id);
        }
        return byPlusOperationStandardList;
    }

    /**
     * 查询文件列表中最新的文件
     *
     * @return
     */
    @Override
    public ByPlusOperationStandard searchNewest() {
        Integer maxId = byPlusOperationStandardMapper.searchMaxId();
        ByPlusOperationStandard byPlusOperationStandard = this.get(maxId);
        return byPlusOperationStandard;
    }
}