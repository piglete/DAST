package club.emergency.project.model;

import club.map.core.mapper.ColumnTransient;
import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("by_plus_record_comparison")
public class ByPlusRecordComparison extends RootObject {
    private Integer projectId;//项目id

    private String projectName;//项目名称

    private Integer recordModelId;//档案资料模板id

    private String recordModel;//档案资料模

    private Integer existFlag;//资料提交标示(0为未提交,1为已提交)

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getRecordModelId() {
        return recordModelId;
    }

    public void setRecordModelId(Integer recordModelId) {
        this.recordModelId = recordModelId;
    }

    @ColumnTransient
    public String getRecordModel() {
        return recordModel;
    }

    public void setRecordModel(String recordModel) {
        this.recordModel = recordModel;
    }

    public Integer getExistFlag() {
        return existFlag;
    }

    public void setExistFlag(Integer existFlag) {
        this.existFlag = existFlag;
    }

    @ColumnTransient
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", projectId=").append(projectId);
        sb.append(", recordModelId=").append(recordModelId);
        sb.append(", existFlag=").append(existFlag);
        sb.append("]");
        return sb.toString();
    }
}