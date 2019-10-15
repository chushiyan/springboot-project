package com.kennatech.coating.appointment.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Category implements Serializable {

    @Id
    private String id;             // varchar(100) not null,
    private String name;           // varchar(50) comment '大类的名称',
    private String title;          // varchar(50) comment '标题(大类名称下的一行信息)',

    private Integer rank;  // 先后顺序

    @Column(name = "picture_url")
    private String pictureUrl;    // varchar(200) comment '大类的图标url',

    private String description;   // varchar(1000),
    private String status;         // int default 1 comment '状态，0：弃用    1：使用中',

    // 外键：一的一方放弃维护关系
//    @OneToMany(mappedBy = "category_id")
    @OneToMany(targetEntity = Appointment.class)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Set<Appointment> appointment;

    public Set<Appointment> getAppointment() {
        return appointment;
    }

    public void setAppointment(Set<Appointment> appointment) {
        this.appointment = appointment;
    }

    public Category() {
    }

    public Category(String id) {
        this.id = id;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        return id != null ? id.equals(category.id) : category.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
