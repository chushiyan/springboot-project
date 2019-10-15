package com.kennatech.coating.shop.pojo;

import java.util.List;
import java.util.Set;

// 用于接收前端传来的店铺数据
public class QueryVo {
    private String id; //              varchar(50) not null,
    private String name; //              varchar(50) comment '店铺名称',
    private Integer defaultShop;     //  值为1 时，是默认店铺
    private String address; //              varchar(150) comment '店铺地址',
    private Integer status; //              int default 1 comment '店铺状态  0：弃用     1：使用中',
    private List<String> appointment;

    public QueryVo() {
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

    public Integer getDefaultShop() {
        return defaultShop;
    }

    public void setDefaultShop(Integer defaultShop) {
        this.defaultShop = defaultShop;
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

    public List<String> getAppointment() {
        return appointment;
    }

    public void setAppointment(List<String> appointment) {
        this.appointment = appointment;
    }
}
