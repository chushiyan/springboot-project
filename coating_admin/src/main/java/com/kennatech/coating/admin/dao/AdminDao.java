package com.kennatech.coating.admin.dao;

import com.kennatech.coating.admin.pojo.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AdminDao extends JpaRepository<Admin,String>, JpaSpecificationExecutor<Admin> {

    public Admin findByUsername(String name);



}


