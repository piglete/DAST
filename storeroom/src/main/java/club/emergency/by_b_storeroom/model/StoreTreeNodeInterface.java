package club.emergency.by_b_storeroom.model;

import club.map.core.mapper.ColumnTransient;

/**
 * Created with IntelliJ IDEA.
 * User: zhaojinxiong
 * Date: 2018-12-13
 * Time: 8:14
 * Description:
 */
public interface StoreTreeNodeInterface {
    Integer getTreeId();

    String getTreeCode();

    String getTreeTitle();

    String getTreeFullName();

    Integer getTreeParentId();

    @ColumnTransient
    default Integer getSortNum() {
        return 0;
    }
}
