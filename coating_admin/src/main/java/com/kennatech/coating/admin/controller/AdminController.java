package com.kennatech.coating.admin.controller;

import com.kennatech.coating.admin.pojo.Admin;
import com.kennatech.coating.admin.service.AdminService;
import com.kennatech.coating.entity.Result;
import com.kennatech.coating.entity.StatusCode;
import com.kennatech.coating.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@CrossOrigin
@RestController
//@RequestMapping("/admin")
@Api(value = "管理员接口", description = "提供管理员的增删改查")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtUtil jwtUtil;

    // 超级管理员和普通管理员 登录
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation("超级管理员和普通管理员登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "admin", value = "管理员对象", required = true)})
    public Result login(@RequestBody Admin admin) {
        Admin adminFromDB = adminService.findByNameAndPassword(admin);
        if (adminFromDB != null ) {
            // 管理员账号密码验证成功之后，发jwt令牌
            // 在令牌中保存 id/username/roles
            String token = jwtUtil.createJWT(admin.getId(), admin.getUsername(), adminFromDB.getRoles());
            Map map = new HashMap();
            map.put("token", token);  // 令牌
            map.put("name", admin.getUsername());// 登陆名
            return new Result(true, StatusCode.OK, "登陆成功", map);
        } else {
            return new Result(false, StatusCode.ERROR, "用户名或者密码错误");
        }
    }

    // 添加管理员
    // 只有超级管理员才有权限添加普通管理员
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation("超级管理员添加普通管理员")
    @ApiImplicitParams({@ApiImplicitParam(name = "admin", value = "管理员对象", required = true)})
    public Result add(@RequestBody Admin admin, HttpServletRequest request) {

        // JwtInterceptor拦截中已经进行拦截了，并在request域中添加了roles属性
        String roles = (String) request.getAttribute("roles");

        if (!StringUtils.isEmpty(roles) && roles.equals("super")) {
            adminService.add(admin);
            return new Result(true, StatusCode.OK, "添加普通管理员成功");
        }
        return new Result(false, StatusCode.ERROR, "您的权限不足");
    }

    // 查询所有管理员
    // 只有超级管理员才有权限查询所有管理员
    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation("超级管理员查询所有管理员")
    public Result findAll(HttpServletRequest request) {
        String roles = (String) request.getAttribute("roles");
        if (!StringUtils.isEmpty(roles) && roles.equals("super")) {
            return new Result(true, StatusCode.OK, "查询成功", adminService.findAll());
        }
        return new Result(false, StatusCode.ERROR, "您的权限不足");
    }

    // 删除普通管理员
    // 只有超级管理员才有权限删除普通管理员
    @RequestMapping(value = "/{adminId}", method = RequestMethod.DELETE)
    @ApiOperation("超级管理员查询所有管理员")
    public Result delete(@PathVariable String adminId, HttpServletRequest request) {

        String roles = (String) request.getAttribute("roles");

        if (!StringUtils.isEmpty(roles) && roles.equals("super")) {
            adminService.deleteById(adminId);
            return new Result(true, StatusCode.OK, "成功删除普通管理员");
        }
        return new Result(false, StatusCode.ERROR, "您的权限不足");
    }


    // 修改管理员密码
    @RequestMapping(value = "/{adminId}", method = RequestMethod.PUT)
    @ApiOperation("超级管理员查询所有管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminId", value = "管理员id", required = true),
            @ApiImplicitParam(name = "admin", value = "管理员对象", required = true)
    })
    public Result update(@PathVariable String adminId, @RequestBody Admin admin, HttpServletRequest request) {
        String roles = (String) request.getAttribute("roles");
        if (!StringUtils.isEmpty(roles)) {
            if(roles.equals("super")){
                admin.setId(adminId);
                adminService.update(admin);
                return new Result(true, StatusCode.OK, "修改成功");
            }
            if(roles.equals("general")){
                if(adminId.equals(request.getAttribute("adminId"))){
                    admin.setId(adminId);
                    adminService.update(admin);
                    return new Result(true, StatusCode.OK, "修改成功");
                }
            }
        }
        return new Result(false, StatusCode.ERROR, "您的权限不足");
    }



}
