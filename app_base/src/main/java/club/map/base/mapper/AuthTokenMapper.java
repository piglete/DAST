package club.map.base.mapper;

import club.map.base.model.AuthToken;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthTokenMapper extends GenericMapper<AuthToken, Integer> {
}