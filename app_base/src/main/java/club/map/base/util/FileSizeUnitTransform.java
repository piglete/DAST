package club.map.base.util;

import java.io.File;
import java.text.DecimalFormat;

/**
 * Created with IntelliJ IDEA.
 * User: zhaojinxiong
 * Date: 2019-01-02
 * Time: 10:34
 * Description:
 */
public class FileSizeUnitTransform {

    public static String GetFileSize(Long size) {
        String fileSize = "";
        DecimalFormat df = new DecimalFormat("#.00");
        if (size < 1024) {
            fileSize = df.format((double) size) + "BT";
        } else if (size < 1048576) {
            fileSize = df.format((double) size / 1024) + "KB";
        } else if (size < 1073741824) {
            fileSize = df.format((double) size / 1048576) + "MB";
        } else {
            fileSize = df.format((double) size / 1073741824) + "GB";
        }
        return fileSize;
    }
}
