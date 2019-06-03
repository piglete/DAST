package club.emergency.project.mapper;

import club.emergency.project.model.ByPlusStorageFile;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ByPlusStorageFileMapper extends GenericMapper<ByPlusStorageFile, Integer> {

    @Select("select id as id,file_url as fileUrl,file_rename as fileRename from by_plus_storage_file where file_name = #{arg0} and using_type = 1")
    ByPlusStorageFile searchFileName(String fileName);
}