package club.map.base;

/**
 * Created by Zjs-yd on 2017/6/19.
 */
public abstract class AppConstants {

    public static final int SYSTEM_FLAG = 2;//1: 考试系统; 2(及其他): 班组系统

    public static final String DEFAULT_COURSE_NAME = SYSTEM_FLAG == 1 ? "数据库" : "题库";

    //评论模块、点赞模块/操作记录模块--操作器：默认为MySQL操作
    public static final String QUERY_MODE = "MYSQL";//MONGO:mongo操作；MYSQL:mysql操作

}
