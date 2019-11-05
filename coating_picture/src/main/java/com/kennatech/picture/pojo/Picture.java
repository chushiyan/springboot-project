package com.kennatech.picture.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Picture implements Serializable {

    @Id
    private String id;           // varchar(100) not null,
    private String name;        // varchar(200) comment '图片名',

    @JsonIgnoreProperties(value = {"pictures","types"})
    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;    // char(10) comment '图片所属大类',

    @JsonIgnoreProperties(value = {"pictures","parent"})
    @ManyToOne(targetEntity = Type.class)
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private Type type;         // char(10) comment '图片所属小类（二级分类）',


    private String description; // varchar(500) comment '图片描述',

    @JsonIgnoreProperties(value = "pictures")
    @ManyToMany
    @JoinTable(
            name = "picture_tag",
            joinColumns = {@JoinColumn(name = "picture_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id", referencedColumnName = "id")}
    )
    private Set<Tag> tags = new HashSet<Tag>(0);  // char(10) comment '外键，图片tag',

    @Column(name="upload_date")
    private Timestamp uploadDate;  // timestamp(0) comment '上传日期',

    private String url;          // varchar(600) comment '图片url',

    private int status;      // int default 1 comment '状态   0：弃用    1：使用中',


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

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Timestamp getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Timestamp uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", tags=" + tags +
                ", uploadDate=" + uploadDate +
                ", url='" + url + '\'' +
                ", status=" + status +
                '}';
    }
}
