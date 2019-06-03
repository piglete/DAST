package club.emergency.project.mapper.sqlprovider;

import club.map.core.mapper.SqlProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ByPlusProjectProvider extends SqlProvider {
    protected final Log log = LogFactory.getLog(getClass());

    public String searchFinishAllProjectCount(String startDate, String endDate, String workGroupCode, String departmentCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId) {
        StringBuffer sb = new StringBuffer(256);
        sb.append("select count(*) ").append("from ");
        sb.append("by_plus_project p,by_plus_task t ");
        sb.append("where p.id = t.project_id and p.project_state_id = 78 and p.using_type = 1 and t.using_type = 1 ");
        if (!"".equals(startDate) && !"".equals(endDate)) {
            sb.append("and p.start_date between '").append(startDate).append("' and '").append(endDate).append("' ");
        }
        if (!"".equals(workGroupCode)) {
            sb.append("and t.work_group_code = ").append(workGroupCode).append(" ");
        }
        if (!"".equals(departmentCode)) {
            sb.append("and t.department_code = ").append(departmentCode).append(" ");
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
        String sql = sb.toString();
        log.debug(sql);
        return sql;
    }

    public String getNowMonthFinishProject(String startDate, String endDate, String workGroupCode, String departmentCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId) {
        StringBuffer sb = new StringBuffer(256);
        sb.append("select count(*) ").append("from ");
        sb.append("by_plus_project p,by_plus_task t ");
        sb.append("where p.id = t.project_id and p.project_state_id = 78 and p.using_type = 1 and t.using_type = 1 ");
        if (!"".equals(startDate) && !"".equals(endDate)) {
            sb.append("and p.start_date between '").append(startDate).append("' and '").append(endDate).append("' ");
        }
        if (!"".equals(workGroupCode)) {
            sb.append("and t.work_group_code = ").append(workGroupCode).append(" ");
        }
        if (!"".equals(departmentCode)) {
            sb.append("and t.department_code = ").append(departmentCode).append(" ");
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
        String sql = sb.toString();
        log.debug(sql);
        return sql;
    }

    public String getTaskScore(String startDate, String endDate, String workGroupCode, String departmentCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId) {
        StringBuffer sb = new StringBuffer(256);
        sb.append("select t.project_id as projectId,t.task_score as taskScore ").append("from ");
        sb.append("by_plus_project p,by_plus_task t ");
        sb.append("where p.id = t.project_id and p.project_state_id = 78 and p.using_type = 1 and t.using_type = 1 ");
        if (!"".equals(startDate) && !"".equals(endDate)) {
            sb.append("and p.start_date between '").append(startDate).append("' and '").append(endDate).append("' ");
        }
        if (!"".equals(workGroupCode)) {
            sb.append("and t.work_group_code = ").append(workGroupCode).append(" ");
        }
        if (!"".equals(departmentCode)) {
            sb.append("and t.department_code = ").append(departmentCode).append(" ");
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
        String sql = sb.toString();
        log.debug(sql);
        return sql;
    }

    public String getAverageTaskScore(String startDate, String endDate, String workGroupCode, String departmentCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId) {
        StringBuffer sb = new StringBuffer(256);
        sb.append("select round(avg(t.task_score),2) ").append("from ");
        sb.append("by_plus_project p,by_plus_task t ");
        sb.append("where p.id = t.project_id and p.project_state_id = 78 and p.using_type = 1 and t.using_type = 1 ");
        if (!"".equals(startDate) && !"".equals(endDate)) {
            sb.append("and p.start_date between '").append(startDate).append("' and '").append(endDate).append("' ");
        }
        if (!"".equals(workGroupCode)) {
            sb.append("and t.work_group_code = ").append(workGroupCode).append(" ");
        }
        if (!"".equals(departmentCode)) {
            sb.append("and t.department_code = ").append(departmentCode).append(" ");
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
        String sql = sb.toString();
        log.debug(sql);
        return sql;
    }

    public String getOverdue(String startDate, String endDate, String workGroupCode, String departmentCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId) {
        StringBuffer sb = new StringBuffer(256);
        sb.append("select count(*) ").append("from ");
        sb.append("by_plus_project p,by_plus_task t ");
        sb.append("where p.id = t.project_id and p.project_state_id = 78 and p.using_type = 1 and t.using_type = 1 ");
        if (!"".equals(startDate) && !"".equals(endDate)) {
            sb.append("and p.start_date between '").append(startDate).append("' and '").append(endDate).append("' ");
        }
        if (!"".equals(workGroupCode)) {
            sb.append("and t.work_group_code = ").append(workGroupCode).append(" ");
        }
        if (!"".equals(departmentCode)) {
            sb.append("and t.department_code = ").append(departmentCode).append(" ");
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
        sb.append("and t.over_date_count > 0");
        String sql = sb.toString();
        log.debug(sql);
        return sql;
    }

    public String getOverdueRecordTypeList(String startDate, String endDate, String workGroupCode, String departmentCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId) {
        StringBuffer sb = new StringBuffer(256);
        sb.append("select count(p.record_type_id) as count,p.record_type_id as recordTypeId ").append("from ");
        sb.append("by_plus_project p,by_plus_task t ");
        sb.append("where p.id = t.project_id and p.project_state_id = 78 and p.using_type = 1 and t.using_type = 1 ");
        if (!"".equals(startDate) && !"".equals(endDate)) {
            sb.append("and p.start_date between '").append(startDate).append("' and '").append(endDate).append("' ");
        }
        if (!"".equals(workGroupCode)) {
            sb.append("and t.work_group_code = ").append(workGroupCode).append(" ");
        }
        if (!"".equals(departmentCode)) {
            sb.append("and t.department_code = ").append(departmentCode).append(" ");
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
        sb.append("and t.over_date_count > 0 ");
        sb.append("group by p.record_type_id");
        String sql = sb.toString();
        log.debug(sql);
        return sql;
    }

    public String getRecordTypeAverageScoreList(String startDate, String endDate, String workGroupCode, String departmentCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId) {
        StringBuffer sb = new StringBuffer(256);
        sb.append("select round(avg(t.task_score),2) as taskScore,p.record_type_id as recordTypeId ").append("from ");
        sb.append("by_plus_project p,by_plus_task t ");
        sb.append("where p.id = t.project_id and p.project_state_id = 78 and p.using_type = 1 and t.using_type = 1 ");
        if (!"".equals(startDate) && !"".equals(endDate)) {
            sb.append("and p.start_date between '").append(startDate).append("' and '").append(endDate).append("' ");
        }
        if (!"".equals(workGroupCode)) {
            sb.append("and t.work_group_code = ").append(workGroupCode).append(" ");
        }
        if (!"".equals(departmentCode)) {
            sb.append("and t.department_code = ").append(departmentCode).append(" ");
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
        sb.append("group by p.record_type_id");
        String sql = sb.toString();
        log.debug(sql);
        return sql;
    }

    public String getOverdueRegionList(String startDate, String endDate, String workGroupCode, String departmentCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId) {
        StringBuffer sb = new StringBuffer(256);
        sb.append("select count(p.region_code) as count,p.region_code as regionCode ").append("from ");
        sb.append("by_plus_project p,by_plus_task t ");
        sb.append("where p.id = t.project_id and p.project_state_id = 78 and p.using_type = 1 and t.using_type = 1 ");
        if (!"".equals(startDate) && !"".equals(endDate)) {
            sb.append("and p.start_date between '").append(startDate).append("' and '").append(endDate).append("' ");
        }
        if (!"".equals(workGroupCode)) {
            sb.append("and t.work_group_code = ").append(workGroupCode).append(" ");
        }
        if (!"".equals(departmentCode)) {
            sb.append("and t.department_code = ").append(departmentCode).append(" ");
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
        sb.append("and t.over_date_count > 0 ");
        sb.append("group by p.region_code");
        String sql = sb.toString();
        log.debug(sql);
        return sql;
    }

    public String getRegionCountList(String startDate, String endDate, String workGroupCode, String departmentCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId) {
        StringBuffer sb = new StringBuffer(256);
        sb.append("select count(p.region_code) as count,p.region_code as regionCode ").append("from ");
        sb.append("by_plus_project p,by_plus_task t ");
        sb.append("where p.id = t.project_id and p.project_state_id = 78 and p.using_type = 1 and t.using_type = 1 ");
        if (!"".equals(startDate) && !"".equals(endDate)) {
            sb.append("and p.start_date between '").append(startDate).append("' and '").append(endDate).append("' ");
        }
        if (!"".equals(workGroupCode)) {
            sb.append("and t.work_group_code = ").append(workGroupCode).append(" ");
        }
        if (!"".equals(departmentCode)) {
            sb.append("and t.department_code = ").append(departmentCode).append(" ");
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
        sb.append("group by p.region_code");
        String sql = sb.toString();
        log.debug(sql);
        return sql;
    }

    public String getRecordTypeCountList(String startDate, String endDate, String workGroupCode, String departmentCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId) {
        StringBuffer sb = new StringBuffer(256);
        sb.append("select count(p.record_type_id) as count,p.record_type_id as recordTypeId ").append("from ");
        sb.append("by_plus_project p,by_plus_task t ");
        sb.append("where p.id = t.project_id and p.project_state_id = 78 and p.using_type = 1 and t.using_type = 1 ");
        if (!"".equals(startDate) && !"".equals(endDate)) {
            sb.append("and p.start_date between '").append(startDate).append("' and '").append(endDate).append("' ");
        }
        if (!"".equals(workGroupCode)) {
            sb.append("and t.work_group_code = ").append(workGroupCode).append(" ");
        }
        if (!"".equals(departmentCode)) {
            sb.append("and t.department_code = ").append(departmentCode).append(" ");
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
        sb.append("group by p.record_type_id");
        String sql = sb.toString();
        log.debug(sql);
        return sql;
    }

    public String getRegionAverageScoreList(String startDate, String endDate, String workGroupCode, String departmentCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId) {
        StringBuffer sb = new StringBuffer(256);
        sb.append("select round(avg(t.task_score),2) as taskScore,p.region_code as regionCode ").append("from ");
        sb.append("by_plus_project p,by_plus_task t ");
        sb.append("where p.id = t.project_id and p.project_state_id = 78 and p.using_type = 1 and t.using_type = 1 ");
        if (!"".equals(startDate) && !"".equals(endDate)) {
            sb.append("and p.start_date between '").append(startDate).append("' and '").append(endDate).append("' ");
        }
        if (!"".equals(workGroupCode)) {
            sb.append("and t.work_group_code = ").append(workGroupCode).append(" ");
        }
        if (!"".equals(departmentCode)) {
            sb.append("and t.department_code = ").append(departmentCode).append(" ");
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
        sb.append("group by p.region_code");
        String sql = sb.toString();
        log.debug(sql);
        return sql;
    }

    public String getTaskOverdueList(String startDate, String endDate, String workGroupCode, String departmentCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId) {
        StringBuffer sb = new StringBuffer(256);
        sb.append("select t.over_date_count as overDateCount ").append("from ");
        sb.append("by_plus_project p,by_plus_task t ");
        sb.append("where p.id = t.project_id and p.project_state_id = 78 and p.using_type = 1 and t.using_type = 1 ");
        if (!"".equals(startDate) && !"".equals(endDate)) {
            sb.append("and p.start_date between '").append(startDate).append("' and '").append(endDate).append("' ");
        }
        if (!"".equals(workGroupCode)) {
            sb.append("and t.work_group_code = ").append(workGroupCode).append(" ");
        }
        if (!"".equals(departmentCode)) {
            sb.append("and t.department_code = ").append(departmentCode).append(" ");
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
        sb.append("and t.over_date_count > 0 ");
        String sql = sb.toString();
        log.debug(sql);
        return sql;
    }

    public String getMonthTaskCount(String regionCode, Integer recordTypeId, String startDate, String endDate) {
        StringBuffer sb = new StringBuffer(256);
        sb.append("select count(*) ").append("from ");
        sb.append("by_plus_project p,by_plus_task t ");
        sb.append("where p.id = t.project_id and p.project_state_id = 78 and p.using_type = 1 and t.using_type = 1 ");
        if (!"".equals(regionCode)) {
            sb.append("and p.region_code = ").append(regionCode).append(" ");
        }
        if (recordTypeId != null) {
            sb.append("and p.record_type_id = ").append(recordTypeId).append(" ");
        }
        if (!"".equals(startDate) && !"".equals(endDate)) {
            sb.append("and p.start_date between '").append(startDate).append("' and '").append(endDate).append("'");
        }
        String sql = sb.toString();
        log.debug(sql);
        return sql;
    }

    public String getProjectList(String startDate, String endDate) {
        StringBuffer sb = new StringBuffer(256);
        sb.append("select distinct p.id as id,p.name as name,p.region_code as regionCode,p.record_type_id as recordTypeId,p.start_date as startDate,p.finish_date as finishDate,p.project_period as projectPeriod,t.task_score as taskScore,t.over_date_count as overDateCount ");
        sb.append("from ");
        sb.append("by_plus_project p,by_plus_task t ");
        sb.append("where p.id = t.project_id and p.project_state_id = 78 and p.using_type = 1 and t.using_type = 1 ");
        if (!"".equals(startDate) && !"".equals(endDate)) {
            sb.append("and p.start_date between '").append(startDate).append("' and '").append(endDate).append("' ");
        }
        sb.append("order by p.create_time desc");
        String sql = sb.toString();
        log.debug(sql);
        return sql;
    }

    public String getRecordTypeAndRegionCountList(String startDate, String endDate, String regionCode, String workGroupCode, String departmentCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId) {
        StringBuffer sb = new StringBuffer(256);
        sb.append("select count(p.record_type_id) as count,p.record_type_id as recordTypeId ").append("from ");
        sb.append("by_plus_project p,by_plus_task t ");
        sb.append("where p.id = t.project_id and p.project_state_id = 78 and p.using_type = 1 and t.using_type = 1 ");
        if (!"".equals(startDate) && !"".equals(endDate)) {
            sb.append("and p.start_date between '").append(startDate).append("' and '").append(endDate).append("' ");
        }
        if (!"".equals(regionCode)) {
            sb.append("and p.region_code = ").append(regionCode).append(" ");
        }
        if (!"".equals(workGroupCode)) {
            sb.append("and t.work_group_code = ").append(workGroupCode).append(" ");
        }
        if (!"".equals(departmentCode)) {
            sb.append("and t.department_code = ").append(departmentCode).append(" ");
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
        sb.append("group by p.record_type_id");
        String sql = sb.toString();
        log.debug(sql);
        return sql;
    }

    public String getRegionAndRecordTypeCountList(String startDate, String endDate, Integer recordTypeId, String workGroupCode, String departmentCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId) {
        StringBuffer sb = new StringBuffer(256);
        sb.append("select count(p.region_code) as count,p.region_code as regionCode ").append("from ");
        sb.append("by_plus_project p,by_plus_task t ");
        sb.append("where p.id = t.project_id and p.project_state_id = 78 and p.using_type = 1 and t.using_type = 1 ");
        if (!"".equals(startDate) && !"".equals(endDate)) {
            sb.append("and p.start_date between '").append(startDate).append("' and '").append(endDate).append("' ");
        }
        if (recordTypeId != null) {
            sb.append("and p.record_type_id = ").append(recordTypeId).append(" ");
        }
        if (!"".equals(workGroupCode)) {
            sb.append("and t.work_group_code = ").append(workGroupCode).append(" ");
        }
        if (!"".equals(departmentCode)) {
            sb.append("and t.department_code = ").append(departmentCode).append(" ");
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
        sb.append("group by p.region_code");
        String sql = sb.toString();
        log.debug(sql);
        return sql;
    }

    public String getTaskCountByGroupList(String startDate, String endDate) {
        StringBuffer sb = new StringBuffer(256);
        sb.append("select count(*) as count,t.work_group_code as groupCode ").append("from ");
        sb.append("by_plus_project p,by_plus_task t ");
        sb.append("where p.id = t.project_id and p.project_state_id = 78 and p.using_type = 1 and t.using_type = 1 ");
        if (!"".equals(startDate) && !"".equals(endDate)) {
            sb.append("and p.start_date between '").append(startDate).append("' and '").append(endDate).append("' ");
        }
        sb.append("group by t.work_group_code");
        String sql = sb.toString();
        log.debug(sql);
        return sql;
    }

    public String getTaskScoreByGroupList(String startDate, String endDate) {
        StringBuffer sb = new StringBuffer(256);
        sb.append("select avg(t.task_score) as taskScore,t.work_group_code as groupCode ").append("from ");
        sb.append("by_plus_project p,by_plus_task t ");
        sb.append("where p.id = t.project_id and p.project_state_id = 78 and p.using_type = 1 and t.using_type = 1 ");
        if (!"".equals(startDate) && !"".equals(endDate)) {
            sb.append("and p.start_date between '").append(startDate).append("' and '").append(endDate).append("' ");
        }
        sb.append("group by t.work_group_code");
        String sql = sb.toString();
        log.debug(sql);
        return sql;
    }

    public String getTaskOverdueByGroupList(String startDate, String endDate) {
        StringBuffer sb = new StringBuffer(256);
        sb.append("select count(*) as count,t.work_group_code as groupCode ").append("from ");
        sb.append("by_plus_project p,by_plus_task t ");
        sb.append("where p.id = t.project_id and p.project_state_id = 78 and p.using_type = 1 and t.using_type = 1 ");
        if (!"".equals(startDate) && !"".equals(endDate)) {
            sb.append("and p.start_date between '").append(startDate).append("' and '").append(endDate).append("' ");
        }
        sb.append("and t.over_date_count > 0 ");
        sb.append("group by t.work_group_code");
        String sql = sb.toString();
        log.debug(sql);
        return sql;
    }

    public String getTaskPeriodByGroupList(String startDate, String endDate) {
        StringBuffer sb = new StringBuffer(256);
        sb.append("select sum(p.project_period) as count,t.work_group_code as groupCode ").append("from ");
        sb.append("by_plus_project p,by_plus_task t ");
        sb.append("where p.id = t.project_id and p.project_state_id = 78 and p.using_type = 1 and t.using_type = 1 ");
        if (!"".equals(startDate) && !"".equals(endDate)) {
            sb.append("and p.start_date between '").append(startDate).append("' and '").append(endDate).append("' ");
        }
        sb.append("and t.over_date_count > 0 ");
        sb.append("group by t.work_group_code");
        String sql = sb.toString();
        log.debug(sql);
        return sql;
    }

    public String getLessScore(String startDate, String endDate, String workGroupCode, String departmentCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId) {
        StringBuffer sb = new StringBuffer(256);
        sb.append("select count(*) ").append("from ");
        sb.append("by_plus_project p,by_plus_task t ");
        sb.append("where p.id = t.project_id and p.project_state_id = 78 and p.using_type = 1 and t.using_type = 1 ");
        if (!"".equals(startDate) && !"".equals(endDate)) {
            sb.append("and p.start_date between '").append(startDate).append("' and '").append(endDate).append("' ");
        }
        if (!"".equals(workGroupCode)) {
            sb.append("and t.work_group_code = ").append(workGroupCode).append(" ");
        }
        if (!"".equals(departmentCode)) {
            sb.append("and t.department_code = ").append(departmentCode).append(" ");
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
        sb.append("and t.task_score < 60 ");
        String sql = sb.toString();
        log.debug(sql);
        return sql;
    }

    /**
     * 查询已入档项目全部信息，where 后的条件project_state_id字段值为79 ,代表已入档
     * 四张表关联查询
     *
     * @param name
     * @param startTime
     * @param endTime
     * @param unitName
     * @param linkman
     * @param telephone
     * @param regionCode
     * @param recordTypeId
     * @param fileNumber
     * @param dataNumber
     * @param yearType
     * @param monthType
     * @return
     */
    public String getProjectInformationList(String name, String startTime, String endTime, String unitName, String linkman, String telephone, String regionCode, Integer recordTypeId, String fileNumber, String dataNumber, String departmentCode, String groupCode) {
        StringBuffer sb = new StringBuffer(256);
        sb.append("select distinct p.id as id,p.name as name,p.longitude as longitude,p.latitude as latitude,e.full_name as regionName ");
        sb.append("from by_plus_project p,by_r_record r, by_plus_task t,by_b_region e ");
        sb.append("where p.id = r.project_id and p.id = t.project_id and p.region_code = e.code and p.project_state_id = 78 ");
        sb.append("and p.using_type = 1 and r.using_type = 1 and t.using_type = 1 ");
        if (!"".equals(name)) {
            sb.append("and p.name like ").append("'%").append(name).append("%' ");
        }
        if (!"".equals(startTime) && !"".equals(endTime)) {
            sb.append("and p.start_date between '").append(startTime).append("' and '").append(endTime).append("' ");
        }
        if (!"".equals(unitName)) {
            sb.append("and p.unit_name like ").append("'%").append(unitName).append("%' ");
        }
        if (!"".equals(linkman)) {
            sb.append("and p.linkman like ").append("'%").append(linkman).append("%' ");
        }
        if (!"".equals(telephone)) {
            sb.append("and p.linkman like ").append("'%").append(telephone).append("%' ");
        }
        if (!"".equals(regionCode)) {
            sb.append("and p.region_code like ").append("'").append(regionCode).append("%' ");
        }
        if (recordTypeId != null) {
            sb.append("and p.record_type_id = ").append(recordTypeId).append(" ");
        }
        if (!"".equals(fileNumber)) {
            sb.append("and t.file_number like ").append("'%").append(fileNumber).append("%' ");
        }
        if (!"".equals(departmentCode)) {
            sb.append("and t.department_code like ").append("'").append(departmentCode).append("%' ");
        }
        if (!"".equals(fileNumber)) {
            sb.append("and r.group_code like ").append("'").append(fileNumber).append("%' ");
        }
        if (!"".equals(dataNumber)) {
            sb.append("and r.file_number like ").append("'%").append(dataNumber).append("%' ");
        }
        sb.append("order by p.create_time desc");
        String sql = sb.toString();
        log.debug(sql);
        return sql;
    }

    public String getDistinctRecordTypeId(String startDate, String endDate) {
        StringBuffer sb = new StringBuffer(256);
        sb.append("select count(distinct p.record_type_id) from by_plus_project p where p.using_type = 1 ");
        if (!"".equals(startDate) && !"".equals(endDate)) {
            sb.append("and p.start_date between '").append(startDate).append("' and '").append(endDate).append("' ");
        }
        String sql = sb.toString();
        log.debug(sql);
        return sql;
    }

    public String getDistinctRegionCode(String startDate, String endDate) {
        StringBuffer sb = new StringBuffer(256);
        sb.append("select count(distinct p.region_code) from by_plus_project p where p.using_type = 1 ");
        if (!"".equals(startDate) && !"".equals(endDate)) {
            sb.append("and p.start_date between '").append(startDate).append("' and '").append(endDate).append("' ");
        }
        String sql = sb.toString();
        log.debug(sql);
        return sql;
    }

    /**
     * 项目表,任务表关联查询
     *
     * @return
     */
    public String getProjectCollectInformationList(String name, String regionCode, Integer recordTypeId, String unitName, String linkman, String telephone, String score, Integer overdueFlag, String dayCount, String startDate, String endDate, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId, Integer startPage, Integer pageSize, String departmentCode, String workGroupCode, Integer recordOrRegionOverdueFlag) {
        StringBuffer sb = new StringBuffer(256);
        sb.append("select p.id as id,p.name as name,p.record_type_id as recordTypeId,p.region_code as regionCode," +
                "p.start_date as startDate,p.unit_name as unitName,p.linkman as linkman,p.telephone as telephone," +
                "t.work_group_code as taskGroupCode from ");
        sb.append("by_plus_project p,by_plus_task t where p.id = t.project_id and p.project_state_id = 78 and p.using_type = 1 and t.using_type = 1 ");
        if (!"".equals(name)) {
            sb.append("and p.name like ").append("'%").append(name).append("%' ");
        }
        if (!"".equals(regionCode)) {
            sb.append("and p.region_code = ").append(regionCode).append(" ");
        }
        if (recordTypeId != null) {
            sb.append("and p.record_type_id = ").append(recordTypeId).append(" ");
        }
        if (!"".equals(unitName)) {
            sb.append("and p.unit_name like ").append("'%").append(unitName).append("%' ");
        }
        if (!"".equals(linkman)) {
            sb.append("and p.linkman like ").append("'%").append(linkman).append("%' ");
        }
        if (!"".equals(telephone)) {
            sb.append("and p.telephone like ").append("'%").append(telephone).append("%' ");
        }
        //score分数处理:判断该分数是一个分数还是区间,然后分别处理,只有100是具体分数,其他均为区间
        if (!"".equals(score)) {
            boolean flag = score.contains("-");
            if (flag) {
                String[] strScore = score.split("-");
                Double startScore = Double.valueOf(strScore[0]);
                Double endScore = Double.valueOf(strScore[1]);
                sb.append("and t.task_score >= ").append(startScore).append(" and ").append("t.task_score < ").append(endScore).append(" ");
            } else {
                Double singleScore = Double.valueOf(score);
                sb.append("and t.task_score = ").append(singleScore).append(" ");
            }
        }
        //超期天数处理
        if (overdueFlag == 1) {
            //处理1-6天,6-8天,8-10天之类的
            boolean flag = dayCount.contains("-");
            if (flag) {
                String[] strOverduoDay = dayCount.split("-");
                Integer startDayCount = Integer.valueOf(strOverduoDay[0]);
                String endStr = strOverduoDay[1].substring(0, strOverduoDay[1].length() - 1);
                Integer endDayCount = Integer.valueOf(endStr);
                sb.append("and t.over_date_count > ").append(startDayCount).append(" and ").append("t.over_date_count <= ").append(endDayCount).append(" ");
                //处理1天,10天以上
            } else {
                if (dayCount.length() <= 2) {
                    Integer strDayCount = Integer.valueOf(dayCount.substring(0, 1));
                    sb.append("and t.over_date_count = ").append(strDayCount).append(" ");
                } else {
                    Integer skrDayCount = Integer.valueOf(dayCount.substring(0, dayCount.length() - 3));
                    sb.append("and t.over_date_count > ").append(skrDayCount).append(" ");
                }
            }
        }
        if (!"".equals(startDate) && !"".equals(endDate)) {
            sb.append("and p.start_date between '").append(startDate).append("' and '").append(endDate).append("' ");
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
        if (!"".equals(workGroupCode)) {
            sb.append("and t.work_group_code = '").append(workGroupCode).append("' ");
        }
        if (!"".equals(departmentCode)) {
            sb.append("and t.department_code = '").append(departmentCode).append("' ");
        }
        if (recordOrRegionOverdueFlag == 1) {
            sb.append("and t.over_date_count > 0 ");
        }
        sb.append("order by p.create_time desc limit ");
        sb.append(startPage + "," + pageSize);
        String sql = sb.toString();
        log.debug(sql);
        return sql;
    }

    public String getProjectCollectInformationCount(String name, String regionCode, Integer recordTypeId, String unitName, String linkman, String telephone, String score, Integer overdueFlag, String dayCount, String startDate, String endDate, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId, String departmentCode, String workGroupCode, Integer recordOrRegionOverdueFlag) {
        StringBuffer sb = new StringBuffer(256);
        sb.append("select count(*) from ");
        sb.append("by_plus_project p,by_plus_task t where p.id = t.project_id and p.project_state_id = 78 and p.using_type = 1 and t.using_type = 1 ");
        if (!"".equals(name)) {
            sb.append("and p.name like ").append("'%").append(name).append("%' ");
        }
        if (!"".equals(regionCode)) {
            sb.append("and p.region_code = ").append(regionCode).append(" ");
        }
        if (recordTypeId != null) {
            sb.append("and p.record_type_id = ").append(recordTypeId).append(" ");
        }
        if (!"".equals(unitName)) {
            sb.append("and p.unit_name like ").append("'%").append(unitName).append("%' ");
        }
        if (!"".equals(linkman)) {
            sb.append("and p.linkman like ").append("'%").append(linkman).append("%' ");
        }
        if (!"".equals(telephone)) {
            sb.append("and p.telephone like ").append("'%").append(telephone).append("%' ");
        }
        //score分数处理:判断该分数是一个分数还是区间,然后分别处理,只有100是具体分数,其他均为区间
        if (!"".equals(score)) {
            boolean flag = score.contains("-");
            if (flag) {
                String[] strScore = score.split("-");
                Double startScore = Double.valueOf(strScore[0]);
                Double endScore = Double.valueOf(strScore[1]);
                sb.append("and t.task_score >= ").append(startScore).append(" and ").append("t.task_score < ").append(endScore).append(" ");
            } else {
                Double singleScore = Double.valueOf(score);
                sb.append("and t.task_score = ").append(singleScore).append(" ");
            }
        }
        //超期天数处理
        if (overdueFlag == 1) {
            //处理1-6天,6-8天,8-10天之类的
            boolean flag = dayCount.contains("-");
            if (flag) {
                String[] strOverduoDay = dayCount.split("-");
                Integer startDayCount = Integer.valueOf(strOverduoDay[0]);
                String endStr = strOverduoDay[1].substring(0, strOverduoDay[1].length() - 1);
                Integer endDayCount = Integer.valueOf(endStr);
                sb.append("and t.over_date_count > ").append(startDayCount).append(" and ").append("t.over_date_count <= ").append(endDayCount).append(" ");
                //处理1天,10天以上
            } else {
                if (dayCount.length() <= 2) {
                    Integer strDayCount = Integer.valueOf(dayCount.substring(0, 1));
                    sb.append("and t.over_date_count = ").append(strDayCount).append(" ");
                } else {
                    Integer skrDayCount = Integer.valueOf(dayCount.substring(0, dayCount.length() - 3));
                    sb.append("and t.over_date_count > ").append(skrDayCount).append(" ");
                }
            }
        }
        if (!"".equals(startDate) && !"".equals(endDate)) {
            sb.append("and p.start_date between '").append(startDate).append("' and '").append(endDate).append("' ");
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
        if (!"".equals(workGroupCode)) {
            sb.append("and t.work_group_code = '").append(workGroupCode).append("' ");
        }
        if (!"".equals(departmentCode)) {
            sb.append("and t.department_code = '").append(departmentCode).append("' ");
        }
        if (recordOrRegionOverdueFlag == 1) {
            sb.append("and t.over_date_count > 0");
        }
        String sql = sb.toString();
        log.debug(sql);
        return sql;
    }

}