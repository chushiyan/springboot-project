package com.kennatech.coating.appointment.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Entity
public class Appointment implements Serializable {
    @Id
    private String id;               // varchar(50) not null,
    private String username;        // varchar(50) comment '预约的用户名，默认微信名',

    @Column(name = "create_date")
    private Timestamp createDate;        // date comment '创建时间',

    @Column(name = "appointment_date")
    private Timestamp appointmentDate;  // date comment '预约的时间',

    private String phone;            // varchar(20) comment '预约用户的手机号',


    @JsonIgnoreProperties(value = {"appointment"}) // 忽略category表的appointment外键，避免出现多表查询死循环
    // 多对一关系映射：多个联系人对应客户
    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;        // char(10) comment '选择的服务大类',


    @JsonIgnoreProperties(value = {"appointment"})
    // 多对一关系映射：多个联系人对应客户
    @ManyToOne(targetEntity = Shop.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "shop_id", referencedColumnName = "id")
    private Shop shop;               // char(10) comment '预约的店铺',


    private Integer status;           // int default 1 comment '预约的状态，0：删除  1：正在服务   2：待处理    3：已完成     4：已过期  ',

    public Appointment() {
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Timestamp appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
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
        return "Appointment{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", createDate=" + createDate +
                ", appointmentDate=" + appointmentDate +
                ", phone='" + phone + '\'' +
                ", category=" + category +
                ", shop=" + shop +
                ", status='" + status + '\'' +
                '}';
    }
}
