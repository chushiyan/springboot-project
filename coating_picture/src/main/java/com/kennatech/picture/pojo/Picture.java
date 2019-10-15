package com.kennatech.picture.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Picture implements Serializable {

    @Id
    private String id;           // varchar(100) not null,
    private String name;        // varchar(200) comment '图片名',
    private Category category;    // char(10) comment '图片所属大类',
    private Type type;         // char(10) comment '图片所属小类（二级分类）',
    private String description; // varchar(500) comment '图片描述',
    private String tags;         // char(10) comment '外键，图片tag',
    private String uploadDate;  // timestamp(0) comment '上传日期',
    private String url;          // varchar(600) comment '图片url',
    private String status;      // int default 1 comment '状态   0：弃用    1：使用中',

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
