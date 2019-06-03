package club.map.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: zhaojinxiong
 * Date: 2018-11-20
 * Time: 10:21
 * Description:
 */
public class DateTransform {


    /**
     *
     * @param str
     * @return date
     * 将时间类字符串转换成标准时间格式
     */
    public static Date strToStandardDate(String str){
        Date date=null;
        if (str == null || str.length() == 0) {
            return date;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date=dateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
        return date;
    }

    /**
     *
     * @param str
     * @return date
     * 将时间类字符串转换成简单时间格式
     */
    public static Date strToSimpleDate(String str){
        Date date=null;
        if (str == null || str.length() == 0) {
            return date;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date=dateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
        return date;
    }


}
