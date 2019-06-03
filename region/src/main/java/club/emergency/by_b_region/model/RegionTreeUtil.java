package club.emergency.by_b_region.model;

import club.map.base.util.AppTreeNodeInterface;
import club.map.base.util.AppTreeUtil;
import club.map.core.util.StringUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhaojinxiong
 * Date: 2018-12-07
 * Time: 8:14
 * Description:
 */
public class RegionTreeUtil {

    private Integer id;//ID
    private String code;//code
    private String name;//名称
    private String fullTitle;//全名称
    private Integer sortNum;//排序号
    private List<RegionTreeUtil> children;//子节点
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

    public List<RegionTreeUtil> getChildren() {
        return children;
    }

    public void setChildren(List<RegionTreeUtil> children) {
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

    public RegionTreeUtil(Integer treeId, String treeCode, String treeTitle, String treeFullTile, Integer sortNum, Integer parentId) {
        this.id = treeId;
        this.code = treeCode;
        this.name = treeTitle;
        this.fullTitle = treeFullTile;
        this.sortNum = sortNum;
        this.parentId = parentId;
    }

    //AppTreeUtil
    public static List<RegionTreeUtil> getTreeData(List<RegionTreeNodeInterface> lst) {
        List<RegionTreeUtil> menuList = new ArrayList<>();
        // Collections.sort(lst, getSortNumComparator());
        lst.forEach(node -> {
            if (StringUtil.assertNull(node.getTreeParentId())) {
                RegionTreeUtil menu = new RegionTreeUtil(
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
    public static List<RegionTreeUtil> getChild(Integer id, String fullTitle, List<RegionTreeNodeInterface> lst) {
        List<RegionTreeUtil> menuChildList = new ArrayList<>();
        for (RegionTreeNodeInterface node : lst) {
            if (null != node.getTreeParentId() && node.getTreeParentId().equals(id)) {
                String selfFullTitle = fullTitle + "/" + node.getTreeTitle();
                RegionTreeUtil menuChild = new RegionTreeUtil(
                        node.getTreeId(), node.getTreeCode(),
                        node.getTreeTitle(), selfFullTitle, node.getSortNum(), id
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
