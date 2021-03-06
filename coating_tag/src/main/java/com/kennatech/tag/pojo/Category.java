package com.kennatech.tag.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Category implements Serializable {

    @Id
    private String id;   //          varchar(100) not null,
    private String name;   //          varchar(50) comment '大类的名称',
    private String title;   //          varchar(50) comment '标题(大类名称下的一行信息)',

    @Column(name = "picture_url")
    private String pictureUrl;   //          varchar(200) comment '大类的图标url',
    private String description;   //          varchar(1000),
    private String rank;
    private String status;   //          int default 1 comment '状态，0：弃用    1：使用中',

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private Set<Picture> pictures = new HashSet<Picture>();

    @OneToMany(mappedBy = "parent")
    private Set<Type> types = new HashSet<Type>();


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

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public Set<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
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
