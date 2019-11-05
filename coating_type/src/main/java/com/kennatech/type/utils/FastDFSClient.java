package com.kennatech.type.utils;

import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class FastDFSClient {

    @Value("${fastdfs.tracker_servers}")
    String tracker_servers;

    @Value("${fastdfs.connect_timeout_in_millisecond}")
    int connect_timeout_in_millisecond;

    @Value("${fastdfs.network_timeout_in_millisecond}")
    int network_timeout_in_millisecond;

    @Value("${fastdfs.charset}")
    String charset;


    /**
     * 上传文件
     * 传入MultipartFile对象，返回文件id
     *
     * @param multipartFile 文件
     * @return 文件id
     */
    public String uploadFile(MultipartFile multipartFile) throws IOException, MyException {
        // 初始化fastDFS的环境
        initFdfsConfig();

        // 创建trackerClient
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();

        // 得到storage服务器
        StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);

        // 创建storageClient来上传文件
        StorageClient1 storageClient1 = new StorageClient1(trackerServer, storeStorage);

        // 上传文件
        // 得到文件字节
        byte[] bytes = multipartFile.getBytes();

        // 得到文件的原始名称
        String originalFilename = multipartFile.getOriginalFilename();

        // 得到文件扩展名
        String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

        String fileId = storageClient1.upload_file1(bytes, ext, null);

        return fileId;
    }

    // 初始化fastDFS环境
    private void initFdfsConfig() throws IOException, MyException {
        // 初始化tracker服务地址（多个tracker中间以半角逗号分隔）
        ClientGlobal.initByTrackers(tracker_servers);
        ClientGlobal.setG_charset(charset);
        ClientGlobal.setG_network_timeout(network_timeout_in_millisecond);
        ClientGlobal.setG_connect_timeout(connect_timeout_in_millisecond);

    }
}

