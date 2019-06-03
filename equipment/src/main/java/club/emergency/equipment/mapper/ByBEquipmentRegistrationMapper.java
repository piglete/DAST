package club.emergency.equipment.mapper;

import club.emergency.equipment.model.ByBEquipmentRegistration;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ByBEquipmentRegistrationMapper extends GenericMapper<ByBEquipmentRegistration, Integer> {
}