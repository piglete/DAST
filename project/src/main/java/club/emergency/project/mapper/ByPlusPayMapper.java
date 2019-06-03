package club.emergency.project.mapper;

import club.emergency.project.model.ByPlusPay;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ByPlusPayMapper extends GenericMapper<ByPlusPay, Integer> {
}