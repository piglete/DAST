package club.emergency.by_b_contract.mapper;

import club.emergency.by_b_contract.model.ByBContract;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ByBContractMapper extends GenericMapper<ByBContract, Integer> {
}