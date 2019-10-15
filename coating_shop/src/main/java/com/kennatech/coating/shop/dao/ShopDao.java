package com.kennatech.coating.shop.dao;

import com.kennatech.coating.shop.pojo.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ShopDao  extends JpaRepository<Shop,String> , JpaSpecificationExecutor<Shop> {

    public Shop findShopByDefaultShop(Integer defaultShop);

}
