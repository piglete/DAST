package club.emergency.by_b_return_reason.mapper;

import club.emergency.by_b_return_reason.model.ByBReturnReason;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ByBReturnReasonMapper extends GenericMapper<ByBReturnReason, Integer> {

    @Update("update by_b_return_reason set using_type = 0 where parent_id = #{arg0} and using_type = 1")
    void removeByParentId(Integer pid);
}