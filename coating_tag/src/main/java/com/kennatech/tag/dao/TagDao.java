package com.kennatech.tag.dao;

import com.kennatech.tag.pojo.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagDao extends JpaSpecificationExecutor<Tag>, JpaRepository<Tag, String> {


    public Tag findByName(String name);

    @Query("update Tag set status = '0' where id = ?1")
    @Modifying
    public void updateStatus(String id);


    public List<Tag> findAllByStatusNot(String status);
}
