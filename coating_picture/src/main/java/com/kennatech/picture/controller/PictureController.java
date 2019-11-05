package com.kennatech.picture.controller;

import com.kennatech.coating.entity.PageResult;
import com.kennatech.coating.entity.Result;
import com.kennatech.coating.entity.StatusCode;
import com.kennatech.picture.entity.QueryVo;
import com.kennatech.picture.pojo.Picture;
import com.kennatech.picture.service.PictureService;
import org.apache.commons.lang.StringUtils;
import org.csource.common.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QuerydslUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/picture")
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @Autowired
    private HttpServletRequest request;

    /**
     * 增加
     *
     * @param multipartFile post提交的图片
     * @param queryVo       post提交的图片相关信息
     * @return
     */
    // 超级管理员和普通管理员、未登录的都有权限
    @PostMapping
    public Result add(MultipartFile multipartFile, QueryVo queryVo) {
        if (multipartFile == null) {
            return new Result(false, StatusCode.ERROR, "没有上传文件");
        }

        if (StringUtils.isEmpty(queryVo.getName()) || StringUtils.isEmpty(queryVo.getCategory()) ||
                StringUtils.isEmpty(queryVo.getType()) || queryVo.getType() == null ||
                StringUtils.isEmpty(queryVo.getDescription())) {
            return new Result(false, StatusCode.ERROR, "参数不完整");
        }

        Picture picture = null;
        try {
            picture = pictureService.add(multipartFile, queryVo);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "图片上传失败");
        }

        return new Result(true, StatusCode.OK, "图片上传成功", picture);

    }


    /**
     * 逻辑删除，将status值设为0
     *
     * @param id
     * @return
     */
    // 超级管理员和普通管理员都有权限删除，未登录的没有
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        String roles = (String) request.getAttribute("roles");

        if (StringUtils.isEmpty(roles)) {
            return new Result(false, StatusCode.ERROR, "您的权限不足");
        }

        pictureService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 修改
     *
     * @param id
     * @param multipartFile
     * @param queryVo
     * @return
     * @throws IOException
     * @throws MyException
     */
    @PutMapping("/{id}")
    // 超级管理员和普通管理员都有权限修改，未登录的没有
    public Result update(@PathVariable String id, MultipartFile multipartFile, QueryVo queryVo) throws IOException, MyException {
        String roles = (String) request.getAttribute("roles");

        if (StringUtils.isEmpty(roles)) {
            return new Result(false, StatusCode.ERROR, "您的权限不足");
        }

        pictureService.update(id, multipartFile, queryVo);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    // 超级管理员和普通管理员都有权限查询，未登录的没有
    public Result search(@PathVariable int page, @PathVariable int size, @RequestBody QueryVo queryVo) {
        String roles = (String) request.getAttribute("roles");

        if (StringUtils.isEmpty(roles)) {
            return new Result(false, StatusCode.ERROR, "您的权限不足");
        }

        Page pageList = pictureService.search(page, size, queryVo);
        PageResult<Picture> pageRequest = new PageResult<Picture>(pageList.getTotalElements(), pageList.getContent());
        return new Result(true, StatusCode.OK, "查询成功", pageRequest);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    // 超级管理员和普通管理员、未登录的都有权限
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", pictureService.findById(id));
    }


}
