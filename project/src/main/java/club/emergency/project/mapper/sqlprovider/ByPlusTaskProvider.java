package club.emergency.project.mapper.sqlprovider;

import club.map.core.mapper.SqlProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ByPlusTaskProvider extends SqlProvider {
    protected final Log log = LogFactory.getLog(getClass());

    /**
     * 该方法只做了两张表关联
     *
     * @param name
     * @param recordTypeId
     * @param unitName
     * @param linkman
     * @param telephone
     * @param projectPeriod
     * @param regionCode
     * @param startDate
     * @param endDate
     * @param departmentCode
     * @param taskStateId
     * @param employeeId
     * @param workGroupEmployeeId
     * @param oneInspectionUserId
     * @param twoInspectionUserId
     * @param threeInspectionUserId
     * @param recordInspectionUserId
     * @param outCheckUserId
     * @param outCheckState
     * @param workGroupCode
     * @param flag
     * @param invalidFlag
     * @param assistFlag
     * @return
     */
    public String getProjectAndTaskInfo(String name, Integer recordTypeId, String unitName, String linkman, String telephone, Integer projectPeriod, String regionCode, String startDate, String endDate, String departmentCode, Integer taskStateId, Integer employeeId, Integer workGroupEmployeeId, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId, Integer recordInspectionUserId, Integer outCheckUserId, Integer outCheckState, String workGroupCode, Integer flag, Integer invalidFlag, Integer assistFlag) {
        StringBuffer sb = new StringBuffer(256);
        sb.append("select distinct t.id as id,t.project_id as projectId").append(" from ");
        sb.append("by_plus_project p,by_plus_task t").append(" where p.id = t.project_id and p.using_type = 1 and t.using_type = 1");
        if (!"".equals(name)) {
            sb.append(" and p.name like ").append("'%").append(name).append("%'");
        }
        if (recordTypeId != null) {
            sb.append(" and p.record_type_id = ").append(recordTypeId);
        }
        if (!"".equals(unitName)) {
            sb.append(" and p.unit_name like ").append("'%").append(name).append("%'");
        }
        if (!"".equals(linkman)) {
            sb.append(" and p.linkman like ").append("'%").append(name).append("%'");
        }
        if (!"".equals(telephone)) {
            sb.append(" and p.telephone like ").append("'%").append(name).append("%'");
        }
        if (projectPeriod != null) {
            sb.append(" and p.project_period = ").append(recordTypeId);
        }
        if (!"".equals(regionCode)) {
            sb.append(" and p.region_code like ").append("'").append(regionCode).append("%'");
        }
        if (!"".equals(startDate) && !"".equals(endDate)) {
            sb.append(" and p.start_date between ").append(startDate).append(" and ").append(endDate);
        }
        if (!"".equals(departmentCode)) {
            sb.append(" and t.department_code like ").append("'").append(departmentCode).append("%'");
        }
        if (taskStateId != null) {
            sb.append(" and t.task_state_id = ").append(taskStateId);
        }
        if (employeeId != null) {
            sb.append(" and t.employee_id = ").append(employeeId);
        }
        if (workGroupEmployeeId != null) {
            sb.append(" and t.work_group_employee_id = ").append(workGroupEmployeeId);
        }
        if (oneInspectionUserId != null) {
            sb.append(" and t.one_inspection_user_id = ").append(oneInspectionUserId);
        }
        if (twoInspectionUserId != null) {
            sb.append(" and t.two_inspection_user_id = ").append(twoInspectionUserId);
        }
        if (threeInspectionUserId != null) {
            sb.append(" and t.three_inspection_user_id = ").append(threeInspectionUserId);
        }
        if (recordInspectionUserId != null) {
            sb.append(" and t.record_inspection_user_id = ").append(recordInspectionUserId);
        }
        if (outCheckUserId != null) {
            sb.append(" and t.out_check_user_id = ").append(outCheckUserId);
        }
        if (outCheckState != null) {
            sb.append(" and t.out_check_state = ").append(outCheckState);
        }
        if (!"".equals(workGroupCode)) {
            sb.append(" and t.work_group_code like ").append("'").append(workGroupCode).append("%'");
        }
        if (flag != null) {
            sb.append(" and t.flag = ").append(flag);
        }
        if (invalidFlag != null) {
            sb.append(" and t.invalid_flag = ").append(invalidFlag);
        }
        if (assistFlag != null) {
            sb.append(" and t.assist_flag = ").append(assistFlag);
        }
        sb.append(" order by t.create_time desc");
        String sql = sb.toString();
        log.debug(sql);
        return sql;
    }
}