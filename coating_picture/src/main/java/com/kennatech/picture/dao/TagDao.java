package com.kennatech.picture.dao;

import com.kennatech.picture.pojo.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TagDao extends JpaRepository<Tag, String>, JpaSpecificationExecutor<Tag> {
}
