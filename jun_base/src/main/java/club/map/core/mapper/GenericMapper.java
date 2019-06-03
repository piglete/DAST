package club.map.core.mapper;

import club.map.core.model.DAOFilter;
import club.map.core.model.FlipFilter;
import club.map.core.model.RootObject;
import org.apache.ibatis.annotations.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by zero-mac on 16/7/1.
 */
public interface GenericMapper<T extends RootObject, ID extends Serializable> {

    /**
     * 创建.
     *
     * @param t
     */
    @InsertProvider(type = SqlProvider.class, method = "insert")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void create(T t);

    @UpdateProvider(type = SqlProvider.class, method = "update")
    void update(T t);

    @UpdateProvider(type = SqlProvider.class, method = "updateByFilterForUpdate")
    void updateByFilter(@Param("obj") T t, @Param("filter") DAOFilter filter);

    @UpdateProvider(type = SqlProvider.class, method = "updateByFilterForDelete")
    void updateByFilterForDelete(@Param("obj") T t, @Param("filter") DAOFilter filter);

    @UpdateProvider(type = SqlProvider.class, method = "updateFields")
    void updateFields(T t, String[] fields);

    /**
     * 返回一条记录，最多只能有一条.
     *
     * @param filter
     * @return
     */
    @SelectProvider(type = SqlProvider.class, method = "select")
    T get(DAOFilter filter);

    @SelectProvider(type = SqlProvider.class, method = "getById")
    T getById(Class clazz, @Param("id") ID id);

    /**
     * 只返回第一条记录.
     *
     * @param field
     * @param val
     * @return
     */
    @SelectProvider(type = SqlProvider.class, method = "selectTop1")
    T getFirst(Class clazz, String field, @Param("P0") String val);

    @SelectProvider(type = SqlProvider.class, method = "count")
    long count(DAOFilter filter);

    /**
     * 查询
     *
     * @param filter
     * @return
     */
    @SelectProvider(type = SqlProvider.class, method = "select")
    List<T> list(DAOFilter filter);

    @SelectProvider(type = SqlProvider.class, method = "flip")
    List<T> flip(FlipFilter filter);

    /**
     * 根据主键删除数据.
     *
     * @param clazz
     * @param id
     */
    @DeleteProvider(type = SqlProvider.class, method = "deleteById")
    void remove(Class clazz, @Param("id") ID id);

    /**
     * 根据过滤条件 进行物理删除
     *
     * @param filter
     */
    @DeleteProvider(type = SqlProvider.class, method = "deleteByFilter")
    void removeRealByFilter(DAOFilter filter);

    /**
     * 获取第一条符合条件的记录.
     *
     * @param filter
     * @return
     */
    @SelectProvider(type = SqlProvider.class, method = "flipFirst")
    T getFirstByFilter(FlipFilter filter);

    /**
     * 通过自定义SQL获取对象MAP
     *
     * @param sql
     * @return
     */
    @SelectProvider(type = SqlProvider.class, method = "sql")
    Map<String, Object> getObjectBySql(String sql);

    /**
     * 通过自定义SQL获取对象集合
     *
     * @param sql
     * @return
     */
    @SelectProvider(type = SqlProvider.class, method = "sql")
    List<Map<String, Object>> getListBySql(String sql);

    /**
     * 通过自定义SQL获取对象总数
     *
     * @param sql
     * @return
     */
    @SelectProvider(type = SqlProvider.class, method = "sql")
    long getCountBySql(String sql);

    /**
     * 通过自定义SQL获取一个值
     *
     * @param sql
     * @return
     */
    @SelectProvider(type = SqlProvider.class, method = "sql")
    String getValueBySql(String sql);
}
