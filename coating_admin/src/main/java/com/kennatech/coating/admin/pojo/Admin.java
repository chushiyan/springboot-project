package com.kennatech.coating.admin.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * admin 后台管理员表
 */
@Entity
@Table(name = "data")
public class Admin implements Serializable {
    @Id
    private String id;  // id
    private String username;  // 用户名
    private String password; // 密码
    private String roles; // 角色，分为两种super / general
    private Integer status;  // 状态，分为 0：删除   1：使用中

    public Admin() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}