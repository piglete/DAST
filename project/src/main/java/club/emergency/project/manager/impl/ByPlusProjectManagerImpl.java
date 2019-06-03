package club.emergency.project.manager.impl;

import club.emergency.by_b_department.mapper.ByBDepartmentMapper;
import club.emergency.by_b_region.manager.ByBRegionManager;
import club.emergency.by_b_region.mapper.ByBRegionMapper;
import club.emergency.by_b_region.model.ByBRegion;
import club.emergency.dictionary.manager.ByBDictionaryChildManager;
import club.emergency.dictionary.mapper.ByBDictionaryChildMapper;
import club.emergency.dictionary.model.ByBDictionaryChild;
import club.emergency.project.manager.ByPlusProjectManager;
import club.emergency.project.manager.ByPlusTipMessageManager;
import club.emergency.project.mapper.ByPlusProjectMapper;
import club.emergency.project.model.*;
import club.map.base.util.CoordinateTransformationUtil;
import club.map.base.util.FtpUtils;
import club.map.core.manager.impl.GenericManagerImpl;
import club.map.core.model.FlipFilter;
import club.map.core.model.Operate;
import club.map.core.model.Page;
import com.wanqing.util.StringHandler;
import org.apache.commons.collections4.list.TreeList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 此模块包含项目信息的修改和项目统计信息
 */
@Service
public class ByPlusProjectManagerImpl extends GenericManagerImpl<ByPlusProject, Integer> implements ByPlusProjectManager {

    private ByPlusProjectMapper byPlusProjectMapper;
    private ByBDictionaryChildManager byBDictionaryChildManager;
    private ByBDepartmentMapper byBDepartmentMapper;
    private ByBRegionMapper byBRegionMapper;
    private ByBRegionManager byBRegionManager;
    private ByPlusTipMessageManager byPlusTipMessageManager;
    private ByBDictionaryChildMapper byBDictionaryChildMapper;

    @Autowired
    public ByPlusProjectManagerImpl(ByPlusProjectMapper mapper,
                                    ByPlusProjectMapper byPlusProjectMapper,
                                    ByBDictionaryChildManager byBDictionaryChildManager,
                                    ByBDepartmentMapper byBDepartmentMapper,
                                    ByBRegionMapper byBRegionMapper,
                                    ByBRegionManager byBRegionManager,
                                    ByPlusTipMessageManager byPlusTipMessageManager,
                                    ByBDictionaryChildMapper byBDictionaryChildMapper
    ) {
        super(mapper, ByPlusProject.class);
        this.byPlusProjectMapper = byPlusProjectMapper;
        this.byBDictionaryChildManager = byBDictionaryChildManager;
        this.byBDepartmentMapper = byBDepartmentMapper;
        this.byBRegionMapper = byBRegionMapper;
        this.byBRegionManager = byBRegionManager;
        this.byPlusTipMessageManager = byPlusTipMessageManager;
        this.byBDictionaryChildMapper = byBDictionaryChildMapper;
    }

    /**
     * 分页查询
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
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Page searchWithPage(String name, Integer flag, String startTime, String endTime, String unitName, String linkman, Integer projectPeriod, String regionCode, String telephone, Integer recordTypeId, Integer projectStateId, Integer pageNo, Integer pageSize) {
        FlipFilter flipFilter = new FlipFilter(ByPlusProject.class);
        flipFilter.addSearch("%" + name + "%", Operate.LIKE, "name");
        flipFilter.addSearch(flag, Operate.EQUAL, "flag");
        flipFilter.addSearch(recordTypeId, Operate.EQUAL, "recordTypeId");
        flipFilter.addSearch(projectPeriod, Operate.EQUAL, "projectPeriod");
        flipFilter.addSearch(projectStateId, Operate.EQUAL, "projectStateId");
        flipFilter.addSearch("%" + unitName + "%", Operate.LIKE, "unitName");
        flipFilter.addSearch("%" + linkman + "%", Operate.LIKE, "linkman");
        flipFilter.addSearch(regionCode + "%", Operate.LIKE, "regionCode");
        flipFilter.addSearch("%" + telephone + "%", Operate.LIKE, "telephone");
        if (!"".equals(startTime) && !"".equals(endTime)) {
            flipFilter.addRegion("startDate", startTime, endTime);
        }
        flipFilter.setPageNo(pageNo);
        flipFilter.setPageSize(pageSize);
        Page page = this.flipUsingInPage(flipFilter);
        List<ByPlusProject> byPlusProjectList = page.getListInfo();
        for (ByPlusProject byPlusProject : byPlusProjectList) {
            this.searchInfo(byPlusProject);
        }
        return page;
    }

    /**
     * 查询项目详细信息
     *
     * @param id
     * @return
     */
    @Override
    public ByPlusProject searchById(Integer id) {
        ByPlusProject byPlusProject = this.get(id);
        this.searchInfo(byPlusProject);
        return byPlusProject;
    }

    /**
     * 依赖注入(项目表依赖的其他表主键,根据id,获取对应名称注入)
     *
     * @param byPlusProject
     */
    private void searchInfo(ByPlusProject byPlusProject) {
        //获取项目类型和路径
        Integer recordTypeId = byPlusProject.getRecordTypeId();
        if (recordTypeId != null) {
            ByBDictionaryChild byBDictionaryChild = byBDictionaryChildManager.get(recordTypeId);
            byPlusProject.setRecordTypeName(byBDictionaryChild.getAlias());
            byPlusProject.setRecordIconUrl(byBDictionaryChild.getIconUrl());
        } else {
            byPlusProject.setRecordTypeName("");
            byPlusProject.setRecordIconUrl("");
        }
        //获取区域名称和全称
        String regionCode = byPlusProject.getRegionCode();
        if (StringHandler.isNotEmptyOrNull(regionCode)) {
            ByBRegion byBRegion = byBRegionMapper.searchByCode(regionCode);
            byPlusProject.setRegionName(byBRegion.getName());
            byPlusProject.setRegionFullName(byBRegion.getFullName());
        } else {
            byPlusProject.setRegionName("");
            byPlusProject.setRegionFullName("");
        }
        //项目状态
        Integer projectStateId = byPlusProject.getProjectStateId();
        if (projectStateId != null) {
            ByBDictionaryChild byBDictionaryChild = byBDictionaryChildManager.get(projectStateId);
            byPlusProject.setProjectState(byBDictionaryChild.getAlias());
        } else {
            byPlusProject.setProjectState("");
        }
        //费用支付状态
        Integer projectCostId = byPlusProject.getProjectCostId();
        if (projectCostId != null) {
            ByBDictionaryChild byBDictionaryChild = byBDictionaryChildManager.get(projectCostId);
            byPlusProject.setProjectCost(byBDictionaryChild.getAlias());
        } else {
            byPlusProject.setProjectCost("");
        }
        //资料交付状态
        Integer dataStateId = byPlusProject.getDataStateId();
        if (dataStateId != null) {
            ByBDictionaryChild byBDictionaryChild = byBDictionaryChildManager.get(dataStateId);
            byPlusProject.setDataState(byBDictionaryChild.getAlias());
        } else {
            byPlusProject.setDataState("");
        }
    }

    /**
     * 删除项目时,删除与项目有关的所有数据
     * 此处近乎所有的删除都用的是<byPlusProjectMapper>,所有删除是逻辑删除,没有引用别的表mapper,
     * 是为了防止在别的表处理时互相引用,导致循环引用
     * 注意:此项目为多模块springboot项目,互相注入会导致循环引用,因此在该方法处理时,删除其他表的关联信息时采用的是sql注入
     *
     * @param ids
     */
    @Transactional
    @Override
    public void removeByIds(String ids) {
        String[] idArr = ids.split(",");
        Arrays.asList(idArr).forEach(id -> {
            //获取项目id
            Integer pid = Integer.valueOf(id);
            //获取该项目下所有的任务
            List<ByPlusTask> byPlusTaskList = byPlusProjectMapper.searchTask(pid);
            for (ByPlusTask byPlusTask : byPlusTaskList) {
                //获取每一个任务id
                Integer taskId = byPlusTask.getId();
                //删除外查消息
                byPlusProjectMapper.removeOutCheckMessage(taskId);
                //删除档案部回退记录
                byPlusProjectMapper.removeRecordReturn(taskId);
                //删除任务质量记录
                byPlusProjectMapper.removeTaskQuality(taskId);
                //删除任务工作量
                byPlusProjectMapper.removeTaskWorkload(taskId);
                //删除二检回退记录
                byPlusProjectMapper.removeTwoReturn(taskId);
                //删除三检回退记录
                byPlusProjectMapper.removeThreeReturn(taskId);
                //删除三检回退至二检记录
                byPlusProjectMapper.removeThreeToTwoReturn(taskId);
                //删除外协部门记录
                byPlusProjectMapper.removeTaskDepartmentAssist(taskId);
                //删除外协回退记录
                byPlusProjectMapper.removeTaskAssistReturn(taskId);
                //删除任务暂停记录
                byPlusProjectMapper.removeTaskStop(taskId);
            }
            //删除任务文件
            List<ByPlusTaskFile> byPlusTaskFileList = byPlusProjectMapper.searchTaskFile(pid);
            if (byPlusTaskFileList.size() > 0) {
                for (ByPlusTaskFile byPlusTaskFile : byPlusTaskFileList) {
                    //删除文件
                    String path = byPlusTaskFile.getFileUrl();
                    String name = byPlusTaskFile.getFileRename();
                    FtpUtils.deleteFtpFile(path, name);
                }
                //删除任务文件记录
                byPlusProjectMapper.removeTaskFile(pid);
            }
            //删除支付记录
            byPlusProjectMapper.removePay(pid);
            //删除项目入库文件
            List<ByPlusStorageFile> byPlusStorageFileList = byPlusProjectMapper.searchStorageFile(pid);
            if (byPlusStorageFileList.size() > 0) {
                for (ByPlusStorageFile byPlusStorageFile : byPlusStorageFileList) {
                    //删除文件
                    String path = byPlusStorageFile.getFileUrl();
                    String name = byPlusStorageFile.getFileRename();
                    FtpUtils.deleteFtpFile(path, name);
                }
                //删除项目入库文件记录
                byPlusProjectMapper.removeStorageFile(pid);
            }
            //删除该任务下的通知消息
            byPlusProjectMapper.removeMessage(pid);
            //删除该项目下的超期消息
            byPlusProjectMapper.removeTipMessage(pid);
            //删除客户资料文件
            List<ByPlusDocument> byPlusDocumentList = byPlusProjectMapper.searchDocument(pid);
            if (byPlusDocumentList.size() > 0) {
                for (ByPlusDocument byPlusDocument : byPlusDocumentList) {
                    //删除文件
                    String path = byPlusDocument.getFileUrl();
                    String name = byPlusDocument.getFileRename();
                    FtpUtils.deleteFtpFile(path, name);
                }
                //删除客户资料文件记录
                byPlusProjectMapper.removeDocument(pid);
            }
            //删除档案提交资料比对
            byPlusProjectMapper.removeRecordComparison(pid);
            //删除该项目下所有任务
            byPlusProjectMapper.removeTask(pid);
            //删除项目
            this.remove(pid);
        });
    }

    /**
     * 编辑项目
     *
     * @param byPlusProject
     */
    @Transactional
    @Override
    public void upperSave(ByPlusProject byPlusProject) {
        Integer id = byPlusProject.getId();
        //添加项目创建时间
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (byPlusProject.getId() == null) {
            Date date = new Date();
            String addDate = sd.format(date);
            byPlusProject.setAddDate(addDate);
        }
        //根据项目预计周期(天)获取项目预计结束日期
        String startDate = byPlusProject.getStartDate();
        Integer projectPeriod = byPlusProject.getProjectPeriod();
        if (!"".equals(startDate) && projectPeriod != null) {
            String finishDate = this.getPeriod(startDate, projectPeriod);
            byPlusProject.setFinishDate(finishDate);
        }
        this.save(byPlusProject);
        //添加超期通知消息,id为空时,说明当前方法为新增,需要创建一条通知消息
        if (id == null) {
            Integer projectId = byPlusProject.getId();
            ByPlusTipMessage byPlusTipMessage = new ByPlusTipMessage();
            byPlusTipMessage.setProjectId(projectId);
            byPlusTipMessage.setMessageContent("");
            byPlusTipMessage.setTipDate(byPlusProject.getFinishDate());
            byPlusTipMessageManager.upperSave(byPlusTipMessage);
        }
    }

