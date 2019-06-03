package club.map.base.util;

import club.map.core.util.StringUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by junli on 2017/5/19.
 */
public class AppTreeUtil {

    private Integer id;//ID
    private String code;//code
    private String title;//名称
    private String fullTitle;//全名称
    private Integer sortNum;//排序号
    private List<AppTreeUtil> children;//子节点
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFullTitle() {
        return fullTitle;
    }

    public void setFullTitle(String fullTitle) {
        this.fullTitle = fullTitle;
    }

    public List<AppTreeUtil> getChildren() {
        return children;
    }

    public void setChildren(List<AppTreeUtil> children) {
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

    public AppTreeUtil(Integer treeId, String treeCode, String treeTitle, String treeFullTile, Integer sortNum, Integer parentId) {
        this.id = treeId;
        this.code = treeCode;
        this.title = treeTitle;
        this.fullTitle = treeFullTile;
        this.sortNum = sortNum;
        this.parentId = parentId;
    }

    //AppTreeUtil
    public static List<AppTreeUtil> getTreeData(List<AppTreeNodeInterface> lst) {
        List<AppTreeUtil> menuList = new ArrayList<>();
        // Collections.sort(lst, getSortNumComparator());
        lst.forEach(node -> {
            if (StringUtil.assertNull(node.getTreeParentId())) {
                AppTreeUtil menu = new AppTreeUtil(
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
    public static List<AppTreeUtil> getChild(Integer id, String fullTitle, List<AppTreeNodeInterface> lst) {
        List<AppTreeUtil> menuChildList = new ArrayList<>();
        for (AppTreeNodeInterface node : lst) {
            if (null != node.getTreeParentId() && node.getTreeParentId().equals(id)) {
                String selfFullTitle = fullTitle + "/" + node.getTreeTitle();
                AppTreeUtil menuChild = new AppTreeUtil(
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

    private static Comparator getSortNumComparator() {
        return new Comparator<AppTreeNodeInterface>() {
            @Override
            public int compare(AppTreeNodeInterface o1, AppTreeNodeInterface o2) {
                int i = compareByInteger(o1.getSortNum(), o2.getSortNum());
                if (i == 0) {
                    return compareByInteger(o1.getTreeId(), o2.getTreeId());
                }
                return i;
            }

            public int compareByInteger(Integer o1, Integer o2) {
                if (o1 == null && o2 == null) {
                    return 0;
                }
                //sortNum为空表示最小, 返回-1
                if (o1 == null) {
                    return -1;
                }
                if (o2 == null) {
                    return 1;
                }
                if (o1 < o2) {
                    return -1;
                }
                if (o1 > o2) {
                    return 1;
                }
                return 0;
            }
        };
    }
}
