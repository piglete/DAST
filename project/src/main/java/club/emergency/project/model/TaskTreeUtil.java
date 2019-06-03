package club.emergency.project.model;

import club.map.core.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhaojinxiong
 * Date: 2018-11-20
 * Time: 15:53
 * Description:
 */
public class TaskTreeUtil {

    private Integer id;//id

    private Integer projectId;//项目编号

    private String projectName;//项目名称

    private Integer parentId;//父节点

    private String name;//任务名称

    private String startDate;//开始时间

    private String endDate;//结束时间

    private Integer leaf;//是否缩放打开(0关闭,1打开)

    private Integer duration;//任务时长

    private Integer percentDone;//完成百分比

    private String actualStartDate;//实际开始时间

    private String actualEndDate;//实际结束时间

    private String note;//备注

    private List<TaskTreeUtil> children;//子节点

    private Integer expanded;//是否打开(0关闭,1打开)

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getLeaf() {
        return leaf;
    }

    public void setLeaf(Integer leaf) {
        this.leaf = leaf;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getPercentDone() {
        return percentDone;
    }

    public void setPercentDone(Integer percentDone) {
        this.percentDone = percentDone;
    }

    public String getActualStartDate() {
        return actualStartDate;
    }

    public void setActualStartDate(String actualStartDate) {
        this.actualStartDate = actualStartDate;
    }

    public String getActualEndDate() {
        return actualEndDate;
    }

    public void setActualEndDate(String actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<TaskTreeUtil> getChildren() {
        return children;
    }

    public void setChildren(List<TaskTreeUtil> children) {
        this.children = children;
    }

    public Integer getExpanded() {
        return expanded;
    }

    public void setExpanded(Integer expanded) {
        this.expanded = expanded;
    }

    public TaskTreeUtil(Integer id, Integer projectId, String projectName, Integer expanded, String name, String startDate, String endDate, Integer leaf, Integer duration, Integer percentDone, String actualStartDate, String actualEndDate, String note, Integer parentId) {
        this.id = id;
        this.projectId = projectId;
        this.projectName = projectName;
        this.expanded = expanded;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.leaf = leaf;
        this.duration = duration;
        this.percentDone = percentDone;
        this.actualStartDate = actualStartDate;
        this.actualEndDate = actualEndDate;
        this.note = note;
        this.parentId = parentId;
    }

    //AppTreeUtil
    public static List<TaskTreeUtil> getTreeData(List<TaskTreeNodeInterface> lst) {
        List<TaskTreeUtil> taskTreeList = new ArrayList<>();
        lst.forEach(node -> {
            if (StringUtil.assertNull(node.getParentId())) {
                TaskTreeUtil task = new TaskTreeUtil(
                        node.getId(), node.getProjectId(), node.getProjectName(), node.getExpanded(),
                        node.getName(), node.getStartDate(), node.getEndDate(), node.getLeaf(),
                        node.getDuration(), node.getPercentDone(), node.getActualStartDate(), node.getActualEndDate(),
                        node.getNote(), null
                );
                task.setChildren(getChild(node.getId(), lst));
                taskTreeList.add(task);
            }
        });
        return taskTreeList;
    }

    //获取子节点
    public static List<TaskTreeUtil> getChild(Integer id, List<TaskTreeNodeInterface> lst) {
        List<TaskTreeUtil> taskChildList = new ArrayList<>();
        for (TaskTreeNodeInterface node : lst) {
            if (null != node.getParentId() && node.getParentId().equals(id)) {
                TaskTreeUtil taskChild = new TaskTreeUtil(
                        node.getId(), node.getProjectId(), node.getProjectName(), node.getExpanded(),
                        node.getName(), node.getStartDate(), node.getEndDate(), node.getLeaf(),
                        node.getDuration(), node.getPercentDone(), node.getActualStartDate(), node.getActualEndDate(),
                        node.getNote(),id
                );
                taskChild.setChildren(getChild(taskChild.getId(), lst));
                taskChildList.add(taskChild);
            }
        }
        if (taskChildList.size() == 0) {
            return null;
        }
        return taskChildList;
    }
}
