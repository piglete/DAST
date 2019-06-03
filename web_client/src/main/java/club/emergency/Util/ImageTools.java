package club.emergency.Util;

import org.apache.commons.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: zhaojinxiong
 * Date: 2018-05-28
 * Time: 10:54
 * Description:
 */
public class ImageTools {

    //图片转64位编码
    public static String getImageStr(MultipartFile file) {
        byte[] data = null;
        try {
            InputStream inputStream = file.getInputStream();
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
         * BASE64Encoder 可能会在未来的发行版本中删除,由于存在不安全隐患,现在替换为Base64
         */
//        BASE64Encoder encoder = new BASE64Encoder();
        Base64 base64 = new Base64();
        String img = base64.encodeAsString(data);
        return img;
    }
}
