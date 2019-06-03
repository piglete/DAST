package club.map.base.mapper;

import club.map.base.model.BusinessLog;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BusinessLogMapper extends GenericMapper<BusinessLog, Integer> {
}