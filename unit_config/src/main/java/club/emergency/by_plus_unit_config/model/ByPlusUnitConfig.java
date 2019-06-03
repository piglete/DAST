package club.emergency.by_plus_unit_config.model;

import club.map.core.mapper.ColumnTransient;
import club.map.core.model.RootObject;
import club.map.core.model.TableName;

import java.math.BigDecimal;

@TableName("by_plus_unit_config")
public class ByPlusUnitConfig extends RootObject {
    private Integer unitTypeId;//申报单位id

    private Integer itemApplicationId;//申报内容id

    private Integer recordTypeId;//项目类型id

    private String unitTypeName;//申报单位名称

    private String itemApplicationName;//申报内容

    private String recordTypeName;//项目类型名称(例:竣工）

    private BigDecimal internalVal;//内部产值单价



    public Integer getRecordTypeId() {
        return recordTypeId;
    }

    public void setRecordTypeId(Integer recordTypeId) {
        this.recordTypeId = recordTypeId;
    }
    @ColumnTransient
    public String getItemApplicationName() {
        return itemApplicationName;
    }

    public void setItemApplicationName(String itemApplicationName) {
        this.itemApplicationName = itemApplicationName;
    }
    @ColumnTransient
    public String getRecordTypeName() {
        return recordTypeName;
    }

    public void setRecordTypeName(String recordTypeName) {
        this.recordTypeName = recordTypeName;
    }
    public BigDecimal getInternalVal() {
        return internalVal;
    }

    public void setInternalVal(BigDecimal internalVal) {
        this.internalVal = internalVal;
    }

    public Integer getUnitTypeId() {
        return unitTypeId;
    }

    public void setUnitTypeId(Integer unitTypeId) {
        this.unitTypeId = unitTypeId;
    }

    public Integer getItemApplicationId() {
        return itemApplicationId;
    }

    public void setItemApplicationId(Integer itemApplicationId) {
        this.itemApplicationId = itemApplicationId;
    }
    @ColumnTransient
    public String getUnitTypeName() {
        return unitTypeName;
    }

    public void setUnitTypeName(String unitTypeName) {
        this.unitTypeName = unitTypeName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", recordTypeName=").append(recordTypeName);
        sb.append(", unitTypeName=").append(unitTypeName);
        sb.append(", itemApplicationName=").append(itemApplicationName);
        sb.append(", internalVal=").append(internalVal);
        sb.append("]");
        return sb.toString();
    }
}