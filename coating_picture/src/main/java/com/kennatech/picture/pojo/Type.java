package com.kennatech.picture.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity

public class Type implements Serializable {
    @Id
    private String id;// varchar(100) not null,
    private String name;// varchar(20) comment '二级分类的名称',
    private String title;// varchar(50),
    private Category parent;// char(10) comment '外键，所属的大类',
    private String pictureUrl;// varchar(200) comment '图标url',
    private String description;// varchar(1000),
    private String status;// int default 1 comment '二级分类的状态，0：弃用    1：使用中',

    public Type() {
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
