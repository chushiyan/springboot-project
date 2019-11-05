package com.kennatech.type.controller;


import com.kennatech.coating.entity.Result;
import com.kennatech.coating.entity.StatusCode;
import com.kennatech.type.entity.QueryVo;
import com.kennatech.type.service.CategoryService;
import org.apache.commons.lang.StringUtils;
import org.hibernate.cfg.annotations.QueryBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private HttpServletRequest request;


    /**
     * 增加 服务大类
     *
     * @param multipartFile 服务大类的图标
     * @param queryVo       服务大类的其他属性
     * @return
     */
    // 超级管理员和普通管理员都有权限增加，未登录的没有
    @PostMapping
    public Result add(MultipartFile multipartFile, QueryVo queryVo) {
        String roles = (String) request.getAttribute("roles");

        if (StringUtils.isEmpty(roles)) {
            return new Result(false, StatusCode.ERROR, "您的权限不足");
        }

        categoryService.add(multipartFile, queryVo);
        return new Result(true, StatusCode.OK, "增加成功");
    }


    /**
     * 根据id进行逻辑删除（将属性status值设为0）
     *
     * @param id
     * @return
     */
    // 超级管理员和普通管理员都有权限删除，未登录的没有
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable String id) {
        String roles = (String) request.getAttribute("roles");
        if (StringUtils.isEmpty(roles)) {
            return new Result(false, StatusCode.ERROR, "您的权限不足");
        }
        categoryService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    // 修改
    @PutMapping("/{id}")
    // 超级管理员和普通管理员都有权限修改，未登录的没有
    public Result update(@PathVariable String id, MultipartFile multipartFile, QueryVo queryVo) {
        String roles = (String) request.getAttribute("roles");
        if (StringUtils.isEmpty(roles)) {
            return new Result(false, StatusCode.ERROR, "您的权限不足");
        }

        categoryService.update(id, queryVo, multipartFile);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 根据id查询服务大类
     *
     * @return
     */
    @GetMapping("/item/{id}")
    // 超级管理员和普通管理员、未登录的都有权限
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", categoryService.findById(id));
    }


    /**
     * 查询所有status不为0的服务大类
     * 由于数据量很小，没必要分页
     *
     * @return
     */
    // 超级管理员和普通管理员、未登录的都有权限
    @GetMapping("/list")
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", categoryService.findAll());
    }


}
