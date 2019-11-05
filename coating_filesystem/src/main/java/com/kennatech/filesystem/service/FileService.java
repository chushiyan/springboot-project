package com.kennatech.filesystem.service;

import com.kennatech.filesystem.utils.FastDFSClient;
import org.csource.common.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {

    @Value("${fastdfs.nginx_url}")
    String nginxUrl;

    @Autowired
    private FastDFSClient fastDFSClient;

    public String uploadFile(MultipartFile multipartFile) throws IOException, MyException {

        String fileId = fastDFSClient.uploadFile(multipartFile);

        if (StringUtils.isEmpty(fileId)) {
            return null;
        }
        return nginxUrl + fileId;
    }

}
