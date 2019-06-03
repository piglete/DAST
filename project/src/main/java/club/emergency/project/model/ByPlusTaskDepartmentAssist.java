package club.emergency.project.model;

import club.map.core.mapper.ColumnTransient;
import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("by_plus_task_department_assist")
public class ByPlusTaskDepartmentAssist extends RootObject {
    private Integer taskId;//任务id

    private String departmentCode;//协助部门

    private String departmentName;//部门名称

    private Integer revertUserId;//部门接收人id

    private String revertUser;//部门接收人

    private String releaseDate;//部门任务下达时间

    private String releaseRequire;//部门协作要求

    private String departmentRevertDate;//部门接收时间

    private String departmentFinishDate;//部门完成时间

    private String departmentFinishPeriod;//完成周期

    private Integer needDayCount;//作业要求天数

    /**
     * 外协任务进行状态标识(-1为外协任务未下达,0为外协任务下达,1为外协部门接收,2为外协部门下达,3为外协小组接收,4为外协小组成,
     * 5为外协一检接收,6为外协一检完成,7为(主部门下的)小组接收,8为(主部门下的)小组确定完成,9为(主部门下的)小组回退)
     */
    private Integer stateFlag;

    private Integer returnCount;//回退次数(0为没有回退,其他为回退次数)

    private String startDate;//外协任务开始时间

    private ByPlusTask byPlusTask;//任务表信息

    private String groupCode;//外协部门小组code

    private String groupName;//小组名称

    private Integer groupRevertUserId;//外协部门小组接收人id

    private String groupRevertUser;//外协部门小组接收人

    private String groupRevertDate;//小组接收时间

    private String groupFinishDate;//小组完成时间

    private String groupFinishPeriod;//小组完成周期

    private Integer finishFlag;//外协任务完成标识(0为未完成,1为已完成)

    private Integer oneInspectionUserId;//一检人员id

    private String oneInspectionUser;//一检人员

    private String oneInspectionRevertDate;//一检接收时间

    private String oneInspectionFinishDate;//一检完成时间

    private String oneInspectionFinishPeriod;//一检完成周期

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public Integer getRevertUserId() {
        return revertUserId;
    }

