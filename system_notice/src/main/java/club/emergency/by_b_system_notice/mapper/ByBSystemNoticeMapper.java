package club.emergency.by_b_system_notice.mapper;

import club.emergency.by_b_system_notice.model.ByBSystemNotice;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ByBSystemNoticeMapper extends GenericMapper<ByBSystemNotice, Integer> {
}