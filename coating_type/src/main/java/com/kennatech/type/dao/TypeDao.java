package com.kennatech.type.dao;

import com.kennatech.type.pojo.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeDao extends JpaRepository<Type,String>, JpaSpecificationExecutor<Type> {

    // 逻辑删除，将status值改为0
    @Query("update Type set status = 0 where id = ?1")
    @Modifying
    public void updateStatus(String id);


    // 查询所有status不为0的 服务小类
    public List<Type> findAllByStatusNot(String status);


}