    /**
     * 时间处理(根据项目计划开始时间和项目预定完成周期,计算项目预计完成时间)
     *
     * @param startDate     项目计划开始时间
     * @param projectPeriod 项目预定完成周期
     * @return
     */
    private String getPeriod(String startDate, Integer projectPeriod) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sdf.parse(startDate, new ParsePosition(0)));
        calendar.add(Calendar.DATE, projectPeriod);
        String finishDate = sdf.format(calendar.getTime());
        return finishDate;
    }

    @Override
    public ByPlusProject searchName(Integer id) {
        ByPlusProject byPlusProject = this.get(id);
        //获取区域名称和全称
        String regionCode = byPlusProject.getRegionCode();
        if (StringHandler.isNotEmptyOrNull(regionCode)) {
            ByBRegion byBRegion = byBRegionMapper.searchByCode(regionCode);
            byPlusProject.setRegionName(byBRegion.getName());
        } else {
            byPlusProject.setRegionName("");
        }
        //获取项目类型
        Integer recordTypeId = byPlusProject.getRecordTypeId();
        if (recordTypeId != null) {
            ByBDictionaryChild byBDictionaryChild = byBDictionaryChildManager.get(recordTypeId);
            byPlusProject.setRecordTypeName(byBDictionaryChild.getAlias());
        } else {
            byPlusProject.setRecordTypeName("");
        }
        return byPlusProject;
    }

    @Transactional
    @Override
    public void updateProjectCostById(Integer id, Integer projectCostId) {
        byPlusProjectMapper.updateProjectCostById(id, projectCostId);
    }

    @Transactional
    @Override
    public void updateDataStateById(Integer id, Integer dataStateId) {
        byPlusProjectMapper.updateDataStateById(id, dataStateId);
    }

    /**
     * 修改项目状态
     *
     * @param projectId
     */
    @Transactional
    @Override
    public void updateProjectState(Integer projectId, Integer projectStateId) {
        byPlusProjectMapper.updateProjectStateById(projectStateId, projectId);
    }

    /**
     * 根据不同部门查询已完成项目(根据部门或者小组)
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
    @Override
    public Page search(String name, String departmentCode, String groupCode, Integer projectStateId, Integer flag, Integer pageNo, Integer pageSize) {
        //根据部门查询所有已完成的任务
        Set<Integer> set = new HashSet<>();
        //根据部门查询已入档的所有任务
        if (StringHandler.isNotEmptyOrNull(departmentCode)) {
            List<ByPlusTask> byPlusTaskList = byPlusProjectMapper.searchTaskByDepartment(departmentCode);
            if (byPlusTaskList.size() > 0) {
                for (ByPlusTask byPlusTask : byPlusTaskList) {
                    Integer projectId = byPlusTask.getProjectId();
                    set.add(projectId);
                }
            }
        }
        //根据小组查询已入档的所有任务
        if (StringHandler.isNotEmptyOrNull(groupCode)) {
            List<ByPlusTask> byPlusTaskList = byPlusProjectMapper.searchTaskByGroupCode(groupCode);
            if (byPlusTaskList.size() > 0) {
                for (ByPlusTask byPlusTask : byPlusTaskList) {
                    Integer projectId = byPlusTask.getProjectId();
                    set.add(projectId);
                }
            }
        }
        FlipFilter flipFilter = new FlipFilter(ByPlusProject.class);
        flipFilter.addSearch(set, Operate.IN, "id");
        flipFilter.addSearch(79, Operate.EQUAL, "projectStateId");
        flipFilter.addSearch(projectStateId, Operate.EQUAL, "projectStateId");
        flipFilter.addSearch(flag, Operate.EQUAL, "flag");
        flipFilter.setPageSize(pageSize);
        flipFilter.updatePageNo(pageNo);
        Page page = this.flipUsingInPage(flipFilter);
        List<ByPlusProject> byPlusProjectList = page.getListInfo();
        for (ByPlusProject byPlusProject : byPlusProjectList) {
            this.searchInfo(byPlusProject);
        }
        return page;
    }

    /**
     * 档案入库时,项目flag变成1
     * 1.项目超期提示消息
     *
     * @param projectId
     * @param projectStateId
     * @param flag
     */
    @Transactional
    @Override
    public void updateProjectStateFinish(Integer projectId, Integer projectStateId, Integer flag) {
        byPlusProjectMapper.updateProjectStateAndFlagById(projectStateId, projectId, flag);
        byPlusTipMessageManager.deleteMessageByProject(projectId);
    }

    /**
     * 修改项目下的任务回退次数和任务下达标识(0)
     *
     * @param taskReturnCount
     * @param projectId
     */
    @Transactional
    @Override
    public void updateTaskReturnCountById(Integer taskReturnCount, Integer projectId) {
        byPlusProjectMapper.updateTaskReturnCountById(taskReturnCount, projectId);
    }

    /**
     * 修改项目信息(包括项目位置(address),经度(longitude),纬度(latitude),项目名称(name),
     * 单位名称(unitName),联系人(linkman),联系电话(telephone))
     * 1.判断哪些字段修改了,未修改的赋予之前的值
     * 2.如果项目名称修改了,任务名称也必须修改
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
    @Transactional
    @Override
    public void updateProjectLocationInfo(String name, String unitName, String linkman, String telephone, String address, String longitude, String latitude, Integer id) {
        ByPlusProject byPlusProject = this.get(id);
        String oldName = byPlusProject.getName();
        if (StringHandler.isEmptyOrNull(name)) {
            name = oldName;
        }
        String oldUnitName = byPlusProject.getUnitName();
        if (StringHandler.isEmptyOrNull(unitName)) {
            unitName = oldUnitName;
        }
        String oldLinkman = byPlusProject.getLinkman();
        if (StringHandler.isEmptyOrNull(linkman)) {
            linkman = oldLinkman;
        }
        String oldTelephone = byPlusProject.getTelephone();
        if (StringHandler.isEmptyOrNull(telephone)) {
            telephone = oldTelephone;
        }
        String oldAddress = byPlusProject.getAddress();
        if (StringHandler.isEmptyOrNull(address)) {
            address = oldAddress;
        }
        //坐标转换处理,城建坐标转WGS84坐标
        String oldLongitude = byPlusProject.getLongitude();
        String oldLatitude = byPlusProject.getLatitude();
        if ("".equals(longitude) || "".equals(latitude)) {
            longitude = oldLongitude;
            latitude = oldLatitude;
        }
        byPlusProjectMapper.updateProjectLocationInfo(name, unitName, linkman, telephone, address, longitude, latitude, id);
        if (StringHandler.isNotEmptyOrNull(name)) {
            //未作废的任务修改任务名称
            Integer taskId = byPlusProjectMapper.searchTaskId(id);
            byPlusProjectMapper.updateTaskNameByTaskId(name, taskId);
        }
    }

    /**
     * 项目坐标信息修改(经纬度转城建)
     */
    @Transactional
    @Override
    public void updateCoordinateByIds() {
        FlipFilter flipFilter = new FlipFilter(ByPlusProject.class);
        flipFilter.addSearch(1, Operate.EQUAL, "taskFlag");
        List<ByPlusProject> byPlusProjectList = this.list(flipFilter);
        for (ByPlusProject byPlusProject : byPlusProjectList) {
            Integer id = byPlusProject.getId();
            String longitude = byPlusProject.getLongitude();
            String latitude = byPlusProject.getLatitude();
            if (!"".equals(longitude) && !"".equals(latitude)) {
                //判断是否已经转为城建坐标了
                boolean flag = this.IsWGS(longitude, latitude);
                if (flag) {
                    String str = longitude + "," + latitude;
                    String[] coor = CoordinateTransformationUtil.TranspantWGS842XACJ(str).split(",");
                    String log = coor[0];
                    String lat = coor[1];
                    byPlusProjectMapper.updateCoordinateById(log, lat, id);
                }
            }
        }
    }

    /**
     * 查询无分页
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
    @Override
    public List<ByPlusProject> searchWithoutPage(String name, String startTime, String endTime, String unitName, String linkman, String telephone, String regionCode, Integer recordTypeId, String fileNumber, String dataNumber, String departmentCode, String groupCode) {
        List<ByPlusProject> byPlusProjectList = byPlusProjectMapper.searchProjectAndRecordList(name, startTime, endTime, unitName, linkman, telephone, regionCode, recordTypeId, fileNumber, dataNumber, departmentCode, groupCode);
        return byPlusProjectList;
    }

    /**
     * 项目全部信息查询:包含项目档案信息(档案编号),项目任务信息(作业小组)
     *
     * @param id
     */
    @Override
    public ByPlusProject searchProjectInformationById(Integer id) {
        //查询项目信息
        ByPlusProject byPlusProject = this.get(id);
        Integer recordTypeId = byPlusProject.getRecordTypeId();
        if (recordTypeId != null) {
            byPlusProject.setRecordTypeName(byBDictionaryChildManager.get(recordTypeId).getAlias());
        } else {
            byPlusProject.setRecordTypeName("");
        }
        String regionCode = byPlusProject.getRegionCode();
        if (!"".equals(regionCode)) {
            byPlusProject.setRegionName(byBRegionMapper.searchByCode(regionCode).getName());
        } else {
            byPlusProject.setRegionName("");
        }
        //任务部门和小组信息
        ByPlusTask byPlusTask = byPlusProjectMapper.searchByTaskInformation(id);
        String departmentCode = byPlusTask.getDepartmentCode();
        String departmentName = byBDepartmentMapper.searchNameByCode(departmentCode);
        String groupCode = byPlusTask.getWorkGroupCode();
        String groupName = byBDepartmentMapper.searchNameByCode(groupCode);
        byPlusTask.setDepartmentName(departmentName);
        byPlusTask.setWorkGroupName(groupName);
        byPlusProject.setByPlusTask(byPlusTask);
        //档案信息
        ByRRecord byRRecord = byPlusProjectMapper.searchRecordInformation(id);
        byPlusProject.setByRRecord(byRRecord);
        return byPlusProject;
    }

    /**
     * 无分页查询
     * 该方法主要是用在项目统计时,点击柱状图或者饼状图出现列表
     * 此处涉及到多表关联,因此自己手写sql完成
     *
     * @param name
     * @param regionName
     * @param recordName
     * @param unitName
     * @param linkman
     * @param telephone
     * @param score                     分数区间()
     * @param dayCount                  超期天数区间()
     * @param startDate
     * @param endDate
     * @param oneInspectionUserId
     * @param twoInspectionUserId
     * @param threeInspectionUserId
     * @param departmentCode
     * @param workGroupCode
     * @param recordOrRegionOverdueFlag 是否查询区域或者档案的超期标识
     * @param regionCode
     * @param recordTypeId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Page searchProjectCensusWithPage(String name, String regionName, String recordName, String unitName, String linkman, String telephone, String score, String dayCount, String startDate, String endDate, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId, String departmentCode, String workGroupCode, Integer recordOrRegionOverdueFlag, String regionCode, Integer recordTypeId, Integer pageNo, Integer pageSize) {
        //区域处理,名称转code;
        if (!"".equals(regionName)) {
            regionCode = byBRegionMapper.searchCodeByName(regionName);
        }
        //项目类型处理,名称转id
        if (!"".equals(recordName)) {
            recordTypeId = byBDictionaryChildMapper.searchRecordTypeId(recordName);
        }
        //是否进行超期查询处理
        Integer overdueFlag = 0;
        if (!"".equals(dayCount)) {
            overdueFlag = 1;
        }
        //获取分页开始数量
        Integer startPage = (pageNo - 1) * pageSize;
        Integer totalCount = byPlusProjectMapper.searchProjectCollectInformationCount(name, regionCode, recordTypeId, unitName, linkman, telephone, score, overdueFlag, dayCount, startDate, endDate, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId, departmentCode, workGroupCode, recordOrRegionOverdueFlag);
        List<ByPlusProject> byPlusProjectList = byPlusProjectMapper.searchProjectCollectInformation(name, regionCode, recordTypeId, unitName, linkman, telephone, score, overdueFlag, dayCount, startDate, endDate, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId, startPage, pageSize, departmentCode, workGroupCode, recordOrRegionOverdueFlag);
        for (ByPlusProject byPlusProject : byPlusProjectList) {
            this.searchInfo(byPlusProject);
            String taskGroupCode = byPlusProject.getTaskGroupCode();
            if (!"".equals(taskGroupCode)) {
                byPlusProject.setTaskGroupName(byBDepartmentMapper.searchNameByCode(taskGroupCode));
            } else {
                byPlusProject.setTaskGroupName("");
            }
        }
        FlipFilter flipFilter = new FlipFilter(ByPlusProject.class);
        flipFilter.setPageSize(pageSize);
        flipFilter.setPageNo(pageNo);
        Page page = new Page(flipFilter, totalCount, byPlusProjectList);
        return page;
    }

    /**
     * 判断是否是经纬度(针对西安地区,西安地区经度是以108开始的字符串,纬度是以34开始的字符串)
     *
     * @param longitude
     * @param latitude
     * @return
     */
    private boolean IsWGS(String longitude, String latitude) {
        boolean flag = false;
        boolean flag1 = longitude.startsWith("108");
        boolean flag2 = latitude.startsWith("34");
        if (flag1 == true && flag2 == true) {
            flag = true;
        }
        return flag;
    }

    /**
     * 按月份统计数据
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
    @Override
    public Map<String, Object> searchByMonth(String startDate, String endDate, String departmentCode, String workGroupCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId) {
        Map<String, Object> map = new HashMap<String, Object>();
        //已完成/当月完成任务统计
        int finishAllProject = byPlusProjectMapper.searchFinishAllProject(startDate, endDate, workGroupCode, departmentCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        int nowMonthFinishProject = byPlusProjectMapper.searchNowMonthFinishProject(startDate, endDate, workGroupCode, departmentCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        Map<String, Object> finishMap = new HashMap<String, Object>();
        finishMap.put("已完成总任务", finishAllProject);
        finishMap.put("当月完成总任务", nowMonthFinishProject);
        map.put("finishProject", finishMap);
        //项目类型当月统计
        Map<String, Integer> recordTypeMap = this.searchCountByRecordType(startDate, endDate, departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        map.put("projectTypeCount", this.transRecord(recordTypeMap));
        //区域类型当月统计
        Map<String, Integer> regionTypeMap = this.searchCountByRegionType(startDate, endDate, departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        map.put("regionTypeCount", this.transRegion(regionTypeMap));
        //按类型平均分统计
        Integer flag1 = 0;
        Map<String, Object> taskScoreRecordTypeMap = this.searchTaskScoreRecordTypeCount(startDate, endDate, departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId, flag1);
        map.put("taskScoreRecordType", taskScoreRecordTypeMap);
        //总/当月平均分统计
        Map<String, Object> averageScoreMap = new HashMap<String, Object>();
        //查询所有项目平均分时,不需要日期,为了方法通用,设置为""
        String startDate1 = "";
        String endDate1 = "";
        Double averageAllScore = byPlusProjectMapper.searchAverageScore(startDate1, endDate1, workGroupCode, departmentCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        Double averageMonthScore = byPlusProjectMapper.searchAverageScore(startDate, endDate, workGroupCode, departmentCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        //查询结果为null时,赋初值为0,防止当月统计出现null值,页面展示不友好
        if (averageAllScore != null) {
            averageScoreMap.put("总平均分", averageAllScore);
        } else {
            averageScoreMap.put("总平均分", 0.0);
        }
        if (averageMonthScore != null) {
            averageScoreMap.put("当月平均分", averageMonthScore);
        } else {
            averageScoreMap.put("当月平均分", 0.0);
        }
        map.put("averageScore", averageScoreMap);
        //总超期/当月超期统计
        Map<String, Object> overdueMap = new HashMap<String, Object>();
        Integer overdueAll = byPlusProjectMapper.searchOverdue(startDate1, endDate1, workGroupCode, departmentCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        Integer overdueMonth = byPlusProjectMapper.searchOverdue(startDate, endDate, workGroupCode, departmentCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        //低于60分的数量
        Integer underSixtyScore = byPlusProjectMapper.searchLessScore(startDate, endDate, workGroupCode, departmentCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        overdueMap.put("总超期", overdueAll);
        overdueMap.put("当月超期", overdueMonth);
        overdueMap.put("低于60分数量", underSixtyScore);
        map.put("overdueCount", overdueMap);
        //按档案类型超期分类统计
        Map<String, Integer> overdueRecordTypeMap = this.searchCountByOverdueRecordType(startDate, endDate, departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        map.put("overdueProjectTypeCount", this.transRecord(overdueRecordTypeMap));
        return map;
    }

    /**
     * 项目类型-分数转化(当前查询到的集合taskScoreRecordTypeMap 均为有效值map,对于未查到的项目类型,初始分数默认为0.0)
     *
     * @param taskScoreRecordTypeMap
     * @return
     */
    private Object transRecordScore(Map<String, Double> taskScoreRecordTypeMap) {
        //获取项目所有类型
        String parentCode = "100100100";
        Map<String, String> mapStr = this.getProjectType(parentCode);
        for (String s : mapStr.values()) {
            if (!taskScoreRecordTypeMap.containsKey(s)) {
                taskScoreRecordTypeMap.put(s, 0.0);
            }
        }
        return taskScoreRecordTypeMap;
    }

    /**
     * 获取当月项目任务集合list
     *
     * @param startDate
     * @param endDate
     * @return
     */
    private List<ByPlusProject> getProjectList(String startDate, String endDate) {
        List<ByPlusProject> byPlusProjectList = byPlusProjectMapper.searchProjectList(startDate, endDate);
        for (ByPlusProject byPlusProject : byPlusProjectList) {
            this.searchInfo(byPlusProject);
        }
        return byPlusProjectList;
    }

    /**
     * 按项目类型统计(根据超期,数量,评分分别统计不同项目类型的数据)
     * flag标识,任务类型按月份统计时查询方法复用,为增强方法使用有效性
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
    @Override
    public Map<String, Object> searchByRecordType(String year, String regionCode, String departmentCode, String workGroupCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId, Integer flag) {
        Map<String, Object> map = new HashMap<String, Object>();
        String startDate = "";
        String endDate = "";
        if (flag == 0) {
            //类型超期统计
            Map<String, Object> overdueRecordTypeCountMap = this.searchOverdueCount(departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
            map.put("overdueRecordTypeProject", overdueRecordTypeCountMap);
            //类型统计
            Map<String, Object> taskRecordTypeCountMap = this.searchOverdueTaskCount(startDate, endDate, departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
            map.put("taskRecordTypeCount", taskRecordTypeCountMap);
            //任务评分类型统计
            Integer flag1 = 1;
            Map<String, Object> taskScoreRecordTypeMap = this.searchTaskScoreRecordTypeCount(startDate, endDate, departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId, flag1);
            map.put("taskScoreRecordType", taskScoreRecordTypeMap);
        }
        //任务类型(分组)、区域、月份数量统计
        Map<String, Object> taskRecordRegionCountMap = this.searchTaskRecordTypeAndRegion(year, regionCode, departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        map.put("taskRecordRegionCountMap", taskRecordRegionCountMap);
        return map;
    }

    /**
     * 类型和区域数量统计(按月份),以类型分组,查询到的list集合中不存在的项目类型,默认赋初值为0,最后进行类型和月份转换(前端需要)
     *
     * @param year
     * @param regionCode
     * @param departmentCode
     * @param workGroupCode
     * @param oneInspectionUserId
     * @param twoInspectionUserId
     * @param threeInspectionUserId
     * @return
     */
    private Map<String, Object> searchTaskRecordTypeAndRegion(String year, String regionCode, String departmentCode, String workGroupCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId) {
        Map<String, Object> map = new HashMap<String, Object>();
        String parentCode = "100100100";
        //按月份统计
        LocalDateTime localDateTime = LocalDateTime.now();
        //获取当前月份,如果参数year小于当前年份,则月份为12(查询全年所有月份)
        int month = localDateTime.getMonth().getValue();
        int nowYear = localDateTime.getYear();
        if (!"".equals(year)) {
            Integer integerYear = Integer.valueOf(year);
            if (integerYear < nowYear) {
                nowYear = Integer.valueOf(year);
                month = 12;
            }
        }
        //获取当前月份之前的月份集合
        Map<String, String> monthMap = this.getYearMonthMap(nowYear, month);
        //查询任务数量
        String yearStr = nowYear + "-";
        String startDate = "";
        String endDate = "";
        List<Map.Entry<String, String>> list = new ArrayList<>(monthMap.entrySet());
        for (Map.Entry<String, String> integerStringEntry : list) {
            String[] str = integerStringEntry.getValue().split(",");
            startDate = yearStr + str[0];
            endDate = yearStr + str[1];
            List<ByPlusProject> byPlusProjectList = byPlusProjectMapper.searchRecordTypeAndRegionCount(startDate, endDate, regionCode, workGroupCode, departmentCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
            Map<String, Integer> nowMonthMap = this.getMap(byPlusProjectList, parentCode);
            //项目类型处理(未查询到的项目类型初始值处理为0)
            map.put(integerStringEntry.getKey(), this.transRecord(nowMonthMap));
        }
        //获取项目类型,将项目类型和月份转换
        Map<String, String> stringMap = this.getProjectType(parentCode);
        Map<String, Object> objectMap = this.getTrans(map, stringMap);
        return objectMap;
    }

    /**
     * 类型和月份转换
     *
     * @param map
     * @param stringMap
     * @return
     */
    private Map<String, Object> getTrans(Map<String, Object> map, Map<String, String> stringMap) {
        Map<String, Object> stringObjectMap = new HashMap<String, Object>();
        List<String> list = new ArrayList<String>();
        for (String s : map.keySet()) {
            //获取数据,拼成字符串,后面再拆解
            Map<String, Integer> typeMap = (Map<String, Integer>) map.get(s);
            for (String s1 : typeMap.keySet()) {
                //str=月份+类型+数量
                String str = s + "," + s1 + "," + typeMap.get(s1).toString();
                list.add(str);
            }
        }
        for (String s : stringMap.values()) {
            Map<String, Integer> integerMap = new TreeMap<String, Integer>();
            for (String s1 : list) {
                String[] str = s1.split(",");
                if ("1月".equals(str[0]) && s.equals(str[1])) {
                    integerMap.put("1月", Integer.valueOf(str[2]));
                }
                if ("2月".equals(str[0]) && s.equals(str[1])) {
                    integerMap.put("2月", Integer.valueOf(str[2]));
                }
                if ("3月".equals(str[0]) && s.equals(str[1])) {
                    integerMap.put("3月", Integer.valueOf(str[2]));
                }
                if ("4月".equals(str[0]) && s.equals(str[1])) {
                    integerMap.put("4月", Integer.valueOf(str[2]));
                }
                if ("5月".equals(str[0]) && s.equals(str[1])) {
                    integerMap.put("5月", Integer.valueOf(str[2]));
                }
                if ("6月".equals(str[0]) && s.equals(str[1])) {
                    integerMap.put("6月", Integer.valueOf(str[2]));
                }
                if ("7月".equals(str[0]) && s.equals(str[1])) {
                    integerMap.put("7月", Integer.valueOf(str[2]));
                }
                if ("8月".equals(str[0]) && s.equals(str[1])) {
                    integerMap.put("8月", Integer.valueOf(str[2]));
                }
                if ("9月".equals(str[0]) && s.equals(str[1])) {
                    integerMap.put("9月", Integer.valueOf(str[2]));
                }
                if ("10月".equals(str[0]) && s.equals(str[1])) {
                    integerMap.put("10月", Integer.valueOf(str[2]));
                }
                if ("11月".equals(str[0]) && s.equals(str[1])) {
                    integerMap.put("11月", Integer.valueOf(str[2]));
                }
                if ("12月".equals(str[0]) && s.equals(str[1])) {
                    integerMap.put("12月", Integer.valueOf(str[2]));
                }
            }
            stringObjectMap.put(s, integerMap);
        }
        return stringObjectMap;
    }

    /**
     * 所有月份初始化,可以写入一个公共类维护
     *
     * @return
     */
    private Map<String, Integer> getMonthAll() {
        Map<String, Integer> map = new LinkedHashMap<String, Integer>();
        map.put("1月", 0);
        map.put("2月", 0);
        map.put("3月", 0);
        map.put("4月", 0);
        map.put("5月", 0);
        map.put("6月", 0);
        map.put("7月", 0);
        map.put("8月", 0);
        map.put("9月", 0);
        map.put("10月", 0);
        map.put("11月", 0);
        map.put("12月", 0);
        return map;
    }

    /**
     * 按区域统计(超期,总数,评分,月份)
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
    @Override
    public Map<String, Object> searchByRegion(String year, Integer recordTypeId, String departmentCode, String workGroupCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId, Integer flag) {
        String startDate = "";
        String endDate = "";
        Map<String, Object> map = new HashMap<String, Object>();
        if (flag == 0) {
            //区域超期统计
            Map<String, Object> overdueRegionCountMap = this.searchOverdueRegionCount(departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
            map.put("overdueRegionProject", overdueRegionCountMap);
            //区域统计
            Map<String, Object> taskRegionCountMap = this.searchRegionTaskCount(startDate, endDate, departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
            map.put("taskRegionCount", taskRegionCountMap);
            //区域评分
            Map<String, Object> taskScoreRegionMap = this.searchTaskScoreRegionCount(departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
            map.put("taskRegionScore", taskScoreRegionMap);
        }
        //任务区域(分组)、类型、月份数量统计
        Map<String, Object> taskRegionRecordCountMap = this.searchTaskRegionAndRecordType(year, recordTypeId, departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        map.put("taskRegionRecordCountMap", taskRegionRecordCountMap);
        return map;
    }

    /**
     * 区域和类型数量统计(按月份),以区域分组
     *
     * @param year
     * @param recordTypeId
     * @param departmentCode
     * @param workGroupCode
     * @param oneInspectionUserId
     * @param twoInspectionUserId
     * @param threeInspectionUserId
     * @return
     */
    private Map<String, Object> searchTaskRegionAndRecordType(String year, Integer recordTypeId, String departmentCode, String workGroupCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId) {
        Map<String, Object> map = new HashMap<String, Object>();
        //按月份统计
        LocalDateTime localDateTime = LocalDateTime.now();
        //获取当前年份
        //获取当前月份,如果参数year小于当前年份,则月份为12(查询一整年所有月份)
        int month = localDateTime.getMonth().getValue();
        int nowYear = localDateTime.getYear();
        if (!"".equals(year)) {
            Integer integerYear = Integer.valueOf(year);
            if (integerYear < nowYear) {
                nowYear = Integer.valueOf(year);
                month = 12;
            }
        }
        //获取当前月份之前的月份集合
        Map<String, String> monthMap = this.getYearMonthMap(nowYear, month);
        //查询任务数量
        String yearStr = nowYear + "-";
        String startDate = "";
        String endDate = "";
        List<Map.Entry<String, String>> list = new ArrayList<>(monthMap.entrySet());
        for (Map.Entry<String, String> integerStringEntry : list) {
            String[] str = integerStringEntry.getValue().split(",");
            startDate = yearStr + str[0];
            endDate = yearStr + str[1];
            List<ByPlusProject> byPlusProjectList = byPlusProjectMapper.searchRegionAndRecordTypeCount(startDate, endDate, recordTypeId, workGroupCode, departmentCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
            //项目类型处理,未出现的类型初始化值
            Map<String, Integer> nowMonthMap = this.getRegionMap(byPlusProjectList);
            map.put(integerStringEntry.getKey(), this.transRegion(nowMonthMap));
        }
        //获取项目类型,并进行月份转换
        Integer parentId = 4;
        Map<String, String> regionNameMap = byBRegionManager.searchRegion(parentId);
        Map<String, Object> objectMap = this.getTrans(map, regionNameMap);
        return objectMap;
    }

    /**
     * 按评分统计(项目类型,评分区间数量,区域,月份统计)
     *
     * @param year
     * @param departmentCode
     * @param workGroupCode
     * @param oneInspectionUserId
     * @param twoInspectionUserId
     * @param threeInspectionUserId
     * @return
     */
    @Override
    public Map<String, Object> searchByScore(String year, String departmentCode, String workGroupCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId) {
        //按类型评分
        String startDate = "";
        String endDate = "";
        Integer flag = 1;
        Map<String, Object> map = new HashMap<String, Object>();
        //任务总数
        int finishAllProject = byPlusProjectMapper.searchFinishAllProject(startDate, endDate, workGroupCode, departmentCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        Map<String, Object> taskScoreRecordTypeMap = this.searchTaskScoreRecordTypeCount(startDate, endDate, departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId, flag);
        taskScoreRecordTypeMap.put("taskTotal", finishAllProject);
        map.put("taskScoreRecordType", taskScoreRecordTypeMap);
        //按分数统计 flag=1(flag项目完成标识)
        Map<String, Object> scoreMap = this.searchCountByScore(startDate, endDate, departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        scoreMap.put("taskTotal", finishAllProject);
        map.put("scoreMap", scoreMap);
        //按区域评分
        Map<String, Object> taskScoreRegionMap = this.searchTaskScoreRegionCount(departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        taskScoreRegionMap.put("taskTotal", finishAllProject);
        map.put("taskRegionScore", taskScoreRegionMap);
        //任务类型平均分月份统计汇总
        Map<String, Object> taskRecordScoreCountMap = this.searchTaskRecordScoreGroupAndMonth(year, departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        map.put("taskRecordScoreCountMap", taskRecordScoreCountMap);
        //任务区域平均分月份统计汇总
        Map<String, Object> taskRegionScoreCountMap = this.searchTaskRegionScoreGroupAndMonth(year, departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        map.put("taskRegionScoreCountMap", taskRegionScoreCountMap);
        return map;
    }

    /**
     * 按月获取不同区域平均分
     *
     * @param departmentCode
     * @param workGroupCode
     * @param oneInspectionUserId
     * @param twoInspectionUserId
     * @param threeInspectionUserId
     * @return
     */
    private Map<String, Object> searchTaskRegionScoreGroupAndMonth(String year, String departmentCode, String workGroupCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId) {
        Map<String, Object> map = new HashMap<String, Object>();
        //按月份统计
        LocalDateTime localDateTime = LocalDateTime.now();
        //获取当前月份,如果参数year小于当前年份,则月份为12(查询一整年所有月份)
        int month = localDateTime.getMonth().getValue();
        int nowYear = localDateTime.getYear();
        if (!"".equals(year)) {
            Integer integerYear = Integer.valueOf(year);
            if (integerYear < nowYear) {
                nowYear = Integer.valueOf(year);
                month = 12;
            }
        }
        //获取当前月份之前的月份集合
        Map<String, String> monthMap = this.getYearMonthMap(nowYear, month);
        //查询任务数量
        String yearStr = nowYear + "-";
        String startDate = "";
        String endDate = "";
        Integer regionFlag = 0;
        //获取项目类型
        Integer parentId = 4;
        Map<String, String> regionMap = byBRegionManager.searchRegion(parentId);
        Map<String, Integer> integerMap = this.valueTransKey(regionMap);
        List<Map.Entry<String, String>> list = new ArrayList<>(monthMap.entrySet());
        for (Map.Entry<String, String> integerStringEntry : list) {
            String[] str = integerStringEntry.getValue().split(",");
            Map<String, Double> map1 = new HashMap<String, Double>();
            startDate = yearStr + str[0];
            endDate = yearStr + str[1];
            List<ByPlusProject> byPlusTaskList = byPlusProjectMapper.searchRegionAverageScore(startDate, endDate, workGroupCode, departmentCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
            for (ByPlusProject byPlusProject : byPlusTaskList) {
                String regionCode = byPlusProject.getRegionCode();
                Double score = byPlusProject.getTaskScore();
                map1.put(regionMap.get(regionCode), score);
            }
            //区域评分区间初始值设置
            map.put(integerStringEntry.getKey(), this.transRegionScore(map1));
        }
        //评分区间和月份转化
        Map<String, Object> objectMap = this.getTransForSecondMapValueInteger(map, integerMap);
        return objectMap;
    }

    /**
     * 按月获取不同项目类型平均分
     *
     * @param departmentCode
     * @param workGroupCode
     * @param oneInspectionUserId
     * @param twoInspectionUserId
     * @param threeInspectionUserId
     * @return
     */
    private Map<String, Object> searchTaskRecordScoreGroupAndMonth(String year, String departmentCode, String workGroupCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId) {
        Map<String, Object> map = new HashMap<String, Object>();
        //按月份统计
        LocalDateTime localDateTime = LocalDateTime.now();
        //获取当前月份,如果参数year小于当前年份,则月份为12(查询一整年所有月份)
        int month = localDateTime.getMonth().getValue();
        int nowYear = localDateTime.getYear();
        if (!"".equals(year)) {
            Integer integerYear = Integer.valueOf(year);
            if (integerYear < nowYear) {
                nowYear = Integer.valueOf(year);
                month = 12;
            }
        }
        //获取当前月份之前的月份集合
        Map<String, String> monthMap = this.getYearMonthMap(nowYear, month);
        //查询任务数量
        String yearStr = nowYear + "-";
        String startDate = "";
        String endDate = "";
        Integer regionFlag = 0;
        //获取项目类型
        String parentCode = "100100100";
        Map<String, String> mapStr = this.getProjectType(parentCode);
        //所有类型初始化,赋初值为0
        Map<String, Integer> integerMap = this.valueTransKey(mapStr);
        List<Map.Entry<String, String>> list = new ArrayList<>(monthMap.entrySet());
        for (Map.Entry<String, String> integerStringEntry : list) {
            String[] str = integerStringEntry.getValue().split(",");
            Map<String, Double> map1 = new HashMap<String, Double>();
            startDate = yearStr + str[0];
            endDate = yearStr + str[1];
            List<ByPlusProject> byPlusTaskList = byPlusProjectMapper.searchRecordTypeAverageScore(startDate, endDate, workGroupCode, departmentCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
            for (ByPlusProject byPlusProject : byPlusTaskList) {
                Integer recordTypeId = byPlusProject.getRecordTypeId();
                Double score = byPlusProject.getTaskScore();
                map1.put(mapStr.get(recordTypeId.toString()), score);
            }
            //项目类型评分初始化设置
            map.put(integerStringEntry.getKey(), this.transRecordScore(map1));
        }
        //项目类型评分和月份转化
        Map<String, Object> objectMap = this.getTransForSecondMapValueInteger(map, integerMap);
        return objectMap;
    }

    /**
     * 评分和月份转换
     *
     * @param map
     * @param scoreSection
     * @return
     */
    private Map<String, Object> getTransForSecondMapValueInteger(Map<String, Object> map, Map<String, Integer> scoreSection) {
        Map<String, Object> stringObjectMap = new HashMap<String, Object>();
        List<String> list = new ArrayList<String>();
        for (String s : map.keySet()) {
            Map<String, Double> typeMap = (Map<String, Double>) map.get(s);
            for (String s1 : typeMap.keySet()) {
                //str=月份+类型+数量
                String str = s + "," + s1 + "," + typeMap.get(s1).toString();
                list.add(str);
            }
        }
        for (String s : scoreSection.keySet()) {
            Map<String, Double> integerMap = new TreeMap<String, Double>();
            for (String s1 : list) {
                String[] str = s1.split(",");
                if ("1月".equals(str[0]) && s.equals(str[1])) {
                    integerMap.put("1月", Double.valueOf(str[2]));
                }
                if ("2月".equals(str[0]) && s.equals(str[1])) {
                    integerMap.put("2月", Double.valueOf(str[2]));
                }
                if ("3月".equals(str[0]) && s.equals(str[1])) {
                    integerMap.put("3月", Double.valueOf(str[2]));
                }
                if ("4月".equals(str[0]) && s.equals(str[1])) {
                    integerMap.put("4月", Double.valueOf(str[2]));
                }
                if ("5月".equals(str[0]) && s.equals(str[1])) {
                    integerMap.put("5月", Double.valueOf(str[2]));
                }
                if ("6月".equals(str[0]) && s.equals(str[1])) {
                    integerMap.put("6月", Double.valueOf(str[2]));
                }
                if ("7月".equals(str[0]) && s.equals(str[1])) {
                    integerMap.put("7月", Double.valueOf(str[2]));
                }
                if ("8月".equals(str[0]) && s.equals(str[1])) {
                    integerMap.put("8月", Double.valueOf(str[2]));
                }
                if ("9月".equals(str[0]) && s.equals(str[1])) {
                    integerMap.put("9月", Double.valueOf(str[2]));
                }
                if ("10月".equals(str[0]) && s.equals(str[1])) {
                    integerMap.put("10月", Double.valueOf(str[2]));
                }
                if ("11月".equals(str[0]) && s.equals(str[1])) {
                    integerMap.put("11月", Double.valueOf(str[2]));
                }
                if ("12月".equals(str[0]) && s.equals(str[1])) {
                    integerMap.put("12月", Double.valueOf(str[2]));
                }
            }
            stringObjectMap.put(s, integerMap);
        }
        return stringObjectMap;
    }

    /**
     * 超期统计
     *
     * @return
     */
    @Override
    public Map<String, Object> searchByTaskOverdue(String year, String departmentCode, String workGroupCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId) {
        String startDate = "";
        String endDate = "";
        Map<String, Object> map = new HashMap<String, Object>();
        //类型超期统计
        Map<String, Object> overdueRecordTypeCountMap = this.searchOverdueCount(departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        map.put("overdueRecordTypeProject", overdueRecordTypeCountMap);
        //天数超期统计
        String groupCode = "";
        Map<String, Object> dayOverdueCountMap = this.searchDayOverdueCount(startDate, endDate, departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        map.put("overdueDayProject", dayOverdueCountMap);
        //区域超期统计
        Map<String, Object> overdueRegionCountMap = this.searchOverdueRegionCount(departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        map.put("overdueRegionProject", overdueRegionCountMap);
        //任务类型超期数量月份统计汇总
        Map<String, Object> taskRecordOverdueCountMap = this.searchTaskRecordOverdueGroupAndMonth(year, departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        map.put("taskRecordOverdueCountMap", taskRecordOverdueCountMap);
        //任务区域超期数量月份统计汇总
        Map<String, Object> taskRegionOverdueCountMap = this.searchTaskRegionOverdueGroupAndMonth(year, departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        map.put("taskRegionOverdueCountMap", taskRegionOverdueCountMap);
        return map;
    }

    /**
     * 任务区域超期按月份统计
     *
     * @param departmentCode
     * @param workGroupCode
     * @param oneInspectionUserId
     * @param twoInspectionUserId
     * @param threeInspectionUserId
     * @return
     */
    private Map<String, Object> searchTaskRegionOverdueGroupAndMonth(String year, String departmentCode, String workGroupCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId) {
        Map<String, Object> map = new HashMap<String, Object>();
        //按月份统计
        LocalDateTime localDateTime = LocalDateTime.now();
        //获取当前月份,如果参数year小于当前年份,则月份为12(查询一整年所有月份)
        int month = localDateTime.getMonth().getValue();
        int nowYear = localDateTime.getYear();
        if (!"".equals(year)) {
            Integer integerYear = Integer.valueOf(year);
            if (integerYear < nowYear) {
                nowYear = Integer.valueOf(year);
                month = 12;
            }
        }
        //获取当前月份之前的月份集合
        Map<String, String> monthMap = this.getYearMonthMap(nowYear, month);
        //查询任务数量
        String yearStr = nowYear + "-";
        String startDate = "";
        String endDate = "";
        Integer regionFlag = 0;
        //获取项目类型
        Integer parentId = 4;
        Map<String, String> regionMap = byBRegionManager.searchRegion(parentId);
        Map<String, Integer> regionAllMap = this.valueTransKey(regionMap);
        List<Map.Entry<String, String>> list = new ArrayList<>(monthMap.entrySet());
        for (Map.Entry<String, String> integerStringEntry : list) {
            String[] str = integerStringEntry.getValue().split(",");
            Map<String, Integer> map1 = new HashMap<String, Integer>();
            startDate = yearStr + str[0];
            endDate = yearStr + str[1];
            List<ByPlusProject> byPlusTaskList = byPlusProjectMapper.searchOverdueRegionCount(startDate, endDate, workGroupCode, departmentCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
            for (ByPlusProject byPlusProject : byPlusTaskList) {
                String regionCode = byPlusProject.getRegionCode();
                Integer count = byPlusProject.getCount();
                map1.put(regionMap.get(regionCode), count);
            }
            //区域超期初始化设置(未出现的区域初始值设为0)
            map.put(integerStringEntry.getKey(), this.transRegion(map1));
        }
        //超期区域和月份转换
        Map<String, Object> objectMap = this.getTransForOverdue(map, regionAllMap);
        return objectMap;
    }

    /**
     * 任务超期汇总转换
     *
     * @param map
     * @param regionAllMap
     * @return
     */
    private Map<String, Object> getTransForOverdue(Map<String, Object> map, Map<String, Integer> regionAllMap) {
        Map<String, Object> stringObjectMap = new HashMap<String, Object>();
        List<String> list = new ArrayList<String>();
        for (String s : map.keySet()) {
            Map<String, Integer> typeMap = (Map<String, Integer>) map.get(s);
            for (String s1 : typeMap.keySet()) {
                //str=月份+类型+数量
                String str = s + "," + s1 + "," + typeMap.get(s1).toString();
                list.add(str);
            }
        }
        for (String s : regionAllMap.keySet()) {
            Map<String, Integer> integerMap = new TreeMap<String, Integer>();
            for (String s1 : list) {
                String[] str = s1.split(",");
                if ("1月".equals(str[0]) && s.equals(str[1])) {
                    integerMap.put("1月", Integer.valueOf(str[2]));
                }
                if ("2月".equals(str[0]) && s.equals(str[1])) {
                    integerMap.put("2月", Integer.valueOf(str[2]));
                }
                if ("3月".equals(str[0]) && s.equals(str[1])) {
                    integerMap.put("3月", Integer.valueOf(str[2]));
                }
                if ("4月".equals(str[0]) && s.equals(str[1])) {
                    integerMap.put("4月", Integer.valueOf(str[2]));
                }
                if ("5月".equals(str[0]) && s.equals(str[1])) {
                    integerMap.put("5月", Integer.valueOf(str[2]));
                }
                if ("6月".equals(str[0]) && s.equals(str[1])) {
                    integerMap.put("6月", Integer.valueOf(str[2]));
                }
                if ("7月".equals(str[0]) && s.equals(str[1])) {
                    integerMap.put("7月", Integer.valueOf(str[2]));
                }
                if ("8月".equals(str[0]) && s.equals(str[1])) {
                    integerMap.put("8月", Integer.valueOf(str[2]));
                }
                if ("9月".equals(str[0]) && s.equals(str[1])) {
                    integerMap.put("9月", Integer.valueOf(str[2]));
                }
                if ("10月".equals(str[0]) && s.equals(str[1])) {
                    integerMap.put("10月", Integer.valueOf(str[2]));
                }
                if ("11月".equals(str[0]) && s.equals(str[1])) {
                    integerMap.put("11月", Integer.valueOf(str[2]));
                }
                if ("12月".equals(str[0]) && s.equals(str[1])) {
                    integerMap.put("12月", Integer.valueOf(str[2]));
                }
            }
            stringObjectMap.put(s, integerMap);
        }
        return stringObjectMap;
    }

    /**
     * 任务类型超期按月份统计
     *
     * @param departmentCode
     * @param workGroupCode
     * @param oneInspectionUserId
     * @param twoInspectionUserId
     * @param threeInspectionUserId
     * @return
     */
    private Map<String, Object> searchTaskRecordOverdueGroupAndMonth(String year, String departmentCode, String workGroupCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId) {
        Map<String, Object> map = new HashMap<String, Object>();
        //按月份统计
        LocalDateTime localDateTime = LocalDateTime.now();
        //获取当前月份,如果参数year小于当前年份,则月份为12(查询一整年所有月份)
        int month = localDateTime.getMonth().getValue();
        int nowYear = localDateTime.getYear();
        if (!"".equals(year)) {
            Integer integerYear = Integer.valueOf(year);
            if (integerYear < nowYear) {
                nowYear = Integer.valueOf(year);
                month = 12;
            }
        }
        //获取当前月份之前的月份集合
        Map<String, String> monthMap = this.getYearMonthMap(nowYear, month);
        //查询任务数量
        String yearStr = nowYear + "-";
        String startDate = "";
        String endDate = "";
        Integer regionFlag = 0;
        //获取项目类型
        String parentCode = "100100100";
        Map<String, String> mapStr = this.getProjectType(parentCode);
        //所有类型初始化,赋初值为0
        Map<String, Integer> typeAllMap = this.valueTransKey(mapStr);
        List<Map.Entry<String, String>> list = new ArrayList<>(monthMap.entrySet());
        for (Map.Entry<String, String> integerStringEntry : list) {
            String[] str = integerStringEntry.getValue().split(",");
            Map<String, Integer> map1 = new HashMap<String, Integer>();
            startDate = yearStr + str[0];
            endDate = yearStr + str[1];
            List<ByPlusProject> byPlusTaskList = byPlusProjectMapper.searchOverdueRecordTypeList(startDate, endDate, workGroupCode, departmentCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
            for (ByPlusProject byPlusProject : byPlusTaskList) {
                Integer recordTypeId = byPlusProject.getRecordTypeId();
                Integer count = byPlusProject.getCount();
                map1.put(mapStr.get(recordTypeId.toString()), count);
            }
            //项目类型初始化值设置
            map.put(integerStringEntry.getKey(), this.transRecord(map1));
        }
        //项目类型和月份转换
        Map<String, Object> objectMap = this.getTransForOverdue(map, typeAllMap);
        return objectMap;
    }

    /**
     * 小组或者部门按年份统计
     *
     * @param year
     * @param departmentCode
     * @param workGroupCode
     * @return
     */
    @Override
    public Map<String, Object> searchByGroup(String year, String departmentCode, String workGroupCode) {
        //如果year为空,则查询所有,如果不为空,则查询对应年份
        String startDate = "";
        String endDate = "";
        if (!"".equals(year)) {
            startDate = year + "-01-01";
            endDate = year + "-12-31";
        }
        //方法复用,设置null只是为了方法复用,无实际意义
        Integer oneInspectionUserId = null;
        Integer twoInspectionUserId = null;
        Integer threeInspectionUserId = null;
        Map<String, Object> map = new HashMap<String, Object>();
        //小组类型统计
        Map<String, Object> taskRecordTypeCountMap = this.searchOverdueTaskCount(startDate, endDate, departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        map.put("taskRecordTypeCount", taskRecordTypeCountMap);
        //小组超期天数统计
        Map<String, Object> dayOverdueCountMap = this.searchDayOverdueCount(startDate, endDate, departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        map.put("overdueDayProject", dayOverdueCountMap);
        //小组评分统计
        Map<String, Object> scoreMap = this.searchCountByScore(startDate, endDate, departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        map.put("scoreMap", scoreMap);
        //小组区域统计
        Map<String, Object> taskRegionCountMap = this.searchRegionTaskCount(startDate, endDate, departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        map.put("taskRegionCount", taskRegionCountMap);
        return map;
    }

    /**
     * 汇总统计
     *
     * @param year
     * @param regionCode
     * @param recordTypeId
     * @param flag
     * @return
     */
    @Override
    public Map<String, Object> searchByTaskCollect(String year, String regionCode, Integer recordTypeId, Integer flag) {
        //如果year为空,则查询所有,如果不为空,则查询对应年份
        String startDate = "";
        String endDate = "";
        if (!"".equals(year)) {
            startDate = year + "-01-01";
            endDate = year + "-12-31";
        }
        String departmentCode = "";
        String workGroupCode = "";
        Integer oneInspectionUserId = null;
        Integer twoInspectionUserId = null;
        Integer threeInspectionUserId = null;
        Map<String, Object> map = new HashMap<String, Object>();
        if (flag == 0) {
            //任务总数
            int finishAllProject = byPlusProjectMapper.searchFinishAllProject(startDate, endDate, workGroupCode, departmentCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
            map.put("taskTotalCount", finishAllProject);
            //作业类型
            int recordTypeIdUnique = byPlusProjectMapper.searchDistinctRecordTypeId(startDate, endDate);
            map.put("recordTypeUnique", recordTypeIdUnique);
            //作业区域
            int regionCodeUnique = byPlusProjectMapper.searchDistinctRegionCode(startDate, endDate);
            map.put("regionCodeUnique", regionCodeUnique);
            //任务评定分数平均值
            Double averageScore = byPlusProjectMapper.searchAverageScore(startDate, endDate, workGroupCode, departmentCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
            map.put("averageScore", averageScore);
            //超期次数/超期天数
            String overdueRatio = this.searchCollectOverdue(startDate, endDate, departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
            map.put("overdueTimesAndDayCount", overdueRatio);
            //资料未交付/费用未交付
            String payRatio = this.searchCollectPay();
            //作业类型汇总
            Map<String, Object> taskRecordTypeCountMap = this.searchOverdueTaskCount(startDate, endDate, departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
            map.put("taskRecordTypeCount", taskRecordTypeCountMap);
            //作业区域汇总
            Map<String, Object> taskRegionCountMap = this.searchRegionTaskCount(startDate, endDate, departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
            map.put("taskRegionCount", taskRegionCountMap);
            //作业超期汇总
            Map<String, Object> dayOverdueCountMap = this.searchDayOverdueCount(startDate, endDate, departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
            map.put("overdueDayProject", dayOverdueCountMap);
            //作业评分汇总
            Map<String, Object> scoreMap = this.searchCountByScore(startDate, endDate, departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
            map.put("scoreMap", scoreMap);
            //任务数量部门最大,最小统计
            Map<String, Object> taskMaxAndMinGroup = this.searchTaskMaxAndMinGroup(startDate, endDate);
            map.put("taskMaxAndMinGroup", taskMaxAndMinGroup);
        }
        //作业数量统计
        Map<String, Integer> monthMap = this.searchYearMonthCount(year, regionCode, recordTypeId);
        map.put("monthCount", monthMap);
        return map;
    }

    /**
     * 分别统计
     * 1.任务数量最多和最少的小组
     * 2.评定分数最高和最低的小组
     * 3.超期数量最多和最少的小组
     * 4.任务工期数量最多和最少的小组
     *
     * @return
     */
    private Map<String, Object> searchTaskMaxAndMinGroup(String startDate, String endDate) {
        Map<String, Object> taskMap = new HashMap<String, Object>();
        //1.任务数量最多和最少的小组
        Map<String, Integer> map = new HashMap<String, Integer>();
        List<ByPlusProject> byPlusProjectList = byPlusProjectMapper.searchTaskCountByGroup(startDate, endDate);
        for (ByPlusProject byPlusProject : byPlusProjectList) {
            String groupCode = byPlusProject.getGroupCode();
            Integer groupCount = byPlusProject.getCount();
            map.put(groupCode, groupCount);
        }
        //最小和最大的任务数量
        if (map.size() == 0) {
            taskMap.put("taskCountMaxGroup", null);
            taskMap.put("taskCountMinGroup", null);
        } else if (map.size() == 1) {
            for (String key : map.keySet()) {
                String groupName = byBDepartmentMapper.searchNameByCode(key);
                taskMap.put("taskScoreMaxGroup", groupName);
                taskMap.put("taskScoreMinGroup", groupName);
            }
        } else {
            List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
            Collections.sort(list, (o1, o2) -> (o1.getValue() - o2.getValue()));
            String minTaskCountGroup = list.get(0).getKey();
            String maxTaskCountGroup = list.get(list.size() - 1).getKey();
            String minTaskCountGroupName = byBDepartmentMapper.searchNameByCode(minTaskCountGroup);
            String maxTaskCountGroupName = byBDepartmentMapper.searchNameByCode(maxTaskCountGroup);
            taskMap.put("taskCountMaxGroup", maxTaskCountGroupName);
            taskMap.put("taskCountMinGroup", minTaskCountGroupName);
        }
        //2.评定分数最高和最低的小组
        Map<String, Double> scoreMap = new HashMap<String, Double>();
        List<ByPlusProject> byPlusProjectScoreList = byPlusProjectMapper.searchTaskScoreByGroup(startDate, endDate);
        for (ByPlusProject byPlusProject : byPlusProjectScoreList) {
            String groupCode = byPlusProject.getGroupCode();
            Double taskScore = byPlusProject.getTaskScore();
            scoreMap.put(groupCode, taskScore);
        }
        //最小和最大的任务评定分数
        if (scoreMap.size() == 0) {
            taskMap.put("taskScoreMaxGroup", null);
            taskMap.put("taskScoreMinGroup", null);
        } else if (scoreMap.size() == 1) {
            for (String key : scoreMap.keySet()) {
                String groupName = byBDepartmentMapper.searchNameByCode(key);
                taskMap.put("taskScoreMaxGroup", groupName);
                taskMap.put("taskScoreMinGroup", groupName);
            }
        } else {
            Collection<Double> valueCollection = scoreMap.values();
            List<Double> valueList = new ArrayList<>(valueCollection);
            Collections.sort(valueList);
            Double minScore = valueList.get(0);
            Double maxScore = valueList.get(valueList.size() - 1);
            String minScoreGroup = null;
            String maxScoreGroup = null;
            for (Map.Entry<String, Double> stringDoubleEntry : scoreMap.entrySet()) {
                if (stringDoubleEntry.getValue() == minScore) {
                    minScoreGroup = stringDoubleEntry.getKey();
                }
                if (stringDoubleEntry.getValue() == maxScore) {
                    maxScoreGroup = stringDoubleEntry.getKey();
                }
            }
            String minScoreGroupName = byBDepartmentMapper.searchNameByCode(minScoreGroup);
            String maxScoreGroupName = byBDepartmentMapper.searchNameByCode(maxScoreGroup);
            taskMap.put("taskScoreMaxGroup", maxScoreGroupName);
            taskMap.put("taskScoreMinGroup", minScoreGroupName);
        }
        //3.超期数量最多和最少的小组
        Map<String, Integer> overdueMap = new HashMap<String, Integer>();
        List<ByPlusProject> byPlusProjectOverdueList = byPlusProjectMapper.searchTaskOverdueByGroup(startDate, endDate);
        for (ByPlusProject byPlusProject : byPlusProjectOverdueList) {
            String groupCode = byPlusProject.getGroupCode();
            Integer groupCount = byPlusProject.getCount();
            overdueMap.put(groupCode, groupCount);
        }
        //最小和最大的超期小组
        if (overdueMap.size() == 0) {
            taskMap.put("taskOverdueMaxGroup", null);
            taskMap.put("taskOverdueMinGroup", null);
        } else if (overdueMap.size() == 1) {
            for (String key : overdueMap.keySet()) {
                String groupName = byBDepartmentMapper.searchNameByCode(key);
                taskMap.put("taskOverdueMaxGroup", groupName);
                taskMap.put("taskOverdueMinGroup", groupName);
            }
        } else {
            List<Map.Entry<String, Integer>> overdueList = new ArrayList<>(overdueMap.entrySet());
            Collections.sort(overdueList, (o1, o2) -> (o1.getValue() - o2.getValue()));
            String minOverdueGroup = overdueList.get(0).getKey();
            String maxOverdueGroup = overdueList.get(overdueList.size() - 1).getKey();
            String minOverdueGroupName = byBDepartmentMapper.searchNameByCode(minOverdueGroup);
            String maxOverdueGroupName = byBDepartmentMapper.searchNameByCode(maxOverdueGroup);
            taskMap.put("taskOverdueMaxGroup", maxOverdueGroupName);
            taskMap.put("taskOverdueMinGroup", minOverdueGroupName);
        }
        //4.任务工期数量最多和最少的小组
        Map<String, Integer> taskPeriodMap = new HashMap<String, Integer>();
        List<ByPlusProject> byPlusProjectPeriodList = byPlusProjectMapper.searchTaskPeriodByGroup(startDate, endDate);
        for (ByPlusProject byPlusProject : byPlusProjectPeriodList) {
            String groupCode = byPlusProject.getGroupCode();
            Integer groupCount = byPlusProject.getCount();
            taskPeriodMap.put(groupCode, groupCount);
        }
        //最小和最大任务工期数量小组
        if (taskPeriodMap.size() == 0) {
            taskMap.put("taskPeriodMaxGroup", null);
            taskMap.put("taskPeriodMinGroup", null);
        } else if (taskPeriodMap.size() == 1) {
            for (String key : taskPeriodMap.keySet()) {
                String groupName = byBDepartmentMapper.searchNameByCode(key);
                taskMap.put("taskPeriodMaxGroup", groupName);
                taskMap.put("taskPeriodMinGroup", groupName);
            }
        } else {
            List<Map.Entry<String, Integer>> periodList = new ArrayList<>(taskPeriodMap.entrySet());
            Collections.sort(periodList, (o1, o2) -> (o1.getValue() - o2.getValue()));
            String minPeriodGroup = periodList.get(0).getKey();
            String maxPeriodGroup = periodList.get(periodList.size() - 1).getKey();
            String minPeriodGroupName = byBDepartmentMapper.searchNameByCode(minPeriodGroup);
            String maxPeriodGroupName = byBDepartmentMapper.searchNameByCode(maxPeriodGroup);
            taskMap.put("taskPeriodMaxGroup", maxPeriodGroupName);
            taskMap.put("taskPeriodMinGroup", minPeriodGroupName);
        }
        return taskMap;
    }

    /**
     * 年度月份统计(当前年份至今为止的前面月份)
     *
     * @param regionCode
     * @param recordTypeId
     * @return
     */
    private Map<String, Integer> searchYearMonthCount(String year, String regionCode, Integer recordTypeId) {
        Map<String, Integer> monthMap = new TreeMap<String, Integer>();
        //按月份统计
        LocalDateTime localDateTime = LocalDateTime.now();
        //获取当前月份,如果参数year小于当前年份,则月份为12(查询一整年所有月份)
        int month = localDateTime.getMonth().getValue();
        int nowYear = localDateTime.getYear();
        if (!"".equals(year)) {
            Integer integerYear = Integer.valueOf(year);
            if (integerYear < nowYear) {
                nowYear = Integer.valueOf(year);
                month = 12;
            }
        }
        //获取当前月份之前的月份集合
        Map<String, String> map = this.getYearMonthMap(nowYear, month);
        //查询任务数量
        String yearStr = nowYear + "-";
        String startDate = "";
        String endDate = "";
        List<Map.Entry<String, String>> list = new TreeList<>(map.entrySet());
        for (Map.Entry<String, String> integerStringEntry : list) {
            String[] str = integerStringEntry.getValue().split(",");
            startDate = yearStr + str[0];
            endDate = yearStr + str[1];
            int taskCount = byPlusProjectMapper.searchMonthCount(regionCode, recordTypeId, startDate, endDate);
            monthMap.put(integerStringEntry.getKey(), taskCount);
        }
        //对于未查询到的月份初始值为0补充
        Map<String, Integer> allMonthMap = this.getMonthAll();
        for (String s : allMonthMap.keySet()) {
            if (monthMap.containsKey(s)) {
                allMonthMap.put(s, monthMap.get(s));
            }
        }
        return allMonthMap;
    }

    /**
     * 所有月份对应时间处理
     *
     * @return
     */
    private Map<String, String> getYearMonthMap(Integer year, Integer month) {
        Map<String, String> stringMap = new HashMap<String, String>();
        //判断是不是瑞年,flag = 0不是瑞年;flag = 1是瑞年
        int flag = 0;
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            flag = 1;
        }
        if (month >= 1) {
            stringMap.put("1月", "01-01,01-31");
        }
        if (month >= 2) {
            if (flag == 0) {
                stringMap.put("2月", "02-01,02-28");
            } else {
                stringMap.put("2月", "02-01,02-29");
            }
        }
        if (month >= 3) {
            stringMap.put("3月", "03-01,03-31");
        }
        if (month >= 4) {
            stringMap.put("4月", "04-01,04-30");
        }
        if (month >= 5) {
            stringMap.put("5月", "05-01,05-31");
        }
        if (month >= 6) {
            stringMap.put("6月", "06-01,06-30");
        }
        if (month >= 7) {
            stringMap.put("7月", "07-01,07-31");
        }
        if (month >= 8) {
            stringMap.put("8月", "08-01,08-31");
        }
        if (month >= 9) {
            stringMap.put("9月", "09-01,09-30");
        }
        if (month >= 10) {
            stringMap.put("10月", "10-01,10-31");
        }
        if (month >= 11) {
            stringMap.put("11月", "11-01,11-30");
        }
        if (month >= 12) {
            stringMap.put("12月", "12-01,12-31");
        }
        return stringMap;
    }

    /**
     * 资料未交付/费用未交付
     *
     * @return
     */
    private String searchCollectPay() {
        //查询资料未交付的数量(资料交付状态总共有三种:未交付、部分交付、全部交付)
        int dataState = byPlusProjectMapper.searchDataStateCount();
        //查询费用未交付的数量(费用交付状态总共有三种:未交费、部分交费、全部交费)
        int payCost = byPlusProjectMapper.searchProjectCostCount();
        String payRatio = dataState + "/" + payCost;
        return payRatio;
    }

    /**
     * 超期次数/超期天数查询
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
    private String searchCollectOverdue(String startDate, String endDate, String departmentCode, String workGroupCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId) {
        String groupCode = "";
        List<ByPlusTask> byPlusTaskList = byPlusProjectMapper.searchListForTaskOverdue(startDate, endDate, workGroupCode, departmentCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        int overdueTimes = byPlusTaskList.size();
        int overdueDayCount = 0;
        for (ByPlusTask byPlusTask : byPlusTaskList) {
            Integer dayCount = byPlusTask.getOverDateCount();
            overdueDayCount += dayCount;
        }
        String ratio = overdueTimes + "/" + overdueDayCount;
        return ratio;
    }

    /**
     * 超期天数统计
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
    private Map<String, Object> searchDayOverdueCount(String startDate, String endDate, String departmentCode, String workGroupCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId) {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        List<ByPlusTask> byPlusTaskList = byPlusProjectMapper.searchListForTaskOverdue(startDate, endDate, workGroupCode, departmentCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        Integer overdueDayCount = byPlusTaskList.size();
        //获取分数段
        Map<String, Integer> daySection = this.getOverdueDaySection();
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (ByPlusTask byPlusTask : byPlusTaskList) {
            Integer overDay = byPlusTask.getOverDateCount();
            if (overDay <= 1) {
                String key = "1天";
                this.searchOverDeal(map, key);
            } else if (overDay > 1 && overDay <= 6) {
                String key = "1-6天";
                this.searchOverDeal(map, key);
            } else if (overDay > 6 && overDay <= 8) {
                String key = "6-8天";
                this.searchOverDeal(map, key);
            } else if (overDay > 8 && overDay <= 10) {
                String key = "8-10天";
                this.searchOverDeal(map, key);
            } else if (overDay > 10) {
                String key = "10天以上";
                this.searchOverDeal(map, key);
            }
        }
        //最小和最大的超期天数区间
        if (map.size() == 0) {
            objectMap.put("maxDayArea", "无");
            objectMap.put("maxDayAreaCount", 0);
            objectMap.put("minDayArea", "无");
            objectMap.put("minDayAreaCount", 0);
        } else if (map.size() == 1) {
            for (String s : map.keySet()) {
                objectMap.put("maxDayArea", s);
                objectMap.put("maxDayAreaCount", map.get(s));
                objectMap.put("minDayArea", s);
                objectMap.put("minDayAreaCount", map.get(s));
            }
        } else {
            List<Map.Entry<String, Integer>> list = new TreeList<>(map.entrySet());
            Collections.sort(list, (o1, o2) -> (o1.getValue() - o2.getValue()));
            String minKey = list.get(0).getKey();
            String maxKey = list.get(list.size() - 1).getKey();
            objectMap.put("maxDayArea", maxKey);
            objectMap.put("maxDayAreaCount", map.get(maxKey));
            objectMap.put("minDayArea", minKey);
            objectMap.put("minDayAreaCount", map.get(minKey));
        }
        //当前项目集合中不存在的分数区间初始化数量值为0
        for (String s : daySection.keySet()) {
            if (map.containsKey(s)) {
                daySection.put(s, map.get(s));
            }
        }
        objectMap.put("allDayCount", overdueDayCount);
        objectMap.put("allOverdueDayCount", daySection);
        return objectMap;
    }

    /**
     * 超期天数区间初始化值
     *
     * @return
     */
    private Map<String, Integer> getOverdueDaySection() {
        Map<String, Integer> map = new TreeMap<String, Integer>();
        //分数区间不含左含右
        map.put("1天", 0);
        map.put("1-6天", 0);
        map.put("6-8天", 0);
        map.put("8-10天", 0);
        map.put("10天以上", 0);
        return map;
    }

    /**
     * 获取任务区域分数
     *
     * @param departmentCode
     * @param workGroupCode
     * @param oneInspectionUserId
     * @param twoInspectionUserId
     * @param threeInspectionUserId
     * @return
     */
    private Map<String, Object> searchTaskScoreRegionCount(String departmentCode, String workGroupCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId) {
        Map<String, Object> averageScoreMap = new HashMap<String, Object>();
        //总平均分
        String startDate = "";
        String endDate = "";
        Double averageAllScore = 0.0;
        Double averageAllScore1 = byPlusProjectMapper.searchAverageScore(startDate, endDate, workGroupCode, departmentCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        if (averageAllScore1 != null) {
            averageAllScore = averageAllScore1;
        }
        //区域平均分
        Map<String, Double> regionAverageScoreMap = this.searchRegionAverageScore(startDate, endDate, departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        //最小和最大的分数
        if (regionAverageScoreMap.size() == 0) {
            averageScoreMap.put("maxRegion", "无");
            averageScoreMap.put("maxRegionScore", 0);
            averageScoreMap.put("minRegion", "无");
            averageScoreMap.put("minRegionScore", 0);
        } else if (regionAverageScoreMap.size() == 1) {
            for (String s : regionAverageScoreMap.keySet()) {
                averageScoreMap.put("maxRegion", s);
                averageScoreMap.put("maxRegionScore", regionAverageScoreMap.get(s));
                averageScoreMap.put("minRegion", s);
                averageScoreMap.put("minRegionScore", regionAverageScoreMap.get(s));
            }
        } else {
            Collection<Double> valueCollection = regionAverageScoreMap.values();
            List<Double> valueList = new ArrayList<>(valueCollection);
            Collections.sort(valueList);
            Double minScore = valueList.get(0);
            Double maxScore = valueList.get(valueList.size() - 1);
            String minRegion = null;
            String maxRegion = null;
            for (Map.Entry<String, Double> stringDoubleEntry : regionAverageScoreMap.entrySet()) {
                if (stringDoubleEntry.getValue() == minScore) {
                    minRegion = stringDoubleEntry.getKey();
                }
                if (stringDoubleEntry.getValue() == maxScore) {
                    maxRegion = stringDoubleEntry.getKey();
                }
            }
            averageScoreMap.put("maxRegion", maxRegion);
            averageScoreMap.put("maxRegionScore", maxScore);
            averageScoreMap.put("minRegion", minRegion);
            averageScoreMap.put("minRegionScore", minScore);
        }
        averageScoreMap.put("averageAllScore", averageAllScore);
        averageScoreMap.put("allRecordTypeIdScore", this.transRegionScore(regionAverageScoreMap));
        return averageScoreMap;
    }

    /**
     * 区域-分数转化(没有的区域默认赋初值)
     *
     * @param regionAverageScoreMap
     * @return
     */
    private Object transRegionScore(Map<String, Double> regionAverageScoreMap) {
        //获取区域
        Integer parentId = 4;
        Map<String, String> regionMap = byBRegionManager.searchRegion(parentId);
        //当前项目集合中不存在的区域类型初始化数量值为0
        for (String s : regionMap.values()) {
            if (!regionAverageScoreMap.containsKey(s)) {
                regionAverageScoreMap.put(s, 0.0);
            }
        }
        return regionAverageScoreMap;
    }

    /**
     * 获取区域平均分
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
    private Map<String, Double> searchRegionAverageScore(String startDate, String endDate, String departmentCode, String workGroupCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId) {
        Map<String, Double> map = new HashMap<String, Double>();
        //获取区域
        Integer parentId = 4;
        Map<String, String> regionMap = byBRegionManager.searchRegion(parentId);
        Map<String, Integer> integerMap = this.valueTransKey(regionMap);
        List<ByPlusProject> byPlusTaskList = byPlusProjectMapper.searchRegionAverageScore(startDate, endDate, workGroupCode, departmentCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        for (ByPlusProject byPlusProject : byPlusTaskList) {
            String regionCode = byPlusProject.getRegionCode();
            Double score = byPlusProject.getTaskScore();
            map.put(regionMap.get(regionCode), score);
        }
        return map;
    }

    /**
     * 区域统计
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
    private Map<String, Object> searchRegionTaskCount(String startDate, String endDate, String departmentCode, String workGroupCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId) {
        Map<String, Object> map = new HashMap<String, Object>();
        //项目类型统计,flag=1
        Map<String, Integer> regionTypeMap = this.searchCountByRegionType(startDate, endDate, departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        int finishAllTask = byPlusProjectMapper.searchFinishAllProject(startDate, endDate, workGroupCode, departmentCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        //获取map中最大的和最小的(如果多个最大的或者最小的只取其中一个)
        if (regionTypeMap.size() == 0) {
            map.put("maxRegionCode", "无");
            map.put("maxRegionCodeCount", 0);
            map.put("minRegionCode", "无");
            map.put("minRegionCodeCount", 0);
        } else if (regionTypeMap.size() == 1) {
            for (String s : regionTypeMap.keySet()) {
                map.put("maxRegionCode", s);
                map.put("maxRegionCodeCount", regionTypeMap.get(s));
                map.put("minRegionCode", s);
                map.put("minRegionCodeCount", regionTypeMap.get(s));
            }
        } else {
            List<Map.Entry<String, Integer>> list = new TreeList<>(regionTypeMap.entrySet());
            Collections.sort(list, (o1, o2) -> (o1.getValue() - o2.getValue()));
            String minKey = list.get(0).getKey();
            String maxKey = list.get(list.size() - 1).getKey();
            map.put("maxRegionCode", maxKey);
            map.put("maxRegionCodeCount", regionTypeMap.get(maxKey));
            map.put("minRegionCode", minKey);
            map.put("minRegionCodeCount", regionTypeMap.get(minKey));
        }

        map.put("allRegion", this.transRegion(regionTypeMap));
        map.put("finishAllTask", finishAllTask);
        //由于最多的和最少的已取出,因此不需要在recordTypeMap中列出
        return map;
    }

    /**
     * 区域数值初始化(没有查询到的区域初值为0)
     *
     * @param regionTypeMap
     * @return
     */
    private Object transRegion(Map<String, Integer> regionTypeMap) {
        //获取区域
        Integer parentId = 4;
        Map<String, String> regionMap = byBRegionManager.searchRegion(parentId);
        //当前项目集合中不存在的区域类型初始化数量值为0
        for (String s : regionMap.values()) {
            if (!regionTypeMap.containsKey(s)) {
                regionTypeMap.put(s, 0);
            }
        }
        return regionTypeMap;
    }

    /**
     * 项目类型数值初始化(没有查询到的项目类型初值为0)
     *
     * @param recordTypeMap
     * @return
     */
    private Object transRecord(Map<String, Integer> recordTypeMap) {
        //获取项目类型
        String parentCode = "100100100";
        Map<String, String> mapStr = this.getProjectType(parentCode);
        for (String s : mapStr.values()) {
            if (!recordTypeMap.containsKey(s)) {
                recordTypeMap.put(s, 0);
            }
        }
        return recordTypeMap;
    }

    /**
     * 区域超期统计
     *
     * @param departmentCode
     * @param workGroupCode
     * @param oneInspectionUserId
     * @param twoInspectionUserId
     * @param threeInspectionUserId
     * @return
     */
    private Map<String, Object> searchOverdueRegionCount(String departmentCode, String workGroupCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId) {
        Map<String, Object> regionMap = new HashMap<String, Object>();
        //总超期数量
        String startDate = "";
        String endDate = "";
        List<ByPlusProject> byPlusProjectList = byPlusProjectMapper.searchOverdueRegionCount(startDate, endDate, workGroupCode, departmentCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        Map<String, Integer> map = new HashMap<String, Integer>();
        Integer parentId = 4;
        Map<String, String> regionNameMap = byBRegionManager.searchRegion(parentId);
        //count超期总数
        int count = 0;
        for (ByPlusProject byPlusProject : byPlusProjectList) {
            String key = byPlusProject.getRegionCode();
            Integer regionCount = byPlusProject.getCount();
            map.put(regionNameMap.get(key), regionCount);
            count += regionCount.intValue();
        }
        if (map.size() == 0) {
            regionMap.put("maxRegionCode", "无");
            regionMap.put("maxRegionCount", 0);
            regionMap.put("minRegionCode", "无");
            regionMap.put("minRegionCount", 0);
        } else if (map.size() == 0) {
            for (String s : map.keySet()) {
                regionMap.put("maxRegionCode", s);
                regionMap.put("maxRegionCount", map.get(s));
                regionMap.put("minRegionCode", s);
                regionMap.put("minRegionCount", map.get(s));
            }
        } else {
            List<Map.Entry<String, Integer>> list = new TreeList<>(map.entrySet());
            Collections.sort(list, (o1, o2) -> (o1.getValue() - o2.getValue()));
            String minKey = list.get(0).getKey();
            String maxKey = list.get(list.size() - 1).getKey();
            regionMap.put("maxRegionCode", maxKey);
            regionMap.put("maxRegionCount", map.get(maxKey));
            regionMap.put("minRegionCode", minKey);
            regionMap.put("minRegionCount", map.get(minKey));
        }
        regionMap.put("allRegionCode", this.transRegion(map));
        regionMap.put("allOverdueTaskCount", count);
        return regionMap;
    }

    /**
     * 任务分数统计
     *
     * @param startDate
     * @param endDate
     * @param departmentCode
     * @param workGroupCode
     * @param oneInspectionUserId
     * @param twoInspectionUserId
     * @param threeInspectionUserId
     * @param flag
     * @return
     */
    private Map<String, Object> searchTaskScoreRecordTypeCount(String startDate, String endDate, String departmentCode, String workGroupCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId, Integer flag) {
        Map<String, Object> averageScoreMap = new HashMap<String, Object>();
        //总平均分
        Double averageAllScore = 0.0;
        Double averageAllScore1 = byPlusProjectMapper.searchAverageScore(startDate, endDate, workGroupCode, departmentCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        //如果查询到的平均分为null,赋处置为0
        if (averageAllScore1 != null) {
            averageAllScore = averageAllScore1;
        }
        //类型平均分
        Map<String, Double> recordTypeAverageScoreMap = this.searchRecordTypeAverageScore(startDate, endDate, departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        if (flag == 1) {
            if (recordTypeAverageScoreMap.size() == 0) {
                averageScoreMap.put("maxRecordType", "无");
                averageScoreMap.put("maxRecordTypeScore", 0.0);
                averageScoreMap.put("minRecordType", "无");
                averageScoreMap.put("minRecordTypeScore", 0.0);
            } else if (recordTypeAverageScoreMap.size() == 1) {
                for (String s : recordTypeAverageScoreMap.keySet()) {
                    averageScoreMap.put("maxRecordType", s);
                    averageScoreMap.put("maxRecordTypeScore", recordTypeAverageScoreMap.get(s));
                    averageScoreMap.put("minRecordType", s);
                    averageScoreMap.put("minRecordTypeScore", recordTypeAverageScoreMap.get(s));
                }
            } else {
                Collection<Double> valueCollection = recordTypeAverageScoreMap.values();
                List<Double> valueList = new ArrayList<>(valueCollection);
                Collections.sort(valueList);
                //最小和最大的分数
                Double minScore = valueList.get(0);
                Double maxScore = valueList.get(valueList.size() - 1);
                String minRecord = null;
                String maxRecord = null;
                for (Map.Entry<String, Double> stringDoubleEntry : recordTypeAverageScoreMap.entrySet()) {
                    if (stringDoubleEntry.getValue() == minScore) {
                        minRecord = stringDoubleEntry.getKey();
                    }
                    if (stringDoubleEntry.getValue() == maxScore) {
                        maxRecord = stringDoubleEntry.getKey();
                    }
                }
                averageScoreMap.put("maxRecordType", maxRecord);
                averageScoreMap.put("maxRecordTypeScore", maxScore);
                averageScoreMap.put("minRecordType", minRecord);
                averageScoreMap.put("minRecordTypeScore", minScore);
            }
            averageScoreMap.put("averageAllScore", averageAllScore);
        }
        averageScoreMap.put("allRecordTypeIdScore", this.transRecordScore(recordTypeAverageScoreMap));
        return averageScoreMap;
    }

    /**
     * 获取项目分类平均分
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
    private Map<String, Double> searchRecordTypeAverageScore(String startDate, String endDate, String departmentCode, String workGroupCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId) {
        Map<String, Double> map = new HashMap<String, Double>();
        String parentCode = "100100100";
        Map<String, String> mapStr = this.getProjectType(parentCode);
        List<ByPlusProject> byPlusTaskList = byPlusProjectMapper.searchRecordTypeAverageScore(startDate, endDate, workGroupCode, departmentCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        for (ByPlusProject byPlusProject : byPlusTaskList) {
            Integer recordTypeId = byPlusProject.getRecordTypeId();
            Double score = byPlusProject.getTaskScore();
            map.put(mapStr.get(recordTypeId.toString()), score);
        }
        return map;
    }


    /**
     * 总的任务数量
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
    private Map<String, Object> searchOverdueTaskCount(String startDate, String endDate, String departmentCode, String workGroupCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId) {
        Map<String, Object> map = new HashMap<String, Object>();
        //项目类型统计,projectStateId = 79为项目已完成入档,等同于flag=1
        Integer projectStateId = 79;
        Map<String, Integer> recordTypeMap = this.searchCountByRecordType(startDate, endDate, departmentCode, workGroupCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        int finishAllTask = byPlusProjectMapper.searchFinishAllProject(startDate, endDate, workGroupCode, departmentCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        //获取map中最大的和最小的(如果多个最大的或者最小的只取其中一个)
        if (recordTypeMap.size() == 0) {
            map.put("maxRecordType", "无");
            map.put("maxRecordTypeCount", 0);
            map.put("minRecordType", "无");
            map.put("minRecordTypeCount", 0);
        } else if (recordTypeMap.size() == 1) {
            for (String s : recordTypeMap.keySet()) {
                map.put("maxRecordType", s);
                map.put("maxRecordTypeCount", recordTypeMap.get(s));
                map.put("minRecordType", s);
                map.put("minRecordTypeCount", recordTypeMap.get(s));
            }
        } else {
            List<Map.Entry<String, Integer>> list = new TreeList<>(recordTypeMap.entrySet());
            Collections.sort(list, (o1, o2) -> (o1.getValue() - o2.getValue()));
            String minKey = list.get(0).getKey();
            String maxKey = list.get(list.size() - 1).getKey();
            map.put("maxRecordType", maxKey);
            map.put("maxRecordTypeCount", recordTypeMap.get(maxKey));
            map.put("minRecordType", minKey);
            map.put("minRecordTypeCount", recordTypeMap.get(minKey));
        }
        map.put("allRecordType", this.transRecord(recordTypeMap));
        map.put("finishAllTask", finishAllTask);
        return map;
    }

    /**
     * 按类型统计--超期统计
     *
     * @param departmentCode
     * @param workGroupCode
     * @param oneInspectionUserId
     * @param twoInspectionUserId
     * @param threeInspectionUserId
     * @return
     */
    private Map<String, Object> searchOverdueCount(String departmentCode, String workGroupCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId) {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        String startDate = "";
        String endDate = "";
        List<ByPlusProject> byPlusProjectList = byPlusProjectMapper.searchOverdueRecordTypeList(startDate, endDate, workGroupCode, departmentCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        //获取项目类型
        Map<String, Integer> map = new HashMap<>();
        String parentCode = "100100100";
        Map<String, String> mapStr = this.getProjectType(parentCode);
        int overCount = 0;
        for (ByPlusProject byPlusProject : byPlusProjectList) {
            Integer count = byPlusProject.getCount();
            overCount += count.intValue();
            Integer recordTypeId = byPlusProject.getRecordTypeId();
            String key = recordTypeId.toString();
            map.put(mapStr.get(key), count);
        }
        objectMap.put("totalCount", overCount);
        //获取map中最大的和最小的(如果多个最大的或者最小的只取其中一个)
        if (map.size() == 0) {
            objectMap.put("maxRecordType", "无");
            objectMap.put("maxRecordTypeCount", 0);
            objectMap.put("minRecordType", "无");
            objectMap.put("minRecordTypeCount", 0);
        } else if (map.size() == 1) {
            for (String s : map.keySet()) {
                objectMap.put("maxRecordType", s);
                objectMap.put("maxRecordTypeCount", map.get(s));
                objectMap.put("minRecordType", s);
                objectMap.put("minRecordTypeCount", map.get(s));
            }
        } else {
            List<Map.Entry<String, Integer>> list = new TreeList<>(map.entrySet());
            Collections.sort(list, (o1, o2) -> (o1.getValue() - o2.getValue()));
            String minKey = list.get(0).getKey();
            String maxKey = list.get(list.size() - 1).getKey();
            objectMap.put("maxRecordType", maxKey);
            objectMap.put("maxRecordTypeCount", map.get(maxKey));
            objectMap.put("minRecordType", minKey);
            objectMap.put("minRecordTypeCount", map.get(minKey));
        }
        objectMap.put("otherRecordType", this.transRecord(map));
        return objectMap;
    }

    /**
     * 超期档案类型统计
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
    private Map<String, Integer> searchCountByOverdueRecordType(String startDate, String endDate, String departmentCode, String workGroupCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId) {
        List<ByPlusProject> byPlusProjectList = byPlusProjectMapper.searchOverdueRecordTypeList(startDate, endDate, workGroupCode, departmentCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        //获取项目类型
        String parentCode = "100100100";
        Map<String, Integer> map = new HashMap<>();
        Map<String, String> mapStr = this.getProjectType(parentCode);
        for (ByPlusProject byPlusProject : byPlusProjectList) {
            Integer recordTypeId = byPlusProject.getRecordTypeId();
            String key = recordTypeId.toString();
            map.put(mapStr.get(key), byPlusProject.getCount());
        }
        return map;
    }

    /**
     * 按分数统计
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
    private Map<String, Object> searchCountByScore(String startDate, String endDate, String departmentCode, String workGroupCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId) {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        List<ByPlusTask> byPlusTaskList = byPlusProjectMapper.searchTaskScore(startDate, endDate, workGroupCode, departmentCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        Integer totalProjectCount = byPlusTaskList.size();
        //获取分数段
        Map<String, Integer> scoreSection = this.getScoreSection();
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (ByPlusTask byPlusTask : byPlusTaskList) {
            Double taskScore = byPlusTask.getTaskScore();
            if (taskScore < 60) {
                String key = "0-60";
                this.searchOverDeal(map, key);
            } else if (taskScore >= 60 && taskScore < 70) {
                String key = "60-70";
                this.searchOverDeal(map, key);
            } else if (taskScore >= 70 && taskScore < 80) {
                String key = "70-80";
                this.searchOverDeal(map, key);
            } else if (taskScore >= 80 && taskScore < 90) {
                String key = "80-90";
                this.searchOverDeal(map, key);
            } else if (taskScore >= 90 && taskScore < 100) {
                String key = "90-100";
                this.searchOverDeal(map, key);
            } else if (taskScore == 100) {
                String key = "100";
                this.searchOverDeal(map, key);
            }
        }
        //当前项目集合中不存在的分数区间初始化数量值为0
        for (String s : scoreSection.keySet()) {
            if (map.containsKey(s)) {
                scoreSection.put(s, map.get(s));
            }
        }
        //获取map中最大的和最小的(如果多个最大的或者最小的只取其中一个)
        if (map.size() == 0) {
            objectMap.put("maxScoreArea", "无");
            objectMap.put("maxScoreAreaCount", 0);
            objectMap.put("minScoreArea", "无");
            objectMap.put("minScoreAreaCount", 0);
        } else if (map.size() == 1) {
            for (String s : map.keySet()) {
                objectMap.put("maxScoreArea", s);
                objectMap.put("maxScoreAreaCount", map.get(s));
                objectMap.put("minScoreArea", s);
                objectMap.put("minScoreAreaCount", map.get(s));
            }
        } else {
            List<Map.Entry<String, Integer>> list = new TreeList<>(map.entrySet());
            Collections.sort(list, (o1, o2) -> (o1.getValue() - o2.getValue()));
            String minKey = list.get(0).getKey();
            String maxKey = list.get(list.size() - 1).getKey();
            objectMap.put("maxScoreArea", maxKey);
            objectMap.put("maxScoreAreaCount", map.get(maxKey));
            objectMap.put("minScoreArea", minKey);
            objectMap.put("minScoreAreaCount", map.get(minKey));
        }
        objectMap.put("allScoreTypeMap", scoreSection);
        objectMap.put("totalProjectCount", totalProjectCount);
        return objectMap;
    }

    /**
     * 分数处理/超期天数处理
     *
     * @param key
     * @param map
     */
    private void searchOverDeal(Map<String, Integer> map, String key) {
        if (map.containsKey(key)) {
            Integer value = map.get(key);
            Integer newValue = value.intValue() + 1;
            map.put(key, newValue);
        } else {
            map.put(key, 1);
        }
    }

    /**
     * 分数区间
     *
     * @return
     */
    private Map<String, Integer> getScoreSection() {
        Map<String, Integer> map = new LinkedHashMap<String, Integer>();
        //分数区间含左不含右
        map.put("0-60", 0);
        map.put("60-70", 0);
        map.put("70-80", 0);
        map.put("80-90", 0);
        map.put("90-100", 0);
        map.put("100", 0);
        return map;
    }

    /**
     * 按区域查询统计
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
    private Map<String, Integer> searchCountByRegionType(String startDate, String endDate, String departmentCode, String workGroupCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId) {
        List<ByPlusProject> byPlusProjectList = byPlusProjectMapper.searchRegionCount(startDate, endDate, workGroupCode, departmentCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        return this.getRegionMap(byPlusProjectList);
    }

    /**
     * 以区域分组统计(当前只统计西安市内的所有区域)
     *
     * @param regionFlag
     * @param byPlusProjectList
     * @return
     */
    private Map<String, Integer> getRegionMap(List<ByPlusProject> byPlusProjectList) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        //获取区域
        Integer parentId = 4;
        Map<String, String> regionMap = byBRegionManager.searchRegion(parentId);
        Map<String, Integer> integerMap = this.valueTransKey(regionMap);
        for (ByPlusProject byPlusProject : byPlusProjectList) {
            String key = byPlusProject.getRegionCode();
            Integer count = byPlusProject.getCount();
            map.put(regionMap.get(key), count);
        }
        return map;
    }


    /**
     * 根据集合list,parentCode统计类型数量(parentCode下的所有子项)
     *
     * @param byPlusProjectList
     * @param parentCode
     * @return
     */
    private Map<String, Integer> getMap(List<ByPlusProject> byPlusProjectList, String parentCode) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        Map<String, String> mapStr = this.getProjectType(parentCode);
        //mapStr值作为键,值为0
        for (ByPlusProject byPlusProject : byPlusProjectList) {
            Integer recordTypeId = byPlusProject.getRecordTypeId();
            String key = recordTypeId.toString();
            Integer count = byPlusProject.getCount();
            map.put(mapStr.get(key), count);
        }
        return map;
    }


    /**
     * 键和值转换
     *
     * @param mapStr
     * @return
     */
    private Map<String, Integer> valueTransKey(Map<String, String> mapStr) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (String s : mapStr.keySet()) {
            map.put(mapStr.get(s), 0);
        }
        return map;
    }

    /**
     * 按项目类型查询
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
    private Map<String, Integer> searchCountByRecordType(String startDate, String endDate, String departmentCode, String workGroupCode, Integer oneInspectionUserId, Integer twoInspectionUserId, Integer threeInspectionUserId) {
        List<ByPlusProject> byPlusProjectList = byPlusProjectMapper.searchRecordTypeCount(startDate, endDate, workGroupCode, departmentCode, oneInspectionUserId, twoInspectionUserId, threeInspectionUserId);
        //获取项目类型
        String parentCode = "100100100";
        Map<String, Integer> map = this.getMap(byPlusProjectList, parentCode);
        return map;
    }

    /**
     * 获取不同类型的选项值
     * parentCode
     *
     * @return map
     */
    private Map<String, String> getProjectType(String parentCode) {
        Map<String, String> map = new HashMap<String, String>();
        FlipFilter flipFilter = new FlipFilter(ByBDictionaryChild.class);
        flipFilter.addSearch(parentCode, Operate.EQUAL, "dictionaryCode");
        List<ByBDictionaryChild> byBDictionaryChildList = byBDictionaryChildManager.list(flipFilter);
        for (ByBDictionaryChild byBDictionaryChild : byBDictionaryChildList) {
            map.put(byBDictionaryChild.getId().toString(), byBDictionaryChild.getAlias());
        }
        return map;
    }

}