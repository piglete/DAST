package club.emergency.project.mapper.sqlprovider;

import club.emergency.project.model.VProjectTask;
import club.map.core.mapper.SqlProvider;
import club.map.core.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class VProjectTaskProvider extends SqlProvider {
    protected final Log log = LogFactory.getLog(getClass());
    public String search(VProjectTask vProjectTask, Integer year, Integer month) {
        StringBuffer sb = new StringBuffer(256);
        sb.append("select * from ");
        sb.append("v_project_task p").append(" where  p.using_type = 1 and p.task_using_type = 1");
        if (!StringUtil.isNull(vProjectTask.getUnitName())) {
            sb.append(" and p.unit_name like '").append(vProjectTask.getUnitName()).append("%'");
        }
        if (!StringUtil.isNull(vProjectTask.getDepartmentCode())) {
            sb.append(" and p.department_code = ").append(vProjectTask.getDepartmentCode());
        }
        if (!StringUtil.isNull(vProjectTask.getWorkGroupCode())) {
            sb.append(" and p.work_group_code like ").append("'%").append(vProjectTask.getWorkGroupCode()).append("%'");
        }
        if (year!=null) {
            sb.append(" and YEAR(start_date) = ").append(year);
        }
        if (month!=null) {
            sb.append(" and MONTH(start_date) = ").append(month);
        }
        if (!StringUtil.isNull(vProjectTask.getRegionCode())) {
            sb.append(" and p.region_code like ").append("'%").append(vProjectTask.getRegionCode()).append("%'");
        }
        if (vProjectTask.getRecordTypeId() != null) {
            sb.append(" and p.record_type_id = ").append(vProjectTask.getRecordTypeId());
        }
//        if (vProjectTask.getProjectStateId() != null) {
//            sb.append(" and p.project_state_id = ").append(vProjectTask.getProjectStateId());
//        }

        sb.append(" order by p.create_time desc");
        String sql = sb.toString();
        log.debug(sql);
        return sql;
    }
}