package club.emergency.project.manager;

import club.emergency.project.model.ByPlusDocument;
import club.map.core.manager.GenericManager;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 客户资料管理
 */
public interface ByPlusDocumentManager extends GenericManager<ByPlusDocument, Integer> {
    /**
     * 客户资料查询
     *
     * @param projectId
     * @return
     */
    List<ByPlusDocument> search(Integer projectId);

    /**
     * 客户资料上传
     *
     * @param byPlusDocument
     * @param file
     */
    void upperSave(ByPlusDocument byPlusDocument, MultipartFile file);

    /**
     * 客户资料删除
     *
     * @param ids
     */
    void removeByIds(String ids);

    /**
     * 客户资料详情查询
     *
     * @param id
     * @return
     */
    ByPlusDocument searchById(Integer id);

    /**
     * 客户资料下载
     *
     * @param id
     * @param response
     * @return
     */
    boolean downloadFile(Integer id, HttpServletResponse response);
}