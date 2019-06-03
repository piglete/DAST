package club.emergency.project.mapper;

import club.emergency.project.model.ByPlusRecordModel;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ByPlusRecordModelMapper extends GenericMapper<ByPlusRecordModel, Integer> {
}