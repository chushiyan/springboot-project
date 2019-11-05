package com.kennatech.filesystem.controller;

import com.kennatech.coating.entity.Result;
import com.kennatech.coating.entity.StatusCode;
import com.kennatech.filesystem.service.FileService;
import org.csource.common.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/filesystem")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping
    public Result upload(MultipartFile multipartFile) {
        String fileUrl = null;
        try {
            fileUrl = fileService.uploadFile(multipartFile);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "上传图片失败");
        }
        return new Result(true, StatusCode.OK, "上传图片成功", fileUrl);
    }
}
