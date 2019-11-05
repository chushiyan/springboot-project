package com.kennatech.tag.entity;

import java.io.Serializable;

public class QueryVo implements Serializable {

    private String id;
    private String name;    // varchar(100),
    private String status;  // int default 1,

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
