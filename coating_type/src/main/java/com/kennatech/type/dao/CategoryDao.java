package com.kennatech.type.dao;

import com.kennatech.type.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryDao extends JpaRepository<Category,String> , JpaSpecificationExecutor<Category> {

    @Query("update Category set status = 0 where id = ?1")
    @Modifying
    public void updateStatus(String id);


    public List<Category> findAllByStatusNot(String status);
}
