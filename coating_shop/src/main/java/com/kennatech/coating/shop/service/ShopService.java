package com.kennatech.coating.shop.service;

import com.kennatech.coating.shop.dao.ShopDao;
import com.kennatech.coating.shop.pojo.QueryVo;
import com.kennatech.coating.shop.pojo.Shop;
import com.kennatech.coating.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService {

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 增加店铺
     */
    public void add(QueryVo queryVo) {
        Shop shop = new Shop();
        shop.setId(String.valueOf(idWorker.nextId()));

        // 如果新添加的店铺被设置为默认店铺
        if(queryVo.getDefaultShop() == 1){
            // 首先，修改之前默认店铺为非默认店铺
            Shop defaultShop = shopDao.findShopByDefaultShop(1);
            defaultShop.setDefaultShop(0);
            shopDao.save(defaultShop);
        }
        // 然后，将当前的店铺修改为默认的
        // 这里queryVo.getDefaultShop()可能为0或1,
        shop.setDefaultShop(queryVo.getDefaultShop());

        shop.setName(queryVo.getName());
        shop.setAddress(queryVo.getAddress());
        shop.setStatus(1);
        shopDao.save(shop);
    }

    /**
     * 逻辑删除店铺
     *
     * @param id
     */
    public void deleteById(String id) {
        Shop shop = shopDao.findById(id).get();
        shop.setStatus(0);
        shopDao.save(shop);
    }


    /**
     * 修改店铺
     *
     * @param qureyVo
     */
    public void update(QueryVo qureyVo) {
        Shop shop = shopDao.findById(qureyVo.getId()).get();
        shop.setName(qureyVo.getName());
        shop.setAddress(qureyVo.getAddress());

        // 将不是默认店铺的店铺修改为默认的
        if (shop.getDefaultShop() == 0 && qureyVo.getDefaultShop() == 1) {
            // 首先，修改之前默认店铺为非默认店铺
            Shop defaultShop = shopDao.findShopByDefaultShop(1);
            defaultShop.setDefaultShop(0);
            shopDao.save(defaultShop);
            // 然后，将当前的店铺修改为默认的
            shop.setDefaultShop(1);
        }
        // 将是默认店铺的店铺修改为非默认的
        if (shop.getDefaultShop() == 1 && qureyVo.getDefaultShop() == 0) {
            shop.setDefaultShop(0);
        }
        shopDao.save(shop);


    }

    /**
     * 查询所有店铺
     * 店铺数量少，没必要分页
     */
    public List<Shop> findAll() {
        // 根据defaultShop降序排序，这样defaultShop值为1的默认店铺放在第一位
        return shopDao.findAll(Sort.by("defaultShop").descending());
    }

    public Shop findDefaultShop() {
        return shopDao.findShopByDefaultShop(1);
    }
}
