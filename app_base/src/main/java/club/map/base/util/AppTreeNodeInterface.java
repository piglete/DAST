package club.map.base.util;

import club.map.core.mapper.ColumnTransient;

/**
 * Created by junli on 2017/5/19.
 */
public interface AppTreeNodeInterface {
    Integer getTreeId();

    String getTreeCode();

    String getTreeTitle();

    Integer getTreeParentId();

    @ColumnTransient
    default Integer getSortNum() {
        return 0;
    }
}
