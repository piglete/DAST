package club.emergency.file.manager.impl;

import club.emergency.file.manager.PluploadManager;
import club.emergency.file.model.FileInfo;
import club.emergency.file.model.Plupload;
import club.map.base.util.FileSizeUnitTransform;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhaojinxiong
 * Date: 2018-12-07
 * Time: 11:37
 * Description:
 */
@Service
public class PluploadManagerImpl implements PluploadManager {

    @Override
    public FileInfo upload(Plupload plupload, File dir) {
        String fileName = "" + System.currentTimeMillis() + plupload.getName();// 在服务器内生成唯一文件名
        upload(plupload, dir, fileName);
        FileInfo fileInfo = null;
        //添加上传记录
        int chunks = plupload.getChunks();// 用户上传文件被分隔的总块数
        int nowChunk = plupload.getChunk();// 当前块，从0开始
        if (chunks - 1 == nowChunk) {
            fileInfo = new FileInfo();
            String path = dir + "\\" + fileName;
            File file = new File(path);
            String fileSize = FileSizeUnitTransform.GetFileSize(file.length());
            fileInfo.setFileName(plupload.getName());
            fileInfo.setFileSize(fileSize);
            fileInfo.setFileUrl(path);
        }
        return fileInfo;
    }

    /**
     * 此处进行文件判断
     * flag = 0;表示文件删除成功
     * flag = 1;表示文件不存在
     * flag = 2;表示文件删除失败
     */
    @Override
    public Integer remove(String path) {
        int flag = 0;
        File file = new File(path);
        if (file.exists()) {
            if (file.delete()) {
                return flag;
            } else {
                flag = 2;
                return flag;
            }
        } else {
            flag = 1;
            return flag;
        }
    }

    @Override
    public boolean downFile(String name, String filePath, HttpServletResponse response) {
        File file = new File(filePath);
        if (file.exists()) { //判断文件父目录是否存在
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;fileName=" + name);
            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;
            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private static void upload(Plupload plupload, File pluploadDir, String fileName) {

        int chunks = plupload.getChunks();// 用户上传文件被分隔的总块数
        int nowChunk = plupload.getChunk();// 当前块，从0开始

        // 这里Request请求类型的强制转换可能出错，配置文件中向SpringIOC容器引入multipartResolver对象即可。
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) plupload.getRequest();
        // 调试发现map中只有一个键值对
        MultiValueMap<String, MultipartFile> map = multipartHttpServletRequest.getMultiFileMap();
        if (map != null) {
            try {
                Iterator<String> iterator = map.keySet().iterator();
                String key = null;
                List<MultipartFile> multipartFileList = null;
                File targetFile = null;
                File tempFile = null;
                while (iterator.hasNext()) {
                    key = iterator.next();
                    multipartFileList = (List<MultipartFile>) map.get(key);
                    for (MultipartFile multipartFile : multipartFileList) {// 循环只进行一次
                        plupload.setMultipartFile(multipartFile);// 手动向Plupload对象传入MultipartFile属性值
                        targetFile = new File(pluploadDir, fileName);// 新建目标文件，只有被流写入时才会真正存在
                        if (chunks > 1) {// 用户上传资料总块数大于1，要进行合并
                            tempFile = new File(pluploadDir.getPath(), multipartFile.getName());
                            // 第一块直接从头写入，不用从末端写入
                            savePluploadFile(multipartFile.getInputStream(), tempFile, nowChunk == 0 ? false : true);
                            if (chunks - nowChunk == 1) {// 全部块已经上传完毕，此时targetFile因为有被流写入而存在，要改文件名字
                                tempFile.renameTo(targetFile);
                            }
                        } else {
                            // 只有一块，就直接拷贝文件内容
                            multipartFile.transferTo(targetFile);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 描述 : 写入文件到磁盘. <br>
     *
     * @param inputStream 数据流
     * @param tempFile    临时文件
     * @param flag        是否是追加，是的话需要在原来的基础上添加，否则直接写入
     * @author yjw@jusfoun.com
     * @date 2017年10月18日 上午9:32:15
     */
    private static void savePluploadFile(InputStream inputStream, File tempFile, boolean flag) {
        OutputStream outputStream = null;
        try {
            if (flag == false) {
                // 从头写入
                outputStream = new BufferedOutputStream(new FileOutputStream(tempFile));
            } else {
                // 从末端写入,追加
                outputStream = new BufferedOutputStream(new FileOutputStream(tempFile, true));
            }
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = (inputStream.read(bytes))) > 0) {
                outputStream.write(bytes, 0, len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
