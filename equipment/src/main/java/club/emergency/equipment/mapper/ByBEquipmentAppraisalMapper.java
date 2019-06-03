package club.emergency.equipment.mapper;

import club.emergency.equipment.model.ByBEquipmentAppraisal;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ByBEquipmentAppraisalMapper extends GenericMapper<ByBEquipmentAppraisal, Integer> {
    @Update("update by_b_equipment_appraisal set using_type = 0 where equipment_id = #{arg0} and using_type = 1")
    void removeByEquipmentId(Integer uid);
}