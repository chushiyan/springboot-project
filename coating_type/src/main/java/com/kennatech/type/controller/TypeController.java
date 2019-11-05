package com.kennatech.type.controller;

import com.kennatech.coating.entity.Result;
import com.kennatech.coating.entity.StatusCode;
import com.kennatech.type.entity.QueryVo;
import com.kennatech.type.pojo.Type;
import com.kennatech.type.service.TypeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Configuration
@RequestMapping("/type")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private HttpServletRequest request;

    // 增加服务小类
    // 超级管理员和普通管理员都有权限增加，未登录的没有
    @PostMapping
    public Result add(MultipartFile multipartFile, QueryVo queryVo) {

        String roles = (String) request.getAttribute("roles");

        if (StringUtils.isEmpty(roles)) {
            return new Result(false, StatusCode.ERROR, "您的权限不足");
        }

        typeService.add(multipartFile, queryVo);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    // 逻辑删除
    // 超级管理员和普通管理员都有权限删除，未登录的没有
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable String id) {
        String roles = (String) request.getAttribute("roles");

        if (StringUtils.isEmpty(roles)) {
            return new Result(false, StatusCode.ERROR, "您的权限不足");
        }
        typeService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    // 修改服务小类
    // 超级管理员和普通管理员都有权限修改，未登录的没有
    @PutMapping("/{id}")
    public Result update(@PathVariable String id, MultipartFile multipartFile, QueryVo queryVo) {
        String roles = (String) request.getAttribute("roles");

        if (StringUtils.isEmpty(roles)) {
            return new Result(false, StatusCode.ERROR, "您的权限不足");
        }
        typeService.update(id, multipartFile, queryVo);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    // 根据id查询
    // 超级管理员和普通管理员、未登录的都有权限
    @GetMapping("/item/{id}")
    public Result findById(@PathVariable String id) {

        Type type = typeService.findById(id);
        if (type == null) {
            return new Result(false, StatusCode.ERROR, "查询失败");
        }
        return new Result(true, StatusCode.OK, "查询成功", typeService.findById(id));
    }

    // 查询所有status不为0的 服务小类
    // 超级管理员和普通管理员、未登录的都有权限
    @GetMapping("/list")
    public Result findAll() {
        List<Type> typeList = typeService.findAll();
        if (typeList == null) {
            return new Result(false, StatusCode.ERROR, "查询失败");
        }
        return new Result(true, StatusCode.OK, "查询成功", typeList);
    }


}
