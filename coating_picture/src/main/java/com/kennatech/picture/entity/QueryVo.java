package com.kennatech.picture.entity;

import com.kennatech.picture.pojo.Category;
import com.kennatech.picture.pojo.Type;

import java.io.Serializable;

public class QueryVo implements Serializable {

    private String name;        // varchar(200) comment '图片名',
    private String category;    // char(10) comment '图片所属大类',
    private String type;         // char(10) comment '图片所属小类（二级分类）',
    private String description; // varchar(500) comment '图片描述',
    private String[] tags;         // char(10) comment '图片tag',
    private String uploadDate;  // timestamp(0) comment '上传日期',


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }
}
