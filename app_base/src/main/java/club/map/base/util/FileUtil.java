
package club.map.base.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: zhaojinxiong
 * Date: 2019-03-19
 * Time: 9:15
 * Description:
 */
public class FileUtil {

    /**
     * 在basePath下保存上传的文件夹
     *
     * @param basePath
     * @param files
     */
    public static void saveMultiFile(String basePath, MultipartFile[] files) {
        if (files == null || files.length == 0) {
            return;
        }
        if (basePath.endsWith("/")) {
            basePath = basePath.substring(0, basePath.length() - 1);
        }
        for (MultipartFile file : files) {
            String filePath = basePath + "/" + file.getOriginalFilename();
            makeDir(filePath);
            File dest = new File(filePath);
            try {
                file.transferTo(dest);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean saveSimpleMultiFile(String basePath, MultipartFile file, String fileName) {
        if (file == null) {
            return false;
        }
        if (basePath.endsWith("/")) {
            basePath = basePath.substring(0, basePath.length() - 1);
        }
        String filePath = basePath + "/" + fileName;
        File dest = new File(filePath);
        try {
            file.transferTo(dest);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        return true;
    }


    /**
     * 确保目录存在，不存在则创建
     *
     * @param filePath
     */
    private static void makeDir(String filePath) {
        if (filePath.lastIndexOf('/') > 0) {
            String dirPath = filePath.substring(0, filePath.lastIndexOf('/'));
            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }
    }
}
