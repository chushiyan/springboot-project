package com.kennatech.coating.shop.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Shop implements Serializable {
    @Id
    private String id; //              varchar(50) not null,
    private String name; //              varchar(50) comment '店铺名称',

    @Column(name = "default_shop")
    private Integer defaultShop;     //  值为1 时，是默认店铺
    private String address; //              varchar(150) comment '店铺地址',
    private Integer status; //              int default 1 comment '店铺状态  0：弃用     1：使用中',


    @JsonBackReference
//    @JsonIgnoreProperties(value = {"appointment"})
    @OneToMany(targetEntity = Appointment.class)
    @JoinColumn(name = "shop_id", referencedColumnName = "id")
    private Set<Appointment> appointment;

    public Set<Appointment> getAppointment() {
        return appointment;
    }

    public void setAppointment(Set<Appointment> appointment) {
        this.appointment = appointment;
    }

    public Shop() {
    }

    public Shop(String id) {
        this.id = id;
    }

    public Shop(String name, String address) {
        this.name = name;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDefaultShop() {
        return defaultShop;
    }

    public void setDefaultShop(Integer defaultShop) {
        this.defaultShop = defaultShop;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
