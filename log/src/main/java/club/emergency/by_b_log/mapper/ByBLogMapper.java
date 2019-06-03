package club.emergency.by_b_log.mapper;

import club.emergency.by_b_log.model.ByBLog;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ByBLogMapper extends GenericMapper<ByBLog, Integer> {
}