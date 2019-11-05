package com.kennatech.type.dao;

import com.kennatech.type.pojo.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TagDao extends JpaRepository<Tag, String>, JpaSpecificationExecutor<Tag> {
}
