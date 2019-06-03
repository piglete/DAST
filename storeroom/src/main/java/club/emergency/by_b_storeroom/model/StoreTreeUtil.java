package club.emergency.by_b_storeroom.model;

import club.map.core.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhaojinxiong
 * Date: 2018-12-13
 * Time: 8:14
 * Description:
 */
public class StoreTreeUtil {

    private Integer id;//ID
    private String code;//code
    private String name;//名称
    private String fullTitle;//全名称
    private Integer sortNum;//排序号
    private List<StoreTreeUtil> children;//子节点
    private Integer parentId;//父界点名称

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullTitle() {
        return fullTitle;
    }

    public void setFullTitle(String fullTitle) {
        this.fullTitle = fullTitle;
    }

    public List<StoreTreeUtil> getChildren() {
        return children;
    }

    public void setChildren(List<StoreTreeUtil> children) {
        this.children = children;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public StoreTreeUtil(Integer treeId, String treeCode, String treeTitle, String treeFullTile, Integer sortNum, Integer parentId) {
        this.id = treeId;
        this.code = treeCode;
        this.name = treeTitle;
        this.fullTitle = treeFullTile;
        this.sortNum = sortNum;
        this.parentId = parentId;
    }

    //AppTreeUtil
    public static List<StoreTreeUtil> getTreeData(List<StoreTreeNodeInterface> lst) {
        List<StoreTreeUtil> menuList = new ArrayList<>();
        // Collections.sort(lst, getSortNumComparator());
        lst.forEach(node -> {
            if (StringUtil.assertNull(node.getTreeParentId())) {
                StoreTreeUtil menu = new StoreTreeUtil(
                        node.getTreeId(), node.getTreeCode(),
                        node.getTreeTitle(), node.getTreeTitle(), node.getSortNum(), null
                );
                menu.setChildren(getChild(node.getTreeId(), node.getTreeTitle(), lst));
                menuList.add(menu);
            }
        });
        return menuList;
    }

    //获取子节点
    public static List<StoreTreeUtil> getChild(Integer id, String fullTitle, List<StoreTreeNodeInterface> lst) {
        List<StoreTreeUtil> menuChildList = new ArrayList<>();
        for (StoreTreeNodeInterface node : lst) {
            if (null != node.getTreeParentId() && node.getTreeParentId().equals(id)) {
                StoreTreeUtil menuChild = new StoreTreeUtil(
                        node.getTreeId(), node.getTreeCode(),
                        node.getTreeTitle(), node.getTreeFullName(), node.getSortNum(), id
                );
                menuChild.setChildren(getChild(menuChild.getId(), fullTitle, lst));
                menuChildList.add(menuChild);
            }
        }
        if (menuChildList.size() == 0) {
            return null;
        }
        return menuChildList;
    }
}
