package club.emergency.project.mapper;

import club.emergency.project.model.ByPlusTaskQuality;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ByPlusTaskQualityMapper extends GenericMapper<ByPlusTaskQuality, Integer> {
}