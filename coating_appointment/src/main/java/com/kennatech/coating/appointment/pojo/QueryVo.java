package com.kennatech.coating.appointment.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

import java.sql.Timestamp;
import java.util.Date;


public class QueryVo implements Serializable {
    private String id;               // varchar(50) not null,
    private String username;        // varchar(50) comment '预约的用户名，默认微信名',

    //    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;        // date comment '创建时间',

    //    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date appointmentDate;  // date comment '预约的时间',

    private String phone;            // varchar(20) comment '预约用户的手机号',
    private String category;        // char(10) comment '选择的服务大类',
    private String shop;               // char(10) comment '预约的店铺',


    private Integer status;

    public QueryVo() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "QueryVo{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", createDate=" + createDate +
                ", appointmentDate=" + appointmentDate +
                ", phone='" + phone + '\'' +
                ", category='" + category + '\'' +
                ", shop='" + shop + '\'' +
                ", status=" + status +
                '}';
    }
}
