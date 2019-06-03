package club.emergency.auth_permission.model;

/**
 * Created with IntelliJ IDEA.
 * User: zhaojinxiong
 * Date: 2018-11-17
 * Time: 15:54
 * Description:
 */
public interface MenuTreeNodeInterface {

    Integer getMenuId();

    String getMenuName();

    String getMenuIcon();

    Integer getMenuParentId();

    String getMenuUrl();
}
