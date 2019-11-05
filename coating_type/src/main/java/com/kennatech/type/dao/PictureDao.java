package com.kennatech.type.dao;

import com.kennatech.type.pojo.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PictureDao extends JpaRepository<Picture, String>, JpaSpecificationExecutor<Picture> {





}
