package cn.mislily.gmall.manager.utils;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class UploadUtils {

    public static String uploadImage(MultipartFile file) {

        // 配置fdfs的全局信息
        String path = UploadUtils.class.getClassLoader().getResource("tracker.conf").getFile();

        try {
            ClientGlobal.init(path);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }

        // 获得tracker
        TrackerClient trackerClient = new TrackerClient();

        TrackerServer connection = null;

        try {
            connection = trackerClient.getConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 通过 tracker 获得 storage
        // tracker 保存了 storage 的信息
        StorageClient storageClient = new StorageClient(connection, null);

        // 通过 storage 上传文件
        String[] gifs = new String[0];

        try {

            // 获取文件的全名
            String originalFilename = file.getOriginalFilename();

            // 通过最后一个 . 获取扩展名
            int i = originalFilename.lastIndexOf(".");

            // 截取 文件名 获取到文件的扩展名 [序号要 + 1]
            // substring 只传一个参数的情况下 默认 截取到字符串最后
            String substring = originalFilename.substring(i + 1);

            // 上传文件 返回文件 名各级 目录 不含 "/"
            gifs = storageClient.upload_file(file.getBytes(), substring, null);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }

        // 组合生成文件 url
        String url = "http://192.168.127.133";

        for (String gif : gifs) {
            url = url + "/" + gif;
        }

        // 返回文件的 url
        return url;
    }
}
