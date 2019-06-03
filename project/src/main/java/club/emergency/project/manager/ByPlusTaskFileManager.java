package club.emergency.project.manager;

import club.emergency.project.model.ByPlusTaskFile;
import club.map.core.manager.GenericManager;
import club.map.core.model.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * 任务文件管理接口
 */
public interface ByPlusTaskFileManager extends GenericManager<ByPlusTaskFile, Integer> {
    /**
     * 任务文件分页查询
     *
     * @param projectId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page search(Integer projectId, Integer pageNo, Integer pageSize);
    /**
     * 档案库列表--分页查询
     *
     * @param recordTypeId
     * @return
     */
    List<Map<String,Object>> recordList(Integer recordTypeId,String regionCode);
    /**
     * 文件/文件夹列表--通过父节点查找所有子节点（做缓存）
     *
     * @param parentId
     * @return
     */
    List<Map<String,Object>> getByParentId(Integer parentId);


    /**
     * 任务文件上传
     *
     * @param byPlusTaskFile
     * @param file
     */
    void upperSave(ByPlusTaskFile byPlusTaskFile, MultipartFile file);

    /**
     * 任务文件详情查询
     *
     * @param id
     * @return
     */
    ByPlusTaskFile searchById(Integer id);

    /**
     * 任务文件删除
     *
     * @param ids
     */
    void removeByIds(String ids);

    /**
     * 任务文件、文件夹下载
     *
     * @param id
     * @param response
     * @return
     */
    boolean downloadFile(Integer id, String type, HttpServletResponse response) throws FileNotFoundException;

    /**
     * 单文件、多文件上传
     *
     * @param files
     * @param projectId
     * @param commitUser
     * @param resultTypeId
     */
    void uploadFolder(List<MultipartFile> files, Integer projectId, String commitUser, Integer resultTypeId);

}