package club.emergency.by_b_department.mapper.sqlprovider;

import club.map.core.mapper.SqlProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ByBDepartmentProvider extends SqlProvider {
    protected final Log log = LogFactory.getLog(getClass());

    public String getList(Integer releaseFlag, String departmentCode) {
        String sql = "select distinct id as id,department_name as departmentName,code as code " +
                "from by_b_department " +
                "where using_type = 1";
        if (releaseFlag != null) {
            sql += " and release_flag = " + releaseFlag;
        }
        if (!"".equals(departmentCode)) {
            sql += " and code = " + departmentCode;
        }
        return sql;
    }
}