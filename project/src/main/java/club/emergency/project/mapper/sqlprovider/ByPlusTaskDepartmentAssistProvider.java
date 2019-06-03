package club.emergency.project.mapper.sqlprovider;

import club.map.core.mapper.SqlProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ByPlusTaskDepartmentAssistProvider extends SqlProvider {
    protected final Log log = LogFactory.getLog(getClass());

    public String getList(String departmentCode, Integer employeeId, String workGroupCode, Integer workGroupEmployeeId, Integer oneInspectionUserId) {
        String sql = "select distinct task_id as taskId " +
                "from by_plus_task_department_assist " +
                "where using_type = 1";
        if (!"".equals(departmentCode)) {
            sql += " and department_code = " + departmentCode;
        }
        if (employeeId != null) {
            sql += " and revert_user_id = " + employeeId;
        }
        if (!"".equals(workGroupCode)) {
            sql += " and group_code = " + workGroupCode;
        }
        if (workGroupEmployeeId != null) {
            sql += " and group_revert_user_id = " + workGroupEmployeeId;
        }
        if (oneInspectionUserId != null) {
            sql += " and one_inspection_user_id = " + oneInspectionUserId;
        }
        return sql;
    }
}