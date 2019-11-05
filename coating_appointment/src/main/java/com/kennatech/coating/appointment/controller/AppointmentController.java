package com.kennatech.coating.appointment.controller;


import com.kennatech.coating.appointment.pojo.Appointment;
import com.kennatech.coating.appointment.pojo.QueryVo;
import com.kennatech.coating.appointment.service.AppointmentService;
import com.kennatech.coating.entity.PageResult;
import com.kennatech.coating.entity.Result;
import com.kennatech.coating.entity.StatusCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@CrossOrigin
//@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    /**
     * 添加预约
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody QueryVo queryVo) {
        System.out.println(queryVo);
        // 如果提交的数据中username/phone/appointment任一为空，则预约失败
        if (StringUtils.isEmpty(queryVo.getUsername()) ||
                StringUtils.isEmpty(queryVo.getPhone()) ||
                queryVo.getAppointmentDate() == null) {
            return new Result(true, StatusCode.OK, "數據不合法，預約失败");
        }
        appointmentService.add(queryVo);
        return new Result(true, StatusCode.OK, "預約成功");
    }


    /**
     * 根据id删除预约
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id, HttpServletRequest request) {
        String roles = (String) request.getAttribute("roles");
        if (!StringUtils.isEmpty(roles)) {
            appointmentService.deleteById(id);
            return new Result(true, StatusCode.OK, "成功刪除預約");
        }
        return new Result(false, StatusCode.ERROR, "您的权限不足");
    }

    /**
     * 修改预约
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Result update(@RequestBody QueryVo queryVo, HttpServletRequest request) {
        // 检验是否是超级管理员或者普通管理员，如果是，则有权修改
        String roles = (String) request.getAttribute("roles");
        if (!StringUtils.isEmpty(roles)) {
            appointmentService.update(queryVo);
            return new Result(true, StatusCode.OK, "成功修改預約");
        } else {
            return new Result(false, StatusCode.ERROR, "您的权限不足");
        }
    }

    /**
     * 根据id查询预约
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id, HttpServletRequest request) {
        String roles = (String) request.getAttribute("roles");
        if (!StringUtils.isEmpty(roles)) {
            return new Result(true, StatusCode.OK, "查詢成功", appointmentService.findById(id));
        } else {
            return new Result(false, StatusCode.ERROR, "您的权限不足");
        }
    }


    /**
     * 分页查询
     */
    @RequestMapping(value = "search/{page}/{size}",method = RequestMethod.POST)
    public Result search(@RequestBody QueryVo queryVo, @PathVariable int page,
                         @PathVariable int size, HttpServletRequest request) {

        String roles = (String) request.getAttribute("roles");

        if (StringUtils.isNotEmpty(roles)) {

            Page pageList=appointmentService.search(queryVo,page,size);

            System.out.println(pageList);

            return new Result(true, StatusCode.OK, "查詢成功",
                    new PageResult<Appointment>(pageList.getTotalElements(),pageList.getContent() )
            );
        } else {
            return new Result(false, StatusCode.ERROR, "您的权限不足");
        }

    }
}
