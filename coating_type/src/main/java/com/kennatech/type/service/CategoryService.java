package com.kennatech.type.service;

import com.kennatech.coating.entity.Result;
import com.kennatech.coating.entity.StatusCode;
import com.kennatech.coating.utils.IdWorker;
import com.kennatech.type.client.FileSystemClient;
import com.kennatech.type.dao.CategoryDao;
import com.kennatech.type.entity.QueryVo;
import com.kennatech.type.exception.MyException;
import com.kennatech.type.pojo.Category;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.plaf.nimbus.State;
import java.util.List;

@Service
@Transactional
public class CategoryService {


    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private FileSystemClient fileSystemClient;

    @Autowired
    private IdWorker idWorker;

    // 增加
    public void add(MultipartFile multipartFile, QueryVo queryVo) {

        // 校验提交的数据
        if (StringUtils.isEmpty(queryVo.getName()) ||
                StringUtils.isEmpty(queryVo.getDescription()) ||
                StringUtils.isEmpty(queryVo.getTitle()) ||
                StringUtils.isEmpty(queryVo.getRank())
        ) {
            throw new RuntimeException("提交的数据不完整");
        }
        Category category = new Category();
        category.setId(String.valueOf(idWorker.nextId()));

        category.setName(queryVo.getName());
        category.setTitle(queryVo.getTitle());
        category.setDescription(queryVo.getDescription());
        category.setRank(queryVo.getRank());

        Result result = fileSystemClient.upload(multipartFile);

        if (result.getCode() == StatusCode.ERROR) {
            throw new RuntimeException(result.getMessage());
        }

        category.setPictureUrl((String) result.getData());

        category.setStatus("1");
        categoryDao.save(category);

    }

    // 根据id进行逻辑删除（将属性status值设为0）
    public void deleteById(String id) {
        categoryDao.updateStatus(id);
    }

    // 修改服务大类
    public void update(String id,QueryVo queryVo, MultipartFile multipartFile) {


        if(StringUtils.isEmpty(id)){
            throw new RuntimeException("没有提交id");
        }
        Category category = categoryDao.findById(id).orElse(null);

        if (category == null) {
            throw new RuntimeException("没有找到需要修改的服务大类");
        }

        if (StringUtils.isNotEmpty(queryVo.getName())) {
            category.setName(queryVo.getName());
        }

        if (StringUtils.isNotEmpty(queryVo.getTitle())) {
            category.setTitle(queryVo.getTitle());
        }

        if (StringUtils.isNotEmpty(queryVo.getDescription())) {
            category.setDescription(queryVo.getDescription());
        }

        if (multipartFile != null) {
            Result result = fileSystemClient.upload(multipartFile);
            if (result.getCode() == StatusCode.ERROR) {
                throw new RuntimeException(result.getMessage());
            }
            category.setPictureUrl((String) result.getData());
        }

        if (StringUtils.isNotEmpty(queryVo.getRank())) {
            category.setRank(queryVo.getRank());
        }
        categoryDao.save(category);

    }


    // 根据id查询服务大类
    public Category findById(String id) {
        return categoryDao.findById(id).get();
    }

    //  查询所有status不为0的服务大类
    public List<Category> findAll() {
        return categoryDao.findAllByStatusNot("0");
    }

}
