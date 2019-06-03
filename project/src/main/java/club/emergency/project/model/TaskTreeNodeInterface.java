package club.emergency.project.model;

/**
 * Created with IntelliJ IDEA.
 * User: zhaojinxiong
 * Date: 2018-11-20
 * Time: 15:59
 * Description:
 */
public interface TaskTreeNodeInterface {
    Integer getId();

    Integer getProjectId();

    String getProjectName();

    Integer getParentId();

    String getName();

    String getStartDate();

    String getEndDate();

    Integer getLeaf();

    Integer getDuration();

    Integer getPercentDone();

    String getActualStartDate();

    String getActualEndDate();

    String getNote();

    Integer getExpanded();
}
