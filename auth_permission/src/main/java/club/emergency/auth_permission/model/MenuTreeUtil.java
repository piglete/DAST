package club.emergency.auth_permission.model;

import club.map.core.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhaojinxiong
 * Date: 2018-11-17
 * Time: 15:56
 * Description:
 */
public class MenuTreeUtil {

    private Integer id;//id
    private String menuName;//菜单名称
    private String menuIcon;//图标文件地址
    private String menuUrl;//菜单地址
    private List<MenuTreeUtil> children;//子节点
    private Integer parentId;//级菜单编号

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuIcon() {
        return menuIcon;
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

    public List<MenuTreeUtil> getChildren() {
        return children;
    }

    public void setChildren(List<MenuTreeUtil> children) {
        this.children = children;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public MenuTreeUtil(Integer id, String menuName, String menuIcon, String menuUrl, Integer parentId) {
        this.id = id;
        this.menuName = menuName;
        this.menuIcon = menuIcon;
        this.menuUrl = menuUrl;
        this.parentId = parentId;
    }

    //AppTreeUtil
    public static List<MenuTreeUtil> getTreeData(List<MenuTreeNodeInterface> lst) {
        List<MenuTreeUtil> menuTreeList = new ArrayList<>();
        lst.forEach(node -> {
            if (node.getMenuParentId() == 0) {
                MenuTreeUtil menu = new MenuTreeUtil(
                        node.getMenuId(), node.getMenuName(),
                        node.getMenuIcon(), node.getMenuUrl(), 0
                );
                menu.setChildren(getChild(node.getMenuId(), lst));
                menuTreeList.add(menu);
            }
        });
        return menuTreeList;
    }

    //获取子节点
    public static List<MenuTreeUtil> getChild(Integer id, List<MenuTreeNodeInterface> lst) {
        List<MenuTreeUtil> menuChildList = new ArrayList<>();
        for (MenuTreeNodeInterface node : lst) {
            if (!node.getMenuParentId().equals(0) && node.getMenuParentId().equals(id)) {
                MenuTreeUtil menuChild = new MenuTreeUtil(
                        node.getMenuId(), node.getMenuName(),
                        node.getMenuIcon(), node.getMenuUrl(), id
                );
                menuChild.setChildren(getChild(menuChild.getId(), lst));
                menuChildList.add(menuChild);
            }
        }
        if (menuChildList.size() == 0) {
            return null;
        }
        return menuChildList;
    }

}
