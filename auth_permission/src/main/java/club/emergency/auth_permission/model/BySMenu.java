package club.emergency.auth_permission.model;


import club.map.core.mapper.ColumnTransient;
import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("by_s_menu")
public class BySMenu extends RootObject implements MenuTreeNodeInterface{
    private String menuName;//菜单名称

    private Integer parentId;//上级菜单id

    private String menuIcon;//图标文件地址

    private String menuUrl;//菜单地址

    private Integer sortNum;//排序

    @ColumnTransient
    @Override
    public Integer getMenuId() {
        return id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    @ColumnTransient
    @Override
    public Integer getMenuParentId() {
        return parentId;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", menuName=").append(menuName);
        sb.append(", parentId=").append(parentId);
        sb.append(", menuIcon=").append(menuIcon);
        sb.append(", menuUrl=").append(menuUrl);
        sb.append(", sortNum=").append(sortNum);
        sb.append("]");
        return sb.toString();
    }

}