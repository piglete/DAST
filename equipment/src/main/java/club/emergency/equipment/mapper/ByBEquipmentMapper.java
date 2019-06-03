package club.emergency.equipment.mapper;

import club.emergency.equipment.model.ByBEquipment;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ByBEquipmentMapper extends GenericMapper<ByBEquipment, Integer> {

    @Update("update by_b_equipment set " +
            "appraisal_time = #{arg0}, " +
            "place_storage = #{arg1}, " +
            "department_name = #{arg2}, " +
            "department_id = #{arg3}, " +
            "employee_name = #{arg4}, " +
            "employee_user_name = #{arg5}, " +
            "remark = #{arg6}, " +
            "modify_time = now()," +
            "appraisal_id = #{arg7} " +
            "where id = #{arg8} and using_type = 1")
    void updateAppraisalIdById(String appraisalTime, String placeStorage, String departmentName, Integer departmentId, String employeeName, String employeeUserName, String remark, Integer appraisalId, Integer id);

    @Select("select \n" +
            "a.id as id,\n" +
            "a.asset_name as assetName,\n" +
            "a.asset_model as assetModel,\n" +
            "a.unit as unit,\n" +
            "a.equipment_count as equipmentCount,\n" +
            "a.appraisal_id as appraisalId,\n" +
            "b.appraisal_time as appraisalTime,\n" +
            "b.place_storage as placeStorage,\n" +
            "b.department_id as departmentId,\n" +
            "b.employee_id as employeeId,\n" +
            "b.employee_id_user as employee_id_user,\n" +
            "b.remark as remark \n" +
            "from by_b_equipment as a LEFT JOIN by_b_equipment_appraisal as b on a.appraisal_id = b.id where \n" +
            "a.asset_name = #{arg0} and \n" +
            "a.asset_model like concat('%',#{arg1},'%') and \n" +
            "b.place_storage = #{arg0} and \n" +
            "b.department_id = #{arg1} and \n" +
            "a.using_type = 1"
    )
    List<ByBEquipment> searchByField();

    @Select("select * from view_equipment u where u.assetName = #{arg0} and u.assetModel like concat('%',#{arg1},'%') and u.placeStorage = #{arg2} and u.departmentId = #{arg3}")
    List<ByBEquipment> search(String assetName, String assetModel, String placeStorage, Integer departmentId);

    @Update("update by_b_equipment set appraisal_id = #{arg0} where id = #{arg1} and using_type = 1")
    void updateAppraisalId(Integer id, Integer equipmentId);
}