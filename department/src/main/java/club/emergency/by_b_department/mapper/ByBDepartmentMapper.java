package club.emergency.by_b_department.mapper;

import club.emergency.by_b_department.model.ByBDepartment;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ByBDepartmentMapper extends GenericMapper<ByBDepartment, Integer> {

    @Select("select max(code) from by_b_department where code like #{arg0} and parent_id = #{arg1} and using_type = 1")
    String queryMaxCodeByParentId(String s, Integer parentId);

    @Select("select id as id,department_name as departmentName from by_b_department where code = #{arg0} and using_type = 1")
    ByBDepartment getByCode(String depCode);

    @Select("select department_name as departmentName from by_b_department where code = #{arg0} and using_type = 1")
    String searchNameByCode(String departmentCode);

    @Select("select id as id,department_name as departmentName,code as code,description as description,parent_id as parentId,\n" +
            "sort_num as sortNum,release_flag as releaseFlag,invalid_flag as invalidFlag from by_b_department \n" +
            "where parent_id = #{arg0} and using_type = 1 order by sort_num asc")
    List<ByBDepartment> searchByParentId(Integer id);

    @Select("select count(*) from by_b_department where code like CONCAT(#{arg0},'%') and parent_id = #{arg1} and using_type = 1")
    Integer queryMaxSortNumByParentId(String s, Integer parentId);

    @Select("<script>" +
            "select distinct id as id,department_name as departmentName,code as code " +
            "from by_b_department " +
            "where using_type = 1" +
            "<if test='releaseFlag != null'>" +
            " and release_flag = #{releaseFlag}" +
            "</if>" +
            "<if test='departmentCode != null'>" +
            " and code = #{departmentCode}" +
            "</if>" +
            "</script>")
    List<ByBDepartment> searchList(@Param("releaseFlag") Integer releaseFlag, @Param("departmentCode") String departmentCode);

//    @SelectProvider(type = ByBDepartmentProvider.class, method = "getList")
//    List<ByBDepartment> searchList(Integer releaseFlag, String departmentCode);
}