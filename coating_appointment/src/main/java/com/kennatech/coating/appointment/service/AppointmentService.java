package com.kennatech.coating.appointment.service;

import com.kennatech.coating.appointment.dao.AppointmentDao;
import com.kennatech.coating.appointment.dao.CategoryDao;
import com.kennatech.coating.appointment.dao.ShopDao;
import com.kennatech.coating.appointment.pojo.Appointment;
import com.kennatech.coating.appointment.pojo.Category;
import com.kennatech.coating.appointment.pojo.QueryVo;
import com.kennatech.coating.appointment.pojo.Shop;
import com.kennatech.coating.utils.IdWorker;
import com.netflix.discovery.converters.Auto;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class AppointmentService {

    @Autowired
    private AppointmentDao appointmentDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 添加预约
     */
    public void add(QueryVo queryVo) {

        Appointment appointment = new Appointment();

        appointment.setId(String.valueOf(idWorker.nextId()));

        appointment.setUsername(queryVo.getUsername());

        appointment.setCreateDate(new Timestamp(new Date().getTime()));

        appointment.setAppointmentDate(new Timestamp(queryVo.getAppointmentDate().getTime()));

        appointment.setPhone(queryVo.getPhone());

        Category category = categoryDao.findById(queryVo.getCategory()).orElse(null);
        appointment.setCategory(category);

        Shop shop = shopDao.findById(queryVo.getShop()).orElse(null);
        appointment.setShop(shop);

        appointment.setStatus(1);

        appointmentDao.save(appointment);
    }


    /**
     * 删除预约
     * 只是逻辑删除
     */
    public void deleteById(String id) {
        Appointment appointment = appointmentDao.findById(id).orElse(null);
        if (appointment == null) {
            throw new RuntimeException("預約不存在");
        }
        appointment.setStatus(0);
        appointmentDao.save(appointment);
    }

    /**
     * 修改预约
     */
    public void update(QueryVo queryVo) {
        // 1、设置id（在controller中已经校验了id）
        Appointment appointment = appointmentDao.findById(queryVo.getId()).get();

        // 2、username不为空则设置这个新的username
        if (StringUtils.isNotEmpty(queryVo.getUsername())) {
            appointment.setUsername(queryVo.getUsername());
        }

        // 3、appointmentDate不为空则设置这个新的appointmentDate
        if (queryVo.getAppointmentDate() != null) {
            appointment.setAppointmentDate(new Timestamp(queryVo.getAppointmentDate().getTime()));
        }

        // 4、phone不为空则设置这个新的phone
        if (StringUtils.isNotEmpty(queryVo.getPhone())) {
            appointment.setPhone(queryVo.getPhone());
        }

        // 5、category不为空则设置这个新的category
        if (StringUtils.isNotEmpty(queryVo.getCategory())) {
            Category category = categoryDao.findById(queryVo.getCategory()).get();
            appointment.setCategory(category);
        }

        // 6、shop不为空则设置这个新的shop
        if (StringUtils.isNotEmpty(queryVo.getShop())) {
            Shop shop = shopDao.findById(queryVo.getShop()).get();
            appointment.setShop(shop);
        }

        // 7、status不为空则设置这个新的status
        if (queryVo.getStatus() != null) {
            appointment.setStatus(queryVo.getStatus());
        }

        appointmentDao.save(appointment);
    }

    /**
     * 根据id查询预约
     */
    public Appointment findById(String id) {
        return appointmentDao.findById(id).get();
    }




    /**
     * 根据条件分页查询
     */
    public Page<Appointment> search(QueryVo queryVo, int page, int size) {
        Specification<Appointment> specification = new Specification<Appointment>() {
            @Override
            public Predicate toPredicate(Root<Appointment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<Predicate>();

                // 剔除status等于0的记录（等于0的是被逻辑删除的）
                predicateList.add(criteriaBuilder.notEqual(root.get("status").as(Integer.class), 0));


                // 如果username不为空就加入查询条件
                if (StringUtils.isNotEmpty(queryVo.getUsername())) {
                    predicateList.add(criteriaBuilder.like(root.get("username").as(String.class), "%" + queryVo.getUsername() + "%"));
                }

                // 如果category不为空就加入查询条件
                if (StringUtils.isNotEmpty(queryVo.getCategory())) {
                    // appointment和category 两张表关联查询
                    Join<Appointment, Category> join = root.join(root.getModel().getSingularAttribute("category", Category.class), JoinType.INNER);
                    predicateList.add(criteriaBuilder.like(join.get("id").as(String.class), "%" + queryVo.getCategory() + "%"));
                }

                // 如果shop不为空就加入查询条件
                if (StringUtils.isNotEmpty(queryVo.getShop())) {
                    // appointment和shop 两张表关联查询
                    Join<Appointment, Shop> join = root.join(root.getModel().getSingularAttribute("shop", Shop.class), JoinType.LEFT);
                    predicateList.add(criteriaBuilder.like(join.get("id").as(String.class), "%" + queryVo.getShop() + "%"));
                }


                // 如果phone不为空就加入查询条件
                if (StringUtils.isNotEmpty(queryVo.getPhone())) {
                    predicateList.add(criteriaBuilder.like(root.get("phone").as(String.class), queryVo.getPhone()));
                }

                // 如果status不为空就加入查询条件
                if (queryVo.getStatus() != null) {
                    predicateList.add(
                            criteriaBuilder.equal(root.get("status").as(Integer.class), queryVo.getStatus())
                    );
                }
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };

        // 根据预约时间降序排序
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("appointmentDate").descending());

        return appointmentDao.findAll(specification, pageRequest);
    }
}