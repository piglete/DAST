package club.emergency.project.mapper;

import club.emergency.project.model.ByPlusDocument;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ByPlusDocumentMapper extends GenericMapper<ByPlusDocument, Integer> {
}