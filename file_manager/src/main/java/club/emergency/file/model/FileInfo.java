package club.emergency.file.model;

/**
 * Created with IntelliJ IDEA.
 * User: zhaojinxiong
 * Date: 2019-01-02
 * Time: 11:39
 * Description:
 */
public class FileInfo {

    private String fileName;//文件真实名称

    private String fileSize;//文件大小

    private String fileUrl;//文件保存路径

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", fileName=").append(fileName);
        sb.append(", fileSize=").append(fileSize);
        sb.append(", fileUrl=").append(fileUrl);
        sb.append("]");
        return sb.toString();
    }
}
