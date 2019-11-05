package com.kennatech.picture.dao;

import com.kennatech.picture.pojo.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TypeDao extends JpaRepository<Type,String>, JpaSpecificationExecutor<Type> {
}
