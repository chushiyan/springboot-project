package com.kennatech.coating.appointment.dao;

import com.kennatech.coating.appointment.pojo.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ShopDao extends JpaRepository<Shop, String>, JpaSpecificationExecutor<Shop> {
}
