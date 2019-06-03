package club.emergency.project.mapper.sqlprovider;

import club.map.core.mapper.SqlProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ByPlusTaskWorkloadProvider extends SqlProvider {
    protected final Log log = LogFactory.getLog(getClass());

    public String getTaskWorkloadList(Integer taskId, String departmentCode, String groupCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId, Integer itemApplicationId, Integer flag, String startDate, String endDate) {
        StringBuffer sb = new StringBuffer(256);
        sb.append("select distinct w.id as id,w.task_id as taskId,w.work_count as workCount,w.item_application_id as itemApplicationId,w.unit_type_id as unitTypeId,w.department_code as departmentCode,w.remark as remark,w.flag as flag,w.create_time as createTime ").append("from ");
        sb.append("by_plus_task_workload w,by_plus_task t ");
        sb.append("where w.task_id = t.id and t.flag = 2 and w.using_type = 1 and t.using_type = 1 ");
        if (taskId != null) {
            sb.append("and w.task_id = ").append(taskId).append(" ");
        }
        if (itemApplicationId != null) {
            sb.append("and w.item_application_id = ").append(itemApplicationId).append(" ");
        }
        if (flag != null) {
            sb.append("and w.flag = ").append(flag).append(" ");
        }
        if (!"".equals(departmentCode)) {
            sb.append("and t.department_code = ").append(departmentCode).append(" ");
        }
        if (!"".equals(groupCode)) {
            sb.append("and t.work_group_code = ").append(groupCode).append(" ");
        }
        if (oneInspectionUserId != null) {
            sb.append("and t.one_inspection_user_id = ").append(oneInspectionUserId).append(" ");
        }
        if (twoInspectionUserId != null) {
            sb.append("and t.two_inspection_user_id = ").append(twoInspectionUserId).append(" ");
        }
        if (threeInspectionUserId != null) {
            sb.append("and t.three_inspection_user_id = ").append(threeInspectionUserId).append(" ");
        }
        if (!"".equals(startDate) && !"".equals(endDate)) {
            sb.append("and t.three_check_date between '").append(startDate).append("' and '").append(endDate).append("' ");
        }
        String sql = sb.toString();
        log.debug(sql);
        return sql;
    }
}