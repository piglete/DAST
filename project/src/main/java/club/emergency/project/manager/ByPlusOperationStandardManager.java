package club.emergency.project.manager;

import club.emergency.project.model.ByPlusOperationStandard;
import club.map.core.manager.GenericManager;
import club.map.core.model.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 作业指导文件管理
 */
public interface ByPlusOperationStandardManager extends GenericManager<ByPlusOperationStandard, Integer> {
    /**
     * 分页查询
     *
     * @param title
     * @param fileName
     * @param fileClassifyId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page search(String title, String fileName, Integer fileClassifyId, Integer pageNo, Integer pageSize);

    /**
     * 编辑(新增或者修改F)
     *
     * @param byPlusOperationStandard
     * @param file
     */
    void upperSave(ByPlusOperationStandard byPlusOperationStandard, MultipartFile file);

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
     * 查询详情
     *
     * @param id
     * @return
     */
    ByPlusOperationStandard searchInfo(Integer id);

    /**
     * 查询全部
     *
     * @param title
     * @param fileName
     * @param fileClassifyId
     * @return
     */
    List<ByPlusOperationStandard> searchWithoutPage(String title, String fileName, Integer fileClassifyId);

    /**
     * 查询最新的文件
     *
     * @return
     */
    ByPlusOperationStandard searchNewest();
}