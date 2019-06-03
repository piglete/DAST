package club.emergency.project.mapper;

import club.emergency.project.model.ByPlusRecordReturn;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ByPlusRecordReturnMapper extends GenericMapper<ByPlusRecordReturn, Integer> {
}