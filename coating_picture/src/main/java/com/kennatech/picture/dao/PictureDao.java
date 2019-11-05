package com.kennatech.picture.dao;

import com.kennatech.picture.pojo.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface PictureDao extends JpaRepository<Picture, String>, JpaSpecificationExecutor<Picture> {

    // 逻辑删除，将status值设为0
    @Query(value = "update Picture set status = 0 where id = ?1")
    @Modifying

    public void updateStatus(String id);



}
