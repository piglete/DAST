package club.map.base.thread;

import club.map.core.exception.ZeroAppException;

/**
 * 企业管理平台中当前登录的用户信息.
 * <p>
 * 采用策略如下:
 * 1.在ThreadLocal中存放staffId, 确保在当前线程内都能随时获取到当前登录的用户
 * 2.获取当前登录的用户信息时,
 * a.如果只是使用id,则可以直接get
 * b.如果需要获取完整对象,则可以通过redis工具类根据staffId获取 (此处需配合redis使用)
 * <p>
 * FIXMED: Q.什么时候销毁ThreadLocal中的数据? HandlerInterceptor.afterCompletion
 * Created by Zjs-yd on 2017/5/12.
 */
public abstract class ThreadContext {

    private final static int INDEX_STAFF_ID = 0;
    private final static int INDEX_COMPANY_ID = 1;
    private final static int INDEX_LEADER_ID = 2;
    private final static int INDEX_LIMIT = 3;


    private static ThreadLocal<Object[]> TLS = new ThreadLocal<>();

    private ThreadContext() {
    }

    /**
     * 彻底清除TLS
     */
    public final static void clear() {
        TLS.remove();
    }

    public final static Object[] getTLS() {
        Object[] values = TLS.get();
        if (values == null) {
            values = new Object[INDEX_LIMIT];
            TLS.set(values);
        }
        return values;
    }

    public final static void setStaffId(Integer staffId) {
        getTLS()[INDEX_STAFF_ID] = staffId;
    }

    public static Integer getStaffId() {
        return getIntegerByIndex(INDEX_STAFF_ID);
    }

    public final static void setCompanyId(Integer companyId) {
        getTLS()[INDEX_COMPANY_ID] = companyId;
    }

    public static Integer getCompanyId() {
        return getIntegerByIndex(INDEX_COMPANY_ID);
    }

    public final static void setLeaderId(Integer leaderId) {
        getTLS()[INDEX_LEADER_ID] = leaderId;
    }

    public static Integer getLeaderId() {
        return getIntegerByIndex(INDEX_LEADER_ID);
    }

    private static Integer getIntegerByIndex(int index) {
        Object[] values = TLS.get();
        if (values == null) {
            throw new ZeroAppException("ThreadContext未进行初始化");
//            return null;
        }
        Object value = values[index];
        Integer id = (Integer) value;
        if (id == null) {
            throw new ZeroAppException("ThreadContext中未存储该数据");
        }
        return id;
    }

}
