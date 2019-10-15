package com.kennatech.coating.shop.controller;

import com.kennatech.coating.entity.Result;
import com.kennatech.coating.entity.StatusCode;
import com.kennatech.coating.shop.pojo.QueryVo;
import com.kennatech.coating.shop.pojo.Shop;
import com.kennatech.coating.shop.service.ShopService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private HttpServletRequest request;

    /**
     * 增加店铺
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody QueryVo queryVo) {
        shopService.add(queryVo);
        return new Result(true, StatusCode.OK, "店铺添加成功");
    }

    /**
     * 删除店铺
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String id) {
        shopService.deleteById(id);
        return new Result(true, StatusCode.OK, "店铺删除成功");
    }

    /**
     * 修改店铺
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Result update(@RequestBody QueryVo queryVo) {
        shopService.update(queryVo);
        return new Result(true, StatusCode.OK, "店铺修改成功");
    }


    /**
     * 查询店铺
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "店铺查询成功", shopService.findAll());
    }

    /**
     * 查询默认的店铺
     */
    @RequestMapping(value = "/default", method = RequestMethod.GET)
    public Result findDefaultShop() {
        Shop shop = null;
        try {
            shop = shopService.findDefaultShop();
            if (shop == null) {
                return new Result(false, StatusCode.ERROR, "店铺查询失败，未指定默认店铺");
            } else {
                return new Result(true, StatusCode.OK, "店铺查询成功", shop);
            }
        } catch (Exception e) {
            return new Result(true, StatusCode.ERROR, "店铺查询失败，存在多个默认店铺");
        }
    }
}
