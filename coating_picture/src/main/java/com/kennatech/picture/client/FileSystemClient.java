package com.kennatech.picture.client;

import com.kennatech.coating.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Component
@FeignClient("coating-filesystem")
public interface FileSystemClient {
    @PostMapping(value = "/filesystem", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result upload(MultipartFile multipartFile);
}

