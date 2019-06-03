package club.emergency.project.model;

import club.map.core.mapper.ColumnTransient;
import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("by_plus_operation_standard")
public class ByPlusOperationStandard extends RootObject {
    private String fileName;//文件名称

    private String fileSize;//文件大小

    private String fileUrl;//文件保存路径

    private String title;//标题

    private String uploadUser;//上传人

    private String remark;//备注

    private String fileRename;//文件重命名

    private String fileType;//文件类型

    private Integer fileClassifyId;//文件分类id

    private String fileClassifyName;//文件分类名称

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Integer getFileClassifyId() {
        return fileClassifyId;
    }

    public void setFileClassifyId(Integer fileClassifyId) {
        this.fileClassifyId = fileClassifyId;
    }

    @ColumnTransient
    public String getFileClassifyName() {
        return fileClassifyName;
    }

    public void setFileClassifyName(String fileClassifyName) {
        this.fileClassifyName = fileClassifyName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", fileName=").append(fileName);
        sb.append(", fileSize=").append(fileSize);
        sb.append(", fileUrl=").append(fileUrl);
        sb.append(", title=").append(title);
        sb.append(", uploadUser=").append(uploadUser);
        sb.append(", remark=").append(remark);
        sb.append(", fileRename=").append(fileRename);
        sb.append(", fileType=").append(fileType);
        sb.append(", fileClassifyId=").append(fileClassifyId);
        sb.append("]");
        return sb.toString();
    }
}