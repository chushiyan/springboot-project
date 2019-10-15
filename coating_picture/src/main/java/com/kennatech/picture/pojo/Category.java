package com.kennatech.picture.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Category implements Serializable {

    @Id
    private String id;   //          varchar(100) not null,
    private String name;   //          varchar(50) comment '大类的名称',
    private String title;   //          varchar(50) comment '标题(大类名称下的一行信息)',
    private String iconUrl;   //          varchar(200) comment '大类的图标url',
    private String description;   //          varchar(1000),
    private String status;   //          int default 1 comment '状态，0：弃用    1：使用中',

    public Category() {
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

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
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
