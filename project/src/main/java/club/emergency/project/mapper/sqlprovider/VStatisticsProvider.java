package club.emergency.project.mapper.sqlprovider;

import club.map.core.mapper.SqlProvider;
import com.wanqing.util.StringHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

public class VStatisticsProvider extends SqlProvider {
    protected final Log log = LogFactory.getLog(getClass());
    public static Map<Object,String> map= null;
    static{
        map= new HashMap<>();
        map.put("total","select count(*) from v_statistics where 1 = 1 ");
        map.put("completed","select count(*) from v_statistics where project_state_id = 78 and t_flag > 1 ");
        map.put("underway","select count(*) from v_statistics where t_flag = 0 ");
        map.put("unstart","select count(*) from v_statistics where task_state_id = 86 ");
        map.put("pause",  "select count(*) from v_statistics where t_flag = 1 ");
        map.put("overdue",  "select count(*) from v_statistics where project_state_id = 78 and over_date_count > 0 ");
        map.put("g95",  "select count(*) from v_statistics where project_state_id = 78 and task_score > 95 ");
        map.put("l60",  "select count(*) from v_statistics where project_state_id = 78 and task_score < 60 ");
        map.put("output",  "select sum(internal_output) from v_statistics where project_state_id = 78 ");
        map.put("year",        " MONTH(start_date) ");
        map.put("recordType",  " record_type_name ");
        map.put("region",      " region_name ");
        map.put("department",  " department_name ");
        map.put("group",       " group_name ");
    }

    public String taskCount(String SearchType, Integer year, Integer month, String regionCode, Integer recordTypeId, String departmentCode, String workGroupCode ) {
        if (SearchType == null) {
            return "";
        }
        String sql = map.get(SearchType);
        if (StringHandler.isEmptyOrNull(sql)) {
            return "";
        }
        if (year != null) {
            sql += " and YEAR(start_date)  = " + year;
        }
        if (month != null) {
            sql += " and MONTH(start_date) = " + month;
        }
        if (!"".equals(regionCode)) {
            sql += " and region_code = " + regionCode;
        }
        if (recordTypeId != null) {
            sql += " and record_type_id = " + recordTypeId;
        }
        if (!"".equals(departmentCode)) {
            sql += " and department_code = " + departmentCode;
        }
        if (!"".equals(workGroupCode)) {
            sql += " and group_code = " + workGroupCode;
        }
        return sql;
    }
    /**
     *
     * @param year
     * @param month
     * @param regionCode
     * @param recordTypeId
     * @param departmentCode
     * @param workGroupCode
     * @return
     */
    public String taskInfoWithWorkloads(Integer year, Integer month, String regionCode, Integer recordTypeId, String departmentCode, String workGroupCode,String taskType) {
        String sql = "select id as id, " +
                " name as name, " +
                " unit_name as unitName, " +
                " address as address, " +
                " longitude as longitude, " +
                " latitude as latitude, " +
                " region_name as regionName, " +
                " record_type_name  as recordTypeName " +
                " from v_statistics  " +
                " where 1 = 1 ";
        if (!"".equals(taskType)) {
            sql += " and " + (map.get(taskType).split("where"))[1];
        }
        if (year != null) {
            sql += " and YEAR(start_date)  = " + year;
        }
        if (month != null) {
            sql += " and MONTH(start_date) = " + month;
        }
        if (!"".equals(regionCode)) {
            sql += " and region_code = " + regionCode;
        }
        if (recordTypeId != null) {
            sql += " and record_type_id = " + recordTypeId;
        }
        if (!"".equals(departmentCode)) {
            sql += " and department_code = " + departmentCode;
        }
        if (!"".equals(workGroupCode)) {
            sql += " and group_code = " + workGroupCode;
        }
        sql += "  order by create_time desc " ;
        return sql;
    }
//    /**
//     *
//     * @param year
//     * @param month
//     * @param regionCode
//     * @param recordTypeId
//     * @param departmentCode
//     * @param workGroupCode
//     * @return
//     */
//    public String taskInfoWithWorkloads(Integer year, Integer month, String regionCode, Integer recordTypeId, String departmentCode, String workGroupCode,String taskType) {
//        String sql = "select p.id as id, " +
//                " p.name as name, " +
//                " p.unit_name as unitName, " +
//                " p.address as address, " +
//                " p.longitude as longitude, " +
//                " p.latitude as latitude, " +
//                " r.name as regionName, " +
//                " d.alias as recordTypeName, " +
//                " w.work_count as workload " +
//                " from by_plus_project p " +
//                " inner join by_plus_task t on p.id = t.project_id " +
//                " inner join by_b_region r on p.region_code = r.code " +
//                " inner join by_b_dictionary_child d on p.record_type_id = d.id " +
//                " inner join (select sum(work_count) as work_count,id ,task_id, internal_work_output, temp_internal_val from by_plus_task_workload GROUP BY task_id) w on t.id = w.task_id " +
//                " where 1 = 1 ";
//        //sql += " and " + (map.get(taskType).split("where"))[1];
//        if (year != null) {
//            sql += " and YEAR(p.start_date)  = " + year;
//        }
//        if (month != null) {
//            sql += " and MONTH(p.start_date) = " + month;
//        }
//        if (!"".equals(regionCode)) {
//            sql += " and p.region_code = " + regionCode;
//        }
//        if (recordTypeId != null) {
//            sql += " and p.record_type_id = " + recordTypeId;
//        }
//        if (!"".equals(departmentCode)) {
//            sql += " and t.department_code = " + departmentCode;
//        }
//        if (!"".equals(workGroupCode)) {
//            sql += " and t.group_code = " + workGroupCode;
//        }
//        sql += "  order by p.create_time desc " ;
//        return sql;
//    }

    /**
     *
     * @param year
     * @param month
     * @param regionCode
     * @param recordTypeId
     * @return
     */
    public String taskGroupByParam(String groupByParam,Integer year, Integer month, String regionCode, Integer recordTypeId) {
        String sql = "select  COUNT(*) as count,IFNULL(sum(internal_output),0.0) as internal_output, " +
                " " +map.get(groupByParam)+" as param " +
                " from v_statistics where 1 = 1 ";
        if (year != null) {
            sql += " and YEAR(start_date)  = " + year;
        }
        if (month != null) {
            sql += " and MONTH(start_date) = " + month;
        }
        if (!"".equals(regionCode)) {
            sql += " and region_code = " + regionCode;
        }
        if (recordTypeId != null) {
            sql += " and record_type_id = " + recordTypeId;
        }
        sql += " group by " + map.get(groupByParam);
        return sql;
    }
}