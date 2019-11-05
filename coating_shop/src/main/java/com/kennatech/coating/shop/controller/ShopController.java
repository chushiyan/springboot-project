package com.kennatech.coating.shop.controller;

import com.kennatech.coating.entity.Result;
import com.kennatech.coating.entity.StatusCode;
import com.kennatech.coating.shop.pojo.QueryVo;
import com.kennatech.coating.shop.pojo.Shop;
import com.kennatech.coating.shop.service.ShopService;
import com.netflix.discovery.converters.Auto;
import org.apache.commons.lang.StringUtils;
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
    // 超级管理员和普通管理员都有权限增加，未登录的没有
    @PostMapping
    public Result add(@RequestBody QueryVo queryVo) {

        String roles = (String) request.getAttribute("roles");

        if (StringUtils.isEmpty(roles)) {
            return new Result(false, StatusCode.ERROR, "您的权限不足");
        }

        shopService.add(queryVo);
        return new Result(true, StatusCode.OK, "店铺添加成功");
    }

    /**
     * 删除店铺
     */
    // 超级管理员和普通管理员都有权限删除，未登录的没有
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String id) {
        String roles = (String) request.getAttribute("roles");

        if (StringUtils.isEmpty(roles)) {
            return new Result(false, StatusCode.ERROR, "您的权限不足");
        }

        shopService.deleteById(id);
        return new Result(true, StatusCode.OK, "店铺删除成功");
    }

    /**
     * 修改店铺
     */
    // 超级管理员和普通管理员都有权限修改，未登录的没有
    @RequestMapping(method = RequestMethod.PUT)
    public Result update(@RequestBody QueryVo queryVo) {

        String roles = (String) request.getAttribute("roles");

        if (StringUtils.isEmpty(roles)) {
            return new Result(false, StatusCode.ERROR, "您的权限不足");
        }

        shopService.update(queryVo);
        return new Result(true, StatusCode.OK, "店铺修改成功");
    }


    /**
     * 查询店铺
     */
    // 超级管理员和普通管理员、未登录的都有权限
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "店铺查询成功", shopService.findAll());
    }

    /**
     * 查询默认的店铺
     */
    // 超级管理员和普通管理员、未登录的都有权限
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
