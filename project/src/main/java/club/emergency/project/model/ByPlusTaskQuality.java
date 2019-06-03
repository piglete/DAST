package club.emergency.project.model;

import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("by_plus_task_quality")
public class ByPlusTaskQuality extends RootObject {
    private Integer taskId;//任务id

    private Integer dataOneError;//数据及结构正确性A类错误数

    private Integer dataTwoError;//数据及结构正确性B类错误数

    private Integer dataThreeError;//数据及结构正确性C类错误数

    private Integer dataFourError;//数据及结构正确性D类错误数

    private Integer geographicOneError;//地理精度A类错误数

    private Integer geographicTwoError;//地理精度B类错误数

    private Integer geographicThreeError;//地理精度C类错误数

    private Integer geographicFourError;//地理精度D类错误数

    private Integer groomOneError;//整饰质量A类错误数

    private Integer groomTwoError;//整饰质量B类错误数

    private Integer groomThreeError;//整饰质量C类错误数

    private Integer groomFourError;//整饰质量D类错误数

    private Integer attachmentOneError;//附件质量A类错误数

    private Integer attachmentTwoError;//附件质量B类错误数

    private Integer attachmentThreeError;//附件质量C类错误数

    private Integer attachmentFourError;//附件质量D类错误数

    private String assessUser;//评定人员

    private String assessDate;//评定日期

    private Integer checkDepartment;//质检部门(2为二检,3为三检)

    private Double score;//得分

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getDataOneError() {
        return dataOneError;
    }

    public void setDataOneError(Integer dataOneError) {
        this.dataOneError = dataOneError;
    }

    public Integer getDataTwoError() {
        return dataTwoError;
    }

    public void setDataTwoError(Integer dataTwoError) {
        this.dataTwoError = dataTwoError;
    }

    public Integer getDataThreeError() {
        return dataThreeError;
    }

    public void setDataThreeError(Integer dataThreeError) {
        this.dataThreeError = dataThreeError;
    }

    public Integer getDataFourError() {
        return dataFourError;
    }

    public void setDataFourError(Integer dataFourError) {
        this.dataFourError = dataFourError;
    }

    public Integer getGeographicOneError() {
        return geographicOneError;
    }

    public void setGeographicOneError(Integer geographicOneError) {
        this.geographicOneError = geographicOneError;
    }

    public Integer getGeographicTwoError() {
        return geographicTwoError;
    }

    public void setGeographicTwoError(Integer geographicTwoError) {
        this.geographicTwoError = geographicTwoError;
    }

    public Integer getGeographicThreeError() {
        return geographicThreeError;
    }

    public void setGeographicThreeError(Integer geographicThreeError) {
        this.geographicThreeError = geographicThreeError;
    }

    public Integer getGeographicFourError() {
        return geographicFourError;
    }

    public void setGeographicFourError(Integer geographicFourError) {
        this.geographicFourError = geographicFourError;
    }

    public Integer getGroomOneError() {
        return groomOneError;
    }

    public void setGroomOneError(Integer groomOneError) {
        this.groomOneError = groomOneError;
    }

    public Integer getGroomTwoError() {
        return groomTwoError;
    }

    public void setGroomTwoError(Integer groomTwoError) {
        this.groomTwoError = groomTwoError;
    }

    public Integer getGroomThreeError() {
        return groomThreeError;
    }

    public void setGroomThreeError(Integer groomThreeError) {
        this.groomThreeError = groomThreeError;
    }

    public Integer getGroomFourError() {
        return groomFourError;
    }

    public void setGroomFourError(Integer groomFourError) {
        this.groomFourError = groomFourError;
    }

    public Integer getAttachmentOneError() {
        return attachmentOneError;
    }

    public void setAttachmentOneError(Integer attachmentOneError) {
        this.attachmentOneError = attachmentOneError;
    }

    public Integer getAttachmentTwoError() {
        return attachmentTwoError;
    }

    public void setAttachmentTwoError(Integer attachmentTwoError) {
        this.attachmentTwoError = attachmentTwoError;
    }

    public Integer getAttachmentThreeError() {
        return attachmentThreeError;
    }

    public void setAttachmentThreeError(Integer attachmentThreeError) {
        this.attachmentThreeError = attachmentThreeError;
    }

    public Integer getAttachmentFourError() {
        return attachmentFourError;
    }

    public void setAttachmentFourError(Integer attachmentFourError) {
        this.attachmentFourError = attachmentFourError;
    }

    public String getAssessUser() {
        return assessUser;
    }

    public void setAssessUser(String assessUser) {
        this.assessUser = assessUser;
    }

    public String getAssessDate() {
        return assessDate;
    }

    public void setAssessDate(String assessDate) {
        this.assessDate = assessDate;
    }

    public Integer getCheckDepartment() {
        return checkDepartment;
    }

    public void setCheckDepartment(Integer checkDepartment) {
        this.checkDepartment = checkDepartment;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", taskId=").append(taskId);
        sb.append(", dataOneError=").append(dataOneError);
        sb.append(", dataTwoError=").append(dataTwoError);
        sb.append(", dataThreeError=").append(dataThreeError);
        sb.append(", dataFourError=").append(dataFourError);
        sb.append(", geographicOneError=").append(geographicOneError);
        sb.append(", geographicTwoError=").append(geographicTwoError);
        sb.append(", geographicThreeError=").append(geographicThreeError);
        sb.append(", geographicFourError=").append(geographicFourError);
        sb.append(", groomOneError=").append(groomOneError);
        sb.append(", groomTwoError=").append(groomTwoError);
        sb.append(", groomThreeError=").append(groomThreeError);
        sb.append(", groomFourError=").append(groomFourError);
        sb.append(", attachmentOneError=").append(attachmentOneError);
        sb.append(", attachmentTwoError=").append(attachmentTwoError);
        sb.append(", attachmentThreeError=").append(attachmentThreeError);
        sb.append(", attachmentFourError=").append(attachmentFourError);
        sb.append(", assessUser=").append(assessUser);
        sb.append(", assessDate=").append(assessDate);
        sb.append(", checkDepartment=").append(checkDepartment);
        sb.append(", score=").append(score);
        sb.append("]");
        return sb.toString();
    }
}