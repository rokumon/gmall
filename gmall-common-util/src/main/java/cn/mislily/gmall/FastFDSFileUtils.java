package cn.mislily.gmall;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class FastFDSFileUtils {

    public static int removeImage(String url) {

        int startIndex = url.indexOf("group");
        int endIndex = url.indexOf("/",startIndex);
        String picGroupName = url.substring(startIndex,endIndex);
        String picRemoteFileName = url.substring(endIndex + 1);

        TrackerServer connection = null;

        try {

            // 配置fdfs的全局信息
            String path = FastFDSFileUtils.class.getClassLoader().getResource("tracker.conf").getFile();

            ClientGlobal.init(path);

            // 获得tracker
            TrackerClient trackerClient = new TrackerClient();

            connection = trackerClient.getConnection();

            // 通过 tracker 获得 storage
            // tracker 保存了 storage 的信息
            StorageClient storageClient = new StorageClient(connection, null);

            return storageClient.delete_file(picGroupName, picRemoteFileName);

        } catch (Exception e) {
            e.printStackTrace();
            return 100000;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String uploadImage(MultipartFile file) {

        // 组合生成文件 url
        String url = null;

        TrackerServer connection = null;

        try {

            // 配置fdfs的全局信息
            String path = FastFDSFileUtils.class.getClassLoader().getResource("tracker.conf").getFile();

            ClientGlobal.init(path);

            // 获得tracker
            TrackerClient trackerClient = new TrackerClient();


            connection = trackerClient.getConnection();

            // 通过 tracker 获得 storage
            // tracker 保存了 storage 的信息
            StorageClient storageClient = new StorageClient(connection, null);

            // 通过 storage 上传文件
            String[] gifs = new String[0];

            // 获取文件的全名
            String originalFilename = file.getOriginalFilename();

            // 通过最后一个 . 获取扩展名
            int i = originalFilename.lastIndexOf(".");

            // 截取 文件名 获取到文件的扩展名 [序号要 + 1]
            // substring 只传一个参数的情况下 默认 截取到字符串最后
            String substring = originalFilename.substring(i + 1);

            // 上传文件 返回文件 名各级 目录 不含 "/"
            gifs = storageClient.upload_file(file.getBytes(), substring, null);

            url = "http://192.168.127.133";

            for (String gif : gifs) {
                url = url + "/" + gif;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // 返回文件的 url
        return url;
    }
}
