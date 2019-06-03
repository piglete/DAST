package club.emergency.project.model;

import club.map.core.mapper.ColumnTransient;
import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("by_plus_task_file")
public class ByPlusTaskFile extends RootObject {
//    private Integer projectId;//项目id

    private String fileName;//文件名称

    private String fileSize;//文件大小

    private Integer resultTypeId;//成果类型id(字典)

    private String resultType;//成果类型

    private String commitUser;//提交人

    private String pinyin;//拼音

    private String fileUrl;//文件路径

    private String fileRename;//文件重命名

    private String fileType;//文件类型
//
//    public Integer getProjectId() {
//        return projectId;
//    }
//
//    public void setProjectId(Integer projectId) {
//        this.projectId = projectId;
//    }

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

    public Integer getResultTypeId() {
        return resultTypeId;
    }

    public void setResultTypeId(Integer resultTypeId) {
        this.resultTypeId = resultTypeId;
    }

    public String getCommitUser() {
        return commitUser;
    }

    public void setCommitUser(String commitUser) {
        this.commitUser = commitUser;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @ColumnTransient
    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getFileRename() {
        return fileRename;
    }

    public void setFileRename(String fileRename) {
        this.fileRename = fileRename;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
//        sb.append(", projectId=").append(projectId);
        sb.append(", fileName=").append(fileName);
        sb.append(", fileSize=").append(fileSize);
        sb.append(", resultTypeId=").append(resultTypeId);
        sb.append(", commitUser=").append(commitUser);
        sb.append(", pinyin=").append(pinyin);
        sb.append(", fileUrl=").append(fileUrl);
        sb.append(", fileRename=").append(fileRename);
        sb.append(", fileType=").append(fileType);
        sb.append("]");
        return sb.toString();
    }
}