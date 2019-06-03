package club.emergency.by_b_region.model;

import club.map.core.mapper.ColumnTransient;

/**
 * Created with IntelliJ IDEA.
 * User: zhaojinxiong
 * Date: 2018-12-07
 * Time: 8:14
 * Description:
 */
public interface RegionTreeNodeInterface {
    Integer getTreeId();

    String getTreeCode();

    String getTreeTitle();

    Integer getTreeParentId();

    @ColumnTransient
    default Integer getSortNum() {
        return 0;
    }
}
