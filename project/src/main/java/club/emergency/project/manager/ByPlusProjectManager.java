package club.emergency.project.manager;

import club.emergency.project.model.ByPlusProject;
import club.map.core.manager.GenericManager;
import club.map.core.model.Page;

import java.util.List;
import java.util.Map;

/**
 * 项目统计管理接口
 */
public interface ByPlusProjectManager extends GenericManager<ByPlusProject, Integer> {

    /**
     * 分页查询方法
     *
     * @param name
     * @param flag
     * @param startTime
     * @param endTime
     * @param unitName
     * @param linkman
     * @param projectPeriod
     * @param regionCode
     * @param telephone
     * @param recordTypeId
     * @param projectStateId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page searchWithPage(String name, Integer flag, String startTime, String endTime, String unitName, String linkman, Integer projectPeriod, String regionCode, String telephone, Integer recordTypeId, Integer projectStateId, Integer pageNo, Integer pageSize);

    /**
     * 根据id查询详情
     *
     * @param id
     * @return
     */
    ByPlusProject searchById(Integer id);

    /**
     * 批量删除
     *
     * @param ids
     */
    void removeByIds(String ids);

    /**
     * 编辑方法(新增和修改)
     *
     * @param byPlusProject
     */
    void upperSave(ByPlusProject byPlusProject);

    /**
     * 查询项目信息(当前已被废弃)
     *
     * @param id
     * @return
     */
    ByPlusProject searchName(Integer id);

    /**
     * 修改项目项目费用支付状态
     *
     * @param id
     * @param projectCostId
     */
    void updateProjectCostById(Integer id, Integer projectCostId);

    /**
     * 修改项目资料提交情况
     *
     * @param id
     * @param dataStateId
     */
    void updateDataStateById(Integer id, Integer dataStateId);

    /**
     * 修改项目状态(暂未被使用的方法或者已废弃)
     *
     * @param projectId
     * @param projectStateId
     */
    void updateProjectState(Integer projectId, Integer projectStateId);

    /**
     * 按部门或者小组查询项目信息
     *
     * @param name
     * @param departmentCode
     * @param groupCode
     * @param projectStateId
     * @param flag
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page search(String name, String departmentCode, String groupCode, Integer projectStateId, Integer flag, Integer pageNo, Integer pageSize);

    /**
     * 修改项目完成状态
     *
     * @param projectId
     * @param projectStateId
     * @param flag
     */
    void updateProjectStateFinish(Integer projectId, Integer projectStateId, Integer flag);

    /**
     * 修改任务回退次数(项目表记录任务回退次数)
     *
     * @param taskReturnCount
     * @param projectId
     */
    void updateTaskReturnCountById(Integer taskReturnCount, Integer projectId);

    /**
     * 修改项目信息(部分字段)
     *
     * @param name
     * @param unitName
     * @param linkman
     * @param telephone
     * @param address
     * @param longitude
     * @param latitude
     * @param id
     */
    void updateProjectLocationInfo(String name, String unitName, String linkman, String telephone, String address, String longitude, String latitude, Integer id);

    /**
     * 按月份统计(项目统计)
     *
     * @param startDate
     * @param endDate
     * @param departmentCode
     * @param workGroupCode
     * @param oneInspectionUserId
     * @param twoInspectionUserId
     * @param threeInspectionUserId
     * @return
     */
    Map<String, Object> searchByMonth(String startDate, String endDate, String departmentCode, String workGroupCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId);

    /**
     * 按类型统计(项目统计)
     *
     * @param year
     * @param regionCode
     * @param departmentCode
     * @param workGroupCode
     * @param oneInspectionUserId
     * @param twoInspectionUserId
     * @param threeInspectionUserId
     * @param flag
     * @return
     */
    Map<String, Object> searchByRecordType(String year, String regionCode, String departmentCode, String workGroupCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId, Integer flag);

    /**
     * 按区域统计(项目统计)
     *
     * @param year
     * @param recordTypeId
     * @param departmentCode
     * @param workGroupCode
     * @param oneInspectionUserId
     * @param twoInspectionUserId
     * @param threeInspectionUserId
     * @param flag
     * @return
     */
    Map<String, Object> searchByRegion(String year, Integer recordTypeId, String departmentCode, String workGroupCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId, Integer flag);

    /**
     * 按评分统计(项目统计)
     *
     * @param year
     * @param departmentCode
     * @param workGroupCode
     * @param oneInspectionUserId
     * @param twoInspectionUserId
     * @param threeInspectionUserId
     * @return
     */
    Map<String, Object> searchByScore(String year, String departmentCode, String workGroupCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId);

    /**
     * 按超期统计(项目统计)
     *
     * @param year
     * @param departmentCode
     * @param workGroupCode
     * @param oneInspectionUserId
     * @param twoInspectionUserId
     * @param threeInspectionUserId
     * @return
     */
    Map<String, Object> searchByTaskOverdue(String year, String departmentCode, String workGroupCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId);

    /**
     * 按小組统计(项目统计)
     *
     * @param year
     * @param workGroupCode
     * @param departmentCode
     * @return
     */
    Map<String, Object> searchByGroup(String year, String departmentCode, String workGroupCode);

    /**
     * 汇总统计(项目统计)
     *
     * @param year
     * @param regionCode
     * @param recordTypeId
     * @param flag
     * @return
     */
    Map<String, Object> searchByTaskCollect(String year, String regionCode, Integer recordTypeId, Integer flag);

    /**
     * 项目坐标转换(经纬度转城建)
     */
    void updateCoordinateByIds();

    /**
     * 项目查询(可根据档案信息:档案号;该项目为地图上查询,项目均为已完成且入档的)
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
     * @param departmentCode
     * @param groupCode
     * @return
     */
    List<ByPlusProject> searchWithoutPage(String name, String startTime, String endTime, String unitName, String linkman, String telephone, String regionCode, Integer recordTypeId, String fileNumber, String dataNumber, String departmentCode, String groupCode);

    /**
     * 项目信息单个查询(地图显示单个项目信息详情)
     *
     * @param id
     */
    ByPlusProject searchProjectInformationById(Integer id);

    /**
     * 统计展示列表查询(项目统计,点击某个柱状图或者饼状图出来列表)
     *
     * @param name
     * @param regionName
     * @param recordName
     * @param unitName
     * @param linkman
     * @param telephone
     * @param score
     * @param dayCount
     * @param startDate
     * @param endDate
     * @param oneInspectionUserId
     * @param twoInspectionUserId
     * @param threeInspectionUserId
     * @param departmentCode
     * @param threeInspectionUserId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page searchProjectCensusWithPage(String name, String regionName, String recordName, String unitName, String linkman, String telephone, String score, String dayCount, String startDate, String endDate, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId, String departmentCode, String workGroupCode, Integer recordOrRegionOverdueFlag, String regionCode, Integer recordTypeId, Integer pageNo, Integer pageSize);
}