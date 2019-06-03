package club.map.core;

import java.text.DecimalFormat;

/**
 * 系统常量.
 * Created by zero-mac on 16/6/29.
 */
public class Constants {


    /**
     * 默认每页数据条数.
     */
    public static final int DEFAULT_PAGE_SIZE = 10;

    public final static String DATEFORMAT = "yyyy-MM-dd";

    public final static DecimalFormat decimalFormat = new DecimalFormat("0.##");
    //通过配置文件更新默认参数.
//    static {
//        try {
//            PropertiesConfiguration configuration = new PropertiesConfiguration("/application.properties");
//            DEFAULT_PAGE_SIZE = configuration.getInt("DEFAULT_PAGE_SIZE");
//        } catch (ConfigurationException e) {
//            e.printStackTrace();
    // Do noThing.
//        }
//    }

    public static String getLimitSql(int pageNo) {
        StringBuffer sql = new StringBuffer();
        pageNo = pageNo < 1 ? 1 : pageNo;
        sql.append(" LIMIT ").append((pageNo - 1) * Constants.DEFAULT_PAGE_SIZE).append(",").append(Constants.DEFAULT_PAGE_SIZE);
        return sql.toString();
    }

    public static String getLimitSql(int pageNo, int offset) {
        pageNo = pageNo < 1 ? 1 : pageNo;
        Integer beginNo = (pageNo - 1) * Constants.DEFAULT_PAGE_SIZE - offset < 0 ? 0 : (pageNo - 1) * Constants.DEFAULT_PAGE_SIZE - offset;
        return " LIMIT " + beginNo + "," + Constants.DEFAULT_PAGE_SIZE;
    }

}
