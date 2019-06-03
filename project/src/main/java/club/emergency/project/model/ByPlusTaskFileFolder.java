package club.emergency.project.model;

import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("by_plus_task_file_folder")
public class ByPlusTaskFileFolder extends RootObject {
    private Integer parentId;//父节点

    private Integer projectId;//项目编号

    private String folderName;//文件夹名称

    private String folderFullName;//文件夹全称

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderFullName() {
        return folderFullName;
    }

    public void setFolderFullName(String folderFullName) {
        this.folderFullName = folderFullName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", parentId=").append(parentId);
        sb.append(", projectId=").append(projectId);
        sb.append(", folderName=").append(folderName);
        sb.append(", folderFullName=").append(folderFullName);
        sb.append("]");
        return sb.toString();
    }
}