    public void setRevertUserId(Integer revertUserId) {
        this.revertUserId = revertUserId;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReleaseRequire() {
        return releaseRequire;
    }

    public void setReleaseRequire(String releaseRequire) {
        this.releaseRequire = releaseRequire;
    }

    public String getDepartmentRevertDate() {
        return departmentRevertDate;
    }

    public void setDepartmentRevertDate(String departmentRevertDate) {
        this.departmentRevertDate = departmentRevertDate;
    }

    public String getDepartmentFinishDate() {
        return departmentFinishDate;
    }

    public void setDepartmentFinishDate(String departmentFinishDate) {
        this.departmentFinishDate = departmentFinishDate;
    }

    public String getDepartmentFinishPeriod() {
        return departmentFinishPeriod;
    }

    public void setDepartmentFinishPeriod(String departmentFinishPeriod) {
        this.departmentFinishPeriod = departmentFinishPeriod;
    }

    public Integer getNeedDayCount() {
        return needDayCount;
    }

    public void setNeedDayCount(Integer needDayCount) {
        this.needDayCount = needDayCount;
    }

    public Integer getStateFlag() {
        return stateFlag;
    }

    public void setStateFlag(Integer stateFlag) {
        this.stateFlag = stateFlag;
    }

    public Integer getReturnCount() {
        return returnCount;
    }

    public void setReturnCount(Integer returnCount) {
        this.returnCount = returnCount;
    }

    @ColumnTransient
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @ColumnTransient
    public String getRevertUser() {
        return revertUser;
    }

    public void setRevertUser(String revertUser) {
        this.revertUser = revertUser;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @ColumnTransient
    public ByPlusTask getByPlusTask() {
        return byPlusTask;
    }

    public void setByPlusTask(ByPlusTask byPlusTask) {
        this.byPlusTask = byPlusTask;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public Integer getGroupRevertUserId() {
        return groupRevertUserId;
    }

    public void setGroupRevertUserId(Integer groupRevertUserId) {
        this.groupRevertUserId = groupRevertUserId;
    }

    public String getGroupRevertDate() {
        return groupRevertDate;
    }

    public void setGroupRevertDate(String groupRevertDate) {
        this.groupRevertDate = groupRevertDate;
    }

    public String getGroupFinishDate() {
        return groupFinishDate;
    }

    public void setGroupFinishDate(String groupFinishDate) {
        this.groupFinishDate = groupFinishDate;
    }

    public String getGroupFinishPeriod() {
        return groupFinishPeriod;
    }

    public void setGroupFinishPeriod(String groupFinishPeriod) {
        this.groupFinishPeriod = groupFinishPeriod;
    }

    @ColumnTransient
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @ColumnTransient
    public String getGroupRevertUser() {
        return groupRevertUser;
    }

    public void setGroupRevertUser(String groupRevertUser) {
        this.groupRevertUser = groupRevertUser;
    }

    public Integer getFinishFlag() {
        return finishFlag;
    }

    public void setFinishFlag(Integer finishFlag) {
        this.finishFlag = finishFlag;
    }

    public Integer getOneInspectionUserId() {
        return oneInspectionUserId;
    }

    public void setOneInspectionUserId(Integer oneInspectionUserId) {
        this.oneInspectionUserId = oneInspectionUserId;
    }

    public String getOneInspectionRevertDate() {
        return oneInspectionRevertDate;
    }

    public void setOneInspectionRevertDate(String oneInspectionRevertDate) {
        this.oneInspectionRevertDate = oneInspectionRevertDate;
    }

    public String getOneInspectionFinishDate() {
        return oneInspectionFinishDate;
    }

    public void setOneInspectionFinishDate(String oneInspectionFinishDate) {
        this.oneInspectionFinishDate = oneInspectionFinishDate;
    }

    public String getOneInspectionFinishPeriod() {
        return oneInspectionFinishPeriod;
    }

    public void setOneInspectionFinishPeriod(String oneInspectionFinishPeriod) {
        this.oneInspectionFinishPeriod = oneInspectionFinishPeriod;
    }

    @ColumnTransient
    public String getOneInspectionUser() {
        return oneInspectionUser;
    }

    public void setOneInspectionUser(String oneInspectionUser) {
        this.oneInspectionUser = oneInspectionUser;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", taskId=").append(taskId);
        sb.append(", departmentCode=").append(departmentCode);
        sb.append(", revertUserId=").append(revertUserId);
        sb.append(", releaseDate=").append(releaseDate);
        sb.append(", releaseRequire=").append(releaseRequire);
        sb.append(", departmentRevertDate=").append(departmentRevertDate);
        sb.append(", departmentFinishDate=").append(departmentFinishDate);
        sb.append(", departmentFinishPeriod=").append(departmentFinishPeriod);
        sb.append(", needDayCount=").append(needDayCount);
        sb.append(", stateFlag=").append(stateFlag);
        sb.append(", returnCount=").append(returnCount);
        sb.append(", startDate=").append(startDate);
        sb.append(", groupCode=").append(groupCode);
        sb.append(", groupRevertUserId=").append(groupRevertUserId);
        sb.append(", groupRevertDate=").append(groupRevertDate);
        sb.append(", groupFinishDate=").append(groupFinishDate);
        sb.append(", groupFinishPeriod=").append(groupFinishPeriod);
        sb.append(", finishFlag=").append(finishFlag);
        sb.append(", oneInspectionUserId=").append(oneInspectionUserId);
        sb.append(", oneInspectionRevertDate=").append(oneInspectionRevertDate);
        sb.append(", oneInspectionFinishDate=").append(oneInspectionFinishDate);
        sb.append(", oneInspectionFinishPeriod=").append(oneInspectionFinishPeriod);
        sb.append("]");
        return sb.toString();
    }
}