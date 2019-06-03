package club.emergency.project.model;

import club.map.core.mapper.ColumnTransient;
import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("by_plus_document")
public class ByPlusDocument extends RootObject {
    private Integer projectId;//项目id

    private String title;//标题

    private String fileName;//文件名称

    private String fileSize;//文件大小

    private String departmentCode;//上传部门code

    private String departmentName;//部门

    private String uploadUser;//上传用户

    private String remark;//备注

    private String fileUrl;//文件路径

    private String fileRename;//文件重命名

    private String fileType;//文件类型

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getUploadUser() {
        return uploadUser;
    }

    public void setUploadUser(String uploadUser) {
        this.uploadUser = uploadUser;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @ColumnTransient
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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
        sb.append(", projectId=").append(projectId);
        sb.append(", title=").append(title);
        sb.append(", fileName=").append(fileName);
        sb.append(", fileSize=").append(fileSize);
        sb.append(", departmentCode=").append(departmentCode);
        sb.append(", uploadUser=").append(uploadUser);
        sb.append(", remark=").append(remark);
        sb.append(", fileUrl=").append(fileUrl);
        sb.append(", fileRename=").append(fileRename);
        sb.append(", fileType=").append(fileType);
        sb.append("]");
        return sb.toString();
    }
}