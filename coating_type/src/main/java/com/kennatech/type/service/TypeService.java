package com.kennatech.type.service;

import com.kennatech.coating.entity.Result;
import com.kennatech.coating.entity.StatusCode;
import com.kennatech.coating.utils.IdWorker;
import com.kennatech.type.client.FileSystemClient;
import com.kennatech.type.dao.CategoryDao;
import com.kennatech.type.dao.TypeDao;
import com.kennatech.type.entity.QueryVo;
import com.kennatech.type.pojo.Category;
import com.kennatech.type.pojo.Type;
import jdk.nashorn.internal.runtime.arrays.TypedArrayData;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
public class TypeService {

    @Autowired
    private TypeDao typeDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private FileSystemClient fileSystemClient;

    // 增加
    public void add(MultipartFile multipartFile, QueryVo queryVo) {
        if (multipartFile == null ||
                StringUtils.isEmpty(queryVo.getName()) ||
                StringUtils.isEmpty(queryVo.getTitle()) ||
                StringUtils.isEmpty(queryVo.getDescription()) ||
                StringUtils.isEmpty(queryVo.getParent())
        ) {
            throw new RuntimeException("提交的数据不完整");
        }

        Result result = fileSystemClient.upload(multipartFile);
        if (result.getCode() == StatusCode.ERROR) {
            throw new RuntimeException(result.getMessage());
        }
        Type type = new Type();

        type.setPictureUrl((String) result.getData());

        type.setId(String.valueOf(idWorker.nextId()));

        type.setName(queryVo.getName());
        type.setTitle(queryVo.getTitle());
        type.setDescription(queryVo.getDescription());

        Category category = categoryDao.findById(queryVo.getId()).orElse(null);
        if (category == null) {
            throw new RuntimeException("未找到指定的服务大类");
        }
        type.setParent(category);
        type.setStatus("1");

        typeDao.save(type);


    }

    // 逻辑删除
    public void deleteById(String id) {
        typeDao.updateStatus(id);
    }

    // 修改
    public void update(String id, MultipartFile multipartFile, QueryVo queryVo) {

        Type type = typeDao.findById(id).orElse(null);
        if (type == null) {
            throw new RuntimeException("未找到修改的服务小类");
        }

        // 如果没有指定服务大类，就抛出异常
        if (StringUtils.isEmpty(queryVo.getParent())) {
            throw new RuntimeException("未指定服务小类所属服务大类");
        }

        Category category = categoryDao.findById(queryVo.getParent()).orElse(null);
        if (category == null) {
            throw new RuntimeException("未找到修改的服务大类");
        }
        type.setParent(category);

        if (StringUtils.isNotEmpty(queryVo.getName())) {
            type.setName(queryVo.getName());
        }
        if (StringUtils.isNotEmpty(queryVo.getTitle())) {
            type.setTitle(queryVo.getTitle());
        }
        if (StringUtils.isNotEmpty(queryVo.getDescription())) {
            type.setDescription(queryVo.getDescription());
        }
        if (StringUtils.isNotEmpty(queryVo.getStatus())) {
            type.setStatus(queryVo.getStatus());
        }
        typeDao.save(type);
    }


    // 根据id 查询
    public Type findById(String id) {
        return typeDao.findById(id).orElse(null);
    }

    // 查询所有status不为0的 服务小类
    public List<Type> findAll() {
        return  typeDao.findAllByStatusNot("0");
    }
}
