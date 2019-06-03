package club.emergency;

public interface BYCHCons {
    /**
     * redis模式单机0单机1集群
     */
    public static final String REDIS_MODE_SINGLE="0";
    public static final String REDIS_MODE_CLUSTER="1";
    /**
     * 日期格式
     */

    public static final String DATE = "yyyy-MM-dd";
    /**
     * 日期时间格式
     */
    public static final String DATETIME = "yyyy-MM-dd HH:mm:ss";
    /**
     * 日期时间格式 精确到分
     */
    public static final String DATETIMEMIN = "yyyy-MM-dd HH:mm";
    /**
     * 存储过程业务成功状态码：成功
     */
    public static final int PROC_SUCCESS = 1;
    /**
     * 业务状态码：成功
     */
    public static final int SUCCESS = 1;

    /**
     * 业务状态吗：警告
     */
    public static final int WARN = 0;

    /**
     * 业务状态码：失败
     */
    public static final int ERROR = -1;
}
