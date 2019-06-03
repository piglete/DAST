package club.emergency.project.model;

import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("v_record_file")
public class VRecordFile extends RootObject {
    private Integer parentId;//父节点

    private Integer projectId;//项目编号

    private String folderName;//文件夹名称

    private String folderFullName;//文件夹全称

    private String fileName;//文件名称

    private String fileSize;//文件大小

    private String fileUrl;//文件路径

    private String fileRename;//文件重命名

    private String commitUser;//提交人

    private Integer resultTypeId;//成果类型id

    private Integer fileUsingType;

    private String name;//项目名称

    private Integer projectUsingType;

    private Integer recordTypeId;//项目类型id

    private String regionCode;//区域code

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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileRename() {
        return fileRename;
    }

    public void setFileRename(String fileRename) {
        this.fileRename = fileRename;
    }

    public String getCommitUser() {
        return commitUser;
    }

    public void setCommitUser(String commitUser) {
        this.commitUser = commitUser;
    }

    public Integer getResultTypeId() {
        return resultTypeId;
    }

    public void setResultTypeId(Integer resultTypeId) {
        this.resultTypeId = resultTypeId;
    }

    public Integer getFileUsingType() {
        return fileUsingType;
    }

    public void setFileUsingType(Integer fileUsingType) {
        this.fileUsingType = fileUsingType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProjectUsingType() {
        return projectUsingType;
    }

    public void setProjectUsingType(Integer projectUsingType) {
        this.projectUsingType = projectUsingType;
    }

    public Integer getRecordTypeId() {
        return recordTypeId;
    }

    public void setRecordTypeId(Integer recordTypeId) {
        this.recordTypeId = recordTypeId;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
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
        sb.append(", fileName=").append(fileName);
        sb.append(", fileSize=").append(fileSize);
        sb.append(", fileUrl=").append(fileUrl);
        sb.append(", fileRename=").append(fileRename);
        sb.append(", commitUser=").append(commitUser);
        sb.append(", resultTypeId=").append(resultTypeId);
        sb.append(", fileUsingType=").append(fileUsingType);
        sb.append(", name=").append(name);
        sb.append(", projectUsingType=").append(projectUsingType);
        sb.append(", recordTypeId=").append(recordTypeId);
        sb.append(", regionCode=").append(regionCode);
        sb.append("]");
        return sb.toString();
    }
}