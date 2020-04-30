package com.ljw.gmall.manager.util;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class PmsUploadUtil {
    public static String uploadImage(MultipartFile multipartFile) {
        String imgUrl = "http://47.96.143.240";
        //上传图片到服务器
        //配置fdfs的全局链接地址
        String tracker = PmsUploadUtil.class.getResource("/tarcker.conf").getPath();//获取配置问件的路径   必须加/不然报错
        try {
            ClientGlobal.init(tracker);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TrackerClient trackerClient = new TrackerClient();

        //获取一个trackerServer的实例
        TrackerServer trackerServer = null;
        try {
            trackerServer = trackerClient.getTrackerServer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //通过tracker获得一个Storage链接客户端
        StorageClient storageClient = new StorageClient(trackerServer, null);

        try {
            byte[]   bytes = multipartFile.getBytes(); //获得上传的二进制对象
            //获得文件的后缀名
            String originalFilename = multipartFile.getOriginalFilename();
            int i = originalFilename.lastIndexOf(".");
            String exrName = originalFilename.substring(i + 1);

            String[]  uploadInfos = storageClient.upload_file(bytes, exrName, null);


            for (String uploadInfo : uploadInfos) {
                 imgUrl += "/"+uploadInfo;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return imgUrl;
    }
}
