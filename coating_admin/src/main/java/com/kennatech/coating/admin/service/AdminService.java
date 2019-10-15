package com.kennatech.coating.admin.service;

import com.kennatech.coating.admin.dao.AdminDao;
import com.kennatech.coating.admin.pojo.Admin;
import com.kennatech.coating.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
@Transactional
public class AdminService {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Admin findByNameAndPassword(Admin admin) {
        Admin adminFromDB = adminDao.findByUsername(admin.getUsername());

        if (adminFromDB != null && adminFromDB.getStatus() != 0 && bCryptPasswordEncoder.matches(admin.getPassword(), adminFromDB.getPassword())) {
            return adminFromDB;
        }
        return null;
    }


    // 添加管理员
    public void add(Admin admin) {
        // 生成、设置 id
        admin.setId(String.valueOf(idWorker.nextId()));
        // 加密密码
        admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
        admin.setStatus(1);
        admin.setRoles("general");
        adminDao.save(admin);
    }


    // 查询所有 可用的 管理员
    public List<Admin> findAll() {
        // 构建查询条件：status = 1
        Specification<Admin> spec = new Specification<Admin>() {
            public Predicate toPredicate(Root<Admin> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // cb:构建查询，添加查询方式   like：模糊匹配
                // root：从实体Customer对象中按照custName属性进行查询
                return cb.equal(root.get("status").as(Integer.class), 1);
            }
        };
        return adminDao.findAll(spec);
    }

    // 逻辑上删除普通管理员
    public void deleteById(String adminId) {
        Admin admin = adminDao.findById(adminId).get();
        admin.setStatus(0);
    }

    public void update(Admin admin) {
        admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
        adminDao.save(admin);
    }
}
