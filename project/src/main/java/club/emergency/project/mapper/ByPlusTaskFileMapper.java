package club.emergency.project.mapper;

import club.emergency.project.mapper.sqlprovider.ByPlusTaskFileProvider;
import club.emergency.project.model.ByPlusTaskFile;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface ByPlusTaskFileMapper extends GenericMapper<ByPlusTaskFile, Integer> {

    @Select("select id as id,file_url as fileUrl,file_rename as fileRename from by_plus_task_file where file_name = #{arg0} and using_type = 1")
    ByPlusTaskFile searchFileName(String fileName);
    /*
    档案库列表
     */
    @SelectProvider(type = ByPlusTaskFileProvider.class, method = "recordList")
    List<Map<String,Object>> recordList(Integer recordTypeId,String regionCode);
    /*
    获取子文件夹列表
     */
    @SelectProvider(type = ByPlusTaskFileProvider.class, method = "getFilesByFolderId")
    List<Map<String,Object>> getFilesByFolderId(Integer parentId);
    /*
    获取子文件列表
     */
    @SelectProvider(type = ByPlusTaskFileProvider.class, method = "getFolderByParentId")
    List<Map<String,Object>> getFolderByParentId(Integer parentId);

    @Select("select * from by_plus_task_file where folder_id = #{arg0} and using_type = 1")
    List<ByPlusTaskFile> getByFolderId(Integer parentId);
    /*
    根据id查找
     */
    @Select(" select id as id, " +
            " project_id as projectId, " +
            " folder_name as folderName, " +
            " folder_full_name as folderFullName " +
            " from by_plus_task_file_folder where id = #{arg0} and using_type = 1")
    Map<String,Object> getFolderById(Integer id);
}