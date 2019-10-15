package com.kennatech.coating.appointment.dao;

import com.kennatech.coating.appointment.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoryDao extends JpaRepository<Category, String>, JpaSpecificationExecutor<Category> {
}
