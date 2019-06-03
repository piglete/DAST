package club.emergency.project.model;

import club.map.core.mapper.ColumnTransient;
import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("by_plus_record_model")
public class ByPlusRecordModel extends RootObject {
    private Integer recordTypeId;//档案类型id(从字典表获取,和此处有对应关系)

    private String recordType;//档案类型

    private String catalogContent;//目录内容

    public Integer getRecordTypeId() {
        return recordTypeId;
    }

    public void setRecordTypeId(Integer recordTypeId) {
        this.recordTypeId = recordTypeId;
    }

    @ColumnTransient
    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getCatalogContent() {
        return catalogContent;
    }

    public void setCatalogContent(String catalogContent) {
        this.catalogContent = catalogContent;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", recordTypeId=").append(recordTypeId);
        sb.append(", catalogContent=").append(catalogContent);
        sb.append("]");
        return sb.toString();
    }
}