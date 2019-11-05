package com.kennatech.type.entity;

import java.io.Serializable;

public class QueryVo implements Serializable {

    private String id;
    private String name;   //          varchar(50) comment '大类的名称',
    private String title;   //          varchar(50) comment '标题(大类名称下的一行信息)',
    private String pictureUrl;   //          varchar(200) comment '大类的图标url',
    private String description;   //          varchar(1000),
    private String rank;
    private String status;   //          int default 1 comment '状态，0：弃用    1：使用中',

    private String parent;// char(10) comment '外键，所属的大类',


    public void setId(String id) {
    }

    public String getId() {
        return id;
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

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }


}
