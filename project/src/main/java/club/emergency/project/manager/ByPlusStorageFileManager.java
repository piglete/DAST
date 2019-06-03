package club.emergency.project.manager;

import club.emergency.project.model.ByPlusStorageFile;
import club.map.core.manager.GenericManager;
import club.map.core.model.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 任务入库文件管理
 */
public interface ByPlusStorageFileManager extends GenericManager<ByPlusStorageFile, Integer> {

    /**
     * 文件上传
     *
     * @param byPlusStorageFile
     * @param file
     */
    void upperSave(ByPlusStorageFile byPlusStorageFile, MultipartFile file);

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    ByPlusStorageFile searchById(Integer id);

    /**
     * 删除
     *
     * @param ids
     */
    void removeByIds(String ids);

    /**
     * 下载
     *
     * @param id
     * @param response
     * @return
     */
    boolean downloadFile(Integer id, HttpServletResponse response);

    /**
     * 分页查询
     *
     * @param projectId
     * @param fileType
     * @param pinyin
     * @param recordTypeId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page search(Integer projectId, Integer fileType, String pinyin, Integer recordTypeId, Integer pageNo, Integer pageSize);

    /**
     * 文件夹上传
     *
     * @param file
     * @param projectId
     * @param commitUser
     * @param remark
     */
    void uploadFile(List<MultipartFile> file, Integer projectId, String commitUser, String remark);
}