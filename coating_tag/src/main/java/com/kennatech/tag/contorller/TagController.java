package com.kennatech.tag.contorller;

import com.kennatech.coating.entity.Result;
import com.kennatech.coating.entity.StatusCode;
import com.kennatech.tag.entity.QueryVo;
import com.kennatech.tag.pojo.Tag;
import com.kennatech.tag.service.TagService;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private HttpServletRequest request;

    // 增加标签
    // 超级管理员和普通管理员都有权限增加，未登录的没有
    @PostMapping
    public Result add(@RequestBody QueryVo queryVo) {

        String roles = (String) request.getAttribute("roles");

        if (StringUtils.isEmpty(roles)) {
            return new Result(false, StatusCode.ERROR, "您的权限不足");
        }

        tagService.add(queryVo);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    // 根据id 逻辑删除
    // 超级管理员和普通管理员都有权限删除，未登录的没有
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable String id) {
        String roles = (String) request.getAttribute("roles");

        if (StringUtils.isEmpty(roles)) {
            return new Result(false, StatusCode.ERROR, "您的权限不足");
        }
        tagService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    // 修改标签
    // 超级管理员和普通管理员都有权限修改，未登录的没有
    @PutMapping("/{id}")
    public Result update(@PathVariable String id, @RequestBody QueryVo queryVo) {
        String roles = (String) request.getAttribute("roles");

        if (StringUtils.isEmpty(roles)) {
            return new Result(false, StatusCode.ERROR, "您的权限不足");
        }

        queryVo.setId(id);
        tagService.update(queryVo);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @GetMapping("/item/{id}")
    // 超级管理员和普通管理员、未登录的都有权限
    public Result findById(@PathVariable String id) {
        Tag tag = tagService.findById(id);

        if (tag == null) {
            return new Result(false, StatusCode.ERROR, "查询失败");
        }
        return new Result(true, StatusCode.OK, "查询成功", tag);
    }

    @GetMapping("/list")
    // 超级管理员和普通管理员、未登录的都有权限
    public Result findAll() {
        List<Tag> tags = tagService.findAll();
        if (tags == null) {
            return new Result(false, StatusCode.ERROR, "查询失败");
        }
        return new Result(true, StatusCode.OK, "查询成功", tags);
    }

}
